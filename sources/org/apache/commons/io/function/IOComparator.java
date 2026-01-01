package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Comparator;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface IOComparator<T> {
    int compare(T t, T t2) throws IOException;

    default Comparator<T> asComparator() {
        return new Comparator() { // from class: org.apache.commons.io.function.IOComparator$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Uncheck.compare(this.f$0, obj, obj2);
            }
        };
    }
}
