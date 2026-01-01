package org.apache.commons.io;

import java.io.File;

@Deprecated
/* loaded from: classes4.dex */
public class FileCleaner {
    private static final FileCleaningTracker INSTANCE = new FileCleaningTracker();

    @Deprecated
    public static synchronized void exitWhenFinished() {
        INSTANCE.exitWhenFinished();
    }

    public static FileCleaningTracker getInstance() {
        return INSTANCE;
    }

    @Deprecated
    public static int getTrackCount() {
        return INSTANCE.getTrackCount();
    }

    @Deprecated
    public static void track(File file, Object obj) {
        INSTANCE.track(file, obj);
    }

    @Deprecated
    public static void track(File file, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        INSTANCE.track(file, obj, fileDeleteStrategy);
    }

    @Deprecated
    public static void track(String str, Object obj) {
        INSTANCE.track(str, obj);
    }

    @Deprecated
    public static void track(String str, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        INSTANCE.track(str, obj, fileDeleteStrategy);
    }
}
