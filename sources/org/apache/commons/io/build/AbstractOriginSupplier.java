package org.apache.commons.io.build;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IORandomAccessFile;
import org.apache.commons.io.build.AbstractOrigin;
import org.apache.commons.io.build.AbstractOriginSupplier;

/* loaded from: classes4.dex */
public abstract class AbstractOriginSupplier<T, B extends AbstractOriginSupplier<T, B>> extends AbstractSupplier<T, B> {
    private AbstractOrigin<?, ?> origin;

    protected static AbstractOrigin.ByteArrayOrigin newByteArrayOrigin(byte[] bArr) {
        return new AbstractOrigin.ByteArrayOrigin(bArr);
    }

    protected static AbstractOrigin.CharSequenceOrigin newCharSequenceOrigin(CharSequence charSequence) {
        return new AbstractOrigin.CharSequenceOrigin(charSequence);
    }

    protected static AbstractOrigin.FileOrigin newFileOrigin(File file) {
        return new AbstractOrigin.FileOrigin(file);
    }

    protected static AbstractOrigin.FileOrigin newFileOrigin(String str) {
        return new AbstractOrigin.FileOrigin(new File(str));
    }

    protected static AbstractOrigin.InputStreamOrigin newInputStreamOrigin(InputStream inputStream) {
        return new AbstractOrigin.InputStreamOrigin(inputStream);
    }

    protected static AbstractOrigin.OutputStreamOrigin newOutputStreamOrigin(OutputStream outputStream) {
        return new AbstractOrigin.OutputStreamOrigin(outputStream);
    }

    protected static AbstractOrigin.PathOrigin newPathOrigin(Path path) {
        return new AbstractOrigin.PathOrigin(path);
    }

    protected static AbstractOrigin.PathOrigin newPathOrigin(String str) {
        return new AbstractOrigin.PathOrigin(Paths.get(str, new String[0]));
    }

    protected static AbstractOrigin.IORandomAccessFileOrigin newRandomAccessFileOrigin(IORandomAccessFile iORandomAccessFile) {
        return new AbstractOrigin.IORandomAccessFileOrigin(iORandomAccessFile);
    }

    protected static AbstractOrigin.RandomAccessFileOrigin newRandomAccessFileOrigin(RandomAccessFile randomAccessFile) {
        return new AbstractOrigin.RandomAccessFileOrigin(randomAccessFile);
    }

    protected static AbstractOrigin.ReaderOrigin newReaderOrigin(Reader reader) {
        return new AbstractOrigin.ReaderOrigin(reader);
    }

    protected static AbstractOrigin.URIOrigin newURIOrigin(URI uri) {
        return new AbstractOrigin.URIOrigin(uri);
    }

    protected static AbstractOrigin.WriterOrigin newWriterOrigin(Writer writer) {
        return new AbstractOrigin.WriterOrigin(writer);
    }

    protected AbstractOrigin<?, ?> checkOrigin() {
        AbstractOrigin<?, ?> abstractOrigin = this.origin;
        if (abstractOrigin != null) {
            return abstractOrigin;
        }
        throw new IllegalStateException("origin == null");
    }

    protected AbstractOrigin<?, ?> getOrigin() {
        return this.origin;
    }

    protected boolean hasOrigin() {
        return this.origin != null;
    }

    public B setByteArray(byte[] bArr) {
        return (B) setOrigin(newByteArrayOrigin(bArr));
    }

    public B setCharSequence(CharSequence charSequence) {
        return (B) setOrigin(newCharSequenceOrigin(charSequence));
    }

    public B setFile(File file) {
        return (B) setOrigin(newFileOrigin(file));
    }

    public B setFile(String str) {
        return (B) setOrigin(newFileOrigin(str));
    }

    public B setInputStream(InputStream inputStream) {
        return (B) setOrigin(newInputStreamOrigin(inputStream));
    }

    protected B setOrigin(AbstractOrigin<?, ?> abstractOrigin) {
        this.origin = abstractOrigin;
        return asThis();
    }

    public B setOutputStream(OutputStream outputStream) {
        return (B) setOrigin(newOutputStreamOrigin(outputStream));
    }

    public B setPath(Path path) {
        return (B) setOrigin(newPathOrigin(path));
    }

    public B setPath(String str) {
        return (B) setOrigin(newPathOrigin(str));
    }

    public B setRandomAccessFile(IORandomAccessFile iORandomAccessFile) {
        return (B) setOrigin(newRandomAccessFileOrigin(iORandomAccessFile));
    }

    public B setRandomAccessFile(RandomAccessFile randomAccessFile) {
        return (B) setOrigin(newRandomAccessFileOrigin(randomAccessFile));
    }

    public B setReader(Reader reader) {
        return (B) setOrigin(newReaderOrigin(reader));
    }

    public B setURI(URI uri) {
        return (B) setOrigin(newURIOrigin(uri));
    }

    public B setWriter(Writer writer) {
        return (B) setOrigin(newWriterOrigin(writer));
    }
}
