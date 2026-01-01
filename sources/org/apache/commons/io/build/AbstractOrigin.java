package org.apache.commons.io.build;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.Objects;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.IORandomAccessFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.RandomAccessFileMode;
import org.apache.commons.io.RandomAccessFiles;
import org.apache.commons.io.build.AbstractOrigin;
import org.apache.commons.io.file.spi.FileSystemProviders;
import org.apache.commons.io.function.IOFunction;
import org.apache.commons.io.input.BufferedFileChannelInputStream;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.io.output.RandomAccessFileOutputStream;
import org.apache.commons.io.output.WriterOutputStream;

/* loaded from: classes4.dex */
public abstract class AbstractOrigin<T, B extends AbstractOrigin<T, B>> extends AbstractSupplier<T, B> {
    final T origin;

    public static abstract class AbstractRandomAccessFileOrigin<T extends RandomAccessFile, B extends AbstractRandomAccessFileOrigin<T, B>> extends AbstractOrigin<T, B> {
        public AbstractRandomAccessFileOrigin(T t) {
            super(t);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray() throws IOException {
            long length = this.origin.length();
            if (length > 2147483647L) {
                throw new IllegalStateException("Origin too large.");
            }
            return RandomAccessFiles.read(this.origin, 0L, (int) length);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray(long j, int i) throws IOException {
            return RandomAccessFiles.read(this.origin, j, i);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public CharSequence getCharSequence(Charset charset) throws IOException {
            return new String(getByteArray(), charset);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
            return BufferedFileChannelInputStream.builder().setFileChannel(this.origin.getChannel()).get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.apache.commons.io.build.AbstractOrigin
        public OutputStream getOutputStream(OpenOption... openOptionArr) throws IOException {
            return ((RandomAccessFileOutputStream.Builder) RandomAccessFileOutputStream.builder().setRandomAccessFile(this.origin)).get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public T getRandomAccessFile(OpenOption... openOptionArr) {
            return get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Reader getReader(Charset charset) throws IOException {
            return new InputStreamReader(getInputStream(new OpenOption[0]), charset);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Writer getWriter(Charset charset, OpenOption... openOptionArr) throws IOException {
            return new OutputStreamWriter(getOutputStream(openOptionArr), charset);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public long size() throws IOException {
            return this.origin.length();
        }
    }

    public static class ByteArrayOrigin extends AbstractOrigin<byte[], ByteArrayOrigin> {
        public ByteArrayOrigin(byte[] bArr) {
            super(bArr);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray() {
            return get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
            return new ByteArrayInputStream((byte[]) this.origin);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Reader getReader(Charset charset) throws IOException {
            return new InputStreamReader(getInputStream(new OpenOption[0]), charset);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public long size() throws IOException {
            return ((byte[]) this.origin).length;
        }
    }

    public static class CharSequenceOrigin extends AbstractOrigin<CharSequence, CharSequenceOrigin> {
        public CharSequenceOrigin(CharSequence charSequence) {
            super(charSequence);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray() {
            return ((CharSequence) this.origin).toString().getBytes(Charset.defaultCharset());
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public CharSequence getCharSequence(Charset charset) {
            return get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
            return ((CharSequenceInputStream.Builder) CharSequenceInputStream.builder().setCharSequence(getCharSequence(Charset.defaultCharset()))).get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Reader getReader(Charset charset) throws IOException {
            return new CharSequenceReader(get());
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public long size() throws IOException {
            return ((CharSequence) this.origin).length();
        }
    }

    public static class FileOrigin extends AbstractOrigin<File, FileOrigin> {
        public FileOrigin(File file) {
            super(file);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray(long j, int i) throws IOException {
            RandomAccessFile randomAccessFileCreate = RandomAccessFileMode.READ_ONLY.create((File) this.origin);
            try {
                byte[] bArr = RandomAccessFiles.read(randomAccessFileCreate, j, i);
                if (randomAccessFileCreate != null) {
                    randomAccessFileCreate.close();
                }
                return bArr;
            } catch (Throwable th) {
                if (randomAccessFileCreate != null) {
                    try {
                        randomAccessFileCreate.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public File getFile() {
            return get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Path getPath() {
            return get().toPath();
        }
    }

    public static class InputStreamOrigin extends AbstractOrigin<InputStream, InputStreamOrigin> {
        public InputStreamOrigin(InputStream inputStream) {
            super(inputStream);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray() throws IOException {
            return IOUtils.toByteArray((InputStream) this.origin);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) {
            return get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Reader getReader(Charset charset) throws IOException {
            return new InputStreamReader(getInputStream(new OpenOption[0]), charset);
        }
    }

    public static class IORandomAccessFileOrigin extends AbstractRandomAccessFileOrigin<IORandomAccessFile, IORandomAccessFileOrigin> {
        public IORandomAccessFileOrigin(IORandomAccessFile iORandomAccessFile) {
            super(iORandomAccessFile);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public File getFile() {
            return ((IORandomAccessFile) get()).getFile();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Path getPath() {
            return getFile().toPath();
        }
    }

    public static class OutputStreamOrigin extends AbstractOrigin<OutputStream, OutputStreamOrigin> {
        public OutputStreamOrigin(OutputStream outputStream) {
            super(outputStream);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public OutputStream getOutputStream(OpenOption... openOptionArr) {
            return get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Writer getWriter(Charset charset, OpenOption... openOptionArr) throws IOException {
            return new OutputStreamWriter((OutputStream) this.origin, charset);
        }
    }

    public static class PathOrigin extends AbstractOrigin<Path, PathOrigin> {
        public PathOrigin(Path path) {
            super(path);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray(final long j, final int i) throws IOException {
            return (byte[]) RandomAccessFileMode.READ_ONLY.apply(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) this.origin), new IOFunction() { // from class: org.apache.commons.io.build.AbstractOrigin$PathOrigin$$ExternalSyntheticLambda0
                @Override // org.apache.commons.io.function.IOFunction
                public final Object apply(Object obj) {
                    return RandomAccessFiles.read((RandomAccessFile) obj, j, i);
                }
            });
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public File getFile() {
            return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) get()).toFile();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Path getPath() {
            return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) get());
        }
    }

    public static class RandomAccessFileOrigin extends AbstractRandomAccessFileOrigin<RandomAccessFile, RandomAccessFileOrigin> {
        public RandomAccessFileOrigin(RandomAccessFile randomAccessFile) {
            super(randomAccessFile);
        }
    }

    public static class ReaderOrigin extends AbstractOrigin<Reader, ReaderOrigin> {
        public ReaderOrigin(Reader reader) {
            super(reader);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public byte[] getByteArray() throws IOException {
            return IOUtils.toByteArray((Reader) this.origin, Charset.defaultCharset());
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public CharSequence getCharSequence(Charset charset) throws IOException {
            return IOUtils.toString((Reader) this.origin);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
            return ((ReaderInputStream.Builder) ReaderInputStream.builder().setReader((Reader) this.origin)).setCharset(Charset.defaultCharset()).get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Reader getReader(Charset charset) throws IOException {
            return get();
        }
    }

    public static class URIOrigin extends AbstractOrigin<URI, URIOrigin> {
        private static final String SCHEME_HTTP = "http";
        private static final String SCHEME_HTTPS = "https";

        public URIOrigin(URI uri) {
            super(uri);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public File getFile() {
            return getPath().toFile();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
            URI uri = get();
            String scheme = uri.getScheme();
            FileSystemProvider fileSystemProvider = FileSystemProviders.installed().getFileSystemProvider(scheme);
            if (fileSystemProvider != null) {
                return Files.newInputStream(fileSystemProvider.getPath(uri), openOptionArr);
            }
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                return uri.toURL().openStream();
            }
            return Files.newInputStream(getPath(), openOptionArr);
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Path getPath() {
            return Paths.get(get());
        }
    }

    public static class WriterOrigin extends AbstractOrigin<Writer, WriterOrigin> {
        public WriterOrigin(Writer writer) {
            super(writer);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.apache.commons.io.build.AbstractOrigin
        public OutputStream getOutputStream(OpenOption... openOptionArr) throws IOException {
            return ((WriterOutputStream.Builder) WriterOutputStream.builder().setWriter((Writer) this.origin)).setCharset(Charset.defaultCharset()).get();
        }

        @Override // org.apache.commons.io.build.AbstractOrigin
        public Writer getWriter(Charset charset, OpenOption... openOptionArr) throws IOException {
            return get();
        }
    }

    protected AbstractOrigin(T t) {
        this.origin = (T) Objects.requireNonNull(t, "origin");
    }

    @Override // org.apache.commons.io.function.IOSupplier
    public T get() {
        return this.origin;
    }

    public byte[] getByteArray() throws IOException {
        return Files.readAllBytes(getPath());
    }

    public byte[] getByteArray(long j, int i) throws IOException {
        int i2;
        byte[] byteArray = getByteArray();
        int intExact = Math.toIntExact(j);
        if (intExact < 0 || i < 0 || (i2 = intExact + i) < 0 || i2 > byteArray.length) {
            throw new IllegalArgumentException("Couldn't read array (start: " + intExact + ", length: " + i + ", data length: " + byteArray.length + ").");
        }
        return Arrays.copyOfRange(byteArray, intExact, i2);
    }

    public CharSequence getCharSequence(Charset charset) throws IOException {
        return new String(getByteArray(), charset);
    }

    public File getFile() {
        throw new UnsupportedOperationException(String.format("%s#getFile() for %s origin %s", getSimpleClassName(), this.origin.getClass().getSimpleName(), this.origin));
    }

    public InputStream getInputStream(OpenOption... openOptionArr) throws IOException {
        return Files.newInputStream(getPath(), openOptionArr);
    }

    public OutputStream getOutputStream(OpenOption... openOptionArr) throws IOException {
        return Files.newOutputStream(getPath(), openOptionArr);
    }

    public Path getPath() {
        throw new UnsupportedOperationException(String.format("%s#getPath() for %s origin %s", getSimpleClassName(), this.origin.getClass().getSimpleName(), this.origin));
    }

    public RandomAccessFile getRandomAccessFile(OpenOption... openOptionArr) throws FileNotFoundException {
        return RandomAccessFileMode.valueOf(openOptionArr).create(getFile());
    }

    public Reader getReader(Charset charset) throws IOException {
        return Files.newBufferedReader(getPath(), charset);
    }

    private String getSimpleClassName() {
        return getClass().getSimpleName();
    }

    public Writer getWriter(Charset charset, OpenOption... openOptionArr) throws IOException {
        return Files.newBufferedWriter(getPath(), charset, openOptionArr);
    }

    public long size() throws IOException {
        return Files.size(getPath());
    }

    public String toString() {
        return getSimpleClassName() + "[" + this.origin.toString() + "]";
    }
}
