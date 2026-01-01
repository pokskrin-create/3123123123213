package org.apache.tika.extractor;

import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
public class ParentContentHandler {
    private final ContentHandler contentHandler;

    public ParentContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }
}
