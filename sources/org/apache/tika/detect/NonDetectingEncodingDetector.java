package org.apache.tika.detect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.tika.config.Field;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class NonDetectingEncodingDetector implements EncodingDetector {
    private Charset charset;

    public NonDetectingEncodingDetector() {
        this.charset = StandardCharsets.UTF_8;
    }

    public NonDetectingEncodingDetector(Charset charset) {
        Charset charset2 = StandardCharsets.UTF_8;
        this.charset = charset;
    }

    @Override // org.apache.tika.detect.EncodingDetector
    public Charset detect(InputStream inputStream, Metadata metadata) throws IOException {
        return this.charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

    @Field
    private void setCharset(String str) {
        this.charset = Charset.forName(str);
    }
}
