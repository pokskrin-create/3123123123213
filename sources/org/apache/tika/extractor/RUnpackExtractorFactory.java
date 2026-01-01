package org.apache.tika.extractor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

/* loaded from: classes4.dex */
public class RUnpackExtractorFactory implements EmbeddedDocumentByteStoreExtractorFactory {
    public static long DEFAULT_MAX_EMBEDDED_BYTES_FOR_EXTRACTION = 10737418240L;
    private boolean writeFileNameToContent = true;
    private Set<String> embeddedBytesIncludeMimeTypes = Collections.EMPTY_SET;
    private Set<String> embeddedBytesExcludeMimeTypes = Collections.EMPTY_SET;
    private Set<String> embeddedBytesIncludeEmbeddedResourceTypes = Collections.EMPTY_SET;
    private Set<String> embeddedBytesExcludeEmbeddedResourceTypes = Collections.EMPTY_SET;
    private long maxEmbeddedBytesForExtraction = DEFAULT_MAX_EMBEDDED_BYTES_FOR_EXTRACTION;

    @Field
    public void setWriteFileNameToContent(boolean z) {
        this.writeFileNameToContent = z;
    }

    @Field
    public void setEmbeddedBytesIncludeMimeTypes(List<String> list) {
        HashSet hashSet = new HashSet();
        this.embeddedBytesIncludeMimeTypes = hashSet;
        hashSet.addAll(list);
    }

    @Field
    public void setEmbeddedBytesExcludeMimeTypes(List<String> list) {
        HashSet hashSet = new HashSet();
        this.embeddedBytesExcludeMimeTypes = hashSet;
        hashSet.addAll(list);
    }

    @Field
    public void setEmbeddedBytesIncludeEmbeddedResourceTypes(List<String> list) {
        HashSet hashSet = new HashSet();
        this.embeddedBytesIncludeEmbeddedResourceTypes = hashSet;
        hashSet.addAll(list);
    }

    @Field
    public void setEmbeddedBytesExcludeEmbeddedResourceTypes(List<String> list) {
        HashSet hashSet = new HashSet();
        this.embeddedBytesExcludeEmbeddedResourceTypes = hashSet;
        hashSet.addAll(list);
    }

    @Field
    public void setMaxEmbeddedBytesForExtraction(long j) throws TikaConfigException {
        if (j < 0) {
            throw new TikaConfigException("maxEmbeddedBytesForExtraction must be >= 0");
        }
        this.maxEmbeddedBytesForExtraction = j;
    }

    @Override // org.apache.tika.extractor.EmbeddedDocumentExtractorFactory
    public EmbeddedDocumentExtractor newInstance(Metadata metadata, ParseContext parseContext) {
        RUnpackExtractor rUnpackExtractor = new RUnpackExtractor(parseContext, this.maxEmbeddedBytesForExtraction);
        rUnpackExtractor.setWriteFileNameToContent(this.writeFileNameToContent);
        rUnpackExtractor.setEmbeddedBytesSelector(createEmbeddedBytesSelector());
        return rUnpackExtractor;
    }

    private EmbeddedBytesSelector createEmbeddedBytesSelector() {
        if (this.embeddedBytesIncludeMimeTypes.size() == 0 && this.embeddedBytesExcludeMimeTypes.size() == 0 && this.embeddedBytesIncludeEmbeddedResourceTypes.size() == 0 && this.embeddedBytesExcludeEmbeddedResourceTypes.size() == 0) {
            return EmbeddedBytesSelector.ACCEPT_ALL;
        }
        return new BasicEmbeddedBytesSelector(this.embeddedBytesIncludeMimeTypes, this.embeddedBytesExcludeMimeTypes, this.embeddedBytesIncludeEmbeddedResourceTypes, this.embeddedBytesExcludeEmbeddedResourceTypes);
    }
}
