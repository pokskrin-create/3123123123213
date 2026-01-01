package org.apache.tika.utils;

/* loaded from: classes4.dex */
public class CompareUtils {
    public static int compareClassName(Object obj, Object obj2) {
        String name = obj.getClass().getName();
        String name2 = obj2.getClass().getName();
        boolean zStartsWith = name.startsWith("org.apache.tika.");
        if (zStartsWith == name2.startsWith("org.apache.tika.")) {
            return name.compareTo(name2);
        }
        return zStartsWith ? 1 : -1;
    }
}
