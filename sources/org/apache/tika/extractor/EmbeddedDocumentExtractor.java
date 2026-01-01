package org.apache.tika.extractor;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public interface EmbeddedDocumentExtractor {
    void parseEmbedded(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, boolean z) throws SAXException, IOException;

    boolean shouldParseEmbedded(Metadata metadata);
}
