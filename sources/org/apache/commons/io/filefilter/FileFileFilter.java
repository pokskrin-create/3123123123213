package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: classes4.dex */
public class FileFileFilter extends AbstractFileFilter implements Serializable {

    @Deprecated
    public static final IOFileFilter FILE;
    public static final IOFileFilter INSTANCE;
    private static final long serialVersionUID = 5345244090827540862L;

    static {
        FileFileFilter fileFileFilter = new FileFileFilter();
        INSTANCE = fileFileFilter;
        FILE = fileFileFilter;
    }

    protected FileFileFilter() {
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        return file != null && file.isFile();
    }

    @Override // org.apache.commons.io.filefilter.IOFileFilter, org.apache.commons.io.file.PathFilter
    public FileVisitResult accept(Path path, BasicFileAttributes basicFileAttributes) {
        boolean z = false;
        if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
            z = true;
        }
        return toFileVisitResult(z);
    }
}
