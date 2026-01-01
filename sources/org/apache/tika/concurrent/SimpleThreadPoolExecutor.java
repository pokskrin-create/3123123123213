package org.apache.tika.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* loaded from: classes4.dex */
public class SimpleThreadPoolExecutor extends ThreadPoolExecutor implements ConfigurableThreadPoolExecutor, AutoCloseable {
    @Override // org.apache.tika.concurrent.ConfigurableThreadPoolExecutor, java.lang.AutoCloseable
    public /* synthetic */ void close() throws InterruptedException {
        UByte$$ExternalSyntheticBackport0.m((ExecutorService) this);
    }

    public SimpleThreadPoolExecutor() {
        super(1, 2, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: org.apache.tika.concurrent.SimpleThreadPoolExecutor$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return SimpleThreadPoolExecutor.lambda$new$0(runnable);
            }
        });
    }

    static /* synthetic */ Thread lambda$new$0(Runnable runnable) {
        return new Thread(runnable, "Tika Executor Thread");
    }
}
