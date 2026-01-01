package org.apache.commons.io.function;

import java.io.IOException;

/* loaded from: classes4.dex */
final class Constants {
    static final IOBiConsumer IO_BI_CONSUMER = new IOBiConsumer() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda0
        @Override // org.apache.commons.io.function.IOBiConsumer
        public final void accept(Object obj, Object obj2) throws IOException {
            Constants.lambda$static$0(obj, obj2);
        }
    };
    static final IORunnable IO_RUNNABLE = new IORunnable() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda1
        @Override // org.apache.commons.io.function.IORunnable
        public final void run() throws IOException {
            Constants.lambda$static$1();
        }
    };
    static final IOBiFunction IO_BI_FUNCTION = new IOBiFunction() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda2
        @Override // org.apache.commons.io.function.IOBiFunction
        public final Object apply(Object obj, Object obj2) {
            return Constants.lambda$static$2(obj, obj2);
        }
    };
    static final IOFunction IO_FUNCTION_ID = new IOFunction() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda3
        @Override // org.apache.commons.io.function.IOFunction
        public final Object apply(Object obj) {
            return Constants.lambda$static$3(obj);
        }
    };
    static final IOPredicate<Object> IO_PREDICATE_FALSE = new IOPredicate() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda4
        @Override // org.apache.commons.io.function.IOPredicate
        public final boolean test(Object obj) {
            return Constants.lambda$static$4(obj);
        }
    };
    static final IOPredicate<Object> IO_PREDICATE_TRUE = new IOPredicate() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda5
        @Override // org.apache.commons.io.function.IOPredicate
        public final boolean test(Object obj) {
            return Constants.lambda$static$5(obj);
        }
    };
    static final IOTriConsumer IO_TRI_CONSUMER = new IOTriConsumer() { // from class: org.apache.commons.io.function.Constants$$ExternalSyntheticLambda6
        @Override // org.apache.commons.io.function.IOTriConsumer
        public final void accept(Object obj, Object obj2, Object obj3) throws IOException {
            Constants.lambda$static$6(obj, obj2, obj3);
        }
    };

    static /* synthetic */ void lambda$static$0(Object obj, Object obj2) throws IOException {
    }

    static /* synthetic */ void lambda$static$1() throws IOException {
    }

    static /* synthetic */ Object lambda$static$2(Object obj, Object obj2) throws IOException {
        return null;
    }

    static /* synthetic */ Object lambda$static$3(Object obj) throws IOException {
        return obj;
    }

    static /* synthetic */ boolean lambda$static$4(Object obj) throws IOException {
        return false;
    }

    static /* synthetic */ boolean lambda$static$5(Object obj) throws IOException {
        return true;
    }

    static /* synthetic */ void lambda$static$6(Object obj, Object obj2, Object obj3) throws IOException {
    }

    private Constants() {
    }
}
