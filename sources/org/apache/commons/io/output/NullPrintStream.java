package org.apache.commons.io.output;

import java.io.PrintStream;

/* loaded from: classes4.dex */
public class NullPrintStream extends PrintStream {
    public static final NullPrintStream INSTANCE;

    @Deprecated
    public static final NullPrintStream NULL_PRINT_STREAM;

    static {
        NullPrintStream nullPrintStream = new NullPrintStream();
        INSTANCE = nullPrintStream;
        NULL_PRINT_STREAM = nullPrintStream;
    }

    @Deprecated
    public NullPrintStream() {
        super(NullOutputStream.INSTANCE);
    }
}
