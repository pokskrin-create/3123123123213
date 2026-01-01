package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import androidx.work.Logger;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes.dex */
public class PackageManagerHelper {
    private static final String TAG = Logger.tagWithPrefix("PackageManagerHelper");

    private static boolean isComponentEnabled(int setting, boolean defaults) {
        return setting == 0 ? defaults : setting == 1;
    }

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(Context context, Class<?> klazz, boolean enabled) {
        try {
            if (enabled == isComponentEnabled(getComponentEnabledSetting(context, klazz.getName()), false)) {
                Logger.get().debug(TAG, "Skipping component enablement for " + klazz.getName());
                return;
            }
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, klazz.getName()), enabled ? 1 : 2, 1);
            Logger logger = Logger.get();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(klazz.getName());
            sb.append(StringUtils.SPACE);
            sb.append(enabled ? "enabled" : "disabled");
            logger.debug(str, sb.toString());
        } catch (Exception e) {
            Logger logger2 = Logger.get();
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(klazz.getName());
            sb2.append("could not be ");
            sb2.append(enabled ? "enabled" : "disabled");
            logger2.debug(str2, sb2.toString(), e);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> klazz) {
        return isComponentEnabled(getComponentEnabledSetting(context, klazz.getName()), false);
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String className) {
        return getComponentEnabledSetting(context, className) == 1;
    }

    private static int getComponentEnabledSetting(Context context, String className) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, className));
    }
}
