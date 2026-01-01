package org.apache.commons.io.function;

import java.util.Objects;
import java.util.Spliterator;

/* loaded from: classes4.dex */
public interface IOSpliterator<T> {
    Spliterator<T> unwrap();

    static <E> IOSpliterator<E> adapt(Spliterator<E> spliterator) {
        return IOSpliteratorAdapter.adapt((Spliterator) spliterator);
    }

    default Spliterator<T> asSpliterator() {
        return new UncheckedIOSpliterator(this);
    }

    default int characteristics() {
        return unwrap().characteristics();
    }

    default long estimateSize() {
        return unwrap().estimateSize();
    }

    default void forEachRemaining(IOConsumer<? super T> iOConsumer) {
        while (tryAdvance(iOConsumer)) {
        }
    }

    default IOComparator<? super T> getComparator() {
        return (IOComparator) unwrap().getComparator();
    }

    default long getExactSizeIfKnown() {
        return unwrap().getExactSizeIfKnown();
    }

    default boolean hasCharacteristics(int i) {
        return unwrap().hasCharacteristics(i);
    }

    default boolean tryAdvance(IOConsumer<? super T> iOConsumer) {
        return unwrap().tryAdvance(((IOConsumer) Objects.requireNonNull(iOConsumer, "action")).asConsumer());
    }

    default IOSpliterator<T> trySplit() {
        return adapt(unwrap().trySplit());
    }
}
