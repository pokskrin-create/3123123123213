package com.mr.flutter.plugin.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.internal.view.SupportMenu;
import com.google.firebase.messaging.Constants;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FilePickerDelegate.kt */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\b\u0018\u0000 K2\u00020\u0001:\u0001KB\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u00105\u001a\u0002062\b\u0010)\u001a\u0004\u0018\u00010*J\"\u00107\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u001d2\u0006\u00109\u001a\u00020\u001d2\b\u0010:\u001a\u0004\u0018\u00010;H\u0016J\u001a\u0010<\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u001d2\b\u0010:\u001a\u0004\u0018\u00010;H\u0002J\u0012\u0010=\u001a\u00020\u000f2\b\u0010>\u001a\u0004\u0018\u00010?H\u0002J\u001a\u0010@\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u001d2\b\u0010:\u001a\u0004\u0018\u00010;H\u0002J\u000e\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u0005J\u0010\u0010C\u001a\u0002062\b\u0010:\u001a\u0004\u0018\u00010DJ\u0018\u0010E\u001a\u0002062\u0006\u0010F\u001a\u00020\u00172\b\u0010G\u001a\u0004\u0018\u00010\u0017J\u0010\u0010H\u001a\u0002062\u0006\u0010I\u001a\u00020\u000fH\u0002J\b\u0010J\u001a\u000206H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R.\u0010\"\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0018\u00010#j\n\u0012\u0004\u0012\u00020\u0017\u0018\u0001`$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010)\u001a\u0004\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010/\u001a\u0004\u0018\u000100X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104¨\u0006L"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FilePickerDelegate;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "activity", "Landroid/app/Activity;", "pendingResult", "Lio/flutter/plugin/common/MethodChannel$Result;", "<init>", "(Landroid/app/Activity;Lio/flutter/plugin/common/MethodChannel$Result;)V", "getActivity", "()Landroid/app/Activity;", "getPendingResult", "()Lio/flutter/plugin/common/MethodChannel$Result;", "setPendingResult", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "isMultipleSelection", "", "()Z", "setMultipleSelection", "(Z)V", "loadDataToMemory", "getLoadDataToMemory", "setLoadDataToMemory", "type", "", "getType", "()Ljava/lang/String;", "setType", "(Ljava/lang/String;)V", "compressionQuality", "", "getCompressionQuality", "()I", "setCompressionQuality", "(I)V", "allowedExtensions", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getAllowedExtensions", "()Ljava/util/ArrayList;", "setAllowedExtensions", "(Ljava/util/ArrayList;)V", "eventSink", "Lio/flutter/plugin/common/EventChannel$EventSink;", "getEventSink", "()Lio/flutter/plugin/common/EventChannel$EventSink;", "setEventSink", "(Lio/flutter/plugin/common/EventChannel$EventSink;)V", "bytes", "", "getBytes", "()[B", "setBytes", "([B)V", "setEventHandler", "", "onActivityResult", "requestCode", "resultCode", Constants.ScionAnalytics.MessageType.DATA_MESSAGE, "Landroid/content/Intent;", "handleSaveFileResult", "saveFile", "uri", "Landroid/net/Uri;", "handleFilePickerResult", "setPendingMethodCallResult", "result", "finishWithSuccess", "", "finishWithError", "errorCode", "errorMessage", "dispatchEventStatus", "status", "clearPendingResult", "Companion", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FilePickerDelegate implements PluginRegistry.ActivityResultListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int REQUEST_CODE = (FilePickerPlugin.class.hashCode() + 43) & SupportMenu.USER_MASK;
    private static final int SAVE_FILE_CODE = (FilePickerPlugin.class.hashCode() + 83) & SupportMenu.USER_MASK;
    public static final String TAG = "FilePickerDelegate";
    private final Activity activity;
    private ArrayList<String> allowedExtensions;
    private byte[] bytes;
    private int compressionQuality;
    private EventChannel.EventSink eventSink;
    private boolean isMultipleSelection;
    private boolean loadDataToMemory;
    private MethodChannel.Result pendingResult;
    private String type;

    public FilePickerDelegate(Activity activity, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.pendingResult = result;
    }

    public /* synthetic */ FilePickerDelegate(Activity activity, MethodChannel.Result result, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(activity, (i & 2) != 0 ? null : result);
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final MethodChannel.Result getPendingResult() {
        return this.pendingResult;
    }

    public final void setPendingResult(MethodChannel.Result result) {
        this.pendingResult = result;
    }

    /* compiled from: FilePickerDelegate.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FilePickerDelegate$Companion;", "", "<init>", "()V", "TAG", "", "REQUEST_CODE", "", "getREQUEST_CODE", "()I", "SAVE_FILE_CODE", "getSAVE_FILE_CODE", "finishWithAlreadyActiveError", "", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getREQUEST_CODE() {
            return FilePickerDelegate.REQUEST_CODE;
        }

        public final int getSAVE_FILE_CODE() {
            return FilePickerDelegate.SAVE_FILE_CODE;
        }

        public final void finishWithAlreadyActiveError(MethodChannel.Result result) {
            Intrinsics.checkNotNullParameter(result, "result");
            result.error("already_active", "File picker is already active", null);
        }
    }

    /* renamed from: isMultipleSelection, reason: from getter */
    public final boolean getIsMultipleSelection() {
        return this.isMultipleSelection;
    }

    public final void setMultipleSelection(boolean z) {
        this.isMultipleSelection = z;
    }

    public final boolean getLoadDataToMemory() {
        return this.loadDataToMemory;
    }

    public final void setLoadDataToMemory(boolean z) {
        this.loadDataToMemory = z;
    }

    public final String getType() {
        return this.type;
    }

    public final void setType(String str) {
        this.type = str;
    }

    public final int getCompressionQuality() {
        return this.compressionQuality;
    }

    public final void setCompressionQuality(int i) {
        this.compressionQuality = i;
    }

    public final ArrayList<String> getAllowedExtensions() {
        return this.allowedExtensions;
    }

    public final void setAllowedExtensions(ArrayList<String> arrayList) {
        this.allowedExtensions = arrayList;
    }

    public final EventChannel.EventSink getEventSink() {
        return this.eventSink;
    }

    public final void setEventSink(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final void setBytes(byte[] bArr) {
        this.bytes = bArr;
    }

    public final void setEventHandler(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SAVE_FILE_CODE) {
            return handleSaveFileResult(resultCode, data);
        }
        if (requestCode == REQUEST_CODE) {
            return handleFilePickerResult(resultCode, data);
        }
        finishWithError("unknown_activity", "Unknown activity error, please file an issue.");
        return false;
    }

    private final boolean handleSaveFileResult(int resultCode, Intent data) {
        if (resultCode == -1) {
            return saveFile(data != null ? data.getData() : null);
        }
        if (resultCode != 0) {
            return false;
        }
        finishWithSuccess(null);
        return false;
    }

    private final boolean saveFile(Uri uri) {
        if (uri == null) {
            return false;
        }
        dispatchEventStatus(true);
        try {
            Uri uriWriteBytesData = FileUtils.INSTANCE.writeBytesData(this.activity, uri, this.bytes);
            if (uriWriteBytesData != null) {
                uri = uriWriteBytesData;
            }
            finishWithSuccess(uri.getPath());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error while saving file", e);
            finishWithError("Error while saving file", e.getMessage());
            return false;
        }
    }

    private final boolean handleFilePickerResult(int resultCode, Intent data) {
        if (resultCode != -1) {
            if (resultCode != 0) {
                return false;
            }
            finishWithSuccess(null);
            return true;
        }
        dispatchEventStatus(true);
        FileUtils fileUtils = FileUtils.INSTANCE;
        Activity activity = this.activity;
        int i = this.compressionQuality;
        boolean z = this.loadDataToMemory;
        String str = this.type;
        if (str == null) {
            str = "";
        }
        fileUtils.processFiles(this, activity, data, i, z, str);
        return true;
    }

    public final boolean setPendingMethodCallResult(MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(result, "result");
        if (this.pendingResult != null) {
            return false;
        }
        this.pendingResult = result;
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishWithSuccess(java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 0
            r5.dispatchEventStatus(r0)
            io.flutter.plugin.common.MethodChannel$Result r0 = r5.pendingResult
            if (r0 == 0) goto L5e
            r1 = 0
            if (r6 == 0) goto L1e
            boolean r2 = r6 instanceof java.lang.String
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r2)
            r3.getClass()
            if (r2 == 0) goto L18
            r2 = r6
            goto L19
        L18:
            r2 = r1
        L19:
            if (r2 != 0) goto L1c
            goto L1e
        L1c:
            r1 = r2
            goto L58
        L1e:
            boolean r2 = r6 instanceof java.util.ArrayList
            if (r2 == 0) goto L25
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            goto L26
        L25:
            r6 = r1
        L26:
            if (r6 == 0) goto L58
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.Iterator r6 = r6.iterator()
        L35:
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L55
            java.lang.Object r3 = r6.next()
            boolean r4 = r3 instanceof com.mr.flutter.plugin.filepicker.FileInfo
            if (r4 == 0) goto L46
            com.mr.flutter.plugin.filepicker.FileInfo r3 = (com.mr.flutter.plugin.filepicker.FileInfo) r3
            goto L47
        L46:
            r3 = r1
        L47:
            if (r3 == 0) goto L4e
            java.util.HashMap r3 = r3.toMap()
            goto L4f
        L4e:
            r3 = r1
        L4f:
            if (r3 == 0) goto L35
            r2.add(r3)
            goto L35
        L55:
            r1 = r2
            java.util.List r1 = (java.util.List) r1
        L58:
            r0.success(r1)
            r5.clearPendingResult()
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mr.flutter.plugin.filepicker.FilePickerDelegate.finishWithSuccess(java.lang.Object):void");
    }

    public final void finishWithError(String errorCode, String errorMessage) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        dispatchEventStatus(false);
        MethodChannel.Result result = this.pendingResult;
        if (result != null) {
            result.error(errorCode, errorMessage, null);
        }
        clearPendingResult();
    }

    private final void dispatchEventStatus(final boolean status) {
        if (this.eventSink == null || Intrinsics.areEqual(this.type, "dir")) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.mr.flutter.plugin.filepicker.FilePickerDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FilePickerDelegate.dispatchEventStatus$lambda$4(this.f$0, status);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dispatchEventStatus$lambda$4(FilePickerDelegate filePickerDelegate, boolean z) {
        EventChannel.EventSink eventSink = filePickerDelegate.eventSink;
        if (eventSink != null) {
            eventSink.success(Boolean.valueOf(z));
        }
    }

    private final void clearPendingResult() {
        this.pendingResult = null;
    }
}
