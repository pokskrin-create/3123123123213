package org.apache.tika.metadata.filter;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public class FieldNameMappingFilter extends MetadataFilter {
    Map<String, String> mappings = new LinkedHashMap();
    boolean excludeUnmapped = true;

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        if (this.excludeUnmapped) {
            for (String str : metadata.names()) {
                if (this.mappings.containsKey(str)) {
                    String[] values = metadata.getValues(str);
                    metadata.remove(str);
                    for (String str2 : values) {
                        metadata.add(this.mappings.get(str), str2);
                    }
                } else {
                    metadata.remove(str);
                }
            }
            return;
        }
        for (String str3 : metadata.names()) {
            if (this.mappings.containsKey(str3)) {
                String[] values2 = metadata.getValues(str3);
                metadata.remove(str3);
                for (String str4 : values2) {
                    metadata.add(this.mappings.get(str3), str4);
                }
            }
        }
    }

    @Field
    public void setExcludeUnmapped(boolean z) {
        this.excludeUnmapped = z;
    }

    @Field
    public void setMappings(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.mappings.put(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, String> getMappins() {
        return this.mappings;
    }

    public String toString() {
        return "FieldNameMappingFilter{mappings=" + this.mappings + ", excludeUnmapped=" + this.excludeUnmapped + "}";
    }
}
