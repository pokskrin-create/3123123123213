package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.ProxyInputStream;

@Deprecated
/* loaded from: classes4.dex */
public class CountingInputStream extends ProxyInputStream {
    private long count;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    CountingInputStream(InputStream inputStream, ProxyInputStream.AbstractBuilder<?, ?> abstractBuilder) {
        super(inputStream, abstractBuilder);
    }

    CountingInputStream(ProxyInputStream.AbstractBuilder<?, ?> abstractBuilder) throws IOException {
        super(abstractBuilder);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream
    protected synchronized void afterRead(int i) throws IOException {
        if (i != -1) {
            this.count += i;
            super.afterRead(i);
        } else {
            super.afterRead(i);
        }
    }

    public synchronized long getByteCount() {
        return this.count;
    }

    @Deprecated
    public int getCount() {
        long byteCount = getByteCount();
        if (byteCount <= 2147483647L) {
            return (int) byteCount;
        }
        throw new ArithmeticException("The byte count " + byteCount + " is too large to be converted to an int");
    }

    public synchronized long resetByteCount() {
        long j;
        j = this.count;
        this.count = 0L;
        return j;
    }

    @Deprecated
    public int resetCount() {
        long jResetByteCount = resetByteCount();
        if (jResetByteCount <= 2147483647L) {
            return (int) jResetByteCount;
        }
        throw new ArithmeticException("The byte count " + jResetByteCount + " is too large to be converted to an int");
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long j) throws IOException {
        long jSkip;
        jSkip = super.skip(j);
        this.count += jSkip;
        return jSkip;
    }
}
