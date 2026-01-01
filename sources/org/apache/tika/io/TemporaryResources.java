package org.apache.tika.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class TemporaryResources implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) TemporaryResources.class);
    private final LinkedList<Closeable> resources = new LinkedList<>();
    private Path tempFileDir = null;

    public void setTemporaryFileDirectory(Path path) {
        this.tempFileDir = path;
    }

    public void setTemporaryFileDirectory(File file) {
        this.tempFileDir = file == null ? null : file.toPath();
    }

    public Path createTempFile(String str) throws IOException {
        if (StringUtils.isBlank(str)) {
            str = ".tmp";
        }
        Path path = this.tempFileDir;
        final Path pathCreateTempFile = path == null ? Files.createTempFile("apache-tika-", str, new FileAttribute[0]) : Files.createTempFile(path, "apache-tika-", str, new FileAttribute[0]);
        addResource(new Closeable() { // from class: org.apache.tika.io.TemporaryResources$$ExternalSyntheticLambda0
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() throws IOException {
                TemporaryResources.lambda$createTempFile$0(pathCreateTempFile);
            }
        });
        return pathCreateTempFile;
    }

    static /* synthetic */ void lambda$createTempFile$0(Path path) throws IOException {
        try {
            Files.delete(path);
        } catch (IOException unused) {
            LOG.warn("delete tmp file fail, will delete it on exit");
            path.toFile().deleteOnExit();
        }
    }

    public Path createTempFile() throws IOException {
        return createTempFile("");
    }

    public Path createTempFile(Metadata metadata) throws IOException {
        String str = metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY);
        if (StringUtils.isBlank(str)) {
            return createTempFile("");
        }
        return createTempFile(FilenameUtils.getSuffixFromPath(str));
    }

    public File createTemporaryFile() throws IOException {
        return createTempFile("").toFile();
    }

    public void addResource(Closeable closeable) {
        this.resources.addFirst(closeable);
    }

    public <T extends Closeable> T getResource(Class<T> cls) {
        Iterator<Closeable> it = this.resources.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (cls.isAssignableFrom(t.getClass())) {
                return t;
            }
        }
        return null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Iterator<Closeable> it = this.resources.iterator();
        IOException iOException = null;
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException e) {
                if (iOException == null) {
                    iOException = e;
                } else {
                    iOException.addSuppressed(e);
                }
            }
        }
        this.resources.clear();
        if (iOException != null) {
            throw iOException;
        }
    }

    public void dispose() throws TikaException {
        try {
            close();
        } catch (IOException e) {
            throw new TikaException("Failed to close temporary resources", e);
        }
    }
}
