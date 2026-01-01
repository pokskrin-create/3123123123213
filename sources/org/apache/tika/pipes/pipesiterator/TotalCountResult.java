package org.apache.tika.pipes.pipesiterator;

/* loaded from: classes4.dex */
public class TotalCountResult {
    public static TotalCountResult UNSUPPORTED = new TotalCountResult(-1, STATUS.UNSUPPORTED);
    private STATUS status;
    private long totalCount;

    public enum STATUS {
        UNSUPPORTED,
        EXCEPTION,
        NOT_COMPLETED,
        COMPLETED
    }

    public TotalCountResult() {
        this.totalCount = 0L;
        this.status = STATUS.NOT_COMPLETED;
    }

    public TotalCountResult(long j, STATUS status) {
        this.totalCount = j;
        this.status = status;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public STATUS getStatus() {
        return this.status;
    }

    public String toString() {
        return "TotalCountResult{totalCount=" + this.totalCount + ", status=" + this.status + "}";
    }
}
