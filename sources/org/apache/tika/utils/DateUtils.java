package org.apache.tika.utils;

import j$.time.temporal.ChronoUnit;
import j$.util.DesugarCalendar;
import j$.util.DesugarTimeZone;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class DateUtils {
    private final List<DateFormat> iso8601InputFormats = loadDateFormats();
    public static final TimeZone UTC = DesugarTimeZone.getTimeZone("UTC");
    public static final TimeZone MIDDAY = DesugarTimeZone.getTimeZone("GMT-12:00");

    private static DateFormat createDateFormat(String str, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, new DateFormatSymbols(Locale.US));
        if (timeZone != null) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        return simpleDateFormat;
    }

    public static String formatDate(Date date) {
        Calendar gregorianCalendar = GregorianCalendar.getInstance(UTC, Locale.US);
        gregorianCalendar.setTime(date);
        return doFormatDate(gregorianCalendar);
    }

    public static String formatDate(Calendar calendar) {
        return doFormatDate(calendar);
    }

    public static String formatDateUnknownTimezone(Date date) {
        Calendar gregorianCalendar = GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.US);
        gregorianCalendar.setTime(date);
        return formatDate(gregorianCalendar).substring(0, r2.length() - 1);
    }

    private static String doFormatDate(Calendar calendar) {
        return DesugarCalendar.toInstant(calendar).truncatedTo(ChronoUnit.SECONDS).toString();
    }

    private List<DateFormat> loadDateFormats() {
        ArrayList arrayList = new ArrayList();
        TimeZone timeZone = UTC;
        arrayList.add(createDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", timeZone));
        arrayList.add(createDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", null));
        arrayList.add(createDateFormat("yyyy-MM-dd'T'HH:mm:ss", null));
        arrayList.add(createDateFormat("yyyy-MM-dd' 'HH:mm:ss'Z'", timeZone));
        arrayList.add(createDateFormat("yyyy-MM-dd' 'HH:mm:ssZ", null));
        arrayList.add(createDateFormat("yyyy-MM-dd' 'HH:mm:ss", null));
        TimeZone timeZone2 = MIDDAY;
        arrayList.add(createDateFormat("yyyy-MM-dd", timeZone2));
        arrayList.add(createDateFormat("yyyy:MM:dd", timeZone2));
        return arrayList;
    }

    public Date tryToParse(String str) {
        int length = str.length();
        int i = length - 3;
        if (str.charAt(i) == ':') {
            int i2 = length - 6;
            if (str.charAt(i2) == '+' || str.charAt(i2) == '-') {
                str = str.substring(0, i) + str.substring(length - 2);
            }
        }
        Iterator<DateFormat> it = this.iso8601InputFormats.iterator();
        while (it.hasNext()) {
            try {
                return it.next().parse(str);
            } catch (ParseException unused) {
            }
        }
        return null;
    }
}
