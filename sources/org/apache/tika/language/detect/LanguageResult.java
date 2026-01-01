package org.apache.tika.language.detect;

import java.util.Locale;

/* loaded from: classes4.dex */
public class LanguageResult {
    public static final LanguageResult NULL = new LanguageResult("", LanguageConfidence.NONE, 0.0f);
    private final LanguageConfidence confidence;
    private final String language;
    private final float rawScore;

    public LanguageResult(String str, LanguageConfidence languageConfidence, float f) {
        this.language = str;
        this.confidence = languageConfidence;
        this.rawScore = f;
    }

    public String getLanguage() {
        return this.language;
    }

    public float getRawScore() {
        return this.rawScore;
    }

    public LanguageConfidence getConfidence() {
        return this.confidence;
    }

    public boolean isReasonablyCertain() {
        return this.confidence == LanguageConfidence.HIGH;
    }

    public boolean isUnknown() {
        return this.confidence == LanguageConfidence.NONE;
    }

    public boolean isLanguage(String str) {
        String[] strArrSplit = str.split("\\-");
        String[] strArrSplit2 = this.language.split("\\-");
        int iMin = Math.min(strArrSplit.length, strArrSplit2.length);
        for (int i = 0; i < iMin; i++) {
            if (!strArrSplit[i].equalsIgnoreCase(strArrSplit2[i])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return String.format(Locale.US, "%s: %s (%f)", this.language, this.confidence, Float.valueOf(this.rawScore));
    }
}
