package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
class InputStreamProxy extends InputStream implements ForkProxy {
    private static final long serialVersionUID = 4350939227765568438L;
    private transient DataInputStream input;
    private transient DataOutputStream output;
    private final int resource;

    public InputStreamProxy(int i) {
        this.resource = i;
    }

    @Override // org.apache.tika.fork.ForkProxy
    public void init(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.input = dataInputStream;
        this.output = dataOutputStream;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        this.output.writeByte(3);
        this.output.writeByte(this.resource);
        this.output.writeInt(1);
        this.output.flush();
        int i = this.input.readInt();
        return i == 1 ? this.input.readUnsignedByte() : i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.output.writeByte(3);
        this.output.writeByte(this.resource);
        this.output.writeInt(i2);
        this.output.flush();
        int i3 = this.input.readInt();
        if (i3 > 0) {
            this.input.readFully(bArr, i, i3);
        }
        return i3;
    }
}
