package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: classes4.dex */
public class TrueFileFilter implements IOFileFilter, Serializable {
    public static final IOFileFilter INSTANCE;
    private static final String TO_STRING = Boolean.TRUE.toString();
    public static final IOFileFilter TRUE;
    private static final long serialVersionUID = 8782512160909720199L;

    @Override // org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return true;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return true;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter
    public IOFileFilter and(IOFileFilter iOFileFilter) {
        return iOFileFilter;
    }

    static {
        TrueFileFilter trueFileFilter = new TrueFileFilter();
        TRUE = trueFileFilter;
        INSTANCE = trueFileFilter;
    }

    protected TrueFileFilter() {
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        return FileVisitResult.CONTINUE;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter
    public IOFileFilter negate() {
        return FalseFileFilter.INSTANCE;
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter
    public IOFileFilter or(IOFileFilter iOFileFilter) {
        return INSTANCE;
    }

    public String toString() {
        return TO_STRING;
    }
}
