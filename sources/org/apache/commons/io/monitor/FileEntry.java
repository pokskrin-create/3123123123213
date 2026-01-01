package org.apache.commons.io.monitor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.attribute.FileTimes;

/* loaded from: classes4.dex */
public class FileEntry implements Serializable {
    static final FileEntry[] EMPTY_FILE_ENTRY_ARRAY = new FileEntry[0];
    private static final long serialVersionUID = -2505664948818681153L;
    private FileEntry[] children;
    private boolean directory;
    private boolean exists;
    private final File file;
    private SerializableFileTime lastModified;
    private long length;
    private String name;
    private final FileEntry parent;

    public FileEntry(File file) {
        this(null, file);
    }

    public FileEntry(FileEntry fileEntry, File file) {
        this.lastModified = SerializableFileTime.EPOCH;
        this.file = (File) Objects.requireNonNull(file, "file");
        this.parent = fileEntry;
        this.name = file.getName();
    }

    public FileEntry[] getChildren() {
        FileEntry[] fileEntryArr = this.children;
        return fileEntryArr != null ? fileEntryArr : EMPTY_FILE_ENTRY_ARRAY;
    }

    public File getFile() {
        return this.file;
    }

    public long getLastModified() {
        return this.lastModified.toMillis();
    }

    public FileTime getLastModifiedFileTime() {
        return this.lastModified.unwrap();
    }

    public long getLength() {
        return this.length;
    }

    public int getLevel() {
        FileEntry fileEntry = this.parent;
        if (fileEntry == null) {
            return 0;
        }
        return fileEntry.getLevel() + 1;
    }

    public String getName() {
        return this.name;
    }

    public FileEntry getParent() {
        return this.parent;
    }

    public boolean isDirectory() {
        return this.directory;
    }

    public boolean isExists() {
        return this.exists;
    }

    public FileEntry newChildInstance(File file) {
        return new FileEntry(this, file);
    }

    public boolean refresh(File file) {
        boolean z = this.exists;
        SerializableFileTime serializableFileTime = this.lastModified;
        boolean z2 = this.directory;
        long j = this.length;
        this.name = file.getName();
        boolean zExists = Files.exists(file.toPath(), new LinkOption[0]);
        this.exists = zExists;
        this.directory = zExists && file.isDirectory();
        try {
            setLastModified(this.exists ? FileUtils.lastModifiedFileTime(file) : FileTimes.EPOCH);
        } catch (IOException unused) {
            setLastModified(SerializableFileTime.EPOCH);
        }
        this.length = (!this.exists || this.directory) ? 0L : file.length();
        return (this.exists == z && this.lastModified.equals(serializableFileTime) && this.directory == z2 && this.length == j) ? false : true;
    }

    public void setChildren(FileEntry... fileEntryArr) {
        this.children = fileEntryArr;
    }

    public void setDirectory(boolean z) {
        this.directory = z;
    }

    public void setExists(boolean z) {
        this.exists = z;
    }

    public void setLastModified(FileTime fileTime) {
        setLastModified(new SerializableFileTime(fileTime));
    }

    public void setLastModified(long j) {
        setLastModified(FileTime.fromMillis(j));
    }

    void setLastModified(SerializableFileTime serializableFileTime) {
        this.lastModified = serializableFileTime;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public void setName(String str) {
        this.name = str;
    }
}
