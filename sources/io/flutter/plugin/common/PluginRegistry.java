package io.flutter.plugin.common;

import android.content.Intent;

/* loaded from: classes4.dex */
public interface PluginRegistry {

    public interface ActivityResultListener {
        boolean onActivityResult(int i, int i2, Intent intent);
    }

    public interface NewIntentListener {
        boolean onNewIntent(Intent intent);
    }

    public interface RequestPermissionsResultListener {
        boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    public interface UserLeaveHintListener {
        void onUserLeaveHint();
    }

    public interface WindowFocusChangedListener {
        void onWindowFocusChanged(boolean z);
    }
}
