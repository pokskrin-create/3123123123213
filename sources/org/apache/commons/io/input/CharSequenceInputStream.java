package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.charset.CharsetEncoders;
import org.apache.commons.io.function.IOSupplier;
import org.apache.commons.io.function.Uncheck;

/* loaded from: classes4.dex */
public class CharSequenceInputStream extends InputStream {
    private static final int NO_MARK = -1;
    private final ByteBuffer bBuf;
    private int bBufMark;
    private final CharBuffer cBuf;
    private int cBufMark;
    private final CharsetEncoder charsetEncoder;

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public static class Builder extends AbstractStreamBuilder<CharSequenceInputStream, Builder> {
        private CharsetEncoder charsetEncoder = CharSequenceInputStream.newEncoder(getCharset());

        @Override // org.apache.commons.io.function.IOSupplier
        public CharSequenceInputStream get() {
            return (CharSequenceInputStream) Uncheck.get(new IOSupplier() { // from class: org.apache.commons.io.input.CharSequenceInputStream$Builder$$ExternalSyntheticLambda1
                @Override // org.apache.commons.io.function.IOSupplier
                public final Object get() {
                    return this.f$0.m2192xc0c8f29c();
                }
            });
        }

        /* renamed from: lambda$get$0$org-apache-commons-io-input-CharSequenceInputStream$Builder, reason: not valid java name */
        /* synthetic */ CharSequenceInputStream m2192xc0c8f29c() throws IOException {
            return new CharSequenceInputStream(getCharSequence(), getBufferSize(), this.charsetEncoder);
        }

        CharsetEncoder getCharsetEncoder() {
            return this.charsetEncoder;
        }

        @Override // org.apache.commons.io.build.AbstractStreamBuilder
        public Builder setCharset(Charset charset) {
            super.setCharset(charset);
            this.charsetEncoder = CharSequenceInputStream.newEncoder(getCharset());
            return this;
        }

        /* renamed from: lambda$setCharsetEncoder$1$org-apache-commons-io-input-CharSequenceInputStream$Builder, reason: not valid java name */
        /* synthetic */ CharsetEncoder m2193xabe124b9() {
            return CharSequenceInputStream.newEncoder(getCharsetDefault());
        }

        public Builder setCharsetEncoder(CharsetEncoder charsetEncoder) {
            CharsetEncoder charsetEncoder2 = CharsetEncoders.toCharsetEncoder(charsetEncoder, new Supplier() { // from class: org.apache.commons.io.input.CharSequenceInputStream$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2193xabe124b9();
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

    /* JADX INFO: Access modifiers changed from: private */
    public static CharsetEncoder newEncoder(Charset charset) {
        return Charsets.toCharset(charset).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    @Deprecated
    public CharSequenceInputStream(CharSequence charSequence, Charset charset) {
        this(charSequence, charset, 8192);
    }

    @Deprecated
    public CharSequenceInputStream(CharSequence charSequence, Charset charset, int i) {
        this(charSequence, i, newEncoder(charset));
    }

    private CharSequenceInputStream(CharSequence charSequence, int i, CharsetEncoder charsetEncoder) {
        this.charsetEncoder = charsetEncoder;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(ReaderInputStream.checkMinBufferSize(charsetEncoder, i));
        this.bBuf = byteBufferAllocate;
        byteBufferAllocate.flip();
        this.cBuf = CharBuffer.wrap(charSequence);
        this.cBufMark = -1;
        this.bBufMark = -1;
        try {
            fillBuffer();
        } catch (CharacterCodingException unused) {
            this.bBuf.clear();
            this.bBuf.flip();
            this.cBuf.rewind();
        }
    }

    @Deprecated
    public CharSequenceInputStream(CharSequence charSequence, String str) {
        this(charSequence, str, 8192);
    }

    @Deprecated
    public CharSequenceInputStream(CharSequence charSequence, String str, int i) {
        this(charSequence, Charsets.toCharset(str), i);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.bBuf.remaining();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ByteBuffer byteBuffer = this.bBuf;
        byteBuffer.position(byteBuffer.limit());
    }

    private void fillBuffer() throws CharacterCodingException {
        this.bBuf.compact();
        CoderResult coderResultEncode = this.charsetEncoder.encode(this.cBuf, this.bBuf, true);
        if (coderResultEncode.isError()) {
            coderResultEncode.throwException();
        }
        this.bBuf.flip();
    }

    CharsetEncoder getCharsetEncoder() {
        return this.charsetEncoder;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i) {
        this.cBufMark = this.cBuf.position();
        this.bBufMark = this.bBuf.position();
        this.cBuf.mark();
        this.bBuf.mark();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (!this.bBuf.hasRemaining()) {
            fillBuffer();
            if (!this.bBuf.hasRemaining() && !this.cBuf.hasRemaining()) {
                return -1;
            }
        }
        return this.bBuf.get() & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        Objects.requireNonNull(bArr, "array");
        if (i2 < 0 || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException("Array Size=" + bArr.length + ", offset=" + i + ", length=" + i2);
        }
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (!this.bBuf.hasRemaining() && !this.cBuf.hasRemaining()) {
            return -1;
        }
        while (i2 > 0) {
            if (this.bBuf.hasRemaining()) {
                int iMin = Math.min(this.bBuf.remaining(), i2);
                this.bBuf.get(bArr, i, iMin);
                i += iMin;
                i2 -= iMin;
                i3 += iMin;
            } else {
                fillBuffer();
                if (!this.bBuf.hasRemaining() && !this.cBuf.hasRemaining()) {
                    break;
                }
            }
        }
        if (i3 != 0 || this.cBuf.hasRemaining()) {
            return i3;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.cBufMark != -1) {
            if (this.cBuf.position() != 0) {
                this.charsetEncoder.reset();
                this.cBuf.rewind();
                this.bBuf.rewind();
                this.bBuf.limit(0);
                while (this.cBuf.position() < this.cBufMark) {
                    this.bBuf.rewind();
                    this.bBuf.limit(0);
                    fillBuffer();
                }
            }
            if (this.cBuf.position() != this.cBufMark) {
                throw new IllegalStateException("Unexpected CharBuffer position: actual=" + this.cBuf.position() + " expected=" + this.cBufMark);
            }
            this.bBuf.position(this.bBufMark);
            this.cBufMark = -1;
            this.bBufMark = -1;
        }
        mark(0);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        while (j > 0 && available() > 0) {
            read();
            j--;
            j2++;
        }
        return j2;
    }
}
