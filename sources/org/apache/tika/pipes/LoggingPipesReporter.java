package org.apache.tika.pipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class LoggingPipesReporter extends PipesReporter {
    Logger LOGGER = LoggerFactory.getLogger((Class<?>) LoggingPipesReporter.class);

    @Override // org.apache.tika.pipes.PipesReporter
    public void report(FetchEmitTuple fetchEmitTuple, PipesResult pipesResult, long j) {
        this.LOGGER.debug("{} {} {}", fetchEmitTuple, pipesResult, Long.valueOf(j));
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void error(Throwable th) {
        this.LOGGER.error("pipes error", th);
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void error(String str) {
        this.LOGGER.error("error {}", str);
    }
}
