package org.apache.commons.io.file.spi;

import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0;

/* loaded from: classes4.dex */
public class FileSystemProviders {
    private static final FileSystemProviders INSTALLED = new FileSystemProviders(FileSystemProvider.installedProviders());
    private static final String SCHEME_FILE = "file";
    private final List<FileSystemProvider> providers;

    public static FileSystemProvider getFileSystemProvider(Path path) {
        return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path, "path")).getFileSystem().provider();
    }

    public static FileSystemProviders installed() {
        return INSTALLED;
    }

    private FileSystemProviders(List<FileSystemProvider> list) {
        this.providers = list == null ? Collections.EMPTY_LIST : list;
    }

    public FileSystemProvider getFileSystemProvider(final String str) {
        Objects.requireNonNull(str, "scheme");
        if (str.equalsIgnoreCase(SCHEME_FILE)) {
            return FileSystems.getDefault().provider();
        }
        return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2155m((Object) this.providers.stream().filter(new Predicate() { // from class: org.apache.commons.io.file.spi.FileSystemProviders$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FileSystemProvider) obj).getScheme().equalsIgnoreCase(str);
            }
        }).findFirst().orElse(null));
    }

    public FileSystemProvider getFileSystemProvider(URI uri) {
        return getFileSystemProvider(((URI) Objects.requireNonNull(uri, "uri")).getScheme());
    }

    public FileSystemProvider getFileSystemProvider(URL url) {
        return getFileSystemProvider(((URL) Objects.requireNonNull(url, "url")).getProtocol());
    }
}
