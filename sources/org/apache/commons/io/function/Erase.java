package org.apache.commons.io.function;

import java.io.IOException;

/* loaded from: classes4.dex */
public final class Erase {
    static <T, U> void accept(IOBiConsumer<T, U> iOBiConsumer, T t, U u) throws Throwable {
        try {
            iOBiConsumer.accept(t, u);
        } catch (IOException e) {
            rethrow(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void accept(IOConsumer<T> iOConsumer, T t) {
        try {
            iOConsumer.accept(t);
        } catch (IOException e) {
            rethrow(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T, U, R> R apply(IOBiFunction<? super T, ? super U, ? extends R> iOBiFunction, T t, U u) {
        try {
            return iOBiFunction.apply(t, u);
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T, R> R apply(IOFunction<? super T, ? extends R> iOFunction, T t) {
        try {
            return iOFunction.apply(t);
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> int compare(IOComparator<? super T> iOComparator, T t, T t2) {
        try {
            return iOComparator.compare(t, t2);
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    static <T> T get(IOSupplier<T> iOSupplier) {
        try {
            return iOSupplier.get();
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    public static <T extends Throwable> RuntimeException rethrow(Throwable th) throws Throwable {
        throw th;
    }

    static void run(IORunnable iORunnable) {
        try {
            iORunnable.run();
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> boolean test(IOPredicate<? super T> iOPredicate, T t) {
        try {
            return iOPredicate.test(t);
        } catch (IOException e) {
            throw rethrow(e);
        }
    }

    private Erase() {
    }
}
