package org.apache.tika.utils;

import java.util.List;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* loaded from: classes4.dex */
public class StringUtils {
    public static final String EMPTY = "";
    static int PAD_LIMIT = 10000;
    public static final String SPACE = " ";

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isBlank(String str) {
        return str == null || UByte$$ExternalSyntheticBackport0.m(str);
    }

    public static String leftPad(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = SPACE;
        }
        int length = str2.length();
        int length2 = i - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= PAD_LIMIT) {
            return leftPad(str, i, str2.charAt(0));
        }
        if (length2 == length) {
            return str2.concat(str);
        }
        if (length2 < length) {
            return str2.substring(0, length2).concat(str);
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i2 = 0; i2 < length2; i2++) {
            cArr[i2] = charArray[i2 % length];
        }
        return new String(cArr).concat(str);
    }

    public static String leftPad(String str, int i, char c) {
        if (str == null) {
            return null;
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        if (length > PAD_LIMIT) {
            return leftPad(str, i, String.valueOf(c));
        }
        return repeat(c, length).concat(str);
    }

    public static String repeat(char c, int i) {
        if (i <= 0) {
            return "";
        }
        char[] cArr = new char[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            cArr[i2] = c;
        }
        return new String(cArr);
    }

    public static String repeat(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i <= 0) {
            return "";
        }
        int length = str.length();
        if (i == 1 || length == 0) {
            return str;
        }
        if (length == 1 && i <= PAD_LIMIT) {
            return repeat(str.charAt(0), i);
        }
        int i2 = length * i;
        if (length == 1) {
            return repeat(str.charAt(0), i);
        }
        if (length == 2) {
            char cCharAt = str.charAt(0);
            char cCharAt2 = str.charAt(1);
            char[] cArr = new char[i2];
            for (int i3 = (i * 2) - 2; i3 >= 0; i3 -= 2) {
                cArr[i3] = cCharAt;
                cArr[i3 + 1] = cCharAt2;
            }
            return new String(cArr);
        }
        StringBuilder sb = new StringBuilder(i2);
        for (int i4 = 0; i4 < i; i4++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String joinWith(String str, List<String> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str2 : list) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(str);
            }
            sb.append(str2);
            i = i2;
        }
        return sb.toString();
    }
}
