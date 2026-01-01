package org.apache.commons.io.input;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import org.apache.commons.io.IOUtils$$ExternalSyntheticThreadLocal9;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class ReadAheadInputStream extends FilterInputStream {
    private static final ThreadLocal<byte[]> BYTE_ARRAY_1 = new IOUtils$$ExternalSyntheticThreadLocal9(new Supplier() { // from class: org.apache.commons.io.input.ReadAheadInputStream$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return ReadAheadInputStream.lambda$static$0();
        }
    });
    private ByteBuffer activeBuffer;
    private final Condition asyncReadComplete;
    private boolean endOfStream;
    private final ExecutorService executorService;
    private boolean isClosed;
    private boolean isReading;
    private boolean isUnderlyingInputStreamBeingClosed;
    private final AtomicBoolean isWaiting;
    private boolean readAborted;
    private ByteBuffer readAheadBuffer;
    private Throwable readException;
    private boolean readInProgress;
    private final boolean shutdownExecutorService;
    private final ReentrantLock stateChangeLock;

    public static class Builder extends AbstractStreamBuilder<ReadAheadInputStream, Builder> {
        private ExecutorService executorService;

        @Override // org.apache.commons.io.function.IOSupplier
        public ReadAheadInputStream get() throws IOException {
            InputStream inputStream = getInputStream();
            int bufferSize = getBufferSize();
            ExecutorService executorServiceNewExecutorService = this.executorService;
            if (executorServiceNewExecutorService == null) {
                executorServiceNewExecutorService = ReadAheadInputStream.newExecutorService();
            }
            return new ReadAheadInputStream(inputStream, bufferSize, executorServiceNewExecutorService, this.executorService == null);
        }

        public Builder setExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }
    }

    static /* synthetic */ byte[] lambda$static$0() {
        return new byte[1];
    }

    public static Builder builder() {
        return new Builder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Thread newDaemonThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "commons-io-read-ahead");
        thread.setDaemon(true);
        return thread;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ExecutorService newExecutorService() {
        return Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: org.apache.commons.io.input.ReadAheadInputStream$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ReadAheadInputStream.newDaemonThread(runnable);
            }
        });
    }

    @Deprecated
    public ReadAheadInputStream(InputStream inputStream, int i) {
        this(inputStream, i, newExecutorService(), true);
    }

    @Deprecated
    public ReadAheadInputStream(InputStream inputStream, int i, ExecutorService executorService) {
        this(inputStream, i, executorService, false);
    }

    private ReadAheadInputStream(InputStream inputStream, int i, ExecutorService executorService, boolean z) {
        super((InputStream) Objects.requireNonNull(inputStream, "inputStream"));
        ReentrantLock reentrantLock = new ReentrantLock();
        this.stateChangeLock = reentrantLock;
        this.isWaiting = new AtomicBoolean();
        this.asyncReadComplete = reentrantLock.newCondition();
        if (i <= 0) {
            throw new IllegalArgumentException("bufferSizeInBytes should be greater than 0, but the value is " + i);
        }
        this.executorService = (ExecutorService) Objects.requireNonNull(executorService, "executorService");
        this.shutdownExecutorService = z;
        this.activeBuffer = ByteBuffer.allocate(i);
        this.readAheadBuffer = ByteBuffer.allocate(i);
        this.activeBuffer.flip();
        this.readAheadBuffer.flip();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        this.stateChangeLock.lock();
        try {
            return (int) Math.min(2147483647L, this.activeBuffer.remaining() + this.readAheadBuffer.remaining());
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    private void checkReadException() throws IOException {
        if (this.readAborted) {
            Throwable th = this.readException;
            if (th instanceof IOException) {
                throw ((IOException) th);
            }
            throw new IOException(this.readException);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.stateChangeLock.lock();
        try {
            if (this.isClosed) {
                return;
            }
            boolean z = true;
            this.isClosed = true;
            if (this.isReading) {
                z = false;
            } else {
                this.isUnderlyingInputStreamBeingClosed = true;
            }
            this.stateChangeLock.unlock();
            if (this.shutdownExecutorService) {
                try {
                    try {
                        this.executorService.shutdownNow();
                        this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException(e.getMessage());
                        interruptedIOException.initCause(e);
                        throw interruptedIOException;
                    }
                } finally {
                    if (z) {
                        super.close();
                    }
                }
            }
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    private void closeUnderlyingInputStreamIfNecessary() throws IOException {
        this.stateChangeLock.lock();
        boolean z = false;
        try {
            this.isReading = false;
            if (this.isClosed) {
                if (!this.isUnderlyingInputStreamBeingClosed) {
                    z = true;
                }
            }
            if (z) {
                try {
                    super.close();
                } catch (IOException unused) {
                }
            }
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    private boolean isEndOfStream() {
        return (this.activeBuffer.hasRemaining() || this.readAheadBuffer.hasRemaining() || !this.endOfStream) ? false : true;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        byte b;
        if (this.activeBuffer.hasRemaining()) {
            b = this.activeBuffer.get();
        } else {
            byte[] bArr = BYTE_ARRAY_1.get();
            bArr[0] = 0;
            if (read(bArr, 0, 1) == -1) {
                return -1;
            }
            b = bArr[0];
        }
        return b & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        if (!this.activeBuffer.hasRemaining()) {
            this.stateChangeLock.lock();
            try {
                waitForAsyncReadComplete();
                if (!this.readAheadBuffer.hasRemaining()) {
                    readAsync();
                    waitForAsyncReadComplete();
                    if (isEndOfStream()) {
                        this.stateChangeLock.unlock();
                        return -1;
                    }
                }
                swapBuffers();
                readAsync();
            } finally {
                this.stateChangeLock.unlock();
            }
        }
        int iMin = Math.min(i2, this.activeBuffer.remaining());
        this.activeBuffer.get(bArr, i, iMin);
        return iMin;
    }

    private void readAsync() throws IOException {
        this.stateChangeLock.lock();
        try {
            final byte[] bArrArray = this.readAheadBuffer.array();
            if (!this.endOfStream && !this.readInProgress) {
                checkReadException();
                this.readAheadBuffer.position(0);
                this.readAheadBuffer.flip();
                this.readInProgress = true;
                this.stateChangeLock.unlock();
                this.executorService.execute(new Runnable() { // from class: org.apache.commons.io.input.ReadAheadInputStream$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        this.f$0.m2194x982106fc(bArrArray);
                    }
                });
            }
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    /* renamed from: lambda$readAsync$1$org-apache-commons-io-input-ReadAheadInputStream, reason: not valid java name */
    /* synthetic */ void m2194x982106fc(byte[] bArr) throws IOException {
        this.stateChangeLock.lock();
        try {
            if (this.isClosed) {
                this.readInProgress = false;
                return;
            }
            this.isReading = true;
            this.stateChangeLock.unlock();
            int length = bArr.length;
            int i = 0;
            int i2 = 0;
            do {
                try {
                    i2 = this.in.read(bArr, i, length);
                    if (i2 > 0) {
                        i += i2;
                        length -= i2;
                        if (length <= 0) {
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (Throwable th) {
                    try {
                        if (th instanceof Error) {
                            throw th;
                        }
                        this.stateChangeLock.lock();
                        try {
                            this.readAheadBuffer.limit(i);
                            if (i2 < 0 || (th instanceof EOFException)) {
                                this.endOfStream = true;
                            } else {
                                this.readAborted = true;
                                this.readException = th;
                            }
                            this.readInProgress = false;
                            signalAsyncReadComplete();
                            this.stateChangeLock.unlock();
                            closeUnderlyingInputStreamIfNecessary();
                            return;
                        } finally {
                        }
                    } catch (Throwable th2) {
                        this.stateChangeLock.lock();
                        try {
                            this.readAheadBuffer.limit(i);
                            if (i2 < 0 || (th instanceof EOFException)) {
                                this.endOfStream = true;
                            } else {
                                this.readAborted = true;
                                this.readException = th;
                            }
                            this.readInProgress = false;
                            signalAsyncReadComplete();
                            this.stateChangeLock.unlock();
                            closeUnderlyingInputStreamIfNecessary();
                            throw th2;
                        } finally {
                        }
                    }
                }
            } while (!this.isWaiting.get());
            this.stateChangeLock.lock();
            try {
                this.readAheadBuffer.limit(i);
                if (i2 < 0) {
                    this.endOfStream = true;
                }
                this.readInProgress = false;
                signalAsyncReadComplete();
                this.stateChangeLock.unlock();
                closeUnderlyingInputStreamIfNecessary();
            } finally {
            }
        } finally {
        }
    }

    private void signalAsyncReadComplete() {
        this.stateChangeLock.lock();
        try {
            this.asyncReadComplete.signalAll();
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        if (j <= 0) {
            return 0L;
        }
        if (j <= this.activeBuffer.remaining()) {
            ByteBuffer byteBuffer = this.activeBuffer;
            byteBuffer.position(((int) j) + byteBuffer.position());
            return j;
        }
        this.stateChangeLock.lock();
        try {
            return skipInternal(j);
        } finally {
            this.stateChangeLock.unlock();
        }
    }

    private long skipInternal(long j) throws IOException {
        if (!this.stateChangeLock.isLocked()) {
            throw new IllegalStateException("Expected stateChangeLock to be locked");
        }
        waitForAsyncReadComplete();
        if (isEndOfStream()) {
            return 0L;
        }
        if (available() >= j) {
            int iRemaining = ((int) j) - this.activeBuffer.remaining();
            if (iRemaining <= 0) {
                throw new IllegalStateException("Expected toSkip > 0, actual: " + iRemaining);
            }
            this.activeBuffer.position(0);
            this.activeBuffer.flip();
            ByteBuffer byteBuffer = this.readAheadBuffer;
            byteBuffer.position(iRemaining + byteBuffer.position());
            swapBuffers();
            readAsync();
            return j;
        }
        long jAvailable = available();
        this.activeBuffer.position(0);
        this.activeBuffer.flip();
        this.readAheadBuffer.position(0);
        this.readAheadBuffer.flip();
        long jSkip = this.in.skip(j - jAvailable);
        readAsync();
        return jAvailable + jSkip;
    }

    private void swapBuffers() {
        ByteBuffer byteBuffer = this.activeBuffer;
        this.activeBuffer = this.readAheadBuffer;
        this.readAheadBuffer = byteBuffer;
    }

    private void waitForAsyncReadComplete() throws IOException {
        this.stateChangeLock.lock();
        try {
            try {
                this.isWaiting.set(true);
                while (this.readInProgress) {
                    this.asyncReadComplete.await();
                }
                try {
                    this.isWaiting.set(false);
                    this.stateChangeLock.unlock();
                    checkReadException();
                } finally {
                }
            } catch (InterruptedException e) {
                InterruptedIOException interruptedIOException = new InterruptedIOException(e.getMessage());
                interruptedIOException.initCause(e);
                throw interruptedIOException;
            }
        } catch (Throwable th) {
            try {
                this.isWaiting.set(false);
                throw th;
            } finally {
            }
        }
    }
}
