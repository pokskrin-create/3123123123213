package org.apache.tika.pipes;

import java.io.Closeable;
import java.io.IOException;
import org.apache.tika.pipes.pipesiterator.TotalCountResult;

/* loaded from: classes4.dex */
public abstract class PipesReporter implements Closeable {
    public static final PipesReporter NO_OP_REPORTER = new PipesReporter() { // from class: org.apache.tika.pipes.PipesReporter.1
        @Override // org.apache.tika.pipes.PipesReporter
        public void error(String str) {
        }

        @Override // org.apache.tika.pipes.PipesReporter
        public void error(Throwable th) {
        }

        @Override // org.apache.tika.pipes.PipesReporter
        public void report(FetchEmitTuple fetchEmitTuple, PipesResult pipesResult, long j) {
        }
    };

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public abstract void error(String str);

    public abstract void error(Throwable th);

    public abstract void report(FetchEmitTuple fetchEmitTuple, PipesResult pipesResult, long j);

    public void report(TotalCountResult totalCountResult) {
    }

    public boolean supportsTotalCount() {
        return false;
    }
}
