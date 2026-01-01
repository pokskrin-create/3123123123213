package org.apache.commons.io.input.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/* loaded from: classes4.dex */
public class PeekableInputStream extends CircularBufferInputStream {
    public PeekableInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public PeekableInputStream(InputStream inputStream, int i) {
        super(inputStream, i);
    }

    public boolean peek(byte[] bArr) throws IOException {
        Objects.requireNonNull(bArr, "sourceBuffer");
        return peek(bArr, 0, bArr.length);
    }

    public boolean peek(byte[] bArr, int i, int i2) throws IOException {
        Objects.requireNonNull(bArr, "sourceBuffer");
        if (bArr.length > this.bufferSize) {
            throw new IllegalArgumentException("Peek request size of " + bArr.length + " bytes exceeds buffer size of " + this.bufferSize + " bytes");
        }
        if (this.buffer.getCurrentNumberOfBytes() < bArr.length) {
            fillBuffer();
        }
        return this.buffer.peek(bArr, i, i2);
    }
}
