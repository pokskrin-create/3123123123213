package com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    private String[] sourceDirectories(final Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo.splitSourceDirs != null && applicationInfo.splitSourceDirs.length != 0) {
            String[] strArr = new String[applicationInfo.splitSourceDirs.length + 1];
            strArr[0] = applicationInfo.sourceDir;
            System.arraycopy(applicationInfo.splitSourceDirs, 0, strArr, 1, applicationInfo.splitSourceDirs.length);
            return strArr;
        }
        return new String[]{applicationInfo.sourceDir};
    }

    private static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile, ZipEntry zipEntry) {
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(final Context context, final String[] abis, final String mappedLibraryName, final ReLinkerInstance instance) throws IOException {
        String[] strArrSourceDirectories = sourceDirectories(context);
        int length = strArrSourceDirectories.length;
        int i = 0;
        while (true) {
            ZipFile zipFile = null;
            if (i >= length) {
                return null;
            }
            String str = strArrSourceDirectories[i];
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(str), 1);
                    break;
                } catch (IOException unused) {
                    i2 = i3;
                }
            }
            if (zipFile != null) {
                int i4 = 0;
                while (true) {
                    int i5 = i4 + 1;
                    if (i4 < 5) {
                        for (String str2 : abis) {
                            String str3 = "lib" + File.separatorChar + str2 + File.separatorChar + mappedLibraryName;
                            instance.log("Looking for %s in APK %s...", str3, str);
                            ZipEntry entry = zipFile.getEntry(str3);
                            if (entry != null) {
                                return new ZipFileInZipEntry(zipFile, entry);
                            }
                        }
                        i4 = i5;
                    } else {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException unused2) {
                        }
                    }
                }
            }
            i++;
        }
    }

    private String[] getSupportedABIs(Context context, String mappedLibraryName) {
        Pattern patternCompile = Pattern.compile("lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + mappedLibraryName);
        HashSet hashSet = new HashSet();
        for (String str : sourceDirectories(context)) {
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(new File(str), 1).entries();
                while (enumerationEntries.hasMoreElements()) {
                    Matcher matcher = patternCompile.matcher(enumerationEntries.nextElement().getName());
                    if (matcher.matches()) {
                        hashSet.add(matcher.group(1));
                    }
                }
            } catch (IOException unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0060, code lost:
    
        r1.zipFile.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:?, code lost:
    
        return;
     */
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void installLibrary(android.content.Context r9, java.lang.String[] r10, java.lang.String r11, java.io.File r12, com.getkeepsafe.relinker.ReLinkerInstance r13) throws java.lang.Throwable {
        /*
            r8 = this;
            r0 = 0
            com.getkeepsafe.relinker.ApkLibraryInstaller$ZipFileInZipEntry r1 = r8.findAPKWithLibrary(r9, r10, r11, r13)     // Catch: java.lang.Throwable -> La8
            if (r1 == 0) goto L91
            r9 = 0
            r10 = r9
        L9:
            int r2 = r10 + 1
            r3 = 5
            if (r10 >= r3) goto L84
            java.lang.String r10 = "Found %s! Extracting..."
            java.lang.Object[] r3 = new java.lang.Object[]{r11}     // Catch: java.lang.Throwable -> L96
            r13.log(r10, r3)     // Catch: java.lang.Throwable -> L96
            boolean r10 = r12.exists()     // Catch: java.io.IOException -> L82 java.lang.Throwable -> L96
            if (r10 != 0) goto L25
            boolean r10 = r12.createNewFile()     // Catch: java.io.IOException -> L82 java.lang.Throwable -> L96
            if (r10 != 0) goto L25
            goto L82
        L25:
            java.util.zip.ZipFile r10 = r1.zipFile     // Catch: java.lang.Throwable -> L70 java.io.IOException -> L79 java.io.FileNotFoundException -> L7c
            java.util.zip.ZipEntry r3 = r1.zipEntry     // Catch: java.lang.Throwable -> L70 java.io.IOException -> L79 java.io.FileNotFoundException -> L7c
            java.io.InputStream r10 = r10.getInputStream(r3)     // Catch: java.lang.Throwable -> L70 java.io.IOException -> L79 java.io.FileNotFoundException -> L7c
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L6c java.io.FileNotFoundException -> L6e
            r3.<init>(r12)     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L6c java.io.FileNotFoundException -> L6e
            long r4 = r8.copy(r10, r3)     // Catch: java.lang.Throwable -> L66 java.lang.Throwable -> L7e
            java.io.FileDescriptor r6 = r3.getFD()     // Catch: java.lang.Throwable -> L66 java.lang.Throwable -> L7e
            r6.sync()     // Catch: java.lang.Throwable -> L66 java.lang.Throwable -> L7e
            long r6 = r12.length()     // Catch: java.lang.Throwable -> L66 java.lang.Throwable -> L7e
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L4a
            goto L7e
        L46:
            r8.closeSilently(r3)     // Catch: java.lang.Throwable -> L96
            goto L82
        L4a:
            r8.closeSilently(r10)     // Catch: java.lang.Throwable -> L96
            r8.closeSilently(r3)     // Catch: java.lang.Throwable -> L96
            r10 = 1
            r12.setReadable(r10, r9)     // Catch: java.lang.Throwable -> L96
            r12.setExecutable(r10, r9)     // Catch: java.lang.Throwable -> L96
            r12.setWritable(r10)     // Catch: java.lang.Throwable -> L96
            if (r1 == 0) goto L90
            java.util.zip.ZipFile r9 = r1.zipFile     // Catch: java.io.IOException -> L90
            if (r9 == 0) goto L90
        L60:
            java.util.zip.ZipFile r9 = r1.zipFile     // Catch: java.io.IOException -> L90
            r9.close()     // Catch: java.io.IOException -> L90
            goto L90
        L66:
            r9 = move-exception
            goto L6a
        L68:
            r9 = move-exception
            r3 = r0
        L6a:
            r0 = r10
            goto L72
        L6c:
            r3 = r0
            goto L7e
        L6e:
            r3 = r0
            goto L7e
        L70:
            r9 = move-exception
            r3 = r0
        L72:
            r8.closeSilently(r0)     // Catch: java.lang.Throwable -> L96
            r8.closeSilently(r3)     // Catch: java.lang.Throwable -> L96
            throw r9     // Catch: java.lang.Throwable -> L96
        L79:
            r10 = r0
            r3 = r10
            goto L7e
        L7c:
            r10 = r0
            r3 = r10
        L7e:
            r8.closeSilently(r10)     // Catch: java.lang.Throwable -> L96
            goto L46
        L82:
            r10 = r2
            goto L9
        L84:
            java.lang.String r9 = "FATAL! Couldn't extract the library from the APK!"
            r13.log(r9)     // Catch: java.lang.Throwable -> L96
            if (r1 == 0) goto L90
            java.util.zip.ZipFile r9 = r1.zipFile     // Catch: java.io.IOException -> L90
            if (r9 == 0) goto L90
            goto L60
        L90:
            return
        L91:
            java.lang.String[] r9 = r8.getSupportedABIs(r9, r11)     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            goto La2
        L96:
            r9 = move-exception
            r0 = r1
            goto La9
        L99:
            r9 = move-exception
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L96
            java.lang.String[] r9 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L96
        La2:
            com.getkeepsafe.relinker.MissingLibraryException r12 = new com.getkeepsafe.relinker.MissingLibraryException     // Catch: java.lang.Throwable -> L96
            r12.<init>(r11, r10, r9)     // Catch: java.lang.Throwable -> L96
            throw r12     // Catch: java.lang.Throwable -> L96
        La8:
            r9 = move-exception
        La9:
            if (r0 == 0) goto Lb4
            java.util.zip.ZipFile r10 = r0.zipFile     // Catch: java.io.IOException -> Lb4
            if (r10 == 0) goto Lb4
            java.util.zip.ZipFile r10 = r0.zipFile     // Catch: java.io.IOException -> Lb4
            r10.close()     // Catch: java.io.IOException -> Lb4
        Lb4:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getkeepsafe.relinker.ApkLibraryInstaller.installLibrary(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.getkeepsafe.relinker.ReLinkerInstance):void");
    }

    private long copy(InputStream in, OutputStream out) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = in.read(bArr);
            if (i != -1) {
                out.write(bArr, 0, i);
                j += i;
            } else {
                out.flush();
                return j;
            }
        }
    }

    private void closeSilently(final Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
