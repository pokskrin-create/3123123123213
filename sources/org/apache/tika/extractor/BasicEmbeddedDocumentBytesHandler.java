package org.apache.tika.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.UnsynchronizedBufferedInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.pipes.extractor.EmbeddedDocumentBytesConfig;

/* loaded from: classes4.dex */
public class BasicEmbeddedDocumentBytesHandler extends AbstractEmbeddedDocumentBytesHandler {
    private final EmbeddedDocumentBytesConfig config;
    Map<Integer, byte[]> docBytes = new HashMap();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public BasicEmbeddedDocumentBytesHandler(EmbeddedDocumentBytesConfig embeddedDocumentBytesConfig) {
        this.config = embeddedDocumentBytesConfig;
    }

    @Override // org.apache.tika.extractor.AbstractEmbeddedDocumentBytesHandler, org.apache.tika.extractor.EmbeddedDocumentBytesHandler
    public void add(int i, Metadata metadata, InputStream inputStream) throws IOException {
        super.add(i, metadata, inputStream);
        this.docBytes.put(Integer.valueOf(i), IOUtils.toByteArray(inputStream));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InputStream getDocument(int i) throws IOException {
        return ((UnsynchronizedBufferedInputStream.Builder) new UnsynchronizedBufferedInputStream.Builder().setByteArray(this.docBytes.get(Integer.valueOf(i)))).get();
    }
}
