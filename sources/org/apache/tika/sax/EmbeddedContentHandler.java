package org.apache.tika.sax;

import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
public class EmbeddedContentHandler extends ContentHandlerDecorator {
    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() {
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() {
    }

    public EmbeddedContentHandler(ContentHandler contentHandler) {
        super(contentHandler);
    }
}
