package org.apache.tika.language.translate;

import java.io.IOException;
import org.apache.tika.exception.TikaException;

/* loaded from: classes4.dex */
public interface Translator {
    boolean isAvailable();

    String translate(String str, String str2) throws TikaException, IOException;

    String translate(String str, String str2, String str3) throws TikaException, IOException;
}
