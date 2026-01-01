package org.apache.tika.sax;

import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
public interface ContentHandlerFactory extends Serializable {
    ContentHandler getNewContentHandler();

    ContentHandler getNewContentHandler(OutputStream outputStream, Charset charset);
}
