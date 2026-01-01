package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.lang.ProcessBuilder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.AbstractRecursiveParserWrapperHandler;
import org.apache.tika.sax.RecursiveParserWrapperHandler;
import org.apache.tika.utils.ProcessUtils;
import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
class ForkClient {
    private static final AtomicInteger CLIENT_COUNTER = new AtomicInteger(0);
    private volatile int filesProcessed;
    private final int id;
    private final DataInputStream input;
    private final File jar;
    private final ClassLoader loader;
    private final DataOutputStream output;
    private final Process process;
    private final List<ForkResource> resources;

    public ForkClient(Path path, ParserFactoryFactory parserFactoryFactory, List<String> list, TimeoutLimits timeoutLimits) throws TikaException, IOException {
        this(path, parserFactoryFactory, null, list, timeoutLimits);
    }

    public ForkClient(Path path, ParserFactoryFactory parserFactoryFactory, ClassLoader classLoader, List<String> list, TimeoutLimits timeoutLimits) throws TikaException, IOException {
        String str;
        ArrayList arrayList = new ArrayList();
        this.resources = arrayList;
        this.id = CLIENT_COUNTER.incrementAndGet();
        this.filesProcessed = 0;
        this.jar = null;
        this.loader = null;
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.add("-cp");
        String string = path.toAbsolutePath().toString();
        if (!string.endsWith("/")) {
            str = string + "/*";
        } else {
            str = string + "/";
        }
        arrayList2.add(ProcessUtils.escapeCommandLine(str));
        arrayList2.add("org.apache.tika.fork.ForkServer");
        arrayList2.add(Long.toString(timeoutLimits.getPulseMS()));
        arrayList2.add(Long.toString(timeoutLimits.getParseTimeoutMS()));
        arrayList2.add(Long.toString(timeoutLimits.getWaitTimeoutMS()));
        processBuilder.command(arrayList2);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            Process processStart = processBuilder.start();
            this.process = processStart;
            DataOutputStream dataOutputStream = new DataOutputStream(processStart.getOutputStream());
            this.output = dataOutputStream;
            this.input = new DataInputStream(processStart.getInputStream());
            waitForStartBeacon();
            if (classLoader != null) {
                dataOutputStream.writeByte(8);
            } else {
                dataOutputStream.writeByte(6);
            }
            dataOutputStream.flush();
            sendObject(parserFactoryFactory, arrayList);
            if (classLoader != null) {
                sendObject(classLoader, arrayList);
            }
            waitForStartBeacon();
        } finally {
        }
    }

    public ForkClient(ClassLoader classLoader, Object obj, List<String> list, TimeoutLimits timeoutLimits) throws TikaException, IOException {
        ArrayList arrayList = new ArrayList();
        this.resources = arrayList;
        this.id = CLIENT_COUNTER.incrementAndGet();
        this.filesProcessed = 0;
        try {
            this.loader = classLoader;
            File fileCreateBootstrapJar = createBootstrapJar();
            this.jar = fileCreateBootstrapJar;
            ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
            ArrayList arrayList2 = new ArrayList(list);
            arrayList2.add("-jar");
            arrayList2.add(fileCreateBootstrapJar.getPath());
            arrayList2.add(Long.toString(timeoutLimits.getPulseMS()));
            arrayList2.add(Long.toString(timeoutLimits.getParseTimeoutMS()));
            arrayList2.add(Long.toString(timeoutLimits.getWaitTimeoutMS()));
            processBuilder.command(arrayList2);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process processStart = processBuilder.start();
            this.process = processStart;
            DataOutputStream dataOutputStream = new DataOutputStream(processStart.getOutputStream());
            this.output = dataOutputStream;
            this.input = new DataInputStream(processStart.getInputStream());
            waitForStartBeacon();
            dataOutputStream.writeByte(7);
            dataOutputStream.flush();
            sendObject(classLoader, arrayList);
            sendObject(obj, arrayList);
            waitForStartBeacon();
        } catch (Throwable th) {
            close();
            throw th;
        }
    }

    private static File createBootstrapJar() throws IOException {
        File file = Files.createTempFile("apache-tika-fork-", ".jar", new FileAttribute[0]).toFile();
        try {
            fillBootstrapJar(file);
            return file;
        } catch (Throwable th) {
            file.delete();
            throw th;
        }
    }

    private static void fillBootstrapJar(File file) throws IOException {
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file));
        try {
            String str = "Main-Class: " + ForkServer.class.getName() + "\n";
            jarOutputStream.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            jarOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            Class[] clsArr = {ForkServer.class, ForkObjectInputStream.class, ForkProxy.class, ClassLoaderProxy.class, MemoryURLConnection.class, MemoryURLStreamHandler.class, MemoryURLStreamHandlerFactory.class, MemoryURLStreamRecord.class, TikaException.class};
            ClassLoader classLoader = ForkServer.class.getClassLoader();
            for (int i = 0; i < 9; i++) {
                String str2 = clsArr[i].getName().replace(FilenameUtils.EXTENSION_SEPARATOR, IOUtils.DIR_SEPARATOR_UNIX) + ".class";
                InputStream resourceAsStream = classLoader.getResourceAsStream(str2);
                try {
                    jarOutputStream.putNextEntry(new JarEntry(str2));
                    IOUtils.copy(resourceAsStream, jarOutputStream);
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                } catch (Throwable th) {
                    if (resourceAsStream == null) {
                        throw th;
                    }
                    try {
                        resourceAsStream.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            }
            jarOutputStream.close();
        } finally {
        }
    }

    private void waitForStartBeacon() throws IOException {
        int i;
        do {
            i = this.input.read();
            byte b = (byte) i;
            if (b == 4) {
                return;
            }
            if (b == 5) {
                throw new IOException("Server had a catastrophic initialization failure");
            }
        } while (i != -1);
        throw new IOException("EOF while waiting for start beacon");
    }

    public synchronized boolean ping() {
        try {
            this.output.writeByte(2);
            this.output.flush();
        } catch (IOException unused) {
            return false;
        }
        return this.input.read() == 2;
    }

    public synchronized Throwable call(String str, Object... objArr) throws TikaException, IOException {
        ArrayList arrayList;
        this.filesProcessed++;
        arrayList = new ArrayList(this.resources);
        this.output.writeByte(1);
        this.output.writeUTF(str);
        for (Object obj : objArr) {
            sendObject(obj, arrayList);
        }
        return waitForResponse(arrayList);
    }

    public int getFilesProcessed() {
        return this.filesProcessed;
    }

    private void sendObject(Object obj, List<ForkResource> list) throws TikaException, IOException {
        int size = list.size();
        if (obj instanceof InputStream) {
            list.add(new InputStreamResource((InputStream) obj));
            obj = new InputStreamProxy(size);
        } else if (obj instanceof RecursiveParserWrapperHandler) {
            RecursiveParserWrapperHandler recursiveParserWrapperHandler = (RecursiveParserWrapperHandler) obj;
            list.add(new RecursiveMetadataContentHandlerResource(recursiveParserWrapperHandler));
            obj = new RecursiveMetadataContentHandlerProxy(size, recursiveParserWrapperHandler.getContentHandlerFactory());
        } else if ((obj instanceof ContentHandler) && !(obj instanceof AbstractRecursiveParserWrapperHandler)) {
            list.add(new ContentHandlerResource((ContentHandler) obj));
            obj = new ContentHandlerProxy(size);
        } else if (obj instanceof ClassLoader) {
            list.add(new ClassLoaderResource((ClassLoader) obj));
            obj = new ClassLoaderProxy(size);
        }
        try {
            ForkObjectInputStream.sendObject(obj, this.output);
            waitForResponse(list);
        } catch (NotSerializableException e) {
            throw new TikaException("Unable to serialize " + obj.getClass().getSimpleName() + " to pass to the Forked Parser", e);
        }
    }

    public synchronized void close() {
        try {
            DataOutputStream dataOutputStream = this.output;
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            DataInputStream dataInputStream = this.input;
            if (dataInputStream != null) {
                dataInputStream.close();
            }
        } catch (IOException unused) {
        }
        Process process = this.process;
        if (process != null) {
            process.destroyForcibly();
            try {
                this.process.waitFor();
            } catch (InterruptedException unused2) {
            }
        }
        File file = this.jar;
        if (file != null) {
            file.delete();
        }
    }

    private Throwable waitForResponse(List<ForkResource> list) throws IOException {
        this.output.flush();
        while (!Thread.currentThread().isInterrupted()) {
            int i = this.input.read();
            if (i == -1) {
                throw new IOException("Lost connection to a forked server process");
            }
            if (i != 3) {
                if (((byte) i) != -1) {
                    return null;
                }
                try {
                    return (Throwable) ForkObjectInputStream.readObject(this.input, this.loader);
                } catch (ClassNotFoundException e) {
                    throw new IOException("Unable to deserialize an exception", e);
                }
            }
            list.get(this.input.readUnsignedByte()).process(this.input, this.output);
        }
        throw new IOException(new InterruptedException());
    }

    public int getId() {
        return this.id;
    }
}
