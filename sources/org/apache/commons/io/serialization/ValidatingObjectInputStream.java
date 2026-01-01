package org.apache.commons.io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.regex.Pattern;
import org.apache.commons.io.build.AbstractStreamBuilder;

/* loaded from: classes4.dex */
public class ValidatingObjectInputStream extends ObjectInputStream {
    private final ObjectStreamClassPredicate predicate;

    public static class Builder extends AbstractStreamBuilder<ValidatingObjectInputStream, Builder> {
        private ObjectStreamClassPredicate predicate = new ObjectStreamClassPredicate();

        @Deprecated
        public Builder() {
        }

        public Builder accept(Class<?>... clsArr) {
            this.predicate.accept(clsArr);
            return this;
        }

        public Builder accept(ClassNameMatcher classNameMatcher) {
            this.predicate.accept(classNameMatcher);
            return this;
        }

        public Builder accept(Pattern pattern) {
            this.predicate.accept(pattern);
            return this;
        }

        public Builder accept(String... strArr) {
            this.predicate.accept(strArr);
            return this;
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public ValidatingObjectInputStream get() throws IOException {
            return new ValidatingObjectInputStream(getInputStream(), this.predicate);
        }

        public ObjectStreamClassPredicate getPredicate() {
            return this.predicate;
        }

        public Builder reject(Class<?>... clsArr) {
            this.predicate.reject(clsArr);
            return this;
        }

        public Builder reject(ClassNameMatcher classNameMatcher) {
            this.predicate.reject(classNameMatcher);
            return this;
        }

        public Builder reject(Pattern pattern) {
            this.predicate.reject(pattern);
            return this;
        }

        public Builder reject(String... strArr) {
            this.predicate.reject(strArr);
            return this;
        }

        public Builder setPredicate(ObjectStreamClassPredicate objectStreamClassPredicate) {
            if (objectStreamClassPredicate == null) {
                objectStreamClassPredicate = new ObjectStreamClassPredicate();
            }
            this.predicate = objectStreamClassPredicate;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public ValidatingObjectInputStream(InputStream inputStream) throws IOException {
        this(inputStream, new ObjectStreamClassPredicate());
    }

    private ValidatingObjectInputStream(InputStream inputStream, ObjectStreamClassPredicate objectStreamClassPredicate) throws IOException {
        super(inputStream);
        this.predicate = objectStreamClassPredicate;
    }

    public ValidatingObjectInputStream accept(Class<?>... clsArr) {
        this.predicate.accept(clsArr);
        return this;
    }

    public ValidatingObjectInputStream accept(ClassNameMatcher classNameMatcher) {
        this.predicate.accept(classNameMatcher);
        return this;
    }

    public ValidatingObjectInputStream accept(Pattern pattern) {
        this.predicate.accept(pattern);
        return this;
    }

    public ValidatingObjectInputStream accept(String... strArr) {
        this.predicate.accept(strArr);
        return this;
    }

    private void checkClassName(String str) throws InvalidClassException {
        if (this.predicate.test(str)) {
            return;
        }
        invalidClassNameFound(str);
    }

    protected void invalidClassNameFound(String str) throws InvalidClassException {
        throw new InvalidClassException("Class name not accepted: " + str);
    }

    public <T> T readObjectCast() throws ClassNotFoundException, IOException {
        return (T) super.readObject();
    }

    public ValidatingObjectInputStream reject(Class<?>... clsArr) {
        this.predicate.reject(clsArr);
        return this;
    }

    public ValidatingObjectInputStream reject(ClassNameMatcher classNameMatcher) {
        this.predicate.reject(classNameMatcher);
        return this;
    }

    public ValidatingObjectInputStream reject(Pattern pattern) {
        this.predicate.reject(pattern);
        return this;
    }

    public ValidatingObjectInputStream reject(String... strArr) {
        this.predicate.reject(strArr);
        return this;
    }

    @Override // java.io.ObjectInputStream
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        checkClassName(objectStreamClass.getName());
        return super.resolveClass(objectStreamClass);
    }
}
