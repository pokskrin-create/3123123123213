package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
public interface IOIterator<E> {
    boolean hasNext() throws IOException;

    E next() throws IOException;

    Iterator<E> unwrap();

    static <E> IOIterator<E> adapt(Iterable<E> iterable) {
        return IOIteratorAdapter.adapt((Iterator) iterable.iterator());
    }

    static <E> IOIterator<E> adapt(Iterator<E> it) {
        return IOIteratorAdapter.adapt((Iterator) it);
    }

    default Iterator<E> asIterator() {
        return new UncheckedIOIterator(this);
    }

    default void forEachRemaining(IOConsumer<? super E> iOConsumer) throws IOException {
        Objects.requireNonNull(iOConsumer);
        while (hasNext()) {
            iOConsumer.accept(next());
        }
    }

    default void remove() throws IOException {
        unwrap().remove();
    }
}
