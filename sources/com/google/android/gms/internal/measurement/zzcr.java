package com.google.android.gms.internal.measurement;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
public interface zzcr extends IInterface {
    void beginAdUnitExposure(String str, long j) throws RemoteException;

    void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException;

    void clearMeasurementEnabled(long j) throws RemoteException;

    void endAdUnitExposure(String str, long j) throws RemoteException;

    void generateEventId(zzcu zzcuVar) throws RemoteException;

    void getAppInstanceId(zzcu zzcuVar) throws RemoteException;

    void getCachedAppInstanceId(zzcu zzcuVar) throws RemoteException;

    void getConditionalUserProperties(String str, String str2, zzcu zzcuVar) throws RemoteException;

    void getCurrentScreenClass(zzcu zzcuVar) throws RemoteException;

    void getCurrentScreenName(zzcu zzcuVar) throws RemoteException;

    void getGmpAppId(zzcu zzcuVar) throws RemoteException;

    void getMaxUserProperties(String str, zzcu zzcuVar) throws RemoteException;

    void getSessionId(zzcu zzcuVar) throws RemoteException;

    void getTestFlag(zzcu zzcuVar, int i) throws RemoteException;

    void getUserProperties(String str, String str2, boolean z, zzcu zzcuVar) throws RemoteException;

    void initForTests(Map map) throws RemoteException;

    void initialize(IObjectWrapper iObjectWrapper, zzdd zzddVar, long j) throws RemoteException;

    void isDataCollectionEnabled(zzcu zzcuVar) throws RemoteException;

    void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws RemoteException;

    void logEventAndBundle(String str, String str2, Bundle bundle, zzcu zzcuVar, long j) throws RemoteException;

    void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException;

    void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException;

    void onActivityCreatedByScionActivityInfo(zzdf zzdfVar, Bundle bundle, long j) throws RemoteException;

    void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityDestroyedByScionActivityInfo(zzdf zzdfVar, long j) throws RemoteException;

    void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityPausedByScionActivityInfo(zzdf zzdfVar, long j) throws RemoteException;

    void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityResumedByScionActivityInfo(zzdf zzdfVar, long j) throws RemoteException;

    void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzcu zzcuVar, long j) throws RemoteException;

    void onActivitySaveInstanceStateByScionActivityInfo(zzdf zzdfVar, zzcu zzcuVar, long j) throws RemoteException;

    void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityStartedByScionActivityInfo(zzdf zzdfVar, long j) throws RemoteException;

    void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityStoppedByScionActivityInfo(zzdf zzdfVar, long j) throws RemoteException;

    void performAction(Bundle bundle, zzcu zzcuVar, long j) throws RemoteException;

    void registerOnMeasurementEventListener(zzda zzdaVar) throws RemoteException;

    void resetAnalyticsData(long j) throws RemoteException;

    void retrieveAndUploadBatches(zzcx zzcxVar) throws RemoteException;

    void setConditionalUserProperty(Bundle bundle, long j) throws RemoteException;

    void setConsent(Bundle bundle, long j) throws RemoteException;

    void setConsentThirdParty(Bundle bundle, long j) throws RemoteException;

    void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws RemoteException;

    void setCurrentScreenByScionActivityInfo(zzdf zzdfVar, String str, String str2, long j) throws RemoteException;

    void setDataCollectionEnabled(boolean z) throws RemoteException;

    void setDefaultEventParameters(Bundle bundle) throws RemoteException;

    void setEventInterceptor(zzda zzdaVar) throws RemoteException;

    void setInstanceIdProvider(zzdc zzdcVar) throws RemoteException;

    void setMeasurementEnabled(boolean z, long j) throws RemoteException;

    void setMinimumSessionDuration(long j) throws RemoteException;

    void setSessionTimeoutDuration(long j) throws RemoteException;

    void setSgtmDebugInfo(Intent intent) throws RemoteException;

    void setUserId(String str, long j) throws RemoteException;

    void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException;

    void unregisterOnMeasurementEventListener(zzda zzdaVar) throws RemoteException;
}
