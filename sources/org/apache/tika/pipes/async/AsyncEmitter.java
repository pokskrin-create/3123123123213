package org.apache.tika.pipes.async;

import j$.time.Instant;
import j$.time.temporal.ChronoUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.tika.pipes.async.AsyncEmitter;
import org.apache.tika.pipes.emitter.EmitData;
import org.apache.tika.pipes.emitter.Emitter;
import org.apache.tika.pipes.emitter.EmitterManager;
import org.apache.tika.pipes.emitter.TikaEmitterException;
import org.apache.tika.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class AsyncEmitter implements Callable<Integer> {
    static final int EMITTER_FUTURE_CODE = 2;
    static final EmitData EMIT_DATA_STOP_SEMAPHORE = new EmitData(null, null, null);
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) AsyncEmitter.class);
    private final AsyncConfig asyncConfig;
    private final ArrayBlockingQueue<EmitData> emitDataQueue;
    private final EmitterManager emitterManager;
    Instant lastEmitted = Instant.now();

    public AsyncEmitter(AsyncConfig asyncConfig, ArrayBlockingQueue<EmitData> arrayBlockingQueue, EmitterManager emitterManager) {
        this.asyncConfig = asyncConfig;
        this.emitDataQueue = arrayBlockingQueue;
        this.emitterManager = emitterManager;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public Integer call() throws Exception {
        EmitDataCache emitDataCache = new EmitDataCache(this.asyncConfig.getEmitMaxEstimatedBytes());
        while (true) {
            EmitData emitDataPoll = this.emitDataQueue.poll(500L, TimeUnit.MILLISECONDS);
            if (emitDataPoll == EMIT_DATA_STOP_SEMAPHORE) {
                emitDataCache.emitAll();
                return 2;
            }
            if (emitDataPoll != null) {
                emitDataCache.add(emitDataPoll);
            } else {
                LOG.trace("Nothing on the async queue");
            }
            Logger logger = LOG;
            logger.debug("cache size: ({}) bytes and extract count: {}", Long.valueOf(emitDataCache.estimatedSize), Integer.valueOf(emitDataCache.size));
            long jBetween = ChronoUnit.MILLIS.between(this.lastEmitted, Instant.now());
            if (jBetween > this.asyncConfig.getEmitWithinMillis()) {
                logger.debug("{} elapsed > {}, going to emitAll", Long.valueOf(jBetween), Long.valueOf(this.asyncConfig.getEmitWithinMillis()));
                emitDataCache.emitAll();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EmitDataCache {
        private final long maxBytes;
        long estimatedSize = 0;
        int size = 0;
        Map<String, List<EmitData>> map = new HashMap();

        public EmitDataCache(long j) {
            this.maxBytes = j;
        }

        void updateEstimatedSize(long j) {
            this.estimatedSize += j;
        }

        void add(EmitData emitData) {
            this.size++;
            long estimatedSizeBytes = emitData.getEstimatedSizeBytes();
            if (this.estimatedSize + estimatedSizeBytes > this.maxBytes) {
                AsyncEmitter.LOG.debug("estimated size ({}) > maxBytes({}), going to emitAll", Long.valueOf(this.estimatedSize + estimatedSizeBytes), Long.valueOf(this.maxBytes));
                emitAll();
            }
            List<EmitData> listComputeIfAbsent = this.map.computeIfAbsent(emitData.getEmitKey().getEmitterName(), new Function() { // from class: org.apache.tika.pipes.async.AsyncEmitter$EmitDataCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AsyncEmitter.EmitDataCache.lambda$add$0((String) obj);
                }
            });
            updateEstimatedSize(estimatedSizeBytes);
            listComputeIfAbsent.add(emitData);
        }

        static /* synthetic */ List lambda$add$0(String str) {
            return new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void emitAll() {
            AsyncEmitter.LOG.debug("about to emit {} files, {} estimated bytes", Integer.valueOf(this.size), Long.valueOf(this.estimatedSize));
            int size = 0;
            for (Map.Entry<String, List<EmitData>> entry : this.map.entrySet()) {
                tryToEmit(AsyncEmitter.this.emitterManager.getEmitter(entry.getKey()), entry.getValue());
                size += entry.getValue().size();
            }
            AsyncEmitter.LOG.debug("emitted: {} files", Integer.valueOf(size));
            this.estimatedSize = 0L;
            this.size = 0;
            this.map.clear();
            AsyncEmitter.this.lastEmitted = Instant.now();
        }

        private void tryToEmit(Emitter emitter, List<EmitData> list) {
            try {
                emitter.emit(list);
            } catch (IOException | TikaEmitterException e) {
                AsyncEmitter.LOG.warn("emitter class ({}): {}", emitter.getClass(), ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
