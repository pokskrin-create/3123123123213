package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.charset.CharsetDecoders;

/* loaded from: classes4.dex */
public class WriterOutputStream extends OutputStream {
    private static final int BUFFER_SIZE = 8192;
    private final CharsetDecoder decoder;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    private final boolean writeImmediately;
    private final Writer writer;

    public static class Builder extends AbstractStreamBuilder<WriterOutputStream, Builder> {
        private CharsetDecoder charsetDecoder = getCharset().newDecoder();
        private boolean writeImmediately;

        @Override // org.apache.commons.io.function.IOSupplier
        public WriterOutputStream get() throws IOException {
            return new WriterOutputStream(getWriter(), this.charsetDecoder, getBufferSize(), this.writeImmediately);
        }

        @Override // org.apache.commons.io.build.AbstractStreamBuilder
        public Builder setCharset(Charset charset) {
            super.setCharset(charset);
            this.charsetDecoder = getCharset().newDecoder();
            return this;
        }

        @Override // org.apache.commons.io.build.AbstractStreamBuilder
        public Builder setCharset(String str) {
            super.setCharset(str);
            this.charsetDecoder = getCharset().newDecoder();
            return this;
        }

        public Builder setCharsetDecoder(CharsetDecoder charsetDecoder) {
            if (charsetDecoder == null) {
                charsetDecoder = getCharsetDefault().newDecoder();
            }
            this.charsetDecoder = charsetDecoder;
            super.setCharset(charsetDecoder.charset());
            return this;
        }

        public Builder setWriteImmediately(boolean z) {
            this.writeImmediately = z;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static void checkIbmJdkWithBrokenUTF16(Charset charset) {
        if (StandardCharsets.UTF_16.name().equals(charset.name())) {
            byte[] bytes = "vés".getBytes(charset);
            CharsetDecoder charsetDecoderNewDecoder = charset.newDecoder();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
            CharBuffer charBufferAllocate = CharBuffer.allocate(3);
            int length = bytes.length;
            int i = 0;
            while (i < length) {
                byteBufferAllocate.put(bytes[i]);
                byteBufferAllocate.flip();
                try {
                    charsetDecoderNewDecoder.decode(byteBufferAllocate, charBufferAllocate, i == length + (-1));
                    byteBufferAllocate.compact();
                    i++;
                } catch (IllegalArgumentException unused) {
                    throw new UnsupportedOperationException("UTF-16 requested when running on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
                }
            }
            charBufferAllocate.rewind();
            if (!"vés".equals(charBufferAllocate.toString())) {
                throw new UnsupportedOperationException("UTF-16 requested when running on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
            }
        }
    }

    @Deprecated
    public WriterOutputStream(Writer writer) {
        this(writer, Charset.defaultCharset(), 8192, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, Charset charset) {
        this(writer, charset, 8192, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, Charset charset, int i, boolean z) {
        this(writer, Charsets.toCharset(charset).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), i, z);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder) {
        this(writer, charsetDecoder, 8192, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder, int i, boolean z) {
        this.decoderIn = ByteBuffer.allocate(128);
        checkIbmJdkWithBrokenUTF16(CharsetDecoders.toCharsetDecoder(charsetDecoder).charset());
        this.writer = writer;
        this.decoder = CharsetDecoders.toCharsetDecoder(charsetDecoder);
        this.writeImmediately = z;
        this.decoderOut = CharBuffer.allocate(i);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, String str) {
        this(writer, str, 8192, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer, String str, int i, boolean z) {
        this(writer, Charsets.toCharset(str), i, z);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        processInput(true);
        flushOutput();
        this.writer.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        flushOutput();
        this.writer.flush();
    }

    private void flushOutput() throws IOException {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }

    private void processInput(boolean z) throws IOException {
        CoderResult coderResultDecode;
        this.decoderIn.flip();
        while (true) {
            coderResultDecode = this.decoder.decode(this.decoderIn, this.decoderOut, z);
            if (!coderResultDecode.isOverflow()) {
                break;
            } else {
                flushOutput();
            }
        }
        if (!coderResultDecode.isUnderflow()) {
            throw new IOException("Unexpected coder result");
        }
        this.decoderIn.compact();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int iMin = Math.min(i2, this.decoderIn.remaining());
            this.decoderIn.put(bArr, i, iMin);
            processInput(false);
            i2 -= iMin;
            i += iMin;
        }
        if (this.writeImmediately) {
            flushOutput();
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        write(new byte[]{(byte) i}, 0, 1);
    }
}
