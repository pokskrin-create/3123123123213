package org.slf4j.event;

import java.util.Objects;

/* loaded from: classes4.dex */
public class KeyValuePair {
    public final String key;
    public final Object value;

    public KeyValuePair(String str, Object obj) {
        this.key = str;
        this.value = obj;
    }

    public String toString() {
        return String.valueOf(this.key) + "=\"" + String.valueOf(this.value) + "\"";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyValuePair keyValuePair = (KeyValuePair) obj;
            if (Objects.equals(this.key, keyValuePair.key) && Objects.equals(this.value, keyValuePair.value)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }
}
