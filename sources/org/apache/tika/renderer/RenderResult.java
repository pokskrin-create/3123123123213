package org.apache.tika.renderer;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class RenderResult implements Closeable {
    private final int id;
    private final Metadata metadata;
    private final Object result;
    private final STATUS status;
    TemporaryResources tmp = new TemporaryResources();

    public enum STATUS {
        SUCCESS,
        EXCEPTION,
        TIMEOUT
    }

    public RenderResult(STATUS status, int i, final Object obj, Metadata metadata) {
        this.status = status;
        this.id = i;
        this.result = obj;
        this.metadata = metadata;
        if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1(obj)) {
            this.tmp.addResource(new Closeable() { // from class: org.apache.tika.renderer.RenderResult.1
                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    Files.delete(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(obj));
                }
            });
        } else if (obj instanceof Closeable) {
            this.tmp.addResource((Closeable) obj);
        }
    }

    public InputStream getInputStream() throws IOException {
        if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1(this.result)) {
            return TikaInputStream.get(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(this.result), this.metadata);
        }
        TikaInputStream tikaInputStream = TikaInputStream.get(new byte[0]);
        tikaInputStream.setOpenContainer(this.result);
        return tikaInputStream;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public STATUS getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.tmp.close();
    }
}
