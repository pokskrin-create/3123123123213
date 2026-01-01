package io.flutter.plugins.firebase.messaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class FlutterFirebaseMessagingReceiver extends BroadcastReceiver {
    private static final String TAG = "FLTFireMsgReceiver";
    static HashMap<String, RemoteMessage> notifications = new HashMap<>();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "broadcast received for message");
        if (ContextHolder.getApplicationContext() == null) {
            ContextHolder.setApplicationContext(context.getApplicationContext() != null ? context.getApplicationContext() : context);
        }
        if (intent.getExtras() == null) {
            Log.d(TAG, "broadcast received but intent contained no extras to process RemoteMessage. Operation cancelled.");
            return;
        }
        RemoteMessage remoteMessage = new RemoteMessage(intent.getExtras());
        if (remoteMessage.getNotification() != null) {
            notifications.put(remoteMessage.getMessageId(), remoteMessage);
            FlutterFirebaseMessagingStore.getInstance().storeFirebaseMessage(remoteMessage);
        }
        if (FlutterFirebaseMessagingUtils.isApplicationForeground(context)) {
            FlutterFirebaseRemoteMessageLiveData.getInstance().postRemoteMessage(remoteMessage);
            return;
        }
        Intent intent2 = new Intent(context, (Class<?>) FlutterFirebaseMessagingBackgroundService.class);
        Parcel parcelObtain = Parcel.obtain();
        remoteMessage.writeToParcel(parcelObtain, 0);
        intent2.putExtra("notification", parcelObtain.marshall());
        FlutterFirebaseMessagingBackgroundService.enqueueMessageProcessing(context, intent2, remoteMessage.getOriginalPriority() == 1);
    }
}
