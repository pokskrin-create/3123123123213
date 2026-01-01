package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
public enum zzlx {
    DOUBLE(0, 1, zzms.DOUBLE),
    FLOAT(1, 1, zzms.FLOAT),
    INT64(2, 1, zzms.LONG),
    UINT64(3, 1, zzms.LONG),
    INT32(4, 1, zzms.INT),
    FIXED64(5, 1, zzms.LONG),
    FIXED32(6, 1, zzms.INT),
    BOOL(7, 1, zzms.BOOLEAN),
    STRING(8, 1, zzms.STRING),
    MESSAGE(9, 1, zzms.MESSAGE),
    BYTES(10, 1, zzms.BYTE_STRING),
    UINT32(11, 1, zzms.INT),
    ENUM(12, 1, zzms.ENUM),
    SFIXED32(13, 1, zzms.INT),
    SFIXED64(14, 1, zzms.LONG),
    SINT32(15, 1, zzms.INT),
    SINT64(16, 1, zzms.LONG),
    GROUP(17, 1, zzms.MESSAGE),
    DOUBLE_LIST(18, 2, zzms.DOUBLE),
    FLOAT_LIST(19, 2, zzms.FLOAT),
    INT64_LIST(20, 2, zzms.LONG),
    UINT64_LIST(21, 2, zzms.LONG),
    INT32_LIST(22, 2, zzms.INT),
    FIXED64_LIST(23, 2, zzms.LONG),
    FIXED32_LIST(24, 2, zzms.INT),
    BOOL_LIST(25, 2, zzms.BOOLEAN),
    STRING_LIST(26, 2, zzms.STRING),
    MESSAGE_LIST(27, 2, zzms.MESSAGE),
    BYTES_LIST(28, 2, zzms.BYTE_STRING),
    UINT32_LIST(29, 2, zzms.INT),
    ENUM_LIST(30, 2, zzms.ENUM),
    SFIXED32_LIST(31, 2, zzms.INT),
    SFIXED64_LIST(32, 2, zzms.LONG),
    SINT32_LIST(33, 2, zzms.INT),
    SINT64_LIST(34, 2, zzms.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzms.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzms.FLOAT),
    INT64_LIST_PACKED(37, 3, zzms.LONG),
    UINT64_LIST_PACKED(38, 3, zzms.LONG),
    INT32_LIST_PACKED(39, 3, zzms.INT),
    FIXED64_LIST_PACKED(40, 3, zzms.LONG),
    FIXED32_LIST_PACKED(41, 3, zzms.INT),
    BOOL_LIST_PACKED(42, 3, zzms.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzms.INT),
    ENUM_LIST_PACKED(44, 3, zzms.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzms.INT),
    SFIXED64_LIST_PACKED(46, 3, zzms.LONG),
    SINT32_LIST_PACKED(47, 3, zzms.INT),
    SINT64_LIST_PACKED(48, 3, zzms.LONG),
    GROUP_LIST(49, 2, zzms.MESSAGE),
    MAP(50, 4, zzms.VOID);

    private static final zzlx[] zzaa;
    private final int zzZ;

    static {
        zzlx[] zzlxVarArrValues = values();
        zzaa = new zzlx[zzlxVarArrValues.length];
        for (zzlx zzlxVar : zzlxVarArrValues) {
            zzaa[zzlxVar.zzZ] = zzlxVar;
        }
    }

    zzlx(int i, int i2, zzms zzmsVar) {
        this.zzZ = i;
        int i3 = i2 - 1;
        if (i3 == 1 || i3 == 3) {
            zzmsVar.zza();
        }
        if (i2 == 1) {
            zzms zzmsVar2 = zzms.VOID;
            zzmsVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzZ;
    }
}
