package org.apache.tika.detect;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.utils.ServiceLoaderUtils;

/* loaded from: classes4.dex */
public class DefaultDetector extends CompositeDetector {
    private static final long serialVersionUID = -8170114575326908027L;
    private final transient ServiceLoader loader;

    public DefaultDetector(MimeTypes mimeTypes, ServiceLoader serviceLoader, Collection<Class<? extends Detector>> collection) {
        super(mimeTypes.getMediaTypeRegistry(), getDefaultDetectors(mimeTypes, serviceLoader, collection));
        this.loader = serviceLoader;
    }

    public DefaultDetector(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
        this(mimeTypes, serviceLoader, Collections.EMPTY_SET);
    }

    public DefaultDetector(MimeTypes mimeTypes, ClassLoader classLoader) {
        this(mimeTypes, new ServiceLoader(classLoader));
    }

    public DefaultDetector(ClassLoader classLoader) {
        this(MimeTypes.getDefaultMimeTypes(), classLoader);
    }

    public DefaultDetector(MimeTypes mimeTypes) {
        this(mimeTypes, new ServiceLoader());
    }

    public DefaultDetector() {
        this(MimeTypes.getDefaultMimeTypes());
    }

    private static List<Detector> getDefaultDetectors(MimeTypes mimeTypes, ServiceLoader serviceLoader, Collection<Class<? extends Detector>> collection) {
        List<Detector> listLoadStaticServiceProviders = serviceLoader.loadStaticServiceProviders(Detector.class, collection);
        ServiceLoaderUtils.sortLoadedClasses(listLoadStaticServiceProviders);
        Iterator<Detector> it = listLoadStaticServiceProviders.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (it.next() instanceof OverrideDetector) {
                break;
            }
            i++;
        }
        if (i > -1) {
            listLoadStaticServiceProviders.add(0, listLoadStaticServiceProviders.remove(i));
        }
        listLoadStaticServiceProviders.add(mimeTypes);
        return listLoadStaticServiceProviders;
    }

    @Override // org.apache.tika.detect.CompositeDetector
    public List<Detector> getDetectors() {
        ServiceLoader serviceLoader = this.loader;
        if (serviceLoader != null && serviceLoader.isDynamic()) {
            List<Detector> listLoadDynamicServiceProviders = this.loader.loadDynamicServiceProviders(Detector.class);
            if (listLoadDynamicServiceProviders.size() > 0) {
                listLoadDynamicServiceProviders.addAll(super.getDetectors());
                return listLoadDynamicServiceProviders;
            }
            return super.getDetectors();
        }
        return super.getDetectors();
    }
}
