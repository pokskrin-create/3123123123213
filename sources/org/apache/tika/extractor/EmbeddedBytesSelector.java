package org.apache.tika.extractor;

import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public interface EmbeddedBytesSelector {
    public static final EmbeddedBytesSelector ACCEPT_ALL = new AcceptAll();

    public static class AcceptAll implements EmbeddedBytesSelector {
        @Override // org.apache.tika.extractor.EmbeddedBytesSelector
        public boolean select(Metadata metadata) {
            return true;
        }
    }

    boolean select(Metadata metadata);
}
