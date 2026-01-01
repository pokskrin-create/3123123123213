package vn.hunghd.flutterdownloader;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.work.Configuration;
import androidx.work.WorkManager;
import com.google.android.gms.actions.SearchIntents;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FlutterDownloaderInitializer.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016JK\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J/\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0017J9\u0010\u0018\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\r\u001a\u0004\u0018\u00010\f2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002¨\u0006\u001e"}, d2 = {"Lvn/hunghd/flutterdownloader/FlutterDownloaderInitializer;", "Landroid/content/ContentProvider;", "<init>", "()V", "onCreate", "", SearchIntents.EXTRA_QUERY, "", "uri", "Landroid/net/Uri;", "strings", "", "", "s", "strings1", "s1", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Void;", "getType", "insert", "contentValues", "Landroid/content/ContentValues;", "delete", "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "getMaxConcurrentTaskMetadata", "context", "Landroid/content/Context;", "Companion", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FlutterDownloaderInitializer extends ContentProvider {
    private static final int DEFAULT_MAX_CONCURRENT_TASKS = 3;
    private static final String TAG = "DownloaderInitializer";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String s, String[] strings) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public Void getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public Void query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ String getType(Uri uri) {
        return (String) getType(uri);
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return (Cursor) query(uri, strArr, str, strArr2, str2);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() throws PackageManager.NameNotFoundException {
        Context context = getContext();
        if (context == null) {
            throw new IllegalArgumentException("Cannot find context from the provider.".toString());
        }
        int maxConcurrentTaskMetadata = getMaxConcurrentTaskMetadata(context);
        Configuration.Builder builder = new Configuration.Builder();
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(maxConcurrentTaskMetadata);
        Intrinsics.checkNotNullExpressionValue(executorServiceNewFixedThreadPool, "newFixedThreadPool(...)");
        WorkManager.initialize(context, builder.setExecutor(executorServiceNewFixedThreadPool).build());
        return true;
    }

    private final int getMaxConcurrentTaskMetadata(Context context) throws PackageManager.NameNotFoundException {
        try {
            ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(new ComponentName(context, "vn.hunghd.flutterdownloader.FlutterDownloaderInitializer"), 128);
            Intrinsics.checkNotNullExpressionValue(providerInfo, "getProviderInfo(...)");
            int i = providerInfo.metaData.getInt("vn.hunghd.flutterdownloader.MAX_CONCURRENT_TASKS", 3);
            Log.d(TAG, "MAX_CONCURRENT_TASKS = " + i);
            return i;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
            return 3;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e2.getMessage());
            return 3;
        }
    }
}
