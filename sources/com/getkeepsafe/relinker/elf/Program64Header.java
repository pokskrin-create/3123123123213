package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Program64Header extends Elf.ProgramHeader {
    public Program64Header(final ElfParser parser, final Elf.Header header, final long index) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j = header.phoff + (index * header.phentsize);
        this.type = parser.readWord(byteBufferAllocate, j);
        this.offset = parser.readLong(byteBufferAllocate, 8 + j);
        this.vaddr = parser.readLong(byteBufferAllocate, 16 + j);
        this.memsz = parser.readLong(byteBufferAllocate, j + 40);
    }
}
