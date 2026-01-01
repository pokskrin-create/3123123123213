package org.apache.tika.detect;

import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public class TypeDetector implements Detector {
    @Override // org.apache.tika.detect.Detector
    public MediaType detect(InputStream inputStream, Metadata metadata) {
        MediaType mediaType;
        String str = metadata.get("Content-Type");
        return (str == null || (mediaType = MediaType.parse(str)) == null) ? MediaType.OCTET_STREAM : mediaType;
    }
}
