package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.text.PositionedGlyphs;
import android.graphics.text.TextRunShaper;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Preconditions;
import androidx.tracing.Trace;
import java.util.List;

/* loaded from: classes.dex */
public class TypefaceCompat {
    public static final boolean DOWNLOADABLE_FALLBACK_DEBUG = false;
    public static final boolean DOWNLOADABLE_FONT_TRACING = true;
    private static final String REFERENCE_CHAR_FOR_PRIMARY_FONT = " ";
    private static final String TAG = "TypefaceCompat";
    private static Paint sCachedPaint;
    private static final LruCache<String, Typeface> sTypefaceCache;
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;

    static {
        Trace.beginSection("TypefaceCompat static init");
        if (Build.VERSION.SDK_INT >= 31) {
            sTypefaceCompatImpl = new TypefaceCompatApi31Impl();
        } else if (Build.VERSION.SDK_INT >= 29) {
            sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
        } else if (Build.VERSION.SDK_INT >= 28) {
            sTypefaceCompatImpl = new TypefaceCompatApi28Impl();
        } else if (Build.VERSION.SDK_INT >= 26) {
            sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
        } else if (TypefaceCompatApi24Impl.isUsable()) {
            sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
        } else {
            sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
        }
        sTypefaceCache = new LruCache<>(16);
        sCachedPaint = null;
        Trace.endSection();
    }

    private TypefaceCompat() {
    }

    public static Typeface findFromCache(Resources resources, int i, String str, int i2, int i3) {
        return sTypefaceCache.get(createResourceUid(resources, i, str, i2, i3));
    }

    @Deprecated
    public static Typeface findFromCache(Resources resources, int i, int i2) {
        return findFromCache(resources, i, null, 0, i2);
    }

    private static String createResourceUid(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }

    public static Font guessPrimaryFont(Typeface typeface) {
        if (sCachedPaint == null) {
            sCachedPaint = new Paint();
        }
        sCachedPaint.setTextSize(10.0f);
        sCachedPaint.setTypeface(typeface);
        PositionedGlyphs positionedGlyphsShapeTextRun = TextRunShaper.shapeTextRun((CharSequence) " ", 0, 1, 0, 1, 0.0f, 0.0f, false, sCachedPaint);
        if (positionedGlyphsShapeTextRun.glyphCount() == 0) {
            return null;
        }
        return positionedGlyphsShapeTextRun.getFont(0);
    }

