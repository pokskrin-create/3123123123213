package com.google.android.gms.internal.common;

import org.apache.commons.io.IOUtils;

/* compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* loaded from: classes.dex */
final class zzo extends zzn {
    private final char zza;

    zzo(char c) {
        this.zza = c;
    }

    public final String toString() {
        char[] cArr = new char[6];
        cArr[0] = IOUtils.DIR_SEPARATOR_WINDOWS;
        cArr[1] = 'u';
        cArr[2] = 0;
        cArr[3] = 0;
        cArr[4] = 0;
        cArr[5] = 0;
        int i = this.zza;
        for (int i2 = 0; i2 < 4; i2++) {
            cArr[5 - i2] = "0123456789ABCDEF".charAt(i & 15);
            i >>= 4;
        }
        return "CharMatcher.is('" + String.copyValueOf(cArr) + "')";
    }

    @Override // com.google.android.gms.internal.common.zzr
    public final boolean zza(char c) {
        return c == this.zza;
    }
}
