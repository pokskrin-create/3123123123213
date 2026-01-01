package org.apache.tika.metadata.filter;

import java.util.List;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.utils.ServiceLoaderUtils;

/* loaded from: classes4.dex */
public class DefaultMetadataFilter extends CompositeMetadataFilter {
    public DefaultMetadataFilter(ServiceLoader serviceLoader) {
        super(getDefaultFilters(serviceLoader));
    }

    public DefaultMetadataFilter(List<MetadataFilter> list) {
        super(list);
    }

    public DefaultMetadataFilter() {
        this(new ServiceLoader());
    }

    private static List<MetadataFilter> getDefaultFilters(ServiceLoader serviceLoader) {
        List<MetadataFilter> listLoadStaticServiceProviders = serviceLoader.loadStaticServiceProviders(MetadataFilter.class);
        ServiceLoaderUtils.sortLoadedClasses(listLoadStaticServiceProviders);
        return listLoadStaticServiceProviders;
    }
}
