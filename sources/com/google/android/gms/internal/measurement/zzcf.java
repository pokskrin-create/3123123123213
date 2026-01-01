package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.common.base.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzcf {
    private static final ThreadLocal zza = new zzce();

    public static SharedPreferences zza(Context context, String str, int i, zzca zzcaVar) {
        zzbv.zza();
        zzcd zzcdVar = str.equals("") ? new zzcd() : null;
        if (zzcdVar != null) {
            return zzcdVar;
        }
        ThreadLocal threadLocal = zza;
        Preconditions.checkArgument(((Boolean) threadLocal.get()).booleanValue());
        threadLocal.set(false);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
            threadLocal.set(true);
            return sharedPreferences;
        } catch (Throwable th) {
            zza.set(true);
            throw th;
        }
    }
}
