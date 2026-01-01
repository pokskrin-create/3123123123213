package org.apache.tika.sax.xpath;

import java.util.Objects;

/* loaded from: classes4.dex */
public class NamedElementMatcher extends ChildMatcher {
    private final String name;
    private final String namespace;

    protected NamedElementMatcher(String str, String str2, Matcher matcher) {
        super(matcher);
        this.namespace = str;
        this.name = str2;
    }

    @Override // org.apache.tika.sax.xpath.ChildMatcher, org.apache.tika.sax.xpath.Matcher
    public Matcher descend(String str, String str2) {
        if (Objects.equals(str, this.namespace) && str2.equals(this.name)) {
            return super.descend(str, str2);
        }
        return FAIL;
    }
}
