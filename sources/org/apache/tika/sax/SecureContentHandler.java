package org.apache.tika.sax;

import java.io.IOException;
import java.util.LinkedList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class SecureContentHandler extends ContentHandlerDecorator {
    private long characterCount;
    private int currentDepth;
    private int maxDepth;
    private int maxPackageEntryDepth;
    private final LinkedList<Integer> packageEntryDepths;
    private long ratio;
    private final TikaInputStream stream;
    private long threshold;

    public SecureContentHandler(ContentHandler contentHandler, TikaInputStream tikaInputStream) {
        super(contentHandler);
        this.packageEntryDepths = new LinkedList<>();
        this.characterCount = 0L;
        this.currentDepth = 0;
        this.threshold = 1000000L;
        this.ratio = 100L;
        this.maxDepth = 100;
        this.maxPackageEntryDepth = 10;
        this.stream = tikaInputStream;
    }

    public long getOutputThreshold() {
        return this.threshold;
    }

    public void setOutputThreshold(long j) {
        this.threshold = j;
    }

    public long getMaximumCompressionRatio() {
        return this.ratio;
    }

    public void setMaximumCompressionRatio(long j) {
        this.ratio = j;
    }

    public int getMaximumDepth() {
        return this.maxDepth;
    }

    public void setMaximumDepth(int i) {
        this.maxDepth = i;
    }

    public int getMaximumPackageEntryDepth() {
        return this.maxPackageEntryDepth;
    }

    public void setMaximumPackageEntryDepth(int i) {
        this.maxPackageEntryDepth = i;
    }

    public void throwIfCauseOf(SAXException sAXException) throws TikaException {
        if ((sAXException instanceof SecureSAXException) && ((SecureSAXException) sAXException).isCausedBy(this)) {
            throw new TikaException("Zip bomb detected!", sAXException);
        }
    }

    private long getByteCount() throws SAXException {
        try {
            if (this.stream.hasLength()) {
                return this.stream.getLength();
            }
            return this.stream.getPosition();
        } catch (IOException e) {
            throw new SAXException("Unable to get stream length", e);
        }
    }

    protected void advance(int i) throws SAXException {
        this.characterCount += i;
        long byteCount = getByteCount();
        long j = this.characterCount;
        if (j <= this.threshold || j <= this.ratio * byteCount) {
            return;
        }
        throw new SecureSAXException("Suspected zip bomb: " + byteCount + " input bytes produced " + this.characterCount + " output characters");
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        int i = this.currentDepth + 1;
        this.currentDepth = i;
        if (i >= this.maxDepth) {
            throw new SecureSAXException("Suspected zip bomb: " + this.currentDepth + " levels of XML element nesting");
        }
        if ("div".equals(str3) && "package-entry".equals(attributes.getValue("class"))) {
            this.packageEntryDepths.addLast(Integer.valueOf(this.currentDepth));
            if (this.packageEntryDepths.size() >= this.maxPackageEntryDepth) {
                throw new SecureSAXException("Suspected zip bomb: " + this.packageEntryDepths.size() + " levels of package entry nesting");
            }
        }
        super.startElement(str, str2, str3, attributes);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        super.endElement(str, str2, str3);
        if (!this.packageEntryDepths.isEmpty() && this.packageEntryDepths.getLast().intValue() == this.currentDepth) {
            this.packageEntryDepths.removeLast();
        }
        this.currentDepth--;
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        advance(i2);
        super.characters(cArr, i, i2);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        advance(i2);
        super.ignorableWhitespace(cArr, i, i2);
    }

    private class SecureSAXException extends SAXException {
        private static final long serialVersionUID = 2285245380321771445L;

        public SecureSAXException(String str) throws SAXException {
            super(str);
        }

        public boolean isCausedBy(SecureContentHandler secureContentHandler) {
            return SecureContentHandler.this == secureContentHandler;
        }
    }
}
