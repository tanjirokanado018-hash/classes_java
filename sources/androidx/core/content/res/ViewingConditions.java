package androidx.core.content.res;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class ViewingConditions {
    static final ViewingConditions DEFAULT = make(CamUtils.WHITE_POINT_D65, (float) ((((double) CamUtils.yFromLStar(50.0f)) * 63.66197723675813d) / 100.0d), 50.0f, 2.0f, false);
    private final float mAw;
    private final float mC;
    private final float mFl;
    private final float mFlRoot;
    private final float mN;
    private final float mNbb;
    private final float mNc;
    private final float mNcb;
    private final float[] mRgbD;
    private final float mZ;

    private ViewingConditions(float f2, float f3, float f4, float f5, float f6, float f7, float[] fArr, float f8, float f9, float f10) {
        this.mN = f2;
        this.mAw = f3;
        this.mNbb = f4;
        this.mNcb = f5;
        this.mC = f6;
        this.mNc = f7;
        this.mRgbD = fArr;
        this.mFl = f8;
        this.mFlRoot = f9;
        this.mZ = f10;
    }

    @NonNull
    public static ViewingConditions make(@NonNull float[] fArr, float f2, float f3, float f4, boolean z2) {
        float[][] fArr2 = CamUtils.XYZ_TO_CAM16RGB;
        float f5 = fArr[0];
        float[] fArr3 = fArr2[0];
        float f6 = fArr3[0] * f5;
        float f7 = fArr[1];
        float f8 = (fArr3[1] * f7) + f6;
        float f9 = fArr[2];
        float f10 = (fArr3[2] * f9) + f8;
        float[] fArr4 = fArr2[1];
        float f11 = (fArr4[2] * f9) + (fArr4[1] * f7) + (fArr4[0] * f5);
        float[] fArr5 = fArr2[2];
        float f12 = (f9 * fArr5[2]) + (f7 * fArr5[1]) + (f5 * fArr5[0]);
        float f13 = (f4 / 10.0f) + 0.8f;
        float fLerp = ((double) f13) >= 0.9d ? CamUtils.lerp(0.59f, 0.69f, (f13 - 0.9f) * 10.0f) : CamUtils.lerp(0.525f, 0.59f, (f13 - 0.8f) * 10.0f);
        float fExp = z2 ? 1.0f : (1.0f - (((float) Math.exp(((-f2) - 42.0f) / 92.0f)) * 0.2777778f)) * f13;
        double d = fExp;
        if (d > 1.0d) {
            fExp = 1.0f;
        } else if (d < 0.0d) {
            fExp = 0.0f;
        }
        float[] fArr6 = {(((100.0f / f10) * fExp) + 1.0f) - fExp, (((100.0f / f11) * fExp) + 1.0f) - fExp, (((100.0f / f12) * fExp) + 1.0f) - fExp};
        float f14 = 1.0f / ((5.0f * f2) + 1.0f);
        float f15 = f14 * f14 * f14 * f14;
        float f16 = 1.0f - f15;
        float fCbrt = (0.1f * f16 * f16 * ((float) Math.cbrt(((double) f2) * 5.0d))) + (f15 * f2);
        float fYFromLStar = CamUtils.yFromLStar(f3) / fArr[1];
        double d2 = fYFromLStar;
        float fSqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float fPow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr7 = {(float) Math.pow(((double) ((fArr6[0] * fCbrt) * f10)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr6[1] * fCbrt) * f11)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr6[2] * fCbrt) * f12)) / 100.0d, 0.42d)};
        float f17 = fArr7[0];
        float f18 = (f17 * 400.0f) / (f17 + 27.13f);
        float f19 = fArr7[1];
        float f20 = (f19 * 400.0f) / (f19 + 27.13f);
        float f21 = fArr7[2];
        float[] fArr8 = {f18, f20, (400.0f * f21) / (f21 + 27.13f)};
        return new ViewingConditions(fYFromLStar, ((fArr8[2] * 0.05f) + (fArr8[0] * 2.0f) + fArr8[1]) * fPow, fPow, fPow, fLerp, f13, fArr6, fCbrt, (float) Math.pow(fCbrt, 0.25d), fSqrt);
    }

    public float getAw() {
        return this.mAw;
    }

    public float getC() {
        return this.mC;
    }

    public float getFl() {
        return this.mFl;
    }

    public float getFlRoot() {
        return this.mFlRoot;
    }

    public float getN() {
        return this.mN;
    }

    public float getNbb() {
        return this.mNbb;
    }

    public float getNc() {
        return this.mNc;
    }

    public float getNcb() {
        return this.mNcb;
    }

    @NonNull
    public float[] getRgbD() {
        return this.mRgbD;
    }

    public float getZ() {
        return this.mZ;
    }
}
