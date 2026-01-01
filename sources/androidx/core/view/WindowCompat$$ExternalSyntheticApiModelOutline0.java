package androidx.core.view;

import android.graphics.Insets;
import android.view.RoundedCorner;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowCompat$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ RoundedCorner m(int i, int i2, int i3, int i4) {
        return new RoundedCorner(i, i2, i3, i4);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ WindowInsets.Builder m133m() {
        return new WindowInsets.Builder();
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ WindowInsets.Builder m134m(WindowInsets windowInsets) {
        return new WindowInsets.Builder(windowInsets);
    }

    public static /* synthetic */ WindowInsetsAnimation.Bounds m(Insets insets, Insets insets2) {
        return new WindowInsetsAnimation.Bounds(insets, insets2);
    }

    public static /* synthetic */ WindowInsetsAnimation m(int i, Interpolator interpolator, long j) {
        return new WindowInsetsAnimation(i, interpolator, j);
    }

    public static /* bridge */ /* synthetic */ WindowInsetsAnimation m(Object obj) {
        return (WindowInsetsAnimation) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ WindowInsetsController.OnControllableInsetsChangedListener m137m(Object obj) {
        return (WindowInsetsController.OnControllableInsetsChangedListener) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ void m141m() {
    }

    /* renamed from: m$1, reason: collision with other method in class */
    public static /* synthetic */ void m146m$1() {
    }
}
