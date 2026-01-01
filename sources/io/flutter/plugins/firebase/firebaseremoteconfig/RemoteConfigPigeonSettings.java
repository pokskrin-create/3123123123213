package io.flutter.plugins.firebase.firebaseremoteconfig;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeneratedAndroidFirebaseRemoteConfig.g.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0017"}, d2 = {"Lio/flutter/plugins/firebase/firebaseremoteconfig/RemoteConfigPigeonSettings;", "", "fetchTimeoutSeconds", "", "minimumFetchIntervalSeconds", "<init>", "(JJ)V", "getFetchTimeoutSeconds", "()J", "getMinimumFetchIntervalSeconds", "toList", "", "equals", "", "other", "hashCode", "", "component1", "component2", "copy", "toString", "", "Companion", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class RemoteConfigPigeonSettings {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long fetchTimeoutSeconds;
    private final long minimumFetchIntervalSeconds;

    public static /* synthetic */ RemoteConfigPigeonSettings copy$default(RemoteConfigPigeonSettings remoteConfigPigeonSettings, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = remoteConfigPigeonSettings.fetchTimeoutSeconds;
        }
        if ((i & 2) != 0) {
            j2 = remoteConfigPigeonSettings.minimumFetchIntervalSeconds;
        }
        return remoteConfigPigeonSettings.copy(j, j2);
    }

    /* renamed from: component1, reason: from getter */
    public final long getFetchTimeoutSeconds() {
        return this.fetchTimeoutSeconds;
    }

    /* renamed from: component2, reason: from getter */
    public final long getMinimumFetchIntervalSeconds() {
        return this.minimumFetchIntervalSeconds;
    }

    public final RemoteConfigPigeonSettings copy(long fetchTimeoutSeconds, long minimumFetchIntervalSeconds) {
        return new RemoteConfigPigeonSettings(fetchTimeoutSeconds, minimumFetchIntervalSeconds);
    }

    public String toString() {
        return "RemoteConfigPigeonSettings(fetchTimeoutSeconds=" + this.fetchTimeoutSeconds + ", minimumFetchIntervalSeconds=" + this.minimumFetchIntervalSeconds + ")";
    }

    public RemoteConfigPigeonSettings(long j, long j2) {
        this.fetchTimeoutSeconds = j;
        this.minimumFetchIntervalSeconds = j2;
    }

    public final long getFetchTimeoutSeconds() {
        return this.fetchTimeoutSeconds;
    }

    public final long getMinimumFetchIntervalSeconds() {
        return this.minimumFetchIntervalSeconds;
    }

    /* compiled from: GeneratedAndroidFirebaseRemoteConfig.g.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007¨\u0006\b"}, d2 = {"Lio/flutter/plugins/firebase/firebaseremoteconfig/RemoteConfigPigeonSettings$Companion;", "", "<init>", "()V", "fromList", "Lio/flutter/plugins/firebase/firebaseremoteconfig/RemoteConfigPigeonSettings;", "pigeonVar_list", "", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final RemoteConfigPigeonSettings fromList(List<? extends Object> pigeonVar_list) {
            Intrinsics.checkNotNullParameter(pigeonVar_list, "pigeonVar_list");
            Object obj = pigeonVar_list.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
            long jLongValue = ((Long) obj).longValue();
            Object obj2 = pigeonVar_list.get(1);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
            return new RemoteConfigPigeonSettings(jLongValue, ((Long) obj2).longValue());
        }
    }

    public final List<Object> toList() {
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(this.fetchTimeoutSeconds), Long.valueOf(this.minimumFetchIntervalSeconds)});
    }

    public boolean equals(Object other) {
        if (!(other instanceof RemoteConfigPigeonSettings)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.deepEquals(toList(), ((RemoteConfigPigeonSettings) other).toList());
    }

    public int hashCode() {
        return toList().hashCode();
    }
}
