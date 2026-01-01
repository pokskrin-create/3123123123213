package com.mr.flutter.plugin.filepicker;

import android.os.Handler;
import android.os.Looper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MethodResultWrapper.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J$\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u0010\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/MethodResultWrapper;", "Lio/flutter/plugin/common/MethodChannel$Result;", "methodResult", "<init>", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "handler", "Landroid/os/Handler;", FirebaseAnalytics.Param.SUCCESS, "", "result", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "errorCode", "", "errorMessage", "errorDetails", "notImplemented", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class MethodResultWrapper implements MethodChannel.Result {
    private final Handler handler;
    private final MethodChannel.Result methodResult;

    public MethodResultWrapper(MethodChannel.Result methodResult) {
        Intrinsics.checkNotNullParameter(methodResult, "methodResult");
        this.methodResult = methodResult;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void success(final Object result) {
        this.handler.post(new Runnable() { // from class: com.mr.flutter.plugin.filepicker.MethodResultWrapper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MethodResultWrapper.success$lambda$0(this.f$0, result);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void success$lambda$0(MethodResultWrapper methodResultWrapper, Object obj) {
        methodResultWrapper.methodResult.success(obj);
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void error(final String errorCode, final String errorMessage, final Object errorDetails) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        this.handler.post(new Runnable() { // from class: com.mr.flutter.plugin.filepicker.MethodResultWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MethodResultWrapper.error$lambda$1(this.f$0, errorCode, errorMessage, errorDetails);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void error$lambda$1(MethodResultWrapper methodResultWrapper, String str, String str2, Object obj) {
        methodResultWrapper.methodResult.error(str, str2, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notImplemented$lambda$2(MethodResultWrapper methodResultWrapper) {
        methodResultWrapper.methodResult.notImplemented();
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void notImplemented() {
        this.handler.post(new Runnable() { // from class: com.mr.flutter.plugin.filepicker.MethodResultWrapper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MethodResultWrapper.notImplemented$lambda$2(this.f$0);
            }
        });
    }
}
