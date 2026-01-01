package org.apache.tika.config;

import org.apache.tika.exception.TikaConfigException;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public interface InitializableProblemHandler {
    public static final InitializableProblemHandler DEFAULT;
    public static final InitializableProblemHandler IGNORE = new InitializableProblemHandler() { // from class: org.apache.tika.config.InitializableProblemHandler.1
        @Override // org.apache.tika.config.InitializableProblemHandler
        public void handleInitializableProblem(String str, String str2) {
        }

        public String toString() {
            return "IGNORE";
        }
    };
    public static final InitializableProblemHandler INFO = new InitializableProblemHandler() { // from class: org.apache.tika.config.InitializableProblemHandler.2
        @Override // org.apache.tika.config.InitializableProblemHandler
        public void handleInitializableProblem(String str, String str2) {
            LoggerFactory.getLogger(str).info(str2);
        }

        public String toString() {
            return "INFO";
        }
    };
    public static final InitializableProblemHandler THROW;
    public static final InitializableProblemHandler WARN;

    void handleInitializableProblem(String str, String str2) throws TikaConfigException;

    static {
        InitializableProblemHandler initializableProblemHandler = new InitializableProblemHandler() { // from class: org.apache.tika.config.InitializableProblemHandler.3
            @Override // org.apache.tika.config.InitializableProblemHandler
            public void handleInitializableProblem(String str, String str2) {
                LoggerFactory.getLogger(str).warn(str2);
            }

            public String toString() {
                return "WARN";
            }
        };
        WARN = initializableProblemHandler;
        THROW = new InitializableProblemHandler() { // from class: org.apache.tika.config.InitializableProblemHandler.4
            @Override // org.apache.tika.config.InitializableProblemHandler
            public void handleInitializableProblem(String str, String str2) throws TikaConfigException {
                throw new TikaConfigException(str2);
            }

            public String toString() {
                return "THROW";
            }
        };
        DEFAULT = initializableProblemHandler;
    }
}
