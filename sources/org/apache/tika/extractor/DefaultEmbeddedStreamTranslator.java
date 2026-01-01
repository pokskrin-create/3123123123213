package org.apache.tika.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.utils.ServiceLoaderUtils;

/* loaded from: classes4.dex */
public class DefaultEmbeddedStreamTranslator implements EmbeddedStreamTranslator {
    final List<EmbeddedStreamTranslator> translators;

    private static List<EmbeddedStreamTranslator> getDefaultFilters(ServiceLoader serviceLoader) {
        List<EmbeddedStreamTranslator> listLoadServiceProviders = serviceLoader.loadServiceProviders(EmbeddedStreamTranslator.class);
        ServiceLoaderUtils.sortLoadedClasses(listLoadServiceProviders);
        return listLoadServiceProviders;
    }

    public DefaultEmbeddedStreamTranslator() {
        this(getDefaultFilters(new ServiceLoader()));
    }

    private DefaultEmbeddedStreamTranslator(List<EmbeddedStreamTranslator> list) {
        this.translators = list;
    }

    @Override // org.apache.tika.extractor.EmbeddedStreamTranslator
    public boolean shouldTranslate(InputStream inputStream, Metadata metadata) throws IOException {
        Iterator<EmbeddedStreamTranslator> it = this.translators.iterator();
        while (it.hasNext()) {
            if (it.next().shouldTranslate(inputStream, metadata)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.tika.extractor.EmbeddedStreamTranslator
    public InputStream translate(InputStream inputStream, Metadata metadata) throws IOException {
        Iterator<EmbeddedStreamTranslator> it = this.translators.iterator();
        while (it.hasNext()) {
            InputStream inputStreamTranslate = it.next().translate(inputStream, metadata);
            if (inputStreamTranslate != null) {
                return inputStreamTranslate;
            }
        }
        return inputStream;
    }
}
