package org.apache.tika.parser;

import java.util.Map;

/* loaded from: classes4.dex */
public class AutoDetectParserFactory extends ParserFactory {
    public static final String TIKA_CONFIG_PATH = "tika_config_path";

    public AutoDetectParserFactory(Map<String, String> map) {
        super(map);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    @Override // org.apache.tika.parser.ParserFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.apache.tika.parser.Parser build() throws org.apache.tika.exception.TikaException, org.xml.sax.SAXException, java.io.IOException {
        /*
            r3 = this;
            java.util.Map<java.lang.String, java.lang.String> r0 = r3.args
            java.lang.String r1 = "tika_config_path"
            java.lang.Object r0 = r0.remove(r1)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L4e
            r1 = 0
            java.lang.String[] r2 = new java.lang.String[r1]
            java.nio.file.Path r2 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(r0, r2)
            boolean r2 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1742m$4(r2)
            if (r2 == 0) goto L25
            org.apache.tika.config.TikaConfig r2 = new org.apache.tika.config.TikaConfig
            java.lang.String[] r1 = new java.lang.String[r1]
            java.nio.file.Path r0 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(r0, r1)
            r2.<init>(r0)
            goto L4f
        L25:
            java.lang.Class r1 = r3.getClass()
            java.net.URL r1 = r1.getResource(r0)
            if (r1 == 0) goto L4e
            java.lang.Class r1 = r3.getClass()
            java.io.InputStream r0 = r1.getResourceAsStream(r0)
            org.apache.tika.config.TikaConfig r2 = new org.apache.tika.config.TikaConfig     // Catch: java.lang.Throwable -> L42
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L42
            if (r0 == 0) goto L4f
            r0.close()
            goto L4f
        L42:
            r1 = move-exception
            if (r0 == 0) goto L4d
            r0.close()     // Catch: java.lang.Throwable -> L49
            goto L4d
        L49:
            r0 = move-exception
            r1.addSuppressed(r0)
        L4d:
            throw r1
        L4e:
            r2 = 0
        L4f:
            if (r2 != 0) goto L55
            org.apache.tika.config.TikaConfig r2 = org.apache.tika.config.TikaConfig.getDefaultConfig()
        L55:
            org.apache.tika.parser.AutoDetectParser r0 = new org.apache.tika.parser.AutoDetectParser
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.parser.AutoDetectParserFactory.build():org.apache.tika.parser.Parser");
    }
}
