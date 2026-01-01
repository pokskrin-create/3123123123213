package org.apache.commons.io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.UByte$$ExternalSyntheticBackport0;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileSystem;
import org.apache.commons.io.StandardLineSeparator;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOIterable;
import org.apache.commons.io.function.IOIterator;

/* loaded from: classes4.dex */
public class ReversedLinesFileReader implements Closeable, IOIterable<String> {
    private static final int DEFAULT_BLOCK_SIZE = FileSystem.getCurrent().getBlockSize();
    private static final String EMPTY_STRING = "";
    private final int avoidNewlineSplitBufferSize;
    private final int blockSize;
    private final int byteDecrement;
    private final SeekableByteChannel channel;
    private final Charset charset;
    private FilePart currentFilePart;
    private final byte[][] newLineSequences;
    private final long totalBlockCount;
    private final long totalByteLength;
    private boolean trailingNewlineOfFileSkipped;

    @Override // org.apache.commons.io.function.IOIterable
    public Iterable<String> unwrap() {
        return null;
    }

    public static class Builder extends AbstractStreamBuilder<ReversedLinesFileReader, Builder> {
        public Builder() {
            setBufferSizeDefault(ReversedLinesFileReader.DEFAULT_BLOCK_SIZE);
            setBufferSize(ReversedLinesFileReader.DEFAULT_BLOCK_SIZE);
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public ReversedLinesFileReader get() throws IOException {
            return new ReversedLinesFileReader(getPath(), getBufferSize(), getCharset());
        }
    }

    private final class FilePart {
        private int currentLastBytePos;
        private final byte[] data;
        private byte[] leftOver;
        private final long partNumber;

        private FilePart(long j, int i, byte[] bArr) throws IOException {
            this.partNumber = j;
            byte[] bArr2 = new byte[(bArr != null ? bArr.length : 0) + i];
            this.data = bArr2;
            long j2 = (j - 1) * ReversedLinesFileReader.this.blockSize;
            if (j > 0) {
                ReversedLinesFileReader.this.channel.position(j2);
                if (ReversedLinesFileReader.this.channel.read(ByteBuffer.wrap(bArr2, 0, i)) != i) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr2, i, bArr.length);
            }
            this.currentLastBytePos = bArr2.length - 1;
            this.leftOver = null;
        }

        private void createLeftOver() {
            int i = this.currentLastBytePos + 1;
            if (i > 0) {
                this.leftOver = Arrays.copyOf(this.data, i);
            } else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }

