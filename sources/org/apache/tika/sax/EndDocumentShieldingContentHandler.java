package org.apache.tika.sax;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class EndDocumentShieldingContentHandler extends ContentHandlerDecorator {
    private boolean endDocumentCalled;

    public EndDocumentShieldingContentHandler(ContentHandler contentHandler) {
        super(contentHandler);
        this.endDocumentCalled = false;
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        this.endDocumentCalled = true;
    }

    public void reallyEndDocument() throws SAXException {
        super.endDocument();
    }

    public boolean isEndDocumentWasCalled() {
        return this.endDocumentCalled;
    }
}
