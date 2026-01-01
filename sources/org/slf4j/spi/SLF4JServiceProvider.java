package org.slf4j.spi;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;

/* loaded from: classes4.dex */
public interface SLF4JServiceProvider {
    ILoggerFactory getLoggerFactory();

    MDCAdapter getMDCAdapter();

    IMarkerFactory getMarkerFactory();

    String getRequestedApiVersion();

    void initialize();
}
