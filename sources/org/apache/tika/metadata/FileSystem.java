package org.apache.tika.metadata;

/* loaded from: classes4.dex */
public interface FileSystem {
    public static final String PREFIX = "fs:";
    public static final Property CREATED = Property.externalDate("fs:created");
    public static final Property MODIFIED = Property.externalDate("fs:modified");
    public static final Property ACCESSED = Property.externalDate("fs:accessed");
}
