package org.apache.tika.detect;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class NNExampleModelDetector extends TrainedModelDetector {
    private static final String EXAMPLE_NNMODEL_FILE = "tika-example.nnmodel";
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) NNExampleModelDetector.class);
    private static final long serialVersionUID = 1;

    public NNExampleModelDetector() {
    }

    public NNExampleModelDetector(Path path) {
        loadDefaultModels(path);
    }

    public NNExampleModelDetector(File file) {
        loadDefaultModels(file);
    }

    @Override // org.apache.tika.detect.TrainedModelDetector
    public void loadDefaultModels(InputStream inputStream) throws IOException, NumberFormatException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        NNTrainedModelBuilder nNTrainedModelBuilder = new NNTrainedModelBuilder();
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return;
                }
                String strTrim = line.trim();
                if (strTrim.startsWith("#")) {
                    readDescription(nNTrainedModelBuilder, strTrim);
                } else {
                    readNNParams(nNTrainedModelBuilder, strTrim);
                    super.registerModels(nNTrainedModelBuilder.getType(), nNTrainedModelBuilder.build());
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to read the default media type registry", e);
            }
        }
    }

    @Override // org.apache.tika.detect.TrainedModelDetector
    public void loadDefaultModels(ClassLoader classLoader) throws IOException {
        if (classLoader == null) {
            classLoader = TrainedModelDetector.class.getClassLoader();
        }
        String str = TrainedModelDetector.class.getPackage().getName().replace(FilenameUtils.EXTENSION_SEPARATOR, IOUtils.DIR_SEPARATOR_UNIX) + "/";
        URL resource = classLoader.getResource(str + EXAMPLE_NNMODEL_FILE);
        Objects.requireNonNull(resource, "required resource " + str + "tika-example.nnmodel not found");
        try {
            InputStream inputStreamOpenStream = resource.openStream();
            try {
                loadDefaultModels(inputStreamOpenStream);
                if (inputStreamOpenStream != null) {
                    inputStreamOpenStream.close();
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the default media type registry", e);
        }
    }

    private void readDescription(NNTrainedModelBuilder nNTrainedModelBuilder, String str) throws NumberFormatException {
        String[] strArrSplit = str.split("\t");
        try {
            MediaType mediaType = MediaType.parse(strArrSplit[1]);
            int i = Integer.parseInt(strArrSplit[2]);
            int i2 = Integer.parseInt(strArrSplit[3]);
            int i3 = Integer.parseInt(strArrSplit[4]);
            nNTrainedModelBuilder.setNumOfInputs(i);
            nNTrainedModelBuilder.setNumOfHidden(i2);
            nNTrainedModelBuilder.setNumOfOutputs(i3);
            nNTrainedModelBuilder.setType(mediaType);
        } catch (Exception e) {
            LOG.warn("Unable to parse the model configuration", (Throwable) e);
            throw new RuntimeException("Unable to parse the model configuration", e);
        }
    }

    private void readNNParams(NNTrainedModelBuilder nNTrainedModelBuilder, String str) {
        String[] strArrSplit = str.split("\t");
        float[] fArr = new float[strArrSplit.length];
        try {
            int i = 0;
            for (String str2 : strArrSplit) {
                fArr[i] = Float.parseFloat(str2);
                i++;
            }
            nNTrainedModelBuilder.setParams(fArr);
        } catch (Exception e) {
            LOG.warn("Unable to parse the model configuration", (Throwable) e);
            throw new RuntimeException("Unable to parse the model configuration", e);
        }
    }
}
