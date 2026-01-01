package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
public final class zziy extends zzmf implements zznn {
    private static final zziy zzf;
    private int zzb;
    private String zzd = "";
    private zzmo zze = zzcv();

    static {
        zziy zziyVar = new zziy();
        zzf = zziyVar;
        zzmf.zzcp(zziy.class, zziyVar);
    }

    private zziy() {
    }

    public final String zza() {
        return this.zzd;
    }

    public final List zzb() {
        return this.zze;
    }

    @Override // com.google.android.gms.internal.measurement.zzmf
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzcq(zzf, "\u0004\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b", new Object[]{"zzb", "zzd", "zze", zzje.class});
        }
        if (i2 == 3) {
            return new zziy();
        }
        byte[] bArr = null;
        if (i2 == 4) {
            return new zzix(bArr);
        }
        if (i2 == 5) {
            return zzf;
        }
        throw null;
    }
}
