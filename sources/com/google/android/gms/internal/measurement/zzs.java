package com.google.android.gms.internal.measurement;

import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
final class zzs extends zzai {
    final boolean zza;
    final boolean zzb;
    final /* synthetic */ zzt zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzs(zzt zztVar, boolean z, boolean z2) {
        super("log");
        Objects.requireNonNull(zztVar);
        this.zzc = zztVar;
        this.zza = z;
        this.zzb = z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007f  */
    @Override // com.google.android.gms.internal.measurement.zzai
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.measurement.zzao zza(com.google.android.gms.internal.measurement.zzg r12, java.util.List r13) {
        /*
            r11 = this;
            java.lang.String r0 = "log"
            r1 = 1
            com.google.android.gms.internal.measurement.zzh.zzb(r0, r1, r13)
            int r0 = r13.size()
            r2 = 0
            if (r0 != r1) goto L2e
            com.google.android.gms.internal.measurement.zzt r0 = r11.zzc
            java.lang.Object r13 = r13.get(r2)
            com.google.android.gms.internal.measurement.zzao r13 = (com.google.android.gms.internal.measurement.zzao) r13
            com.google.android.gms.internal.measurement.zzao r12 = r12.zza(r13)
            java.lang.String r3 = r12.zzc()
            boolean r5 = r11.zza
            boolean r6 = r11.zzb
            java.util.List r4 = java.util.Collections.EMPTY_LIST
            com.google.android.gms.internal.measurement.zzr r1 = r0.zzb()
            r2 = 3
            r1.zza(r2, r3, r4, r5, r6)
            com.google.android.gms.internal.measurement.zzao r12 = com.google.android.gms.internal.measurement.zzs.zzf
            return r12
        L2e:
            java.lang.Object r0 = r13.get(r2)
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            com.google.android.gms.internal.measurement.zzao r0 = r12.zza(r0)
            java.lang.Double r0 = r0.zzd()
            double r2 = r0.doubleValue()
            int r0 = com.google.android.gms.internal.measurement.zzh.zzg(r2)
            r2 = 5
            r3 = 2
            if (r0 == r3) goto L57
            r4 = 3
            if (r0 == r4) goto L55
            if (r0 == r2) goto L53
            r5 = 6
            if (r0 == r5) goto L51
            goto L58
        L51:
            r6 = r3
            goto L59
        L53:
            r6 = r2
            goto L59
        L55:
            r6 = r1
            goto L59
        L57:
            r4 = 4
        L58:
            r6 = r4
        L59:
            java.lang.Object r0 = r13.get(r1)
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            com.google.android.gms.internal.measurement.zzao r0 = r12.zza(r0)
            java.lang.String r7 = r0.zzc()
            int r0 = r13.size()
            if (r0 != r3) goto L7f
            com.google.android.gms.internal.measurement.zzt r12 = r11.zzc
            boolean r9 = r11.zza
            boolean r10 = r11.zzb
            java.util.List r8 = java.util.Collections.EMPTY_LIST
            com.google.android.gms.internal.measurement.zzr r5 = r12.zzb()
            r5.zza(r6, r7, r8, r9, r10)
            com.google.android.gms.internal.measurement.zzao r12 = com.google.android.gms.internal.measurement.zzs.zzf
            return r12
        L7f:
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
        L84:
            int r0 = r13.size()
            int r0 = java.lang.Math.min(r0, r2)
            if (r3 >= r0) goto La2
            java.lang.Object r0 = r13.get(r3)
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            com.google.android.gms.internal.measurement.zzao r0 = r12.zza(r0)
            java.lang.String r0 = r0.zzc()
            r8.add(r0)
            int r3 = r3 + 1
            goto L84
        La2:
            com.google.android.gms.internal.measurement.zzt r12 = r11.zzc
            boolean r9 = r11.zza
            boolean r10 = r11.zzb
            com.google.android.gms.internal.measurement.zzr r5 = r12.zzb()
            r5.zza(r6, r7, r8, r9, r10)
            com.google.android.gms.internal.measurement.zzao r12 = com.google.android.gms.internal.measurement.zzs.zzf
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzs.zza(com.google.android.gms.internal.measurement.zzg, java.util.List):com.google.android.gms.internal.measurement.zzao");
    }
}
