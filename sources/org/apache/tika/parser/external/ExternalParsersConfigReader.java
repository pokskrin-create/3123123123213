package org.apache.tika.parser.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.apache.tika.exception.TikaException;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.utils.StringUtils;
import org.apache.tika.utils.XMLReaderUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public final class ExternalParsersConfigReader implements ExternalParsersConfigReaderMetKeys {
    public static List<ExternalParser> read(InputStream inputStream) throws TikaException, IOException {
        try {
            return read(XMLReaderUtils.getDocumentBuilder().parse(new InputSource(inputStream)));
        } catch (SAXException e) {
            throw new TikaException("Invalid parser configuration", e);
        }
    }

    public static List<ExternalParser> read(Document document) throws TikaException, IOException {
        return read(document.getDocumentElement());
    }

    public static List<ExternalParser> read(Element element) throws TikaException, IOException {
        ExternalParser parser;
        ArrayList arrayList = new ArrayList();
        if (element != null && element.getTagName().equals(ExternalParsersConfigReaderMetKeys.EXTERNAL_PARSERS_TAG)) {
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node nodeItem = childNodes.item(i);
                if (nodeItem.getNodeType() == 1) {
                    Element element2 = (Element) nodeItem;
                    if (element2.getTagName().equals(ExternalParsersConfigReaderMetKeys.PARSER_TAG) && (parser = readParser(element2)) != null) {
                        arrayList.add(parser);
                    }
                }
            }
            return arrayList;
        }
        throw new MimeTypeException("Not a <external-parsers/> configuration document: " + (element != null ? element.getTagName() : "n/a"));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.apache.tika.parser.external.ExternalParser readParser(org.w3c.dom.Element r8) throws org.apache.tika.exception.TikaException {
        /*
            org.apache.tika.parser.external.ExternalParser r0 = new org.apache.tika.parser.external.ExternalParser
            r0.<init>()
            org.w3c.dom.NodeList r8 = r8.getChildNodes()
            r1 = 0
            r2 = r1
        Lb:
            int r3 = r8.getLength()
            if (r2 >= r3) goto L9a
            org.w3c.dom.Node r3 = r8.item(r2)
            short r4 = r3.getNodeType()
            r5 = 1
            if (r4 != r5) goto L96
            org.w3c.dom.Element r3 = (org.w3c.dom.Element) r3
            java.lang.String r4 = r3.getTagName()
            r4.hashCode()
            int r6 = r4.hashCode()
            r7 = -1
            switch(r6) {
                case -450004177: goto L4e;
                case 94627080: goto L45;
                case 950394699: goto L3a;
                case 1081186720: goto L2f;
                default: goto L2d;
            }
        L2d:
            r5 = r7
            goto L58
        L2f:
            java.lang.String r5 = "mime-types"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L38
            goto L2d
        L38:
            r5 = 3
            goto L58
        L3a:
            java.lang.String r5 = "command"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L43
            goto L2d
        L43:
            r5 = 2
            goto L58
        L45:
            java.lang.String r6 = "check"
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L58
            goto L2d
        L4e:
            java.lang.String r5 = "metadata"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L57
            goto L2d
        L57:
            r5 = r1
        L58:
            switch(r5) {
                case 0: goto L8f;
                case 1: goto L87;
                case 2: goto L7b;
                case 3: goto L73;
                default: goto L5b;
            }
        L5b:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r3.getTagName()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "reaction not defined for "
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r8.<init>(r0)
            throw r8
        L73:
            java.util.Set r3 = readMimeTypes(r3)
            r0.setSupportedTypes(r3)
            goto L96
        L7b:
            java.lang.String r3 = getString(r3)
            java.lang.String[] r3 = new java.lang.String[]{r3}
            r0.setCommand(r3)
            goto L96
        L87:
            boolean r3 = readCheckTagAndCheck(r3)
            if (r3 != 0) goto L96
            r8 = 0
            return r8
        L8f:
            java.util.Map r3 = readMetadataPatterns(r3)
            r0.setMetadataExtractionPatterns(r3)
        L96:
            int r2 = r2 + 1
            goto Lb
        L9a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.parser.external.ExternalParsersConfigReader.readParser(org.w3c.dom.Element):org.apache.tika.parser.external.ExternalParser");
    }

    private static Set<MediaType> readMimeTypes(Element element) {
        HashSet hashSet = new HashSet();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1) {
                Element element2 = (Element) nodeItem;
                if (element2.getTagName().equals("mime-type")) {
                    hashSet.add(MediaType.parse(getString(element2)));
                }
            }
        }
        return hashSet;
    }

    private static Map<Pattern, String> readMetadataPatterns(Element element) {
        HashMap map = new HashMap();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1) {
                Element element2 = (Element) nodeItem;
                if (element2.getTagName().equals("match")) {
                    map.put(Pattern.compile(getString(element2)), element2.getAttribute(ExternalParsersConfigReaderMetKeys.METADATA_KEY_ATTR));
                }
            }
        }
        return map;
    }

    private static boolean readCheckTagAndCheck(Element element) {
        ArrayList arrayList = new ArrayList();
        NodeList childNodes = element.getChildNodes();
        String string = null;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1) {
                Element element2 = (Element) nodeItem;
                if (element2.getTagName().equals(ExternalParsersConfigReaderMetKeys.COMMAND_TAG)) {
                    string = getString(element2);
                }
                if (element2.getTagName().equals(ExternalParsersConfigReaderMetKeys.ERROR_CODES_TAG)) {
                    StringTokenizer stringTokenizer = new StringTokenizer(getString(element2), ",");
                    while (stringTokenizer.hasMoreElements()) {
                        try {
                            arrayList.add(Integer.valueOf(Integer.parseInt(stringTokenizer.nextToken())));
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
        }
        if (string == null) {
            return true;
        }
        String[] strArrSplit = string.split(StringUtils.SPACE);
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        return ExternalParser.check(strArrSplit, iArr);
    }

    private static String getString(Element element) {
        StringBuilder sb = new StringBuilder();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 3) {
                sb.append(nodeItem.getNodeValue());
            }
        }
        return sb.toString();
    }
}
