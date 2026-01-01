package org.apache.tika.sax;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import kotlin.text.Typography;
import org.apache.tika.metadata.Office;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class ToHTMLContentHandler extends ToXMLContentHandler {
    private static final Set<String> EMPTY_ELEMENTS = new HashSet(Arrays.asList("area", "base", "basefont", "br", "col", "frame", "hr", "img", "input", "isindex", "link", Office.PREFIX_DOC_META, "param"));

    @Override // org.apache.tika.sax.ToXMLContentHandler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
    }

    public ToHTMLContentHandler(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        super(outputStream, str);
    }

    public ToHTMLContentHandler() {
    }

    @Override // org.apache.tika.sax.ToXMLContentHandler, org.apache.tika.sax.ToTextContentHandler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException, IOException {
        if (this.inStartElement) {
            write(Typography.greater);
            this.inStartElement = false;
            if (EMPTY_ELEMENTS.contains(str2)) {
                this.namespaces.clear();
                return;
            }
        }
        super.endElement(str, str2, str3);
    }
}
