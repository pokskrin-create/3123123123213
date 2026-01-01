package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class ChunkedOutputStream extends FilterOutputStream {
    private final int chunkSize;

    public static class Builder extends AbstractStreamBuilder<ChunkedOutputStream, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public ChunkedOutputStream get() throws IOException {
            return new ChunkedOutputStream(getOutputStream(), getBufferSize());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public ChunkedOutputStream(OutputStream outputStream) {
        this(outputStream, 8192);
    }

    @Deprecated
    public ChunkedOutputStream(OutputStream outputStream, int i) {
        super(outputStream);
        if (i <= 0) {
            throw new IllegalArgumentException("chunkSize <= 0");
        }
        this.chunkSize = i;
    }

    int getChunkSize() {
        return this.chunkSize;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int iMin = Math.min(i2, this.chunkSize);
            this.out.write(bArr, i, iMin);
            i2 -= iMin;
            i += iMin;
        }
    }
}
