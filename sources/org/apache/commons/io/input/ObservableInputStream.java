package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.build.AbstractStreamBuilder;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.function.IOIntConsumer;
import org.apache.commons.io.input.ObservableInputStream;
import org.apache.commons.io.input.ProxyInputStream;

/* loaded from: classes4.dex */
public class ObservableInputStream extends ProxyInputStream {
    private final List<Observer> observers;

    public static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends ProxyInputStream.AbstractBuilder<ObservableInputStream, T> {
        private List<Observer> observers;

        @Override // org.apache.commons.io.input.ProxyInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ IOIntConsumer getAfterRead() {
            return super.getAfterRead();
        }

        @Override // org.apache.commons.io.input.ProxyInputStream.AbstractBuilder
        public /* bridge */ /* synthetic */ AbstractStreamBuilder setAfterRead(IOIntConsumer iOIntConsumer) {
            return super.setAfterRead(iOIntConsumer);
        }

        public void setObservers(List<Observer> list) {
            this.observers = list;
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {
        @Override // org.apache.commons.io.function.IOSupplier
        public ObservableInputStream get() throws IOException {
            return new ObservableInputStream(this);
        }
    }

    public static abstract class Observer {
        public void closed() throws IOException {
        }

        public void data(int i) throws IOException {
        }

        public void data(byte[] bArr, int i, int i2) throws IOException {
        }

        public void finished() throws IOException {
        }

        public void error(IOException iOException) throws IOException {
            throw iOException;
        }
    }

    ObservableInputStream(AbstractBuilder abstractBuilder) throws IOException {
        super(abstractBuilder);
        this.observers = abstractBuilder.observers;
    }

    public ObservableInputStream(InputStream inputStream) {
        this(inputStream, new ArrayList());
    }

    private ObservableInputStream(InputStream inputStream, List<Observer> list) {
        super(inputStream);
        this.observers = list;
    }

    public ObservableInputStream(InputStream inputStream, Observer... observerArr) {
        this(inputStream, (List<Observer>) Arrays.asList(observerArr));
    }

    public void add(Observer observer) {
        this.observers.add(observer);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            super.close();
            e = null;
        } catch (IOException e) {
            e = e;
        }
        if (e == null) {
            noteClosed();
        } else {
            noteError(e);
        }
    }

    public void consume() throws IOException {
        IOUtils.consume(this);
    }

    private void forEachObserver(IOConsumer<Observer> iOConsumer) throws IOException {
        IOConsumer.forAll(iOConsumer, this.observers);
    }

    public List<Observer> getObservers() {
        return new ArrayList(this.observers);
    }

    protected void noteClosed() throws IOException {
        forEachObserver(new IOConsumer() { // from class: org.apache.commons.io.input.ObservableInputStream$$ExternalSyntheticLambda2
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((ObservableInputStream.Observer) obj).closed();
            }
        });
    }

    protected void noteDataByte(final int i) throws IOException {
        forEachObserver(new IOConsumer() { // from class: org.apache.commons.io.input.ObservableInputStream$$ExternalSyntheticLambda0
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((ObservableInputStream.Observer) obj).data(i);
            }
        });
    }

    protected void noteDataBytes(final byte[] bArr, final int i, final int i2) throws IOException {
        forEachObserver(new IOConsumer() { // from class: org.apache.commons.io.input.ObservableInputStream$$ExternalSyntheticLambda3
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((ObservableInputStream.Observer) obj).data(bArr, i, i2);
            }
        });
    }

    protected void noteError(final IOException iOException) throws IOException {
        forEachObserver(new IOConsumer() { // from class: org.apache.commons.io.input.ObservableInputStream$$ExternalSyntheticLambda4
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((ObservableInputStream.Observer) obj).error(iOException);
            }
        });
    }

    protected void noteFinished() throws IOException {
        forEachObserver(new IOConsumer() { // from class: org.apache.commons.io.input.ObservableInputStream$$ExternalSyntheticLambda1
            @Override // org.apache.commons.io.function.IOConsumer
            public final void accept(Object obj) throws IOException {
                ((ObservableInputStream.Observer) obj).finished();
            }
        });
    }

    private void notify(byte[] bArr, int i, int i2, IOException iOException) throws IOException {
        if (iOException != null) {
            noteError(iOException);
            throw iOException;
        }
        if (i2 == -1) {
            noteFinished();
        } else if (i2 > 0) {
            noteDataBytes(bArr, i, i2);
        }
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i;
        try {
            i = super.read();
            e = null;
        } catch (IOException e) {
            e = e;
            i = 0;
        }
        if (e != null) {
            noteError(e);
            throw e;
        }
        if (i == -1) {
            noteFinished();
        } else {
            noteDataByte(i);
        }
        return i;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        int i;
        try {
            i = super.read(bArr);
            e = null;
        } catch (IOException e) {
            e = e;
            i = 0;
        }
        notify(bArr, 0, i, e);
        return i;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        try {
            i3 = super.read(bArr, i, i2);
            e = null;
        } catch (IOException e) {
            e = e;
            i3 = 0;
        }
        notify(bArr, i, i3, e);
        return i3;
    }

    public void remove(Observer observer) {
        this.observers.remove(observer);
    }

    public void removeAllObservers() {
        this.observers.clear();
    }
}
