package androidx.privacysandbox.ads.adservices.adid;

import android.content.Context;
import androidx.media.AudioManagerCompat$$ExternalSyntheticApiModelOutline0;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdIdManagerApi33Ext4Impl.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/privacysandbox/ads/adservices/adid/AdIdManagerApi33Ext4Impl;", "Landroidx/privacysandbox/ads/adservices/adid/AdIdManagerImplCommon;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AdIdManagerApi33Ext4Impl extends AdIdManagerImplCommon {
    /* JADX WARN: Illegal instructions before constructor call */
    public AdIdManagerApi33Ext4Impl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) AudioManagerCompat$$ExternalSyntheticApiModelOutline0.m200m());
        Intrinsics.checkNotNullExpressionValue(systemService, "context.getSystemService….AdIdManager::class.java)");
        super(AudioManagerCompat$$ExternalSyntheticApiModelOutline0.m186m(systemService));
    }
}
