package org.apache.tika.pipes.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.UByte$$ExternalSyntheticBackport0;
import org.apache.tika.config.ConfigBase;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;

/* loaded from: classes4.dex */
public class FetcherManager extends ConfigBase {
    private final Map<String, Fetcher> fetcherMap = new ConcurrentHashMap();

    public static FetcherManager load(Path path) throws IOException, TikaConfigException {
        InputStream inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
        try {
            FetcherManager fetcherManager = (FetcherManager) buildComposite("fetchers", FetcherManager.class, "fetcher", Fetcher.class, inputStreamNewInputStream);
            if (inputStreamNewInputStream != null) {
                inputStreamNewInputStream.close();
            }
            return fetcherManager;
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

    public FetcherManager(List<Fetcher> list) throws TikaConfigException {
        for (Fetcher fetcher : list) {
            String name = fetcher.getName();
            if (name == null || UByte$$ExternalSyntheticBackport0.m(name)) {
                throw new TikaConfigException("fetcher name must not be blank");
            }
            if (this.fetcherMap.containsKey(fetcher.getName())) {
                throw new TikaConfigException("Multiple fetchers cannot support the same prefix: " + fetcher.getName());
            }
            this.fetcherMap.put(fetcher.getName(), fetcher);
        }
    }

    public Fetcher getFetcher(String str) throws TikaException, IOException {
        Fetcher fetcher = this.fetcherMap.get(str);
        if (fetcher != null) {
            return fetcher;
        }
        throw new IllegalArgumentException("Can't find fetcher for fetcherName: " + str + ". I've loaded: " + this.fetcherMap.keySet());
    }

    public Set<String> getSupported() {
        return this.fetcherMap.keySet();
    }

    public Fetcher getFetcher() {
        if (this.fetcherMap.size() == 0) {
            throw new IllegalArgumentException("fetchers size must == 1 for the no arg call");
        }
        if (this.fetcherMap.size() > 1) {
            throw new IllegalArgumentException("need to specify 'fetcherName' if > 1 fetchers are available");
        }
        Iterator<Fetcher> it = this.fetcherMap.values().iterator();
        if (it.hasNext()) {
            return it.next();
        }
        throw new IllegalArgumentException("fetchers size must == 0");
    }
}
