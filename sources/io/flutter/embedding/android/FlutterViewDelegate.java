package io.flutter.embedding.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;
import android.view.WindowInsets;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.util.ViewUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FlutterViewDelegate {
    public WindowInsets getWindowInsets(Context context) {
        Window window;
        Activity activity = ViewUtils.getActivity(context);
        if (activity == null || (window = activity.getWindow()) == null) {
            return null;
        }
        return window.getDecorView().getRootWindowInsets();
    }

    public List<Rect> getCaptionBarInsets(Context context) {
        WindowInsets windowInsets = getWindowInsets(context);
        if (windowInsets == null) {
            return Collections.EMPTY_LIST;
        }
        return windowInsets.getBoundingRects(WindowInsets.Type.captionBar());
    }

    public void growViewportMetricsToCaptionBar(Context context, FlutterRenderer.ViewportMetrics viewportMetrics) {
        List<Rect> captionBarInsets = getCaptionBarInsets(context);
        int iMax = viewportMetrics.viewPaddingTop;
        Iterator<Rect> it = captionBarInsets.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().bottom);
        }
        viewportMetrics.viewPaddingTop = iMax;
    }
}
