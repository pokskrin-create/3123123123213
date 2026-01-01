package com.google.firebase.abt;

import android.text.TextUtils;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class AbtExperimentInfo {
    static final String EXPERIMENT_ID_KEY = "experimentId";
    static final String TRIGGER_EVENT_KEY = "triggerEvent";
    static final String VARIANT_ID_KEY = "variantId";
    private final String experimentId;
    private final Date experimentStartTime;
    private final long timeToLiveInMillis;
    private final String triggerEventName;
    private final long triggerTimeoutInMillis;
    private final String variantId;
    static final String EXPERIMENT_START_TIME_KEY = "experimentStartTime";
    static final String TIME_TO_LIVE_KEY = "timeToLiveMillis";
    static final String TRIGGER_TIMEOUT_KEY = "triggerTimeoutMillis";
    private static final String[] ALL_REQUIRED_KEYS = {"experimentId", EXPERIMENT_START_TIME_KEY, TIME_TO_LIVE_KEY, TRIGGER_TIMEOUT_KEY, "variantId"};
    static final DateFormat protoTimestampStringParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

    public AbtExperimentInfo(String str, String str2, String str3, Date date, long j, long j2) {
        this.experimentId = str;
        this.variantId = str2;
        this.triggerEventName = str3;
        this.experimentStartTime = date;
        this.triggerTimeoutInMillis = j;
        this.timeToLiveInMillis = j2;
    }

    static AbtExperimentInfo fromMap(Map<String, String> map) throws AbtException, NumberFormatException, ParseException {
        String str;
        validateExperimentInfoMap(map);
        try {
            Date date = protoTimestampStringParser.parse(map.get(EXPERIMENT_START_TIME_KEY));
            long j = Long.parseLong(map.get(TRIGGER_TIMEOUT_KEY));
            long j2 = Long.parseLong(map.get(TIME_TO_LIVE_KEY));
            String str2 = map.get("experimentId");
            String str3 = map.get("variantId");
            if (map.containsKey(TRIGGER_EVENT_KEY)) {
                str = map.get(TRIGGER_EVENT_KEY);
            } else {
                str = "";
            }
            return new AbtExperimentInfo(str2, str3, str, date, j, j2);
        } catch (NumberFormatException e) {
            throw new AbtException("Could not process experiment: one of the durations could not be converted into a long.", e);
        } catch (ParseException e2) {
            throw new AbtException("Could not process experiment: parsing experiment start time failed.", e2);
        }
    }

    String getExperimentId() {
        return this.experimentId;
    }

    String getVariantId() {
        return this.variantId;
    }

    String getTriggerEventName() {
        return this.triggerEventName;
    }

    long getStartTimeInMillisSinceEpoch() {
        return this.experimentStartTime.getTime();
    }

    long getTriggerTimeoutInMillis() {
        return this.triggerTimeoutInMillis;
    }

    long getTimeToLiveInMillis() {
        return this.timeToLiveInMillis;
    }

    private static void validateExperimentInfoMap(Map<String, String> map) throws AbtException {
        ArrayList arrayList = new ArrayList();
        for (String str : ALL_REQUIRED_KEYS) {
            if (!map.containsKey(str)) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            throw new AbtException(String.format("The following keys are missing from the experiment info map: %s", arrayList));
        }
    }

    static void validateAbtExperimentInfo(AbtExperimentInfo abtExperimentInfo) throws AbtException {
        validateExperimentInfoMap(abtExperimentInfo.toStringMap());
    }

    Map<String, String> toStringMap() {
        HashMap map = new HashMap();
        map.put("experimentId", this.experimentId);
        map.put("variantId", this.variantId);
        map.put(TRIGGER_EVENT_KEY, this.triggerEventName);
        map.put(EXPERIMENT_START_TIME_KEY, protoTimestampStringParser.format(this.experimentStartTime));
        map.put(TRIGGER_TIMEOUT_KEY, Long.toString(this.triggerTimeoutInMillis));
        map.put(TIME_TO_LIVE_KEY, Long.toString(this.timeToLiveInMillis));
        return map;
    }

    AnalyticsConnector.ConditionalUserProperty toConditionalUserProperty(String str) {
        AnalyticsConnector.ConditionalUserProperty conditionalUserProperty = new AnalyticsConnector.ConditionalUserProperty();
        conditionalUserProperty.origin = str;
        conditionalUserProperty.creationTimestamp = getStartTimeInMillisSinceEpoch();
        conditionalUserProperty.name = this.experimentId;
        conditionalUserProperty.value = this.variantId;
        conditionalUserProperty.triggerEventName = TextUtils.isEmpty(this.triggerEventName) ? null : this.triggerEventName;
        conditionalUserProperty.triggerTimeout = this.triggerTimeoutInMillis;
        conditionalUserProperty.timeToLive = this.timeToLiveInMillis;
        return conditionalUserProperty;
    }

    static AbtExperimentInfo fromConditionalUserProperty(AnalyticsConnector.ConditionalUserProperty conditionalUserProperty) {
        String str;
        if (conditionalUserProperty.triggerEventName == null) {
            str = "";
        } else {
            str = conditionalUserProperty.triggerEventName;
        }
        return new AbtExperimentInfo(conditionalUserProperty.name, String.valueOf(conditionalUserProperty.value), str, new Date(conditionalUserProperty.creationTimestamp), conditionalUserProperty.triggerTimeout, conditionalUserProperty.timeToLive);
    }
}
