package org.apache.tika.io;

import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.apache.tika.metadata.TikaCoreProperties;

/* loaded from: classes4.dex */
public class FilenameUtils {
    private static final Pattern ASCII_NUMERIC;
    private static final HashSet<Character> RESERVED;
    public static final char[] RESERVED_FILENAME_CHARACTERS;

    static {
        char[] cArr = {0, 1, 2, 3, 4, 5, 6, 7, '\b', '\t', '\n', 11, '\f', '\r', 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, '?', ':', '*', Typography.less, Typography.greater, '|'};
        RESERVED_FILENAME_CHARACTERS = cArr;
        RESERVED = new HashSet<>(38);
        for (char c : cArr) {
            RESERVED.add(Character.valueOf(c));
        }
        ASCII_NUMERIC = Pattern.compile("\\A\\.(?i)[a-z0-9]{1,5}\\Z");
    }

    public static String normalize(String str) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            if (RESERVED.contains(Character.valueOf(c))) {
                sb.append('%');
                sb.append(c < 16 ? "0" : "");
                sb.append(Integer.toHexString(c).toUpperCase(Locale.ROOT));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getName(String str) {
        if (str != null && !str.isEmpty()) {
            String strSubstring = str.substring(Math.max(str.lastIndexOf(TikaCoreProperties.NAMESPACE_PREFIX_DELIMITER), Math.max(str.lastIndexOf("/"), str.lastIndexOf("\\"))) + 1);
            if (!strSubstring.equals("..") && !strSubstring.equals(".")) {
                return strSubstring;
            }
        }
        return "";
    }

    public static String getSuffixFromPath(String str) {
        String name = getName(str);
        int iLastIndexOf = name.lastIndexOf(".");
        if (iLastIndexOf > -1 && name.length() - iLastIndexOf < 6) {
            String strSubstring = name.substring(iLastIndexOf);
            return ASCII_NUMERIC.matcher(strSubstring).matches() ? strSubstring : "";
        }
        return "";
    }
}
