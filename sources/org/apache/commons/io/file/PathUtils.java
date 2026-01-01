package org.apache.commons.io.file;

import j$.time.Duration;
import j$.time.Instant;
import j$.time.TimeConversions;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.temporal.TemporalAmount;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils$$ExternalSyntheticLambda21;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.RandomAccessFileMode;
import org.apache.commons.io.RandomAccessFiles;
import org.apache.commons.io.ThreadUtils;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.file.attribute.FileTimes;
import org.apache.commons.io.function.IOFunction;
import org.apache.commons.io.function.IOSupplier;

/* loaded from: classes4.dex */
public final class PathUtils {
    private static final OpenOption[] OPEN_OPTIONS_TRUNCATE = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
    private static final OpenOption[] OPEN_OPTIONS_APPEND = {StandardOpenOption.CREATE, StandardOpenOption.APPEND};
    public static final CopyOption[] EMPTY_COPY_OPTIONS = new CopyOption[0];
    public static final DeleteOption[] EMPTY_DELETE_OPTION_ARRAY = new DeleteOption[0];
    public static final FileAttribute<?>[] EMPTY_FILE_ATTRIBUTE_ARRAY = new FileAttribute[0];
    public static final FileVisitOption[] EMPTY_FILE_VISIT_OPTION_ARRAY = new FileVisitOption[0];
    public static final LinkOption[] EMPTY_LINK_OPTION_ARRAY = new LinkOption[0];

    @Deprecated
    public static final LinkOption[] NOFOLLOW_LINK_OPTION_ARRAY = {LinkOption.NOFOLLOW_LINKS};
    static final LinkOption NULL_LINK_OPTION = null;
    public static final OpenOption[] EMPTY_OPEN_OPTION_ARRAY = new OpenOption[0];
    public static final Path[] EMPTY_PATH_ARRAY = new Path[0];

    /* JADX INFO: Access modifiers changed from: private */
    static final class RelativeSortedPaths {
        final boolean equals;
        final List<Path> relativeFileList1;
        final List<Path> relativeFileList2;

        private static boolean equalsIgnoreFileSystem(List<Path> list, List<Path> list2) {
            if (list.size() != list2.size()) {
                return false;
            }
            Iterator<Path> it = list.iterator();
            Iterator<Path> it2 = list2.iterator();
            while (it.hasNext() && it2.hasNext()) {
                if (!equalsIgnoreFileSystem(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) it.next()), PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) it2.next()))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean equalsIgnoreFileSystem(Path path, Path path2) {
            FileSystem fileSystem = path.getFileSystem();
            FileSystem fileSystem2 = path2.getFileSystem();
            if (fileSystem == fileSystem2) {
                return path.equals(path2);
            }
            String separator = fileSystem.getSeparator();
            String separator2 = fileSystem2.getSeparator();
            String string = path.toString();
            String string2 = path2.toString();
            if (Objects.equals(separator, separator2)) {
                return Objects.equals(string, string2);
            }
            return extractKey(separator, string).equals(extractKey(separator2, string2));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static String extractKey(String str, String str2) {
            return str2.replaceAll("\\" + str, ">");
        }

        private RelativeSortedPaths(Path path, Path path2, int i, LinkOption[] linkOptionArr, FileVisitOption[] fileVisitOptionArr) throws IOException {
            List<Path> list;
            List<Path> list2 = null;
            if (path == null && path2 == null) {
                this.equals = true;
            } else {
                if ((path == null) ^ (path2 == null)) {
                    this.equals = false;
                } else {
                    boolean zNotExists = Files.notExists(path, linkOptionArr);
                    boolean zNotExists2 = Files.notExists(path2, linkOptionArr);
                    if (!zNotExists && !zNotExists2) {
                        AccumulatorPathVisitor accumulatorPathVisitorAccumulate = PathUtils.accumulate(path, i, fileVisitOptionArr);
                        AccumulatorPathVisitor accumulatorPathVisitorAccumulate2 = PathUtils.accumulate(path2, i, fileVisitOptionArr);
                        if (accumulatorPathVisitorAccumulate.getDirList().size() != accumulatorPathVisitorAccumulate2.getDirList().size() || accumulatorPathVisitorAccumulate.getFileList().size() != accumulatorPathVisitorAccumulate2.getFileList().size() || !equalsIgnoreFileSystem(accumulatorPathVisitorAccumulate.relativizeDirectories(path, true, null), accumulatorPathVisitorAccumulate2.relativizeDirectories(path2, true, null))) {
                            this.equals = false;
                        } else {
                            List<Path> listRelativizeFiles = accumulatorPathVisitorAccumulate.relativizeFiles(path, true, null);
                            List<Path> listRelativizeFiles2 = accumulatorPathVisitorAccumulate2.relativizeFiles(path2, true, null);
                            this.equals = equalsIgnoreFileSystem(listRelativizeFiles, listRelativizeFiles2);
                            list2 = listRelativizeFiles;
                            list = listRelativizeFiles2;
                            this.relativeFileList1 = list2;
                            this.relativeFileList2 = list;
                        }
                    } else {
                        this.equals = zNotExists && zNotExists2;
                    }
                }
            }
            list = null;
            this.relativeFileList1 = list2;
            this.relativeFileList2 = list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AccumulatorPathVisitor accumulate(Path path, int i, FileVisitOption[] fileVisitOptionArr) throws IOException {
        return (AccumulatorPathVisitor) visitFileTree(AccumulatorPathVisitor.builder().setDirectoryPostTransformer(new UnaryOperator() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda29
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PathUtils.stripTrailingSeparator((Path) obj);
            }
        }).get(), path, toFileVisitOptionSet(fileVisitOptionArr), i);
    }

