package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MotionController {
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    String[] attributeTable;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    String mConstraintTag;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private CurveFit[] mSpline;
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    View mView;
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    float mMotionStagger = Float.NaN;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<Key> mKeyList = new ArrayList<>();
    private int mPathMotionArc = Key.UNSET;

    public MotionController(View view) {
        setView(view);
    }

    private float getAdjustedPosition(float f2, float[] fArr) {
        float f3 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f4 = this.mStaggerScale;
            if (f4 != 1.0d) {
                float f5 = this.mStaggerOffset;
                if (f2 < f5) {
                    f2 = 0.0f;
                }
                if (f2 > f5 && f2 < 1.0d) {
                    f2 = (f2 - f5) * f4;
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float f6 = Float.NaN;
        for (MotionPaths motionPaths : this.mMotionPaths) {
            Easing easing2 = motionPaths.mKeyFrameEasing;
            if (easing2 != null) {
                float f7 = motionPaths.time;
                if (f7 < f2) {
                    easing = easing2;
                    f3 = f7;
                } else if (Float.isNaN(f6)) {
                    f6 = motionPaths.time;
                }
            }
        }
        if (easing != null) {
            float f8 = (Float.isNaN(f6) ? 1.0f : f6) - f3;
            double d = (f2 - f3) / f8;
            f2 = (((float) easing.get(d)) * f8) + f3;
            if (fArr != null) {
                fArr[0] = (float) easing.getDiff(d);
            }
        }
        return f2;
    }

    private float getPreCycleDistance() {
        float[] fArr = new float[2];
        float f2 = 1.0f / 99;
        double d = 0.0d;
        double d2 = 0.0d;
        int i2 = 0;
        float fHypot = 0.0f;
        while (i2 < 100) {
            float f3 = i2 * f2;
            double d3 = f3;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f4 = Float.NaN;
            float f5 = 0.0f;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                float f6 = f2;
                if (easing2 != null) {
                    float f7 = motionPaths.time;
                    if (f7 < f3) {
                        f5 = f7;
                        easing = easing2;
                    } else if (Float.isNaN(f4)) {
                        f4 = motionPaths.time;
                    }
                }
                f2 = f6;
            }
            float f8 = f2;
            if (easing != null) {
                if (Float.isNaN(f4)) {
                    f4 = 1.0f;
                }
                float f9 = f4 - f5;
                d3 = (((float) easing.get((f3 - f5) / f9)) * f9) + f5;
            }
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
            if (i2 > 0) {
                fHypot = (float) (Math.hypot(d2 - ((double) fArr[1]), d - ((double) fArr[0])) + ((double) fHypot));
            }
            d = fArr[0];
            d2 = fArr[1];
            i2++;
            f2 = f8;
        }
        return fHypot;
    }

    private void insertKey(MotionPaths motionPaths) {
        int iBinarySearch = Collections.binarySearch(this.mMotionPaths, motionPaths);
        if (iBinarySearch == 0) {
            float f2 = motionPaths.position;
        }
        this.mMotionPaths.add((-iBinarySearch) - 1, motionPaths);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    public void addKeys(ArrayList<Key> arrayList) {
        this.mKeyList.addAll(arrayList);
    }

    public void buildBounds(float[] fArr, int i2) {
        float f2 = 1.0f / (i2 - 1);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        if (map != null) {
            map.get("translationX");
        }
        HashMap<String, SplineSet> map2 = this.mAttributesMap;
        if (map2 != null) {
            map2.get("translationY");
        }
        HashMap<String, KeyCycleOscillator> map3 = this.mCycleMap;
        if (map3 != null) {
            map3.get("translationX");
        }
        HashMap<String, KeyCycleOscillator> map4 = this.mCycleMap;
        if (map4 != null) {
            map4.get("translationY");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            float f3 = i3 * f2;
            float f4 = this.mStaggerScale;
            float f5 = 0.0f;
            if (f4 != 1.0f) {
                float f6 = this.mStaggerOffset;
                if (f3 < f6) {
                    f3 = 0.0f;
                }
                if (f3 > f6 && f3 < 1.0d) {
                    f3 = (f3 - f6) * f4;
                }
            }
            double d = f3;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f7 = Float.NaN;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                if (easing2 != null) {
                    float f8 = motionPaths.time;
                    if (f8 < f3) {
                        easing = easing2;
                        f5 = f8;
                    } else if (Float.isNaN(f7)) {
                        f7 = motionPaths.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f7)) {
                    f7 = 1.0f;
                }
                float f9 = f7 - f5;
                d = (((float) easing.get((f3 - f5) / f9)) * f9) + f5;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, fArr, i3 * 2);
        }
    }

    public int buildKeyBounds(float[] fArr, int[] iArr) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                iArr[i2] = it.next().mMode;
                i2++;
            }
        }
        int i3 = 0;
        for (double d : timePoints) {
            this.mSpline[0].getPos(d, this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    public int buildKeyFrames(float[] fArr, int[] iArr) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                iArr[i2] = it.next().mMode;
                i2++;
            }
        }
        int i3 = 0;
        for (double d : timePoints) {
            this.mSpline[0].getPos(d, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0066 A[PHI: r11
      0x0066: PHI (r11v2 float) = (r11v1 float), (r11v4 float) binds: [B:21:0x004c, B:26:0x0057] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:60:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:61:0x0101 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:62:0x0103  */
    /* JADX WARN: Code duplicated, block: B:68:0x010e A[SYNTHETIC] */
    public void buildPath(float[] fArr, int i2) {
        SplineSet splineSet;
        int i3 = i2;
        float f2 = 1.0f;
        float f3 = 1.0f / (i3 - 1);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        SplineSet splineSet2 = map == null ? null : map.get("translationX");
        HashMap<String, SplineSet> map2 = this.mAttributesMap;
        SplineSet splineSet3 = map2 == null ? null : map2.get("translationY");
        HashMap<String, KeyCycleOscillator> map3 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, KeyCycleOscillator> map4 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i4 = 0;
        while (i4 < i3) {
            float f4 = i4 * f3;
            float f5 = this.mStaggerScale;
            float f6 = 0.0f;
            if (f5 != f2) {
                float f7 = this.mStaggerOffset;
                if (f4 < f7) {
                    f4 = 0.0f;
                }
                if (f4 > f7) {
                    splineSet = splineSet2;
                    if (f4 < 1.0d) {
                        f4 = (f4 - f7) * f5;
                    }
                } else {
                    splineSet = splineSet2;
                }
            } else {
                splineSet = splineSet2;
            }
            double d = f4;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f8 = Float.NaN;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                if (easing2 != null) {
                    float f9 = motionPaths.time;
                    if (f9 < f4) {
                        f6 = f9;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = motionPaths.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                float f10 = f8 - f6;
                d = (((float) easing.get((f4 - f6) / f10)) * f10) + f6;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            int i5 = i4 * 2;
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, fArr, i5);
            if (keyCycleOscillator != null) {
                fArr[i5] = keyCycleOscillator.get(f4) + fArr[i5];
            } else {
                if (splineSet != null) {
                    splineSet2 = splineSet;
                    fArr[i5] = splineSet2.get(f4) + fArr[i5];
                }
                if (keyCycleOscillator2 != null) {
                    int i6 = i5 + 1;
                    fArr[i6] = keyCycleOscillator2.get(f4) + fArr[i6];
                } else if (splineSet3 != null) {
                    int i7 = i5 + 1;
                    fArr[i7] = splineSet3.get(f4) + fArr[i7];
                }
                i4++;
                i3 = i2;
                f2 = 1.0f;
            }
            splineSet2 = splineSet;
            if (keyCycleOscillator2 != null) {
                int i8 = i5 + 1;
                fArr[i8] = keyCycleOscillator2.get(f4) + fArr[i8];
            } else if (splineSet3 != null) {
                int i9 = i5 + 1;
                fArr[i9] = splineSet3.get(f4) + fArr[i9];
            }
            i4++;
            i3 = i2;
            f2 = 1.0f;
        }
    }

    public void buildRect(float f2, float[] fArr, int i2) {
        this.mSpline[0].getPos(getAdjustedPosition(f2, null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, fArr, i2);
    }

    public void buildRectangles(float[] fArr, int i2) {
        float f2 = 1.0f / (i2 - 1);
        for (int i3 = 0; i3 < i2; i3++) {
            this.mSpline[0].getPos(getAdjustedPosition(i3 * f2, null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, fArr, i3 * 8);
        }
    }

    public int getAttributeValues(String str, float[] fArr, int i2) {
        SplineSet splineSet = this.mAttributesMap.get(str);
        if (splineSet == null) {
            return -1;
        }
        for (int i3 = 0; i3 < fArr.length; i3++) {
            fArr[i3] = splineSet.get(i3 / (fArr.length - 1));
        }
        return fArr.length;
    }

    public void getDpDt(float f2, float f3, float f4, float[] fArr) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(f2, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i2 = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f5 = motionPaths.f19x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f6 = f5 - motionPaths2.f19x;
            float f7 = motionPaths.f20y - motionPaths2.f20y;
            float f8 = motionPaths.width - motionPaths2.width;
            float f9 = (motionPaths.height - motionPaths2.height) + f7;
            fArr[0] = ((f8 + f6) * f3) + ((1.0f - f3) * f6);
            fArr[1] = (f9 * f4) + ((1.0f - f4) * f7);
            return;
        }
        double d = adjustedPosition;
        curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        float f10 = this.mVelocity[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i2 >= dArr.length) {
                break;
            }
            dArr[i2] = dArr[i2] * ((double) f10);
            i2++;
        }
        CurveFit curveFit = this.mArcSpline;
        if (curveFit == null) {
            this.mStartMotionPath.setDpDt(f3, f4, fArr, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        double[] dArr2 = this.mInterpolateData;
        if (dArr2.length > 0) {
            curveFit.getPos(d, dArr2);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(f3, f4, fArr, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
    }

    public int getDrawPath() {
        int iMax = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().mDrawPath);
        }
        return Math.max(iMax, this.mEndMotionPath.mDrawPath);
    }

    public float getFinalX() {
        return this.mEndMotionPath.f19x;
    }

    public float getFinalY() {
        return this.mEndMotionPath.f20y;
    }

    public MotionPaths getKeyFrame(int i2) {
        return this.mMotionPaths.get(i2);
    }

    public int getKeyFrameInfo(int i2, int[] iArr) {
        float[] fArr = new float[2];
        int i3 = 0;
        int i4 = 0;
        for (Key key : this.mKeyList) {
            int i5 = key.mType;
            if (i5 == i2 || i2 != -1) {
                iArr[i4] = 0;
                iArr[i4 + 1] = i5;
                int i6 = key.mFramePosition;
                iArr[i4 + 2] = i6;
                this.mSpline[0].getPos(i6 / 100.0f, this.mInterpolateData);
                this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                iArr[i4 + 3] = Float.floatToIntBits(fArr[0]);
                int i7 = i4 + 4;
                iArr[i7] = Float.floatToIntBits(fArr[1]);
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    iArr[i4 + 5] = keyPosition.mPositionType;
                    iArr[i4 + 6] = Float.floatToIntBits(keyPosition.mPercentX);
                    i7 = i4 + 7;
                    iArr[i7] = Float.floatToIntBits(keyPosition.mPercentY);
                }
                int i8 = i7 + 1;
                iArr[i4] = i8 - i4;
                i3++;
                i4 = i8;
            }
        }
        return i3;
    }

    public float getKeyFrameParameter(int i2, float f2, float f3) {
        MotionPaths motionPaths = this.mEndMotionPath;
        float f4 = motionPaths.f19x;
        MotionPaths motionPaths2 = this.mStartMotionPath;
        float f5 = motionPaths2.f19x;
        float f6 = f4 - f5;
        float f7 = motionPaths.f20y;
        float f8 = motionPaths2.f20y;
        float f9 = f7 - f8;
        float f10 = (motionPaths2.width / 2.0f) + f5;
        float f11 = (motionPaths2.height / 2.0f) + f8;
        float fHypot = (float) Math.hypot(f6, f9);
        if (fHypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f12 = f2 - f10;
        float f13 = f3 - f11;
        if (((float) Math.hypot(f12, f13)) == 0.0f) {
            return 0.0f;
        }
        float f14 = (f13 * f9) + (f12 * f6);
        if (i2 == 0) {
            return f14 / fHypot;
        }
        if (i2 == 1) {
            return (float) Math.sqrt((fHypot * fHypot) - (f14 * f14));
        }
        if (i2 == 2) {
            return f12 / f6;
        }
        if (i2 == 3) {
            return f13 / f6;
        }
        if (i2 == 4) {
            return f12 / f9;
        }
        if (i2 != 5) {
            return 0.0f;
        }
        return f13 / f9;
    }

    public KeyPositionBase getPositionKeyframe(int i2, int i3, float f2, float f3) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f4 = motionPaths.f19x;
        rectF.left = f4;
        float f5 = motionPaths.f20y;
        rectF.top = f5;
        rectF.right = f4 + motionPaths.width;
        rectF.bottom = f5 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f6 = motionPaths2.f19x;
        rectF2.left = f6;
        float f7 = motionPaths2.f20y;
        rectF2.top = f7;
        rectF2.right = f6 + motionPaths2.width;
        rectF2.bottom = f7 + motionPaths2.height;
        for (Key key : this.mKeyList) {
            if (key instanceof KeyPositionBase) {
                KeyPositionBase keyPositionBase = (KeyPositionBase) key;
                if (keyPositionBase.intersects(i2, i3, rectF, rectF2, f2, f3)) {
                    return keyPositionBase;
                }
            }
        }
        return null;
    }

    public void getPostLayoutDvDp(float f2, int i2, int i3, float f3, float f4, float[] fArr) {
        float adjustedPosition = getAdjustedPosition(f2, this.mVelocity);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        SplineSet splineSet = map == null ? null : map.get("translationX");
        HashMap<String, SplineSet> map2 = this.mAttributesMap;
        SplineSet splineSet2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, SplineSet> map3 = this.mAttributesMap;
        SplineSet splineSet3 = map3 == null ? null : map3.get("rotation");
        HashMap<String, SplineSet> map4 = this.mAttributesMap;
        SplineSet splineSet4 = map4 == null ? null : map4.get("scaleX");
        HashMap<String, SplineSet> map5 = this.mAttributesMap;
        SplineSet splineSet5 = map5 == null ? null : map5.get("scaleY");
        HashMap<String, KeyCycleOscillator> map6 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = map6 == null ? null : map6.get("translationX");
        HashMap<String, KeyCycleOscillator> map7 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = map7 == null ? null : map7.get("translationY");
        HashMap<String, KeyCycleOscillator> map8 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator3 = map8 == null ? null : map8.get("rotation");
        HashMap<String, KeyCycleOscillator> map9 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator4 = map9 == null ? null : map9.get("scaleX");
        HashMap<String, KeyCycleOscillator> map10 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator5 = map10 != null ? map10.get("scaleY") : null;
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(splineSet3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(splineSet, splineSet2, adjustedPosition);
        velocityMatrix.setScaleVelocity(splineSet4, splineSet5, adjustedPosition);
        velocityMatrix.setRotationVelocity(keyCycleOscillator3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(keyCycleOscillator, keyCycleOscillator2, adjustedPosition);
        velocityMatrix.setScaleVelocity(keyCycleOscillator4, keyCycleOscillator5, adjustedPosition);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                double d = adjustedPosition;
                curveFit.getPos(d, dArr);
                this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(f3, f4, fArr, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
            velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
            return;
        }
        int i4 = 0;
        if (this.mSpline == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f5 = motionPaths.f19x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f6 = f5 - motionPaths2.f19x;
            KeyCycleOscillator keyCycleOscillator6 = keyCycleOscillator5;
            float f7 = motionPaths.f20y - motionPaths2.f20y;
            KeyCycleOscillator keyCycleOscillator7 = keyCycleOscillator4;
            float f8 = motionPaths.width - motionPaths2.width;
            float f9 = (motionPaths.height - motionPaths2.height) + f7;
            fArr[0] = ((f8 + f6) * f3) + ((1.0f - f3) * f6);
            fArr[1] = (f9 * f4) + ((1.0f - f4) * f7);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(splineSet3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(splineSet, splineSet2, adjustedPosition);
            velocityMatrix.setScaleVelocity(splineSet4, splineSet5, adjustedPosition);
            velocityMatrix.setRotationVelocity(keyCycleOscillator3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(keyCycleOscillator, keyCycleOscillator2, adjustedPosition);
            velocityMatrix.setScaleVelocity(keyCycleOscillator7, keyCycleOscillator6, adjustedPosition);
            velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
            return;
        }
        double adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f10 = this.mVelocity[0];
        while (true) {
            double[] dArr2 = this.mInterpolateVelocity;
            if (i4 >= dArr2.length) {
                this.mStartMotionPath.setDpDt(f3, f4, fArr, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
                return;
            } else {
                dArr2[i4] = dArr2[i4] * ((double) f10);
                i4++;
            }
        }
    }

    public float getStartX() {
        return this.mStartMotionPath.f19x;
    }

    public float getStartY() {
        return this.mStartMotionPath.f20y;
    }

    public int getkeyFramePositions(int[] iArr, float[] fArr) {
        int i2 = 0;
        int i3 = 0;
        for (Key key : this.mKeyList) {
            int i4 = key.mFramePosition;
            iArr[i2] = (key.mType * 1000) + i4;
            this.mSpline[0].getPos(i4 / 100.0f, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
            i2++;
        }
        return i2;
    }

    public boolean interpolate(View view, float f2, long j2, KeyCache keyCache) {
        TimeCycleSplineSet.PathRotate pathRotate;
        boolean pathRotate2;
        float adjustedPosition = getAdjustedPosition(f2, null);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        if (map != null) {
            Iterator<SplineSet> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(view, adjustedPosition);
            }
        }
        HashMap<String, TimeCycleSplineSet> map2 = this.mTimeCycleAttributesMap;
        if (map2 != null) {
            pathRotate = null;
            boolean property = false;
            for (TimeCycleSplineSet timeCycleSplineSet : map2.values()) {
                if (timeCycleSplineSet instanceof TimeCycleSplineSet.PathRotate) {
                    pathRotate = (TimeCycleSplineSet.PathRotate) timeCycleSplineSet;
                } else {
                    property |= timeCycleSplineSet.setProperty(view, adjustedPosition, j2, keyCache);
                }
            }
            pathRotate2 = property;
        } else {
            pathRotate = null;
            pathRotate2 = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            double d = adjustedPosition;
            curveFitArr[0].getPos(d, this.mInterpolateData);
            this.mSpline[0].getSlope(d, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                    this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                }
            }
            this.mStartMotionPath.setView(view, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null);
            HashMap<String, SplineSet> map3 = this.mAttributesMap;
            if (map3 != null) {
                for (SplineSet splineSet : map3.values()) {
                    if (splineSet instanceof SplineSet.PathRotate) {
                        double[] dArr2 = this.mInterpolateVelocity;
                        ((SplineSet.PathRotate) splineSet).setPathRotate(view, adjustedPosition, dArr2[0], dArr2[1]);
                    }
                }
            }
            if (pathRotate != null) {
                double[] dArr3 = this.mInterpolateVelocity;
                pathRotate2 |= pathRotate.setPathRotate(view, keyCache, adjustedPosition, j2, dArr3[0], dArr3[1]);
            }
            int i2 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i2 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i2].getPos(d, this.mValuesBuff);
                this.mStartMotionPath.attributes.get(this.mAttributeNames[i2 - 1]).setInterpolatedValue(view, this.mValuesBuff);
                i2++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (adjustedPosition <= 0.0f) {
                    view.setVisibility(motionConstrainedPoint.visibility);
                } else if (adjustedPosition >= 1.0f) {
                    motionConstrainedPoint = this.mEndPoint;
                    view.setVisibility(motionConstrainedPoint.visibility);
                } else if (this.mEndPoint.visibility != motionConstrainedPoint.visibility) {
                    view.setVisibility(0);
                }
            }
            if (this.mKeyTriggers != null) {
                int i3 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i3 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i3].conditionallyFire(adjustedPosition, view);
                    i3++;
                }
            }
        } else {
            MotionPaths motionPaths = this.mStartMotionPath;
            float f3 = motionPaths.f19x;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float fA = a.a(motionPaths2.f19x, f3, adjustedPosition, f3);
            float f4 = motionPaths.f20y;
            float fA2 = a.a(motionPaths2.f20y, f4, adjustedPosition, f4);
            float f5 = motionPaths.width;
            float f6 = motionPaths2.width;
            float fA3 = a.a(f6, f5, adjustedPosition, f5);
            float f7 = motionPaths.height;
            float f8 = motionPaths2.height;
            float f9 = fA + 0.5f;
            int i4 = (int) f9;
            float f10 = fA2 + 0.5f;
            int i5 = (int) f10;
            int i6 = (int) (f9 + fA3);
            int iA = (int) (f10 + a.a(f8, f7, adjustedPosition, f7));
            int i7 = i6 - i4;
            int i8 = iA - i5;
            if (f6 != f5 || f8 != f7) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i7, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i8, BasicMeasure.EXACTLY));
            }
            view.layout(i4, i5, i6, iA);
        }
        HashMap<String, KeyCycleOscillator> map4 = this.mCycleMap;
        if (map4 != null) {
            for (KeyCycleOscillator keyCycleOscillator : map4.values()) {
                if (keyCycleOscillator instanceof KeyCycleOscillator.PathRotateSet) {
                    double[] dArr4 = this.mInterpolateVelocity;
                    ((KeyCycleOscillator.PathRotateSet) keyCycleOscillator).setPathRotate(view, adjustedPosition, dArr4[0], dArr4[1]);
                } else {
                    keyCycleOscillator.setProperty(view, adjustedPosition);
                }
            }
        }
        return pathRotate2;
    }

    public String name() {
        return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
    }

    public void positionKeyframe(View view, KeyPositionBase keyPositionBase, float f2, float f3, String[] strArr, float[] fArr) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f4 = motionPaths.f19x;
        rectF.left = f4;
        float f5 = motionPaths.f20y;
        rectF.top = f5;
        rectF.right = f4 + motionPaths.width;
        rectF.bottom = f5 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f6 = motionPaths2.f19x;
        rectF2.left = f6;
        float f7 = motionPaths2.f20y;
        rectF2.top = f7;
        rectF2.right = f6 + motionPaths2.width;
        rectF2.bottom = f7 + motionPaths2.height;
        keyPositionBase.positionAttributes(view, rectF, rectF2, f2, f3, strArr, fArr);
    }

    public void setDrawPath(int i2) {
        this.mStartMotionPath.mDrawPath = i2;
    }

    public void setEndState(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.time = 1.0f;
        motionPaths.position = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.setBounds(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(constraintWidget, constraintSet, this.mId);
    }

    public void setPathMotionArc(int i2) {
        this.mPathMotionArc = i2;
    }

    public void setStartCurrentState(View view) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        motionPaths.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mStartPoint.setState(view);
    }

    public void setStartState(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        readView(motionPaths);
        this.mStartMotionPath.setBounds(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(parameters);
        this.mMotionStagger = parameters.motion.mMotionStagger;
        this.mStartPoint.setState(constraintWidget, constraintSet, this.mId);
    }

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            this.mConstraintTag = ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void setup(int i2, int i3, float f2, long j2) {
        ArrayList arrayList;
        String[] strArr;
        TimeCycleSplineSet timeCycleSplineSetMakeSpline;
        ConstraintAttribute constraintAttribute;
        SplineSet splineSetMakeSpline;
        ConstraintAttribute constraintAttribute2;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        int i4 = this.mPathMotionArc;
        if (i4 != Key.UNSET) {
            this.mStartMotionPath.mPathMotionArc = i4;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet2);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            arrayList = null;
            for (Key key : arrayList2) {
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    insertKey(new MotionPaths(i2, i3, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i5 = keyPosition.mCurveFit;
                    if (i5 != Key.UNSET) {
                        this.mCurveFitType = i5;
                    }
                } else if (key instanceof KeyCycle) {
                    key.getAttributeNames(hashSet3);
                } else if (key instanceof KeyTimeCycle) {
                    key.getAttributeNames(hashSet);
                } else if (key instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) key);
                } else {
                    key.setInterpolation(map);
                    key.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            for (String str : hashSet2) {
                if (str.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str2 = str.split(",")[1];
                    for (Key key2 : this.mKeyList) {
                        HashMap<String, ConstraintAttribute> map2 = key2.mCustomConstraints;
                        if (map2 != null && (constraintAttribute2 = map2.get(str2)) != null) {
                            sparseArray.append(key2.mFramePosition, constraintAttribute2);
                        }
                    }
                    splineSetMakeSpline = SplineSet.makeCustomSpline(str, sparseArray);
                } else {
                    splineSetMakeSpline = SplineSet.makeSpline(str);
                }
                if (splineSetMakeSpline != null) {
                    splineSetMakeSpline.setType(str);
                    this.mAttributesMap.put(str, splineSetMakeSpline);
                }
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                for (Key key3 : arrayList3) {
                    if (key3 instanceof KeyAttributes) {
                        key3.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str3 : this.mAttributesMap.keySet()) {
                this.mAttributesMap.get(str3).setup(map.containsKey(str3) ? map.get(str3).intValue() : 0);
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            for (String str4 : hashSet) {
                if (!this.mTimeCycleAttributesMap.containsKey(str4)) {
                    if (str4.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str5 = str4.split(",")[1];
                        for (Key key4 : this.mKeyList) {
                            HashMap<String, ConstraintAttribute> map3 = key4.mCustomConstraints;
                            if (map3 != null && (constraintAttribute = map3.get(str5)) != null) {
                                sparseArray2.append(key4.mFramePosition, constraintAttribute);
                            }
                        }
                        timeCycleSplineSetMakeSpline = TimeCycleSplineSet.makeCustomSpline(str4, sparseArray2);
                    } else {
                        timeCycleSplineSetMakeSpline = TimeCycleSplineSet.makeSpline(str4, j2);
                    }
                    if (timeCycleSplineSetMakeSpline != null) {
                        timeCycleSplineSetMakeSpline.setType(str4);
                        this.mTimeCycleAttributesMap.put(str4, timeCycleSplineSetMakeSpline);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                for (Key key5 : arrayList4) {
                    if (key5 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) key5).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str6 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str6).setup(map.containsKey(str6) ? map.get(str6).intValue() : 0);
            }
        }
        int size = this.mMotionPaths.size();
        int i6 = size + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[i6];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size + 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        int i7 = 1;
        while (it.hasNext()) {
            motionPathsArr[i7] = it.next();
            i7++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str7 : this.mEndMotionPath.attributes.keySet()) {
            if (this.mStartMotionPath.attributes.containsKey(str7)) {
                if (!hashSet2.contains("CUSTOM," + str7)) {
                    hashSet4.add(str7);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpCount = new int[strArr2.length];
        int i8 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i8 >= strArr.length) {
                break;
            }
            String str8 = strArr[i8];
            this.mAttributeInterpCount[i8] = 0;
            for (int i9 = 0; i9 < i6; i9++) {
                if (motionPathsArr[i9].attributes.containsKey(str8)) {
                    int[] iArr = this.mAttributeInterpCount;
                    iArr[i8] = motionPathsArr[i9].attributes.get(str8).noOfInterpValues() + iArr[i8];
                    break;
                }
            }
            i8++;
        }
        boolean z2 = motionPathsArr[0].mPathMotionArc != Key.UNSET;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i10 = 1; i10 < i6; i10++) {
            motionPathsArr[i10].different(motionPathsArr[i10 - 1], zArr, this.mAttributeNames, z2);
        }
        int i11 = 0;
        for (int i12 = 1; i12 < length; i12++) {
            if (zArr[i12]) {
                i11++;
            }
        }
        int[] iArr2 = new int[i11];
        this.mInterpolateVariables = iArr2;
        this.mInterpolateData = new double[iArr2.length];
        this.mInterpolateVelocity = new double[iArr2.length];
        int i13 = 0;
        for (int i14 = 1; i14 < length; i14++) {
            if (zArr[i14]) {
                this.mInterpolateVariables[i13] = i14;
                i13++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i6, this.mInterpolateVariables.length);
        double[] dArr2 = new double[i6];
        for (int i15 = 0; i15 < i6; i15++) {
            motionPathsArr[i15].fillStandard(dArr[i15], this.mInterpolateVariables);
            dArr2[i15] = motionPathsArr[i15].time;
        }
        int i16 = 0;
        while (true) {
            int[] iArr3 = this.mInterpolateVariables;
            if (i16 >= iArr3.length) {
                break;
            }
            if (iArr3[i16] < MotionPaths.names.length) {
                String strG = a.g(new StringBuilder(), MotionPaths.names[this.mInterpolateVariables[i16]], " [");
                for (int i17 = 0; i17 < i6; i17++) {
                    StringBuilder sbH = a.h(strG);
                    sbH.append(dArr[i17][i16]);
                    strG = sbH.toString();
                }
            }
            i16++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i18 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i18 >= strArr3.length) {
                break;
            }
            String str9 = strArr3[i18];
            int i19 = 0;
            double[] dArr3 = null;
            int i20 = 0;
            double[][] dArr4 = null;
            while (i19 < i6) {
                if (motionPathsArr[i19].hasCustomData(str9)) {
                    if (dArr4 == null) {
                        dArr3 = new double[i6];
                        dArr4 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i6, motionPathsArr[i19].getCustomDataCount(str9));
                    }
                    MotionPaths motionPaths = motionPathsArr[i19];
                    dArr3[i20] = motionPaths.time;
                    motionPaths.getCustomData(str9, dArr4[i20], 0);
                    i20++;
                }
                i19++;
                motionPathsArr = motionPathsArr;
            }
            i18++;
            this.mSpline[i18] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr3, i20), (double[][]) Arrays.copyOf(dArr4, i20));
            motionPathsArr = motionPathsArr;
        }
        MotionPaths[] motionPathsArr2 = motionPathsArr;
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr2[0].mPathMotionArc != Key.UNSET) {
            int[] iArr4 = new int[i6];
            double[] dArr5 = new double[i6];
            double[][] dArr6 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i6, 2);
            for (int i21 = 0; i21 < i6; i21++) {
                MotionPaths motionPaths2 = motionPathsArr2[i21];
                iArr4[i21] = motionPaths2.mPathMotionArc;
                dArr5[i21] = motionPaths2.time;
                double[] dArr7 = dArr6[i21];
                dArr7[0] = motionPaths2.f19x;
                dArr7[1] = motionPaths2.f20y;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr5, dArr6);
        }
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            float preCycleDistance = Float.NaN;
            for (String str10 : hashSet3) {
                KeyCycleOscillator keyCycleOscillatorMakeSpline = KeyCycleOscillator.makeSpline(str10);
                if (keyCycleOscillatorMakeSpline != null) {
                    if (keyCycleOscillatorMakeSpline.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    keyCycleOscillatorMakeSpline.setType(str10);
                    this.mCycleMap.put(str10, keyCycleOscillatorMakeSpline);
                }
            }
            for (Key key6 : this.mKeyList) {
                if (key6 instanceof KeyCycle) {
                    ((KeyCycle) key6).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<KeyCycleOscillator> it2 = this.mCycleMap.values().iterator();
            while (it2.hasNext()) {
                it2.next().setup(preCycleDistance);
            }
        }
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.f19x + " y: " + this.mStartMotionPath.f20y + " end: x: " + this.mEndMotionPath.f19x + " y: " + this.mEndMotionPath.f20y;
    }
}
