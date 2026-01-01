package org.apache.tika.pipes.fetcher;

import org.apache.tika.config.Field;

/* loaded from: classes4.dex */
public abstract class AbstractFetcher implements Fetcher {
    private String name;

    public AbstractFetcher() {
    }

    public AbstractFetcher(String str) {
        this.name = str;
    }

    @Override // org.apache.tika.pipes.fetcher.Fetcher
    public String getName() {
        return this.name;
    }

    @Field
    public void setName(String str) {
        this.name = str;
    }
}
