package org.apache.tika.detect;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public class NNTrainedModel extends TrainedModel {
    private final float[][] Theta1;
    private final float[][] Theta2;
    private final int numOfHidden;
    private final int numOfInputs;
    private final int numOfOutputs;

    @Override // org.apache.tika.detect.TrainedModel
    public double predict(double[] dArr) {
        return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    }

    public NNTrainedModel(int i, int i2, int i3, float[] fArr) {
        this.numOfInputs = i;
        this.numOfHidden = i2;
        this.numOfOutputs = i3;
        this.Theta1 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, i2, i + 1);
        this.Theta2 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, i3, i2 + 1);
        populateThetas(fArr);
    }

    private void populateThetas(float[] fArr) {
        float[][] fArr2 = this.Theta1;
        int length = fArr2.length;
        int length2 = fArr2[0].length;
        int i = 0;
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = 0; i3 < length; i3++) {
                this.Theta1[i3][i2] = fArr[i];
                i++;
            }
        }
        float[][] fArr3 = this.Theta2;
        int length3 = fArr3.length;
        int length4 = fArr3[0].length;
        for (int i4 = 0; i4 < length4; i4++) {
            for (int i5 = 0; i5 < length3; i5++) {
                this.Theta2[i5][i4] = fArr[i];
                i++;
            }
        }
    }

    @Override // org.apache.tika.detect.TrainedModel
    public float predict(float[] fArr) {
        float[][] fArr2 = this.Theta1;
        int length = fArr2.length;
        int length2 = fArr2[0].length;
        float[] fArr3 = new float[length + 1];
        fArr3[0] = 1.0f;
        int i = 0;
        while (true) {
            double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            if (i >= length) {
                break;
            }
            for (int i2 = 0; i2 < length2; i2++) {
                d += this.Theta1[i][i2] * fArr[i2];
            }
            i++;
            fArr3[i] = (float) (1.0d / (Math.exp(-d) + 1.0d));
        }
        float[][] fArr4 = this.Theta2;
        int length3 = fArr4.length;
        int length4 = fArr4[0].length;
        float[] fArr5 = new float[length3];
        for (int i3 = 0; i3 < length3; i3++) {
            double d2 = 0.0d;
            for (int i4 = 0; i4 < length4; i4++) {
                d2 += this.Theta2[i3][i4] * fArr3[i4];
            }
            fArr5[i3] = (float) (1.0d / (Math.exp(-d2) + 1.0d));
        }
        return fArr5[0];
    }
}
