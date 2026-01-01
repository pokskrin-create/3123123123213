package org.apache.tika.metadata.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;

/* loaded from: classes4.dex */
public class ClearByAttachmentTypeMetadataFilter extends MetadataFilter {
    private final Set<String> types;

    public ClearByAttachmentTypeMetadataFilter() {
        this(new HashSet());
    }

    public ClearByAttachmentTypeMetadataFilter(Set<String> set) {
        this.types = set;
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        String str = metadata.get(TikaCoreProperties.EMBEDDED_RESOURCE_TYPE);
        if (str != null && this.types.contains(str)) {
            for (String str2 : metadata.names()) {
                metadata.remove(str2);
            }
        }
    }

    @Field
    public void setTypes(List<String> list) throws TikaConfigException {
        for (String str : list) {
            try {
                TikaCoreProperties.EmbeddedResourceType.valueOf(str);
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder();
                TikaCoreProperties.EmbeddedResourceType[] embeddedResourceTypeArrValues = TikaCoreProperties.EmbeddedResourceType.values();
                int length = embeddedResourceTypeArrValues.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    TikaCoreProperties.EmbeddedResourceType embeddedResourceType = embeddedResourceTypeArrValues[i];
                    int i3 = i2 + 1;
                    if (i2 > 0) {
                        sb.append(", ");
                    }
                    sb.append(embeddedResourceType.name());
                    i++;
                    i2 = i3;
                }
                throw new TikaConfigException("I'm sorry. I regret I don't recognise " + str + ". I do recognize the following (case-sensitive):" + sb.toString());
            }
        }
        this.types.addAll(list);
    }

    public List<String> getTypes() {
        return new ArrayList(this.types);
    }
}
