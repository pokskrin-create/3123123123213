package org.apache.tika.sax;

import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class TaggedSAXException extends SAXException {
    private final Object tag;

    public TaggedSAXException(SAXException sAXException, Object obj) {
        super(sAXException.getMessage(), sAXException);
        this.tag = obj;
    }

    public Object getTag() {
        return this.tag;
    }

    @Override // java.lang.Throwable
    public SAXException getCause() {
        return (SAXException) super.getCause();
    }
}
