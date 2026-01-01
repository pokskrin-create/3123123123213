package org.apache.commons.io.function;

import java.util.Objects;
import java.util.Spliterator;

/* loaded from: classes4.dex */
final class IOSpliteratorAdapter<T> implements IOSpliterator<T> {
    private final Spliterator<T> delegate;

    static <E> IOSpliteratorAdapter<E> adapt(Spliterator<E> spliterator) {
        return new IOSpliteratorAdapter<>(spliterator);
    }

    IOSpliteratorAdapter(Spliterator<T> spliterator) {
        this.delegate = (Spliterator) Objects.requireNonNull(spliterator, "delegate");
    }

    @Override // org.apache.commons.io.function.IOSpliterator
    public Spliterator<T> unwrap() {
        return this.delegate;
    }
}
