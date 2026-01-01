package androidx.browser.auth;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.IAuthTabCallback;
import android.util.Log;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.IntentCompat;

/* loaded from: classes.dex */
public final class AuthTabSessionToken {
    private static final String TAG = "AuthTabSessionToken";
    private final AuthTabCallback mCallback;
    private final IAuthTabCallback mCallbackBinder;
    private final PendingIntent mSessionId;

    public AuthTabSessionToken(IAuthTabCallback iAuthTabCallback, PendingIntent pendingIntent) {
        if (iAuthTabCallback == null && pendingIntent == null) {
            throw new IllegalStateException("AuthTabSessionToken must have either a session id or a callback (or both).");
        }
        this.mCallbackBinder = iAuthTabCallback;
        this.mSessionId = pendingIntent;
        this.mCallback = iAuthTabCallback == null ? null : new AuthTabCallback() { // from class: androidx.browser.auth.AuthTabSessionToken.1
            @Override // androidx.browser.auth.AuthTabCallback
            public void onNavigationEvent(int i, Bundle bundle) {
                try {
                    AuthTabSessionToken.this.mCallbackBinder.onNavigationEvent(i, bundle);
                } catch (RemoteException unused) {
                    Log.e(AuthTabSessionToken.TAG, "RemoteException during IAuthTabCallback transaction");
                }
            }

            @Override // androidx.browser.auth.AuthTabCallback
            public void onExtraCallback(String str, Bundle bundle) {
                try {
                    AuthTabSessionToken.this.mCallbackBinder.onExtraCallback(str, bundle);
                } catch (RemoteException unused) {
                    Log.e(AuthTabSessionToken.TAG, "RemoteException during IAuthTabCallback transaction");
                }
            }

            @Override // androidx.browser.auth.AuthTabCallback
            public Bundle onExtraCallbackWithResult(String str, Bundle bundle) {
                try {
                    return AuthTabSessionToken.this.mCallbackBinder.onExtraCallbackWithResult(str, bundle);
                } catch (RemoteException unused) {
                    Log.e(AuthTabSessionToken.TAG, "RemoteException during IAuthTabCallback transaction");
                    return Bundle.EMPTY;
                }
            }

            @Override // androidx.browser.auth.AuthTabCallback
            public void onWarmupCompleted(Bundle bundle) {
                try {
                    AuthTabSessionToken.this.mCallbackBinder.onWarmupCompleted(bundle);
                } catch (RemoteException unused) {
                    Log.e(AuthTabSessionToken.TAG, "RemoteException during IAuthTabCallback transaction");
                }
            }
        };
    }

    public static AuthTabSessionToken createSessionTokenFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }
        IBinder binder = extras.getBinder(CustomTabsIntent.EXTRA_SESSION);
        PendingIntent pendingIntent = (PendingIntent) IntentCompat.getParcelableExtra(intent, CustomTabsIntent.EXTRA_SESSION_ID, PendingIntent.class);
        if (binder == null && pendingIntent == null) {
            return null;
        }
        return new AuthTabSessionToken(binder != null ? IAuthTabCallback.Stub.asInterface(binder) : null, pendingIntent);
    }

    public AuthTabCallback getCallback() {
        return this.mCallback;
    }

    public IBinder getCallbackBinder() {
        IAuthTabCallback iAuthTabCallback = this.mCallbackBinder;
        if (iAuthTabCallback == null) {
            return null;
        }
        return iAuthTabCallback.asBinder();
    }

    public PendingIntent getId() {
        return this.mSessionId;
    }

    public boolean hasId() {
        return this.mSessionId != null;
    }

    public int hashCode() {
        PendingIntent pendingIntent = this.mSessionId;
        return pendingIntent != null ? pendingIntent.hashCode() : getCallbackBinderAssertNotNull().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AuthTabSessionToken)) {
            return false;
        }
        AuthTabSessionToken authTabSessionToken = (AuthTabSessionToken) obj;
        PendingIntent id = authTabSessionToken.getId();
        PendingIntent pendingIntent = this.mSessionId;
        if ((pendingIntent == null) != (id == null)) {
            return false;
        }
        if (pendingIntent != null) {
            return pendingIntent.equals(id);
        }
        return getCallbackBinderAssertNotNull().equals(authTabSessionToken.getCallbackBinderAssertNotNull());
    }

    private IBinder getCallbackBinderAssertNotNull() {
        IAuthTabCallback iAuthTabCallback = this.mCallbackBinder;
        if (iAuthTabCallback == null) {
            throw new IllegalStateException("AuthTabSessionToken must have valid binder or pending session");
        }
        return iAuthTabCallback.asBinder();
    }

    public boolean isAssociatedWith(AuthTabSession authTabSession) {
        return authTabSession.getBinder().equals(this.mCallbackBinder);
    }

    static class MockCallback extends IAuthTabCallback.Stub {
        @Override // android.support.customtabs.IAuthTabCallback
        public void onExtraCallback(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.IAuthTabCallback
        public void onNavigationEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.IAuthTabCallback
        public void onWarmupCompleted(Bundle bundle) throws RemoteException {
        }

        MockCallback() {
        }

        @Override // android.support.customtabs.IAuthTabCallback
        public Bundle onExtraCallbackWithResult(String str, Bundle bundle) throws RemoteException {
            return Bundle.EMPTY;
        }
    }
}
