package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
class ContentHandlerProxy implements ContentHandler, ForkProxy {
    public static final int CHARACTERS = 7;
    public static final int END_DOCUMENT = 2;
    public static final int END_ELEMENT = 6;
    public static final int END_PREFIX_MAPPING = 4;
    public static final int IGNORABLE_WHITESPACE = 8;
    public static final int PROCESSING_INSTRUCTION = 9;
    public static final int SKIPPED_ENTITY = 10;
    public static final int START_DOCUMENT = 1;
    public static final int START_ELEMENT = 5;
    public static final int START_PREFIX_MAPPING = 3;
    private static final long serialVersionUID = 737511106054617524L;
    private transient DataOutputStream output;
    private final int resource;

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
    }

    public ContentHandlerProxy(int i) {
        this.resource = i;
    }

    @Override // org.apache.tika.fork.ForkProxy
    public void init(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.output = dataOutputStream;
    }

    private void sendRequest(int i) throws SAXException, IOException {
        try {
            this.output.writeByte(3);
            this.output.writeByte(this.resource);
            this.output.writeByte(i);
        } catch (IOException e) {
            throw new SAXException("Unexpected fork proxy problem", e);
        }
    }

    private void sendString(String str) throws SAXException, IOException {
        try {
            if (str != null) {
                this.output.writeBoolean(true);
                writeString(str);
            } else {
                this.output.writeBoolean(false);
            }
        } catch (IOException e) {
            throw new SAXException("Unexpected fork proxy problem", e);
        }
    }

    private void writeString(String str) throws IOException {
        int iCeil = (int) Math.ceil(str.length() / 21845);
        this.output.writeInt(iCeil);
        int i = 0;
        while (i < iCeil) {
            this.output.writeUTF(str.substring(i * 21845, i < iCeil + (-1) ? (i + 1) * 21845 : str.length()));
            i++;
        }
    }

    private void sendCharacters(char[] cArr, int i, int i2) throws SAXException {
        try {
            writeString(new String(cArr, i, i2));
        } catch (IOException e) {
            throw new SAXException("Unexpected fork proxy problem", e);
        }
    }

    private void doneSending() throws SAXException, IOException {
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new SAXException("Unexpected fork proxy problem", e);
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws SAXException, IOException {
        sendRequest(1);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws SAXException, IOException {
        sendRequest(2);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException, IOException {
        sendRequest(3);
        sendString(str);
        sendString(str2);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException, IOException {
        sendRequest(4);
        sendString(str);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException, IOException {
        sendRequest(5);
        sendString(str);
        sendString(str2);
        sendString(str3);
        int length = attributes != null ? attributes.getLength() : -1;
        try {
            this.output.writeInt(length);
            for (int i = 0; i < length; i++) {
                sendString(attributes.getURI(i));
                sendString(attributes.getLocalName(i));
                sendString(attributes.getQName(i));
                sendString(attributes.getType(i));
                sendString(attributes.getValue(i));
            }
            doneSending();
        } catch (IOException e) {
            throw new SAXException("Unexpected fork proxy problem", e);
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException, IOException {
        sendRequest(6);
        sendString(str);
        sendString(str2);
        sendString(str3);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException, IOException {
        sendRequest(7);
        sendCharacters(cArr, i, i2);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException, IOException {
        sendRequest(8);
        sendCharacters(cArr, i, i2);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException, IOException {
        sendRequest(9);
        sendString(str);
        sendString(str2);
        doneSending();
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException, IOException {
        sendRequest(10);
        sendString(str);
        doneSending();
    }
}
