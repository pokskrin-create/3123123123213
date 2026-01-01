package org.apache.tika.mime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.utils.XMLReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class MimeTypesReader extends DefaultHandler implements MimeTypesReaderMetKeys {
    protected int priority;
    protected final MimeTypes types;
    private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static int POOL_SIZE = 10;
    private static ArrayBlockingQueue<SAXParser> SAX_PARSERS = new ArrayBlockingQueue<>(POOL_SIZE);
    static Logger LOG = LoggerFactory.getLogger((Class<?>) MimeTypesReader.class);
    protected MimeType type = null;
    protected StringBuilder characters = null;
    private ClauseRecord current = new ClauseRecord(null);

    static {
        try {
            setPoolSize(POOL_SIZE);
        } catch (TikaException e) {
            throw new RuntimeException("problem initializing SAXParser pool", e);
        }
    }

    protected MimeTypesReader(MimeTypes mimeTypes) {
        this.types = mimeTypes;
    }

    private static SAXParser acquireSAXParser() throws TikaException {
        SAXParser sAXParserPoll;
        do {
            try {
                try {
                    ReentrantReadWriteLock reentrantReadWriteLock = READ_WRITE_LOCK;
                    reentrantReadWriteLock.readLock().lock();
                    sAXParserPoll = SAX_PARSERS.poll(10L, TimeUnit.MILLISECONDS);
                    reentrantReadWriteLock.readLock().unlock();
                } catch (InterruptedException e) {
                    throw new TikaException("interrupted while waiting for SAXParser", e);
                }
            } catch (Throwable th) {
                READ_WRITE_LOCK.readLock().unlock();
                throw th;
            }
        } while (sAXParserPoll == null);
        return sAXParserPoll;
    }

    private static void releaseParser(SAXParser sAXParser) {
        try {
            sAXParser.reset();
        } catch (UnsupportedOperationException unused) {
        }
        try {
            ReentrantReadWriteLock reentrantReadWriteLock = READ_WRITE_LOCK;
            reentrantReadWriteLock.readLock().lock();
            SAX_PARSERS.offer(sAXParser);
            reentrantReadWriteLock.readLock().unlock();
        } catch (Throwable th) {
            READ_WRITE_LOCK.readLock().unlock();
            throw th;
        }
    }

    public static void setPoolSize(int i) throws TikaException {
        try {
            READ_WRITE_LOCK.writeLock().lock();
            SAX_PARSERS = new ArrayBlockingQueue<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                SAX_PARSERS.offer(newSAXParser());
            }
            POOL_SIZE = i;
        } finally {
            READ_WRITE_LOCK.writeLock().unlock();
        }
    }

    private static SAXParser newSAXParser() throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException, TikaException {
        SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
        sAXParserFactoryNewInstance.setNamespaceAware(false);
        try {
            sAXParserFactoryNewInstance.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        } catch (ParserConfigurationException | SAXException unused) {
            LOG.warn("can't set secure processing feature on: " + sAXParserFactoryNewInstance.getClass() + ". User assumes responsibility for consequences.");
        }
        try {
            return sAXParserFactoryNewInstance.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new TikaException("Can't create new sax parser", e);
        }
    }

    public void read(InputStream inputStream) throws IOException, MimeTypeException {
        SAXParser sAXParserAcquireSAXParser = null;
        try {
            try {
                sAXParserAcquireSAXParser = acquireSAXParser();
                sAXParserAcquireSAXParser.parse(inputStream, this);
            } catch (TikaException e) {
                throw new MimeTypeException("Unable to create an XML parser", e);
            } catch (SAXException e2) {
                throw new MimeTypeException("Invalid type configuration", e2);
            }
        } finally {
            if (sAXParserAcquireSAXParser != null) {
                releaseParser(sAXParserAcquireSAXParser);
            }
        }
    }

    public void read(Document document) throws MimeTypeException {
        try {
            XMLReaderUtils.getTransformer().transform(new DOMSource(document), new SAXResult(this));
        } catch (TransformerException | TikaException e) {
            throw new MimeTypeException("Failed to parse type registry", e);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.EntityResolver
    public InputSource resolveEntity(String str, String str2) {
        return new InputSource(new UnsynchronizedByteArrayInputStream(new byte[0]));
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (this.type == null) {
            if ("mime-type".equals(str3)) {
                String value = attributes.getValue("type");
                boolean zEquals = "true".equals(attributes.getValue(MimeTypesReaderMetKeys.INTERPRETED_ATTR));
                try {
                    MimeType mimeTypeForName = this.types.forName(value);
                    this.type = mimeTypeForName;
                    mimeTypeForName.setInterpreted(zEquals);
                } catch (MimeTypeException e) {
                    handleMimeError(value, e, str3, attributes);
                }
            }
        } else {
            if (MimeTypesReaderMetKeys.ALIAS_TAG.equals(str3)) {
                this.types.addAlias(this.type, MediaType.parse(attributes.getValue("type")));
                return;
            }
            if (MimeTypesReaderMetKeys.SUB_CLASS_OF_TAG.equals(str3)) {
                this.types.setSuperType(this.type, MediaType.parse(attributes.getValue("type")));
                return;
            }
            if (MimeTypesReaderMetKeys.ACRONYM_TAG.equals(str3) || MimeTypesReaderMetKeys.COMMENT_TAG.equals(str3) || MimeTypesReaderMetKeys.TIKA_LINK_TAG.equals(str3) || MimeTypesReaderMetKeys.TIKA_UTI_TAG.equals(str3)) {
                this.characters = new StringBuilder();
                return;
            }
            if (MimeTypesReaderMetKeys.GLOB_TAG.equals(str3)) {
                String value2 = attributes.getValue(MimeTypesReaderMetKeys.PATTERN_ATTR);
                String value3 = attributes.getValue(MimeTypesReaderMetKeys.ISREGEX_ATTR);
                if (value2 != null) {
                    try {
                        this.types.addPattern(this.type, value2, Boolean.parseBoolean(value3));
                    } catch (MimeTypeException e2) {
                        handleGlobError(this.type, value2, e2, str3, attributes);
                    }
                }
            } else {
                if (MimeTypesReaderMetKeys.ROOT_XML_TAG.equals(str3)) {
                    this.type.addRootXML(attributes.getValue(MimeTypesReaderMetKeys.NS_URI_ATTR), attributes.getValue(MimeTypesReaderMetKeys.LOCAL_NAME_ATTR));
                    return;
                }
                if ("match".equals(str3)) {
                    if (attributes.getValue(MimeTypesReaderMetKeys.MATCH_MINSHOULDMATCH_ATTR) != null) {
                        this.current = new ClauseRecord(new MinShouldMatchVal(Integer.parseInt(attributes.getValue(MimeTypesReaderMetKeys.MATCH_MINSHOULDMATCH_ATTR))));
                        return;
                    }
                    String value4 = attributes.getValue("type");
                    String value5 = attributes.getValue(MimeTypesReaderMetKeys.MATCH_OFFSET_ATTR);
                    String value6 = attributes.getValue("value");
                    String value7 = attributes.getValue(MimeTypesReaderMetKeys.MATCH_MASK_ATTR);
                    if (value4 == null) {
                        value4 = "string";
                    }
                    this.current = new ClauseRecord(new MagicMatch(this.type.getType(), value4, value5, value6, value7));
                    return;
                }
                if (MimeTypesReaderMetKeys.MAGIC_TAG.equals(str3)) {
                    String value8 = attributes.getValue(MimeTypesReaderMetKeys.MAGIC_PRIORITY_ATTR);
                    if (value8 != null && value8.length() > 0) {
                        this.priority = Integer.parseInt(value8);
                    } else {
                        this.priority = 50;
                    }
                    this.current = new ClauseRecord(null);
                }
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        if (this.type != null) {
            if ("mime-type".equals(str3)) {
                this.type = null;
                return;
            }
            if (MimeTypesReaderMetKeys.COMMENT_TAG.equals(str3)) {
                this.type.setDescription(this.characters.toString().trim());
                this.characters = null;
                return;
            }
            if (MimeTypesReaderMetKeys.ACRONYM_TAG.equals(str3)) {
                this.type.setAcronym(this.characters.toString().trim());
                this.characters = null;
                return;
            }
            if (MimeTypesReaderMetKeys.TIKA_UTI_TAG.equals(str3)) {
                this.type.setUniformTypeIdentifier(this.characters.toString().trim());
                this.characters = null;
                return;
            }
            if (MimeTypesReaderMetKeys.TIKA_LINK_TAG.equals(str3)) {
                try {
                    this.type.addLink(new URI(this.characters.toString().trim()));
                    this.characters = null;
                    return;
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException("unable to parse link: " + ((Object) this.characters), e);
                }
            }
            if ("match".equals(str3)) {
                this.current.stop();
                return;
            }
            if (MimeTypesReaderMetKeys.MAGIC_TAG.equals(str3)) {
                Iterator<Clause> it = this.current.getClauses().iterator();
                while (it.hasNext()) {
                    this.type.addMagic(new Magic(this.type, this.priority, it.next()));
                }
                this.current = null;
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        StringBuilder sb = this.characters;
        if (sb != null) {
            sb.append(cArr, i, i2);
        }
    }

    protected void handleMimeError(String str, MimeTypeException mimeTypeException, String str2, Attributes attributes) throws SAXException {
        throw new SAXException(mimeTypeException);
    }

    protected void handleGlobError(MimeType mimeType, String str, MimeTypeException mimeTypeException, String str2, Attributes attributes) throws SAXException {
        throw new SAXException(mimeTypeException);
    }

    private static class MinShouldMatchVal implements Clause {
        private final int val;

        @Override // org.apache.tika.mime.Clause
        public int size() {
            return 0;
        }

        MinShouldMatchVal(int i) {
            this.val = i;
        }

        int getVal() {
            return this.val;
        }

        @Override // org.apache.tika.mime.Clause
        public boolean eval(byte[] bArr) {
            throw new IllegalStateException("This should never be used on this placeholder class");
        }
    }

    private class ClauseRecord {
        private Clause clause;
        private final ClauseRecord parent;
        private List<Clause> subclauses = null;

        public ClauseRecord(Clause clause) {
            this.parent = MimeTypesReader.this.current;
            this.clause = clause;
        }

        public void stop() {
            Clause orClause;
            if (this.clause instanceof MinShouldMatchVal) {
                this.clause = new MinShouldMatchClause(((MinShouldMatchVal) this.clause).getVal(), this.subclauses);
            } else {
                List<Clause> list = this.subclauses;
                if (list != null) {
                    if (list.size() == 1) {
                        orClause = this.subclauses.get(0);
                    } else {
                        orClause = new OrClause(this.subclauses);
                    }
                    this.clause = new AndClause(this.clause, orClause);
                }
            }
            ClauseRecord clauseRecord = this.parent;
            List<Clause> list2 = clauseRecord.subclauses;
            if (list2 == null) {
                clauseRecord.subclauses = Collections.singletonList(this.clause);
            } else {
                if (list2.size() == 1) {
                    this.parent.subclauses = new ArrayList(this.parent.subclauses);
                }
                this.parent.subclauses.add(this.clause);
            }
            MimeTypesReader mimeTypesReader = MimeTypesReader.this;
            mimeTypesReader.current = mimeTypesReader.current.parent;
        }

        public List<Clause> getClauses() {
            return this.subclauses;
        }
    }
}
