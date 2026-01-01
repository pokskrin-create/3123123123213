package org.apache.tika.detect;

/* loaded from: classes4.dex */
public class TextStatistics {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int[] counts = new int[256];
    private int total = 0;

    public void addData(byte[] bArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            int[] iArr = this.counts;
            int i4 = bArr[i + i3] & 255;
            iArr[i4] = iArr[i4] + 1;
            this.total++;
        }
    }

    public boolean isMostlyAscii() {
        int iCount = count(0, 32);
        int iCount2 = count(32, 128);
        int iCountSafeControl = countSafeControl();
        int i = this.total;
        return i > 0 && (iCount - iCountSafeControl) * 100 < i * 2 && (iCount2 + iCountSafeControl) * 100 > i * 90;
    }

    public boolean looksLikeUTF8() {
        int iCount = count(0, 32);
        int iCount2 = count(32, 128);
        int iCountSafeControl = countSafeControl();
        int[] iArr = {count(192, 224), count(224, 240), count(240, 248)};
        int i = 0;
        int i2 = 0;
        while (i < 3) {
            int i3 = iArr[i];
            iCount2 += i3;
            i++;
            i2 += i3 * i;
        }
        int iCount3 = count(128, 192);
        return iCount2 > 0 && iCount3 <= i2 && iCount3 >= i2 - 3 && count(248, 256) == 0 && (iCount - iCountSafeControl) * 100 < iCount2 * 2;
    }

    public int count() {
        return this.total;
    }

    public int count(int i) {
        return this.counts[i & 255];
    }

    public int countControl() {
        return count(0, 32) - countSafeControl();
    }

    public int countSafeAscii() {
        return count(32, 128) + countSafeControl();
    }

    public int countEightBit() {
        return count(128, 256);
    }

    private int count(int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            i3 += this.counts[i];
            i++;
        }
        return i3;
    }

    private int countSafeControl() {
        return count(9) + count(10) + count(13) + count(12) + count(27);
    }
}
