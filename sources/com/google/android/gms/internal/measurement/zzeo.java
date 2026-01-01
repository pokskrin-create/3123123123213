package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
/* loaded from: classes.dex */
final class zzeo extends zzeq {
    final /* synthetic */ zzes zza;
    final /* synthetic */ zzfb zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeo(zzfb zzfbVar, zzes zzesVar) {
        super(zzfbVar, true);
        this.zza = zzesVar;
        Objects.requireNonNull(zzfbVar);
        this.zzb = zzfbVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzeq
    final void zza() throws RemoteException {
        ((zzcr) Preconditions.checkNotNull(this.zzb.zzQ())).unregisterOnMeasurementEventListener(this.zza);
    }
}
