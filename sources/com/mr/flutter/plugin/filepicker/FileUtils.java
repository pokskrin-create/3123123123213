package com.mr.flutter.plugin.filepicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.mr.flutter.plugin.filepicker.FileInfo;
import io.flutter.plugin.common.MethodChannel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.TikaCoreProperties;

/* compiled from: FileUtils.kt */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J4\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u001a\u0010\u001b\u001a\u00020\t*\u00020\n2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\n\u0010\u001f\u001a\u00020\t*\u00020\nJ[\u0010\u001f\u001a\u00020\t*\u0004\u0018\u00010\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\u0010 \u001a\u0004\u0018\u00010\u00122\b\u0010!\u001a\u0004\u0018\u00010\u00122\u0016\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u00050#j\b\u0012\u0004\u0012\u00020\u0005`$2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010%\u001a\u00020&¢\u0006\u0002\u0010'J\u0010\u0010(\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u001c\u0010)\u001a\u00020\u00052\b\u0010*\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J:\u0010+\u001a\u00020\t*\u00020\n2\b\u0010*\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\u0010,\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010%\u001a\u00020&J \u0010-\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J.\u0010.\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0/H\u0002J$\u00100\u001a\u0016\u0012\u0004\u0012\u000201\u0018\u00010#j\n\u0012\u0004\u0012\u000201\u0018\u0001`$2\u0006\u00102\u001a\u000203H\u0002J2\u00104\u001a\u0012\u0012\u0004\u0012\u00020\u00050#j\b\u0012\u0004\u0012\u00020\u0005`$2\u001a\u0010\"\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010#j\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`$J\u001a\u00105\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u00106\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0007J\u001a\u0010(\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0002J\u0018\u00107\u001a\u0002082\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0002J\u0010\u00109\u001a\u00020\u00052\u0006\u0010:\u001a\u000208H\u0002J \u0010;\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010=\u001a\u00020>2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010?\u001a\u000208H\u0002J\u0010\u0010@\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0015H\u0002J\u0010\u0010A\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010B\u001a\u00020\t2\u0006\u0010C\u001a\u00020>2\u0006\u0010D\u001a\u00020EH\u0002J\"\u0010F\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0012H\u0007J\u0010\u0010G\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0015H\u0002J\u001c\u0010H\u001a\u0004\u0018\u00010\u00052\b\u0010I\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010J\u001a\u00020\u00052\u0006\u0010I\u001a\u00020\u0015H\u0002J\u0012\u0010K\u001a\u00020\t2\b\u0010C\u001a\u0004\u0018\u00010>H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FileUtils;", "", "<init>", "()V", "TAG", "", "CSV_EXTENSION", "CSV_MIME_TYPE", "processFiles", "", "Lcom/mr/flutter/plugin/filepicker/FilePickerDelegate;", "activity", "Landroid/app/Activity;", Constants.ScionAnalytics.MessageType.DATA_MESSAGE, "Landroid/content/Intent;", "compressionQuality", "", "loadDataToMemory", "", "type", "writeBytesData", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "uri", "bytes", "", "handleFileResult", "files", "", "Lcom/mr/flutter/plugin/filepicker/FileInfo;", "startFileExplorer", "isMultipleSelection", "withData", "allowedExtensions", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "(Lcom/mr/flutter/plugin/filepicker/FilePickerDelegate;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/util/ArrayList;Ljava/lang/Integer;Lio/flutter/plugin/common/MethodChannel$Result;)V", "getFileExtension", "getMimeTypeForBytes", "fileName", "saveFile", "initialDirectory", "processUri", "addFile", "", "getSelectedItems", "Landroid/os/Parcelable;", "bundle", "Landroid/os/Bundle;", "getMimeTypes", "getFileName", "isImage", "getCompressFormat", "Landroid/graphics/Bitmap$CompressFormat;", "getCompressFormatBasedFileExtension", "format", "compressImage", "originalImageUri", "createImageFile", "Ljava/io/File;", "compressFormat", "isDownloadsDocument", "clearCache", "loadData", "file", "fileInfo", "Lcom/mr/flutter/plugin/filepicker/FileInfo$Builder;", "openFileStream", "getPathFromTreeUri", "getFullPathFromTreeUri", "treeUri", "getDocumentPathFromTreeUri", "recursiveDeleteFile", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FileUtils {
    private static final String CSV_EXTENSION = "csv";
    private static final String CSV_MIME_TYPE = "text/csv";
    public static final FileUtils INSTANCE = new FileUtils();
    private static final String TAG = "FilePickerUtils";

    /* compiled from: FileUtils.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            try {
                iArr[Bitmap.CompressFormat.PNG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Bitmap.CompressFormat.WEBP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FileUtils() {
    }

    /* compiled from: FileUtils.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "com.mr.flutter.plugin.filepicker.FileUtils$processFiles$1", f = "FileUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.mr.flutter.plugin.filepicker.FileUtils$processFiles$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Activity $activity;
        final /* synthetic */ int $compressionQuality;
        final /* synthetic */ Intent $data;
        final /* synthetic */ boolean $loadDataToMemory;
        final /* synthetic */ FilePickerDelegate $this_processFiles;
        final /* synthetic */ String $type;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Intent intent, FilePickerDelegate filePickerDelegate, Activity activity, int i, boolean z, String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = intent;
            this.$this_processFiles = filePickerDelegate;
            this.$activity = activity;
            this.$compressionQuality = i;
            this.$loadDataToMemory = z;
            this.$type = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$data, this.$this_processFiles, this.$activity, this.$compressionQuality, this.$loadDataToMemory, this.$type, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.$data == null) {
                this.$this_processFiles.finishWithError("unknown_activity", "Unknown activity error, please fill an issue.");
                return Unit.INSTANCE;
            }
            ArrayList arrayList = new ArrayList();
            if (this.$data.getClipData() != null) {
                ClipData clipData = this.$data.getClipData();
                Intrinsics.checkNotNull(clipData);
                int itemCount = clipData.getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    ClipData clipData2 = this.$data.getClipData();
                    Intrinsics.checkNotNull(clipData2);
                    Uri uri = clipData2.getItemAt(i).getUri();
                    FileUtils fileUtils = FileUtils.INSTANCE;
                    Activity activity = this.$activity;
                    Intrinsics.checkNotNull(uri);
                    FileUtils.INSTANCE.addFile(this.$activity, fileUtils.processUri(activity, uri, this.$compressionQuality), this.$loadDataToMemory, arrayList);
                }
                this.$this_processFiles.finishWithSuccess(arrayList);
            } else if (this.$data.getData() != null) {
                FileUtils fileUtils2 = FileUtils.INSTANCE;
                Activity activity2 = this.$activity;
                Uri data = this.$data.getData();
                Intrinsics.checkNotNull(data);
                Uri uriProcessUri = fileUtils2.processUri(activity2, data, this.$compressionQuality);
                if (!Intrinsics.areEqual(this.$type, "dir")) {
                    FileUtils.INSTANCE.addFile(this.$activity, uriProcessUri, this.$loadDataToMemory, arrayList);
                    FileUtils.INSTANCE.handleFileResult(this.$this_processFiles, arrayList);
                } else {
                    String fullPathFromTreeUri = FileUtils.getFullPathFromTreeUri(DocumentsContract.buildDocumentUriUsingTree(uriProcessUri, DocumentsContract.getTreeDocumentId(uriProcessUri)), this.$activity);
                    if (fullPathFromTreeUri != null) {
                        this.$this_processFiles.finishWithSuccess(fullPathFromTreeUri);
                    } else {
                        this.$this_processFiles.finishWithError("unknown_path", "Failed to retrieve directory path.");
                    }
                }
            } else {
                Bundle extras = this.$data.getExtras();
                if (extras != null && extras.containsKey("selectedItems")) {
                    FileUtils fileUtils3 = FileUtils.INSTANCE;
                    Bundle extras2 = this.$data.getExtras();
                    Intrinsics.checkNotNull(extras2);
                    ArrayList selectedItems = fileUtils3.getSelectedItems(extras2);
                    if (selectedItems != null) {
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj2 : selectedItems) {
                            if (obj2 instanceof Uri) {
                                arrayList2.add(obj2);
                            }
                        }
                        Activity activity3 = this.$activity;
                        boolean z = this.$loadDataToMemory;
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            FileUtils.INSTANCE.addFile(activity3, (Uri) it.next(), z, arrayList);
                        }
                    }
                    this.$this_processFiles.finishWithSuccess(arrayList);
                } else {
                    this.$this_processFiles.finishWithError("unknown_activity", "Unknown activity error, please fill an issue.");
                }
            }
            return Unit.INSTANCE;
        }
    }

    public final void processFiles(FilePickerDelegate filePickerDelegate, Activity activity, Intent intent, int i, boolean z, String type) {
        Intrinsics.checkNotNullParameter(filePickerDelegate, "<this>");
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(type, "type");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(intent, filePickerDelegate, activity, i, z, type, null), 3, null);
    }

    public final Uri writeBytesData(Context context, Uri uri, byte[] bytes) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri);
        if (outputStreamOpenOutputStream == null) {
            return uri;
        }
        OutputStream outputStream = outputStreamOpenOutputStream;
        try {
            OutputStream outputStream2 = outputStream;
            if (bytes != null) {
                outputStream2.write(bytes);
                Unit unit = Unit.INSTANCE;
            }
            CloseableKt.closeFinally(outputStream, null);
            return uri;
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleFileResult(FilePickerDelegate filePickerDelegate, List<FileInfo> list) {
        if (!list.isEmpty()) {
            filePickerDelegate.finishWithSuccess(list);
        } else {
            filePickerDelegate.finishWithError("unknown_path", "Failed to retrieve path.");
        }
    }

    public final void startFileExplorer(FilePickerDelegate filePickerDelegate) {
        Intent intent;
        List listSplit$default;
        Intrinsics.checkNotNullParameter(filePickerDelegate, "<this>");
        if (filePickerDelegate.getType() == null) {
            return;
        }
        if (Intrinsics.areEqual(filePickerDelegate.getType(), "dir")) {
            intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
        } else if (Intrinsics.areEqual(filePickerDelegate.getType(), "image/*")) {
            intent = new Intent("android.intent.action.PICK");
            intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + File.separator), filePickerDelegate.getType());
            intent.setType(filePickerDelegate.getType());
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", filePickerDelegate.getIsMultipleSelection());
            intent.putExtra("multi-pick", filePickerDelegate.getIsMultipleSelection());
            String type = filePickerDelegate.getType();
            if (type != null) {
                if (!StringsKt.contains$default((CharSequence) type, (CharSequence) ",", false, 2, (Object) null)) {
                    type = null;
                }
                if (type != null && (listSplit$default = StringsKt.split$default((CharSequence) type, new String[]{","}, false, 0, 6, (Object) null)) != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : listSplit$default) {
                        if (((String) obj).length() > 0) {
                            arrayList.add(obj);
                        }
                    }
                    filePickerDelegate.setAllowedExtensions(new ArrayList<>(arrayList));
                }
            }
            if (filePickerDelegate.getAllowedExtensions() != null) {
                intent.putExtra("android.intent.extra.MIME_TYPES", filePickerDelegate.getAllowedExtensions());
            }
        } else {
            intent = new Intent("android.intent.action.OPEN_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType(filePickerDelegate.getType());
            ArrayList<String> allowedExtensions = filePickerDelegate.getAllowedExtensions();
            if (allowedExtensions != null && !allowedExtensions.isEmpty()) {
                ArrayList<String> allowedExtensions2 = filePickerDelegate.getAllowedExtensions();
                Intrinsics.checkNotNull(allowedExtensions2);
                intent.putExtra("android.intent.extra.MIME_TYPES", (String[]) allowedExtensions2.toArray(new String[0]));
            } else {
                intent.putExtra("android.intent.extra.MIME_TYPES", intent.getType());
            }
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", filePickerDelegate.getIsMultipleSelection());
            intent.putExtra("multi-pick", filePickerDelegate.getIsMultipleSelection());
        }
        if (intent.resolveActivity(filePickerDelegate.getActivity().getPackageManager()) != null) {
            filePickerDelegate.getActivity().startActivityForResult(intent, FilePickerDelegate.INSTANCE.getREQUEST_CODE());
        } else {
            Log.e(FilePickerDelegate.TAG, "Can't find a valid activity to handle the request. Make sure you've a file explorer installed.");
            filePickerDelegate.finishWithError("invalid_format_type", "Can't handle the provided file type.");
        }
    }

    public static /* synthetic */ void startFileExplorer$default(FileUtils fileUtils, FilePickerDelegate filePickerDelegate, String str, Boolean bool, Boolean bool2, ArrayList arrayList, Integer num, MethodChannel.Result result, int i, Object obj) {
        if ((i & 16) != 0) {
            num = 0;
        }
        fileUtils.startFileExplorer(filePickerDelegate, str, bool, bool2, arrayList, num, result);
    }

    public final void startFileExplorer(FilePickerDelegate filePickerDelegate, String str, Boolean bool, Boolean bool2, ArrayList<String> allowedExtensions, Integer num, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(allowedExtensions, "allowedExtensions");
        Intrinsics.checkNotNullParameter(result, "result");
        if (filePickerDelegate != null && !filePickerDelegate.setPendingMethodCallResult(result)) {
            FilePickerDelegate.INSTANCE.finishWithAlreadyActiveError(result);
            return;
        }
        if (filePickerDelegate != null) {
            filePickerDelegate.setType(str);
        }
        if (bool != null && filePickerDelegate != null) {
            filePickerDelegate.setMultipleSelection(bool.booleanValue());
        }
        if (bool2 != null && filePickerDelegate != null) {
            filePickerDelegate.setLoadDataToMemory(bool2.booleanValue());
        }
        if (filePickerDelegate != null) {
            filePickerDelegate.setAllowedExtensions(allowedExtensions);
        }
        if (num != null && filePickerDelegate != null) {
            filePickerDelegate.setCompressionQuality(num.intValue());
        }
        if (filePickerDelegate != null) {
            startFileExplorer(filePickerDelegate);
        }
    }

    public final String getFileExtension(byte[] bytes) throws IOException {
        String strDetect = new Tika().detect(bytes);
        Intrinsics.checkNotNull(strDetect);
        return StringsKt.substringAfter$default(strDetect, "/", (String) null, 2, (Object) null);
    }

    private final String getMimeTypeForBytes(String fileName, byte[] bytes) throws IOException {
        String strDetect;
        Tika tika = new Tika();
        String str = fileName;
        if (str == null || str.length() == 0) {
            strDetect = tika.detect(bytes);
        } else {
            Detector detector = tika.getDetector();
            TikaInputStream tikaInputStream = TikaInputStream.get(bytes);
            org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);
            strDetect = detector.detect(tikaInputStream, metadata).toString();
        }
        if (Intrinsics.areEqual(strDetect, "text/plain")) {
            return "*/*";
        }
        Intrinsics.checkNotNull(strDetect);
        return strDetect;
    }

    public final void saveFile(FilePickerDelegate filePickerDelegate, String str, String str2, String str3, byte[] bArr, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(filePickerDelegate, "<this>");
        Intrinsics.checkNotNullParameter(result, "result");
        if (!filePickerDelegate.setPendingMethodCallResult(result)) {
            FilePickerDelegate.INSTANCE.finishWithAlreadyActiveError(result);
            return;
        }
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        String str4 = str;
        if (str4 != null && str4.length() != 0) {
            intent.putExtra("android.intent.extra.TITLE", str);
        }
        filePickerDelegate.setBytes(bArr);
        if (!Intrinsics.areEqual("dir", str2)) {
            try {
                intent.setType(getMimeTypeForBytes(str, bArr));
            } catch (Throwable th) {
                intent.setType("*/*");
                Log.e(FilePickerDelegate.TAG, "Failed to detect mime type. " + th);
            }
        }
        String str5 = str3;
        if (str5 != null && str5.length() != 0 && Build.VERSION.SDK_INT >= 26) {
            intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(str3));
        }
        if (intent.resolveActivity(filePickerDelegate.getActivity().getPackageManager()) != null) {
            filePickerDelegate.getActivity().startActivityForResult(intent, FilePickerDelegate.INSTANCE.getSAVE_FILE_CODE());
        } else {
            Log.e(FilePickerDelegate.TAG, "Can't find a valid activity to handle the request. Make sure you've a file explorer installed.");
            filePickerDelegate.finishWithError("invalid_format_type", "Can't handle the provided file type.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Uri processUri(Activity activity, Uri uri, int compressionQuality) {
        if (compressionQuality > 0) {
            Context applicationContext = activity.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            if (isImage(applicationContext, uri)) {
                Context applicationContext2 = activity.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
                return compressImage(uri, compressionQuality, applicationContext2);
            }
        }
        return uri;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addFile(Activity activity, Uri uri, boolean loadDataToMemory, List<FileInfo> files) throws Throwable {
        FileInfo fileInfoOpenFileStream = openFileStream(activity, uri, loadDataToMemory);
        if (fileInfoOpenFileStream != null) {
            files.add(fileInfoOpenFileStream);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ArrayList<Parcelable> getSelectedItems(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 33) {
            return bundle.getParcelableArrayList("selectedItems", Parcelable.class);
        }
        return bundle.getParcelableArrayList("selectedItems");
    }

    public final ArrayList<String> getMimeTypes(ArrayList<String> allowedExtensions) {
        ArrayList<String> arrayList = allowedExtensions;
        if (arrayList == null || arrayList.isEmpty()) {
            return new ArrayList<>(CollectionsKt.listOf("*/*"));
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(allowedExtensions.get(i));
            if (mimeTypeFromExtension == null) {
                Log.w(TAG, "Custom file type '" + ((Object) allowedExtensions.get(i)) + "' is unsupported and will not be filtered.");
                return new ArrayList<>(CollectionsKt.listOf("*/*"));
            }
            arrayList2.add(mimeTypeFromExtension);
            if (Intrinsics.areEqual(allowedExtensions.get(i), CSV_EXTENSION)) {
                arrayList2.add(CSV_MIME_TYPE);
            }
        }
        Log.d(TAG, "Custom file types are " + allowedExtensions + ". The mime types were detected as " + arrayList2 + ".");
        return arrayList2;
    }

    @JvmStatic
    public static final String getFileName(Uri uri, Context context) {
        Exception exc;
        Uri uri2;
        String string;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(context, "context");
        String str = null;
        try {
            if (Intrinsics.areEqual(uri.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
                uri2 = uri;
                Cursor cursorQuery = context.getContentResolver().query(uri2, new String[]{"_display_name"}, null, null, null);
                try {
                    Cursor cursor = cursorQuery;
                    string = (cursor == null || !cursor.moveToFirst()) ? null : cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    Unit unit = Unit.INSTANCE;
                    try {
                        CloseableKt.closeFinally(cursorQuery, null);
                    } catch (Exception e) {
                        exc = e;
                        str = string;
                        Log.e(TAG, "Failed to handle file name: " + exc);
                        return str;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    str = string;
                    Throwable th3 = th;
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(cursorQuery, th3);
                        throw th4;
                    }
                }
            } else {
                uri2 = uri;
                string = null;
            }
            if (string != null) {
                return string;
            }
            String path = uri2.getPath();
            if (path != null) {
                return StringsKt.substringAfterLast$default(path, IOUtils.DIR_SEPARATOR_UNIX, (String) null, 2, (Object) null);
            }
            return null;
        } catch (Exception e2) {
            exc = e2;
        }
    }

    @JvmStatic
    public static final boolean isImage(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        String fileExtension = INSTANCE.getFileExtension(context, uri);
        if (fileExtension == null) {
            return false;
        }
        return fileExtension.contentEquals("jpg") || fileExtension.contentEquals("jpeg") || fileExtension.contentEquals("png") || fileExtension.contentEquals("webp") || fileExtension.contentEquals("heic") || fileExtension.contentEquals("heif");
    }

    private final String getFileExtension(Context context, Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
    }

    private final Bitmap.CompressFormat getCompressFormat(Context context, Uri uri) {
        String fileExtension = getFileExtension(context, uri);
        Intrinsics.checkNotNull(fileExtension);
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
        String upperCase = fileExtension.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return Intrinsics.areEqual(upperCase, "PNG") ? Bitmap.CompressFormat.PNG : Intrinsics.areEqual(upperCase, "WEBP") ? Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.JPEG;
    }

    private final String getCompressFormatBasedFileExtension(Bitmap.CompressFormat format) {
        int i = WhenMappings.$EnumSwitchMapping$0[format.ordinal()];
        if (i == 1) {
            return "png";
        }
        if (i == 2) {
            return "webp";
        }
        return "jpeg";
    }

    @JvmStatic
    public static final Uri compressImage(Uri originalImageUri, int compressionQuality, Context context) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(originalImageUri, "originalImageUri");
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(originalImageUri);
            try {
                FileUtils fileUtils = INSTANCE;
                Bitmap.CompressFormat compressFormat = fileUtils.getCompressFormat(context, originalImageUri);
                File fileCreateImageFile = fileUtils.createImageFile(context, compressFormat);
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateImageFile);
                bitmapDecodeStream.compress(compressFormat, compressionQuality, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Uri uriFromFile = Uri.fromFile(fileCreateImageFile);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStreamOpenInputStream, null);
                return uriFromFile;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final File createImageFile(Context context, Bitmap.CompressFormat compressFormat) throws IOException {
        File fileCreateTempFile = File.createTempFile("IMAGE_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + "_", "." + getCompressFormatBasedFileExtension(compressFormat), context.getCacheDir());
        Intrinsics.checkNotNullExpressionValue(fileCreateTempFile, "createTempFile(...)");
        return fileCreateTempFile;
    }

    private final boolean isDownloadsDocument(Uri uri) {
        return Intrinsics.areEqual(uri.getAuthority(), "com.android.providers.downloads.documents");
    }

    @JvmStatic
    public static final boolean clearCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            INSTANCE.recursiveDeleteFile(new File(context.getCacheDir() + "/file_picker/"));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "There was an error while clearing cached files: " + e);
            return false;
        }
    }

    private final void loadData(File file, FileInfo.Builder fileInfo) throws IOException {
        try {
            int length = (int) file.length();
            byte[] bArr = new byte[length];
            try {
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    bufferedInputStream.read(bArr, 0, length);
                    bufferedInputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "File not found: " + e.getMessage(), null);
                }
            } catch (IOException e2) {
                Log.e(TAG, "Failed to close file streams: " + e2.getMessage(), null);
            }
            fileInfo.withData(bArr);
        } catch (Exception e3) {
            Log.e(TAG, "Failed to load bytes into memory with error " + e3 + ". Probably the file is too big to fit device memory. Bytes won't be added to the file this time.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0124 A[Catch: IOException -> 0x0120, TryCatch #7 {IOException -> 0x0120, blocks: (B:55:0x0116, B:57:0x011c, B:61:0x0124, B:63:0x0129), top: B:77:0x0116 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0129 A[Catch: IOException -> 0x0120, TRY_LEAVE, TryCatch #7 {IOException -> 0x0120, blocks: (B:55:0x0116, B:57:0x011c, B:61:0x0124, B:63:0x0129), top: B:77:0x0116 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0116 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.mr.flutter.plugin.filepicker.FileInfo openFileStream(android.content.Context r13, android.net.Uri r14, boolean r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mr.flutter.plugin.filepicker.FileUtils.openFileStream(android.content.Context, android.net.Uri, boolean):com.mr.flutter.plugin.filepicker.FileInfo");
    }

    private final String getPathFromTreeUri(Uri uri) {
        String treeDocumentId = DocumentsContract.getTreeDocumentId(uri);
        Intrinsics.checkNotNull(treeDocumentId);
        List listSplit$default = StringsKt.split$default((CharSequence) treeDocumentId, new String[]{TikaCoreProperties.NAMESPACE_PREFIX_DELIMITER}, false, 0, 6, (Object) null);
        if (listSplit$default.size() > 1) {
            String str = (String) listSplit$default.get(0);
            String str2 = (String) listSplit$default.get(1);
            if (StringsKt.equals("primary", str, true)) {
                return Environment.getExternalStorageDirectory() + "/" + str2;
            }
            return "/storage/" + str + "/" + str2;
        }
        return Environment.getExternalStorageDirectory() + "/" + CollectionsKt.last((List<? extends Object>) listSplit$default);
    }

    @JvmStatic
    public static final String getFullPathFromTreeUri(Uri treeUri, Context context) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(context, "context");
        if (treeUri == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 30 && INSTANCE.isDownloadsDocument(treeUri)) {
            String documentId = DocumentsContract.getDocumentId(treeUri);
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            if (Intrinsics.areEqual(documentId, "downloads")) {
                return path;
            }
            Intrinsics.checkNotNull(documentId);
            String str = documentId;
            if (new Regex("^ms[df]:.*").matches(str)) {
                return path + "/" + getFileName(treeUri, context);
            }
            if (!StringsKt.startsWith$default(documentId, "raw:", false, 2, (Object) null)) {
                return null;
            }
            List<String> listSplit = new Regex(TikaCoreProperties.NAMESPACE_PREFIX_DELIMITER).split(str, 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                listEmptyList = CollectionsKt.emptyList();
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            return ((String[]) listEmptyList.toArray(new String[0]))[1];
        }
        FileUtils fileUtils = INSTANCE;
        String pathFromTreeUri = fileUtils.getPathFromTreeUri(treeUri);
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        if (StringsKt.endsWith$default(pathFromTreeUri, separator, false, 2, (Object) null)) {
            pathFromTreeUri = StringsKt.dropLast(pathFromTreeUri, 1);
        }
        String documentPathFromTreeUri = fileUtils.getDocumentPathFromTreeUri(treeUri);
        String separator2 = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator2, "separator");
        if (StringsKt.endsWith$default(documentPathFromTreeUri, separator2, false, 2, (Object) null)) {
            documentPathFromTreeUri = StringsKt.dropLast(documentPathFromTreeUri, 1);
        }
        if (documentPathFromTreeUri.length() <= 0 || StringsKt.endsWith$default(pathFromTreeUri, documentPathFromTreeUri, false, 2, (Object) null)) {
            return pathFromTreeUri;
        }
        String separator3 = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator3, "separator");
        if (StringsKt.startsWith$default(documentPathFromTreeUri, separator3, false, 2, (Object) null)) {
            return pathFromTreeUri + documentPathFromTreeUri;
        }
        return pathFromTreeUri + File.separator + documentPathFromTreeUri;
    }

    private final String getDocumentPathFromTreeUri(Uri treeUri) {
        List listEmptyList;
        String treeDocumentId = DocumentsContract.getTreeDocumentId(treeUri);
        Intrinsics.checkNotNull(treeDocumentId);
        List<String> listSplit = new Regex(TikaCoreProperties.NAMESPACE_PREFIX_DELIMITER).split(treeDocumentId, 0);
        if (!listSplit.isEmpty()) {
            ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
            while (listIterator.hasPrevious()) {
                if (listIterator.previous().length() != 0) {
                    listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    break;
                }
            }
            listEmptyList = CollectionsKt.emptyList();
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
        if (strArr.length >= 2) {
            return strArr[1];
        }
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        return separator;
    }

    private final void recursiveDeleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.listFiles() != null && file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                fileArrListFiles = new File[0];
            }
            for (File file2 : fileArrListFiles) {
                recursiveDeleteFile(file2);
            }
        }
        file.delete();
    }
}
