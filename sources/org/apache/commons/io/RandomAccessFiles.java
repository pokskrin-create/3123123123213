package org.apache.commons.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.SeekableByteChannel;
import java.util.Objects;
import org.apache.commons.io.channels.FileChannels;
import org.apache.commons.io.function.IOTriFunction;

/* loaded from: classes4.dex */
public class RandomAccessFiles {
    public static boolean contentEquals(RandomAccessFile randomAccessFile, RandomAccessFile randomAccessFile2) throws IOException {
        if (Objects.equals(randomAccessFile, randomAccessFile2)) {
            return true;
        }
        long length = length(randomAccessFile);
        long length2 = length(randomAccessFile2);
        if (length != length2) {
            return false;
        }
        if (length == 0 && length2 == 0) {
            return true;
        }
        return FileChannels.contentEquals((SeekableByteChannel) randomAccessFile.getChannel(), (SeekableByteChannel) randomAccessFile2.getChannel(), 8192);
    }

    private static long length(RandomAccessFile randomAccessFile) throws IOException {
        if (randomAccessFile != null) {
            return randomAccessFile.length();
        }
        return 0L;
    }

    public static byte[] read(final RandomAccessFile randomAccessFile, long j, int i) throws IOException {
        randomAccessFile.seek(j);
        Objects.requireNonNull(randomAccessFile);
        return IOUtils.toByteArray((IOTriFunction<byte[], Integer, Integer, Integer>) new IOTriFunction() { // from class: org.apache.commons.io.RandomAccessFiles$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOTriFunction
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return Integer.valueOf(randomAccessFile.read((byte[]) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue()));
            }
        }, i);
    }

    public static RandomAccessFile reset(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(0L);
        return randomAccessFile;
    }

    @Deprecated
    public RandomAccessFiles() {
    }
}
