package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
final class IOIteratorAdapter<E> implements IOIterator<E> {
    private final Iterator<E> delegate;

    static <E> IOIteratorAdapter<E> adapt(Iterator<E> it) {
        return new IOIteratorAdapter<>(it);
    }

    private IOIteratorAdapter(Iterator<E> it) {
        this.delegate = (Iterator) Objects.requireNonNull(it, "delegate");
    }

    @Override // org.apache.commons.io.function.IOIterator
    public boolean hasNext() throws IOException {
        return this.delegate.hasNext();
    }

    @Override // org.apache.commons.io.function.IOIterator
    public E next() throws IOException {
        return this.delegate.next();
    }

    @Override // org.apache.commons.io.function.IOIterator
    public Iterator<E> unwrap() {
        return this.delegate;
    }
}
