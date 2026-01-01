package org.apache.tika.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.tika.exception.CorruptedFileException;
import org.apache.tika.exception.EncryptedDocumentException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.WriteLimitReachedException;
import org.apache.tika.exception.ZeroByteFileException;
import org.apache.tika.extractor.ParentContentHandler;
import org.apache.tika.io.FilenameUtils;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.apache.tika.sax.AbstractRecursiveParserWrapperHandler;
import org.apache.tika.sax.SecureContentHandler;
import org.apache.tika.utils.ParserUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class RecursiveParserWrapper extends ParserDecorator {
    private static final long serialVersionUID = 9086536568120690938L;
    private final boolean catchEmbeddedExceptions;
    private final boolean inlineContent;

    public RecursiveParserWrapper(Parser parser) {
        this(parser, true);
    }

    public RecursiveParserWrapper(Parser parser, boolean z) {
        super(parser);
        this.inlineContent = false;
        this.catchEmbeddedExceptions = z;
    }

    @Override // org.apache.tika.parser.ParserDecorator, org.apache.tika.parser.Parser
    public Set<MediaType> getSupportedTypes(ParseContext parseContext) {
        return getWrappedParser().getSupportedTypes(parseContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0054  */
    @Override // org.apache.tika.parser.ParserDecorator, org.apache.tika.parser.Parser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.io.InputStream r17, org.xml.sax.ContentHandler r18, org.apache.tika.metadata.Metadata r19, org.apache.tika.parser.ParseContext r20) throws org.apache.tika.exception.TikaException, org.xml.sax.SAXException, java.io.IOException {
        /*
            r16 = this;
            r0 = r18
            r1 = r19
            r7 = r20
            boolean r2 = r0 instanceof org.apache.tika.sax.AbstractRecursiveParserWrapperHandler
            if (r2 == 0) goto Le1
            org.apache.tika.parser.RecursiveParserWrapper$ParserState r13 = new org.apache.tika.parser.RecursiveParserWrapper$ParserState
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r0 = (org.apache.tika.sax.AbstractRecursiveParserWrapperHandler) r0
            r15 = 0
            r13.<init>(r0)
            org.apache.tika.parser.RecursiveParserWrapper$EmbeddedParserDecorator r8 = new org.apache.tika.parser.RecursiveParserWrapper$EmbeddedParserDecorator
            org.apache.tika.parser.Parser r10 = r16.getWrappedParser()
            java.lang.String r12 = "/"
            r14 = 0
            java.lang.String r11 = "/"
            r9 = r16
            r8.<init>(r10, r11, r12, r13)
            java.lang.Class<org.apache.tika.parser.Parser> r3 = org.apache.tika.parser.Parser.class
            r7.set(r3, r8)
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r3 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            org.xml.sax.ContentHandler r3 = r3.getNewContentHandler()
            long r8 = java.lang.System.currentTimeMillis()
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r4 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            r4.startDocument()
            org.apache.tika.io.TemporaryResources r10 = new org.apache.tika.io.TemporaryResources
            r10.<init>()
            if (r2 == 0) goto L54
            org.apache.tika.sax.ContentHandlerFactory r0 = r0.getContentHandlerFactory()
            boolean r2 = r0 instanceof org.apache.tika.sax.WriteLimiter
            if (r2 == 0) goto L54
            org.apache.tika.sax.WriteLimiter r0 = (org.apache.tika.sax.WriteLimiter) r0
            int r2 = r0.getWriteLimit()
            boolean r0 = r0.isThrowOnWriteLimitReached()
            goto L56
        L54:
            r2 = -1
            r0 = 1
        L56:
            r6 = r0
            r0 = r17
            org.apache.tika.io.TikaInputStream r4 = org.apache.tika.io.TikaInputStream.get(r0, r10, r1)     // Catch: java.lang.Throwable -> L99
            org.apache.tika.parser.RecursiveParserWrapper$RecursivelySecureContentHandler r0 = new org.apache.tika.parser.RecursiveParserWrapper$RecursivelySecureContentHandler     // Catch: java.lang.Throwable -> L99
            org.apache.tika.parser.RecursiveParserWrapper$SecureHandlerCounter r5 = new org.apache.tika.parser.RecursiveParserWrapper$SecureHandlerCounter     // Catch: java.lang.Throwable -> L99
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L99
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L99
            java.lang.Class<org.apache.tika.parser.RecursiveParserWrapper$RecursivelySecureContentHandler> r0 = org.apache.tika.parser.RecursiveParserWrapper.RecursivelySecureContentHandler.class
            r7.set(r0, r2)     // Catch: java.lang.Throwable -> L99
            org.apache.tika.parser.Parser r0 = r16.getWrappedParser()     // Catch: java.lang.Throwable -> L99
            r0.parse(r4, r2, r1, r7)     // Catch: java.lang.Throwable -> L99
        L74:
            r10.dispose()
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r8
            org.apache.tika.metadata.Property r0 = org.apache.tika.metadata.TikaCoreProperties.PARSE_TIME_MILLIS
            java.lang.String r2 = java.lang.Long.toString(r4)
            r1.set(r0, r2)
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r0 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            r0.endDocument(r3, r1)
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r0 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            r0.endDocument()
            java.lang.Class<org.apache.tika.parser.RecursiveParserWrapper$RecursivelySecureContentHandler> r0 = org.apache.tika.parser.RecursiveParserWrapper.RecursivelySecureContentHandler.class
            r7.set(r0, r15)
            return
        L99:
            r0 = move-exception
            boolean r2 = r0 instanceof org.apache.tika.exception.EncryptedDocumentException     // Catch: java.lang.Throwable -> Lbb
            java.lang.String r4 = "true"
            if (r2 == 0) goto La5
            org.apache.tika.metadata.Property r2 = org.apache.tika.metadata.TikaCoreProperties.IS_ENCRYPTED     // Catch: java.lang.Throwable -> Lbb
            r1.set(r2, r4)     // Catch: java.lang.Throwable -> Lbb
        La5:
            boolean r2 = org.apache.tika.exception.WriteLimitReachedException.isWriteLimitReached(r0)     // Catch: java.lang.Throwable -> Lbb
            if (r2 == 0) goto Lb1
            org.apache.tika.metadata.Property r0 = org.apache.tika.metadata.TikaCoreProperties.WRITE_LIMIT_REACHED     // Catch: java.lang.Throwable -> Lbb
            r1.set(r0, r4)     // Catch: java.lang.Throwable -> Lbb
            goto L74
        Lb1:
            java.lang.String r2 = org.apache.tika.utils.ExceptionUtils.getFilteredStackTrace(r0)     // Catch: java.lang.Throwable -> Lbb
            org.apache.tika.metadata.Property r4 = org.apache.tika.metadata.TikaCoreProperties.CONTAINER_EXCEPTION     // Catch: java.lang.Throwable -> Lbb
            r1.add(r4, r2)     // Catch: java.lang.Throwable -> Lbb
            throw r0     // Catch: java.lang.Throwable -> Lbb
        Lbb:
            r0 = move-exception
            r10.dispose()
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r8
            org.apache.tika.metadata.Property r2 = org.apache.tika.metadata.TikaCoreProperties.PARSE_TIME_MILLIS
            java.lang.String r4 = java.lang.Long.toString(r4)
            r1.set(r2, r4)
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r2 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            r2.endDocument(r3, r1)
            org.apache.tika.sax.AbstractRecursiveParserWrapperHandler r1 = org.apache.tika.parser.RecursiveParserWrapper.ParserState.m2280$$Nest$fgetrecursiveParserWrapperHandler(r13)
            r1.endDocument()
            java.lang.Class<org.apache.tika.parser.RecursiveParserWrapper$RecursivelySecureContentHandler> r1 = org.apache.tika.parser.RecursiveParserWrapper.RecursivelySecureContentHandler.class
            r7.set(r1, r15)
            throw r0
        Le1:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "ContentHandler must implement RecursiveParserWrapperHandler"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.parser.RecursiveParserWrapper.parse(java.io.InputStream, org.xml.sax.ContentHandler, org.apache.tika.metadata.Metadata, org.apache.tika.parser.ParseContext):void");
    }

    public static String getResourceName(Metadata metadata, AtomicInteger atomicInteger) {
        String str;
        if (metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY) != null) {
            str = metadata.get(TikaCoreProperties.RESOURCE_NAME_KEY);
        } else if (metadata.get(TikaCoreProperties.EMBEDDED_RELATIONSHIP_ID) != null) {
            str = metadata.get(TikaCoreProperties.EMBEDDED_RELATIONSHIP_ID);
        } else if (metadata.get(TikaCoreProperties.VERSION_NUMBER) != null) {
            str = "version-number-" + metadata.get(TikaCoreProperties.VERSION_NUMBER);
        } else {
            str = "embedded-" + atomicInteger.incrementAndGet();
        }
        return FilenameUtils.getName(str);
    }

    private class EmbeddedParserDecorator extends StatefulParser {
        private static final long serialVersionUID = 207648200464263337L;
        private String embeddedIdPath;
        private String location;
        private final ParserState parserState;

        private EmbeddedParserDecorator(Parser parser, String str, String str2, ParserState parserState) {
            super(parser);
            this.embeddedIdPath = null;
            this.location = str;
            if (!str.endsWith("/")) {
                this.location = this.location + "/";
            }
            this.embeddedIdPath = str2;
            this.parserState = parserState;
        }

        @Override // org.apache.tika.parser.ParserDecorator, org.apache.tika.parser.Parser
        public void parse(InputStream inputStream, ContentHandler contentHandler, Metadata metadata, ParseContext parseContext) throws TikaException, SAXException, IOException {
            String str;
            TemporaryResources temporaryResources;
            TikaInputStream tikaInputStream;
            if (this.parserState.recursiveParserWrapperHandler.hasHitMaximumEmbeddedResources()) {
                return;
            }
            String resourceName = RecursiveParserWrapper.getResourceName(metadata, this.parserState.unknownCount);
            String str2 = this.location + resourceName;
            metadata.add(TikaCoreProperties.EMBEDDED_RESOURCE_PATH, str2);
            if (this.embeddedIdPath.equals("/")) {
                String str3 = this.embeddedIdPath;
                ParserState parserState = this.parserState;
                int i = parserState.embeddedCount + 1;
                parserState.embeddedCount = i;
                str = str3 + i;
            } else {
                String str4 = this.embeddedIdPath;
                ParserState parserState2 = this.parserState;
                int i2 = parserState2.embeddedCount + 1;
                parserState2.embeddedCount = i2;
                str = str4 + "/" + i2;
            }
            String str5 = str;
            metadata.add(TikaCoreProperties.EMBEDDED_ID_PATH, str5);
            metadata.set(TikaCoreProperties.EMBEDDED_ID, this.parserState.embeddedCount);
            ContentHandler newContentHandler = this.parserState.recursiveParserWrapperHandler.getNewContentHandler();
            this.parserState.recursiveParserWrapperHandler.startEmbeddedDocument(newContentHandler, metadata);
            Parser parser = (Parser) parseContext.get(Parser.class);
            parseContext.set(Parser.class, RecursiveParserWrapper.this.new EmbeddedParserDecorator(getWrappedParser(), str2, str5, this.parserState));
            long jCurrentTimeMillis = System.currentTimeMillis();
            RecursivelySecureContentHandler recursivelySecureContentHandler = (RecursivelySecureContentHandler) parseContext.get(RecursivelySecureContentHandler.class);
            ParentContentHandler parentContentHandler = (ParentContentHandler) parseContext.get(ParentContentHandler.class);
            parseContext.set(ParentContentHandler.class, new ParentContentHandler(recursivelySecureContentHandler));
            TikaInputStream tikaInputStreamCast = TikaInputStream.cast(inputStream);
            if (tikaInputStreamCast == null) {
                TemporaryResources temporaryResources2 = new TemporaryResources();
                tikaInputStream = TikaInputStream.get(CloseShieldInputStream.wrap(inputStream), temporaryResources2, metadata);
                temporaryResources = temporaryResources2;
            } else {
                temporaryResources = null;
                tikaInputStream = tikaInputStreamCast;
            }
            try {
                try {
                    try {
                        try {
                            super.parse(inputStream, new RecursivelySecureContentHandler(newContentHandler, tikaInputStream, recursivelySecureContentHandler.handlerCounter, recursivelySecureContentHandler.throwOnWriteLimitReached, parseContext), metadata, parseContext);
                            parseContext.set(Parser.class, parser);
                            parseContext.set(RecursivelySecureContentHandler.class, recursivelySecureContentHandler);
                            parseContext.set(ParentContentHandler.class, parentContentHandler);
                            metadata.set(TikaCoreProperties.PARSE_TIME_MILLIS, Long.toString(System.currentTimeMillis() - jCurrentTimeMillis));
                            this.parserState.recursiveParserWrapperHandler.endEmbeddedDocument(newContentHandler, metadata);
                            if (temporaryResources != null) {
                                tikaInputStream.close();
                            }
                        } catch (CorruptedFileException e) {
                            throw e;
                        }
                    } catch (SAXException e2) {
                        if (WriteLimitReachedException.isWriteLimitReached(e2)) {
                            metadata.add(TikaCoreProperties.WRITE_LIMIT_REACHED, "true");
                            throw e2;
                        }
                        if (!RecursiveParserWrapper.this.catchEmbeddedExceptions) {
                            throw e2;
                        }
                        ParserUtils.recordParserFailure(this, e2, metadata);
                        parseContext.set(Parser.class, parser);
                        parseContext.set(RecursivelySecureContentHandler.class, recursivelySecureContentHandler);
                        parseContext.set(ParentContentHandler.class, parentContentHandler);
                        metadata.set(TikaCoreProperties.PARSE_TIME_MILLIS, Long.toString(System.currentTimeMillis() - jCurrentTimeMillis));
                        this.parserState.recursiveParserWrapperHandler.endEmbeddedDocument(newContentHandler, metadata);
                        if (temporaryResources == null) {
                            return;
                        }
                        tikaInputStream.close();
                    }
                } catch (TikaException e3) {
                    if (e3 instanceof EncryptedDocumentException) {
                        metadata.set(TikaCoreProperties.IS_ENCRYPTED, true);
                    }
                    if (parseContext.get(ZeroByteFileException.IgnoreZeroByteFileException.class) == null || !(e3 instanceof ZeroByteFileException)) {
                        if (!RecursiveParserWrapper.this.catchEmbeddedExceptions) {
                            throw e3;
                        }
                        ParserUtils.recordParserFailure(this, e3, metadata);
                    }
                    parseContext.set(Parser.class, parser);
                    parseContext.set(RecursivelySecureContentHandler.class, recursivelySecureContentHandler);
                    parseContext.set(ParentContentHandler.class, parentContentHandler);
                    metadata.set(TikaCoreProperties.PARSE_TIME_MILLIS, Long.toString(System.currentTimeMillis() - jCurrentTimeMillis));
                    this.parserState.recursiveParserWrapperHandler.endEmbeddedDocument(newContentHandler, metadata);
                    if (temporaryResources == null) {
                        return;
                    }
                    tikaInputStream.close();
                }
            } catch (Throwable th) {
                parseContext.set(Parser.class, parser);
                parseContext.set(RecursivelySecureContentHandler.class, recursivelySecureContentHandler);
                parseContext.set(ParentContentHandler.class, parentContentHandler);
                metadata.set(TikaCoreProperties.PARSE_TIME_MILLIS, Long.toString(System.currentTimeMillis() - jCurrentTimeMillis));
                this.parserState.recursiveParserWrapperHandler.endEmbeddedDocument(newContentHandler, metadata);
                if (temporaryResources != null) {
                    tikaInputStream.close();
                }
                throw th;
            }
        }
    }

    private static class ParserState {
        private int embeddedCount;
        private final AbstractRecursiveParserWrapperHandler recursiveParserWrapperHandler;
        private AtomicInteger unknownCount;

        private ParserState(AbstractRecursiveParserWrapperHandler abstractRecursiveParserWrapperHandler) {
            this.unknownCount = new AtomicInteger(0);
            this.embeddedCount = 0;
            this.recursiveParserWrapperHandler = abstractRecursiveParserWrapperHandler;
        }
    }

    static class SecureHandlerCounter {
        private int totalChars;
        private final int totalWriteLimit;
        private boolean writeLimitReached;

        private SecureHandlerCounter(int i) {
            this.writeLimitReached = false;
            this.totalChars = 0;
            this.totalWriteLimit = i;
        }

        int getAvailable(int i) {
            return Math.min(this.totalWriteLimit - this.totalChars, i);
        }

        void addChars(int i) {
            this.totalChars += i;
        }
    }

    static class RecursivelySecureContentHandler extends SecureContentHandler {
        private static AtomicInteger COUNTER = new AtomicInteger();
        private final ContentHandler handler;
        private final SecureHandlerCounter handlerCounter;
        private final int id;
        private final ParseContext parseContext;
        private final boolean throwOnWriteLimitReached;

        public RecursivelySecureContentHandler(ContentHandler contentHandler, TikaInputStream tikaInputStream, SecureHandlerCounter secureHandlerCounter, boolean z, ParseContext parseContext) {
            super(contentHandler, tikaInputStream);
            this.id = COUNTER.getAndIncrement();
            this.handler = contentHandler;
            this.handlerCounter = secureHandlerCounter;
            this.throwOnWriteLimitReached = z;
            this.parseContext = parseContext;
        }

        @Override // org.apache.tika.sax.SecureContentHandler, org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            this.handler.startElement(str, str2, str3, attributes);
        }

        @Override // org.apache.tika.sax.SecureContentHandler, org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            this.handler.endElement(str, str2, str3);
        }

        @Override // org.apache.tika.sax.SecureContentHandler, org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (this.handlerCounter.writeLimitReached) {
                return;
            }
            if (this.handlerCounter.totalWriteLimit < 0) {
                super.characters(cArr, i, i2);
                return;
            }
            int available = this.handlerCounter.getAvailable(i2);
            super.characters(cArr, i, available);
            this.handlerCounter.addChars(available);
            if (available < i2) {
                handleWriteLimitReached();
            }
        }

        @Override // org.apache.tika.sax.SecureContentHandler, org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
            if (this.handlerCounter.writeLimitReached) {
                return;
            }
            if (this.handlerCounter.totalWriteLimit < 0) {
                super.ignorableWhitespace(cArr, i, i2);
                return;
            }
            int available = this.handlerCounter.getAvailable(i2);
            super.ignorableWhitespace(cArr, i, available);
            this.handlerCounter.addChars(available);
            if (available < i2) {
                handleWriteLimitReached();
            }
        }

        private void handleWriteLimitReached() throws WriteLimitReachedException {
            this.handlerCounter.writeLimitReached = true;
            if (this.throwOnWriteLimitReached) {
                throw new WriteLimitReachedException(this.handlerCounter.totalWriteLimit);
            }
            ParseRecord parseRecord = (ParseRecord) this.parseContext.get(ParseRecord.class);
            if (parseRecord != null) {
                parseRecord.setWriteLimitReached(true);
            }
        }
    }
}
