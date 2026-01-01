package com.google.android.gms.internal.measurement;

import android.net.Uri;
import com.google.common.base.Function;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzkg {

    @Nullable
    final Uri zza;
    final String zzb;
    final String zzc;
    final boolean zzd;
    final boolean zze;

    public zzkg(Uri uri) {
        this(null, uri, "", "", false, false, false, false, null);
    }

    private zzkg(@Nullable String str, @Nullable Uri uri, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4, @Nullable Function function) {
        this.zza = uri;
        this.zzb = "";
        this.zzc = "";
        this.zzd = z;
        this.zze = z3;
    }

    public final zzkg zza() {
        String str = this.zzb;
        if (str.isEmpty()) {
            return new zzkg(null, this.zza, str, this.zzc, true, false, this.zze, false, null);
        }
        throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
    }

    public final zzkg zzb() {
        return new zzkg(null, this.zza, this.zzb, this.zzc, this.zzd, false, true, false, null);
    }

    public final zzkm zzc(String str, long j) {
        Long lValueOf = Long.valueOf(j);
        int i = zzkm.zzc;
        return new zzkc(this, str, lValueOf, true);
    }

    public final zzkm zzd(String str, boolean z) {
        Boolean boolValueOf = Boolean.valueOf(z);
        int i = zzkm.zzc;
        return new zzkd(this, str, boolValueOf, true);
    }

    public final zzkm zze(String str, double d) {
        Double dValueOf = Double.valueOf(-3.0d);
        int i = zzkm.zzc;
        return new zzke(this, "measurement.test.double_flag", dValueOf, true);
    }

    public final zzkm zzf(String str, String str2) {
        int i = zzkm.zzc;
        return new zzkf(this, str, str2, true);
    }
}
