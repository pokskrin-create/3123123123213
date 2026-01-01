package org.apache.tika.metadata.writefilter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.tika.metadata.AccessPermissions;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
public class StandardWriteFilter implements MetadataWriteFilter, Serializable {
    public static final Set<String> ALWAYS_ADD_FIELDS;
    public static final Set<String> ALWAYS_SET_FIELDS;
    private static final String METADATA_TRUNCATED_KEY;
    private static final String TIKA_CONTENT_KEY;
    private static final String[] TRUE;
    private final Set<String> excludeFields;
    private final boolean includeEmpty;
    private final Set<String> includeFields;
    private final int maxFieldSize;
    private final int maxKeySize;
    private final int maxTotalEstimatedSize;
    private final int maxValuesPerField;
    private final int minimumMaxFieldSizeInAlwaysFields = 300;
    private Map<String, Integer> fieldSizes = new HashMap();
    int estimatedSize = 0;

    static {
        HashSet hashSet = new HashSet();
        ALWAYS_SET_FIELDS = hashSet;
        HashSet hashSet2 = new HashSet();
        ALWAYS_ADD_FIELDS = hashSet2;
        hashSet.add("Content-Length");
        hashSet.add("Content-Type");
        hashSet.add("Content-Encoding");
        hashSet.add(TikaCoreProperties.CONTENT_TYPE_USER_OVERRIDE.getName());
        hashSet.add(TikaCoreProperties.CONTENT_TYPE_PARSER_OVERRIDE.getName());
        hashSet.add(TikaCoreProperties.CONTENT_TYPE_HINT.getName());
        hashSet.add(TikaCoreProperties.TIKA_CONTENT.getName());
        hashSet.add(TikaCoreProperties.RESOURCE_NAME_KEY);
        hashSet.add(AccessPermissions.EXTRACT_CONTENT.getName());
        hashSet.add(AccessPermissions.EXTRACT_FOR_ACCESSIBILITY.getName());
        hashSet.add("Content-Disposition");
        hashSet.add(TikaCoreProperties.CONTAINER_EXCEPTION.getName());
        hashSet.add(TikaCoreProperties.EMBEDDED_EXCEPTION.getName());
        hashSet2.add(TikaCoreProperties.TIKA_PARSED_BY.getName());
        METADATA_TRUNCATED_KEY = TikaCoreProperties.TRUNCATED_METADATA.getName();
        TIKA_CONTENT_KEY = TikaCoreProperties.TIKA_CONTENT.getName();
        TRUE = new String[]{"true"};
    }

    protected StandardWriteFilter(int i, int i2, int i3, int i4, Set<String> set, Set<String> set2, boolean z) {
        this.maxKeySize = i;
        this.maxFieldSize = i2;
        this.maxTotalEstimatedSize = i3;
        this.maxValuesPerField = i4;
        this.includeFields = set;
        this.excludeFields = set2;
        this.includeEmpty = z;
    }

