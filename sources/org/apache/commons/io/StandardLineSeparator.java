package org.apache.commons.io;

import java.nio.charset.Charset;
import java.util.Objects;

/* loaded from: classes4.dex */
public enum StandardLineSeparator {
    CR("\r"),
    CRLF("\r\n"),
    LF("\n");

    private final String lineSeparator;

    StandardLineSeparator(String str) {
        this.lineSeparator = (String) Objects.requireNonNull(str, "lineSeparator");
    }

    public byte[] getBytes(Charset charset) {
        return this.lineSeparator.getBytes(charset);
    }

    public String getString() {
        return this.lineSeparator;
    }
}
