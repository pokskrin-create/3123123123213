package org.apache.tika.parser;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Office;
import org.apache.tika.mime.MediaType;
import org.apache.tika.sax.TaggedContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.utils.XMLReaderUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class NetworkParser implements Parser {
    private final Set<MediaType> supportedTypes;
    private final URI uri;

    public NetworkParser(URI uri, Set<MediaType> set) {
        this.uri = uri;
        this.supportedTypes = set;
    }

    public NetworkParser(URI uri) {
        this(uri, Collections.singleton(MediaType.OCTET_STREAM));
    }

    @Override // org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return this.supportedTypes;
    }

    @Override // org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        TemporaryResources temporaryResources = new TemporaryResources();
        try {
            parse(TikaInputStream.get(inputStream, temporaryResources, metadata), contentHandler, metadata, parseContext);
        } finally {
            temporaryResources.dispose();
        }
    }

    private void parse(TikaInputStream tikaInputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        if ("telnet".equals(this.uri.getScheme())) {
            final Socket socket = new Socket(this.uri.getHost(), this.uri.getPort());
            try {
                new ParsingTask(tikaInputStream, new FilterOutputStream(socket.getOutputStream()) { // from class: org.apache.tika.parser.NetworkParser.1
                    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        socket.shutdownOutput();
                    }
                }).parse(socket.getInputStream(), contentHandler, metadata, parseContext);
                socket.close();
                return;
            } catch (Throwable th) {
                try {
                    socket.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        URLConnection uRLConnectionOpenConnection = this.uri.toURL().openConnection();
        uRLConnectionOpenConnection.setDoOutput(true);
        uRLConnectionOpenConnection.connect();
        InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
        try {
            new ParsingTask(tikaInputStream, uRLConnectionOpenConnection.getOutputStream()).parse(CloseShieldInputStream.wrap(inputStream), contentHandler, metadata, parseContext);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th3) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    private static class ParsingTask implements Runnable {
        private volatile Exception exception = null;
        private final TikaInputStream input;
        private final OutputStream output;

        public ParsingTask(TikaInputStream tikaInputStream, OutputStream outputStream) {
            this.input = tikaInputStream;
            this.output = outputStream;
        }

        public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws InterruptedException, TikaException, SAXException, IOException {
            Thread thread = new Thread(this, "Tika network parser");
            thread.start();
            TaggedContentHandler taggedContentHandler = new TaggedContentHandler(contentHandler);
            try {
                try {
                    XMLReaderUtils.parseSAX(inputStream, new TeeContentHandler(taggedContentHandler, new MetaHandler(metadata)), parseContext);
                    try {
                        thread.join(1000L);
                        if (this.exception == null) {
                            return;
                        }
                        this.input.throwIfCauseOf(this.exception);
                        throw new TikaException("Unexpected network parser error", this.exception);
                    } catch (InterruptedException e) {
                        throw new TikaException("Network parser interrupted", e);
                    }
                } catch (IOException e2) {
                    throw new TikaException("Unable to read network parser output", e2);
                } catch (SAXException e3) {
                    taggedContentHandler.throwIfCauseOf(e3);
                    throw new TikaException("Invalid network parser output", e3);
                }
            } catch (Throwable th) {
                try {
                    thread.join(1000L);
                    if (this.exception != null) {
                        this.input.throwIfCauseOf(this.exception);
                        throw new TikaException("Unexpected network parser error", this.exception);
                    }
                    throw th;
                } catch (InterruptedException e4) {
                    throw new TikaException("Network parser interrupted", e4);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            try {
                try {
                    IOUtils.copy(this.input, this.output);
                } finally {
                    this.output.close();
                }
            } catch (Exception e) {
                this.exception = e;
            }
        }
    }

    private static class MetaHandler extends DefaultHandler {
        private final Metadata metadata;

        public MetaHandler(Metadata metadata) {
            this.metadata = metadata;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (XHTMLContentHandler.XHTML.equals(str) && Office.PREFIX_DOC_META.equals(str2)) {
                String value = attributes.getValue("", AppMeasurementSdk.ConditionalUserProperty.NAME);
                String value2 = attributes.getValue("", FirebaseAnalytics.Param.CONTENT);
                if (value == null || value2 == null) {
                    return;
                }
                this.metadata.add(value, value2);
            }
        }
    }
}
