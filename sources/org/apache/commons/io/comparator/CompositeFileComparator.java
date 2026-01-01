package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: classes4.dex */
public class CompositeFileComparator extends AbstractFileComparator implements Serializable {
    private static final Comparator<?>[] EMPTY_COMPARATOR_ARRAY = new Comparator[0];
    private static final long serialVersionUID = -2224170307287243428L;
    private final Comparator<File>[] delegates;

    @Override // org.apache.commons.io.comparator.AbstractFileComparator
    public /* bridge */ /* synthetic */ List sort(List list) {
        return super.sort((List<File>) list);
    }

    @Override // org.apache.commons.io.comparator.AbstractFileComparator
    public /* bridge */ /* synthetic */ File[] sort(File[] fileArr) {
        return super.sort(fileArr);
    }

    public CompositeFileComparator(Comparator<File>... comparatorArr) {
        this.delegates = comparatorArr == null ? emptyArray() : (Comparator[]) comparatorArr.clone();
    }

    public CompositeFileComparator(Iterable<Comparator<File>> iterable) {
        Comparator<File>[] comparatorArrEmptyArray;
        if (iterable == null) {
            comparatorArrEmptyArray = emptyArray();
        } else {
            comparatorArrEmptyArray = (Comparator[]) StreamSupport.stream(iterable.spliterator(), false).toArray(new IntFunction() { // from class: org.apache.commons.io.comparator.CompositeFileComparator$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return CompositeFileComparator.lambda$new$0(i);
                }
            });
        }
        this.delegates = comparatorArrEmptyArray;
    }

    static /* synthetic */ Comparator[] lambda$new$0(int i) {
        return new Comparator[i];
    }

    static /* synthetic */ boolean lambda$compare$2(Integer num) {
        return num.intValue() != 0;
    }

    @Override // java.util.Comparator
    public int compare(final File file, final File file2) {
        return ((Integer) Stream.of((Object[]) this.delegates).map(new Function() { // from class: org.apache.commons.io.comparator.CompositeFileComparator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((Comparator) obj).compare(file, file2));
            }
        }).filter(new Predicate() { // from class: org.apache.commons.io.comparator.CompositeFileComparator$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CompositeFileComparator.lambda$compare$2((Integer) obj);
            }
        }).findFirst().orElse(0)).intValue();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.Comparator<?>[], java.util.Comparator<java.io.File>[]] */
    private Comparator<File>[] emptyArray() {
        return EMPTY_COMPARATOR_ARRAY;
    }

    @Override // org.apache.commons.io.comparator.AbstractFileComparator
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('{');
        for (int i = 0; i < this.delegates.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.delegates[i]);
        }
        sb.append('}');
        return sb.toString();
    }
}
