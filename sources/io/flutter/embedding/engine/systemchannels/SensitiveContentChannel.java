package io.flutter.embedding.engine.systemchannels;

import com.google.firebase.messaging.Constants;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;

/* loaded from: classes4.dex */
public class SensitiveContentChannel {
    public static final int AUTO_SENSITIVE_CONTENT_SENSITIVITY = 0;
    public static final int NOT_SENSITIVE_CONTENT_SENSITIVITY = 2;
    public static final int SENSITIVE_CONTENT_SENSITIVITY = 1;
    private static final String TAG = "SensitiveContentChannel";
    public static final int UNKNOWN_CONTENT_SENSITIVITY = 3;
    public final MethodChannel channel;
    public final MethodChannel.MethodCallHandler parsingMethodHandler;
    private SensitiveContentMethodHandler sensitiveContentMethodHandler;

    public interface SensitiveContentMethodHandler {
        int getContentSensitivity();

        boolean isSupported();

        void setContentSensitivity(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int serializeContentSensitivity(int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 3;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int deserializeContentSensitivity(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        throw new IllegalArgumentException("contentSensitivityIndex " + i + " not known to the SensitiveContentChannel.");
    }

    public SensitiveContentChannel(DartExecutor dartExecutor) {
        MethodChannel.MethodCallHandler methodCallHandler = new MethodChannel.MethodCallHandler() { // from class: io.flutter.embedding.engine.systemchannels.SensitiveContentChannel.1
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                String str;
                if (SensitiveContentChannel.this.sensitiveContentMethodHandler == null) {
                    return;
                }
                str = methodCall.method;
                Log.v(SensitiveContentChannel.TAG, "Received '" + str + "' message.");
                str.hashCode();
                switch (str) {
                    case "SensitiveContent.getContentSensitivity":
                        try {
                            int contentSensitivity = SensitiveContentChannel.this.sensitiveContentMethodHandler.getContentSensitivity();
                            Integer numValueOf = Integer.valueOf(contentSensitivity);
                            SensitiveContentChannel sensitiveContentChannel = SensitiveContentChannel.this;
                            numValueOf.getClass();
                            result.success(Integer.valueOf(sensitiveContentChannel.serializeContentSensitivity(contentSensitivity)));
                            break;
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            result.error(Constants.IPC_BUNDLE_KEY_SEND_ERROR, e.getMessage(), null);
                            return;
                        }
                    case "SensitiveContent.setContentSensitivity":
                        try {
                            SensitiveContentChannel.this.sensitiveContentMethodHandler.setContentSensitivity(SensitiveContentChannel.this.deserializeContentSensitivity(((Integer) methodCall.arguments()).intValue()));
                            break;
                        } catch (IllegalArgumentException | IllegalStateException e2) {
                            result.error(Constants.IPC_BUNDLE_KEY_SEND_ERROR, e2.getMessage(), null);
                            return;
                        }
                    case "SensitiveContent.isSupported":
                        result.success(Boolean.valueOf(SensitiveContentChannel.this.sensitiveContentMethodHandler.isSupported()));
                        break;
                    default:
                        Log.v(SensitiveContentChannel.TAG, "Method " + str + " is not implemented for the SensitiveContentChannel.");
                        result.notImplemented();
                        break;
                }
            }
        };
        this.parsingMethodHandler = methodCallHandler;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/sensitivecontent", StandardMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    public void setSensitiveContentMethodHandler(SensitiveContentMethodHandler sensitiveContentMethodHandler) {
        this.sensitiveContentMethodHandler = sensitiveContentMethodHandler;
    }
}
