package org.apache.tika.config;

import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class TikaTaskTimeout {
    private final long timeoutMillis;

    public TikaTaskTimeout(long j) {
        this.timeoutMillis = j;
    }

    public long getTimeoutMillis() {
        return this.timeoutMillis;
    }

    public static long getTimeoutMillis(ParseContext parseContext, long j) {
        TikaTaskTimeout tikaTaskTimeout = (TikaTaskTimeout) parseContext.get(TikaTaskTimeout.class);
        return tikaTaskTimeout == null ? j : tikaTaskTimeout.getTimeoutMillis();
    }
}
