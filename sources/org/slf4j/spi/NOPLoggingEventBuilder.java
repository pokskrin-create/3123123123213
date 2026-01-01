package org.slf4j.spi;

import java.util.function.Supplier;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class NOPLoggingEventBuilder implements LoggingEventBuilder {
    static final NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log() {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str) {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object obj) {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object obj, Object obj2) {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object... objArr) {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(Supplier<String> supplier) {
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setMessage(String str) {
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        return this;
    }

    private NOPLoggingEventBuilder() {
    }

    public static LoggingEventBuilder singleton() {
        return SINGLETON;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addMarker(Marker marker) {
        return singleton();
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addArgument(Object obj) {
        return singleton();
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        return singleton();
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addKeyValue(String str, Object obj) {
        return singleton();
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addKeyValue(String str, Supplier<Object> supplier) {
        return singleton();
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setCause(Throwable th) {
        return singleton();
    }
}
