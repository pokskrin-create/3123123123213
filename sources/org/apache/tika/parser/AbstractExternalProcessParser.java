package org.apache.tika.parser;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public abstract class AbstractExternalProcessParser implements Parser {
    private static final ConcurrentHashMap<String, Process> PROCESS_MAP = new ConcurrentHashMap<>();
    private static final long serialVersionUID = 7186985395903074255L;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { // from class: org.apache.tika.parser.AbstractExternalProcessParser$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractExternalProcessParser.PROCESS_MAP.forEachValue(1L, new Consumer() { // from class: org.apache.tika.parser.AbstractExternalProcessParser$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Process) obj).destroyForcibly();
                    }
                });
            }
        }));
    }

    protected String register(Process process) {
        String string = UUID.randomUUID().toString();
        PROCESS_MAP.put(string, process);
        return string;
    }

    protected Process release(String str) {
        return PROCESS_MAP.remove(str);
    }
}