    public static Counters.PathCounters cleanDirectory(Path path) throws IOException {
        return cleanDirectory(path, EMPTY_DELETE_OPTION_ARRAY);
    }

    public static Counters.PathCounters cleanDirectory(Path path, DeleteOption... deleteOptionArr) throws IOException {
        return ((CleaningPathVisitor) visitFileTree(new CleaningPathVisitor(Counters.longPathCounters(), deleteOptionArr, new String[0]), path)).getPathCounters();
    }

    private static int compareLastModifiedTimeTo(Path path, FileTime fileTime, LinkOption... linkOptionArr) throws IOException {
        return getLastModifiedTime(path, linkOptionArr).compareTo(fileTime);
    }

    public static boolean contentEquals(FileSystem fileSystem, FileSystem fileSystem2) throws IOException {
        if (Objects.equals(fileSystem, fileSystem2)) {
            return true;
        }
        List<Path> sortedList = toSortedList(fileSystem.getRootDirectories());
        List<Path> sortedList2 = toSortedList(fileSystem2.getRootDirectories());
        if (sortedList.size() != sortedList2.size()) {
            return false;
        }
        for (int i = 0; i < sortedList.size(); i++) {
            if (!directoryAndFileContentEquals(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) sortedList.get(i)), PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) sortedList2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public static long copy(IOSupplier<InputStream> iOSupplier, Path path, CopyOption... copyOptionArr) throws IOException {
        InputStream inputStream = iOSupplier.get();
        try {
            long jCopy = Files.copy(inputStream, path, copyOptionArr);
            if (inputStream != null) {
                inputStream.close();
            }
            return jCopy;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static Counters.PathCounters copyDirectory(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        Path absolutePath = path.toAbsolutePath();
        return ((CopyDirectoryVisitor) visitFileTree(new CopyDirectoryVisitor(Counters.longPathCounters(), absolutePath, path2, copyOptionArr), absolutePath)).getPathCounters();
    }

    public static Path copyFile(URL url, Path path, CopyOption... copyOptionArr) throws IOException {
        Objects.requireNonNull(url);
        copy(new FileUtils$$ExternalSyntheticLambda21(url), path, copyOptionArr);
        return path;
    }

    public static Path copyFileToDirectory(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        return Files.copy(path, resolve(path2, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path.getFileName(), "source file name"))), copyOptionArr);
    }

    public static Path copyFileToDirectory(URL url, Path path, CopyOption... copyOptionArr) throws IOException {
        Path pathResolve = path.resolve(FilenameUtils.getName(url.getFile()));
        Objects.requireNonNull(url);
        copy(new FileUtils$$ExternalSyntheticLambda21(url), pathResolve, copyOptionArr);
        return pathResolve;
    }

    public static Counters.PathCounters countDirectory(Path path) throws IOException {
        return ((CountingPathVisitor) visitFileTree(CountingPathVisitor.withLongCounters(), path)).getPathCounters();
    }

    public static Counters.PathCounters countDirectoryAsBigInteger(Path path) throws IOException {
        return ((CountingPathVisitor) visitFileTree(CountingPathVisitor.withBigIntegerCounters(), path)).getPathCounters();
    }

    public static Path createParentDirectories(Path path, FileAttribute<?>... fileAttributeArr) throws IOException {
        return createParentDirectories(path, LinkOption.NOFOLLOW_LINKS, fileAttributeArr);
    }

    public static Path createParentDirectories(Path path, LinkOption linkOption, FileAttribute<?>... fileAttributeArr) throws IOException {
        Path parent = getParent(path);
        if (linkOption != LinkOption.NOFOLLOW_LINKS) {
            parent = readIfSymbolicLink(parent);
        }
        if (parent == null) {
            return null;
        }
        return linkOption == null ? Files.exists(parent, new LinkOption[0]) : Files.exists(parent, linkOption) ? parent : Files.createDirectories(parent, fileAttributeArr);
    }

    public static Path current() {
        return Paths.get(".", new String[0]);
    }

    public static Counters.PathCounters delete(Path path) throws IOException {
        return delete(path, EMPTY_DELETE_OPTION_ARRAY);
    }

    public static Counters.PathCounters delete(Path path, DeleteOption... deleteOptionArr) throws IOException {
        return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS) ? deleteDirectory(path, deleteOptionArr) : deleteFile(path, deleteOptionArr);
    }

