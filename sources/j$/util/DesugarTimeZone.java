package j$.util;

import j$.time.ZoneId;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class DesugarTimeZone {
    public static TimeZone getTimeZone(String str) {
        return TimeZone.getTimeZone(str);
    }

    public static TimeZone getTimeZone(ZoneId zoneId) {
        String id = zoneId.getId();
        char cCharAt = id.charAt(0);
        if (cCharAt == '+' || cCharAt == '-') {
            id = "GMT" + id;
        } else if (cCharAt == 'Z' && id.length() == 1) {
            id = "UTC";
        }
        return TimeZone.getTimeZone(id);
    }

    public static ZoneId toZoneId(TimeZone timeZone) {
        return ZoneId.of(timeZone.getID(), ZoneId.SHORT_IDS);
    }
}
