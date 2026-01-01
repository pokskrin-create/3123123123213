package org.apache.tika.sax.xpath;

/* loaded from: classes4.dex */
public class Matcher {
    public static final Matcher FAIL = new Matcher();

    public boolean matchesAttribute(String str, String str2) {
        return false;
    }

    public boolean matchesElement() {
        return false;
    }

    public boolean matchesText() {
        return false;
    }

    public Matcher descend(String str, String str2) {
        return FAIL;
    }
}
