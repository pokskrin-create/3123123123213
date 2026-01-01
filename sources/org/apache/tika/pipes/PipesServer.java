package org.apache.tika.pipes;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.BasicEmbeddedDocumentBytesHandler;
import org.apache.tika.extractor.EmbeddedDocumentByteStoreExtractorFactory;
import org.apache.tika.extractor.EmbeddedDocumentBytesHandler;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.extractor.EmbeddedDocumentExtractorFactory;
import org.apache.tika.extractor.RUnpackExtractor;
import org.apache.tika.extractor.RUnpackExtractorFactory;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.filter.MetadataFilter;
import org.apache.tika.metadata.listfilter.MetadataListFilter;
import org.apache.tika.metadata.listfilter.NoOpListFilter;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DigestingParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.RecursiveParserWrapper;
import org.apache.tika.pipes.FetchEmitTuple;
import org.apache.tika.pipes.HandlerConfig;
import org.apache.tika.pipes.emitter.EmitData;
import org.apache.tika.pipes.emitter.EmitKey;
import org.apache.tika.pipes.emitter.Emitter;
import org.apache.tika.pipes.emitter.EmitterManager;
import org.apache.tika.pipes.emitter.StreamEmitter;
import org.apache.tika.pipes.extractor.EmbeddedDocumentBytesConfig;
import org.apache.tika.pipes.extractor.EmittingEmbeddedDocumentBytesHandler;
import org.apache.tika.pipes.fetcher.Fetcher;
import org.apache.tika.pipes.fetcher.FetcherManager;
import org.apache.tika.utils.ExceptionUtils;
import org.apache.tika.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class PipesServer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) PipesServer.class);
    public static final int TIMEOUT_EXIT_CODE = 17;
    private Parser autoDetectParser;
    private Detector detector;
    private DigestingParser.Digester digester;
    private EmitterManager emitterManager;
    private FetcherManager fetcherManager;
    private final DataInputStream input;
    private final long maxForEmitBatchBytes;
    private final DataOutputStream output;
    private Parser rMetaParser;
    private final long serverParseTimeoutMillis;
    private final long serverWaitTimeoutMillis;
    private TikaConfig tikaConfig;
    private final Path tikaConfigPath;
    private final Object[] lock = new Object[0];
    private long checkForTimeoutMs = 1000;
    private volatile boolean parsing = false;
    private volatile long since = System.currentTimeMillis();

    public enum STATUS {
        READY,
        CALL,
        PING,
        FAILED_TO_START,
        FETCHER_NOT_FOUND,
        EMITTER_NOT_FOUND,
        FETCHER_INITIALIZATION_EXCEPTION,
        FETCH_EXCEPTION,
        PARSE_SUCCESS,
        PARSE_EXCEPTION_NO_EMIT,
        EMIT_SUCCESS,
        EMIT_SUCCESS_PARSE_EXCEPTION,
        EMIT_EXCEPTION,
        OOM,
        TIMEOUT,
        EMPTY_OUTPUT,
        INTERMEDIATE_RESULT;

        byte getByte() {
            return (byte) (ordinal() + 1);
        }

        public static STATUS lookup(int i) {
            int i2 = i - 1;
            if (i2 < 0) {
                throw new IllegalArgumentException("byte must be > 0");
            }
            STATUS[] statusArrValues = values();
            if (i2 >= statusArrValues.length) {
                throw new IllegalArgumentException("byte with index " + i2 + " must be < " + statusArrValues.length);
            }
            return statusArrValues[i2];
        }
    }

    public PipesServer(Path path, InputStream inputStream, PrintStream printStream, long j, long j2, long j3) throws TikaException, SAXException, IOException {
        this.tikaConfigPath = path;
        this.input = new DataInputStream(inputStream);
        this.output = new DataOutputStream(printStream);
        this.maxForEmitBatchBytes = j;
        this.serverParseTimeoutMillis = j2;
        this.serverWaitTimeoutMillis = j3;
    }

    public static void main(String[] strArr) throws Exception {
        try {
            PipesServer pipesServer = new PipesServer(Paths.get(strArr[0], new String[0]), System.in, System.out, Long.parseLong(strArr[1]), Long.parseLong(strArr[2]), Long.parseLong(strArr[3]));
            System.setIn(UnsynchronizedByteArrayInputStream.builder().setByteArray(new byte[0]).get());
            System.setOut(System.err);
            Thread thread = new Thread(pipesServer, "Tika Watchdog");
            thread.setDaemon(true);
            thread.start();
            pipesServer.processRequests();
        } finally {
            LOG.info("server shutting down");
        }
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        while (true) {
            try {
                synchronized (this.lock) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.since;
                    if (this.parsing && jCurrentTimeMillis > this.serverParseTimeoutMillis) {
                        LOG.warn("timeout server; elapsed {}  with {}", Long.valueOf(jCurrentTimeMillis), Long.valueOf(this.serverParseTimeoutMillis));
                        exit(17);
                    } else if (!this.parsing) {
                        long j = this.serverWaitTimeoutMillis;
                        if (j > 0 && jCurrentTimeMillis > j) {
                            LOG.info("closing down from inactivity");
                            exit(0);
                        }
                    }
                }
                Thread.sleep(this.checkForTimeoutMs);
            } catch (InterruptedException unused) {
                LOG.debug("interrupted");
                return;
            }
        }
    }

    public void processRequests() throws IOException {
        Logger logger = LOG;
        logger.debug("processing requests");
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            initializeResources();
            if (logger.isTraceEnabled()) {
                logger.trace("timer -- initialize parser and other resources: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
            }
            logger.debug("pipes server initialized");
            try {
                write(STATUS.READY);
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                while (true) {
                    int i = this.input.read();
                    if (i == -1) {
                        LOG.warn("received -1 from client; shutting down");
                        exit(1);
                    } else if (i == STATUS.PING.getByte()) {
                        Logger logger2 = LOG;
                        if (logger2.isTraceEnabled()) {
                            logger2.trace("timer -- ping: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis2));
                        }
                        write(STATUS.PING);
                        jCurrentTimeMillis2 = System.currentTimeMillis();
                    } else {
                        if (i != STATUS.CALL.getByte()) {
                            break;
                        }
                        parseOne();
                        Logger logger3 = LOG;
                        if (logger3.isTraceEnabled()) {
                            logger3.trace("timer -- parse one: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis2));
                        }
                        jCurrentTimeMillis2 = System.currentTimeMillis();
                    }
                    this.output.flush();
                }
                throw new IllegalStateException("Unexpected request");
            } catch (Throwable th) {
                LOG.error("main loop error (did the forking process shut down?)", th);
                exit(1);
                System.err.flush();
            }
        } catch (Throwable th2) {
            LOG.error("couldn't initialize parser", th2);
            try {
                this.output.writeByte(STATUS.FAILED_TO_START.getByte());
                this.output.flush();
            } catch (IOException e) {
                LOG.warn("couldn't notify of failure to start", (Throwable) e);
            }
        }
    }

    private boolean metadataIsEmpty(List<Metadata> list) {
        return list == null || list.size() == 0;
    }

    private String getContainerStacktrace(FetchEmitTuple fetchEmitTuple, List<Metadata> list) {
        String str;
        return (metadataIsEmpty(list) || (str = list.get(0).get(TikaCoreProperties.CONTAINER_EXCEPTION)) == null) ? "" : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016 A[Catch: IOException | TikaEmitterException -> 0x0039, IOException -> 0x003b, TRY_LEAVE, TryCatch #3 {IOException | TikaEmitterException -> 0x0039, blocks: (B:4:0x000c, B:6:0x0012, B:7:0x0016), top: B:23:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void emit(java.lang.String r3, org.apache.tika.pipes.emitter.EmitKey r4, boolean r5, org.apache.tika.pipes.PipesServer.MetadataListAndEmbeddedBytes r6, java.lang.String r7, org.apache.tika.parser.ParseContext r8) throws java.io.IOException {
        /*
            r2 = this;
            org.apache.tika.pipes.emitter.EmitterManager r0 = r2.emitterManager     // Catch: java.lang.IllegalArgumentException -> L53
            java.lang.String r1 = r4.getEmitterName()     // Catch: java.lang.IllegalArgumentException -> L53
            org.apache.tika.pipes.emitter.Emitter r3 = r0.getEmitter(r1)     // Catch: java.lang.IllegalArgumentException -> L53
            if (r5 == 0) goto L16
            boolean r5 = r6.toBePackagedForStreamEmitter()     // Catch: org.apache.tika.pipes.emitter.TikaEmitterException -> L39 java.io.IOException -> L3b
            if (r5 == 0) goto L16
            r2.emitContentsAndBytes(r3, r4, r6)     // Catch: org.apache.tika.pipes.emitter.TikaEmitterException -> L39 java.io.IOException -> L3b
            goto L21
        L16:
            java.lang.String r4 = r4.getEmitKey()     // Catch: org.apache.tika.pipes.emitter.TikaEmitterException -> L39 java.io.IOException -> L3b
            java.util.List r5 = r6.getMetadataList()     // Catch: org.apache.tika.pipes.emitter.TikaEmitterException -> L39 java.io.IOException -> L3b
            r3.emit(r4, r5, r8)     // Catch: org.apache.tika.pipes.emitter.TikaEmitterException -> L39 java.io.IOException -> L3b
        L21:
            boolean r3 = org.apache.tika.utils.StringUtils.isBlank(r7)
            if (r3 == 0) goto L2d
            org.apache.tika.pipes.PipesServer$STATUS r3 = org.apache.tika.pipes.PipesServer.STATUS.EMIT_SUCCESS
            r2.write(r3)
            return
        L2d:
            org.apache.tika.pipes.PipesServer$STATUS r3 = org.apache.tika.pipes.PipesServer.STATUS.EMIT_SUCCESS_PARSE_EXCEPTION
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r7.getBytes(r4)
            r2.write(r3, r4)
            return
        L39:
            r3 = move-exception
            goto L3c
        L3b:
            r3 = move-exception
        L3c:
            org.slf4j.Logger r4 = org.apache.tika.pipes.PipesServer.LOG
            java.lang.String r5 = "emit exception"
            r4.warn(r5, r3)
            java.lang.String r3 = org.apache.tika.utils.ExceptionUtils.getStackTrace(r3)
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r3 = r3.getBytes(r4)
            org.apache.tika.pipes.PipesServer$STATUS r4 = org.apache.tika.pipes.PipesServer.STATUS.EMIT_EXCEPTION
            r2.write(r4, r3)
            return
        L53:
            java.lang.String r3 = r2.getNoEmitterMsg(r3)
            org.slf4j.Logger r4 = org.apache.tika.pipes.PipesServer.LOG
            r4.warn(r3)
            org.apache.tika.pipes.PipesServer$STATUS r4 = org.apache.tika.pipes.PipesServer.STATUS.EMITTER_NOT_FOUND
            r2.write(r4, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.pipes.PipesServer.emit(java.lang.String, org.apache.tika.pipes.emitter.EmitKey, boolean, org.apache.tika.pipes.PipesServer$MetadataListAndEmbeddedBytes, java.lang.String, org.apache.tika.parser.ParseContext):void");
    }

    private void emitContentsAndBytes(Emitter emitter, EmitKey emitKey, MetadataListAndEmbeddedBytes metadataListAndEmbeddedBytes) {
        if (!(emitter instanceof StreamEmitter)) {
            throw new IllegalArgumentException("The emitter for embedded document byte store must be a StreamEmitter. I see: " + emitter.getClass());
        }
        throw new UnsupportedOperationException("this is not yet implemented");
    }

    private void parseOne() {
        synchronized (this.lock) {
            this.parsing = true;
            this.since = System.currentTimeMillis();
        }
        FetchEmitTuple fetchEmitTuple = null;
        try {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                fetchEmitTuple = readFetchEmitTuple();
                Logger logger = LOG;
                if (logger.isTraceEnabled()) {
                    logger.trace("timer -- read fetchEmitTuple: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                actuallyParse(fetchEmitTuple);
                if (logger.isTraceEnabled()) {
                    logger.trace("timer -- actually parsed: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis2));
                }
                synchronized (this.lock) {
                    this.parsing = false;
                    this.since = System.currentTimeMillis();
                }
            } catch (OutOfMemoryError e) {
                handleOOM(fetchEmitTuple.getId(), e);
                synchronized (this.lock) {
                    this.parsing = false;
                    this.since = System.currentTimeMillis();
                }
            }
        } catch (Throwable th) {
            synchronized (this.lock) {
                this.parsing = false;
                this.since = System.currentTimeMillis();
                throw th;
            }
        }
    }

    private void actuallyParse(FetchEmitTuple fetchEmitTuple) throws Throwable {
        MetadataListAndEmbeddedBytes fromTuple;
        EmbeddedDocumentBytesHandler embeddedDocumentBytesHandler;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Fetcher fetcher = getFetcher(fetchEmitTuple);
        if (fetcher == null) {
            return;
        }
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("timer -- got fetcher: {}ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        try {
            fromTuple = parseFromTuple(fetchEmitTuple, fetcher);
        } catch (Throwable th) {
            th = th;
            fromTuple = null;
        }
        try {
            if (logger.isTraceEnabled()) {
                logger.trace("timer -- to parse: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis2));
            }
            try {
                if (fromTuple == null || metadataIsEmpty(fromTuple.getMetadataList())) {
                    write(STATUS.EMPTY_OUTPUT);
                    if (fromTuple == null || !fromTuple.hasEmbeddedDocumentByteStore() || !(fromTuple.getEmbeddedDocumentBytesHandler() instanceof Closeable)) {
                        return;
                    } else {
                        embeddedDocumentBytesHandler = fromTuple.getEmbeddedDocumentBytesHandler();
                    }
                } else {
                    emitParseData(fetchEmitTuple, fromTuple);
                    if (fromTuple == null || !fromTuple.hasEmbeddedDocumentByteStore() || !(fromTuple.getEmbeddedDocumentBytesHandler() instanceof Closeable)) {
                        return;
                    } else {
                        embeddedDocumentBytesHandler = fromTuple.getEmbeddedDocumentBytesHandler();
                    }
                }
                embeddedDocumentBytesHandler.close();
            } catch (IOException e) {
                LOG.warn("problem closing embedded document byte store", (Throwable) e);
            }
        } catch (Throwable th2) {
            th = th2;
            if (fromTuple != null && fromTuple.hasEmbeddedDocumentByteStore() && (fromTuple.getEmbeddedDocumentBytesHandler() instanceof Closeable)) {
                try {
                    fromTuple.getEmbeddedDocumentBytesHandler().close();
                } catch (IOException e2) {
                    LOG.warn("problem closing embedded document byte store", (Throwable) e2);
                }
            }
            throw th;
        }
    }

    private void emitParseData(FetchEmitTuple fetchEmitTuple, MetadataListAndEmbeddedBytes metadataListAndEmbeddedBytes) throws IOException {
        EmitKey emitKey;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String containerStacktrace = getContainerStacktrace(fetchEmitTuple, metadataListAndEmbeddedBytes.getMetadataList());
        filterMetadata(fetchEmitTuple, metadataListAndEmbeddedBytes.getMetadataList());
        filterMetadataList(fetchEmitTuple, metadataListAndEmbeddedBytes);
        ParseContext parseContext = fetchEmitTuple.getParseContext();
        FetchEmitTuple.ON_PARSE_EXCEPTION onParseException = fetchEmitTuple.getOnParseException();
        EmbeddedDocumentBytesConfig embeddedDocumentBytesConfig = (EmbeddedDocumentBytesConfig) parseContext.get(EmbeddedDocumentBytesConfig.class);
        if (StringUtils.isBlank(containerStacktrace) || onParseException == FetchEmitTuple.ON_PARSE_EXCEPTION.EMIT) {
            injectUserMetadata(fetchEmitTuple.getMetadata(), metadataListAndEmbeddedBytes.getMetadataList());
            EmitKey emitKey2 = fetchEmitTuple.getEmitKey();
            if (StringUtils.isBlank(emitKey2.getEmitKey())) {
                EmitKey emitKey3 = new EmitKey(emitKey2.getEmitterName(), fetchEmitTuple.getFetchKey().getFetchKey());
                fetchEmitTuple.setEmitKey(emitKey3);
                emitKey = emitKey3;
            } else {
                emitKey = emitKey2;
            }
            EmitData emitData = new EmitData(fetchEmitTuple.getEmitKey(), metadataListAndEmbeddedBytes.getMetadataList(), containerStacktrace);
            if (embeddedDocumentBytesConfig.isExtractEmbeddedDocumentBytes() && metadataListAndEmbeddedBytes.toBePackagedForStreamEmitter()) {
                emit(fetchEmitTuple.getId(), emitKey, embeddedDocumentBytesConfig.isExtractEmbeddedDocumentBytes(), metadataListAndEmbeddedBytes, containerStacktrace, parseContext);
            } else if (this.maxForEmitBatchBytes >= 0 && emitData.getEstimatedSizeBytes() >= this.maxForEmitBatchBytes) {
                emit(fetchEmitTuple.getId(), emitKey, embeddedDocumentBytesConfig.isExtractEmbeddedDocumentBytes(), metadataListAndEmbeddedBytes, containerStacktrace, parseContext);
            } else {
                write(emitData);
            }
            Logger logger = LOG;
            if (logger.isTraceEnabled()) {
                logger.trace("timer -- emitted: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                return;
            }
            return;
        }
        write(STATUS.PARSE_EXCEPTION_NO_EMIT, containerStacktrace);
    }

    private void filterMetadata(FetchEmitTuple fetchEmitTuple, List<Metadata> list) {
        MetadataFilter metadataFilter = (MetadataFilter) fetchEmitTuple.getParseContext().get(MetadataFilter.class);
        if (metadataFilter == null) {
            metadataFilter = this.tikaConfig.getMetadataFilter();
        }
        Iterator<Metadata> it = list.iterator();
        while (it.hasNext()) {
            try {
                metadataFilter.filter(it.next());
            } catch (TikaException e) {
                LOG.warn("failed to filter metadata", (Throwable) e);
            }
        }
    }

    private void filterMetadataList(FetchEmitTuple fetchEmitTuple, MetadataListAndEmbeddedBytes metadataListAndEmbeddedBytes) {
        MetadataListFilter metadataListFilter = (MetadataListFilter) fetchEmitTuple.getParseContext().get(MetadataListFilter.class);
        if (metadataListFilter == null) {
            metadataListFilter = this.tikaConfig.getMetadataListFilter();
        }
        if (metadataListFilter instanceof NoOpListFilter) {
            return;
        }
        try {
            metadataListAndEmbeddedBytes.filter(metadataListFilter);
        } catch (TikaException e) {
            LOG.warn("failed to filter metadata list", (Throwable) e);
        }
    }

    private Fetcher getFetcher(FetchEmitTuple fetchEmitTuple) throws IOException {
        try {
            return this.fetcherManager.getFetcher(fetchEmitTuple.getFetchKey().getFetcherName());
        } catch (IOException e) {
            e = e;
            LOG.warn("Couldn't initialize fetcher for fetch id '" + fetchEmitTuple.getId() + "'", e);
            write(STATUS.FETCHER_INITIALIZATION_EXCEPTION, ExceptionUtils.getStackTrace(e));
            return null;
        } catch (IllegalArgumentException unused) {
            String noFetcherMsg = getNoFetcherMsg(fetchEmitTuple.getFetchKey().getFetcherName());
            LOG.warn(noFetcherMsg);
            write(STATUS.FETCHER_NOT_FOUND, noFetcherMsg);
            return null;
        } catch (TikaException e2) {
            e = e2;
            LOG.warn("Couldn't initialize fetcher for fetch id '" + fetchEmitTuple.getId() + "'", e);
            write(STATUS.FETCHER_INITIALIZATION_EXCEPTION, ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    protected MetadataListAndEmbeddedBytes parseFromTuple(FetchEmitTuple fetchEmitTuple, Fetcher fetcher) throws IOException {
        Metadata metadata = new Metadata();
        try {
            InputStream inputStreamFetch = fetcher.fetch(fetchEmitTuple.getFetchKey().getFetchKey(), metadata, fetchEmitTuple.getParseContext());
            try {
                MetadataListAndEmbeddedBytes withStream = parseWithStream(fetchEmitTuple, inputStreamFetch, metadata);
                if (inputStreamFetch != null) {
                    inputStreamFetch.close();
                }
                return withStream;
            } catch (Throwable th) {
                if (inputStreamFetch != null) {
                    try {
                        inputStreamFetch.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            e = e;
            LOG.warn("fetch exception " + fetchEmitTuple.getId(), e);
            write(STATUS.FETCH_EXCEPTION, ExceptionUtils.getStackTrace(e));
            return null;
        } catch (SecurityException e2) {
            LOG.error("security exception " + fetchEmitTuple.getId(), (Throwable) e2);
            throw e2;
        } catch (TikaException e3) {
            e = e3;
            LOG.warn("fetch exception " + fetchEmitTuple.getId(), e);
            write(STATUS.FETCH_EXCEPTION, ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    private String getNoFetcherMsg(String str) {
        StringBuilder sb = new StringBuilder("Fetcher '");
        sb.append(str);
        sb.append("' not found.\nThe configured FetcherManager supports:");
        int i = 0;
        for (String str2 : this.fetcherManager.getSupported()) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(str2);
            i = i2;
        }
        return sb.toString();
    }

    private String getNoEmitterMsg(String str) {
        StringBuilder sb = new StringBuilder("Emitter '");
        sb.append(str);
        sb.append("' not found.\nThe configured emitterManager supports:");
        int i = 0;
        for (String str2 : this.emitterManager.getSupported()) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(str2);
            i = i2;
        }
        return sb.toString();
    }

    private void handleOOM(String str, OutOfMemoryError outOfMemoryError) throws IOException {
        write(STATUS.OOM);
        LOG.error("oom: " + str, (Throwable) outOfMemoryError);
        exit(1);
    }

    private MetadataListAndEmbeddedBytes parseWithStream(FetchEmitTuple fetchEmitTuple, InputStream inputStream, Metadata metadata) throws IOException, TikaConfigException {
        List<Metadata> concatenated;
        ParseContext parseContext = setupParseContext(fetchEmitTuple);
        HandlerConfig handlerConfig = (HandlerConfig) parseContext.get(HandlerConfig.class);
        if (handlerConfig.getParseMode() == HandlerConfig.PARSE_MODE.RMETA) {
            concatenated = parseRecursive(fetchEmitTuple, handlerConfig, inputStream, metadata, parseContext);
        } else {
            concatenated = parseConcatenated(fetchEmitTuple, handlerConfig, inputStream, metadata, parseContext);
        }
        return new MetadataListAndEmbeddedBytes(concatenated, (EmbeddedDocumentBytesHandler) parseContext.get(EmbeddedDocumentBytesHandler.class));
    }

    private ParseContext setupParseContext(FetchEmitTuple fetchEmitTuple) throws TikaConfigException {
        ParseContext parseContext = fetchEmitTuple.getParseContext();
        if (parseContext.get(HandlerConfig.class) == null) {
            parseContext.set(HandlerConfig.class, HandlerConfig.DEFAULT_HANDLER_CONFIG);
        }
        EmbeddedDocumentBytesConfig embeddedDocumentBytesConfig = (EmbeddedDocumentBytesConfig) parseContext.get(EmbeddedDocumentBytesConfig.class);
        if (embeddedDocumentBytesConfig == null) {
            parseContext.set(EmbeddedDocumentBytesConfig.class, EmbeddedDocumentBytesConfig.SKIP);
            return parseContext;
        }
        EmbeddedDocumentExtractorFactory embeddedDocumentExtractorFactory = ((AutoDetectParser) this.autoDetectParser).getAutoDetectParserConfig().getEmbeddedDocumentExtractorFactory();
        if (embeddedDocumentExtractorFactory == null) {
            parseContext.set(EmbeddedDocumentExtractor.class, new RUnpackExtractor(parseContext, RUnpackExtractorFactory.DEFAULT_MAX_EMBEDDED_BYTES_FOR_EXTRACTION));
        } else if (!(embeddedDocumentExtractorFactory instanceof EmbeddedDocumentByteStoreExtractorFactory)) {
            throw new TikaConfigException("EmbeddedDocumentExtractorFactory must be an instance of EmbeddedDocumentByteStoreExtractorFactory if you wantto extract embedded bytes! I see this embedded doc factory: " + embeddedDocumentExtractorFactory.getClass() + "and a request: " + embeddedDocumentBytesConfig);
        }
        if (!StringUtils.isBlank(embeddedDocumentBytesConfig.getEmitter())) {
            parseContext.set(EmbeddedDocumentBytesHandler.class, new EmittingEmbeddedDocumentBytesHandler(fetchEmitTuple, this.emitterManager));
            return parseContext;
        }
        parseContext.set(EmbeddedDocumentBytesHandler.class, new BasicEmbeddedDocumentBytesHandler(embeddedDocumentBytesConfig));
        return parseContext;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0102 A[PHI: r14
  0x0102: PHI (r14v4 org.slf4j.Logger) = (r14v1 org.slf4j.Logger), (r14v2 org.slf4j.Logger), (r14v5 org.slf4j.Logger) binds: [B:34:0x0100, B:26:0x00cf, B:15:0x0086] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<org.apache.tika.metadata.Metadata> parseConcatenated(org.apache.tika.pipes.FetchEmitTuple r10, org.apache.tika.pipes.HandlerConfig r11, java.io.InputStream r12, org.apache.tika.metadata.Metadata r13, org.apache.tika.parser.ParseContext r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.pipes.PipesServer.parseConcatenated(org.apache.tika.pipes.FetchEmitTuple, org.apache.tika.pipes.HandlerConfig, java.io.InputStream, org.apache.tika.metadata.Metadata, org.apache.tika.parser.ParseContext):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00b6 A[PHI: r13
  0x00b6: PHI (r13v4 org.slf4j.Logger) = (r13v1 org.slf4j.Logger), (r13v3 org.slf4j.Logger), (r13v5 org.slf4j.Logger) binds: [B:20:0x0097, B:25:0x00b4, B:12:0x0063] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<org.apache.tika.metadata.Metadata> parseRecursive(org.apache.tika.pipes.FetchEmitTuple r11, org.apache.tika.pipes.HandlerConfig r12, java.io.InputStream r13, org.apache.tika.metadata.Metadata r14, org.apache.tika.parser.ParseContext r15) throws java.io.IOException {
        /*
            r10 = this;
            java.lang.String r0 = "timer -- parse only time: {} ms"
            java.lang.String r1 = "parse exception: "
            java.lang.String r2 = "security exception:"
            java.lang.String r3 = "encrypted document:"
            java.lang.String r4 = "sax problem:"
            org.apache.tika.sax.RecursiveParserWrapperHandler r5 = new org.apache.tika.sax.RecursiveParserWrapperHandler
            org.apache.tika.sax.BasicContentHandlerFactory r6 = new org.apache.tika.sax.BasicContentHandlerFactory
            org.apache.tika.sax.BasicContentHandlerFactory$HANDLER_TYPE r7 = r12.getType()
            int r8 = r12.getWriteLimit()
            boolean r9 = r12.isThrowOnWriteLimitReached()
            r6.<init>(r7, r8, r9, r15)
            int r12 = r12.getMaxEmbeddedResources()
            r5.<init>(r6, r12)
            long r6 = java.lang.System.currentTimeMillis()
            r10.preParse(r11, r13, r14, r15)
            org.apache.tika.parser.Parser r12 = r10.rMetaParser     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49 java.lang.SecurityException -> L66 org.apache.tika.exception.EncryptedDocumentException -> L7d org.xml.sax.SAXException -> L9a
            r12.parse(r13, r5, r14, r15)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49 java.lang.SecurityException -> L66 org.apache.tika.exception.EncryptedDocumentException -> L7d org.xml.sax.SAXException -> L9a
            org.slf4j.Logger r11 = org.apache.tika.pipes.PipesServer.LOG
            boolean r12 = r11.isTraceEnabled()
            if (r12 == 0) goto Lc2
            long r12 = java.lang.System.currentTimeMillis()
            long r12 = r12 - r6
            java.lang.Long r12 = java.lang.Long.valueOf(r12)
            r11.trace(r0, r12)
            goto Lc2
        L46:
            r11 = move-exception
            goto Lc7
        L49:
            r12 = move-exception
            org.slf4j.Logger r13 = org.apache.tika.pipes.PipesServer.LOG     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r11.getId()     // Catch: java.lang.Throwable -> L46
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r14.<init>(r1)     // Catch: java.lang.Throwable -> L46
            r14.append(r11)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r14.toString()     // Catch: java.lang.Throwable -> L46
            r13.warn(r11, r12)     // Catch: java.lang.Throwable -> L46
            boolean r11 = r13.isTraceEnabled()
            if (r11 == 0) goto Lc2
            goto Lb6
        L66:
            r12 = move-exception
            org.slf4j.Logger r13 = org.apache.tika.pipes.PipesServer.LOG     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r11.getId()     // Catch: java.lang.Throwable -> L46
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r14.<init>(r2)     // Catch: java.lang.Throwable -> L46
            r14.append(r11)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r14.toString()     // Catch: java.lang.Throwable -> L46
            r13.warn(r11, r12)     // Catch: java.lang.Throwable -> L46
            throw r12     // Catch: java.lang.Throwable -> L46
        L7d:
            r12 = move-exception
            org.slf4j.Logger r13 = org.apache.tika.pipes.PipesServer.LOG     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r11.getId()     // Catch: java.lang.Throwable -> L46
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r14.<init>(r3)     // Catch: java.lang.Throwable -> L46
            r14.append(r11)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r14.toString()     // Catch: java.lang.Throwable -> L46
            r13.warn(r11, r12)     // Catch: java.lang.Throwable -> L46
            boolean r11 = r13.isTraceEnabled()
            if (r11 == 0) goto Lc2
            goto Lb6
        L9a:
            r12 = move-exception
            org.slf4j.Logger r13 = org.apache.tika.pipes.PipesServer.LOG     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r11.getId()     // Catch: java.lang.Throwable -> L46
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r14.<init>(r4)     // Catch: java.lang.Throwable -> L46
            r14.append(r11)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r14.toString()     // Catch: java.lang.Throwable -> L46
            r13.warn(r11, r12)     // Catch: java.lang.Throwable -> L46
            boolean r11 = r13.isTraceEnabled()
            if (r11 == 0) goto Lc2
        Lb6:
            long r11 = java.lang.System.currentTimeMillis()
            long r11 = r11 - r6
            java.lang.Long r11 = java.lang.Long.valueOf(r11)
            r13.trace(r0, r11)
        Lc2:
            java.util.List r11 = r5.getMetadataList()
            return r11
        Lc7:
            org.slf4j.Logger r12 = org.apache.tika.pipes.PipesServer.LOG
            boolean r13 = r12.isTraceEnabled()
            if (r13 == 0) goto Ldb
            long r13 = java.lang.System.currentTimeMillis()
            long r13 = r13 - r6
            java.lang.Long r13 = java.lang.Long.valueOf(r13)
            r12.trace(r0, r13)
        Ldb:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.pipes.PipesServer.parseRecursive(org.apache.tika.pipes.FetchEmitTuple, org.apache.tika.pipes.HandlerConfig, java.io.InputStream, org.apache.tika.metadata.Metadata, org.apache.tika.parser.ParseContext):java.util.List");
    }

    private void preParse(FetchEmitTuple fetchEmitTuple, InputStream inputStream, Metadata metadata, ParseContext parseContext) throws IOException {
        try {
            TikaInputStream tikaInputStreamCast = TikaInputStream.cast(inputStream);
            if (tikaInputStreamCast == null) {
                tikaInputStreamCast = TikaInputStream.get(inputStream, (TemporaryResources) null, metadata);
            }
            _preParse(fetchEmitTuple, tikaInputStreamCast, metadata, parseContext);
            IOUtils.closeQuietly((Closeable) null);
            writeIntermediate(fetchEmitTuple.getEmitKey(), metadata);
        } catch (Throwable th) {
            IOUtils.closeQuietly((Closeable) null);
            throw th;
        }
    }

    private void _preParse(FetchEmitTuple fetchEmitTuple, TikaInputStream tikaInputStream, Metadata metadata, ParseContext parseContext) throws IOException {
        DigestingParser.Digester digester = this.digester;
        if (digester != null) {
            try {
                digester.digest(tikaInputStream, metadata, parseContext);
            } catch (IOException e) {
                LOG.warn("problem digesting: " + fetchEmitTuple.getId(), (Throwable) e);
            }
        }
        try {
            MediaType mediaTypeDetect = this.detector.detect(tikaInputStream, metadata);
            metadata.set("Content-Type", mediaTypeDetect.toString());
            metadata.set(TikaCoreProperties.CONTENT_TYPE_PARSER_OVERRIDE, mediaTypeDetect.toString());
        } catch (IOException e2) {
            LOG.warn("problem detecting: " + fetchEmitTuple.getId(), (Throwable) e2);
        }
        EmbeddedDocumentBytesConfig embeddedDocumentBytesConfig = (EmbeddedDocumentBytesConfig) parseContext.get(EmbeddedDocumentBytesConfig.class);
        if (embeddedDocumentBytesConfig == null || !embeddedDocumentBytesConfig.isIncludeOriginal()) {
            return;
        }
        EmbeddedDocumentBytesHandler embeddedDocumentBytesHandler = (EmbeddedDocumentBytesHandler) parseContext.get(EmbeddedDocumentBytesHandler.class);
        try {
            InputStream inputStreamNewInputStream = Files.newInputStream(tikaInputStream.getPath(), new OpenOption[0]);
            try {
                embeddedDocumentBytesHandler.add(0, metadata, inputStreamNewInputStream);
                if (inputStreamNewInputStream != null) {
                    inputStreamNewInputStream.close();
                }
            } finally {
            }
        } catch (IOException e3) {
            LOG.warn("problem reading source file into embedded document byte store", (Throwable) e3);
        }
    }

    private void injectUserMetadata(Metadata metadata, List<Metadata> list) {
        for (String str : metadata.names()) {
            list.get(0).set(str, (String) null);
            for (String str2 : metadata.getValues(str)) {
                list.get(0).add(str, str2);
            }
        }
    }

    private void exit(int i) {
        if (i != 0) {
            LOG.error("exiting: {}", Integer.valueOf(i));
        } else {
            LOG.info("exiting: {}", Integer.valueOf(i));
        }
        System.exit(i);
    }

    private FetchEmitTuple readFetchEmitTuple() throws IOException {
        try {
            byte[] bArr = new byte[this.input.readInt()];
            this.input.readFully(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(UnsynchronizedByteArrayInputStream.builder().setByteArray(bArr).get());
            try {
                FetchEmitTuple fetchEmitTuple = (FetchEmitTuple) objectInputStream.readObject();
                objectInputStream.close();
                return fetchEmitTuple;
            } finally {
            }
        } catch (IOException e) {
            LOG.error("problem reading tuple", (Throwable) e);
            exit(1);
            return null;
        } catch (ClassNotFoundException e2) {
            LOG.error("can't find class?!", (Throwable) e2);
            exit(1);
            return null;
        }
    }

    protected void initializeResources() throws TikaException, SAXException, IOException {
        this.tikaConfig = new TikaConfig(this.tikaConfigPath);
        this.fetcherManager = FetcherManager.load(this.tikaConfigPath);
        if (this.maxForEmitBatchBytes > -1) {
            this.emitterManager = EmitterManager.load(this.tikaConfigPath);
        } else {
            LOG.debug("'maxForEmitBatchBytes' < 0. Not initializing emitters in PipesServer");
            this.emitterManager = null;
        }
        AutoDetectParser autoDetectParser = new AutoDetectParser(this.tikaConfig);
        this.autoDetectParser = autoDetectParser;
        if (autoDetectParser.getAutoDetectParserConfig().getDigesterFactory() != null) {
            this.digester = ((AutoDetectParser) this.autoDetectParser).getAutoDetectParserConfig().getDigesterFactory().build();
            ((AutoDetectParser) this.autoDetectParser).getAutoDetectParserConfig().getDigesterFactory().setSkipContainerDocument(true);
            if (((AutoDetectParser) this.autoDetectParser).getAutoDetectParserConfig().getEmbeddedDocumentExtractorFactory() == null) {
                ((AutoDetectParser) this.autoDetectParser).getAutoDetectParserConfig().setEmbeddedDocumentExtractorFactory(new RUnpackExtractorFactory());
            }
        }
        this.detector = ((AutoDetectParser) this.autoDetectParser).getDetector();
        this.rMetaParser = new RecursiveParserWrapper(this.autoDetectParser);
    }

    private void writeIntermediate(EmitKey emitKey, Metadata metadata) throws IOException {
        try {
            UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream = UnsynchronizedByteArrayOutputStream.builder().get();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsynchronizedByteArrayOutputStream);
            try {
                objectOutputStream.writeObject(metadata);
                objectOutputStream.close();
                write(STATUS.INTERMEDIATE_RESULT, unsynchronizedByteArrayOutputStream.toByteArray());
            } finally {
            }
        } catch (IOException e) {
            LOG.error("problem writing intermediate data (forking process shutdown?)", (Throwable) e);
            exit(1);
        }
    }

    private void write(EmitData emitData) throws IOException {
        try {
            UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream = UnsynchronizedByteArrayOutputStream.builder().get();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsynchronizedByteArrayOutputStream);
            try {
                objectOutputStream.writeObject(emitData);
                objectOutputStream.close();
                write(STATUS.PARSE_SUCCESS, unsynchronizedByteArrayOutputStream.toByteArray());
            } finally {
            }
        } catch (IOException e) {
            LOG.error("problem writing emit data (forking process shutdown?)", (Throwable) e);
            exit(1);
        }
    }

    private void write(STATUS status, String str) throws IOException {
        write(status, str.getBytes(StandardCharsets.UTF_8));
    }

    private void write(STATUS status, byte[] bArr) throws IOException {
        try {
            int length = bArr.length;
            this.output.write(status.getByte());
            this.output.writeInt(length);
            this.output.write(bArr);
            this.output.flush();
        } catch (IOException e) {
            LOG.error("problem writing data (forking process shutdown?)", (Throwable) e);
            exit(1);
        }
    }

    private void write(STATUS status) throws IOException {
        try {
            this.output.write(status.getByte());
            this.output.flush();
        } catch (IOException e) {
            LOG.error("problem writing data (forking process shutdown?)", (Throwable) e);
            exit(1);
        }
    }

    static class MetadataListAndEmbeddedBytes {
        final Optional<EmbeddedDocumentBytesHandler> embeddedDocumentBytesHandler;
        List<Metadata> metadataList;

        public MetadataListAndEmbeddedBytes(List<Metadata> list, EmbeddedDocumentBytesHandler embeddedDocumentBytesHandler) {
            this.metadataList = list;
            this.embeddedDocumentBytesHandler = Optional.ofNullable(embeddedDocumentBytesHandler);
        }

        public List<Metadata> getMetadataList() {
            return this.metadataList;
        }

        public void filter(MetadataListFilter metadataListFilter) throws TikaException {
            this.metadataList = metadataListFilter.filter(this.metadataList);
        }

        public EmbeddedDocumentBytesHandler getEmbeddedDocumentBytesHandler() {
            return this.embeddedDocumentBytesHandler.get();
        }

        public boolean hasEmbeddedDocumentByteStore() {
            return this.embeddedDocumentBytesHandler.isPresent();
        }

        public boolean toBePackagedForStreamEmitter() {
            return !(this.embeddedDocumentBytesHandler.get() instanceof EmittingEmbeddedDocumentBytesHandler);
        }
    }
}
