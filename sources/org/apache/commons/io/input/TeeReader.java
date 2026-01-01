package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

/* loaded from: classes4.dex */
public class TeeReader extends ProxyReader {
    private final Writer branch;
    private final boolean closeBranch;

    public TeeReader(Reader reader, Writer writer) {
        this(reader, writer, false);
    }

    public TeeReader(Reader reader, Writer writer, boolean z) {
        super(reader);
        this.branch = writer;
        this.closeBranch = z;
    }

    @Override // org.apache.commons.io.input.ProxyReader, java.io.FilterReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (this.closeBranch) {
                this.branch.close();
            }
        }
    }

    @Override // org.apache.commons.io.input.ProxyReader, java.io.FilterReader, java.io.Reader
    public int read() throws IOException {
        int i = super.read();
        if (i != -1) {
            this.branch.write(i);
        }
        return i;
    }

    @Override // org.apache.commons.io.input.ProxyReader, java.io.Reader
    public int read(char[] cArr) throws IOException {
        int i = super.read(cArr);
        if (i != -1) {
            this.branch.write(cArr, 0, i);
        }
        return i;
    }

    @Override // org.apache.commons.io.input.ProxyReader, java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3 = super.read(cArr, i, i2);
        if (i3 != -1) {
            this.branch.write(cArr, i, i3);
        }
        return i3;
    }

    @Override // org.apache.commons.io.input.ProxyReader, java.io.Reader, java.lang.Readable
    public int read(CharBuffer charBuffer) throws IOException {
        int iPosition = charBuffer.position();
        int i = super.read(charBuffer);
        if (i == -1) {
            return i;
        }
        int iPosition2 = charBuffer.position();
        int iLimit = charBuffer.limit();
        try {
            charBuffer.position(iPosition).limit(iPosition2);
            this.branch.append((CharSequence) charBuffer);
            return i;
        } finally {
            charBuffer.position(iPosition2).limit(iLimit);
        }
    }
}
