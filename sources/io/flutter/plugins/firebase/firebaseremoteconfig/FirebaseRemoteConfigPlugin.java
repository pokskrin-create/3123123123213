package io.flutter.plugins.firebase.firebaseremoteconfig;

import android.os.Handler;
import android.os.Looper;
import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration;
import com.google.firebase.remoteconfig.CustomSignals;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import com.google.firebase.remoteconfig.RemoteConfigComponent;
import com.google.firebase.remoteconfig.RemoteConfigConstants;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugins.firebase.core.FlutterFirebasePlugin;
import io.flutter.plugins.firebase.core.FlutterFirebasePluginRegistry;
import io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: FirebaseRemoteConfigPlugin.kt */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 L2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0001LB\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\"\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00180\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0017H\u0016J\u0010\u0010!\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\"\u001a\u00020\u0012H\u0002J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\tH\u0002J,\u0010%\u001a\b\u0012\u0004\u0012\u00020 0\u00172\u0006\u0010\u001d\u001a\u00020\u001e2\u0014\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018H\u0002J(\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00182\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020)0\u0018H\u0002J\u001c\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010+\u001a\u00020)H\u0002J\u0010\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020.H\u0002J\u0018\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u000204H\u0016J\u0012\u00105\u001a\u00020\u00122\b\u00102\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u00106\u001a\u00020\u0012H\u0002J8\u00107\u001a\u00020\u0012\"\u0004\b\u0000\u001082\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H80;\u0012\u0004\u0012\u00020\u00120:2\u000e\u0010<\u001a\n\u0018\u00010=j\u0004\u0018\u0001`>H\u0002J*\u0010?\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120;\u0012\u0004\u0012\u00020\u00120:H\u0016J*\u0010@\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0;\u0012\u0004\u0012\u00020\u00120:H\u0016J*\u0010B\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0;\u0012\u0004\u0012\u00020\u00120:H\u0016J2\u0010C\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0006\u0010D\u001a\u00020E2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120;\u0012\u0004\u0012\u00020\u00120:H\u0016J@\u0010F\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0014\u0010G\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00182\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120;\u0012\u0004\u0012\u00020\u00120:H\u0016J*\u0010H\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120;\u0012\u0004\u0012\u00020\u00120:H\u0016J@\u0010%\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2\u0014\u0010I\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00182\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120;\u0012\u0004\u0012\u00020\u00120:H\u0016J8\u0010J\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2&\u00109\u001a\"\u0012\u0018\u0012\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00180;\u0012\u0004\u0012\u00020\u00120:H\u0016J6\u0010K\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\t2$\u00109\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00180;\u0012\u0004\u0012\u00020\u00120:H\u0016R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lio/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigPlugin;", "Lio/flutter/plugins/firebase/core/FlutterFirebasePlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/EventChannel$StreamHandler;", "Lio/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigHostApi;", "<init>", "()V", "listenersMap", "", "", "Lcom/google/firebase/remoteconfig/ConfigUpdateListenerRegistration;", "eventChannel", "Lio/flutter/plugin/common/EventChannel;", "mainThreadHandler", "Landroid/os/Handler;", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "onAttachedToEngine", "", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "getPluginConstantsForFirebaseApp", "Lcom/google/android/gms/tasks/Task;", "", "", "firebaseApp", "Lcom/google/firebase/FirebaseApp;", "getConfigProperties", "remoteConfig", "Lcom/google/firebase/remoteconfig/FirebaseRemoteConfig;", "didReinitializeFirebaseCore", "Ljava/lang/Void;", "setupChannel", "tearDownChannel", "getRemoteConfig", "appName", "setCustomSignals", "customSignalsArguments", "parseParameters", "parameters", "Lcom/google/firebase/remoteconfig/FirebaseRemoteConfigValue;", "createRemoteConfigValueMap", "remoteConfigValue", "mapLastFetchStatus", "status", "", "mapValueSource", "source", "onListen", "arguments", "events", "Lio/flutter/plugin/common/EventChannel$EventSink;", "onCancel", "removeEventListeners", "handleFailure", "T", "callback", "Lkotlin/Function1;", "Lkotlin/Result;", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", RemoteConfigComponent.FETCH_FILE_NAME, "fetchAndActivate", "", RemoteConfigComponent.ACTIVATE_FILE_NAME, "setConfigSettings", "settings", "Lio/flutter/plugins/firebase/firebaseremoteconfig/RemoteConfigPigeonSettings;", "setDefaults", "defaultParameters", "ensureInitialized", RemoteConfigConstants.RequestFieldKey.CUSTOM_SIGNALS, "getAll", "getProperties", "Companion", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FirebaseRemoteConfigPlugin implements FlutterFirebasePlugin, FlutterPlugin, EventChannel.StreamHandler, FirebaseRemoteConfigHostApi {
    public static final String EVENT_CHANNEL = "plugins.flutter.io/firebase_remote_config_updated";
    public static final String METHOD_CHANNEL = "plugins.flutter.io/firebase_remote_config";
    public static final String TAG = "FRCPlugin";
    private EventChannel eventChannel;
    private final Map<String, ConfigUpdateListenerRegistration> listenersMap = new HashMap();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private BinaryMessenger messenger;

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        setupChannel(binaryMessenger);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        tearDownChannel();
    }

    @Override // io.flutter.plugins.firebase.core.FlutterFirebasePlugin
    public Task<Map<String, Object>> getPluginConstantsForFirebaseApp(final FirebaseApp firebaseApp) {
        Intrinsics.checkNotNullParameter(firebaseApp, "firebaseApp");
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        FlutterFirebasePlugin.cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                FirebaseRemoteConfigPlugin.getPluginConstantsForFirebaseApp$lambda$0(firebaseApp, this, taskCompletionSource);
            }
        });
        Task<Map<String, Object>> task = taskCompletionSource.getTask();
        Intrinsics.checkNotNullExpressionValue(task, "getTask(...)");
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getPluginConstantsForFirebaseApp$lambda$0(FirebaseApp firebaseApp, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, TaskCompletionSource taskCompletionSource) {
        try {
            FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance(firebaseApp);
            Intrinsics.checkNotNullExpressionValue(firebaseRemoteConfig, "getInstance(...)");
            HashMap map = new HashMap(firebaseRemoteConfigPlugin.getConfigProperties(firebaseRemoteConfig));
            Map<String, FirebaseRemoteConfigValue> all = firebaseRemoteConfig.getAll();
            Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
            map.put("parameters", firebaseRemoteConfigPlugin.parseParameters(all));
            taskCompletionSource.setResult(map);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private final Map<String, Object> getConfigProperties(FirebaseRemoteConfig remoteConfig) {
        HashMap map = new HashMap();
        map.put("fetchTimeout", Long.valueOf(remoteConfig.getInfo().getConfigSettings().getFetchTimeoutInSeconds()));
        map.put("minimumFetchInterval", Long.valueOf(remoteConfig.getInfo().getConfigSettings().getMinimumFetchIntervalInSeconds()));
        map.put("lastFetchTime", Long.valueOf(remoteConfig.getInfo().getFetchTimeMillis()));
        map.put("lastFetchStatus", mapLastFetchStatus(remoteConfig.getInfo().getLastFetchStatus()));
        return map;
    }

    @Override // io.flutter.plugins.firebase.core.FlutterFirebasePlugin
    public Task<Void> didReinitializeFirebaseCore() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        FlutterFirebasePlugin.cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FirebaseRemoteConfigPlugin.didReinitializeFirebaseCore$lambda$1(this.f$0, taskCompletionSource);
            }
        });
        Task<Void> task = taskCompletionSource.getTask();
        Intrinsics.checkNotNullExpressionValue(task, "getTask(...)");
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void didReinitializeFirebaseCore$lambda$1(FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, TaskCompletionSource taskCompletionSource) {
        try {
            firebaseRemoteConfigPlugin.removeEventListeners();
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private final void setupChannel(BinaryMessenger messenger) {
        FirebaseRemoteConfigHostApi.Companion.setUp$default(FirebaseRemoteConfigHostApi.INSTANCE, messenger, this, null, 4, null);
        FlutterFirebasePluginRegistry.registerPlugin(METHOD_CHANNEL, this);
        EventChannel eventChannel = new EventChannel(messenger, EVENT_CHANNEL);
        this.eventChannel = eventChannel;
        Intrinsics.checkNotNull(eventChannel);
        eventChannel.setStreamHandler(this);
        this.messenger = messenger;
    }

    private final void tearDownChannel() {
        if (this.messenger == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        FirebaseRemoteConfigHostApi.Companion companion = FirebaseRemoteConfigHostApi.INSTANCE;
        BinaryMessenger binaryMessenger = this.messenger;
        Intrinsics.checkNotNull(binaryMessenger);
        FirebaseRemoteConfigHostApi.Companion.setUp$default(companion, binaryMessenger, null, null, 4, null);
        this.messenger = null;
        EventChannel eventChannel = this.eventChannel;
        Intrinsics.checkNotNull(eventChannel);
        eventChannel.setStreamHandler(null);
        this.eventChannel = null;
        removeEventListeners();
    }

    private final FirebaseRemoteConfig getRemoteConfig(String appName) {
        FirebaseApp firebaseApp = FirebaseApp.getInstance(appName);
        Intrinsics.checkNotNullExpressionValue(firebaseApp, "getInstance(...)");
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance(firebaseApp);
        Intrinsics.checkNotNullExpressionValue(firebaseRemoteConfig, "getInstance(...)");
        return firebaseRemoteConfig;
    }

    private final Task<Void> setCustomSignals(final FirebaseRemoteConfig remoteConfig, final Map<String, ? extends Object> customSignalsArguments) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        FlutterFirebasePlugin.cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FirebaseRemoteConfigPlugin.setCustomSignals$lambda$2(customSignalsArguments, remoteConfig, taskCompletionSource);
            }
        });
        Task<Void> task = taskCompletionSource.getTask();
        Intrinsics.checkNotNullExpressionValue(task, "getTask(...)");
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCustomSignals$lambda$2(Map map, FirebaseRemoteConfig firebaseRemoteConfig, TaskCompletionSource taskCompletionSource) {
        try {
            CustomSignals.Builder builder = new CustomSignals.Builder();
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    builder.put(str, (String) value);
                } else if (value instanceof Long) {
                    builder.put(str, ((Number) value).longValue());
                } else if (value instanceof Integer) {
                    builder.put(str, ((Number) value).intValue());
                } else if (value instanceof Double) {
                    builder.put(str, ((Number) value).doubleValue());
                } else if (value == null) {
                    builder.put(str, (String) null);
                }
            }
            Tasks.await(firebaseRemoteConfig.setCustomSignals(builder.build()));
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private final Map<String, Object> parseParameters(Map<String, ? extends FirebaseRemoteConfigValue> parameters) {
        HashMap map = new HashMap();
        for (String str : parameters.keySet()) {
            FirebaseRemoteConfigValue firebaseRemoteConfigValue = parameters.get(str);
            Intrinsics.checkNotNull(firebaseRemoteConfigValue);
            map.put(str, createRemoteConfigValueMap(firebaseRemoteConfigValue));
        }
        return map;
    }

    private final Map<String, Object> createRemoteConfigValueMap(FirebaseRemoteConfigValue remoteConfigValue) {
        HashMap map = new HashMap();
        map.put("value", remoteConfigValue.asByteArray());
        map.put("source", mapValueSource(remoteConfigValue.getSource()));
        return map;
    }

    private final String mapLastFetchStatus(int status) {
        if (status == -1) {
            return FirebaseAnalytics.Param.SUCCESS;
        }
        if (status == 0) {
            return "noFetchYet";
        }
        if (status == 1 || status != 2) {
            return "failure";
        }
        return "throttled";
    }

    private final String mapValueSource(int source) {
        if (source == 0) {
            return "static";
        }
        if (source == 1) {
            return "default";
        }
        if (source != 2) {
            return "static";
        }
        return "remote";
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object arguments, EventChannel.EventSink events) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(events, "events");
        Object objRequireNonNull = Objects.requireNonNull(((Map) arguments).get("appName"));
        Intrinsics.checkNotNull(objRequireNonNull, "null cannot be cast to non-null type kotlin.String");
        String str = (String) objRequireNonNull;
        this.listenersMap.put(str, getRemoteConfig(str).addOnConfigUpdateListener(new AnonymousClass1(events)));
    }

    /* compiled from: FirebaseRemoteConfigPlugin.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"io/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigPlugin$onListen$1", "Lcom/google/firebase/remoteconfig/ConfigUpdateListener;", "onUpdate", "", "configUpdate", "Lcom/google/firebase/remoteconfig/ConfigUpdate;", "onError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/google/firebase/remoteconfig/FirebaseRemoteConfigException;", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    /* renamed from: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$onListen$1, reason: invalid class name */
    public static final class AnonymousClass1 implements ConfigUpdateListener {
        final /* synthetic */ EventChannel.EventSink $events;

        AnonymousClass1(EventChannel.EventSink eventSink) {
            this.$events = eventSink;
        }

        @Override // com.google.firebase.remoteconfig.ConfigUpdateListener
        public void onUpdate(ConfigUpdate configUpdate) {
            Intrinsics.checkNotNullParameter(configUpdate, "configUpdate");
            final ArrayList arrayList = new ArrayList(configUpdate.getUpdatedKeys());
            Handler handler = FirebaseRemoteConfigPlugin.this.mainThreadHandler;
            final EventChannel.EventSink eventSink = this.$events;
            handler.post(new Runnable() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$onListen$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    eventSink.success(arrayList);
                }
            });
        }

        @Override // com.google.firebase.remoteconfig.ConfigUpdateListener
        public void onError(FirebaseRemoteConfigException error) {
            Intrinsics.checkNotNullParameter(error, "error");
            this.$events.error("firebase_remote_config", error.getMessage(), null);
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object arguments) {
        Map map = arguments instanceof Map ? (Map) arguments : null;
        if (map == null) {
            return;
        }
        Object objRequireNonNull = Objects.requireNonNull(map.get("appName"));
        Intrinsics.checkNotNull(objRequireNonNull, "null cannot be cast to non-null type kotlin.String");
        String str = (String) objRequireNonNull;
        ConfigUpdateListenerRegistration configUpdateListenerRegistration = this.listenersMap.get(str);
        if (configUpdateListenerRegistration != null) {
            configUpdateListenerRegistration.remove();
            this.listenersMap.remove(str);
        }
    }

    private final void removeEventListeners() {
        Iterator<ConfigUpdateListenerRegistration> it = this.listenersMap.values().iterator();
        while (it.hasNext()) {
            it.next().remove();
        }
        this.listenersMap.clear();
    }

    private final <T> void handleFailure(Function1<? super Result<? extends T>, Unit> callback, Exception exception) {
        String message;
        HashMap map = new HashMap();
        if (exception instanceof FirebaseRemoteConfigFetchThrottledException) {
            map.put("code", "throttled");
            map.put("message", "frequency of requests exceeds throttled limits");
        } else if (exception instanceof FirebaseRemoteConfigClientException) {
            map.put("code", "internal");
            map.put("message", "internal remote config fetch error");
        } else if (exception instanceof FirebaseRemoteConfigServerException) {
            map.put("code", "remote-config-server-error");
            FirebaseRemoteConfigServerException firebaseRemoteConfigServerException = (FirebaseRemoteConfigServerException) exception;
            map.put("message", firebaseRemoteConfigServerException.getMessage());
            Throwable cause = firebaseRemoteConfigServerException.getCause();
            if (cause != null && (message = cause.getMessage()) != null && StringsKt.contains$default((CharSequence) message, (CharSequence) "Forbidden", false, 2, (Object) null)) {
                map.put("code", "forbidden");
            }
        } else {
            map.put("code", EnvironmentCompat.MEDIA_UNKNOWN);
            map.put("message", "unknown remote config error");
        }
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m478boximpl(Result.m479constructorimpl(ResultKt.createFailure(new FlutterError("firebase_remote_config", exception != null ? exception.getMessage() : null, map)))));
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void fetch(String appName, final Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        getRemoteConfig(appName).fetch().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.fetch$lambda$3(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetch$lambda$3(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(Unit.INSTANCE)));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void fetchAndActivate(String appName, final Function1<? super Result<Boolean>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        getRemoteConfig(appName).fetchAndActivate().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda6
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.fetchAndActivate$lambda$4(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchAndActivate$lambda$4(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(task.getResult())));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void activate(String appName, final Function1<? super Result<Boolean>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        getRemoteConfig(appName).activate().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda9
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.activate$lambda$5(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void activate$lambda$5(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(task.getResult())));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void setConfigSettings(String appName, RemoteConfigPigeonSettings settings, final Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(settings, "settings");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettingsBuild = new FirebaseRemoteConfigSettings.Builder().setFetchTimeoutInSeconds(settings.getFetchTimeoutSeconds()).setMinimumFetchIntervalInSeconds(settings.getMinimumFetchIntervalSeconds()).build();
        Intrinsics.checkNotNullExpressionValue(firebaseRemoteConfigSettingsBuild, "build(...)");
        getRemoteConfig(appName).setConfigSettingsAsync(firebaseRemoteConfigSettingsBuild).addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda3
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.setConfigSettings$lambda$6(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setConfigSettings$lambda$6(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(Unit.INSTANCE)));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void setDefaults(String appName, Map<String, ? extends Object> defaultParameters, final Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(defaultParameters, "defaultParameters");
        Intrinsics.checkNotNullParameter(callback, "callback");
        getRemoteConfig(appName).setDefaultsAsync(defaultParameters).addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda8
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.setDefaults$lambda$7(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setDefaults$lambda$7(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(Unit.INSTANCE)));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void ensureInitialized(String appName, final Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        getRemoteConfig(appName).ensureInitialized().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda7
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.ensureInitialized$lambda$8(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ensureInitialized$lambda$8(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(Unit.INSTANCE)));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void setCustomSignals(String appName, Map<String, ? extends Object> customSignals, final Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(customSignals, "customSignals");
        Intrinsics.checkNotNullParameter(callback, "callback");
        setCustomSignals(getRemoteConfig(appName), customSignals).addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda5
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FirebaseRemoteConfigPlugin.setCustomSignals$lambda$9(callback, this, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCustomSignals$lambda$9(Function1 function1, FirebaseRemoteConfigPlugin firebaseRemoteConfigPlugin, Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (task.isSuccessful()) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m478boximpl(Result.m479constructorimpl(Unit.INSTANCE)));
        } else {
            firebaseRemoteConfigPlugin.handleFailure(function1, task.getException());
        }
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void getAll(String appName, Function1<? super Result<? extends Map<String, ? extends Object>>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FirebaseRemoteConfig remoteConfig = getRemoteConfig(appName);
        Result.Companion companion = Result.INSTANCE;
        Map<String, FirebaseRemoteConfigValue> all = remoteConfig.getAll();
        Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
        callback.invoke(Result.m478boximpl(Result.m479constructorimpl(parseParameters(all))));
    }

    @Override // io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi
    public void getProperties(String appName, Function1<? super Result<? extends Map<String, ? extends Object>>, Unit> callback) {
        Intrinsics.checkNotNullParameter(appName, "appName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Map<String, Object> configProperties = getConfigProperties(getRemoteConfig(appName));
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m478boximpl(Result.m479constructorimpl(configProperties)));
    }
}
