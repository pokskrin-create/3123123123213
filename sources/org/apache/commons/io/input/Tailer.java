package org.apache.commons.io.input;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import j$.time.Duration;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.ThreadUtils;
import org.apache.commons.io.build.AbstractOrigin;
import org.apache.commons.io.build.AbstractOriginSupplier;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.file.attribute.FileTimes;
import org.apache.commons.io.input.Tailer;

/* loaded from: classes4.dex */
public class Tailer implements Runnable, AutoCloseable {
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_READ_ONLY_MODE = "r";
    private final Charset charset;
    private final Duration delayDuration;
    private final byte[] inbuf;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;
    private final boolean tailAtEnd;
    private final Tailable tailable;

    public interface RandomAccessResourceBridge extends Closeable {
        long getPointer() throws IOException;

        int read(byte[] bArr) throws IOException;

        void seek(long j) throws IOException;
    }

    public interface Tailable {
        RandomAccessResourceBridge getRandomAccess(String str) throws FileNotFoundException;

        boolean isNewer(FileTime fileTime) throws IOException;

        FileTime lastModifiedFileTime() throws IOException;

        long size() throws IOException;
    }

    public static class Builder extends AbstractStreamBuilder<Tailer, Builder> {
        private static final Duration DEFAULT_DELAY_DURATION = Duration.ofMillis(1000);
        private boolean reOpen;
        private boolean tailFromEnd;
        private Tailable tailable;
        private TailerListener tailerListener;
        private Duration delayDuration = DEFAULT_DELAY_DURATION;
        private boolean startThread = true;
        private ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: org.apache.commons.io.input.Tailer$Builder$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return Tailer.Builder.newDaemonThread(runnable);
            }
        });

        @Override // org.apache.commons.io.build.AbstractOriginSupplier
        protected /* bridge */ /* synthetic */ AbstractOriginSupplier setOrigin(AbstractOrigin abstractOrigin) {
            return setOrigin((AbstractOrigin<?, ?>) abstractOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Thread newDaemonThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "commons-io-tailer");
            thread.setDaemon(true);
            return thread;
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public Tailer get() {
            Tailer tailer = new Tailer(this.tailable, getCharset(), this.tailerListener, this.delayDuration, this.tailFromEnd, this.reOpen, getBufferSize());
            if (this.startThread) {
                this.executorService.submit(tailer);
            }
            return tailer;
        }

        public Builder setDelayDuration(Duration duration) {
            if (duration == null) {
                duration = DEFAULT_DELAY_DURATION;
            }
            this.delayDuration = duration;
            return this;
        }

        public Builder setExecutorService(ExecutorService executorService) {
            this.executorService = (ExecutorService) Objects.requireNonNull(executorService, "executorService");
            return this;
        }

        @Override // org.apache.commons.io.build.AbstractOriginSupplier
        protected Builder setOrigin(AbstractOrigin<?, ?> abstractOrigin) {
            setTailable(new TailablePath(abstractOrigin.getPath(), new LinkOption[0]));
            return (Builder) super.setOrigin(abstractOrigin);
        }

        public Builder setReOpen(boolean z) {
            this.reOpen = z;
            return this;
        }

        public Builder setStartThread(boolean z) {
            this.startThread = z;
            return this;
        }

        public Builder setTailable(Tailable tailable) {
            this.tailable = (Tailable) Objects.requireNonNull(tailable, "tailable");
            return this;
        }

        public Builder setTailerListener(TailerListener tailerListener) {
            this.tailerListener = (TailerListener) Objects.requireNonNull(tailerListener, "tailerListener");
            return this;
        }

        public Builder setTailFromEnd(boolean z) {
            this.tailFromEnd = z;
            return this;
        }
    }

    private static final class RandomAccessFileBridge implements RandomAccessResourceBridge {
        private final RandomAccessFile randomAccessFile;

        private RandomAccessFileBridge(File file, String str) throws FileNotFoundException {
            this.randomAccessFile = new RandomAccessFile(file, str);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.randomAccessFile.close();
        }

        @Override // org.apache.commons.io.input.Tailer.RandomAccessResourceBridge
        public long getPointer() throws IOException {
            return this.randomAccessFile.getFilePointer();
        }

        @Override // org.apache.commons.io.input.Tailer.RandomAccessResourceBridge
        public int read(byte[] bArr) throws IOException {
            return this.randomAccessFile.read(bArr);
        }

        @Override // org.apache.commons.io.input.Tailer.RandomAccessResourceBridge
        public void seek(long j) throws IOException {
            this.randomAccessFile.seek(j);
        }
    }

    private static final class TailablePath implements Tailable {
        private final LinkOption[] linkOptions;
        private final Path path;

        private TailablePath(Path path, LinkOption... linkOptionArr) {
            this.path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m(Objects.requireNonNull(path, "path"));
            this.linkOptions = linkOptionArr;
        }

        Path getPath() {
            return this.path;
        }

        @Override // org.apache.commons.io.input.Tailer.Tailable
        public RandomAccessResourceBridge getRandomAccess(String str) throws FileNotFoundException {
            return new RandomAccessFileBridge(this.path.toFile(), str);
        }

        @Override // org.apache.commons.io.input.Tailer.Tailable
        public boolean isNewer(FileTime fileTime) throws IOException {
            return PathUtils.isNewer(this.path, fileTime, this.linkOptions);
        }

        @Override // org.apache.commons.io.input.Tailer.Tailable
        public FileTime lastModifiedFileTime() throws IOException {
            return Files.getLastModifiedTime(this.path, this.linkOptions);
        }

        @Override // org.apache.commons.io.input.Tailer.Tailable
        public long size() throws IOException {
            return Files.size(this.path);
        }

        public String toString() {
            return "TailablePath [file=" + this.path + ", linkOptions=" + Arrays.toString(this.linkOptions) + "]";
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, Charset charset, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setCharset(charset).setDelayDuration(Duration.ofMillis(j)).setTailFromEnd(z).setReOpen(z2).setBufferSize(i).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener, long j) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setDelayDuration(Duration.ofMillis(j)).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setDelayDuration(Duration.ofMillis(j)).setTailFromEnd(z).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, boolean z2) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setDelayDuration(Duration.ofMillis(j)).setTailFromEnd(z).setReOpen(z2).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setDelayDuration(Duration.ofMillis(j)).setTailFromEnd(z).setReOpen(z2).setBufferSize(i).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, int i) {
        return ((Builder) builder().setFile(file)).setTailerListener(tailerListener).setDelayDuration(Duration.ofMillis(j)).setTailFromEnd(z).setBufferSize(i).get();
    }

    @Deprecated
    public Tailer(File file, Charset charset, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        this(new TailablePath(file.toPath(), new LinkOption[0]), charset, tailerListener, Duration.ofMillis(j), z, z2, i);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener, long j) {
        this(file, tailerListener, j, false);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener, long j, boolean z) {
        this(file, tailerListener, j, z, 8192);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener, long j, boolean z, boolean z2) {
        this(file, tailerListener, j, z, z2, 8192);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        this(file, DEFAULT_CHARSET, tailerListener, j, z, z2, i);
    }

    @Deprecated
    public Tailer(File file, TailerListener tailerListener, long j, boolean z, int i) {
        this(file, tailerListener, j, z, false, i);
    }

    private Tailer(Tailable tailable, Charset charset, TailerListener tailerListener, Duration duration, boolean z, boolean z2, int i) {
        this.run = true;
        this.tailable = (Tailable) Objects.requireNonNull(tailable, "tailable");
        this.listener = (TailerListener) Objects.requireNonNull(tailerListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.delayDuration = duration;
        this.tailAtEnd = z;
        this.inbuf = IOUtils.byteArray(i);
        tailerListener.init(this);
        this.reOpen = z2;
        this.charset = charset;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.run = false;
    }

    @Deprecated
    public long getDelay() {
        return this.delayDuration.toMillis();
    }

    public Duration getDelayDuration() {
        return this.delayDuration;
    }

    public File getFile() {
        Tailable tailable = this.tailable;
        if (tailable instanceof TailablePath) {
            return ((TailablePath) tailable).getPath().toFile();
        }
        throw new IllegalStateException("Cannot extract java.io.File from " + this.tailable.getClass().getName());
    }

    protected boolean getRun() {
        return this.run;
    }

    public Tailable getTailable() {
        return this.tailable;
    }

    private long readLines(RandomAccessResourceBridge randomAccessResourceBridge) throws IOException {
        int i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64);
        try {
            long pointer = randomAccessResourceBridge.getPointer();
            long pointer2 = pointer;
            boolean z = false;
            while (getRun() && (i = randomAccessResourceBridge.read(this.inbuf)) != -1) {
                for (int i2 = 0; i2 < i; i2++) {
                    byte b = this.inbuf[i2];
                    if (b == 10) {
                        this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.charset));
                        byteArrayOutputStream.reset();
                        pointer = i2 + pointer2 + 1;
                        z = false;
                    } else if (b != 13) {
                        if (z) {
                            this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.charset));
                            byteArrayOutputStream.reset();
                            pointer = i2 + pointer2 + 1;
                            z = false;
                        }
                        byteArrayOutputStream.write(b);
                    } else {
                        if (z) {
                            byteArrayOutputStream.write(13);
                        }
                        z = true;
                    }
                }
                pointer2 = randomAccessResourceBridge.getPointer();
            }
            randomAccessResourceBridge.seek(pointer);
            TailerListener tailerListener = this.listener;
            if (tailerListener instanceof TailerListenerAdapter) {
                ((TailerListenerAdapter) tailerListener).endOfFileReached();
            }
            byteArrayOutputStream.close();
            return pointer;
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        RandomAccessResourceBridge randomAccess;
        RandomAccessResourceBridge randomAccess2 = null;
        try {
            try {
                FileTime fileTimeLastModifiedFileTime = FileTimes.EPOCH;
                long lines = 0;
                while (getRun() && randomAccess2 == null) {
                    try {
                        randomAccess2 = this.tailable.getRandomAccess(RAF_READ_ONLY_MODE);
                    } catch (FileNotFoundException unused) {
                        this.listener.fileNotFound();
                    }
                    if (randomAccess2 == null) {
                        ThreadUtils.sleep(this.delayDuration);
                    } else {
                        lines = this.tailAtEnd ? this.tailable.size() : 0L;
                        fileTimeLastModifiedFileTime = this.tailable.lastModifiedFileTime();
                        randomAccess2.seek(lines);
                    }
                }
                while (getRun()) {
                    boolean zIsNewer = this.tailable.isNewer(fileTimeLastModifiedFileTime);
                    long size = this.tailable.size();
                    if (size < lines) {
                        this.listener.fileRotated();
                        try {
                            randomAccess = this.tailable.getRandomAccess(RAF_READ_ONLY_MODE);
                            try {
                                try {
                                    readLines(randomAccess2);
                                } catch (Throwable th) {
                                    th = th;
                                    if (randomAccess2 != null) {
                                        try {
                                            randomAccess2.close();
                                        } catch (Throwable th2) {
                                            try {
                                                th.addSuppressed(th2);
                                            } catch (FileNotFoundException unused2) {
                                                randomAccess2 = randomAccess;
                                                this.listener.fileNotFound();
                                                ThreadUtils.sleep(this.delayDuration);
                                            }
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException e) {
                                this.listener.handle(e);
                            }
                            if (randomAccess2 != null) {
                                try {
                                    try {
                                        randomAccess2.close();
                                    } catch (InterruptedException e2) {
                                        e = e2;
                                        randomAccess2 = randomAccess;
                                        Thread.currentThread().interrupt();
                                        this.listener.handle(e);
                                        IOUtils.close(randomAccess2);
                                        close();
                                    } catch (Exception e3) {
                                        e = e3;
                                        randomAccess2 = randomAccess;
                                        this.listener.handle(e);
                                        IOUtils.close(randomAccess2);
                                        close();
                                    } catch (Throwable th3) {
                                        th = th3;
                                        randomAccess2 = randomAccess;
                                        try {
                                            IOUtils.close(randomAccess2);
                                        } catch (IOException e4) {
                                            this.listener.handle(e4);
                                        }
                                        close();
                                        throw th;
                                    }
                                } catch (FileNotFoundException unused3) {
                                    lines = 0;
                                    randomAccess2 = randomAccess;
                                    this.listener.fileNotFound();
                                    ThreadUtils.sleep(this.delayDuration);
                                }
                            }
                            lines = 0;
                            randomAccess2 = randomAccess;
                        } catch (Throwable th4) {
                            th = th4;
                            randomAccess = randomAccess2;
                        }
                    } else {
                        if (size > lines) {
                            lines = readLines(randomAccess2);
                            fileTimeLastModifiedFileTime = this.tailable.lastModifiedFileTime();
                        } else if (zIsNewer) {
                            randomAccess2.seek(0L);
                            lines = readLines(randomAccess2);
                            fileTimeLastModifiedFileTime = this.tailable.lastModifiedFileTime();
                        }
                        if (this.reOpen && randomAccess2 != null) {
                            randomAccess2.close();
                        }
                        ThreadUtils.sleep(this.delayDuration);
                        if (getRun() && this.reOpen) {
                            randomAccess2 = this.tailable.getRandomAccess(RAF_READ_ONLY_MODE);
                            randomAccess2.seek(lines);
                        }
                    }
                }
            } catch (InterruptedException e5) {
                e = e5;
            } catch (Exception e6) {
                e = e6;
            }
            try {
                IOUtils.close(randomAccess2);
            } catch (IOException e7) {
                this.listener.handle(e7);
            }
            close();
        } catch (Throwable th5) {
            th = th5;
        }
    }

    @Deprecated
    public void stop() {
        close();
    }
}
