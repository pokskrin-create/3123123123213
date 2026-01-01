package org.apache.commons.io;

import j$.time.Duration;

/* loaded from: classes4.dex */
final class ThreadMonitor implements Runnable {
    private final Thread thread;
    private final Duration timeout;

    static Thread start(Duration duration) {
        return start(Thread.currentThread(), duration);
    }

    static Thread start(Thread thread, Duration duration) {
        if (duration.isZero() || duration.isNegative()) {
            return null;
        }
        Thread thread2 = new Thread(new ThreadMonitor(thread, duration), "ThreadMonitor");
        thread2.setDaemon(true);
        thread2.start();
        return thread2;
    }

    static void stop(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    private ThreadMonitor(Thread thread, Duration duration) {
        this.thread = thread;
        this.timeout = duration;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ThreadUtils.sleep(this.timeout);
            this.thread.interrupt();
        } catch (InterruptedException unused) {
        }
    }
}
