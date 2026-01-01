package org.apache.tika.metadata.filter;

import j$.time.ZoneId;
import j$.util.DesugarTimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class DateNormalizingMetadataFilter extends MetadataFilter {
    private TimeZone defaultTimeZone = UTC;
    private static TimeZone UTC = DesugarTimeZone.getTimeZone("UTC");
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) DateNormalizingMetadataFilter.class);

    @Override // org.apache.tika.metadata.filter.MetadataFilter
    public void filter(Metadata metadata) throws TikaException {
        SimpleDateFormat simpleDateFormat = null;
        SimpleDateFormat simpleDateFormat2 = null;
        for (String str : metadata.names()) {
            Property property = Property.get(str);
            if (property != null && property.getValueType().equals(Property.ValueType.DATE)) {
                String str2 = metadata.get(property);
                if (!str2.endsWith("Z")) {
                    if (simpleDateFormat == null) {
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        simpleDateFormat.setTimeZone(this.defaultTimeZone);
                        simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                        simpleDateFormat2.setTimeZone(UTC);
                    }
                    try {
                        metadata.set(property, simpleDateFormat2.format(simpleDateFormat.parse(str2)));
                    } catch (ParseException unused) {
                        LOGGER.warn("Couldn't convert date to default time zone: >" + str2 + "<");
                    }
                }
            }
        }
    }

    @Field
    public void setDefaultTimeZone(String str) {
        this.defaultTimeZone = DesugarTimeZone.getTimeZone(ZoneId.of(str));
    }

    public String getDefaultTimeZone() {
        return DesugarTimeZone.toZoneId(this.defaultTimeZone).toString();
    }
}
