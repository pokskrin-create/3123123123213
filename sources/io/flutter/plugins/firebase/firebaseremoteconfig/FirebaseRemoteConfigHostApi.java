package io.flutter.plugins.firebase.firebaseremoteconfig;

import com.google.firebase.remoteconfig.RemoteConfigComponent;
import com.google.firebase.remoteconfig.RemoteConfigConstants;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeneratedAndroidFirebaseRemoteConfig.g.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0007\bf\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017J*\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0004\u0012\u00020\u00030\u0007H&J*\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\b\u0012\u0004\u0012\u00020\u00030\u0007H&J*\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\b\u0012\u0004\u0012\u00020\u00030\u0007H&J2\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0004\u0012\u00020\u00030\u0007H&J@\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0014\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00112\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0004\u0012\u00020\u00030\u0007H&J*\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0004\u0012\u00020\u00030\u0007H&J@\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00112\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0004\u0012\u00020\u00030\u0007H&J8\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052&\u0010\u0006\u001a\"\u0012\u0018\u0012\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00110\b\u0012\u0004\u0012\u00020\u00030\u0007H&J6\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052$\u0010\u0006\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00110\b\u0012\u0004\u0012\u00020\u00030\u0007H&¨\u0006\u0018"}, d2 = {"Lio/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigHostApi;", "", RemoteConfigComponent.FETCH_FILE_NAME, "", "appName", "", "callback", "Lkotlin/Function1;", "Lkotlin/Result;", "fetchAndActivate", "", RemoteConfigComponent.ACTIVATE_FILE_NAME, "setConfigSettings", "settings", "Lio/flutter/plugins/firebase/firebaseremoteconfig/RemoteConfigPigeonSettings;", "setDefaults", "defaultParameters", "", "ensureInitialized", "setCustomSignals", RemoteConfigConstants.RequestFieldKey.CUSTOM_SIGNALS, "getAll", "getProperties", "Companion", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface FirebaseRemoteConfigHostApi {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void activate(String appName, Function1<? super Result<Boolean>, Unit> callback);

    void ensureInitialized(String appName, Function1<? super Result<Unit>, Unit> callback);

    void fetch(String appName, Function1<? super Result<Unit>, Unit> callback);

    void fetchAndActivate(String appName, Function1<? super Result<Boolean>, Unit> callback);

    void getAll(String appName, Function1<? super Result<? extends Map<String, ? extends Object>>, Unit> callback);

    void getProperties(String appName, Function1<? super Result<? extends Map<String, ? extends Object>>, Unit> callback);

    void setConfigSettings(String appName, RemoteConfigPigeonSettings settings, Function1<? super Result<Unit>, Unit> callback);

    void setCustomSignals(String appName, Map<String, ? extends Object> customSignals, Function1<? super Result<Unit>, Unit> callback);

    void setDefaults(String appName, Map<String, ? extends Object> defaultParameters, Function1<? super Result<Unit>, Unit> callback);

    /* compiled from: GeneratedAndroidFirebaseRemoteConfig.g.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007R#\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lio/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigHostApi$Companion;", "", "<init>", "()V", "codec", "Lio/flutter/plugin/common/MessageCodec;", "getCodec", "()Lio/flutter/plugin/common/MessageCodec;", "codec$delegate", "Lkotlin/Lazy;", "setUp", "", "binaryMessenger", "Lio/flutter/plugin/common/BinaryMessenger;", "api", "Lio/flutter/plugins/firebase/firebaseremoteconfig/FirebaseRemoteConfigHostApi;", "messageChannelSuffix", "", "firebase_remote_config_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* renamed from: codec$delegate, reason: from kotlin metadata */
        private static final Lazy<GeneratedAndroidFirebaseRemoteConfigPigeonCodec> codec = LazyKt.lazy(new Function0() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FirebaseRemoteConfigHostApi.Companion.codec_delegate$lambda$0();
            }
        });

        public final void setUp(BinaryMessenger binaryMessenger, FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi) {
            Intrinsics.checkNotNullParameter(binaryMessenger, "binaryMessenger");
            setUp$default(this, binaryMessenger, firebaseRemoteConfigHostApi, null, 4, null);
        }

        private Companion() {
        }

        public final MessageCodec<Object> getCodec() {
            return codec.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final GeneratedAndroidFirebaseRemoteConfigPigeonCodec codec_delegate$lambda$0() {
            return new GeneratedAndroidFirebaseRemoteConfigPigeonCodec();
        }

        public static /* synthetic */ void setUp$default(Companion companion, BinaryMessenger binaryMessenger, FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, String str, int i, Object obj) {
            if ((i & 4) != 0) {
                str = "";
            }
            companion.setUp(binaryMessenger, firebaseRemoteConfigHostApi, str);
        }

        public final void setUp(BinaryMessenger binaryMessenger, final FirebaseRemoteConfigHostApi api, String messageChannelSuffix) {
            String str;
            Intrinsics.checkNotNullParameter(binaryMessenger, "binaryMessenger");
            Intrinsics.checkNotNullParameter(messageChannelSuffix, "messageChannelSuffix");
            if (messageChannelSuffix.length() > 0) {
                str = "." + messageChannelSuffix;
            } else {
                str = "";
            }
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.fetch" + str, getCodec());
            if (api != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$3$lambda$2(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.fetchAndActivate" + str, getCodec());
            if (api != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda10
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$6$lambda$5(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.activate" + str, getCodec());
            if (api != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda11
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$9$lambda$8(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.setConfigSettings" + str, getCodec());
            if (api != null) {
                basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda12
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$12$lambda$11(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel4.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.setDefaults" + str, getCodec());
            if (api != null) {
                basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda13
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$15$lambda$14(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel5.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.ensureInitialized" + str, getCodec());
            if (api != null) {
                basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda14
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$18$lambda$17(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel6.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.setCustomSignals" + str, getCodec());
            if (api != null) {
                basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda15
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$21$lambda$20(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel7.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.getAll" + str, getCodec());
            if (api != null) {
                basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda16
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$24$lambda$23(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel8.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.firebase_remote_config_platform_interface.FirebaseRemoteConfigHostApi.getProperties" + str, getCodec());
            if (api != null) {
                basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda17
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        FirebaseRemoteConfigHostApi.Companion.setUp$lambda$27$lambda$26(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel9.setMessageHandler(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$3$lambda$2(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.fetch((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$3$lambda$2$lambda$1(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$3$lambda$2$lambda$1(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult(null));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$6$lambda$5(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.fetchAndActivate((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$6$lambda$5$lambda$4(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$6$lambda$5$lambda$4(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                Object value = result.getValue();
                if (Result.m485isFailureimpl(value)) {
                    value = null;
                }
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult((Boolean) value));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$9$lambda$8(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.activate((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$9$lambda$8$lambda$7(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$9$lambda$8$lambda$7(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                Object value = result.getValue();
                if (Result.m485isFailureimpl(value)) {
                    value = null;
                }
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult((Boolean) value));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$12$lambda$11(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type io.flutter.plugins.firebase.firebaseremoteconfig.RemoteConfigPigeonSettings");
            firebaseRemoteConfigHostApi.setConfigSettings((String) obj2, (RemoteConfigPigeonSettings) obj3, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj4) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$12$lambda$11$lambda$10(reply, (Result) obj4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$12$lambda$11$lambda$10(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult(null));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$15$lambda$14(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any?>");
            firebaseRemoteConfigHostApi.setDefaults((String) obj2, (Map) obj3, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj4) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$15$lambda$14$lambda$13(reply, (Result) obj4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$15$lambda$14$lambda$13(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult(null));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$18$lambda$17(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.ensureInitialized((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$18$lambda$17$lambda$16(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$18$lambda$17$lambda$16(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult(null));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$21$lambda$20(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any?>");
            firebaseRemoteConfigHostApi.setCustomSignals((String) obj2, (Map) obj3, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj4) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$21$lambda$20$lambda$19(reply, (Result) obj4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$21$lambda$20$lambda$19(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult(null));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$24$lambda$23(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.getAll((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$24$lambda$23$lambda$22(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$24$lambda$23$lambda$22(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                Object value = result.getValue();
                if (Result.m485isFailureimpl(value)) {
                    value = null;
                }
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult((Map) value));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$27$lambda$26(FirebaseRemoteConfigHostApi firebaseRemoteConfigHostApi, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            firebaseRemoteConfigHostApi.getProperties((String) obj2, new Function1() { // from class: io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigHostApi$Companion$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return FirebaseRemoteConfigHostApi.Companion.setUp$lambda$27$lambda$26$lambda$25(reply, (Result) obj3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit setUp$lambda$27$lambda$26$lambda$25(BasicMessageChannel.Reply reply, Result result) {
            Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(result.getValue());
            if (thM482exceptionOrNullimpl != null) {
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapError(thM482exceptionOrNullimpl));
            } else {
                Object value = result.getValue();
                if (Result.m485isFailureimpl(value)) {
                    value = null;
                }
                reply.reply(GeneratedAndroidFirebaseRemoteConfigPigeonUtils.INSTANCE.wrapResult((Map) value));
            }
            return Unit.INSTANCE;
        }
    }
}
