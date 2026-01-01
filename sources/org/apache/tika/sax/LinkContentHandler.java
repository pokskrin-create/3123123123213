package org.apache.tika.sax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class LinkContentHandler extends DefaultHandler {
    private final LinkedList<LinkBuilder> builderStack;
    private final boolean collapseWhitespaceInAnchor;
    private final List<Link> links;

    public LinkContentHandler() {
        this(false);
    }

    public LinkContentHandler(boolean z) {
        this.builderStack = new LinkedList<>();
        this.links = new ArrayList();
        this.collapseWhitespaceInAnchor = z;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) {
        if (XHTMLContentHandler.XHTML.equals(str)) {
            if ("a".equals(str2)) {
                LinkBuilder linkBuilder = new LinkBuilder("a");
                linkBuilder.setURI(attributes.getValue("", "href"));
                linkBuilder.setTitle(attributes.getValue("", "title"));
                linkBuilder.setRel(attributes.getValue("", "rel"));
                this.builderStack.addFirst(linkBuilder);
                return;
            }
            if ("link".equals(str2)) {
                LinkBuilder linkBuilder2 = new LinkBuilder("link");
                linkBuilder2.setURI(attributes.getValue("", "href"));
                linkBuilder2.setRel(attributes.getValue("", "rel"));
                this.builderStack.addFirst(linkBuilder2);
                return;
            }
            if ("script".equals(str2)) {
                if (attributes.getValue("", "src") != null) {
                    LinkBuilder linkBuilder3 = new LinkBuilder("script");
                    linkBuilder3.setURI(attributes.getValue("", "src"));
                    this.builderStack.addFirst(linkBuilder3);
                    return;
                }
                return;
            }
            if ("iframe".equals(str2)) {
                LinkBuilder linkBuilder4 = new LinkBuilder("iframe");
                linkBuilder4.setURI(attributes.getValue("", "src"));
                this.builderStack.addFirst(linkBuilder4);
            } else if ("img".equals(str2)) {
                LinkBuilder linkBuilder5 = new LinkBuilder("img");
                linkBuilder5.setURI(attributes.getValue("", "src"));
                linkBuilder5.setTitle(attributes.getValue("", "title"));
                linkBuilder5.setRel(attributes.getValue("", "rel"));
                this.builderStack.addFirst(linkBuilder5);
                String value = attributes.getValue("", "alt");
                if (value != null) {
                    char[] charArray = value.toCharArray();
                    characters(charArray, 0, charArray.length);
                }
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        Iterator<LinkBuilder> it = this.builderStack.iterator();
        while (it.hasNext()) {
            it.next().characters(cArr, i, i2);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) {
        characters(cArr, i, i2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        if (this.builderStack.isEmpty() || !XHTMLContentHandler.XHTML.equals(str)) {
            return;
        }
        if (("a".equals(str2) || "img".equals(str2) || "link".equals(str2) || "script".equals(str2) || "iframe".equals(str2)) && this.builderStack.getFirst().getType().equals(str2)) {
            this.links.add(this.builderStack.removeFirst().getLink(this.collapseWhitespaceInAnchor));
        }
    }
}
