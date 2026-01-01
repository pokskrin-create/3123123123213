package org.apache.tika.detect;

import java.util.List;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.mime.ProbabilisticMimeDetectionSelector;
import org.apache.tika.utils.ServiceLoaderUtils;

/* loaded from: classes4.dex */
public class DefaultProbDetector extends CompositeDetector {
    private static final long serialVersionUID = -8836240060532323352L;
    private final transient ServiceLoader loader;

    public DefaultProbDetector(ProbabilisticMimeDetectionSelector probabilisticMimeDetectionSelector, ServiceLoader serviceLoader) {
        super(probabilisticMimeDetectionSelector.getMediaTypeRegistry(), getDefaultDetectors(probabilisticMimeDetectionSelector, serviceLoader));
        this.loader = serviceLoader;
    }

    public DefaultProbDetector(ProbabilisticMimeDetectionSelector probabilisticMimeDetectionSelector, ClassLoader classLoader) {
        this(probabilisticMimeDetectionSelector, new ServiceLoader(classLoader));
    }

    public DefaultProbDetector(ClassLoader classLoader) {
        this(new ProbabilisticMimeDetectionSelector(), classLoader);
    }

    public DefaultProbDetector(MimeTypes mimeTypes) {
        this(new ProbabilisticMimeDetectionSelector(mimeTypes), new ServiceLoader());
    }

    public DefaultProbDetector() {
        this(MimeTypes.getDefaultMimeTypes());
    }

    private static List<Detector> getDefaultDetectors(ProbabilisticMimeDetectionSelector probabilisticMimeDetectionSelector, ServiceLoader serviceLoader) {
        List<Detector> listLoadStaticServiceProviders = serviceLoader.loadStaticServiceProviders(Detector.class);
        ServiceLoaderUtils.sortLoadedClasses(listLoadStaticServiceProviders);
        listLoadStaticServiceProviders.add(probabilisticMimeDetectionSelector);
        return listLoadStaticServiceProviders;
    }

    @Override // org.apache.tika.detect.CompositeDetector
    public List<Detector> getDetectors() {
        ServiceLoader serviceLoader = this.loader;
        if (serviceLoader != null) {
            List<Detector> listLoadDynamicServiceProviders = serviceLoader.loadDynamicServiceProviders(Detector.class);
            listLoadDynamicServiceProviders.addAll(super.getDetectors());
            return listLoadDynamicServiceProviders;
        }
        return super.getDetectors();
    }
}
