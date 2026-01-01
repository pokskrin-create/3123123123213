package org.apache.tika.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.utils.ServiceLoaderUtils;

/* loaded from: classes4.dex */
public class ServiceLoader {
    private final boolean dynamic;
    private final LoadErrorHandler handler;
    private final InitializableProblemHandler initializableProblemHandler;
    private final ClassLoader loader;
    private static final Map<Object, RankedService> SERVICES = new HashMap();
    private static final Pattern COMMENT = Pattern.compile("#.*");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static volatile ClassLoader CONTEXT_CLASS_LOADER = null;

    public ServiceLoader(ClassLoader classLoader, LoadErrorHandler loadErrorHandler, InitializableProblemHandler initializableProblemHandler, boolean z) {
        this.loader = classLoader;
        this.handler = loadErrorHandler;
        this.initializableProblemHandler = initializableProblemHandler;
        this.dynamic = z;
    }

    public ServiceLoader(ClassLoader classLoader, LoadErrorHandler loadErrorHandler, boolean z) {
        this(classLoader, loadErrorHandler, InitializableProblemHandler.WARN, z);
    }

    public ServiceLoader(ClassLoader classLoader, LoadErrorHandler loadErrorHandler) {
        this(classLoader, loadErrorHandler, false);
    }

    public ServiceLoader(ClassLoader classLoader) {
        this(classLoader, Boolean.getBoolean("org.apache.tika.service.error.warn") ? LoadErrorHandler.WARN : LoadErrorHandler.IGNORE);
    }

    public ServiceLoader() {
        this(getContextClassLoader(), Boolean.getBoolean("org.apache.tika.service.error.warn") ? LoadErrorHandler.WARN : LoadErrorHandler.IGNORE, true);
    }

