package org.apache.commons.io;

import java.net.URL;
import org.apache.commons.io.function.IOSupplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class FileUtils$$ExternalSyntheticLambda21 implements IOSupplier {
    public final /* synthetic */ URL f$0;

    public /* synthetic */ FileUtils$$ExternalSyntheticLambda21(URL url) {
        this.f$0 = url;
    }

    @Override // org.apache.commons.io.function.IOSupplier
    public final Object get() {
        return this.f$0.openStream();
    }
}
