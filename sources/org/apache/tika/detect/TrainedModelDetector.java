package org.apache.tika.detect;

import androidx.core.view.InputDeviceCompat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public abstract class TrainedModelDetector implements Detector {
    private static final long serialVersionUID = 1;
    private final Map<MediaType, TrainedModel> MODEL_MAP = new HashMap();

    public int getMinLength() {
        return Integer.MAX_VALUE;
    }

    public abstract void loadDefaultModels(InputStream inputStream);

    public abstract void loadDefaultModels(ClassLoader classLoader);

    public TrainedModelDetector() {
        loadDefaultModels(getClass().getClassLoader());
    }

    @Override // org.apache.tika.detect.Detector
    public MediaType detect(InputStream inputStream, Metadata metadata) throws IOException {
        if (inputStream == null) {
            return null;
        }
        inputStream.mark(getMinLength());
        float[] byteFrequencies = readByteFrequencies(inputStream);
        MediaType mediaType = MediaType.OCTET_STREAM;
        float f = 0.5f;
        for (Map.Entry<MediaType, TrainedModel> entry : this.MODEL_MAP.entrySet()) {
            MediaType key = entry.getKey();
            float fPredict = entry.getValue().predict(byteFrequencies);
            if (f < fPredict) {
                f = fPredict;
                mediaType = key;
            }
        }
        inputStream.reset();
        return mediaType;
    }

    protected float[] readByteFrequencies(InputStream inputStream) throws IOException {
        ReadableByteChannel readableByteChannelNewChannel = Channels.newChannel(inputStream);
        float[] fArr = new float[InputDeviceCompat.SOURCE_KEYBOARD];
        fArr[0] = 1.0f;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(5120);
        float fMax = -1.0f;
        for (int i = readableByteChannelNewChannel.read(byteBufferAllocate); i != -1; i = readableByteChannelNewChannel.read(byteBufferAllocate)) {
            byteBufferAllocate.flip();
            while (byteBufferAllocate.hasRemaining()) {
                byte b = byteBufferAllocate.get();
                int i2 = b + 1;
                if (b < 0) {
                    i2 = b + 257;
                    fArr[i2] = fArr[i2] + 1.0f;
                } else {
                    fArr[i2] = fArr[i2] + 1.0f;
                }
                fMax = Math.max(fMax, fArr[i2]);
            }
        }
        for (int i3 = 1; i3 < 257; i3++) {
            float f = fArr[i3] / fMax;
            fArr[i3] = f;
            fArr[i3] = (float) Math.sqrt(f);
        }
        return fArr;
    }

    private void writeHisto(float[] fArr) throws IOException {
        BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(new TemporaryResources().createTempFile(), StandardCharsets.UTF_8, new OpenOption[0]);
        try {
            for (float f : fArr) {
                bufferedWriterNewBufferedWriter.write(f + "\t");
            }
            bufferedWriterNewBufferedWriter.write("\r\n");
            if (bufferedWriterNewBufferedWriter != null) {
                bufferedWriterNewBufferedWriter.close();
            }
        } catch (Throwable th) {
            if (bufferedWriterNewBufferedWriter != null) {
                try {
                    bufferedWriterNewBufferedWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void loadDefaultModels(Path path) throws IOException {
        try {
            InputStream inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
            try {
                loadDefaultModels(inputStreamNewInputStream);
                if (inputStreamNewInputStream != null) {
                    inputStreamNewInputStream.close();
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the default media type registry", e);
        }
    }

    public void loadDefaultModels(File file) throws IOException {
        loadDefaultModels(file.toPath());
    }

    protected void registerModels(MediaType mediaType, TrainedModel trainedModel) {
        this.MODEL_MAP.put(mediaType, trainedModel);
    }
}
