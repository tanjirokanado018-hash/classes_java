package androidx.constraintlayout.motion.widget;

import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class TimeCycleSplineSet {
    private static final int CURVE_OFFSET = 2;
    private static final int CURVE_PERIOD = 1;
    private static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    private static float VAL_2PI = 6.2831855f;
    private int count;
    long last_time;
    protected CurveFit mCurveFit;
    private String mType;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);
    private float[] mCache = new float[3];
    protected boolean mContinue = false;
    float last_cycle = Float.NaN;

    public static class AlphaSet extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setAlpha(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class CustomSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        SparseArray<ConstraintAttribute> mConstraintAttributeList;
        float[] mTempValues;
        SparseArray<float[]> mWaveProperties = new SparseArray<>();

        public CustomSet(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.mAttributeName = str.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            this.mCurveFit.getPos(f2, this.mTempValues);
            float[] fArr = this.mTempValues;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            float f5 = (float) (((((j2 - this.last_time) * 1.0E-9d) * ((double) f3)) + ((double) this.last_cycle)) % 1.0d);
            this.last_cycle = f5;
            this.last_time = j2;
            float fCalcWave = calcWave(f5);
            this.mContinue = false;
            int i2 = 0;
            while (true) {
                float[] fArr2 = this.mCache;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.mContinue;
                float f6 = this.mTempValues[i2];
                this.mContinue = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fCalcWave) + f4;
                i2++;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(view, this.mCache);
            if (f3 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.mConstraintAttributeList.size();
            int iNoOfInterpValues = this.mConstraintAttributeList.valueAt(0).noOfInterpValues();
            double[] dArr = new double[size];
            int i3 = iNoOfInterpValues + 2;
            this.mTempValues = new float[i3];
            this.mCache = new float[iNoOfInterpValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.mConstraintAttributeList.keyAt(i4);
                ConstraintAttribute constraintAttributeValueAt = this.mConstraintAttributeList.valueAt(i4);
                float[] fArrValueAt = this.mWaveProperties.valueAt(i4);
                dArr[i4] = ((double) iKeyAt) * 0.01d;
                constraintAttributeValueAt.getValuesToInterpolate(this.mTempValues);
                int i5 = 0;
                while (true) {
                    float[] fArr = this.mTempValues;
                    if (i5 < fArr.length) {
                        dArr2[i4][i5] = fArr[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNoOfInterpValues] = fArrValueAt[0];
                dArr3[iNoOfInterpValues + 1] = fArrValueAt[1];
            }
            this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, ConstraintAttribute constraintAttribute, float f2, int i3, float f3) {
            this.mConstraintAttributeList.append(i2, constraintAttribute);
            this.mWaveProperties.append(i2, new float[]{f2, f3});
            this.mWaveShape = Math.max(this.mWaveShape, i3);
        }
    }

    public static class ElevationSet extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setElevation(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class PathRotate extends TimeCycleSplineSet {
        public boolean setPathRotate(View view, KeyCache keyCache, float f2, long j2, double d, double d2) {
            view.setRotation(get(f2, j2, view, keyCache) + ((float) Math.toDegrees(Math.atan2(d2, d))));
            return this.mContinue;
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            return this.mContinue;
        }
    }

    public static class ProgressSet extends TimeCycleSplineSet {
        boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            Method method;
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f2, j2, view, keyCache));
            } else {
                if (this.mNoMethod) {
                    return false;
                }
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.mNoMethod = true;
                    method = null;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(get(f2, j2, view, keyCache)));
                    } catch (IllegalAccessException | InvocationTargetException unused2) {
                    }
                }
            }
            return this.mContinue;
        }
    }

    public static class RotationSet extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotation(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class RotationXset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotationX(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class RotationYset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotationY(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class ScaleXset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setScaleX(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class ScaleYset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setScaleY(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class Sort {
        private Sort() {
        }

        public static void doubleQuickSort(int[] iArr, float[][] fArr, int i2, int i3) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i3;
            iArr2[1] = i2;
            int i4 = 2;
            while (i4 > 0) {
                int i5 = iArr2[i4 - 1];
                int i6 = i4 - 2;
                int i7 = iArr2[i6];
                if (i5 < i7) {
                    int iPartition = partition(iArr, fArr, i5, i7);
                    iArr2[i6] = iPartition - 1;
                    iArr2[i4 - 1] = i5;
                    int i8 = i4 + 1;
                    iArr2[i4] = i7;
                    i4 += 2;
                    iArr2[i8] = iPartition + 1;
                } else {
                    i4 = i6;
                }
            }
        }

        private static int partition(int[] iArr, float[][] fArr, int i2, int i3) {
            int i4 = iArr[i3];
            int i5 = i2;
            while (i2 < i3) {
                if (iArr[i2] <= i4) {
                    swap(iArr, fArr, i5, i2);
                    i5++;
                }
                i2++;
            }
            swap(iArr, fArr, i5, i3);
            return i5;
        }

        private static void swap(int[] iArr, float[][] fArr, int i2, int i3) {
            int i4 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i4;
            float[] fArr2 = fArr[i2];
            fArr[i2] = fArr[i3];
            fArr[i3] = fArr2;
        }
    }

    public static class TranslationXset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationX(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class TranslationYset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationY(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static class TranslationZset extends TimeCycleSplineSet {
        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationZ(get(f2, j2, view, keyCache));
            return this.mContinue;
        }
    }

    public static TimeCycleSplineSet makeCustomSpline(String str, SparseArray<ConstraintAttribute> sparseArray) {
        return new CustomSet(str, sparseArray);
    }

    public static TimeCycleSplineSet makeSpline(String str, long j2) {
        TimeCycleSplineSet rotationXset;
        str.getClass();
        switch (str) {
            case "rotationX":
                rotationXset = new RotationXset();
                break;
            case "rotationY":
                rotationXset = new RotationYset();
                break;
            case "translationX":
                rotationXset = new TranslationXset();
                break;
            case "translationY":
                rotationXset = new TranslationYset();
                break;
            case "translationZ":
                rotationXset = new TranslationZset();
                break;
            case "progress":
                rotationXset = new ProgressSet();
                break;
            case "scaleX":
                rotationXset = new ScaleXset();
                break;
            case "scaleY":
                rotationXset = new ScaleYset();
                break;
            case "rotation":
                rotationXset = new RotationSet();
                break;
            case "elevation":
                rotationXset = new ElevationSet();
                break;
            case "transitionPathRotate":
                rotationXset = new PathRotate();
                break;
            case "alpha":
                rotationXset = new AlphaSet();
                break;
            default:
                return null;
        }
        rotationXset.setStartTime(j2);
        return rotationXset;
    }

    public float calcWave(float f2) {
        float fAbs;
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(f2 * VAL_2PI);
            case 2:
                fAbs = Math.abs(f2);
                break;
            case 3:
                return (((f2 * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                fAbs = ((f2 * 2.0f) + 1.0f) % 2.0f;
                break;
            case 5:
                return (float) Math.cos(f2 * VAL_2PI);
            case 6:
                float fAbs2 = 1.0f - Math.abs(((f2 * 4.0f) % 4.0f) - 2.0f);
                fAbs = fAbs2 * fAbs2;
                break;
            default:
                return (float) Math.sin(f2 * VAL_2PI);
        }
        return 1.0f - fAbs;
    }

    public float get(float f2, long j2, View view, KeyCache keyCache) {
        this.mCurveFit.getPos(f2, this.mCache);
        float[] fArr = this.mCache;
        float f3 = fArr[1];
        if (f3 == 0.0f) {
            this.mContinue = false;
            return fArr[2];
        }
        if (Float.isNaN(this.last_cycle)) {
            float floatValue = keyCache.getFloatValue(view, this.mType, 0);
            this.last_cycle = floatValue;
            if (Float.isNaN(floatValue)) {
                this.last_cycle = 0.0f;
            }
        }
        float f4 = (float) (((((j2 - this.last_time) * 1.0E-9d) * ((double) f3)) + ((double) this.last_cycle)) % 1.0d);
        this.last_cycle = f4;
        keyCache.setFloatValue(view, this.mType, 0, f4);
        this.last_time = j2;
        float f5 = this.mCache[0];
        float fCalcWave = (calcWave(this.last_cycle) * f5) + this.mCache[2];
        this.mContinue = (f5 == 0.0f && f3 == 0.0f) ? false : true;
        return fCalcWave;
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public void setPoint(int i2, float f2, float f3, int i3, float f4) {
        int[] iArr = this.mTimePoints;
        int i4 = this.count;
        iArr[i4] = i2;
        float[] fArr = this.mValues[i4];
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[2] = f4;
        this.mWaveShape = Math.max(this.mWaveShape, i3);
        this.count++;
    }

    public abstract boolean setProperty(View view, float f2, long j2, KeyCache keyCache);

    public void setStartTime(long j2) {
        this.last_time = j2;
    }

    public void setType(String str) {
        this.mType = str;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0048  */
    public void setup(int i2) {
        int i3 = this.count;
        if (i3 == 0) {
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i3 - 1);
        int i4 = 1;
        int i5 = 0;
        while (true) {
            int[] iArr = this.mTimePoints;
            if (i4 >= iArr.length) {
                break;
            }
            if (iArr[i4] != iArr[i4 - 1]) {
                i5++;
            }
            i4++;
        }
        if (i5 == 0) {
            i5 = 1;
        }
        double[] dArr = new double[i5];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i5, 3);
        int i6 = 0;
        for (int i7 = 0; i7 < this.count; i7++) {
            if (i7 > 0) {
                int[] iArr2 = this.mTimePoints;
                if (iArr2[i7] != iArr2[i7 - 1]) {
                    dArr[i6] = ((double) this.mTimePoints[i7]) * 0.01d;
                    double[] dArr3 = dArr2[i6];
                    float[] fArr = this.mValues[i7];
                    dArr3[0] = fArr[0];
                    dArr3[1] = fArr[1];
                    dArr3[2] = fArr[2];
                    i6++;
                }
            } else {
                dArr[i6] = ((double) this.mTimePoints[i7]) * 0.01d;
                double[] dArr4 = dArr2[i6];
                float[] fArr2 = this.mValues[i7];
                dArr4[0] = fArr2[0];
                dArr4[1] = fArr2[1];
                dArr4[2] = fArr2[2];
                i6++;
            }
        }
        this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
    }

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.count; i2++) {
            str = str + "[" + this.mTimePoints[i2] + " , " + decimalFormat.format(this.mValues[i2]) + "] ";
        }
        return str;
    }
}
