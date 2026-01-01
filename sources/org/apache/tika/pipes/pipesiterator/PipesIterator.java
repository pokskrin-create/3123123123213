package org.apache.tika.pipes.pipesiterator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.tika.config.ConfigBase;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaTimeoutException;
import org.apache.tika.pipes.FetchEmitTuple;
import org.apache.tika.pipes.HandlerConfig;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public abstract class PipesIterator extends ConfigBase implements Callable<Integer>, Iterable<FetchEmitTuple>, Initializable {
    public static final long DEFAULT_MAX_WAIT_MS = 300000;
    public static final int DEFAULT_QUEUE_SIZE = 1000;
    private String emitterName;
    private String fetcherName;
    private FutureTask<Integer> futureTask;
    public static final FetchEmitTuple COMPLETED_SEMAPHORE = new FetchEmitTuple(null, null, null, null, null, null);
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) PipesIterator.class);
    private long maxWaitMs = 300000;
    private ArrayBlockingQueue<FetchEmitTuple> queue = null;
    private int queueSize = 1000;
    private FetchEmitTuple.ON_PARSE_EXCEPTION onParseException = FetchEmitTuple.ON_PARSE_EXCEPTION.EMIT;
    private BasicContentHandlerFactory.HANDLER_TYPE handlerType = BasicContentHandlerFactory.HANDLER_TYPE.TEXT;
    private HandlerConfig.PARSE_MODE parseMode = HandlerConfig.PARSE_MODE.RMETA;
    private boolean throwOnWriteLimitReached = false;
    private int writeLimit = -1;
    private int maxEmbeddedResources = -1;
    private int added = 0;

    @Override // org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
    }

    protected abstract void enqueue() throws InterruptedException, TimeoutException, IOException;

    @Override // org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
    }

    public static PipesIterator build(Path path) throws IOException, TikaConfigException {
        InputStream inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
        try {
            PipesIterator pipesIterator = (PipesIterator) buildSingle("pipesIterator", PipesIterator.class, inputStreamNewInputStream);
            if (inputStreamNewInputStream != null) {
                inputStreamNewInputStream.close();
            }
            return pipesIterator;
        } catch (Throwable th) {
            if (inputStreamNewInputStream != null) {
                try {
                    inputStreamNewInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public String getFetcherName() {
        return this.fetcherName;
    }

    @Field
    public void setFetcherName(String str) {
        this.fetcherName = str;
    }

    public String getEmitterName() {
        return this.emitterName;
    }

    @Field
    public void setEmitterName(String str) {
        this.emitterName = str;
    }

    @Field
    public void setMaxWaitMs(long j) {
        this.maxWaitMs = j;
    }

    @Field
    public void setQueueSize(int i) {
        this.queueSize = i;
    }

    public FetchEmitTuple.ON_PARSE_EXCEPTION getOnParseException() {
        return this.onParseException;
    }

    @Field
    public void setOnParseException(String str) throws TikaConfigException {
        if ("skip".equalsIgnoreCase(str)) {
            setOnParseException(FetchEmitTuple.ON_PARSE_EXCEPTION.SKIP);
        } else if ("emit".equalsIgnoreCase(str)) {
            setOnParseException(FetchEmitTuple.ON_PARSE_EXCEPTION.EMIT);
        } else {
            throw new TikaConfigException("must be either 'skip' or 'emit': " + str);
        }
    }

    public void setOnParseException(FetchEmitTuple.ON_PARSE_EXCEPTION on_parse_exception) {
        this.onParseException = on_parse_exception;
    }

    @Field
    public void setHandlerType(String str) {
        this.handlerType = BasicContentHandlerFactory.parseHandlerType(str, BasicContentHandlerFactory.HANDLER_TYPE.TEXT);
    }

    @Field
    public void setWriteLimit(int i) {
        this.writeLimit = i;
    }

    @Field
    public void setThrowOnWriteLimitReached(boolean z) {
        this.throwOnWriteLimitReached = z;
    }

    @Field
    public void setMaxEmbeddedResources(int i) {
        this.maxEmbeddedResources = i;
    }

    @Field
    public void setParseMode(String str) {
        setParseMode(HandlerConfig.PARSE_MODE.parseMode(str));
    }

    public void setParseMode(HandlerConfig.PARSE_MODE parse_mode) {
        this.parseMode = parse_mode;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public Integer call() throws Exception {
        enqueue();
        tryToAdd(COMPLETED_SEMAPHORE);
        return Integer.valueOf(this.added);
    }

    protected HandlerConfig getHandlerConfig() {
        return new HandlerConfig(this.handlerType, this.parseMode, this.writeLimit, this.maxEmbeddedResources, this.throwOnWriteLimitReached);
    }

    protected void tryToAdd(FetchEmitTuple fetchEmitTuple) throws InterruptedException, TimeoutException {
        this.added++;
        if (!this.queue.offer(fetchEmitTuple, this.maxWaitMs, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException("timed out while offering");
        }
    }

    @Override // java.lang.Iterable
    public Iterator<FetchEmitTuple> iterator() {
        if (this.futureTask != null) {
            throw new IllegalStateException("Can't call iterator more than once!");
        }
        this.futureTask = new FutureTask<>(this);
        this.queue = new ArrayBlockingQueue<>(this.queueSize);
        new Thread(this.futureTask).start();
        return new TupleIterator();
    }

    private class TupleIterator implements Iterator<FetchEmitTuple> {
        FetchEmitTuple next;

        private TupleIterator() {
            this.next = null;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.next == null) {
                this.next = pollNext();
            }
            return this.next != PipesIterator.COMPLETED_SEMAPHORE;
        }

        @Override // java.util.Iterator
        public FetchEmitTuple next() {
            if (this.next == PipesIterator.COMPLETED_SEMAPHORE) {
                throw new IllegalStateException("don't call next() after hasNext() has returned false!");
            }
            FetchEmitTuple fetchEmitTuple = this.next;
            this.next = pollNext();
            return fetchEmitTuple;
        }

        private FetchEmitTuple pollNext() throws ExecutionException, TikaTimeoutException {
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                FetchEmitTuple fetchEmitTuple = null;
                while (fetchEmitTuple == null) {
                    if (jCurrentTimeMillis2 >= PipesIterator.this.maxWaitMs) {
                        break;
                    }
                    checkThreadOk();
                    fetchEmitTuple = (FetchEmitTuple) PipesIterator.this.queue.poll(100L, TimeUnit.MILLISECONDS);
                    jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                }
                if (fetchEmitTuple != null) {
                    return fetchEmitTuple;
                }
                throw new TikaTimeoutException("waited longer than " + PipesIterator.this.maxWaitMs + "ms for the next tuple");
            } catch (InterruptedException unused) {
                PipesIterator.LOGGER.warn("interrupted");
                return PipesIterator.COMPLETED_SEMAPHORE;
            }
        }

        private void checkThreadOk() throws ExecutionException, InterruptedException {
            if (PipesIterator.this.futureTask.isDone()) {
                try {
                    PipesIterator.this.futureTask.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e.getCause());
                }
            }
        }
    }
}
