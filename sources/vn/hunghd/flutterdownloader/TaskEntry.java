package vn.hunghd.flutterdownloader;

import android.provider.BaseColumns;
import kotlin.Metadata;

/* compiled from: TaskEntry.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lvn/hunghd/flutterdownloader/TaskEntry;", "Landroid/provider/BaseColumns;", "<init>", "()V", "TABLE_NAME", "", "COLUMN_NAME_TASK_ID", "COLUMN_NAME_STATUS", "COLUMN_NAME_PROGRESS", "COLUMN_NAME_URL", "COLUMN_NAME_SAVED_DIR", "COLUMN_NAME_FILE_NAME", "COLUMN_NAME_MIME_TYPE", "COLUMN_NAME_RESUMABLE", "COLUMN_NAME_HEADERS", "COLUMN_NAME_SHOW_NOTIFICATION", "COLUMN_NAME_OPEN_FILE_FROM_NOTIFICATION", "COLUMN_NAME_TIME_CREATED", "COLUMN_SAVE_IN_PUBLIC_STORAGE", "COLUMN_ALLOW_CELLULAR", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class TaskEntry implements BaseColumns {
    public static final String COLUMN_ALLOW_CELLULAR = "allow_cellular";
    public static final String COLUMN_NAME_FILE_NAME = "file_name";
    public static final String COLUMN_NAME_HEADERS = "headers";
    public static final String COLUMN_NAME_MIME_TYPE = "mime_type";
    public static final String COLUMN_NAME_OPEN_FILE_FROM_NOTIFICATION = "open_file_from_notification";
    public static final String COLUMN_NAME_PROGRESS = "progress";
    public static final String COLUMN_NAME_RESUMABLE = "resumable";
    public static final String COLUMN_NAME_SAVED_DIR = "saved_dir";
    public static final String COLUMN_NAME_SHOW_NOTIFICATION = "show_notification";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_TASK_ID = "task_id";
    public static final String COLUMN_NAME_TIME_CREATED = "time_created";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_SAVE_IN_PUBLIC_STORAGE = "save_in_public_storage";
    public static final TaskEntry INSTANCE = new TaskEntry();
    public static final String TABLE_NAME = "task";

    private TaskEntry() {
    }
}
