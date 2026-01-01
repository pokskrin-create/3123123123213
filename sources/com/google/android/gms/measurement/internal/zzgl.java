package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
public final class zzgl extends zzg {
    private static final String[] zza = {"app_version", "ALTER TABLE messages ADD COLUMN app_version TEXT;", "app_version_int", "ALTER TABLE messages ADD COLUMN app_version_int INTEGER;"};
    private final zzgj zzb;
    private boolean zzc;

    zzgl(zzic zzicVar) {
        super(zzicVar);
        Context contextZzaY = this.zzu.zzaY();
        this.zzu.zzc();
        this.zzb = new zzgj(this, contextZzaY, "google_app_measurement_local.db");
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009e A[Catch: SQLiteException -> 0x0083, SQLiteDatabaseLockedException -> 0x008a, SQLiteFullException -> 0x008e, all -> 0x015b, TRY_ENTER, TryCatch #16 {all -> 0x015b, blocks: (B:23:0x0078, B:25:0x007e, B:36:0x009e, B:38:0x00c2, B:40:0x00cc, B:42:0x00d4, B:48:0x00e9, B:69:0x0122, B:71:0x0128, B:72:0x012b, B:89:0x0162, B:79:0x014b), top: B:106:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0122 A[Catch: all -> 0x015b, TRY_ENTER, TryCatch #16 {all -> 0x015b, blocks: (B:23:0x0078, B:25:0x007e, B:36:0x009e, B:38:0x00c2, B:40:0x00cc, B:42:0x00d4, B:48:0x00e9, B:69:0x0122, B:71:0x0128, B:72:0x012b, B:89:0x0162, B:79:0x014b), top: B:106:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0157 A[PHI: r8 r10 r17
  0x0157: PHI (r8v5 int) = (r8v3 int), (r8v3 int), (r8v6 int) binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]
  0x0157: PHI (r10v7 android.database.sqlite.SQLiteDatabase) = 
  (r10v5 android.database.sqlite.SQLiteDatabase)
  (r10v6 android.database.sqlite.SQLiteDatabase)
  (r10v8 android.database.sqlite.SQLiteDatabase)
 binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]
  0x0157: PHI (r17v7 boolean) = (r17v4 boolean), (r17v5 boolean), (r17v8 boolean) binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0176  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzs(int r19, byte[] r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzs(int, byte[]):boolean");
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return false;
    }

