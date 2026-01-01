package org.apache.tika.sax;

/* loaded from: classes4.dex */
public interface WriteLimiter {
    int getWriteLimit();

    boolean isThrowOnWriteLimitReached();
}
