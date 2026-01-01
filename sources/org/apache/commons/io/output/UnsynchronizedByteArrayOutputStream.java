package org.apache.commons.io.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOSupplier;
import org.apache.commons.io.function.Uncheck;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.commons.io.output.AbstractByteArrayOutputStream;

/* loaded from: classes4.dex */
public final class UnsynchronizedByteArrayOutputStream extends AbstractByteArrayOutputStream<UnsynchronizedByteArrayOutputStream> {

    public static class Builder extends AbstractStreamBuilder<UnsynchronizedByteArrayOutputStream, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public UnsynchronizedByteArrayOutputStream get() {
            return new UnsynchronizedByteArrayOutputStream(getBufferSize());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return toBufferedInputStream(inputStream, 1024);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int i) throws IOException {
        UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream = builder().setBufferSize(i).get();
        try {
            unsynchronizedByteArrayOutputStream.write(inputStream);
            InputStream inputStream2 = unsynchronizedByteArrayOutputStream.toInputStream();
            if (unsynchronizedByteArrayOutputStream != null) {
                unsynchronizedByteArrayOutputStream.close();
            }
            return inputStream2;
        } catch (Throwable th) {
            if (unsynchronizedByteArrayOutputStream != null) {
                try {
                    unsynchronizedByteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Deprecated
    public UnsynchronizedByteArrayOutputStream() {
        this(1024);
    }

    @Deprecated
    public UnsynchronizedByteArrayOutputStream(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        needNewBuffer(i);
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public void reset() {
        resetImpl();
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public int size() {
        return this.count;
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public byte[] toByteArray() {
        return toByteArrayImpl();
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public InputStream toInputStream() {
        return toInputStream(new AbstractByteArrayOutputStream.InputStreamConstructor() { // from class: org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream.InputStreamConstructor
            public final InputStream construct(byte[] bArr, int i, int i2) {
                return UnsynchronizedByteArrayOutputStream.lambda$toInputStream$1(bArr, i, i2);
            }
        });
    }

    static /* synthetic */ UnsynchronizedByteArrayInputStream lambda$toInputStream$1(final byte[] bArr, final int i, final int i2) {
        return (UnsynchronizedByteArrayInputStream) Uncheck.get(new IOSupplier() { // from class: org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOSupplier
            public final Object get() {
                byte[] bArr2 = bArr;
                return UnsynchronizedByteArrayInputStream.builder().setByteArray(bArr2).setOffset(i).setLength(i2).get();
            }
        });
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        int i3;
        if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException(String.format("offset=%,d, length=%,d", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        if (i2 == 0) {
            return;
        }
        writeImpl(bArr, i, i2);
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public int write(InputStream inputStream) throws IOException {
        return writeImpl(inputStream);
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream, java.io.OutputStream
    public void write(int i) {
        writeImpl(i);
    }

    @Override // org.apache.commons.io.output.AbstractByteArrayOutputStream
    public void writeTo(OutputStream outputStream) throws IOException {
        writeToImpl(outputStream);
    }
}
