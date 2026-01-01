package com.google.common.cache;

@ElementTypesAreNonnullByDefault
/* loaded from: classes4.dex */
public interface RemovalListener<K, V> {
    void onRemoval(RemovalNotification<K, V> removalNotification);
}
