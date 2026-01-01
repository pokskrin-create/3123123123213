package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class WindowsLineEndingInputStream extends InputStream {
    private boolean atEos;
    private boolean atSlashCr;
    private boolean atSlashLf;
    private final InputStream in;
    private boolean injectSlashLf;
    private final boolean lineFeedAtEos;

    public WindowsLineEndingInputStream(InputStream inputStream, boolean z) {
        this.in = inputStream;
        this.lineFeedAtEos = z;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.in.close();
    }

    private int handleEos() {
        if (!this.lineFeedAtEos) {
            return -1;
        }
        boolean z = this.atSlashLf;
        if (!z && !this.atSlashCr) {
            this.atSlashCr = true;
            return 13;
        }
        if (z) {
            return -1;
        }
        this.atSlashCr = false;
        this.atSlashLf = true;
        return 10;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i) {
        throw UnsupportedOperationExceptions.mark();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.atEos) {
            return handleEos();
        }
        if (this.injectSlashLf) {
            this.injectSlashLf = false;
            return 10;
        }
        boolean z = this.atSlashCr;
        int i = this.in.read();
        boolean z2 = i == -1;
        this.atEos = z2;
        if (!z2) {
            this.atSlashCr = i == 13;
            this.atSlashLf = i == 10;
        }
        if (z2) {
            return handleEos();
        }
        if (i != 10 || z) {
            return i;
        }
        this.injectSlashLf = true;
        return 13;
    }
}
