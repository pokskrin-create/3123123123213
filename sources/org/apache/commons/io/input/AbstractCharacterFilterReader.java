package org.apache.commons.io.input;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.IntPredicate;

/* loaded from: classes4.dex */
public abstract class AbstractCharacterFilterReader extends FilterReader {
    protected static final IntPredicate SKIP_NONE = new IntPredicate() { // from class: org.apache.commons.io.input.AbstractCharacterFilterReader$$ExternalSyntheticLambda0
        @Override // java.util.function.IntPredicate
        public final boolean test(int i) {
            return AbstractCharacterFilterReader.lambda$static$0(i);
        }
    };
    private final IntPredicate skip;

    static /* synthetic */ boolean lambda$static$0(int i) {
        return false;
    }

    protected AbstractCharacterFilterReader(Reader reader) {
        this(reader, SKIP_NONE);
    }

    protected AbstractCharacterFilterReader(Reader reader, IntPredicate intPredicate) {
        super(reader);
        this.skip = intPredicate == null ? SKIP_NONE : intPredicate;
    }

    protected boolean filter(int i) {
        return this.skip.test(i);
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read() throws IOException {
        int i;
        do {
            i = this.in.read();
            if (i == -1) {
                break;
            }
        } while (filter(i));
        return i;
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3 = super.read(cArr, i, i2);
        if (i3 == -1) {
            return -1;
        }
        int i4 = i - 1;
        for (int i5 = i; i5 < i + i3; i5++) {
            if (!filter(cArr[i5]) && (i4 = i4 + 1) < i5) {
                cArr[i4] = cArr[i5];
            }
        }
        return (i4 - i) + 1;
    }
}
