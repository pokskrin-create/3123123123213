package org.slf4j;

import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

/* loaded from: classes4.dex */
public interface Logger {
    public static final String ROOT_LOGGER_NAME = "ROOT";

    void debug(String str);

    void debug(String str, Object obj);

    void debug(String str, Object obj, Object obj2);

    void debug(String str, Throwable th);

    void debug(String str, Object... objArr);

    void debug(Marker marker, String str);

    void debug(Marker marker, String str, Object obj);

    void debug(Marker marker, String str, Object obj, Object obj2);

    void debug(Marker marker, String str, Throwable th);

    void debug(Marker marker, String str, Object... objArr);

    void error(String str);

    void error(String str, Object obj);

    void error(String str, Object obj, Object obj2);

    void error(String str, Throwable th);

    void error(String str, Object... objArr);

    void error(Marker marker, String str);

    void error(Marker marker, String str, Object obj);

    void error(Marker marker, String str, Object obj, Object obj2);

    void error(Marker marker, String str, Throwable th);

    void error(Marker marker, String str, Object... objArr);

    String getName();

    void info(String str);

    void info(String str, Object obj);

    void info(String str, Object obj, Object obj2);

    void info(String str, Throwable th);

    void info(String str, Object... objArr);

    void info(Marker marker, String str);

    void info(Marker marker, String str, Object obj);

    void info(Marker marker, String str, Object obj, Object obj2);

    void info(Marker marker, String str, Throwable th);

    void info(Marker marker, String str, Object... objArr);

    boolean isDebugEnabled();

    boolean isDebugEnabled(Marker marker);

    boolean isErrorEnabled();

    boolean isErrorEnabled(Marker marker);

    boolean isInfoEnabled();

    boolean isInfoEnabled(Marker marker);

    boolean isTraceEnabled();

    boolean isTraceEnabled(Marker marker);

    boolean isWarnEnabled();

    boolean isWarnEnabled(Marker marker);

    void trace(String str);

    void trace(String str, Object obj);

    void trace(String str, Object obj, Object obj2);

    void trace(String str, Throwable th);

    void trace(String str, Object... objArr);

    void trace(Marker marker, String str);

    void trace(Marker marker, String str, Object obj);

    void trace(Marker marker, String str, Object obj, Object obj2);

    void trace(Marker marker, String str, Throwable th);

    void trace(Marker marker, String str, Object... objArr);

    void warn(String str);

    void warn(String str, Object obj);

    void warn(String str, Object obj, Object obj2);

    void warn(String str, Throwable th);

    void warn(String str, Object... objArr);

    void warn(Marker marker, String str);

    void warn(Marker marker, String str, Object obj);

    void warn(Marker marker, String str, Object obj, Object obj2);

    void warn(Marker marker, String str, Throwable th);

    void warn(Marker marker, String str, Object... objArr);

    default LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return new DefaultLoggingEventBuilder(this, level);
    }

    @CheckReturnValue
    default LoggingEventBuilder atLevel(Level level) {
        if (isEnabledForLevel(level)) {
            return makeLoggingEventBuilder(level);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    default boolean isEnabledForLevel(Level level) {
        int i = level.toInt();
        if (i == 0) {
            return isTraceEnabled();
        }
        if (i == 10) {
            return isDebugEnabled();
        }
        if (i == 20) {
            return isInfoEnabled();
        }
        if (i == 30) {
            return isWarnEnabled();
        }
        if (i == 40) {
            return isErrorEnabled();
        }
        throw new IllegalArgumentException("Level [" + level + "] not recognized.");
    }

    @CheckReturnValue
    default LoggingEventBuilder atTrace() {
        if (isTraceEnabled()) {
            return makeLoggingEventBuilder(Level.TRACE);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    @CheckReturnValue
    default LoggingEventBuilder atDebug() {
        if (isDebugEnabled()) {
            return makeLoggingEventBuilder(Level.DEBUG);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    @CheckReturnValue
    default LoggingEventBuilder atInfo() {
        if (isInfoEnabled()) {
            return makeLoggingEventBuilder(Level.INFO);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    @CheckReturnValue
    default LoggingEventBuilder atWarn() {
        if (isWarnEnabled()) {
            return makeLoggingEventBuilder(Level.WARN);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    @CheckReturnValue
    default LoggingEventBuilder atError() {
        if (isErrorEnabled()) {
            return makeLoggingEventBuilder(Level.ERROR);
        }
        return NOPLoggingEventBuilder.singleton();
    }
}
