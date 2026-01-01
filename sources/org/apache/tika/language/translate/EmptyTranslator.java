package org.apache.tika.language.translate;

/* loaded from: classes4.dex */
public class EmptyTranslator implements Translator {
    @Override // org.apache.tika.language.translate.Translator
    public boolean isAvailable() {
        return true;
    }

    @Override // org.apache.tika.language.translate.Translator
    public String translate(String str, String str2) {
        return null;
    }

    @Override // org.apache.tika.language.translate.Translator
    public String translate(String str, String str2, String str3) {
        return null;
    }
}
