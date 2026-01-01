package org.apache.tika.io;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class IOUtils {
    public static long skip(InputStream inputStream, long j, byte[] bArr) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j);
        }
        long j2 = j;
        while (j2 > 0) {
            long j3 = inputStream.read(bArr, 0, (int) Math.min(j2, bArr.length));
            if (j3 < 0) {
                break;
            }
            j2 -= j3;
        }
        return j - j2;
    }
}
