package org.apache.tika.io;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class TailStream extends FilterInputStream {
    private static final int SKIP_SIZE = 4096;
    private long bytesRead;
    private int currentIndex;
    private byte[] markBuffer;
    private long markBytesRead;
    private int markIndex;
    private final byte[] tailBuffer;
    private final int tailSize;

    public TailStream(InputStream inputStream, int i) {
        super(inputStream);
        this.tailSize = i;
        this.tailBuffer = new byte[i];
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = super.read();
        if (i != -1) {
            appendByte((byte) i);
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        int i = super.read(bArr);
        if (i > 0) {
            appendBuf(bArr, 0, i);
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 > 0) {
            appendBuf(bArr, i, i3);
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        int iMin = (int) Math.min(j, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        byte[] bArr = new byte[iMin];
        long j2 = 0;
        int i = 0;
        while (j2 < j && i != -1) {
            i = read(bArr, 0, (int) Math.min(iMin, j - j2));
            if (i != -1) {
                j2 += i;
            }
        }
        if (i >= 0 || j2 != 0) {
            return j2;
        }
        return -1L;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        int i2 = this.tailSize;
        byte[] bArr = new byte[i2];
        this.markBuffer = bArr;
        System.arraycopy(this.tailBuffer, 0, bArr, 0, i2);
        this.markIndex = this.currentIndex;
        this.markBytesRead = this.bytesRead;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() {
        byte[] bArr = this.markBuffer;
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.tailBuffer, 0, this.tailSize);
            this.currentIndex = this.markIndex;
            this.bytesRead = this.markBytesRead;
        }
    }

    public byte[] getTail() {
        int iMin = (int) Math.min(this.tailSize, this.bytesRead);
        byte[] bArr = new byte[iMin];
        byte[] bArr2 = this.tailBuffer;
        int i = this.currentIndex;
        System.arraycopy(bArr2, i, bArr, 0, iMin - i);
        byte[] bArr3 = this.tailBuffer;
        int i2 = this.currentIndex;
        System.arraycopy(bArr3, 0, bArr, iMin - i2, i2);
        return bArr;
    }

    private void appendByte(byte b) {
        byte[] bArr = this.tailBuffer;
        int i = this.currentIndex;
        int i2 = i + 1;
        this.currentIndex = i2;
        bArr[i] = b;
        if (i2 >= this.tailSize) {
            this.currentIndex = 0;
        }
        this.bytesRead++;
    }

    private void appendBuf(byte[] bArr, int i, int i2) {
        if (i2 >= this.tailSize) {
            replaceTailBuffer(bArr, i, i2);
        } else {
            copyToTailBuffer(bArr, i, i2);
        }
        this.bytesRead += i2;
    }

    private void replaceTailBuffer(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        int i4 = this.tailSize;
        System.arraycopy(bArr, i3 - i4, this.tailBuffer, 0, i4);
        this.currentIndex = 0;
    }

    private void copyToTailBuffer(byte[] bArr, int i, int i2) {
        int iMin = Math.min(this.tailSize - this.currentIndex, i2);
        System.arraycopy(bArr, i, this.tailBuffer, this.currentIndex, iMin);
        System.arraycopy(bArr, i + iMin, this.tailBuffer, 0, i2 - iMin);
        this.currentIndex = (this.currentIndex + i2) % this.tailSize;
    }
}
