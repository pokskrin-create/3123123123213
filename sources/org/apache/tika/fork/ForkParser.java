package org.apache.tika.fork;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.AbstractRecursiveParserWrapperHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class ForkParser implements Parser, Closeable {
    private static final long serialVersionUID = -4962742892274663950L;
    private int currentlyInUse;
    private List<String> java;
    private final ClassLoader loader;

    @Field
    private int maxFilesProcessedPerClient;
    private final Parser parser;
    private final ParserFactoryFactory parserFactoryFactory;
    private final Queue<ForkClient> pool;

    @Field
    private int poolSize;

    @Field
    private long serverParseTimeoutMillis;

    @Field
    private long serverPulseMillis;

    @Field
    private long serverWaitTimeoutMillis;
    private final Path tikaBin;

    public ForkParser(Path path, ParserFactoryFactory parserFactoryFactory) {
        this.pool = new LinkedList();
        this.java = Arrays.asList("java", "-Xmx32m", "-Djava.awt.headless=true");
        this.poolSize = 5;
        this.currentlyInUse = 0;
        this.serverPulseMillis = 1000L;
        this.serverParseTimeoutMillis = 60000L;
        this.serverWaitTimeoutMillis = 60000L;
        this.maxFilesProcessedPerClient = -1;
        this.loader = null;
        this.parser = null;
        this.tikaBin = path;
        this.parserFactoryFactory = parserFactoryFactory;
    }

    public ForkParser(Path path, ParserFactoryFactory parserFactoryFactory, ClassLoader classLoader) {
        this.pool = new LinkedList();
        this.java = Arrays.asList("java", "-Xmx32m", "-Djava.awt.headless=true");
        this.poolSize = 5;
        this.currentlyInUse = 0;
        this.serverPulseMillis = 1000L;
        this.serverParseTimeoutMillis = 60000L;
        this.serverWaitTimeoutMillis = 60000L;
        this.maxFilesProcessedPerClient = -1;
        this.parser = null;
        this.loader = classLoader;
        this.tikaBin = path;
        this.parserFactoryFactory = parserFactoryFactory;
    }

    public ForkParser(ClassLoader classLoader, Parser parser) {
        this.pool = new LinkedList();
        this.java = Arrays.asList("java", "-Xmx32m", "-Djava.awt.headless=true");
        this.poolSize = 5;
        this.currentlyInUse = 0;
        this.serverPulseMillis = 1000L;
        this.serverParseTimeoutMillis = 60000L;
        this.serverWaitTimeoutMillis = 60000L;
        this.maxFilesProcessedPerClient = -1;
        if (parser instanceof ForkParser) {
            throw new IllegalArgumentException("The underlying parser of a ForkParser should not be a ForkParser, but a specific implementation.");
        }
        this.tikaBin = null;
        this.parserFactoryFactory = null;
        this.loader = classLoader;
        this.parser = parser;
    }

    public ForkParser(ClassLoader classLoader) {
        this(classLoader, new AutoDetectParser());
    }

    public ForkParser() {
        this(ForkParser.class.getClassLoader());
    }

    public synchronized int getPoolSize() {
        return this.poolSize;
    }

    public synchronized void setPoolSize(int i) {
        this.poolSize = i;
    }

    public void setJavaCommand(List<String> list) {
        this.java = new ArrayList(list);
    }

    public List<String> getJavaCommandAsList() {
        return Collections.unmodifiableList(this.java);
    }

    @Override // org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return this.parser.getSupportedTypes(parseContext);
    }

    @Override // org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws Throwable {
        if (inputStream == null) {
            throw new NullPointerException("null stream");
        }
        ForkClient forkClientAcquireClient = acquireClient();
        boolean z = false;
        try {
            try {
                if (!(contentHandler instanceof AbstractRecursiveParserWrapperHandler)) {
                    contentHandler = new TeeContentHandler(contentHandler, new MetadataContentHandler(metadata));
                }
                Throwable thCall = forkClientAcquireClient.call("parse", inputStream, contentHandler, metadata, parseContext);
                releaseClient(forkClientAcquireClient, true);
                if (thCall instanceof IOException) {
                    throw ((IOException) thCall);
                }
                if (thCall instanceof SAXException) {
                    throw ((SAXException) thCall);
                }
                if (thCall instanceof TikaException) {
                    throw ((TikaException) thCall);
                }
                if (thCall != null) {
                    throw new TikaException("Unexpected error in forked server process", thCall);
                }
            } catch (IOException e) {
                throw new TikaException("Failed to communicate with a forked parser process. The process has most likely crashed due to some error like running out of memory. A new process will be started for the next parsing request.", e);
            } catch (TikaException e2) {
                try {
                    throw e2;
                } catch (Throwable th) {
                    th = th;
                    z = true;
                    releaseClient(forkClientAcquireClient, z);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            releaseClient(forkClientAcquireClient, z);
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        Iterator<ForkClient> it = this.pool.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        this.pool.clear();
        this.poolSize = 0;
    }

    private synchronized ForkClient acquireClient() throws TikaException, IOException {
        ForkClient forkClientPoll;
        while (true) {
            forkClientPoll = this.pool.poll();
            if (forkClientPoll == null && this.currentlyInUse < this.poolSize) {
                forkClientPoll = newClient();
            }
            if (forkClientPoll != null && !forkClientPoll.ping()) {
                forkClientPoll.close();
                forkClientPoll = null;
            }
            if (forkClientPoll != null) {
                this.currentlyInUse++;
            } else if (this.currentlyInUse >= this.poolSize) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new TikaException("Interrupted while waiting for a fork parser", e);
                }
            }
        }
        return forkClientPoll;
    }

    private ForkClient newClient() throws TikaException, IOException {
        TimeoutLimits timeoutLimits = new TimeoutLimits(this.serverPulseMillis, this.serverParseTimeoutMillis, this.serverWaitTimeoutMillis);
        ClassLoader classLoader = this.loader;
        if (classLoader == null && this.parser == null && this.tikaBin != null && this.parserFactoryFactory != null) {
            return new ForkClient(this.tikaBin, this.parserFactoryFactory, this.java, timeoutLimits);
        }
        if (classLoader != null && this.parser != null && this.tikaBin == null && this.parserFactoryFactory == null) {
            return new ForkClient(this.loader, this.parser, this.java, timeoutLimits);
        }
        if (classLoader != null && this.parser == null && this.tikaBin != null && this.parserFactoryFactory != null) {
            return new ForkClient(this.tikaBin, this.parserFactoryFactory, this.loader, this.java, timeoutLimits);
        }
        throw new IllegalStateException("Unexpected combination of state items");
    }

    private synchronized void releaseClient(ForkClient forkClient, boolean z) {
        int i = this.currentlyInUse - 1;
        this.currentlyInUse = i;
        if (i + this.pool.size() < this.poolSize && z) {
            if (this.maxFilesProcessedPerClient > 0 && forkClient.getFilesProcessed() >= this.maxFilesProcessedPerClient) {
                forkClient.close();
            } else {
                this.pool.offer(forkClient);
            }
            notifyAll();
        } else {
            forkClient.close();
        }
    }

    public void setServerPulseMillis(long j) {
        this.serverPulseMillis = j;
    }

    public void setServerParseTimeoutMillis(long j) {
        this.serverParseTimeoutMillis = j;
    }

    public void setServerWaitTimeoutMillis(long j) {
        this.serverWaitTimeoutMillis = j;
    }

    public void setMaxFilesProcessedPerServer(int i) {
        this.maxFilesProcessedPerClient = i;
    }
}
