package vn.hunghd.flutterdownloader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TaskDbHelper.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\u0011\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016¨\u0006\u0013"}, d2 = {"Lvn/hunghd/flutterdownloader/TaskDbHelper;", "Landroid/database/sqlite/SQLiteOpenHelper;", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "onCreate", "", "db", "Landroid/database/sqlite/SQLiteDatabase;", "onUpgrade", "oldVersion", "", "newVersion", "update1to2", "update2to3", "update3to4", "onDowngrade", "Companion", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class TaskDbHelper extends SQLiteOpenHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DATABASE_NAME = "download_tasks.db";
    public static final int DATABASE_VERSION = 4;
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE task (_id INTEGER PRIMARY KEY,task_id VARCHAR(256), url TEXT, status INTEGER DEFAULT 0, progress INTEGER DEFAULT 0, file_name TEXT, saved_dir TEXT, headers TEXT, mime_type VARCHAR(128), resumable TINYINT DEFAULT 0, show_notification TINYINT DEFAULT 0, open_file_from_notification TINYINT DEFAULT 0, time_created INTEGER DEFAULT 0, save_in_public_storage TINYINT DEFAULT 0, allow_cellular TINYINT DEFAULT 1)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS task";
    private static TaskDbHelper instance;

    public /* synthetic */ TaskDbHelper(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    private TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 4);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) throws SQLException {
        Intrinsics.checkNotNullParameter(db, "db");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
        Intrinsics.checkNotNullParameter(db, "db");
        update1to2(db, oldVersion);
        update2to3(db, oldVersion);
        update3to4(db, oldVersion);
    }

    private final void update1to2(SQLiteDatabase db, int oldVersion) throws SQLException {
        if (oldVersion > 1) {
            return;
        }
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private final void update2to3(SQLiteDatabase db, int oldVersion) throws SQLException {
        if (oldVersion > 2) {
            return;
        }
        db.execSQL("ALTER TABLE task ADD COLUMN save_in_public_storage TINYINT DEFAULT 0");
    }

    private final void update3to4(SQLiteDatabase db, int oldVersion) throws SQLException {
        if (oldVersion > 3) {
            return;
        }
        db.execSQL("ALTER TABLE task ADD COLUMN allow_cellular TINYINT DEFAULT 1");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
        Intrinsics.checkNotNullParameter(db, "db");
        onUpgrade(db, oldVersion, newVersion);
    }

    /* compiled from: TaskDbHelper.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lvn/hunghd/flutterdownloader/TaskDbHelper$Companion;", "", "<init>", "()V", "DATABASE_VERSION", "", "DATABASE_NAME", "", "instance", "Lvn/hunghd/flutterdownloader/TaskDbHelper;", "SQL_CREATE_ENTRIES", "SQL_DELETE_ENTRIES", "getInstance", "ctx", "Landroid/content/Context;", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TaskDbHelper getInstance(Context ctx) {
            if (TaskDbHelper.instance == null) {
                Intrinsics.checkNotNull(ctx);
                Context applicationContext = ctx.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                TaskDbHelper.instance = new TaskDbHelper(applicationContext, null);
            }
            TaskDbHelper taskDbHelper = TaskDbHelper.instance;
            Intrinsics.checkNotNull(taskDbHelper);
            return taskDbHelper;
        }
    }
}
