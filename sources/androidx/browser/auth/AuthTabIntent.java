package androidx.browser.auth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.browser.auth.AuthTabColorSchemeParams;
import androidx.browser.auth.AuthTabSession;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.IntentCompat;
import androidx.core.os.BundleCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class AuthTabIntent {
    public static final String EXTRA_HTTPS_REDIRECT_HOST = "androidx.browser.auth.extra.HTTPS_REDIRECT_HOST";
    public static final String EXTRA_HTTPS_REDIRECT_PATH = "androidx.browser.auth.extra.HTTPS_REDIRECT_PATH";
    public static final String EXTRA_LAUNCH_AUTH_TAB = "androidx.browser.auth.extra.LAUNCH_AUTH_TAB";
    public static final String EXTRA_REDIRECT_SCHEME = "androidx.browser.auth.extra.REDIRECT_SCHEME";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final int RESULT_UNKNOWN_CODE = -2;
    public static final int RESULT_VERIFICATION_FAILED = 2;
    public static final int RESULT_VERIFICATION_TIMED_OUT = 3;
    public final Intent intent;
    private final AuthTabSession.PendingSession mPendingSession;
    private final AuthTabSession mSession;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public void launch(ActivityResultLauncher<Intent> activityResultLauncher, Uri uri, String str) {
        this.intent.setData(uri);
        this.intent.putExtra(EXTRA_REDIRECT_SCHEME, str);
        activityResultLauncher.launch(this.intent);
    }

    public void launch(ActivityResultLauncher<Intent> activityResultLauncher, Uri uri, String str, String str2) {
        this.intent.setData(uri);
        this.intent.putExtra(EXTRA_HTTPS_REDIRECT_HOST, str);
        this.intent.putExtra(EXTRA_HTTPS_REDIRECT_PATH, str2);
        activityResultLauncher.launch(this.intent);
    }

    public boolean isEphemeralBrowsingEnabled() {
        return this.intent.getBooleanExtra(CustomTabsIntent.EXTRA_ENABLE_EPHEMERAL_BROWSING, false);
    }

    public static AuthTabColorSchemeParams getColorSchemeParams(Intent intent, int i) {
        Bundle bundle;
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return AuthTabColorSchemeParams.fromBundle(null);
        }
        AuthTabColorSchemeParams authTabColorSchemeParamsFromBundle = AuthTabColorSchemeParams.fromBundle(extras);
        SparseArray sparseParcelableArray = BundleCompat.getSparseParcelableArray(extras, CustomTabsIntent.EXTRA_COLOR_SCHEME_PARAMS, Bundle.class);
        return (sparseParcelableArray == null || (bundle = (Bundle) sparseParcelableArray.get(i)) == null) ? authTabColorSchemeParamsFromBundle : AuthTabColorSchemeParams.fromBundle(bundle).withDefaults(authTabColorSchemeParamsFromBundle);
    }

    public AuthTabSession getSession() {
        return this.mSession;
    }

    public AuthTabSession.PendingSession getPendingSession() {
        return this.mPendingSession;
    }

    public Bitmap getCloseButtonIcon() {
        return (Bitmap) IntentCompat.getParcelableExtra(this.intent, CustomTabsIntent.EXTRA_CLOSE_BUTTON_ICON, Bitmap.class);
    }

    private AuthTabIntent(Intent intent, AuthTabSession authTabSession, AuthTabSession.PendingSession pendingSession) {
        this.intent = intent;
        this.mSession = authTabSession;
        this.mPendingSession = pendingSession;
    }

    public static final class Builder {
        private SparseArray<Bundle> mColorSchemeParamBundles;
        private Bundle mDefaultColorSchemeBundle;
        private AuthTabSession.PendingSession mPendingSession;
        private AuthTabSession mSession;
        private final Intent mIntent = new Intent("android.intent.action.VIEW");
        private final AuthTabColorSchemeParams.Builder mDefaultColorSchemeBuilder = new AuthTabColorSchemeParams.Builder();

        public Builder setSession(AuthTabSession authTabSession) {
            this.mSession = authTabSession;
            this.mIntent.setPackage(authTabSession.getComponentName().getPackageName());
            setSessionParameters(authTabSession.getBinder(), authTabSession.getId());
            return this;
        }

        public Builder setPendingSession(AuthTabSession.PendingSession pendingSession) {
            this.mPendingSession = pendingSession;
            setSessionParameters(null, pendingSession.getId());
            return this;
        }

        private void setSessionParameters(IBinder iBinder, PendingIntent pendingIntent) {
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, iBinder);
            if (pendingIntent != null) {
                bundle.putParcelable(CustomTabsIntent.EXTRA_SESSION_ID, pendingIntent);
            }
            this.mIntent.putExtras(bundle);
        }

        public Builder setEphemeralBrowsingEnabled(boolean z) {
            this.mIntent.putExtra(CustomTabsIntent.EXTRA_ENABLE_EPHEMERAL_BROWSING, z);
            return this;
        }

        public Builder setColorScheme(int i) {
            this.mIntent.putExtra(CustomTabsIntent.EXTRA_COLOR_SCHEME, i);
            return this;
        }

        public Builder setColorSchemeParams(int i, AuthTabColorSchemeParams authTabColorSchemeParams) {
            if (this.mColorSchemeParamBundles == null) {
                this.mColorSchemeParamBundles = new SparseArray<>();
            }
            this.mColorSchemeParamBundles.put(i, authTabColorSchemeParams.toBundle());
            return this;
        }

        public Builder setDefaultColorSchemeParams(AuthTabColorSchemeParams authTabColorSchemeParams) {
            this.mDefaultColorSchemeBundle = authTabColorSchemeParams.toBundle();
            return this;
        }

        public Builder setCloseButtonIcon(Bitmap bitmap) {
            this.mIntent.putExtra(CustomTabsIntent.EXTRA_CLOSE_BUTTON_ICON, bitmap);
            return this;
        }

        public AuthTabIntent build() {
            this.mIntent.putExtra(AuthTabIntent.EXTRA_LAUNCH_AUTH_TAB, true);
            if (!this.mIntent.hasExtra(CustomTabsIntent.EXTRA_SESSION)) {
                setSessionParameters(null, null);
            }
            this.mIntent.putExtras(this.mDefaultColorSchemeBuilder.build().toBundle());
            Bundle bundle = this.mDefaultColorSchemeBundle;
            if (bundle != null) {
                this.mIntent.putExtras(bundle);
            }
            if (this.mColorSchemeParamBundles != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putSparseParcelableArray(CustomTabsIntent.EXTRA_COLOR_SCHEME_PARAMS, this.mColorSchemeParamBundles);
                this.mIntent.putExtras(bundle2);
            }
            return new AuthTabIntent(this.mIntent, this.mSession, this.mPendingSession);
        }
    }

    public static ActivityResultLauncher<Intent> registerActivityResultLauncher(ActivityResultCaller activityResultCaller, ActivityResultCallback<AuthResult> activityResultCallback) {
        return activityResultCaller.registerForActivityResult(new AuthenticateUserResultContract(), activityResultCallback);
    }

    public static final class AuthResult {
        public final int resultCode;
        public final Uri resultUri;

        AuthResult(int i, Uri uri) {
            this.resultCode = i;
            this.resultUri = uri;
        }
    }

    static class AuthenticateUserResultContract extends ActivityResultContract<Intent, AuthResult> {
        @Override // androidx.activity.result.contract.ActivityResultContract
        public Intent createIntent(Context context, Intent intent) {
            return intent;
        }

        AuthenticateUserResultContract() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        public AuthResult parseResult(int i, Intent intent) {
            Uri data = null;
            if (i != -1) {
                if (i != 0 && i != 2 && i != 3) {
                    i = -2;
                }
            } else if (intent != null) {
                data = intent.getData();
            }
            return new AuthResult(i, data);
        }
    }
}
