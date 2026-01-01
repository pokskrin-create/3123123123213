package org.apache.commons.io.filefilter;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.file.PathVisitor;
import org.apache.commons.io.function.IOSupplier;

/* loaded from: classes4.dex */
public abstract class AbstractFileFilter implements IOFileFilter, PathVisitor {
    private final FileVisitResult onAccept;
    private final FileVisitResult onReject;

    @Override // java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
        return postVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
    }

    @Override // java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return preVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
    }

    @Override // java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return visitFile2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
    }

    @Override // java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult visitFileFailed(Path path, IOException iOException) throws IOException {
        return visitFileFailed2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
    }

    static FileVisitResult toDefaultFileVisitResult(boolean z) {
        return z ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
    }

    public AbstractFileFilter() {
        this(FileVisitResult.CONTINUE, FileVisitResult.TERMINATE);
    }

    protected AbstractFileFilter(FileVisitResult fileVisitResult, FileVisitResult fileVisitResult2) {
        this.onAccept = fileVisitResult;
        this.onReject = fileVisitResult2;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        Objects.requireNonNull(file, "file");
        return accept(file.getParentFile(), file.getName());
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        Objects.requireNonNull(str, AppMeasurementSdk.ConditionalUserProperty.NAME);
        return accept(new File(file, str));
    }

    void append(List<?> list, StringBuilder sb) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(list.get(i));
        }
    }

    void append(Object[] objArr, StringBuilder sb) {
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(objArr[i]);
        }
    }

    FileVisitResult get(IOSupplier<FileVisitResult> iOSupplier) {
        try {
            return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1710m((Object) iOSupplier.get());
        } catch (IOException e) {
            return handle(e);
        }
    }

    protected FileVisitResult handle(Throwable th) {
        return FileVisitResult.TERMINATE;
    }

    /* renamed from: postVisitDirectory, reason: avoid collision after fix types in other method */
    public FileVisitResult postVisitDirectory2(Path path, IOException iOException) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /* renamed from: preVisitDirectory, reason: avoid collision after fix types in other method */
    public FileVisitResult preVisitDirectory2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return accept(path, basicFileAttributes);
    }

    FileVisitResult toFileVisitResult(boolean z) {
        return z ? this.onAccept : this.onReject;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    /* renamed from: visitFile, reason: avoid collision after fix types in other method */
    public FileVisitResult visitFile2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return accept(path, basicFileAttributes);
    }

    /* renamed from: visitFileFailed, reason: avoid collision after fix types in other method */
    public FileVisitResult visitFileFailed2(Path path, IOException iOException) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
