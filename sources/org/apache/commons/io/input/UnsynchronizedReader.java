package org.apache.commons.io.input;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.Reader;

/* loaded from: classes4.dex */
public abstract class UnsynchronizedReader extends Reader {
    private static final int MAX_SKIP_BUFFER_SIZE = 8192;
    private boolean closed;
    private char[] skipBuffer;

    void checkOpen() throws IOException {
        Input.checkOpen(!isClosed());
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean z) {
        this.closed = z;
    }

    @Override // java.io.Reader
    public long skip(long j) throws IOException {
        int i;
        if (j < 0) {
            throw new IllegalArgumentException("skip value < 0");
        }
        int iMin = (int) Math.min(j, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
        char[] cArr = this.skipBuffer;
        if (cArr == null || cArr.length < iMin) {
            this.skipBuffer = new char[iMin];
        }
        long j2 = j;
        while (j2 > 0 && (i = read(this.skipBuffer, 0, (int) Math.min(j2, iMin))) != -1) {
            j2 -= i;
        }
        return j - j2;
    }
}
