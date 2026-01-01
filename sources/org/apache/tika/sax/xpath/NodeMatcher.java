package org.apache.tika.sax.xpath;

/* loaded from: classes4.dex */
public class NodeMatcher extends Matcher {
    public static final Matcher INSTANCE = new NodeMatcher();

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesAttribute(String str, String str2) {
        return true;
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesElement() {
        return true;
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesText() {
        return true;
    }
}
