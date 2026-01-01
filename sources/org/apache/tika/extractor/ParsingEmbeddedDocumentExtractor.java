package org.apache.tika.extractor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.tika.exception.CorruptedFileException;
import org.apache.tika.exception.EncryptedDocumentException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.DelegatingParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ParseRecord;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.EmbeddedContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/* loaded from: classes4.dex */
public class ParsingEmbeddedDocumentExtractor implements EmbeddedDocumentExtractor {
    private static final File ABSTRACT_PATH = new File("");
    private static final Parser DELEGATING_PARSER = new DelegatingParser();
    protected final ParseContext context;
    private boolean writeFileNameToContent = true;

    public ParsingEmbeddedDocumentExtractor(ParseContext parseContext) {
        this.context = parseContext;
    }

    @Override // org.apache.tika.extractor.EmbeddedDocumentExtractor
    public boolean shouldParseEmbedded(Metadata metadata) {
        String str;
        DocumentSelector documentSelector = (DocumentSelector) this.context.get(DocumentSelector.class);
        if (documentSelector != null) {
            return documentSelector.select(metadata);
        }
        FilenameFilter filenameFilter = (FilenameFilter) this.context.get(FilenameFilter.class);
        if (filenameFilter == null || (str = metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY)) == null) {
            return true;
        }
        return filenameFilter.accept(ABSTRACT_PATH, str);
    }

    @Override // org.apache.tika.extractor.EmbeddedDocumentExtractor
    public void parseEmbedded(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, boolean z) throws SAXException, IOException {
        TemporaryResources temporaryResources;
        Object openContainer;
        if (z) {
            AttributesImpl attributesImpl = new AttributesImpl();
            attributesImpl.addAttribute("", "class", "class", "CDATA", "package-entry");
            contentHandler.startElement(XHTMLContentHandler.XHTML, "div", "div", attributesImpl);
        }
        String str = metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY);
        if (this.writeFileNameToContent && str != null && str.length() > 0 && z) {
            contentHandler.startElement(XHTMLContentHandler.XHTML, "h1", "h1", new AttributesImpl());
            char[] charArray = str.toCharArray();
            contentHandler.characters(charArray, 0, charArray.length);
            contentHandler.endElement(XHTMLContentHandler.XHTML, "h1", "h1");
        }
        try {
            temporaryResources = new TemporaryResources();
        } catch (CorruptedFileException e) {
            throw new IOException(e);
        } catch (EncryptedDocumentException e2) {
            recordException(e2, this.context);
        } catch (TikaException e3) {
            recordException(e3, this.context);
        }
        try {
            TikaInputStream tikaInputStream = TikaInputStream.get(CloseShieldInputStream.wrap(inputStream), temporaryResources, metadata);
            if ((inputStream instanceof TikaInputStream) && (openContainer = ((TikaInputStream) inputStream).getOpenContainer()) != null) {
                tikaInputStream.setOpenContainer(openContainer);
            }
            DELEGATING_PARSER.parse(tikaInputStream, new EmbeddedContentHandler(new BodyContentHandler(contentHandler)), metadata, this.context);
            temporaryResources.close();
            if (z) {
                contentHandler.endElement(XHTMLContentHandler.XHTML, "div", "div");
            }
        } finally {
        }
    }

    void recordException(Exception exc, ParseContext parseContext) {
        ParseRecord parseRecord = (ParseRecord) parseContext.get(ParseRecord.class);
        if (parseRecord == null) {
            return;
        }
        parseRecord.addException(exc);
    }

    public Parser getDelegatingParser() {
        return DELEGATING_PARSER;
    }

    public void setWriteFileNameToContent(boolean z) {
        this.writeFileNameToContent = z;
    }

    public boolean isWriteFileNameToContent() {
        return this.writeFileNameToContent;
    }
}
