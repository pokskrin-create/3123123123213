package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Objects;
import org.apache.commons.io.output.CloseShieldOutputStream;

/* loaded from: classes4.dex */
public class HexDump {

    @Deprecated
    public static final String EOL = System.lineSeparator();
    private static final char[] HEX_CODES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] SHIFTS = {28, 24, 20, 16, 12, 8, 4, 0};

    public static void dump(byte[] bArr, Appendable appendable) throws IOException, ArrayIndexOutOfBoundsException {
        dump(bArr, 0L, appendable, 0, bArr.length);
    }

    public static void dump(byte[] bArr, long j, Appendable appendable, int i, int i2) throws IOException, ArrayIndexOutOfBoundsException {
        int i3;
        Objects.requireNonNull(appendable, "appendable");
        if (i < 0 || i >= bArr.length) {
            throw new ArrayIndexOutOfBoundsException("illegal index: " + i + " into array of length " + bArr.length);
        }
        long j2 = j + i;
        StringBuilder sb = new StringBuilder(74);
        if (i2 < 0 || (i3 = i + i2) > bArr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Range [%s, %<s + %s) out of bounds for length %s", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)));
        }
        while (i < i3) {
            int i4 = i3 - i;
            if (i4 > 16) {
                i4 = 16;
            }
            dump(sb, j2).append(' ');
            for (int i5 = 0; i5 < 16; i5++) {
                if (i5 < i4) {
                    dump(sb, bArr[i5 + i]);
                } else {
                    sb.append("  ");
                }
                sb.append(' ');
            }
            for (int i6 = 0; i6 < i4; i6++) {
                byte b = bArr[i6 + i];
                if (b >= 32 && b < 127) {
                    sb.append((char) b);
                } else {
                    sb.append(FilenameUtils.EXTENSION_SEPARATOR);
                }
            }
            sb.append(System.lineSeparator());
            appendable.append(sb);
            sb.setLength(0);
            j2 += i4;
            i += 16;
        }
    }

    public static void dump(byte[] bArr, long j, OutputStream outputStream, int i) throws IOException, ArrayIndexOutOfBoundsException {
        Objects.requireNonNull(outputStream, "stream");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(CloseShieldOutputStream.wrap(outputStream), Charset.defaultCharset());
        try {
            dump(bArr, j, outputStreamWriter, i, bArr.length - i);
            outputStreamWriter.close();
        } finally {
        }
    }

    private static StringBuilder dump(StringBuilder sb, byte b) {
        for (int i = 0; i < 2; i++) {
            sb.append(HEX_CODES[(b >> SHIFTS[i + 6]) & 15]);
        }
        return sb;
    }

    private static StringBuilder dump(StringBuilder sb, long j) {
        for (int i = 0; i < 8; i++) {
            sb.append(HEX_CODES[((int) (j >> SHIFTS[i])) & 15]);
        }
        return sb;
    }
}
