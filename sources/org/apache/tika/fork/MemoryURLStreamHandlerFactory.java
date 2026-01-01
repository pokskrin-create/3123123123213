package org.apache.tika.fork;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/* loaded from: classes4.dex */
class MemoryURLStreamHandlerFactory implements URLStreamHandlerFactory {
    MemoryURLStreamHandlerFactory() {
    }

    @Override // java.net.URLStreamHandlerFactory
    public URLStreamHandler createURLStreamHandler(String str) {
        if ("tika-in-memory".equals(str)) {
            return new MemoryURLStreamHandler();
        }
        return null;
    }
}
