package io.flutter.embedding.android;

import android.R;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.TypedValue;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class AndroidTouchProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BYTES_PER_FIELD = 8;
    static final int DEFAULT_HORIZONTAL_SCROLL_FACTOR = 48;
    static final int DEFAULT_VERTICAL_SCROLL_FACTOR = 48;
    private static final Matrix IDENTITY_TRANSFORM = new Matrix();
    private static final int IMPLICIT_VIEW_ID = 0;
    static final int POINTER_DATA_FIELD_COUNT = 36;
    private static final int POINTER_DATA_FLAG_BATCHED = 1;
    private static final String TAG = "AndroidTouchProcessor";
    private static final int TOOL_TYPE_BITS = 3;
    private static final int TOOL_TYPE_MASK = 7;
    private int cachedVerticalScrollFactor;
    private final FlutterRenderer renderer;
    private final boolean trackMotionEvents;
    private final Map<Integer, float[]> ongoingPans = new HashMap();
    private final MotionEventTracker motionEventTracker = MotionEventTracker.getInstance();

    public @interface PointerChange {
        public static final int ADD = 1;
        public static final int CANCEL = 0;
        public static final int DOWN = 4;
        public static final int HOVER = 3;
        public static final int MOVE = 5;
        public static final int PAN_ZOOM_END = 9;
        public static final int PAN_ZOOM_START = 7;
        public static final int PAN_ZOOM_UPDATE = 8;
        public static final int REMOVE = 2;
        public static final int UP = 6;
    }

    public @interface PointerDeviceKind {
        public static final int INVERTED_STYLUS = 3;
        public static final int MOUSE = 1;
        public static final int STYLUS = 2;
        public static final int TOUCH = 0;
        public static final int TRACKPAD = 4;
        public static final int UNKNOWN = 5;
    }

    public @interface PointerSignalKind {
        public static final int NONE = 0;
        public static final int SCALE = 3;
        public static final int SCROLL = 1;
        public static final int SCROLL_INERTIA_CANCEL = 2;
        public static final int UNKNOWN = 4;
    }

    private int getPointerChangeForAction(int i) {
        if (i == 0) {
            return 4;
        }
        if (i == 1) {
            return 6;
        }
        if (i == 5) {
            return 4;
        }
        if (i == 6) {
            return 6;
        }
        if (i == 2) {
            return 5;
        }
        if (i == 7) {
            return 3;
        }
        if (i == 3) {
            return 0;
        }
        return i == 8 ? 3 : -1;
    }

    private int getPointerChangeForPanZoom(int i) {
        if (i == 4) {
            return 7;
        }
        if (i == 5) {
            return 8;
        }
        return (i == 6 || i == 0) ? 9 : -1;
    }

    private int getPointerDeviceTypeForToolType(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 2;
        }
        if (i != 3) {
            return i != 4 ? 5 : 3;
        }
        return 1;
    }

    public AndroidTouchProcessor(FlutterRenderer flutterRenderer, boolean z) {
        this.renderer = flutterRenderer;
        this.trackMotionEvents = z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onTouchEvent(motionEvent, IDENTITY_TRANSFORM);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, Matrix matrix) {
        int actionMasked = motionEvent.getActionMasked();
        int pointerChangeForAction = getPointerChangeForAction(motionEvent.getActionMasked());
        boolean z = actionMasked == 0 || actionMasked == 5;
        boolean z2 = !z && (actionMasked == 1 || actionMasked == 6);
        int i = (z2 && getPointerDeviceTypeForToolType(motionEvent.getToolType(motionEvent.getActionIndex())) == 0) ? 1 : 0;
        int pointerCount = motionEvent.getPointerCount();
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect((pointerCount + i) * 288);
        byteBufferAllocateDirect.order(ByteOrder.LITTLE_ENDIAN);
        if (z) {
            addPointerForIndex(motionEvent, motionEvent.getActionIndex(), pointerChangeForAction, 0, matrix, byteBufferAllocateDirect);
        } else if (z2) {
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (i2 != motionEvent.getActionIndex() && motionEvent.getToolType(i2) == 1) {
                    addPointerForIndex(motionEvent, i2, 5, 1, matrix, byteBufferAllocateDirect);
                }
            }
            addPointerForIndex(motionEvent, motionEvent.getActionIndex(), pointerChangeForAction, 0, matrix, byteBufferAllocateDirect);
            if (i != 0) {
                addPointerForIndex(motionEvent, motionEvent.getActionIndex(), 2, 0, matrix, byteBufferAllocateDirect);
            }
        } else {
            for (int i3 = 0; i3 < pointerCount; i3++) {
                addPointerForIndex(motionEvent, i3, pointerChangeForAction, 0, matrix, byteBufferAllocateDirect);
            }
        }
        if (byteBufferAllocateDirect.position() % 288 != 0) {
            throw new AssertionError("Packet position is not on field boundary");
        }
        this.renderer.dispatchPointerDataPacket(byteBufferAllocateDirect, byteBufferAllocateDirect.position());
        return true;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent, Context context) {
        boolean zIsFromSource = motionEvent.isFromSource(2);
        boolean z = motionEvent.getActionMasked() == 7 || motionEvent.getActionMasked() == 8;
        if (!zIsFromSource || !z) {
            return false;
        }
        int pointerChangeForAction = getPointerChangeForAction(motionEvent.getActionMasked());
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(motionEvent.getPointerCount() * 288);
        byteBufferAllocateDirect.order(ByteOrder.LITTLE_ENDIAN);
        addPointerForIndex(motionEvent, motionEvent.getActionIndex(), pointerChangeForAction, 0, IDENTITY_TRANSFORM, byteBufferAllocateDirect, context);
        if (byteBufferAllocateDirect.position() % 288 != 0) {
            throw new AssertionError("Packet position is not on field boundary.");
        }
        this.renderer.dispatchPointerDataPacket(byteBufferAllocateDirect, byteBufferAllocateDirect.position());
        return true;
    }

    private void addPointerForIndex(MotionEvent motionEvent, int i, int i2, int i3, Matrix matrix, ByteBuffer byteBuffer) {
        addPointerForIndex(motionEvent, i, i2, i3, matrix, byteBuffer, null);
    }

    private int uniquePointerIdByType(MotionEvent motionEvent, int i) {
        return (motionEvent.getToolType(i) & 7) | (motionEvent.getPointerId(i) << 3);
    }

    private void addPointerForIndex(MotionEvent motionEvent, int i, int i2, int i3, Matrix matrix, ByteBuffer byteBuffer, Context context) {
        int i4;
        int i5;
        long j;
        long j2;
        double min;
        double max;
        double horizontalScrollFactor;
        double verticalScrollFactor;
        InputDevice.MotionRange motionRange;
        int i6 = -1;
        if (i2 == -1) {
            return;
        }
        int iUniquePointerIdByType = uniquePointerIdByType(motionEvent, i);
        int pointerDeviceTypeForToolType = getPointerDeviceTypeForToolType(motionEvent.getToolType(i));
        float[] fArr = {motionEvent.getX(i), motionEvent.getY(i)};
        matrix.mapPoints(fArr);
        if (pointerDeviceTypeForToolType == 1) {
            i5 = 0;
            j = 0;
            long buttonState = motionEvent.getButtonState() & 31;
            if (buttonState == 0) {
                i4 = 1;
                if (motionEvent.getSource() == 8194 && i2 == 4) {
                    this.ongoingPans.put(Integer.valueOf(iUniquePointerIdByType), fArr);
                }
            } else {
                i4 = 1;
            }
            j2 = buttonState;
        } else {
            i4 = 1;
            i5 = 0;
            j = 0;
            j2 = pointerDeviceTypeForToolType == 2 ? (motionEvent.getButtonState() >> 4) & 15 : 0L;
        }
        boolean zContainsKey = this.ongoingPans.containsKey(Integer.valueOf(iUniquePointerIdByType));
        if (zContainsKey) {
            int pointerChangeForPanZoom = getPointerChangeForPanZoom(i2);
            if (pointerChangeForPanZoom == -1) {
                return;
            } else {
                i6 = pointerChangeForPanZoom;
            }
        }
        long id = this.trackMotionEvents ? this.motionEventTracker.track(motionEvent).getId() : j;
        int i7 = motionEvent.getActionMasked() == 8 ? i4 : i5;
        long eventTime = motionEvent.getEventTime() * 1000;
        byteBuffer.putLong(id);
        byteBuffer.putLong(eventTime);
        if (zContainsKey) {
            byteBuffer.putLong(i6);
            byteBuffer.putLong(4L);
        } else {
            byteBuffer.putLong(i2);
            byteBuffer.putLong(pointerDeviceTypeForToolType);
        }
        byteBuffer.putLong(i7);
        byteBuffer.putLong(iUniquePointerIdByType);
        byteBuffer.putLong(j);
        if (zContainsKey) {
            float[] fArr2 = this.ongoingPans.get(Integer.valueOf(iUniquePointerIdByType));
            byteBuffer.putDouble(fArr2[i5]);
            byteBuffer.putDouble(fArr2[i4]);
        } else {
            byteBuffer.putDouble(fArr[i5]);
            byteBuffer.putDouble(fArr[i4]);
        }
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putLong(j2);
        byteBuffer.putLong(0L);
        byteBuffer.putLong(0L);
        byteBuffer.putDouble(motionEvent.getPressure(i));
        if (motionEvent.getDevice() == null || (motionRange = motionEvent.getDevice().getMotionRange(2)) == null) {
            min = 0.0d;
            max = 1.0d;
        } else {
            min = motionRange.getMin();
            max = motionRange.getMax();
        }
        byteBuffer.putDouble(min);
        byteBuffer.putDouble(max);
        if (pointerDeviceTypeForToolType == 2) {
            byteBuffer.putDouble(motionEvent.getAxisValue(24, i));
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        } else {
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
        byteBuffer.putDouble(motionEvent.getSize(i));
        byteBuffer.putDouble(motionEvent.getToolMajor(i));
        byteBuffer.putDouble(motionEvent.getToolMinor(i));
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putDouble(motionEvent.getAxisValue(8, i));
        if (pointerDeviceTypeForToolType == 2) {
            byteBuffer.putDouble(motionEvent.getAxisValue(25, i));
        } else {
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
        byteBuffer.putLong(i3);
        if (i7 == i4) {
            if (context != null) {
                horizontalScrollFactor = getHorizontalScrollFactor(context);
                verticalScrollFactor = getVerticalScrollFactor(context);
            } else {
                horizontalScrollFactor = 48.0d;
                verticalScrollFactor = 48.0d;
            }
            byteBuffer.putDouble(horizontalScrollFactor * (-motionEvent.getAxisValue(10, i)));
            byteBuffer.putDouble(verticalScrollFactor * (-motionEvent.getAxisValue(9, i)));
        } else {
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
        if (zContainsKey) {
            float[] fArr3 = this.ongoingPans.get(Integer.valueOf(iUniquePointerIdByType));
            byteBuffer.putDouble(fArr[i5] - fArr3[i5]);
            byteBuffer.putDouble(fArr[1] - fArr3[1]);
        } else {
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putDouble(1.0d);
        byteBuffer.putDouble(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        byteBuffer.putLong(0L);
        if (zContainsKey && i6 == 9) {
            this.ongoingPans.remove(Integer.valueOf(iUniquePointerIdByType));
        }
    }

    private float getHorizontalScrollFactor(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return ViewConfiguration.get(context).getScaledHorizontalScrollFactor();
        }
        return getVerticalScrollFactorPre26(context);
    }

    private float getVerticalScrollFactor(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return getVerticalScrollFactorAbove26(context);
        }
        return getVerticalScrollFactorPre26(context);
    }

    private float getVerticalScrollFactorAbove26(Context context) {
        return ViewConfiguration.get(context).getScaledVerticalScrollFactor();
    }

    private int getVerticalScrollFactorPre26(Context context) {
        if (this.cachedVerticalScrollFactor == 0) {
            TypedValue typedValue = new TypedValue();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                return 48;
            }
            this.cachedVerticalScrollFactor = (int) typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.cachedVerticalScrollFactor;
    }
}
