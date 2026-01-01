package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.io.RandomAccessFileMode;
import org.apache.commons.io.RandomAccessFiles;
import org.apache.commons.io.function.IOFunction;

/* loaded from: classes4.dex */
public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -547733176983104172L;
    private final long byteOffset;
    private final byte[] magicNumbers;

    public MagicNumberFileFilter(byte[] bArr) {
        this(bArr, 0L);
    }

    public MagicNumberFileFilter(byte[] bArr, long j) {
        Objects.requireNonNull(bArr, "magicNumbers");
        if (bArr.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (j < 0) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = (byte[]) bArr.clone();
        this.byteOffset = j;
    }

    public MagicNumberFileFilter(String str) {
        this(str, 0L);
    }

    public MagicNumberFileFilter(String str, long j) {
        Objects.requireNonNull(str, "magicNumber");
        if (str.isEmpty()) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (j < 0) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = str.getBytes(Charset.defaultCharset());
        this.byteOffset = j;
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        if (file == null || !file.isFile() || !file.canRead()) {
            return false;
        }
        try {
            return ((Boolean) RandomAccessFileMode.READ_ONLY.apply(file.toPath(), new IOFunction() { // from class: org.apache.commons.io.filefilter.MagicNumberFileFilter$$ExternalSyntheticLambda0
                @Override // org.apache.commons.io.function.IOFunction
                public final Object apply(Object obj) {
                    return this.f$0.m2184x5edb28f0((RandomAccessFile) obj);
                }
            })).booleanValue();
        } catch (IOException unused) {
            return false;
        }
    }

    /* renamed from: lambda$accept$0$org-apache-commons-io-filefilter-MagicNumberFileFilter, reason: not valid java name */
    /* synthetic */ Boolean m2184x5edb28f0(RandomAccessFile randomAccessFile) throws IOException {
        byte[] bArr = this.magicNumbers;
        return Boolean.valueOf(Arrays.equals(bArr, RandomAccessFiles.read(randomAccessFile, this.byteOffset, bArr.length)));
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        FileVisitResult fileVisitResult;
        if (path != null && Files.isRegularFile(path, new LinkOption[0]) && Files.isReadable(path)) {
            try {
                FileChannel fileChannelOpen = FileChannel.open(path, new OpenOption[0]);
                try {
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.magicNumbers.length);
                    fileChannelOpen.position(this.byteOffset);
                    int i = fileChannelOpen.read(byteBufferAllocate);
                    byte[] bArr = this.magicNumbers;
                    if (i != bArr.length) {
                        fileVisitResult = FileVisitResult.TERMINATE;
                        if (fileChannelOpen != null) {
                        }
                        return fileVisitResult;
                    }
                    fileVisitResult = toFileVisitResult(Arrays.equals(bArr, byteBufferAllocate.array()));
                    if (fileChannelOpen == null) {
                        return fileVisitResult;
                    }
                    fileChannelOpen.close();
                    return fileVisitResult;
                } finally {
                }
            } catch (IOException unused) {
            }
        }
        return FileVisitResult.TERMINATE;
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        return super.toString() + "(" + new String(this.magicNumbers, Charset.defaultCharset()) + "," + this.byteOffset + ")";
    }
}
