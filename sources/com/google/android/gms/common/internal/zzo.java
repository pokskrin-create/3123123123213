package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* loaded from: classes.dex */
public final class zzo {
    private static final Uri zza = new Uri.Builder().scheme(FirebaseAnalytics.Param.CONTENT).authority("com.google.android.gms.chimera").build();
    private final String zzb;
    private final String zzc;
    private final ComponentName zzd;
    private final int zze;
    private final boolean zzf;

    public zzo(ComponentName componentName, int i) {
        this.zzb = null;
        this.zzc = null;
        Preconditions.checkNotNull(componentName);
        this.zzd = componentName;
        this.zze = 4225;
        this.zzf = false;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzo)) {
            return false;
        }
        zzo zzoVar = (zzo) obj;
        if (Objects.equal(this.zzb, zzoVar.zzb) && Objects.equal(this.zzc, zzoVar.zzc) && Objects.equal(this.zzd, zzoVar.zzd)) {
            int i = zzoVar.zze;
            if (this.zzf == zzoVar.zzf) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzb, this.zzc, this.zzd, 4225, Boolean.valueOf(this.zzf));
    }

    public final String toString() {
        String str = this.zzb;
        if (str != null) {
            return str;
        }
        Preconditions.checkNotNull(this.zzd);
        return this.zzd.flattenToString();
    }

    public final ComponentName zza() {
        return this.zzd;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ad A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.Intent zzb(android.content.Context r6) throws com.google.android.gms.common.internal.zzaj, android.os.RemoteException {
        /*
            r5 = this;
            java.lang.String r0 = "ConnectionStatusConfig"
            java.lang.String r1 = r5.zzb
            if (r1 == 0) goto Lae
            boolean r1 = r5.zzf
            r2 = 0
            if (r1 == 0) goto L9d
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r3 = r5.zzb
            java.lang.String r4 = "serviceActionBundleKey"
            r1.putString(r4, r3)
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            android.net.Uri r3 = com.google.android.gms.common.internal.zzo.zza     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            android.content.ContentProviderClient r6 = r6.acquireUnstableContentProviderClient(r3)     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            if (r6 == 0) goto L36
            java.lang.String r3 = "serviceIntentCall"
            android.os.Bundle r1 = r6.call(r3, r2, r1)     // Catch: java.lang.Throwable -> L31
            r6.release()     // Catch: java.lang.IllegalArgumentException -> L2d android.os.RemoteException -> L2f
            goto L4f
        L2d:
            r6 = move-exception
            goto L42
        L2f:
            r6 = move-exception
            goto L42
        L31:
            r1 = move-exception
            r6.release()     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            throw r1     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
        L36:
            android.os.RemoteException r6 = new android.os.RemoteException     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            java.lang.String r1 = "Failed to acquire ContentProviderClient"
            r6.<init>(r1)     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
            throw r6     // Catch: java.lang.IllegalArgumentException -> L3e android.os.RemoteException -> L40
        L3e:
            r6 = move-exception
            goto L41
        L40:
            r6 = move-exception
        L41:
            r1 = r2
        L42:
            java.lang.String r3 = "Dynamic intent resolution failed: "
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = r3.concat(r6)
            android.util.Log.w(r0, r6)
        L4f:
            if (r1 == 0) goto L8c
            java.lang.String r6 = "serviceResponseIntentKey"
            android.os.Parcelable r6 = r1.getParcelable(r6)
            android.content.Intent r6 = (android.content.Intent) r6
            if (r6 != 0) goto L8b
            java.lang.String r2 = "serviceMissingResolutionIntentKey"
            android.os.Parcelable r1 = r1.getParcelable(r2)
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            if (r1 != 0) goto L66
            goto L8b
        L66:
            java.lang.String r6 = r5.zzb
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Dynamic lookup for intent failed for action "
            r2.<init>(r3)
            r2.append(r6)
            java.lang.String r6 = " but has possible resolution"
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            android.util.Log.w(r0, r6)
            com.google.android.gms.common.internal.zzaj r6 = new com.google.android.gms.common.internal.zzaj
            com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult
            r2 = 25
            r0.<init>(r2, r1)
            r6.<init>(r0)
            throw r6
        L8b:
            r2 = r6
        L8c:
            if (r2 != 0) goto L9d
            java.lang.String r6 = r5.zzb
            java.lang.String r1 = "Dynamic lookup for intent failed for action: "
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r6 = r1.concat(r6)
            android.util.Log.w(r0, r6)
        L9d:
            if (r2 != 0) goto Lad
            java.lang.String r6 = r5.zzb
            android.content.Intent r0 = new android.content.Intent
            r0.<init>(r6)
            java.lang.String r6 = r5.zzc
            android.content.Intent r6 = r0.setPackage(r6)
            return r6
        Lad:
            return r2
        Lae:
            android.content.Intent r6 = new android.content.Intent
            r6.<init>()
            android.content.ComponentName r0 = r5.zzd
            android.content.Intent r6 = r6.setComponent(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzo.zzb(android.content.Context):android.content.Intent");
    }

    public final String zzc() {
        return this.zzc;
    }

    public zzo(String str, int i, boolean z) {
        this(str, "com.google.android.gms", 4225, false);
    }

    public zzo(String str, String str2, int i, boolean z) {
        Preconditions.checkNotEmpty(str);
        this.zzb = str;
        Preconditions.checkNotEmpty(str2);
        this.zzc = str2;
        this.zzd = null;
        this.zze = 4225;
        this.zzf = z;
    }
}
