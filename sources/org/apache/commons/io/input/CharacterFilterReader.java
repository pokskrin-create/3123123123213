package org.apache.commons.io.input;

import java.io.Reader;
import java.util.function.IntPredicate;

/* loaded from: classes4.dex */
public class CharacterFilterReader extends AbstractCharacterFilterReader {
    static /* synthetic */ boolean lambda$new$0(int i, int i2) {
        return i2 == i;
    }

    public CharacterFilterReader(Reader reader, final int i) {
        super(reader, new IntPredicate() { // from class: org.apache.commons.io.input.CharacterFilterReader$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                return CharacterFilterReader.lambda$new$0(i, i2);
            }
        });
    }

    public CharacterFilterReader(Reader reader, IntPredicate intPredicate) {
        super(reader, intPredicate);
    }
}
