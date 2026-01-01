package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.function.IOConsumer;

/* loaded from: classes4.dex */
public class FilterCollectionWriter extends Writer {
    protected final Collection<Writer> EMPTY_WRITERS;
    protected final Collection<Writer> writers;

    protected FilterCollectionWriter(Collection<Writer> collection) {
        List list = Collections.EMPTY_LIST;
        this.EMPTY_WRITERS = list;
        this.writers = collection == null ? list : collection;
    }

    protected FilterCollectionWriter(Writer... writerArr) {
        List list = Collections.EMPTY_LIST;
        this.EMPTY_WRITERS = list;
        this.writers = writerArr != null ? Arrays.asList(writerArr) : list;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(final char c) throws IOException {
        return forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda9
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).append(c);
            }
        });
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(final CharSequence charSequence) throws IOException {
        return forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda3
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).append(charSequence);
            }
        });
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(final CharSequence charSequence, final int i, final int i2) throws IOException {
        return forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).append(charSequence, i, i2);
            }
        });
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).close();
            }
        });
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda6
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).flush();
            }
        });
    }

    private FilterCollectionWriter forAllWriters(IOConsumer<Writer> iOConsumer) throws IOExceptionList {
        IOConsumer.forAll(iOConsumer, writers());
        return this;
    }

    @Override // java.io.Writer
    public void write(final char[] cArr) throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).write(cArr);
            }
        });
    }

    @Override // java.io.Writer
    public void write(final char[] cArr, final int i, final int i2) throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda5
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).write(cArr, i, i2);
            }
        });
    }

    @Override // java.io.Writer
    public void write(final int i) throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda8
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).write(i);
            }
        });
    }

    @Override // java.io.Writer
    public void write(final String str) throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda2
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).write(str);
            }
        });
    }

    @Override // java.io.Writer
    public void write(final String str, final int i, final int i2) throws IOException {
        forAllWriters(new IOConsumer() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda7
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((Writer) obj).write(str, i, i2);
            }
        });
    }

    private Stream<Writer> writers() {
        return this.writers.stream().filter(new Predicate() { // from class: org.apache.commons.io.output.FilterCollectionWriter$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((Writer) obj);
            }
        });
    }
}
