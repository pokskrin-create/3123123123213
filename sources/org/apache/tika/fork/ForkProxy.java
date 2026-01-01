package org.apache.tika.fork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

/* loaded from: classes4.dex */
public interface ForkProxy extends Serializable {
    void init(DataInputStream dataInputStream, DataOutputStream dataOutputStream);
}
