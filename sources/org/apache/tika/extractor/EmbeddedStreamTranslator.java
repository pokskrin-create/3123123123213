package org.apache.tika.extractor;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public interface EmbeddedStreamTranslator {
    boolean shouldTranslate(InputStream inputStream, Metadata metadata) throws IOException;

    InputStream translate(InputStream inputStream, Metadata metadata) throws IOException;
}
