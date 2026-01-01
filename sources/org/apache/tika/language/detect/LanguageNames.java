package org.apache.tika.language.detect;

import java.util.Locale;

/* loaded from: classes4.dex */
public class LanguageNames {
    public static String getMacroLanguage(String str) {
        return str;
    }

    public static boolean hasMacroLanguage(String str) {
        return false;
    }

    public static boolean isMacroLanguage(String str) {
        return false;
    }

    public static String makeName(String str, String str2, String str3) {
        return new Locale.Builder().setLanguage(str).setScript(str2).setRegion(str3).build().toLanguageTag();
    }

    public static String normalizeName(String str) {
        return Locale.forLanguageTag(str).toLanguageTag();
    }

    public static boolean equals(String str, String str2) {
        return Locale.forLanguageTag(str).equals(Locale.forLanguageTag(str2));
    }
}
