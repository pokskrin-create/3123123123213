package org.apache.tika.mime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/* loaded from: classes4.dex */
public class MimeTypesFactory {
    public static final String CUSTOM_MIMES_SYS_PROP = "tika.custom-mimetypes";
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) MimeTypesFactory.class);

    public static MimeTypes create() {
        return new MimeTypes();
    }

    public static MimeTypes create(Document document) throws MimeTypeException {
        MimeTypes mimeTypes = new MimeTypes();
        new MimeTypesReader(mimeTypes).read(document);
        mimeTypes.init();
        return mimeTypes;
    }

    public static MimeTypes create(InputStream... inputStreamArr) throws IOException, MimeTypeException {
        MimeTypes mimeTypes = new MimeTypes();
        MimeTypesReader mimeTypesReader = new MimeTypesReader(mimeTypes);
        for (InputStream inputStream : inputStreamArr) {
            mimeTypesReader.read(inputStream);
        }
        mimeTypes.init();
        return mimeTypes;
    }

    public static MimeTypes create(InputStream inputStream) throws IOException, MimeTypeException {
        return create(inputStream);
    }

    public static MimeTypes create(URL... urlArr) throws IOException, MimeTypeException {
        int length = urlArr.length;
        InputStream[] inputStreamArr = new InputStream[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            inputStreamArr[i2] = urlArr[i2].openStream();
        }
        try {
            return create(inputStreamArr);
        } finally {
            while (i < length) {
                inputStreamArr[i].close();
                i++;
            }
        }
    }

    public static MimeTypes create(URL url) throws IOException, MimeTypeException {
        return create(url);
    }

    public static MimeTypes create(String str) throws IOException, MimeTypeException {
        return create(MimeTypesReader.class.getResource(str));
    }

    public static MimeTypes create(String str, String str2) throws IOException, MimeTypeException {
        return create(str, str2, null);
    }

    public static MimeTypes create(String str, String str2, ClassLoader classLoader) throws IOException, MimeTypeException {
        if (classLoader == null) {
            classLoader = MimeTypesReader.class.getClassLoader();
        }
        URL resource = classLoader.getResource((MimeTypesReader.class.getPackage().getName().replace(FilenameUtils.EXTENSION_SEPARATOR, IOUtils.DIR_SEPARATOR_UNIX) + "/") + str);
        ArrayList list = Collections.list(classLoader.getResources(str2));
        ArrayList arrayList = new ArrayList();
        arrayList.add(resource);
        arrayList.addAll(list);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            arrayList.stream().forEach(new Consumer() { // from class: org.apache.tika.mime.MimeTypesFactory$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MimeTypesFactory.LOG.debug("Loaded custom mimes file: {}", (URL) obj);
                }
            });
        }
        String property = System.getProperty(CUSTOM_MIMES_SYS_PROP);
        if (property != null) {
            File file = new File(property);
            if (!file.exists()) {
                throw new IOException("Specified custom mimetypes file not found: " + property);
            }
            arrayList.add(file.toURI().toURL());
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded external custom mimetypes file: {}", file.getAbsolutePath());
            }
        }
        return create((URL[]) arrayList.toArray(new URL[0]));
    }
}
