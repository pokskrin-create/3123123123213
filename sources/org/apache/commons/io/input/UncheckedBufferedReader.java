package org.apache.commons.io.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.CharBuffer;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOBooleanSupplier;
import org.apache.commons.io.function.IOFunction;
import org.apache.commons.io.function.IOIntConsumer;
import org.apache.commons.io.function.IOIntSupplier;
import org.apache.commons.io.function.IORunnable;
import org.apache.commons.io.function.IOSupplier;
import org.apache.commons.io.function.IOTriFunction;
import org.apache.commons.io.function.Uncheck;

/* loaded from: classes4.dex */
public final class UncheckedBufferedReader extends BufferedReader {

    public static class Builder extends AbstractStreamBuilder<UncheckedBufferedReader, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public UncheckedBufferedReader get() {
            return (UncheckedBufferedReader) Uncheck.get(new IOSupplier() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$Builder$$ExternalSyntheticLambda0
                @Override // org.apache.commons.io.function.IOSupplier
                public final Object get() {
                    return this.f$0.m2206xc48f3479();
                }
            });
        }

        /* renamed from: lambda$get$0$org-apache-commons-io-input-UncheckedBufferedReader$Builder, reason: not valid java name */
        /* synthetic */ UncheckedBufferedReader m2206xc48f3479() throws IOException {
            return new UncheckedBufferedReader(getReader(), getBufferSize());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private UncheckedBufferedReader(Reader reader, int i) {
        super(reader, i);
    }

    @Override // java.io.BufferedReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda9
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2196xabdb1404();
            }
        });
    }

    /* renamed from: lambda$close$0$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ void m2196xabdb1404() throws IOException {
        super.close();
    }

    /* renamed from: lambda$mark$1$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ void m2197x1d875af4(int i) throws IOException {
        super.mark(i);
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public void mark(int i) throws UncheckedIOException {
        Uncheck.accept(new IOIntConsumer() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOIntConsumer
            public final void accept(int i2) throws IOException {
                this.f$0.m2197x1d875af4(i2);
            }
        }, i);
    }

    /* renamed from: lambda$read$2$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ int m2198xcbc9ce7e() throws IOException {
        return super.read();
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public int read() throws UncheckedIOException {
        return Uncheck.getAsInt(new IOIntSupplier() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda6
            @Override // org.apache.commons.io.function.IOIntSupplier
            public final int getAsInt() {
                return this.f$0.m2198xcbc9ce7e();
            }
        });
    }

    /* renamed from: lambda$read$3$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ Integer m2199x59047fff(char[] cArr) throws IOException {
        return Integer.valueOf(super.read(cArr));
    }

    @Override // java.io.Reader
    public int read(char[] cArr) throws UncheckedIOException {
        return ((Integer) Uncheck.apply(new IOFunction() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return this.f$0.m2199x59047fff((char[]) obj);
            }
        }, cArr)).intValue();
    }

    /* renamed from: lambda$read$4$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ Integer m2200xe63f3180(char[] cArr, int i, int i2) throws IOException {
        return Integer.valueOf(super.read(cArr, i, i2));
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws UncheckedIOException {
        return ((Integer) Uncheck.apply(new IOTriFunction() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IOTriFunction
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return this.f$0.m2200xe63f3180((char[]) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        }, cArr, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    /* renamed from: lambda$read$5$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ Integer m2201x7379e301(CharBuffer charBuffer) throws IOException {
        return Integer.valueOf(super.read(charBuffer));
    }

    @Override // java.io.Reader, java.lang.Readable
    public int read(CharBuffer charBuffer) throws UncheckedIOException {
        return ((Integer) Uncheck.apply(new IOFunction() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda3
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return this.f$0.m2201x7379e301((CharBuffer) obj);
            }
        }, charBuffer)).intValue();
    }

    /* renamed from: lambda$readLine$6$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ String m2202x7a7a2016() throws IOException {
        return super.readLine();
    }

    @Override // java.io.BufferedReader
    public String readLine() throws UncheckedIOException {
        return (String) Uncheck.get(new IOSupplier() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda2
            @Override // org.apache.commons.io.function.IOSupplier
            public final Object get() {
                return this.f$0.m2202x7a7a2016();
            }
        });
    }

    /* renamed from: lambda$ready$7$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ boolean m2203xc2f09bf6() throws IOException {
        return super.ready();
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public boolean ready() throws UncheckedIOException {
        return Uncheck.getAsBoolean(new IOBooleanSupplier() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda8
            @Override // org.apache.commons.io.function.IOBooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.m2203xc2f09bf6();
            }
        });
    }

    /* renamed from: lambda$reset$8$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ void m2204x27249823() throws IOException {
        super.reset();
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public void reset() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda7
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2204x27249823();
            }
        });
    }

    /* renamed from: lambda$skip$9$org-apache-commons-io-input-UncheckedBufferedReader, reason: not valid java name */
    /* synthetic */ Long m2205x10708c2e(long j) throws IOException {
        return Long.valueOf(super.skip(j));
    }

    @Override // java.io.BufferedReader, java.io.Reader
    public long skip(long j) throws UncheckedIOException {
        return ((Long) Uncheck.apply(new IOFunction() { // from class: org.apache.commons.io.input.UncheckedBufferedReader$$ExternalSyntheticLambda5
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return this.f$0.m2205x10708c2e(((Long) obj).longValue());
            }
        }, Long.valueOf(j))).longValue();
    }
}
