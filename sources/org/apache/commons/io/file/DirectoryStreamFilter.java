package org.apache.commons.io.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.Objects;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;

/* loaded from: classes4.dex */
public class DirectoryStreamFilter implements DirectoryStream.Filter<Path> {
    private final PathFilter pathFilter;

    @Override // java.nio.file.DirectoryStream.Filter
    public /* bridge */ /* synthetic */ boolean accept(Path path) throws IOException {
        return accept2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path));
    }

    public DirectoryStreamFilter(PathFilter pathFilter) {
        this.pathFilter = (PathFilter) Objects.requireNonNull(pathFilter, "pathFilter");
    }

    /* renamed from: accept, reason: avoid collision after fix types in other method */
    public boolean accept2(Path path) throws IOException {
        return this.pathFilter.accept(path, PathUtils.readBasicFileAttributes(path, PathUtils.EMPTY_LINK_OPTION_ARRAY)) == FileVisitResult.CONTINUE;
    }

    public PathFilter getPathFilter() {
        return this.pathFilter;
    }
}
