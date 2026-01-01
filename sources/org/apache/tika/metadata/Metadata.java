package org.apache.tika.metadata;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.writefilter.MetadataWriteFilter;
import org.apache.tika.utils.DateUtils;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
public class Metadata implements CreativeCommons, Geographic, HttpHeaders, Message, ClimateForcast, TIFF, TikaMimeKeys, Serializable {
    private static final MetadataWriteFilter ACCEPT_ALL = new MetadataWriteFilter() { // from class: org.apache.tika.metadata.Metadata.1
        @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
        public void filterExisting(Map<String, String[]> map) {
        }

        @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
        public void add(String str, String str2, Map<String, String[]> map) {
            String[] strArr = map.get(str);
            if (strArr == null) {
                set(str, str2, map);
            } else {
                map.put(str, appendValues(strArr, str2));
            }
        }

        @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
        public void set(String str, String str2, Map<String, String[]> map) {
            if (str2 != null) {
                map.put(str, new String[]{str2});
            } else {
                map.remove(str);
            }
        }

        private String[] appendValues(String[] strArr, String str) {
            if (str == null) {
                return strArr;
            }
            int length = strArr.length;
            String[] strArr2 = new String[length + 1];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            strArr2[length] = str;
            return strArr2;
        }
    };
    private static final DateUtils DATE_UTILS = new DateUtils();
    private static final long serialVersionUID = 5623926545693153182L;
    private Map<String, String[]> metadata;
    private MetadataWriteFilter writeFilter = ACCEPT_ALL;

    public Metadata() {
        this.metadata = null;
        this.metadata = new HashMap();
    }

