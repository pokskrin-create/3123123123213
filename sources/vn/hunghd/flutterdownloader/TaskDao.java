package vn.hunghd.flutterdownloader;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.actions.SearchIntents;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TaskDao.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005Jh\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0016J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bJ\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\b\u0010\u001e\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\f\u001a\u00020\bJ\u001e\u0010 \u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J0\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\b2\b\u0010\"\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u0016J\u0016\u0010 \u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u0016J\"\u0010 \u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\b\u0010$\u001a\u0004\u0018\u00010\b2\b\u0010%\u001a\u0004\u0018\u00010\bJ\u000e\u0010&\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bJ\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020)H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006*"}, d2 = {"Lvn/hunghd/flutterdownloader/TaskDao;", "", "dbHelper", "Lvn/hunghd/flutterdownloader/TaskDbHelper;", "<init>", "(Lvn/hunghd/flutterdownloader/TaskDbHelper;)V", "projection", "", "", "[Ljava/lang/String;", "insertOrUpdateNewTask", "", "taskId", "url", "status", "Lvn/hunghd/flutterdownloader/DownloadStatus;", "progress", "", "fileName", "savedDir", "headers", "showNotification", "", "openFileFromNotification", "saveInPublicStorage", "allowCellular", "loadAllTasks", "", "Lvn/hunghd/flutterdownloader/DownloadTask;", "loadTasksWithRawQuery", SearchIntents.EXTRA_QUERY, "loadTask", "updateTask", "currentTaskId", "newTaskId", TaskEntry.COLUMN_NAME_RESUMABLE, "filename", "mimeType", "deleteTask", "parseCursor", "cursor", "Landroid/database/Cursor;", "flutter_downloader_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class TaskDao {
    private final TaskDbHelper dbHelper;
    private final String[] projection;

    public TaskDao(TaskDbHelper dbHelper) {
        Intrinsics.checkNotNullParameter(dbHelper, "dbHelper");
        this.dbHelper = dbHelper;
        this.projection = new String[]{"_id", TaskEntry.COLUMN_NAME_TASK_ID, "progress", "status", "url", "file_name", TaskEntry.COLUMN_NAME_SAVED_DIR, "headers", TaskEntry.COLUMN_NAME_MIME_TYPE, TaskEntry.COLUMN_NAME_RESUMABLE, "open_file_from_notification", "show_notification", TaskEntry.COLUMN_NAME_TIME_CREATED, "save_in_public_storage", TaskEntry.COLUMN_ALLOW_CELLULAR};
    }

    public final void insertOrUpdateNewTask(String taskId, String url, DownloadStatus status, int progress, String fileName, String savedDir, String headers, boolean showNotification, boolean openFileFromNotification, boolean saveInPublicStorage, boolean allowCellular) {
        Intrinsics.checkNotNullParameter(status, "status");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_NAME_TASK_ID, taskId);
        contentValues.put("url", url);
        contentValues.put("status", Integer.valueOf(status.ordinal()));
        contentValues.put("progress", Integer.valueOf(progress));
        contentValues.put("file_name", fileName);
        contentValues.put(TaskEntry.COLUMN_NAME_SAVED_DIR, savedDir);
        contentValues.put("headers", headers);
        contentValues.put(TaskEntry.COLUMN_NAME_MIME_TYPE, EnvironmentCompat.MEDIA_UNKNOWN);
        contentValues.put("show_notification", Integer.valueOf(showNotification ? 1 : 0));
        contentValues.put("open_file_from_notification", Integer.valueOf(openFileFromNotification ? 1 : 0));
        contentValues.put(TaskEntry.COLUMN_NAME_RESUMABLE, (Integer) 0);
        contentValues.put(TaskEntry.COLUMN_NAME_TIME_CREATED, Long.valueOf(System.currentTimeMillis()));
        contentValues.put("save_in_public_storage", Integer.valueOf(saveInPublicStorage ? 1 : 0));
        contentValues.put(TaskEntry.COLUMN_ALLOW_CELLULAR, Integer.valueOf(allowCellular ? 1 : 0));
        writableDatabase.beginTransaction();
        try {
            writableDatabase.insertWithOnConflict(TaskEntry.TABLE_NAME, null, contentValues, 5);
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final List<DownloadTask> loadAllTasks() {
        Cursor cursorQuery = this.dbHelper.getReadableDatabase().query(TaskEntry.TABLE_NAME, this.projection, null, null, null, null, null);
        Intrinsics.checkNotNullExpressionValue(cursorQuery, "query(...)");
        ArrayList arrayList = new ArrayList();
        while (cursorQuery.moveToNext()) {
            arrayList.add(parseCursor(cursorQuery));
        }
        cursorQuery.close();
        return arrayList;
    }

    public final List<DownloadTask> loadTasksWithRawQuery(String query) {
        SQLiteDatabase readableDatabase = this.dbHelper.getReadableDatabase();
        Intrinsics.checkNotNull(query);
        Cursor cursorRawQuery = readableDatabase.rawQuery(query, null);
        Intrinsics.checkNotNullExpressionValue(cursorRawQuery, "rawQuery(...)");
        ArrayList arrayList = new ArrayList();
        while (cursorRawQuery.moveToNext()) {
            arrayList.add(parseCursor(cursorRawQuery));
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public final DownloadTask loadTask(String taskId) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        Cursor cursorQuery = this.dbHelper.getReadableDatabase().query(TaskEntry.TABLE_NAME, this.projection, "task_id = ?", new String[]{taskId}, null, null, "_id DESC", "1");
        Intrinsics.checkNotNullExpressionValue(cursorQuery, "query(...)");
        DownloadTask cursor = null;
        while (cursorQuery.moveToNext()) {
            cursor = parseCursor(cursorQuery);
        }
        cursorQuery.close();
        return cursor;
    }

    public final void updateTask(String taskId, DownloadStatus status, int progress) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        Intrinsics.checkNotNullParameter(status, "status");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", Integer.valueOf(status.ordinal()));
        contentValues.put("progress", Integer.valueOf(progress));
        writableDatabase.beginTransaction();
        try {
            writableDatabase.update(TaskEntry.TABLE_NAME, contentValues, "task_id = ?", new String[]{taskId});
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void updateTask(String currentTaskId, String newTaskId, DownloadStatus status, int progress, boolean resumable) {
        Intrinsics.checkNotNullParameter(currentTaskId, "currentTaskId");
        Intrinsics.checkNotNullParameter(status, "status");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_NAME_TASK_ID, newTaskId);
        contentValues.put("status", Integer.valueOf(status.ordinal()));
        contentValues.put("progress", Integer.valueOf(progress));
        contentValues.put(TaskEntry.COLUMN_NAME_RESUMABLE, Integer.valueOf(resumable ? 1 : 0));
        contentValues.put(TaskEntry.COLUMN_NAME_TIME_CREATED, Long.valueOf(System.currentTimeMillis()));
        writableDatabase.beginTransaction();
        try {
            writableDatabase.update(TaskEntry.TABLE_NAME, contentValues, "task_id = ?", new String[]{currentTaskId});
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void updateTask(String taskId, boolean resumable) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_NAME_RESUMABLE, Integer.valueOf(resumable ? 1 : 0));
        writableDatabase.beginTransaction();
        try {
            writableDatabase.update(TaskEntry.TABLE_NAME, contentValues, "task_id = ?", new String[]{taskId});
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void updateTask(String taskId, String filename, String mimeType) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("file_name", filename);
        if (mimeType == null) {
            mimeType = EnvironmentCompat.MEDIA_UNKNOWN;
        }
        contentValues.put(TaskEntry.COLUMN_NAME_MIME_TYPE, mimeType);
        writableDatabase.beginTransaction();
        try {
            writableDatabase.update(TaskEntry.TABLE_NAME, contentValues, "task_id = ?", new String[]{taskId});
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void deleteTask(String taskId) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            writableDatabase.delete(TaskEntry.TABLE_NAME, "task_id = ?", new String[]{taskId});
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    private final vn.hunghd.flutterdownloader.DownloadTask parseCursor(android.database.Cursor r20) {
        /*
            r19 = this;
            r0 = r20
            java.lang.String r1 = "_id"
            int r1 = r0.getColumnIndexOrThrow(r1)
            int r3 = r0.getInt(r1)
            java.lang.String r1 = "task_id"
            int r1 = r0.getColumnIndexOrThrow(r1)
            java.lang.String r4 = r0.getString(r1)
            java.lang.String r1 = "status"
            int r1 = r0.getColumnIndexOrThrow(r1)
            int r1 = r0.getInt(r1)
            java.lang.String r2 = "progress"
            int r2 = r0.getColumnIndexOrThrow(r2)
            int r6 = r0.getInt(r2)
            java.lang.String r2 = "url"
            int r2 = r0.getColumnIndexOrThrow(r2)
            java.lang.String r7 = r0.getString(r2)
            java.lang.String r2 = "file_name"
            int r2 = r0.getColumnIndexOrThrow(r2)
            java.lang.String r8 = r0.getString(r2)
            java.lang.String r2 = "saved_dir"
            int r2 = r0.getColumnIndexOrThrow(r2)
            java.lang.String r9 = r0.getString(r2)
            java.lang.String r2 = "headers"
            int r2 = r0.getColumnIndexOrThrow(r2)
            java.lang.String r10 = r0.getString(r2)
            java.lang.String r2 = "mime_type"
            int r2 = r0.getColumnIndexOrThrow(r2)
            java.lang.String r11 = r0.getString(r2)
            java.lang.String r2 = "resumable"
            int r2 = r0.getColumnIndexOrThrow(r2)
            short r2 = r0.getShort(r2)
            java.lang.String r5 = "show_notification"
            int r5 = r0.getColumnIndexOrThrow(r5)
            short r5 = r0.getShort(r5)
            java.lang.String r12 = "open_file_from_notification"
            int r12 = r0.getColumnIndexOrThrow(r12)
            short r12 = r0.getShort(r12)
            java.lang.String r13 = "time_created"
            int r13 = r0.getColumnIndexOrThrow(r13)
            long r15 = r0.getLong(r13)
            java.lang.String r13 = "save_in_public_storage"
            int r13 = r0.getColumnIndexOrThrow(r13)
            short r13 = r0.getShort(r13)
            java.lang.String r14 = "allow_cellular"
            int r14 = r0.getColumnIndexOrThrow(r14)
            short r0 = r0.getShort(r14)
            vn.hunghd.flutterdownloader.DownloadTask r14 = new vn.hunghd.flutterdownloader.DownloadTask
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            vn.hunghd.flutterdownloader.DownloadStatus[] r17 = vn.hunghd.flutterdownloader.DownloadStatus.values()
            r1 = r17[r1]
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)
            r17 = 0
            r20 = r1
            r1 = 1
            if (r2 != r1) goto Lb5
            r2 = r1
            goto Lb7
        Lb5:
            r2 = r17
        Lb7:
            if (r5 != r1) goto Lbb
            r5 = r1
            goto Lbd
        Lbb:
            r5 = r17
        Lbd:
            if (r12 != r1) goto Lc3
            r12 = r2
            r2 = r14
            r14 = r1
            goto Lc7
        Lc3:
            r12 = r2
            r2 = r14
            r14 = r17
        Lc7:
            if (r13 != r1) goto Lce
            r13 = r17
            r17 = r1
            goto Ld0
        Lce:
            r13 = r17
        Ld0:
            if (r0 != r1) goto Ld5
            r18 = r1
            goto Ld7
        Ld5:
            r18 = r13
        Ld7:
            r13 = r5
            r5 = r20
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r17, r18)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: vn.hunghd.flutterdownloader.TaskDao.parseCursor(android.database.Cursor):vn.hunghd.flutterdownloader.DownloadTask");
    }
}
