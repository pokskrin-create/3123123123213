package org.apache.tika.sax;

import okio.Utf8;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/* loaded from: classes4.dex */
public class SafeContentHandler extends ContentHandlerDecorator {
    private static final char[] REPLACEMENT = {Utf8.REPLACEMENT_CHARACTER};
    private final Output charactersOutput;
    private final Output ignorableWhitespaceOutput;

    protected interface Output {
        void write(char[] cArr, int i, int i2) throws SAXException;
    }

    protected boolean isInvalid(int i) {
        return i < 32 ? (i == 9 || i == 10 || i == 13) ? false : true : i < 57344 ? i > 55295 : i < 65536 ? i > 65533 : i > 1114111;
    }

    /* renamed from: lambda$new$0$org-apache-tika-sax-SafeContentHandler, reason: not valid java name */
    /* synthetic */ void m2306lambda$new$0$orgapachetikasaxSafeContentHandler(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
    }

    /* renamed from: lambda$new$1$org-apache-tika-sax-SafeContentHandler, reason: not valid java name */
    /* synthetic */ void m2307lambda$new$1$orgapachetikasaxSafeContentHandler(char[] cArr, int i, int i2) throws SAXException {
        super.ignorableWhitespace(cArr, i, i2);
    }

    public SafeContentHandler(ContentHandler contentHandler) {
        super(contentHandler);
        this.charactersOutput = new Output() { // from class: org.apache.tika.sax.SafeContentHandler$$ExternalSyntheticLambda0
            @Override // org.apache.tika.sax.SafeContentHandler.Output
            public final void write(char[] cArr, int i, int i2) throws SAXException {
                this.f$0.m2306lambda$new$0$orgapachetikasaxSafeContentHandler(cArr, i, i2);
            }
        };
        this.ignorableWhitespaceOutput = new Output() { // from class: org.apache.tika.sax.SafeContentHandler$$ExternalSyntheticLambda1
            @Override // org.apache.tika.sax.SafeContentHandler.Output
            public final void write(char[] cArr, int i, int i2) throws SAXException {
                this.f$0.m2307lambda$new$1$orgapachetikasaxSafeContentHandler(cArr, i, i2);
            }
        };
    }

    private void filter(char[] cArr, int i, int i2, Output output) throws SAXException {
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            int iCodePointAt = Character.codePointAt(cArr, i, i3);
            int iCharCount = Character.charCount(iCodePointAt) + i;
            if (isInvalid(iCodePointAt)) {
                if (i > i4) {
                    output.write(cArr, i4, i - i4);
                }
                writeReplacement(output);
                i4 = iCharCount;
            }
            i = iCharCount;
        }
        output.write(cArr, i4, i3 - i4);
    }

    private boolean isInvalid(String str) {
        char[] charArray = str.toCharArray();
        int iCharCount = 0;
        while (iCharCount < charArray.length) {
            int iCodePointAt = Character.codePointAt(charArray, iCharCount);
            if (isInvalid(iCodePointAt)) {
                return true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return false;
    }

    protected void writeReplacement(Output output) throws SAXException {
        char[] cArr = REPLACEMENT;
        output.write(cArr, 0, cArr.length);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        int i = 0;
        while (true) {
            if (i >= attributes.getLength()) {
                break;
            }
            if (isInvalid(attributes.getValue(i))) {
                AttributesImpl attributesImpl = new AttributesImpl();
                for (int i2 = 0; i2 < attributes.getLength(); i2++) {
                    String value = attributes.getValue(i2);
                    if (i2 >= i && isInvalid(value)) {
                        StringOutput stringOutput = new StringOutput();
                        filter(value.toCharArray(), 0, value.length(), stringOutput);
                        value = stringOutput.toString();
                    }
                    attributesImpl.addAttribute(attributes.getURI(i2), attributes.getLocalName(i2), attributes.getQName(i2), attributes.getType(i2), value);
                }
                attributes = attributesImpl;
            } else {
                i++;
            }
        }
        super.startElement(str, str2, str3, attributes);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        super.endElement(str, str2, str3);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        filter(cArr, i, i2, this.charactersOutput);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        filter(cArr, i, i2, this.ignorableWhitespaceOutput);
    }

    private static class StringOutput implements Output {
        private final StringBuilder builder;

        private StringOutput() {
            this.builder = new StringBuilder();
        }

        @Override // org.apache.tika.sax.SafeContentHandler.Output
        public void write(char[] cArr, int i, int i2) {
            this.builder.append(cArr, i, i2);
        }

        public String toString() {
            return this.builder.toString();
        }
    }
}
