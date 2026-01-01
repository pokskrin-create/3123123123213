package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.charset.CharsetEncoders;

/* loaded from: classes4.dex */
public class ReaderInputStream extends AbstractInputStream {
    private final CharsetEncoder charsetEncoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private boolean endOfInput;
    private CoderResult lastCoderResult;
    private final Reader reader;

    public static class Builder extends AbstractStreamBuilder<ReaderInputStream, Builder> {
        private CharsetEncoder charsetEncoder = ReaderInputStream.newEncoder(getCharset());

        @Override // org.apache.commons.io.function.IOSupplier
        public ReaderInputStream get() throws IOException {
            return new ReaderInputStream(getReader(), this.charsetEncoder, getBufferSize());
        }

        CharsetEncoder getCharsetEncoder() {
            return this.charsetEncoder;
        }

        @Override // org.apache.commons.io.build.AbstractStreamBuilder
        public Builder setCharset(Charset charset) {
            super.setCharset(charset);
            this.charsetEncoder = ReaderInputStream.newEncoder(getCharset());
            return this;
        }

        /* renamed from: lambda$setCharsetEncoder$0$org-apache-commons-io-input-ReaderInputStream$Builder, reason: not valid java name */
        /* synthetic */ CharsetEncoder m2195x5a2c7bac() {
            return ReaderInputStream.newEncoder(getCharsetDefault());
        }

        public Builder setCharsetEncoder(CharsetEncoder charsetEncoder) {
            CharsetEncoder charsetEncoder2 = CharsetEncoders.toCharsetEncoder(charsetEncoder, new Supplier() { // from class: org.apache.commons.io.input.ReaderInputStream$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2195x5a2c7bac();
                }
            });
            this.charsetEncoder = charsetEncoder2;
            super.setCharset(charsetEncoder2.charset());
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    static int checkMinBufferSize(CharsetEncoder charsetEncoder, int i) {
        float fMinBufferSize = minBufferSize(charsetEncoder);
        if (i >= fMinBufferSize) {
            return i;
        }
        throw new IllegalArgumentException(String.format("Buffer size %,d must be at least %s for a CharsetEncoder %s.", Integer.valueOf(i), Float.valueOf(fMinBufferSize), charsetEncoder.charset().displayName()));
    }

    static float minBufferSize(CharsetEncoder charsetEncoder) {
        return charsetEncoder.maxBytesPerChar() * 2.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharsetEncoder newEncoder(Charset charset) {
        return Charsets.toCharset(charset).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    @Deprecated
    public ReaderInputStream(Reader reader) {
        this(reader, Charset.defaultCharset());
    }

    @Deprecated
    public ReaderInputStream(Reader reader, Charset charset) {
        this(reader, charset, 8192);
    }

    @Deprecated
    public ReaderInputStream(Reader reader, Charset charset, int i) {
        this(reader, Charsets.toCharset(charset).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), i);
    }

    @Deprecated
    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder) {
        this(reader, charsetEncoder, 8192);
    }

    @Deprecated
    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder, int i) {
        this.reader = reader;
        CharsetEncoder charsetEncoder2 = CharsetEncoders.toCharsetEncoder(charsetEncoder);
        this.charsetEncoder = charsetEncoder2;
        CharBuffer charBufferAllocate = CharBuffer.allocate(checkMinBufferSize(charsetEncoder2, i));
        this.encoderIn = charBufferAllocate;
        charBufferAllocate.flip();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(128);
        this.encoderOut = byteBufferAllocate;
        byteBufferAllocate.flip();
    }

    @Deprecated
    public ReaderInputStream(Reader reader, String str) {
        this(reader, str, 8192);
    }

    @Deprecated
    public ReaderInputStream(Reader reader, String str, int i) {
        this(reader, Charsets.toCharset(str), i);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.encoderOut.hasRemaining()) {
            return this.encoderOut.remaining();
        }
        return 0;
    }

    @Override // org.apache.commons.io.input.AbstractInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
        super.close();
    }

    private void fillBuffer() throws IOException {
        CoderResult coderResult;
        boolean z = this.endOfInput;
        if (z) {
            return;
        }
        if (!z && ((coderResult = this.lastCoderResult) == null || coderResult.isUnderflow())) {
            this.encoderIn.compact();
            int iPosition = this.encoderIn.position();
            int i = this.reader.read(this.encoderIn.array(), iPosition, this.encoderIn.remaining());
            if (i == -1) {
                this.endOfInput = true;
            } else {
                this.encoderIn.position(iPosition + i);
            }
            this.encoderIn.flip();
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.charsetEncoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        if (this.endOfInput) {
            this.lastCoderResult = this.charsetEncoder.flush(this.encoderOut);
        }
        if (this.lastCoderResult.isError()) {
            this.lastCoderResult.throwException();
        }
        this.encoderOut.flip();
    }

    CharsetEncoder getCharsetEncoder() {
        return this.charsetEncoder;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        checkOpen();
        while (!this.encoderOut.hasRemaining()) {
            fillBuffer();
            if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                return -1;
            }
        }
        return this.encoderOut.get() & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        Objects.requireNonNull(bArr, "array");
        if (i2 < 0 || i < 0 || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException("Array size=" + bArr.length + ", offset=" + i + ", length=" + i2);
        }
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        while (i2 > 0) {
            if (this.encoderOut.hasRemaining()) {
                int iMin = Math.min(this.encoderOut.remaining(), i2);
                this.encoderOut.get(bArr, i, iMin);
                i += iMin;
                i2 -= iMin;
                i3 += iMin;
            } else {
                if (this.endOfInput) {
                    break;
                }
                fillBuffer();
            }
        }
        if (i3 == 0 && this.endOfInput) {
            return -1;
        }
        return i3;
    }
}
