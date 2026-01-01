package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
public final class zzgk extends zzmb implements zznn {
    private zzgk() {
        throw null;
    }

    /* synthetic */ zzgk(byte[] bArr) {
        super(zzgl.zzu);
    }

    public final int zza() {
        return ((zzgl) this.zza).zzf();
    }

    public final zzgj zzb(int i) {
        return ((zzgl) this.zza).zzg(i);
    }

    public final zzgk zzc(int i, zzgi zzgiVar) {
        zzaX();
        ((zzgl) this.zza).zzt(i, (zzgj) zzgiVar.zzbc());
        return this;
    }

    public final List zzd() {
        return Collections.unmodifiableList(((zzgl) this.zza).zzh());
    }

    public final zzgk zze() {
        zzaX();
        ((zzgl) this.zza).zzu();
        return this;
    }

    public final zzgk zzf() {
        zzaX();
        ((zzgl) this.zza).zzv();
        return this;
    }

    public final List zzg() {
        return Collections.unmodifiableList(((zzgl) this.zza).zzk());
    }

    public final String zzh() {
        return ((zzgl) this.zza).zzm();
    }
}
