package org.apache.tika.renderer;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tika.io.TemporaryResources;

/* loaded from: classes4.dex */
public class RenderResults implements Closeable {
    private List<RenderResult> results = new ArrayList();
    private final TemporaryResources tmp;

    public RenderResults(TemporaryResources temporaryResources) {
        this.tmp = temporaryResources;
    }

    public void add(RenderResult renderResult) {
        this.tmp.addResource(renderResult);
        this.results.add(renderResult);
    }

    public List<RenderResult> getResults() {
        return this.results;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.tmp.close();
    }
}
