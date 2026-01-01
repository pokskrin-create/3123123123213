package org.apache.tika.pipes.fetcher.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Map;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.FileSystem;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.pipes.fetcher.AbstractFetcher;
import org.apache.tika.pipes.fetcher.fs.config.FileSystemFetcherConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class FileSystemFetcher extends AbstractFetcher implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) FileSystemFetcher.class);
    private Path basePath = null;
    private boolean extractFileSystemMetadata = false;

    @Override // org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
    }

    public FileSystemFetcher() {
    }

    public FileSystemFetcher(FileSystemFetcherConfig fileSystemFetcherConfig) {
        setBasePath(fileSystemFetcherConfig.getBasePath());
        setExtractFileSystemMetadata(fileSystemFetcherConfig.isExtractFileSystemMetadata());
    }

    static boolean isDescendant(Path path, Path path2) {
        return path2.toAbsolutePath().normalize().startsWith(path.toAbsolutePath().normalize());
    }

    @Override // org.apache.tika.pipes.fetcher.Fetcher
    public InputStream fetch(String str, Metadata metadata, ParseContext parseContext) throws TikaException, IOException {
        Path pathResolve;
        if (str.contains("\u0000")) {
            throw new IllegalArgumentException("Path must not contain 'u0000'. Please review the life decisions that led you to requesting a file name with this character in it.");
        }
        Path path = this.basePath;
        if (path != null) {
            pathResolve = path.resolve(str);
            if (!pathResolve.toRealPath(new LinkOption[0]).startsWith(this.basePath.toRealPath(new LinkOption[0]))) {
                throw new IllegalArgumentException("fetchKey must resolve to be a descendant of the 'basePath'");
            }
        } else {
            pathResolve = Paths.get(str, new String[0]);
        }
        metadata.set(TikaCoreProperties.SOURCE_PATH, str);
        updateFileSystemMetadata(pathResolve, metadata);
        if (!Files.isRegularFile(pathResolve, new LinkOption[0])) {
            Path path2 = this.basePath;
            if (path2 != null && !Files.isDirectory(path2, new LinkOption[0])) {
                throw new IOException("BasePath is not a directory: " + this.basePath);
            }
            throw new FileNotFoundException(pathResolve.toAbsolutePath().toString());
        }
        return TikaInputStream.get(pathResolve, metadata);
    }

    private void updateFileSystemMetadata(Path path, Metadata metadata) throws IOException {
        if (this.extractFileSystemMetadata) {
            BasicFileAttributes attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(), new LinkOption[0]);
            updateFileTime(FileSystem.CREATED, attributes.creationTime(), metadata);
            updateFileTime(FileSystem.MODIFIED, attributes.lastModifiedTime(), metadata);
            updateFileTime(FileSystem.ACCESSED, attributes.lastAccessTime(), metadata);
        }
    }

    private void updateFileTime(Property property, FileTime fileTime, Metadata metadata) {
        if (fileTime == null) {
            return;
        }
        metadata.set(property, new Date(fileTime.toMillis()));
    }

    public Path getBasePath() {
        return this.basePath;
    }

    @Field
    public void setBasePath(String str) {
        this.basePath = Paths.get(str, new String[0]);
    }

    @Field
    public void setExtractFileSystemMetadata(boolean z) {
        this.extractFileSystemMetadata = z;
    }

    @Override // org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
        Path path = this.basePath;
        if (path == null || UByte$$ExternalSyntheticBackport0.m(path.toString())) {
            LOG.warn("'basePath' has not been set. This means that client code or clients can read from any file that this process has permissions to read. If you are running tika-server, make absolutely certain that you've locked down access to tika-server and file-permissions for the tika-server process.");
            return;
        }
        if (this.basePath.toString().startsWith("http://")) {
            throw new TikaConfigException("FileSystemFetcher only works with local file systems.  Please use the tika-fetcher-http module for http calls");
        }
        if (this.basePath.toString().startsWith("ftp://")) {
            throw new TikaConfigException("FileSystemFetcher only works with local file systems.  Please consider contributing an ftp fetcher module");
        }
        if (this.basePath.toString().startsWith("s3://")) {
            throw new TikaConfigException("FileSystemFetcher only works with local file systems.  Please use the tika-fetcher-s3 module");
        }
        if (this.basePath.toAbsolutePath().toString().contains("\u0000")) {
            throw new TikaConfigException("base path must not contain \u0000. Seriously, what were you thinking?");
        }
    }
}
