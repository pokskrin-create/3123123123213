package vn.hunghd.flutterdownloader;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: DownloadStatus.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lvn/hunghd/flutterdownloader/DownloadStatus;", "", "<init>", "(Ljava/lang/String;I)V", "UNDEFINED", "ENQUEUED", DebugCoroutineInfoImplKt.RUNNING, "COMPLETE", "FAILED", "CANCELED", "PAUSED", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class DownloadStatus {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ DownloadStatus[] $VALUES;
    public static final DownloadStatus UNDEFINED = new DownloadStatus("UNDEFINED", 0);
    public static final DownloadStatus ENQUEUED = new DownloadStatus("ENQUEUED", 1);
    public static final DownloadStatus RUNNING = new DownloadStatus(DebugCoroutineInfoImplKt.RUNNING, 2);
    public static final DownloadStatus COMPLETE = new DownloadStatus("COMPLETE", 3);
    public static final DownloadStatus FAILED = new DownloadStatus("FAILED", 4);
    public static final DownloadStatus CANCELED = new DownloadStatus("CANCELED", 5);
    public static final DownloadStatus PAUSED = new DownloadStatus("PAUSED", 6);

    private static final /* synthetic */ DownloadStatus[] $values() {
        return new DownloadStatus[]{UNDEFINED, ENQUEUED, RUNNING, COMPLETE, FAILED, CANCELED, PAUSED};
    }

    public static EnumEntries<DownloadStatus> getEntries() {
        return $ENTRIES;
    }

    private DownloadStatus(String str, int i) {
    }

    static {
        DownloadStatus[] downloadStatusArr$values = $values();
        $VALUES = downloadStatusArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(downloadStatusArr$values);
    }

    public static DownloadStatus valueOf(String str) {
        return (DownloadStatus) Enum.valueOf(DownloadStatus.class, str);
    }

    public static DownloadStatus[] values() {
        return (DownloadStatus[]) $VALUES.clone();
    }
}
