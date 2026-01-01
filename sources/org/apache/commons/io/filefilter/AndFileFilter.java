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
public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
    private static final long serialVersionUID = 7215974688563965257L;
    private final List<IOFileFilter> fileFilters;

    public AndFileFilter() {
        this(0);
    }

    private AndFileFilter(ArrayList<IOFileFilter> arrayList) {
        this.fileFilters = (List) Objects.requireNonNull(arrayList, "initialList");
    }

    private AndFileFilter(int i) {
        this((ArrayList<IOFileFilter>) new ArrayList(i));
    }

    public AndFileFilter(IOFileFilter... iOFileFilterArr) {
        this(((IOFileFilter[]) Objects.requireNonNull(iOFileFilterArr, "fileFilters")).length);
        addFileFilter(iOFileFilterArr);
    }

    public AndFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        this(2);
        addFileFilter(iOFileFilter);
        addFileFilter(iOFileFilter2);
    }

    public AndFileFilter(List<IOFileFilter> list) {
        this((ArrayList<IOFileFilter>) new ArrayList((Collection) Objects.requireNonNull(list, "fileFilters")));
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(final File file) {
        return !isEmpty() && this.fileFilters.stream().allMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.AndFileFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IOFileFilter) obj).accept(file);
            }
        });
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(final File file, final String str) {
        return !isEmpty() && this.fileFilters.stream().allMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.AndFileFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IOFileFilter) obj).accept(file, str);
            }
        });
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(final Path path, final BasicFileAttributes basicFileAttributes) {
        return isEmpty() ? FileVisitResult.TERMINATE : toDefaultFileVisitResult(this.fileFilters.stream().allMatch(new Predicate() { // from class: org.apache.commons.io.filefilter.AndFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndFileFilter.lambda$accept$2(path, basicFileAttributes, (IOFileFilter) obj);
            }
        }));
    }

    static /* synthetic */ boolean lambda$accept$2(Path path, BasicFileAttributes basicFileAttributes, IOFileFilter iOFileFilter) {
        return iOFileFilter.accept(path, basicFileAttributes) == FileVisitResult.CONTINUE;
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void addFileFilter(IOFileFilter iOFileFilter) {
        this.fileFilters.add((IOFileFilter) Objects.requireNonNull(iOFileFilter, "fileFilter"));
    }

    public void addFileFilter(IOFileFilter... iOFileFilterArr) {
        Stream.of((Object[]) Objects.requireNonNull(iOFileFilterArr, "fileFilters")).forEach(new Consumer() { // from class: org.apache.commons.io.filefilter.AndFileFilter$$ExternalSyntheticLambda1
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

    private boolean isEmpty() {
        return this.fileFilters.isEmpty();
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public boolean removeFileFilter(IOFileFilter iOFileFilter) {
        return this.fileFilters.remove(iOFileFilter);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void setFileFilters(List<IOFileFilter> list) {
        this.fileFilters.clear();
        this.fileFilters.addAll(list);
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
