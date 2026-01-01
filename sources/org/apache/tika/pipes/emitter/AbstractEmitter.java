package org.apache.tika.pipes.emitter;

import java.io.IOException;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AbstractEmitter implements Emitter {
    private String name;

    @Override // org.apache.tika.pipes.emitter.Emitter
    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    @Override // org.apache.tika.pipes.emitter.Emitter
    public void emit(List<? extends EmitData> list) throws TikaEmitterException, IOException {
        for (EmitData emitData : list) {
            emit(emitData.getEmitKey().getEmitKey(), emitData.getMetadataList(), emitData.getParseContext());
        }
    }
}
