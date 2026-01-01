package org.apache.tika.pipes.fetcher;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public interface RangeFetcher extends Fetcher {
    InputStream fetch(String str, long j, long j2, Metadata metadata, ParseContext parseContext) throws TikaException, IOException;

    default InputStream fetch(String str, long j, long j2, Metadata metadata) throws TikaException, IOException {
        return fetch(str, j, j2, metadata, new ParseContext());
    }
}
