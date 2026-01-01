package org.apache.tika.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class DelegatingParser implements Parser {
    protected Parser getDelegateParser(ParseContext parseContext) {
        return (Parser) parseContext.get(Parser.class, EmptyParser.INSTANCE);
    }

    @Override // org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return getDelegateParser(parseContext).getSupportedTypes(parseContext);
    }

    @Override // org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        getDelegateParser(parseContext).parse(inputStream, contentHandler, metadata, parseContext);
    }
}
