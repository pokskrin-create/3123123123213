package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public final class UnsynchronizedBufferedInputStream extends UnsynchronizedFilterInputStream {
    protected volatile byte[] buffer;
    protected int count;
    protected int markLimit;
    protected int markPos;
    protected int pos;

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public static class Builder extends AbstractStreamBuilder<UnsynchronizedBufferedInputStream, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public UnsynchronizedBufferedInputStream get() throws IOException {
            return new UnsynchronizedBufferedInputStream(getInputStream(), getBufferSize());
        }
    }

    private UnsynchronizedBufferedInputStream(InputStream inputStream, int i) {
        super(inputStream);
        this.markPos = -1;
        if (i <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.buffer = new byte[i];
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public int available() throws IOException {
        InputStream inputStream = this.inputStream;
        if (this.buffer == null || inputStream == null) {
            throw new IOException("Stream is closed");
        }
        return (this.count - this.pos) + inputStream.available();
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.buffer = null;
        InputStream inputStream = this.inputStream;
        this.inputStream = null;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private int fillBuffer(InputStream inputStream, byte[] bArr) throws IOException {
        int i = this.markPos;
        if (i != -1) {
            int i2 = this.pos - i;
            int i3 = this.markLimit;
            if (i2 < i3) {
                if (i == 0 && i3 > bArr.length) {
                    int length = bArr.length * 2;
                    if (length <= i3) {
                        i3 = length;
                    }
                    byte[] bArr2 = new byte[i3];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    this.buffer = bArr2;
                    bArr = bArr2;
                } else if (i > 0) {
                    System.arraycopy(bArr, i, bArr, 0, bArr.length - i);
                }
                int i4 = this.pos - this.markPos;
                this.pos = i4;
                this.markPos = 0;
                this.count = 0;
                int i5 = inputStream.read(bArr, i4, bArr.length - i4);
                int i6 = this.pos;
                if (i5 > 0) {
                    i6 += i5;
                }
                this.count = i6;
                return i5;
            }
        }
        int i7 = inputStream.read(bArr);
        if (i7 > 0) {
            this.markPos = -1;
            this.pos = 0;
            this.count = i7;
        }
        return i7;
    }

    byte[] getBuffer() {
        return this.buffer;
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public void mark(int i) {
        this.markLimit = i;
        this.markPos = this.pos;
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = this.buffer;
        InputStream inputStream = this.inputStream;
        if (bArr == null || inputStream == null) {
            throw new IOException("Stream is closed");
        }
        if (this.pos >= this.count && fillBuffer(inputStream, bArr) == -1) {
            return -1;
        }
        if (bArr != this.buffer && (bArr = this.buffer) == null) {
            throw new IOException("Stream is closed");
        }
        int i = this.count;
        int i2 = this.pos;
        if (i - i2 <= 0) {
            return -1;
        }
        this.pos = i2 + 1;
        return bArr[i2] & 255;
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        byte[] bArr2 = this.buffer;
        if (bArr2 == null) {
            throw new IOException("Stream is closed");
        }
        if (i > bArr.length - i2 || i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        InputStream inputStream = this.inputStream;
        if (inputStream == null) {
            throw new IOException("Stream is closed");
        }
        int i5 = this.pos;
        int i6 = this.count;
        if (i5 < i6) {
            int i7 = i6 - i5 >= i2 ? i2 : i6 - i5;
            System.arraycopy(bArr2, i5, bArr, i, i7);
            this.pos += i7;
            if (i7 == i2 || inputStream.available() == 0) {
                return i7;
            }
            i += i7;
            i3 = i2 - i7;
        } else {
            i3 = i2;
        }
        while (true) {
            if (this.markPos == -1 && i3 >= bArr2.length) {
                i4 = inputStream.read(bArr, i, i3);
                if (i4 == -1) {
                    if (i3 == i2) {
                        return -1;
                    }
                    return i2 - i3;
                }
            } else {
                if (fillBuffer(inputStream, bArr2) == -1) {
                    if (i3 == i2) {
                        return -1;
                    }
                    return i2 - i3;
                }
                if (bArr2 != this.buffer && (bArr2 = this.buffer) == null) {
                    throw new IOException("Stream is closed");
                }
                int i8 = this.count;
                int i9 = this.pos;
                i4 = i8 - i9 >= i3 ? i3 : i8 - i9;
                System.arraycopy(bArr2, i9, bArr, i, i4);
                this.pos += i4;
            }
            i3 -= i4;
            if (i3 == 0) {
                return i2;
            }
            if (inputStream.available() == 0) {
                return i2 - i3;
            }
            i += i4;
        }
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        if (this.buffer == null) {
            throw new IOException("Stream is closed");
        }
        int i = this.markPos;
        if (-1 == i) {
            throw new IOException("Mark has been invalidated");
        }
        this.pos = i;
    }

    @Override // org.apache.commons.io.input.UnsynchronizedFilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        byte[] bArr = this.buffer;
        InputStream inputStream = this.inputStream;
        if (bArr == null) {
            throw new IOException("Stream is closed");
        }
        if (j < 1) {
            return 0L;
        }
        if (inputStream == null) {
            throw new IOException("Stream is closed");
        }
        int i = this.count;
        int i2 = this.pos;
        if (i - i2 >= j) {
            this.pos = i2 + ((int) j);
            return j;
        }
        int i3 = i - i2;
        this.pos = i;
        if (this.markPos != -1 && j <= this.markLimit) {
            if (fillBuffer(inputStream, bArr) == -1) {
                return i3;
            }
            int i4 = this.count;
            int i5 = this.pos;
            if (i4 - i5 >= j - i3) {
                this.pos = i5 + (((int) j) - i3);
                return j;
            }
            this.pos = i4;
            return i3 + (i4 - i5);
        }
        long j2 = i3;
        return j2 + inputStream.skip(j - j2);
    }
}
