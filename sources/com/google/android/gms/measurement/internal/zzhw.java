package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes4.dex */
final class zzhw implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ zzhz zza;
    private final String zzb;

    public zzhw(zzhz zzhzVar, String str) {
        Objects.requireNonNull(zzhzVar);
        this.zza = zzhzVar;
        Preconditions.checkNotNull(str);
        this.zzb = str;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zza.zzu.zzaV().zzb().zzb(this.zzb, th);
    }
}
