package org.apache.tika.pipes.fetcher;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class EmptyFetcher implements Fetcher {
    @Override // org.apache.tika.pipes.fetcher.Fetcher
    public InputStream fetch(String str, Metadata metadata, ParseContext parseContext) throws TikaException, IOException {
        return null;
    }

    @Override // org.apache.tika.pipes.fetcher.Fetcher
    public String getName() {
        return "empty";
    }
}
