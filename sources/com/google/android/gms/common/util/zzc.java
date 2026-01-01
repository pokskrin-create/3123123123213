package com.google.android.gms.common.util;

import android.os.StrictMode;

/* compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* loaded from: classes.dex */
public final class zzc {
    public static StrictMode.VmPolicy zza() {
        StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
        if (PlatformVersion.isAtLeastS()) {
            StrictMode.setVmPolicy(zzb.zza(new StrictMode.VmPolicy.Builder(vmPolicy)).build());
        }
        return vmPolicy;
    }
}
