package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes4.dex */
final class zzad extends zzos {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    zzad(zzpg zzpgVar) {
        super(zzpgVar);
    }

    private final zzy zzc(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzy) this.zzc.get(num);
        }
        zzy zzyVar = new zzy(this, this.zza, null);
        this.zzc.put(num, zzyVar);
        return zzyVar;
    }

    private final boolean zzd(int i, int i2) {
        zzy zzyVar = (zzy) this.zzc.get(Integer.valueOf(i));
        if (zzyVar == null) {
            return false;
        }
        return zzyVar.zzc().get(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:406:0x0964, code lost:
    
        r0 = r13.zzaV().zze();
        r2 = com.google.android.gms.measurement.internal.zzgu.zzl(r34.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x0976, code lost:
    
        if (r12.zza() == false) goto L409;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x0978, code lost:
    
        r3 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x0981, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0982, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r2, java.lang.String.valueOf(r3));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02ba A[PHI: r0 r6
  0x02ba: PHI (r0v72 java.util.Map) = (r0v57 java.util.Map), (r0v74 java.util.Map), (r0v51 java.util.Map) binds: [B:129:0x02e0, B:120:0x02c0, B:117:0x02b8] A[DONT_GENERATE, DONT_INLINE]
  0x02ba: PHI (r6v10 android.database.Cursor) = (r6v4 android.database.Cursor), (r6v11 android.database.Cursor), (r6v11 android.database.Cursor) binds: [B:129:0x02e0, B:120:0x02c0, B:117:0x02b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x084c A[PHI: r0 r13 r36
  0x084c: PHI (r0v120 java.util.Map) = (r0v122 java.util.Map), (r0v128 java.util.Map) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]
  0x084c: PHI (r13v4 android.database.Cursor) = (r13v5 android.database.Cursor), (r13v6 android.database.Cursor) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]
  0x084c: PHI (r36v4 java.util.Iterator) = (r36v5 java.util.Iterator), (r36v9 java.util.Iterator) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0893  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a47  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014b A[PHI: r0 r5
  0x014b: PHI (r0v203 java.util.Map) = (r0v202 java.util.Map), (r0v206 java.util.Map) binds: [B:53:0x016b, B:42:0x0149] A[DONT_GENERATE, DONT_INLINE]
  0x014b: PHI (r5v44 android.database.Cursor) = (r5v43 android.database.Cursor), (r5v45 android.database.Cursor) binds: [B:53:0x016b, B:42:0x0149] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01a6 A[Catch: SQLiteException -> 0x021b, all -> 0x0a4d, TRY_LEAVE, TryCatch #10 {SQLiteException -> 0x021b, blocks: (B:61:0x01a0, B:63:0x01a6, B:67:0x01b6, B:68:0x01bb, B:69:0x01c5, B:70:0x01d5, B:72:0x01e4), top: B:450:0x01a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b6 A[Catch: SQLiteException -> 0x021b, all -> 0x0a4d, TRY_ENTER, TryCatch #10 {SQLiteException -> 0x021b, blocks: (B:61:0x01a0, B:63:0x01a6, B:67:0x01b6, B:68:0x01bb, B:69:0x01c5, B:70:0x01d5, B:72:0x01e4), top: B:450:0x01a0 }] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.lang.Object, java.util.Map] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v40 */
    /* JADX WARN: Type inference failed for: r5v42, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v49 */
    /* JADX WARN: Type inference failed for: r5v50 */
    /* JADX WARN: Type inference failed for: r5v51 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.util.List zzb(java.lang.String r35, java.util.List r36, java.util.List r37, java.lang.Long r38, java.lang.Long r39, boolean r40) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2645
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzad.zzb(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long, boolean):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzos
    protected final boolean zzbb() {
        return false;
    }
}
