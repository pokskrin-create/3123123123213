package com.google.android.gms.internal.common;

/* compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* loaded from: classes.dex */
public class zzj {
    private final Class zza;
    private final Object zzb;

    private zzj(Class cls, Object obj) {
        this.zza = cls;
        this.zzb = obj;
    }

    public static zzj zzb(Class cls, Object obj) {
        return new zzj(cls, obj);
    }

    public final Class zzc() {
        return this.zza;
    }

    public final Object zzd() {
        return this.zzb;
    }
}
