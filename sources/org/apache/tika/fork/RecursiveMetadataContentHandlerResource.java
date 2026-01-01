package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.AbstractRecursiveParserWrapperHandler;
import org.apache.tika.sax.RecursiveParserWrapperHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
class RecursiveMetadataContentHandlerResource implements ForkResource {
    private static final ContentHandler DEFAULT_HANDLER = new DefaultHandler();
    private final AbstractRecursiveParserWrapperHandler handler;

    public RecursiveMetadataContentHandlerResource(RecursiveParserWrapperHandler recursiveParserWrapperHandler) {
        this.handler = recursiveParserWrapperHandler;
    }

    @Override // org.apache.tika.fork.ForkResource
    public Throwable process(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        try {
            internalProcess(dataInputStream);
            return null;
        } catch (SAXException e) {
            return e;
        }
    }

    private void internalProcess(DataInputStream dataInputStream) throws SAXException, IOException {
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        ContentHandler contentHandler = DEFAULT_HANDLER;
        if (b2 == 3) {
            contentHandler = (ContentHandler) readObject(dataInputStream);
        } else if (b2 != 4) {
            throw new IllegalArgumentException("Expected HANDLER_AND_METADATA or METADATA_ONLY, but got:" + ((int) b2));
        }
        Metadata metadata = (Metadata) readObject(dataInputStream);
        if (b == 1) {
            this.handler.endEmbeddedDocument(contentHandler, metadata);
        } else if (b == 2) {
            this.handler.endDocument(contentHandler, metadata);
        } else {
            throw new IllegalArgumentException("Expected either 0x01 or 0x02, but got: " + ((int) b));
        }
        byte b3 = dataInputStream.readByte();
        if (b3 == 5) {
            return;
        }
        throw new IOException("Expected the 'complete' signal, but got: " + ((int) b3));
    }

    private Object readObject(DataInputStream dataInputStream) throws IOException {
        try {
            return ForkObjectInputStream.readObject(dataInputStream, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }
}
