package org.apache.tika.pipes.fetcher.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.pipes.fetcher.AbstractFetcher;

/* loaded from: classes4.dex */
public class UrlFetcher extends AbstractFetcher {
    @Override // org.apache.tika.pipes.fetcher.Fetcher
    public InputStream fetch(String str, Metadata metadata, ParseContext parseContext) throws TikaException, IOException {
        if (str.contains("\u0000")) {
            throw new IllegalArgumentException("URL must not contain \u0000. Please review the life decisions that led you to requesting a URL with this character in it.");
        }
        if (str.toLowerCase(Locale.US).trim().startsWith("file:")) {
            throw new IllegalArgumentException("The UrlFetcher does not fetch from file shares; please use the FileSystemFetcher");
        }
        return TikaInputStream.get(new URL(str), metadata);
    }
}
