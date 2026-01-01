package org.apache.commons.io.output;

import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.io.input.QueueInputStream;

/* loaded from: classes4.dex */
public class QueueOutputStream extends OutputStream {
    private final BlockingQueue<Integer> blockingQueue;

    public QueueOutputStream() {
        this(new LinkedBlockingQueue());
    }

    public QueueOutputStream(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = (BlockingQueue) Objects.requireNonNull(blockingQueue, "blockingQueue");
    }

    public QueueInputStream newQueueInputStream() {
        return QueueInputStream.builder().setBlockingQueue(this.blockingQueue).get();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws InterruptedException, InterruptedIOException {
        try {
            this.blockingQueue.put(Integer.valueOf(i & 255));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            InterruptedIOException interruptedIOException = new InterruptedIOException();
            interruptedIOException.initCause(e);
            throw interruptedIOException;
        }
    }
}
