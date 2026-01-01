package com.google.android.gms.internal.measurement;

import androidx.core.internal.view.SupportMenu;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
final class zzlq {
    private final Object zza;
    private final int zzb;

    zzlq(Object obj, int i) {
        this.zza = obj;
        this.zzb = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzlq)) {
            return false;
        }
        zzlq zzlqVar = (zzlq) obj;
        return this.zza == zzlqVar.zza && this.zzb == zzlqVar.zzb;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zza) * SupportMenu.USER_MASK) + this.zzb;
    }
}
