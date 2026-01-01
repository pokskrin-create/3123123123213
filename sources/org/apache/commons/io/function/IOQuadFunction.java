package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface IOQuadFunction<T, U, V, W, R> {
    R apply(T t, U u, V v, W w) throws IOException;

    default <X> IOQuadFunction<T, U, V, W, X> andThen(final IOFunction<? super R, ? extends X> iOFunction) {
        Objects.requireNonNull(iOFunction);
        return new IOQuadFunction() { // from class: org.apache.commons.io.function.IOQuadFunction$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOQuadFunction
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                return iOFunction.apply(this.f$0.apply(obj, obj2, obj3, obj4));
            }
        };
    }
}