    @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
    public void filterExisting(Map<String, String[]> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if (includeField(key)) {
                for (String str : value) {
                    if (include(key, str)) {
                        add(key, str, map2);
                    }
                }
            }
        }
        map.clear();
        map.putAll(map2);
    }

    @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
    public void set(String str, String str2, Map<String, String[]> map) {
        if (include(str, str2)) {
            if (ALWAYS_SET_FIELDS.contains(str) || ALWAYS_ADD_FIELDS.contains(str)) {
                setAlwaysInclude(str, str2, map);
            } else {
                setFilterKey(filterKey(str, str2, map), str2, map);
            }
        }
    }

    private void setAlwaysInclude(String str, String str2, Map<String, String[]> map) {
        if (TIKA_CONTENT_KEY.equals(str)) {
            map.put(str, new String[]{str2});
            return;
        }
        int iEstimateSize = estimateSize(str2);
        int iMax = Math.max(300, this.maxFieldSize);
        if (iEstimateSize > iMax) {
            str2 = truncate(str2, iMax, map);
            iEstimateSize = estimateSize(str2);
        }
        int iEstimateSize2 = (map.containsKey(str) ? 0 : estimateSize(str)) + iEstimateSize;
        if (map.containsKey(str)) {
            String[] strArr = map.get(str);
            if (strArr.length > 0) {
                iEstimateSize2 -= estimateSize(strArr[0]);
            }
        }
        this.estimatedSize += iEstimateSize2;
        map.put(str, new String[]{str2});
    }

    private void addAlwaysInclude(String str, String str2, Map<String, String[]> map) {
        if (TIKA_CONTENT_KEY.equals(str)) {
            map.put(str, new String[]{str2});
            return;
        }
        if (!map.containsKey(str)) {
            setAlwaysInclude(str, str2, map);
            return;
        }
        int iEstimateSize = estimateSize(str2);
        int iMax = Math.max(300, this.maxFieldSize);
        if (iEstimateSize > iMax) {
            str2 = truncate(str2, iMax, map);
            iEstimateSize = estimateSize(str2);
        }
        this.estimatedSize += (map.containsKey(str) ? 0 : estimateSize(str)) + iEstimateSize;
        map.put(str, appendValue(map.get(str), str2));
    }

    private int maxAllowedToSet(StringSizePair stringSizePair) {
        Integer num = this.fieldSizes.get(stringSizePair.string);
        return Math.min(this.maxFieldSize, ((this.maxTotalEstimatedSize - this.estimatedSize) + (num == null ? 0 : num.intValue())) - (num == null ? stringSizePair.size : 0));
    }

    @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilter
    public void add(String str, String str2, Map<String, String[]> map) {
        if (include(str, str2)) {
            if (ALWAYS_SET_FIELDS.contains(str)) {
                setAlwaysInclude(str, str2, map);
                return;
            }
            if (ALWAYS_ADD_FIELDS.contains(str)) {
                addAlwaysInclude(str, str2, map);
                return;
            }
            StringSizePair stringSizePairFilterKey = filterKey(str, str2, map);
            if (!map.containsKey(stringSizePairFilterKey.string)) {
                setFilterKey(stringSizePairFilterKey, str2, map);
                return;
            }
            String[] strArr = map.get(stringSizePairFilterKey.string);
            if (strArr != null && strArr.length >= this.maxValuesPerField) {
                setTruncated(map);
                return;
            }
            Integer num = this.fieldSizes.get(stringSizePairFilterKey.string);
            int iIntValue = num == null ? 0 : num.intValue();
            int iMaxAllowedToAdd = maxAllowedToAdd(stringSizePairFilterKey);
            if (iMaxAllowedToAdd <= 0) {
                setTruncated(map);
                return;
            }
            int iEstimateSize = estimateSize(str2);
            if (iEstimateSize <= iMaxAllowedToAdd || (iEstimateSize = estimateSize((str2 = truncate(str2, iMaxAllowedToAdd, map)))) != 0) {
                this.estimatedSize += num == null ? stringSizePairFilterKey.size + iEstimateSize : iEstimateSize;
                this.fieldSizes.put(stringSizePairFilterKey.string, Integer.valueOf(iEstimateSize + iIntValue));
                map.put(stringSizePairFilterKey.string, appendValue(map.get(stringSizePairFilterKey.string), str2));
            }
        }
    }

    private String[] appendValue(String[] strArr, String str) {
        if (str == null) {
            return strArr;
        }
        int length = strArr.length;
        String[] strArr2 = new String[length + 1];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        strArr2[length] = str;
        return strArr2;
    }

    private int maxAllowedToAdd(StringSizePair stringSizePair) {
        Integer num = this.fieldSizes.get(stringSizePair.string);
        return Math.min(this.maxFieldSize - (num == null ? 0 : num.intValue()), ((this.maxTotalEstimatedSize - this.estimatedSize) - 1) - (num == null ? stringSizePair.size : 0));
    }

    private void setFilterKey(StringSizePair stringSizePair, String str, Map<String, String[]> map) {
        if (!map.containsKey(stringSizePair.string) && stringSizePair.size + this.estimatedSize > this.maxTotalEstimatedSize) {
            setTruncated(map);
            return;
        }
        Integer num = this.fieldSizes.get(stringSizePair.string);
        int iIntValue = num == null ? 0 : num.intValue();
        int iMaxAllowedToSet = maxAllowedToSet(stringSizePair);
        if (iMaxAllowedToSet <= 0) {
            setTruncated(map);
            return;
        }
        int iEstimateSize = estimateSize(str);
        if (iEstimateSize <= iMaxAllowedToSet || (iEstimateSize = estimateSize((str = truncate(str, iMaxAllowedToSet, map)))) != 0) {
            this.estimatedSize += (num == null ? stringSizePair.size : 0) + (iEstimateSize - iIntValue);
            this.fieldSizes.put(stringSizePair.string, Integer.valueOf(iEstimateSize));
            map.put(stringSizePair.string, new String[]{str});
        }
    }

    private void setTruncated(Map<String, String[]> map) {
        map.put(METADATA_TRUNCATED_KEY, TRUE);
    }

    private StringSizePair filterKey(String str, String str2, Map<String, String[]> map) {
        int iEstimateSize = estimateSize(str);
        int i = this.maxKeySize;
        if (iEstimateSize <= i) {
            return new StringSizePair(str, iEstimateSize, false);
        }
        String strTruncate = truncate(str, i, map);
        return new StringSizePair(strTruncate, estimateSize(strTruncate), true);
    }

    private String truncate(String str, int i, Map<String, String[]> map) {
        setTruncated(map);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_16BE), 0, i);
        CharBuffer charBufferAllocate = CharBuffer.allocate(i);
        CharsetDecoder charsetDecoderNewDecoder = StandardCharsets.UTF_16BE.newDecoder();
        charsetDecoderNewDecoder.onMalformedInput(CodingErrorAction.IGNORE);
        charsetDecoderNewDecoder.decode(byteBufferWrap, charBufferAllocate, true);
        charsetDecoderNewDecoder.flush(charBufferAllocate);
        return new String(charBufferAllocate.array(), 0, charBufferAllocate.position());
    }

    private boolean include(String str, String str2) {
        return includeField(str) && includeValue(str2);
    }

    private boolean includeValue(String str) {
        return this.includeEmpty || !StringUtils.isBlank(str);
    }

    private boolean includeField(String str) {
        if (ALWAYS_SET_FIELDS.contains(str)) {
            return true;
        }
        if (this.excludeFields.contains(str)) {
            return false;
        }
        return this.includeFields.isEmpty() || this.includeFields.contains(str);
    }

    private static int estimateSize(String str) {
        return str.length() * 2;
    }

    private static class StringSizePair {
        final int size;
        final String string;
        final boolean truncated;

        public StringSizePair(String str, int i, boolean z) {
            this.string = str;
            this.size = i;
            this.truncated = z;
        }
    }
}
