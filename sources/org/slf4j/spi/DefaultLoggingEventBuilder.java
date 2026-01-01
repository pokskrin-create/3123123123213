package org.slf4j.spi;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

/* loaded from: classes4.dex */
public class DefaultLoggingEventBuilder implements LoggingEventBuilder, CallerBoundaryAware {
    static String DLEB_FQCN = "org.slf4j.spi.DefaultLoggingEventBuilder";
    protected Logger logger;
    protected DefaultLoggingEvent loggingEvent;

    public DefaultLoggingEventBuilder(Logger logger, Level level) {
        this.logger = logger;
        this.loggingEvent = new DefaultLoggingEvent(level, logger);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addMarker(Marker marker) {
        this.loggingEvent.addMarker(marker);
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setCause(Throwable th) {
        this.loggingEvent.setThrowable(th);
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addArgument(Object obj) {
        this.loggingEvent.addArgument(obj);
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        this.loggingEvent.addArgument(supplier.get());
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addKeyValue(String str, Object obj) {
        this.loggingEvent.addKeyValue(str, obj);
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder addKeyValue(String str, Supplier<Object> supplier) {
        this.loggingEvent.addKeyValue(str, supplier.get());
        return this;
    }

    @Override // org.slf4j.spi.CallerBoundaryAware
    public void setCallerBoundary(String str) {
        this.loggingEvent.setCallerBoundary(str);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log() {
        log(this.loggingEvent);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setMessage(String str) {
        this.loggingEvent.setMessage(str);
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        this.loggingEvent.setMessage(supplier.get());
        return this;
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str) {
        this.loggingEvent.setMessage(str);
        log(this.loggingEvent);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object obj) {
        this.loggingEvent.setMessage(str);
        this.loggingEvent.addArgument(obj);
        log(this.loggingEvent);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object obj, Object obj2) {
        this.loggingEvent.setMessage(str);
        this.loggingEvent.addArgument(obj);
        this.loggingEvent.addArgument(obj2);
        log(this.loggingEvent);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(String str, Object... objArr) {
        this.loggingEvent.setMessage(str);
        this.loggingEvent.addArguments(objArr);
        log(this.loggingEvent);
    }

    @Override // org.slf4j.spi.LoggingEventBuilder
    public void log(Supplier<String> supplier) {
        if (supplier == null) {
            log((String) null);
        } else {
            log(supplier.get());
        }
    }

    protected void log(LoggingEvent loggingEvent) {
        if (loggingEvent.getCallerBoundary() == null) {
            setCallerBoundary(DLEB_FQCN);
        }
        Logger logger = this.logger;
        if (logger instanceof LoggingEventAware) {
            ((LoggingEventAware) logger).log(loggingEvent);
        } else if (logger instanceof LocationAwareLogger) {
            logViaLocationAwareLoggerAPI((LocationAwareLogger) logger, loggingEvent);
        } else {
            logViaPublicSLF4JLoggerAPI(loggingEvent);
        }
    }

    private void logViaLocationAwareLoggerAPI(LocationAwareLogger locationAwareLogger, LoggingEvent loggingEvent) {
        loggingEvent.getMessage();
        loggingEvent.getMarkers();
        locationAwareLogger.log(null, loggingEvent.getCallerBoundary(), loggingEvent.getLevel().toInt(), mergeMarkersAndKeyValuePairsAndMessage(loggingEvent), loggingEvent.getArgumentArray(), loggingEvent.getThrowable());
    }

    private void logViaPublicSLF4JLoggerAPI(LoggingEvent loggingEvent) {
        Object[] argumentArray = loggingEvent.getArgumentArray();
        int length = argumentArray == null ? 0 : argumentArray.length;
        Throwable throwable = loggingEvent.getThrowable();
        Object[] objArr = new Object[(throwable == null ? 0 : 1) + length];
        if (argumentArray != null) {
            System.arraycopy(argumentArray, 0, objArr, 0, length);
        }
        if (throwable != null) {
            objArr[length] = throwable;
        }
        String strMergeMarkersAndKeyValuePairsAndMessage = mergeMarkersAndKeyValuePairsAndMessage(loggingEvent);
        int i = AnonymousClass1.$SwitchMap$org$slf4j$event$Level[loggingEvent.getLevel().ordinal()];
        if (i == 1) {
            this.logger.trace(strMergeMarkersAndKeyValuePairsAndMessage, objArr);
            return;
        }
        if (i == 2) {
            this.logger.debug(strMergeMarkersAndKeyValuePairsAndMessage, objArr);
            return;
        }
        if (i == 3) {
            this.logger.info(strMergeMarkersAndKeyValuePairsAndMessage, objArr);
        } else if (i == 4) {
            this.logger.warn(strMergeMarkersAndKeyValuePairsAndMessage, objArr);
        } else {
            if (i != 5) {
                return;
            }
            this.logger.error(strMergeMarkersAndKeyValuePairsAndMessage, objArr);
        }
    }

    /* renamed from: org.slf4j.spi.DefaultLoggingEventBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$slf4j$event$Level;

        static {
            int[] iArr = new int[Level.values().length];
            $SwitchMap$org$slf4j$event$Level = iArr;
            try {
                iArr[Level.TRACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private String mergeMarkersAndKeyValuePairsAndMessage(LoggingEvent loggingEvent) {
        return mergeMessage(loggingEvent.getMessage(), mergeKeyValuePairs(loggingEvent.getKeyValuePairs(), mergeMarkers(loggingEvent.getMarkers(), null)));
    }

    private StringBuilder mergeMarkers(List<Marker> list, StringBuilder sb) {
        if (list != null && !list.isEmpty()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            Iterator<Marker> it = list.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(' ');
            }
        }
        return sb;
    }

    private StringBuilder mergeKeyValuePairs(List<KeyValuePair> list, StringBuilder sb) {
        if (list != null && !list.isEmpty()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (KeyValuePair keyValuePair : list) {
                sb.append(keyValuePair.key);
                sb.append('=');
                sb.append(keyValuePair.value);
                sb.append(' ');
            }
        }
        return sb;
    }

    private String mergeMessage(String str, StringBuilder sb) {
        if (sb == null) {
            return str;
        }
        sb.append(str);
        return sb.toString();
    }
}
