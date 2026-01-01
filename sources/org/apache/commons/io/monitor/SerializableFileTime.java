package org.apache.commons.io.monitor;

import j$.time.Instant;
import j$.time.TimeConversions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0;
import org.apache.commons.io.file.attribute.FileTimes;

/* loaded from: classes4.dex */
final class SerializableFileTime implements Serializable {
    static final SerializableFileTime EPOCH = new SerializableFileTime(FileTimes.EPOCH);
    private static final long serialVersionUID = 1;
    private FileTime fileTime;

    SerializableFileTime(FileTime fileTime) {
        this.fileTime = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m2149m(Objects.requireNonNull(fileTime));
    }

    public int compareTo(FileTime fileTime) {
        return this.fileTime.compareTo(fileTime);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerializableFileTime) {
            return Objects.equals(this.fileTime, ((SerializableFileTime) obj).fileTime);
        }
        return false;
    }

    public int hashCode() {
        return this.fileTime.hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        this.fileTime = FileTime.from(TimeConversions.convert((Instant) objectInputStream.readObject()));
    }

    long to(TimeUnit timeUnit) {
        return this.fileTime.to(timeUnit);
    }

    Instant toInstant() {
        return TimeConversions.convert(this.fileTime.toInstant());
    }

    long toMillis() {
        return this.fileTime.toMillis();
    }

    public String toString() {
        return this.fileTime.toString();
    }

    FileTime unwrap() {
        return this.fileTime;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(TimeConversions.convert(this.fileTime.toInstant()));
    }
}
