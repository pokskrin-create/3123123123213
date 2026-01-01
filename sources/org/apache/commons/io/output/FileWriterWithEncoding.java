package org.apache.commons.io.output;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Objects;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class FileWriterWithEncoding extends ProxyWriter {

    public static class Builder extends AbstractStreamBuilder<FileWriterWithEncoding, Builder> {
        private boolean append;
        private CharsetEncoder charsetEncoder = super.getCharset().newEncoder();

        @Override // org.apache.commons.io.function.IOSupplier
        public FileWriterWithEncoding get() throws IOException {
            if (this.charsetEncoder != null && getCharset() != null && !this.charsetEncoder.charset().equals(getCharset())) {
                throw new IllegalStateException(String.format("Mismatched Charset(%s) and CharsetEncoder(%s)", getCharset(), this.charsetEncoder.charset()));
            }
            Object charset = this.charsetEncoder;
            if (charset == null) {
                charset = getCharset();
            }
            return new FileWriterWithEncoding(FileWriterWithEncoding.initWriter(checkOrigin().getFile(), charset, this.append));
        }

        public Builder setAppend(boolean z) {
            this.append = z;
            return this;
        }

        public Builder setCharsetEncoder(CharsetEncoder charsetEncoder) {
            this.charsetEncoder = charsetEncoder;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static OutputStreamWriter initWriter(File file, Object obj, boolean z) throws Exception {
        Objects.requireNonNull(file, "file");
        boolean zExists = file.exists();
        try {
            OutputStream outputStreamNewOutputStream = FileUtils.newOutputStream(file, z);
            if (obj != null && !(obj instanceof Charset)) {
                if (obj instanceof CharsetEncoder) {
                    return new OutputStreamWriter(outputStreamNewOutputStream, (CharsetEncoder) obj);
                }
                return new OutputStreamWriter(outputStreamNewOutputStream, (String) obj);
            }
            return new OutputStreamWriter(outputStreamNewOutputStream, Charsets.toCharset((Charset) obj));
        } catch (IOException | RuntimeException e) {
            try {
                IOUtils.close((Closeable) null);
            } catch (IOException e2) {
                e.addSuppressed(e2);
            }
            if (!zExists) {
                FileUtils.deleteQuietly(file);
                throw e;
            }
            throw e;
        }
    }

    @Deprecated
    public FileWriterWithEncoding(File file, Charset charset) throws IOException {
        this(file, charset, false);
    }

    @Deprecated
    public FileWriterWithEncoding(File file, Charset charset, boolean z) throws IOException {
        this(initWriter(file, charset, z));
    }

    @Deprecated
    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder) throws IOException {
        this(file, charsetEncoder, false);
    }

    @Deprecated
    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder, boolean z) throws IOException {
        this(initWriter(file, charsetEncoder, z));
    }

    @Deprecated
    public FileWriterWithEncoding(File file, String str) throws IOException {
        this(file, str, false);
    }

    @Deprecated
    public FileWriterWithEncoding(File file, String str, boolean z) throws IOException {
        this(initWriter(file, str, z));
    }

    private FileWriterWithEncoding(OutputStreamWriter outputStreamWriter) {
        super(outputStreamWriter);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, Charset charset) throws IOException {
        this(new File(str), charset, false);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, Charset charset, boolean z) throws IOException {
        this(new File(str), charset, z);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, CharsetEncoder charsetEncoder) throws IOException {
        this(new File(str), charsetEncoder, false);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, CharsetEncoder charsetEncoder, boolean z) throws IOException {
        this(new File(str), charsetEncoder, z);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, String str2) throws IOException {
        this(new File(str), str2, false);
    }

    @Deprecated
    public FileWriterWithEncoding(String str, String str2, boolean z) throws IOException {
        this(new File(str), str2, z);
    }
}
