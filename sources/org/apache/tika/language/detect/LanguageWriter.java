package org.apache.tika.language.detect;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes4.dex */
public class LanguageWriter extends Writer {
    private final LanguageDetector detector;

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public LanguageWriter(LanguageDetector languageDetector) {
        this.detector = languageDetector;
        languageDetector.reset();
    }

    public LanguageDetector getDetector() {
        return this.detector;
    }

    public LanguageResult getLanguage() {
        return this.detector.detect();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        this.detector.addText(cArr, i, i2);
    }

    public void reset() {
        this.detector.reset();
    }
}
