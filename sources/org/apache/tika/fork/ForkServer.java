package org.apache.tika.fork;

import androidx.lifecycle.CoroutineLiveDataKt;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
class ForkServer implements Runnable {
    public static final byte CALL = 1;
    public static final byte DONE = 0;
    public static final byte ERROR = -1;
    public static final byte FAILED_TO_START = 5;
    public static final byte INIT_LOADER_PARSER = 7;
    public static final byte INIT_PARSER_FACTORY_FACTORY = 6;
    public static final byte INIT_PARSER_FACTORY_FACTORY_LOADER = 8;
    public static final byte PING = 2;
    public static final byte READY = 4;
    public static final byte RESOURCE = 3;
    private ClassLoader classLoader;
    private final DataInputStream input;
    private final DataOutputStream output;
    private Object parser;
    private boolean parsing;
    private long serverParserTimeoutMillis;
    private long serverPulseMillis;
    private long serverWaitTimeoutMillis;
    private final Object[] lock = new Object[0];
    private final boolean active = true;
    private long since = System.currentTimeMillis();

    public ForkServer(InputStream inputStream, OutputStream outputStream, long j, long j2, long j3) throws IOException {
        this.serverPulseMillis = CoroutineLiveDataKt.DEFAULT_TIMEOUT;
        this.serverParserTimeoutMillis = 60000L;
        this.serverWaitTimeoutMillis = 60000L;
        this.parsing = false;
        this.input = new DataInputStream(inputStream);
        this.output = new DataOutputStream(outputStream);
        this.serverPulseMillis = j;
        this.serverParserTimeoutMillis = j2;
        this.serverWaitTimeoutMillis = j3;
        this.parsing = false;
    }

