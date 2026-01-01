package org.apache.commons.io.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.UnaryOperator;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.build.AbstractSupplier;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.SimplePathVisitor;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SymbolicLinkFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.function.IOBiFunction;

/* loaded from: classes4.dex */
public class CountingPathVisitor extends SimplePathVisitor {
    static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final PathFilter directoryFilter;
    private final UnaryOperator<Path> directoryPostTransformer;
    private final PathFilter fileFilter;
    private final Counters.PathCounters pathCounters;

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
        return postVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return preVisitDirectory(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public /* bridge */ /* synthetic */ FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return visitFile(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
    }

    public static abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> extends SimplePathVisitor.AbstractBuilder<T, B> {
        private Counters.PathCounters pathCounters = CountingPathVisitor.defaultPathCounters();
        private PathFilter fileFilter = CountingPathVisitor.defaultFileFilter();
        private PathFilter directoryFilter = CountingPathVisitor.defaultDirectoryFilter();
        private UnaryOperator<Path> directoryPostTransformer = CountingPathVisitor.defaultDirectoryTransformer();

        @Override // org.apache.commons.io.file.SimplePathVisitor.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractSupplier setVisitFileFailedFunction(IOBiFunction iOBiFunction) {
            return super.setVisitFileFailedFunction(iOBiFunction);
        }

        PathFilter getDirectoryFilter() {
            return this.directoryFilter;
        }

        UnaryOperator<Path> getDirectoryPostTransformer() {
            return this.directoryPostTransformer;
        }

        PathFilter getFileFilter() {
            return this.fileFilter;
        }

        Counters.PathCounters getPathCounters() {
            return this.pathCounters;
        }

        public B setDirectoryFilter(PathFilter pathFilter) {
            if (pathFilter == null) {
                pathFilter = CountingPathVisitor.defaultDirectoryFilter();
            }
            this.directoryFilter = pathFilter;
            return asThis();
        }

        public B setDirectoryPostTransformer(UnaryOperator<Path> unaryOperator) {
            if (unaryOperator == null) {
                unaryOperator = CountingPathVisitor.defaultDirectoryTransformer();
            }
            this.directoryPostTransformer = unaryOperator;
            return asThis();
        }

        public B setFileFilter(PathFilter pathFilter) {
            if (pathFilter == null) {
                pathFilter = CountingPathVisitor.defaultFileFilter();
            }
            this.fileFilter = pathFilter;
            return asThis();
        }

        public B setPathCounters(Counters.PathCounters pathCounters) {
            if (pathCounters == null) {
                pathCounters = CountingPathVisitor.defaultPathCounters();
            }
            this.pathCounters = pathCounters;
            return asThis();
        }
    }

    public static class Builder extends AbstractBuilder<CountingPathVisitor, Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public CountingPathVisitor get() {
            return new CountingPathVisitor(this);
        }
    }

    static IOFileFilter defaultDirectoryFilter() {
        return TrueFileFilter.INSTANCE;
    }

    static UnaryOperator<Path> defaultDirectoryTransformer() {
        return UnaryOperator.identity();
    }

    static IOFileFilter defaultFileFilter() {
        return new SymbolicLinkFileFilter(FileVisitResult.TERMINATE, FileVisitResult.CONTINUE);
    }

    static Counters.PathCounters defaultPathCounters() {
        return Counters.longPathCounters();
    }

    public static CountingPathVisitor withBigIntegerCounters() {
        return new Builder().setPathCounters(Counters.bigIntegerPathCounters()).get();
    }

    public static CountingPathVisitor withLongCounters() {
        return new Builder().setPathCounters(Counters.longPathCounters()).get();
    }

    CountingPathVisitor(AbstractBuilder<?, ?> abstractBuilder) {
        super(abstractBuilder);
        this.pathCounters = abstractBuilder.getPathCounters();
        this.fileFilter = abstractBuilder.getFileFilter();
        this.directoryFilter = abstractBuilder.getDirectoryFilter();
        this.directoryPostTransformer = abstractBuilder.getDirectoryPostTransformer();
    }

    public CountingPathVisitor(Counters.PathCounters pathCounters) {
        this(new Builder().setPathCounters(pathCounters));
    }

    public CountingPathVisitor(Counters.PathCounters pathCounters, PathFilter pathFilter, PathFilter pathFilter2) {
        this.pathCounters = (Counters.PathCounters) Objects.requireNonNull(pathCounters, "pathCounters");
        this.fileFilter = (PathFilter) Objects.requireNonNull(pathFilter, "fileFilter");
        this.directoryFilter = (PathFilter) Objects.requireNonNull(pathFilter2, "directoryFilter");
        this.directoryPostTransformer = UnaryOperator.identity();
    }

    @Deprecated
    public CountingPathVisitor(Counters.PathCounters pathCounters, PathFilter pathFilter, PathFilter pathFilter2, IOBiFunction<Path, IOException, FileVisitResult> iOBiFunction) {
        super(iOBiFunction);
        this.pathCounters = (Counters.PathCounters) Objects.requireNonNull(pathCounters, "pathCounters");
        this.fileFilter = (PathFilter) Objects.requireNonNull(pathFilter, "fileFilter");
        this.directoryFilter = (PathFilter) Objects.requireNonNull(pathFilter2, "directoryFilter");
        this.directoryPostTransformer = UnaryOperator.identity();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CountingPathVisitor) {
            return Objects.equals(this.pathCounters, ((CountingPathVisitor) obj).pathCounters);
        }
        return false;
    }

    public Counters.PathCounters getPathCounters() {
        return this.pathCounters;
    }

    public int hashCode() {
        return Objects.hash(this.pathCounters);
    }

    /* renamed from: postVisitDirectory, reason: avoid collision after fix types in other method */
    public FileVisitResult postVisitDirectory2(Path path, IOException iOException) throws IOException {
        updateDirCounter(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(this.directoryPostTransformer.apply(path)), iOException);
        return FileVisitResult.CONTINUE;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        return this.directoryFilter.accept(path, basicFileAttributes) != FileVisitResult.CONTINUE ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
    }

    public String toString() {
        return this.pathCounters.toString();
    }

    protected void updateDirCounter(Path path, IOException iOException) {
        this.pathCounters.getDirectoryCounter().increment();
    }

    protected void updateFileCounters(Path path, BasicFileAttributes basicFileAttributes) {
        this.pathCounters.getFileCounter().increment();
        this.pathCounters.getByteCounter().add(basicFileAttributes.size());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        if (Files.exists(path, new LinkOption[0]) && this.fileFilter.accept(path, basicFileAttributes) == FileVisitResult.CONTINUE) {
            updateFileCounters(path, basicFileAttributes);
        }
        return FileVisitResult.CONTINUE;
    }
}
