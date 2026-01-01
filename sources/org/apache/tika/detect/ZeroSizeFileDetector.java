package org.apache.tika.detect;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public class ZeroSizeFileDetector implements Detector {
    @Override // org.apache.tika.detect.Detector
    public MediaType detect(InputStream inputStream, Metadata metadata) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.mark(1);
                if (inputStream.read() == -1) {
                    return MediaType.EMPTY;
                }
            } finally {
                inputStream.reset();
            }
        }
        return MediaType.OCTET_STREAM;
    }
}
