package org.apache.commons.io.input;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class RandomAccessFileInputStream extends AbstractInputStream {
    private final boolean propagateClose;
    private final RandomAccessFile randomAccessFile;

    public static class Builder extends AbstractStreamBuilder<RandomAccessFileInputStream, Builder> {
        private boolean propagateClose;

        @Override // org.apache.commons.io.function.IOSupplier
        public RandomAccessFileInputStream get() throws IOException {
            return new RandomAccessFileInputStream(getRandomAccessFile(), this.propagateClose);
        }

        public Builder setCloseOnClose(boolean z) {
            this.propagateClose = z;
            return this;
        }

        @Override // org.apache.commons.io.build.AbstractOriginSupplier
        public Builder setRandomAccessFile(RandomAccessFile randomAccessFile) {
            return (Builder) super.setRandomAccessFile(randomAccessFile);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public RandomAccessFileInputStream(RandomAccessFile randomAccessFile) {
        this(randomAccessFile, false);
    }

    @Deprecated
    public RandomAccessFileInputStream(RandomAccessFile randomAccessFile, boolean z) {
        this.randomAccessFile = (RandomAccessFile) Objects.requireNonNull(randomAccessFile, "file");
        this.propagateClose = z;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return Math.toIntExact(Math.min(availableLong(), 2147483647L));
    }

    public long availableLong() throws IOException {
        if (isClosed()) {
            return 0L;
        }
        return this.randomAccessFile.length() - this.randomAccessFile.getFilePointer();
    }

    @Override // org.apache.commons.io.input.AbstractInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        if (this.propagateClose) {
            this.randomAccessFile.close();
        }
    }

    public long copy(long j, long j2, OutputStream outputStream) throws IOException {
        this.randomAccessFile.seek(j);
        return IOUtils.copyLarge(this, outputStream, 0L, j2);
    }

    public RandomAccessFile getRandomAccessFile() {
        return this.randomAccessFile;
    }

    public boolean isCloseOnClose() {
        return this.propagateClose;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.randomAccessFile.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return this.randomAccessFile.read(bArr);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.randomAccessFile.read(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j <= 0) {
            return 0L;
        }
        long filePointer = this.randomAccessFile.getFilePointer();
        long length = this.randomAccessFile.length();
        if (filePointer >= length) {
            return 0L;
        }
        long j2 = j + filePointer;
        if (j2 > length) {
            j2 = length - 1;
        }
        if (j2 > 0) {
            this.randomAccessFile.seek(j2);
        }
        return this.randomAccessFile.getFilePointer() - filePointer;
    }
}
