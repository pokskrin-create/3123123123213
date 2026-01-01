package org.apache.commons.io.function;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.io.IOExceptionList;

/* loaded from: classes4.dex */
final class IOStreams {
    static final Object NONE = new Object();

    static /* synthetic */ IOException lambda$forAll$0(Integer num, IOException iOException) {
        return iOException;
    }

    static <T> void forAll(Stream<T> stream, IOConsumer<T> iOConsumer) throws IOExceptionList {
        forAll(stream, iOConsumer, new BiFunction() { // from class: org.apache.commons.io.function.IOStreams$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return IOStreams.lambda$forAll$0((Integer) obj, (IOException) obj2);
            }
        });
    }

    static <T> void forAll(Stream<T> stream, IOConsumer<T> iOConsumer, BiFunction<Integer, IOException, IOException> biFunction) throws IOExceptionList {
        IOStream.adapt(stream).forAll(iOConsumer, new IOConsumer$$ExternalSyntheticLambda1());
    }

    static <T> void forEach(Stream<T> stream, IOConsumer<T> iOConsumer) throws IOException {
        final IOConsumer iOConsumer2 = toIOConsumer(iOConsumer);
        of(stream).forEach(new Consumer() { // from class: org.apache.commons.io.function.IOStreams$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Erase.accept(iOConsumer2, obj);
            }
        });
    }

    static <T> Stream<T> of(Iterable<T> iterable) {
        return iterable == null ? Stream.empty() : StreamSupport.stream(iterable.spliterator(), false);
    }

    static <T> Stream<T> of(Stream<T> stream) {
        return stream == null ? Stream.empty() : stream;
    }

    @SafeVarargs
    static <T> Stream<T> of(T... tArr) {
        return tArr == null ? Stream.empty() : Stream.of((Object[]) tArr);
    }

    static <T> IOConsumer<T> toIOConsumer(IOConsumer<T> iOConsumer) {
        return iOConsumer != null ? iOConsumer : IOConsumer.noop();
    }

    private IOStreams() {
    }
}
