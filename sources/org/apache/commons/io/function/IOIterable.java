package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Objects;
import java.util.Spliterator;

/* loaded from: classes4.dex */
public interface IOIterable<T> {
    IOIterator<T> iterator();

    Iterable<T> unwrap();

    default void forEach(IOConsumer<? super T> iOConsumer) throws IOException {
        iterator().forEachRemaining((IOConsumer) Objects.requireNonNull(iOConsumer));
    }

    default IOSpliterator<T> spliterator() {
        return IOSpliteratorAdapter.adapt((Spliterator) new UncheckedIOIterable(this).spliterator());
    }
}
