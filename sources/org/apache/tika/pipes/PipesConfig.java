package org.apache.tika.pipes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import org.apache.tika.exception.TikaConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class PipesConfig extends PipesConfigBase {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) PipesClient.class);
    private long maxWaitForClientMillis = 60000;

    public static PipesConfig load(Path path) throws IOException, TikaConfigException {
        PipesConfig pipesConfig = new PipesConfig();
        InputStream inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
        try {
            pipesConfig.configure("pipes", inputStreamNewInputStream);
            if (inputStreamNewInputStream != null) {
                inputStreamNewInputStream.close();
            }
            if (pipesConfig.getTikaConfig() == null) {
                LOG.debug("A separate tikaConfig was not specified in the <pipes/> element in the  config file; will use {} for pipes", path);
                pipesConfig.setTikaConfig(path);
            }
            return pipesConfig;
        } catch (Throwable th) {
            if (inputStreamNewInputStream != null) {
                try {
                    inputStreamNewInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static PipesConfig load(InputStream inputStream) throws IOException, TikaConfigException {
        PipesConfig pipesConfig = new PipesConfig();
        pipesConfig.configure("pipes", inputStream);
        return pipesConfig;
    }

    private PipesConfig() {
    }

    public long getMaxWaitForClientMillis() {
        return this.maxWaitForClientMillis;
    }

    public void setMaxWaitForClientMillis(long j) {
        this.maxWaitForClientMillis = j;
    }
}