    public static void main(String[] strArr) throws Exception {
        long j = Long.parseLong(strArr[0]);
        long j2 = Long.parseLong(strArr[1]);
        long j3 = Long.parseLong(strArr[2]);
        URL.setURLStreamHandlerFactory(new MemoryURLStreamHandlerFactory());
        ForkServer forkServer = new ForkServer(System.in, System.out, j, j2, j3);
        System.setIn(new ByteArrayInputStream(new byte[0]));
        System.setOut(System.err);
        Thread thread = new Thread(forkServer, "Tika Watchdog");
        thread.setDaemon(true);
        thread.start();
        forkServer.processRequests();
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        while (true) {
            try {
                synchronized (this.lock) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.since;
                    boolean z = this.parsing;
                    if (!z || jCurrentTimeMillis <= this.serverParserTimeoutMillis) {
                        if (!z) {
                            long j = this.serverWaitTimeoutMillis;
                            if (j > 0 && jCurrentTimeMillis > j) {
                            }
                        }
                    }
                }
                Thread.sleep(this.serverPulseMillis);
            } catch (InterruptedException unused) {
                return;
            }
        }
        System.exit(0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        throw new java.lang.IllegalStateException("Unexpected request");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void processRequests() throws java.io.IOException {
        /*
            r2 = this;
            r2.initializeParserAndLoader()     // Catch: java.lang.Throwable -> L38
        L3:
            java.io.DataInputStream r0 = r2.input     // Catch: java.lang.Throwable -> L2e
            int r0 = r0.read()     // Catch: java.lang.Throwable -> L2e
            r1 = -1
            if (r0 != r1) goto Ld
            goto L32
        Ld:
            r1 = 2
            if (r0 != r1) goto L16
            java.io.DataOutputStream r0 = r2.output     // Catch: java.lang.Throwable -> L2e
            r0.writeByte(r1)     // Catch: java.lang.Throwable -> L2e
            goto L20
        L16:
            r1 = 1
            if (r0 != r1) goto L26
            java.lang.ClassLoader r0 = r2.classLoader     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r1 = r2.parser     // Catch: java.lang.Throwable -> L2e
            r2.call(r0, r1)     // Catch: java.lang.Throwable -> L2e
        L20:
            java.io.DataOutputStream r0 = r2.output     // Catch: java.lang.Throwable -> L2e
            r0.flush()     // Catch: java.lang.Throwable -> L2e
            goto L3
        L26:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L2e
            java.lang.String r1 = "Unexpected request"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L2e
            throw r0     // Catch: java.lang.Throwable -> L2e
        L2e:
            r0 = move-exception
            r0.printStackTrace()
        L32:
            java.io.PrintStream r0 = java.lang.System.err
            r0.flush()
            return
        L38:
            r0 = move-exception
            r0.printStackTrace()
            java.io.PrintStream r0 = java.lang.System.err
            r0.flush()
            java.io.DataOutputStream r0 = r2.output     // Catch: java.io.IOException -> L4d
            r1 = 5
            r0.writeByte(r1)     // Catch: java.io.IOException -> L4d
            java.io.DataOutputStream r0 = r2.output     // Catch: java.io.IOException -> L4d
            r0.flush()     // Catch: java.io.IOException -> L4d
            goto L56
        L4d:
            r0 = move-exception
            r0.printStackTrace()
            java.io.PrintStream r0 = java.lang.System.err
            r0.flush()
        L56:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.fork.ForkServer.processRequests():void");
    }

    private void initializeParserAndLoader() throws TikaException, SAXException, IOException, ClassNotFoundException {
        this.output.writeByte(4);
        this.output.flush();
        int i = this.input.read();
        if (i == -1) {
            throw new TikaException("eof! pipe closed?!");
        }
        Object object = readObject(ForkServer.class.getClassLoader());
        if (i != 6) {
            if (i != 7) {
                if (i == 8) {
                    if (object instanceof ParserFactoryFactory) {
                        this.parser = ((ParserFactoryFactory) object).build().build();
                        this.classLoader = (ClassLoader) readObject(ForkServer.class.getClassLoader());
                        Thread.currentThread().setContextClassLoader(this.classLoader);
                    } else {
                        throw new IllegalStateException("Expecing ParserFactoryFactory followed by a class loader");
                    }
                }
            } else if (object instanceof ClassLoader) {
                this.classLoader = (ClassLoader) object;
                Thread.currentThread().setContextClassLoader(this.classLoader);
                this.parser = readObject(this.classLoader);
            } else {
                throw new IllegalArgumentException("Expecting ClassLoader followed by a Parser");
            }
        } else if (object instanceof ParserFactoryFactory) {
            this.classLoader = ForkServer.class.getClassLoader();
            this.parser = ((ParserFactoryFactory) object).build().build();
        } else {
            throw new IllegalArgumentException("Expecting only one object of class ParserFactoryFactory");
        }
        this.output.writeByte(4);
        this.output.flush();
    }

    private void call(ClassLoader classLoader, Object obj) throws Exception {
        synchronized (this.lock) {
            this.parsing = true;
            this.since = System.currentTimeMillis();
        }
        try {
            Method method = getMethod(obj, this.input.readUTF());
            int length = method.getParameterTypes().length;
            Object[] objArr = new Object[length];
            for (int i = 0; i < length; i++) {
                objArr[i] = readObject(classLoader);
            }
            try {
                method.invoke(obj, objArr);
                this.output.write(0);
            } catch (InvocationTargetException e) {
                this.output.write(-1);
                Throwable cause = e.getCause();
                try {
                    ForkObjectInputStream.sendObject(cause, this.output);
                } catch (NotSerializableException unused) {
                    TikaException tikaException = new TikaException(cause.getMessage());
                    tikaException.setStackTrace(cause.getStackTrace());
                    ForkObjectInputStream.sendObject(tikaException, this.output);
                }
            }
            synchronized (this.lock) {
                this.parsing = false;
                this.since = System.currentTimeMillis();
            }
        } catch (Throwable th) {
            synchronized (this.lock) {
                this.parsing = false;
                this.since = System.currentTimeMillis();
                throw th;
            }
        }
    }

    private Method getMethod(Object obj, String str) throws SecurityException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            for (Class<?> cls : superclass.getInterfaces()) {
                for (Method method : cls.getMethods()) {
                    if (str.equals(method.getName())) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    private Object readObject(ClassLoader classLoader) throws IOException, ClassNotFoundException {
        Object object = ForkObjectInputStream.readObject(this.input, classLoader);
        if (object instanceof ForkProxy) {
            ((ForkProxy) object).init(this.input, this.output);
        }
        this.output.writeByte(0);
        this.output.flush();
        return object;
    }
}
