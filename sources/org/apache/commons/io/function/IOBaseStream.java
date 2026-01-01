package org.apache.commons.io.function;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;
import org.apache.commons.io.function.IOBaseStream;

/* loaded from: classes4.dex */
public interface IOBaseStream<T, S extends IOBaseStream<T, S, B>, B extends BaseStream<T, B>> extends Closeable {
    B unwrap();

    S wrap(B b);

    default BaseStream<T, B> asBaseStream() {
        return new UncheckedIOBaseStream(this);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    default void close() {
        unwrap().close();
    }

    default boolean isParallel() {
        return unwrap().isParallel();
    }

    default IOIterator<T> iterator() {
        return IOIteratorAdapter.adapt((Iterator) unwrap().iterator());
    }

    default S onClose(final IORunnable iORunnable) throws IOException {
        return (S) wrap(unwrap().onClose(new Runnable() { // from class: org.apache.commons.io.function.IOBaseStream$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Erase.run(iORunnable);
            }
        }));
    }

    default S parallel() {
        return isParallel() ? this : (S) wrap(unwrap().parallel());
    }

    default S sequential() {
        return isParallel() ? (S) wrap(unwrap().sequential()) : this;
    }

    default IOSpliterator<T> spliterator() {
        return IOSpliteratorAdapter.adapt((Spliterator) unwrap().spliterator());
    }

    default S unordered() {
        return (S) wrap(unwrap().unordered());
    }
}
