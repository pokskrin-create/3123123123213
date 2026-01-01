package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Program32Header extends Elf.ProgramHeader {
    public Program32Header(final ElfParser parser, final Elf.Header header, final long index) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j = header.phoff + (index * header.phentsize);
        this.type = parser.readWord(byteBufferAllocate, j);
        this.offset = parser.readWord(byteBufferAllocate, 4 + j);
        this.vaddr = parser.readWord(byteBufferAllocate, 8 + j);
        this.memsz = parser.readWord(byteBufferAllocate, j + 20);
    }
}
