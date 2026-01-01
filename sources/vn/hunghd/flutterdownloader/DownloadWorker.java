package vn.hunghd.flutterdownloader;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.celltrionph.ghr_app.MainActivity$$ExternalSyntheticApiModelOutline0;
import com.google.common.net.HttpHeaders;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterCallbackInformation;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DownloadWorker.kt */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 `2\u00020\u00012\u00020\u0002:\u0001`B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010(\u001a\u00020)2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0018\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020)H\u0016J\b\u00100\u001a\u000201H\u0016J\u0018\u00102\u001a\u00020)2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u001eH\u0002J\"\u00106\u001a\u00020%2\u0006\u00103\u001a\u0002042\b\u00107\u001a\u0004\u0018\u00010\u001e2\u0006\u00108\u001a\u00020\u001eH\u0002JB\u00109\u001a\u00020)2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010:\u001a\u00020\u001e2\u0006\u00108\u001a\u00020\u001e2\b\u00107\u001a\u0004\u0018\u00010\u001e2\u0006\u00105\u001a\u00020\u001e2\u0006\u0010;\u001a\u00020\u00162\u0006\u0010<\u001a\u00020\u001bH\u0002J\u001a\u0010=\u001a\u0004\u0018\u00010>2\u0006\u00107\u001a\u00020\u001e2\u0006\u00108\u001a\u00020\u001eH\u0002J\u001e\u0010?\u001a\u0004\u0018\u00010@2\b\u00107\u001a\u0004\u0018\u00010\u001e2\b\u0010A\u001a\u0004\u0018\u00010\u001eH\u0003J\u0012\u0010B\u001a\u0004\u0018\u00010\u001e2\u0006\u0010C\u001a\u00020@H\u0002J\b\u0010D\u001a\u00020)H\u0002J\u0010\u0010H\u001a\u00020)2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J<\u0010I\u001a\u00020)2\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010J\u001a\u0004\u0018\u00010\u001e2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\u001b2\b\u0010N\u001a\u0004\u0018\u00010O2\u0006\u0010P\u001a\u00020\u0016H\u0002J\u0018\u0010Q\u001a\u00020)2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\u001bH\u0002J\u0014\u0010R\u001a\u0004\u0018\u00010\u001e2\b\u0010S\u001a\u0004\u0018\u00010\u001eH\u0002J\u001e\u0010T\u001a\u0004\u0018\u00010\u001e2\b\u0010U\u001a\u0004\u0018\u00010\u001e2\b\u0010V\u001a\u0004\u0018\u00010\u001eH\u0002J\u0014\u0010W\u001a\u0004\u0018\u00010\u001e2\b\u0010S\u001a\u0004\u0018\u00010\u001eH\u0002J\u0012\u0010X\u001a\u00020\u00162\b\u0010S\u001a\u0004\u0018\u00010\u001eH\u0002J\u0012\u0010Y\u001a\u00020\u00162\b\u0010Z\u001a\u0004\u0018\u00010\u001eH\u0002J&\u0010[\u001a\u00020)2\b\u0010\\\u001a\u0004\u0018\u00010\u001e2\b\u0010Z\u001a\u0004\u0018\u00010\u001e2\b\u0010S\u001a\u0004\u0018\u00010\u001eH\u0002J\u0010\u0010]\u001a\u00020)2\u0006\u0010^\u001a\u00020\u001eH\u0002J\u0010\u0010_\u001a\u00020)2\u0006\u0010^\u001a\u00020\u001eH\u0002R\u0018\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0018\u0010\r\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0018\u0010\u000e\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010E\u001a\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bF\u0010G¨\u0006a"}, d2 = {"Lvn/hunghd/flutterdownloader/DownloadWorker;", "Landroidx/work/Worker;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "context", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "<init>", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "charsetPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "Ljava/util/regex/Pattern;", "filenameStarPattern", "filenamePattern", "backgroundChannel", "Lio/flutter/plugin/common/MethodChannel;", "dbHelper", "Lvn/hunghd/flutterdownloader/TaskDbHelper;", "taskDao", "Lvn/hunghd/flutterdownloader/TaskDao;", "showNotification", "", "clickToOpenDownloadedFile", DownloadWorker.ARG_DEBUG, DownloadWorker.ARG_IGNORESSL, "lastProgress", "", "primaryId", "msgStarted", "", "msgInProgress", "msgCanceled", "msgFailed", "msgPaused", "msgComplete", "lastCallUpdateNotification", "", DownloadWorker.ARG_STEP, "saveInPublicStorage", "startBackgroundIsolate", "", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "onStopped", "doWork", "Landroidx/work/ListenableWorker$Result;", "setupHeaders", "conn", "Ljava/net/HttpURLConnection;", "headers", "setupPartialDownloadedDataHeader", "filename", "savedDir", "downloadFile", "fileURL", "isResume", DownloadWorker.ARG_TIMEOUT, "createFileInAppSpecificDir", "Ljava/io/File;", "createFileInPublicDownloadsDir", "Landroid/net/Uri;", "mimeType", "getMediaStoreEntryPathApi29", "uri", "cleanUp", "notificationIconRes", "getNotificationIconRes", "()I", "setupNotification", "updateNotification", "title", "status", "Lvn/hunghd/flutterdownloader/DownloadStatus;", "progress", "intent", "Landroid/app/PendingIntent;", "finalize", "sendUpdateProcessEvent", "getCharsetFromContentType", "contentType", "getFileNameFromContentDisposition", "disposition", "contentCharset", "getContentTypeWithoutCharset", "isImageOrVideoFile", "isExternalStoragePath", "filePath", "addImageOrVideoToGallery", "fileName", "log", "message", "logError", "Companion", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class DownloadWorker extends Worker implements MethodChannel.MethodCallHandler {
    public static final String ARG_CALLBACK_HANDLE = "callback_handle";
    public static final String ARG_DEBUG = "debug";
    public static final String ARG_FILE_NAME = "file_name";
    public static final String ARG_HEADERS = "headers";
    public static final String ARG_IGNORESSL = "ignoreSsl";
    public static final String ARG_IS_RESUME = "is_resume";
    public static final String ARG_OPEN_FILE_FROM_NOTIFICATION = "open_file_from_notification";
    public static final String ARG_SAVED_DIR = "saved_file";
    public static final String ARG_SAVE_IN_PUBLIC_STORAGE = "save_in_public_storage";
    public static final String ARG_SHOW_NOTIFICATION = "show_notification";
    public static final String ARG_STEP = "step";
    public static final String ARG_TIMEOUT = "timeout";
    public static final String ARG_URL = "url";
    private static final int BUFFER_SIZE = 4096;
    private static final String CHANNEL_ID = "FLUTTER_DOWNLOADER_NOTIFICATION";
    private static FlutterEngine backgroundFlutterEngine;
    private MethodChannel backgroundChannel;
    private final Pattern charsetPattern;
    private boolean clickToOpenDownloadedFile;
    private TaskDbHelper dbHelper;
    private boolean debug;
    private final Pattern filenamePattern;
    private final Pattern filenameStarPattern;
    private boolean ignoreSsl;
    private long lastCallUpdateNotification;
    private int lastProgress;
    private String msgCanceled;
    private String msgComplete;
    private String msgFailed;
    private String msgInProgress;
    private String msgPaused;
    private String msgStarted;
    private int primaryId;
    private boolean saveInPublicStorage;
    private boolean showNotification;
    private int step;
    private TaskDao taskDao;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "DownloadWorker";
    private static final AtomicBoolean isolateStarted = new AtomicBoolean(false);
    private static final ArrayDeque<List<Object>> isolateQueue = new ArrayDeque<>();
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() { // from class: vn.hunghd.flutterdownloader.DownloadWorker$$ExternalSyntheticLambda1
        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            return DownloadWorker.DO_NOT_VERIFY$lambda$9(str, sSLSession);
        }
    };

    /* compiled from: DownloadWorker.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DownloadStatus.values().length];
            try {
                iArr[DownloadStatus.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DownloadStatus.CANCELED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DownloadStatus.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DownloadStatus.PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DownloadStatus.COMPLETE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean DO_NOT_VERIFY$lambda$9(String str, SSLSession sSLSession) {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DownloadWorker(final Context context, WorkerParameters params) {
        super(context, params);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        this.charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");
        this.filenameStarPattern = Pattern.compile("(?i)\\bfilename\\*=([^']+)'([^']*)'\"?([^\"]+)\"?");
        this.filenamePattern = Pattern.compile("(?i)\\bfilename=\"?([^\"]+)\"?");
        new Handler(context.getMainLooper()).post(new Runnable() { // from class: vn.hunghd.flutterdownloader.DownloadWorker$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startBackgroundIsolate(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startBackgroundIsolate(Context context) {
        DartExecutor dartExecutor;
        synchronized (isolateStarted) {
            if (backgroundFlutterEngine == null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(FlutterDownloaderPlugin.SHARED_PREFERENCES_KEY, 0);
                Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
                long j = sharedPreferences.getLong(FlutterDownloaderPlugin.CALLBACK_DISPATCHER_HANDLE_KEY, 0L);
                backgroundFlutterEngine = new FlutterEngine(getApplicationContext(), (String[]) null, false);
                FlutterCallbackInformation flutterCallbackInformationLookupCallbackInformation = FlutterCallbackInformation.lookupCallbackInformation(j);
                if (flutterCallbackInformationLookupCallbackInformation == null) {
                    log("Fatal: failed to find callback");
                    return;
                }
                String strFindAppBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath();
                Intrinsics.checkNotNullExpressionValue(strFindAppBundlePath, "findAppBundlePath(...)");
                AssetManager assets = getApplicationContext().getAssets();
                FlutterEngine flutterEngine = backgroundFlutterEngine;
                if (flutterEngine != null && (dartExecutor = flutterEngine.getDartExecutor()) != null) {
                    dartExecutor.executeDartCallback(new DartExecutor.DartCallback(assets, strFindAppBundlePath, flutterCallbackInformationLookupCallbackInformation));
                }
            }
            Unit unit = Unit.INSTANCE;
            FlutterEngine flutterEngine2 = backgroundFlutterEngine;
            Intrinsics.checkNotNull(flutterEngine2);
            MethodChannel methodChannel = new MethodChannel(flutterEngine2.getDartExecutor(), "vn.hunghd/downloader_background");
            this.backgroundChannel = methodChannel;
            methodChannel.setMethodCallHandler(this);
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        if (call.method.equals("didInitializeDispatcher")) {
            synchronized (isolateStarted) {
                while (true) {
                    ArrayDeque<List<Object>> arrayDeque = isolateQueue;
                    if (!arrayDeque.isEmpty()) {
                        MethodChannel methodChannel = this.backgroundChannel;
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("", arrayDeque.remove());
                        }
                    } else {
                        isolateStarted.set(true);
                        result.success(null);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
            return;
        }
        result.notImplemented();
    }

    @Override // androidx.work.ListenableWorker
    public void onStopped() throws InterruptedException {
        DownloadTask downloadTaskLoadTask;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.dbHelper = TaskDbHelper.INSTANCE.getInstance(applicationContext);
        TaskDbHelper taskDbHelper = this.dbHelper;
        Intrinsics.checkNotNull(taskDbHelper);
        this.taskDao = new TaskDao(taskDbHelper);
        String string = getInputData().getString("url");
        String string2 = getInputData().getString("file_name");
        TaskDao taskDao = this.taskDao;
        if (taskDao != null) {
            String string3 = getId().toString();
            Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
            downloadTaskLoadTask = taskDao.loadTask(string3);
        } else {
            downloadTaskLoadTask = null;
        }
        if (downloadTaskLoadTask == null || downloadTaskLoadTask.getStatus() != DownloadStatus.ENQUEUED) {
            return;
        }
        if (string2 == null) {
            string2 = string;
        }
        updateNotification(applicationContext, string2, DownloadStatus.CANCELED, -1, null, true);
        TaskDao taskDao2 = this.taskDao;
        if (taskDao2 != null) {
            String string4 = getId().toString();
            Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
            taskDao2.updateTask(string4, DownloadStatus.CANCELED, this.lastProgress);
        }
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() throws Throwable {
        DownloadTask downloadTaskLoadTask;
        Object status;
        String str;
        String str2;
        this.dbHelper = TaskDbHelper.INSTANCE.getInstance(getApplicationContext());
        TaskDbHelper taskDbHelper = this.dbHelper;
        Intrinsics.checkNotNull(taskDbHelper);
        this.taskDao = new TaskDao(taskDbHelper);
        String string = getInputData().getString("url");
        if (string == null) {
            throw new IllegalArgumentException("Argument 'url' should not be null");
        }
        String string2 = getInputData().getString("file_name");
        String string3 = getInputData().getString(ARG_SAVED_DIR);
        if (string3 == null) {
            throw new IllegalArgumentException("Argument 'saved_file' should not be null");
        }
        String string4 = getInputData().getString("headers");
        if (string4 == null) {
            throw new IllegalArgumentException("Argument 'headers' should not be null");
        }
        boolean z = getInputData().getBoolean(ARG_IS_RESUME, false);
        int i = getInputData().getInt(ARG_TIMEOUT, 15000);
        this.debug = getInputData().getBoolean(ARG_DEBUG, false);
        this.step = getInputData().getInt(ARG_STEP, 10);
        this.ignoreSsl = getInputData().getBoolean(ARG_IGNORESSL, false);
        Resources resources = getApplicationContext().getResources();
        this.msgStarted = resources.getString(R.string.flutter_downloader_notification_started);
        this.msgInProgress = resources.getString(R.string.flutter_downloader_notification_in_progress);
        this.msgCanceled = resources.getString(R.string.flutter_downloader_notification_canceled);
        this.msgFailed = resources.getString(R.string.flutter_downloader_notification_failed);
        this.msgPaused = resources.getString(R.string.flutter_downloader_notification_paused);
        this.msgComplete = resources.getString(R.string.flutter_downloader_notification_complete);
        TaskDao taskDao = this.taskDao;
        if (taskDao != null) {
            String string5 = getId().toString();
            Intrinsics.checkNotNullExpressionValue(string5, "toString(...)");
            downloadTaskLoadTask = taskDao.loadTask(string5);
        } else {
            downloadTaskLoadTask = null;
        }
        if (downloadTaskLoadTask == null || (status = downloadTaskLoadTask.getStatus()) == null) {
            status = "GONE";
        }
        log("DownloadWorker{url=" + string + ",filename=" + string2 + ",savedDir=" + string3 + ",header=" + string4 + ",isResume=" + z + ",status=" + status);
        if (downloadTaskLoadTask == null || downloadTaskLoadTask.getStatus() == DownloadStatus.CANCELED) {
            ListenableWorker.Result resultSuccess = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess, "success(...)");
            return resultSuccess;
        }
        this.showNotification = getInputData().getBoolean("show_notification", false);
        this.clickToOpenDownloadedFile = getInputData().getBoolean("open_file_from_notification", false);
        this.saveInPublicStorage = getInputData().getBoolean("save_in_public_storage", false);
        this.primaryId = downloadTaskLoadTask.getPrimaryId();
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        setupNotification(applicationContext);
        Context applicationContext2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        updateNotification(applicationContext2, string2 == null ? string : string2, DownloadStatus.RUNNING, downloadTaskLoadTask.getProgress(), null, false);
        TaskDao taskDao2 = this.taskDao;
        if (taskDao2 != null) {
            String string6 = getId().toString();
            Intrinsics.checkNotNullExpressionValue(string6, "toString(...)");
            taskDao2.updateTask(string6, DownloadStatus.RUNNING, downloadTaskLoadTask.getProgress());
        }
        if (new File(string3 + File.separator + string2).exists()) {
            log("exists file for " + string2 + "automatic resuming...");
            z = true;
        }
        boolean z2 = z;
        try {
            Context applicationContext3 = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext3, "getApplicationContext(...)");
            str = string;
            str2 = string2;
            try {
                downloadFile(applicationContext3, str, string3, str2, string4, z2, i);
                cleanUp();
                this.dbHelper = null;
                this.taskDao = null;
                ListenableWorker.Result resultSuccess2 = ListenableWorker.Result.success();
                Intrinsics.checkNotNull(resultSuccess2);
                return resultSuccess2;
            } catch (Exception e) {
                e = e;
                Context applicationContext4 = getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext4, "getApplicationContext(...)");
                if (str2 != null) {
                    str = str2;
                }
                updateNotification(applicationContext4, str, DownloadStatus.FAILED, -1, null, true);
                TaskDao taskDao3 = this.taskDao;
                if (taskDao3 != null) {
                    String string7 = getId().toString();
                    Intrinsics.checkNotNullExpressionValue(string7, "toString(...)");
                    taskDao3.updateTask(string7, DownloadStatus.FAILED, this.lastProgress);
                }
                e.printStackTrace();
                this.dbHelper = null;
                this.taskDao = null;
                ListenableWorker.Result resultFailure = ListenableWorker.Result.failure();
                Intrinsics.checkNotNull(resultFailure);
                return resultFailure;
            }
        } catch (Exception e2) {
            e = e2;
            str = string;
            str2 = string2;
        }
    }

    private final void setupHeaders(HttpURLConnection conn, String headers) {
        if (headers.length() > 0) {
            log("Headers = " + headers);
            try {
                JSONObject jSONObject = new JSONObject(headers);
                Iterator<String> itKeys = jSONObject.keys();
                Intrinsics.checkNotNullExpressionValue(itKeys, "keys(...)");
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    conn.setRequestProperty(next, jSONObject.getString(next));
                }
                conn.setDoInput(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private final long setupPartialDownloadedDataHeader(HttpURLConnection conn, String filename, String savedDir) {
        long length = new File(savedDir + File.separator + filename).length();
        StringBuilder sb = new StringBuilder("Resume download: Range: bytes=");
        sb.append(length);
        sb.append("-");
        log(sb.toString());
        conn.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
        conn.setRequestProperty(HttpHeaders.RANGE, "bytes=" + length + "-");
        conn.setDoInput(true);
        return length;
    }

    /* JADX WARN: Removed duplicated region for block: B:133:0x0333 A[Catch: all -> 0x0436, IOException -> 0x0438, TryCatch #31 {IOException -> 0x0438, all -> 0x0436, blocks: (B:120:0x02ff, B:131:0x0329, B:133:0x0333, B:135:0x0344, B:137:0x034a, B:139:0x0353, B:140:0x0355, B:142:0x035c, B:144:0x0365, B:147:0x036d, B:149:0x037c, B:151:0x0380, B:153:0x0386, B:155:0x038c, B:156:0x0393, B:158:0x0397, B:174:0x03b8, B:176:0x03cc, B:180:0x03eb, B:183:0x040b, B:187:0x042d, B:181:0x03f6, B:145:0x0368, B:146:0x036b), top: B:310:0x02ff }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x034a A[Catch: all -> 0x0436, IOException -> 0x0438, TryCatch #31 {IOException -> 0x0438, all -> 0x0436, blocks: (B:120:0x02ff, B:131:0x0329, B:133:0x0333, B:135:0x0344, B:137:0x034a, B:139:0x0353, B:140:0x0355, B:142:0x035c, B:144:0x0365, B:147:0x036d, B:149:0x037c, B:151:0x0380, B:153:0x0386, B:155:0x038c, B:156:0x0393, B:158:0x0397, B:174:0x03b8, B:176:0x03cc, B:180:0x03eb, B:183:0x040b, B:187:0x042d, B:181:0x03f6, B:145:0x0368, B:146:0x036b), top: B:310:0x02ff }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x035c A[Catch: all -> 0x0436, IOException -> 0x0438, TryCatch #31 {IOException -> 0x0438, all -> 0x0436, blocks: (B:120:0x02ff, B:131:0x0329, B:133:0x0333, B:135:0x0344, B:137:0x034a, B:139:0x0353, B:140:0x0355, B:142:0x035c, B:144:0x0365, B:147:0x036d, B:149:0x037c, B:151:0x0380, B:153:0x0386, B:155:0x038c, B:156:0x0393, B:158:0x0397, B:174:0x03b8, B:176:0x03cc, B:180:0x03eb, B:183:0x040b, B:187:0x042d, B:181:0x03f6, B:145:0x0368, B:146:0x036b), top: B:310:0x02ff }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x036b A[Catch: all -> 0x0436, IOException -> 0x0438, TryCatch #31 {IOException -> 0x0438, all -> 0x0436, blocks: (B:120:0x02ff, B:131:0x0329, B:133:0x0333, B:135:0x0344, B:137:0x034a, B:139:0x0353, B:140:0x0355, B:142:0x035c, B:144:0x0365, B:147:0x036d, B:149:0x037c, B:151:0x0380, B:153:0x0386, B:155:0x038c, B:156:0x0393, B:158:0x0397, B:174:0x03b8, B:176:0x03cc, B:180:0x03eb, B:183:0x040b, B:187:0x042d, B:181:0x03f6, B:145:0x0368, B:146:0x036b), top: B:310:0x02ff }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x037c A[Catch: all -> 0x0436, IOException -> 0x0438, TryCatch #31 {IOException -> 0x0438, all -> 0x0436, blocks: (B:120:0x02ff, B:131:0x0329, B:133:0x0333, B:135:0x0344, B:137:0x034a, B:139:0x0353, B:140:0x0355, B:142:0x035c, B:144:0x0365, B:147:0x036d, B:149:0x037c, B:151:0x0380, B:153:0x0386, B:155:0x038c, B:156:0x0393, B:158:0x0397, B:174:0x03b8, B:176:0x03cc, B:180:0x03eb, B:183:0x040b, B:187:0x042d, B:181:0x03f6, B:145:0x0368, B:146:0x036b), top: B:310:0x02ff }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x05b3  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x05ca  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x05c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x059d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0249 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:334:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:335:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01ee A[PHI: r8
  0x01ee: PHI (r8v4 java.lang.String) = (r8v0 java.lang.String), (r8v0 java.lang.String), (r8v24 java.lang.String) binds: [B:48:0x017d, B:49:0x017f, B:66:0x01c2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0207 A[Catch: IOException -> 0x046a, all -> 0x053f, TryCatch #16 {all -> 0x053f, blocks: (B:28:0x00dd, B:30:0x0109, B:31:0x010f, B:35:0x011b, B:36:0x011e, B:206:0x046e, B:208:0x0488, B:210:0x0491, B:213:0x0499, B:217:0x04b5, B:221:0x04d5, B:220:0x04c4, B:211:0x0494, B:212:0x0497, B:42:0x012d, B:44:0x0133, B:46:0x013d, B:47:0x0151, B:50:0x0181, B:52:0x01a0, B:58:0x01ad, B:59:0x01b2, B:61:0x01b7, B:67:0x01c4, B:68:0x01e1, B:73:0x01ef, B:75:0x0207, B:76:0x0215, B:71:0x01ea, B:235:0x04f7), top: B:296:0x00dd }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x021d A[Catch: all -> 0x023b, IOException -> 0x0241, TRY_ENTER, TRY_LEAVE, TryCatch #27 {IOException -> 0x0241, all -> 0x023b, blocks: (B:79:0x021d, B:87:0x024d, B:89:0x0251), top: B:318:0x021b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void downloadFile(android.content.Context r29, java.lang.String r30, java.lang.String r31, java.lang.String r32, java.lang.String r33, boolean r34, int r35) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1496
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: vn.hunghd.flutterdownloader.DownloadWorker.downloadFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, int):void");
    }

    private final File createFileInAppSpecificDir(String filename, String savedDir) {
        File file = new File(savedDir, filename);
        try {
            if (file.createNewFile()) {
                return file;
            }
            logError("It looks like you are trying to save file in public storage but not setting 'saveInPublicStorage' to 'true'");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logError("Create a file using java.io API failed ");
            return null;
        }
    }

    private final Uri createFileInPublicDownloadsDir(String filename, String mimeType) {
        Uri EXTERNAL_CONTENT_URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", filename);
        contentValues.put(TaskEntry.COLUMN_NAME_MIME_TYPE, mimeType);
        contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
        try {
            return getApplicationContext().getContentResolver().insert(EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            logError("Create a file using MediaStore API failed.");
            return null;
        }
    }

    private final String getMediaStoreEntryPathApi29(Uri uri) {
        try {
            Cursor cursorQuery = getApplicationContext().getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            try {
                Cursor cursor = cursorQuery;
                if (cursor == null) {
                    CloseableKt.closeFinally(cursorQuery, null);
                    return null;
                }
                String string = !cursor.moveToFirst() ? null : cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                CloseableKt.closeFinally(cursorQuery, null);
                return string;
            } finally {
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logError("Get a path for a MediaStore failed");
            return null;
        }
    }

    private final void cleanUp() {
        TaskDao taskDao = this.taskDao;
        Intrinsics.checkNotNull(taskDao);
        String string = getId().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        DownloadTask downloadTaskLoadTask = taskDao.loadTask(string);
        if (downloadTaskLoadTask == null || downloadTaskLoadTask.getStatus() == DownloadStatus.COMPLETE || downloadTaskLoadTask.getResumable()) {
            return;
        }
        String filename = downloadTaskLoadTask.getFilename();
        if (filename == null) {
            filename = downloadTaskLoadTask.getUrl().substring(StringsKt.lastIndexOf$default((CharSequence) downloadTaskLoadTask.getUrl(), "/", 0, false, 6, (Object) null) + 1, downloadTaskLoadTask.getUrl().length());
            Intrinsics.checkNotNullExpressionValue(filename, "substring(...)");
        }
        File file = new File(downloadTaskLoadTask.getSavedDir() + File.separator + filename);
        if (file.exists()) {
            file.delete();
        }
    }

    private final int getNotificationIconRes() throws PackageManager.NameNotFoundException {
        try {
            ApplicationInfo applicationInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), 128);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
            return applicationInfo.metaData.getInt("vn.hunghd.flutterdownloader.NOTIFICATION_ICON", applicationInfo.icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private final void setupNotification(Context context) throws Resources.NotFoundException {
        if (this.showNotification && Build.VERSION.SDK_INT >= 26) {
            Resources resources = getApplicationContext().getResources();
            String string = resources.getString(R.string.flutter_downloader_notification_channel_name);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String string2 = resources.getString(R.string.flutter_downloader_notification_channel_description);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            MainActivity$$ExternalSyntheticApiModelOutline0.m301m();
            NotificationChannel notificationChannelM = MainActivity$$ExternalSyntheticApiModelOutline0.m(CHANNEL_ID, string, 2);
            notificationChannelM.setDescription(string2);
            notificationChannelM.setSound(null, null);
            NotificationManagerCompat notificationManagerCompatFrom = NotificationManagerCompat.from(context);
            Intrinsics.checkNotNullExpressionValue(notificationManagerCompatFrom, "from(...)");
            notificationManagerCompatFrom.createNotificationChannel(notificationChannelM);
        }
    }

    private final void updateNotification(Context context, String title, DownloadStatus status, int progress, PendingIntent intent, boolean finalize) throws InterruptedException {
        sendUpdateProcessEvent(status, progress);
        if (this.showNotification) {
            NotificationCompat.Builder priority = new NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title).setContentIntent(intent).setOnlyAlertOnce(true).setAutoCancel(true).setPriority(-1);
            Intrinsics.checkNotNullExpressionValue(priority, "setPriority(...)");
            int i = WhenMappings.$EnumSwitchMapping$0[status.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    priority.setContentText(this.msgCanceled).setProgress(0, 0, false);
                    priority.setOngoing(false).setSmallIcon(android.R.drawable.stat_sys_download_done);
                } else if (i == 3) {
                    priority.setContentText(this.msgFailed).setProgress(0, 0, false);
                    priority.setOngoing(false).setSmallIcon(android.R.drawable.stat_sys_download_done);
                } else if (i == 4) {
                    priority.setContentText(this.msgPaused).setProgress(0, 0, false);
                    priority.setOngoing(false).setSmallIcon(android.R.drawable.stat_sys_download_done);
                } else if (i == 5) {
                    priority.setContentText(this.msgComplete).setProgress(0, 0, false);
                    priority.setOngoing(false).setSmallIcon(android.R.drawable.stat_sys_download_done);
                } else {
                    priority.setProgress(0, 0, false);
                    priority.setOngoing(false).setSmallIcon(getNotificationIconRes());
                }
            } else if (progress <= 0) {
                priority.setContentText(this.msgStarted).setProgress(0, 0, false);
                priority.setOngoing(false).setSmallIcon(getNotificationIconRes());
            } else if (progress < 100) {
                priority.setContentText(this.msgInProgress).setProgress(100, progress, false);
                priority.setOngoing(true).setSmallIcon(android.R.drawable.stat_sys_download);
            } else {
                priority.setContentText(this.msgComplete).setProgress(0, 0, false);
                priority.setOngoing(false).setSmallIcon(android.R.drawable.stat_sys_download_done);
            }
            if (System.currentTimeMillis() - this.lastCallUpdateNotification < 1000) {
                if (finalize) {
                    log("Update too frequently!!!!, but it is the final update, we should sleep a second to ensure the update call can be processed");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log("Update too frequently!!!!, this should be dropped");
                    return;
                }
            }
            log("Update notification: {notificationId: " + this.primaryId + ", title: " + title + ", status: " + status + ", progress: " + progress + "}");
            NotificationManagerCompat.from(context).notify(this.primaryId, priority.build());
            this.lastCallUpdateNotification = System.currentTimeMillis();
        }
    }

    private final void sendUpdateProcessEvent(DownloadStatus status, int progress) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(getInputData().getLong(ARG_CALLBACK_HANDLE, 0L)));
        String string = getId().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        arrayList.add(string);
        arrayList.add(Integer.valueOf(status.ordinal()));
        arrayList.add(Integer.valueOf(progress));
        AtomicBoolean atomicBoolean = isolateStarted;
        synchronized (atomicBoolean) {
            if (!atomicBoolean.get()) {
                isolateQueue.add(arrayList);
            } else {
                new Handler(getApplicationContext().getMainLooper()).post(new Runnable() { // from class: vn.hunghd.flutterdownloader.DownloadWorker$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadWorker.sendUpdateProcessEvent$lambda$5$lambda$4(this.f$0, arrayList);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendUpdateProcessEvent$lambda$5$lambda$4(DownloadWorker downloadWorker, List list) {
        MethodChannel methodChannel = downloadWorker.backgroundChannel;
        if (methodChannel != null) {
            methodChannel.invokeMethod("", list);
        }
    }

    private final String getCharsetFromContentType(String contentType) {
        String strGroup;
        if (contentType == null) {
            return null;
        }
        Matcher matcher = this.charsetPattern.matcher(contentType);
        if (matcher.find() && (strGroup = matcher.group(1)) != null) {
            String str = strGroup;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            String string = str.subSequence(i, length + 1).toString();
            if (string != null) {
                Locale US = Locale.US;
                Intrinsics.checkNotNullExpressionValue(US, "US");
                String upperCase = string.toUpperCase(US);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                return upperCase;
            }
        }
        return null;
    }

    private final String getFileNameFromContentDisposition(String disposition, String contentCharset) throws UnsupportedEncodingException {
        if (disposition == null) {
            return null;
        }
        String str = disposition;
        Matcher matcher = this.filenamePattern.matcher(str);
        String strGroup = matcher.find() ? matcher.group(1) : null;
        Matcher matcher2 = this.filenameStarPattern.matcher(str);
        if (matcher2.find()) {
            strGroup = matcher2.group(3);
            String strGroup2 = matcher2.group(1);
            if (strGroup2 != null) {
                Locale US = Locale.US;
                Intrinsics.checkNotNullExpressionValue(US, "US");
                String upperCase = strGroup2.toUpperCase(US);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                contentCharset = upperCase;
            } else {
                contentCharset = null;
            }
        }
        if (strGroup == null) {
            return null;
        }
        if (contentCharset == null) {
            contentCharset = "ISO-8859-1";
        }
        return URLDecoder.decode(strGroup, contentCharset);
    }

    private final String getContentTypeWithoutCharset(String contentType) {
        List listSplit$default;
        String[] strArr;
        String str;
        if (contentType == null || (listSplit$default = StringsKt.split$default((CharSequence) contentType, new String[]{";"}, false, 0, 6, (Object) null)) == null || (strArr = (String[]) listSplit$default.toArray(new String[0])) == null || (str = strArr[0]) == null) {
            return null;
        }
        String str2 = str;
        int length = str2.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) str2.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        return str2.subSequence(i, length + 1).toString();
    }

    private final boolean isImageOrVideoFile(String contentType) {
        String contentTypeWithoutCharset = getContentTypeWithoutCharset(contentType);
        return contentTypeWithoutCharset != null && (StringsKt.startsWith$default(contentTypeWithoutCharset, "image/", false, 2, (Object) null) || StringsKt.startsWith$default(contentTypeWithoutCharset, "video", false, 2, (Object) null));
    }

    private final boolean isExternalStoragePath(String filePath) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Intrinsics.checkNotNullExpressionValue(externalStorageDirectory, "getExternalStorageDirectory(...)");
        if (filePath != null) {
            String path = externalStorageDirectory.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            if (StringsKt.startsWith$default(filePath, path, false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    private final void addImageOrVideoToGallery(String fileName, String filePath, String contentType) {
        if (contentType == null || filePath == null || fileName == null) {
            return;
        }
        if (StringsKt.startsWith$default(contentType, "image/", false, 2, (Object) null)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", fileName);
            contentValues.put("_display_name", fileName);
            contentValues.put("description", "");
            contentValues.put(TaskEntry.COLUMN_NAME_MIME_TYPE, contentType);
            contentValues.put("date_added", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("_data", filePath);
            log("insert " + contentValues + " to MediaStore");
            getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            return;
        }
        if (StringsKt.startsWith$default(contentType, "video", false, 2, (Object) null)) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("title", fileName);
            contentValues2.put("_display_name", fileName);
            contentValues2.put("description", "");
            contentValues2.put(TaskEntry.COLUMN_NAME_MIME_TYPE, contentType);
            contentValues2.put("date_added", Long.valueOf(System.currentTimeMillis()));
            contentValues2.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues2.put("_data", filePath);
            log("insert " + contentValues2 + " to MediaStore");
            getApplicationContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues2);
        }
    }

    private final void log(String message) {
        if (this.debug) {
            Log.d(TAG, message);
        }
    }

    private final void logError(String message) {
        if (this.debug) {
            Log.e(TAG, message);
        }
    }

    /* compiled from: DownloadWorker.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010#\u001a\u00020$H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\n \u0013*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006%"}, d2 = {"Lvn/hunghd/flutterdownloader/DownloadWorker$Companion;", "", "<init>", "()V", "ARG_URL", "", "ARG_FILE_NAME", "ARG_SAVED_DIR", "ARG_HEADERS", "ARG_IS_RESUME", "ARG_TIMEOUT", "ARG_SHOW_NOTIFICATION", "ARG_OPEN_FILE_FROM_NOTIFICATION", "ARG_CALLBACK_HANDLE", "ARG_DEBUG", "ARG_STEP", "ARG_SAVE_IN_PUBLIC_STORAGE", "ARG_IGNORESSL", "TAG", "kotlin.jvm.PlatformType", "Ljava/lang/String;", "BUFFER_SIZE", "", "CHANNEL_ID", "isolateStarted", "Ljava/util/concurrent/atomic/AtomicBoolean;", "isolateQueue", "Ljava/util/ArrayDeque;", "", "backgroundFlutterEngine", "Lio/flutter/embedding/engine/FlutterEngine;", "DO_NOT_VERIFY", "Ljavax/net/ssl/HostnameVerifier;", "getDO_NOT_VERIFY", "()Ljavax/net/ssl/HostnameVerifier;", "trustAllHosts", "", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HostnameVerifier getDO_NOT_VERIFY() {
            return DownloadWorker.DO_NOT_VERIFY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void trustAllHosts() throws NoSuchAlgorithmException, KeyManagementException {
            final String str = "trustAllHosts";
            TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: vn.hunghd.flutterdownloader.DownloadWorker$Companion$trustAllHosts$trustManagers$1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    Intrinsics.checkNotNullParameter(chain, "chain");
                    Intrinsics.checkNotNullParameter(authType, "authType");
                    Log.i(str, "checkClientTrusted");
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    Intrinsics.checkNotNullParameter(chain, "chain");
                    Intrinsics.checkNotNullParameter(authType, "authType");
                    Log.i(str, "checkServerTrusted");
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            try {
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                Intrinsics.checkNotNullExpressionValue(sSLContext, "getInstance(...)");
                sSLContext.init(null, trustManagerArr, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
