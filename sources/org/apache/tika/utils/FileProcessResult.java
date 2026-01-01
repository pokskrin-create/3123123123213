package org.apache.tika.utils;

/* loaded from: classes4.dex */
public class FileProcessResult {
    String stderr = "";
    String stdout = "";
    int exitValue = -1;
    long processTimeMillis = -1;
    boolean isTimeout = false;
    long stdoutLength = -1;
    long stderrLength = -1;
    boolean stderrTruncated = false;
    boolean stdoutTruncated = false;

    public String getStderr() {
        return this.stderr;
    }

    public String getStdout() {
        return this.stdout;
    }

    public int getExitValue() {
        return this.exitValue;
    }

    public long getProcessTimeMillis() {
        return this.processTimeMillis;
    }

    public boolean isTimeout() {
        return this.isTimeout;
    }

    public long getStdoutLength() {
        return this.stdoutLength;
    }

    public long getStderrLength() {
        return this.stderrLength;
    }

    public boolean isStderrTruncated() {
        return this.stderrTruncated;
    }

    public boolean isStdoutTruncated() {
        return this.stdoutTruncated;
    }

    public void setStderr(String str) {
        this.stderr = str;
    }

    public void setStdout(String str) {
        this.stdout = str;
    }

    public void setExitValue(int i) {
        this.exitValue = i;
    }

    public void setProcessTimeMillis(long j) {
        this.processTimeMillis = j;
    }

    public void setTimeout(boolean z) {
        this.isTimeout = z;
    }

    public void setStdoutLength(long j) {
        this.stdoutLength = j;
    }

    public void setStderrLength(long j) {
        this.stderrLength = j;
    }

    public void setStderrTruncated(boolean z) {
        this.stderrTruncated = z;
    }

    public void setStdoutTruncated(boolean z) {
        this.stdoutTruncated = z;
    }

    public String toString() {
        return "FileProcessResult{stderr='" + this.stderr + "', stdout='" + this.stdout + "', exitValue=" + this.exitValue + ", processTimeMillis=" + this.processTimeMillis + ", isTimeout=" + this.isTimeout + ", stdoutLength=" + this.stdoutLength + ", stderrLength=" + this.stderrLength + ", stderrTruncated=" + this.stderrTruncated + ", stdoutTruncated=" + this.stdoutTruncated + "}";
    }
}
