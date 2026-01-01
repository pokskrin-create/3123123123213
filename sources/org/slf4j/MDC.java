package org.slf4j;

import java.io.Closeable;
import java.util.Deque;
import java.util.Map;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Reporter;
import org.slf4j.helpers.SubstituteServiceProvider;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/* loaded from: classes4.dex */
public class MDC {
    static MDCAdapter MDC_ADAPTER = null;
    private static final String MDC_APAPTER_CANNOT_BE_NULL_MESSAGE = "MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA";
    static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";

    public static class MDCCloseable implements Closeable {
        private final String key;

        private MDCCloseable(String str) {
            this.key = str;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IllegalArgumentException {
            MDC.remove(this.key);
        }
    }

    private MDC() {
    }

    private static MDCAdapter getMDCAdapterGivenByProvider() {
        SLF4JServiceProvider provider = LoggerFactory.getProvider();
        if (provider != null) {
            MDCAdapter mDCAdapter = provider.getMDCAdapter();
            emitTemporaryMDCAdapterWarningIfNeeded(provider);
            return mDCAdapter;
        }
        Reporter.error("Failed to find provider.");
        Reporter.error("Defaulting to no-operation MDCAdapter implementation.");
        return new NOPMDCAdapter();
    }

    private static void emitTemporaryMDCAdapterWarningIfNeeded(SLF4JServiceProvider sLF4JServiceProvider) {
        if (sLF4JServiceProvider instanceof SubstituteServiceProvider) {
            Reporter.info("Temporary mdcAdapter given by SubstituteServiceProvider.");
            Reporter.info("This mdcAdapter will be replaced after backend initialization has completed.");
        }
    }

    public static void put(String str, String str2) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().put(str, str2);
    }

    public static MDCCloseable putCloseable(String str, String str2) throws IllegalArgumentException {
        put(str, str2);
        return new MDCCloseable(str);
    }

    public static String get(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return getMDCAdapter().get(str);
    }

    public static void remove(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().remove(str);
    }

    public static void clear() {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().clear();
    }

    public static Map<String, String> getCopyOfContextMap() {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return getMDCAdapter().getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> map) {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().setContextMap(map);
    }

    public static MDCAdapter getMDCAdapter() {
        if (MDC_ADAPTER == null) {
            MDC_ADAPTER = getMDCAdapterGivenByProvider();
        }
        return MDC_ADAPTER;
    }

    static void setMDCAdapter(MDCAdapter mDCAdapter) {
        if (mDCAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        MDC_ADAPTER = mDCAdapter;
    }

    public static void pushByKey(String str, String str2) {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().pushByKey(str, str2);
    }

    public static String popByKey(String str) {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return getMDCAdapter().popByKey(str);
    }

    public Deque<String> getCopyOfDequeByKey(String str) {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return getMDCAdapter().getCopyOfDequeByKey(str);
    }
}
