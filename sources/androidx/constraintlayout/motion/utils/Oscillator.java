package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double[] mArea;
    int mType;
    float[] mPeriod = new float[0];
    double[] mPosition = new double[0];
    double PI2 = 6.283185307179586d;
    private boolean mNormalized = false;

    public void addPoint(double d, float f2) {
        int length = this.mPeriod.length + 1;
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, length);
        this.mPeriod = Arrays.copyOf(this.mPeriod, length);
        this.mArea = new double[length];
        double[] dArr = this.mPosition;
        System.arraycopy(dArr, iBinarySearch, dArr, iBinarySearch + 1, (length - iBinarySearch) - 1);
        this.mPosition[iBinarySearch] = d;
        this.mPeriod[iBinarySearch] = f2;
        this.mNormalized = false;
    }

    public double getDP(double d) {
        if (d <= 0.0d) {
            d = 1.0E-5d;
        } else if (d >= 1.0d) {
            d = 0.999999d;
        }
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d);
        if (iBinarySearch > 0 || iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = -iBinarySearch;
        int i3 = i2 - 1;
        float[] fArr = this.mPeriod;
        float f2 = fArr[i3];
        int i4 = i2 - 2;
        float f3 = fArr[i4];
        double[] dArr = this.mPosition;
        double d2 = dArr[i3];
        double d3 = dArr[i4];
        double d4 = ((double) (f2 - f3)) / (d2 - d3);
        return (((double) f3) - (d4 * d3)) + (d * d4);
    }

    public double getP(double d) {
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d);
        if (iBinarySearch > 0) {
            return 1.0d;
        }
        if (iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = -iBinarySearch;
        int i3 = i2 - 1;
        float[] fArr = this.mPeriod;
        float f2 = fArr[i3];
        int i4 = i2 - 2;
        float f3 = fArr[i4];
        double[] dArr = this.mPosition;
        double d2 = dArr[i3];
        double d3 = dArr[i4];
        double d4 = ((double) (f2 - f3)) / (d2 - d3);
        return ((((d * d) - (d3 * d3)) * d4) / 2.0d) + ((d - d3) * (((double) f3) - (d4 * d3))) + this.mArea[i4];
    }

    public double getSlope(double d) {
        switch (this.mType) {
            case 1:
                return 0.0d;
            case 2:
                return Math.signum((((getP(d) * 4.0d) + 3.0d) % 4.0d) - 2.0d) * getDP(d) * 4.0d;
            case 3:
                return getDP(d) * 2.0d;
            case 4:
                return (-getDP(d)) * 2.0d;
            case 5:
                return Math.sin(getP(d) * this.PI2) * getDP(d) * (-this.PI2);
            case 6:
                return ((((getP(d) * 4.0d) + 2.0d) % 4.0d) - 2.0d) * getDP(d) * 4.0d;
            default:
                return Math.cos(getP(d) * this.PI2) * getDP(d) * this.PI2;
        }
    }

    public double getValue(double d) {
        double dAbs;
        switch (this.mType) {
            case 1:
                return Math.signum(0.5d - (getP(d) % 1.0d));
            case 2:
                dAbs = Math.abs((((getP(d) * 4.0d) + 1.0d) % 4.0d) - 2.0d);
                break;
            case 3:
                return (((getP(d) * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                dAbs = ((getP(d) * 2.0d) + 1.0d) % 2.0d;
                break;
            case 5:
                return Math.cos(getP(d) * this.PI2);
            case 6:
                double dAbs2 = 1.0d - Math.abs(((getP(d) * 4.0d) % 4.0d) - 2.0d);
                dAbs = dAbs2 * dAbs2;
                break;
            default:
                return Math.sin(getP(d) * this.PI2);
        }
        return 1.0d - dAbs;
    }

    public void normalize() {
        double d = 0.0d;
        int i2 = 0;
        while (true) {
            float[] fArr = this.mPeriod;
            if (i2 >= fArr.length) {
                break;
            }
            d += (double) fArr[i2];
            i2++;
        }
        double d2 = 0.0d;
        int i3 = 1;
        while (true) {
            float[] fArr2 = this.mPeriod;
            if (i3 >= fArr2.length) {
                break;
            }
            int i4 = i3 - 1;
            float f2 = (fArr2[i4] + fArr2[i3]) / 2.0f;
            double[] dArr = this.mPosition;
            d2 += (dArr[i3] - dArr[i4]) * ((double) f2);
            i3++;
        }
        int i5 = 0;
        while (true) {
            float[] fArr3 = this.mPeriod;
            if (i5 >= fArr3.length) {
                break;
            }
            fArr3[i5] = (float) (((double) fArr3[i5]) * (d / d2));
            i5++;
        }
        this.mArea[0] = 0.0d;
        int i6 = 1;
        while (true) {
            float[] fArr4 = this.mPeriod;
            if (i6 >= fArr4.length) {
                this.mNormalized = true;
                return;
            }
            int i7 = i6 - 1;
            float f3 = (fArr4[i7] + fArr4[i6]) / 2.0f;
            double[] dArr2 = this.mPosition;
            double d3 = dArr2[i6] - dArr2[i7];
            double[] dArr3 = this.mArea;
            dArr3[i6] = (d3 * ((double) f3)) + dArr3[i7];
            i6++;
        }
    }

    public void setType(int i2) {
        this.mType = i2;
    }

    public String toString() {
        return "pos =" + Arrays.toString(this.mPosition) + " period=" + Arrays.toString(this.mPeriod);
    }
}
