package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ColorUtils {
    static final double[][] SRGB_TO_XYZ = {new double[]{0.41233895d, 0.35762064d, 0.18051042d}, new double[]{0.2126d, 0.7152d, 0.0722d}, new double[]{0.01932141d, 0.11916382d, 0.95034478d}};
    static final double[][] XYZ_TO_SRGB = {new double[]{3.2413774792388685d, -1.5376652402851851d, -0.49885366846268053d}, new double[]{-0.9691452513005321d, 1.8758853451067872d, 0.04156585616912061d}, new double[]{0.05562093689691305d, -0.20395524564742123d, 1.0571799111220335d}};
    static final double[] WHITE_POINT_D65 = {95.047d, 100.0d, 108.883d};

    private ColorUtils() {
    }

    public static int alphaFromArgb(int i2) {
        return (i2 >> 24) & 255;
    }

    public static int argbFromLab(double d, double d2, double d3) {
        double[] dArr = WHITE_POINT_D65;
        double d4 = (d + 16.0d) / 116.0d;
        double d5 = d4 - (d3 / 200.0d);
        return argbFromXyz(labInvf((d2 / 500.0d) + d4) * dArr[0], labInvf(d4) * dArr[1], labInvf(d5) * dArr[2]);
    }

    public static int argbFromLinrgb(double[] dArr) {
        return argbFromRgb(delinearized(dArr[0]), delinearized(dArr[1]), delinearized(dArr[2]));
    }

    public static int argbFromLstar(double d) {
        int iDelinearized = delinearized(yFromLstar(d));
        return argbFromRgb(iDelinearized, iDelinearized, iDelinearized);
    }

    public static int argbFromRgb(int i2, int i3, int i4) {
        return ((i2 & 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((i3 & 255) << 8) | (i4 & 255);
    }

    public static int argbFromXyz(double d, double d2, double d3) {
        double[][] dArr = XYZ_TO_SRGB;
        double[] dArr2 = dArr[0];
        double d4 = (dArr2[2] * d3) + (dArr2[1] * d2) + (dArr2[0] * d);
        double[] dArr3 = dArr[1];
        double d5 = (dArr3[2] * d3) + (dArr3[1] * d2) + (dArr3[0] * d);
        double[] dArr4 = dArr[2];
        return argbFromRgb(delinearized(d4), delinearized(d5), delinearized((dArr4[2] * d3) + (dArr4[1] * d2) + (dArr4[0] * d)));
    }

    public static int blueFromArgb(int i2) {
        return i2 & 255;
    }

    public static int delinearized(double d) {
        double d2 = d / 100.0d;
        return MathUtils.clampInt(0, 255, (int) Math.round((d2 <= 0.0031308d ? d2 * 12.92d : (Math.pow(d2, 0.4166666666666667d) * 1.055d) - 0.055d) * 255.0d));
    }

    public static int greenFromArgb(int i2) {
        return (i2 >> 8) & 255;
    }

    public static boolean isOpaque(int i2) {
        return alphaFromArgb(i2) >= 255;
    }

    public static double labF(double d) {
        return d > 0.008856451679035631d ? Math.pow(d, 0.3333333333333333d) : ((d * 903.2962962962963d) + 16.0d) / 116.0d;
    }

    public static double[] labFromArgb(int i2) {
        double dLinearized = linearized(redFromArgb(i2));
        double dLinearized2 = linearized(greenFromArgb(i2));
        double dLinearized3 = linearized(blueFromArgb(i2));
        double[][] dArr = SRGB_TO_XYZ;
        double[] dArr2 = dArr[0];
        double d = (dArr2[2] * dLinearized3) + (dArr2[1] * dLinearized2) + (dArr2[0] * dLinearized);
        double[] dArr3 = dArr[1];
        double d2 = (dArr3[2] * dLinearized3) + (dArr3[1] * dLinearized2) + (dArr3[0] * dLinearized);
        double[] dArr4 = dArr[2];
        double d3 = (dArr4[2] * dLinearized3) + (dArr4[1] * dLinearized2) + (dArr4[0] * dLinearized);
        double[] dArr5 = WHITE_POINT_D65;
        double d4 = d / dArr5[0];
        double d5 = d2 / dArr5[1];
        double d6 = d3 / dArr5[2];
        double dLabF = labF(d4);
        double dLabF2 = labF(d5);
        return new double[]{(116.0d * dLabF2) - 16.0d, (dLabF - dLabF2) * 500.0d, (dLabF2 - labF(d6)) * 200.0d};
    }

    public static double labInvf(double d) {
        double d2 = d * d * d;
        return d2 > 0.008856451679035631d ? d2 : ((d * 116.0d) - 16.0d) / 903.2962962962963d;
    }

    public static double linearized(int i2) {
        double d = ((double) i2) / 255.0d;
        return (d <= 0.040449936d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d)) * 100.0d;
    }

    public static double lstarFromArgb(int i2) {
        return (labF(xyzFromArgb(i2)[1] / 100.0d) * 116.0d) - 16.0d;
    }

    public static double lstarFromY(double d) {
        return (labF(d / 100.0d) * 116.0d) - 16.0d;
    }

    public static int redFromArgb(int i2) {
        return (i2 >> 16) & 255;
    }

    public static double[] whitePointD65() {
        return WHITE_POINT_D65;
    }

    public static double[] xyzFromArgb(int i2) {
        return MathUtils.matrixMultiply(new double[]{linearized(redFromArgb(i2)), linearized(greenFromArgb(i2)), linearized(blueFromArgb(i2))}, SRGB_TO_XYZ);
    }

    public static double yFromLstar(double d) {
        return labInvf((d + 16.0d) / 116.0d) * 100.0d;
    }
}
