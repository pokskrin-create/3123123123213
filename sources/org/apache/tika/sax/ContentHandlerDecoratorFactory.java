package org.apache.tika.sax;

import java.io.Serializable;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
public interface ContentHandlerDecoratorFactory extends Serializable {
    ContentHandler decorate(ContentHandler contentHandler, Metadata metadata, ParseContext parseContext);
}
