package org.apache.tika.fork;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/* loaded from: classes4.dex */
class ForkObjectInputStream extends ObjectInputStream {
    private final ClassLoader loader;

    public ForkObjectInputStream(InputStream inputStream, ClassLoader classLoader) throws IOException {
        super(inputStream);
        this.loader = classLoader;
    }

    public static void sendObject(Object obj, DataOutputStream dataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try {
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            dataOutputStream.writeInt(byteArray.length);
            dataOutputStream.write(byteArray);
        } catch (Throwable th) {
            try {
                objectOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static Object readObject(DataInputStream dataInputStream, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        return new ForkObjectInputStream(new ByteArrayInputStream(bArr), classLoader).readObject();
    }

    @Override // java.io.ObjectInputStream
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws ClassNotFoundException {
        return Class.forName(objectStreamClass.getName(), false, this.loader);
    }
}
