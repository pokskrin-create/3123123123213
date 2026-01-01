package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzpf implements Supplier {
    private static final zzpf zza = new zzpf();
    private final Supplier zzb = Suppliers.ofInstance(new zzph());

    @SideEffectFree
    public static long zza() {
        return zza.get().zza();
    }

    @Override // com.google.common.base.Supplier
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzpg get() {
        return (zzpg) this.zzb.get();
    }
}
