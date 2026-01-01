package androidx.browser.trusted;

import android.net.Uri;
import android.os.Bundle;
import androidx.core.os.BundleCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class FileHandlingData {
    public static final String KEY_URIS = "androidx.browser.trusted.KEY_URIS";
    public final List<Uri> uris;

    public FileHandlingData(List<Uri> list) {
        this.uris = (List) Objects.requireNonNull(list);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.uris != null) {
            bundle.putParcelableArrayList(KEY_URIS, new ArrayList<>(this.uris));
        }
        return bundle;
    }

    public static FileHandlingData fromBundle(Bundle bundle) {
        return new FileHandlingData(BundleCompat.getParcelableArrayList(bundle, KEY_URIS, Uri.class));
    }
}
