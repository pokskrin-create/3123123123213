package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public abstract class zzkm {
    public static final /* synthetic */ int zzc = 0;
    private static final Object zzd = new Object();

    @Nullable
    private static volatile zzkh zze = null;
    private static volatile boolean zzf = false;
    private static final AtomicInteger zzh;
    final zzkg zza;
    final String zzb;
    private Object zzg;
    private volatile int zzi = -1;
    private volatile Object zzj;
    private volatile boolean zzk;

    static {
        new AtomicReference();
        Preconditions.checkNotNull(zzkk.zza, "BuildInfo must be non-null");
        zzh = new AtomicInteger();
    }

    /* synthetic */ zzkm(zzkg zzkgVar, String str, Object obj, boolean z, byte[] bArr) {
        if (zzkgVar.zza == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zza = zzkgVar;
        this.zzb = str;
        this.zzg = obj;
        this.zzk = false;
    }

    public static void zzb(final Context context) {
        if (zze != null || context == null) {
            return;
        }
        Object obj = zzd;
        synchronized (obj) {
            if (zze == null) {
                synchronized (obj) {
                    zzkh zzkhVar = zze;
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                    if (zzkhVar == null || zzkhVar.zza() != context) {
                        if (zzkhVar != null) {
                            zzjr.zzd();
                            zzko.zzb();
                            zzjy.zzc();
                        }
                        zze = new zzjn(context, Suppliers.memoize(new Supplier() { // from class: com.google.android.gms.internal.measurement.zzkl
                            @Override // com.google.common.base.Supplier
                            public final /* synthetic */ Object get() {
                                int i = zzkm.zzc;
                                return zzjz.zza(context);
                            }
                        }));
                        zzh.incrementAndGet();
                    }
                }
            }
        }
    }

    public static void zzc() {
        zzh.incrementAndGet();
    }

    @Nullable
    abstract Object zza(Object obj);

    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0073 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0096 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzd() {
        /*
            r9 = this;
            java.util.concurrent.atomic.AtomicInteger r0 = com.google.android.gms.internal.measurement.zzkm.zzh
            int r0 = r0.get()
            int r1 = r9.zzi
            if (r1 >= r0) goto Lcc
            monitor-enter(r9)
            int r1 = r9.zzi     // Catch: java.lang.Throwable -> Lc9
            if (r1 >= r0) goto Lc7
            com.google.android.gms.internal.measurement.zzkh r1 = com.google.android.gms.internal.measurement.zzkm.zze     // Catch: java.lang.Throwable -> Lc9
            com.google.common.base.Optional r2 = com.google.common.base.Optional.absent()     // Catch: java.lang.Throwable -> Lc9
            r3 = 0
            if (r1 == 0) goto L47
            com.google.common.base.Supplier r4 = r1.zzb()     // Catch: java.lang.Throwable -> Lc9
            if (r4 == 0) goto L47
            com.google.common.base.Supplier r2 = r1.zzb()     // Catch: java.lang.Throwable -> Lc9
            java.lang.Object r2 = com.google.common.base.Preconditions.checkNotNull(r2)     // Catch: java.lang.Throwable -> Lc9
            com.google.common.base.Supplier r2 = (com.google.common.base.Supplier) r2     // Catch: java.lang.Throwable -> Lc9
            java.lang.Object r2 = r2.get()     // Catch: java.lang.Throwable -> Lc9
            com.google.common.base.Optional r2 = (com.google.common.base.Optional) r2     // Catch: java.lang.Throwable -> Lc9
            boolean r4 = r2.isPresent()     // Catch: java.lang.Throwable -> Lc9
            if (r4 == 0) goto L47
            java.lang.Object r4 = r2.get()     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzjt r4 = (com.google.android.gms.internal.measurement.zzjt) r4     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzkg r5 = r9.zza     // Catch: java.lang.Throwable -> Lc9
            android.net.Uri r6 = r5.zza     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r5 = r5.zzc     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r7 = r9.zzb     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r4 = r4.zza(r6, r3, r5, r7)     // Catch: java.lang.Throwable -> Lc9
            goto L48
        L47:
            r4 = r3
        L48:
            if (r1 == 0) goto L4c
            r5 = 1
            goto L4d
        L4c:
            r5 = 0
        L4d:
            java.lang.String r6 = "Must call PhenotypeFlagInitializer.maybeInit() first"
            com.google.common.base.Preconditions.checkState(r5, r6)     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzkg r5 = r9.zza     // Catch: java.lang.Throwable -> Lc9
            android.net.Uri r6 = r5.zza     // Catch: java.lang.Throwable -> Lc9
            if (r6 == 0) goto L73
            android.content.Context r7 = r1.zza()     // Catch: java.lang.Throwable -> Lc9
            boolean r7 = com.google.android.gms.internal.measurement.zzka.zza(r7, r6)     // Catch: java.lang.Throwable -> Lc9
            if (r7 == 0) goto L71
            android.content.Context r7 = r1.zza()     // Catch: java.lang.Throwable -> Lc9
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzkj r8 = com.google.android.gms.internal.measurement.zzkj.zza     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzjr r6 = com.google.android.gms.internal.measurement.zzjr.zza(r7, r6, r8)     // Catch: java.lang.Throwable -> Lc9
            goto L83
        L71:
            r6 = r3
            goto L83
        L73:
            android.content.Context r6 = r1.zza()     // Catch: java.lang.Throwable -> Lc9
            java.lang.Object r7 = com.google.common.base.Preconditions.checkNotNull(r3)     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzki r8 = com.google.android.gms.internal.measurement.zzki.zza     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzko r6 = com.google.android.gms.internal.measurement.zzko.zza(r6, r7, r8)     // Catch: java.lang.Throwable -> Lc9
        L83:
            if (r6 == 0) goto L92
            java.lang.String r7 = r9.zzb     // Catch: java.lang.Throwable -> Lc9
            java.lang.Object r6 = r6.zze(r7)     // Catch: java.lang.Throwable -> Lc9
            if (r6 == 0) goto L92
            java.lang.Object r6 = r9.zza(r6)     // Catch: java.lang.Throwable -> Lc9
            goto L93
        L92:
            r6 = r3
        L93:
            if (r6 == 0) goto L96
            goto Lb4
        L96:
            boolean r5 = r5.zzd     // Catch: java.lang.Throwable -> Lc9
            if (r5 != 0) goto Lae
            android.content.Context r1 = r1.zza()     // Catch: java.lang.Throwable -> Lc9
            com.google.android.gms.internal.measurement.zzjy r1 = com.google.android.gms.internal.measurement.zzjy.zza(r1)     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r5 = r9.zzb     // Catch: java.lang.Throwable -> Lc9
            java.lang.String r1 = r1.zze(r5)     // Catch: java.lang.Throwable -> Lc9
            if (r1 == 0) goto Lae
            java.lang.Object r3 = r9.zza(r1)     // Catch: java.lang.Throwable -> Lc9
        Lae:
            if (r3 != 0) goto Lb3
            java.lang.Object r6 = r9.zzg     // Catch: java.lang.Throwable -> Lc9
            goto Lb4
        Lb3:
            r6 = r3
        Lb4:
            boolean r1 = r2.isPresent()     // Catch: java.lang.Throwable -> Lc9
            if (r1 == 0) goto Lc3
            if (r4 != 0) goto Lbf
            java.lang.Object r6 = r9.zzg     // Catch: java.lang.Throwable -> Lc9
            goto Lc3
        Lbf:
            java.lang.Object r6 = r9.zza(r4)     // Catch: java.lang.Throwable -> Lc9
        Lc3:
            r9.zzj = r6     // Catch: java.lang.Throwable -> Lc9
            r9.zzi = r0     // Catch: java.lang.Throwable -> Lc9
        Lc7:
            monitor-exit(r9)     // Catch: java.lang.Throwable -> Lc9
            goto Lcc
        Lc9:
            r0 = move-exception
            monitor-exit(r9)     // Catch: java.lang.Throwable -> Lc9
            throw r0
        Lcc:
            java.lang.Object r0 = r9.zzj
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkm.zzd():java.lang.Object");
    }
}
