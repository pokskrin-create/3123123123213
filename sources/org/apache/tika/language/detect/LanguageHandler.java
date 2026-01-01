package org.apache.tika.language.detect;

import java.io.IOException;
import org.apache.tika.sax.WriteOutContentHandler;

/* loaded from: classes4.dex */
public class LanguageHandler extends WriteOutContentHandler {
    private final LanguageWriter writer;

    public LanguageHandler() throws IOException {
        this(new LanguageWriter(LanguageDetector.getDefaultLanguageDetector().loadModels()));
    }

    public LanguageHandler(LanguageWriter languageWriter) {
        super(languageWriter);
        this.writer = languageWriter;
    }

    public LanguageHandler(LanguageDetector languageDetector) {
        this(new LanguageWriter(languageDetector));
    }

    public LanguageDetector getDetector() {
        return this.writer.getDetector();
    }

    public LanguageResult getLanguage() {
        return this.writer.getLanguage();
    }
}
