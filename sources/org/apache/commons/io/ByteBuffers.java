package org.apache.commons.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public final class ByteBuffers {
    public static ByteBuffer littleEndian(byte[] bArr) {
        return littleEndian(ByteBuffer.wrap(bArr));
    }

    public static ByteBuffer littleEndian(ByteBuffer byteBuffer) {
        return byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuffer littleEndian(int i) {
        return littleEndian(ByteBuffer.allocate(i));
    }

    private ByteBuffers() {
    }
}
