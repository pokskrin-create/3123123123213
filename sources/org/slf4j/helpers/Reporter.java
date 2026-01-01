package org.slf4j.helpers;

import java.io.PrintStream;

/* loaded from: classes4.dex */
public class Reporter {
    static final String SLF4J_DEBUG_PREFIX = "SLF4J(D): ";
    static final String SLF4J_ERROR_PREFIX = "SLF4J(E): ";
    static final String SLF4J_INFO_PREFIX = "SLF4J(I): ";
    public static final String SLF4J_INTERNAL_REPORT_STREAM_KEY = "slf4j.internal.report.stream";
    public static final String SLF4J_INTERNAL_VERBOSITY_KEY = "slf4j.internal.verbosity";
    static final String SLF4J_WARN_PREFIX = "SLF4J(W): ";
    private static final String[] SYSOUT_KEYS = {"System.out", "stdout", "sysout"};
    private static final TargetChoice TARGET_CHOICE = getTargetChoice();
    private static final Level INTERNAL_VERBOSITY = initVerbosity();

    private enum TargetChoice {
        Stderr,
        Stdout
    }

    private enum Level {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3);

        int levelInt;

        Level(int i) {
            this.levelInt = i;
        }

        private int getLevelInt() {
            return this.levelInt;
        }
    }

    private static TargetChoice getTargetChoice() {
        String property = System.getProperty(SLF4J_INTERNAL_REPORT_STREAM_KEY);
        if (property == null || property.isEmpty()) {
            return TargetChoice.Stderr;
        }
        for (String str : SYSOUT_KEYS) {
            if (str.equalsIgnoreCase(property)) {
                return TargetChoice.Stdout;
            }
        }
        return TargetChoice.Stderr;
    }

    private static Level initVerbosity() {
        String property = System.getProperty(SLF4J_INTERNAL_VERBOSITY_KEY);
        if (property == null || property.isEmpty()) {
            return Level.INFO;
        }
        if (property.equalsIgnoreCase("DEBUG")) {
            return Level.DEBUG;
        }
        if (property.equalsIgnoreCase("ERROR")) {
            return Level.ERROR;
        }
        if (property.equalsIgnoreCase("WARN")) {
            return Level.WARN;
        }
        return Level.INFO;
    }

    static boolean isEnabledFor(Level level) {
        return level.levelInt >= INTERNAL_VERBOSITY.levelInt;
    }

    private static PrintStream getTarget() {
        if (TARGET_CHOICE.ordinal() == 1) {
            return System.out;
        }
        return System.err;
    }

    public static void debug(String str) {
        if (isEnabledFor(Level.DEBUG)) {
            getTarget().println(SLF4J_DEBUG_PREFIX + str);
        }
    }

    public static void info(String str) {
        if (isEnabledFor(Level.INFO)) {
            getTarget().println(SLF4J_INFO_PREFIX + str);
        }
    }

    public static final void warn(String str) {
        if (isEnabledFor(Level.WARN)) {
            getTarget().println(SLF4J_WARN_PREFIX + str);
        }
    }

    public static final void error(String str, Throwable th) {
        getTarget().println(SLF4J_ERROR_PREFIX + str);
        getTarget().println("SLF4J(E): Reported exception:");
        th.printStackTrace(getTarget());
    }

    public static final void error(String str) {
        getTarget().println(SLF4J_ERROR_PREFIX + str);
    }
}
