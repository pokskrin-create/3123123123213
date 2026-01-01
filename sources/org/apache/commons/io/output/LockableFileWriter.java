package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Objects;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.build.AbstractOrigin;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class LockableFileWriter extends Writer {
    private static final String LCK = ".lck";
    private final File lockFile;
    private final Writer out;

    public static class Builder extends AbstractStreamBuilder<LockableFileWriter, Builder> {
        private boolean append;
        private AbstractOrigin<?, ?> lockDirectory = newFileOrigin(FileUtils.getTempDirectoryPath());

        public Builder() {
            setBufferSizeDefault(1024);
            setBufferSize(1024);
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public LockableFileWriter get() throws IOException {
            return new LockableFileWriter(checkOrigin().getFile(), getCharset(), this.append, this.lockDirectory.getFile().toString());
        }

        public Builder setAppend(boolean z) {
            this.append = z;
            return this;
        }

        public Builder setLockDirectory(File file) {
            if (file == null) {
                file = FileUtils.getTempDirectory();
            }
            this.lockDirectory = newFileOrigin(file);
            return this;
        }

        public Builder setLockDirectory(String str) {
            if (str == null) {
                str = FileUtils.getTempDirectoryPath();
            }
            this.lockDirectory = newFileOrigin(str);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public LockableFileWriter(File file) throws IOException {
        this(file, false, (String) null);
    }

    @Deprecated
    public LockableFileWriter(File file, boolean z) throws IOException {
        this(file, z, (String) null);
    }

    @Deprecated
    public LockableFileWriter(File file, boolean z, String str) throws IOException {
        this(file, Charset.defaultCharset(), z, str);
    }

    @Deprecated
    public LockableFileWriter(File file, Charset charset) throws IOException {
        this(file, charset, false, (String) null);
    }

    @Deprecated
    public LockableFileWriter(File file, Charset charset, boolean z, String str) throws IOException {
        File absoluteFile = ((File) Objects.requireNonNull(file, "file")).getAbsoluteFile();
        if (absoluteFile.getParentFile() != null) {
            FileUtils.forceMkdir(absoluteFile.getParentFile());
        }
        if (absoluteFile.isDirectory()) {
            throw new IOException("File specified is a directory");
        }
        File file2 = new File(str == null ? FileUtils.getTempDirectoryPath() : str);
        FileUtils.forceMkdir(file2);
        testLockDir(file2);
        this.lockFile = new File(file2, absoluteFile.getName() + LCK);
        createLock();
        this.out = initWriter(absoluteFile, charset, z);
    }

    @Deprecated
    public LockableFileWriter(File file, String str) throws IOException {
        this(file, str, false, (String) null);
    }

    @Deprecated
    public LockableFileWriter(File file, String str, boolean z, String str2) throws IOException {
        this(file, Charsets.toCharset(str), z, str2);
    }

    @Deprecated
    public LockableFileWriter(String str) throws IOException {
        this(str, false, (String) null);
    }

    @Deprecated
    public LockableFileWriter(String str, boolean z) throws IOException {
        this(str, z, (String) null);
    }

    @Deprecated
    public LockableFileWriter(String str, boolean z, String str2) throws IOException {
        this(new File(str), z, str2);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.out.close();
        } finally {
            FileUtils.delete(this.lockFile);
        }
    }

    private void createLock() throws IOException {
        synchronized (LockableFileWriter.class) {
            if (!this.lockFile.createNewFile()) {
                throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
            }
            this.lockFile.deleteOnExit();
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    private Writer initWriter(File file, Charset charset, boolean z) throws IOException {
        boolean zExists = file.exists();
        try {
            return new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), z), Charsets.toCharset(charset));
        } catch (IOException | RuntimeException e) {
            FileUtils.deleteQuietly(this.lockFile);
            if (!zExists) {
                FileUtils.deleteQuietly(file);
            }
            throw e;
        }
    }

    private void testLockDir(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Could not find lockDir: " + file.getAbsolutePath());
        }
        if (file.canWrite()) {
            return;
        }
        throw new IOException("Could not write to lockDir: " + file.getAbsolutePath());
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        this.out.write(cArr);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        this.out.write(cArr, i, i2);
    }

    @Override // java.io.Writer
    public void write(int i) throws IOException {
        this.out.write(i);
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        this.out.write(str);
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        this.out.write(str, i, i2);
    }
}
