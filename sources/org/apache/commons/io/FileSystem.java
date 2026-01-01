package org.apache.commons.io;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import org.apache.tika.metadata.MachineMetadata;

/* loaded from: classes4.dex */
public enum FileSystem {
    GENERIC(4096, false, false, Integer.MAX_VALUE, Integer.MAX_VALUE, new int[]{0}, new String[0], false, false, IOUtils.DIR_SEPARATOR_UNIX),
    LINUX(8192, true, true, 255, 4096, new int[]{0, 47}, new String[0], false, false, IOUtils.DIR_SEPARATOR_UNIX),
    MAC_OSX(4096, true, true, 255, 1024, new int[]{0, 47, 58}, new String[0], false, false, IOUtils.DIR_SEPARATOR_UNIX),
    WINDOWS(4096, false, true, 255, 32000, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34, 42, 47, 58, 60, 62, 63, 92, 124}, new String[]{"AUX", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM²", "COM³", "COM¹", "CON", "CONIN$", "CONOUT$", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9", "LPT²", "LPT³", "LPT¹", "NUL", "PRN"}, true, true, IOUtils.DIR_SEPARATOR_WINDOWS);

    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
    private final int blockSize;
    private final boolean casePreserving;
    private final boolean caseSensitive;
    private final int[] illegalFileNameChars;
    private final int maxFileNameLength;
    private final int maxPathLength;
    private final char nameSeparator;
    private final char nameSeparatorOther;
    private final String[] reservedFileNames;
    private final boolean reservedFileNamesExtensions;
    private final boolean supportsDriveLetter;
    private static final boolean IS_OS_LINUX = getOsMatchesName(MachineMetadata.PLATFORM_LINUX);
    private static final boolean IS_OS_MAC = getOsMatchesName("Mac");
    private static final boolean IS_OS_WINDOWS = getOsMatchesName("Windows");
    private static final FileSystem CURRENT = current();

    private static FileSystem current() {
        if (IS_OS_LINUX) {
            return LINUX;
        }
        if (IS_OS_MAC) {
            return MAC_OSX;
        }
        if (IS_OS_WINDOWS) {
            return WINDOWS;
        }
        return GENERIC;
    }

    public static FileSystem getCurrent() {
        return CURRENT;
    }

    private static boolean getOsMatchesName(String str) {
        return isOsNameMatch(getSystemProperty("os.name"), str);
    }

    private static String getSystemProperty(String str) {
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            System.err.println("Caught a SecurityException reading the system property '" + str + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }

    private static int indexOf(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(i, i2);
        }
        int length = charSequence.length();
        if (i2 < 0) {
            i2 = 0;
        }
        if (i < 65536) {
            while (i2 < length) {
                if (charSequence.charAt(i2) == i) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        if (i <= 1114111) {
            char[] chars = Character.toChars(i);
            while (i2 < length - 1) {
                char cCharAt = charSequence.charAt(i2);
                int i3 = i2 + 1;
                char cCharAt2 = charSequence.charAt(i3);
                if (cCharAt == chars[0] && cCharAt2 == chars[1]) {
                    return i2;
                }
                i2 = i3;
            }
        }
        return -1;
    }

    private static boolean isOsNameMatch(String str, String str2) {
        if (str == null) {
            return false;
        }
        return str.toUpperCase(Locale.ROOT).startsWith(str2.toUpperCase(Locale.ROOT));
    }

    private static String replace(String str, char c, char c2) {
        if (str == null) {
            return null;
        }
        return str.replace(c, c2);
    }

    FileSystem(int i, boolean z, boolean z2, int i2, int i3, int[] iArr, String[] strArr, boolean z3, boolean z4, char c) {
        this.blockSize = i;
        this.maxFileNameLength = i2;
        this.maxPathLength = i3;
        this.illegalFileNameChars = (int[]) Objects.requireNonNull(iArr, "illegalFileNameChars");
        this.reservedFileNames = (String[]) Objects.requireNonNull(strArr, "reservedFileNames");
        this.reservedFileNamesExtensions = z3;
        this.caseSensitive = z;
        this.casePreserving = z2;
        this.supportsDriveLetter = z4;
        this.nameSeparator = c;
        this.nameSeparatorOther = FilenameUtils.flipSeparator(c);
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public char[] getIllegalFileNameChars() {
        char[] cArr = new char[this.illegalFileNameChars.length];
        int i = 0;
        while (true) {
            int[] iArr = this.illegalFileNameChars;
            if (i >= iArr.length) {
                return cArr;
            }
            cArr[i] = (char) iArr[i];
            i++;
        }
    }

    public int[] getIllegalFileNameCodePoints() {
        return (int[]) this.illegalFileNameChars.clone();
    }

    public int getMaxFileNameLength() {
        return this.maxFileNameLength;
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    public char getNameSeparator() {
        return this.nameSeparator;
    }

    public String[] getReservedFileNames() {
        return (String[]) this.reservedFileNames.clone();
    }

    public boolean isCasePreserving() {
        return this.casePreserving;
    }

    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isIllegalFileNameChar(int i) {
        return Arrays.binarySearch(this.illegalFileNameChars, i) >= 0;
    }

    public boolean isLegalFileName(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0 || charSequence.length() > this.maxFileNameLength || isReservedFileName(charSequence)) {
            return false;
        }
        return charSequence.chars().noneMatch(new IntPredicate() { // from class: org.apache.commons.io.FileSystem$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return this.f$0.isIllegalFileNameChar(i);
            }
        });
    }

    public boolean isReservedFileName(CharSequence charSequence) {
        if (this.reservedFileNamesExtensions) {
            charSequence = trimExtension(charSequence);
        }
        return Arrays.binarySearch(this.reservedFileNames, charSequence) >= 0;
    }

    public String normalizeSeparators(String str) {
        return replace(str, this.nameSeparatorOther, this.nameSeparator);
    }

    public boolean supportsDriveLetter() {
        return this.supportsDriveLetter;
    }

    public String toLegalFileName(String str, final char c) {
        if (isIllegalFileNameChar(c)) {
            throw new IllegalArgumentException(String.format("The replacement character '%s' cannot be one of the %s illegal characters: %s", c == 0 ? "\\0" : Character.valueOf(c), name(), Arrays.toString(this.illegalFileNameChars)));
        }
        int length = str.length();
        int i = this.maxFileNameLength;
        if (length > i) {
            str = str.substring(0, i);
        }
        int[] array = str.chars().map(new IntUnaryOperator() { // from class: org.apache.commons.io.FileSystem$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i2) {
                return this.f$0.m2177lambda$toLegalFileName$0$orgapachecommonsioFileSystem(c, i2);
            }
        }).toArray();
        return new String(array, 0, array.length);
    }

    /* renamed from: lambda$toLegalFileName$0$org-apache-commons-io-FileSystem, reason: not valid java name */
    /* synthetic */ int m2177lambda$toLegalFileName$0$orgapachecommonsioFileSystem(char c, int i) {
        return isIllegalFileNameChar(i) ? c : i;
    }

    CharSequence trimExtension(CharSequence charSequence) {
        int iIndexOf = indexOf(charSequence, 46, 0);
        return iIndexOf < 0 ? charSequence : charSequence.subSequence(0, iIndexOf);
    }
}
