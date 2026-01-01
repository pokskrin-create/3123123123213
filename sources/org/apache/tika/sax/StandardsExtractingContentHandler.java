package org.apache.tika.sax;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class StandardsExtractingContentHandler extends ContentHandlerDecorator {
    public static final String STANDARD_REFERENCES = "standard_references";
    private int maxBufferLength;
    private final Metadata metadata;
    private final StringBuilder stringBuilder;
    private double threshold;

    public StandardsExtractingContentHandler(ContentHandler contentHandler, Metadata metadata) {
        super(contentHandler);
        this.maxBufferLength = 100000;
        this.threshold = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        this.metadata = metadata;
        this.stringBuilder = new StringBuilder();
    }

    protected StandardsExtractingContentHandler() {
        this(new DefaultHandler(), new Metadata());
    }

    public double getThreshold() {
        return this.threshold;
    }

    public void setThreshold(double d) {
        this.threshold = d;
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        int length;
        try {
            int i3 = this.maxBufferLength;
            if (i3 > -1 && (length = i3 - this.stringBuilder.length()) <= i2) {
                i2 = length;
            }
            this.stringBuilder.append(new String(Arrays.copyOfRange(cArr, i, i + i2)));
            super.characters(cArr, i, i2);
        } catch (SAXException e) {
            handleException(e);
        }
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        super.endDocument();
        Iterator<StandardReference> it = StandardsText.extractStandardReferences(this.stringBuilder.toString(), this.threshold).iterator();
        while (it.hasNext()) {
            this.metadata.add(STANDARD_REFERENCES, it.next().toString());
        }
    }

    public void setMaxBufferLength(int i) {
        this.maxBufferLength = i;
    }
}
