package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import com.google.common.base.Optional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.tika.utils.StringUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@23.0.0 */
/* loaded from: classes.dex */
public final class zzjz {
    private static volatile Optional zza;

    private zzjz() {
    }

    public static Optional zza(Context context) {
        Optional optionalAbsent;
        Optional optionalAbsent2;
        Optional optional = zza;
        if (optional != null) {
            return optional;
        }
        synchronized (zzjz.class) {
            optionalAbsent = zza;
            if (optionalAbsent == null) {
                String str = Build.TYPE;
                String str2 = Build.TAGS;
                int i = zzkb.zza;
                if ((str.equals("eng") || str.equals("userdebug")) && (str2.contains("dev-keys") || str2.contains("test-keys"))) {
                    Context contextCreateDeviceProtectedStorageContext = (!zzjm.zza() || context.isDeviceProtectedStorage()) ? context : context.createDeviceProtectedStorageContext();
                    StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        StrictMode.allowThreadDiskWrites();
                        char c = 0;
                        try {
                            File file = new File(contextCreateDeviceProtectedStorageContext.getDir("phenotype_hermetic", 0), "overrides.txt");
                            optionalAbsent2 = file.exists() ? Optional.of(file) : Optional.absent();
                        } catch (RuntimeException e) {
                            Log.e("HermeticFileOverrides", "no data dir", e);
                            optionalAbsent2 = Optional.absent();
                        }
                        if (optionalAbsent2.isPresent()) {
                            File file2 = (File) optionalAbsent2.get();
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                                try {
                                    SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
                                    HashMap map = new HashMap();
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        String[] strArrSplit = line.split(StringUtils.SPACE, 3);
                                        if (strArrSplit.length != 3) {
                                            StringBuilder sb = new StringBuilder(line.length() + 9);
                                            sb.append("Invalid: ");
                                            sb.append(line);
                                            Log.e("HermeticFileOverrides", sb.toString());
                                        } else {
                                            String str3 = new String(strArrSplit[c]);
                                            String strDecode = Uri.decode(new String(strArrSplit[1]));
                                            String strDecode2 = (String) map.get(strArrSplit[2]);
                                            if (strDecode2 == null) {
                                                String str4 = new String(strArrSplit[2]);
                                                strDecode2 = Uri.decode(str4);
                                                if (strDecode2.length() < 1024 || strDecode2 == str4) {
                                                    map.put(str4, strDecode2);
                                                }
                                            }
                                            SimpleArrayMap simpleArrayMap2 = (SimpleArrayMap) simpleArrayMap.get(str3);
                                            if (simpleArrayMap2 == null) {
                                                simpleArrayMap2 = new SimpleArrayMap();
                                                simpleArrayMap.put(str3, simpleArrayMap2);
                                            }
                                            simpleArrayMap2.put(strDecode, strDecode2);
                                            c = 0;
                                        }
                                    }
                                    String string = file2.toString();
                                    String packageName = contextCreateDeviceProtectedStorageContext.getPackageName();
                                    StringBuilder sb2 = new StringBuilder(string.length() + 28 + String.valueOf(packageName).length());
                                    sb2.append("Parsed ");
                                    sb2.append(string);
                                    sb2.append(" for Android package ");
                                    sb2.append(packageName);
                                    Log.w("HermeticFileOverrides", sb2.toString());
                                    zzjt zzjtVar = new zzjt(simpleArrayMap);
                                    bufferedReader.close();
                                    optionalAbsent = Optional.of(zzjtVar);
                                } finally {
                                }
                            } catch (IOException e2) {
                                throw new RuntimeException(e2);
                            }
                        } else {
                            optionalAbsent = Optional.absent();
                        }
                    } finally {
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                    }
                } else {
                    optionalAbsent = Optional.absent();
                }
                zza = optionalAbsent;
            }
        }
        return optionalAbsent;
    }
}
