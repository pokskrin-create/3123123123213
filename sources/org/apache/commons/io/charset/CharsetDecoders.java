package org.apache.commons.io.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* loaded from: classes4.dex */
public final class CharsetDecoders {
    public static CharsetDecoder toCharsetDecoder(CharsetDecoder charsetDecoder) {
        return charsetDecoder != null ? charsetDecoder : Charset.defaultCharset().newDecoder();
    }

    private CharsetDecoders() {
    }
}
