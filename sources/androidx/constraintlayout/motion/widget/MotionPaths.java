package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 3;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes;
    float height;
    int mDrawPath;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mProgress;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    float f19x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    float f20y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private boolean diff(float f2, float f3) {
        if (Float.isNaN(f2) || Float.isNaN(f3)) {
            return Float.isNaN(f2) != Float.isNaN(f3);
        }
        return Math.abs(f2 - f3) > 1.0E-6f;
    }

    private static final float xRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return (((f6 - f4) * f3) - ((f7 - f5) * f2)) + f4;
    }

    private static final float yRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return ((f7 - f5) * f3) + ((f6 - f4) * f2) + f5;
    }

    public void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        this.mProgress = constraint.propertySet.mProgress;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute.getType() != ConstraintAttribute.AttributeType.STRING_TYPE) {
                this.attributes.put(str, constraintAttribute);
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull MotionPaths motionPaths) {
        return Float.compare(this.position, motionPaths.position);
    }

    public void different(MotionPaths motionPaths, boolean[] zArr, String[] strArr, boolean z2) {
        zArr[0] = zArr[0] | diff(this.position, motionPaths.position);
        zArr[1] = zArr[1] | diff(this.f19x, motionPaths.f19x) | z2;
        zArr[2] = z2 | diff(this.f20y, motionPaths.f20y) | zArr[2];
        zArr[3] = zArr[3] | diff(this.width, motionPaths.width);
        zArr[4] = diff(this.height, motionPaths.height) | zArr[4];
    }

    public void fillStandard(double[] dArr, int[] iArr) {
        float[] fArr = {this.position, this.f19x, this.f20y, this.width, this.height, this.mPathRotate};
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 < 6) {
                dArr[i2] = fArr[i3];
                i2++;
            }
        }
    }

    public void getBounds(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.width;
        float f3 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f4 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 3) {
                f2 = f4;
            } else if (i4 == 4) {
                f3 = f4;
            }
        }
        fArr[i2] = f2;
        fArr[i2 + 1] = f3;
    }

    public void getCenter(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.f19x;
        float f3 = this.f20y;
        float f4 = this.width;
        float f5 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f6 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                f2 = f6;
            } else if (i4 == 2) {
                f3 = f6;
            } else if (i4 == 3) {
                f4 = f6;
            } else if (i4 == 4) {
                f5 = f6;
            }
        }
        fArr[i2] = (f4 / 2.0f) + f2 + 0.0f;
        fArr[i2 + 1] = (f5 / 2.0f) + f3 + 0.0f;
    }

    public int getCustomData(String str, double[] dArr, int i2) {
        ConstraintAttribute constraintAttribute = this.attributes.get(str);
        if (constraintAttribute.noOfInterpValues() == 1) {
            dArr[i2] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int iNoOfInterpValues = constraintAttribute.noOfInterpValues();
        float[] fArr = new float[iNoOfInterpValues];
        constraintAttribute.getValuesToInterpolate(fArr);
        int i3 = 0;
        while (i3 < iNoOfInterpValues) {
            dArr[i2] = fArr[i3];
            i3++;
            i2++;
        }
        return iNoOfInterpValues;
    }

    public int getCustomDataCount(String str) {
        return this.attributes.get(str).noOfInterpValues();
    }

    public void getRect(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.f19x;
        float f3 = this.f20y;
        float f4 = this.width;
        float f5 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f6 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                f2 = f6;
            } else if (i4 == 2) {
                f3 = f6;
            } else if (i4 == 3) {
                f4 = f6;
            } else if (i4 == 4) {
                f5 = f6;
            }
        }
        float f7 = f4 + f2;
        float f8 = f5 + f3;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        fArr[i2] = f2 + 0.0f;
        fArr[i2 + 1] = f3 + 0.0f;
        fArr[i2 + 2] = f7 + 0.0f;
        fArr[i2 + 3] = f3 + 0.0f;
        fArr[i2 + 4] = f7 + 0.0f;
        int i5 = i2 + 6;
        fArr[i2 + 5] = f8 + 0.0f;
        fArr[i5] = f2 + 0.0f;
        fArr[i2 + 7] = f8 + 0.0f;
    }

    public boolean hasCustomData(String str) {
        return this.attributes.containsKey(str);
    }

    public void initCartesian(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = keyPosition.mDrawPath;
        float f3 = Float.isNaN(keyPosition.mPercentWidth) ? f2 : keyPosition.mPercentWidth;
        float f4 = Float.isNaN(keyPosition.mPercentHeight) ? f2 : keyPosition.mPercentHeight;
        float f5 = motionPaths2.width;
        float f6 = motionPaths.width;
        float f7 = f5 - f6;
        float f8 = motionPaths2.height;
        float f9 = motionPaths.height;
        float f10 = f8 - f9;
        this.position = this.time;
        float f11 = motionPaths.f19x;
        float f12 = motionPaths.f20y;
        float f13 = ((f5 / 2.0f) + motionPaths2.f19x) - ((f6 / 2.0f) + f11);
        float f14 = ((f8 / 2.0f) + motionPaths2.f20y) - ((f9 / 2.0f) + f12);
        float f15 = f7 * f3;
        float f16 = f15 / 2.0f;
        this.f19x = (int) (((f13 * f2) + f11) - f16);
        float f17 = (f14 * f2) + f12;
        float f18 = f10 * f4;
        float f19 = f18 / 2.0f;
        this.f20y = (int) (f17 - f19);
        this.width = (int) (f6 + f15);
        this.height = (int) (f9 + f18);
        float f20 = Float.isNaN(keyPosition.mPercentX) ? f2 : keyPosition.mPercentX;
        float f21 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        if (!Float.isNaN(keyPosition.mPercentY)) {
            f2 = keyPosition.mPercentY;
        }
        float f22 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
        this.mMode = 2;
        this.f19x = (int) (((f22 * f14) + ((f20 * f13) + motionPaths.f19x)) - f16);
        this.f20y = (int) (((f14 * f2) + ((f13 * f21) + motionPaths.f20y)) - f19);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    public void initPath(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = keyPosition.mDrawPath;
        float f3 = Float.isNaN(keyPosition.mPercentWidth) ? f2 : keyPosition.mPercentWidth;
        float f4 = Float.isNaN(keyPosition.mPercentHeight) ? f2 : keyPosition.mPercentHeight;
        float f5 = motionPaths2.width - motionPaths.width;
        float f6 = motionPaths2.height - motionPaths.height;
        this.position = this.time;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            f2 = keyPosition.mPercentX;
        }
        float f7 = motionPaths.f19x;
        float f8 = motionPaths.width;
        float f9 = motionPaths.f20y;
        float f10 = motionPaths.height;
        float f11 = ((motionPaths2.width / 2.0f) + motionPaths2.f19x) - ((f8 / 2.0f) + f7);
        float f12 = ((motionPaths2.height / 2.0f) + motionPaths2.f20y) - ((f10 / 2.0f) + f9);
        float f13 = f11 * f2;
        float f14 = f5 * f3;
        float f15 = f14 / 2.0f;
        this.f19x = (int) ((f7 + f13) - f15);
        float f16 = f2 * f12;
        float f17 = f6 * f4;
        float f18 = f17 / 2.0f;
        this.f20y = (int) ((f9 + f16) - f18);
        this.width = (int) (f8 + f14);
        this.height = (int) (f10 + f17);
        float f19 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
        this.mMode = 1;
        float f20 = (int) ((motionPaths.f19x + f13) - f15);
        float f21 = (int) ((motionPaths.f20y + f16) - f18);
        this.f19x = f20 + ((-f12) * f19);
        this.f20y = f21 + (f11 * f19);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    public void initScreen(int i2, int i3, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = keyPosition.mDrawPath;
        float f3 = Float.isNaN(keyPosition.mPercentWidth) ? f2 : keyPosition.mPercentWidth;
        float f4 = Float.isNaN(keyPosition.mPercentHeight) ? f2 : keyPosition.mPercentHeight;
        float f5 = motionPaths2.width;
        float f6 = motionPaths.width;
        float f7 = f5 - f6;
        float f8 = motionPaths2.height;
        float f9 = motionPaths.height;
        float f10 = f8 - f9;
        this.position = this.time;
        float f11 = motionPaths.f19x;
        float f12 = motionPaths.f20y;
        float f13 = (f5 / 2.0f) + motionPaths2.f19x;
        float f14 = (f8 / 2.0f) + motionPaths2.f20y;
        float f15 = f7 * f3;
        this.f19x = (int) ((((f13 - ((f6 / 2.0f) + f11)) * f2) + f11) - (f15 / 2.0f));
        float f16 = f10 * f4;
        this.f20y = (int) ((((f14 - ((f9 / 2.0f) + f12)) * f2) + f12) - (f16 / 2.0f));
        this.width = (int) (f6 + f15);
        this.height = (int) (f9 + f16);
        this.mMode = 3;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            this.f19x = (int) (keyPosition.mPercentX * ((int) (i2 - this.width)));
        }
        if (!Float.isNaN(keyPosition.mPercentY)) {
            this.f20y = (int) (keyPosition.mPercentY * ((int) (i3 - this.height)));
        }
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    public void setBounds(float f2, float f3, float f4, float f5) {
        this.f19x = f2;
        this.f20y = f3;
        this.width = f4;
        this.height = f5;
    }

    public void setDpDt(float f2, float f3, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f8 = (float) dArr[i2];
            double d = dArr2[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f4 = f8;
            } else if (i3 == 2) {
                f6 = f8;
            } else if (i3 == 3) {
                f5 = f8;
            } else if (i3 == 4) {
                f7 = f8;
            }
        }
        float f9 = f4 - ((0.0f * f5) / 2.0f);
        float f10 = f6 - ((0.0f * f7) / 2.0f);
        fArr[0] = (((f5 * 1.0f) + f9) * f2) + ((1.0f - f2) * f9) + 0.0f;
        fArr[1] = (((f7 * 1.0f) + f10) * f3) + ((1.0f - f3) * f10) + 0.0f;
    }

    /* JADX WARN: Code duplicated, block: B:56:0x00ff  */
    public void setView(View view, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3) {
        float degrees;
        int i2;
        int i3;
        float f2;
        float f3 = this.f19x;
        float f4 = this.f20y;
        float f5 = this.width;
        float f6 = this.height;
        if (iArr.length != 0 && this.mTempValue.length <= iArr[iArr.length - 1]) {
            int i4 = iArr[iArr.length - 1] + 1;
            this.mTempValue = new double[i4];
            this.mTempDelta = new double[i4];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        int i5 = 0;
        for (int i6 = 0; i6 < iArr.length; i6++) {
            double[] dArr4 = this.mTempValue;
            int i7 = iArr[i6];
            dArr4[i7] = dArr[i6];
            this.mTempDelta[i7] = dArr2[i6];
        }
        float f7 = Float.NaN;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        while (true) {
            double[] dArr5 = this.mTempValue;
            if (i5 >= dArr5.length) {
                break;
            }
            if (Double.isNaN(dArr5[i5]) && (dArr3 == null || dArr3[i5] == 0.0d)) {
                f2 = f7;
            } else {
                double d = dArr3 != null ? dArr3[i5] : 0.0d;
                if (!Double.isNaN(this.mTempValue[i5])) {
                    d = this.mTempValue[i5] + d;
                }
                f2 = f7;
                float f12 = (float) d;
                float f13 = (float) this.mTempDelta[i5];
                if (i5 == 1) {
                    f7 = f2;
                    f3 = f12;
                    f8 = f13;
                } else if (i5 == 2) {
                    f7 = f2;
                    f4 = f12;
                    f10 = f13;
                } else if (i5 == 3) {
                    f7 = f2;
                    f5 = f12;
                    f9 = f13;
                } else if (i5 == 4) {
                    f7 = f2;
                    f6 = f12;
                    f11 = f13;
                } else if (i5 == 5) {
                    f7 = f12;
                }
                i5++;
            }
            f7 = f2;
            i5++;
        }
        float f14 = f7;
        if (Float.isNaN(f14)) {
            degrees = Float.NaN;
            if (!Float.isNaN(Float.NaN)) {
            }
            float f15 = f3 + 0.5f;
            int i8 = (int) f15;
            float f16 = f4 + 0.5f;
            int i9 = (int) f16;
            int i10 = (int) (f15 + f5);
            int i11 = (int) (f16 + f6);
            i2 = i10 - i8;
            i3 = i11 - i9;
            if (i2 == view.getMeasuredWidth() || i3 != view.getMeasuredHeight()) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i2, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i3, BasicMeasure.EXACTLY));
            }
            view.layout(i8, i9, i10, i11);
        }
        degrees = (float) (Math.toDegrees(Math.atan2((f11 / 2.0f) + f10, (f9 / 2.0f) + f8)) + ((double) f14) + ((double) (Float.isNaN(Float.NaN) ? 0.0f : Float.NaN)));
        view.setRotation(degrees);
        float f17 = f3 + 0.5f;
        int i12 = (int) f17;
        float f18 = f4 + 0.5f;
        int i13 = (int) f18;
        int i14 = (int) (f17 + f5);
        int i15 = (int) (f18 + f6);
        i2 = i14 - i12;
        i3 = i15 - i13;
        if (i2 == view.getMeasuredWidth()) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i3, BasicMeasure.EXACTLY));
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i3, BasicMeasure.EXACTLY));
        }
        view.layout(i12, i13, i14, i15);
    }

    public MotionPaths(int i2, int i3, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        int i4 = keyPosition.mPositionType;
        if (i4 == 1) {
            initPath(keyPosition, motionPaths, motionPaths2);
        } else if (i4 != 2) {
            initCartesian(keyPosition, motionPaths, motionPaths2);
        } else {
            initScreen(i2, i3, keyPosition, motionPaths, motionPaths2);
        }
    }
}
