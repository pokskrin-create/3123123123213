package j$.time.chrono;

import j$.time.DateTimeException;

/* loaded from: classes3.dex */
public enum MinguoEra implements Era {
    BEFORE_ROC,
    ROC;

    public static MinguoEra of(int i) {
        if (i == 0) {
            return BEFORE_ROC;
        }
        if (i == 1) {
            return ROC;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    @Override // j$.time.chrono.Era
    public int getValue() {
        return ordinal();
    }
}
