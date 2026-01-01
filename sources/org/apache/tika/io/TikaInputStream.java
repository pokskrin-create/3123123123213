package org.apache.tika.io;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import org.apache.commons.io.input.TaggedInputStream;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
public class TikaInputStream extends TaggedInputStream {
    private static final int BLOB_SIZE_THRESHOLD = 1048576;
    private static final int MAX_CONSECUTIVE_EOFS = 1000;
    private int consecutiveEOFs;
    private long length;
    private long mark;
    private Object openContainer;
    private Path path;
    private long position;
    private byte[] skipBuffer;
    private InputStreamFactory streamFactory;
    private String suffix;
    private final TemporaryResources tmp;

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    private TikaInputStream(Path path) throws IOException {
        super(new BufferedInputStream(Files.newInputStream(path, new OpenOption[0])));
        this.position = 0L;
        this.mark = -1L;
        this.consecutiveEOFs = 0;
        this.suffix = null;
        this.path = path;
        this.tmp = new TemporaryResources();
        this.length = Files.size(path);
        this.suffix = FilenameUtils.getSuffixFromPath(path.getFileName().toString());
    }

    private TikaInputStream(Path path, TemporaryResources temporaryResources, long j) throws IOException {
        super(new BufferedInputStream(Files.newInputStream(path, new OpenOption[0])));
        this.position = 0L;
        this.mark = -1L;
        this.consecutiveEOFs = 0;
        this.suffix = null;
        this.path = path;
        this.tmp = temporaryResources;
        this.length = j;
        this.suffix = FilenameUtils.getSuffixFromPath(path.getFileName().toString());
    }

