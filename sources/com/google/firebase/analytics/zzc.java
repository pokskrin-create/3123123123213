package com.google.firebase.analytics;

import java.util.Objects;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement-api@@23.0.0 */
/* loaded from: classes4.dex */
final class zzc implements Callable {
    final /* synthetic */ FirebaseAnalytics zza;

    zzc(FirebaseAnalytics firebaseAnalytics) {
        Objects.requireNonNull(firebaseAnalytics);
        this.zza = firebaseAnalytics;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        return this.zza.zza().zzH();
    }
}
