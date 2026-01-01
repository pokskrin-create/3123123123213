package org.apache.tika.parser.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class ExternalParser implements Parser {
    public static final String INPUT_FILE_TOKEN = "${INPUT}";
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) ExternalParser.class);
    public static final String OUTPUT_FILE_TOKEN = "${OUTPUT}";
    private static final long serialVersionUID = -1079128990650687037L;
    private final long timeoutMs = 60000;
    private Set<MediaType> supportedTypes = Collections.EMPTY_SET;
    private Map<Pattern, String> metadataPatterns = null;
    private String[] command = {"cat"};
    private LineConsumer ignoredLineConsumer = LineConsumer.NULL;

    public interface LineConsumer extends Serializable {
        public static final LineConsumer NULL = new ExternalParser$LineConsumer$$ExternalSyntheticLambda0();

        static /* synthetic */ void lambda$static$e2402a94$1(String str) {
        }

        void consume(String str);
    }

    private static void ignoreStream(InputStream inputStream) throws InterruptedException {
        ignoreStream(inputStream, true);
    }

    private static Thread ignoreStream(final InputStream inputStream, boolean z) throws InterruptedException {
        Thread thread = new Thread(new Runnable() { // from class: org.apache.tika.parser.external.ExternalParser$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ExternalParser.lambda$ignoreStream$0(inputStream);
            }
        });
        thread.start();
        if (z) {
            try {
                thread.join();
            } catch (InterruptedException unused) {
            }
        }
        return thread;
    }

    static /* synthetic */ void lambda$ignoreStream$0(InputStream inputStream) {
        try {
            IOUtils.copy(inputStream, NullOutputStream.INSTANCE);
        } catch (IOException unused) {
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static boolean check(String str, int... iArr) {
        return check(new String[]{str}, iArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean check(java.lang.String[] r8, int... r9) {
        /*
            int r0 = r9.length
            r1 = 1
            r2 = 0
            if (r0 != 0) goto Lb
            int[] r9 = new int[r1]
            r0 = 127(0x7f, float:1.78E-43)
            r9[r2] = r0
        Lb:
            r0 = 0
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.lang.Process r0 = r3.exec(r8)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.io.InputStream r3 = r0.getErrorStream()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.lang.Thread r3 = ignoreStream(r3, r2)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.io.InputStream r4 = r0.getInputStream()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.lang.Thread r4 = ignoreStream(r4, r2)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r3.join()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r4.join()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r4 = 60000(0xea60, double:2.9644E-319)
            boolean r3 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0, r4, r3)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            if (r3 == 0) goto L5d
            int r3 = r0.exitValue()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            org.slf4j.Logger r4 = org.apache.tika.parser.external.ExternalParser.LOG     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.lang.String r5 = "exit value for {}: {}"
            r6 = r8[r2]     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r4.debug(r5, r6, r7)     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            int r4 = r9.length     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r5 = r2
        L48:
            if (r5 >= r4) goto L57
            r6 = r9[r5]     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            if (r3 != r6) goto L54
            if (r0 == 0) goto L53
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        L53:
            return r2
        L54:
            int r5 = r5 + 1
            goto L48
        L57:
            if (r0 == 0) goto L5c
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        L5c:
            return r1
        L5d:
            java.util.concurrent.TimeoutException r9 = new java.util.concurrent.TimeoutException     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            r9.<init>()     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
            throw r9     // Catch: java.lang.Throwable -> L63 java.lang.Error -> L65 java.lang.SecurityException -> La3 java.util.concurrent.TimeoutException -> La5 java.lang.InterruptedException -> La7 java.io.IOException -> La9
        L63:
            r8 = move-exception
            goto Lc8
        L65:
            r9 = move-exception
            java.lang.String r1 = r9.getMessage()     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto La2
            java.lang.String r1 = r9.getMessage()     // Catch: java.lang.Throwable -> L63
            java.lang.String r3 = "posix_spawn"
            boolean r1 = r1.contains(r3)     // Catch: java.lang.Throwable -> L63
            if (r1 != 0) goto L84
            java.lang.String r1 = r9.getMessage()     // Catch: java.lang.Throwable -> L63
            java.lang.String r3 = "UNIXProcess"
            boolean r1 = r1.contains(r3)     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto La2
        L84:
            org.slf4j.Logger r1 = org.apache.tika.parser.external.ExternalParser.LOG     // Catch: java.lang.Throwable -> L63
            r8 = r8[r2]     // Catch: java.lang.Throwable -> L63
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
            r3.<init>()     // Catch: java.lang.Throwable -> L63
            java.lang.String r4 = "(TIKA-1526): exception trying to run: "
            r3.append(r4)     // Catch: java.lang.Throwable -> L63
            r3.append(r8)     // Catch: java.lang.Throwable -> L63
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> L63
            r1.debug(r8, r9)     // Catch: java.lang.Throwable -> L63
            if (r0 == 0) goto La1
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        La1:
            return r2
        La2:
            throw r9     // Catch: java.lang.Throwable -> L63
        La3:
            r8 = move-exception
            throw r8     // Catch: java.lang.Throwable -> L63
        La5:
            r9 = move-exception
            goto Laa
        La7:
            r9 = move-exception
            goto Laa
        La9:
            r9 = move-exception
        Laa:
            org.slf4j.Logger r1 = org.apache.tika.parser.external.ExternalParser.LOG     // Catch: java.lang.Throwable -> L63
            r8 = r8[r2]     // Catch: java.lang.Throwable -> L63
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
            r3.<init>()     // Catch: java.lang.Throwable -> L63
            java.lang.String r4 = "exception trying to run  "
            r3.append(r4)     // Catch: java.lang.Throwable -> L63
            r3.append(r8)     // Catch: java.lang.Throwable -> L63
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> L63
            r1.debug(r8, r9)     // Catch: java.lang.Throwable -> L63
            if (r0 == 0) goto Lc7
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        Lc7:
            return r2
        Lc8:
            if (r0 == 0) goto Lcd
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        Lcd:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.parser.external.ExternalParser.check(java.lang.String[], int[]):boolean");
    }

    @Override // org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return getSupportedTypes();
    }

    public Set<MediaType> getSupportedTypes() {
        return this.supportedTypes;
    }

    public void setSupportedTypes(Set<MediaType> set) {
        this.supportedTypes = Collections.unmodifiableSet(new HashSet(set));
    }

    public String[] getCommand() {
        return this.command;
    }

    public void setCommand(String... strArr) {
        this.command = strArr;
    }

    public LineConsumer getIgnoredLineConsumer() {
        return this.ignoredLineConsumer;
    }

    public void setIgnoredLineConsumer(LineConsumer lineConsumer) {
        this.ignoredLineConsumer = lineConsumer;
    }

    public Map<Pattern, String> getMetadataExtractionPatterns() {
        return this.metadataPatterns;
    }

    public void setMetadataExtractionPatterns(Map<Pattern, String> map) {
        this.metadataPatterns = map;
    }

    @Override // org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        XHTMLContentHandler xHTMLContentHandler = new XHTMLContentHandler(contentHandler, metadata);
        TemporaryResources temporaryResources = new TemporaryResources();
        try {
            parse(TikaInputStream.get(inputStream, temporaryResources, metadata), xHTMLContentHandler, metadata, temporaryResources);
        } finally {
            temporaryResources.dispose();
        }
    }

    private void parse(TikaInputStream tikaInputStream, XHTMLContentHandler xHTMLContentHandler, Metadata metadata, TemporaryResources temporaryResources) throws InterruptedException, TikaException, SAXException, IOException {
        String[] strArrSplit;
        Process processExec;
        Map<Pattern, String> map = this.metadataPatterns;
        boolean z = (map == null || map.isEmpty()) ? false : true;
        String[] strArr = this.command;
        if (strArr.length == 1) {
            strArrSplit = strArr[0].split(StringUtils.SPACE);
        } else {
            String[] strArr2 = new String[strArr.length];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            strArrSplit = strArr2;
        }
        Process process = null;
        boolean z2 = true;
        boolean z3 = true;
        File fileCreateTemporaryFile = null;
        for (int i = 0; i < strArrSplit.length; i++) {
            if (strArrSplit[i].contains(INPUT_FILE_TOKEN)) {
                strArrSplit[i] = strArrSplit[i].replace(INPUT_FILE_TOKEN, tikaInputStream.getFile().getPath());
                z2 = false;
            }
            if (strArrSplit[i].contains(OUTPUT_FILE_TOKEN)) {
                fileCreateTemporaryFile = temporaryResources.createTemporaryFile();
                strArrSplit[i] = strArrSplit[i].replace(OUTPUT_FILE_TOKEN, fileCreateTemporaryFile.getPath());
                z3 = false;
            }
        }
        try {
            if (strArrSplit.length == 1) {
                processExec = Runtime.getRuntime().exec(strArrSplit[0]);
            } else {
                processExec = Runtime.getRuntime().exec(strArrSplit);
            }
            process = processExec;
        } catch (Exception e) {
            LOG.warn("problem with process exec", (Throwable) e);
        }
        try {
            if (z2) {
                sendInput(process, tikaInputStream);
            } else {
                process.getOutputStream().close();
            }
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            if (z) {
                extractMetadata(errorStream, metadata);
                if (z3) {
                    extractOutput(inputStream, xHTMLContentHandler);
                } else {
                    extractMetadata(inputStream, metadata);
                }
            } else {
                ignoreStream(errorStream);
                if (z3) {
                    extractOutput(inputStream, xHTMLContentHandler);
                } else {
                    ignoreStream(inputStream);
                }
            }
            if (z3) {
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(fileCreateTemporaryFile);
            try {
                extractOutput(fileInputStream, xHTMLContentHandler);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
            try {
                process.waitFor();
            } catch (InterruptedException unused) {
            }
        }
    }

    private void extractOutput(InputStream inputStream, XHTMLContentHandler xHTMLContentHandler) throws SAXException, IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try {
            xHTMLContentHandler.startDocument();
            xHTMLContentHandler.startElement("p");
            char[] cArr = new char[1024];
            while (true) {
                int i = inputStreamReader.read(cArr);
                if (i != -1) {
                    xHTMLContentHandler.characters(cArr, 0, i);
                } else {
                    xHTMLContentHandler.endElement("p");
                    xHTMLContentHandler.endDocument();
                    inputStreamReader.close();
                    return;
                }
            }
        } catch (Throwable th) {
            try {
                inputStreamReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void sendInput(final Process process, final InputStream inputStream) throws InterruptedException {
        Thread thread = new Thread(new Runnable() { // from class: org.apache.tika.parser.external.ExternalParser$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                IOUtils.copy(inputStream, process.getOutputStream());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException unused) {
        }
    }

    private void extractMetadata(final InputStream inputStream, final Metadata metadata) throws InterruptedException {
        Thread thread = new Thread(new Runnable() { // from class: org.apache.tika.parser.external.ExternalParser$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2288x84b78ecd(inputStream, metadata);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException unused) {
        }
    }

    /* renamed from: lambda$extractMetadata$2$org-apache-tika-parser-external-ExternalParser, reason: not valid java name */
    /* synthetic */ void m2288x84b78ecd(InputStream inputStream, Metadata metadata) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                boolean z = false;
                for (Map.Entry<Pattern, String> entry : this.metadataPatterns.entrySet()) {
                    Matcher matcher = entry.getKey().matcher(line);
                    if (matcher.find()) {
                        if (entry.getValue() != null && !entry.getValue().equals("")) {
                            metadata.add(entry.getValue(), matcher.group(1));
                        } else {
                            metadata.add(matcher.group(1), matcher.group(2));
                        }
                        z = true;
                    }
                }
                if (!z) {
                    this.ignoredLineConsumer.consume(line);
                }
            } catch (IOException unused) {
            } catch (Throwable th) {
                IOUtils.closeQuietly((Reader) bufferedReader);
                IOUtils.closeQuietly(inputStream);
                throw th;
            }
        }
        IOUtils.closeQuietly((Reader) bufferedReader);
        IOUtils.closeQuietly(inputStream);
    }
}
