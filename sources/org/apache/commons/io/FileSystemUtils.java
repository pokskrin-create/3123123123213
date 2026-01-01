package org.apache.commons.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Deprecated
/* loaded from: classes4.dex */
public class FileSystemUtils {
    @Deprecated
    public static long freeSpace(String str) throws IOException {
        return getFreeSpace(str);
    }

    @Deprecated
    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1L);
    }

    @Deprecated
    public static long freeSpaceKb(long j) throws IOException {
        return freeSpaceKb(FileUtils.current().getAbsolutePath(), j);
    }

    @Deprecated
    public static long freeSpaceKb(String str) throws IOException {
        return freeSpaceKb(str, -1L);
    }

    @Deprecated
    public static long freeSpaceKb(String str, long j) throws IOException {
        return getFreeSpace(str) / 1024;
    }

    static long getFreeSpace(String str) throws IOException {
        Path path = Paths.get((String) Objects.requireNonNull(str, "pathStr"), new String[0]);
        if (Files.exists(path, new LinkOption[0])) {
            return Files.getFileStore(path.toAbsolutePath()).getUsableSpace();
        }
        throw new IllegalArgumentException(path.toString());
    }

    @Deprecated
    public FileSystemUtils() {
    }
}
