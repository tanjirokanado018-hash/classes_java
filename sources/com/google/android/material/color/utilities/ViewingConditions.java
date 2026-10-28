package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT = defaultWithBackgroundLstar(50.0d);
    private final double aw;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final double f95c;
    private final double fl;
    private final double flRoot;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    private final double f96n;
    private final double nbb;
    private final double nc;
    private final double ncb;
    private final double[] rgbD;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    private final double f97z;

    private ViewingConditions(double d, double d2, double d3, double d4, double d5, double d6, double[] dArr, double d7, double d8, double d9) {
        this.f96n = d;
        this.aw = d2;
        this.nbb = d3;
        this.ncb = d4;
        this.f95c = d5;
        this.nc = d6;
        this.rgbD = dArr;
        this.fl = d7;
        this.flRoot = d8;
        this.f97z = d9;
    }

    public static ViewingConditions defaultWithBackgroundLstar(double d) {
        return make(ColorUtils.whitePointD65(), (ColorUtils.yFromLstar(50.0d) * 63.66197723675813d) / 100.0d, d, 2.0d, false);
    }

    public static ViewingConditions make(double[] dArr, double d, double d2, double d3, boolean z2) {
        double d4;
        double d5;
        double d6;
        double dMax = Math.max(0.1d, d2);
        double[][] dArr2 = Cam16.XYZ_TO_CAM16RGB;
        double d7 = dArr[0];
        double[] dArr3 = dArr2[0];
        double d8 = dArr3[0] * d7;
        double d9 = dArr[1];
        double d10 = (dArr3[1] * d9) + d8;
        double d11 = dArr[2];
        double d12 = (dArr3[2] * d11) + d10;
        double[] dArr4 = dArr2[1];
        double d13 = (dArr4[2] * d11) + (dArr4[1] * d9) + (dArr4[0] * d7);
        double[] dArr5 = dArr2[2];
        double d14 = (d11 * dArr5[2]) + (d9 * dArr5[1]) + (d7 * dArr5[0]);
        double d15 = (d3 / 10.0d) + 0.8d;
        if (d15 >= 0.9d) {
            d4 = (d15 - 0.9d) * 10.0d;
            d5 = 0.59d;
            d6 = 0.69d;
        } else {
            d4 = (d15 - 0.8d) * 10.0d;
            d5 = 0.525d;
            d6 = 0.59d;
        }
        double dLerp = MathUtils.lerp(d5, d6, d4);
        double dClampDouble = MathUtils.clampDouble(0.0d, 1.0d, z2 ? 1.0d : (1.0d - (Math.exp(((-d) - 42.0d) / 92.0d) * 0.2777777777777778d)) * d15);
        double[] dArr6 = {(((100.0d / d12) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d13) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d14) * dClampDouble) + 1.0d) - dClampDouble};
        double d16 = 5.0d * d;
        double d17 = 1.0d / (d16 + 1.0d);
        double d18 = d17 * d17 * d17 * d17;
        double d19 = 1.0d - d18;
        double dCbrt = (Math.cbrt(d16) * 0.1d * d19 * d19) + (d18 * d);
        double dYFromLstar = ColorUtils.yFromLstar(dMax) / dArr[1];
        double dSqrt = Math.sqrt(dYFromLstar) + 1.48d;
        double dPow = 0.725d / Math.pow(dYFromLstar, 0.2d);
        double[] dArr7 = {Math.pow(((dArr6[0] * dCbrt) * d12) / 100.0d, 0.42d), Math.pow(((dArr6[1] * dCbrt) * d13) / 100.0d, 0.42d), Math.pow(((dArr6[2] * dCbrt) * d14) / 100.0d, 0.42d)};
        double d20 = dArr7[0];
        double d21 = (d20 * 400.0d) / (d20 + 27.13d);
        double d22 = dArr7[1];
        double d23 = (d22 * 400.0d) / (d22 + 27.13d);
        double d24 = dArr7[2];
        double[] dArr8 = {d21, d23, (400.0d * d24) / (d24 + 27.13d)};
        return new ViewingConditions(dYFromLstar, ((dArr8[2] * 0.05d) + (dArr8[0] * 2.0d) + dArr8[1]) * dPow, dPow, dPow, dLerp, d15, dArr6, dCbrt, Math.pow(dCbrt, 0.25d), dSqrt);
    }

    public double getAw() {
        return this.aw;
    }

    public double getC() {
        return this.f95c;
    }

    public double getFl() {
        return this.fl;
    }

    public double getFlRoot() {
        return this.flRoot;
    }

    public double getN() {
        return this.f96n;
    }

    public double getNbb() {
        return this.nbb;
    }

    public double getNc() {
        return this.nc;
    }

    public double getNcb() {
        return this.ncb;
    }

    public double[] getRgbD() {
        return this.rgbD;
    }

    public double getZ() {
        return this.f97z;
    }
}
