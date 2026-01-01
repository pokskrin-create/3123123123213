package org.apache.tika.pipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.pipes.pipesiterator.TotalCountResult;

/* loaded from: classes4.dex */
public class CompositePipesReporter extends PipesReporter implements Initializable {
    private List<PipesReporter> pipesReporters = new ArrayList();

    @Override // org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void report(FetchEmitTuple fetchEmitTuple, PipesResult pipesResult, long j) {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        while (it.hasNext()) {
            it.next().report(fetchEmitTuple, pipesResult, j);
        }
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void report(TotalCountResult totalCountResult) {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        while (it.hasNext()) {
            it.next().report(totalCountResult);
        }
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public boolean supportsTotalCount() {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        while (it.hasNext()) {
            if (it.next().supportsTotalCount()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void error(Throwable th) {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        while (it.hasNext()) {
            it.next().error(th);
        }
    }

    @Override // org.apache.tika.pipes.PipesReporter
    public void error(String str) {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        while (it.hasNext()) {
            it.next().error(str);
        }
    }

    @Field
    public void addPipesReporter(PipesReporter pipesReporter) {
        this.pipesReporters.add(pipesReporter);
    }

    public List<PipesReporter> getPipesReporters() {
        return this.pipesReporters;
    }

    @Override // org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
        List<PipesReporter> list = this.pipesReporters;
        if (list == null) {
            throw new TikaConfigException("must specify 'pipesReporters'");
        }
        if (list.size() == 0) {
            throw new TikaConfigException("must specify at least one pipes reporter");
        }
    }

    @Override // org.apache.tika.pipes.PipesReporter, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Iterator<PipesReporter> it = this.pipesReporters.iterator();
        IOException e = null;
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException e2) {
                e = e2;
            }
        }
        if (e != null) {
            throw e;
        }
    }
}
