package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class VelocityMatrix {
    private static String TAG = "VelocityMatrix";
    float mDRotate;
    float mDScaleX;
    float mDScaleY;
    float mDTranslateX;
    float mDTranslateY;
    float mRotate;

    public void applyTransform(float f2, float f3, int i2, int i3, float[] fArr) {
        float f4 = fArr[0];
        float f5 = fArr[1];
        float f6 = (f2 - 0.5f) * 2.0f;
        float f7 = (f3 - 0.5f) * 2.0f;
        float f8 = f4 + this.mDTranslateX;
        float f9 = f5 + this.mDTranslateY;
        float f10 = (this.mDScaleX * f6) + f8;
        float f11 = (this.mDScaleY * f7) + f9;
        float radians = (float) Math.toRadians(this.mRotate);
        float radians2 = (float) Math.toRadians(this.mDRotate);
        double d = radians;
        double d2 = i3 * f7;
        float fSin = (((float) ((Math.sin(d) * ((double) ((-i2) * f6))) - (Math.cos(d) * d2))) * radians2) + f10;
        float fCos = (radians2 * ((float) ((Math.cos(d) * ((double) (i2 * f6))) - (Math.sin(d) * d2)))) + f11;
        fArr[0] = fSin;
        fArr[1] = fCos;
    }

    public void clear() {
        this.mDRotate = 0.0f;
        this.mDTranslateY = 0.0f;
        this.mDTranslateX = 0.0f;
        this.mDScaleY = 0.0f;
        this.mDScaleX = 0.0f;
    }

    public void setRotationVelocity(KeyCycleOscillator keyCycleOscillator, float f2) {
        if (keyCycleOscillator != null) {
            this.mDRotate = keyCycleOscillator.getSlope(f2);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f2) {
        if (keyCycleOscillator == null && keyCycleOscillator2 == null) {
            return;
        }
        if (keyCycleOscillator == null) {
            this.mDScaleX = keyCycleOscillator.getSlope(f2);
        }
        if (keyCycleOscillator2 == null) {
            this.mDScaleY = keyCycleOscillator2.getSlope(f2);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f2) {
        if (keyCycleOscillator != null) {
            this.mDTranslateX = keyCycleOscillator.getSlope(f2);
        }
        if (keyCycleOscillator2 != null) {
            this.mDTranslateY = keyCycleOscillator2.getSlope(f2);
        }
    }

    public void setRotationVelocity(SplineSet splineSet, float f2) {
        if (splineSet != null) {
            this.mDRotate = splineSet.getSlope(f2);
            this.mRotate = splineSet.get(f2);
        }
    }

    public void setScaleVelocity(SplineSet splineSet, SplineSet splineSet2, float f2) {
        if (splineSet != null) {
            this.mDScaleX = splineSet.getSlope(f2);
        }
        if (splineSet2 != null) {
            this.mDScaleY = splineSet2.getSlope(f2);
        }
    }

    public void setTranslationVelocity(SplineSet splineSet, SplineSet splineSet2, float f2) {
        if (splineSet != null) {
            this.mDTranslateX = splineSet.getSlope(f2);
        }
        if (splineSet2 != null) {
            this.mDTranslateY = splineSet2.getSlope(f2);
        }
    }
}
