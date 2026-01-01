package org.apache.tika.fork;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Office;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
class MetadataContentHandler extends DefaultHandler {
    private final Metadata metadata;

    public MetadataContentHandler(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (Office.PREFIX_DOC_META.equals(str2)) {
            this.metadata.add(attributes.getValue(AppMeasurementSdk.ConditionalUserProperty.NAME), attributes.getValue(FirebaseAnalytics.Param.CONTENT));
        }
    }
}
