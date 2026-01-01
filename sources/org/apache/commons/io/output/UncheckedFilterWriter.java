package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOFunction;
import org.apache.commons.io.function.IOIntConsumer;
import org.apache.commons.io.function.IORunnable;
import org.apache.commons.io.function.IOTriConsumer;
import org.apache.commons.io.function.IOTriFunction;
import org.apache.commons.io.function.Uncheck;

/* loaded from: classes4.dex */
public final class UncheckedFilterWriter extends FilterWriter {

    public static class Builder extends AbstractStreamBuilder<UncheckedFilterWriter, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public UncheckedFilterWriter get() throws IOException {
            return new UncheckedFilterWriter(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private UncheckedFilterWriter(Builder builder) throws IOException {
        super(builder.getWriter());
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) throws UncheckedIOException {
        return (Writer) Uncheck.apply(new IOFunction() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return this.f$0.m2233xc7671afa(((Character) obj).charValue());
            }
        }, Character.valueOf(c));
    }

    /* renamed from: lambda$append$0$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ Writer m2233xc7671afa(char c) throws IOException {
        return super.append(c);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) throws UncheckedIOException {
        return (Writer) Uncheck.apply(new IOFunction() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return this.f$0.m2234xf53fb559((CharSequence) obj);
            }
        }, charSequence);
    }

    /* renamed from: lambda$append$1$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ Writer m2234xf53fb559(CharSequence charSequence) throws IOException {
        return super.append(charSequence);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i, int i2) throws UncheckedIOException {
        return (Writer) Uncheck.apply(new IOTriFunction() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda5
            @Override // org.apache.commons.io.function.IOTriFunction
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return this.f$0.m2235x23184fb8((CharSequence) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        }, charSequence, Integer.valueOf(i), Integer.valueOf(i2));
    }

    /* renamed from: lambda$append$2$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ Writer m2235x23184fb8(CharSequence charSequence, int i, int i2) throws IOException {
        return super.append(charSequence, i, i2);
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda6
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2236x17bbe853();
            }
        });
    }

    /* renamed from: lambda$close$3$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2236x17bbe853() throws IOException {
        super.close();
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Flushable
    public void flush() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2237x41f43526();
            }
        });
    }

    /* renamed from: lambda$flush$4$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2237x41f43526() throws IOException {
        super.flush();
    }

    /* renamed from: lambda$write$5$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2238x7979858a(char[] cArr) throws IOException {
        super.write(cArr);
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws UncheckedIOException {
        Uncheck.accept((IOConsumer<char[]>) new IOConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda7
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                this.f$0.m2238x7979858a((char[]) obj);
            }
        }, cArr);
    }

    /* renamed from: lambda$write$6$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2239xa7521fe9(char[] cArr, int i, int i2) throws IOException {
        super.write(cArr, i, i2);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) throws UncheckedIOException {
        Uncheck.accept(new IOTriConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda3
            @Override // org.apache.commons.io.function.IOTriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) throws IOException {
                this.f$0.m2239xa7521fe9((char[]) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        }, cArr, Integer.valueOf(i), Integer.valueOf(i2));
    }

    /* renamed from: lambda$write$7$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2240xd52aba48(int i) throws IOException {
        super.write(i);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(int i) throws UncheckedIOException {
        Uncheck.accept(new IOIntConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda9
            @Override // org.apache.commons.io.function.IOIntConsumer
            public final void accept(int i2) throws IOException {
                this.f$0.m2240xd52aba48(i2);
            }
        }, i);
    }

    /* renamed from: lambda$write$8$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2241x30354a7(String str) throws IOException {
        super.write(str);
    }

    @Override // java.io.Writer
    public void write(String str) throws UncheckedIOException {
        Uncheck.accept((IOConsumer<String>) new IOConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda8
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                this.f$0.m2241x30354a7((String) obj);
            }
        }, str);
    }

    /* renamed from: lambda$write$9$org-apache-commons-io-output-UncheckedFilterWriter, reason: not valid java name */
    /* synthetic */ void m2242x30dbef06(String str, int i, int i2) throws IOException {
        super.write(str, i, i2);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(String str, int i, int i2) throws UncheckedIOException {
        Uncheck.accept(new IOTriConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterWriter$$ExternalSyntheticLambda2
            @Override // org.apache.commons.io.function.IOTriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) throws IOException {
                this.f$0.m2242x30dbef06((String) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        }, str, Integer.valueOf(i), Integer.valueOf(i2));
    }
}
