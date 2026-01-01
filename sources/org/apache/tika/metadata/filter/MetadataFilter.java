package org.apache.tika.metadata.filter;

import java.io.IOException;
import java.io.Serializable;
import org.apache.tika.config.ConfigBase;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public abstract class MetadataFilter extends ConfigBase implements Serializable {
    public abstract void filter(Metadata metadata) throws TikaException;

    public static MetadataFilter load(Element element, boolean z) throws IOException, TikaConfigException {
        try {
            return (MetadataFilter) buildComposite("metadataFilters", CompositeMetadataFilter.class, "metadataFilter", MetadataFilter.class, element);
        } catch (TikaConfigException e) {
            if (z && e.getMessage().contains("could not find metadataFilters")) {
                return new NoOpFilter();
            }
            throw e;
        }
    }
}
