package org.apache.commons.io.input;

/* loaded from: classes4.dex */
final class UnsupportedOperationExceptions {
    private static final String MARK_RESET = "mark/reset";

    UnsupportedOperationExceptions() {
    }

    static UnsupportedOperationException mark() {
        return method(MARK_RESET);
    }

    static UnsupportedOperationException method(String str) {
        return new UnsupportedOperationException(str + " not supported");
    }

    static UnsupportedOperationException reset() {
        return method(MARK_RESET);
    }
}
