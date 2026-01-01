package org.apache.commons.io.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.Erase;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOIntConsumer;

/* loaded from: classes4.dex */
public abstract class ProxyInputStream extends FilterInputStream {
    private final IOIntConsumer afterRead;
    private boolean closed;
    private final IOConsumer<IOException> exceptionHandler;

    protected void beforeRead(int i) throws IOException {
    }

    protected static abstract class AbstractBuilder<T, B extends AbstractStreamBuilder<T, B>> extends AbstractStreamBuilder<T, B> {
        private IOIntConsumer afterRead;

        protected AbstractBuilder() {
        }

        public IOIntConsumer getAfterRead() {
            return this.afterRead;
        }

        public B setAfterRead(IOIntConsumer iOIntConsumer) {
            this.afterRead = iOIntConsumer;
            return asThis();
        }
    }

    protected ProxyInputStream(AbstractBuilder<?, ?> abstractBuilder) throws IOException {
        this(abstractBuilder.getInputStream(), abstractBuilder);
    }

    public ProxyInputStream(InputStream inputStream) {
        super(inputStream);
        this.exceptionHandler = new IOConsumer() { // from class: org.apache.commons.io.input.ProxyInputStream$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws Throwable {
                Erase.rethrow((IOException) obj);
            }
        };
        this.afterRead = IOIntConsumer.NOOP;
    }

    protected ProxyInputStream(InputStream inputStream, AbstractBuilder<?, ?> abstractBuilder) {
        super(inputStream);
        this.exceptionHandler = new IOConsumer() { // from class: org.apache.commons.io.input.ProxyInputStream$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws Throwable {
                Erase.rethrow((IOException) obj);
            }
        };
        this.afterRead = abstractBuilder.getAfterRead() != null ? abstractBuilder.getAfterRead() : IOIntConsumer.NOOP;
    }

    protected void afterRead(int i) throws IOException {
        this.afterRead.accept(i);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        if (this.in == null || isClosed()) {
            return 0;
        }
        try {
            return this.in.available();
        } catch (IOException e) {
            handleIOException(e);
            return 0;
        }
    }

    void checkOpen() throws IOException {
        Input.checkOpen(!isClosed());
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.in, new IOConsumer() { // from class: org.apache.commons.io.input.ProxyInputStream$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                this.f$0.handleIOException((IOException) obj);
            }
        });
        this.closed = true;
    }

    protected void handleIOException(IOException iOException) throws IOException {
        this.exceptionHandler.accept(iOException);
    }

    boolean isClosed() {
        return this.closed;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        if (this.in != null) {
            this.in.mark(i);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return this.in != null && this.in.markSupported();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = 1;
        try {
            beforeRead(1);
            int i2 = this.in.read();
            if (i2 == -1) {
                i = -1;
            }
            afterRead(i);
            return i2;
        } catch (IOException e) {
            handleIOException(e);
            return -1;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        try {
            beforeRead(IOUtils.length(bArr));
            int i = this.in.read(bArr);
            afterRead(i);
            return i;
        } catch (IOException e) {
            handleIOException(e);
            return -1;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        try {
            beforeRead(i2);
            int i3 = this.in.read(bArr, i, i2);
            afterRead(i3);
            return i3;
        } catch (IOException e) {
            handleIOException(e);
            return -1;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        try {
            this.in.reset();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public ProxyInputStream setReference(InputStream inputStream) {
        this.in = inputStream;
        return this;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        try {
            return this.in.skip(j);
        } catch (IOException e) {
            handleIOException(e);
            return 0L;
        }
    }

    public InputStream unwrap() {
        return this.in;
    }
}
