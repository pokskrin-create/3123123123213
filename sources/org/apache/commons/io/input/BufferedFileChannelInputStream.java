package org.apache.commons.io.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public final class BufferedFileChannelInputStream extends InputStream {
    private final ByteBuffer byteBuffer;
    private final FileChannel fileChannel;

    public static class Builder extends AbstractStreamBuilder<BufferedFileChannelInputStream, Builder> {
        private FileChannel fileChannel;

        @Override // org.apache.commons.io.function.IOSupplier
        public BufferedFileChannelInputStream get() throws IOException {
            return this.fileChannel != null ? new BufferedFileChannelInputStream(this.fileChannel, getBufferSize()) : new BufferedFileChannelInputStream(getPath(), getBufferSize());
        }

        public Builder setFileChannel(FileChannel fileChannel) {
            this.fileChannel = fileChannel;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public BufferedFileChannelInputStream(File file) throws IOException {
        this(file, 8192);
    }

    @Deprecated
    public BufferedFileChannelInputStream(File file, int i) throws IOException {
        this(file.toPath(), i);
    }

    private BufferedFileChannelInputStream(FileChannel fileChannel, int i) {
        this.fileChannel = (FileChannel) Objects.requireNonNull(fileChannel, "path");
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i);
        this.byteBuffer = byteBufferAllocateDirect;
        byteBufferAllocateDirect.flip();
    }

    @Deprecated
    public BufferedFileChannelInputStream(Path path) throws IOException {
        this(path, 8192);
    }

    @Deprecated
    public BufferedFileChannelInputStream(Path path, int i) throws IOException {
        this(FileChannel.open(path, StandardOpenOption.READ), i);
    }

    @Override // java.io.InputStream
    public synchronized int available() throws IOException {
        if (!this.fileChannel.isOpen()) {
            return 0;
        }
        if (!refill()) {
            return 0;
        }
        return this.byteBuffer.remaining();
    }

    private void clean(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            cleanDirectBuffer(byteBuffer);
        }
    }

    private void cleanDirectBuffer(ByteBuffer byteBuffer) {
        if (ByteBufferCleaner.isSupported()) {
            ByteBufferCleaner.clean(byteBuffer);
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        try {
            this.fileChannel.close();
        } finally {
            clean(this.byteBuffer);
        }
    }

    @Override // java.io.InputStream
    public synchronized int read() throws IOException {
        if (!refill()) {
            return -1;
        }
        return this.byteBuffer.get() & 255;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i >= 0 && i2 >= 0 && (i3 = i + i2) >= 0) {
            if (i3 <= bArr.length) {
                if (!refill()) {
                    return -1;
                }
                int iMin = Math.min(i2, this.byteBuffer.remaining());
                this.byteBuffer.get(bArr, i, iMin);
                return iMin;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private boolean refill() throws IOException {
        Input.checkOpen(this.fileChannel.isOpen());
        if (this.byteBuffer.hasRemaining()) {
            return true;
        }
        this.byteBuffer.clear();
        int i = 0;
        while (i == 0) {
            i = this.fileChannel.read(this.byteBuffer);
        }
        this.byteBuffer.flip();
        return i >= 0;
    }

    @Override // java.io.InputStream
    public synchronized long skip(long j) throws IOException {
        if (j <= 0) {
            return 0L;
        }
        if (this.byteBuffer.remaining() >= j) {
            ByteBuffer byteBuffer = this.byteBuffer;
            byteBuffer.position(byteBuffer.position() + ((int) j));
            return j;
        }
        long jRemaining = this.byteBuffer.remaining();
        this.byteBuffer.position(0);
        this.byteBuffer.flip();
        return jRemaining + skipFromFileChannel(j - jRemaining);
    }

    private long skipFromFileChannel(long j) throws IOException {
        long jPosition = this.fileChannel.position();
        long size = this.fileChannel.size();
        long j2 = size - jPosition;
        if (j > j2) {
            this.fileChannel.position(size);
            return j2;
        }
        this.fileChannel.position(jPosition + j);
        return j;
    }
}
