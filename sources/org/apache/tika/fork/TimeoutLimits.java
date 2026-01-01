package org.apache.tika.fork;

/* loaded from: classes4.dex */
class TimeoutLimits {
    private final long parseTimeoutMS;
    private final long pulseMS;
    private final long waitTimeoutMS;

    TimeoutLimits(long j, long j2, long j3) {
        this.pulseMS = j;
        this.parseTimeoutMS = j2;
        this.waitTimeoutMS = j3;
    }

    public long getPulseMS() {
        return this.pulseMS;
    }

    public long getParseTimeoutMS() {
        return this.parseTimeoutMS;
    }

    public long getWaitTimeoutMS() {
        return this.waitTimeoutMS;
    }
}
