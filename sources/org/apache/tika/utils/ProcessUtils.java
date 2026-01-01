package org.apache.tika.utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ProcessUtils {
    private static final ConcurrentHashMap<String, Process> PROCESS_MAP = new ConcurrentHashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { // from class: org.apache.tika.utils.ProcessUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ProcessUtils.PROCESS_MAP.forEachValue(1L, new Consumer() { // from class: org.apache.tika.utils.ProcessUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Process) obj).destroyForcibly();
                    }
                });
            }
        }));
    }

    private static String register(Process process) {
        String string = UUID.randomUUID().toString();
        PROCESS_MAP.put(string, process);
        return string;
    }

    private static Process release(String str) {
        return PROCESS_MAP.remove(str);
    }

    public static String escapeCommandLine(String str) {
        if (str == null || !str.contains(StringUtils.SPACE) || !SystemUtils.IS_OS_WINDOWS || str.startsWith("\"") || str.endsWith("\"")) {
            return str;
        }
        return "\"" + str + "\"";
    }

    public static String unescapeCommandLine(String str) {
        return (str.contains(StringUtils.SPACE) && SystemUtils.IS_OS_WINDOWS && str.startsWith("\"") && str.endsWith("\"")) ? str.substring(1, str.length() - 1) : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.apache.tika.utils.FileProcessResult execute(java.lang.ProcessBuilder r11, long r12, int r14, int r15) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "\n"
            r1 = 0
            java.lang.Process r11 = r11.start()     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r1 = register(r11)     // Catch: java.lang.Throwable -> Lc3
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc3
            org.apache.tika.utils.StreamGobbler r4 = new org.apache.tika.utils.StreamGobbler     // Catch: java.lang.Throwable -> Lc3
            java.io.InputStream r5 = r11.getInputStream()     // Catch: java.lang.Throwable -> Lc3
            r4.<init>(r5, r14)     // Catch: java.lang.Throwable -> Lc3
            org.apache.tika.utils.StreamGobbler r14 = new org.apache.tika.utils.StreamGobbler     // Catch: java.lang.Throwable -> Lc3
            java.io.InputStream r5 = r11.getErrorStream()     // Catch: java.lang.Throwable -> Lc3
            r14.<init>(r5, r15)     // Catch: java.lang.Throwable -> Lc3
            java.lang.Thread r15 = new java.lang.Thread     // Catch: java.lang.Throwable -> Lc3
            r15.<init>(r4)     // Catch: java.lang.Throwable -> Lc3
            r15.start()     // Catch: java.lang.Throwable -> Lc3
            java.lang.Thread r5 = new java.lang.Thread     // Catch: java.lang.Throwable -> Lc3
            r5.<init>(r14)     // Catch: java.lang.Throwable -> Lc3
            r5.start()     // Catch: java.lang.Throwable -> Lc3
            r6 = -1
            r8 = 0
            java.util.concurrent.TimeUnit r9 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            boolean r8 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r11, r12, r9)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            long r12 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            long r6 = r12 - r2
            r12 = 1000(0x3e8, double:4.94E-321)
            if (r8 == 0) goto L4f
            int r2 = r11.exitValue()     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            r15.join(r12)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            r5.join(r12)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            goto L68
        L4f:
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r11)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            r15.join(r12)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            r5.join(r12)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            r2 = 500(0x1f4, double:2.47E-321)
            boolean r12 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r11, r2, r12)     // Catch: java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            if (r12 == 0) goto L67
            int r2 = r11.exitValue()     // Catch: java.lang.IllegalThreadStateException -> L67 java.lang.Throwable -> L6f java.lang.InterruptedException -> L77
            goto L68
        L67:
            r2 = -1
        L68:
            r15.interrupt()     // Catch: java.lang.Throwable -> Lc3
            r5.interrupt()     // Catch: java.lang.Throwable -> Lc3
            goto L7f
        L6f:
            r12 = move-exception
            r15.interrupt()     // Catch: java.lang.Throwable -> Lc3
            r5.interrupt()     // Catch: java.lang.Throwable -> Lc3
            throw r12     // Catch: java.lang.Throwable -> Lc3
        L77:
            r15.interrupt()     // Catch: java.lang.Throwable -> Lc3
            r5.interrupt()     // Catch: java.lang.Throwable -> Lc3
            r2 = -1000(0xfffffffffffffc18, float:NaN)
        L7f:
            org.apache.tika.utils.FileProcessResult r12 = new org.apache.tika.utils.FileProcessResult     // Catch: java.lang.Throwable -> Lc3
            r12.<init>()     // Catch: java.lang.Throwable -> Lc3
            r12.processTimeMillis = r6     // Catch: java.lang.Throwable -> Lc3
            long r5 = r14.getStreamLength()     // Catch: java.lang.Throwable -> Lc3
            r12.stderrLength = r5     // Catch: java.lang.Throwable -> Lc3
            long r5 = r4.getStreamLength()     // Catch: java.lang.Throwable -> Lc3
            r12.stdoutLength = r5     // Catch: java.lang.Throwable -> Lc3
            r13 = r8 ^ 1
            r12.isTimeout = r13     // Catch: java.lang.Throwable -> Lc3
            r12.exitValue = r2     // Catch: java.lang.Throwable -> Lc3
            java.util.List r13 = r4.getLines()     // Catch: java.lang.Throwable -> Lc3
            java.lang.String r13 = org.apache.tika.utils.StringUtils.joinWith(r0, r13)     // Catch: java.lang.Throwable -> Lc3
            r12.stdout = r13     // Catch: java.lang.Throwable -> Lc3
            java.util.List r13 = r14.getLines()     // Catch: java.lang.Throwable -> Lc3
            java.lang.String r13 = org.apache.tika.utils.StringUtils.joinWith(r0, r13)     // Catch: java.lang.Throwable -> Lc3
            r12.stderr = r13     // Catch: java.lang.Throwable -> Lc3
            boolean r13 = r4.getIsTruncated()     // Catch: java.lang.Throwable -> Lc3
            r12.stdoutTruncated = r13     // Catch: java.lang.Throwable -> Lc3
            boolean r13 = r14.getIsTruncated()     // Catch: java.lang.Throwable -> Lc3
            r12.stderrTruncated = r13     // Catch: java.lang.Throwable -> Lc3
            if (r11 == 0) goto Lbd
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r11)
        Lbd:
            if (r1 == 0) goto Lc2
            release(r1)
        Lc2:
            return r12
        Lc3:
            r12 = move-exception
            r10 = r1
            r1 = r11
            r11 = r10
            goto Lca
        Lc8:
            r12 = move-exception
            r11 = r1
        Lca:
            if (r1 == 0) goto Lcf
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r1)
        Lcf:
            if (r11 == 0) goto Ld4
            release(r11)
        Ld4:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.utils.ProcessUtils.execute(java.lang.ProcessBuilder, long, int, int):org.apache.tika.utils.FileProcessResult");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.apache.tika.utils.FileProcessResult execute(java.lang.ProcessBuilder r9, long r10, java.nio.file.Path r12, int r13) throws java.lang.Throwable {
        /*
            java.nio.file.Path r0 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(r12)
            r1 = 0
            java.nio.file.LinkOption[] r2 = new java.nio.file.LinkOption[r1]
            boolean r0 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1732m(r0, r2)
            if (r0 != 0) goto L16
            java.nio.file.Path r0 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(r12)
            java.nio.file.attribute.FileAttribute[] r2 = new java.nio.file.attribute.FileAttribute[r1]
            kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(r0, r2)
        L16:
            java.io.File r0 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2132m(r12)
            org.apache.tika.utils.ProcessUtils$$ExternalSyntheticApiModelOutline0.m(r9, r0)
            r0 = 0
            java.lang.Process r9 = r9.start()     // Catch: java.lang.Throwable -> L9e
            java.lang.String r0 = register(r9)     // Catch: java.lang.Throwable -> L99
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L99
            org.apache.tika.utils.StreamGobbler r4 = new org.apache.tika.utils.StreamGobbler     // Catch: java.lang.Throwable -> L99
            java.io.InputStream r5 = r9.getErrorStream()     // Catch: java.lang.Throwable -> L99
            r4.<init>(r5, r13)     // Catch: java.lang.Throwable -> L99
            java.lang.Thread r13 = new java.lang.Thread     // Catch: java.lang.Throwable -> L99
            r13.<init>(r4)     // Catch: java.lang.Throwable -> L99
            r13.start()     // Catch: java.lang.Throwable -> L99
            r5 = -1
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.InterruptedException -> L5c java.lang.Throwable -> L99
            boolean r10 = okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r9, r10, r7)     // Catch: java.lang.InterruptedException -> L5c java.lang.Throwable -> L99
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.InterruptedException -> L5d java.lang.Throwable -> L99
            long r5 = r5 - r2
            r2 = 1000(0x3e8, double:4.94E-321)
            if (r10 == 0) goto L54
            int r11 = r9.exitValue()     // Catch: java.lang.InterruptedException -> L5d java.lang.Throwable -> L99
            r13.join(r2)     // Catch: java.lang.InterruptedException -> L5d java.lang.Throwable -> L99
            goto L5f
        L54:
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r9)     // Catch: java.lang.InterruptedException -> L5d java.lang.Throwable -> L99
            r13.join(r2)     // Catch: java.lang.InterruptedException -> L5d java.lang.Throwable -> L99
            r11 = -1
            goto L5f
        L5c:
            r10 = r1
        L5d:
            r11 = -1000(0xfffffffffffffc18, float:NaN)
        L5f:
            org.apache.tika.utils.FileProcessResult r13 = new org.apache.tika.utils.FileProcessResult     // Catch: java.lang.Throwable -> L99
            r13.<init>()     // Catch: java.lang.Throwable -> L99
            r13.processTimeMillis = r5     // Catch: java.lang.Throwable -> L99
            long r2 = r4.getStreamLength()     // Catch: java.lang.Throwable -> L99
            r13.stderrLength = r2     // Catch: java.lang.Throwable -> L99
            long r2 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m1700m(r12)     // Catch: java.lang.Throwable -> L99
            r13.stdoutLength = r2     // Catch: java.lang.Throwable -> L99
            r10 = r10 ^ 1
            r13.isTimeout = r10     // Catch: java.lang.Throwable -> L99
            r13.exitValue = r11     // Catch: java.lang.Throwable -> L99
            java.lang.String r10 = ""
            r13.stdout = r10     // Catch: java.lang.Throwable -> L99
            java.lang.String r10 = "\n"
            java.util.List r11 = r4.getLines()     // Catch: java.lang.Throwable -> L99
            java.lang.String r10 = org.apache.tika.utils.StringUtils.joinWith(r10, r11)     // Catch: java.lang.Throwable -> L99
            r13.stderr = r10     // Catch: java.lang.Throwable -> L99
            r13.stdoutTruncated = r1     // Catch: java.lang.Throwable -> L99
            boolean r10 = r4.getIsTruncated()     // Catch: java.lang.Throwable -> L99
            r13.stderrTruncated = r10     // Catch: java.lang.Throwable -> L99
            if (r9 == 0) goto L95
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r9)
        L95:
            release(r0)
            return r13
        L99:
            r10 = move-exception
            r8 = r0
            r0 = r9
            r9 = r8
            goto La0
        L9e:
            r10 = move-exception
            r9 = r0
        La0:
            if (r0 == 0) goto La5
            okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(r0)
        La5:
            release(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.utils.ProcessUtils.execute(java.lang.ProcessBuilder, long, java.nio.file.Path, int):org.apache.tika.utils.FileProcessResult");
    }
}
