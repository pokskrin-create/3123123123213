package org.apache.tika.detect;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public class MagicDetector implements Detector {
    private final boolean isRegex;
    private final boolean isStringIgnoreCase;
    private final int length;
    private final byte[] mask;
    private final int offsetRangeBegin;
    private final int offsetRangeEnd;
    private final byte[] pattern;
    private final int patternLength;
    private final MediaType type;

    public MagicDetector(MediaType mediaType, byte[] bArr) {
        this(mediaType, bArr, 0);
    }

    public MagicDetector(MediaType mediaType, byte[] bArr, int i) {
        this(mediaType, bArr, null, i, i);
    }

    public MagicDetector(MediaType mediaType, byte[] bArr, byte[] bArr2, int i, int i2) {
        this(mediaType, bArr, bArr2, false, i, i2);
    }

    public MagicDetector(MediaType mediaType, byte[] bArr, byte[] bArr2, boolean z, int i, int i2) {
        this(mediaType, bArr, bArr2, z, false, i, i2);
    }

    public MagicDetector(MediaType mediaType, byte[] bArr, byte[] bArr2, boolean z, boolean z2, int i, int i2) {
        if (mediaType == null) {
            throw new IllegalArgumentException("Matching media type is null");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Magic match pattern is null");
        }
        if (i < 0 || i2 < i) {
            throw new IllegalArgumentException("Invalid offset range: [" + i + "," + i2 + "]");
        }
        this.type = mediaType;
        this.isRegex = z;
        this.isStringIgnoreCase = z2;
        int iMax = Math.max(bArr.length, bArr2 != null ? bArr2.length : 0);
        this.patternLength = iMax;
        if (z) {
            this.length = 8192;
        } else {
            this.length = iMax;
        }
        this.mask = new byte[iMax];
        this.pattern = new byte[iMax];
        for (int i3 = 0; i3 < this.patternLength; i3++) {
            if (bArr2 != null && i3 < bArr2.length) {
                this.mask[i3] = bArr2[i3];
            } else {
                this.mask[i3] = -1;
            }
            if (i3 < bArr.length) {
                this.pattern[i3] = (byte) (bArr[i3] & this.mask[i3]);
            } else {
                this.pattern[i3] = 0;
            }
        }
        this.offsetRangeBegin = i;
        this.offsetRangeEnd = i2;
    }

    public static MagicDetector parse(MediaType mediaType, String str, String str2, String str3, String str4) throws NumberFormatException {
        int i;
        int i2;
        int i3 = 0;
        if (str2 == null) {
            i = i3;
            i2 = i;
        } else {
            int iIndexOf = str2.indexOf(58);
            if (iIndexOf == -1) {
                i3 = Integer.parseInt(str2);
                i = i3;
                i2 = i;
            } else {
                int i4 = Integer.parseInt(str2.substring(0, iIndexOf));
                i2 = Integer.parseInt(str2.substring(iIndexOf + 1));
                i = i4;
            }
        }
        return new MagicDetector(mediaType, decodeValue(str3, str), str4 != null ? decodeValue(str4, str) : null, str.equals("regex"), str.equals("stringignorecase"), i, i2);
    }

    private static byte[] decodeValue(String str, String str2) throws NumberFormatException {
        String strSubstring;
        int i;
        if (str == null || str2 == null) {
            return null;
        }
        if (str.startsWith("0x")) {
            strSubstring = str.substring(2);
            i = 16;
        } else {
            strSubstring = str;
            i = 8;
        }
        str2.hashCode();
        switch (str2) {
            case "host16":
            case "little16":
                int i2 = Integer.parseInt(strSubstring, i);
                return new byte[]{(byte) (i2 & 255), (byte) (i2 >> 8)};
            case "host32":
            case "little32":
                long j = Long.parseLong(strSubstring, i);
                return new byte[]{(byte) (j & 255), (byte) ((j & 65280) >> 8), (byte) ((j & 16711680) >> 16), (byte) ((j & (-16777216)) >> 24)};
            case "unicodeBE":
            case "unicodeLE":
            case "string":
            case "regex":
                return decodeString(str, str2);
            case "stringignorecase":
                return decodeString(str.toLowerCase(Locale.ROOT), str2);
            case "byte":
                return strSubstring.getBytes(StandardCharsets.UTF_8);
            case "big16":
                int i3 = Integer.parseInt(strSubstring, i);
                return new byte[]{(byte) (i3 >> 8), (byte) (i3 & 255)};
            case "big32":
                long j2 = Long.parseLong(strSubstring, i);
                return new byte[]{(byte) ((j2 & (-16777216)) >> 24), (byte) ((j2 & 16711680) >> 16), (byte) ((j2 & 65280) >> 8), (byte) (j2 & 255)};
            default:
                return null;
        }
    }

    private static byte[] decodeString(String str, String str2) {
        int i = 0;
        if (str.startsWith("0x")) {
            int length = (str.length() - 2) / 2;
            byte[] bArr = new byte[length];
            while (i < length) {
                int i2 = i * 2;
                bArr[i] = (byte) Integer.parseInt(str.substring(i2 + 2, i2 + 4), 16);
                i++;
            }
            return bArr;
        }
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        int i3 = 0;
        while (i3 < str.length()) {
            if (str.charAt(i3) == '\\') {
                int i4 = i3 + 1;
                if (str.charAt(i4) == '\\') {
                    charArrayWriter.write(92);
                } else if (str.charAt(i4) == 'x') {
                    charArrayWriter.write(Integer.parseInt(str.substring(i3 + 2, i3 + 4), 16));
                    i3 += 3;
                } else if (str.charAt(i4) == 'r') {
                    charArrayWriter.write(13);
                } else if (str.charAt(i4) == 'n') {
                    charArrayWriter.write(10);
                } else {
                    int i5 = i4;
                    while (i5 < i3 + 4 && i5 < str.length() && Character.isDigit(str.charAt(i5))) {
                        i5++;
                    }
                    charArrayWriter.write(Short.decode("0" + str.substring(i4, i5)).byteValue());
                    i3 = i5 + (-1);
                }
                i3 = i4;
            } else {
                charArrayWriter.write(str.charAt(i3));
            }
            i3++;
        }
        char[] charArray = charArrayWriter.toCharArray();
        if ("unicodeLE".equals(str2)) {
            byte[] bArr2 = new byte[charArray.length * 2];
            while (i < charArray.length) {
                int i6 = i * 2;
                char c = charArray[i];
                bArr2[i6] = (byte) (c & 255);
                bArr2[i6 + 1] = (byte) (c >> '\b');
                i++;
            }
            return bArr2;
        }
        if ("unicodeBE".equals(str2)) {
            byte[] bArr3 = new byte[charArray.length * 2];
            while (i < charArray.length) {
                int i7 = i * 2;
                char c2 = charArray[i];
                bArr3[i7] = (byte) (c2 >> '\b');
                bArr3[i7 + 1] = (byte) (c2 & 255);
                i++;
            }
            return bArr3;
        }
        int length2 = charArray.length;
        byte[] bArr4 = new byte[length2];
        while (i < length2) {
            bArr4[i] = (byte) charArray[i];
            i++;
        }
        return bArr4;
    }

    @Override // org.apache.tika.detect.Detector
    public MediaType detect(InputStream inputStream, Metadata metadata) throws IOException {
        if (inputStream == null) {
            return MediaType.OCTET_STREAM;
        }
        inputStream.mark(this.offsetRangeEnd + this.length);
        int i = 0;
        while (true) {
            try {
                int i2 = this.offsetRangeBegin;
                if (i >= i2) {
                    int i3 = this.length + (this.offsetRangeEnd - i2);
                    byte[] bArr = new byte[i3];
                    int i4 = inputStream.read(bArr);
                    if (i4 > 0) {
                        i += i4;
                    }
                    while (i4 != -1 && i < this.offsetRangeEnd + this.length) {
                        int i5 = i - this.offsetRangeBegin;
                        i4 = inputStream.read(bArr, i5, i3 - i5);
                        if (i4 > 0) {
                            i += i4;
                        }
                    }
                    if (this.isRegex) {
                        Matcher matcher = Pattern.compile(new String(this.pattern, StandardCharsets.UTF_8), this.isStringIgnoreCase ? 2 : 0).matcher(StandardCharsets.ISO_8859_1.decode(ByteBuffer.wrap(bArr)));
                        for (int i6 = 0; i6 <= this.offsetRangeEnd - this.offsetRangeBegin; i6++) {
                            matcher.region(i6, this.length + i6);
                            if (matcher.lookingAt()) {
                                return this.type;
                            }
                        }
                    } else {
                        if (i < this.offsetRangeBegin + this.length) {
                            return MediaType.OCTET_STREAM;
                        }
                        for (int i7 = 0; i7 <= this.offsetRangeEnd - this.offsetRangeBegin; i7++) {
                            boolean z = true;
                            for (int i8 = 0; z && i8 < this.length; i8++) {
                                int lowerCase = bArr[i7 + i8] & this.mask[i8];
                                if (this.isStringIgnoreCase) {
                                    lowerCase = Character.toLowerCase(lowerCase);
                                }
                                z = lowerCase == this.pattern[i8];
                            }
                            if (z) {
                                return this.type;
                            }
                        }
                    }
                    return MediaType.OCTET_STREAM;
                }
                long jSkip = inputStream.skip(i2 - i);
                if (jSkip > 0) {
                    i = (int) (i + jSkip);
                } else {
                    if (inputStream.read() == -1) {
                        return MediaType.OCTET_STREAM;
                    }
                    i++;
                }
            } finally {
                inputStream.reset();
            }
        }
    }

    public int getLength() {
        return this.patternLength;
    }

    public String toString() {
        MediaType mediaType = this.type;
        byte[] bArr = this.pattern;
        return "Magic Detection for " + mediaType + " looking for " + bArr.length + " bytes = " + Arrays.toString(bArr) + " mask = " + Arrays.toString(this.mask);
    }
}
