package io.flutter.plugin.view;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import io.flutter.embedding.engine.systemchannels.SensitiveContentChannel;

/* loaded from: classes4.dex */
public class SensitiveContentPlugin implements SensitiveContentChannel.SensitiveContentMethodHandler {
    private Activity mFlutterActivity;
    private final int mFlutterViewId;
    private final SensitiveContentChannel mSensitiveContentChannel;

    public SensitiveContentPlugin(int i, Activity activity, SensitiveContentChannel sensitiveContentChannel) {
        this.mFlutterActivity = activity;
        this.mFlutterViewId = i;
        this.mSensitiveContentChannel = sensitiveContentChannel;
        sensitiveContentChannel.setSensitiveContentMethodHandler(this);
    }

    private String getNotSupportedErrorReason() {
        return "isSupported() should be called before attempting to set content sensitivity as it is not supported on this device.";
    }

    private String getFlutterViewNotFoundErrorReason() {
        return "FlutterView with ID " + this.mFlutterViewId + "not found";
    }

    @Override // io.flutter.embedding.engine.systemchannels.SensitiveContentChannel.SensitiveContentMethodHandler
    public void setContentSensitivity(int i) {
        if (!isSupported()) {
            throw new IllegalStateException(getNotSupportedErrorReason());
        }
        View viewFindViewById = this.mFlutterActivity.findViewById(this.mFlutterViewId);
        if (viewFindViewById == null) {
            throw new IllegalArgumentException(getFlutterViewNotFoundErrorReason());
        }
        if (viewFindViewById.getContentSensitivity() == i) {
            return;
        }
        viewFindViewById.setContentSensitivity(i);
        viewFindViewById.invalidate();
    }

    @Override // io.flutter.embedding.engine.systemchannels.SensitiveContentChannel.SensitiveContentMethodHandler
    public int getContentSensitivity() {
        if (!isSupported()) {
            return 2;
        }
        View viewFindViewById = this.mFlutterActivity.findViewById(this.mFlutterViewId);
        if (viewFindViewById == null) {
            throw new IllegalArgumentException(getFlutterViewNotFoundErrorReason());
        }
        return viewFindViewById.getContentSensitivity();
    }

    @Override // io.flutter.embedding.engine.systemchannels.SensitiveContentChannel.SensitiveContentMethodHandler
    public boolean isSupported() {
        return Build.VERSION.SDK_INT >= 35;
    }

    public void destroy() {
        this.mSensitiveContentChannel.setSensitiveContentMethodHandler(null);
        this.mFlutterActivity = null;
    }
}
