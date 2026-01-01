package com.mr.flutter.plugin.filepicker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: FilePickerPlugin.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 '2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002'(B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J(\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\rH\u0016J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\rH\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u0007H\u0016J\b\u0010$\u001a\u00020\u0017H\u0016J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u0007H\u0016J\b\u0010&\u001a\u00020\u0017H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0018\u00010\u0011R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FilePickerPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "<init>", "()V", "activityBinding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "delegate", "Lcom/mr/flutter/plugin/filepicker/FilePickerDelegate;", "application", "Landroid/app/Application;", "pluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "observer", "Lcom/mr/flutter/plugin/filepicker/FilePickerPlugin$LifeCycleObserver;", "activity", "Landroid/app/Activity;", "channel", "Lio/flutter/plugin/common/MethodChannel;", "onMethodCall", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "rawResult", "Lio/flutter/plugin/common/MethodChannel$Result;", "setup", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "tearDown", "onAttachedToEngine", "binding", "onDetachedFromEngine", "onAttachedToActivity", "onDetachedFromActivityForConfigChanges", "onReattachedToActivityForConfigChanges", "onDetachedFromActivity", "Companion", "LifeCycleObserver", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FilePickerPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin, ActivityAware {
    private static final String CHANNEL = "miguelruivo.flutter.plugins.filepicker";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EVENT_CHANNEL = "miguelruivo.flutter.plugins.filepickerevent";
    private static final String TAG = "FilePicker";
    private Activity activity;
    private ActivityPluginBinding activityBinding;
    private Application application;
    private MethodChannel channel;
    private FilePickerDelegate delegate;
    private Lifecycle lifecycle;
    private LifeCycleObserver observer;
    private FlutterPlugin.FlutterPluginBinding pluginBinding;

    /* compiled from: FilePickerPlugin.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FilePickerPlugin$Companion;", "", "<init>", "()V", "TAG", "", "CHANNEL", "EVENT_CHANNEL", "resolveType", "type", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String resolveType(String type) {
            switch (type.hashCode()) {
                case -1349088399:
                    if (type.equals("custom")) {
                        return "*/*";
                    }
                    return null;
                case 96748:
                    if (type.equals("any")) {
                        return "*/*";
                    }
                    return null;
                case 99469:
                    if (type.equals("dir")) {
                        return "dir";
                    }
                    return null;
                case 93166550:
                    if (type.equals("audio")) {
                        return "audio/*";
                    }
                    return null;
                case 100313435:
                    if (type.equals("image")) {
                        return "image/*";
                    }
                    return null;
                case 103772132:
                    if (type.equals("media")) {
                        return "image/*,video/*";
                    }
                    return null;
                case 112202875:
                    if (type.equals("video")) {
                        return "video/*";
                    }
                    return null;
                default:
                    return null;
            }
        }
    }

    /* compiled from: FilePickerPlugin.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0018\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0013H\u0016J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FilePickerPlugin$LifeCycleObserver;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "Landroidx/lifecycle/DefaultLifecycleObserver;", "thisActivity", "Landroid/app/Activity;", "<init>", "(Lcom/mr/flutter/plugin/filepicker/FilePickerPlugin;Landroid/app/Activity;)V", "onCreate", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "onStart", "onResume", "onPause", "onStop", "onDestroy", "onActivityCreated", "activity", "savedInstanceState", "Landroid/os/Bundle;", "onActivityStarted", "onActivityResumed", "onActivityPaused", "onActivitySaveInstanceState", "outState", "onActivityDestroyed", "onActivityStopped", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    private final class LifeCycleObserver implements Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {
        final /* synthetic */ FilePickerPlugin this$0;
        private final Activity thisActivity;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(outState, "outState");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onCreate(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onPause(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onResume(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStart(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
        }

        public LifeCycleObserver(FilePickerPlugin filePickerPlugin, Activity thisActivity) {
            Intrinsics.checkNotNullParameter(thisActivity, "thisActivity");
            this.this$0 = filePickerPlugin;
            this.thisActivity = thisActivity;
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStop(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            onActivityStopped(this.thisActivity);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onDestroy(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            onActivityDestroyed(this.thisActivity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (this.thisActivity != activity || activity.getApplicationContext() == null) {
                return;
            }
            Context applicationContext = activity.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type android.app.Application");
            ((Application) applicationContext).unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result rawResult) throws IOException {
        Context applicationContext;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(rawResult, "rawResult");
        if (this.activity == null) {
            rawResult.error("no_activity", "file picker plugin requires a foreground activity", null);
            return;
        }
        MethodResultWrapper methodResultWrapper = new MethodResultWrapper(rawResult);
        Object obj = call.arguments;
        HashMap map = obj instanceof HashMap ? (HashMap) obj : null;
        String str = call.method;
        if (str != null) {
            int iHashCode = str.hashCode();
            if (iHashCode != -1349088399) {
                if (iHashCode != 3522941) {
                    if (iHashCode == 94746189 && str.equals("clear")) {
                        Activity activity = this.activity;
                        if (activity != null && (applicationContext = activity.getApplicationContext()) != null) {
                            objValueOf = Boolean.valueOf(FileUtils.clearCache(applicationContext));
                        }
                        methodResultWrapper.success(objValueOf);
                        return;
                    }
                } else if (str.equals("save")) {
                    Companion companion = INSTANCE;
                    Object obj2 = map != null ? map.get("fileType") : null;
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
                    String strResolveType = companion.resolveType((String) obj2);
                    String str2 = (String) (map != null ? map.get("initialDirectory") : null);
                    byte[] bArr = (byte[]) (map != null ? map.get("bytes") : null);
                    String strValueOf = String.valueOf(map != null ? map.get("fileName") : null);
                    String str3 = strValueOf;
                    if (str3.length() > 0 && !StringsKt.contains$default((CharSequence) str3, (CharSequence) ".", false, 2, (Object) null)) {
                        strValueOf = strValueOf + "." + FileUtils.INSTANCE.getFileExtension(bArr);
                    }
                    String str4 = strValueOf;
                    FilePickerDelegate filePickerDelegate = this.delegate;
                    if (filePickerDelegate != null) {
                        FileUtils.INSTANCE.saveFile(filePickerDelegate, str4, strResolveType, str2, bArr, methodResultWrapper);
                        return;
                    }
                    return;
                }
            } else if (str.equals("custom")) {
                ArrayList<String> mimeTypes = FileUtils.INSTANCE.getMimeTypes((ArrayList) (map != null ? map.get("allowedExtensions") : null));
                FilePickerDelegate filePickerDelegate2 = this.delegate;
                if (filePickerDelegate2 != null) {
                    FileUtils.INSTANCE.startFileExplorer(filePickerDelegate2, INSTANCE.resolveType(str), (Boolean) (map != null ? map.get("allowMultipleSelection") : null), (Boolean) (map != null ? map.get("withData") : null), mimeTypes, (Integer) (map != null ? map.get("compressionQuality") : null), methodResultWrapper);
                    return;
                }
                return;
            }
        }
        Companion companion2 = INSTANCE;
        Intrinsics.checkNotNull(str);
        String strResolveType2 = companion2.resolveType(str);
        if (strResolveType2 == null) {
            methodResultWrapper.notImplemented();
            return;
        }
        FilePickerDelegate filePickerDelegate3 = this.delegate;
        if (filePickerDelegate3 != null) {
            FileUtils.INSTANCE.startFileExplorer(filePickerDelegate3, strResolveType2, (Boolean) (map != null ? map.get("allowMultipleSelection") : null), (Boolean) (map != null ? map.get("withData") : null), FileUtils.INSTANCE.getMimeTypes((ArrayList) (map != null ? map.get("allowedExtensions") : null)), (Integer) (map != null ? map.get("compressionQuality") : null), methodResultWrapper);
        }
    }

    private final void setup(BinaryMessenger messenger, Application application, Activity activity, ActivityPluginBinding activityBinding) {
        this.activity = activity;
        this.application = application;
        this.delegate = new FilePickerDelegate(activity, null, 2, null);
        MethodChannel methodChannel = new MethodChannel(messenger, CHANNEL);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        final FilePickerDelegate filePickerDelegate = this.delegate;
        if (filePickerDelegate != null) {
            new EventChannel(messenger, EVENT_CHANNEL).setStreamHandler(new EventChannel.StreamHandler() { // from class: com.mr.flutter.plugin.filepicker.FilePickerPlugin$setup$1$1
                @Override // io.flutter.plugin.common.EventChannel.StreamHandler
                public void onListen(Object arguments, EventChannel.EventSink events) {
                    filePickerDelegate.setEventHandler(events);
                }

                @Override // io.flutter.plugin.common.EventChannel.StreamHandler
                public void onCancel(Object arguments) {
                    filePickerDelegate.setEventHandler(null);
                }
            });
            this.observer = new LifeCycleObserver(this, activity);
            activityBinding.addActivityResultListener(filePickerDelegate);
            Lifecycle activityLifecycle = FlutterLifecycleAdapter.getActivityLifecycle(activityBinding);
            this.lifecycle = activityLifecycle;
            LifeCycleObserver lifeCycleObserver = this.observer;
            if (lifeCycleObserver == null || activityLifecycle == null) {
                return;
            }
            activityLifecycle.addObserver(lifeCycleObserver);
        }
    }

    private final void tearDown() {
        ActivityPluginBinding activityPluginBinding;
        FilePickerDelegate filePickerDelegate = this.delegate;
        if (filePickerDelegate != null && (activityPluginBinding = this.activityBinding) != null) {
            activityPluginBinding.removeActivityResultListener(filePickerDelegate);
        }
        this.activityBinding = null;
        LifeCycleObserver lifeCycleObserver = this.observer;
        if (lifeCycleObserver != null) {
            Lifecycle lifecycle = this.lifecycle;
            if (lifecycle != null) {
                lifecycle.removeObserver(lifeCycleObserver);
            }
            Application application = this.application;
            if (application != null) {
                application.unregisterActivityLifecycleCallbacks(lifeCycleObserver);
            }
        }
        this.lifecycle = null;
        FilePickerDelegate filePickerDelegate2 = this.delegate;
        if (filePickerDelegate2 != null) {
            filePickerDelegate2.setEventHandler(null);
        }
        this.delegate = null;
        MethodChannel methodChannel = this.channel;
        if (methodChannel != null) {
            methodChannel.setMethodCallHandler(null);
        }
        this.channel = null;
        this.application = null;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.pluginBinding = binding;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.pluginBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activityBinding = binding;
        FlutterPlugin.FlutterPluginBinding flutterPluginBinding = this.pluginBinding;
        if (flutterPluginBinding != null) {
            BinaryMessenger binaryMessenger = flutterPluginBinding.getBinaryMessenger();
            Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
            Context applicationContext = flutterPluginBinding.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type android.app.Application");
            ActivityPluginBinding activityPluginBinding = this.activityBinding;
            Intrinsics.checkNotNull(activityPluginBinding);
            Activity activity = activityPluginBinding.getActivity();
            Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
            ActivityPluginBinding activityPluginBinding2 = this.activityBinding;
            Intrinsics.checkNotNull(activityPluginBinding2);
            setup(binaryMessenger, (Application) applicationContext, activity, activityPluginBinding2);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        onAttachedToActivity(binding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        tearDown();
    }
}
