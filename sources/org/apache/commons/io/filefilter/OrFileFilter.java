package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class OrFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
    private static final long serialVersionUID = 5767770777065432721L;
    private final List<IOFileFilter> fileFilters;

    public OrFileFilter() {
        this(0);
    }

    private OrFileFilter(ArrayList<IOFileFilter> arrayList) {
        this.fileFilters = (List) Objects.requireNonNull(arrayList, "initialList");
    }

    private OrFileFilter(int i) {
        this((ArrayList<IOFileFilter>) new ArrayList(i));
    }

    public OrFileFilter(IOFileFilter... iOFileFilterArr) {
        this(((IOFileFilter[]) Objects.requireNonNull(iOFileFilterArr, "fileFilters")).length);
        addFileFilter(iOFileFilterArr);
    }

    public OrFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        this(2);
        addFileFilter(iOFileFilter);
        addFileFilter(iOFileFilter2);
    }

    public OrFileFilter(List<IOFileFilter> list) {
        this((ArrayList<IOFileFilter>) new ArrayList((Collection) Objects.requireNonNull(list, "fileFilters")));
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(final File file) {
        return this.fileFilters.stream().anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.OrFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IOFileFilter) obj).accept(file);
            }
        });
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(final File file, final String str) {
        return this.fileFilters.stream().anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.OrFileFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IOFileFilter) obj).accept(file, str);
            }
        });
    }

    static /* synthetic */ boolean lambda$accept$2(Path path, BasicFileAttributes basicFileAttributes, IOFileFilter iOFileFilter) {
        return iOFileFilter.accept(path, basicFileAttributes) == FileVisitResult.CONTINUE;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(final Path path, final BasicFileAttributes basicFileAttributes) {
        return toDefaultFileVisitResult(this.fileFilters.stream().anyMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.OrFileFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return OrFileFilter.lambda$accept$2(path, basicFileAttributes, (IOFileFilter) obj);
            }
        }));
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void addFileFilter(IOFileFilter iOFileFilter) {
        this.fileFilters.add((IOFileFilter) Objects.requireNonNull(iOFileFilter, "fileFilter"));
    }

    public void addFileFilter(IOFileFilter... iOFileFilterArr) {
        Stream.of((Object[]) Objects.requireNonNull(iOFileFilterArr, "fileFilters")).forEach(new Consumer() { // from class: org.apache.commons.io.filefilter.OrFileFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.addFileFilter((IOFileFilter) obj);
            }
        });
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public List<IOFileFilter> getFileFilters() {
        return Collections.unmodifiableList(this.fileFilters);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public boolean removeFileFilter(IOFileFilter iOFileFilter) {
        return this.fileFilters.remove(iOFileFilter);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void setFileFilters(List<IOFileFilter> list) {
        this.fileFilters.clear();
        this.fileFilters.addAll((Collection) Objects.requireNonNull(list, "fileFilters"));
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        append(this.fileFilters, sb);
        sb.append(")");
        return sb.toString();
    }
}
