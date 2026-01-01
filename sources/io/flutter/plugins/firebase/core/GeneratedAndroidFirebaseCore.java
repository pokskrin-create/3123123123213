package io.flutter.plugins.firebase.core;

import android.util.Log;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class GeneratedAndroidFirebaseCore {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @interface CanIgnoreReturnValue {
    }

    public interface NullableResult<T> {
        void error(Throwable th);

        void success(T t);
    }

    public interface Result<T> {
        void error(Throwable th);

        void success(T t);
    }

    public interface VoidResult {
        void error(Throwable th);

        void success();
    }

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(String str, String str2, Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    protected static ArrayList<Object> wrapError(Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
            return arrayList;
        }
        arrayList.add(th.toString());
        arrayList.add(th.getClass().getSimpleName());
        arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        return arrayList;
    }

    public static final class CoreFirebaseOptions {
        private String androidClientId;
        private String apiKey;
        private String appGroupId;
        private String appId;
        private String authDomain;
        private String databaseURL;
        private String deepLinkURLScheme;
        private String iosBundleId;
        private String iosClientId;
        private String measurementId;
        private String messagingSenderId;
        private String projectId;
        private String storageBucket;
        private String trackingId;

        public String getApiKey() {
            return this.apiKey;
        }

        public void setApiKey(String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"apiKey\" is null.");
            }
            this.apiKey = str;
        }

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"appId\" is null.");
            }
            this.appId = str;
        }

        public String getMessagingSenderId() {
            return this.messagingSenderId;
        }

        public void setMessagingSenderId(String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"messagingSenderId\" is null.");
            }
            this.messagingSenderId = str;
        }

        public String getProjectId() {
            return this.projectId;
        }

        public void setProjectId(String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"projectId\" is null.");
            }
            this.projectId = str;
        }

        public String getAuthDomain() {
            return this.authDomain;
        }

        public void setAuthDomain(String str) {
            this.authDomain = str;
        }

        public String getDatabaseURL() {
            return this.databaseURL;
        }

        public void setDatabaseURL(String str) {
            this.databaseURL = str;
        }

        public String getStorageBucket() {
            return this.storageBucket;
        }

        public void setStorageBucket(String str) {
            this.storageBucket = str;
        }

        public String getMeasurementId() {
            return this.measurementId;
        }

        public void setMeasurementId(String str) {
            this.measurementId = str;
        }

        public String getTrackingId() {
            return this.trackingId;
        }

        public void setTrackingId(String str) {
            this.trackingId = str;
        }

        public String getDeepLinkURLScheme() {
            return this.deepLinkURLScheme;
        }

        public void setDeepLinkURLScheme(String str) {
            this.deepLinkURLScheme = str;
        }

        public String getAndroidClientId() {
            return this.androidClientId;
        }

        public void setAndroidClientId(String str) {
            this.androidClientId = str;
        }

        public String getIosClientId() {
            return this.iosClientId;
        }

        public void setIosClientId(String str) {
            this.iosClientId = str;
        }

        public String getIosBundleId() {
            return this.iosBundleId;
        }

        public void setIosBundleId(String str) {
            this.iosBundleId = str;
        }

        public String getAppGroupId() {
            return this.appGroupId;
        }

        public void setAppGroupId(String str) {
            this.appGroupId = str;
        }

        CoreFirebaseOptions() {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                CoreFirebaseOptions coreFirebaseOptions = (CoreFirebaseOptions) obj;
                if (this.apiKey.equals(coreFirebaseOptions.apiKey) && this.appId.equals(coreFirebaseOptions.appId) && this.messagingSenderId.equals(coreFirebaseOptions.messagingSenderId) && this.projectId.equals(coreFirebaseOptions.projectId) && Objects.equals(this.authDomain, coreFirebaseOptions.authDomain) && Objects.equals(this.databaseURL, coreFirebaseOptions.databaseURL) && Objects.equals(this.storageBucket, coreFirebaseOptions.storageBucket) && Objects.equals(this.measurementId, coreFirebaseOptions.measurementId) && Objects.equals(this.trackingId, coreFirebaseOptions.trackingId) && Objects.equals(this.deepLinkURLScheme, coreFirebaseOptions.deepLinkURLScheme) && Objects.equals(this.androidClientId, coreFirebaseOptions.androidClientId) && Objects.equals(this.iosClientId, coreFirebaseOptions.iosClientId) && Objects.equals(this.iosBundleId, coreFirebaseOptions.iosBundleId) && Objects.equals(this.appGroupId, coreFirebaseOptions.appGroupId)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.apiKey, this.appId, this.messagingSenderId, this.projectId, this.authDomain, this.databaseURL, this.storageBucket, this.measurementId, this.trackingId, this.deepLinkURLScheme, this.androidClientId, this.iosClientId, this.iosBundleId, this.appGroupId);
        }

        public static final class Builder {
            private String androidClientId;
            private String apiKey;
            private String appGroupId;
            private String appId;
            private String authDomain;
            private String databaseURL;
            private String deepLinkURLScheme;
            private String iosBundleId;
            private String iosClientId;
            private String measurementId;
            private String messagingSenderId;
            private String projectId;
            private String storageBucket;
            private String trackingId;

            public Builder setApiKey(String str) {
                this.apiKey = str;
                return this;
            }

            public Builder setAppId(String str) {
                this.appId = str;
                return this;
            }

            public Builder setMessagingSenderId(String str) {
                this.messagingSenderId = str;
                return this;
            }

            public Builder setProjectId(String str) {
                this.projectId = str;
                return this;
            }

            public Builder setAuthDomain(String str) {
                this.authDomain = str;
                return this;
            }

            public Builder setDatabaseURL(String str) {
                this.databaseURL = str;
                return this;
            }

            public Builder setStorageBucket(String str) {
                this.storageBucket = str;
                return this;
            }

            public Builder setMeasurementId(String str) {
                this.measurementId = str;
                return this;
            }

            public Builder setTrackingId(String str) {
                this.trackingId = str;
                return this;
            }

            public Builder setDeepLinkURLScheme(String str) {
                this.deepLinkURLScheme = str;
                return this;
            }

            public Builder setAndroidClientId(String str) {
                this.androidClientId = str;
                return this;
            }

            public Builder setIosClientId(String str) {
                this.iosClientId = str;
                return this;
            }

            public Builder setIosBundleId(String str) {
                this.iosBundleId = str;
                return this;
            }

            public Builder setAppGroupId(String str) {
                this.appGroupId = str;
                return this;
            }

            public CoreFirebaseOptions build() {
                CoreFirebaseOptions coreFirebaseOptions = new CoreFirebaseOptions();
                coreFirebaseOptions.setApiKey(this.apiKey);
                coreFirebaseOptions.setAppId(this.appId);
                coreFirebaseOptions.setMessagingSenderId(this.messagingSenderId);
                coreFirebaseOptions.setProjectId(this.projectId);
                coreFirebaseOptions.setAuthDomain(this.authDomain);
                coreFirebaseOptions.setDatabaseURL(this.databaseURL);
                coreFirebaseOptions.setStorageBucket(this.storageBucket);
                coreFirebaseOptions.setMeasurementId(this.measurementId);
                coreFirebaseOptions.setTrackingId(this.trackingId);
                coreFirebaseOptions.setDeepLinkURLScheme(this.deepLinkURLScheme);
                coreFirebaseOptions.setAndroidClientId(this.androidClientId);
                coreFirebaseOptions.setIosClientId(this.iosClientId);
                coreFirebaseOptions.setIosBundleId(this.iosBundleId);
                coreFirebaseOptions.setAppGroupId(this.appGroupId);
                return coreFirebaseOptions;
            }
        }

        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(14);
            arrayList.add(this.apiKey);
            arrayList.add(this.appId);
            arrayList.add(this.messagingSenderId);
            arrayList.add(this.projectId);
            arrayList.add(this.authDomain);
            arrayList.add(this.databaseURL);
            arrayList.add(this.storageBucket);
            arrayList.add(this.measurementId);
            arrayList.add(this.trackingId);
            arrayList.add(this.deepLinkURLScheme);
            arrayList.add(this.androidClientId);
            arrayList.add(this.iosClientId);
            arrayList.add(this.iosBundleId);
            arrayList.add(this.appGroupId);
            return arrayList;
        }

        static CoreFirebaseOptions fromList(ArrayList<Object> arrayList) {
            CoreFirebaseOptions coreFirebaseOptions = new CoreFirebaseOptions();
            coreFirebaseOptions.setApiKey((String) arrayList.get(0));
            coreFirebaseOptions.setAppId((String) arrayList.get(1));
            coreFirebaseOptions.setMessagingSenderId((String) arrayList.get(2));
            coreFirebaseOptions.setProjectId((String) arrayList.get(3));
            coreFirebaseOptions.setAuthDomain((String) arrayList.get(4));
            coreFirebaseOptions.setDatabaseURL((String) arrayList.get(5));
            coreFirebaseOptions.setStorageBucket((String) arrayList.get(6));
            coreFirebaseOptions.setMeasurementId((String) arrayList.get(7));
            coreFirebaseOptions.setTrackingId((String) arrayList.get(8));
            coreFirebaseOptions.setDeepLinkURLScheme((String) arrayList.get(9));
            coreFirebaseOptions.setAndroidClientId((String) arrayList.get(10));
            coreFirebaseOptions.setIosClientId((String) arrayList.get(11));
            coreFirebaseOptions.setIosBundleId((String) arrayList.get(12));
            coreFirebaseOptions.setAppGroupId((String) arrayList.get(13));
            return coreFirebaseOptions;
        }
    }

    public static final class CoreInitializeResponse {
        private Boolean isAutomaticDataCollectionEnabled;
        private String name;
        private CoreFirebaseOptions options;
        private Map<String, Object> pluginConstants;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"name\" is null.");
            }
            this.name = str;
        }

        public CoreFirebaseOptions getOptions() {
            return this.options;
        }

        public void setOptions(CoreFirebaseOptions coreFirebaseOptions) {
            if (coreFirebaseOptions == null) {
                throw new IllegalStateException("Nonnull field \"options\" is null.");
            }
            this.options = coreFirebaseOptions;
        }

        public Boolean getIsAutomaticDataCollectionEnabled() {
            return this.isAutomaticDataCollectionEnabled;
        }

        public void setIsAutomaticDataCollectionEnabled(Boolean bool) {
            this.isAutomaticDataCollectionEnabled = bool;
        }

        public Map<String, Object> getPluginConstants() {
            return this.pluginConstants;
        }

        public void setPluginConstants(Map<String, Object> map) {
            if (map == null) {
                throw new IllegalStateException("Nonnull field \"pluginConstants\" is null.");
            }
            this.pluginConstants = map;
        }

        CoreInitializeResponse() {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                CoreInitializeResponse coreInitializeResponse = (CoreInitializeResponse) obj;
                if (this.name.equals(coreInitializeResponse.name) && this.options.equals(coreInitializeResponse.options) && Objects.equals(this.isAutomaticDataCollectionEnabled, coreInitializeResponse.isAutomaticDataCollectionEnabled) && this.pluginConstants.equals(coreInitializeResponse.pluginConstants)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.name, this.options, this.isAutomaticDataCollectionEnabled, this.pluginConstants);
        }

        public static final class Builder {
            private Boolean isAutomaticDataCollectionEnabled;
            private String name;
            private CoreFirebaseOptions options;
            private Map<String, Object> pluginConstants;

            public Builder setName(String str) {
                this.name = str;
                return this;
            }

            public Builder setOptions(CoreFirebaseOptions coreFirebaseOptions) {
                this.options = coreFirebaseOptions;
                return this;
            }

            public Builder setIsAutomaticDataCollectionEnabled(Boolean bool) {
                this.isAutomaticDataCollectionEnabled = bool;
                return this;
            }

            public Builder setPluginConstants(Map<String, Object> map) {
                this.pluginConstants = map;
                return this;
            }

            public CoreInitializeResponse build() {
                CoreInitializeResponse coreInitializeResponse = new CoreInitializeResponse();
                coreInitializeResponse.setName(this.name);
                coreInitializeResponse.setOptions(this.options);
                coreInitializeResponse.setIsAutomaticDataCollectionEnabled(this.isAutomaticDataCollectionEnabled);
                coreInitializeResponse.setPluginConstants(this.pluginConstants);
                return coreInitializeResponse;
            }
        }

        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(4);
            arrayList.add(this.name);
            arrayList.add(this.options);
            arrayList.add(this.isAutomaticDataCollectionEnabled);
            arrayList.add(this.pluginConstants);
            return arrayList;
        }

        static CoreInitializeResponse fromList(ArrayList<Object> arrayList) {
            CoreInitializeResponse coreInitializeResponse = new CoreInitializeResponse();
            coreInitializeResponse.setName((String) arrayList.get(0));
            coreInitializeResponse.setOptions((CoreFirebaseOptions) arrayList.get(1));
            coreInitializeResponse.setIsAutomaticDataCollectionEnabled((Boolean) arrayList.get(2));
            coreInitializeResponse.setPluginConstants((Map) arrayList.get(3));
            return coreInitializeResponse;
        }
    }

    private static class PigeonCodec extends StandardMessageCodec {
        public static final PigeonCodec INSTANCE = new PigeonCodec();

        private PigeonCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b, ByteBuffer byteBuffer) {
            if (b == -127) {
                return CoreFirebaseOptions.fromList((ArrayList) readValue(byteBuffer));
            }
            if (b == -126) {
                return CoreInitializeResponse.fromList((ArrayList) readValue(byteBuffer));
            }
            return super.readValueOfType(b, byteBuffer);
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof CoreFirebaseOptions) {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((CoreFirebaseOptions) obj).toList());
            } else if (obj instanceof CoreInitializeResponse) {
                byteArrayOutputStream.write(130);
                writeValue(byteArrayOutputStream, ((CoreInitializeResponse) obj).toList());
            } else {
                super.writeValue(byteArrayOutputStream, obj);
            }
        }
    }

    public interface FirebaseCoreHostApi {
        void initializeApp(String str, CoreFirebaseOptions coreFirebaseOptions, Result<CoreInitializeResponse> result);

        void initializeCore(Result<List<CoreInitializeResponse>> result);

        void optionsFromResource(Result<CoreFirebaseOptions> result);

        static MessageCodec<Object> getCodec() {
            return PigeonCodec.INSTANCE;
        }

        static void setUp(BinaryMessenger binaryMessenger, FirebaseCoreHostApi firebaseCoreHostApi) {
            setUp(binaryMessenger, "", firebaseCoreHostApi);
        }

        static void setUp(BinaryMessenger binaryMessenger, String str, final FirebaseCoreHostApi firebaseCoreHostApi) {
            String str2;
            if (str.isEmpty()) {
                str2 = "";
            } else {
                str2 = "." + str;
            }
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseCoreHostApi.initializeApp" + str2, getCodec());
            if (firebaseCoreHostApi != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.lambda$setUp$0(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseCoreHostApi.initializeCore" + str2, getCodec());
            if (firebaseCoreHostApi != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.lambda$setUp$1(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseCoreHostApi.optionsFromResource" + str2, getCodec());
            if (firebaseCoreHostApi != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda2
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.lambda$setUp$2(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
        }

        static /* synthetic */ void lambda$setUp$0(FirebaseCoreHostApi firebaseCoreHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) obj;
            firebaseCoreHostApi.initializeApp((String) arrayList2.get(0), (CoreFirebaseOptions) arrayList2.get(1), new Result<CoreInitializeResponse>() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.1
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void success(CoreInitializeResponse coreInitializeResponse) {
                    arrayList.add(0, coreInitializeResponse);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }

        static /* synthetic */ void lambda$setUp$1(FirebaseCoreHostApi firebaseCoreHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            firebaseCoreHostApi.initializeCore(new Result<List<CoreInitializeResponse>>() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.2
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void success(List<CoreInitializeResponse> list) {
                    arrayList.add(0, list);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }

        static /* synthetic */ void lambda$setUp$2(FirebaseCoreHostApi firebaseCoreHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            firebaseCoreHostApi.optionsFromResource(new Result<CoreFirebaseOptions>() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseCoreHostApi.3
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void success(CoreFirebaseOptions coreFirebaseOptions) {
                    arrayList.add(0, coreFirebaseOptions);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.Result
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }
    }

    public interface FirebaseAppHostApi {
        void delete(String str, VoidResult voidResult);

        void setAutomaticDataCollectionEnabled(String str, Boolean bool, VoidResult voidResult);

        void setAutomaticResourceManagementEnabled(String str, Boolean bool, VoidResult voidResult);

        static MessageCodec<Object> getCodec() {
            return PigeonCodec.INSTANCE;
        }

        static void setUp(BinaryMessenger binaryMessenger, FirebaseAppHostApi firebaseAppHostApi) {
            setUp(binaryMessenger, "", firebaseAppHostApi);
        }

        static void setUp(BinaryMessenger binaryMessenger, String str, final FirebaseAppHostApi firebaseAppHostApi) {
            String str2;
            if (str.isEmpty()) {
                str2 = "";
            } else {
                str2 = "." + str;
            }
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseAppHostApi.setAutomaticDataCollectionEnabled" + str2, getCodec());
            if (firebaseAppHostApi != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseAppHostApi.lambda$setUp$0(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseAppHostApi.setAutomaticResourceManagementEnabled" + str2, getCodec());
            if (firebaseAppHostApi != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseAppHostApi.lambda$setUp$1(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_core_platform_interface.FirebaseAppHostApi.delete" + str2, getCodec());
            if (firebaseAppHostApi != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda2
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        GeneratedAndroidFirebaseCore.FirebaseAppHostApi.lambda$setUp$2(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
        }

        static /* synthetic */ void lambda$setUp$0(FirebaseAppHostApi firebaseAppHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) obj;
            firebaseAppHostApi.setAutomaticDataCollectionEnabled((String) arrayList2.get(0), (Boolean) arrayList2.get(1), new VoidResult() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseAppHostApi.1
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void success() {
                    arrayList.add(0, null);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }

        static /* synthetic */ void lambda$setUp$1(FirebaseAppHostApi firebaseAppHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) obj;
            firebaseAppHostApi.setAutomaticResourceManagementEnabled((String) arrayList2.get(0), (Boolean) arrayList2.get(1), new VoidResult() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseAppHostApi.2
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void success() {
                    arrayList.add(0, null);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }

        static /* synthetic */ void lambda$setUp$2(FirebaseAppHostApi firebaseAppHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            firebaseAppHostApi.delete((String) ((ArrayList) obj).get(0), new VoidResult() { // from class: io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.FirebaseAppHostApi.3
                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void success() {
                    arrayList.add(0, null);
                    reply.reply(arrayList);
                }

                @Override // io.flutter.plugins.firebase.core.GeneratedAndroidFirebaseCore.VoidResult
                public void error(Throwable th) {
                    reply.reply(GeneratedAndroidFirebaseCore.wrapError(th));
                }
            });
        }
    }
}
