package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
final class zzaj implements Iterator {
    final /* synthetic */ Iterator zza;

    zzaj(Iterator it) {
        this.zza = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        return new zzas((String) this.zza.next());
    }
}
