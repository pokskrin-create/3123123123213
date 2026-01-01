package com.google.firebase.remoteconfig.internal;

/* loaded from: classes4.dex */
public class DefaultsXmlParser {
    private static final String XML_TAG_ENTRY = "entry";
    private static final String XML_TAG_KEY = "key";
    private static final String XML_TAG_VALUE = "value";

    /* JADX WARN: Removed duplicated region for block: B:37:0x0078 A[Catch: IOException | XmlPullParserException -> 0x0083, XmlPullParserException -> 0x0085, TryCatch #2 {IOException | XmlPullParserException -> 0x0083, blocks: (B:3:0x0007, B:5:0x000d, B:7:0x0013, B:12:0x0025, B:38:0x007d, B:15:0x002d, B:19:0x003d, B:20:0x0041, B:26:0x004f, B:37:0x0078, B:31:0x005e, B:33:0x0066, B:34:0x006b, B:36:0x0073), top: B:46:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Map<java.lang.String, java.lang.String> getDefaultsFromXml(android.content.Context r7, int r8) throws android.content.res.Resources.NotFoundException {
        /*
            java.lang.String r0 = "FirebaseRemoteConfig"
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            android.content.res.Resources r7 = r7.getResources()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            if (r7 != 0) goto L13
            java.lang.String r7 = "Could not find the resources of the current context while trying to set defaults from an XML."
            android.util.Log.e(r0, r7)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            return r1
        L13:
            android.content.res.XmlResourceParser r7 = r7.getXml(r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            int r8 = r7.getEventType()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            r2 = 0
            r3 = r2
            r4 = r3
            r5 = r4
        L1f:
            r6 = 1
            if (r8 == r6) goto L82
            r6 = 2
            if (r8 != r6) goto L2a
            java.lang.String r3 = r7.getName()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            goto L7d
        L2a:
            r6 = 3
            if (r8 != r6) goto L4a
            java.lang.String r8 = r7.getName()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            java.lang.String r3 = "entry"
            boolean r8 = r8.equals(r3)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            if (r8 == 0) goto L48
            if (r4 == 0) goto L41
            if (r5 == 0) goto L41
            r1.put(r4, r5)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            goto L46
        L41:
            java.lang.String r8 = "An entry in the defaults XML has an invalid key and/or value tag."
            android.util.Log.w(r0, r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
        L46:
            r4 = r2
            r5 = r4
        L48:
            r3 = r2
            goto L7d
        L4a:
            r6 = 4
            if (r8 != r6) goto L7d
            if (r3 == 0) goto L7d
            int r8 = r3.hashCode()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            r6 = 106079(0x19e5f, float:1.48648E-40)
            if (r8 == r6) goto L6b
            r6 = 111972721(0x6ac9171, float:6.4912916E-35)
            if (r8 == r6) goto L5e
            goto L78
        L5e:
            java.lang.String r8 = "value"
            boolean r8 = r3.equals(r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            if (r8 == 0) goto L78
            java.lang.String r5 = r7.getText()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            goto L7d
        L6b:
            java.lang.String r8 = "key"
            boolean r8 = r3.equals(r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            if (r8 == 0) goto L78
            java.lang.String r4 = r7.getText()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            goto L7d
        L78:
            java.lang.String r8 = "Encountered an unexpected tag while parsing the defaults XML."
            android.util.Log.w(r0, r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
        L7d:
            int r8 = r7.next()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L85
            goto L1f
        L82:
            return r1
        L83:
            r7 = move-exception
            goto L86
        L85:
            r7 = move-exception
        L86:
            java.lang.String r8 = "Encountered an error while parsing the defaults XML file."
            android.util.Log.e(r0, r8, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.remoteconfig.internal.DefaultsXmlParser.getDefaultsFromXml(android.content.Context, int):java.util.Map");
    }
}
