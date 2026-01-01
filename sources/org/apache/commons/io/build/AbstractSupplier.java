package org.apache.commons.io.build;

import org.apache.commons.io.build.AbstractSupplier;
import org.apache.commons.io.function.IOSupplier;

/* loaded from: classes4.dex */
public abstract class AbstractSupplier<T, B extends AbstractSupplier<T, B>> implements IOSupplier<T> {
    protected B asThis() {
        return this;
    }
}
