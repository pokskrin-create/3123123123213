package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes4.dex */
final class zzoz implements Callable {
    final /* synthetic */ zzr zza;
    final /* synthetic */ zzpg zzb;

    zzoz(zzpg zzpgVar, zzr zzrVar) {
        this.zza = zzrVar;
        Objects.requireNonNull(zzpgVar);
        this.zzb = zzpgVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzr zzrVar = this.zza;
        String str = (String) Preconditions.checkNotNull(zzrVar.zza);
        zzpg zzpgVar = this.zzb;
        zzjl zzjlVarZzB = zzpgVar.zzB(str);
        zzjk zzjkVar = zzjk.ANALYTICS_STORAGE;
        if (zzjlVarZzB.zzo(zzjkVar) && zzjl.zzf(zzrVar.zzs, 100).zzo(zzjkVar)) {
            return zzpgVar.zzao(zzrVar).zzd();
        }
        zzpgVar.zzaV().zzk().zza("Analytics storage consent denied. Returning null app instance id");
        return null;
    }
}
