package org.apache.tika.sax.xpath;

/* loaded from: classes4.dex */
public class CompositeMatcher extends Matcher {
    private final Matcher a;
    private final Matcher b;

    public CompositeMatcher(Matcher matcher, Matcher matcher2) {
        this.a = matcher;
        this.b = matcher2;
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public Matcher descend(String str, String str2) {
        Matcher matcherDescend = this.a.descend(str, str2);
        Matcher matcherDescend2 = this.b.descend(str, str2);
        return matcherDescend == FAIL ? matcherDescend2 : matcherDescend2 == FAIL ? matcherDescend : (this.a == matcherDescend && this.b == matcherDescend2) ? this : new CompositeMatcher(matcherDescend, matcherDescend2);
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesElement() {
        return this.a.matchesElement() || this.b.matchesElement();
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesAttribute(String str, String str2) {
        return this.a.matchesAttribute(str, str2) || this.b.matchesAttribute(str, str2);
    }

    @Override // org.apache.tika.sax.xpath.Matcher
    public boolean matchesText() {
        return this.a.matchesText() || this.b.matchesText();
    }
}
