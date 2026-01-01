package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.io.input.ObservableInputStream;

@Deprecated
/* loaded from: classes4.dex */
public class MessageDigestCalculatingInputStream extends ObservableInputStream {
    private static final String DEFAULT_ALGORITHM = "MD5";
    private final MessageDigest messageDigest;

    public static class Builder extends ObservableInputStream.AbstractBuilder<Builder> {
        private MessageDigest messageDigest;

        public Builder() {
            try {
                this.messageDigest = MessageDigestCalculatingInputStream.getDefaultMessageDigest();
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // org.apache.commons.io.function.IOSupplier
        public MessageDigestCalculatingInputStream get() throws IOException {
            setObservers(Arrays.asList(new MessageDigestMaintainingObserver(this.messageDigest)));
            return new MessageDigestCalculatingInputStream(this);
        }

        public void setMessageDigest(MessageDigest messageDigest) {
            this.messageDigest = messageDigest;
        }

        public void setMessageDigest(String str) throws NoSuchAlgorithmException {
            this.messageDigest = MessageDigest.getInstance(str);
        }
    }

    public static class MessageDigestMaintainingObserver extends ObservableInputStream.Observer {
        private final MessageDigest messageDigest;

        public MessageDigestMaintainingObserver(MessageDigest messageDigest) {
            this.messageDigest = (MessageDigest) Objects.requireNonNull(messageDigest, "messageDigest");
        }

        @Override // org.apache.commons.io.input.ObservableInputStream.Observer
        public void data(byte[] bArr, int i, int i2) throws IOException {
            this.messageDigest.update(bArr, i, i2);
        }

        @Override // org.apache.commons.io.input.ObservableInputStream.Observer
        public void data(int i) throws IOException {
            this.messageDigest.update((byte) i);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    static MessageDigest getDefaultMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(DEFAULT_ALGORITHM);
    }

    private MessageDigestCalculatingInputStream(Builder builder) throws IOException {
        super(builder);
        this.messageDigest = builder.messageDigest;
    }

    @Deprecated
    public MessageDigestCalculatingInputStream(InputStream inputStream) throws NoSuchAlgorithmException {
        this(inputStream, getDefaultMessageDigest());
    }

    @Deprecated
    public MessageDigestCalculatingInputStream(InputStream inputStream, MessageDigest messageDigest) {
        super(inputStream, new MessageDigestMaintainingObserver(messageDigest));
        this.messageDigest = messageDigest;
    }

    @Deprecated
    public MessageDigestCalculatingInputStream(InputStream inputStream, String str) throws NoSuchAlgorithmException {
        this(inputStream, MessageDigest.getInstance(str));
    }

    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }
}
