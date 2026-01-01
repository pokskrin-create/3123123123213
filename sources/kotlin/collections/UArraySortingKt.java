package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\n\u0010\u000b\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\r\u0010\u000e\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0012\u0010\u0013\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u000b\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0010\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0015\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort-Aa5vz7o", "([SII)V", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "quickSort-oBK06Vg", "([III)V", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "quickSort--nroSd4", "([JII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-Aa5vz7o", "sortArray-oBK06Vg", "sortArray--nroSd4", "kotlin-stdlib"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m943partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM559getw2LRezQ = UByteArray.m559getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM559getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m559getw2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m559getw2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM559getw2LRezQ2 = UByteArray.m559getw2LRezQ(bArr, i);
                UByteArray.m564setVurrAj0(bArr, i, UByteArray.m559getw2LRezQ(bArr, i2));
                UByteArray.m564setVurrAj0(bArr, i2, bM559getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m947quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM943partition4UcCI2c = m943partition4UcCI2c(bArr, i, i2);
        int i3 = iM943partition4UcCI2c - 1;
        if (i < i3) {
            m947quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM943partition4UcCI2c < i2) {
            m947quickSort4UcCI2c(bArr, iM943partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m944partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM822getMh2AYeg = UShortArray.m822getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM822getMh2AYeg = UShortArray.m822getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM822getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM822getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m822getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM822getMh2AYeg2 = UShortArray.m822getMh2AYeg(sArr, i);
                UShortArray.m827set01HTLdE(sArr, i, UShortArray.m822getMh2AYeg(sArr, i2));
                UShortArray.m827set01HTLdE(sArr, i2, sM822getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m948quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM944partitionAa5vz7o = m944partitionAa5vz7o(sArr, i, i2);
        int i3 = iM944partitionAa5vz7o - 1;
        if (i < i3) {
            m948quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM944partitionAa5vz7o < i2) {
            m948quickSortAa5vz7o(sArr, iM944partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m945partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM638getpVg5ArA = UIntArray.m638getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compare(UIntArray.m638getpVg5ArA(iArr, i) ^ Integer.MIN_VALUE, iM638getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i++;
            }
            while (Integer.compare(UIntArray.m638getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM638getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM638getpVg5ArA2 = UIntArray.m638getpVg5ArA(iArr, i);
                UIntArray.m643setVXSXFK8(iArr, i, UIntArray.m638getpVg5ArA(iArr, i2));
                UIntArray.m643setVXSXFK8(iArr, i2, iM638getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m949quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM945partitionoBK06Vg = m945partitionoBK06Vg(iArr, i, i2);
        int i3 = iM945partitionoBK06Vg - 1;
        if (i < i3) {
            m949quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM945partitionoBK06Vg < i2) {
            m949quickSortoBK06Vg(iArr, iM945partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m942partitionnroSd4(long[] jArr, int i, int i2) {
        long jM717getsVKNKU = ULongArray.m717getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compare(ULongArray.m717getsVKNKU(jArr, i) ^ Long.MIN_VALUE, jM717getsVKNKU ^ Long.MIN_VALUE) < 0) {
                i++;
            }
            while (Long.compare(ULongArray.m717getsVKNKU(jArr, i2) ^ Long.MIN_VALUE, jM717getsVKNKU ^ Long.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM717getsVKNKU2 = ULongArray.m717getsVKNKU(jArr, i);
                ULongArray.m722setk8EXiF4(jArr, i, ULongArray.m717getsVKNKU(jArr, i2));
                ULongArray.m722setk8EXiF4(jArr, i2, jM717getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m946quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM942partitionnroSd4 = m942partitionnroSd4(jArr, i, i2);
        int i3 = iM942partitionnroSd4 - 1;
        if (i < i3) {
            m946quickSortnroSd4(jArr, i, i3);
        }
        if (iM942partitionnroSd4 < i2) {
            m946quickSortnroSd4(jArr, iM942partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m951sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m947quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m952sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m948quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m953sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m949quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m950sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m946quickSortnroSd4(array, i, i2 - 1);
    }
}
