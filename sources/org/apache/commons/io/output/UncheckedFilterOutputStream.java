package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOIntConsumer;
import org.apache.commons.io.function.IORunnable;
import org.apache.commons.io.function.IOTriConsumer;
import org.apache.commons.io.function.Uncheck;

/* loaded from: classes4.dex */
public final class UncheckedFilterOutputStream extends FilterOutputStream {

    public static class Builder extends AbstractStreamBuilder<UncheckedFilterOutputStream, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public UncheckedFilterOutputStream get() throws IOException {
            return new UncheckedFilterOutputStream(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private UncheckedFilterOutputStream(Builder builder) throws IOException {
        super(builder.getOutputStream());
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.output.UncheckedFilterOutputStream$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2228x705c6664();
            }
        });
    }

    /* renamed from: lambda$close$0$org-apache-commons-io-output-UncheckedFilterOutputStream, reason: not valid java name */
    /* synthetic */ void m2228x705c6664() throws IOException {
        super.close();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws UncheckedIOException {
        Uncheck.run(new IORunnable() { // from class: org.apache.commons.io.output.UncheckedFilterOutputStream$$ExternalSyntheticLambda3
            @Override // org.apache.commons.io.function.IORunnable
            public final void run() throws IOException {
                this.f$0.m2229x3ee88f7();
            }
        });
    }

    /* renamed from: lambda$flush$1$org-apache-commons-io-output-UncheckedFilterOutputStream, reason: not valid java name */
    /* synthetic */ void m2229x3ee88f7() throws IOException {
        super.flush();
    }

    /* renamed from: lambda$write$2$org-apache-commons-io-output-UncheckedFilterOutputStream, reason: not valid java name */
    /* synthetic */ void m2230x6026fe5b(byte[] bArr) throws IOException {
        super.write(bArr);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws UncheckedIOException {
        Uncheck.accept((IOConsumer<byte[]>) new IOConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterOutputStream$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                this.f$0.m2230x6026fe5b((byte[]) obj);
            }
        }, bArr);
    }

    /* renamed from: lambda$write$3$org-apache-commons-io-output-UncheckedFilterOutputStream, reason: not valid java name */
    /* synthetic */ void m2231xed14157a(byte[] bArr, int i, int i2) throws IOException {
        super.write(bArr, i, i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws UncheckedIOException {
        Uncheck.accept(new IOTriConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterOutputStream$$ExternalSyntheticLambda2
            @Override // org.apache.commons.io.function.IOTriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) throws IOException {
                this.f$0.m2231xed14157a((byte[]) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
            }
        }, bArr, Integer.valueOf(i), Integer.valueOf(i2));
    }

    /* renamed from: lambda$write$4$org-apache-commons-io-output-UncheckedFilterOutputStream, reason: not valid java name */
    /* synthetic */ void m2232x7a012c99(int i) throws IOException {
        super.write(i);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws UncheckedIOException {
        Uncheck.accept(new IOIntConsumer() { // from class: org.apache.commons.io.output.UncheckedFilterOutputStream$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOIntConsumer
            public final void accept(int i2) throws IOException {
                this.f$0.m2232x7a012c99(i2);
            }
        }, i);
    }
}
