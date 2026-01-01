package org.apache.tika.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import org.apache.tika.config.ServiceLoader;

/* loaded from: classes4.dex */
public class ServiceLoaderUtils {
    public static <T> void sortLoadedClasses(List<T> list) {
        list.sort(new Comparator() { // from class: org.apache.tika.utils.ServiceLoaderUtils$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtils.compareClassName(obj, obj2);
            }
        });
    }

    public static <T> T newInstance(String str) {
        return (T) newInstance(str, ServiceLoader.class.getClassLoader());
    }

    public static <T> T newInstance(String str, ClassLoader classLoader) {
        try {
            return (T) Class.forName(str, true, classLoader).getDeclaredConstructor(null).newInstance(null);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class cls, ServiceLoader serviceLoader) {
        try {
            try {
                return cls.getDeclaredConstructor(ServiceLoader.class).newInstance(serviceLoader);
            } catch (NoSuchMethodException unused) {
                return cls.getDeclaredConstructor(null).newInstance(null);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }
}
