package org.apache.commons.io.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class FileChannels {
    @Deprecated
    public static boolean contentEquals(FileChannel fileChannel, FileChannel fileChannel2, int i) throws IOException {
        return contentEquals((SeekableByteChannel) fileChannel, (SeekableByteChannel) fileChannel2, i);
    }

    public static boolean contentEquals(ReadableByteChannel readableByteChannel, ReadableByteChannel readableByteChannel2, int i) throws IOException {
        if (Objects.equals(readableByteChannel, readableByteChannel2)) {
            return true;
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i);
        ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(i);
        boolean z = false;
        boolean z2 = false;
        int toLimit = 0;
        int toLimit2 = 0;
        while (true) {
            if (!z) {
                toLimit = readToLimit(readableByteChannel, byteBufferAllocateDirect);
                byteBufferAllocateDirect.clear();
                z2 = toLimit == 0;
            }
            if (!z2) {
                toLimit2 = readToLimit(readableByteChannel2, byteBufferAllocateDirect2);
                byteBufferAllocateDirect2.clear();
                z = toLimit2 == 0;
            }
            if (toLimit == -1 && toLimit2 == -1) {
                return byteBufferAllocateDirect.equals(byteBufferAllocateDirect2);
            }
            if (toLimit == 0 || toLimit2 == 0) {
                Thread.yield();
            } else if (toLimit != toLimit2 || !byteBufferAllocateDirect.equals(byteBufferAllocateDirect2)) {
                return false;
            }
        }
    }

    public static boolean contentEquals(SeekableByteChannel seekableByteChannel, SeekableByteChannel seekableByteChannel2, int i) throws IOException {
        if (Objects.equals(seekableByteChannel, seekableByteChannel2)) {
            return true;
        }
        long size = size(seekableByteChannel);
        long size2 = size(seekableByteChannel2);
        if (size != size2) {
            return false;
        }
        return (size == 0 && size2 == 0) || contentEquals((ReadableByteChannel) seekableByteChannel, (ReadableByteChannel) seekableByteChannel2, i);
    }

    private static int readToLimit(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int i;
        if (!byteBuffer.hasRemaining()) {
            throw new IllegalArgumentException();
        }
        int i2 = 0;
        while (byteBuffer.hasRemaining() && (i = readableByteChannel.read(byteBuffer)) != -1) {
            if (i == 0) {
                Thread.yield();
            } else {
                i2 += i;
            }
        }
        if (i2 != 0) {
            return i2;
        }
        return -1;
    }

    private static long size(SeekableByteChannel seekableByteChannel) throws IOException {
        if (seekableByteChannel != null) {
            return seekableByteChannel.size();
        }
        return 0L;
    }

    private FileChannels() {
    }
}
