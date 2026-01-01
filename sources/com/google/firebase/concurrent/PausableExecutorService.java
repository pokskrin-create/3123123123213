package com.google.firebase.concurrent;

import java.util.concurrent.ExecutorService;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* loaded from: classes4.dex */
public interface PausableExecutorService extends ExecutorService, PausableExecutor, AutoCloseable {
    @Override // java.lang.AutoCloseable
    /* synthetic */ default void close() throws InterruptedException {
        UByte$$ExternalSyntheticBackport0.m((ExecutorService) this);
    }
}
