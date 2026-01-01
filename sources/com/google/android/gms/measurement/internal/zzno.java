package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes4.dex */
final class zzno implements Runnable {
    final /* synthetic */ zzpg zza;
    final /* synthetic */ Runnable zzb;

    zzno(zznt zzntVar, zzpg zzpgVar, Runnable runnable) {
        this.zza = zzpgVar;
        this.zzb = runnable;
        Objects.requireNonNull(zzntVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzpg zzpgVar = this.zza;
        zzpgVar.zzZ();
        zzpgVar.zzY(this.zzb);
        zzpgVar.zzM();
    }
}
