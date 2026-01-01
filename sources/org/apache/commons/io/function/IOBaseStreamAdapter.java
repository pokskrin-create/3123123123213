package org.apache.commons.io.function;

import java.util.Objects;
import java.util.stream.BaseStream;
import org.apache.commons.io.function.IOBaseStream;

/* loaded from: classes4.dex */
abstract class IOBaseStreamAdapter<T, S extends IOBaseStream<T, S, B>, B extends BaseStream<T, B>> implements IOBaseStream<T, S, B> {
    private final B delegate;

    IOBaseStreamAdapter(B b) {
        this.delegate = (B) Objects.requireNonNull(b, "delegate");
    }

    @Override // org.apache.commons.io.function.IOBaseStream
    public B unwrap() {
        return this.delegate;
    }
}
