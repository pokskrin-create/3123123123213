package org.apache.tika.metadata;

/* loaded from: classes4.dex */
public interface Epub {
    public static final String EPUB_PREFIX = "epub:";
    public static final Property RENDITION_LAYOUT = Property.externalClosedChoise("epub:rendition:layout", "pre-paginated", "reflowable");
    public static final Property VERSION = Property.externalText("epub:version");
}
