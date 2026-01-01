package org.slf4j.helpers;

/* loaded from: classes4.dex */
public class Slf4jEnvUtil {
    public static String slf4jVersion() {
        Package r0 = Slf4jEnvUtil.class.getPackage();
        if (r0 == null) {
            return null;
        }
        return r0.getImplementationVersion();
    }
}
