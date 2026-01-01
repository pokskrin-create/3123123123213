package org.apache.commons.io.serialization;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class ObjectStreamClassPredicate$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ List f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((FullClassNameMatcher) obj);
    }
}
