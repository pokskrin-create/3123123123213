package androidx.browser.trusted;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class LaunchHandlerClientMode {
    public static final int AUTO = 0;
    public static final int FOCUS_EXISTING = 2;
    public static final int NAVIGATE_EXISTING = 1;
    public static final int NAVIGATE_NEW = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClientMode {
    }

    private LaunchHandlerClientMode() {
    }
}
