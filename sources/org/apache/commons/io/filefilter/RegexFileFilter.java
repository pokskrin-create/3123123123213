package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.apache.commons.io.IOCase;
import org.apache.tika.mime.MimeTypesReaderMetKeys;

/* loaded from: classes4.dex */
public class RegexFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 4269646126155225062L;
    private final transient Function<Path, String> pathToString;
    private final Pattern pattern;

    private static Pattern compile(String str, int i) {
        Objects.requireNonNull(str, MimeTypesReaderMetKeys.PATTERN_ATTR);
        return Pattern.compile(str, i);
    }

    private static int toFlags(IOCase iOCase) {
        return IOCase.isCaseSensitive(iOCase) ? 0 : 2;
    }

    public RegexFileFilter(Pattern pattern) {
        this(pattern, new RegexFileFilter$$ExternalSyntheticLambda1());
    }

    public RegexFileFilter(Pattern pattern, Function<Path, String> function) {
        Objects.requireNonNull(pattern, MimeTypesReaderMetKeys.PATTERN_ATTR);
        this.pattern = pattern;
        this.pathToString = function == null ? new Function() { // from class: org.apache.commons.io.filefilter.RegexFileFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Objects.toString((Path) obj);
            }
        } : function;
    }

    public RegexFileFilter(String str) {
        this(str, 0);
    }

    public RegexFileFilter(String str, int i) {
        this(compile(str, i));
    }

    public RegexFileFilter(String str, IOCase iOCase) {
        this(compile(str, toFlags(iOCase)));
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return this.pattern.matcher(str).matches();
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        String strApply = this.pathToString.apply(path);
        return toFileVisitResult(strApply != null && this.pattern.matcher(strApply).matches());
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        return "RegexFileFilter [pattern=" + this.pattern + "]";
    }
}
