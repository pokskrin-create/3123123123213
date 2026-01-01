package org.apache.tika.fork;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes4.dex */
class MemoryURLConnection extends URLConnection {
    private final byte[] data;

    @Override // java.net.URLConnection
    public void connect() {
    }

    MemoryURLConnection(URL url, byte[] bArr) {
        super(url);
        this.data = bArr;
    }

    @Override // java.net.URLConnection
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.data);
    }
}
