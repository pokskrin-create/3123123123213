package org.apache.tika.detect;

import org.apache.tika.mime.MediaType;

/* loaded from: classes4.dex */
public class NNTrainedModelBuilder {
    private int numOfHidden;
    private int numOfInputs;
    private int numOfOutputs;
    private float[] params;
    private MediaType type;

    public MediaType getType() {
        return this.type;
    }

    public void setType(MediaType mediaType) {
        this.type = mediaType;
    }

    public int getNumOfInputs() {
        return this.numOfInputs;
    }

    public void setNumOfInputs(int i) {
        this.numOfInputs = i;
    }

    public int getNumOfHidden() {
        return this.numOfHidden;
    }

    public void setNumOfHidden(int i) {
        this.numOfHidden = i;
    }

    public int getNumOfOutputs() {
        return this.numOfOutputs;
    }

    public void setNumOfOutputs(int i) {
        this.numOfOutputs = i;
    }

    public float[] getParams() {
        return this.params;
    }

    public void setParams(float[] fArr) {
        this.params = fArr;
    }

    public NNTrainedModel build() {
        return new NNTrainedModel(this.numOfInputs, this.numOfHidden, this.numOfOutputs, this.params);
    }
}
