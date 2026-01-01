package org.apache.tika.metadata.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class ExcludeFieldMetadataFilter extends MetadataFilter {
    private final Set<String> excludeSet;

    public ExcludeFieldMetadataFilter() {
        this(new HashSet());
    }

    public ExcludeFieldMetadataFilter(Set<String> set) {
        this.excludeSet = set;
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        Iterator<String> it = this.excludeSet.iterator();
        while (it.hasNext()) {
            metadata.remove(it.next());
        }
    }

    @Field
    public void setExclude(List<String> list) {
        this.excludeSet.addAll(list);
    }

    public List<String> getExclude() {
        return new ArrayList(this.excludeSet);
    }
}
