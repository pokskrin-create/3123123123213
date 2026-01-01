package com.google.android.gms.measurement.internal;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
final class zzob {
    final /* synthetic */ zzoc zza;

    zzob(zzoc zzocVar) {
        Objects.requireNonNull(zzocVar);
        this.zza = zzocVar;
    }

    final void zza() {
        zzoc zzocVar = this.zza;
        zzocVar.zzg();
        zzic zzicVar = zzocVar.zzu;
        if (zzicVar.zzd().zzp(zzicVar.zzaZ().currentTimeMillis())) {
            zzicVar.zzd().zzg.zzb(true);
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance == 100) {
                zzicVar.zzaV().zzk().zza("Detected application was in foreground");
                zzc(zzicVar.zzaZ().currentTimeMillis(), false);
            }
        }
    }

    final void zzb(long j, boolean z) {
        zzoc zzocVar = this.zza;
        zzocVar.zzg();
        zzocVar.zzj();
        zzic zzicVar = zzocVar.zzu;
        if (zzicVar.zzd().zzp(j)) {
            zzicVar.zzd().zzg.zzb(true);
            zzocVar.zzu.zzv().zzi();
        }
        zzicVar.zzd().zzk.zzb(j);
        if (zzicVar.zzd().zzg.zza()) {
            zzc(j, z);
        }
    }

    final void zzc(long j, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzoc zzocVar = this.zza;
        zzocVar.zzg();
        if (zzocVar.zzu.zzB()) {
            zzic zzicVar = zzocVar.zzu;
            zzicVar.zzd().zzk.zzb(j);
            zzicVar.zzaV().zzk().zzb("Session started, time", Long.valueOf(zzicVar.zzaZ().elapsedRealtime()));
            long j2 = j / 1000;
            zzic zzicVar2 = zzocVar.zzu;
            Long lValueOf = Long.valueOf(j2);
            zzicVar2.zzj().zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_sid", lValueOf, j);
            zzhe zzheVar = zzicVar.zzd().zzl;
            lValueOf.getClass();
            zzheVar.zzb(j2);
            zzicVar.zzd().zzg.zzb(false);
            Bundle bundle = new Bundle();
            lValueOf.getClass();
            bundle.putLong("_sid", j2);
            zzicVar2.zzj().zzG(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_s", j, bundle);
            String strZza = zzicVar.zzd().zzq.zza();
            if (TextUtils.isEmpty(strZza)) {
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("_ffr", strZza);
            zzicVar2.zzj().zzG(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ssr", j, bundle2);
        }
    }
}
