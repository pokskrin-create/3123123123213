package org.apache.commons.io.serialization;

import org.apache.commons.io.FilenameUtils;

/* loaded from: classes4.dex */
final class WildcardClassNameMatcher implements ClassNameMatcher {
    private final String pattern;

    WildcardClassNameMatcher(String str) {
        this.pattern = str;
    }

    @Override // org.apache.commons.io.serialization.ClassNameMatcher
    public boolean matches(String str) {
        return FilenameUtils.wildcardMatch(str, this.pattern);
    }
}
