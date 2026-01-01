package org.apache.commons.io.filefilter;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.file.PathUtils;

/* loaded from: classes4.dex */
public class NameFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 176844364689077340L;
    private final IOCase ioCase;
    private final String[] names;

    public NameFileFilter(List<String> list) {
        this(list, (IOCase) null);
    }

    public NameFileFilter(List<String> list, IOCase iOCase) {
        Objects.requireNonNull(list, "names");
        this.names = (String[]) list.toArray(EMPTY_STRING_ARRAY);
        this.ioCase = toIOCase(iOCase);
    }

    public NameFileFilter(String str) {
        this(str, IOCase.SENSITIVE);
    }

    public NameFileFilter(String... strArr) {
        this(strArr, IOCase.SENSITIVE);
    }

    public NameFileFilter(String str, IOCase iOCase) {
        Objects.requireNonNull(str, AppMeasurementSdk.ConditionalUserProperty.NAME);
        this.names = new String[]{str};
        this.ioCase = toIOCase(iOCase);
    }

    public NameFileFilter(String[] strArr, IOCase iOCase) {
        Objects.requireNonNull(strArr, "names");
        this.names = (String[]) strArr.clone();
        this.ioCase = toIOCase(iOCase);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return file != null && acceptBaseName(file.getName());
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return acceptBaseName(str);
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        return toFileVisitResult(acceptBaseName(PathUtils.getFileNameString(path)));
    }

    private boolean acceptBaseName(final String str) {
        return Stream.of((Object[]) this.names).anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.NameFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2185xeb0d6bf5(str, (String) obj);
            }
        });
    }

    /* renamed from: lambda$acceptBaseName$0$org-apache-commons-io-filefilter-NameFileFilter, reason: not valid java name */
    /* synthetic */ boolean m2185xeb0d6bf5(String str, String str2) {
        return this.ioCase.checkEquals(str, str2);
    }

    private IOCase toIOCase(IOCase iOCase) {
        return IOCase.value(iOCase, IOCase.SENSITIVE);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        append(this.names, sb);
        sb.append(")");
        return sb.toString();
    }
}
