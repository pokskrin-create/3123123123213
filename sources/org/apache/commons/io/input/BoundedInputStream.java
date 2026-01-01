package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOBiConsumer;
import org.apache.commons.io.function.IOIntConsumer;
import org.apache.commons.io.input.ProxyInputStream;

/* loaded from: classes4.dex */
public class BoundedInputStream extends ProxyInputStream {
    private long count;
    private long mark;
    private final long maxCount;
    private final IOBiConsumer<Long, Long> onMaxCount;
    private boolean propagateClose;

    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends ProxyInputStream.AbstractBuilder<BoundedInputStream, T> {
        private long count;
        private long maxCount = -1;
        private IOBiConsumer<Long, Long> onMaxCount = IOBiConsumer.noop();
        private boolean propagateClose = true;

        AbstractBuilder() {
        }

        long getCount() {
            return this.count;
        }

        long getMaxCount() {
            return this.maxCount;
        }

        IOBiConsumer<Long, Long> getOnMaxCount() {
            return this.onMaxCount;
        }

        boolean isPropagateClose() {
            return this.propagateClose;
        }

        public T setCount(long j) {
            this.count = Math.max(0L, j);
            return (T) asThis();
        }

        public T setMaxCount(long j) {
            this.maxCount = Math.max(-1L, j);
            return (T) asThis();
        }

        public T setOnMaxCount(IOBiConsumer<Long, Long> iOBiConsumer) {
            if (iOBiConsumer == null) {
                iOBiConsumer = IOBiConsumer.noop();
            }
            this.onMaxCount = iOBiConsumer;
            return (T) asThis();
        }

        public T setPropagateClose(boolean z) {
            this.propagateClose = z;
            return (T) asThis();
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {
        @Override // org.apache.commons.io.input.ProxyInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ IOIntConsumer getAfterRead() {
            return super.getAfterRead();
        }

        @Override // org.apache.commons.io.input.ProxyInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractStreamBuilder setAfterRead(IOIntConsumer iOIntConsumer) {
            return super.setAfterRead(iOIntConsumer);
        }

        @Override // org.apache.commons.io.input.BoundedInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractBuilder setCount(long j) {
            return super.setCount(j);
        }

        @Override // org.apache.commons.io.input.BoundedInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractBuilder setMaxCount(long j) {
            return super.setMaxCount(j);
        }

        @Override // org.apache.commons.io.input.BoundedInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractBuilder setOnMaxCount(IOBiConsumer iOBiConsumer) {
            return super.setOnMaxCount(iOBiConsumer);
        }

        @Override // org.apache.commons.io.input.BoundedInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractBuilder setPropagateClose(boolean z) {
            return super.setPropagateClose(z);
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public BoundedInputStream get() throws IOException {
            return new BoundedInputStream(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    BoundedInputStream(Builder builder) throws IOException {
        super(builder);
        this.propagateClose = true;
        this.count = builder.getCount();
        this.maxCount = builder.getMaxCount();
        this.propagateClose = builder.isPropagateClose();
        this.onMaxCount = builder.getOnMaxCount();
    }

    @Deprecated
    public BoundedInputStream(InputStream inputStream) {
        this(inputStream, -1L);
    }

    BoundedInputStream(InputStream inputStream, Builder builder) {
        super(inputStream, builder);
        this.propagateClose = true;
        this.count = builder.getCount();
        this.maxCount = builder.getMaxCount();
        this.propagateClose = builder.isPropagateClose();
        this.onMaxCount = builder.getOnMaxCount();
    }

    @Deprecated
    public BoundedInputStream(InputStream inputStream, long j) {
        this(inputStream, (Builder) builder().setMaxCount(j));
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

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        if (isMaxCount()) {
            onMaxLength(this.maxCount, getCount());
            return 0;
        }
        return this.in.available();
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.propagateClose) {
            super.close();
        }
    }

    public synchronized long getCount() {
        return this.count;
    }

    public long getMaxCount() {
        return this.maxCount;
    }

    @Deprecated
    public long getMaxLength() {
        return this.maxCount;
    }

    public long getRemaining() {
        return Math.max(0L, getMaxCount() - getCount());
    }

    private boolean isMaxCount() {
        return this.maxCount >= 0 && getCount() >= this.maxCount;
    }

    public boolean isPropagateClose() {
        return this.propagateClose;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        this.in.mark(i);
        this.mark = this.count;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return this.in.markSupported();
    }

    protected void onMaxLength(long j, long j2) throws IOException {
        this.onMaxCount.accept(Long.valueOf(j), Long.valueOf(j2));
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (isMaxCount()) {
            onMaxLength(this.maxCount, getCount());
            return -1;
        }
        return super.read();
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (isMaxCount()) {
            onMaxLength(this.maxCount, getCount());
            return -1;
        }
        return super.read(bArr, i, (int) toReadLen(i2));
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        this.in.reset();
        this.count = this.mark;
    }

    @Deprecated
    public void setPropagateClose(boolean z) {
        this.propagateClose = z;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long j) throws IOException {
        long jSkip;
        jSkip = super.skip(toReadLen(j));
        this.count += jSkip;
        return jSkip;
    }

    private long toReadLen(long j) {
        long j2 = this.maxCount;
        return j2 >= 0 ? Math.min(j, j2 - getCount()) : j;
    }

    public String toString() {
        return this.in.toString();
    }
}
