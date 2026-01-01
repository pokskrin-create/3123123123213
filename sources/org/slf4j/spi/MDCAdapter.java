package org.slf4j.spi;

import java.util.Deque;
import java.util.Map;

/* loaded from: classes4.dex */
public interface MDCAdapter {
    void clear();

    void clearDequeByKey(String str);

    String get(String str);

    Map<String, String> getCopyOfContextMap();

    Deque<String> getCopyOfDequeByKey(String str);

    String popByKey(String str);

    void pushByKey(String str, String str2);

    void put(String str, String str2);

    void remove(String str);

    void setContextMap(Map<String, String> map);
}
