package com.mr.flutter.plugin.filepicker;

import android.net.Uri;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FileInfo.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0004\b\u000b\u0010\fJ&\u0010\u0016\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0017j\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0001`\u0018R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001a"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FileInfo;", "", "path", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "uri", "Landroid/net/Uri;", "size", "", "bytes", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;J[B)V", "getPath", "()Ljava/lang/String;", "getName", "getUri", "()Landroid/net/Uri;", "getSize", "()J", "getBytes", "()[B", "toMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "Builder", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FileInfo {
    private final byte[] bytes;
    private final String name;
    private final String path;
    private final long size;
    private final Uri uri;

    public FileInfo(String str, String str2, Uri uri, long j, byte[] bArr) {
        this.path = str;
        this.name = str2;
        this.uri = uri;
        this.size = j;
        this.bytes = bArr;
    }

    public final String getPath() {
        return this.path;
    }

    public final String getName() {
        return this.name;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public final long getSize() {
        return this.size;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    /* compiled from: FileInfo.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\u00002\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J\u0010\u0010\u000e\u001a\u00020\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0011\u001a\u00020\u00002\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u0006\u0010\u0012\u001a\u00020\u0013R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/mr/flutter/plugin/filepicker/FileInfo$Builder;", "", "<init>", "()V", "path", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "uri", "Landroid/net/Uri;", "size", "", "bytes", "", "withPath", "withName", "withSize", "withData", "withUri", "build", "Lcom/mr/flutter/plugin/filepicker/FileInfo;", "file_picker_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Builder {
        private byte[] bytes;
        private String name;
        private String path;
        private long size;
        private Uri uri;

        public final Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public final Builder withName(String name) {
            this.name = name;
            return this;
        }

        public final Builder withSize(long size) {
            this.size = size;
            return this;
        }

        public final Builder withData(byte[] bytes) {
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            this.bytes = bytes;
            return this;
        }

        public final Builder withUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public final FileInfo build() {
            return new FileInfo(this.path, this.name, this.uri, this.size, this.bytes);
        }
    }

    public final HashMap<String, Object> toMap() {
        return MapsKt.hashMapOf(new Pair("path", this.path), new Pair(AppMeasurementSdk.ConditionalUserProperty.NAME, this.name), new Pair("size", Long.valueOf(this.size)), new Pair("bytes", this.bytes), new Pair("identifier", String.valueOf(this.uri)));
    }
}
