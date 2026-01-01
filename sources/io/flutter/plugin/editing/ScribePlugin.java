package io.flutter.plugin.editing;

import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import io.flutter.embedding.engine.systemchannels.ScribeChannel;

/* loaded from: classes4.dex */
public class ScribePlugin implements ScribeChannel.ScribeMethodHandler {
    private final InputMethodManager mInputMethodManager;
    private final ScribeChannel mScribeChannel;
    private View mView;

    public ScribePlugin(View view, InputMethodManager inputMethodManager, ScribeChannel scribeChannel) {
        if (Build.VERSION.SDK_INT >= 33) {
            view.setAutoHandwritingEnabled(false);
        }
        this.mView = view;
        this.mInputMethodManager = inputMethodManager;
        this.mScribeChannel = scribeChannel;
        scribeChannel.setScribeMethodHandler(this);
    }

    public void setView(View view) {
        if (view == this.mView) {
            return;
        }
        this.mView = view;
    }

    public void destroy() {
        this.mScribeChannel.setScribeMethodHandler(null);
    }

    @Override // io.flutter.embedding.engine.systemchannels.ScribeChannel.ScribeMethodHandler
    public boolean isStylusHandwritingAvailable() {
        return this.mInputMethodManager.isStylusHandwritingAvailable();
    }

    @Override // io.flutter.embedding.engine.systemchannels.ScribeChannel.ScribeMethodHandler
    public void startStylusHandwriting() {
        this.mInputMethodManager.startStylusHandwriting(this.mView);
    }

    @Override // io.flutter.embedding.engine.systemchannels.ScribeChannel.ScribeMethodHandler
    public boolean isFeatureAvailable() {
        return Build.VERSION.SDK_INT >= 34 && isStylusHandwritingAvailable();
    }
}