    public static Typeface getSystemFontFamily(String str) {
        if (str != null && !str.isEmpty()) {
            Typeface typefaceCreate = Typeface.create(str, 0);
            Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
            if (typefaceCreate != null && !typefaceCreate.equals(typefaceCreate2)) {
                return typefaceCreate;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e9, code lost:
    
        return r0.build();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Typeface getSystemFontFamilyWithFallback(androidx.core.content.res.FontResourcesParserCompat.ProviderResourceEntry r8) {
        /*
            java.lang.String r0 = r8.getSystemFontFamilyName()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L11
            android.graphics.Typeface r0 = getSystemFontFamily(r0)
            if (r0 == 0) goto L11
            return r0
        L11:
            java.util.List r8 = r8.getRequests()
            int r0 = r8.size()
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L2c
            java.lang.Object r8 = r8.get(r1)
            androidx.core.provider.FontRequest r8 = (androidx.core.provider.FontRequest) r8
            java.lang.String r8 = r8.getSystemFont()
            android.graphics.Typeface r8 = getSystemFontFamily(r8)
            return r8
        L2c:
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 31
            r4 = 0
            if (r0 >= r3) goto L34
            return r4
        L34:
            r0 = r1
        L35:
            int r3 = r8.size()
            if (r0 >= r3) goto L4f
            java.lang.Object r3 = r8.get(r0)
            androidx.core.provider.FontRequest r3 = (androidx.core.provider.FontRequest) r3
            java.lang.String r3 = r3.getSystemFont()
            android.graphics.Typeface r3 = getSystemFontFamily(r3)
            if (r3 != 0) goto L4c
            return r4
        L4c:
            int r0 = r0 + 1
            goto L35
        L4f:
            r0 = r4
        L50:
            int r3 = r8.size()
            if (r1 >= r3) goto Le5
            java.lang.Object r3 = r8.get(r1)
            androidx.core.provider.FontRequest r3 = (androidx.core.provider.FontRequest) r3
            int r5 = r8.size()
            int r5 = r5 - r2
            if (r1 != r5) goto L75
            java.lang.String r5 = r3.getVariationSettings()
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L75
            java.lang.String r8 = r3.getSystemFont()
            androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r0, r8)
            goto Le5
        L75:
            java.lang.String r5 = r3.getSystemFont()
            android.graphics.Typeface r5 = getSystemFontFamily(r5)
            android.graphics.fonts.Font r5 = guessPrimaryFont(r5)
            java.lang.String r6 = "TypefaceCompat"
            if (r5 != 0) goto La0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "Unable identify the primary font for "
            r8.<init>(r0)
            java.lang.String r0 = r3.getSystemFont()
            r8.append(r0)
            java.lang.String r0 = ". Falling back to provider font."
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            android.util.Log.w(r6, r8)
            return r4
        La0:
            java.lang.String r7 = r3.getVariationSettings()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto Lcf
            androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m106m()     // Catch: java.io.IOException -> Lc9
            androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m114m$1()     // Catch: java.io.IOException -> Lc9
            android.graphics.fonts.Font$Builder r5 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r5)     // Catch: java.io.IOException -> Lc9
            java.lang.String r3 = r3.getVariationSettings()     // Catch: java.io.IOException -> Lc9
            android.graphics.fonts.Font$Builder r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r5, r3)     // Catch: java.io.IOException -> Lc9
            android.graphics.fonts.Font r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r3)     // Catch: java.io.IOException -> Lc9
            android.graphics.fonts.FontFamily$Builder r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m92m(r3)     // Catch: java.io.IOException -> Lc9
            android.graphics.fonts.FontFamily r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r3)     // Catch: java.io.IOException -> Lc9
            goto Ld7
        Lc9:
            java.lang.String r8 = "Failed to clone Font instance. Fall back to provider font."
            android.util.Log.e(r6, r8)
            return r4
        Lcf:
            android.graphics.fonts.FontFamily$Builder r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m92m(r5)
            android.graphics.fonts.FontFamily r3 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r3)
        Ld7:
            if (r0 != 0) goto Lde
            android.graphics.Typeface$CustomFallbackBuilder r0 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r3)
            goto Le1
        Lde:
            androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r0, r3)
        Le1:
            int r1 = r1 + 1
            goto L50
        Le5:
            android.graphics.Typeface r8 = androidx.core.util.HalfKt$$ExternalSyntheticApiModelOutline0.m(r0)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompat.getSystemFontFamilyWithFallback(androidx.core.content.res.FontResourcesParserCompat$ProviderResourceEntry):android.graphics.Typeface");
    }

    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, String str, int i2, int i3, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean z) throws NoSuchFieldException {
        Typeface typefaceCreateFromFontFamilyFilesResourceEntry;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            Typeface systemFontFamilyWithFallback = getSystemFontFamilyWithFallback(providerResourceEntry);
            if (systemFontFamilyWithFallback != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(systemFontFamilyWithFallback, handler);
                }
                sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), systemFontFamilyWithFallback);
                return systemFontFamilyWithFallback;
            }
            boolean z2 = !z ? fontCallback != null : providerResourceEntry.getFetchStrategy() != 0;
            int timeout = z ? providerResourceEntry.getTimeout() : -1;
            typefaceCreateFromFontFamilyFilesResourceEntry = FontsContractCompat.requestFont(context, providerResourceEntry.getRequests(), i3, z2, timeout, ResourcesCompat.FontCallback.getHandler(handler), new ResourcesCallbackAdapter(fontCallback));
        } else {
            typefaceCreateFromFontFamilyFilesResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, i3);
            if (fontCallback != null) {
                if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
                    fontCallback.callbackSuccessAsync(typefaceCreateFromFontFamilyFilesResourceEntry, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
        }
        if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typefaceCreateFromFontFamilyFilesResourceEntry);
        }
        return typefaceCreateFromFontFamilyFilesResourceEntry;
    }

    @Deprecated
    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, int i2, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean z) {
        return createFromResourcesFamilyXml(context, familyResourceEntry, resources, i, null, 0, i2, fontCallback, handler, z);
    }

    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2, int i3) {
        Typeface typefaceCreateFromResourcesFontFile = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, i, str, i3);
        if (typefaceCreateFromResourcesFontFile != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typefaceCreateFromResourcesFontFile);
        }
        return typefaceCreateFromResourcesFontFile;
    }

    @Deprecated
    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        return createFromResourcesFontFile(context, resources, i, str, 0, i2);
    }

    public static Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfo");
        try {
            return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fontInfoArr, i);
        } finally {
            Trace.endSection();
        }
    }

    public static Typeface createFromFontInfoWithFallback(Context context, CancellationSignal cancellationSignal, List<FontsContractCompat.FontInfo[]> list, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfoWithFallback");
        try {
            return sTypefaceCompatImpl.createFromFontInfoWithFallback(context, cancellationSignal, list, i);
        } finally {
            Trace.endSection();
        }
    }

    private static Typeface getBestFontFromFamily(Context context, Typeface typeface, int i) throws NoSuchFieldException {
        TypefaceCompatBaseImpl typefaceCompatBaseImpl = sTypefaceCompatImpl;
        FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamily = typefaceCompatBaseImpl.getFontFamily(typeface);
        if (fontFamily == null) {
            return null;
        }
        return typefaceCompatBaseImpl.createFromFontFamilyFilesResourceEntry(context, fontFamily, context.getResources(), i);
    }

    public static Typeface create(Context context, Typeface typeface, int i) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        return Typeface.create(typeface, i);
    }

    public static Typeface create(Context context, Typeface typeface, int i, boolean z) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        Preconditions.checkArgumentInRange(i, 1, 1000, "weight");
        if (typeface == null) {
            typeface = Typeface.DEFAULT;
        }
        return sTypefaceCompatImpl.createWeightStyle(context, typeface, i, z);
    }

    public static void clearCache() {
        sTypefaceCache.evictAll();
    }

    public static class ResourcesCallbackAdapter extends FontsContractCompat.FontRequestCallback {
        private ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRetrieved(Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.m69x46c88379(typeface);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRequestFailed(int i) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.m68xb24343b7(i);
            }
        }
    }
}
