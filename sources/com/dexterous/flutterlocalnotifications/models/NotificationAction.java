package com.dexterous.flutterlocalnotifications.models;

import android.graphics.Color;
import com.google.firebase.messaging.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class NotificationAction implements Serializable {
    private static final String ALLOW_GENERATED_REPLIES = "allowGeneratedReplies";
    private static final String CANCEL_NOTIFICATION = "cancelNotification";
    private static final String CONTEXTUAL = "contextual";
    private static final String ICON = "icon";
    private static final String ICON_SOURCE = "iconBitmapSource";
    private static final String ID = "id";
    private static final String INPUTS = "inputs";
    private static final String INVISIBLE = "invisible";
    private static final String SEMANTIC_ACTION = "semanticAction";
    private static final String SHOWS_USER_INTERFACE = "showsUserInterface";
    private static final String TITLE = "title";
    private static final String TITLE_COLOR_ALPHA = "titleColorAlpha";
    private static final String TITLE_COLOR_BLUE = "titleColorBlue";
    private static final String TITLE_COLOR_GREEN = "titleColorGreen";
    private static final String TITLE_COLOR_RED = "titleColorRed";
    public final List<NotificationActionInput> actionInputs = new ArrayList();
    public final Boolean allowGeneratedReplies;
    public final Boolean cancelNotification;
    public final Boolean contextual;
    public final String icon;
    public final IconSource iconSource;
    public final String id;
    public final Boolean invisible;
    public final Integer semanticAction;
    public final Boolean showsUserInterface;
    public final String title;
    public final Integer titleColor;

    public static class NotificationActionInput implements Serializable {
        public final Boolean allowFreeFormInput;
        public final List<String> allowedMimeTypes;
        public final List<String> choices;
        public final String label;

        public NotificationActionInput(List<String> list, Boolean bool, String str, List<String> list2) {
            this.choices = list;
            this.allowFreeFormInput = bool;
            this.label = str;
            this.allowedMimeTypes = list2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                NotificationActionInput notificationActionInput = (NotificationActionInput) obj;
                List<String> list = this.choices;
                if (list == null ? notificationActionInput.choices != null : !list.equals(notificationActionInput.choices)) {
                    return false;
                }
                Boolean bool = this.allowFreeFormInput;
                if (bool == null ? notificationActionInput.allowFreeFormInput != null : !bool.equals(notificationActionInput.allowFreeFormInput)) {
                    return false;
                }
                String str = this.label;
                if (str == null ? notificationActionInput.label != null : !str.equals(notificationActionInput.label)) {
                    return false;
                }
                List<String> list2 = this.allowedMimeTypes;
                if (list2 != null) {
                    return list2.equals(notificationActionInput.allowedMimeTypes);
                }
                if (notificationActionInput.allowedMimeTypes == null) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            List<String> list = this.choices;
            int iHashCode = (list != null ? list.hashCode() : 0) * 31;
            Boolean bool = this.allowFreeFormInput;
            int iHashCode2 = (iHashCode + (bool != null ? bool.hashCode() : 0)) * 31;
            String str = this.label;
            int iHashCode3 = (iHashCode2 + (str != null ? str.hashCode() : 0)) * 31;
            List<String> list2 = this.allowedMimeTypes;
            return iHashCode3 + (list2 != null ? list2.hashCode() : 0);
        }
    }

    public NotificationAction(Map<String, Object> map) {
        List<Map> list;
        this.id = (String) map.get(ID);
        this.cancelNotification = (Boolean) map.get(CANCEL_NOTIFICATION);
        this.title = (String) map.get(TITLE);
        Integer num = (Integer) map.get(TITLE_COLOR_ALPHA);
        Integer num2 = (Integer) map.get(TITLE_COLOR_RED);
        Integer num3 = (Integer) map.get(TITLE_COLOR_GREEN);
        Integer num4 = (Integer) map.get(TITLE_COLOR_BLUE);
        if (num != null && num2 != null && num3 != null && num4 != null) {
            this.titleColor = Integer.valueOf(Color.argb(num.intValue(), num2.intValue(), num3.intValue(), num4.intValue()));
        } else {
            this.titleColor = null;
        }
        this.icon = (String) map.get(ICON);
        this.contextual = (Boolean) map.get(CONTEXTUAL);
        this.showsUserInterface = (Boolean) map.get(SHOWS_USER_INTERFACE);
        this.allowGeneratedReplies = (Boolean) map.get(ALLOW_GENERATED_REPLIES);
        this.semanticAction = (Integer) map.get(SEMANTIC_ACTION);
        this.invisible = (Boolean) map.get(INVISIBLE);
        Integer num5 = (Integer) map.get(ICON_SOURCE);
        if (num5 != null) {
            this.iconSource = IconSource.values()[num5.intValue()];
        } else {
            this.iconSource = null;
        }
        if (map.get(INPUTS) == null || (list = (List) map.get(INPUTS)) == null) {
            return;
        }
        for (Map map2 : list) {
            this.actionInputs.add(new NotificationActionInput(castList(String.class, (Collection) map2.get("choices")), (Boolean) map2.get("allowFreeFormInput"), (String) map2.get(Constants.ScionAnalytics.PARAM_LABEL), castList(String.class, (Collection) map2.get("allowedMimeTypes"))));
        }
    }

    public static <T> List<T> castList(Class<? extends T> cls, Collection<?> collection) {
        if (collection == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(cls.cast(it.next()));
            } catch (ClassCastException unused) {
            }
        }
        return arrayList;
    }
}
