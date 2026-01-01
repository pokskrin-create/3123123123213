package org.apache.commons.io.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.io.IOExceptionList;

/* loaded from: classes4.dex */
public interface IOStream<T> extends IOBaseStream<T, IOStream<T>, Stream<T>> {
    static /* synthetic */ IOException lambda$forAll$10(Integer num, IOException iOException) {
        return iOException;
    }

    static <T> IOStream<T> adapt(Stream<T> stream) {
        return IOStreamAdapter.adapt(stream);
    }

    static <T> IOStream<T> empty() {
        return IOStreamAdapter.adapt(Stream.empty());
    }

    static <T> IOStream<T> iterate(final T t, final IOUnaryOperator<T> iOUnaryOperator) {
        Objects.requireNonNull(iOUnaryOperator);
        return adapt(StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>() { // from class: org.apache.commons.io.function.IOStream.1
            T t = (T) IOStreams.NONE;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return true;
            }

            @Override // java.util.Iterator
            public T next() throws NoSuchElementException {
                try {
                    T tApply = this.t == IOStreams.NONE ? (T) t : iOUnaryOperator.apply(this.t);
                    this.t = tApply;
                    return tApply;
                } catch (IOException e) {
                    NoSuchElementException noSuchElementException = new NoSuchElementException();
                    noSuchElementException.initCause(e);
                    throw noSuchElementException;
                }
            }
        }, 1040), false));
    }

    static <T> IOStream<T> of(Iterable<T> iterable) {
        return iterable == null ? empty() : adapt(StreamSupport.stream(iterable.spliterator(), false));
    }

    @SafeVarargs
    static <T> IOStream<T> of(T... tArr) {
        return (tArr == null || tArr.length == 0) ? empty() : adapt(Arrays.stream(tArr));
    }

    static <T> IOStream<T> of(T t) {
        return adapt(Stream.of(t));
    }

    default boolean allMatch(final IOPredicate<? super T> iOPredicate) throws IOException {
        return unwrap().allMatch(new Predicate() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Erase.test(iOPredicate, obj);
            }
        });
    }

    default boolean anyMatch(final IOPredicate<? super T> iOPredicate) throws IOException {
        return unwrap().anyMatch(new Predicate() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Erase.test(iOPredicate, obj);
            }
        });
    }

    default <R, A> R collect(Collector<? super T, A, R> collector) {
        return (R) unwrap().collect(collector);
    }

    default <R> R collect(final IOSupplier<R> iOSupplier, final IOBiConsumer<R, ? super T> iOBiConsumer, final IOBiConsumer<R, R> iOBiConsumer2) throws IOException {
        return (R) unwrap().collect(new Supplier() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda22
            @Override // java.util.function.Supplier
            public final Object get() {
                return Erase.get(iOSupplier);
            }
        }, new BiConsumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda23
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Throwable {
                Erase.accept(iOBiConsumer, obj, obj2);
            }
        }, new BiConsumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Throwable {
                Erase.accept(iOBiConsumer2, obj, obj2);
            }
        });
    }

    default long count() {
        return unwrap().count();
    }

    default IOStream<T> distinct() {
        return adapt(unwrap().distinct());
    }

    default IOStream<T> filter(final IOPredicate<? super T> iOPredicate) throws IOException {
        return adapt(unwrap().filter(new Predicate() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Erase.test(iOPredicate, obj);
            }
        }));
    }

    default Optional<T> findAny() {
        return unwrap().findAny();
    }

    default Optional<T> findFirst() {
        return unwrap().findFirst();
    }

    default <R> IOStream<R> flatMap(final IOFunction<? super T, ? extends IOStream<? extends R>> iOFunction) throws IOException {
        return adapt(unwrap().flatMap(new Function() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IOStream) Erase.apply(iOFunction, obj)).unwrap();
            }
        }));
    }

    static /* synthetic */ DoubleStream lambda$flatMapToDouble$7(IOFunction iOFunction, Object obj) {
        return (DoubleStream) Erase.apply(iOFunction, obj);
    }

    default DoubleStream flatMapToDouble(final IOFunction<? super T, ? extends DoubleStream> iOFunction) throws IOException {
        return unwrap().flatMapToDouble(new Function() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IOStream.lambda$flatMapToDouble$7(iOFunction, obj);
            }
        });
    }

    static /* synthetic */ IntStream lambda$flatMapToInt$8(IOFunction iOFunction, Object obj) {
        return (IntStream) Erase.apply(iOFunction, obj);
    }

    default IntStream flatMapToInt(final IOFunction<? super T, ? extends IntStream> iOFunction) throws IOException {
        return unwrap().flatMapToInt(new Function() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IOStream.lambda$flatMapToInt$8(iOFunction, obj);
            }
        });
    }

    static /* synthetic */ LongStream lambda$flatMapToLong$9(IOFunction iOFunction, Object obj) {
        return (LongStream) Erase.apply(iOFunction, obj);
    }

    default LongStream flatMapToLong(final IOFunction<? super T, ? extends LongStream> iOFunction) throws IOException {
        return unwrap().flatMapToLong(new Function() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IOStream.lambda$flatMapToLong$9(iOFunction, obj);
            }
        });
    }

    default void forAll(IOConsumer<T> iOConsumer) throws IOExceptionList {
        forAll(iOConsumer, new BiFunction() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return IOStream.lambda$forAll$10((Integer) obj, (IOException) obj2);
            }
        });
    }

    default void forAll(IOConsumer<T> iOConsumer, final BiFunction<Integer, IOException, IOException> biFunction) throws IOExceptionList {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicInteger atomicInteger = new AtomicInteger();
        final IOConsumer iOConsumer2 = IOStreams.toIOConsumer(iOConsumer);
        unwrap().forEach(new Consumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IOStream.lambda$forAll$11(iOConsumer2, atomicReference, biFunction, atomicInteger, obj);
            }
        });
        IOExceptionList.checkEmpty((List) atomicReference.get(), null);
    }

    static /* synthetic */ void lambda$forAll$11(IOConsumer iOConsumer, AtomicReference atomicReference, BiFunction biFunction, AtomicInteger atomicInteger, Object obj) {
        try {
            iOConsumer.accept(obj);
        } catch (IOException e) {
            if (atomicReference.get() == null) {
                atomicReference.set(new ArrayList());
            }
            if (biFunction != null) {
                ((List) atomicReference.get()).add((IOException) biFunction.apply(Integer.valueOf(atomicInteger.get()), e));
            }
        }
        atomicInteger.incrementAndGet();
    }

    default void forEach(final IOConsumer<? super T> iOConsumer) throws IOException {
        unwrap().forEach(new Consumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Erase.accept(iOConsumer, obj);
            }
        });
    }

    default void forEachOrdered(final IOConsumer<? super T> iOConsumer) throws IOException {
        unwrap().forEachOrdered(new Consumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Erase.accept(iOConsumer, obj);
            }
        });
    }

    default IOStream<T> limit(long j) {
        return adapt(unwrap().limit(j));
    }

    default <R> IOStream<R> map(final IOFunction<? super T, ? extends R> iOFunction) throws IOException {
        return adapt(unwrap().map(new Function() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Erase.apply(iOFunction, obj);
            }
        }));
    }

    default DoubleStream mapToDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return unwrap().mapToDouble(toDoubleFunction);
    }

    default IntStream mapToInt(ToIntFunction<? super T> toIntFunction) {
        return unwrap().mapToInt(toIntFunction);
    }

    default LongStream mapToLong(ToLongFunction<? super T> toLongFunction) {
        return unwrap().mapToLong(toLongFunction);
    }

    default Optional<T> max(final IOComparator<? super T> iOComparator) throws IOException {
        return unwrap().max(new Comparator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda21
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Erase.compare(iOComparator, obj, obj2);
            }
        });
    }

    default Optional<T> min(final IOComparator<? super T> iOComparator) throws IOException {
        return unwrap().min(new Comparator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda20
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Erase.compare(iOComparator, obj, obj2);
            }
        });
    }

    default boolean noneMatch(final IOPredicate<? super T> iOPredicate) throws IOException {
        return unwrap().noneMatch(new Predicate() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Erase.test(iOPredicate, obj);
            }
        });
    }

    default IOStream<T> peek(final IOConsumer<? super T> iOConsumer) throws IOException {
        return adapt(unwrap().peek(new Consumer() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Erase.accept(iOConsumer, obj);
            }
        }));
    }

    default Optional<T> reduce(final IOBinaryOperator<T> iOBinaryOperator) throws IOException {
        return unwrap().reduce(new BinaryOperator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Erase.apply(iOBinaryOperator, obj, obj2);
            }
        });
    }

    default T reduce(T t, final IOBinaryOperator<T> iOBinaryOperator) throws IOException {
        return unwrap().reduce(t, new BinaryOperator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda13
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Erase.apply(iOBinaryOperator, obj, obj2);
            }
        });
    }

    default <U> U reduce(U u, final IOBiFunction<U, ? super T, U> iOBiFunction, final IOBinaryOperator<U> iOBinaryOperator) throws IOException {
        return (U) unwrap().reduce(u, new BiFunction() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda7
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Erase.apply(iOBiFunction, obj, obj2);
            }
        }, new BinaryOperator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Erase.apply(iOBinaryOperator, obj, obj2);
            }
        });
    }

    default IOStream<T> skip(long j) {
        return adapt(unwrap().skip(j));
    }

    default IOStream<T> sorted() {
        return adapt(unwrap().sorted());
    }

    default IOStream<T> sorted(final IOComparator<? super T> iOComparator) throws IOException {
        return adapt(unwrap().sorted(new Comparator() { // from class: org.apache.commons.io.function.IOStream$$ExternalSyntheticLambda15
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Erase.compare(iOComparator, obj, obj2);
            }
        }));
    }

    default Object[] toArray() {
        return unwrap().toArray();
    }

    default <A> A[] toArray(IntFunction<A[]> intFunction) {
        return (A[]) unwrap().toArray(intFunction);
    }
}
