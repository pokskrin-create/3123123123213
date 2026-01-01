package io.flutter.view;

import android.app.ActivityManager;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.ImageReader;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import java.util.Locale;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityBridge$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ ActivityManager.TaskDescription m(String str, int i, int i2) {
        return new ActivityManager.TaskDescription(str, i, i2);
    }

    public static /* synthetic */ ImageReader.Builder m(int i, int i2) {
        return new ImageReader.Builder(i, i2);
    }

    public static /* synthetic */ Surface m(SurfaceControl surfaceControl) {
        return new Surface(surfaceControl);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ SurfaceControl.Builder m456m() {
        return new SurfaceControl.Builder();
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ SurfaceControl.Transaction m457m() {
        return new SurfaceControl.Transaction();
    }

    public static /* bridge */ /* synthetic */ SurfaceControl.Transaction m(Object obj) {
        return (SurfaceControl.Transaction) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ AutofillManager m458m(Object obj) {
        return (AutofillManager) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ AutofillValue m459m(Object obj) {
        return (AutofillValue) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ Class m460m() {
        return AutofillManager.class;
    }

    public static /* synthetic */ Locale.LanguageRange m(String str) {
        return new Locale.LanguageRange(str);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ void m462m() {
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m465m(Object obj) {
        return obj instanceof AdaptiveIconDrawable;
    }

    public static /* synthetic */ void m$1() {
    }

    public static /* synthetic */ void m$2() {
    }

    public static /* synthetic */ void m$3() {
    }
}
