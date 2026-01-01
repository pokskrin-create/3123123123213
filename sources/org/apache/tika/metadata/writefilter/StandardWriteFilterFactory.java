package org.apache.tika.metadata.writefilter;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class StandardWriteFilterFactory implements MetadataWriteFilterFactory {
    public static int DEFAULT_MAX_FIELD_SIZE = 102400;
    public static int DEFAULT_MAX_KEY_SIZE = 1024;
    public static int DEFAULT_MAX_VALUES_PER_FIELD = 10;
    public static int DEFAULT_TOTAL_ESTIMATED_BYTES = 10485760;
    private Set<String> includeFields = Collections.EMPTY_SET;
    private Set<String> excludeFields = Collections.EMPTY_SET;
    private int maxKeySize = DEFAULT_MAX_KEY_SIZE;
    private int maxFieldSize = DEFAULT_MAX_FIELD_SIZE;
    private int maxTotalEstimatedBytes = DEFAULT_TOTAL_ESTIMATED_BYTES;
    private int maxValuesPerField = DEFAULT_MAX_VALUES_PER_FIELD;
    private boolean includeEmpty = false;

    @Override // org.apache.tika.metadata.writefilter.MetadataWriteFilterFactory
    public MetadataWriteFilter newInstance() {
        if (this.maxFieldSize < 0) {
            throw new IllegalArgumentException("maxFieldSize must be > 0");
        }
        if (this.maxValuesPerField < 1) {
            throw new IllegalArgumentException("maxValuesPerField must be > 0");
        }
        if (this.maxTotalEstimatedBytes < 0) {
            throw new IllegalArgumentException("max estimated size must be > 0");
        }
        return new StandardWriteFilter(this.maxKeySize, this.maxFieldSize, this.maxTotalEstimatedBytes, this.maxValuesPerField, this.includeFields, this.excludeFields, this.includeEmpty);
    }

    public void setIncludeFields(List<String> list) {
        ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet(list.size());
        keySetViewNewKeySet.addAll(list);
        this.includeFields = Collections.unmodifiableSet(keySetViewNewKeySet);
    }

    public void setExcludeFields(List<String> list) {
        ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet(list.size());
        keySetViewNewKeySet.addAll(list);
        this.excludeFields = Collections.unmodifiableSet(keySetViewNewKeySet);
    }

    public void setMaxTotalEstimatedBytes(int i) {
        this.maxTotalEstimatedBytes = i;
    }

    public void setMaxKeySize(int i) {
        this.maxKeySize = i;
    }

    public void setMaxFieldSize(int i) {
        this.maxFieldSize = i;
    }

    public void setIncludeEmpty(boolean z) {
        this.includeEmpty = z;
    }

    public void setMaxValuesPerField(int i) {
        this.maxValuesPerField = i;
    }

    public Set<String> getIncludeFields() {
        return this.includeFields;
    }

    public int getMaxKeySize() {
        return this.maxKeySize;
    }

    public int getMaxFieldSize() {
        return this.maxFieldSize;
    }

    public int getMaxTotalEstimatedBytes() {
        return this.maxTotalEstimatedBytes;
    }

    public int getMaxValuesPerField() {
        return this.maxValuesPerField;
    }

    public boolean isIncludeEmpty() {
        return this.includeEmpty;
    }

    public String toString() {
        return "StandardWriteFilterFactory{includeFields=" + this.includeFields + ", maxKeySize=" + this.maxKeySize + ", maxFieldSize=" + this.maxFieldSize + ", maxTotalEstimatedBytes=" + this.maxTotalEstimatedBytes + ", maxValuesPerField=" + this.maxValuesPerField + ", includeEmpty=" + this.includeEmpty + "}";
    }
}
