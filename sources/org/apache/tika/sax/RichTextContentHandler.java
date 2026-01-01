package org.apache.tika.sax;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class RichTextContentHandler extends WriteOutContentHandler {
    public RichTextContentHandler(Writer writer) {
        super(writer);
    }

    @Override // org.apache.tika.sax.ContentHandlerDecorator, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        super.startElement(str, str2, str3, attributes);
        if ("img".equals(str2) && attributes.getValue("alt") != null) {
            String str4 = "[image: " + attributes.getValue("alt") + "]";
            characters(str4.toCharArray(), 0, str4.length());
        }
        if (!"a".equals(str2) || attributes.getValue(AppMeasurementSdk.ConditionalUserProperty.NAME) == null) {
            return;
        }
        String str5 = "[bookmark: " + attributes.getValue(AppMeasurementSdk.ConditionalUserProperty.NAME) + "]";
        characters(str5.toCharArray(), 0, str5.length());
    }
}
