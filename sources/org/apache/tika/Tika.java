package org.apache.tika;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.WriteLimitReachedException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.language.translate.Translator;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParsingReader;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class Tika {
    private final Detector detector;
    private int maxStringLength;
    private final Parser parser;
    private final Translator translator;

    public Tika(Detector detector, Parser parser) {
        this.maxStringLength = 100000;
        this.detector = detector;
        this.parser = parser;
        this.translator = TikaConfig.getDefaultConfig().getTranslator();
    }

    public Tika(Detector detector, Parser parser, Translator translator) {
        this.maxStringLength = 100000;
        this.detector = detector;
        this.parser = parser;
        this.translator = translator;
    }

    public Tika(TikaConfig tikaConfig) {
        this(tikaConfig.getDetector(), new AutoDetectParser(tikaConfig), tikaConfig.getTranslator());
    }

    public Tika() {
        this(TikaConfig.getDefaultConfig());
    }

    public Tika(Detector detector) {
        this(detector, new AutoDetectParser(detector));
    }

    public String detect(InputStream inputStream, Metadata metadata) throws IOException {
        if (inputStream == null || inputStream.markSupported()) {
            return this.detector.detect(inputStream, metadata).toString();
        }
        return this.detector.detect(new BufferedInputStream(inputStream), metadata).toString();
    }

    public String detect(InputStream inputStream, String str) throws IOException {
        Metadata metadata = new Metadata();
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, str);
        return detect(inputStream, metadata);
    }

    public String detect(InputStream inputStream) throws IOException {
        return detect(inputStream, new Metadata());
    }

    public String detect(byte[] bArr, String str) throws IOException {
        try {
            TikaInputStream tikaInputStream = TikaInputStream.get(bArr);
            try {
                String strDetect = detect(tikaInputStream, str);
                if (tikaInputStream != null) {
                    tikaInputStream.close();
                }
                return strDetect;
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected IOException", e);
        }
    }

    public String detect(byte[] bArr) throws IOException {
        try {
            TikaInputStream tikaInputStream = TikaInputStream.get(bArr);
            try {
                String strDetect = detect(tikaInputStream);
                if (tikaInputStream != null) {
                    tikaInputStream.close();
                }
                return strDetect;
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected IOException", e);
        }
    }

    public String detect(Path path) throws IOException {
        Metadata metadata = new Metadata();
        TikaInputStream tikaInputStream = TikaInputStream.get(path, metadata);
        try {
            String strDetect = detect(tikaInputStream, metadata);
            if (tikaInputStream != null) {
                tikaInputStream.close();
            }
            return strDetect;
        } catch (Throwable th) {
            if (tikaInputStream != null) {
                try {
                    tikaInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public String detect(File file) throws IOException {
        Metadata metadata = new Metadata();
        TikaInputStream tikaInputStream = TikaInputStream.get(file, metadata);
        try {
            String strDetect = detect(tikaInputStream, metadata);
            if (tikaInputStream != null) {
                tikaInputStream.close();
            }
            return strDetect;
        } catch (Throwable th) {
            if (tikaInputStream != null) {
                try {
                    tikaInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public String detect(URL url) throws IOException {
        Metadata metadata = new Metadata();
        TikaInputStream tikaInputStream = TikaInputStream.get(url, metadata);
        try {
            String strDetect = detect(tikaInputStream, metadata);
            if (tikaInputStream != null) {
                tikaInputStream.close();
            }
            return strDetect;
        } catch (Throwable th) {
            if (tikaInputStream != null) {
                try {
                    tikaInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public String detect(String str) {
        try {
            return detect((InputStream) null, str);
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected IOException", e);
        }
    }

    public String translate(String str, String str2, String str3) {
        try {
            return this.translator.translate(str, str2, str3);
        } catch (Exception e) {
            throw new IllegalStateException("Error translating data.", e);
        }
    }

    public String translate(String str, String str2) {
        try {
            return this.translator.translate(str, str2);
        } catch (Exception e) {
            throw new IllegalStateException("Error translating data.", e);
        }
    }

    public Reader parse(InputStream inputStream, Metadata metadata) throws IOException {
        ParseContext parseContext = new ParseContext();
        parseContext.set(Parser.class, this.parser);
        return new ParsingReader(this.parser, inputStream, metadata, parseContext);
    }

    public Reader parse(InputStream inputStream) throws IOException {
        return parse(inputStream, new Metadata());
    }

    public Reader parse(Path path, Metadata metadata) throws IOException {
        return parse(TikaInputStream.get(path, metadata), metadata);
    }

    public Reader parse(Path path) throws IOException {
        return parse(path, new Metadata());
    }

    public Reader parse(File file, Metadata metadata) throws IOException {
        return parse(TikaInputStream.get(file, metadata), metadata);
    }

    public Reader parse(File file) throws IOException {
        return parse(file, new Metadata());
    }

    public Reader parse(URL url) throws IOException {
        Metadata metadata = new Metadata();
        return parse(TikaInputStream.get(url, metadata), metadata);
    }

    public String parseToString(InputStream inputStream, Metadata metadata) throws TikaException, IOException {
        return parseToString(inputStream, metadata, this.maxStringLength);
    }

    public String parseToString(InputStream inputStream, Metadata metadata, int i) throws TikaException, IOException {
        WriteOutContentHandler writeOutContentHandler = new WriteOutContentHandler(i);
        ParseContext parseContext = new ParseContext();
        parseContext.set(Parser.class, this.parser);
        try {
            try {
                this.parser.parse(inputStream, new BodyContentHandler(writeOutContentHandler), metadata, parseContext);
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
            }
        } catch (SAXException e) {
            if (!WriteLimitReachedException.isWriteLimitReached(e)) {
                throw new TikaException("Unexpected SAX processing failure", e);
            }
        }
        return writeOutContentHandler.toString();
    }

    public String parseToString(InputStream inputStream) throws TikaException, IOException {
        return parseToString(inputStream, new Metadata());
    }

    public String parseToString(Path path) throws TikaException, IOException {
        Metadata metadata = new Metadata();
        return parseToString(TikaInputStream.get(path, metadata), metadata);
    }

    public String parseToString(File file) throws TikaException, IOException {
        Metadata metadata = new Metadata();
        return parseToString(TikaInputStream.get(file, metadata), metadata);
    }

    public String parseToString(URL url) throws TikaException, IOException {
        Metadata metadata = new Metadata();
        return parseToString(TikaInputStream.get(url, metadata), metadata);
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public void setMaxStringLength(int i) {
        this.maxStringLength = i;
    }

    public Parser getParser() {
        return this.parser;
    }

    public Detector getDetector() {
        return this.detector;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public String toString() {
        return getString();
    }

    public static String getString() throws IOException {
        String property = null;
        try {
            InputStream resourceAsStream = Tika.class.getResourceAsStream("/META-INF/maven/org.apache.tika/tika-core/pom.properties");
            if (resourceAsStream != null) {
                try {
                    Properties properties = new Properties();
                    properties.load(resourceAsStream);
                    property = properties.getProperty("version");
                } finally {
                }
            }
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        } catch (Exception unused) {
        }
        if (property != null) {
            return "Apache Tika " + property;
        }
        return "Apache Tika";
    }
}
