package org.apache.commons.io.file.attribute;

import j$.time.Instant;
import j$.time.TimeConversions;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class FileTimes {
    private static final long HUNDRED = 100;
    private static final BigDecimal HUNDRED_BD;
    static final long HUNDRED_NANOS_PER_MILLISECOND;
    static final BigDecimal HUNDRED_NANOS_PER_MILLISECOND_BD;
    private static final long HUNDRED_NANOS_PER_SECOND;
    private static final BigDecimal HUNDRED_NANOS_PER_SECOND_BD;
    private static final BigDecimal LONG_MIN_VALUE_BD = BigDecimal.valueOf(Long.MIN_VALUE);
    private static final BigDecimal LONG_MAX_VALUE_BD = BigDecimal.valueOf(Long.MAX_VALUE);
    private static final MathContext MATH_CONTEXT = new MathContext(0, RoundingMode.FLOOR);
    public static final FileTime EPOCH = FileTime.from(TimeConversions.convert(Instant.EPOCH));
    static final long UNIX_TO_NTFS_OFFSET = -116444736000000000L;
    private static final BigDecimal UNIX_TO_NTFS_OFFSET_BD = BigDecimal.valueOf(UNIX_TO_NTFS_OFFSET);

    public static boolean isUnixTime(long j) {
        return -2147483648L <= j && j <= 2147483647L;
    }

    static {
        long nanos = TimeUnit.SECONDS.toNanos(1L) / HUNDRED;
        HUNDRED_NANOS_PER_SECOND = nanos;
        HUNDRED_NANOS_PER_SECOND_BD = BigDecimal.valueOf(nanos);
        long nanos2 = TimeUnit.MILLISECONDS.toNanos(1L) / HUNDRED;
        HUNDRED_NANOS_PER_MILLISECOND = nanos2;
        HUNDRED_NANOS_PER_MILLISECOND_BD = BigDecimal.valueOf(nanos2);
        HUNDRED_BD = BigDecimal.valueOf(HUNDRED);
    }

    public static FileTime fromUnixTime(long j) {
        return FileTime.from(j, TimeUnit.SECONDS);
    }

    public static boolean isUnixTime(FileTime fileTime) {
        return isUnixTime(toUnixTime(fileTime));
    }

    public static FileTime minusMillis(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).minusMillis(j)));
    }

    public static FileTime minusNanos(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).minusNanos(j)));
    }

    public static FileTime minusSeconds(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).minusSeconds(j)));
    }

    public static FileTime now() {
        return FileTime.from(TimeConversions.convert(Instant.now()));
    }

    static Date ntfsTimeToDate(BigDecimal bigDecimal) {
        return new Date(ntfsTimeToInstant(bigDecimal).toEpochMilli());
    }

    public static Date ntfsTimeToDate(long j) {
        return ntfsTimeToDate(BigDecimal.valueOf(j));
    }

    public static FileTime ntfsTimeToFileTime(long j) {
        return FileTime.from(TimeConversions.convert(ntfsTimeToInstant(j)));
    }

    static Instant ntfsTimeToInstant(BigDecimal bigDecimal) {
        BigDecimal[] bigDecimalArrDivideAndRemainder = bigDecimal.add(UNIX_TO_NTFS_OFFSET_BD).divideAndRemainder(HUNDRED_NANOS_PER_SECOND_BD, MATH_CONTEXT);
        return Instant.ofEpochSecond(bigDecimalArrDivideAndRemainder[0].longValueExact(), bigDecimalArrDivideAndRemainder[1].multiply(HUNDRED_BD).longValueExact());
    }

    static Instant ntfsTimeToInstant(long j) {
        return ntfsTimeToInstant(BigDecimal.valueOf(j));
    }

    public static FileTime plusMillis(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).plusMillis(j)));
    }

    public static FileTime plusNanos(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).plusNanos(j)));
    }

    public static FileTime plusSeconds(FileTime fileTime, long j) {
        return FileTime.from(TimeConversions.convert(TimeConversions.convert(fileTime.toInstant()).plusSeconds(j)));
    }

    public static void setLastModifiedTime(Path path) throws IOException {
        Files.setLastModifiedTime(path, now());
    }

    public static Date toDate(FileTime fileTime) {
        if (fileTime != null) {
            return new Date(fileTime.toMillis());
        }
        return null;
    }

    public static FileTime toFileTime(Date date) {
        if (date != null) {
            return FileTime.fromMillis(date.getTime());
        }
        return null;
    }

    public static long toNtfsTime(Date date) {
        return toNtfsTime(date.getTime());
    }

    public static long toNtfsTime(FileTime fileTime) {
        return toNtfsTime(TimeConversions.convert(fileTime.toInstant()));
    }

    static long toNtfsTime(Instant instant) {
        return BigDecimal.valueOf(instant.getEpochSecond()).multiply(HUNDRED_NANOS_PER_SECOND_BD).add(BigDecimal.valueOf(instant.getNano() / 100)).subtract(UNIX_TO_NTFS_OFFSET_BD).longValueExact();
    }

    public static long toNtfsTime(long j) {
        BigDecimal bigDecimalSubtract = BigDecimal.valueOf(j).multiply(HUNDRED_NANOS_PER_MILLISECOND_BD).subtract(UNIX_TO_NTFS_OFFSET_BD);
        if (bigDecimalSubtract.compareTo(LONG_MAX_VALUE_BD) >= 0) {
            return Long.MAX_VALUE;
        }
        if (bigDecimalSubtract.compareTo(LONG_MIN_VALUE_BD) <= 0) {
            return Long.MIN_VALUE;
        }
        return bigDecimalSubtract.longValue();
    }

    public static long toUnixTime(FileTime fileTime) {
        if (fileTime != null) {
            return fileTime.to(TimeUnit.SECONDS);
        }
        return 0L;
    }

    private FileTimes() {
    }
}
