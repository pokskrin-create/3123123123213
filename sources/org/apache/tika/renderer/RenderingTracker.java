package org.apache.tika.renderer;

/* loaded from: classes4.dex */
public class RenderingTracker {
    private int id = 0;

    public synchronized int getNextId() {
        int i;
        i = this.id + 1;
        this.id = i;
        return i;
    }
}
