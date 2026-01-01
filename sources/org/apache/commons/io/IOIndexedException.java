package org.apache.commons.io;

import java.io.IOException;

/* loaded from: classes4.dex */
public class IOIndexedException extends IOException {
    private static final long serialVersionUID = 1;
    private final int index;

    protected static String toMessage(int i, Throwable th) {
        String message = "Null";
        String simpleName = th == null ? "Null" : th.getClass().getSimpleName();
        if (th != null) {
            message = th.getMessage();
        }
        return String.format("%s #%,d: %s", simpleName, Integer.valueOf(i), message);
    }

    public IOIndexedException(int i, Throwable th) {
        super(toMessage(i, th), th);
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }
}
