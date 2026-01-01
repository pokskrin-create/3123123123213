package org.apache.tika.concurrent;

import java.util.concurrent.ExecutorService;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* loaded from: classes4.dex */
public interface ConfigurableThreadPoolExecutor extends ExecutorService, AutoCloseable {
    @Override // java.lang.AutoCloseable
    /* synthetic */ default void close() throws InterruptedException {
        UByte$$ExternalSyntheticBackport0.m((ExecutorService) this);
    }

    void setCorePoolSize(int i);

    void setMaximumPoolSize(int i);
}
