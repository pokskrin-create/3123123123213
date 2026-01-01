package org.apache.commons.io.filefilter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Objects;
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0;

/* loaded from: classes4.dex */
public class PathMatcherFileFilter extends AbstractFileFilter {
    private final PathMatcher pathMatcher;

    public PathMatcherFileFilter(PathMatcher pathMatcher) {
        this.pathMatcher = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2141m(Objects.requireNonNull(pathMatcher, "pathMatcher"));
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return file != null && matches(file.toPath());
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, java.nio.file.PathMatcher
    public boolean matches(Path path) {
        return this.pathMatcher.matches(path);
    }
}
