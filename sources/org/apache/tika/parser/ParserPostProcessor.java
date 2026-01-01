package org.apache.tika.parser;

import com.google.firebase.messaging.ServiceStarter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.utils.RegexUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class ParserPostProcessor extends ParserDecorator {
    public ParserPostProcessor(Parser parser) {
        super(parser);
    }

    @Override // org.apache.tika.parser.ParserDecorator, org.apache.tika.parser.Parser
    public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
        BodyContentHandler bodyContentHandler = new BodyContentHandler();
        super.parse(inputStream, new TeeContentHandler(contentHandler, bodyContentHandler), metadata, parseContext);
        String string = bodyContentHandler.toString();
        metadata.set("fulltext", string);
        metadata.set("summary", string.substring(0, Math.min(string.length(), ServiceStarter.ERROR_UNKNOWN)));
        Iterator<String> it = RegexUtils.extractLinks(string).iterator();
        while (it.hasNext()) {
            metadata.add("outlinks", it.next());
        }
    }
}
