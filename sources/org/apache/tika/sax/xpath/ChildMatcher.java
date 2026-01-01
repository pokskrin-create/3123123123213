package org.apache.tika.sax.xpath;

/* loaded from: classes4.dex */
public class ChildMatcher extends Matcher {
    private final Matcher then;

    public ChildMatcher(Matcher matcher) {
        this.then = matcher;
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public Matcher descend(String str, String str2) {
        return this.then;
    }
}
