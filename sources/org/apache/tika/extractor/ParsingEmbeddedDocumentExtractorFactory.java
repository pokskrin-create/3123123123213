package org.apache.tika.extractor;

import org.apache.tika.config.Field;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class ParsingEmbeddedDocumentExtractorFactory implements EmbeddedDocumentExtractorFactory {
    private boolean writeFileNameToContent = true;

    @Field
    public void setWriteFileNameToContent(boolean z) {
        this.writeFileNameToContent = z;
    }

    @Override // org.apache.tika.extractor.EmbeddedDocumentExtractorFactory
    public EmbeddedDocumentExtractor newInstance(Metadata metadata, ParseContext parseContext) {
        ParsingEmbeddedDocumentExtractor parsingEmbeddedDocumentExtractor = new ParsingEmbeddedDocumentExtractor(parseContext);
        parsingEmbeddedDocumentExtractor.setWriteFileNameToContent(this.writeFileNameToContent);
        return parsingEmbeddedDocumentExtractor;
    }
}
