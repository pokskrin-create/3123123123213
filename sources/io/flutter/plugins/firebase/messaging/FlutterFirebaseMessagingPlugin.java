package io.flutter.plugins.firebase.messaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.os.EnvironmentCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.firebase.core.FlutterFirebasePlugin;
import io.flutter.plugins.firebase.core.FlutterFirebasePluginRegistry;
import io.flutter.plugins.firebase.messaging.FlutterFirebasePermissionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class FlutterFirebaseMessagingPlugin implements FlutterFirebasePlugin, MethodChannel.MethodCallHandler, PluginRegistry.NewIntentListener, FlutterPlugin, ActivityAware {
    private MethodChannel channel;
    private RemoteMessage initialMessage;
    private Map<String, Object> initialMessageNotification;
    private Activity mainActivity;
    FlutterFirebasePermissionManager permissionManager;
    private Observer<RemoteMessage> remoteMessageObserver;
    private Observer<String> tokenObserver;
    private final HashMap<String, Boolean> consumedInitialMessages = new HashMap<>();
    private final LiveData<RemoteMessage> liveDataRemoteMessage = FlutterFirebaseRemoteMessageLiveData.getInstance();
    private final LiveData<String> liveDataToken = FlutterFirebaseTokenLiveData.getInstance();

    private void initInstance(BinaryMessenger binaryMessenger) {
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "plugins.flutter.io/firebase_messaging");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.permissionManager = new FlutterFirebasePermissionManager();
        this.remoteMessageObserver = new Observer() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initInstance$0((RemoteMessage) obj);
            }
        };
        this.tokenObserver = new Observer() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initInstance$1((String) obj);
            }
        };
        this.liveDataRemoteMessage.observeForever(this.remoteMessageObserver);
        this.liveDataToken.observeForever(this.tokenObserver);
        FlutterFirebasePluginRegistry.registerPlugin("plugins.flutter.io/firebase_messaging", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initInstance$0(RemoteMessage remoteMessage) {
        this.channel.invokeMethod("Messaging#onMessage", FlutterFirebaseMessagingUtils.remoteMessageToMap(remoteMessage));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initInstance$1(String str) {
        this.channel.invokeMethod("Messaging#onTokenRefresh", str);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        ContextHolder.setApplicationContext(flutterPluginBinding.getApplicationContext());
        initInstance(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.liveDataToken.removeObserver(this.tokenObserver);
        this.liveDataRemoteMessage.removeObserver(this.remoteMessageObserver);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        activityPluginBinding.addOnNewIntentListener(this);
        activityPluginBinding.addRequestPermissionsResultListener(this.permissionManager);
        Activity activity = activityPluginBinding.getActivity();
        this.mainActivity = activity;
        if (activity.getIntent() == null || this.mainActivity.getIntent().getExtras() == null || (this.mainActivity.getIntent().getFlags() & 1048576) == 1048576) {
            return;
        }
        onNewIntent(this.mainActivity.getIntent());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.mainActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        activityPluginBinding.addOnNewIntentListener(this);
        this.mainActivity = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.mainActivity = null;
    }

    private Task<Void> deleteToken() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$deleteToken$2(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$deleteToken$2(TaskCompletionSource taskCompletionSource) {
        try {
            Tasks.await(FirebaseMessaging.getInstance().deleteToken());
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Map<String, Object>> getToken() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getToken$3(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getToken$3(TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(new HashMap<String, Object>((String) Tasks.await(FirebaseMessaging.getInstance().getToken())) { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin.1
                final /* synthetic */ String val$token;

                {
                    this.val$token = str;
                    put("token", str);
                }
            });
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Void> subscribeToTopic(final Map<String, Object> map) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$subscribeToTopic$4(map, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$subscribeToTopic$4(Map map, TaskCompletionSource taskCompletionSource) {
        try {
            Tasks.await(FlutterFirebaseMessagingUtils.getFirebaseMessagingForArguments(map).subscribeToTopic((String) Objects.requireNonNull(map.get("topic"))));
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Void> unsubscribeFromTopic(final Map<String, Object> map) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$unsubscribeFromTopic$5(map, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$unsubscribeFromTopic$5(Map map, TaskCompletionSource taskCompletionSource) {
        try {
            Tasks.await(FlutterFirebaseMessagingUtils.getFirebaseMessagingForArguments(map).unsubscribeFromTopic((String) Objects.requireNonNull(map.get("topic"))));
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Void> sendMessage(final Map<String, Object> map) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$sendMessage$6(map, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$sendMessage$6(Map map, TaskCompletionSource taskCompletionSource) {
        try {
            FlutterFirebaseMessagingUtils.getFirebaseMessagingForArguments(map).send(FlutterFirebaseMessagingUtils.getRemoteMessageForArguments(map));
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Map<String, Object>> setAutoInitEnabled(final Map<String, Object> map) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setAutoInitEnabled$7(map, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAutoInitEnabled$7(Map map, TaskCompletionSource taskCompletionSource) {
        try {
            FirebaseMessaging firebaseMessagingForArguments = FlutterFirebaseMessagingUtils.getFirebaseMessagingForArguments(map);
            firebaseMessagingForArguments.setAutoInitEnabled(((Boolean) Objects.requireNonNull(map.get("enabled"))).booleanValue());
            taskCompletionSource.setResult(new HashMap<String, Object>(firebaseMessagingForArguments) { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin.2
                final /* synthetic */ FirebaseMessaging val$firebaseMessaging;

                {
                    this.val$firebaseMessaging = firebaseMessagingForArguments;
                    put("isAutoInitEnabled", Boolean.valueOf(firebaseMessagingForArguments.isAutoInitEnabled()));
                }
            });
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Void> setDeliveryMetricsExportToBigQuery(final Map<String, Object> map) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$setDeliveryMetricsExportToBigQuery$8(map, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$setDeliveryMetricsExportToBigQuery$8(Map map, TaskCompletionSource taskCompletionSource) {
        try {
            FlutterFirebaseMessagingUtils.getFirebaseMessagingForArguments(map).setDeliveryMetricsExportToBigQuery(((Boolean) Objects.requireNonNull(map.get("enabled"))).booleanValue());
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    private Task<Map<String, Object>> getInitialMessage() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getInitialMessage$9(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0078 A[PHI: r0
  0x0078: PHI (r0v11 com.google.firebase.messaging.RemoteMessage) = (r0v8 com.google.firebase.messaging.RemoteMessage), (r0v13 com.google.firebase.messaging.RemoteMessage) binds: [B:29:0x0063, B:31:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getInitialMessage$9(com.google.android.gms.tasks.TaskCompletionSource r7) {
        /*
            r6 = this;
            com.google.firebase.messaging.RemoteMessage r0 = r6.initialMessage     // Catch: java.lang.Exception -> Lad
            java.lang.String r1 = "notification"
            r2 = 0
            if (r0 == 0) goto L1a
            java.util.Map r0 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingUtils.remoteMessageToMap(r0)     // Catch: java.lang.Exception -> Lad
            java.util.Map<java.lang.String, java.lang.Object> r3 = r6.initialMessageNotification     // Catch: java.lang.Exception -> Lad
            if (r3 == 0) goto L12
            r0.put(r1, r3)     // Catch: java.lang.Exception -> Lad
        L12:
            r7.setResult(r0)     // Catch: java.lang.Exception -> Lad
            r6.initialMessage = r2     // Catch: java.lang.Exception -> Lad
            r6.initialMessageNotification = r2     // Catch: java.lang.Exception -> Lad
            return
        L1a:
            android.app.Activity r0 = r6.mainActivity     // Catch: java.lang.Exception -> Lad
            if (r0 != 0) goto L22
            r7.setResult(r2)     // Catch: java.lang.Exception -> Lad
            return
        L22:
            android.content.Intent r0 = r0.getIntent()     // Catch: java.lang.Exception -> Lad
            if (r0 == 0) goto La9
            android.os.Bundle r3 = r0.getExtras()     // Catch: java.lang.Exception -> Lad
            if (r3 != 0) goto L30
            goto La9
        L30:
            android.os.Bundle r3 = r0.getExtras()     // Catch: java.lang.Exception -> Lad
            java.lang.String r4 = "google.message_id"
            java.lang.String r3 = r3.getString(r4)     // Catch: java.lang.Exception -> Lad
            if (r3 != 0) goto L46
            android.os.Bundle r0 = r0.getExtras()     // Catch: java.lang.Exception -> Lad
            java.lang.String r3 = "message_id"
            java.lang.String r3 = r0.getString(r3)     // Catch: java.lang.Exception -> Lad
        L46:
            if (r3 == 0) goto La5
            java.util.HashMap<java.lang.String, java.lang.Boolean> r0 = r6.consumedInitialMessages     // Catch: java.lang.Exception -> Lad
            java.lang.Object r0 = r0.get(r3)     // Catch: java.lang.Exception -> Lad
            if (r0 == 0) goto L51
            goto La5
        L51:
            java.util.HashMap<java.lang.String, com.google.firebase.messaging.RemoteMessage> r0 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingReceiver.notifications     // Catch: java.lang.Exception -> Lad
            java.lang.Object r0 = r0.get(r3)     // Catch: java.lang.Exception -> Lad
            com.google.firebase.messaging.RemoteMessage r0 = (com.google.firebase.messaging.RemoteMessage) r0     // Catch: java.lang.Exception -> Lad
            if (r0 != 0) goto L81
            io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingStore r4 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingStore.getInstance()     // Catch: java.lang.Exception -> Lad
            java.util.Map r4 = r4.getFirebaseMessageMap(r3)     // Catch: java.lang.Exception -> Lad
            if (r4 == 0) goto L78
            com.google.firebase.messaging.RemoteMessage r0 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingUtils.getRemoteMessageForArguments(r4)     // Catch: java.lang.Exception -> Lad
            java.lang.Object r5 = r4.get(r1)     // Catch: java.lang.Exception -> Lad
            if (r5 == 0) goto L78
            java.lang.Object r4 = r4.get(r1)     // Catch: java.lang.Exception -> Lad
            java.util.Map r4 = r6.uncheckedCastToMap(r4)     // Catch: java.lang.Exception -> Lad
            goto L79
        L78:
            r4 = r2
        L79:
            io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingStore r5 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingStore.getInstance()     // Catch: java.lang.Exception -> Lad
            r5.removeFirebaseMessage(r3)     // Catch: java.lang.Exception -> Lad
            goto L82
        L81:
            r4 = r2
        L82:
            if (r0 != 0) goto L88
            r7.setResult(r2)     // Catch: java.lang.Exception -> Lad
            return
        L88:
            java.util.HashMap<java.lang.String, java.lang.Boolean> r2 = r6.consumedInitialMessages     // Catch: java.lang.Exception -> Lad
            r5 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Exception -> Lad
            r2.put(r3, r5)     // Catch: java.lang.Exception -> Lad
            java.util.Map r2 = io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingUtils.remoteMessageToMap(r0)     // Catch: java.lang.Exception -> Lad
            com.google.firebase.messaging.RemoteMessage$Notification r0 = r0.getNotification()     // Catch: java.lang.Exception -> Lad
            if (r0 != 0) goto La1
            if (r4 == 0) goto La1
            r2.put(r1, r4)     // Catch: java.lang.Exception -> Lad
        La1:
            r7.setResult(r2)     // Catch: java.lang.Exception -> Lad
            return
        La5:
            r7.setResult(r2)     // Catch: java.lang.Exception -> Lad
            return
        La9:
            r7.setResult(r2)     // Catch: java.lang.Exception -> Lad
            return
        Lad:
            r0 = move-exception
            r7.setException(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin.lambda$getInitialMessage$9(com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    private Task<Map<String, Integer>> requestPermissions() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestPermissions$12(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermissions$12(final TaskCompletionSource taskCompletionSource) {
        final HashMap map = new HashMap();
        try {
            if (!checkPermissions().booleanValue()) {
                this.permissionManager.requestPermissions(this.mainActivity, new FlutterFirebasePermissionManager.RequestPermissionsSuccessCallback() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda3
                    @Override // io.flutter.plugins.firebase.messaging.FlutterFirebasePermissionManager.RequestPermissionsSuccessCallback
                    public final void onSuccess(int i) {
                        FlutterFirebaseMessagingPlugin.lambda$requestPermissions$10(map, taskCompletionSource, i);
                    }
                }, new ErrorCallback() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda4
                    @Override // io.flutter.plugins.firebase.messaging.ErrorCallback
                    public final void onError(String str) {
                        taskCompletionSource.setException(new Exception(str));
                    }
                });
            } else {
                map.put("authorizationStatus", 1);
                taskCompletionSource.setResult(map);
            }
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    static /* synthetic */ void lambda$requestPermissions$10(Map map, TaskCompletionSource taskCompletionSource, int i) {
        map.put("authorizationStatus", Integer.valueOf(i));
        taskCompletionSource.setResult(map);
    }

    private Boolean checkPermissions() {
        return Boolean.valueOf(ContextHolder.getApplicationContext().checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0);
    }

    private Task<Map<String, Integer>> getPermissions() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getPermissions$13(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$getPermissions$13(TaskCompletionSource taskCompletionSource) {
        int iAreNotificationsEnabled;
        try {
            HashMap map = new HashMap();
            if (Build.VERSION.SDK_INT >= 33) {
                iAreNotificationsEnabled = checkPermissions().booleanValue();
            } else {
                iAreNotificationsEnabled = NotificationManagerCompat.from(this.mainActivity).areNotificationsEnabled();
            }
            map.put("authorizationStatus", Integer.valueOf(iAreNotificationsEnabled));
            taskCompletionSource.setResult(map);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) {
        Task initialMessage;
        long jIntValue;
        long jIntValue2;
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "Messaging#getInitialMessage":
                initialMessage = getInitialMessage();
                break;
            case "Messaging#setAutoInitEnabled":
                initialMessage = setAutoInitEnabled((Map) methodCall.arguments());
                break;
            case "Messaging#deleteToken":
                initialMessage = deleteToken();
                break;
            case "Messaging#unsubscribeFromTopic":
                initialMessage = unsubscribeFromTopic((Map) methodCall.arguments());
                break;
            case "Messaging#subscribeToTopic":
                initialMessage = subscribeToTopic((Map) methodCall.arguments());
                break;
            case "Messaging#setDeliveryMetricsExportToBigQuery":
                initialMessage = setDeliveryMetricsExportToBigQuery((Map) methodCall.arguments());
                break;
            case "Messaging#startBackgroundIsolate":
                Map map = (Map) methodCall.arguments;
                Object obj = map.get("pluginCallbackHandle");
                Object obj2 = map.get("userCallbackHandle");
                if (obj instanceof Long) {
                    jIntValue = ((Long) obj).longValue();
                } else if (obj instanceof Integer) {
                    jIntValue = ((Integer) obj).intValue();
                    Long.valueOf(jIntValue).getClass();
                } else {
                    throw new IllegalArgumentException("Expected 'Long' or 'Integer' type for 'pluginCallbackHandle'.");
                }
                if (obj2 instanceof Long) {
                    jIntValue2 = ((Long) obj2).longValue();
                } else if (obj2 instanceof Integer) {
                    jIntValue2 = ((Integer) obj2).intValue();
                    Long.valueOf(jIntValue2).getClass();
                } else {
                    throw new IllegalArgumentException("Expected 'Long' or 'Integer' type for 'userCallbackHandle'.");
                }
                Activity activity = this.mainActivity;
                FlutterShellArgs flutterShellArgsFromIntent = activity != null ? FlutterShellArgs.fromIntent(activity.getIntent()) : null;
                FlutterFirebaseMessagingBackgroundService.setCallbackDispatcher(jIntValue);
                FlutterFirebaseMessagingBackgroundService.setUserCallbackHandle(jIntValue2);
                FlutterFirebaseMessagingBackgroundService.startBackgroundIsolate(jIntValue, flutterShellArgsFromIntent);
                initialMessage = Tasks.forResult(null);
                break;
            case "Messaging#sendMessage":
                initialMessage = sendMessage((Map) methodCall.arguments());
                break;
            case "Messaging#requestPermission":
                if (Build.VERSION.SDK_INT >= 33) {
                    initialMessage = requestPermissions();
                    break;
                } else {
                    initialMessage = getPermissions();
                    break;
                }
            case "Messaging#getNotificationSettings":
                initialMessage = getPermissions();
                break;
            case "Messaging#getToken":
                initialMessage = getToken();
                break;
            default:
                result.notImplemented();
                return;
        }
        initialMessage.addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda15
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.f$0.lambda$onMethodCall$14(result, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$14(MethodChannel.Result result, Task task) {
        if (task.isSuccessful()) {
            result.success(task.getResult());
        } else {
            Exception exception = task.getException();
            result.error("firebase_messaging", exception != null ? exception.getMessage() : null, getExceptionDetails(exception));
        }
    }

    private Map<String, Object> getExceptionDetails(Exception exc) {
        HashMap map = new HashMap();
        map.put("code", EnvironmentCompat.MEDIA_UNKNOWN);
        if (exc != null) {
            map.put("message", exc.getMessage());
            return map;
        }
        map.put("message", "An unknown error has occurred.");
        return map;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.NewIntentListener
    public boolean onNewIntent(Intent intent) {
        Map<String, Object> remoteMessageNotificationForArguments;
        Map<String, Object> map;
        Map<String, Object> firebaseMessageMap;
        if (intent.getExtras() == null) {
            return false;
        }
        String string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID);
        if (string == null) {
            string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        if (string == null) {
            return false;
        }
        RemoteMessage remoteMessageForArguments = FlutterFirebaseMessagingReceiver.notifications.get(string);
        if (remoteMessageForArguments != null || (firebaseMessageMap = FlutterFirebaseMessagingStore.getInstance().getFirebaseMessageMap(string)) == null) {
            remoteMessageNotificationForArguments = null;
        } else {
            remoteMessageForArguments = FlutterFirebaseMessagingUtils.getRemoteMessageForArguments(firebaseMessageMap);
            remoteMessageNotificationForArguments = FlutterFirebaseMessagingUtils.getRemoteMessageNotificationForArguments(firebaseMessageMap);
        }
        if (remoteMessageForArguments == null) {
            return false;
        }
        this.initialMessage = remoteMessageForArguments;
        this.initialMessageNotification = remoteMessageNotificationForArguments;
        FlutterFirebaseMessagingReceiver.notifications.remove(string);
        Map<String, Object> mapRemoteMessageToMap = FlutterFirebaseMessagingUtils.remoteMessageToMap(remoteMessageForArguments);
        if (remoteMessageForArguments.getNotification() == null && (map = this.initialMessageNotification) != null) {
            mapRemoteMessageToMap.put("notification", map);
        }
        this.channel.invokeMethod("Messaging#onMessageOpenedApp", mapRemoteMessageToMap);
        this.mainActivity.setIntent(intent);
        return true;
    }

    @Override // io.flutter.plugins.firebase.core.FlutterFirebasePlugin
    public Task<Map<String, Object>> getPluginConstantsForFirebaseApp(final FirebaseApp firebaseApp) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FlutterFirebaseMessagingPlugin.lambda$getPluginConstantsForFirebaseApp$15(firebaseApp, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$getPluginConstantsForFirebaseApp$15(FirebaseApp firebaseApp, TaskCompletionSource taskCompletionSource) {
        try {
            HashMap map = new HashMap();
            if (firebaseApp.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                map.put("AUTO_INIT_ENABLED", Boolean.valueOf(FirebaseMessaging.getInstance().isAutoInitEnabled()));
            }
            taskCompletionSource.setResult(map);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    @Override // io.flutter.plugins.firebase.core.FlutterFirebasePlugin
    public Task<Void> didReinitializeFirebaseCore() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        cachedThreadPool.execute(new Runnable() { // from class: io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }

    private Map<String, Object> uncheckedCastToMap(Object obj) {
        return (Map) obj;
    }
}
