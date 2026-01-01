package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.io.function.Erase;

/* loaded from: classes4.dex */
public class BrokenOutputStream extends OutputStream {
    public static final BrokenOutputStream INSTANCE = new BrokenOutputStream();
    private final Function<String, Throwable> exceptionFunction;

    static /* synthetic */ Throwable lambda$new$1(IOException iOException, String str) {
        return iOException;
    }

    static /* synthetic */ Throwable lambda$new$3(Throwable th, String str) {
        return th;
    }

    public BrokenOutputStream() {
        this((Function<String, Throwable>) new Function() { // from class: org.apache.commons.io.output.BrokenOutputStream$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BrokenOutputStream.lambda$new$0((String) obj);
            }
        });
    }

    static /* synthetic */ Throwable lambda$new$0(String str) {
        return new IOException("Broken output stream: " + str);
    }

    @Deprecated
    public BrokenOutputStream(final IOException iOException) {
        this((Function<String, Throwable>) new Function() { // from class: org.apache.commons.io.output.BrokenOutputStream$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BrokenOutputStream.lambda$new$1(iOException, (String) obj);
            }
        });
    }

    public BrokenOutputStream(Function<String, Throwable> function) {
        this.exceptionFunction = function;
    }

    @Deprecated
    public BrokenOutputStream(final Supplier<Throwable> supplier) {
        this.exceptionFunction = new Function() { // from class: org.apache.commons.io.output.BrokenOutputStream$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BrokenOutputStream.lambda$new$2(supplier, (String) obj);
            }
        };
    }

    static /* synthetic */ Throwable lambda$new$2(Supplier supplier, String str) {
        return (Throwable) supplier.get();
    }

    public BrokenOutputStream(final Throwable th) {
        this((Function<String, Throwable>) new Function() { // from class: org.apache.commons.io.output.BrokenOutputStream$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BrokenOutputStream.lambda$new$3(th, (String) obj);
            }
        });
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        throw rethrow("close()");
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        throw rethrow("flush()");
    }

    private RuntimeException rethrow(String str) {
        return Erase.rethrow(this.exceptionFunction.apply(str));
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        throw rethrow("write(int)");
    }
}
