package org.apache.commons.io.output;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import java.util.function.Supplier;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.file.PathUtils;

/* loaded from: classes4.dex */
public class DeferredFileOutputStream extends ThresholdingOutputStream {
    private boolean closed;
    private OutputStream currentOutputStream;
    private final Path directory;
    private ByteArrayOutputStream memoryOutputStream;
    private Path outputPath;
    private final String prefix;
    private final String suffix;

    public static class Builder extends AbstractStreamBuilder<DeferredFileOutputStream, Builder> {
        private Path directory;
        private Path outputFile;
        private String prefix;
        private String suffix;
        private int threshold;

        public Builder() {
            setBufferSizeDefault(1024);
            setBufferSize(1024);
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public DeferredFileOutputStream get() {
            return new DeferredFileOutputStream(this.threshold, this.outputFile, this.prefix, this.suffix, this.directory, getBufferSize());
        }

        public Builder setDirectory(File file) {
            this.directory = DeferredFileOutputStream.toPath(file, (Supplier<Path>) null);
            return this;
        }

        public Builder setDirectory(Path path) {
            this.directory = DeferredFileOutputStream.toPath(path, (Supplier<Path>) null);
            return this;
        }

        public Builder setOutputFile(File file) {
            this.outputFile = DeferredFileOutputStream.toPath(file, (Supplier<Path>) null);
            return this;
        }

        public Builder setOutputFile(Path path) {
            this.outputFile = DeferredFileOutputStream.toPath(path, (Supplier<Path>) null);
            return this;
        }

        public Builder setPrefix(String str) {
            this.prefix = str;
            return this;
        }

        public Builder setSuffix(String str) {
            this.suffix = str;
            return this;
        }

        public Builder setThreshold(int i) {
            this.threshold = i;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static int checkBufferSize(int i) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException("Initial buffer size must be at least 0.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Path toPath(File file, Supplier<Path> supplier) {
        if (file != null) {
            return file.toPath();
        }
        if (supplier == null) {
            return null;
        }
        return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) supplier.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Path toPath(Path path, Supplier<Path> supplier) {
        if (path != null) {
            return path;
        }
        if (supplier == null) {
            return null;
        }
        return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) supplier.get());
    }

    @Deprecated
    public DeferredFileOutputStream(int i, File file) {
        this(i, file, (String) null, (String) null, (File) null, 1024);
    }

    private DeferredFileOutputStream(int i, File file, String str, String str2, File file2, int i2) {
        super(i);
        this.outputPath = toPath(file, (Supplier<Path>) null);
        this.prefix = str;
        this.suffix = str2;
        this.directory = toPath(file2, (Supplier<Path>) new Supplier() { // from class: org.apache.commons.io.output.DeferredFileOutputStream$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return PathUtils.getTempDirectory();
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(checkBufferSize(i2));
        this.memoryOutputStream = byteArrayOutputStream;
        this.currentOutputStream = byteArrayOutputStream;
    }

    @Deprecated
    public DeferredFileOutputStream(int i, int i2, File file) {
        this(i, file, (String) null, (String) null, (File) null, i2);
    }

    @Deprecated
    public DeferredFileOutputStream(int i, int i2, String str, String str2, File file) {
        this(i, (File) null, (String) Objects.requireNonNull(str, "prefix"), str2, file, i2);
    }

    private DeferredFileOutputStream(int i, Path path, String str, String str2, Path path2, int i2) {
        super(i);
        this.outputPath = toPath(path, (Supplier<Path>) null);
        this.prefix = str;
        this.suffix = str2;
        this.directory = toPath(path2, (Supplier<Path>) new Supplier() { // from class: org.apache.commons.io.output.DeferredFileOutputStream$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return PathUtils.getTempDirectory();
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(checkBufferSize(i2));
        this.memoryOutputStream = byteArrayOutputStream;
        this.currentOutputStream = byteArrayOutputStream;
    }

    @Deprecated
    public DeferredFileOutputStream(int i, String str, String str2, File file) {
        this(i, (File) null, (String) Objects.requireNonNull(str, "prefix"), str2, file, 1024);
    }

    @Override // org.apache.commons.io.output.ThresholdingOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.closed = true;
    }

    public byte[] getData() {
        ByteArrayOutputStream byteArrayOutputStream = this.memoryOutputStream;
        if (byteArrayOutputStream != null) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public File getFile() {
        Path path = this.outputPath;
        if (path != null) {
            return path.toFile();
        }
        return null;
    }

    public Path getPath() {
        return this.outputPath;
    }

    @Override // org.apache.commons.io.output.ThresholdingOutputStream
    @Deprecated
    protected OutputStream getStream() throws IOException {
        return this.currentOutputStream;
    }

    public boolean isInMemory() {
        return !isThresholdExceeded();
    }

    @Override // org.apache.commons.io.output.ThresholdingOutputStream
    protected void thresholdReached() throws IOException {
        String str = this.prefix;
        if (str != null) {
            this.outputPath = Files.createTempFile(this.directory, str, this.suffix, new FileAttribute[0]);
        }
        PathUtils.createParentDirectories(this.outputPath, null, PathUtils.EMPTY_FILE_ATTRIBUTE_ARRAY);
        OutputStream outputStreamNewOutputStream = Files.newOutputStream(this.outputPath, new OpenOption[0]);
        try {
            this.memoryOutputStream.writeTo(outputStreamNewOutputStream);
            this.currentOutputStream = outputStreamNewOutputStream;
            this.memoryOutputStream = null;
        } catch (IOException e) {
            outputStreamNewOutputStream.close();
            throw e;
        }
    }

    public InputStream toInputStream() throws IOException {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        }
        if (isInMemory()) {
            return this.memoryOutputStream.toInputStream();
        }
        return Files.newInputStream(this.outputPath, new OpenOption[0]);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        }
        if (isInMemory()) {
            this.memoryOutputStream.writeTo(outputStream);
        } else {
            Files.copy(this.outputPath, outputStream);
        }
    }
}