    public static Counters.PathCounters delete(Path path, LinkOption[] linkOptionArr, DeleteOption... deleteOptionArr) throws IOException {
        return Files.isDirectory(path, linkOptionArr) ? deleteDirectory(path, linkOptionArr, deleteOptionArr) : deleteFile(path, linkOptionArr, deleteOptionArr);
    }

    public static Counters.PathCounters deleteDirectory(Path path) throws IOException {
        return deleteDirectory(path, EMPTY_DELETE_OPTION_ARRAY);
    }

    public static Counters.PathCounters deleteDirectory(final Path path, final DeleteOption... deleteOptionArr) throws IOException {
        final LinkOption[] linkOptionArrNoFollowLinkOptionArray = noFollowLinkOptionArray();
        return (Counters.PathCounters) withPosixFileAttributes(getParent(path), linkOptionArrNoFollowLinkOptionArray, overrideReadOnly(deleteOptionArr), new IOFunction() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda31
            @Override // org.apache.commons.io.function.IOFunction
            public final Object apply(Object obj) {
                return ((DeletingPathVisitor) PathUtils.visitFileTree(new DeletingPathVisitor(Counters.longPathCounters(), linkOptionArrNoFollowLinkOptionArray, deleteOptionArr, new String[0]), path)).getPathCounters();
            }
        });
    }

    public static Counters.PathCounters deleteDirectory(Path path, LinkOption[] linkOptionArr, DeleteOption... deleteOptionArr) throws IOException {
        return ((DeletingPathVisitor) visitFileTree(new DeletingPathVisitor(Counters.longPathCounters(), linkOptionArr, deleteOptionArr, new String[0]), path)).getPathCounters();
    }

    public static Counters.PathCounters deleteFile(Path path) throws IOException {
        return deleteFile(path, EMPTY_DELETE_OPTION_ARRAY);
    }

    public static Counters.PathCounters deleteFile(Path path, DeleteOption... deleteOptionArr) throws IOException {
        return deleteFile(path, noFollowLinkOptionArray(), deleteOptionArr);
    }

    public static Counters.PathCounters deleteFile(Path path, LinkOption[] linkOptionArr, DeleteOption... deleteOptionArr) throws IOException {
        if (Files.isDirectory(path, linkOptionArr)) {
            PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1739m$2();
            throw NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(path.toString());
        }
        Counters.PathCounters pathCountersLongPathCounters = Counters.longPathCounters();
        long size = 0;
        long size2 = (!exists(path, linkOptionArr) || Files.isSymbolicLink(path)) ? 0L : Files.size(path);
        try {
            if (Files.deleteIfExists(path)) {
                pathCountersLongPathCounters.getFileCounter().increment();
                pathCountersLongPathCounters.getByteCounter().add(size2);
                return pathCountersLongPathCounters;
            }
        } catch (AccessDeniedException unused) {
        }
        Path parent = getParent(path);
        PosixFileAttributes posixFileAttributes = null;
        try {
            if (overrideReadOnly(deleteOptionArr)) {
                posixFileAttributes = readPosixFileAttributes(parent, linkOptionArr);
                setReadOnly(path, false, linkOptionArr);
            }
            if (exists(path, linkOptionArr) && !Files.isSymbolicLink(path)) {
                size = Files.size(path);
            }
            if (Files.deleteIfExists(path)) {
                pathCountersLongPathCounters.getFileCounter().increment();
                pathCountersLongPathCounters.getByteCounter().add(size);
            }
            return pathCountersLongPathCounters;
        } finally {
            if (posixFileAttributes != null) {
                Files.setPosixFilePermissions(parent, posixFileAttributes.permissions());
            }
        }
    }

    public static void deleteOnExit(Path path) {
        PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path)).toFile().deleteOnExit();
    }

    public static boolean directoryAndFileContentEquals(Path path, Path path2) throws IOException {
        return directoryAndFileContentEquals(path, path2, EMPTY_LINK_OPTION_ARRAY, EMPTY_OPEN_OPTION_ARRAY, EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryAndFileContentEquals(Path path, Path path2, LinkOption[] linkOptionArr, OpenOption[] openOptionArr, FileVisitOption[] fileVisitOptionArr) throws IOException {
        int iBinarySearch;
        if (path == null && path2 == null) {
            return true;
        }
        if (path == null || path2 == null) {
            return false;
        }
        if (notExists(path, new LinkOption[0]) && notExists(path2, new LinkOption[0])) {
            return true;
        }
        RelativeSortedPaths relativeSortedPaths = new RelativeSortedPaths(path, path2, Integer.MAX_VALUE, linkOptionArr, fileVisitOptionArr);
        if (!relativeSortedPaths.equals) {
            return false;
        }
        List<Path> list = relativeSortedPaths.relativeFileList1;
        List<Path> list2 = relativeSortedPaths.relativeFileList2;
        boolean zIsSameFileSystem = isSameFileSystem(path, path2);
        Iterator<Path> it = list.iterator();
        while (it.hasNext()) {
            Path pathM1714m = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) it.next());
            if (zIsSameFileSystem) {
                iBinarySearch = Collections.binarySearch(list2, pathM1714m);
            } else {
                iBinarySearch = Collections.binarySearch(list2, pathM1714m, Comparator.comparing(new Function() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda32
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Path path3 = (Path) obj;
                        return PathUtils.RelativeSortedPaths.extractKey(path3.getFileSystem().getSeparator(), path3.toString());
                    }
                }));
            }
            if (iBinarySearch < 0) {
                throw new IllegalStateException("Unexpected mismatch.");
            }
            if ((zIsSameFileSystem && !fileContentEquals(path.resolve(pathM1714m), path2.resolve(pathM1714m), linkOptionArr, openOptionArr)) || !fileContentEquals(path.resolve(pathM1714m.toString()), path2.resolve(pathM1714m.toString()), linkOptionArr, openOptionArr)) {
                return false;
            }
        }
        return true;
    }

    public static boolean directoryContentEquals(Path path, Path path2) throws IOException {
        return directoryContentEquals(path, path2, Integer.MAX_VALUE, EMPTY_LINK_OPTION_ARRAY, EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryContentEquals(Path path, Path path2, int i, LinkOption[] linkOptionArr, FileVisitOption[] fileVisitOptionArr) throws IOException {
        return new RelativeSortedPaths(path, path2, i, linkOptionArr, fileVisitOptionArr).equals;
    }

    private static boolean exists(Path path, LinkOption... linkOptionArr) {
        if (path != null) {
            if (linkOptionArr != null) {
                if (Files.exists(path, linkOptionArr)) {
                    return true;
                }
            } else if (Files.exists(path, new LinkOption[0])) {
                return true;
            }
        }
        return false;
    }

    public static boolean fileContentEquals(Path path, Path path2) throws IOException {
        return fileContentEquals(path, path2, EMPTY_LINK_OPTION_ARRAY, EMPTY_OPEN_OPTION_ARRAY);
    }

    public static boolean fileContentEquals(Path path, Path path2, LinkOption[] linkOptionArr, OpenOption[] openOptionArr) throws IOException {
        if (path == null && path2 == null) {
            return true;
        }
        if (path == null || path2 == null) {
            return false;
        }
        Path pathNormalize = path.normalize();
        Path pathNormalize2 = path2.normalize();
        boolean zExists = exists(pathNormalize, linkOptionArr);
        if (zExists != exists(pathNormalize2, linkOptionArr)) {
            return false;
        }
        if (!zExists) {
            return true;
        }
        if (Files.isDirectory(pathNormalize, linkOptionArr)) {
            throw new IOException("Can't compare directories, only files: " + pathNormalize);
        }
        if (Files.isDirectory(pathNormalize2, linkOptionArr)) {
            throw new IOException("Can't compare directories, only files: " + pathNormalize2);
        }
        if (Files.size(pathNormalize) != Files.size(pathNormalize2)) {
            return false;
        }
        if (isSameFileSystem(path, path2) && path.equals(path2)) {
            return true;
        }
        try {
            RandomAccessFile randomAccessFileCreate = RandomAccessFileMode.READ_ONLY.create(path.toRealPath(linkOptionArr));
            try {
                RandomAccessFile randomAccessFileCreate2 = RandomAccessFileMode.READ_ONLY.create(path2.toRealPath(linkOptionArr));
                try {
                    boolean zContentEquals = RandomAccessFiles.contentEquals(randomAccessFileCreate, randomAccessFileCreate2);
                    if (randomAccessFileCreate2 != null) {
                        randomAccessFileCreate2.close();
                    }
                    if (randomAccessFileCreate != null) {
                        randomAccessFileCreate.close();
                    }
                    return zContentEquals;
                } finally {
                }
            } finally {
            }
        } catch (UnsupportedOperationException unused) {
            InputStream inputStreamNewInputStream = Files.newInputStream(pathNormalize, openOptionArr);
            try {
                InputStream inputStreamNewInputStream2 = Files.newInputStream(pathNormalize2, openOptionArr);
                try {
                    boolean zContentEquals2 = IOUtils.contentEquals(inputStreamNewInputStream, inputStreamNewInputStream2);
                    if (inputStreamNewInputStream2 != null) {
                        inputStreamNewInputStream2.close();
                    }
                    if (inputStreamNewInputStream != null) {
                        inputStreamNewInputStream.close();
                    }
                    return zContentEquals2;
                } finally {
                }
            } catch (Throwable th) {
                if (inputStreamNewInputStream != null) {
                    try {
                        inputStreamNewInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public static Path[] filter(PathFilter pathFilter, Path... pathArr) {
        Objects.requireNonNull(pathFilter, "filter");
        if (pathArr == null) {
            return EMPTY_PATH_ARRAY;
        }
        return (Path[]) ((List) filterPaths(pathFilter, Stream.of((Object[]) pathArr), Collectors.toList())).toArray(EMPTY_PATH_ARRAY);
    }

    private static <R, A> R filterPaths(final PathFilter pathFilter, Stream<Path> stream, Collector<? super Path, A, R> collector) {
        Objects.requireNonNull(pathFilter, "filter");
        Objects.requireNonNull(collector, "collector");
        if (stream == null) {
            return (R) Stream.empty().collect(collector);
        }
        return (R) stream.filter(new Predicate() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PathUtils.lambda$filterPaths$2(pathFilter, (Path) obj);
            }
        }).collect(collector);
    }

    static /* synthetic */ boolean lambda$filterPaths$2(PathFilter pathFilter, Path path) {
        if (path != null) {
            try {
                if (pathFilter.accept(path, readBasicFileAttributes(path)) == FileVisitResult.CONTINUE) {
                    return true;
                }
            } catch (IOException unused) {
            }
        }
        return false;
    }

    public static List<AclEntry> getAclEntryList(Path path) throws IOException {
        AclFileAttributeView aclFileAttributeView = getAclFileAttributeView(path, new LinkOption[0]);
        if (aclFileAttributeView == null) {
            return null;
        }
        return aclFileAttributeView.getAcl();
    }

    public static AclFileAttributeView getAclFileAttributeView(Path path, LinkOption... linkOptionArr) {
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2145m((Object) Files.getFileAttributeView(path, NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$4(), linkOptionArr));
    }

    public static String getBaseName(Path path) {
        Path fileName;
        if (path == null || (fileName = path.getFileName()) == null) {
            return null;
        }
        return FilenameUtils.removeExtension(fileName.toString());
    }

    public static DosFileAttributeView getDosFileAttributeView(Path path, LinkOption... linkOptionArr) {
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2146m((Object) Files.getFileAttributeView(path, NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1(), linkOptionArr));
    }

    public static String getExtension(Path path) {
        String fileNameString = getFileNameString(path);
        if (fileNameString != null) {
            return FilenameUtils.getExtension(fileNameString);
        }
        return null;
    }

    public static <R> R getFileName(Path path, Function<Path, R> function) {
        Path fileName = path != null ? path.getFileName() : null;
        if (fileName != null) {
            return function.apply(fileName);
        }
        return null;
    }

    public static String getFileNameString(Path path) {
        return (String) getFileName(path, new Function() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda27
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Path) obj).toString();
            }
        });
    }

    public static FileTime getLastModifiedFileTime(File file) throws IOException {
        return getLastModifiedFileTime(file.toPath(), null, EMPTY_LINK_OPTION_ARRAY);
    }

    public static FileTime getLastModifiedFileTime(Path path, FileTime fileTime, LinkOption... linkOptionArr) throws IOException {
        return Files.exists(path, new LinkOption[0]) ? getLastModifiedTime(path, linkOptionArr) : fileTime;
    }

    public static FileTime getLastModifiedFileTime(Path path, LinkOption... linkOptionArr) throws IOException {
        return getLastModifiedFileTime(path, null, linkOptionArr);
    }

    public static FileTime getLastModifiedFileTime(URI uri) throws IOException {
        return getLastModifiedFileTime(Paths.get(uri), null, EMPTY_LINK_OPTION_ARRAY);
    }

    public static FileTime getLastModifiedFileTime(URL url) throws URISyntaxException, IOException {
        return getLastModifiedFileTime(url.toURI());
    }

    private static FileTime getLastModifiedTime(Path path, LinkOption... linkOptionArr) throws IOException {
        return Files.getLastModifiedTime(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path, "path")), linkOptionArr);
    }

    private static Path getParent(Path path) {
        if (path == null) {
            return null;
        }
        return path.getParent();
    }

    public static PosixFileAttributeView getPosixFileAttributeView(Path path, LinkOption... linkOptionArr) {
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2151m((Object) Files.getFileAttributeView(path, NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$6(), linkOptionArr));
    }

    public static Path getTempDirectory() {
        return Paths.get(FileUtils.getTempDirectoryPath(), new String[0]);
    }

    public static boolean isDirectory(Path path, LinkOption... linkOptionArr) {
        return path != null && Files.isDirectory(path, linkOptionArr);
    }

    public static boolean isEmpty(Path path) throws IOException {
        return Files.isDirectory(path, new LinkOption[0]) ? isEmptyDirectory(path) : isEmptyFile(path);
    }

    public static boolean isEmptyDirectory(Path path) throws IOException {
        DirectoryStream directoryStreamNewDirectoryStream = Files.newDirectoryStream(path);
        try {
            boolean z = !directoryStreamNewDirectoryStream.iterator().hasNext();
            if (directoryStreamNewDirectoryStream != null) {
                directoryStreamNewDirectoryStream.close();
            }
            return z;
        } catch (Throwable th) {
            if (directoryStreamNewDirectoryStream != null) {
                try {
                    directoryStreamNewDirectoryStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static boolean isEmptyFile(Path path) throws IOException {
        return Files.size(path) <= 0;
    }

    public static boolean isNewer(Path path, ChronoZonedDateTime<?> chronoZonedDateTime, LinkOption... linkOptionArr) throws IOException {
        Objects.requireNonNull(chronoZonedDateTime, "czdt");
        return isNewer(path, chronoZonedDateTime.toInstant(), linkOptionArr);
    }

    public static boolean isNewer(Path path, FileTime fileTime, LinkOption... linkOptionArr) throws IOException {
        return !notExists(path, new LinkOption[0]) && compareLastModifiedTimeTo(path, fileTime, linkOptionArr) > 0;
    }

    public static boolean isNewer(Path path, Instant instant, LinkOption... linkOptionArr) throws IOException {
        return isNewer(path, FileTime.from(TimeConversions.convert(instant)), linkOptionArr);
    }

    public static boolean isNewer(Path path, long j, LinkOption... linkOptionArr) throws IOException {
        return isNewer(path, FileTime.fromMillis(j), linkOptionArr);
    }

    public static boolean isNewer(Path path, Path path2) throws IOException {
        return isNewer(path, getLastModifiedTime(path2, new LinkOption[0]), new LinkOption[0]);
    }

    public static boolean isOlder(Path path, FileTime fileTime, LinkOption... linkOptionArr) throws IOException {
        return !notExists(path, new LinkOption[0]) && compareLastModifiedTimeTo(path, fileTime, linkOptionArr) < 0;
    }

    public static boolean isOlder(Path path, Instant instant, LinkOption... linkOptionArr) throws IOException {
        return isOlder(path, FileTime.from(TimeConversions.convert(instant)), linkOptionArr);
    }

    public static boolean isOlder(Path path, long j, LinkOption... linkOptionArr) throws IOException {
        return isOlder(path, FileTime.fromMillis(j), linkOptionArr);
    }

    public static boolean isOlder(Path path, Path path2) throws IOException {
        return isOlder(path, getLastModifiedTime(path2, new LinkOption[0]), new LinkOption[0]);
    }

    public static boolean isPosix(Path path, LinkOption... linkOptionArr) {
        return exists(path, linkOptionArr) && readPosixFileAttributes(path, linkOptionArr) != null;
    }

    public static boolean isRegularFile(Path path, LinkOption... linkOptionArr) {
        return path != null && Files.isRegularFile(path, linkOptionArr);
    }

    static boolean isSameFileSystem(Path path, Path path2) {
        return path.getFileSystem() == path2.getFileSystem();
    }

    public static DirectoryStream<Path> newDirectoryStream(Path path, PathFilter pathFilter) throws IOException {
        return Files.newDirectoryStream(path, (DirectoryStream.Filter<? super Path>) new DirectoryStreamFilter(pathFilter));
    }

    public static OutputStream newOutputStream(Path path, boolean z) throws IOException {
        return newOutputStream(path, EMPTY_LINK_OPTION_ARRAY, z ? OPEN_OPTIONS_APPEND : OPEN_OPTIONS_TRUNCATE);
    }

    static OutputStream newOutputStream(Path path, LinkOption[] linkOptionArr, OpenOption... openOptionArr) throws IOException {
        if (!exists(path, linkOptionArr)) {
            createParentDirectories(path, (linkOptionArr == null || linkOptionArr.length <= 0) ? NULL_LINK_OPTION : linkOptionArr[0], new FileAttribute[0]);
        }
        if (openOptionArr == null) {
            openOptionArr = EMPTY_OPEN_OPTION_ARRAY;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(openOptionArr));
        if (linkOptionArr == null) {
            linkOptionArr = EMPTY_LINK_OPTION_ARRAY;
        }
        arrayList.addAll(Arrays.asList(linkOptionArr));
        return Files.newOutputStream(path, (OpenOption[]) arrayList.toArray(EMPTY_OPEN_OPTION_ARRAY));
    }

    public static LinkOption[] noFollowLinkOptionArray() {
        return (LinkOption[]) NOFOLLOW_LINK_OPTION_ARRAY.clone();
    }

    private static boolean notExists(Path path, LinkOption... linkOptionArr) {
        return Files.notExists(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path, "path")), linkOptionArr);
    }

    static /* synthetic */ boolean lambda$overrideReadOnly$3(DeleteOption deleteOption) {
        return deleteOption == StandardDeleteOption.OVERRIDE_READ_ONLY;
    }

    private static boolean overrideReadOnly(DeleteOption... deleteOptionArr) {
        if (deleteOptionArr == null) {
            return false;
        }
        return Stream.of((Object[]) deleteOptionArr).anyMatch(new Predicate() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PathUtils.lambda$overrideReadOnly$3((DeleteOption) obj);
            }
        });
    }

    public static <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> cls, LinkOption... linkOptionArr) {
        if (path == null) {
            return null;
        }
        try {
            return (A) Files.readAttributes(path, cls, linkOptionArr);
        } catch (IOException | UnsupportedOperationException unused) {
            return null;
        }
    }

    public static BasicFileAttributes readBasicFileAttributes(Path path) throws IOException {
        return Files.readAttributes(path, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(), new LinkOption[0]);
    }

    public static BasicFileAttributes readBasicFileAttributes(Path path, LinkOption... linkOptionArr) {
        return readAttributes(path, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(), linkOptionArr);
    }

    @Deprecated
    public static BasicFileAttributes readBasicFileAttributesUnchecked(Path path) {
        return readBasicFileAttributes(path, EMPTY_LINK_OPTION_ARRAY);
    }

    public static DosFileAttributes readDosFileAttributes(Path path, LinkOption... linkOptionArr) {
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2147m((Object) readAttributes(path, NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$2(), linkOptionArr));
    }

    private static Path readIfSymbolicLink(Path path) throws IOException {
        if (path != null) {
            return Files.isSymbolicLink(path) ? Files.readSymbolicLink(path) : path;
        }
        return null;
    }

    public static BasicFileAttributes readOsFileAttributes(Path path, LinkOption... linkOptionArr) {
        PosixFileAttributes posixFileAttributes = readPosixFileAttributes(path, linkOptionArr);
        return posixFileAttributes != null ? posixFileAttributes : readDosFileAttributes(path, linkOptionArr);
    }

    public static PosixFileAttributes readPosixFileAttributes(Path path, LinkOption... linkOptionArr) {
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2152m((Object) readAttributes(path, NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$3(), linkOptionArr));
    }

    public static String readString(Path path, Charset charset) throws IOException {
        return new String(Files.readAllBytes(path), Charsets.toCharset(charset));
    }

    static List<Path> relativize(Collection<Path> collection, final Path path, boolean z, Comparator<? super Path> comparator) {
        Stream<Path> stream = collection.stream();
        Objects.requireNonNull(path);
        Stream map = stream.map(new Function() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda28
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return path.relativize((Path) obj);
            }
        });
        if (z) {
            map = comparator == null ? map.sorted() : map.sorted(comparator);
        }
        return (List) map.collect(Collectors.toList());
    }

    private static Path requireExists(Path path, String str, LinkOption... linkOptionArr) {
        Objects.requireNonNull(path, str);
        if (exists(path, linkOptionArr)) {
            return path;
        }
        throw new IllegalArgumentException("File system element for parameter '" + str + "' does not exist: '" + path + "'");
    }

    static Path resolve(Path path, Path path2) {
        FileSystem fileSystem = path.getFileSystem();
        FileSystem fileSystem2 = path2.getFileSystem();
        if (fileSystem == fileSystem2) {
            return path.resolve(path2);
        }
        String separator = fileSystem2.getSeparator();
        String separator2 = fileSystem.getSeparator();
        String string = path2.toString();
        if (!Objects.equals(separator, separator2)) {
            string = string.replace(separator, separator2);
        }
        return path.resolve(string);
    }

    private static boolean setDosReadOnly(Path path, boolean z, LinkOption... linkOptionArr) throws IOException {
        DosFileAttributeView dosFileAttributeView = getDosFileAttributeView(path, linkOptionArr);
        if (dosFileAttributeView == null) {
            return false;
        }
        dosFileAttributeView.setReadOnly(z);
        return true;
    }

    public static void setLastModifiedTime(Path path, Path path2) throws IOException {
        Objects.requireNonNull(path, "sourceFile");
        Files.setLastModifiedTime(path2, getLastModifiedTime(path, new LinkOption[0]));
    }

    private static boolean setPosixDeletePermissions(Path path, boolean z, LinkOption... linkOptionArr) throws IOException {
        return setPosixPermissions(path, z, Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE), linkOptionArr);
    }

    private static boolean setPosixPermissions(Path path, boolean z, List<PosixFilePermission> list, LinkOption... linkOptionArr) throws IOException {
        if (path == null) {
            return false;
        }
        Set posixFilePermissions = Files.getPosixFilePermissions(path, linkOptionArr);
        HashSet hashSet = new HashSet(posixFilePermissions);
        if (z) {
            hashSet.addAll(list);
        } else {
            hashSet.removeAll(list);
        }
        if (hashSet.equals(posixFilePermissions)) {
            return true;
        }
        Files.setPosixFilePermissions(path, hashSet);
        return true;
    }

    private static void setPosixReadOnlyFile(Path path, boolean z, LinkOption... linkOptionArr) throws IOException {
        Set posixFilePermissions = Files.getPosixFilePermissions(path, linkOptionArr);
        List listAsList = Arrays.asList(PosixFilePermission.OWNER_READ);
        List listAsList2 = Arrays.asList(PosixFilePermission.OWNER_WRITE);
        if (z) {
            posixFilePermissions.addAll(listAsList);
            posixFilePermissions.removeAll(listAsList2);
        } else {
            posixFilePermissions.addAll(listAsList);
            posixFilePermissions.addAll(listAsList2);
        }
        Files.setPosixFilePermissions(path, posixFilePermissions);
    }

    public static Path setReadOnly(Path path, boolean z, LinkOption... linkOptionArr) throws IOException {
        if (!setDosReadOnly(path, z, linkOptionArr)) {
            Path parent = getParent(path);
            if (!isPosix(parent, linkOptionArr)) {
                throw new IOException(String.format("DOS or POSIX file operations not available for '%s', linkOptions %s", path, Arrays.toString(linkOptionArr)));
            }
            if (z) {
                setPosixReadOnlyFile(path, z, linkOptionArr);
                setPosixDeletePermissions(parent, false, linkOptionArr);
            } else {
                setPosixDeletePermissions(parent, true, linkOptionArr);
            }
        }
        return path;
    }

    public static long sizeOf(Path path) throws IOException {
        requireExists(path, "path", new LinkOption[0]);
        return Files.isDirectory(path, new LinkOption[0]) ? sizeOfDirectory(path) : Files.size(path);
    }

    public static BigInteger sizeOfAsBigInteger(Path path) throws IOException {
        requireExists(path, "path", new LinkOption[0]);
        return Files.isDirectory(path, new LinkOption[0]) ? sizeOfDirectoryAsBigInteger(path) : BigInteger.valueOf(Files.size(path));
    }

    public static long sizeOfDirectory(Path path) throws IOException {
        return countDirectory(path).getByteCounter().getLong().longValue();
    }

    public static BigInteger sizeOfDirectoryAsBigInteger(Path path) throws IOException {
        return countDirectoryAsBigInteger(path).getByteCounter().getBigInteger();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Path stripTrailingSeparator(Path path) {
        String separator = path.getFileSystem().getSeparator();
        String fileNameString = getFileNameString(path);
        return (fileNameString == null || !fileNameString.endsWith(separator)) ? path : path.resolveSibling(fileNameString.substring(0, fileNameString.length() - 1));
    }

    static Set<FileVisitOption> toFileVisitOptionSet(FileVisitOption... fileVisitOptionArr) {
        return fileVisitOptionArr == null ? EnumSet.noneOf(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$5()) : (Set) Stream.of((Object[]) fileVisitOptionArr).collect(Collectors.toSet());
    }

    private static <T> List<T> toList(Iterable<T> iterable) {
        return (List) StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    private static List<Path> toSortedList(Iterable<Path> iterable) {
        List<Path> list = toList(iterable);
        Collections.sort(list);
        return list;
    }

    public static Path touch(Path path) throws IOException {
        Objects.requireNonNull(path, "file");
        if (!Files.exists(path, new LinkOption[0])) {
            createParentDirectories(path, new FileAttribute[0]);
            Files.createFile(path, new FileAttribute[0]);
            return path;
        }
        FileTimes.setLastModifiedTime(path);
        return path;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T t, Path path) throws IOException {
        Files.walkFileTree(path, t);
        return t;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T t, Path path, Set<FileVisitOption> set, int i) throws IOException {
        Files.walkFileTree(path, set, i, t);
        return t;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T t, String str, String... strArr) throws IOException {
        return (T) visitFileTree(t, Paths.get(str, strArr));
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T t, URI uri) throws IOException {
        return (T) visitFileTree(t, Paths.get(uri));
    }

    public static boolean waitFor(Path path, Duration duration, LinkOption... linkOptionArr) {
        Objects.requireNonNull(path, "file");
        Instant instantPlus = Instant.now().plus((TemporalAmount) duration);
        boolean z = false;
        while (!exists(path, linkOptionArr)) {
            try {
                Instant instantNow = Instant.now();
                if (instantNow.isAfter(instantPlus)) {
                    return false;
                }
                try {
                    ThreadUtils.sleep(Duration.ofMillis(Math.min(100L, instantPlus.minusMillis(instantNow.toEpochMilli()).toEpochMilli())));
                } catch (InterruptedException unused) {
                    z = true;
                } catch (Exception unused2) {
                }
            } finally {
                if (z) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return exists(path, linkOptionArr);
    }

    public static Stream<Path> walk(Path path, final PathFilter pathFilter, int i, final boolean z, FileVisitOption... fileVisitOptionArr) throws IOException {
        return Files.walk(path, i, fileVisitOptionArr).filter(new Predicate() { // from class: org.apache.commons.io.file.PathUtils$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PathUtils.lambda$walk$4(pathFilter, z, (Path) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$walk$4(PathFilter pathFilter, boolean z, Path path) {
        return pathFilter.accept(path, z ? readBasicFileAttributesUnchecked(path) : null) == FileVisitResult.CONTINUE;
    }

    private static <R> R withPosixFileAttributes(Path path, LinkOption[] linkOptionArr, boolean z, IOFunction<PosixFileAttributes, R> iOFunction) throws IOException {
        PosixFileAttributes posixFileAttributes = z ? readPosixFileAttributes(path, linkOptionArr) : null;
        try {
            return iOFunction.apply(posixFileAttributes);
        } finally {
            if (posixFileAttributes != null && path != null && Files.exists(path, linkOptionArr)) {
                Files.setPosixFilePermissions(path, posixFileAttributes.permissions());
            }
        }
    }

    public static Path writeString(Path path, CharSequence charSequence, Charset charset, OpenOption... openOptionArr) throws IOException {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(charSequence, "charSequence");
        Files.write(path, String.valueOf(charSequence).getBytes(Charsets.toCharset(charset)), openOptionArr);
        return path;
    }

    private PathUtils() {
    }
}
