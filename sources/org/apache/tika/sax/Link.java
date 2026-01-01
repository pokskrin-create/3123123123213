package org.apache.tika.sax;

/* loaded from: classes4.dex */
public class Link {
    private final String rel;
    private final String text;
    private final String title;
    private final String type;
    private final String uri;

    public Link(String str, String str2, String str3, String str4) {
        this.type = str;
        this.uri = str2;
        this.title = str3;
        this.text = str4;
        this.rel = "";
    }

    public Link(String str, String str2, String str3, String str4, String str5) {
        this.type = str;
        this.uri = str2;
        this.title = str3;
        this.text = str4;
        this.rel = str5;
    }

    public boolean isAnchor() {
        return "a".equals(this.type);
    }

    public boolean isImage() {
        return "img".equals(this.type);
    }

    public boolean isLink() {
        return "link".equals(this.type);
    }

    public boolean isIframe() {
        return "iframe".equals(this.type);
    }

    public boolean isScript() {
        return "script".equals(this.type);
    }

    public String getType() {
        return this.type;
    }

    public String getUri() {
        return this.uri;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getRel() {
        return this.rel;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isImage()) {
            sb.append("<img src=\"");
            sb.append(this.uri);
            String str = this.title;
            if (str != null && str.length() > 0) {
                sb.append("\" title=\"");
                sb.append(this.title);
            }
            String str2 = this.text;
            if (str2 != null && str2.length() > 0) {
                sb.append("\" alt=\"");
                sb.append(this.text);
            }
            sb.append("\"/>");
        } else {
            sb.append("<");
            sb.append(this.type);
            sb.append(" href=\"");
            sb.append(this.uri);
            String str3 = this.title;
            if (str3 != null && str3.length() > 0) {
                sb.append("\" title=\"");
                sb.append(this.title);
            }
            String str4 = this.rel;
            if (str4 != null && str4.length() > 0) {
                sb.append("\" rel=\"");
                sb.append(this.rel);
            }
            sb.append("\">");
            sb.append(this.text);
            sb.append("</");
            sb.append(this.type);
            sb.append(">");
        }
        return sb.toString();
    }
}
