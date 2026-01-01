package org.apache.tika.language.detect;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.utils.CompareUtils;

/* loaded from: classes4.dex */
public abstract class LanguageDetector {
    private static final int BUFFER_LENGTH = 4096;
    private static final ServiceLoader DEFAULT_SERVICE_LOADER = new ServiceLoader();
    protected boolean mixedLanguages = false;
    protected boolean shortText = false;

    public abstract void addText(char[] cArr, int i, int i2);

    public abstract List<LanguageResult> detectAll();

    public boolean hasEnoughText() {
        return false;
    }

    public abstract boolean hasModel(String str);

    public abstract LanguageDetector loadModels() throws IOException;

    public abstract LanguageDetector loadModels(Set<String> set) throws IOException;

    public abstract void reset();

    public abstract LanguageDetector setPriors(Map<String, Float> map) throws IOException;

    public static LanguageDetector getDefaultLanguageDetector() {
        List<LanguageDetector> languageDetectors = getLanguageDetectors();
        if (languageDetectors.isEmpty()) {
            throw new IllegalStateException("No language detectors available");
        }
        return languageDetectors.get(0);
    }

    public static List<LanguageDetector> getLanguageDetectors() {
        return getLanguageDetectors(DEFAULT_SERVICE_LOADER);
    }

    public static List<LanguageDetector> getLanguageDetectors(ServiceLoader serviceLoader) {
        List<LanguageDetector> listLoadStaticServiceProviders = serviceLoader.loadStaticServiceProviders(LanguageDetector.class);
        listLoadStaticServiceProviders.sort(new Comparator() { // from class: org.apache.tika.language.detect.LanguageDetector$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtils.compareClassName((LanguageDetector) obj, (LanguageDetector) obj2);
            }
        });
        return listLoadStaticServiceProviders;
    }

    public boolean isMixedLanguages() {
        return this.mixedLanguages;
    }

    public LanguageDetector setMixedLanguages(boolean z) {
        this.mixedLanguages = z;
        return this;
    }

    public boolean isShortText() {
        return this.shortText;
    }

    public LanguageDetector setShortText(boolean z) {
        this.shortText = z;
        return this;
    }

    public void addText(CharSequence charSequence) {
        int length = charSequence.length();
        if (length < 4096) {
            char[] charArray = charSequence.toString().toCharArray();
            addText(charArray, 0, charArray.length);
            return;
        }
        int i = 0;
        while (!hasEnoughText() && i < length) {
            int i2 = i + 4096;
            char[] charArray2 = charSequence.subSequence(i, Math.min(i2, length)).toString().toCharArray();
            addText(charArray2, 0, charArray2.length);
            i = i2;
        }
    }

    public LanguageResult detect() {
        return detectAll().get(0);
    }

    public List<LanguageResult> detectAll(String str) {
        reset();
        addText(str);
        return detectAll();
    }

    public LanguageResult detect(CharSequence charSequence) {
        reset();
        addText(charSequence);
        return detect();
    }
}
