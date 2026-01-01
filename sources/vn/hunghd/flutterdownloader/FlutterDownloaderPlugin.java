package vn.hunghd.flutterdownloader;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.google.android.gms.actions.SearchIntents;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.tika.parser.external.ExternalParsersConfigReaderMetKeys;

/* compiled from: FlutterDownloaderPlugin.kt */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 H2\u00020\u00012\u00020\u0002:\u0001HB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0018\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020\nH\u0002Jh\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010$2\b\u0010&\u001a\u0004\u0018\u00010$2\b\u0010'\u001a\u0004\u0018\u00010$2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020)2\u0006\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u000e2\u0006\u0010/\u001a\u00020)H\u0002J \u00100\u001a\u00020\u00142\u0006\u00101\u001a\u00020$2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u000eH\u0002J\u0018\u00105\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u00106\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001f\u00107\u001a\u0002H8\"\u0004\b\u0000\u00108*\u00020\u001a2\u0006\u00109\u001a\u00020$H\u0002¢\u0006\u0002\u0010:J\u0018\u0010;\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010<\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010=\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010>\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010?\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010@\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010A\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010B\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010C\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010D\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010E\u001a\u00020\u00142\u0006\u0010F\u001a\u00020GH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lvn/hunghd/flutterdownloader/FlutterDownloaderPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "<init>", "()V", "flutterChannel", "Lio/flutter/plugin/common/MethodChannel;", "taskDao", "Lvn/hunghd/flutterdownloader/TaskDao;", "context", "Landroid/content/Context;", "callbackHandle", "", DownloadWorker.ARG_STEP, "", "debugMode", DownloadWorker.ARG_IGNORESSL, "initializationLock", "", "onAttachedToEngine", "", "applicationContext", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "requireContext", "buildRequest", "Landroidx/work/WorkRequest;", "url", "", "savedDir", "filename", "headers", "showNotification", "", "openFileFromNotification", "isResume", "requiresStorageNotLow", "saveInPublicStorage", DownloadWorker.ARG_TIMEOUT, "allowCellular", "sendUpdateProgress", "id", "status", "Lvn/hunghd/flutterdownloader/DownloadStatus;", "progress", "initialize", "registerCallback", "requireArgument", "T", ExternalParsersConfigReaderMetKeys.METADATA_KEY_ATTR, "(Lio/flutter/plugin/common/MethodCall;Ljava/lang/String;)Ljava/lang/Object;", "enqueue", "loadTasks", "loadTasksWithRawQuery", "cancel", "cancelAll", "pause", "resume", "retry", "open", "remove", "deleteFileInMediaStore", "file", "Ljava/io/File;", "Companion", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FlutterDownloaderPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin {
    public static final String CALLBACK_DISPATCHER_HANDLE_KEY = "callback_dispatcher_handle_key";
    private static final String CHANNEL = "vn.hunghd/downloader";
    public static final String SHARED_PREFERENCES_KEY = "vn.hunghd.downloader.pref";
    private static final String TAG = "flutter_download_task";
    private long callbackHandle;
    private Context context;
    private int debugMode;
    private MethodChannel flutterChannel;
    private int ignoreSsl;
    private final Object initializationLock = new Object();
    private int step;
    private TaskDao taskDao;

    private final void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
        synchronized (this.initializationLock) {
            if (this.flutterChannel != null) {
                return;
            }
            this.context = applicationContext;
            MethodChannel methodChannel = new MethodChannel(messenger, CHANNEL);
            this.flutterChannel = methodChannel;
            methodChannel.setMethodCallHandler(this);
            this.taskDao = new TaskDao(TaskDbHelper.INSTANCE.getInstance(this.context));
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) throws Throwable {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1594257912:
                    if (str.equals("enqueue")) {
                        enqueue(call, result);
                        return;
                    }
                    break;
                case -1367724422:
                    if (str.equals("cancel")) {
                        cancel(call, result);
                        return;
                    }
                    break;
                case -934610812:
                    if (str.equals("remove")) {
                        remove(call, result);
                        return;
                    }
                    break;
                case -934426579:
                    if (str.equals("resume")) {
                        resume(call, result);
                        return;
                    }
                    break;
                case -403218424:
                    if (str.equals("registerCallback")) {
                        registerCallback(call, result);
                        return;
                    }
                    break;
                case 3417674:
                    if (str.equals("open")) {
                        open(call, result);
                        return;
                    }
                    break;
                case 106440182:
                    if (str.equals("pause")) {
                        pause(call, result);
                        return;
                    }
                    break;
                case 108405416:
                    if (str.equals("retry")) {
                        retry(call, result);
                        return;
                    }
                    break;
                case 230377166:
                    if (str.equals("loadTasksWithRawQuery")) {
                        loadTasksWithRawQuery(call, result);
                        return;
                    }
                    break;
                case 476547271:
                    if (str.equals("cancelAll")) {
                        cancelAll(result);
                        return;
                    }
                    break;
                case 871091088:
                    if (str.equals("initialize")) {
                        initialize(call, result);
                        return;
                    }
                    break;
                case 1378870856:
                    if (str.equals("loadTasks")) {
                        loadTasks(result);
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Context applicationContext = binding.getApplicationContext();
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        onAttachedToEngine(applicationContext, binaryMessenger);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.context = null;
        MethodChannel methodChannel = this.flutterChannel;
        if (methodChannel != null) {
            methodChannel.setMethodCallHandler(null);
        }
        this.flutterChannel = null;
    }

    private final Context requireContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    private final WorkRequest buildRequest(String url, String savedDir, String filename, String headers, boolean showNotification, boolean openFileFromNotification, boolean isResume, boolean requiresStorageNotLow, boolean saveInPublicStorage, int timeout, boolean allowCellular) throws Throwable {
        OneTimeWorkRequest.Builder backoffCriteria = new OneTimeWorkRequest.Builder(DownloadWorker.class).setConstraints(new Constraints.Builder().setRequiresStorageNotLow(requiresStorageNotLow).setRequiredNetworkType(allowCellular ? NetworkType.CONNECTED : NetworkType.UNMETERED).build()).addTag(TAG).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10L, TimeUnit.SECONDS);
        Data dataBuild = new Data.Builder().putString("url", url).putString(DownloadWorker.ARG_SAVED_DIR, savedDir).putString("file_name", filename).putString("headers", headers).putBoolean("show_notification", showNotification).putBoolean("open_file_from_notification", openFileFromNotification).putBoolean(DownloadWorker.ARG_IS_RESUME, isResume).putLong(DownloadWorker.ARG_CALLBACK_HANDLE, this.callbackHandle).putInt(DownloadWorker.ARG_STEP, this.step).putBoolean(DownloadWorker.ARG_DEBUG, this.debugMode == 1).putBoolean(DownloadWorker.ARG_IGNORESSL, this.ignoreSsl == 1).putBoolean("save_in_public_storage", saveInPublicStorage).putInt(DownloadWorker.ARG_TIMEOUT, timeout).build();
        Intrinsics.checkNotNullExpressionValue(dataBuild, "build(...)");
        return backoffCriteria.setInputData(dataBuild).build();
    }

    private final void sendUpdateProgress(String id, DownloadStatus status, int progress) {
        HashMap map = new HashMap();
        map.put(TaskEntry.COLUMN_NAME_TASK_ID, id);
        map.put("status", Integer.valueOf(status.ordinal()));
        map.put("progress", Integer.valueOf(progress));
        MethodChannel methodChannel = this.flutterChannel;
        if (methodChannel != null) {
            methodChannel.invokeMethod("updateProgress", map);
        }
    }

    private final void initialize(MethodCall call, MethodChannel.Result result) throws NumberFormatException {
        SharedPreferences.Editor editorEdit;
        SharedPreferences.Editor editorPutLong;
        Object obj = call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        List list = (List) obj;
        long j = Long.parseLong(String.valueOf(list.get(0)));
        this.debugMode = Integer.parseInt(String.valueOf(list.get(1)));
        this.ignoreSsl = Integer.parseInt(String.valueOf(list.get(2)));
        Context context = this.context;
        SharedPreferences sharedPreferences = context != null ? context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0) : null;
        if (sharedPreferences != null && (editorEdit = sharedPreferences.edit()) != null && (editorPutLong = editorEdit.putLong(CALLBACK_DISPATCHER_HANDLE_KEY, j)) != null) {
            editorPutLong.apply();
        }
        result.success(null);
    }

    private final void registerCallback(MethodCall call, MethodChannel.Result result) {
        Object obj = call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        List list = (List) obj;
        this.callbackHandle = Long.parseLong(String.valueOf(list.get(0)));
        this.step = Integer.parseInt(String.valueOf(list.get(1)));
        result.success(null);
    }

    private final <T> T requireArgument(MethodCall methodCall, String str) {
        T t = (T) methodCall.argument(str);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException(("Required key '" + str + "' was null").toString());
    }

    private final void enqueue(MethodCall call, MethodChannel.Result result) throws Throwable {
        String str = (String) requireArgument(call, "url");
        String str2 = (String) requireArgument(call, TaskEntry.COLUMN_NAME_SAVED_DIR);
        String str3 = (String) call.argument("file_name");
        String str4 = (String) requireArgument(call, "headers");
        int iIntValue = ((Number) requireArgument(call, DownloadWorker.ARG_TIMEOUT)).intValue();
        boolean zBooleanValue = ((Boolean) requireArgument(call, "show_notification")).booleanValue();
        boolean zBooleanValue2 = ((Boolean) requireArgument(call, "open_file_from_notification")).booleanValue();
        boolean zBooleanValue3 = ((Boolean) requireArgument(call, "requires_storage_not_low")).booleanValue();
        boolean zBooleanValue4 = ((Boolean) requireArgument(call, "save_in_public_storage")).booleanValue();
        boolean zBooleanValue5 = ((Boolean) requireArgument(call, TaskEntry.COLUMN_ALLOW_CELLULAR)).booleanValue();
        WorkRequest workRequestBuildRequest = buildRequest(str, str2, str3, str4, zBooleanValue, zBooleanValue2, false, zBooleanValue3, zBooleanValue4, iIntValue, zBooleanValue5);
        WorkManager.getInstance(requireContext()).enqueue(workRequestBuildRequest);
        String string = workRequestBuildRequest.getId().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        result.success(string);
        sendUpdateProgress(string, DownloadStatus.ENQUEUED, 0);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        taskDao.insertOrUpdateNewTask(string, str, DownloadStatus.ENQUEUED, 0, str3, str2, str4, zBooleanValue, zBooleanValue2, zBooleanValue4, zBooleanValue5);
    }

    private final void loadTasks(MethodChannel.Result result) {
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        List<DownloadTask> listLoadAllTasks = taskDao.loadAllTasks();
        ArrayList arrayList = new ArrayList();
        for (DownloadTask downloadTask : listLoadAllTasks) {
            HashMap map = new HashMap();
            map.put(TaskEntry.COLUMN_NAME_TASK_ID, downloadTask.getTaskId());
            map.put("status", Integer.valueOf(downloadTask.getStatus().ordinal()));
            map.put("progress", Integer.valueOf(downloadTask.getProgress()));
            map.put("url", downloadTask.getUrl());
            map.put("file_name", downloadTask.getFilename());
            map.put(TaskEntry.COLUMN_NAME_SAVED_DIR, downloadTask.getSavedDir());
            map.put(TaskEntry.COLUMN_NAME_TIME_CREATED, Long.valueOf(downloadTask.getTimeCreated()));
            map.put(TaskEntry.COLUMN_ALLOW_CELLULAR, Boolean.valueOf(downloadTask.getAllowCellular()));
            arrayList.add(map);
        }
        result.success(arrayList);
    }

    private final void loadTasksWithRawQuery(MethodCall call, MethodChannel.Result result) {
        String str = (String) requireArgument(call, SearchIntents.EXTRA_QUERY);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        List<DownloadTask> listLoadTasksWithRawQuery = taskDao.loadTasksWithRawQuery(str);
        ArrayList arrayList = new ArrayList();
        for (DownloadTask downloadTask : listLoadTasksWithRawQuery) {
            HashMap map = new HashMap();
            map.put(TaskEntry.COLUMN_NAME_TASK_ID, downloadTask.getTaskId());
            map.put("status", Integer.valueOf(downloadTask.getStatus().ordinal()));
            map.put("progress", Integer.valueOf(downloadTask.getProgress()));
            map.put("url", downloadTask.getUrl());
            map.put("file_name", downloadTask.getFilename());
            map.put(TaskEntry.COLUMN_NAME_SAVED_DIR, downloadTask.getSavedDir());
            map.put(TaskEntry.COLUMN_NAME_TIME_CREATED, Long.valueOf(downloadTask.getTimeCreated()));
            map.put(TaskEntry.COLUMN_ALLOW_CELLULAR, Boolean.valueOf(downloadTask.getAllowCellular()));
            arrayList.add(map);
        }
        result.success(arrayList);
    }

    private final void cancel(MethodCall call, MethodChannel.Result result) {
        WorkManager.getInstance(requireContext()).cancelWorkById(UUID.fromString((String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID)));
        result.success(null);
    }

    private final void cancelAll(MethodChannel.Result result) {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(TAG);
        result.success(null);
    }

    private final void pause(MethodCall call, MethodChannel.Result result) {
        String str = (String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        taskDao.updateTask(str, true);
        WorkManager.getInstance(requireContext()).cancelWorkById(UUID.fromString(str));
        result.success(null);
    }

    private final void resume(MethodCall call, MethodChannel.Result result) throws Throwable {
        String str = (String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        DownloadTask downloadTaskLoadTask = taskDao.loadTask(str);
        boolean zBooleanValue = ((Boolean) requireArgument(call, "requires_storage_not_low")).booleanValue();
        int iIntValue = ((Number) requireArgument(call, DownloadWorker.ARG_TIMEOUT)).intValue();
        if (downloadTaskLoadTask != null) {
            if (downloadTaskLoadTask.getStatus() == DownloadStatus.PAUSED) {
                String filename = downloadTaskLoadTask.getFilename();
                if (filename == null) {
                    filename = downloadTaskLoadTask.getUrl().substring(StringsKt.lastIndexOf$default((CharSequence) downloadTaskLoadTask.getUrl(), "/", 0, false, 6, (Object) null) + 1, downloadTaskLoadTask.getUrl().length());
                    Intrinsics.checkNotNullExpressionValue(filename, "substring(...)");
                }
                if (new File(downloadTaskLoadTask.getSavedDir() + File.separator + filename).exists()) {
                    WorkRequest workRequestBuildRequest = buildRequest(downloadTaskLoadTask.getUrl(), downloadTaskLoadTask.getSavedDir(), downloadTaskLoadTask.getFilename(), downloadTaskLoadTask.getHeaders(), downloadTaskLoadTask.getShowNotification(), downloadTaskLoadTask.getOpenFileFromNotification(), true, zBooleanValue, downloadTaskLoadTask.getSaveInPublicStorage(), iIntValue, downloadTaskLoadTask.getAllowCellular());
                    String string = workRequestBuildRequest.getId().toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    result.success(string);
                    sendUpdateProgress(string, DownloadStatus.RUNNING, downloadTaskLoadTask.getProgress());
                    TaskDao taskDao2 = this.taskDao;
                    Intrinsics.checkNotNull(taskDao2);
                    taskDao2.updateTask(str, string, DownloadStatus.RUNNING, downloadTaskLoadTask.getProgress(), false);
                    Intrinsics.checkNotNull(WorkManager.getInstance(requireContext()).enqueue(workRequestBuildRequest));
                    return;
                }
                TaskDao taskDao3 = this.taskDao;
                Intrinsics.checkNotNull(taskDao3);
                taskDao3.updateTask(str, false);
                result.error("invalid_data", "not found partial downloaded data, this task cannot be resumed", null);
                return;
            }
            result.error("invalid_status", "only paused task can be resumed", null);
            return;
        }
        result.error("invalid_task_id", "not found task corresponding to given task id", null);
    }

    private final void retry(MethodCall call, MethodChannel.Result result) throws Throwable {
        String str = (String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        DownloadTask downloadTaskLoadTask = taskDao.loadTask(str);
        boolean zBooleanValue = ((Boolean) requireArgument(call, "requires_storage_not_low")).booleanValue();
        int iIntValue = ((Number) requireArgument(call, DownloadWorker.ARG_TIMEOUT)).intValue();
        if (downloadTaskLoadTask != null) {
            if (downloadTaskLoadTask.getStatus() == DownloadStatus.FAILED || downloadTaskLoadTask.getStatus() == DownloadStatus.CANCELED) {
                WorkRequest workRequestBuildRequest = buildRequest(downloadTaskLoadTask.getUrl(), downloadTaskLoadTask.getSavedDir(), downloadTaskLoadTask.getFilename(), downloadTaskLoadTask.getHeaders(), downloadTaskLoadTask.getShowNotification(), downloadTaskLoadTask.getOpenFileFromNotification(), false, zBooleanValue, downloadTaskLoadTask.getSaveInPublicStorage(), iIntValue, downloadTaskLoadTask.getAllowCellular());
                String string = workRequestBuildRequest.getId().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                result.success(string);
                sendUpdateProgress(string, DownloadStatus.ENQUEUED, downloadTaskLoadTask.getProgress());
                TaskDao taskDao2 = this.taskDao;
                Intrinsics.checkNotNull(taskDao2);
                taskDao2.updateTask(str, string, DownloadStatus.ENQUEUED, downloadTaskLoadTask.getProgress(), false);
                Intrinsics.checkNotNull(WorkManager.getInstance(requireContext()).enqueue(workRequestBuildRequest));
                return;
            }
            result.error("invalid_status", "only failed and canceled task can be retried", null);
            return;
        }
        result.error("invalid_task_id", "not found task corresponding to given task id", null);
    }

    private final void open(MethodCall call, MethodChannel.Result result) {
        String str = (String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID);
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        DownloadTask downloadTaskLoadTask = taskDao.loadTask(str);
        if (downloadTaskLoadTask == null) {
            result.error("invalid_task_id", "not found task with id " + str, null);
            return;
        }
        if (downloadTaskLoadTask.getStatus() != DownloadStatus.COMPLETE) {
            result.error("invalid_status", "only completed tasks can be opened", null);
            return;
        }
        String url = downloadTaskLoadTask.getUrl();
        String savedDir = downloadTaskLoadTask.getSavedDir();
        String filename = downloadTaskLoadTask.getFilename();
        if (filename == null) {
            filename = url.substring(StringsKt.lastIndexOf$default((CharSequence) url, "/", 0, false, 6, (Object) null) + 1, url.length());
            Intrinsics.checkNotNullExpressionValue(filename, "substring(...)");
        }
        Intent intentValidatedFileIntent = IntentUtils.INSTANCE.validatedFileIntent(requireContext(), savedDir + File.separator + filename, downloadTaskLoadTask.getMimeType());
        if (intentValidatedFileIntent != null) {
            requireContext().startActivity(intentValidatedFileIntent);
            result.success(true);
        } else {
            result.success(false);
        }
    }

    private final void remove(MethodCall call, MethodChannel.Result result) {
        String str = (String) requireArgument(call, TaskEntry.COLUMN_NAME_TASK_ID);
        boolean zBooleanValue = ((Boolean) requireArgument(call, "should_delete_content")).booleanValue();
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        DownloadTask downloadTaskLoadTask = taskDao.loadTask(str);
        if (downloadTaskLoadTask != null) {
            if (downloadTaskLoadTask.getStatus() == DownloadStatus.ENQUEUED || downloadTaskLoadTask.getStatus() == DownloadStatus.RUNNING) {
                WorkManager.getInstance(requireContext()).cancelWorkById(UUID.fromString(str));
            }
            if (zBooleanValue) {
                String filename = downloadTaskLoadTask.getFilename();
                if (filename == null) {
                    filename = downloadTaskLoadTask.getUrl().substring(StringsKt.lastIndexOf$default((CharSequence) downloadTaskLoadTask.getUrl(), "/", 0, false, 6, (Object) null) + 1, downloadTaskLoadTask.getUrl().length());
                    Intrinsics.checkNotNullExpressionValue(filename, "substring(...)");
                }
                File file = new File(downloadTaskLoadTask.getSavedDir() + File.separator + filename);
                if (file.exists()) {
                    try {
                        deleteFileInMediaStore(file);
                    } catch (SecurityException unused) {
                        Log.d("FlutterDownloader", "Failed to delete file in media store, will fall back to normal delete()");
                    }
                    file.delete();
                }
            }
            TaskDao taskDao2 = this.taskDao;
            Intrinsics.checkNotNull(taskDao2);
            taskDao2.deleteTask(str);
            NotificationManagerCompat.from(requireContext()).cancel(downloadTaskLoadTask.getPrimaryId());
            result.success(null);
            return;
        }
        result.error("invalid_task_id", "not found task corresponding to given task id", null);
    }

    private final void deleteFileInMediaStore(File file) {
        String[] strArr = {"_id"};
        String[] strArr2 = {file.getAbsolutePath()};
        Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
        ContentResolver contentResolver = requireContext().getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorQuery = contentResolver.query(EXTERNAL_CONTENT_URI, strArr, "_data = ?", strArr2, null);
        if (cursorQuery != null && cursorQuery.moveToFirst()) {
            Uri uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_id")));
            Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
            contentResolver.delete(uriWithAppendedId, null, null);
        } else {
            Cursor cursorQuery2 = contentResolver.query(EXTERNAL_CONTENT_URI, strArr, "_data = ?", strArr2, null);
            if (cursorQuery2 != null && cursorQuery2.moveToFirst()) {
                Uri uriWithAppendedId2 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursorQuery2.getLong(cursorQuery2.getColumnIndexOrThrow("_id")));
                Intrinsics.checkNotNullExpressionValue(uriWithAppendedId2, "withAppendedId(...)");
                contentResolver.delete(uriWithAppendedId2, null, null);
            }
            if (cursorQuery2 != null) {
                cursorQuery2.close();
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
    }
}
