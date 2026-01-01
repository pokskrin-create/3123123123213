package org.apache.tika.fork;

import androidx.core.internal.view.SupportMenu;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
class ClassLoaderProxy extends ClassLoader implements ForkProxy {
    private static final long serialVersionUID = -7303109260448540420L;
    private transient DataInputStream input;
    private final Set<String> notFound = new HashSet();
    private transient DataOutputStream output;
    private final int resource;

    public ClassLoaderProxy(int i) {
        this.resource = i;
    }

    @Override // org.apache.tika.fork.ForkProxy
    public void init(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.input = dataInputStream;
        this.output = dataOutputStream;
    }

    @Override // java.lang.ClassLoader
    protected synchronized URL findResource(String str) {
        if (this.notFound.contains(str)) {
            return null;
        }
        try {
            this.output.write(3);
            this.output.write(this.resource);
            this.output.write(1);
            this.output.writeUTF(str);
            this.output.flush();
            if (this.input.readBoolean()) {
                return MemoryURLStreamHandler.createURL(readStream());
            }
            this.notFound.add(str);
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.lang.ClassLoader
    protected synchronized Enumeration<URL> findResources(String str) throws IOException {
        ArrayList arrayList;
        this.output.write(3);
        this.output.write(this.resource);
        this.output.write(2);
        this.output.writeUTF(str);
        this.output.flush();
        arrayList = new ArrayList();
        while (this.input.readBoolean()) {
            arrayList.add(MemoryURLStreamHandler.createURL(readStream()));
        }
        return Collections.enumeration(arrayList);
    }

    @Override // java.lang.ClassLoader
    protected synchronized Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> clsDefineClass;
        try {
            this.output.write(3);
            this.output.write(this.resource);
            this.output.write(1);
            this.output.writeUTF(str.replace(FilenameUtils.EXTENSION_SEPARATOR, IOUtils.DIR_SEPARATOR_UNIX) + ".class");
            this.output.flush();
            if (this.input.readBoolean()) {
                byte[] stream = readStream();
                clsDefineClass = defineClass(str, stream, 0, stream.length);
                definePackageIfNecessary(str, clsDefineClass);
            } else {
                throw new ClassNotFoundException("Unable to find class " + str);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Unable to load class " + str, e);
        }
        return clsDefineClass;
    }

    private void definePackageIfNecessary(String str, Class<?> cls) {
        String packageName = toPackageName(str);
        if (packageName == null || getDefinedPackage(packageName) != null) {
            return;
        }
        definePackage(packageName, null, null, null, null, null, null, null);
    }

    private String toPackageName(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf > 0) {
            return str.substring(0, iLastIndexOf);
        }
        return null;
    }

    private byte[] readStream() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[SupportMenu.USER_MASK];
            while (true) {
                int unsignedShort = this.input.readUnsignedShort();
                if (unsignedShort > 0) {
                    this.input.readFully(bArr, 0, unsignedShort);
                    byteArrayOutputStream.write(bArr, 0, unsignedShort);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
