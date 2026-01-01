package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;
import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
final class zzjj extends ContentObserver {
    final /* synthetic */ zzjl zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjj(zzjl zzjlVar, Handler handler) {
        super(null);
        Objects.requireNonNull(zzjlVar);
        this.zza = zzjlVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zza.zzb().set(true);
    }
}
