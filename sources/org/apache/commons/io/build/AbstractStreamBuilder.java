package org.apache.commons.io.build;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.function.IntUnaryOperator;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.file.PathUtils;

/* loaded from: classes4.dex */
public abstract class AbstractStreamBuilder<T, B extends AbstractStreamBuilder<T, B>> extends AbstractOriginSupplier<T, B> {
    private static final int DEFAULT_MAX_VALUE = Integer.MAX_VALUE;
    private static final OpenOption[] DEFAULT_OPEN_OPTIONS = PathUtils.EMPTY_OPEN_OPTION_ARRAY;
    private IntUnaryOperator bufferSizeChecker;
    private final IntUnaryOperator defaultSizeChecker;
    private int bufferSize = 8192;
    private int bufferSizeDefault = 8192;
    private int bufferSizeMax = Integer.MAX_VALUE;
    private Charset charset = Charset.defaultCharset();
    private Charset charsetDefault = Charset.defaultCharset();
    private OpenOption[] openOptions = DEFAULT_OPEN_OPTIONS;

    /* renamed from: lambda$new$0$org-apache-commons-io-build-AbstractStreamBuilder, reason: not valid java name */
    /* synthetic */ int m2178lambda$new$0$orgapachecommonsiobuildAbstractStreamBuilder(int i) {
        int i2 = this.bufferSizeMax;
        return i > i2 ? throwIae(i, i2) : i;
    }

    public AbstractStreamBuilder() {
        IntUnaryOperator intUnaryOperator = new IntUnaryOperator() { // from class: org.apache.commons.io.build.AbstractStreamBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return this.f$0.m2178lambda$new$0$orgapachecommonsiobuildAbstractStreamBuilder(i);
            }
        };
        this.defaultSizeChecker = intUnaryOperator;
        this.bufferSizeChecker = intUnaryOperator;
    }

    private int checkBufferSize(int i) {
        return this.bufferSizeChecker.applyAsInt(i);
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public int getBufferSizeDefault() {
        return this.bufferSizeDefault;
    }

    public CharSequence getCharSequence() throws IOException {
        return checkOrigin().getCharSequence(getCharset());
    }

    public Charset getCharset() {
        return this.charset;
    }

    public Charset getCharsetDefault() {
        return this.charsetDefault;
    }

    public File getFile() {
        return checkOrigin().getFile();
    }

    public InputStream getInputStream() throws IOException {
        return checkOrigin().getInputStream(getOpenOptions());
    }

    public OpenOption[] getOpenOptions() {
        return this.openOptions;
    }

    public OutputStream getOutputStream() throws IOException {
        return checkOrigin().getOutputStream(getOpenOptions());
    }

    public Path getPath() {
        return checkOrigin().getPath();
    }

    public RandomAccessFile getRandomAccessFile() throws IOException {
        return checkOrigin().getRandomAccessFile(getOpenOptions());
    }

    public Reader getReader() throws IOException {
        return checkOrigin().getReader(getCharset());
    }

    public Writer getWriter() throws IOException {
        return checkOrigin().getWriter(getCharset(), getOpenOptions());
    }

    public B setBufferSize(int i) {
        if (i <= 0) {
            i = this.bufferSizeDefault;
        }
        this.bufferSize = checkBufferSize(i);
        return asThis();
    }

    public B setBufferSize(Integer num) {
        setBufferSize(num != null ? num.intValue() : this.bufferSizeDefault);
        return asThis();
    }

    public B setBufferSizeChecker(IntUnaryOperator intUnaryOperator) {
        if (intUnaryOperator == null) {
            intUnaryOperator = this.defaultSizeChecker;
        }
        this.bufferSizeChecker = intUnaryOperator;
        return asThis();
    }

    protected B setBufferSizeDefault(int i) {
        this.bufferSizeDefault = i;
        return asThis();
    }

    public B setBufferSizeMax(int i) {
        if (i <= 0) {
            i = Integer.MAX_VALUE;
        }
        this.bufferSizeMax = i;
        return asThis();
    }

    public B setCharset(Charset charset) {
        this.charset = Charsets.toCharset(charset, this.charsetDefault);
        return asThis();
    }

    public B setCharset(String str) {
        return (B) setCharset(Charsets.toCharset(str, this.charsetDefault));
    }

    protected B setCharsetDefault(Charset charset) {
        this.charsetDefault = charset;
        return asThis();
    }

    public B setOpenOptions(OpenOption... openOptionArr) {
        if (openOptionArr == null) {
            openOptionArr = DEFAULT_OPEN_OPTIONS;
        }
        this.openOptions = openOptionArr;
        return asThis();
    }

    private int throwIae(int i, int i2) {
        throw new IllegalArgumentException(String.format("Request %,d exceeds maximum %,d", Integer.valueOf(i), Integer.valueOf(i2)));
    }
}
