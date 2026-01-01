package org.apache.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOFunction;

/* loaded from: classes4.dex */
public enum RandomAccessFileMode {
    READ_ONLY(R, 1),
    READ_WRITE(RW, 2),
    READ_WRITE_SYNC_ALL(RWS, 4),
    READ_WRITE_SYNC_CONTENT(RWD, 3);

    private static final String R = "r";
    private static final String RW = "rw";
    private static final String RWD = "rwd";
    private static final String RWS = "rws";
    private final int level;
    private final String mode;

    /* JADX WARN: Removed duplicated region for block: B:21:0x003e A[PHI: r3
  0x003e: PHI (r3v5 org.apache.commons.io.RandomAccessFileMode) = 
  (r3v4 org.apache.commons.io.RandomAccessFileMode)
  (r3v6 org.apache.commons.io.RandomAccessFileMode)
  (r3v7 org.apache.commons.io.RandomAccessFileMode)
 binds: [B:20:0x003c, B:17:0x0033, B:14:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.apache.commons.io.RandomAccessFileMode valueOf(java.nio.file.OpenOption... r5) {
        /*
            org.apache.commons.io.RandomAccessFileMode r0 = org.apache.commons.io.RandomAccessFileMode.READ_ONLY
            int r1 = r5.length
            r2 = 0
        L4:
            if (r2 >= r1) goto L42
            r3 = r5[r2]
            boolean r4 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2160m(r3)
            if (r4 == 0) goto L3f
            int[] r4 = org.apache.commons.io.RandomAccessFileMode.AnonymousClass1.$SwitchMap$java$nio$file$StandardOpenOption
            java.nio.file.StandardOpenOption r3 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2144m(r3)
            int r3 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r3)
            r3 = r4[r3]
            r4 = 1
            if (r3 == r4) goto L36
            r4 = 2
            if (r3 == r4) goto L2d
            r4 = 3
            if (r3 == r4) goto L24
            goto L3f
        L24:
            org.apache.commons.io.RandomAccessFileMode r3 = org.apache.commons.io.RandomAccessFileMode.READ_WRITE_SYNC_ALL
            boolean r4 = r0.implies(r3)
            if (r4 != 0) goto L3f
            goto L3e
        L2d:
            org.apache.commons.io.RandomAccessFileMode r3 = org.apache.commons.io.RandomAccessFileMode.READ_WRITE_SYNC_CONTENT
            boolean r4 = r0.implies(r3)
            if (r4 != 0) goto L3f
            goto L3e
        L36:
            org.apache.commons.io.RandomAccessFileMode r3 = org.apache.commons.io.RandomAccessFileMode.READ_WRITE
            boolean r4 = r0.implies(r3)
            if (r4 != 0) goto L3f
        L3e:
            r0 = r3
        L3f:
            int r2 = r2 + 1
            goto L4
        L42:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.RandomAccessFileMode.valueOf(java.nio.file.OpenOption[]):org.apache.commons.io.RandomAccessFileMode");
    }

    /* renamed from: org.apache.commons.io.RandomAccessFileMode$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$StandardOpenOption;

        static {
            int[] iArr = new int[StandardOpenOption.values().length];
            $SwitchMap$java$nio$file$StandardOpenOption = iArr;
            try {
                iArr[StandardOpenOption.WRITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.DSYNC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.SYNC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static RandomAccessFileMode valueOfMode(String str) {
        str.hashCode();
        switch (str) {
            case "r":
                return READ_ONLY;
            case "rw":
                return READ_WRITE;
            case "rwd":
                return READ_WRITE_SYNC_CONTENT;
            case "rws":
                return READ_WRITE_SYNC_ALL;
            default:
                throw new IllegalArgumentException(str);
        }
    }

    RandomAccessFileMode(String str, int i) {
        this.mode = str;
        this.level = i;
    }

    public void accept(Path path, IOConsumer<RandomAccessFile> iOConsumer) throws IOException {
        RandomAccessFile randomAccessFileCreate = create(path);
        try {
            iOConsumer.accept(randomAccessFileCreate);
            if (randomAccessFileCreate != null) {
                randomAccessFileCreate.close();
            }
        } catch (Throwable th) {
            if (randomAccessFileCreate != null) {
                try {
                    randomAccessFileCreate.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public <T> T apply(Path path, IOFunction<RandomAccessFile, T> iOFunction) throws IOException {
        RandomAccessFile randomAccessFileCreate = create(path);
        try {
            T tApply = iOFunction.apply(randomAccessFileCreate);
            if (randomAccessFileCreate != null) {
                randomAccessFileCreate.close();
            }
            return tApply;
        } catch (Throwable th) {
            if (randomAccessFileCreate != null) {
                try {
                    randomAccessFileCreate.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public RandomAccessFile create(File file) throws FileNotFoundException {
        return new IORandomAccessFile(file, this.mode);
    }

    public RandomAccessFile create(Path path) throws FileNotFoundException {
        return create((File) Objects.requireNonNull(path.toFile(), "file"));
    }

    public RandomAccessFile create(String str) throws FileNotFoundException {
        return new IORandomAccessFile(str, this.mode);
    }

    private int getLevel() {
        return this.level;
    }

    public String getMode() {
        return this.mode;
    }

    public boolean implies(RandomAccessFileMode randomAccessFileMode) {
        return getLevel() >= randomAccessFileMode.getLevel();
    }

    public IORandomAccessFile io(String str) throws FileNotFoundException {
        return new IORandomAccessFile(str, this.mode);
    }
}
