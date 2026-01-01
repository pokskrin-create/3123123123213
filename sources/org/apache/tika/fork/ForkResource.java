package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface ForkResource {
    Throwable process(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException;
}
