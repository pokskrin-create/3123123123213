package org.apache.tika.metadata.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class CompositeMetadataFilter extends MetadataFilter {
    private List<MetadataFilter> filters;

    public CompositeMetadataFilter() {
        this.filters = new ArrayList();
    }

    public CompositeMetadataFilter(List<MetadataFilter> list) {
        this.filters = list;
    }

    public void setFilters(List<MetadataFilter> list) {
        this.filters.clear();
        this.filters.addAll(list);
    }

    public List<MetadataFilter> getFilters() {
        return this.filters;
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        Iterator<MetadataFilter> it = this.filters.iterator();
        while (it.hasNext()) {
            it.next().filter(metadata);
        }
    }

    public String toString() {
        return "CompositeMetadataFilter{filters=" + this.filters + "}";
    }
}
