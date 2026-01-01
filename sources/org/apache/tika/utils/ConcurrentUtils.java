package org.apache.tika.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class ConcurrentUtils {
    public static Future execute(ParseContext parseContext, Runnable runnable) {
        ExecutorService executorService = (ExecutorService) parseContext.get(ExecutorService.class);
        if (executorService == null) {
            FutureTask futureTask = new FutureTask(runnable, null);
            new Thread(futureTask, "Tika Thread").start();
            return futureTask;
        }
        return executorService.submit(runnable);
    }
}
