package org.apache.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

@Deprecated
/* loaded from: classes4.dex */
public class CopyUtils {
    public static void copy(byte[] bArr, OutputStream outputStream) throws IOException {
        outputStream.write(bArr);
    }

    @Deprecated
    public static void copy(byte[] bArr, Writer writer) throws IOException {
        copy(new ByteArrayInputStream(bArr), writer);
    }

    public static void copy(byte[] bArr, Writer writer, String str) throws IOException {
        copy(new ByteArrayInputStream(bArr), writer, str);
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArrByteArray = IOUtils.byteArray();
        int i = 0;
        while (true) {
            int i2 = inputStream.read(bArrByteArray);
            if (-1 == i2) {
                return i;
            }
            outputStream.write(bArrByteArray, 0, i2);
            i += i2;
        }
    }

    @Deprecated
    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy(new InputStreamReader(inputStream, Charset.defaultCharset()), writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        copy(new InputStreamReader(inputStream, str), writer);
    }

    @Deprecated
    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.defaultCharset());
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, str);
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        char[] scratchCharArray = IOUtils.getScratchCharArray();
        int i = 0;
        while (true) {
            int i2 = reader.read(scratchCharArray);
            if (-1 == i2) {
                return i;
            }
            writer.write(scratchCharArray, 0, i2);
            i += i2;
        }
    }

    @Deprecated
    public static void copy(String str, OutputStream outputStream) throws IOException {
        StringReader stringReader = new StringReader(str);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.defaultCharset());
        copy(stringReader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(String str, OutputStream outputStream, String str2) throws IOException {
        StringReader stringReader = new StringReader(str);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, str2);
        copy(stringReader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(String str, Writer writer) throws IOException {
        writer.write(str);
    }

    @Deprecated
    public CopyUtils() {
    }
}
