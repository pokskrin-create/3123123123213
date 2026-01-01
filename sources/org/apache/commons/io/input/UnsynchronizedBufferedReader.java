package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes4.dex */
public class UnsynchronizedBufferedReader extends UnsynchronizedReader {
    private static final char NUL = 0;
    private char[] buf;
    private int end;
    private final Reader in;
    private int mark;
    private int markLimit;
    private int pos;

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    public UnsynchronizedBufferedReader(Reader reader) {
        this(reader, 8192);
    }

    public UnsynchronizedBufferedReader(Reader reader, int i) {
        this.mark = -1;
        this.markLimit = -1;
        if (i <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.in = reader;
        this.buf = new char[i];
    }

    final void chompNewline() throws IOException {
        if (this.pos == this.end && fillBuf() == -1) {
            return;
        }
        char[] cArr = this.buf;
        int i = this.pos;
        if (cArr[i] == '\n') {
            this.pos = i + 1;
        }
    }

    @Override // org.apache.commons.io.input.UnsynchronizedReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        this.in.close();
        this.buf = null;
        super.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int fillBuf() throws java.io.IOException {
        /*
            r6 = this;
            int r0 = r6.mark
            r1 = 0
            r2 = -1
            if (r0 == r2) goto L50
            int r3 = r6.pos
            int r3 = r3 - r0
            int r4 = r6.markLimit
            if (r3 < r4) goto Le
            goto L50
        Le:
            if (r0 != 0) goto L25
            char[] r3 = r6.buf
            int r5 = r3.length
            if (r4 <= r5) goto L25
            int r0 = r3.length
            int r0 = r0 * 2
            if (r0 <= r4) goto L1b
            goto L1c
        L1b:
            r4 = r0
        L1c:
            char[] r0 = new char[r4]
            int r4 = r3.length
            java.lang.System.arraycopy(r3, r1, r0, r1, r4)
            r6.buf = r0
            goto L3c
        L25:
            if (r0 <= 0) goto L3c
            char[] r3 = r6.buf
            int r4 = r3.length
            int r4 = r4 - r0
            java.lang.System.arraycopy(r3, r0, r3, r1, r4)
            int r0 = r6.pos
            int r3 = r6.mark
            int r0 = r0 - r3
            r6.pos = r0
            int r0 = r6.end
            int r0 = r0 - r3
            r6.end = r0
            r6.mark = r1
        L3c:
            java.io.Reader r0 = r6.in
            char[] r1 = r6.buf
            int r3 = r6.pos
            int r4 = r1.length
            int r4 = r4 - r3
            int r0 = r0.read(r1, r3, r4)
            if (r0 == r2) goto L4f
            int r1 = r6.end
            int r1 = r1 + r0
            r6.end = r1
        L4f:
            return r0
        L50:
            java.io.Reader r0 = r6.in
            char[] r3 = r6.buf
            int r4 = r3.length
            int r0 = r0.read(r3, r1, r4)
            if (r0 <= 0) goto L61
            r6.mark = r2
            r6.pos = r1
            r6.end = r0
        L61:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.input.UnsynchronizedBufferedReader.fillBuf():int");
    }

    @Override // java.io.Reader
    public void mark(int i) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        checkOpen();
        this.markLimit = i;
        this.mark = this.pos;
    }

    public int peek() throws IOException {
        mark(1);
        int i = read();
        reset();
        return i;
    }

    public int peek(char[] cArr) throws IOException {
        int length = cArr.length;
        mark(length);
        int i = read(cArr, 0, length);
        reset();
        return i;
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        checkOpen();
        if (this.pos >= this.end && fillBuf() == -1) {
            return -1;
        }
        char[] cArr = this.buf;
        int i = this.pos;
        this.pos = i + 1;
        return cArr[i];
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        checkOpen();
        if (i < 0 || i > cArr.length - i2 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = i2;
        while (true) {
            if (i3 <= 0) {
                break;
            }
            int i4 = this.end;
            int i5 = this.pos;
            int i6 = i4 - i5;
            if (i6 > 0) {
                if (i6 >= i3) {
                    i6 = i3;
                }
                System.arraycopy(this.buf, i5, cArr, i, i6);
                this.pos += i6;
                i += i6;
                i3 -= i6;
            }
            if (i3 == 0 || (i3 < i2 && !this.in.ready())) {
                break;
            }
            int i7 = this.mark;
            if ((i7 == -1 || this.pos - i7 >= this.markLimit) && i3 >= this.buf.length) {
                int i8 = this.in.read(cArr, i, i3);
                if (i8 > 0) {
                    i3 -= i8;
                    this.mark = -1;
                }
            } else if (fillBuf() == -1) {
                break;
            }
        }
        int i9 = i2 - i3;
        if (i9 > 0 || i9 == i2) {
            return i9;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00cc, code lost:
    
        if (r1 != 0) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00ce, code lost:
    
        r6 = r9.buf;
        r8 = r9.pos;
        r0.append(r6, r8, r7 - r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d7, code lost:
    
        r0.append(r9.buf, r9.pos, (r7 - r8) - 1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String readLine() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.input.UnsynchronizedBufferedReader.readLine():java.lang.String");
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        checkOpen();
        return this.end - this.pos > 0 || this.in.ready();
    }

    @Override // java.io.Reader
    public void reset() throws IOException {
        checkOpen();
        int i = this.mark;
        if (i == -1) {
            throw new IOException("mark == -1");
        }
        this.pos = i;
    }

    @Override // org.apache.commons.io.input.UnsynchronizedReader, java.io.Reader
    public long skip(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException();
        }
        checkOpen();
        if (j < 1) {
            return 0L;
        }
        int i = this.end;
        int i2 = this.pos;
        if (i - i2 >= j) {
            this.pos = i2 + Math.toIntExact(j);
            return j;
        }
        long j2 = i - i2;
        this.pos = i;
        while (j2 < j) {
            if (fillBuf() == -1) {
                return j2;
            }
            int i3 = this.end;
            int i4 = this.pos;
            long j3 = j - j2;
            if (i3 - i4 >= j3) {
                this.pos = i4 + Math.toIntExact(j3);
                return j;
            }
            j2 += i3 - i4;
            this.pos = i3;
        }
        return j;
    }
}
