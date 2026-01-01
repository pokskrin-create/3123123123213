package org.apache.tika.fork;

import androidx.core.internal.view.SupportMenu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/* loaded from: classes4.dex */
class ClassLoaderResource implements ForkResource {
    private final ClassLoader loader;

    public ClassLoaderResource(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    @Override // org.apache.tika.fork.ForkResource
    public Throwable process(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        byte b = dataInputStream.readByte();
        String utf = dataInputStream.readUTF();
        if (b == 1) {
            InputStream resourceAsStream = this.loader.getResourceAsStream(utf);
            if (resourceAsStream != null) {
                dataOutputStream.writeBoolean(true);
                writeAndCloseStream(dataOutputStream, resourceAsStream);
            } else {
                dataOutputStream.writeBoolean(false);
            }
        } else if (b == 2) {
            Enumeration<URL> resources = this.loader.getResources(utf);
            while (resources.hasMoreElements()) {
                dataOutputStream.writeBoolean(true);
                writeAndCloseStream(dataOutputStream, resources.nextElement().openStream());
            }
            dataOutputStream.writeBoolean(false);
        }
        dataOutputStream.flush();
        return null;
    }

    private void writeAndCloseStream(DataOutputStream dataOutputStream, InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[SupportMenu.USER_MASK];
            while (true) {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    dataOutputStream.writeShort(i);
                    dataOutputStream.write(bArr, 0, i);
                } else {
                    dataOutputStream.writeShort(0);
                    return;
                }
            }
        } finally {
            inputStream.close();
        }
    }
}
