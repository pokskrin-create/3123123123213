package org.apache.tika.pipes.async;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.pipes.PipesConfigBase;
import org.apache.tika.pipes.PipesReporter;

/* loaded from: classes4.dex */
public class AsyncConfig extends PipesConfigBase {
    private long emitWithinMillis = WorkRequest.MIN_BACKOFF_MILLIS;
    private long emitMaxEstimatedBytes = PipesConfigBase.DEFAULT_MAX_FOR_EMIT_BATCH;
    private int queueSize = PipesConfigBase.DEFAULT_MAX_FILES_PROCESSED_PER_PROCESS;
    private int numEmitters = 1;
    private boolean emitIntermediateResults = false;
    private PipesReporter pipesReporter = PipesReporter.NO_OP_REPORTER;

    public static AsyncConfig load(Path path) throws IOException, TikaConfigException {
        AsyncConfig asyncConfig = new AsyncConfig();
        InputStream inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
        try {
            asyncConfig.configure("async", inputStreamNewInputStream);
            if (inputStreamNewInputStream != null) {
                inputStreamNewInputStream.close();
            }
            if (asyncConfig.getTikaConfig() == null) {
                asyncConfig.setTikaConfig(path);
            }
            return asyncConfig;
        } catch (Throwable th) {
            if (inputStreamNewInputStream != null) {
                try {
                    inputStreamNewInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long getEmitWithinMillis() {
        return this.emitWithinMillis;
    }

    public void setEmitWithinMillis(long j) {
        this.emitWithinMillis = j;
    }

    public long getEmitMaxEstimatedBytes() {
        return this.emitMaxEstimatedBytes;
    }

    public void setEmitMaxEstimatedBytes(long j) {
        this.emitMaxEstimatedBytes = j;
    }

    public void setNumEmitters(int i) {
        this.numEmitters = i;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public void setQueueSize(int i) {
        this.queueSize = i;
    }

    public int getNumEmitters() {
        return this.numEmitters;
    }

    public PipesReporter getPipesReporter() {
        return this.pipesReporter;
    }

    public void setPipesReporter(PipesReporter pipesReporter) {
        this.pipesReporter = pipesReporter;
    }

    public void setEmitIntermediateResults(boolean z) {
        this.emitIntermediateResults = z;
    }

    public boolean isEmitIntermediateResults() {
        return this.emitIntermediateResults;
    }
}
