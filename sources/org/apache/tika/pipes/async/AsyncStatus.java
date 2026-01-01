package org.apache.tika.pipes.async;

import j$.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.apache.tika.pipes.PipesResult;
import org.apache.tika.pipes.pipesiterator.TotalCountResult;

/* loaded from: classes4.dex */
public class AsyncStatus {
    private Instant lastUpdate;
    private final Instant started;
    private TotalCountResult totalCountResult = new TotalCountResult(0, TotalCountResult.STATUS.NOT_COMPLETED);
    private Map<PipesResult.STATUS, Long> statusCounts = new HashMap();
    private ASYNC_STATUS asyncStatus = ASYNC_STATUS.STARTED;
    private String crashMessage = "";

    public enum ASYNC_STATUS {
        STARTED,
        COMPLETED,
        CRASHED
    }

    public AsyncStatus() {
        Instant instantNow = Instant.now();
        this.started = instantNow;
        this.lastUpdate = instantNow;
    }

    public synchronized void update(Map<PipesResult.STATUS, Long> map, TotalCountResult totalCountResult, ASYNC_STATUS async_status) {
        this.lastUpdate = Instant.now();
        this.statusCounts = map;
        this.totalCountResult = totalCountResult;
        this.asyncStatus = async_status;
    }

    public void updateCrash(String str) {
        this.crashMessage = str;
    }

    public Instant getStarted() {
        return this.started;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public TotalCountResult getTotalCountResult() {
        return this.totalCountResult;
    }

    public Map<PipesResult.STATUS, Long> getStatusCounts() {
        return this.statusCounts;
    }

    public ASYNC_STATUS getAsyncStatus() {
        return this.asyncStatus;
    }

    public String getCrashMessage() {
        return this.crashMessage;
    }

    public String toString() {
        return "AsyncStatus{started=" + this.started + ", lastUpdate=" + this.lastUpdate + ", totalCountResult=" + this.totalCountResult + ", statusCounts=" + this.statusCounts + ", asyncStatus=" + this.asyncStatus + ", crashMessage='" + this.crashMessage + "'}";
    }
}
