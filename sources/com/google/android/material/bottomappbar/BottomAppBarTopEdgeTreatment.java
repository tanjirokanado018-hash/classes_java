package com.google.android.material.bottomappbar;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class BottomAppBarTopEdgeTreatment extends EdgeTreatment implements Cloneable {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private static final float ROUNDED_CORNER_FAB_OFFSET = 1.75f;
    private float cradleVerticalOffset;
    private float fabCornerSize = -1.0f;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f2, float f3, float f4) {
        this.fabMargin = f2;
        this.roundedCornerRadius = f3;
        setCradleVerticalOffset(f4);
        this.horizontalOffset = 0.0f;
    }

    public float getCradleVerticalOffset() {
        return this.cradleVerticalOffset;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f2, float f3, float f4, @NonNull ShapePath shapePath) {
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13 = this.fabDiameter;
        if (f13 == 0.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f14 = ((this.fabMargin * 2.0f) + f13) / 2.0f;
        float f15 = f4 * this.roundedCornerRadius;
        float f16 = f3 + this.horizontalOffset;
        float fA = androidx.appcompat.graphics.drawable.a.a(1.0f, f4, f14, this.cradleVerticalOffset * f4);
        if (fA / f14 >= 1.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f17 = this.fabCornerSize;
        float f18 = f17 * f4;
        boolean z2 = f17 == -1.0f || Math.abs((f17 * 2.0f) - f13) < 0.1f;
        if (z2) {
            f5 = fA;
            f6 = 0.0f;
        } else {
            f6 = ROUNDED_CORNER_FAB_OFFSET;
            f5 = 0.0f;
        }
        float f19 = f14 + f15;
        float f20 = f5 + f15;
        float fSqrt = (float) Math.sqrt((f19 * f19) - (f20 * f20));
        float f21 = f16 - fSqrt;
        float f22 = f16 + fSqrt;
        float degrees = (float) Math.toDegrees(Math.atan(fSqrt / f20));
        float f23 = (90.0f - degrees) + f6;
        shapePath.lineTo(f21, 0.0f);
        float f24 = f15 * 2.0f;
        shapePath.addArc(f21 - f15, 0.0f, f21 + f15, f24, 270.0f, degrees);
        if (z2) {
            f8 = f16 - f14;
            f9 = (-f14) - f5;
            f7 = f16 + f14;
            f10 = f14 - f5;
            f12 = 180.0f - f23;
            f11 = (f23 * 2.0f) - 180.0f;
        } else {
            float f25 = this.fabMargin;
            float f26 = f18 * 2.0f;
            float f27 = f16 - f14;
            shapePath.addArc(f27, -(f18 + f25), f27 + f25 + f26, f25 + f18, 180.0f - f23, ((f23 * 2.0f) - 180.0f) / 2.0f);
            f7 = f16 + f14;
            float f28 = this.fabMargin;
            shapePath.lineTo(f7 - ((f28 / 2.0f) + f18), f28 + f18);
            float f29 = this.fabMargin;
            f8 = f7 - (f26 + f29);
            f9 = -(f18 + f29);
            f10 = f29 + f18;
            f11 = f23 - 90.0f;
            f12 = 90.0f;
        }
        shapePath.addArc(f8, f9, f7, f10, f12, f11);
        shapePath.addArc(f22 - f15, 0.0f, f22 + f15, f24, 270.0f - degrees, degrees);
        shapePath.lineTo(f2, 0.0f);
    }

    public float getFabCornerRadius() {
        return this.fabCornerSize;
    }

    public float getFabCradleMargin() {
        return this.fabMargin;
    }

    public float getFabCradleRoundedCornerRadius() {
        return this.roundedCornerRadius;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getFabDiameter() {
        return this.fabDiameter;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getHorizontalOffset() {
        return this.horizontalOffset;
    }

    public void setCradleVerticalOffset(@FloatRange(from = 0.0d) float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.cradleVerticalOffset = f2;
    }

    public void setFabCornerSize(float f2) {
        this.fabCornerSize = f2;
    }

    public void setFabCradleMargin(float f2) {
        this.fabMargin = f2;
    }

    public void setFabCradleRoundedCornerRadius(float f2) {
        this.roundedCornerRadius = f2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setFabDiameter(float f2) {
        this.fabDiameter = f2;
    }

    public void setHorizontalOffset(float f2) {
        this.horizontalOffset = f2;
    }
}
