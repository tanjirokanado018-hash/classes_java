package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import androidx.annotation.NonNull;
import androidx.appcompat.graphics.drawable.a;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MatrixEvaluator implements TypeEvaluator<Matrix> {
    private final float[] tempStartValues = new float[9];
    private final float[] tempEndValues = new float[9];
    private final Matrix tempMatrix = new Matrix();

    @Override // android.animation.TypeEvaluator
    @NonNull
    public Matrix evaluate(float f2, @NonNull Matrix matrix, @NonNull Matrix matrix2) {
        matrix.getValues(this.tempStartValues);
        matrix2.getValues(this.tempEndValues);
        for (int i2 = 0; i2 < 9; i2++) {
            float[] fArr = this.tempEndValues;
            float f3 = fArr[i2];
            float f4 = this.tempStartValues[i2];
            fArr[i2] = a.a(f3, f4, f2, f4);
        }
        this.tempMatrix.setValues(this.tempEndValues);
        return this.tempMatrix;
    }
}
