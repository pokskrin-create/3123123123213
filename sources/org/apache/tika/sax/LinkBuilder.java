package org.apache.tika.sax;

import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
class LinkBuilder {
    private final String type;
    private final StringBuilder text = new StringBuilder();
    private String uri = "";
    private String title = "";
    private String rel = "";

    public LinkBuilder(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void setURI(String str) {
        if (str != null) {
            this.uri = str;
        } else {
            this.uri = "";
        }
    }

    public void setTitle(String str) {
        if (str != null) {
            this.title = str;
        } else {
            this.title = "";
        }
    }

    public void setRel(String str) {
        if (str != null) {
            this.rel = str;
        } else {
            this.rel = "";
        }
    }

    public void characters(char[] cArr, int i, int i2) {
        this.text.append(cArr, i, i2);
    }

    public Link getLink() {
        return getLink(false);
    }

    public Link getLink(boolean z) {
        String string = this.text.toString();
        if (z) {
            string = string.replaceAll("\\s+", StringUtils.SPACE).trim();
        }
        return new Link(this.type, this.uri, this.title, string, this.rel);
    }
}
