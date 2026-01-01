package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class ClosedOutputStream extends OutputStream {

    @Deprecated
    public static final ClosedOutputStream CLOSED_OUTPUT_STREAM;
    public static final ClosedOutputStream INSTANCE;

    static {
        ClosedOutputStream closedOutputStream = new ClosedOutputStream();
        INSTANCE = closedOutputStream;
        CLOSED_OUTPUT_STREAM = closedOutputStream;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        throw new IOException("flush() failed: stream is closed");
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        throw new IOException("write(byte[], int, int) failed: stream is closed");
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        throw new IOException("write(int) failed: stream is closed");
    }
}