    @Deprecated
    private TikaInputStream(File file) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream(file)));
        this.position = 0L;
        this.mark = -1L;
        this.consecutiveEOFs = 0;
        this.suffix = null;
        this.path = file.toPath();
        this.tmp = new TemporaryResources();
        this.length = file.length();
        this.suffix = FilenameUtils.getSuffixFromPath(this.path.getFileName().toString());
    }

    private TikaInputStream(InputStream inputStream, TemporaryResources temporaryResources, long j, String str) {
        super(inputStream);
        this.position = 0L;
        this.mark = -1L;
        this.consecutiveEOFs = 0;
        this.path = null;
        this.tmp = temporaryResources;
        this.length = j;
        this.suffix = str;
    }

    public static boolean isTikaInputStream(InputStream inputStream) {
        return inputStream instanceof TikaInputStream;
    }

    public static TikaInputStream get(InputStream inputStream, TemporaryResources temporaryResources, Metadata metadata) {
        if (inputStream == null) {
            throw new NullPointerException("The Stream must not be null");
        }
        if (inputStream instanceof TikaInputStream) {
            return (TikaInputStream) inputStream;
        }
        return new TikaInputStream(!inputStream.markSupported() ? new BufferedInputStream(inputStream) : inputStream, temporaryResources, -1L, getExtension(metadata));
    }

    public static TikaInputStream get(InputStream inputStream) {
        return get(inputStream, new TemporaryResources(), (Metadata) null);
    }

    public static TikaInputStream cast(InputStream inputStream) {
        if (inputStream instanceof TikaInputStream) {
            return (TikaInputStream) inputStream;
        }
        return null;
    }

    public static TikaInputStream get(byte[] bArr) {
        return get(bArr, new Metadata());
    }

    public static TikaInputStream get(byte[] bArr, Metadata metadata) {
        metadata.set("Content-Length", Integer.toString(bArr.length));
        return new TikaInputStream(new UnsynchronizedByteArrayInputStream(bArr), new TemporaryResources(), bArr.length, getExtension(metadata));
    }

    public static TikaInputStream get(Path path) throws IOException {
        return get(path, new Metadata());
    }

    public static TikaInputStream get(Path path, Metadata metadata) throws IOException {
        if (StringUtils.isBlank(metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY))) {
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, path.getFileName().toString());
        }
        metadata.set("Content-Length", Long.toString(Files.size(path)));
        return new TikaInputStream(path);
    }

    public static TikaInputStream get(Path path, Metadata metadata, TemporaryResources temporaryResources) throws IOException {
        long size = Files.size(path);
        if (StringUtils.isBlank(metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY))) {
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, path.getFileName().toString());
        }
        metadata.set("Content-Length", Long.toString(size));
        return new TikaInputStream(path, temporaryResources, size);
    }

    @Deprecated
    public static TikaInputStream get(File file) throws FileNotFoundException {
        return get(file, new Metadata());
    }

    @Deprecated
    public static TikaInputStream get(File file, Metadata metadata) throws FileNotFoundException {
        if (StringUtils.isBlank(metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY))) {
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, file.getName());
        }
        metadata.set("Content-Length", Long.toString(file.length()));
        return new TikaInputStream(file);
    }

    public static TikaInputStream get(InputStreamFactory inputStreamFactory) throws IOException {
        return get(inputStreamFactory, new TemporaryResources());
    }

    public static TikaInputStream get(InputStreamFactory inputStreamFactory, TemporaryResources temporaryResources) throws IOException {
        TikaInputStream tikaInputStream = get(inputStreamFactory.getInputStream(), temporaryResources, (Metadata) null);
        tikaInputStream.streamFactory = inputStreamFactory;
        return tikaInputStream;
    }

    public static TikaInputStream get(Blob blob) throws SQLException {
        return get(blob, new Metadata());
    }

    public static TikaInputStream get(Blob blob, Metadata metadata) throws SQLException {
        long length;
        try {
            length = blob.length();
            try {
                metadata.set("Content-Length", Long.toString(length));
            } catch (SQLException unused) {
            }
        } catch (SQLException unused2) {
            length = -1;
        }
        long j = length;
        if (0 <= j && j <= 1048576) {
            return get(blob.getBytes(1L, (int) j), metadata);
        }
        return new TikaInputStream(new BufferedInputStream(blob.getBinaryStream()), new TemporaryResources(), j, getExtension(metadata));
    }

    private static String getExtension(Metadata metadata) {
        if (metadata == null) {
            return "";
        }
        return FilenameUtils.getSuffixFromPath(metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY));
    }

    public static TikaInputStream get(URI uri) throws IOException {
        return get(uri, new Metadata());
    }

    public static TikaInputStream get(URI uri, Metadata metadata) throws IOException {
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            Path path = Paths.get(uri);
            if (Files.isRegularFile(path, new LinkOption[0])) {
                return get(path, metadata);
            }
        }
        return get(uri.toURL(), metadata);
    }

    public static TikaInputStream get(URL url) throws IOException {
        return get(url, new Metadata());
    }

    public static TikaInputStream get(URL url, Metadata metadata) throws IOException {
        if ("file".equalsIgnoreCase(url.getProtocol())) {
            try {
                Path path = Paths.get(url.toURI());
                if (Files.isRegularFile(path, new LinkOption[0])) {
                    return get(path, metadata);
                }
            } catch (URISyntaxException unused) {
            }
        }
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        String path2 = url.getPath();
        int iLastIndexOf = path2.lastIndexOf(47) + 1;
        if (iLastIndexOf < path2.length()) {
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, path2.substring(iLastIndexOf));
        }
        String contentType = uRLConnectionOpenConnection.getContentType();
        if (contentType != null) {
            metadata.set("Content-Type", contentType);
        }
        String contentEncoding = uRLConnectionOpenConnection.getContentEncoding();
        if (contentEncoding != null) {
            metadata.set("Content-Encoding", contentEncoding);
        }
        int contentLength = uRLConnectionOpenConnection.getContentLength();
        if (contentLength >= 0) {
            metadata.set("Content-Length", Integer.toString(contentLength));
        }
        return new TikaInputStream(new BufferedInputStream(uRLConnectionOpenConnection.getInputStream()), new TemporaryResources(), contentLength, getExtension(metadata));
    }

    public int peek(byte[] bArr) throws IOException {
        mark(bArr.length);
        int i = read(bArr);
        int i2 = 0;
        while (i != -1) {
            i2 += i;
            i = i2 < bArr.length ? read(bArr, i2, bArr.length - i2) : -1;
        }
        reset();
        return i2;
    }

    public Object getOpenContainer() {
        return this.openContainer;
    }

    public void setOpenContainer(Object obj) {
        this.openContainer = obj;
        if (obj instanceof Closeable) {
            this.tmp.addResource((Closeable) obj);
        }
    }

    public void addCloseableResource(Closeable closeable) {
        this.tmp.addResource(closeable);
    }

    public boolean hasInputStreamFactory() {
        return this.streamFactory != null;
    }

    public InputStreamFactory getInputStreamFactory() {
        return this.streamFactory;
    }

    public boolean hasFile() {
        return this.path != null;
    }

    public Path getPath() throws IOException {
        return getPath(-1);
    }

    public Path getPath(int i) throws IOException {
        Path path = this.path;
        if (path != null) {
            return path;
        }
        if (this.position > 0) {
            throw new IOException("Stream is already being read");
        }
        Path pathCreateTempFile = this.tmp.createTempFile(this.suffix);
        if (i > -1) {
            BoundedInputStream boundedInputStream = new BoundedInputStream(i, this);
            try {
                boundedInputStream.mark(i);
                try {
                    Files.copy(boundedInputStream, pathCreateTempFile, StandardCopyOption.REPLACE_EXISTING);
                    if (boundedInputStream.hasHitBound()) {
                        boundedInputStream.close();
                        return null;
                    }
                    boundedInputStream.close();
                } finally {
                    boundedInputStream.reset();
                }
            } catch (Throwable th) {
                try {
                    boundedInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } else {
            Files.copy(this, pathCreateTempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        this.path = pathCreateTempFile;
        InputStream inputStreamNewInputStream = Files.newInputStream(pathCreateTempFile, new OpenOption[0]);
        this.tmp.addResource(inputStreamNewInputStream);
        final InputStream inputStream = this.in;
        this.in = new BufferedInputStream(inputStreamNewInputStream) { // from class: org.apache.tika.io.TikaInputStream.1
            @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                inputStream.close();
            }
        };
        this.length = Files.size(this.path);
        this.position = 0L;
        this.mark = -1L;
        return this.path;
    }

    public File getFile() throws IOException {
        return getPath().toFile();
    }

    public FileChannel getFileChannel() throws IOException {
        FileChannel fileChannelOpen = FileChannel.open(getPath(), new OpenOption[0]);
        this.tmp.addResource(fileChannelOpen);
        return fileChannelOpen;
    }

    public boolean hasLength() {
        return this.length != -1;
    }

    public long getLength() throws IOException {
        if (this.length == -1) {
            getPath();
        }
        return this.length;
    }

    public long getPosition() {
        return this.position;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        if (this.skipBuffer == null) {
            this.skipBuffer = new byte[4096];
        }
        long jSkip = IOUtils.skip(((TaggedInputStream) this).in, j, this.skipBuffer);
        this.position += jSkip;
        return jSkip;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        super.mark(i);
        this.mark = this.position;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        super.reset();
        this.position = this.mark;
        this.mark = -1L;
        this.consecutiveEOFs = 0;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.path = null;
        this.mark = -1L;
        this.tmp.addResource(this.in);
        this.tmp.close();
    }

    @Override // org.apache.commons.io.input.ProxyInputStream
    protected void afterRead(int i) throws IOException {
        if (i != -1) {
            this.position += i;
            return;
        }
        int i2 = this.consecutiveEOFs + 1;
        this.consecutiveEOFs = i2;
        if (i2 > 1000) {
            throw new IOException("Read too many -1 (EOFs); there could be an infinite loop.If you think your file is not corrupt, please open an issue on Tika's JIRA");
        }
    }

    public String toString() {
        String str;
        if (hasFile()) {
            str = "TikaInputStream of " + this.path.toString();
        } else {
            str = "TikaInputStream of " + this.in.toString();
        }
        Object obj = this.openContainer;
        if (obj == null) {
            return str;
        }
        return str + " (in " + obj + ")";
    }
}
