package org.apache.tika.mime;

import java.util.List;
import org.apache.tika.detect.Detector;

/* loaded from: classes4.dex */
public class ProbabilisticMimeDetectionSelector implements Detector {
    private static final float DEFAULT_EXTENSION_TRUST = 0.8f;
    private static final float DEFAULT_MAGIC_TRUST = 0.9f;
    private static final float DEFAULT_META_TRUST = 0.8f;
    private static final long serialVersionUID = 224589862960269260L;
    private final float changeRate;
    private float extension_neg;
    private float extension_trust;
    private float magic_neg;
    private float magic_trust;
    private float meta_neg;
    private float meta_trust;
    private final MimeTypes mimeTypes;
    private float priorExtensionFileType;
    private float priorMagicFileType;
    private float priorMetaFileType;
    private final MediaType rootMediaType;
    private float threshold;

    public ProbabilisticMimeDetectionSelector() {
        this(MimeTypes.getDefaultMimeTypes(), null);
    }

    public ProbabilisticMimeDetectionSelector(Builder builder) {
        this(MimeTypes.getDefaultMimeTypes(), builder);
    }

    public ProbabilisticMimeDetectionSelector(MimeTypes mimeTypes) {
        this(mimeTypes, null);
    }

    public ProbabilisticMimeDetectionSelector(MimeTypes mimeTypes, Builder builder) {
        this.mimeTypes = mimeTypes;
        this.rootMediaType = MediaType.OCTET_STREAM;
        initializeDefaultProbabilityParameters();
        this.changeRate = 0.1f;
        if (builder != null) {
            this.priorMagicFileType = builder.priorMagicFileType == 0.0f ? this.priorMagicFileType : builder.priorMagicFileType;
            this.priorExtensionFileType = builder.priorExtensionFileType == 0.0f ? this.priorExtensionFileType : builder.priorExtensionFileType;
            this.priorMetaFileType = builder.priorMetaFileType == 0.0f ? this.priorMetaFileType : builder.priorMetaFileType;
            this.magic_trust = builder.magic_trust == 0.0f ? this.magic_trust : builder.extension_neg;
            this.extension_trust = builder.extension_trust == 0.0f ? this.extension_trust : builder.extension_trust;
            this.meta_trust = builder.meta_trust == 0.0f ? this.meta_trust : builder.meta_trust;
            this.magic_neg = builder.magic_neg == 0.0f ? this.magic_neg : builder.magic_neg;
            this.extension_neg = builder.extension_neg == 0.0f ? this.extension_neg : builder.extension_neg;
            this.meta_neg = builder.meta_neg == 0.0f ? this.meta_neg : builder.meta_neg;
            this.threshold = builder.threshold == 0.0f ? this.threshold : builder.threshold;
        }
    }

