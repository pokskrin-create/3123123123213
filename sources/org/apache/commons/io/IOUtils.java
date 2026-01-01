package org.apache.commons.io;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.channels.FileChannels;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOFunction;
import org.apache.commons.io.function.IOSupplier;
import org.apache.commons.io.function.IOTriFunction;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.commons.io.input.QueueInputStream;
import org.apache.commons.io.output.AppendableWriter;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.io.output.NullWriter;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;

/* loaded from: classes4.dex */
public class IOUtils {
    public static final int CR = 13;
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final int EOF = -1;
    public static final int LF = 10;
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    @Deprecated
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String LINE_SEPARATOR_UNIX = StandardLineSeparator.LF.getString();
    public static final String LINE_SEPARATOR_WINDOWS = StandardLineSeparator.CRLF.getString();
    private static final ThreadLocal<byte[]> SCRATCH_BYTE_BUFFER_RW = new IOUtils$$ExternalSyntheticThreadLocal9(new Supplier() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return IOUtils.byteArray();
        }
    });
    private static final byte[] SCRATCH_BYTE_BUFFER_WO = byteArray();
    private static final ThreadLocal<char[]> SCRATCH_CHAR_BUFFER_RW = new IOUtils$$ExternalSyntheticThreadLocal9(new Supplier() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return IOUtils.charArray();
        }
    });
    private static final char[] SCRATCH_CHAR_BUFFER_WO = charArray();

    static /* synthetic */ OutputStream lambda$toByteArray$1(UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream, ThresholdingOutputStream thresholdingOutputStream) throws IOException {
        return unsynchronizedByteArrayOutputStream;
    }

    public static BufferedInputStream buffer(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream");
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedInputStream buffer(InputStream inputStream, int i) {
        Objects.requireNonNull(inputStream, "inputStream");
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, i);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream, "outputStream");
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream, int i) {
        Objects.requireNonNull(outputStream, "outputStream");
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, i);
    }

    public static BufferedReader buffer(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader buffer(Reader reader, int i) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i);
    }

    public static BufferedWriter buffer(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static BufferedWriter buffer(Writer writer, int i) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i);
    }

    public static byte[] byteArray() {
        return byteArray(8192);
    }

    public static byte[] byteArray(int i) {
        return new byte[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char[] charArray() {
        return charArray(8192);
    }

    private static char[] charArray(int i) {
        return new char[i];
    }

    static void clear() {
        SCRATCH_BYTE_BUFFER_RW.remove();
        SCRATCH_CHAR_BUFFER_RW.remove();
        Arrays.fill(SCRATCH_BYTE_BUFFER_WO, (byte) 0);
        Arrays.fill(SCRATCH_CHAR_BUFFER_WO, (char) 0);
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static void close(Closeable... closeableArr) throws IOExceptionList {
        IOConsumer.forAll(new IOConsumer() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                IOUtils.close((Closeable) obj);
            }
        }, closeableArr);
    }

    public static void close(Closeable closeable, IOConsumer<IOException> iOConsumer) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                if (iOConsumer != null) {
                    iOConsumer.accept(e);
                }
            } catch (Exception e2) {
                if (iOConsumer != null) {
                    iOConsumer.accept(new IOException(e2));
                }
            }
        }
    }

    public static void close(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    private static void closeQ(Closeable closeable) throws IOException {
        closeQuietly(closeable, null);
    }

    public static void closeQuietly(Closeable closeable) {
        closeQuietly(closeable, null);
    }

    public static void closeQuietly(Closeable... closeableArr) {
        if (closeableArr != null) {
            closeQuietly((Stream<Closeable>) Arrays.stream(closeableArr));
        }
    }

    public static void closeQuietly(Closeable closeable, Consumer<Exception> consumer) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                if (consumer != null) {
                    consumer.accept(e);
                }
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        closeQ(inputStream);
    }

    public static void closeQuietly(Iterable<Closeable> iterable) {
        if (iterable != null) {
            iterable.forEach(new IOUtils$$ExternalSyntheticLambda2());
        }
    }

    public static void closeQuietly(OutputStream outputStream) throws IOException {
        closeQ(outputStream);
    }

    public static void closeQuietly(Reader reader) {
        closeQ(reader);
    }

    public static void closeQuietly(Selector selector) throws IOException {
        closeQ(selector);
    }

    public static void closeQuietly(ServerSocket serverSocket) throws IOException {
        closeQ(serverSocket);
    }

    public static void closeQuietly(Socket socket) throws IOException {
        closeQ(socket);
    }

    public static void closeQuietly(Stream<Closeable> stream) {
        if (stream != null) {
            stream.forEach(new IOUtils$$ExternalSyntheticLambda2());
        }
    }

    public static void closeQuietly(Writer writer) throws IOException {
        closeQ(writer);
    }

    public static long consume(InputStream inputStream) throws IOException {
        return copyLarge(inputStream, NullOutputStream.INSTANCE);
    }

    public static long consume(Reader reader) throws IOException {
        return copyLarge(reader, NullWriter.INSTANCE);
    }

    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException {
        if (inputStream == inputStream2) {
            return true;
        }
        if (inputStream == null || inputStream2 == null) {
            return false;
        }
        return FileChannels.contentEquals(Channels.newChannel(inputStream), Channels.newChannel(inputStream2), 8192);
    }

    private static boolean contentEquals(Iterator<?> it, Iterator<?> it2) {
        while (it.hasNext()) {
            if (!it2.hasNext() || !Objects.equals(it.next(), it2.next())) {
                return false;
            }
        }
        return !it2.hasNext();
    }

    public static boolean contentEquals(Reader reader, Reader reader2) throws IOException {
        int i;
        int i2;
        if (reader == reader2) {
            return true;
        }
        if (reader == null || reader2 == null) {
            return false;
        }
        char[] scratchCharArray = getScratchCharArray();
        char[] cArrCharArray = charArray();
        while (true) {
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < 8192) {
                if (i4 == i3) {
                    do {
                        i2 = reader.read(scratchCharArray, i4, 8192 - i4);
                    } while (i2 == 0);
                    if (i2 == -1) {
                        return i5 == i3 && reader2.read() == -1;
                    }
                    i4 += i2;
                }
                if (i5 == i3) {
                    do {
                        i = reader2.read(cArrCharArray, i5, 8192 - i5);
                    } while (i == 0);
                    if (i == -1) {
                        return i4 == i3 && reader.read() == -1;
                    }
                    i5 += i;
                }
                if (scratchCharArray[i3] != cArrCharArray[i3]) {
                    return false;
                }
                i3++;
            }
        }
    }

    private static boolean contentEquals(Stream<?> stream, Stream<?> stream2) {
        if (stream == stream2) {
            return true;
        }
        if (stream == null || stream2 == null) {
            return false;
        }
        return contentEquals(stream.iterator(), stream2.iterator());
    }

    private static boolean contentEqualsIgnoreEOL(BufferedReader bufferedReader, BufferedReader bufferedReader2) {
        if (bufferedReader == bufferedReader2) {
            return true;
        }
        if (bufferedReader == null || bufferedReader2 == null) {
            return false;
        }
        return contentEquals(bufferedReader.lines(), bufferedReader2.lines());
    }

    public static boolean contentEqualsIgnoreEOL(Reader reader, Reader reader2) throws UncheckedIOException {
        if (reader == reader2) {
            return true;
        }
        if (reader == null || reader2 == null) {
            return false;
        }
        return contentEqualsIgnoreEOL(toBufferedReader(reader), toBufferedReader(reader2));
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long jCopyLarge = copyLarge(inputStream, outputStream);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        return copyLarge(inputStream, outputStream, byteArray(i));
    }

    @Deprecated
    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy(inputStream, writer, Charset.defaultCharset());
    }

    public static void copy(InputStream inputStream, Writer writer, Charset charset) throws IOException {
        copy((Reader) new InputStreamReader(inputStream, Charsets.toCharset(charset)), writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        copy(inputStream, writer, Charsets.toCharset(str));
    }

    public static QueueInputStream copy(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        Objects.requireNonNull(byteArrayOutputStream, "outputStream");
        QueueInputStream queueInputStream = new QueueInputStream();
        byteArrayOutputStream.writeTo(queueInputStream.newQueueOutputStream());
        return queueInputStream;
    }

    public static long copy(Reader reader, Appendable appendable) throws IOException {
        return copy(reader, appendable, CharBuffer.allocate(8192));
    }

    public static long copy(Reader reader, Appendable appendable, CharBuffer charBuffer) throws IOException {
        long j = 0;
        while (true) {
            int i = reader.read(charBuffer);
            if (-1 == i) {
                return j;
            }
            charBuffer.flip();
            appendable.append(charBuffer, 0, i);
            j += i;
        }
    }

    @Deprecated
    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, outputStream, Charset.defaultCharset());
    }

    public static void copy(Reader reader, OutputStream outputStream, Charset charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.toCharset(charset));
        copy(reader, (Writer) outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        copy(reader, outputStream, Charsets.toCharset(str));
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long jCopyLarge = copyLarge(reader, writer);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static long copy(URL url, File file) throws IOException {
        OutputStream outputStreamNewOutputStream = Files.newOutputStream(((File) Objects.requireNonNull(file, "file")).toPath(), new OpenOption[0]);
        try {
            long jCopy = copy(url, outputStreamNewOutputStream);
            if (outputStreamNewOutputStream != null) {
                outputStreamNewOutputStream.close();
            }
            return jCopy;
        } catch (Throwable th) {
            if (outputStreamNewOutputStream != null) {
                try {
                    outputStreamNewOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static long copy(URL url, OutputStream outputStream) throws IOException {
        InputStream inputStreamOpenStream = ((URL) Objects.requireNonNull(url, "url")).openStream();
        try {
            long jCopyLarge = copyLarge(inputStreamOpenStream, outputStream);
            if (inputStreamOpenStream != null) {
                inputStreamOpenStream.close();
            }
            return jCopyLarge;
        } catch (Throwable th) {
            if (inputStreamOpenStream != null) {
                try {
                    inputStreamOpenStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 8192);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (-1 == i) {
                return j;
            }
            outputStream.write(bArr, 0, i);
            j += i;
        }
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j, long j2) throws IOException {
        return copyLarge(inputStream, outputStream, j, j2, getScratchByteArray());
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j, long j2, byte[] bArr) throws IOException {
        long j3 = 0;
        if (j > 0) {
            skipFully(inputStream, j);
        }
        if (j2 == 0) {
            return 0L;
        }
        int length = bArr.length;
        int iMin = (j2 <= 0 || j2 >= ((long) length)) ? length : (int) j2;
        while (iMin > 0) {
            int i = inputStream.read(bArr, 0, iMin);
            if (-1 == i) {
                break;
            }
            outputStream.write(bArr, 0, i);
            j3 += i;
            if (j2 > 0) {
                iMin = (int) Math.min(j2 - j3, length);
            }
        }
        return j3;
    }

    public static long copyLarge(Reader reader, Writer writer) throws IOException {
        return copyLarge(reader, writer, getScratchCharArray());
    }

    public static long copyLarge(Reader reader, Writer writer, char[] cArr) throws IOException {
        long j = 0;
        while (true) {
            int i = reader.read(cArr);
            if (-1 == i) {
                return j;
            }
            writer.write(cArr, 0, i);
            j += i;
        }
    }

    public static long copyLarge(Reader reader, Writer writer, long j, long j2) throws IOException {
        return copyLarge(reader, writer, j, j2, getScratchCharArray());
    }

    public static long copyLarge(Reader reader, Writer writer, long j, long j2, char[] cArr) throws IOException {
        long j3 = 0;
        if (j > 0) {
            skipFully(reader, j);
        }
        if (j2 == 0) {
            return 0L;
        }
        int length = cArr.length;
        if (j2 > 0 && j2 < cArr.length) {
            length = (int) j2;
        }
        while (length > 0) {
            int i = reader.read(cArr, 0, length);
            if (-1 == i) {
                break;
            }
            writer.write(cArr, 0, i);
            j3 += i;
            if (j2 > 0) {
                length = (int) Math.min(j2 - j3, cArr.length);
            }
        }
        return j3;
    }

    private static byte[] fill0(byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        return bArr;
    }

    private static char[] fill0(char[] cArr) {
        Arrays.fill(cArr, (char) 0);
        return cArr;
    }

    static byte[] getScratchByteArray() {
        return fill0(SCRATCH_BYTE_BUFFER_RW.get());
    }

    static byte[] getScratchByteArrayWriteOnly() {
        return fill0(SCRATCH_BYTE_BUFFER_WO);
    }

    static char[] getScratchCharArray() {
        return fill0(SCRATCH_CHAR_BUFFER_RW.get());
    }

    static char[] getScratchCharArrayWriteOnly() {
        return fill0(SCRATCH_CHAR_BUFFER_WO);
    }

    public static int length(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    public static int length(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        return cArr.length;
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static int length(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        return objArr.length;
    }

    public static LineIterator lineIterator(InputStream inputStream, Charset charset) {
        return new LineIterator(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static LineIterator lineIterator(InputStream inputStream, String str) {
        return lineIterator(inputStream, Charsets.toCharset(str));
    }

    public static LineIterator lineIterator(Reader reader) {
        return new LineIterator(reader);
    }

    public static int read(InputStream inputStream, byte[] bArr) throws IOException {
        return read(inputStream, bArr, 0, bArr.length);
    }

    public static int read(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        Objects.requireNonNull(inputStream);
        return read(new IOUtils$$ExternalSyntheticLambda3(inputStream), bArr, i, i2);
    }

    static int read(IOTriFunction<byte[], Integer, Integer, Integer> iOTriFunction, byte[] bArr, int i, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i2);
        }
        int i3 = i2;
        while (i3 > 0) {
            int iIntValue = iOTriFunction.apply(bArr, Integer.valueOf((i2 - i3) + i), Integer.valueOf(i3)).intValue();
            if (-1 == iIntValue) {
                break;
            }
            i3 -= iIntValue;
        }
        return i2 - i3;
    }

    public static int read(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        while (byteBuffer.remaining() > 0 && -1 != readableByteChannel.read(byteBuffer)) {
        }
        return iRemaining - byteBuffer.remaining();
    }

    public static int read(Reader reader, char[] cArr) throws IOException {
        return read(reader, cArr, 0, cArr.length);
    }

    public static int read(Reader reader, char[] cArr, int i, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i2);
        }
        int i3 = i2;
        while (i3 > 0) {
            int i4 = reader.read(cArr, (i2 - i3) + i, i3);
            if (-1 == i4) {
                break;
            }
            i3 -= i4;
        }
        return i2 - i3;
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = read(inputStream, bArr, i, i2);
        if (i3 == i2) {
            return;
        }
        throw new EOFException("Length to read: " + i2 + " actual: " + i3);
    }

    public static byte[] readFully(InputStream inputStream, int i) throws IOException {
        byte[] bArrByteArray = byteArray(i);
        readFully(inputStream, bArrByteArray, 0, bArrByteArray.length);
        return bArrByteArray;
    }

    public static void readFully(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        int i = read(readableByteChannel, byteBuffer);
        if (i == iRemaining) {
            return;
        }
        throw new EOFException("Length to read: " + iRemaining + " actual: " + i);
    }

    public static void readFully(Reader reader, char[] cArr) throws IOException {
        readFully(reader, cArr, 0, cArr.length);
    }

    public static void readFully(Reader reader, char[] cArr, int i, int i2) throws IOException {
        int i3 = read(reader, cArr, i, i2);
        if (i3 == i2) {
            return;
        }
        throw new EOFException("Length to read: " + i2 + " actual: " + i3);
    }

    public static List<String> readLines(CharSequence charSequence) throws UncheckedIOException {
        CharSequenceReader charSequenceReader = new CharSequenceReader(charSequence);
        try {
            List<String> lines = readLines(charSequenceReader);
            charSequenceReader.close();
            return lines;
        } catch (Throwable th) {
            try {
                charSequenceReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Deprecated
    public static List<String> readLines(InputStream inputStream) throws UncheckedIOException {
        return readLines(inputStream, Charset.defaultCharset());
    }

    public static List<String> readLines(InputStream inputStream, Charset charset) throws UncheckedIOException {
        return readLines(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static List<String> readLines(InputStream inputStream, String str) throws UncheckedIOException {
        return readLines(inputStream, Charsets.toCharset(str));
    }

    public static List<String> readLines(Reader reader) throws UncheckedIOException {
        return (List) toBufferedReader(reader).lines().collect(Collectors.toList());
    }

    public static byte[] resourceToByteArray(String str) throws IOException {
        return resourceToByteArray(str, null);
    }

    public static byte[] resourceToByteArray(String str, ClassLoader classLoader) throws IOException {
        return toByteArray(resourceToURL(str, classLoader));
    }

    public static String resourceToString(String str, Charset charset) throws IOException {
        return resourceToString(str, charset, null);
    }

    public static String resourceToString(String str, Charset charset, ClassLoader classLoader) throws IOException {
        return toString(resourceToURL(str, classLoader), charset);
    }

    public static URL resourceToURL(String str) throws IOException {
        return resourceToURL(str, null);
    }

    public static URL resourceToURL(String str, ClassLoader classLoader) throws IOException {
        URL resource = classLoader == null ? IOUtils.class.getResource(str) : classLoader.getResource(str);
        if (resource != null) {
            return resource;
        }
        throw new IOException("Resource not found: " + str);
    }

    public static long skip(InputStream inputStream, long j) throws IOException {
        return skip(inputStream, j, new IOUtils$$ExternalSyntheticLambda5());
    }

    public static long skip(InputStream inputStream, long j, Supplier<byte[]> supplier) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j);
        }
        long j2 = j;
        while (j2 > 0) {
            long j3 = inputStream.read(supplier.get(), 0, (int) Math.min(j2, r4.length));
            if (j3 < 0) {
                break;
            }
            j2 -= j3;
        }
        return j - j2;
    }

    public static long skip(ReadableByteChannel readableByteChannel, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) Math.min(j, PlaybackStateCompat.ACTION_PLAY_FROM_URI));
        long j2 = j;
        while (j2 > 0) {
            byteBufferAllocate.position(0);
            byteBufferAllocate.limit((int) Math.min(j2, PlaybackStateCompat.ACTION_PLAY_FROM_URI));
            int i = readableByteChannel.read(byteBufferAllocate);
            if (i == -1) {
                break;
            }
            j2 -= i;
        }
        return j - j2;
    }

    public static long skip(Reader reader, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j);
        }
        long j2 = j;
        while (j2 > 0) {
            long j3 = reader.read(getScratchCharArrayWriteOnly(), 0, (int) Math.min(j2, r4.length));
            if (j3 < 0) {
                break;
            }
            j2 -= j3;
        }
        return j - j2;
    }

    public static void skipFully(InputStream inputStream, long j) throws IOException {
        long jSkip = skip(inputStream, j, new IOUtils$$ExternalSyntheticLambda5());
        if (jSkip == j) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j + " actual: " + jSkip);
    }

    public static void skipFully(InputStream inputStream, long j, Supplier<byte[]> supplier) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j);
        }
        long jSkip = skip(inputStream, j, supplier);
        if (jSkip == j) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j + " actual: " + jSkip);
    }

    public static void skipFully(ReadableByteChannel readableByteChannel, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j);
        }
        long jSkip = skip(readableByteChannel, j);
        if (jSkip == j) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j + " actual: " + jSkip);
    }

    public static void skipFully(Reader reader, long j) throws IOException {
        long jSkip = skip(reader, j);
        if (jSkip == j) {
            return;
        }
        throw new EOFException("Chars to skip: " + j + " actual: " + jSkip);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return org.apache.commons.io.output.ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int i) throws IOException {
        return org.apache.commons.io.output.ByteArrayOutputStream.toBufferedInputStream(inputStream, i);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader toBufferedReader(Reader reader, int i) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i);
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        final UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream = UnsynchronizedByteArrayOutputStream.builder().get();
        try {
            ThresholdingOutputStream thresholdingOutputStream = new ThresholdingOutputStream(Integer.MAX_VALUE, new IOConsumer() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda7
                @Override // org.apache.commons.io.function.IOConsumer
                public final void accept(Object obj) throws IOException {
                    IOUtils.lambda$toByteArray$0((ThresholdingOutputStream) obj);
                }
            }, new IOFunction() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda8
                @Override // org.apache.commons.io.function.IOFunction
                public final Object apply(Object obj) {
                    return IOUtils.lambda$toByteArray$1(unsynchronizedByteArrayOutputStream, (ThresholdingOutputStream) obj);
                }
            });
            try {
                copy(inputStream, thresholdingOutputStream);
                byte[] byteArray = unsynchronizedByteArrayOutputStream.toByteArray();
                thresholdingOutputStream.close();
                if (unsynchronizedByteArrayOutputStream != null) {
                    unsynchronizedByteArrayOutputStream.close();
                }
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            if (unsynchronizedByteArrayOutputStream != null) {
                try {
                    unsynchronizedByteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static /* synthetic */ void lambda$toByteArray$0(ThresholdingOutputStream thresholdingOutputStream) throws IOException {
        throw new IllegalArgumentException(String.format("Cannot read more than %,d into a byte array", Integer.MAX_VALUE));
    }

    public static byte[] toByteArray(InputStream inputStream, int i) throws IOException {
        if (i == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        InputStream inputStream2 = (InputStream) Objects.requireNonNull(inputStream, "input");
        Objects.requireNonNull(inputStream2);
        return toByteArray(new IOUtils$$ExternalSyntheticLambda3(inputStream2), i);
    }

    public static byte[] toByteArray(InputStream inputStream, long j) throws IOException {
        if (j > 2147483647L) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j);
        }
        return toByteArray(inputStream, (int) j);
    }

    static byte[] toByteArray(IOTriFunction<byte[], Integer, Integer, Integer> iOTriFunction, int i) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + i);
        }
        if (i == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] bArrByteArray = byteArray(i);
        int i2 = 0;
        while (i2 < i) {
            int iIntValue = iOTriFunction.apply(bArrByteArray, Integer.valueOf(i2), Integer.valueOf(i - i2)).intValue();
            if (iIntValue == -1) {
                break;
            }
            i2 += iIntValue;
        }
        if (i2 == i) {
            return bArrByteArray;
        }
        throw new IOException("Unexpected read size, current: " + i2 + ", expected: " + i);
    }

    @Deprecated
    public static byte[] toByteArray(Reader reader) throws IOException {
        return toByteArray(reader, Charset.defaultCharset());
    }

    public static byte[] toByteArray(Reader reader, Charset charset) throws IOException {
        org.apache.commons.io.output.ByteArrayOutputStream byteArrayOutputStream = new org.apache.commons.io.output.ByteArrayOutputStream();
        try {
            copy(reader, byteArrayOutputStream, charset);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] toByteArray(Reader reader, String str) throws IOException {
        return toByteArray(reader, Charsets.toCharset(str));
    }

    @Deprecated
    public static byte[] toByteArray(String str) {
        return str.getBytes(Charset.defaultCharset());
    }

    public static byte[] toByteArray(URI uri) throws IOException {
        return toByteArray(uri.toURL());
    }

    public static byte[] toByteArray(URL url) throws IOException {
        CloseableURLConnection closeableURLConnectionOpen = CloseableURLConnection.open(url);
        try {
            byte[] byteArray = toByteArray(closeableURLConnectionOpen);
            if (closeableURLConnectionOpen != null) {
                closeableURLConnectionOpen.close();
            }
            return byteArray;
        } catch (Throwable th) {
            if (closeableURLConnectionOpen != null) {
                try {
                    closeableURLConnectionOpen.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static byte[] toByteArray(URLConnection uRLConnection) throws IOException {
        InputStream inputStream = uRLConnection.getInputStream();
        try {
            byte[] byteArray = toByteArray(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            return byteArray;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Deprecated
    public static char[] toCharArray(InputStream inputStream) throws IOException {
        return toCharArray(inputStream, Charset.defaultCharset());
    }

    public static char[] toCharArray(InputStream inputStream, Charset charset) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }

    public static char[] toCharArray(InputStream inputStream, String str) throws IOException {
        return toCharArray(inputStream, Charsets.toCharset(str));
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(reader, (Writer) charArrayWriter);
        return charArrayWriter.toCharArray();
    }

    @Deprecated
    public static InputStream toInputStream(CharSequence charSequence) {
        return toInputStream(charSequence, Charset.defaultCharset());
    }

    public static InputStream toInputStream(CharSequence charSequence, Charset charset) {
        return toInputStream(charSequence.toString(), charset);
    }

    public static InputStream toInputStream(CharSequence charSequence, String str) {
        return toInputStream(charSequence, Charsets.toCharset(str));
    }

    @Deprecated
    public static InputStream toInputStream(String str) {
        return toInputStream(str, Charset.defaultCharset());
    }

    public static InputStream toInputStream(String str, Charset charset) {
        return new ByteArrayInputStream(str.getBytes(Charsets.toCharset(charset)));
    }

    public static InputStream toInputStream(String str, String str2) {
        return new ByteArrayInputStream(str.getBytes(Charsets.toCharset(str2)));
    }

    @Deprecated
    public static String toString(byte[] bArr) {
        return new String(bArr, Charset.defaultCharset());
    }

    public static String toString(byte[] bArr, String str) {
        return new String(bArr, Charsets.toCharset(str));
    }

    @Deprecated
    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, Charset.defaultCharset());
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        try {
            copy(inputStream, stringBuilderWriter, charset);
            String string = stringBuilderWriter.toString();
            stringBuilderWriter.close();
            return string;
        } catch (Throwable th) {
            try {
                stringBuilderWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        return toString(inputStream, Charsets.toCharset(str));
    }

    public static String toString(IOSupplier<InputStream> iOSupplier, Charset charset) throws IOException {
        return toString(iOSupplier, charset, new IOSupplier() { // from class: org.apache.commons.io.IOUtils$$ExternalSyntheticLambda6
            @Override // org.apache.commons.io.function.IOSupplier
            public final Object get() {
                return IOUtils.lambda$toString$2();
            }
        });
    }

    static /* synthetic */ String lambda$toString$2() throws IOException {
        throw new NullPointerException("input");
    }

    public static String toString(IOSupplier<InputStream> iOSupplier, Charset charset, IOSupplier<String> iOSupplier2) throws IOException {
        if (iOSupplier == null) {
            return iOSupplier2.get();
        }
        InputStream inputStream = iOSupplier.get();
        try {
            String string = inputStream != null ? toString(inputStream, charset) : iOSupplier2.get();
            if (inputStream != null) {
                inputStream.close();
            }
            return string;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static String toString(Reader reader) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        try {
            copy(reader, (Writer) stringBuilderWriter);
            String string = stringBuilderWriter.toString();
            stringBuilderWriter.close();
            return string;
        } catch (Throwable th) {
            try {
                stringBuilderWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Deprecated
    public static String toString(URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }

    public static String toString(URI uri, Charset charset) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(charset));
    }

    public static String toString(URI uri, String str) throws IOException {
        return toString(uri, Charsets.toCharset(str));
    }

    @Deprecated
    public static String toString(URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }

    public static String toString(URL url, Charset charset) throws IOException {
        Objects.requireNonNull(url);
        return toString(new FileUtils$$ExternalSyntheticLambda21(url), charset);
    }

    public static String toString(URL url, String str) throws IOException {
        return toString(url, Charsets.toCharset(str));
    }

    public static void write(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    @Deprecated
    public static void write(byte[] bArr, Writer writer) throws IOException {
        write(bArr, writer, Charset.defaultCharset());
    }

    public static void write(byte[] bArr, Writer writer, Charset charset) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr, Charsets.toCharset(charset)));
        }
    }

    public static void write(byte[] bArr, Writer writer, String str) throws IOException {
        write(bArr, writer, Charsets.toCharset(str));
    }

    @Deprecated
    public static void write(char[] cArr, OutputStream outputStream) throws IOException {
        write(cArr, outputStream, Charset.defaultCharset());
    }

    public static void write(char[] cArr, OutputStream outputStream, Charset charset) throws IOException {
        if (cArr != null) {
            write(new String(cArr), outputStream, charset);
        }
    }

    public static void write(char[] cArr, OutputStream outputStream, String str) throws IOException {
        write(cArr, outputStream, Charsets.toCharset(str));
    }

    public static void write(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    @Deprecated
    public static void write(CharSequence charSequence, OutputStream outputStream) throws IOException {
        write(charSequence, outputStream, Charset.defaultCharset());
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, Charset charset) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), outputStream, charset);
        }
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, String str) throws IOException {
        write(charSequence, outputStream, Charsets.toCharset(str));
    }

    public static void write(CharSequence charSequence, Writer writer) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), writer);
        }
    }

    @Deprecated
    public static void write(String str, OutputStream outputStream) throws IOException {
        write(str, outputStream, Charset.defaultCharset());
    }

    public static void write(String str, OutputStream outputStream, Charset charset) throws IOException {
        if (str != null) {
            Channels.newChannel(outputStream).write(Charsets.toCharset(charset).encode(str));
        }
    }

    public static void write(String str, OutputStream outputStream, String str2) throws IOException {
        write(str, outputStream, Charsets.toCharset(str2));
    }

    public static void write(String str, Writer writer) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream) throws IOException {
        write(stringBuffer, outputStream, (String) null);
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream, String str) throws IOException {
        if (stringBuffer != null) {
            write(stringBuffer.toString(), outputStream, Charsets.toCharset(str));
        }
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, Writer writer) throws IOException {
        if (stringBuffer != null) {
            writer.write(stringBuffer.toString());
        }
    }

    public static void writeChunked(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            int length = bArr.length;
            int i = 0;
            while (length > 0) {
                int iMin = Math.min(length, 8192);
                outputStream.write(bArr, i, iMin);
                length -= iMin;
                i += iMin;
            }
        }
    }

    public static void writeChunked(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            int length = cArr.length;
            int i = 0;
            while (length > 0) {
                int iMin = Math.min(length, 8192);
                writer.write(cArr, i, iMin);
                length -= iMin;
                i += iMin;
            }
        }
    }

    @Deprecated
    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream) throws IOException {
        writeLines(collection, str, outputStream, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = System.lineSeparator();
        }
        if (StandardCharsets.UTF_16.equals(charset)) {
            charset = StandardCharsets.UTF_16BE;
        }
        byte[] bytes = str.getBytes(charset);
        for (Object obj : collection) {
            if (obj != null) {
                write(obj.toString(), outputStream, charset);
            }
            outputStream.write(bytes);
        }
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, String str2) throws IOException {
        writeLines(collection, str, outputStream, Charsets.toCharset(str2));
    }

    public static void writeLines(Collection<?> collection, String str, Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = System.lineSeparator();
        }
        for (Object obj : collection) {
            if (obj != null) {
                writer.write(obj.toString());
            }
            writer.write(str);
        }
    }

    public static Writer writer(Appendable appendable) {
        Objects.requireNonNull(appendable, "appendable");
        if (appendable instanceof Writer) {
            return (Writer) appendable;
        }
        if (appendable instanceof StringBuilder) {
            return new StringBuilderWriter((StringBuilder) appendable);
        }
        return new AppendableWriter(appendable);
    }

    @Deprecated
    public IOUtils() {
    }
}
