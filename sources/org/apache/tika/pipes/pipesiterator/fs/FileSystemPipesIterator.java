package org.apache.tika.pipes.pipesiterator.fs;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.pipes.FetchEmitTuple;
import org.apache.tika.pipes.HandlerConfig;
import org.apache.tika.pipes.async.AsyncProcessor;
import org.apache.tika.pipes.emitter.EmitKey;
import org.apache.tika.pipes.fetcher.FetchKey;
import org.apache.tika.pipes.pipesiterator.PipesIterator;
import org.apache.tika.pipes.pipesiterator.TotalCountResult;
import org.apache.tika.pipes.pipesiterator.TotalCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class FileSystemPipesIterator extends PipesIterator implements TotalCounter, Initializable, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) AsyncProcessor.class);
    private Path basePath;
    private boolean countTotal = false;
    private FileCountWorker fileCountWorker;

    public FileSystemPipesIterator() {
    }

    public FileSystemPipesIterator(Path path) {
        this.basePath = path;
    }

    @Field
    public void setBasePath(String str) {
        this.basePath = Paths.get(str, new String[0]);
    }

    @Override // org.apache.tika.pipes.pipesiterator.PipesIterator
    protected void enqueue() throws InterruptedException, TimeoutException, IOException {
        if (!Files.isDirectory(this.basePath, new LinkOption[0])) {
            throw new IllegalArgumentException("\"basePath\" directory does not exist: " + this.basePath.toAbsolutePath());
        }
        try {
            Files.walkFileTree(this.basePath, new FSFileVisitor(getFetcherName(), getEmitterName()));
        } catch (IOException e) {
            Throwable cause = e.getCause();
            if (cause != null && (cause instanceof TimeoutException)) {
                throw ((TimeoutException) cause);
            }
            throw e;
        }
    }

    @Override // org.apache.tika.pipes.pipesiterator.PipesIterator, org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
        TikaConfig.mustNotBeEmpty("basePath", this.basePath);
        TikaConfig.mustNotBeEmpty("fetcherName", getFetcherName());
        TikaConfig.mustNotBeEmpty("emitterName", getFetcherName());
    }

    @Override // org.apache.tika.pipes.pipesiterator.PipesIterator, org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
        if (this.countTotal) {
            this.fileCountWorker = new FileCountWorker(this.basePath);
        }
    }

    @Field
    public void setCountTotal(boolean z) {
        this.countTotal = z;
    }

    @Override // org.apache.tika.pipes.pipesiterator.TotalCounter
    public void startTotalCount() {
        if (this.countTotal) {
            this.fileCountWorker.startTotalCount();
        }
    }

    @Override // org.apache.tika.pipes.pipesiterator.TotalCounter
    public TotalCountResult getTotalCount() {
        if (!this.countTotal) {
            return TotalCountResult.UNSUPPORTED;
        }
        return this.fileCountWorker.getTotalCount();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        FileCountWorker fileCountWorker = this.fileCountWorker;
        if (fileCountWorker != null) {
            fileCountWorker.close();
        }
    }

    private class FSFileVisitor implements FileVisitor<Path> {
        private final String emitterName;
        private final String fetcherName;

        @Override // java.nio.file.FileVisitor
        public /* bridge */ /* synthetic */ FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
            return postVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
        }

        @Override // java.nio.file.FileVisitor
        public /* bridge */ /* synthetic */ FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            return preVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
        }

        @Override // java.nio.file.FileVisitor
        public /* bridge */ /* synthetic */ FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            return visitFile2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
        }

        @Override // java.nio.file.FileVisitor
        public /* bridge */ /* synthetic */ FileVisitResult visitFileFailed(Path path, IOException iOException) throws IOException {
            return visitFileFailed2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
        }

        private FSFileVisitor(String str, String str2) {
            this.fetcherName = str;
            this.emitterName = str2;
        }

        /* renamed from: preVisitDirectory, reason: avoid collision after fix types in other method */
        public FileVisitResult preVisitDirectory2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        /* renamed from: visitFile, reason: avoid collision after fix types in other method */
        public FileVisitResult visitFile2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            String string = FileSystemPipesIterator.this.basePath.relativize(path).toString();
            try {
                ParseContext parseContext = new ParseContext();
                parseContext.set(HandlerConfig.class, FileSystemPipesIterator.this.getHandlerConfig());
                FileSystemPipesIterator.this.tryToAdd(new FetchEmitTuple(string, new FetchKey(this.fetcherName, string), new EmitKey(this.emitterName, string), new Metadata(), parseContext, FileSystemPipesIterator.this.getOnParseException()));
                return FileVisitResult.CONTINUE;
            } catch (InterruptedException unused) {
                return FileVisitResult.TERMINATE;
            } catch (TimeoutException e) {
                throw new IOException(e);
            }
        }

        /* renamed from: visitFileFailed, reason: avoid collision after fix types in other method */
        public FileVisitResult visitFileFailed2(Path path, IOException iOException) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        /* renamed from: postVisitDirectory, reason: avoid collision after fix types in other method */
        public FileVisitResult postVisitDirectory2(Path path, IOException iOException) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FileCountWorker implements TotalCounter, Closeable {
        private final Path basePath;
        private TotalCountResult finalResult;
        private Thread totalCounterThread;
        private final AtomicLong totalCount = new AtomicLong(0);
        private TotalCountResult.STATUS status = TotalCountResult.STATUS.NOT_COMPLETED;

        public FileCountWorker(Path path) {
            this.basePath = path;
        }

        @Override // org.apache.tika.pipes.pipesiterator.TotalCounter
        public void startTotalCount() {
            Thread thread = new Thread(new Runnable() { // from class: org.apache.tika.pipes.pipesiterator.fs.FileSystemPipesIterator$FileCountWorker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    this.f$0.m2305x533af4ab();
                }
            });
            this.totalCounterThread = thread;
            thread.setDaemon(true);
            this.totalCounterThread.start();
        }

        /* renamed from: lambda$startTotalCount$0$org-apache-tika-pipes-pipesiterator-fs-FileSystemPipesIterator$FileCountWorker, reason: not valid java name */
        /* synthetic */ void m2305x533af4ab() throws IOException {
            try {
                Files.walkFileTree(this.basePath, new FSFileCounter(this.totalCount));
                this.status = TotalCountResult.STATUS.COMPLETED;
                this.finalResult = new TotalCountResult(this.totalCount.get(), this.status);
            } catch (IOException e) {
                FileSystemPipesIterator.LOG.warn("problem counting files", (Throwable) e);
                this.status = TotalCountResult.STATUS.EXCEPTION;
                this.finalResult = new TotalCountResult(this.totalCount.get(), this.status);
            }
        }

        @Override // org.apache.tika.pipes.pipesiterator.TotalCounter
        public TotalCountResult getTotalCount() {
            TotalCountResult totalCountResult = this.finalResult;
            return totalCountResult != null ? totalCountResult : new TotalCountResult(this.totalCount.get(), this.status);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.totalCounterThread.interrupt();
        }

        private static class FSFileCounter implements FileVisitor<Path> {
            private final AtomicLong count;

            @Override // java.nio.file.FileVisitor
            public /* bridge */ /* synthetic */ FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
                return postVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
            }

            @Override // java.nio.file.FileVisitor
            public /* bridge */ /* synthetic */ FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                return preVisitDirectory2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
            }

            @Override // java.nio.file.FileVisitor
            public /* bridge */ /* synthetic */ FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                return visitFile2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), basicFileAttributes);
            }

            @Override // java.nio.file.FileVisitor
            public /* bridge */ /* synthetic */ FileVisitResult visitFileFailed(Path path, IOException iOException) throws IOException {
                return visitFileFailed2(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1714m((Object) path), iOException);
            }

            private FSFileCounter(AtomicLong atomicLong) {
                this.count = atomicLong;
            }

            /* renamed from: preVisitDirectory, reason: avoid collision after fix types in other method */
            public FileVisitResult preVisitDirectory2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            /* renamed from: visitFile, reason: avoid collision after fix types in other method */
            public FileVisitResult visitFile2(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                this.count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            /* renamed from: visitFileFailed, reason: avoid collision after fix types in other method */
            public FileVisitResult visitFileFailed2(Path path, IOException iOException) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            /* renamed from: postVisitDirectory, reason: avoid collision after fix types in other method */
            public FileVisitResult postVisitDirectory2(Path path, IOException iOException) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        }
    }
}
