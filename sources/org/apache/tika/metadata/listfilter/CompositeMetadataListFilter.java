package org.apache.tika.metadata.listfilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class CompositeMetadataListFilter extends MetadataListFilter {
    private List<MetadataListFilter> filters;

    public CompositeMetadataListFilter() {
        this.filters = new ArrayList();
    }

    public CompositeMetadataListFilter(List<MetadataListFilter> list) {
        this.filters = list;
    }

    public void setFilters(List<MetadataListFilter> list) {
        this.filters.clear();
        this.filters.addAll(list);
    }

    public List<MetadataListFilter> getFilters() {
        return this.filters;
    }

    @Override // org.apache.tika.metadata.listfilter.MetadataListFilter
    public List<Metadata> filter(List<Metadata> list) throws TikaException {
        Iterator<MetadataListFilter> it = this.filters.iterator();
        while (it.hasNext()) {
            list = it.next().filter(list);
        }
        return list;
    }

    public String toString() {
        return "CompositeMetadataListFilter{filters=" + this.filters + "}";
    }
}
