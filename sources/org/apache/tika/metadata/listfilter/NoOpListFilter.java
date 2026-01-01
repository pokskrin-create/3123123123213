package org.apache.tika.metadata.listfilter;

import java.util.List;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class NoOpListFilter extends MetadataListFilter {
    @Override // org.apache.tika.metadata.listfilter.MetadataListFilter
    public List<Metadata> filter(List<Metadata> list) {
        return list;
    }
}
