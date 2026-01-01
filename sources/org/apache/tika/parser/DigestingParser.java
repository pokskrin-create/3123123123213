package org.apache.tika.parser;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class DigestingParser extends ParserDecorator {
    private final Digester digester;
    private final boolean skipContainerDocument;

    public interface Digester {
        void digest(InputStream inputStream, Metadata metadata, ParseContext parseContext) throws IOException;
    }

    public interface DigesterFactory {
        Digester build();

        boolean isSkipContainerDocument();

        void setSkipContainerDocument(boolean z);
    }

    public interface Encoder {
        String encode(byte[] bArr);
    }

    public DigestingParser(Parser parser, Digester digester, boolean z) {
        super(parser);
        this.digester = digester;
        this.skipContainerDocument = z;
    }

    @Override // org.apache.tika.parser.ParserDecorator, org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        TemporaryResources temporaryResources = new TemporaryResources();
        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream, temporaryResources, metadata);
        try {
            if (shouldDigest(metadata)) {
                this.digester.digest(tikaInputStream, metadata, parseContext);
            }
            super.parse(tikaInputStream, contentHandler, metadata, parseContext);
        } finally {
            temporaryResources.dispose();
        }
    }

    private boolean shouldDigest(Metadata metadata) {
        if (this.digester == null) {
            return false;
        }
        if (!this.skipContainerDocument) {
            return true;
        }
        Integer num = metadata.getInt(TikaCoreProperties.EMBEDDED_DEPTH);
        return (num == null || num.intValue() == 0) ? false : true;
    }
}
