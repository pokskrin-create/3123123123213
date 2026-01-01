package org.apache.tika.metadata.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public class ClearByMimeMetadataFilter extends MetadataFilter {
    private final Set<String> mimes;

    public ClearByMimeMetadataFilter() {
        this(new HashSet());
    }

    public ClearByMimeMetadataFilter(Set<String> set) {
        this.mimes = set;
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        String string = metadata.get("Content-Type");
        if (string == null) {
            return;
        }
        MediaType mediaType = MediaType.parse(string);
        if (mediaType != null) {
            string = mediaType.getBaseType().toString();
        }
        if (this.mimes.contains(string)) {
            for (String str : metadata.names()) {
                metadata.remove(str);
            }
        }
    }

    @Field
    public void setMimes(List<String> list) {
        this.mimes.addAll(list);
    }

    public List<String> getMimes() {
        return new ArrayList(this.mimes);
    }
}
