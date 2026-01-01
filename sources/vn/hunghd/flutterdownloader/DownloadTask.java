package vn.hunghd.flutterdownloader;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DownloadTask.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\bA\b\u0086\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u000f¢\u0006\u0004\b\u0016\u0010\u0017J\t\u0010@\u001a\u00020\u0003HÆ\u0003J\t\u0010A\u001a\u00020\u0005HÆ\u0003J\t\u0010B\u001a\u00020\u0007HÆ\u0003J\t\u0010C\u001a\u00020\u0003HÆ\u0003J\t\u0010D\u001a\u00020\u0005HÆ\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010F\u001a\u00020\u0005HÆ\u0003J\t\u0010G\u001a\u00020\u0005HÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010I\u001a\u00020\u000fHÆ\u0003J\t\u0010J\u001a\u00020\u000fHÆ\u0003J\t\u0010K\u001a\u00020\u000fHÆ\u0003J\t\u0010L\u001a\u00020\u0013HÆ\u0003J\t\u0010M\u001a\u00020\u000fHÆ\u0003J\t\u0010N\u001a\u00020\u000fHÆ\u0003J£\u0001\u0010O\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fHÆ\u0001J\u0013\u0010P\u001a\u00020\u000f2\b\u0010Q\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010R\u001a\u00020\u0003HÖ\u0001J\t\u0010S\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0019\"\u0004\b%\u0010\u001bR\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001d\"\u0004\b'\u0010\u001fR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u001d\"\u0004\b)\u0010\u001fR\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001d\"\u0004\b+\u0010\u001fR\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001d\"\u0004\b-\u0010\u001fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001d\"\u0004\b/\u0010\u001fR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u0010\u0010\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00101\"\u0004\b5\u00103R\u001a\u0010\u0011\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00101\"\u0004\b7\u00103R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u00101\"\u0004\b=\u00103R\u001a\u0010\u0015\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u00101\"\u0004\b?\u00103¨\u0006T"}, d2 = {"Lvn/hunghd/flutterdownloader/DownloadTask;", "", "primaryId", "", "taskId", "", "status", "Lvn/hunghd/flutterdownloader/DownloadStatus;", "progress", "url", "filename", "savedDir", "headers", "mimeType", TaskEntry.COLUMN_NAME_RESUMABLE, "", "showNotification", "openFileFromNotification", "timeCreated", "", "saveInPublicStorage", "allowCellular", "<init>", "(ILjava/lang/String;Lvn/hunghd/flutterdownloader/DownloadStatus;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZJZZ)V", "getPrimaryId", "()I", "setPrimaryId", "(I)V", "getTaskId", "()Ljava/lang/String;", "setTaskId", "(Ljava/lang/String;)V", "getStatus", "()Lvn/hunghd/flutterdownloader/DownloadStatus;", "setStatus", "(Lvn/hunghd/flutterdownloader/DownloadStatus;)V", "getProgress", "setProgress", "getUrl", "setUrl", "getFilename", "setFilename", "getSavedDir", "setSavedDir", "getHeaders", "setHeaders", "getMimeType", "setMimeType", "getResumable", "()Z", "setResumable", "(Z)V", "getShowNotification", "setShowNotification", "getOpenFileFromNotification", "setOpenFileFromNotification", "getTimeCreated", "()J", "setTimeCreated", "(J)V", "getSaveInPublicStorage", "setSaveInPublicStorage", "getAllowCellular", "setAllowCellular", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "copy", "equals", "other", "hashCode", "toString", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class DownloadTask {
    private boolean allowCellular;
    private String filename;
    private String headers;
    private String mimeType;
    private boolean openFileFromNotification;
    private int primaryId;
    private int progress;
    private boolean resumable;
    private boolean saveInPublicStorage;
    private String savedDir;
    private boolean showNotification;
    private DownloadStatus status;
    private String taskId;
    private long timeCreated;
    private String url;

    /* renamed from: component1, reason: from getter */
    public final int getPrimaryId() {
        return this.primaryId;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getResumable() {
        return this.resumable;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getShowNotification() {
        return this.showNotification;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getOpenFileFromNotification() {
        return this.openFileFromNotification;
    }

    /* renamed from: component13, reason: from getter */
    public final long getTimeCreated() {
        return this.timeCreated;
    }

    /* renamed from: component14, reason: from getter */
    public final boolean getSaveInPublicStorage() {
        return this.saveInPublicStorage;
    }

    /* renamed from: component15, reason: from getter */
    public final boolean getAllowCellular() {
        return this.allowCellular;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTaskId() {
        return this.taskId;
    }

    /* renamed from: component3, reason: from getter */
    public final DownloadStatus getStatus() {
        return this.status;
    }

    /* renamed from: component4, reason: from getter */
    public final int getProgress() {
        return this.progress;
    }

    /* renamed from: component5, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: component6, reason: from getter */
    public final String getFilename() {
        return this.filename;
    }

    /* renamed from: component7, reason: from getter */
    public final String getSavedDir() {
        return this.savedDir;
    }

    /* renamed from: component8, reason: from getter */
    public final String getHeaders() {
        return this.headers;
    }

    /* renamed from: component9, reason: from getter */
    public final String getMimeType() {
        return this.mimeType;
    }

    public final DownloadTask copy(int primaryId, String taskId, DownloadStatus status, int progress, String url, String filename, String savedDir, String headers, String mimeType, boolean resumable, boolean showNotification, boolean openFileFromNotification, long timeCreated, boolean saveInPublicStorage, boolean allowCellular) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(savedDir, "savedDir");
        Intrinsics.checkNotNullParameter(headers, "headers");
        return new DownloadTask(primaryId, taskId, status, progress, url, filename, savedDir, headers, mimeType, resumable, showNotification, openFileFromNotification, timeCreated, saveInPublicStorage, allowCellular);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DownloadTask)) {
            return false;
        }
        DownloadTask downloadTask = (DownloadTask) other;
        return this.primaryId == downloadTask.primaryId && Intrinsics.areEqual(this.taskId, downloadTask.taskId) && this.status == downloadTask.status && this.progress == downloadTask.progress && Intrinsics.areEqual(this.url, downloadTask.url) && Intrinsics.areEqual(this.filename, downloadTask.filename) && Intrinsics.areEqual(this.savedDir, downloadTask.savedDir) && Intrinsics.areEqual(this.headers, downloadTask.headers) && Intrinsics.areEqual(this.mimeType, downloadTask.mimeType) && this.resumable == downloadTask.resumable && this.showNotification == downloadTask.showNotification && this.openFileFromNotification == downloadTask.openFileFromNotification && this.timeCreated == downloadTask.timeCreated && this.saveInPublicStorage == downloadTask.saveInPublicStorage && this.allowCellular == downloadTask.allowCellular;
    }

    public int hashCode() {
        int iHashCode = ((((((((Integer.hashCode(this.primaryId) * 31) + this.taskId.hashCode()) * 31) + this.status.hashCode()) * 31) + Integer.hashCode(this.progress)) * 31) + this.url.hashCode()) * 31;
        String str = this.filename;
        int iHashCode2 = (((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.savedDir.hashCode()) * 31) + this.headers.hashCode()) * 31;
        String str2 = this.mimeType;
        return ((((((((((((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Boolean.hashCode(this.resumable)) * 31) + Boolean.hashCode(this.showNotification)) * 31) + Boolean.hashCode(this.openFileFromNotification)) * 31) + Long.hashCode(this.timeCreated)) * 31) + Boolean.hashCode(this.saveInPublicStorage)) * 31) + Boolean.hashCode(this.allowCellular);
    }

    public String toString() {
        return "DownloadTask(primaryId=" + this.primaryId + ", taskId=" + this.taskId + ", status=" + this.status + ", progress=" + this.progress + ", url=" + this.url + ", filename=" + this.filename + ", savedDir=" + this.savedDir + ", headers=" + this.headers + ", mimeType=" + this.mimeType + ", resumable=" + this.resumable + ", showNotification=" + this.showNotification + ", openFileFromNotification=" + this.openFileFromNotification + ", timeCreated=" + this.timeCreated + ", saveInPublicStorage=" + this.saveInPublicStorage + ", allowCellular=" + this.allowCellular + ")";
    }

    public DownloadTask(int i, String taskId, DownloadStatus status, int i2, String url, String str, String savedDir, String headers, String str2, boolean z, boolean z2, boolean z3, long j, boolean z4, boolean z5) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(savedDir, "savedDir");
        Intrinsics.checkNotNullParameter(headers, "headers");
        this.primaryId = i;
        this.taskId = taskId;
        this.status = status;
        this.progress = i2;
        this.url = url;
        this.filename = str;
        this.savedDir = savedDir;
        this.headers = headers;
        this.mimeType = str2;
        this.resumable = z;
        this.showNotification = z2;
        this.openFileFromNotification = z3;
        this.timeCreated = j;
        this.saveInPublicStorage = z4;
        this.allowCellular = z5;
    }

    public final int getPrimaryId() {
        return this.primaryId;
    }

    public final void setPrimaryId(int i) {
        this.primaryId = i;
    }

    public final String getTaskId() {
        return this.taskId;
    }

    public final void setTaskId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.taskId = str;
    }

    public final DownloadStatus getStatus() {
        return this.status;
    }

    public final void setStatus(DownloadStatus downloadStatus) {
        Intrinsics.checkNotNullParameter(downloadStatus, "<set-?>");
        this.status = downloadStatus;
    }

    public final int getProgress() {
        return this.progress;
    }

    public final void setProgress(int i) {
        this.progress = i;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public final String getFilename() {
        return this.filename;
    }

    public final void setFilename(String str) {
        this.filename = str;
    }

    public final String getSavedDir() {
        return this.savedDir;
    }

    public final void setSavedDir(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.savedDir = str;
    }

    public final String getHeaders() {
        return this.headers;
    }

    public final void setHeaders(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.headers = str;
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final void setMimeType(String str) {
        this.mimeType = str;
    }

    public final boolean getResumable() {
        return this.resumable;
    }

    public final void setResumable(boolean z) {
        this.resumable = z;
    }

    public final boolean getShowNotification() {
        return this.showNotification;
    }

    public final void setShowNotification(boolean z) {
        this.showNotification = z;
    }

    public final boolean getOpenFileFromNotification() {
        return this.openFileFromNotification;
    }

    public final void setOpenFileFromNotification(boolean z) {
        this.openFileFromNotification = z;
    }

    public final long getTimeCreated() {
        return this.timeCreated;
    }

    public final void setTimeCreated(long j) {
        this.timeCreated = j;
    }

    public final boolean getSaveInPublicStorage() {
        return this.saveInPublicStorage;
    }

    public final void setSaveInPublicStorage(boolean z) {
        this.saveInPublicStorage = z;
    }

    public final boolean getAllowCellular() {
        return this.allowCellular;
    }

    public final void setAllowCellular(boolean z) {
        this.allowCellular = z;
    }
}
