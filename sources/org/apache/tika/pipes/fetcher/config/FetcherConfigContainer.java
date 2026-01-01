package org.apache.tika.pipes.fetcher.config;

/* loaded from: classes4.dex */
public class FetcherConfigContainer {
    private String configClassName;
    private String json;

    public String getConfigClassName() {
        return this.configClassName;
    }

    public FetcherConfigContainer setConfigClassName(String str) {
        this.configClassName = str;
        return this;
    }

    public String getJson() {
        return this.json;
    }

    public FetcherConfigContainer setJson(String str) {
        this.json = str;
        return this;
    }
}
