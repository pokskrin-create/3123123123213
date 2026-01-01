package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.file.PathUtils;

@Deprecated
/* loaded from: classes4.dex */
public class WildcardFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -5037645902506953517L;
    private final String[] wildcards;

    public WildcardFilter(List<String> list) {
        Objects.requireNonNull(list, "wildcards");
        this.wildcards = (String[]) list.toArray(EMPTY_STRING_ARRAY);
    }

    public WildcardFilter(String str) {
        Objects.requireNonNull(str, "wildcard");
        this.wildcards = new String[]{str};
    }

    public WildcardFilter(String... strArr) {
        Objects.requireNonNull(strArr, "wildcards");
        this.wildcards = (String[]) strArr.clone();
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(final File file) {
        if (file.isDirectory()) {
            return false;
        }
        return Stream.of((Object[]) this.wildcards).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.WildcardFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FilenameUtils.wildcardMatch(file.getName(), (String) obj);
            }
        });
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, final String str) {
        if (file == null || !new File(file, str).isDirectory()) {
            return Stream.of((Object[]) this.wildcards).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.WildcardFilter$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return FilenameUtils.wildcardMatch(str, (String) obj);
                }
            });
        }
        return false;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(final Path path, BasicFileAttributes basicFileAttributes) {
        if (Files.isDirectory(path, new LinkOption[0])) {
            return FileVisitResult.TERMINATE;
        }
        return toDefaultFileVisitResult(Stream.of((Object[]) this.wildcards).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.WildcardFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FilenameUtils.wildcardMatch(PathUtils.getFileNameString(path), (String) obj);
            }
        }));
    }
}
