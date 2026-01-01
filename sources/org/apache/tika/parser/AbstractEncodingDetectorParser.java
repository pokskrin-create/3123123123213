package org.apache.tika.parser;

import org.apache.tika.detect.DefaultEncodingDetector;
import org.apache.tika.detect.EncodingDetector;

/* loaded from: classes4.dex */
public abstract class AbstractEncodingDetectorParser implements Parser {
    private EncodingDetector encodingDetector;

    public AbstractEncodingDetectorParser() {
        this.encodingDetector = new DefaultEncodingDetector();
    }

    public AbstractEncodingDetectorParser(EncodingDetector encodingDetector) {
        this.encodingDetector = encodingDetector;
    }

    protected EncodingDetector getEncodingDetector(ParseContext parseContext) {
        EncodingDetector encodingDetector = (EncodingDetector) parseContext.get(EncodingDetector.class);
        return encodingDetector != null ? encodingDetector : getEncodingDetector();
    }

    public EncodingDetector getEncodingDetector() {
        return this.encodingDetector;
    }

    public void setEncodingDetector(EncodingDetector encodingDetector) {
        this.encodingDetector = encodingDetector;
    }
}
