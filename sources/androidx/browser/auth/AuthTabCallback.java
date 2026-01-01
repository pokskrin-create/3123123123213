package androidx.browser.auth;

import android.os.Bundle;

/* loaded from: classes.dex */
public interface AuthTabCallback {
    void onExtraCallback(String str, Bundle bundle);

    Bundle onExtraCallbackWithResult(String str, Bundle bundle);

    void onNavigationEvent(int i, Bundle bundle);

    void onWarmupCompleted(Bundle bundle);
}
