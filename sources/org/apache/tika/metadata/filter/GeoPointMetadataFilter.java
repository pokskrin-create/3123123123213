package org.apache.tika.metadata.filter;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
public class GeoPointMetadataFilter extends MetadataFilter {
    String geoPointFieldName = FirebaseAnalytics.Param.LOCATION;

    @Field
    public void setGeoPointFieldName(String str) {
        this.geoPointFieldName = str;
    }

    public String getGeoPointFieldName() {
        return this.geoPointFieldName;
    }

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        String str = metadata.get(TikaCoreProperties.LATITUDE);
        if (StringUtils.isEmpty(str)) {
            return;
        }
        String str2 = metadata.get(TikaCoreProperties.LONGITUDE);
        if (StringUtils.isEmpty(str2)) {
            return;
        }
        metadata.set(this.geoPointFieldName, str + "," + str2);
    }
}
