package org.apache.tika.metadata.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class IncludeFieldMetadataFilter extends MetadataFilter {
    private final Set<String> includeSet;

    public IncludeFieldMetadataFilter() {
        this(new HashSet());
    }

    public IncludeFieldMetadataFilter(Set<String> set) {
        this.includeSet = set;
    }

    @Field
    public void setInclude(List<String> list) {
        this.includeSet.addAll(list);
    }

    public List<String> getInclude() {
        return new ArrayList(this.includeSet);
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        for (String str : metadata.names()) {
            if (!this.includeSet.contains(str)) {
                metadata.remove(str);
            }
        }
    }
}
