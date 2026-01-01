package org.apache.tika.detect;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.tika.config.LoadErrorHandler;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.apache.tika.utils.CharsetUtils;
import org.xml.sax.InputSource;

/* loaded from: classes4.dex */
public class AutoDetectReader extends BufferedReader {
    private static final EncodingDetector DEFAULT_DETECTOR;
    private static final ServiceLoader DEFAULT_LOADER;
    private final Charset charset;

    static {
        ServiceLoader serviceLoader = new ServiceLoader(AutoDetectReader.class.getClassLoader());
        DEFAULT_LOADER = serviceLoader;
        DEFAULT_DETECTOR = new CompositeEncodingDetector(serviceLoader.loadServiceProviders(EncodingDetector.class));
    }

    private AutoDetectReader(InputStream inputStream, Charset charset) throws IOException {
        super(new InputStreamReader(inputStream, charset));
        this.charset = charset;
        mark(1);
        if (read() != 65279) {
            reset();
        }
    }

    private AutoDetectReader(InputStream inputStream, Metadata metadata, EncodingDetector encodingDetector, LoadErrorHandler loadErrorHandler) throws TikaException, IOException {
        this(inputStream, detect(inputStream, metadata, encodingDetector, loadErrorHandler));
    }

    public AutoDetectReader(InputStream inputStream, Metadata metadata, EncodingDetector encodingDetector) throws TikaException, IOException {
        this(getBuffered(inputStream), metadata, encodingDetector, DEFAULT_LOADER.getLoadErrorHandler());
    }

    public AutoDetectReader(InputStream inputStream, Metadata metadata, ServiceLoader serviceLoader) throws TikaException, IOException {
        this(getBuffered(inputStream), metadata, new CompositeEncodingDetector(serviceLoader.loadServiceProviders(EncodingDetector.class)), serviceLoader.getLoadErrorHandler());
    }

    public AutoDetectReader(InputStream inputStream, Metadata metadata) throws TikaException, IOException {
        this(inputStream, metadata, DEFAULT_DETECTOR);
    }

    public AutoDetectReader(InputStream inputStream) throws TikaException, IOException {
        this(inputStream, new Metadata());
    }

    private static Charset detect(InputStream inputStream, Metadata metadata, EncodingDetector encodingDetector, LoadErrorHandler loadErrorHandler) throws TikaException, IOException {
        String str;
        try {
            Charset charsetDetect = encodingDetector.detect(inputStream, metadata);
            if (charsetDetect != null) {
                return charsetDetect;
            }
        } catch (NoClassDefFoundError e) {
            loadErrorHandler.handleLoadError(encodingDetector.getClass().getName(), e);
        }
        MediaType mediaType = MediaType.parse(metadata.get("Content-Type"));
        if (mediaType != null && (str = mediaType.getParameters().get("charset")) != null) {
            try {
                Charset charsetForName = CharsetUtils.forName(str);
                metadata.set(TikaCoreProperties.DETECTED_ENCODING, charsetForName.name());
                metadata.set(TikaCoreProperties.ENCODING_DETECTOR, "AutoDetectReader-charset-metadata-fallback");
                return charsetForName;
            } catch (IllegalArgumentException unused) {
            }
        }
        throw new TikaException("Failed to detect the character encoding of a document");
    }

    private static InputStream getBuffered(InputStream inputStream) {
        return inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public InputSource asInputSource() {
        InputSource inputSource = new InputSource(this);
        inputSource.setEncoding(this.charset.name());
        return inputSource;
    }
}
