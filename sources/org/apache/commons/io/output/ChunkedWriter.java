package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/* loaded from: classes4.dex */
public class ChunkedWriter extends FilterWriter {
    private static final int DEFAULT_CHUNK_SIZE = 8192;
    private final int chunkSize;

    public ChunkedWriter(Writer writer) {
        this(writer, 8192);
    }

    public ChunkedWriter(Writer writer, int i) {
        super(writer);
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = i;
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int iMin = Math.min(i2, this.chunkSize);
            this.out.write(cArr, i, iMin);
            i2 -= iMin;
            i += iMin;
        }
    }
}
