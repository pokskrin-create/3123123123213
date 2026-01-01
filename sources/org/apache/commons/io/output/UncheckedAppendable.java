package org.apache.commons.io.output;

/* loaded from: classes4.dex */
public interface UncheckedAppendable extends Appendable {
    @Override // java.lang.Appendable
    UncheckedAppendable append(char c);

    @Override // java.lang.Appendable
    UncheckedAppendable append(CharSequence charSequence);

    @Override // java.lang.Appendable
    UncheckedAppendable append(CharSequence charSequence, int i, int i2);

    static UncheckedAppendable on(Appendable appendable) {
        return new UncheckedAppendableImpl(appendable);
    }
}
