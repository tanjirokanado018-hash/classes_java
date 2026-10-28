package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class CutCornerTreatment extends CornerTreatment {
    float size;

    public CutCornerTreatment() {
        this.size = -1.0f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public void getCornerPath(@NonNull ShapePath shapePath, float f2, float f3, float f4) {
        shapePath.reset(0.0f, f4 * f3, 180.0f, 180.0f - f2);
        double d = f4;
        double d2 = f3;
        shapePath.lineTo((float) (Math.sin(Math.toRadians(f2)) * d * d2), (float) (Math.sin(Math.toRadians(90.0f - f2)) * d * d2));
    }

    @Deprecated
    public CutCornerTreatment(float f2) {
        this.size = f2;
    }
}
