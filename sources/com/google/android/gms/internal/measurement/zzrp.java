package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzrp implements zzro {
    public static final zzkm zza = new zzkg(zzkb.zza("com.google.android.gms.measurement")).zza().zzb().zzd("measurement.integration.disable_firebase_instance_id", false);

    @Override // com.google.android.gms.internal.measurement.zzro
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzro
    public final boolean zzb() {
        return ((Boolean) zza.zzd()).booleanValue();
    }
}
