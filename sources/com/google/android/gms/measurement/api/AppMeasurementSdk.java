package com.google.android.gms.measurement.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzdf;
import com.google.android.gms.internal.measurement.zzfb;
import com.google.android.gms.measurement.internal.zzjp;
import com.google.android.gms.measurement.internal.zzjq;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
/* loaded from: classes4.dex */
public class AppMeasurementSdk {
    private final zzfb zza;

    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
    public static final class ConditionalUserProperty {
        public static final String ACTIVE = "active";
        public static final String CREATION_TIMESTAMP = "creation_timestamp";
        public static final String EXPIRED_EVENT_NAME = "expired_event_name";
        public static final String EXPIRED_EVENT_PARAMS = "expired_event_params";
        public static final String NAME = "name";
        public static final String ORIGIN = "origin";
        public static final String TIMED_OUT_EVENT_NAME = "timed_out_event_name";
        public static final String TIMED_OUT_EVENT_PARAMS = "timed_out_event_params";
        public static final String TIME_TO_LIVE = "time_to_live";
        public static final String TRIGGERED_EVENT_NAME = "triggered_event_name";
        public static final String TRIGGERED_EVENT_PARAMS = "triggered_event_params";
        public static final String TRIGGERED_TIMESTAMP = "triggered_timestamp";
        public static final String TRIGGER_EVENT_NAME = "trigger_event_name";
        public static final String TRIGGER_TIMEOUT = "trigger_timeout";
        public static final String VALUE = "value";

        private ConditionalUserProperty() {
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
    public interface EventInterceptor extends zzjp {
        @Override // com.google.android.gms.measurement.internal.zzjp
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@23.0.0 */
    public interface OnEventListener extends zzjq {
        @Override // com.google.android.gms.measurement.internal.zzjq
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    public AppMeasurementSdk(zzfb zzfbVar) {
        this.zza = zzfbVar;
    }

    public static AppMeasurementSdk getInstance(Context context) {
        return zzfb.zza(context, null).zzb();
    }

    public void beginAdUnitExposure(String str) {
        this.zza.zzu(str);
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        this.zza.zzm(str, str2, bundle);
    }

    public void endAdUnitExposure(String str) {
        this.zza.zzv(str);
    }

    public long generateEventId() {
        return this.zza.zzz();
    }

    public String getAppIdOrigin() {
        return this.zza.zzI();
    }

    public String getAppInstanceId() {
        return this.zza.zzy();
    }

    public List<Bundle> getConditionalUserProperties(String str, String str2) {
        return this.zza.zzn(str, str2);
    }

    public String getCurrentScreenClass() {
        return this.zza.zzB();
    }

    public String getCurrentScreenName() {
        return this.zza.zzA();
    }

    public String getGmpAppId() {
        return this.zza.zzx();
    }

    public int getMaxUserProperties(String str) {
        return this.zza.zzF(str);
    }

    public Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return this.zza.zzC(str, str2, z);
    }

    public void logEvent(String str, String str2, Bundle bundle) {
        this.zza.zzi(str, str2, bundle);
    }

    public void logEventNoInterceptor(String str, String str2, Bundle bundle, long j) {
        this.zza.zzj(str, str2, bundle, j);
    }

    public void performAction(Bundle bundle) {
        this.zza.zzE(bundle, false);
    }

    public Bundle performActionWithResponse(Bundle bundle) {
        return this.zza.zzE(bundle, true);
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zza.zzf(onEventListener);
    }

    public void setConditionalUserProperty(Bundle bundle) {
        this.zza.zzl(bundle);
    }

    @Deprecated
    public void setConsent(Bundle bundle) {
    }

    public void setCurrentScreen(Activity activity, String str, String str2) {
        this.zza.zzp(zzdf.zza(activity), str, str2);
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zza.zzd(eventInterceptor);
    }

    @Deprecated
    public void setMeasurementEnabled(Boolean bool) {
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
    }

    public void setUserProperty(String str, String str2, Object obj) {
        this.zza.zzk(str, str2, obj, true);
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zza.zzg(onEventListener);
    }

    public final void zza(boolean z) {
        this.zza.zzK(z);
    }

    @Deprecated
    public static AppMeasurementSdk getInstance(Context context, String str, String str2, String str3, Bundle bundle) {
        return zzfb.zza(context, bundle).zzb();
    }
}
