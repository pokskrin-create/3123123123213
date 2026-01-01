package org.apache.tika.mime;

import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
class MinShouldMatchClause implements Clause {
    private final List<Clause> clauses;
    private final int min;

    MinShouldMatchClause(int i, List<Clause> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("clauses must be not null with size > 0");
        }
        if (i <= list.size()) {
            if (i <= 0) {
                throw new IllegalArgumentException("min cannot be <= 0: " + i);
            }
            this.min = i;
            this.clauses = list;
            return;
        }
        throw new IllegalArgumentException("min (" + i + ") cannot be > clauses.size (" + list.size() + ")");
    }

    @Override // org.apache.tika.mime.Clause
    public boolean eval(byte[] bArr) {
        Iterator<Clause> it = this.clauses.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().eval(bArr) && (i = i + 1) >= this.min) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.tika.mime.Clause
    public int size() {
        Iterator<Clause> it = this.clauses.iterator();
        int iMax = 0;
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().size());
        }
        return iMax;
    }

    public String toString() {
        return "minShouldMatch (min: " + this.min + ") " + this.clauses;
    }
}
