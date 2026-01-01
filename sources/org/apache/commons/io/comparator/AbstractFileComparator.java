package org.apache.commons.io.comparator;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
abstract class AbstractFileComparator implements Comparator<File> {
    AbstractFileComparator() {
    }

    public File[] sort(File... fileArr) {
        if (fileArr != null) {
            Arrays.sort(fileArr, this);
        }
        return fileArr;
    }

    public List<File> sort(List<File> list) {
        if (list != null) {
            list.sort(this);
        }
        return list;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
