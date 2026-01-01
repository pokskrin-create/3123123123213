package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.file.PathUtils;

/* loaded from: classes4.dex */
public class PrefixFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 8533897440809599867L;
    private final IOCase isCase;
    private final String[] prefixes;

    public PrefixFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(List<String> list, IOCase iOCase) {
        Objects.requireNonNull(list, "prefixes");
        this.prefixes = (String[]) list.toArray(EMPTY_STRING_ARRAY);
        this.isCase = IOCase.value(iOCase, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String str) {
        this(str, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String... strArr) {
        this(strArr, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String str, IOCase iOCase) {
        Objects.requireNonNull(str, "prefix");
        this.prefixes = new String[]{str};
        this.isCase = IOCase.value(iOCase, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String[] strArr, IOCase iOCase) {
        Objects.requireNonNull(strArr, "prefixes");
        this.prefixes = (String[]) strArr.clone();
        this.isCase = IOCase.value(iOCase, IOCase.SENSITIVE);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return accept(file == null ? null : file.getName());
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return accept(str);
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        return toFileVisitResult(accept((File) PathUtils.getFileName(path, new Function() { // from class: org.apache.commons.io.filefilter.PrefixFileFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Path) obj).toFile();
            }
        })));
    }

    private boolean accept(final String str) {
        return Stream.of((Object[]) this.prefixes).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.PrefixFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2187x16e103a0(str, (String) obj);
            }
        });
    }

    /* renamed from: lambda$accept$0$org-apache-commons-io-filefilter-PrefixFileFilter, reason: not valid java name */
    /* synthetic */ boolean m2187x16e103a0(String str, String str2) {
        return this.isCase.checkStartsWith(str, str2);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        append(this.prefixes, sb);
        sb.append(")");
        return sb.toString();
    }
}
