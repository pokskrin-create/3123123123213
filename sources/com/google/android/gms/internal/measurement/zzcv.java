package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
public final class zzcv extends zzbl implements zzcx {
    zzcv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IDynamiteUploadBatchesCallback");
    }

    @Override // com.google.android.gms.internal.measurement.zzcx
    public final void zze() throws RemoteException {
        zzd(2, zza());
    }
}
