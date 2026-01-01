package org.apache.commons.io.input;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
final class ByteBufferCleaner {
    private static final Cleaner INSTANCE = getCleaner();

    private interface Cleaner {
        void clean(ByteBuffer byteBuffer) throws ReflectiveOperationException;
    }

    ByteBufferCleaner() {
    }

    private static final class Java8Cleaner implements Cleaner {
        private final Method cleanMethod;
        private final Method cleanerMethod;

        private Java8Cleaner() throws SecurityException, ReflectiveOperationException {
            this.cleanMethod = Class.forName("sun.misc.Cleaner").getMethod("clean", null);
            this.cleanerMethod = Class.forName("sun.nio.ch.DirectBuffer").getMethod("cleaner", null);
        }

        @Override // org.apache.commons.io.input.ByteBufferCleaner.Cleaner
        public void clean(ByteBuffer byteBuffer) throws ReflectiveOperationException, IllegalArgumentException {
            Object objInvoke = this.cleanerMethod.invoke(byteBuffer, null);
            if (objInvoke != null) {
                this.cleanMethod.invoke(objInvoke, null);
            }
        }
    }

    private static final class Java9Cleaner implements Cleaner {
        private final Method invokeCleaner;
        private final Object theUnsafe;

        private Java9Cleaner() throws SecurityException, ReflectiveOperationException {
            Class<?> cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            this.theUnsafe = declaredField.get(null);
            this.invokeCleaner = cls.getMethod("invokeCleaner", ByteBuffer.class);
        }

        @Override // org.apache.commons.io.input.ByteBufferCleaner.Cleaner
        public void clean(ByteBuffer byteBuffer) throws ReflectiveOperationException, IllegalArgumentException {
            this.invokeCleaner.invoke(this.theUnsafe, byteBuffer);
        }
    }

    static void clean(ByteBuffer byteBuffer) {
        try {
            INSTANCE.clean(byteBuffer);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to clean direct buffer.", e);
        }
    }

    private static Cleaner getCleaner() {
        try {
            return new Java8Cleaner();
        } catch (Exception e) {
            try {
                return new Java9Cleaner();
            } catch (Exception unused) {
                throw new IllegalStateException("Failed to initialize a Cleaner.", e);
            }
        }
    }

    static boolean isSupported() {
        return INSTANCE != null;
    }
}
