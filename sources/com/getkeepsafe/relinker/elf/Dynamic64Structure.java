package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Dynamic64Structure extends Elf.DynamicStructure {
    public Dynamic64Structure(final ElfParser parser, final Elf.Header header, long baseOffset, final int index) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j = baseOffset + (index * 16);
        this.tag = parser.readLong(byteBufferAllocate, j);
        this.val = parser.readLong(byteBufferAllocate, j + 8);
    }
}
