package org.apache.commons.io.input;

import java.io.IOException;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CircularInputStream extends AbstractInputStream {
    private long byteCount;
    private int position = -1;
    private final byte[] repeatedContent;
    private final long targetByteCount;

    private static byte[] validate(byte[] bArr) {
        Objects.requireNonNull(bArr, "repeatContent");
        for (byte b : bArr) {
            if (b == -1) {
                throw new IllegalArgumentException("repeatContent contains the end-of-stream marker -1");
            }
        }
        return bArr;
    }

    public CircularInputStream(byte[] bArr, long j) {
        this.repeatedContent = validate(bArr);
        if (bArr.length == 0) {
            throw new IllegalArgumentException("repeatContent is empty.");
        }
        this.targetByteCount = j;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (isClosed()) {
            return 0;
        }
        long j = this.targetByteCount;
        if (j <= 2147483647L) {
            return Math.max(Integer.MAX_VALUE, (int) j);
        }
        return Integer.MAX_VALUE;
    }

    @Override // org.apache.commons.io.input.AbstractInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.byteCount = this.targetByteCount;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.targetByteCount >= 0 || isClosed()) {
            long j = this.byteCount;
            if (j == this.targetByteCount) {
                return -1;
            }
            this.byteCount = j + 1;
        }
        int i = this.position + 1;
        byte[] bArr = this.repeatedContent;
        int length = i % bArr.length;
        this.position = length;
        return bArr[length] & 255;
    }
}
