package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.build.AbstractSupplier;
import org.apache.commons.io.file.PathUtils;

/* loaded from: classes4.dex */
public class WildcardFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -7426486598995782105L;
    private final IOCase ioCase;
    private final String[] wildcards;

    public static class Builder extends AbstractSupplier<WildcardFileFilter, Builder> {
        private IOCase ioCase = IOCase.SENSITIVE;
        private String[] wildcards;

        @Override // org.apache.commons.io.function.IOSupplier
        public WildcardFileFilter get() {
            return new WildcardFileFilter(this.ioCase, this.wildcards);
        }

        public Builder setIoCase(IOCase iOCase) {
            this.ioCase = IOCase.value(iOCase, IOCase.SENSITIVE);
            return this;
        }

        public Builder setWildcards(List<String> list) {
            setWildcards((String[]) ((List) WildcardFileFilter.requireWildcards(list)).toArray(IOFileFilter.EMPTY_STRING_ARRAY));
            return this;
        }

        public Builder setWildcards(String... strArr) {
            this.wildcards = (String[]) WildcardFileFilter.requireWildcards(strArr);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T requireWildcards(T t) {
        return (T) Objects.requireNonNull(t, "wildcards");
    }

    private WildcardFileFilter(IOCase iOCase, String... strArr) {
        this.wildcards = (String[]) ((String[]) requireWildcards(strArr)).clone();
        this.ioCase = IOCase.value(iOCase, IOCase.SENSITIVE);
    }

    @Deprecated
    public WildcardFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    @Deprecated
    public WildcardFileFilter(List<String> list, IOCase iOCase) {
        this(iOCase, (String[]) ((List) requireWildcards(list)).toArray(EMPTY_STRING_ARRAY));
    }

    @Deprecated
    public WildcardFileFilter(String str) {
        this(IOCase.SENSITIVE, (String) requireWildcards(str));
    }

    @Deprecated
    public WildcardFileFilter(String... strArr) {
        this(IOCase.SENSITIVE, strArr);
    }

    @Deprecated
    public WildcardFileFilter(String str, IOCase iOCase) {
        this(iOCase, str);
    }

    @Deprecated
    public WildcardFileFilter(String[] strArr, IOCase iOCase) {
        this(iOCase, strArr);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return accept(file.getName());
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return accept(str);
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        return toFileVisitResult(accept(PathUtils.getFileNameString(path)));
    }

    private boolean accept(final String str) {
        return Stream.of((Object[]) this.wildcards).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.WildcardFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2190xf9a09e68(str, (String) obj);
            }
        });
    }

    /* renamed from: lambda$accept$0$org-apache-commons-io-filefilter-WildcardFileFilter, reason: not valid java name */
    /* synthetic */ boolean m2190xf9a09e68(String str, String str2) {
        return FilenameUtils.wildcardMatch(str, str2, this.ioCase);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        append(this.wildcards, sb);
        sb.append(")");
        return sb.toString();
    }
}
