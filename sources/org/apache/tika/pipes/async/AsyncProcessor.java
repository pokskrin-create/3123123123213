package org.apache.tika.pipes.async;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.tika.exception.TikaException;
import org.apache.tika.pipes.FetchEmitTuple;
import org.apache.tika.pipes.PipesClient;
import org.apache.tika.pipes.PipesException;
import org.apache.tika.pipes.PipesReporter;
import org.apache.tika.pipes.PipesResult;
import org.apache.tika.pipes.emitter.EmitData;
import org.apache.tika.pipes.emitter.EmitterManager;
import org.apache.tika.pipes.pipesiterator.PipesIterator;
import org.apache.tika.pipes.pipesiterator.TotalCountResult;
import org.apache.tika.pipes.pipesiterator.TotalCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class AsyncProcessor implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) AsyncProcessor.class);
    private static long MAX_OFFER_WAIT_MS = 120000;
    static final int PARSER_FUTURE_CODE = 1;
    static final int WATCHER_FUTURE_CODE = 3;
    private boolean addedEmitterSemaphores;
    private final AsyncConfig asyncConfig;
    private final ArrayBlockingQueue<EmitData> emitData;
    private final ExecutorCompletionService<Integer> executorCompletionService;
    private final ExecutorService executorService;
    private final ArrayBlockingQueue<FetchEmitTuple> fetchEmitTuples;
    boolean isShuttingDown;
    private volatile int numEmitterThreadsFinished;
    private volatile int numParserThreadsFinished;
    private final AtomicLong totalProcessed;

    public AsyncProcessor(Path path) throws TikaException, IOException {
        this(path, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AsyncProcessor(Path path, PipesIterator pipesIterator) throws Exception {
        AsyncProcessor asyncProcessor;
        Exception exc;
        this.totalProcessed = new AtomicLong(0L);
        this.numParserThreadsFinished = 0;
        this.numEmitterThreadsFinished = 0;
        this.addedEmitterSemaphores = false;
        this.isShuttingDown = false;
        AsyncConfig asyncConfigLoad = AsyncConfig.load(path);
        this.asyncConfig = asyncConfigLoad;
        this.fetchEmitTuples = new ArrayBlockingQueue<>(asyncConfigLoad.getQueueSize());
        this.emitData = new ArrayBlockingQueue<>(100);
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(asyncConfigLoad.getNumClients() + asyncConfigLoad.getNumEmitters() + 1);
        this.executorService = executorServiceNewFixedThreadPool;
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorServiceNewFixedThreadPool);
        this.executorCompletionService = executorCompletionService;
        try {
            if (!path.toAbsolutePath().equals(asyncConfigLoad.getTikaConfig().toAbsolutePath())) {
                try {
                    LOG.warn("TikaConfig for AsyncProcessor ({}) is different from TikaConfig for workers ({}). If this is intended, please ignore this warning.", path.toAbsolutePath(), asyncConfigLoad.getTikaConfig().toAbsolutePath());
                } catch (Exception e) {
                    exc = e;
                    asyncProcessor = this;
                    LOG.error("problem initializing AsyncProcessor", (Throwable) exc);
                    asyncProcessor.executorService.shutdownNow();
                    asyncProcessor.asyncConfig.getPipesReporter().error(exc);
                    throw exc;
                }
            }
            executorCompletionService.submit(new Callable() { // from class: org.apache.tika.pipes.async.AsyncProcessor$$ExternalSyntheticLambda1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.m2297lambda$new$0$orgapachetikapipesasyncAsyncProcessor();
                }
            });
            if (pipesIterator != 0 && (pipesIterator instanceof TotalCounter)) {
                LOG.debug("going to total counts");
                startCounter((TotalCounter) pipesIterator);
            }
            for (int i = 0; i < this.asyncConfig.getNumClients(); i++) {
                asyncProcessor = this;
                try {
                    this.executorCompletionService.submit(new FetchEmitWorker(this.asyncConfig, this.fetchEmitTuples, this.emitData));
                } catch (Exception e2) {
                    e = e2;
                    exc = e;
                    LOG.error("problem initializing AsyncProcessor", (Throwable) exc);
                    asyncProcessor.executorService.shutdownNow();
                    asyncProcessor.asyncConfig.getPipesReporter().error(exc);
                    throw exc;
                }
            }
            asyncProcessor = this;
            EmitterManager emitterManagerLoad = EmitterManager.load(asyncProcessor.asyncConfig.getTikaConfig());
            for (int i2 = 0; i2 < asyncProcessor.asyncConfig.getNumEmitters(); i2++) {
                asyncProcessor.executorCompletionService.submit(new AsyncEmitter(asyncProcessor.asyncConfig, asyncProcessor.emitData, emitterManagerLoad));
            }
        } catch (Exception e3) {
            e = e3;
            asyncProcessor = this;
        }
    }

    /* renamed from: lambda$new$0$org-apache-tika-pipes-async-AsyncProcessor, reason: not valid java name */
    /* synthetic */ Integer m2297lambda$new$0$orgapachetikapipesasyncAsyncProcessor() throws Exception {
        while (true) {
            try {
                Thread.sleep(500L);
                checkActive();
            } catch (InterruptedException unused) {
                return 3;
            }
        }
    }

    private void startCounter(final TotalCounter totalCounter) {
        Thread thread = new Thread(new Runnable() { // from class: org.apache.tika.pipes.async.AsyncProcessor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m2298lambda$startCounter$1$orgapachetikapipesasyncAsyncProcessor(totalCounter);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /* renamed from: lambda$startCounter$1$org-apache-tika-pipes-async-AsyncProcessor, reason: not valid java name */
    /* synthetic */ void m2298lambda$startCounter$1$orgapachetikapipesasyncAsyncProcessor(TotalCounter totalCounter) throws InterruptedException {
        totalCounter.startTotalCount();
        PipesReporter pipesReporter = this.asyncConfig.getPipesReporter();
        TotalCountResult.STATUS status = totalCounter.getTotalCount().getStatus();
        while (status == TotalCountResult.STATUS.NOT_COMPLETED) {
            try {
                Thread.sleep(500L);
                TotalCountResult totalCount = totalCounter.getTotalCount();
                LOG.trace("counter total  {} {} ", totalCount.getStatus(), Long.valueOf(totalCount.getTotalCount()));
                pipesReporter.report(totalCount);
                status = totalCount.getStatus();
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    public synchronized boolean offer(List<FetchEmitTuple> list, long j) throws PipesException, InterruptedException {
        if (this.isShuttingDown) {
            throw new IllegalStateException("Can't call offer after calling close() or shutdownNow()");
        }
        if (list.size() > this.asyncConfig.getQueueSize()) {
            throw new OfferLargerThanQueueSize(list.size(), this.asyncConfig.getQueueSize());
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (long jCurrentTimeMillis2 = System.currentTimeMillis(); jCurrentTimeMillis2 - jCurrentTimeMillis < j; jCurrentTimeMillis2 = System.currentTimeMillis()) {
            if (this.fetchEmitTuples.remainingCapacity() > list.size()) {
                try {
                    this.fetchEmitTuples.addAll(list);
                    return true;
                } catch (IllegalStateException e) {
                    LOG.debug("couldn't add full list", (Throwable) e);
                }
            }
            Thread.sleep(100L);
        }
        return false;
    }

    public int getCapacity() {
        return this.fetchEmitTuples.remainingCapacity();
    }

    public synchronized boolean offer(FetchEmitTuple fetchEmitTuple, long j) throws PipesException, InterruptedException {
        if (this.fetchEmitTuples == null) {
            throw new IllegalStateException("queue hasn't been initialized yet.");
        }
        if (this.isShuttingDown) {
            throw new IllegalStateException("Can't call offer after calling close() or shutdownNow()");
        }
        checkActive();
        return this.fetchEmitTuples.offer(fetchEmitTuple, j, TimeUnit.MILLISECONDS);
    }

    public void finished() throws InterruptedException {
        for (int i = 0; i < this.asyncConfig.getNumClients(); i++) {
            if (!this.fetchEmitTuples.offer(PipesIterator.COMPLETED_SEMAPHORE, MAX_OFFER_WAIT_MS, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Couldn't offer completed semaphore within " + MAX_OFFER_WAIT_MS + " ms");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean checkActive() throws java.lang.InterruptedException {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.pipes.async.AsyncProcessor.checkActive():boolean");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.executorService.shutdownNow();
        this.asyncConfig.getPipesReporter().close();
    }

    public long getTotalProcessed() {
        return this.totalProcessed.get();
    }

    private class FetchEmitWorker implements Callable<Integer> {
        private final AsyncConfig asyncConfig;
        private final ArrayBlockingQueue<EmitData> emitDataQueue;
        private final ArrayBlockingQueue<FetchEmitTuple> fetchEmitTuples;

        private FetchEmitWorker(AsyncConfig asyncConfig, ArrayBlockingQueue<FetchEmitTuple> arrayBlockingQueue, ArrayBlockingQueue<EmitData> arrayBlockingQueue2) {
            this.asyncConfig = asyncConfig;
            this.fetchEmitTuples = arrayBlockingQueue;
            this.emitDataQueue = arrayBlockingQueue2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Integer call() throws Exception {
            PipesResult pipesResultProcess;
            PipesClient pipesClient = new PipesClient(this.asyncConfig);
            while (true) {
                try {
                    FetchEmitTuple fetchEmitTuplePoll = this.fetchEmitTuples.poll(1L, TimeUnit.SECONDS);
                    if (fetchEmitTuplePoll == null) {
                        if (AsyncProcessor.LOG.isTraceEnabled()) {
                            AsyncProcessor.LOG.trace("null fetch emit tuple");
                        }
                    } else {
                        if (fetchEmitTuplePoll == PipesIterator.COMPLETED_SEMAPHORE) {
                            if (AsyncProcessor.LOG.isTraceEnabled()) {
                                AsyncProcessor.LOG.trace("hit completed semaphore");
                            }
                            pipesClient.close();
                            return 1;
                        }
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        try {
                            pipesResultProcess = pipesClient.process(fetchEmitTuplePoll);
                        } catch (IOException e) {
                            AsyncProcessor.LOG.warn("pipesClient crash", (Throwable) e);
                            pipesResultProcess = PipesResult.UNSPECIFIED_CRASH;
                        }
                        if (AsyncProcessor.LOG.isTraceEnabled()) {
                            AsyncProcessor.LOG.trace("timer -- pipes client process: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                        }
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        if (shouldEmit(pipesResultProcess)) {
                            AsyncProcessor.LOG.trace("adding result to emitter queue: " + pipesResultProcess.getEmitData());
                            if (!this.emitDataQueue.offer(pipesResultProcess.getEmitData(), AsyncProcessor.MAX_OFFER_WAIT_MS, TimeUnit.MILLISECONDS)) {
                                throw new RuntimeException("Couldn't offer emit data to queue within " + AsyncProcessor.MAX_OFFER_WAIT_MS + " ms");
                            }
                        }
                        if (AsyncProcessor.LOG.isTraceEnabled()) {
                            AsyncProcessor.LOG.trace("timer -- offered: {} ms", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis2));
                        }
                        this.asyncConfig.getPipesReporter().report(fetchEmitTuplePoll, pipesResultProcess, System.currentTimeMillis() - jCurrentTimeMillis);
                        AsyncProcessor.this.totalProcessed.incrementAndGet();
                    }
                } catch (Throwable th) {
                    try {
                        pipesClient.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }

        private boolean shouldEmit(PipesResult pipesResult) {
            if (pipesResult.getStatus() == PipesResult.STATUS.PARSE_SUCCESS || pipesResult.getStatus() == PipesResult.STATUS.PARSE_SUCCESS_WITH_EXCEPTION) {
                return true;
            }
            return pipesResult.isIntermediate() && this.asyncConfig.isEmitIntermediateResults();
        }
    }
}
