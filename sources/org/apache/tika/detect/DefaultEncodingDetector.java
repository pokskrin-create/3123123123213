package org.apache.tika.detect;

import java.util.Collection;
import org.apache.tika.config.ServiceLoader;

/* loaded from: classes4.dex */
public class DefaultEncodingDetector extends CompositeEncodingDetector {
    public DefaultEncodingDetector() {
        this(new ServiceLoader(DefaultEncodingDetector.class.getClassLoader()));
    }

    public DefaultEncodingDetector(ServiceLoader serviceLoader) {
        super(serviceLoader.loadServiceProviders(EncodingDetector.class));
    }

    public DefaultEncodingDetector(ServiceLoader serviceLoader, Collection<Class<? extends EncodingDetector>> collection) {
        super(serviceLoader.loadServiceProviders(EncodingDetector.class), collection);
    }
}
