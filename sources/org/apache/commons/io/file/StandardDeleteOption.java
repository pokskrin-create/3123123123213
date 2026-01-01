package org.apache.commons.io.file;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public enum StandardDeleteOption implements DeleteOption {
    OVERRIDE_READ_ONLY;

    public static boolean overrideReadOnly(DeleteOption[] deleteOptionArr) {
        if (IOUtils.length(deleteOptionArr) == 0) {
            return false;
        }
        return Stream.of((Object[]) deleteOptionArr).anyMatch(new Predicate() { // from class: org.apache.commons.io.file.StandardDeleteOption$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StandardDeleteOption.lambda$overrideReadOnly$0((DeleteOption) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$overrideReadOnly$0(DeleteOption deleteOption) {
        return OVERRIDE_READ_ONLY == deleteOption;
    }
}
