package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
/* loaded from: classes.dex */
final class zzdj extends zzeq {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Bundle zzc;
    final /* synthetic */ zzfb zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdj(zzfb zzfbVar, String str, String str2, Bundle bundle) {
        super(zzfbVar, true);
        this.zza = str;
        this.zzb = str2;
        this.zzc = bundle;
        Objects.requireNonNull(zzfbVar);
        this.zzd = zzfbVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzeq
    final void zza() throws RemoteException {
        ((zzcr) Preconditions.checkNotNull(this.zzd.zzQ())).clearConditionalUserProperty(this.zza, this.zzb, this.zzc);
    }
}
