package androidx.browser.customtabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/* loaded from: classes.dex */
public final class ContentActionSelectedData {
    private final Intent mIntent;

    private ContentActionSelectedData(Intent intent) {
        this.mIntent = intent;
    }

    public static ContentActionSelectedData fromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return new ContentActionSelectedData(intent);
    }

    public Uri getPageUrl() {
        return this.mIntent.getData();
    }

    public int getTriggeredActionId() {
        return this.mIntent.getIntExtra(CustomTabsIntent.EXTRA_TRIGGERED_CUSTOM_CONTENT_ACTION_ID, -1);
    }

    public int getClickedContentTargetType() {
        return this.mIntent.getIntExtra(CustomTabsIntent.EXTRA_CLICKED_CONTENT_TARGET_TYPE, 0);
    }

    public String getImageUrl() {
        return this.mIntent.getStringExtra(CustomTabsIntent.EXTRA_CONTEXT_IMAGE_URL);
    }

    public Uri getImageDataUri() {
        if (Build.VERSION.SDK_INT >= 33) {
            return (Uri) this.mIntent.getParcelableExtra(CustomTabsIntent.EXTRA_CONTEXT_IMAGE_DATA_URI, Uri.class);
        }
        return null;
    }

    public String getImageAltText() {
        return this.mIntent.getStringExtra(CustomTabsIntent.EXTRA_CONTEXT_IMAGE_ALT_TEXT);
    }

    public String getLinkUrl() {
        return this.mIntent.getStringExtra(CustomTabsIntent.EXTRA_CONTEXT_LINK_URL);
    }

    public String getLinkText() {
        return this.mIntent.getStringExtra(CustomTabsIntent.EXTRA_CONTEXT_LINK_TEXT);
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
