package org.apache.commons.io;

import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class IOUtils$$ExternalSyntheticThreadLocal9 extends ThreadLocal {
    public final /* synthetic */ Supplier initialValueSupplier;

    @Override // java.lang.ThreadLocal
    protected /* synthetic */ Object initialValue() {
        return this.initialValueSupplier.get();
    }
}
