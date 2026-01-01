package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteDatabase;
import java.io.File;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
public final class zzaw {
    /* JADX WARN: Removed duplicated region for block: B:25:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084 A[Catch: SQLiteException -> 0x00ec, TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bb A[Catch: SQLiteException -> 0x00ec, LOOP:1: B:37:0x00bb->B:42:0x00cd, LOOP_START, PHI: r1
  0x00bb: PHI (r1v5 int) = (r1v4 int), (r1v6 int) binds: [B:36:0x00b9, B:42:0x00cd] A[DONT_GENERATE, DONT_INLINE], TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d6 A[Catch: SQLiteException -> 0x00ec, TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void zza(com.google.android.gms.measurement.internal.zzgu r10, android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String[] r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaw.zza(com.google.android.gms.measurement.internal.zzgu, android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    static void zzb(zzgu zzguVar, SQLiteDatabase sQLiteDatabase) {
        if (zzguVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        com.google.android.gms.internal.measurement.zzbv.zza();
        String path = sQLiteDatabase.getPath();
        int i = com.google.android.gms.internal.measurement.zzca.zzb;
        File file = new File(path);
        if (!file.setReadable(false, false)) {
            zzguVar.zze().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzguVar.zze().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzguVar.zze().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzguVar.zze().zza("Failed to turn on database write permission for owner");
    }
}
