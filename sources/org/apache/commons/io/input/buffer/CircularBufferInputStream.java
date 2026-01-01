package org.apache.commons.io.input.buffer;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public class CircularBufferInputStream extends FilterInputStream {
    protected final CircularByteBuffer buffer;
    protected final int bufferSize;
    private boolean eof;

    public CircularBufferInputStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public CircularBufferInputStream(InputStream inputStream, int i) {
        super((InputStream) Objects.requireNonNull(inputStream, "inputStream"));
        if (i <= 0) {
            throw new IllegalArgumentException("Illegal bufferSize: " + i);
        }
        this.buffer = new CircularByteBuffer(i);
        this.bufferSize = i;
        this.eof = false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.eof = true;
        this.buffer.clear();
    }

    protected void fillBuffer() throws IOException {
        if (this.eof) {
            return;
        }
        int space = this.buffer.getSpace();
        byte[] bArrByteArray = IOUtils.byteArray(space);
        while (space > 0) {
            int i = this.in.read(bArrByteArray, 0, space);
            if (i == -1) {
                this.eof = true;
                return;
            } else if (i > 0) {
                this.buffer.add(bArrByteArray, 0, i);
                space -= i;
            }
        }
    }

    protected boolean haveBytes(int i) throws IOException {
        if (this.buffer.getCurrentNumberOfBytes() < i) {
            fillBuffer();
        }
        return this.buffer.hasBytes();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (haveBytes(1)) {
            return this.buffer.read() & 255;
        }
        return -1;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        Objects.requireNonNull(bArr, "targetBuffer");
        if (i < 0) {
            throw new IllegalArgumentException("Offset must not be negative");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Length must not be negative");
        }
        if (!haveBytes(i2)) {
            return -1;
        }
        int iMin = Math.min(i2, this.buffer.getCurrentNumberOfBytes());
        for (int i3 = 0; i3 < iMin; i3++) {
            bArr[i + i3] = this.buffer.read();
        }
        return iMin;
    }
}
