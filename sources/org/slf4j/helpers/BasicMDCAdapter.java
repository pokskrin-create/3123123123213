package org.slf4j.helpers;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

/* loaded from: classes4.dex */
public class BasicMDCAdapter implements MDCAdapter {
    private final ThreadLocalMapOfStacks threadLocalMapOfDeques = new ThreadLocalMapOfStacks();
    private final InheritableThreadLocal<Map<String, String>> inheritableThreadLocalMap = new InheritableThreadLocal<Map<String, String>>() { // from class: org.slf4j.helpers.BasicMDCAdapter.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.InheritableThreadLocal
        public Map<String, String> childValue(Map<String, String> map) {
            if (map == null) {
                return null;
            }
            return new HashMap(map);
        }
    };

    @Override // org.slf4j.spi.MDCAdapter
    public void put(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map == null) {
            map = new HashMap<>();
            this.inheritableThreadLocalMap.set(map);
        }
        map.put(str, str2);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public String get(String str) {
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map == null || str == null) {
            return null;
        }
        return map.get(str);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void remove(String str) {
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map != null) {
            map.remove(str);
        }
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void clear() {
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map != null) {
            map.clear();
            this.inheritableThreadLocalMap.remove();
        }
    }

    public Set<String> getKeys() {
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> map = this.inheritableThreadLocalMap.get();
        if (map != null) {
            return new HashMap(map);
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void setContextMap(Map<String, String> map) {
        this.inheritableThreadLocalMap.set(map != null ? new HashMap(map) : null);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void pushByKey(String str, String str2) {
        this.threadLocalMapOfDeques.pushByKey(str, str2);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public String popByKey(String str) {
        return this.threadLocalMapOfDeques.popByKey(str);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public Deque<String> getCopyOfDequeByKey(String str) {
        return this.threadLocalMapOfDeques.getCopyOfDequeByKey(str);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void clearDequeByKey(String str) {
        this.threadLocalMapOfDeques.clearDequeByKey(str);
    }
}
