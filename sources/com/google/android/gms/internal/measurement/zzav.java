package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
public abstract class zzav {
    final List zza = new ArrayList();

    protected zzav() {
    }

    public abstract zzao zza(String str, zzg zzgVar, List list);

    final zzao zzb(String str) {
        if (!this.zza.contains(zzh.zze(str))) {
            throw new IllegalArgumentException("Command not supported");
        }
        String.valueOf(str);
        throw new UnsupportedOperationException("Command not implemented: ".concat(String.valueOf(str)));
    }
}