    private static DateFormat createDateFormat(String str, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, new DateFormatSymbols(Locale.US));
        if (timeZone != null) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        return simpleDateFormat;
    }

    private static synchronized Date parseDate(String str) {
        return DATE_UTILS.tryToParse(str);
    }

    public boolean isMultiValued(Property property) {
        return this.metadata.get(property.getName()) != null && this.metadata.get(property.getName()).length > 1;
    }

    public boolean isMultiValued(String str) {
        return this.metadata.get(str) != null && this.metadata.get(str).length > 1;
    }

    public String[] names() {
        return (String[]) this.metadata.keySet().toArray(new String[0]);
    }

    public String get(String str) {
        String[] strArr = this.metadata.get(str);
        if (strArr == null) {
            return null;
        }
        return strArr[0];
    }

    public void setMetadataWriteFilter(MetadataWriteFilter metadataWriteFilter) {
        this.writeFilter = metadataWriteFilter;
        metadataWriteFilter.filterExisting(this.metadata);
    }

    public String get(Property property) {
        return get(property.getName());
    }

    public Integer getInt(Property property) {
        String str;
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE || property.getPrimaryProperty().getValueType() != Property.ValueType.INTEGER || (str = get(property)) == null) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public Date getDate(Property property) {
        String str;
        if (property.getPrimaryProperty().getPropertyType() == Property.PropertyType.SIMPLE && property.getPrimaryProperty().getValueType() == Property.ValueType.DATE && (str = get(property)) != null) {
            return parseDate(str);
        }
        return null;
    }

    public String[] getValues(Property property) {
        return _getValues(property.getName());
    }

    public String[] getValues(String str) {
        return _getValues(str);
    }

    private String[] _getValues(String str) {
        String[] strArr = this.metadata.get(str);
        return strArr == null ? new String[0] : strArr;
    }

    public void add(String str, String str2) {
        this.writeFilter.add(str, str2, this.metadata);
    }

    protected void add(String str, String[] strArr) {
        if (this.metadata.get(str) == null) {
            set(str, strArr);
            return;
        }
        for (String str2 : strArr) {
            add(str, str2);
        }
    }

    public void add(Property property, String str) {
        if (property == null) {
            throw new NullPointerException("property must not be null");
        }
        if (property.getPropertyType() == Property.PropertyType.COMPOSITE) {
            add(property.getPrimaryProperty(), str);
            if (property.getSecondaryExtractProperties() != null) {
                for (Property property2 : property.getSecondaryExtractProperties()) {
                    add(property2, str);
                }
                return;
            }
            return;
        }
        if (this.metadata.get(property.getName()) == null) {
            set(property, str);
            return;
        }
        if (property.isMultiValuePermitted()) {
            add(property.getName(), str);
            return;
        }
        throw new PropertyTypeException(property.getName() + " : " + property.getPropertyType());
    }

    public void setAll(Properties properties) {
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String str = (String) enumerationPropertyNames.nextElement();
            this.metadata.put(str, new String[]{properties.getProperty(str)});
        }
    }

    public void set(String str, String str2) {
        this.writeFilter.set(str, str2, this.metadata);
    }

    protected void set(String str, String[] strArr) {
        if (strArr != null) {
            this.metadata.remove(str);
            for (String str2 : strArr) {
                add(str, str2);
            }
            return;
        }
        this.metadata.remove(str);
    }

    public void set(Property property, String str) {
        if (property == null) {
            throw new NullPointerException("property must not be null");
        }
        if (property.getPropertyType() == Property.PropertyType.COMPOSITE) {
            set(property.getPrimaryProperty(), str);
            if (property.getSecondaryExtractProperties() != null) {
                for (Property property2 : property.getSecondaryExtractProperties()) {
                    set(property2, str);
                }
                return;
            }
            return;
        }
        set(property.getName(), str);
    }

    public void set(Property property, String[] strArr) {
        if (property == null) {
            throw new NullPointerException("property must not be null");
        }
        if (property.getPropertyType() == Property.PropertyType.COMPOSITE) {
            set(property.getPrimaryProperty(), strArr);
            if (property.getSecondaryExtractProperties() != null) {
                for (Property property2 : property.getSecondaryExtractProperties()) {
                    set(property2, strArr);
                }
                return;
            }
            return;
        }
        set(property.getName(), strArr);
    }

    public void set(Property property, int i) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE) {
            throw new PropertyTypeException(Property.PropertyType.SIMPLE, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.INTEGER) {
            throw new PropertyTypeException(Property.ValueType.INTEGER, property.getPrimaryProperty().getValueType());
        }
        set(property, Integer.toString(i));
    }

    public void set(Property property, long j) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE) {
            throw new PropertyTypeException(Property.PropertyType.SIMPLE, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.REAL) {
            throw new PropertyTypeException(Property.ValueType.REAL, property.getPrimaryProperty().getValueType());
        }
        set(property, Long.toString(j));
    }

    public void set(Property property, boolean z) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE) {
            throw new PropertyTypeException(Property.PropertyType.SIMPLE, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.BOOLEAN) {
            throw new PropertyTypeException(Property.ValueType.BOOLEAN, property.getPrimaryProperty().getValueType());
        }
        set(property, Boolean.toString(z));
    }

    public void add(Property property, int i) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SEQ) {
            throw new PropertyTypeException(Property.PropertyType.SEQ, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.INTEGER) {
            throw new PropertyTypeException(Property.ValueType.INTEGER, property.getPrimaryProperty().getValueType());
        }
        add(property, Integer.toString(i));
    }

    public int[] getIntValues(Property property) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SEQ) {
            throw new PropertyTypeException(Property.PropertyType.SEQ, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.INTEGER) {
            throw new PropertyTypeException(Property.ValueType.INTEGER, property.getPrimaryProperty().getValueType());
        }
        String[] values = getValues(property);
        int[] iArr = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            iArr[i] = Integer.parseInt(values[i]);
        }
        return iArr;
    }

    public long[] getLongValues(Property property) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SEQ) {
            throw new PropertyTypeException(Property.PropertyType.SEQ, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.REAL) {
            throw new PropertyTypeException(Property.ValueType.REAL, property.getPrimaryProperty().getValueType());
        }
        String[] values = getValues(property);
        long[] jArr = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            jArr[i] = Long.parseLong(values[i]);
        }
        return jArr;
    }

    public void set(Property property, double d) {
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.REAL && property.getPrimaryProperty().getValueType() != Property.ValueType.RATIONAL) {
            throw new PropertyTypeException(Property.ValueType.REAL, property.getPrimaryProperty().getValueType());
        }
        set(property, Double.toString(d));
    }

    public void set(Property property, Date date) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE) {
            throw new PropertyTypeException(Property.PropertyType.SIMPLE, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.DATE) {
            throw new PropertyTypeException(Property.ValueType.DATE, property.getPrimaryProperty().getValueType());
        }
        set(property, date != null ? DateUtils.formatDate(date) : null);
    }

    public void set(Property property, Calendar calendar) {
        if (property.getPrimaryProperty().getPropertyType() != Property.PropertyType.SIMPLE) {
            throw new PropertyTypeException(Property.PropertyType.SIMPLE, property.getPrimaryProperty().getPropertyType());
        }
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.DATE) {
            throw new PropertyTypeException(Property.ValueType.DATE, property.getPrimaryProperty().getValueType());
        }
        set(property, calendar != null ? DateUtils.formatDate(calendar) : null);
    }

    public void add(Property property, Calendar calendar) {
        if (property.getPrimaryProperty().getValueType() != Property.ValueType.DATE) {
            throw new PropertyTypeException(Property.ValueType.DATE, property.getPrimaryProperty().getValueType());
        }
        add(property, calendar != null ? DateUtils.formatDate(calendar) : null);
    }

    public void remove(String str) {
        this.metadata.remove(str);
    }

    public int size() {
        return this.metadata.size();
    }

    public int hashCode() {
        Iterator<Map.Entry<String, String[]>> it = this.metadata.entrySet().iterator();
        int metadataEntryHashCode = 0;
        while (it.hasNext()) {
            metadataEntryHashCode += getMetadataEntryHashCode(it.next());
        }
        return metadataEntryHashCode;
    }

    private int getMetadataEntryHashCode(Map.Entry<String, String[]> entry) {
        return Arrays.hashCode(entry.getValue()) ^ Objects.hashCode(entry.getKey());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Metadata)) {
            return false;
        }
        Metadata metadata = (Metadata) obj;
        if (metadata.size() != size()) {
            return false;
        }
        for (String str : names()) {
            String[] strArr_getValues = metadata._getValues(str);
            String[] strArr_getValues2 = _getValues(str);
            if (strArr_getValues.length != strArr_getValues2.length) {
                return false;
            }
            for (int i = 0; i < strArr_getValues.length; i++) {
                if (!strArr_getValues[i].equals(strArr_getValues2[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : names()) {
            for (String str2 : _getValues(str)) {
                if (sb.length() > 0) {
                    sb.append(StringUtils.SPACE);
                }
                sb.append(str);
                sb.append("=");
                sb.append(str2);
            }
        }
        return sb.toString();
    }
}
