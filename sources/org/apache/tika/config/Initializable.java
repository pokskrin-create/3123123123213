package org.apache.tika.config;

import java.util.Map;
import org.apache.tika.exception.TikaConfigException;

/* loaded from: classes4.dex */
public interface Initializable {
    void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException;

    void initialize(Map<String, Param> map) throws TikaConfigException;
}