    private void initializeDefaultProbabilityParameters() {
        this.priorMagicFileType = 0.5f;
        this.priorExtensionFileType = 0.5f;
        this.priorMetaFileType = 0.5f;
        this.magic_trust = DEFAULT_MAGIC_TRUST;
        this.extension_trust = 0.8f;
        this.meta_trust = 0.8f;
        this.magic_neg = 0.100000024f;
        this.extension_neg = 0.19999999f;
        this.meta_neg = 0.19999999f;
        this.threshold = 0.5001f;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0059  */
    @Override // org.apache.tika.detect.Detector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.apache.tika.mime.MediaType detect(java.io.InputStream r6, org.apache.tika.metadata.Metadata r7) throws java.io.IOException {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r6 == 0) goto L28
            org.apache.tika.mime.MimeTypes r1 = r5.mimeTypes
            int r1 = r1.getMinLength()
            r6.mark(r1)
            org.apache.tika.mime.MimeTypes r1 = r5.mimeTypes     // Catch: java.lang.Throwable -> L23
            byte[] r1 = r1.readMagicHeader(r6)     // Catch: java.lang.Throwable -> L23
            org.apache.tika.mime.MimeTypes r2 = r5.mimeTypes     // Catch: java.lang.Throwable -> L23
            java.util.List r1 = r2.getMimeType(r1)     // Catch: java.lang.Throwable -> L23
            r0.addAll(r1)     // Catch: java.lang.Throwable -> L23
            r6.reset()
            goto L28
        L23:
            r7 = move-exception
            r6.reset()
            throw r7
        L28:
            java.lang.String r6 = "resourceName"
            java.lang.String r6 = r7.get(r6)
            r1 = 0
            if (r6 == 0) goto L59
            java.net.URI r2 = new java.net.URI     // Catch: java.net.URISyntaxException -> L50
            r2.<init>(r6)     // Catch: java.net.URISyntaxException -> L50
            java.lang.String r2 = r2.getPath()     // Catch: java.net.URISyntaxException -> L50
            if (r2 == 0) goto L4f
            r3 = 47
            int r3 = r2.lastIndexOf(r3)     // Catch: java.net.URISyntaxException -> L50
            int r3 = r3 + 1
            int r4 = r2.length()     // Catch: java.net.URISyntaxException -> L50
            if (r3 >= r4) goto L4f
            java.lang.String r6 = r2.substring(r3)     // Catch: java.net.URISyntaxException -> L50
            goto L50
        L4f:
            r6 = r1
        L50:
            if (r6 == 0) goto L59
            org.apache.tika.mime.MimeTypes r2 = r5.mimeTypes
            org.apache.tika.mime.MimeType r6 = r2.getMimeType(r6)
            goto L5a
        L59:
            r6 = r1
        L5a:
            java.lang.String r2 = "Content-Type"
            java.lang.String r7 = r7.get(r2)
            if (r7 == 0) goto L68
            org.apache.tika.mime.MimeTypes r2 = r5.mimeTypes     // Catch: org.apache.tika.mime.MimeTypeException -> L68
            org.apache.tika.mime.MimeType r1 = r2.forName(r7)     // Catch: org.apache.tika.mime.MimeTypeException -> L68
        L68:
            org.apache.tika.mime.MediaType r6 = r5.applyProbilities(r0, r6, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.mime.ProbabilisticMimeDetectionSelector.detect(java.io.InputStream, org.apache.tika.metadata.Metadata):org.apache.tika.mime.MediaType");
    }

    private MediaType applyProbilities(List<MimeType> list, MimeType mimeType, MimeType mimeType2) {
        MediaType mediaType;
        float[] fArr;
        List<MimeType> list2 = list;
        MimeType mimeType3 = mimeType;
        MediaType type = mimeType3 == null ? null : mimeType3.getType();
        MediaType type2 = mimeType2 != null ? mimeType2.getType() : null;
        int size = list2.size();
        float f = this.magic_trust;
        float f2 = this.magic_neg;
        float f3 = this.extension_trust;
        float f4 = this.extension_neg;
        float f5 = this.meta_trust;
        float f6 = this.meta_neg;
        if (type == null || type.compareTo(this.rootMediaType) == 0) {
            f3 = 1.0f;
            f4 = 1.0f;
        }
        if (type2 == null || type2.compareTo(this.rootMediaType) == 0) {
            f5 = 1.0f;
            f6 = 1.0f;
        }
        MediaType mediaType2 = this.rootMediaType;
        float f7 = -1.0f;
        if (!list2.isEmpty()) {
            int i = 0;
            MediaType mediaType3 = type2;
            while (i < size) {
                MediaType type3 = list2.get(i).getType();
                int i2 = size;
                MediaTypeRegistry mediaTypeRegistry = this.mimeTypes.getMediaTypeRegistry();
                float f8 = f;
                if (type3 == null || !type3.equals(this.rootMediaType)) {
                    if (type != null) {
                        if (type.equals(type3) || mediaTypeRegistry.isSpecializationOf(type, type3)) {
                            list2.set(i, mimeType3);
                        } else if (mediaTypeRegistry.isSpecializationOf(type3, type)) {
                            type = type3;
                        }
                    }
                    if (mediaType3 != null) {
                        if (mediaType3.equals(type3) || mediaTypeRegistry.isSpecializationOf(mediaType3, type3)) {
                            list2.set(i, mimeType2);
                        } else if (mediaTypeRegistry.isSpecializationOf(type3, mediaType3)) {
                            mediaType3 = type3;
                        }
                    }
                    f = f8;
                    mediaType = mediaType3;
                } else {
                    f = 1.0f;
                    f2 = 1.0f;
                    mediaType = mediaType3;
                }
                float[] fArr2 = new float[3];
                float[] fArr3 = new float[3];
                float[] fArr4 = new float[3];
                MediaType type4 = list2.get(i).getType();
                if (i > 0) {
                    float f9 = this.changeRate;
                    f *= 1.0f - f9;
                    f2 *= f9 + 1.0f;
                }
                if (type4 != null && f != 1.0f) {
                    fArr3[0] = f;
                    fArr4[0] = f2;
                    if (mediaType != null && f5 != 1.0f) {
                        if (type4.equals(mediaType)) {
                            fArr3[1] = f5;
                            fArr4[1] = f6;
                        } else {
                            fArr3[1] = 1.0f - f5;
                            fArr4[1] = 1.0f - f6;
                        }
                    } else {
                        fArr3[1] = 1.0f;
                        fArr4[1] = 1.0f;
                    }
                    if (type != null && f3 != 1.0f) {
                        if (type4.equals(type)) {
                            fArr3[2] = f3;
                            fArr4[2] = f4;
                        } else {
                            fArr3[2] = 1.0f - f3;
                            fArr4[2] = 1.0f - f4;
                        }
                    } else {
                        fArr3[2] = 1.0f;
                        fArr4[2] = 1.0f;
                    }
                } else {
                    fArr2[0] = 0.1f;
                }
                float[] fArr5 = new float[3];
                float[] fArr6 = new float[3];
                if (mimeType2 != null && f5 != 1.0f) {
                    fArr5[1] = f5;
                    fArr6[1] = f6;
                    if (type4 != null && f != 1.0f) {
                        if (mediaType.equals(type4)) {
                            fArr5[0] = f;
                            fArr6[0] = f2;
                        } else {
                            fArr5[0] = 1.0f - f;
                            fArr6[0] = 1.0f - f2;
                        }
                    } else {
                        fArr5[0] = 1.0f;
                        fArr6[0] = 1.0f;
                    }
                    if (type != null && f3 != 1.0f) {
                        if (mediaType.equals(type)) {
                            fArr5[2] = f3;
                            fArr6[2] = f4;
                        } else {
                            fArr5[2] = 1.0f - f3;
                            fArr6[2] = 1.0f - f4;
                        }
                    } else {
                        fArr5[2] = 1.0f;
                        fArr6[2] = 1.0f;
                    }
                } else {
                    fArr2[1] = 0.1f;
                }
                float[] fArr7 = new float[3];
                float[] fArr8 = new float[3];
                if (type != null && f3 != 1.0f) {
                    fArr7[2] = f3;
                    fArr8[2] = f4;
                    if (type4 != null && f != 1.0f) {
                        if (type4.equals(type)) {
                            fArr7[0] = f;
                            fArr8[0] = f2;
                        } else {
                            fArr7[0] = 1.0f - f;
                            fArr8[0] = 1.0f - f2;
                        }
                    } else {
                        fArr7[0] = 1.0f;
                        fArr8[0] = 1.0f;
                    }
                    if (mediaType != null && f5 != 1.0f) {
                        if (mediaType.equals(type)) {
                            fArr7[1] = f5;
                            fArr8[1] = f6;
                        } else {
                            fArr7[1] = 1.0f - f5;
                            fArr8[1] = 1.0f - f6;
                        }
                    } else {
                        fArr7[1] = 1.0f;
                        fArr8[1] = 1.0f;
                    }
                } else {
                    fArr2[2] = 0.1f;
                }
                float f10 = this.priorMagicFileType;
                float f11 = 1.0f - f10;
                if (fArr2[0] == 0.0f) {
                    float f12 = f10;
                    fArr = fArr8;
                    for (int i3 = 0; i3 < 3; i3++) {
                        float f13 = fArr3[i3];
                        f12 *= f13;
                        if (f13 != 1.0f) {
                            f11 *= fArr4[i3];
                        }
                    }
                    fArr2[0] = f12 / (f12 + f11);
                } else {
                    fArr = fArr8;
                }
                float f14 = fArr2[0];
                if (f7 < f14) {
                    f7 = f14;
                    mediaType2 = type4;
                }
                float f15 = this.priorMetaFileType;
                float f16 = 1.0f - f15;
                if (fArr2[1] == 0.0f) {
                    float f17 = f15;
                    for (int i4 = 0; i4 < 3; i4++) {
                        float f18 = fArr5[i4];
                        f17 *= f18;
                        if (f18 != 1.0f) {
                            f16 *= fArr6[i4];
                        }
                    }
                    fArr2[1] = f17 / (f17 + f16);
                }
                float f19 = fArr2[1];
                if (f7 < f19) {
                    f7 = f19;
                    mediaType2 = mediaType;
                }
                float f20 = this.priorExtensionFileType;
                float f21 = 1.0f - f20;
                if (fArr2[2] == 0.0f) {
                    float f22 = f20;
                    for (int i5 = 0; i5 < 3; i5++) {
                        float f23 = fArr7[i5];
                        f22 *= f23;
                        if (f23 != 1.0f) {
                            f21 *= fArr[i5];
                        }
                    }
                    fArr2[2] = f22 / (f22 + f21);
                }
                float f24 = fArr2[2];
                if (f7 < f24) {
                    f7 = f24;
                    mediaType2 = type;
                }
                i++;
                list2 = list;
                mimeType3 = mimeType;
                size = i2;
                mediaType3 = mediaType;
            }
        }
        return f7 < this.threshold ? this.rootMediaType : mediaType2;
    }

    public MediaTypeRegistry getMediaTypeRegistry() {
        return this.mimeTypes.getMediaTypeRegistry();
    }

    public static class Builder {
        private float extension_neg;
        private float extension_trust;
        private float magic_neg;
        private float magic_trust;
        private float meta_neg;
        private float meta_trust;
        private float priorExtensionFileType;
        private float priorMagicFileType;
        private float priorMetaFileType;
        private float threshold;

        public synchronized Builder priorMagicFileType(float f) {
            this.priorMagicFileType = f;
            return this;
        }

        public synchronized Builder priorExtensionFileType(float f) {
            this.priorExtensionFileType = f;
            return this;
        }

        public synchronized Builder priorMetaFileType(float f) {
            this.priorMetaFileType = f;
            return this;
        }

        public synchronized Builder magic_trust(float f) {
            this.magic_trust = f;
            return this;
        }

        public synchronized Builder extension_trust(float f) {
            this.extension_trust = f;
            return this;
        }

        public synchronized Builder meta_trust(float f) {
            this.meta_trust = f;
            return this;
        }

        public synchronized Builder magic_neg(float f) {
            this.magic_neg = f;
            return this;
        }

        public synchronized Builder extension_neg(float f) {
            this.extension_neg = f;
            return this;
        }

        public synchronized Builder meta_neg(float f) {
            this.meta_neg = f;
            return this;
        }

        public synchronized Builder threshold(float f) {
            this.threshold = f;
            return this;
        }

        public ProbabilisticMimeDetectionSelector build2() {
            return new ProbabilisticMimeDetectionSelector(this);
        }
    }
}
