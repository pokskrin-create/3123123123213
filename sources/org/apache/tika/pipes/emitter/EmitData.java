package org.apache.tika.pipes.emitter;

import java.io.Serializable;
import java.util.List;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class EmitData implements Serializable {
    private static final long serialVersionUID = -3861669115439125268L;
    private final String containerStackTrace;
    private final EmitKey emitKey;
    private final List<Metadata> metadataList;
    private ParseContext parseContext;

    public EmitData(EmitKey emitKey, List<Metadata> list) {
        this(emitKey, list, "");
    }

    public EmitData(EmitKey emitKey, List<Metadata> list, String str) {
        this(emitKey, list, str, new ParseContext());
    }

    public EmitData(EmitKey emitKey, List<Metadata> list, String str, ParseContext parseContext) {
        this.parseContext = null;
        this.emitKey = emitKey;
        this.metadataList = list;
        this.containerStackTrace = str == null ? "" : str;
        this.parseContext = parseContext;
    }

    public EmitKey getEmitKey() {
        return this.emitKey;
    }

    public List<Metadata> getMetadataList() {
        return this.metadataList;
    }

    public String getContainerStackTrace() {
        return this.containerStackTrace;
    }

    public long getEstimatedSizeBytes() {
        return estimateSizeInBytes(getEmitKey().getEmitKey(), getMetadataList(), this.containerStackTrace);
    }

    public void setParseContext(ParseContext parseContext) {
        this.parseContext = parseContext;
    }

    public ParseContext getParseContext() {
        return this.parseContext;
    }

    private static long estimateSizeInBytes(String str, List<Metadata> list, String str2) {
        long length = (str.length() * 2) + 36 + (str2.length() * 2) + 36;
        for (Metadata metadata : list) {
            for (String str3 : metadata.names()) {
                length += (r5.length() * 2) + 36;
                for (int i = 0; i < metadata.getValues(str3).length; i++) {
                    length += (r5[i].length() * 2) + 36;
                }
            }
        }
        return length;
    }

    public String toString() {
        return "EmitData{emitKey=" + this.emitKey + ", metadataList=" + this.metadataList + ", containerStackTrace='" + this.containerStackTrace + "'}";
    }
}
