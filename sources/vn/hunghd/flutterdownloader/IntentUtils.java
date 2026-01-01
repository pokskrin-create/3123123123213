package vn.hunghd.flutterdownloader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IntentUtils.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002J\"\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005H\u0002¨\u0006\u0012"}, d2 = {"Lvn/hunghd/flutterdownloader/IntentUtils;", "", "<init>", "()V", "buildIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "file", "Ljava/io/File;", "mime", "", "validatedFileIntent", "path", "contentType", "canBeHandled", "", "intent", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class IntentUtils {
    public static final IntentUtils INSTANCE = new IntentUtils();

    private IntentUtils() {
    }

    private final Intent buildIntent(Context context, File file, String mime) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(FileProvider.getUriForFile(context, context.getPackageName() + ".flutter_downloader.provider", file), mime);
        intent.setFlags(268435456);
        intent.addFlags(1);
        return intent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0048 A[Catch: all -> 0x0069, TryCatch #3 {, blocks: (B:3:0x0001, B:10:0x0029, B:29:0x0048, B:31:0x004e, B:13:0x002e, B:23:0x003d, B:26:0x0042, B:40:0x0060, B:44:0x0068, B:43:0x0065), top: B:52:0x0001, inners: #1, #2, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004e A[Catch: all -> 0x0069, TRY_LEAVE, TryCatch #3 {, blocks: (B:3:0x0001, B:10:0x0029, B:29:0x0048, B:31:0x004e, B:13:0x002e, B:23:0x003d, B:26:0x0042, B:40:0x0060, B:44:0x0068, B:43:0x0065), top: B:52:0x0001, inners: #1, #2, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized android.content.Intent validatedFileIntent(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)     // Catch: java.lang.Throwable -> L69
            java.lang.String r0 = "path"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)     // Catch: java.lang.Throwable -> L69
            java.io.File r0 = new java.io.File     // Catch: java.lang.Throwable -> L69
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L69
            android.content.Intent r6 = r3.buildIntent(r4, r0, r6)     // Catch: java.lang.Throwable -> L69
            boolean r1 = r3.canBeHandled(r4, r6)     // Catch: java.lang.Throwable -> L69
            if (r1 == 0) goto L1c
            monitor-exit(r3)
            return r6
        L1c:
            r6 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L36
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L36
            r2 = r1
            java.io.InputStream r2 = (java.io.InputStream) r2     // Catch: java.lang.Exception -> L32 java.lang.Throwable -> L5c
            java.lang.String r2 = java.net.URLConnection.guessContentTypeFromStream(r2)     // Catch: java.lang.Exception -> L32 java.lang.Throwable -> L5c
            r1.close()     // Catch: java.io.IOException -> L2d java.lang.Throwable -> L69
            goto L46
        L2d:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L69
            goto L46
        L32:
            r2 = move-exception
            goto L38
        L34:
            r4 = move-exception
            goto L5e
        L36:
            r2 = move-exception
            r1 = r6
        L38:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L5c
            if (r1 == 0) goto L45
            r1.close()     // Catch: java.io.IOException -> L41 java.lang.Throwable -> L69
            goto L45
        L41:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L69
        L45:
            r2 = r6
        L46:
            if (r2 != 0) goto L4c
            java.lang.String r2 = java.net.URLConnection.guessContentTypeFromName(r5)     // Catch: java.lang.Throwable -> L69
        L4c:
            if (r2 == 0) goto L5a
            android.content.Intent r5 = r3.buildIntent(r4, r0, r2)     // Catch: java.lang.Throwable -> L69
            boolean r4 = r3.canBeHandled(r4, r5)     // Catch: java.lang.Throwable -> L69
            if (r4 == 0) goto L5a
            monitor-exit(r3)
            return r5
        L5a:
            monitor-exit(r3)
            return r6
        L5c:
            r4 = move-exception
            r6 = r1
        L5e:
            if (r6 == 0) goto L68
            r6.close()     // Catch: java.io.IOException -> L64 java.lang.Throwable -> L69
            goto L68
        L64:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L69
        L68:
            throw r4     // Catch: java.lang.Throwable -> L69
        L69:
            r4 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L69
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: vn.hunghd.flutterdownloader.IntentUtils.validatedFileIntent(android.content.Context, java.lang.String, java.lang.String):android.content.Intent");
    }

    private final boolean canBeHandled(Context context, Intent intent) {
        List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        Intrinsics.checkNotNullExpressionValue(listQueryIntentActivities, "queryIntentActivities(...)");
        return listQueryIntentActivities.size() > 0;
    }
}
