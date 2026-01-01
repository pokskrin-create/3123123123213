package org.apache.tika.mime;

import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
class OrClause implements Clause {
    private final List<Clause> clauses;

    OrClause(List<Clause> list) {
        this.clauses = list;
    }

    @Override // org.apache.tika.mime.Clause
    public boolean eval(byte[] bArr) {
        Iterator<Clause> it = this.clauses.iterator();
        while (it.hasNext()) {
            if (it.next().eval(bArr)) {
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
        return "or" + this.clauses;
    }
}
