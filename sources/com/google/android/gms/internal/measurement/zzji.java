package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzji {
    private static zzjh zza;

    public static synchronized void zza(zzjh zzjhVar) {
        if (zza != null) {
            throw new IllegalStateException("init() already called");
        }
        zza = zzjhVar;
    }

    public static synchronized zzjh zzb() {
        if (zza == null) {
            zza(new zzjl());
        }
        return zza;
    }
}
