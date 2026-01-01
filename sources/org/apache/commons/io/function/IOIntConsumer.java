package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface IOIntConsumer {
    public static final IOIntConsumer NOOP = new IOIntConsumer() { // from class: org.apache.commons.io.function.IOIntConsumer$$ExternalSyntheticLambda2
        @Override // org.apache.commons.io.function.IOIntConsumer
        public final void accept(int i) throws IOException {
            IOIntConsumer.lambda$static$0(i);
        }
    };

    static /* synthetic */ void lambda$static$0(int i) throws IOException {
    }

    void accept(int i) throws IOException;

    default IOIntConsumer andThen(final IOIntConsumer iOIntConsumer) {
        Objects.requireNonNull(iOIntConsumer);
        return new IOIntConsumer() { // from class: org.apache.commons.io.function.IOIntConsumer$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOIntConsumer
            public final void accept(int i) throws IOException {
                IOIntConsumer.lambda$andThen$1(this.f$0, iOIntConsumer, i);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$1(IOIntConsumer _this, IOIntConsumer iOIntConsumer, int i) throws IOException {
        _this.accept(i);
        iOIntConsumer.accept(i);
    }

    default Consumer<Integer> asConsumer() {
        return new Consumer() { // from class: org.apache.commons.io.function.IOIntConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Uncheck.accept(this.f$0, ((Integer) obj).intValue());
            }
        };
    }

    default IntConsumer asIntConsumer() {
        return new IntConsumer() { // from class: org.apache.commons.io.function.IOIntConsumer$$ExternalSyntheticLambda3
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                Uncheck.accept(this.f$0, i);
            }
        };
    }
}
