package androidx.window.area;

import android.app.Activity;
import android.os.Binder;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.Flow;

/* compiled from: WindowAreaController.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015J(\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H&J(\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H&R\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Landroidx/window/area/WindowAreaController;", "", "windowAreaInfos", "Lkotlinx/coroutines/flow/Flow;", "", "Landroidx/window/area/WindowAreaInfo;", "getWindowAreaInfos", "()Lkotlinx/coroutines/flow/Flow;", "presentContentOnWindowArea", "", "token", "Landroid/os/Binder;", "activity", "Landroid/app/Activity;", "executor", "Ljava/util/concurrent/Executor;", "windowAreaPresentationSessionCallback", "Landroidx/window/area/WindowAreaPresentationSessionCallback;", "transferActivityToWindowArea", "windowAreaSessionCallback", "Landroidx/window/area/WindowAreaSessionCallback;", "Companion", "window_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface WindowAreaController {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @JvmStatic
    static WindowAreaController getOrCreate() {
        return INSTANCE.getOrCreate();
    }

    @JvmStatic
    static void overrideDecorator(WindowAreaControllerDecorator windowAreaControllerDecorator) {
        INSTANCE.overrideDecorator(windowAreaControllerDecorator);
    }

    @JvmStatic
    static void reset() {
        INSTANCE.reset();
    }

    Flow<List<WindowAreaInfo>> getWindowAreaInfos();

    void presentContentOnWindowArea(Binder token, Activity activity, Executor executor, WindowAreaPresentationSessionCallback windowAreaPresentationSessionCallback);

    void transferActivityToWindowArea(Binder token, Activity activity, Executor executor, WindowAreaSessionCallback windowAreaSessionCallback);

    /* compiled from: WindowAreaController.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\b\u0010\f\u001a\u00020\nH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/window/area/WindowAreaController$Companion;", "", "()V", "TAG", "", "decorator", "Landroidx/window/area/WindowAreaControllerDecorator;", "getOrCreate", "Landroidx/window/area/WindowAreaController;", "overrideDecorator", "", "overridingDecorator", "reset", "window_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final String TAG = Reflection.getOrCreateKotlinClass(WindowAreaController.class).getSimpleName();
        private static WindowAreaControllerDecorator decorator = EmptyDecorator.INSTANCE;

        private Companion() {
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x005e  */
        @kotlin.jvm.JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.window.area.WindowAreaController getOrCreate() {
            /*
                r5 = this;
                r0 = 0
                java.lang.Class r1 = r5.getClass()     // Catch: java.lang.Throwable -> L15
                java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch: java.lang.Throwable -> L15
                if (r1 == 0) goto L26
                androidx.window.area.SafeWindowAreaComponentProvider r2 = new androidx.window.area.SafeWindowAreaComponentProvider     // Catch: java.lang.Throwable -> L15
                r2.<init>(r1)     // Catch: java.lang.Throwable -> L15
                androidx.window.extensions.area.WindowAreaComponent r0 = r2.getWindowAreaComponent()     // Catch: java.lang.Throwable -> L15
                goto L26
            L15:
                androidx.window.core.BuildConfig r1 = androidx.window.core.BuildConfig.INSTANCE
                androidx.window.core.VerificationMode r1 = r1.getVerificationMode()
                androidx.window.core.VerificationMode r2 = androidx.window.core.VerificationMode.LOG
                if (r1 != r2) goto L26
                java.lang.String r1 = androidx.window.area.WindowAreaController.Companion.TAG
                java.lang.String r2 = "Failed to load WindowExtensions"
                android.util.Log.d(r1, r2)
            L26:
                int r1 = android.os.Build.VERSION.SDK_INT
                r2 = 29
                if (r1 <= r2) goto L5e
                if (r0 == 0) goto L5e
                androidx.window.core.ExtensionsUtil r1 = androidx.window.core.ExtensionsUtil.INSTANCE
                int r1 = r1.getSafeVendorApiLevel()
                r2 = 3
                if (r1 >= r2) goto L4d
                androidx.window.area.utils.DeviceUtils r1 = androidx.window.area.utils.DeviceUtils.INSTANCE
                java.lang.String r2 = android.os.Build.MANUFACTURER
                java.lang.String r3 = "MANUFACTURER"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
                java.lang.String r3 = android.os.Build.MODEL
                java.lang.String r4 = "MODEL"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
                boolean r1 = r1.hasDeviceMetrics$window_release(r2, r3)
                if (r1 == 0) goto L5e
            L4d:
                androidx.window.area.WindowAreaControllerImpl r1 = new androidx.window.area.WindowAreaControllerImpl
                kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                androidx.window.core.ExtensionsUtil r2 = androidx.window.core.ExtensionsUtil.INSTANCE
                int r2 = r2.getSafeVendorApiLevel()
                r1.<init>(r0, r2)
                androidx.window.area.WindowAreaController r1 = (androidx.window.area.WindowAreaController) r1
                goto L66
            L5e:
                androidx.window.area.EmptyWindowAreaControllerImpl r0 = new androidx.window.area.EmptyWindowAreaControllerImpl
                r0.<init>()
                r1 = r0
                androidx.window.area.WindowAreaController r1 = (androidx.window.area.WindowAreaController) r1
            L66:
                androidx.window.area.WindowAreaControllerDecorator r0 = androidx.window.area.WindowAreaController.Companion.decorator
                androidx.window.area.WindowAreaController r0 = r0.decorate(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.window.area.WindowAreaController.Companion.getOrCreate():androidx.window.area.WindowAreaController");
        }

        @JvmStatic
        public final void overrideDecorator(WindowAreaControllerDecorator overridingDecorator) {
            Intrinsics.checkNotNullParameter(overridingDecorator, "overridingDecorator");
            decorator = overridingDecorator;
        }

        @JvmStatic
        public final void reset() {
            decorator = EmptyDecorator.INSTANCE;
        }
    }
}
