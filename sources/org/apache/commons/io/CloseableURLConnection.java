package org.apache.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
final class CloseableURLConnection extends URLConnection implements AutoCloseable {
    private final URLConnection urlConnection;

    static CloseableURLConnection open(URI uri) throws IOException {
        return open(((URI) Objects.requireNonNull(uri, "uri")).toURL());
    }

    static CloseableURLConnection open(URL url) throws IOException {
        return new CloseableURLConnection(url.openConnection());
    }

    CloseableURLConnection(URLConnection uRLConnection) {
        super(((URLConnection) Objects.requireNonNull(uRLConnection, "urlConnection")).getURL());
        this.urlConnection = uRLConnection;
    }

    @Override // java.net.URLConnection
    public void addRequestProperty(String str, String str2) {
        this.urlConnection.addRequestProperty(str, str2);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        IOUtils.close(this.urlConnection);
    }

    @Override // java.net.URLConnection
    public void connect() throws IOException {
        this.urlConnection.connect();
    }

    public boolean equals(Object obj) {
        return this.urlConnection.equals(obj);
    }

    @Override // java.net.URLConnection
    public boolean getAllowUserInteraction() {
        return this.urlConnection.getAllowUserInteraction();
    }

    @Override // java.net.URLConnection
    public int getConnectTimeout() {
        return this.urlConnection.getConnectTimeout();
    }

    @Override // java.net.URLConnection
    public Object getContent() throws IOException {
        return this.urlConnection.getContent();
    }

    @Override // java.net.URLConnection
    public Object getContent(Class[] clsArr) throws IOException {
        return this.urlConnection.getContent(clsArr);
    }

    @Override // java.net.URLConnection
    public String getContentEncoding() {
        return this.urlConnection.getContentEncoding();
    }

    @Override // java.net.URLConnection
    public int getContentLength() {
        return this.urlConnection.getContentLength();
    }

    @Override // java.net.URLConnection
    public long getContentLengthLong() {
        return this.urlConnection.getContentLengthLong();
    }

    @Override // java.net.URLConnection
    public String getContentType() {
        return this.urlConnection.getContentType();
    }

    @Override // java.net.URLConnection
    public long getDate() {
        return this.urlConnection.getDate();
    }

    @Override // java.net.URLConnection
    public boolean getDefaultUseCaches() {
        return this.urlConnection.getDefaultUseCaches();
    }

    @Override // java.net.URLConnection
    public boolean getDoInput() {
        return this.urlConnection.getDoInput();
    }

    @Override // java.net.URLConnection
    public boolean getDoOutput() {
        return this.urlConnection.getDoOutput();
    }

    @Override // java.net.URLConnection
    public long getExpiration() {
        return this.urlConnection.getExpiration();
    }

    @Override // java.net.URLConnection
    public String getHeaderField(int i) {
        return this.urlConnection.getHeaderField(i);
    }

    @Override // java.net.URLConnection
    public String getHeaderField(String str) {
        return this.urlConnection.getHeaderField(str);
    }

    @Override // java.net.URLConnection
    public long getHeaderFieldDate(String str, long j) {
        return this.urlConnection.getHeaderFieldDate(str, j);
    }

    @Override // java.net.URLConnection
    public int getHeaderFieldInt(String str, int i) {
        return this.urlConnection.getHeaderFieldInt(str, i);
    }

    @Override // java.net.URLConnection
    public String getHeaderFieldKey(int i) {
        return this.urlConnection.getHeaderFieldKey(i);
    }

    @Override // java.net.URLConnection
    public long getHeaderFieldLong(String str, long j) {
        return this.urlConnection.getHeaderFieldLong(str, j);
    }

    @Override // java.net.URLConnection
    public Map<String, List<String>> getHeaderFields() {
        return this.urlConnection.getHeaderFields();
    }

    @Override // java.net.URLConnection
    public long getIfModifiedSince() {
        return this.urlConnection.getIfModifiedSince();
    }

    @Override // java.net.URLConnection
    public InputStream getInputStream() throws IOException {
        return this.urlConnection.getInputStream();
    }

    @Override // java.net.URLConnection
    public long getLastModified() {
        return this.urlConnection.getLastModified();
    }

    @Override // java.net.URLConnection
    public OutputStream getOutputStream() throws IOException {
        return this.urlConnection.getOutputStream();
    }

    @Override // java.net.URLConnection
    public Permission getPermission() throws IOException {
        return this.urlConnection.getPermission();
    }

    @Override // java.net.URLConnection
    public int getReadTimeout() {
        return this.urlConnection.getReadTimeout();
    }

    @Override // java.net.URLConnection
    public Map<String, List<String>> getRequestProperties() {
        return this.urlConnection.getRequestProperties();
    }

    @Override // java.net.URLConnection
    public String getRequestProperty(String str) {
        return this.urlConnection.getRequestProperty(str);
    }

    @Override // java.net.URLConnection
    public URL getURL() {
        return this.urlConnection.getURL();
    }

    @Override // java.net.URLConnection
    public boolean getUseCaches() {
        return this.urlConnection.getUseCaches();
    }

    public int hashCode() {
        return this.urlConnection.hashCode();
    }

    @Override // java.net.URLConnection
    public void setAllowUserInteraction(boolean z) {
        this.urlConnection.setAllowUserInteraction(z);
    }

    @Override // java.net.URLConnection
    public void setConnectTimeout(int i) {
        this.urlConnection.setConnectTimeout(i);
    }

    @Override // java.net.URLConnection
    public void setDefaultUseCaches(boolean z) {
        this.urlConnection.setDefaultUseCaches(z);
    }

    @Override // java.net.URLConnection
    public void setDoInput(boolean z) {
        this.urlConnection.setDoInput(z);
    }

    @Override // java.net.URLConnection
    public void setDoOutput(boolean z) {
        this.urlConnection.setDoOutput(z);
    }

    @Override // java.net.URLConnection
    public void setIfModifiedSince(long j) {
        this.urlConnection.setIfModifiedSince(j);
    }

    @Override // java.net.URLConnection
    public void setReadTimeout(int i) {
        this.urlConnection.setReadTimeout(i);
    }

    @Override // java.net.URLConnection
    public void setRequestProperty(String str, String str2) {
        this.urlConnection.setRequestProperty(str, str2);
    }

    @Override // java.net.URLConnection
    public void setUseCaches(boolean z) {
        this.urlConnection.setUseCaches(z);
    }

    @Override // java.net.URLConnection
    public String toString() {
        return this.urlConnection.toString();
    }
}
