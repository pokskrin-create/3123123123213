package androidx.browser.trusted;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.core.content.IntentCompat;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class TrustedWebActivityIntent {
    private final List<Uri> mFileHandlingUris;
    private final Intent mIntent;
    private final List<Uri> mSharedFileUris;

    TrustedWebActivityIntent(Intent intent, List<Uri> list, List<Uri> list2) {
        this.mIntent = intent;
        this.mSharedFileUris = list;
        this.mFileHandlingUris = list2;
    }

    public Uri getOriginalLaunchUrl() {
        return (Uri) IntentCompat.getParcelableExtra(getIntent(), TrustedWebActivityIntentBuilder.EXTRA_ORIGINAL_LAUNCH_URL, Uri.class);
    }

    public FileHandlingData getFileHandlingData() {
        return FileHandlingData.fromBundle(getIntent().getBundleExtra(TrustedWebActivityIntentBuilder.EXTRA_FILE_HANDLING_DATA));
    }

    public int getLaunchHandlerClientMode() {
        return getIntent().getIntExtra(TrustedWebActivityIntentBuilder.EXTRA_LAUNCH_HANDLER_CLIENT_MODE, 0);
    }

    public void launchTrustedWebActivity(Context context) {
        grantUriPermissionToProvider(context);
        ContextCompat.startActivity(context, this.mIntent, null);
    }

    private void grantUriPermissionToProvider(Context context) {
        Iterator<Uri> it = this.mSharedFileUris.iterator();
        while (it.hasNext()) {
            context.grantUriPermission(this.mIntent.getPackage(), it.next(), 1);
        }
        Iterator<Uri> it2 = this.mFileHandlingUris.iterator();
        while (it2.hasNext()) {
            context.grantUriPermission(this.mIntent.getPackage(), it2.next(), 3);
        }
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
