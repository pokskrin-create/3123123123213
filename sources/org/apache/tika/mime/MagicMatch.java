package org.apache.tika.mime;

import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.tika.detect.MagicDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
class MagicMatch implements Clause {
    private MagicDetector detector = null;
    private final String mask;
    private final MediaType mediaType;
    private final String offset;
    private final String type;
    private final String value;

    MagicMatch(MediaType mediaType, String str, String str2, String str3, String str4) {
        this.mediaType = mediaType;
        this.type = str;
        this.offset = str2;
        this.value = str3;
        this.mask = str4;
    }

    private synchronized MagicDetector getDetector() {
        if (this.detector == null) {
            this.detector = MagicDetector.parse(this.mediaType, this.type, this.offset, this.value, this.mask);
        }
        return this.detector;
    }

    @Override // org.apache.tika.mime.Clause
    public boolean eval(byte[] bArr) {
        return getDetector().detect(UnsynchronizedByteArrayInputStream.builder().setByteArray(bArr).get(), new Metadata()) != MediaType.OCTET_STREAM;
    }

    @Override // org.apache.tika.mime.Clause
    public int size() {
        return getDetector().getLength();
    }

    public String toString() {
        return this.mediaType.toString() + StringUtils.SPACE + this.type + StringUtils.SPACE + this.offset + StringUtils.SPACE + this.value + StringUtils.SPACE + this.mask;
    }
}
