package org.apache.commons.io.function;

import java.util.stream.Stream;

/* loaded from: classes4.dex */
final class IOStreamAdapter<T> extends IOBaseStreamAdapter<T, IOStream<T>, Stream<T>> implements IOStream<T> {
    static <T> IOStream<T> adapt(Stream<T> stream) {
        return stream != null ? new IOStreamAdapter(stream) : IOStream.empty();
    }

    private IOStreamAdapter(Stream<T> stream) {
        super(stream);
    }

    @Override // org.apache.commons.io.function.IOBaseStream
    public IOStream<T> wrap(Stream<T> stream) {
        return unwrap() == stream ? this : adapt(stream);
    }
}
