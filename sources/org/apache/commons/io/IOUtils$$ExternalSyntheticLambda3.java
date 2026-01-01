package org.apache.commons.io;

import java.io.InputStream;
import org.apache.commons.io.function.IOTriFunction;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class IOUtils$$ExternalSyntheticLambda3 implements IOTriFunction {
    public final /* synthetic */ InputStream f$0;

    public /* synthetic */ IOUtils$$ExternalSyntheticLambda3(InputStream inputStream) {
        this.f$0 = inputStream;
    }

    @Override // org.apache.commons.io.function.IOTriFunction
    public final Object apply(Object obj, Object obj2, Object obj3) {
        return Integer.valueOf(this.f$0.read((byte[]) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue()));
    }
}
