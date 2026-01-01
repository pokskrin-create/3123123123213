package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzrm implements zzrl {
    public static final zzkm zza;

    static {
        zzkg zzkgVarZzb = new zzkg(zzkb.zza("com.google.android.gms.measurement")).zza().zzb();
        zza = zzkgVarZzb.zzd("measurement.tcf.consent_fix", true);
        zzkgVarZzb.zzd("measurement.tcf.client", true);
        zzkgVarZzb.zzd("measurement.tcf.empty_pref_fix", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzrl
    public final boolean zza() {
        return ((Boolean) zza.zzd()).booleanValue();
    }
}
