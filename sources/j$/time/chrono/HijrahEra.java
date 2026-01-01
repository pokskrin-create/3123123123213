package j$.time.chrono;

import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalField;
import j$.time.temporal.ValueRange;

/* loaded from: classes3.dex */
public enum HijrahEra implements Era {
    AH;

    @Override // j$.time.chrono.Era
    public int getValue() {
        return 1;
    }

    @Override // j$.time.chrono.Era, j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return ValueRange.of(1L, 1L);
        }
        return super.range(temporalField);
    }
}
