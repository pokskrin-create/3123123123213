package io.flutter.plugins.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.tika.mime.MimeTypesReaderMetKeys;

/* loaded from: classes4.dex */
class FlutterFirebaseMessagingUtils {
    static final String EXTRA_REMOTE_MESSAGE = "notification";
    static final String IS_AUTO_INIT_ENABLED = "isAutoInitEnabled";
    static final int JOB_ID = 2020;
    private static final String KEY_COLLAPSE_KEY = "collapseKey";
    private static final String KEY_DATA = "data";
    private static final String KEY_FROM = "from";
    private static final String KEY_MESSAGE_ID = "messageId";
    private static final String KEY_MESSAGE_TYPE = "messageType";
    private static final String KEY_SENT_TIME = "sentTime";
    private static final String KEY_TO = "to";
    private static final String KEY_TTL = "ttl";
    static final String SHARED_PREFERENCES_KEY = "io.flutter.firebase.messaging.callback";

    FlutterFirebaseMessagingUtils() {
    }

    static Map<String, Object> remoteMessageToMap(RemoteMessage remoteMessage) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        if (remoteMessage.getCollapseKey() != null) {
            map.put(KEY_COLLAPSE_KEY, remoteMessage.getCollapseKey());
        }
        if (remoteMessage.getFrom() != null) {
            map.put("from", remoteMessage.getFrom());
        }
        if (remoteMessage.getTo() != null) {
            map.put(KEY_TO, remoteMessage.getTo());
        }
        if (remoteMessage.getMessageId() != null) {
            map.put(KEY_MESSAGE_ID, remoteMessage.getMessageId());
        }
        if (remoteMessage.getMessageType() != null) {
            map.put(KEY_MESSAGE_TYPE, remoteMessage.getMessageType());
        }
        if (!remoteMessage.getData().isEmpty()) {
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        map.put("data", map2);
        map.put(KEY_TTL, Integer.valueOf(remoteMessage.getTtl()));
        map.put(KEY_SENT_TIME, Long.valueOf(remoteMessage.getSentTime()));
        if (remoteMessage.getNotification() != null) {
            map.put(EXTRA_REMOTE_MESSAGE, remoteMessageNotificationToMap(remoteMessage.getNotification()));
        }
        return map;
    }

    private static Map<String, Object> remoteMessageNotificationToMap(RemoteMessage.Notification notification) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        if (notification.getTitle() != null) {
            map.put("title", notification.getTitle());
        }
        if (notification.getTitleLocalizationKey() != null) {
            map.put("titleLocKey", notification.getTitleLocalizationKey());
        }
        if (notification.getTitleLocalizationArgs() != null) {
            map.put("titleLocArgs", Arrays.asList(notification.getTitleLocalizationArgs()));
        }
        if (notification.getBody() != null) {
            map.put("body", notification.getBody());
        }
        if (notification.getBodyLocalizationKey() != null) {
            map.put("bodyLocKey", notification.getBodyLocalizationKey());
        }
        if (notification.getBodyLocalizationArgs() != null) {
            map.put("bodyLocArgs", Arrays.asList(notification.getBodyLocalizationArgs()));
        }
        if (notification.getChannelId() != null) {
            map2.put("channelId", notification.getChannelId());
        }
        if (notification.getClickAction() != null) {
            map2.put("clickAction", notification.getClickAction());
        }
        if (notification.getColor() != null) {
            map2.put("color", notification.getColor());
        }
        if (notification.getIcon() != null) {
            map2.put("smallIcon", notification.getIcon());
        }
        if (notification.getImageUrl() != null) {
            map2.put("imageUrl", notification.getImageUrl().toString());
        }
        if (notification.getLink() != null) {
            map2.put("link", notification.getLink().toString());
        }
        if (notification.getNotificationCount() != null) {
            map2.put("count", notification.getNotificationCount());
        }
        if (notification.getNotificationPriority() != null) {
            map2.put(MimeTypesReaderMetKeys.MAGIC_PRIORITY_ATTR, notification.getNotificationPriority());
        }
        if (notification.getSound() != null) {
            map2.put("sound", notification.getSound());
        }
        if (notification.getTicker() != null) {
            map2.put("ticker", notification.getTicker());
        }
        if (notification.getVisibility() != null) {
            map2.put("visibility", notification.getVisibility());
        }
        if (notification.getTag() != null) {
            map2.put("tag", notification.getTag());
        }
        map.put("android", map2);
        return map;
    }

    static boolean isApplicationForeground(Context context) {
        ActivityManager activityManager;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if ((keyguardManager != null && keyguardManager.isKeyguardLocked()) || (activityManager = (ActivityManager) context.getSystemService("activity")) == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    static FirebaseMessaging getFirebaseMessagingForArguments(Map<String, Object> map) {
        return FirebaseMessaging.getInstance();
    }

    static RemoteMessage getRemoteMessageForArguments(Map<String, Object> map) {
        Map map2 = (Map) Objects.requireNonNull(map.get("message"));
        RemoteMessage.Builder builder = new RemoteMessage.Builder((String) Objects.requireNonNull(map2.get(KEY_TO)));
        String str = (String) map2.get(KEY_COLLAPSE_KEY);
        String str2 = (String) map2.get(KEY_MESSAGE_ID);
        String str3 = (String) map2.get(KEY_MESSAGE_TYPE);
        Integer num = (Integer) map2.get(KEY_TTL);
        Map<String, String> map3 = (Map) map2.get("data");
        if (str != null) {
            builder.setCollapseKey(str);
        }
        if (str3 != null) {
            builder.setMessageType(str3);
        }
        if (str2 != null) {
            builder.setMessageId(str2);
        }
        if (num != null) {
            builder.setTtl(num.intValue());
        }
        if (map3 != null) {
            builder.setData(map3);
        }
        return builder.build();
    }

    static Map<String, Object> getRemoteMessageNotificationForArguments(Map<String, Object> map) {
        Map map2 = (Map) Objects.requireNonNull(map.get("message"));
        if (map2.get(EXTRA_REMOTE_MESSAGE) == null) {
            return null;
        }
        return (Map) map2.get(EXTRA_REMOTE_MESSAGE);
    }
}
