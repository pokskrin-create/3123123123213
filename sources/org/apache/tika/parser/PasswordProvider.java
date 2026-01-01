package org.apache.tika.parser;

import org.apache.tika.metadata.Metadata;

/* loaded from: classes4.dex */
public interface PasswordProvider {
    String getPassword(Metadata metadata);
}
