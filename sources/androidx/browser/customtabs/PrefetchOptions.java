package androidx.browser.customtabs;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class PrefetchOptions {
    private static final String KEY_REQUIRES_ANONYMOUS_IP_WHEN_CROSS_ORIGIN = "androidx.browser.customtabs.PrefetchOptions.KEY_REQUIRES_ANONYMOUS_IP_WHEN_CROSS_ORIGIN";
    private static final String KEY_SOURCE_ORIGIN = "androidx.browser.customtabs.PrefetchOptions.KEY_SOURCE_ORIGIN";
    public final boolean requiresAnonymousIpWhenCrossOrigin;
    public final Uri sourceOrigin;

    PrefetchOptions(boolean z, Uri uri) {
        this.requiresAnonymousIpWhenCrossOrigin = z;
        this.sourceOrigin = uri;
    }

    Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_REQUIRES_ANONYMOUS_IP_WHEN_CROSS_ORIGIN, this.requiresAnonymousIpWhenCrossOrigin);
        Uri uri = this.sourceOrigin;
        if (uri != null) {
            bundle.putParcelable(KEY_SOURCE_ORIGIN, uri);
        }
        return bundle;
    }

    static PrefetchOptions fromBundle(Bundle bundle) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 33) {
            uri = (Uri) Api33Impl.getParcelable(bundle, KEY_SOURCE_ORIGIN, Uri.class);
        } else {
            uri = (Uri) bundle.getParcelable(KEY_SOURCE_ORIGIN);
        }
        return new PrefetchOptions(bundle.getBoolean(KEY_REQUIRES_ANONYMOUS_IP_WHEN_CROSS_ORIGIN), uri);
    }

    public static final class Builder {
        private boolean mRequiresAnonymousIpWhenCrossOrigin;
        private Uri mSourceOrigin;

        public Builder setRequiresAnonymousIpWhenCrossOrigin(boolean z) {
            this.mRequiresAnonymousIpWhenCrossOrigin = z;
            return this;
        }

        public Builder setSourceOrigin(Uri uri) {
            this.mSourceOrigin = uri;
            return this;
        }

        public PrefetchOptions build() {
            return new PrefetchOptions(this.mRequiresAnonymousIpWhenCrossOrigin, this.mSourceOrigin);
        }
    }
}