    static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = CONTEXT_CLASS_LOADER;
        if (classLoader == null) {
            classLoader = ServiceLoader.class.getClassLoader();
        }
        return classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    }

    public static void setContextClassLoader(ClassLoader classLoader) {
        CONTEXT_CLASS_LOADER = classLoader;
    }

    static void addService(Object obj, Object obj2, int i) {
        Map<Object, RankedService> map = SERVICES;
        synchronized (map) {
            map.put(obj, new RankedService(obj2, i));
        }
    }

    static Object removeService(Object obj) {
        RankedService rankedServiceRemove;
        Map<Object, RankedService> map = SERVICES;
        synchronized (map) {
            rankedServiceRemove = map.remove(obj);
        }
        return rankedServiceRemove;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    public LoadErrorHandler getLoadErrorHandler() {
        return this.handler;
    }

    public InitializableProblemHandler getInitializableProblemHandler() {
        return this.initializableProblemHandler;
    }

    public InputStream getResourceAsStream(String str) {
        ClassLoader classLoader = this.loader;
        if (classLoader != null) {
            return classLoader.getResourceAsStream(str);
        }
        return null;
    }

    public ClassLoader getLoader() {
        return this.loader;
    }

    public <T> Class<? extends T> getServiceClass(Class<T> cls, String str) throws ClassNotFoundException {
        ClassLoader classLoader = this.loader;
        if (classLoader == null) {
            throw new ClassNotFoundException("Service class " + str + " is not available");
        }
        Class<? extends T> cls2 = (Class<? extends T>) Class.forName(str, true, classLoader);
        if (cls2.isInterface()) {
            throw new ClassNotFoundException("Service class " + str + " is an interface");
        }
        if (cls.isAssignableFrom(cls2)) {
            return cls2;
        }
        throw new ClassNotFoundException("Service class " + str + " does not implement " + cls.getName());
    }

    public Enumeration<URL> findServiceResources(String str) {
        try {
            return this.loader.getResources(str);
        } catch (IOException unused) {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
    }

    public <T> List<T> loadServiceProviders(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(loadDynamicServiceProviders(cls));
        arrayList.addAll(loadStaticServiceProviders(cls));
        ArrayList arrayList2 = new ArrayList();
        HashSet hashSet = new HashSet();
        for (Object obj : arrayList) {
            if (!hashSet.contains(obj.getClass().getCanonicalName())) {
                arrayList2.add(obj);
                hashSet.add(obj.getClass().getCanonicalName());
            }
        }
        return arrayList2;
    }

    public <T> List<T> loadDynamicServiceProviders(Class<T> cls) {
        ArrayList arrayList;
        if (this.dynamic) {
            Map<Object, RankedService> map = SERVICES;
            synchronized (map) {
                ArrayList<RankedService> arrayList2 = new ArrayList(map.values());
                Collections.sort(arrayList2);
                arrayList = new ArrayList(arrayList2.size());
                for (RankedService rankedService : arrayList2) {
                    if (rankedService.isInstanceOf(cls)) {
                        arrayList.add(rankedService.service);
                    }
                }
            }
            return arrayList;
        }
        return Collections.EMPTY_LIST;
    }

    protected <T> List<String> identifyStaticServiceProviders(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        if (this.loader != null) {
            String name = cls.getName();
            Iterator it = Collections.list(findServiceResources("META-INF/services/" + name)).iterator();
            while (it.hasNext()) {
                try {
                    collectServiceClassNames((URL) it.next(), arrayList);
                } catch (IOException e) {
                    this.handler.handleLoadError(name, e);
                }
            }
        }
        return arrayList;
    }

    public <T> List<T> loadStaticServiceProviders(Class<T> cls) {
        return loadStaticServiceProviders(cls, Collections.EMPTY_SET);
    }

    public <T> List<T> loadStaticServiceProviders(Class<T> cls, Collection<Class<? extends T>> collection) {
        Class<?> clsLoadClass;
        ArrayList arrayList = new ArrayList();
        if (this.loader != null) {
            for (String str : identifyStaticServiceProviders(cls)) {
                try {
                    clsLoadClass = this.loader.loadClass(str);
                } catch (Throwable th) {
                    this.handler.handleLoadError(str, th);
                }
                if (cls.isAssignableFrom(clsLoadClass)) {
                    Iterator<Class<? extends T>> it = collection.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().isAssignableFrom(clsLoadClass)) {
                                break;
                            }
                        } else {
                            Object objNewInstance = ServiceLoaderUtils.newInstance(clsLoadClass, this);
                            if (objNewInstance instanceof Initializable) {
                                ((Initializable) objNewInstance).initialize(Collections.EMPTY_MAP);
                                ((Initializable) objNewInstance).checkInitialization(this.initializableProblemHandler);
                            }
                            arrayList.add(objNewInstance);
                        }
                    }
                } else {
                    throw new TikaConfigException("Class " + str + " is not of type: " + cls);
                }
            }
        }
        return arrayList;
    }

    private void collectServiceClassNames(URL url, Collection<String> collection) throws IOException {
        InputStream inputStreamOpenStream = url.openStream();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenStream, StandardCharsets.UTF_8));
            try {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    String strReplaceAll = WHITESPACE.matcher(COMMENT.matcher(line).replaceFirst("")).replaceAll("");
                    if (strReplaceAll.length() > 0) {
                        collection.add(strReplaceAll);
                    }
                }
                bufferedReader.close();
                if (inputStreamOpenStream != null) {
                    inputStreamOpenStream.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if (inputStreamOpenStream != null) {
                try {
                    inputStreamOpenStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static class RankedService implements Comparable<RankedService> {
        private final int rank;
        private final Object service;

        public RankedService(Object obj, int i) {
            this.service = obj;
            this.rank = i;
        }

        public boolean isInstanceOf(Class<?> cls) {
            return cls.isAssignableFrom(this.service.getClass());
        }

        @Override // java.lang.Comparable
        public int compareTo(RankedService rankedService) {
            return rankedService.rank - this.rank;
        }
    }
}
