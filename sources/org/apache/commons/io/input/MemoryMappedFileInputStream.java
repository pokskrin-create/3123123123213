package org.apache.commons.io.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import kotlin.UByte$$ExternalSyntheticBackport0;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public final class MemoryMappedFileInputStream extends AbstractInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.wrap(new byte[0]).asReadOnlyBuffer();
    private ByteBuffer buffer;
    private final int bufferSize;
    private final FileChannel channel;
    private long nextBufferPosition;

    public static class Builder extends AbstractStreamBuilder<MemoryMappedFileInputStream, Builder> {
        public Builder() {
            setBufferSizeDefault(262144);
            setBufferSize(262144);
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public MemoryMappedFileInputStream get() throws IOException {
            return new MemoryMappedFileInputStream(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private MemoryMappedFileInputStream(Builder builder) throws IOException {
        this.buffer = EMPTY_BUFFER;
        this.bufferSize = builder.getBufferSize();
        this.channel = FileChannel.open(builder.getPath(), StandardOpenOption.READ);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.buffer.remaining();
    }

    private void cleanBuffer() {
        if (ByteBufferCleaner.isSupported() && this.buffer.isDirect()) {
            ByteBufferCleaner.clean(this.buffer);
        }
    }

    @Override // org.apache.commons.io.input.AbstractInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        cleanBuffer();
        this.buffer = EMPTY_BUFFER;
        this.channel.close();
        super.close();
    }

    int getBufferSize() {
        return this.bufferSize;
    }

    private void nextBuffer() throws IOException {
        long size = this.channel.size() - this.nextBufferPosition;
        if (size > 0) {
            long jMin = Math.min(size, this.bufferSize);
            cleanBuffer();
            this.buffer = this.channel.map(FileChannel.MapMode.READ_ONLY, this.nextBufferPosition, jMin);
            this.nextBufferPosition += jMin;
            return;
        }
        this.buffer = EMPTY_BUFFER;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        checkOpen();
        if (!this.buffer.hasRemaining()) {
            nextBuffer();
            if (!this.buffer.hasRemaining()) {
                return -1;
            }
        }
        return UByte$$ExternalSyntheticBackport0.m(this.buffer.get());
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        checkOpen();
        if (!this.buffer.hasRemaining()) {
            nextBuffer();
            if (!this.buffer.hasRemaining()) {
                return -1;
            }
        }
        int iMin = Math.min(this.buffer.remaining(), i2);
        this.buffer.get(bArr, i, iMin);
        return iMin;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        checkOpen();
        if (j <= 0) {
            return 0L;
        }
        if (j <= this.buffer.remaining()) {
            this.buffer.position((int) (r0.position() + j));
            return j;
        }
        long jRemaining = this.buffer.remaining() + Math.min(this.channel.size() - this.nextBufferPosition, j - this.buffer.remaining());
        this.nextBufferPosition += jRemaining - this.buffer.remaining();
        nextBuffer();
        return jRemaining;
    }
}
