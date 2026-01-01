package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzpr implements Supplier {
    private static final zzpr zza = new zzpr();
    private final Supplier zzb = Suppliers.ofInstance(new zzpt());

    @SideEffectFree
    public static boolean zza() {
        zza.get().zza();
        return true;
    }

    @SideEffectFree
    public static boolean zzb() {
        return zza.get().zzb();
    }

    @SideEffectFree
    public static boolean zzc() {
        return zza.get().zzc();
    }

    @Override // com.google.common.base.Supplier
    /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzps get() {
        return (zzps) this.zzb.get();
    }
}
