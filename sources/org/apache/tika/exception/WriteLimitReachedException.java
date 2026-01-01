package org.apache.tika.exception;

import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class WriteLimitReachedException extends SAXException {
    private static final int MAX_DEPTH = 100;
    private final int writeLimit;

    public WriteLimitReachedException(int i) {
        this.writeLimit = i;
    }

    @Override // org.xml.sax.SAXException, java.lang.Throwable
    public String getMessage() {
        return "Your document contained more than " + this.writeLimit + " characters, and so your requested limit has been reached. To receive the full text of the document, increase your limit. (Text up to the limit is however available).";
    }

    public static boolean isWriteLimitReached(Throwable th) {
        return isWriteLimitReached(th, 0);
    }

    private static boolean isWriteLimitReached(Throwable th, int i) {
        if (th == null || i > 100) {
            return false;
        }
        if (th instanceof WriteLimitReachedException) {
            return true;
        }
        return isWriteLimitReached(th.getCause(), i + 1);
    }

    public static void throwIfWriteLimitReached(Exception exc) throws SAXException {
        throwIfWriteLimitReached(exc, 0);
    }

    private static void throwIfWriteLimitReached(Throwable th, int i) throws SAXException {
        if (th != null && i <= 100) {
            if (th instanceof WriteLimitReachedException) {
                throw ((SAXException) th);
            }
            throwIfWriteLimitReached(th.getCause(), i + 1);
        }
    }
}
