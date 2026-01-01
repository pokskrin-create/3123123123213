package org.apache.tika.parser.external2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.config.TikaTaskTimeout;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.ExternalProcess;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.EmptyParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.pipes.PipesConfigBase;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.utils.FileProcessResult;
import org.apache.tika.utils.ProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class ExternalParser implements Parser, Initializable {
    public static final long DEFAULT_TIMEOUT_MS = 60000;
    public static final String INPUT_FILE_TOKEN = "${INPUT_FILE}";
    public static final String OUTPUT_FILE_TOKEN = "${OUTPUT_FILE}";
    private static Pattern INPUT_TOKEN_MATCHER = Pattern.compile("\\$\\{INPUT_FILE}");
    private static Pattern OUTPUT_TOKEN_MATCHER = Pattern.compile("\\$\\{OUTPUT_FILE}");
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) ExternalParser.class);
    private Set<MediaType> supportedTypes = new HashSet();
    private List<String> commandLine = new ArrayList();
    private Parser outputParser = EmptyParser.INSTANCE;
    private boolean returnStdout = false;
    private boolean returnStderr = true;
    private long timeoutMs = 60000;
    private int maxStdErr = PipesConfigBase.DEFAULT_MAX_FILES_PROCESSED_PER_PROCESS;
    private int maxStdOut = PipesConfigBase.DEFAULT_MAX_FILES_PROCESSED_PER_PROCESS;

    @Override // org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
    }

    @Override // org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return this.supportedTypes;
    }

    @Override // org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws Throwable {
        TemporaryResources temporaryResources;
        Path path;
        FileProcessResult fileProcessResultExecute;
        FileProcessResult fileProcessResult;
        Path pathCreateTempFile = null;
        try {
            temporaryResources = new TemporaryResources();
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                Path path2 = TikaInputStream.get(inputStream, temporaryResources, metadata).getPath();
                ArrayList arrayList = new ArrayList();
                Matcher matcher = INPUT_TOKEN_MATCHER.matcher("");
                Matcher matcher2 = OUTPUT_TOKEN_MATCHER.matcher("");
                boolean z = false;
                for (String str : this.commandLine) {
                    if (matcher.reset(str).find()) {
                        arrayList.add(str.replace(INPUT_FILE_TOKEN, ProcessUtils.escapeCommandLine(path2.toAbsolutePath().toString())));
                    } else if (matcher2.reset(str).find()) {
                        pathCreateTempFile = Files.createTempFile("tika-external2-", "", new FileAttribute[0]);
                        arrayList.add(str.replace(OUTPUT_FILE_TOKEN, ProcessUtils.escapeCommandLine(pathCreateTempFile.toAbsolutePath().toString())));
                        z = true;
                    } else {
                        arrayList.add(str);
                    }
                }
                long timeoutMillis = TikaTaskTimeout.getTimeoutMillis(parseContext, this.timeoutMs);
                if (!z) {
                    pathCreateTempFile = Files.createTempFile("tika-external2-", "", new FileAttribute[0]);
                    fileProcessResultExecute = ProcessUtils.execute(new ProcessBuilder(arrayList), timeoutMillis, pathCreateTempFile, this.maxStdErr);
                } else {
                    fileProcessResultExecute = ProcessUtils.execute(new ProcessBuilder(arrayList), timeoutMillis, this.maxStdOut, this.maxStdErr);
                }
                fileProcessResult = fileProcessResultExecute;
                path = pathCreateTempFile;
            } catch (Throwable th2) {
                th = th2;
                path = null;
            }
            try {
                metadata.set(ExternalProcess.IS_TIMEOUT, fileProcessResult.isTimeout());
                metadata.set(ExternalProcess.EXIT_VALUE, fileProcessResult.getExitValue());
                metadata.set(ExternalProcess.STD_OUT_LENGTH, fileProcessResult.getStdoutLength());
                metadata.set(ExternalProcess.STD_OUT_IS_TRUNCATED, fileProcessResult.isStdoutTruncated());
                metadata.set(ExternalProcess.STD_ERR_LENGTH, fileProcessResult.getStderrLength());
                metadata.set(ExternalProcess.STD_ERR_IS_TRUNCATED, fileProcessResult.isStderrTruncated());
                if (this.returnStdout) {
                    metadata.set(ExternalProcess.STD_OUT, fileProcessResult.getStdout());
                }
                if (this.returnStderr) {
                    metadata.set(ExternalProcess.STD_ERR, fileProcessResult.getStderr());
                }
                XHTMLContentHandler xHTMLContentHandler = new XHTMLContentHandler(contentHandler, metadata);
                xHTMLContentHandler.startDocument();
                handleOutput(fileProcessResult, path, xHTMLContentHandler, metadata, parseContext);
                xHTMLContentHandler.endDocument();
                temporaryResources.close();
                if (path != null) {
                    Files.delete(path);
                }
            } catch (Throwable th3) {
                th = th3;
                Throwable th4 = th;
                try {
                    temporaryResources.close();
                    throw th4;
                } catch (Throwable th5) {
                    th4.addSuppressed(th5);
                    throw th4;
                }
            }
        } catch (Throwable th6) {
            th = th6;
            pathCreateTempFile = path;
            if (pathCreateTempFile != null) {
                Files.delete(pathCreateTempFile);
            }
            throw th;
        }
    }

    private void handleOutput(FileProcessResult fileProcessResult, Path path, XHTMLContentHandler xHTMLContentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        if (this.outputParser == EmptyParser.INSTANCE) {
            if (path != null) {
                BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
                try {
                    for (String line = bufferedReaderNewBufferedReader.readLine(); line != null; line = bufferedReaderNewBufferedReader.readLine()) {
                        xHTMLContentHandler.characters(line);
                        xHTMLContentHandler.newline();
                    }
                    if (bufferedReaderNewBufferedReader != null) {
                        bufferedReaderNewBufferedReader.close();
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    if (bufferedReaderNewBufferedReader != null) {
                        try {
                            bufferedReaderNewBufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            xHTMLContentHandler.characters(fileProcessResult.getStdout());
            return;
        }
        if (path != null) {
            TikaInputStream tikaInputStream = TikaInputStream.get(path);
            try {
                this.outputParser.parse(tikaInputStream, new BodyContentHandler(xHTMLContentHandler), metadata, parseContext);
                if (tikaInputStream != null) {
                    tikaInputStream.close();
                    return;
                }
                return;
            } catch (Throwable th3) {
                if (tikaInputStream != null) {
                    try {
                        tikaInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }
        TikaInputStream tikaInputStream2 = TikaInputStream.get(fileProcessResult.getStdout().getBytes(StandardCharsets.UTF_8));
        try {
            this.outputParser.parse(tikaInputStream2, new BodyContentHandler(xHTMLContentHandler), metadata, parseContext);
            if (tikaInputStream2 != null) {
                tikaInputStream2.close();
            }
        } catch (Throwable th5) {
            if (tikaInputStream2 != null) {
                try {
                    tikaInputStream2.close();
                } catch (Throwable th6) {
                    th5.addSuppressed(th6);
                }
            }
            throw th5;
        }
    }

    @Field
    public void setSupportedTypes(List<String> list) {
        if (this.supportedTypes.size() > 0) {
            throw new IllegalStateException("can't set supportedTypes after initialization");
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.supportedTypes.add(MediaType.parse(it.next()));
        }
    }

    @Field
    public void setTimeoutMs(long j) {
        this.timeoutMs = j;
    }

    @Field
    public void setMaxStdErr(int i) {
        this.maxStdErr = i;
    }

    @Field
    public void setMaxStdOut(int i) {
        this.maxStdOut = i;
    }

    @Field
    public void setCommandLine(List<String> list) {
        this.commandLine = list;
    }

    @Field
    public void setReturnStdout(boolean z) {
        this.returnStdout = z;
    }

    @Field
    public void setReturnStderr(boolean z) {
        this.returnStderr = z;
    }

    @Field
    public void setOutputParser(Parser parser) {
        this.outputParser = parser;
    }

    public Parser getOutputParser() {
        return this.outputParser;
    }

    @Override // org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
        if (this.supportedTypes.size() == 0) {
            throw new TikaConfigException("supportedTypes size must be > 0");
        }
        if (this.commandLine.isEmpty()) {
            throw new TikaConfigException("commandLine is empty?!");
        }
        if (this.outputParser == EmptyParser.INSTANCE) {
            LOG.debug("no parser selected for the output; contents will be written to the content handler");
        }
    }
}
