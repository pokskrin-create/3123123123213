package org.apache.commons.io.serialization;

import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class ObjectStreamClassPredicate implements Predicate<ObjectStreamClass> {
    private final List<ClassNameMatcher> acceptMatchers = new ArrayList();
    private final List<ClassNameMatcher> rejectMatchers = new ArrayList();

    static /* synthetic */ FullClassNameMatcher lambda$accept$0(Class cls) {
        return new FullClassNameMatcher(cls.getName());
    }

    public ObjectStreamClassPredicate accept(Class<?>... clsArr) {
        Stream map = Stream.of((Object[]) clsArr).map(new Function() { // from class: org.apache.commons.io.serialization.ObjectStreamClassPredicate$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ObjectStreamClassPredicate.lambda$accept$0((Class) obj);
            }
        });
        List<ClassNameMatcher> list = this.acceptMatchers;
        Objects.requireNonNull(list);
        map.forEach(new ObjectStreamClassPredicate$$ExternalSyntheticLambda1(list));
        return this;
    }

    public ObjectStreamClassPredicate accept(ClassNameMatcher classNameMatcher) {
        this.acceptMatchers.add(classNameMatcher);
        return this;
    }

    public ObjectStreamClassPredicate accept(Pattern pattern) {
        this.acceptMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ObjectStreamClassPredicate accept(String... strArr) {
        Stream map = Stream.of((Object[]) strArr).map(new ObjectStreamClassPredicate$$ExternalSyntheticLambda3());
        List<ClassNameMatcher> list = this.acceptMatchers;
        Objects.requireNonNull(list);
        map.forEach(new ObjectStreamClassPredicate$$ExternalSyntheticLambda4(list));
        return this;
    }

    static /* synthetic */ FullClassNameMatcher lambda$reject$1(Class cls) {
        return new FullClassNameMatcher(cls.getName());
    }

    public ObjectStreamClassPredicate reject(Class<?>... clsArr) {
        Stream map = Stream.of((Object[]) clsArr).map(new Function() { // from class: org.apache.commons.io.serialization.ObjectStreamClassPredicate$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ObjectStreamClassPredicate.lambda$reject$1((Class) obj);
            }
        });
        List<ClassNameMatcher> list = this.rejectMatchers;
        Objects.requireNonNull(list);
        map.forEach(new ObjectStreamClassPredicate$$ExternalSyntheticLambda1(list));
        return this;
    }

    public ObjectStreamClassPredicate reject(ClassNameMatcher classNameMatcher) {
        this.rejectMatchers.add(classNameMatcher);
        return this;
    }

    public ObjectStreamClassPredicate reject(Pattern pattern) {
        this.rejectMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ObjectStreamClassPredicate reject(String... strArr) {
        Stream map = Stream.of((Object[]) strArr).map(new ObjectStreamClassPredicate$$ExternalSyntheticLambda3());
        List<ClassNameMatcher> list = this.rejectMatchers;
        Objects.requireNonNull(list);
        map.forEach(new ObjectStreamClassPredicate$$ExternalSyntheticLambda4(list));
        return this;
    }

    @Override // java.util.function.Predicate
    public boolean test(ObjectStreamClass objectStreamClass) {
        return test(objectStreamClass.getName());
    }

    public boolean test(String str) {
        Iterator<ClassNameMatcher> it = this.rejectMatchers.iterator();
        while (it.hasNext()) {
            if (it.next().matches(str)) {
                return false;
            }
        }
        Iterator<ClassNameMatcher> it2 = this.acceptMatchers.iterator();
        while (it2.hasNext()) {
            if (it2.next().matches(str)) {
                return true;
            }
        }
        return false;
    }
}
