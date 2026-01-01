package org.apache.tika.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class BoundedInputStream extends InputStream {
    private static final int EOF = -1;
    private final InputStream in;
    private final long max;
    private long pos;

    public BoundedInputStream(long j, InputStream inputStream) {
        this.max = j;
        this.in = inputStream;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        long j = this.max;
        if (j >= 0 && this.pos >= j) {
            return -1;
        }
        int i = this.in.read();
        this.pos++;
        return i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.max;
        if (j >= 0 && this.pos >= j) {
            return -1;
        }
        int i3 = this.in.read(bArr, i, (int) (j >= 0 ? Math.min(i2, j - this.pos) : i2));
        if (i3 == -1) {
            return -1;
        }
        this.pos += i3;
        return i3;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = this.max;
        if (j2 >= 0) {
            j = Math.min(j, j2 - this.pos);
        }
        long jSkip = this.in.skip(j);
        this.pos += jSkip;
        return jSkip;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        this.in.reset();
        this.pos = 0L;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.in.mark(i);
    }

    public boolean hasHitBound() {
        return this.pos >= this.max;
    }

    @Override // java.io.InputStream
    public byte[] readNBytes(int i) throws IOException {
        return this.in.readNBytes(i);
    }

    @Override // java.io.InputStream
    public int readNBytes(byte[] bArr, int i, int i2) throws IOException {
        return this.in.readNBytes(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override // java.io.InputStream
    public long transferTo(OutputStream outputStream) throws IOException {
        return org.apache.commons.io.IOUtils.copy(this, outputStream);
    }

    public long getPos() {
        return this.pos;
    }
}
