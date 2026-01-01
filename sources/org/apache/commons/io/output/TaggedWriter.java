package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.UUID;
import org.apache.commons.io.TaggedIOException;

/* loaded from: classes4.dex */
public class TaggedWriter extends ProxyWriter {
    private final Serializable tag;

    public TaggedWriter(Writer writer) {
        super(writer);
        this.tag = UUID.randomUUID();
    }

    @Override // org.apache.commons.io.output.ProxyWriter
    protected void handleIOException(IOException iOException) throws IOException {
        throw new TaggedIOException(iOException, this.tag);
    }

    public boolean isCauseOf(Exception exc) {
        return TaggedIOException.isTaggedWith(exc, this.tag);
    }

    public void throwIfCauseOf(Exception exc) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(exc, this.tag);
    }
}
