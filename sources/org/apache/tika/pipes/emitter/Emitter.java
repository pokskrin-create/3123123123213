package org.apache.tika.pipes.emitter;

import java.io.IOException;
import java.util.List;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public interface Emitter {
    void emit(String str, List<Metadata> list, ParseContext parseContext) throws TikaEmitterException, IOException;

    void emit(List<? extends EmitData> list) throws TikaEmitterException, IOException;

    String getName();
}
