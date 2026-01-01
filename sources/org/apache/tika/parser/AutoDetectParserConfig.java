package org.apache.tika.parser;

import java.io.IOException;
import java.io.Serializable;
import org.apache.tika.config.ConfigBase;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.extractor.EmbeddedDocumentExtractorFactory;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.writefilter.MetadataWriteFilterFactory;
import org.apache.tika.parser.DigestingParser;
import org.apache.tika.sax.ContentHandlerDecoratorFactory;
import org.w3c.dom.Element;
import org.xml.sax.ContentHandler;

/* loaded from: classes4.dex */
public class AutoDetectParserConfig extends ConfigBase implements Serializable {
    private ContentHandlerDecoratorFactory contentHandlerDecoratorFactory;
    private DigestingParser.DigesterFactory digesterFactory;
    private EmbeddedDocumentExtractorFactory embeddedDocumentExtractorFactory;
    private Long maximumCompressionRatio;
    private Integer maximumDepth;
    private Integer maximumPackageEntryDepth;
    private MetadataWriteFilterFactory metadataWriteFilterFactory;
    private Long outputThreshold;
    private Long spoolToDisk;
    private boolean throwOnZeroBytes;
    private static ContentHandlerDecoratorFactory NOOP_CONTENT_HANDLER_DECORATOR_FACTORY = new ContentHandlerDecoratorFactory() { // from class: org.apache.tika.parser.AutoDetectParserConfig.1
        @Override // org.apache.tika.sax.ContentHandlerDecoratorFactory
        public ContentHandler decorate(ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) {
            return contentHandler;
        }
    };
    public static AutoDetectParserConfig DEFAULT = new AutoDetectParserConfig();

    public static AutoDetectParserConfig load(Element element) throws IOException, TikaConfigException {
        return (AutoDetectParserConfig) buildSingle("autoDetectParserConfig", AutoDetectParserConfig.class, element, DEFAULT);
    }

    public AutoDetectParserConfig(Long l, Long l2, Long l3, Integer num, Integer num2) {
        this.metadataWriteFilterFactory = null;
        this.embeddedDocumentExtractorFactory = null;
        this.contentHandlerDecoratorFactory = NOOP_CONTENT_HANDLER_DECORATOR_FACTORY;
        this.digesterFactory = null;
        this.throwOnZeroBytes = true;
        this.spoolToDisk = l;
        this.outputThreshold = l2;
        this.maximumCompressionRatio = l3;
        this.maximumDepth = num;
        this.maximumPackageEntryDepth = num2;
    }

    public AutoDetectParserConfig() {
        this.spoolToDisk = null;
        this.outputThreshold = null;
        this.maximumCompressionRatio = null;
        this.maximumDepth = null;
        this.maximumPackageEntryDepth = null;
        this.metadataWriteFilterFactory = null;
        this.embeddedDocumentExtractorFactory = null;
        this.contentHandlerDecoratorFactory = NOOP_CONTENT_HANDLER_DECORATOR_FACTORY;
        this.digesterFactory = null;
        this.throwOnZeroBytes = true;
    }

    public Long getSpoolToDisk() {
        return this.spoolToDisk;
    }

    public void setSpoolToDisk(long j) {
        this.spoolToDisk = Long.valueOf(j);
    }

    public Long getOutputThreshold() {
        return this.outputThreshold;
    }

    public void setOutputThreshold(long j) {
        this.outputThreshold = Long.valueOf(j);
    }

    public Long getMaximumCompressionRatio() {
        return this.maximumCompressionRatio;
    }

    public void setMaximumCompressionRatio(long j) {
        this.maximumCompressionRatio = Long.valueOf(j);
    }

    public Integer getMaximumDepth() {
        return this.maximumDepth;
    }

    public void setMaximumDepth(int i) {
        this.maximumDepth = Integer.valueOf(i);
    }

    public Integer getMaximumPackageEntryDepth() {
        return this.maximumPackageEntryDepth;
    }

    public void setMaximumPackageEntryDepth(int i) {
        this.maximumPackageEntryDepth = Integer.valueOf(i);
    }

    public MetadataWriteFilterFactory getMetadataWriteFilterFactory() {
        return this.metadataWriteFilterFactory;
    }

    public void setMetadataWriteFilterFactory(MetadataWriteFilterFactory metadataWriteFilterFactory) {
        this.metadataWriteFilterFactory = metadataWriteFilterFactory;
    }

    public void setEmbeddedDocumentExtractorFactory(EmbeddedDocumentExtractorFactory embeddedDocumentExtractorFactory) {
        this.embeddedDocumentExtractorFactory = embeddedDocumentExtractorFactory;
    }

    public EmbeddedDocumentExtractorFactory getEmbeddedDocumentExtractorFactory() {
        return this.embeddedDocumentExtractorFactory;
    }

    public void setContentHandlerDecoratorFactory(ContentHandlerDecoratorFactory contentHandlerDecoratorFactory) {
        this.contentHandlerDecoratorFactory = contentHandlerDecoratorFactory;
    }

    public ContentHandlerDecoratorFactory getContentHandlerDecoratorFactory() {
        return this.contentHandlerDecoratorFactory;
    }

    public void setDigesterFactory(DigestingParser.DigesterFactory digesterFactory) {
        this.digesterFactory = digesterFactory;
    }

    public DigestingParser.DigesterFactory getDigesterFactory() {
        return this.digesterFactory;
    }

    public void setThrowOnZeroBytes(boolean z) {
        this.throwOnZeroBytes = z;
    }

    public boolean getThrowOnZeroBytes() {
        return this.throwOnZeroBytes;
    }

    public String toString() {
        return "AutoDetectParserConfig{spoolToDisk=" + this.spoolToDisk + ", outputThreshold=" + this.outputThreshold + ", maximumCompressionRatio=" + this.maximumCompressionRatio + ", maximumDepth=" + this.maximumDepth + ", maximumPackageEntryDepth=" + this.maximumPackageEntryDepth + ", metadataWriteFilterFactory=" + this.metadataWriteFilterFactory + ", embeddedDocumentExtractorFactory=" + this.embeddedDocumentExtractorFactory + ", contentHandlerDecoratorFactory=" + this.contentHandlerDecoratorFactory + ", digesterFactory=" + this.digesterFactory + ", throwOnZeroBytes=" + this.throwOnZeroBytes + "}";
    }
}
