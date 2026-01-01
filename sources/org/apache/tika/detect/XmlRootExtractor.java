package org.apache.tika.detect;

import java.io.CharConversionException;
import java.io.InputStream;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.utils.XMLReaderUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class XmlRootExtractor {
    private static final ParseContext EMPTY_CONTEXT = new ParseContext();

    public QName extractRootElement(byte[] bArr) {
        while (true) {
            try {
                return extractRootElement(new UnsynchronizedByteArrayInputStream(bArr), true);
            } catch (MalformedCharException unused) {
                int length = bArr.length / 2;
                if (length % 2 == 1) {
                    length--;
                }
                if (length <= 0) {
                    return null;
                }
                bArr = Arrays.copyOf(bArr, length);
            }
        }
    }

    public QName extractRootElement(InputStream inputStream) {
        return extractRootElement(inputStream, false);
    }

    private QName extractRootElement(InputStream inputStream, boolean z) {
        ExtractorHandler extractorHandler = new ExtractorHandler();
        try {
            XMLReaderUtils.parseSAX(CloseShieldInputStream.wrap(inputStream), extractorHandler, EMPTY_CONTEXT);
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            if (z && ((e2 instanceof CharConversionException) || (e2.getCause() instanceof CharConversionException))) {
                throw new MalformedCharException(e2);
            }
        }
        return extractorHandler.rootElement;
    }

    private static class ExtractorHandler extends DefaultHandler {
        private QName rootElement;

        private ExtractorHandler() {
            this.rootElement = null;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            this.rootElement = new QName(str, str2);
            throw new SAXException("Aborting: root element received");
        }
    }

    private static class MalformedCharException extends RuntimeException {
        public MalformedCharException(Exception exc) {
            super(exc);
        }
    }
}
