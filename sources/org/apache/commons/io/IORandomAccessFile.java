package org.apache.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class IORandomAccessFile extends RandomAccessFile {
    private final File file;
    private final String mode;

    public IORandomAccessFile(File file, String str) throws FileNotFoundException {
        super(file, str);
        this.file = file;
        this.mode = str;
    }

    public IORandomAccessFile(String str, String str2) throws FileNotFoundException {
        super(str, str2);
        this.file = str != null ? new File(str) : null;
        this.mode = str2;
    }

    public File getFile() {
        return this.file;
    }

    public String getMode() {
        return this.mode;
    }

    public String toString() {
        return Objects.toString(this.file);
    }
}
