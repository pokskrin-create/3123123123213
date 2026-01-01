package org.apache.tika.mime;

/* loaded from: classes4.dex */
class Magic implements Clause, Comparable<Magic> {
    private final Clause clause;
    private final int priority;
    private final String string;
    private final MimeType type;

    Magic(MimeType mimeType, int i, Clause clause) {
        this.type = mimeType;
        this.priority = i;
        this.clause = clause;
        this.string = "[" + i + "/" + clause + "]";
    }

    MimeType getType() {
        return this.type;
    }

    int getPriority() {
        return this.priority;
    }

    @Override // org.apache.tika.mime.Clause
    public boolean eval(byte[] bArr) {
        return this.clause.eval(bArr);
    }

    @Override // org.apache.tika.mime.Clause
    public int size() {
        return this.clause.size();
    }

    public String toString() {
        return this.string;
    }

    @Override // java.lang.Comparable
    public int compareTo(Magic magic) {
        int iCompareTo2 = magic.priority - this.priority;
        if (iCompareTo2 == 0) {
            iCompareTo2 = magic.size() - size();
        }
        if (iCompareTo2 == 0) {
            iCompareTo2 = magic.type.compareTo(this.type);
        }
        return iCompareTo2 == 0 ? magic.string.compareTo(this.string) : iCompareTo2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Magic) {
            Magic magic = (Magic) obj;
            if (this.type.equals(magic.type) && this.string.equals(magic.string)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.type.hashCode() ^ this.string.hashCode();
    }
}
