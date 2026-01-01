package org.apache.tika.parser.external;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.CompositeParser;
import org.apache.tika.parser.Parser;

/* loaded from: classes4.dex */
public class ExternalParsersFactory {
    public static List<ExternalParser> create() throws TikaException, IOException {
        return create(new ServiceLoader());
    }

    public static List<ExternalParser> create(ServiceLoader serviceLoader) throws TikaException, IOException {
        return create("tika-external-parsers.xml", serviceLoader);
    }

    public static List<ExternalParser> create(String str, ServiceLoader serviceLoader) throws TikaException, IOException {
        return create((URL[]) Collections.list(serviceLoader.findServiceResources(ExternalParsersFactory.class.getPackage().getName().replace(FilenameUtils.EXTENSION_SEPARATOR, IOUtils.DIR_SEPARATOR_UNIX) + "/" + str)).toArray(new URL[0]));
    }

    public static List<ExternalParser> create(URL... urlArr) throws TikaException, IOException {
        ArrayList arrayList = new ArrayList();
        for (URL url : urlArr) {
            InputStream inputStreamOpenStream = url.openStream();
            try {
                arrayList.addAll(ExternalParsersConfigReader.read(inputStreamOpenStream));
                if (inputStreamOpenStream != null) {
                    inputStreamOpenStream.close();
                }
            } catch (Throwable th) {
                if (inputStreamOpenStream != null) {
                    try {
                        inputStreamOpenStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        return arrayList;
    }

    public static void attachExternalParsers(TikaConfig tikaConfig) throws TikaException, IOException {
        attachExternalParsers(create(), tikaConfig);
    }

    public static void attachExternalParsers(List<ExternalParser> list, TikaConfig tikaConfig) {
        Parser parser = tikaConfig.getParser();
        if (parser instanceof CompositeParser) {
            ((CompositeParser) parser).getParsers();
        }
    }
}
