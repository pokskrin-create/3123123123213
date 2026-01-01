package j$.time.temporal;

/* loaded from: classes3.dex */
public interface Temporal extends TemporalAccessor {
    Temporal plus(long j, TemporalUnit temporalUnit);

    long until(Temporal temporal, TemporalUnit temporalUnit);

    Temporal with(TemporalField temporalField, long j);

    default Temporal with(TemporalAdjuster temporalAdjuster) {
        return temporalAdjuster.adjustInto(this);
    }

    default Temporal plus(TemporalAmount temporalAmount) {
        return temporalAmount.addTo(this);
    }

    default Temporal minus(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : plus(-j, temporalUnit);
    }
}
