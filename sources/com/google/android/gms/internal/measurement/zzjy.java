package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import com.google.common.base.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
final class zzjy implements zzjv {
    private static zzjy zza;
    private final Context zzb;
    private final ContentObserver zzc;
    private boolean zzd;

    private zzjy() {
        this.zzd = false;
        this.zzb = null;
        this.zzc = null;
    }

    private zzjy(Context context) {
        this.zzd = false;
        this.zzb = context;
        this.zzc = new zzjw(this, null);
    }

    static zzjy zza(Context context) {
        zzjy zzjyVar;
        synchronized (zzjy.class) {
            if (zza == null) {
                zza = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzjy(context) : new zzjy();
            }
            zzjy zzjyVar2 = zza;
            if (zzjyVar2 == null || zzjyVar2.zzc == null || zzjyVar2.zzd) {
                zzjyVar = (zzjy) Preconditions.checkNotNull(zza);
            } else {
                try {
                    context.getContentResolver().registerContentObserver(zzjg.zza, true, zza.zzc);
                    ((zzjy) Preconditions.checkNotNull(zza)).zzd = true;
                } catch (SecurityException e) {
                    Log.e("GservicesLoader", "Unable to register Gservices content observer", e);
                }
                zzjyVar = (zzjy) Preconditions.checkNotNull(zza);
            }
        }
        return zzjyVar;
    }

    static synchronized void zzc() {
        Context context;
        zzjy zzjyVar = zza;
        if (zzjyVar != null && (context = zzjyVar.zzb) != null && zzjyVar.zzc != null && zzjyVar.zzd) {
            context.getContentResolver().unregisterContentObserver(zza.zzc);
        }
        zza = null;
    }

    @Override // com.google.android.gms.internal.measurement.zzjv
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final String zze(final String str) {
        Context context = this.zzb;
        if (context != null && !zzjm.zzb(context)) {
            try {
                return (String) zzjv.zzh(new zzju() { // from class: com.google.android.gms.internal.measurement.zzjx
                    @Override // com.google.android.gms.internal.measurement.zzju
                    public final /* synthetic */ Object zza() {
                        return this.zza.zzd(str);
                    }
                });
            } catch (IllegalStateException | NullPointerException | SecurityException e) {
                Log.e("GservicesLoader", "Unable to read GServices for: ".concat(str), e);
            }
        }
        return null;
    }

    final /* synthetic */ String zzd(String str) {
        return zzjf.zza(((Context) Preconditions.checkNotNull(this.zzb)).getContentResolver(), str, null);
    }
}
