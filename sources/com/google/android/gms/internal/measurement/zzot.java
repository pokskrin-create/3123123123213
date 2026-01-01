package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
public enum zzot {
    DOUBLE(zzou.DOUBLE, 1),
    FLOAT(zzou.FLOAT, 5),
    INT64(zzou.LONG, 0),
    UINT64(zzou.LONG, 0),
    INT32(zzou.INT, 0),
    FIXED64(zzou.LONG, 1),
    FIXED32(zzou.INT, 5),
    BOOL(zzou.BOOLEAN, 0),
    STRING(zzou.STRING, 2),
    GROUP(zzou.MESSAGE, 3),
    MESSAGE(zzou.MESSAGE, 2),
    BYTES(zzou.BYTE_STRING, 2),
    UINT32(zzou.INT, 0),
    ENUM(zzou.ENUM, 0),
    SFIXED32(zzou.INT, 5),
    SFIXED64(zzou.LONG, 1),
    SINT32(zzou.INT, 0),
    SINT64(zzou.LONG, 0);

    private final zzou zzs;
    private final int zzt;

    zzot(zzou zzouVar, int i) {
        this.zzs = zzouVar;
        this.zzt = i;
    }

    public final zzou zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }
}
