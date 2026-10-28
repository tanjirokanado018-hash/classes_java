package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MarkerEdgeTreatment extends EdgeTreatment {
    private final float radius;

    public MarkerEdgeTreatment(float f2) {
        this.radius = f2 - 0.001f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public boolean forceIntersection() {
        return true;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f2, float f3, float f4, @NonNull ShapePath shapePath) {
        float fSqrt = (float) ((Math.sqrt(2.0d) * ((double) this.radius)) / 2.0d);
        float fSqrt2 = (float) Math.sqrt(Math.pow(this.radius, 2.0d) - Math.pow(fSqrt, 2.0d));
        shapePath.reset(f3 - fSqrt, ((float) (-((Math.sqrt(2.0d) * ((double) this.radius)) - ((double) this.radius)))) + fSqrt2);
        shapePath.lineTo(f3, (float) (-((Math.sqrt(2.0d) * ((double) this.radius)) - ((double) this.radius))));
        shapePath.lineTo(f3 + fSqrt, ((float) (-((Math.sqrt(2.0d) * ((double) this.radius)) - ((double) this.radius)))) + fSqrt2);
    }
}
