package org.apache.commons.io;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class StreamIterator<E> implements Iterator<E>, AutoCloseable {
    private boolean closed;
    private final Iterator<E> iterator;
    private final Stream<E> stream;

    public static <T> StreamIterator<T> iterator(Stream<T> stream) {
        return new StreamIterator<>(stream);
    }

    private StreamIterator(Stream<E> stream) {
        this.stream = (Stream) Objects.requireNonNull(stream, "stream");
        this.iterator = stream.iterator();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.closed = true;
        this.stream.close();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.closed) {
            return false;
        }
        boolean zHasNext = this.iterator.hasNext();
        if (!zHasNext) {
            close();
        }
        return zHasNext;
    }

    @Override // java.util.Iterator
    public E next() {
        E next = this.iterator.next();
        if (next == null) {
            close();
        }
        return next;
    }
}
