package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Elf32Header extends Elf.Header {
    private final ElfParser parser;

    public Elf32Header(final boolean bigEndian, final ElfParser parser) throws IOException {
        this.bigEndian = bigEndian;
        this.parser = parser;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = parser.readHalf(byteBufferAllocate, 16L);
        this.phoff = parser.readWord(byteBufferAllocate, 28L);
        this.shoff = parser.readWord(byteBufferAllocate, 32L);
        this.phentsize = parser.readHalf(byteBufferAllocate, 42L);
        this.phnum = parser.readHalf(byteBufferAllocate, 44L);
        this.shentsize = parser.readHalf(byteBufferAllocate, 46L);
        this.shnum = parser.readHalf(byteBufferAllocate, 48L);
        this.shstrndx = parser.readHalf(byteBufferAllocate, 50L);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(final int index) throws IOException {
        return new Section32Header(this.parser, this, index);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(final long index) throws IOException {
        return new Program32Header(this.parser, this, index);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(final long baseOffset, final int index) throws IOException {
        return new Dynamic32Structure(this.parser, this, baseOffset, index);
    }
}