    public final void zzh() {
        int iDelete;
        zzg();
        try {
            SQLiteDatabase sQLiteDatabaseZzp = zzp();
            if (sQLiteDatabaseZzp == null || (iDelete = sQLiteDatabaseZzp.delete("messages", null, null)) <= 0) {
                return;
            }
            this.zzu.zzaV().zzk().zzb("Reset local analytics data. records", Integer.valueOf(iDelete));
        } catch (SQLiteException e) {
            this.zzu.zzaV().zzb().zzb("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zzi(zzbg zzbgVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzbh.zza(zzbgVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(0, bArrMarshall);
        }
        this.zzu.zzaV().zzc().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zzj(zzpl zzplVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzpm.zza(zzplVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(1, bArrMarshall);
        }
        this.zzu.zzaV().zzc().zza("User property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzk(zzah zzahVar) {
        zzic zzicVar = this.zzu;
        byte[] bArrZzae = zzicVar.zzk().zzae(zzahVar);
        if (bArrZzae.length <= 131072) {
            return zzs(2, bArrZzae);
        }
        zzicVar.zzaV().zzc().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzl(zzbe zzbeVar) {
        zzic zzicVar = this.zzu;
        byte[] bArrZzae = zzicVar.zzk().zzae(zzbeVar);
        if (bArrZzae == null) {
            zzicVar.zzaV().zzc().zza("Null default event parameters; not writing to database");
            return false;
        }
        if (bArrZzae.length <= 131072) {
            return zzs(4, bArrZzae);
        }
        zzicVar.zzaV().zzc().zza("Default event parameters too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01d0 A[Catch: SQLiteException -> 0x0224, SQLiteFullException -> 0x0226, SQLiteDatabaseLockedException -> 0x026e, all -> 0x0360, TryCatch #0 {all -> 0x0360, blocks: (B:32:0x00b8, B:34:0x00be, B:36:0x00d1, B:38:0x00d7, B:43:0x00f0, B:48:0x0108, B:50:0x010d, B:197:0x0350, B:186:0x0325, B:188:0x032b, B:189:0x032e, B:208:0x036c, B:61:0x0137, B:62:0x013a, B:58:0x012d, B:73:0x0156, B:75:0x016a, B:82:0x0185, B:83:0x018e, B:84:0x0191, B:80:0x017f, B:91:0x0199, B:95:0x01af, B:105:0x01d0, B:106:0x01da, B:107:0x01dd, B:103:0x01ca, B:110:0x01e3, B:114:0x01f7, B:124:0x0216, B:126:0x0220, B:127:0x0223, B:122:0x0210, B:134:0x022c, B:135:0x023c, B:145:0x027b, B:147:0x0298, B:148:0x02a7), top: B:230:0x0350 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0216 A[Catch: SQLiteException -> 0x02b6, SQLiteFullException -> 0x02b9, SQLiteDatabaseLockedException -> 0x034f, all -> 0x0360, TryCatch #0 {all -> 0x0360, blocks: (B:32:0x00b8, B:34:0x00be, B:36:0x00d1, B:38:0x00d7, B:43:0x00f0, B:48:0x0108, B:50:0x010d, B:197:0x0350, B:186:0x0325, B:188:0x032b, B:189:0x032e, B:208:0x036c, B:61:0x0137, B:62:0x013a, B:58:0x012d, B:73:0x0156, B:75:0x016a, B:82:0x0185, B:83:0x018e, B:84:0x0191, B:80:0x017f, B:91:0x0199, B:95:0x01af, B:105:0x01d0, B:106:0x01da, B:107:0x01dd, B:103:0x01ca, B:110:0x01e3, B:114:0x01f7, B:124:0x0216, B:126:0x0220, B:127:0x0223, B:122:0x0210, B:134:0x022c, B:135:0x023c, B:145:0x027b, B:147:0x0298, B:148:0x02a7), top: B:230:0x0350 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0325 A[Catch: all -> 0x0360, TRY_ENTER, TryCatch #0 {all -> 0x0360, blocks: (B:32:0x00b8, B:34:0x00be, B:36:0x00d1, B:38:0x00d7, B:43:0x00f0, B:48:0x0108, B:50:0x010d, B:197:0x0350, B:186:0x0325, B:188:0x032b, B:189:0x032e, B:208:0x036c, B:61:0x0137, B:62:0x013a, B:58:0x012d, B:73:0x0156, B:75:0x016a, B:82:0x0185, B:83:0x018e, B:84:0x0191, B:80:0x017f, B:91:0x0199, B:95:0x01af, B:105:0x01d0, B:106:0x01da, B:107:0x01dd, B:103:0x01ca, B:110:0x01e3, B:114:0x01f7, B:124:0x0216, B:126:0x0220, B:127:0x0223, B:122:0x0210, B:134:0x022c, B:135:0x023c, B:145:0x027b, B:147:0x0298, B:148:0x02a7), top: B:230:0x0350 }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x035c A[PHI: r6 r11 r13 r17 r19 r21
  0x035c: PHI (r6v13 int) = (r6v6 int), (r6v8 int), (r6v14 int) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r11v3 int) = (r11v1 int), (r11v1 int), (r11v4 int) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r13v9 android.database.sqlite.SQLiteDatabase) = 
  (r13v4 android.database.sqlite.SQLiteDatabase)
  (r13v6 android.database.sqlite.SQLiteDatabase)
  (r13v10 android.database.sqlite.SQLiteDatabase)
 binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r17v8 java.lang.String) = (r17v3 java.lang.String), (r17v5 java.lang.String), (r17v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r19v8 java.lang.String) = (r19v3 java.lang.String), (r19v5 java.lang.String), (r19v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r21v8 java.lang.String) = (r21v3 java.lang.String), (r21v5 java.lang.String), (r21v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0399  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.String, java.util.List] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zzm(int r30) {
        /*
            Method dump skipped, instructions count: 944
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzm(int):java.util.List");
    }

    public final boolean zzn() {
        return zzs(3, new byte[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0068 A[PHI: r4
  0x0068: PHI (r4v4 int) = (r4v2 int), (r4v1 int), (r4v1 int) binds: [B:28:0x0066, B:25:0x005f, B:32:0x007c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzo() {
        /*
            r10 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r10.zzg()
            boolean r1 = r10.zzc
            r2 = 0
            if (r1 == 0) goto Lc
            goto L97
        Lc:
            boolean r1 = r10.zzq()
            if (r1 == 0) goto L97
            r1 = 5
            r4 = r1
            r3 = r2
        L15:
            if (r3 >= r1) goto L88
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r10.zzp()     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            if (r5 != 0) goto L23
            r10.zzc = r6     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            goto L97
        L23:
            r5.beginTransaction()     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            java.lang.String r7 = "messages"
            java.lang.String r8 = "type == ?"
            r9 = 3
            java.lang.String r9 = java.lang.Integer.toString(r9)     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            java.lang.String[] r9 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            r5.delete(r7, r8, r9)     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            r5.endTransaction()     // Catch: java.lang.Throwable -> L40 android.database.sqlite.SQLiteException -> L42 android.database.sqlite.SQLiteDatabaseLockedException -> L60 android.database.sqlite.SQLiteFullException -> L6c
            r5.close()
            return r6
        L40:
            r0 = move-exception
            goto L82
        L42:
            r7 = move-exception
            if (r5 == 0) goto L4e
            boolean r8 = r5.inTransaction()     // Catch: java.lang.Throwable -> L40
            if (r8 == 0) goto L4e
            r5.endTransaction()     // Catch: java.lang.Throwable -> L40
        L4e:
            com.google.android.gms.measurement.internal.zzic r8 = r10.zzu     // Catch: java.lang.Throwable -> L40
            com.google.android.gms.measurement.internal.zzgu r8 = r8.zzaV()     // Catch: java.lang.Throwable -> L40
            com.google.android.gms.measurement.internal.zzgs r8 = r8.zzb()     // Catch: java.lang.Throwable -> L40
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L40
            r10.zzc = r6     // Catch: java.lang.Throwable -> L40
            if (r5 == 0) goto L7f
            goto L68
        L60:
            long r6 = (long) r4     // Catch: java.lang.Throwable -> L40
            android.os.SystemClock.sleep(r6)     // Catch: java.lang.Throwable -> L40
            int r4 = r4 + 20
            if (r5 == 0) goto L7f
        L68:
            r5.close()
            goto L7f
        L6c:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzic r8 = r10.zzu     // Catch: java.lang.Throwable -> L40
            com.google.android.gms.measurement.internal.zzgu r8 = r8.zzaV()     // Catch: java.lang.Throwable -> L40
            com.google.android.gms.measurement.internal.zzgs r8 = r8.zzb()     // Catch: java.lang.Throwable -> L40
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L40
            r10.zzc = r6     // Catch: java.lang.Throwable -> L40
            if (r5 == 0) goto L7f
            goto L68
        L7f:
            int r3 = r3 + 1
            goto L15
        L82:
            if (r5 == 0) goto L87
            r5.close()
        L87:
            throw r0
        L88:
            com.google.android.gms.measurement.internal.zzic r0 = r10.zzu
            com.google.android.gms.measurement.internal.zzgu r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgs r0 = r0.zze()
            java.lang.String r1 = "Error deleting app launch break from local database in reasonable time"
            r0.zza(r1)
        L97:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzo():boolean");
    }

    final SQLiteDatabase zzp() throws SQLiteException {
        if (this.zzc) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzb.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzc = true;
        return null;
    }

    final boolean zzq() {
        zzic zzicVar = this.zzu;
        Context contextZzaY = zzicVar.zzaY();
        zzicVar.zzc();
        return contextZzaY.getDatabasePath("google_app_measurement_local.db").exists();
    }
}
