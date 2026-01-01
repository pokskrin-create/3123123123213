package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes4.dex */
public class ClosedReader extends Reader {

    @Deprecated
    public static final ClosedReader CLOSED_READER;
    public static final ClosedReader INSTANCE;

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) {
        return -1;
    }

    static {
        ClosedReader closedReader = new ClosedReader();
        INSTANCE = closedReader;
        CLOSED_READER = closedReader;
    }
}
