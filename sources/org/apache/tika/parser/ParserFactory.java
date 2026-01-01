package org.apache.tika.parser;

import java.io.IOException;
import java.util.Map;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public abstract class ParserFactory {
    final Map<String, String> args;

    public abstract Parser build() throws TikaException, SAXException, IOException;

    public ParserFactory(Map<String, String> map) {
        this.args = map;
    }
}
