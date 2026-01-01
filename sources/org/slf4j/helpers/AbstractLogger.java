package org.slf4j.helpers;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/* loaded from: classes4.dex */
public abstract class AbstractLogger implements Logger, Serializable {
    private static final long serialVersionUID = -2529255052481744503L;
    protected String name;

    protected abstract String getFullyQualifiedCallerName();

    protected abstract void handleNormalizedLoggingCall(Level level, Marker marker, String str, Object[] objArr, Throwable th);

    @Override // org.slf4j.Logger
    public String getName() {
        return this.name;
    }

    protected Object readResolve() throws ObjectStreamException {
        return LoggerFactory.getLogger(getName());
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        if (isTraceEnabled()) {
            handle_0ArgsCall(Level.TRACE, null, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        if (isTraceEnabled()) {
            handle_1ArgsCall(Level.TRACE, null, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        if (isTraceEnabled()) {
            handle2ArgsCall(Level.TRACE, null, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object... objArr) {
        if (isTraceEnabled()) {
            handleArgArrayCall(Level.TRACE, null, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Throwable th) {
        if (isTraceEnabled()) {
            handle_0ArgsCall(Level.TRACE, null, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str) {
        if (isTraceEnabled(marker)) {
            handle_0ArgsCall(Level.TRACE, marker, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj) {
        if (isTraceEnabled(marker)) {
            handle_1ArgsCall(Level.TRACE, marker, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj, Object obj2) {
        if (isTraceEnabled(marker)) {
            handle2ArgsCall(Level.TRACE, marker, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object... objArr) {
        if (isTraceEnabled(marker)) {
            handleArgArrayCall(Level.TRACE, marker, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Throwable th) {
        if (isTraceEnabled(marker)) {
            handle_0ArgsCall(Level.TRACE, marker, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        if (isDebugEnabled()) {
            handle_0ArgsCall(Level.DEBUG, null, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        if (isDebugEnabled()) {
            handle_1ArgsCall(Level.DEBUG, null, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        if (isDebugEnabled()) {
            handle2ArgsCall(Level.DEBUG, null, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object... objArr) {
        if (isDebugEnabled()) {
            handleArgArrayCall(Level.DEBUG, null, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        if (isDebugEnabled()) {
            handle_0ArgsCall(Level.DEBUG, null, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str) {
        if (isDebugEnabled(marker)) {
            handle_0ArgsCall(Level.DEBUG, marker, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj) {
        if (isDebugEnabled(marker)) {
            handle_1ArgsCall(Level.DEBUG, marker, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj, Object obj2) {
        if (isDebugEnabled(marker)) {
            handle2ArgsCall(Level.DEBUG, marker, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object... objArr) {
        if (isDebugEnabled(marker)) {
            handleArgArrayCall(Level.DEBUG, marker, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Throwable th) {
        if (isDebugEnabled(marker)) {
            handle_0ArgsCall(Level.DEBUG, marker, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str) {
        if (isInfoEnabled()) {
            handle_0ArgsCall(Level.INFO, null, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        if (isInfoEnabled()) {
            handle_1ArgsCall(Level.INFO, null, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj, Object obj2) {
        if (isInfoEnabled()) {
            handle2ArgsCall(Level.INFO, null, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object... objArr) {
        if (isInfoEnabled()) {
            handleArgArrayCall(Level.INFO, null, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Throwable th) {
        if (isInfoEnabled()) {
            handle_0ArgsCall(Level.INFO, null, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str) {
        if (isInfoEnabled(marker)) {
            handle_0ArgsCall(Level.INFO, marker, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj) {
        if (isInfoEnabled(marker)) {
            handle_1ArgsCall(Level.INFO, marker, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj, Object obj2) {
        if (isInfoEnabled(marker)) {
            handle2ArgsCall(Level.INFO, marker, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object... objArr) {
        if (isInfoEnabled(marker)) {
            handleArgArrayCall(Level.INFO, marker, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Throwable th) {
        if (isInfoEnabled(marker)) {
            handle_0ArgsCall(Level.INFO, marker, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        if (isWarnEnabled()) {
            handle_0ArgsCall(Level.WARN, null, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        if (isWarnEnabled()) {
            handle_1ArgsCall(Level.WARN, null, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj, Object obj2) {
        if (isWarnEnabled()) {
            handle2ArgsCall(Level.WARN, null, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object... objArr) {
        if (isWarnEnabled()) {
            handleArgArrayCall(Level.WARN, null, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        if (isWarnEnabled()) {
            handle_0ArgsCall(Level.WARN, null, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str) {
        if (isWarnEnabled(marker)) {
            handle_0ArgsCall(Level.WARN, marker, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj) {
        if (isWarnEnabled(marker)) {
            handle_1ArgsCall(Level.WARN, marker, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj, Object obj2) {
        if (isWarnEnabled(marker)) {
            handle2ArgsCall(Level.WARN, marker, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object... objArr) {
        if (isWarnEnabled(marker)) {
            handleArgArrayCall(Level.WARN, marker, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Throwable th) {
        if (isWarnEnabled(marker)) {
            handle_0ArgsCall(Level.WARN, marker, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str) {
        if (isErrorEnabled()) {
            handle_0ArgsCall(Level.ERROR, null, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj) {
        if (isErrorEnabled()) {
            handle_1ArgsCall(Level.ERROR, null, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj, Object obj2) {
        if (isErrorEnabled()) {
            handle2ArgsCall(Level.ERROR, null, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object... objArr) {
        if (isErrorEnabled()) {
            handleArgArrayCall(Level.ERROR, null, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        if (isErrorEnabled()) {
            handle_0ArgsCall(Level.ERROR, null, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str) {
        if (isErrorEnabled(marker)) {
            handle_0ArgsCall(Level.ERROR, marker, str, null);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj) {
        if (isErrorEnabled(marker)) {
            handle_1ArgsCall(Level.ERROR, marker, str, obj);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj, Object obj2) {
        if (isErrorEnabled(marker)) {
            handle2ArgsCall(Level.ERROR, marker, str, obj, obj2);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object... objArr) {
        if (isErrorEnabled(marker)) {
            handleArgArrayCall(Level.ERROR, marker, str, objArr);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Throwable th) {
        if (isErrorEnabled(marker)) {
            handle_0ArgsCall(Level.ERROR, marker, str, th);
        }
    }

    private void handle_0ArgsCall(Level level, Marker marker, String str, Throwable th) {
        handleNormalizedLoggingCall(level, marker, str, null, th);
    }

    private void handle_1ArgsCall(Level level, Marker marker, String str, Object obj) {
        handleNormalizedLoggingCall(level, marker, str, new Object[]{obj}, null);
    }

    private void handle2ArgsCall(Level level, Marker marker, String str, Object obj, Object obj2) {
        if (obj2 instanceof Throwable) {
            handleNormalizedLoggingCall(level, marker, str, new Object[]{obj}, (Throwable) obj2);
        } else {
            handleNormalizedLoggingCall(level, marker, str, new Object[]{obj, obj2}, null);
        }
    }

    private void handleArgArrayCall(Level level, Marker marker, String str, Object[] objArr) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(objArr);
        if (throwableCandidate != null) {
            handleNormalizedLoggingCall(level, marker, str, MessageFormatter.trimmedCopy(objArr), throwableCandidate);
        } else {
            handleNormalizedLoggingCall(level, marker, str, objArr, null);
        }
    }
}
