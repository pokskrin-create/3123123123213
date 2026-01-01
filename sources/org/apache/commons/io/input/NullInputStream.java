package org.apache.commons.io.input;

import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes4.dex */
public class NullInputStream extends AbstractInputStream {

    @Deprecated
    public static final NullInputStream INSTANCE = new NullInputStream();
    private long mark;
    private final boolean markSupported;
    private long position;
    private long readLimit;
    private final long size;
    private final boolean throwEofException;

    protected int processByte() {
        return 0;
    }

    protected void processBytes(byte[] bArr, int i, int i2) {
    }

    public NullInputStream() {
        this(0L, true, false);
    }

    public NullInputStream(long j) {
        this(j, true, false);
    }

    public NullInputStream(long j, boolean z, boolean z2) {
        this.mark = -1L;
        this.size = j;
        this.markSupported = z;
        this.throwEofException = z2;
    }

    @Override // java.io.InputStream
    public int available() {
        if (isClosed()) {
            return 0;
        }
        long j = this.size - this.position;
        if (j <= 0) {
            return 0;
        }
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    private void checkThrowEof(String str) throws EOFException {
        if (this.throwEofException) {
            throw new EOFException(str);
        }
    }

    @Override // org.apache.commons.io.input.AbstractInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.mark = -1L;
    }

    public long getPosition() {
        return this.position;
    }

    public long getSize() {
        return this.size;
    }

    private int handleEof() throws IOException {
        checkThrowEof("handleEof()");
        return -1;
    }

    public NullInputStream init() {
        setClosed(false);
        this.position = 0L;
        this.mark = -1L;
        this.readLimit = 0L;
        return this;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i) {
        if (!this.markSupported) {
            throw UnsupportedOperationExceptions.mark();
        }
        this.mark = this.position;
        this.readLimit = i;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.markSupported;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        checkOpen();
        long j = this.position;
        if (j == this.size) {
            return handleEof();
        }
        this.position = j + 1;
        return processByte();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr.length == 0 || i2 == 0) {
            return 0;
        }
        checkOpen();
        long j = this.position;
        long j2 = this.size;
        if (j == j2) {
            return handleEof();
        }
        long j3 = j + i2;
        this.position = j3;
        if (j3 > j2) {
            i2 -= (int) (j3 - j2);
            this.position = j2;
        }
        processBytes(bArr, i, i2);
        return i2;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (!this.markSupported) {
            throw UnsupportedOperationExceptions.reset();
        }
        long j = this.mark;
        if (j < 0) {
            throw new IOException("No position has been marked");
        }
        if (this.position > this.readLimit + j) {
            throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readLimit + "]");
        }
        this.position = j;
        setClosed(false);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (isClosed()) {
            checkThrowEof("skip(long)");
            return -1L;
        }
        long j2 = this.position;
        long j3 = this.size;
        if (j2 == j3) {
            return handleEof();
        }
        long j4 = j2 + j;
        this.position = j4;
        if (j4 <= j3) {
            return j;
        }
        long j5 = j - (j4 - j3);
        this.position = j3;
        return j5;
    }
}
