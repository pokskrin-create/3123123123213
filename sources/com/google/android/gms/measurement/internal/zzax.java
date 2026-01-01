package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
final class zzax implements Runnable {
    final /* synthetic */ zzjg zza;
    final /* synthetic */ zzay zzb;

    zzax(zzay zzayVar, zzjg zzjgVar) {
        this.zza = zzjgVar;
        Objects.requireNonNull(zzayVar);
        this.zzb = zzayVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        zzjg zzjgVar = this.zza;
        zzjgVar.zzaU();
        if (zzae.zza()) {
            zzjgVar.zzaW().zzj(this);
            return;
        }
        zzay zzayVar = this.zzb;
        boolean zZzc = zzayVar.zzc();
        zzayVar.zze(0L);
        if (zZzc) {
            zzayVar.zza();
        }
    }
}
