package org.apache.tika.sax;

import java.io.StringWriter;
import java.io.Writer;
import org.apache.tika.exception.WriteLimitReachedException;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ParseRecord;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class WriteOutContentHandler extends ContentHandlerDecorator {
    private ParseContext parseContext;
    private boolean throwOnWriteLimitReached;
    private int writeCount;
    private final int writeLimit;
    private boolean writeLimitReached;

    public WriteOutContentHandler(ContentHandler contentHandler, int i) {
        super(contentHandler);
        this.writeCount = 0;
        this.throwOnWriteLimitReached = true;
        this.parseContext = null;
        this.writeLimit = i;
    }

    public WriteOutContentHandler(Writer writer, int i) {
        this(new ToTextContentHandler(writer), i);
    }

    public WriteOutContentHandler(Writer writer) {
        this(writer, -1);
    }

    public WriteOutContentHandler(int i) {
        this(new StringWriter(), i);
    }

    public WriteOutContentHandler() {
        this(100000);
    }

    public WriteOutContentHandler(ContentHandler contentHandler, int i, boolean z, ParseContext parseContext) {
        super(contentHandler);
        this.writeCount = 0;
        this.writeLimit = i;
        this.throwOnWriteLimitReached = z;
        this.parseContext = parseContext;
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        if (this.writeLimitReached) {
            return;
        }
        int i3 = this.writeLimit;
        if (i3 != -1) {
            int i4 = this.writeCount;
            if (i4 + i2 > i3) {
                super.characters(cArr, i, i3 - i4);
                handleWriteLimitReached();
                return;
            }
        }
        super.characters(cArr, i, i2);
        this.writeCount += i2;
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        if (this.writeLimitReached) {
            return;
        }
        int i3 = this.writeLimit;
        if (i3 != -1) {
            int i4 = this.writeCount;
            if (i4 + i2 > i3) {
                super.ignorableWhitespace(cArr, i, i3 - i4);
                handleWriteLimitReached();
                return;
            }
        }
        super.ignorableWhitespace(cArr, i, i2);
        this.writeCount += i2;
    }

    private void handleWriteLimitReached() throws WriteLimitReachedException {
        this.writeLimitReached = true;
        this.writeCount = this.writeLimit;
        if (this.throwOnWriteLimitReached) {
            throw new WriteLimitReachedException(this.writeLimit);
        }
        ParseRecord parseRecord = (ParseRecord) this.parseContext.get(ParseRecord.class);
        if (parseRecord != null) {
            parseRecord.setWriteLimitReached(true);
        }
    }
}
