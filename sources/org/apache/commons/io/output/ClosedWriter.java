package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes4.dex */
public class ClosedWriter extends Writer {

    @Deprecated
    public static final ClosedWriter CLOSED_WRITER;
    public static final ClosedWriter INSTANCE;

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    static {
        ClosedWriter closedWriter = new ClosedWriter();
        INSTANCE = closedWriter;
        CLOSED_WRITER = closedWriter;
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        throw new IOException("flush() failed: stream is closed");
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        throw new IOException("write(" + new String(cArr) + ", " + i + ", " + i2 + ") failed: stream is closed");
    }
}
