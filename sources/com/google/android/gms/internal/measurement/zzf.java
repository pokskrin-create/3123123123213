package com.google.android.gms.internal.measurement;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
public final class zzf {
    final zzaw zza;
    final zzg zzb;
    final zzg zzc;
    final zzj zzd;

    public zzf() {
        zzaw zzawVar = new zzaw();
        this.zza = zzawVar;
        zzg zzgVar = new zzg(null, zzawVar);
        this.zzc = zzgVar;
        this.zzb = zzgVar.zzc();
        zzj zzjVar = new zzj();
        this.zzd = zzjVar;
        zzgVar.zze("require", new zzw(zzjVar));
        zzjVar.zza("internal.platform", zze.zza);
        zzgVar.zze("runtime.counter", new zzah(Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)));
    }

    public final zzao zza(zzg zzgVar, zzje... zzjeVarArr) {
        zzao zzaoVarZzb = zzao.zzf;
        for (zzje zzjeVar : zzjeVarArr) {
            zzaoVarZzb = zzi.zzb(zzjeVar);
            zzh.zzl(this.zzc);
            if ((zzaoVarZzb instanceof zzap) || (zzaoVarZzb instanceof zzan)) {
                zzaoVarZzb = this.zza.zzb(zzgVar, zzaoVarZzb);
            }
        }
        return zzaoVarZzb;
    }
}
