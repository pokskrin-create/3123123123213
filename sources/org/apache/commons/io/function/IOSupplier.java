package org.apache.commons.io.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface IOSupplier<T> {
    T get() throws IOException;

    default Supplier<T> asSupplier() {
        return new Supplier() { // from class: org.apache.commons.io.function.IOSupplier$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.getUnchecked();
            }
        };
    }

    default T getUnchecked() throws UncheckedIOException {
        return (T) Uncheck.get(this);
    }
}
