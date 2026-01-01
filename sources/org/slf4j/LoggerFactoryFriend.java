package org.slf4j;

/* loaded from: classes4.dex */
public class LoggerFactoryFriend {
    public static void reset() {
        LoggerFactory.reset();
    }

    public static void setDetectLoggerNameMismatch(boolean z) {
        LoggerFactory.DETECT_LOGGER_NAME_MISMATCH = z;
    }
}
