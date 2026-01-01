package org.apache.tika.parser.external;

import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.CompositeParser;

/* loaded from: classes4.dex */
public class CompositeExternalParser extends CompositeParser {
    private static final long serialVersionUID = 6962436916649024024L;

    public CompositeExternalParser() throws TikaException, IOException {
        this(new MediaTypeRegistry());
    }

    public CompositeExternalParser(MediaTypeRegistry mediaTypeRegistry) throws TikaException, IOException {
        super(mediaTypeRegistry, ExternalParsersFactory.create());
    }
}
