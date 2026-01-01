package androidx.browser.trusted;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.browser.customtabs.TrustedWebUtils;
import androidx.browser.trusted.TrustedWebActivityDisplayMode;
import androidx.browser.trusted.sharing.ShareData;
import androidx.browser.trusted.sharing.ShareTarget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class TrustedWebActivityIntentBuilder {
    public static final String EXTRA_ADDITIONAL_TRUSTED_ORIGINS = "android.support.customtabs.extra.ADDITIONAL_TRUSTED_ORIGINS";
    public static final String EXTRA_DISPLAY_MODE = "androidx.browser.trusted.extra.DISPLAY_MODE";
    public static final String EXTRA_FILE_HANDLING_DATA = "androidx.browser.trusted.extra.FILE_HANDLING_DATA";
    public static final String EXTRA_LAUNCH_HANDLER_CLIENT_MODE = "androidx.browser.trusted.extra.LAUNCH_HANDLER_CLIENT_MODE";
    public static final String EXTRA_ORIGINAL_LAUNCH_URL = "androidx.browser.trusted.extra.ORIGINAL_LAUNCH_URL";
    public static final String EXTRA_SCREEN_ORIENTATION = "androidx.browser.trusted.extra.SCREEN_ORIENTATION";
    public static final String EXTRA_SHARE_DATA = "androidx.browser.trusted.extra.SHARE_DATA";
    public static final String EXTRA_SHARE_TARGET = "androidx.browser.trusted.extra.SHARE_TARGET";
    public static final String EXTRA_SPLASH_SCREEN_PARAMS = "androidx.browser.trusted.EXTRA_SPLASH_SCREEN_PARAMS";
    private List<String> mAdditionalTrustedOrigins;
    private FileHandlingData mFileHandlingData;
    private Uri mOriginalLaunchUrl;
    private ShareData mShareData;
    private ShareTarget mShareTarget;
    private Bundle mSplashScreenParams;
    private final Uri mUri;
    private final CustomTabsIntent.Builder mIntentBuilder = new CustomTabsIntent.Builder();
    private int mLaunchHandlerClientMode = 0;
    private TrustedWebActivityDisplayMode mDisplayMode = new TrustedWebActivityDisplayMode.DefaultMode();
    private int mScreenOrientation = 0;

    public TrustedWebActivityIntentBuilder(Uri uri) {
        this.mUri = uri;
    }

    @Deprecated
    public TrustedWebActivityIntentBuilder setToolbarColor(int i) {
        this.mIntentBuilder.setToolbarColor(i);
        return this;
    }

    @Deprecated
    public TrustedWebActivityIntentBuilder setNavigationBarColor(int i) {
        this.mIntentBuilder.setNavigationBarColor(i);
        return this;
    }

    @Deprecated
    public TrustedWebActivityIntentBuilder setNavigationBarDividerColor(int i) {
        this.mIntentBuilder.setNavigationBarDividerColor(i);
        return this;
    }

    public TrustedWebActivityIntentBuilder setColorScheme(int i) {
        this.mIntentBuilder.setColorScheme(i);
        return this;
    }

    public TrustedWebActivityIntentBuilder setColorSchemeParams(int i, CustomTabColorSchemeParams customTabColorSchemeParams) {
        this.mIntentBuilder.setColorSchemeParams(i, customTabColorSchemeParams);
        return this;
    }

    public TrustedWebActivityIntentBuilder setDefaultColorSchemeParams(CustomTabColorSchemeParams customTabColorSchemeParams) {
        this.mIntentBuilder.setDefaultColorSchemeParams(customTabColorSchemeParams);
        return this;
    }

    public TrustedWebActivityIntentBuilder setAdditionalTrustedOrigins(List<String> list) {
        this.mAdditionalTrustedOrigins = list;
        return this;
    }

    public TrustedWebActivityIntentBuilder setSplashScreenParams(Bundle bundle) {
        this.mSplashScreenParams = bundle;
        return this;
    }

    public TrustedWebActivityIntentBuilder setShareParams(ShareTarget shareTarget, ShareData shareData) {
        this.mShareTarget = shareTarget;
        this.mShareData = shareData;
        return this;
    }

    public TrustedWebActivityIntentBuilder setFileHandlingData(FileHandlingData fileHandlingData) {
        this.mFileHandlingData = fileHandlingData;
        return this;
    }

    public TrustedWebActivityIntentBuilder setDisplayMode(TrustedWebActivityDisplayMode trustedWebActivityDisplayMode) {
        this.mDisplayMode = trustedWebActivityDisplayMode;
        return this;
    }

    public TrustedWebActivityIntentBuilder setScreenOrientation(int i) {
        this.mScreenOrientation = i;
        return this;
    }

    public TrustedWebActivityIntentBuilder setOriginalLaunchUrl(Uri uri) {
        this.mOriginalLaunchUrl = uri;
        return this;
    }

    public TrustedWebActivityIntentBuilder setLaunchHandlerClientMode(int i) {
        this.mLaunchHandlerClientMode = i;
        return this;
    }

    public TrustedWebActivityIntent build(CustomTabsSession customTabsSession) {
        if (customTabsSession == null) {
            throw new NullPointerException("CustomTabsSession is required for launching a TWA");
        }
        this.mIntentBuilder.setSession(customTabsSession);
        Intent intent = this.mIntentBuilder.build().intent;
        intent.setData(this.mUri);
        intent.putExtra(TrustedWebUtils.EXTRA_LAUNCH_AS_TRUSTED_WEB_ACTIVITY, true);
        if (this.mAdditionalTrustedOrigins != null) {
            intent.putExtra(EXTRA_ADDITIONAL_TRUSTED_ORIGINS, new ArrayList(this.mAdditionalTrustedOrigins));
        }
        Bundle bundle = this.mSplashScreenParams;
        if (bundle != null) {
            intent.putExtra(EXTRA_SPLASH_SCREEN_PARAMS, bundle);
        }
        List<Uri> list = Collections.EMPTY_LIST;
        ShareTarget shareTarget = this.mShareTarget;
        if (shareTarget != null && this.mShareData != null) {
            intent.putExtra(EXTRA_SHARE_TARGET, shareTarget.toBundle());
            intent.putExtra(EXTRA_SHARE_DATA, this.mShareData.toBundle());
            if (this.mShareData.uris != null) {
                list = this.mShareData.uris;
            }
        }
        List<Uri> list2 = Collections.EMPTY_LIST;
        FileHandlingData fileHandlingData = this.mFileHandlingData;
        if (fileHandlingData != null) {
            intent.putExtra(EXTRA_FILE_HANDLING_DATA, fileHandlingData.toBundle());
            if (this.mFileHandlingData.uris != null) {
                list2 = this.mFileHandlingData.uris;
            }
        }
        intent.putExtra(EXTRA_DISPLAY_MODE, this.mDisplayMode.toBundle());
        intent.putExtra(EXTRA_SCREEN_ORIENTATION, this.mScreenOrientation);
        Uri uri = this.mOriginalLaunchUrl;
        if (uri != null) {
            intent.putExtra(EXTRA_ORIGINAL_LAUNCH_URL, uri);
        }
        intent.putExtra(EXTRA_LAUNCH_HANDLER_CLIENT_MODE, this.mLaunchHandlerClientMode);
        return new TrustedWebActivityIntent(intent, list, list2);
    }

    public CustomTabsIntent buildCustomTabsIntent() {
        return this.mIntentBuilder.build();
    }

    public Uri getUri() {
        return this.mUri;
    }

    public TrustedWebActivityDisplayMode getDisplayMode() {
        return this.mDisplayMode;
    }
}
