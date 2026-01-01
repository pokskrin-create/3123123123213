package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
final class zzmy implements Runnable {
    final /* synthetic */ zzgb zza;
    final /* synthetic */ zznf zzb;

    zzmy(zznf zznfVar, zzgb zzgbVar) {
        this.zza = zzgbVar;
        Objects.requireNonNull(zznfVar);
        this.zzb = zznfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zznf zznfVar = this.zzb;
        synchronized (zznfVar) {
            zznfVar.zzd(false);
            zznl zznlVar = zznfVar.zza;
            if (!zznlVar.zzh()) {
                zznlVar.zzu.zzaV().zzk().zza("Connected to service");
                zznlVar.zzL(this.zza);
            }
        }
    }
}