        private int getNewLineMatchByteCount(byte[] bArr, int i) {
            for (byte[] bArr2 : ReversedLinesFileReader.this.newLineSequences) {
                boolean z = true;
                for (int length = bArr2.length - 1; length >= 0; length--) {
                    int length2 = (i + length) - (bArr2.length - 1);
                    z &= length2 >= 0 && bArr[length2] == bArr2[length];
                }
                if (z) {
                    return bArr2.length;
                }
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:
        
            r5 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0067, code lost:
        
            if (r0 == false) goto L29;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x006b, code lost:
        
            if (r7.leftOver == null) goto L29;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x006d, code lost:
        
            r0 = new java.lang.String(r7.leftOver, r7.this$0.charset);
            r7.leftOver = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x007c, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x007d, code lost:
        
            return r5;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String readLine() {
            /*
                r7 = this;
                long r0 = r7.partNumber
                r2 = 1
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r1 = 1
                if (r0 != 0) goto Lb
                r0 = r1
                goto Lc
            Lb:
                r0 = 0
            Lc:
                int r2 = r7.currentLastBytePos
            Le:
                r3 = -1
                r4 = 0
                if (r2 <= r3) goto L66
                if (r0 != 0) goto L20
                org.apache.commons.io.input.ReversedLinesFileReader r3 = org.apache.commons.io.input.ReversedLinesFileReader.this
                int r3 = org.apache.commons.io.input.ReversedLinesFileReader.access$400(r3)
                if (r2 >= r3) goto L20
                r7.createLeftOver()
                goto L66
            L20:
                byte[] r3 = r7.data
                int r3 = r7.getNewLineMatchByteCount(r3, r2)
                if (r3 <= 0) goto L5a
                int r5 = r2 + 1
                int r6 = r7.currentLastBytePos
                int r6 = r6 - r5
                int r6 = r6 + r1
                if (r6 < 0) goto L46
                byte[] r1 = r7.data
                int r6 = r6 + r5
                byte[] r1 = java.util.Arrays.copyOfRange(r1, r5, r6)
                java.lang.String r5 = new java.lang.String
                org.apache.commons.io.input.ReversedLinesFileReader r6 = org.apache.commons.io.input.ReversedLinesFileReader.this
                java.nio.charset.Charset r6 = org.apache.commons.io.input.ReversedLinesFileReader.access$500(r6)
                r5.<init>(r1, r6)
                int r2 = r2 - r3
                r7.currentLastBytePos = r2
                goto L67
            L46:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "Unexpected negative line length="
                r1.<init>(r2)
                r1.append(r6)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L5a:
                org.apache.commons.io.input.ReversedLinesFileReader r3 = org.apache.commons.io.input.ReversedLinesFileReader.this
                int r3 = org.apache.commons.io.input.ReversedLinesFileReader.access$600(r3)
                int r2 = r2 - r3
                if (r2 >= 0) goto Le
                r7.createLeftOver()
            L66:
                r5 = r4
            L67:
                if (r0 == 0) goto L7d
                byte[] r0 = r7.leftOver
                if (r0 == 0) goto L7d
                java.lang.String r0 = new java.lang.String
                byte[] r1 = r7.leftOver
                org.apache.commons.io.input.ReversedLinesFileReader r2 = org.apache.commons.io.input.ReversedLinesFileReader.this
                java.nio.charset.Charset r2 = org.apache.commons.io.input.ReversedLinesFileReader.access$500(r2)
                r0.<init>(r1, r2)
                r7.leftOver = r4
                return r0
            L7d:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.input.ReversedLinesFileReader.FilePart.readLine():java.lang.String");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FilePart rollOver() throws IOException {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            }
            long j = this.partNumber;
            if (j > 1) {
                ReversedLinesFileReader reversedLinesFileReader = ReversedLinesFileReader.this;
                return reversedLinesFileReader.new FilePart(j - 1, reversedLinesFileReader.blockSize, this.leftOver);
            }
            if (this.leftOver == null) {
                return null;
            }
            throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=".concat(new String(this.leftOver, ReversedLinesFileReader.this.charset)));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public ReversedLinesFileReader(File file) throws IOException {
        this(file, DEFAULT_BLOCK_SIZE, Charset.defaultCharset());
    }

    @Deprecated
    public ReversedLinesFileReader(File file, Charset charset) throws IOException {
        this(file.toPath(), charset);
    }

    @Deprecated
    public ReversedLinesFileReader(File file, int i, Charset charset) throws IOException {
        this(file.toPath(), i, charset);
    }

    @Deprecated
    public ReversedLinesFileReader(File file, int i, String str) throws IOException {
        this(file.toPath(), i, str);
    }

    @Deprecated
    public ReversedLinesFileReader(Path path, Charset charset) throws IOException {
        this(path, DEFAULT_BLOCK_SIZE, charset);
    }

    @Deprecated
    public ReversedLinesFileReader(Path path, int i, Charset charset) throws IOException {
        int i2;
        this.blockSize = i;
        Charset charset2 = Charsets.toCharset(charset);
        this.charset = charset2;
        if (charset2.newEncoder().maxBytesPerChar() == 1.0f || charset2 == StandardCharsets.UTF_8 || charset2 == Charset.forName("Shift_JIS") || charset2 == Charset.forName("windows-31j") || charset2 == Charset.forName("x-windows-949") || charset2 == Charset.forName("gbk") || charset2 == Charset.forName("x-windows-950")) {
            this.byteDecrement = 1;
        } else if (charset2 == StandardCharsets.UTF_16BE || charset2 == StandardCharsets.UTF_16LE) {
            this.byteDecrement = 2;
        } else {
            if (charset2 == StandardCharsets.UTF_16) {
                throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
            }
            throw new UnsupportedEncodingException("Encoding " + charset + " is not supported yet (feel free to submit a patch)");
        }
        byte[][] bArr = {StandardLineSeparator.CRLF.getBytes(charset2), StandardLineSeparator.LF.getBytes(charset2), StandardLineSeparator.CR.getBytes(charset2)};
        this.newLineSequences = bArr;
        this.avoidNewlineSplitBufferSize = bArr[0].length;
        SeekableByteChannel seekableByteChannelNewByteChannel = Files.newByteChannel(path, StandardOpenOption.READ);
        this.channel = seekableByteChannelNewByteChannel;
        long size = seekableByteChannelNewByteChannel.size();
        this.totalByteLength = size;
        long j = i;
        int i3 = (int) (size % j);
        if (i3 > 0) {
            this.totalBlockCount = (size / j) + 1;
        } else {
            this.totalBlockCount = size / j;
            i2 = size > 0 ? i : i2;
            this.currentFilePart = new FilePart(this.totalBlockCount, i2, null);
        }
        i2 = i3;
        this.currentFilePart = new FilePart(this.totalBlockCount, i2, null);
    }

    @Deprecated
    public ReversedLinesFileReader(Path path, int i, String str) throws IOException {
        this(path, i, Charsets.toCharset(str));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.channel.close();
    }

    @Override // org.apache.commons.io.function.IOIterable
    public IOIterator<String> iterator() {
        return new IOIterator<String>() { // from class: org.apache.commons.io.input.ReversedLinesFileReader.1
            private String next;

            @Override // org.apache.commons.io.function.IOIterator
            public Iterator<String> unwrap() {
                return null;
            }

            @Override // org.apache.commons.io.function.IOIterator
            public boolean hasNext() throws IOException {
                if (this.next == null) {
                    this.next = ReversedLinesFileReader.this.readLine();
                }
                return this.next != null;
            }

            @Override // org.apache.commons.io.function.IOIterator
            public String next() throws IOException {
                if (this.next == null) {
                    this.next = ReversedLinesFileReader.this.readLine();
                }
                String str = this.next;
                this.next = null;
                return str;
            }
        };
    }

    public String readLine() throws IOException {
        String line = this.currentFilePart.readLine();
        while (line == null) {
            FilePart filePartRollOver = this.currentFilePart.rollOver();
            this.currentFilePart = filePartRollOver;
            if (filePartRollOver == null) {
                break;
            }
            line = filePartRollOver.readLine();
        }
        if (!"".equals(line) || this.trailingNewlineOfFileSkipped) {
            return line;
        }
        this.trailingNewlineOfFileSkipped = true;
        return readLine();
    }

    public List<String> readLines(int i) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException("lineCount < 0");
        }
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            String line = readLine();
            if (line == null) {
                break;
            }
            arrayList.add(line);
        }
        return arrayList;
    }

    public String toString(int i) throws IOException {
        List<String> lines = readLines(i);
        Collections.reverse(lines);
        if (lines.isEmpty()) {
            return "";
        }
        return UByte$$ExternalSyntheticBackport0.m((CharSequence) System.lineSeparator(), (Iterable) lines) + System.lineSeparator();
    }
}
