package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int PERPENDICULAR = 1;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    private float height;
    private Easing mKeyFrameEasing;
    private float position;
    int visibility;
    private float width;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    private float f17x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    private float f18y;
    private float alpha = 1.0f;
    int mVisibilityMode = 0;
    private boolean applyElevation = false;
    private float elevation = 0.0f;
    private float rotation = 0.0f;
    private float rotationX = 0.0f;
    public float rotationY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float translationZ = 0.0f;
    private int mDrawPath = 0;
    private float mPathRotate = Float.NaN;
    private float mProgress = Float.NaN;
    LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap<>();
    int mMode = 0;
    double[] mTempValue = new double[18];
    double[] mTempDelta = new double[18];

    private boolean diff(float f2, float f3) {
        if (Float.isNaN(f2) || Float.isNaN(f3)) {
            return Float.isNaN(f2) != Float.isNaN(f3);
        }
        return Math.abs(f2 - f3) > 1.0E-6f;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void addValues(HashMap<String, SplineSet> map, int i2) {
        for (String str : map.keySet()) {
            SplineSet splineSet = map.get(str);
            str.getClass();
            byte b = -1;
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        b = 0;
                    }
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        b = 1;
                    }
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        b = 2;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        b = 3;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        b = 4;
                    }
                    break;
                case -1001078227:
                    if (str.equals(NotificationCompat.CATEGORY_PROGRESS)) {
                        b = 5;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        b = 6;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        b = 7;
                    }
                    break;
                case -760884510:
                    if (str.equals("transformPivotX")) {
                        b = 8;
                    }
                    break;
                case -760884509:
                    if (str.equals("transformPivotY")) {
                        b = 9;
                    }
                    break;
                case -40300674:
                    if (str.equals("rotation")) {
                        b = 10;
                    }
                    break;
                case -4379043:
                    if (str.equals("elevation")) {
                        b = 11;
                    }
                    break;
                case 37232917:
                    if (str.equals("transitionPathRotate")) {
                        b = 12;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        b = 13;
                    }
                    break;
            }
            float f2 = 1.0f;
            float f3 = 0.0f;
            switch (b) {
                case 0:
                    if (!Float.isNaN(this.rotationX)) {
                        f3 = this.rotationX;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 1:
                    if (!Float.isNaN(this.rotationY)) {
                        f3 = this.rotationY;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 2:
                    if (!Float.isNaN(this.translationX)) {
                        f3 = this.translationX;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 3:
                    if (!Float.isNaN(this.translationY)) {
                        f3 = this.translationY;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 4:
                    if (!Float.isNaN(this.translationZ)) {
                        f3 = this.translationZ;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 5:
                    if (!Float.isNaN(this.mProgress)) {
                        f3 = this.mProgress;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 6:
                    if (!Float.isNaN(this.scaleX)) {
                        f2 = this.scaleX;
                    }
                    splineSet.setPoint(i2, f2);
                    break;
                case 7:
                    if (!Float.isNaN(this.scaleY)) {
                        f2 = this.scaleY;
                    }
                    splineSet.setPoint(i2, f2);
                    break;
                case 8:
                    if (!Float.isNaN(this.mPivotX)) {
                        f3 = this.mPivotX;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 9:
                    if (!Float.isNaN(this.mPivotY)) {
                        f3 = this.mPivotY;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 10:
                    if (!Float.isNaN(this.rotation)) {
                        f3 = this.rotation;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 11:
                    if (!Float.isNaN(this.elevation)) {
                        f3 = this.elevation;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 12:
                    if (!Float.isNaN(this.mPathRotate)) {
                        f3 = this.mPathRotate;
                    }
                    splineSet.setPoint(i2, f3);
                    break;
                case 13:
                    if (!Float.isNaN(this.alpha)) {
                        f2 = this.alpha;
                    }
                    splineSet.setPoint(i2, f2);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        if (this.attributes.containsKey(str2)) {
                            ConstraintAttribute constraintAttribute = this.attributes.get(str2);
                            if (splineSet instanceof SplineSet.CustomSet) {
                                ((SplineSet.CustomSet) splineSet).setPoint(i2, constraintAttribute);
                            } else {
                                constraintAttribute.getValueToInterpolate();
                                Objects.toString(splineSet);
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void applyParameters(View view) {
        this.visibility = view.getVisibility();
        this.alpha = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.applyElevation = false;
        this.elevation = view.getElevation();
        this.rotation = view.getRotation();
        this.rotationX = view.getRotationX();
        this.rotationY = view.getRotationY();
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
        this.mPivotX = view.getPivotX();
        this.mPivotY = view.getPivotY();
        this.translationX = view.getTranslationX();
        this.translationY = view.getTranslationY();
        this.translationZ = view.getTranslationZ();
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionConstrainedPoint motionConstrainedPoint) {
        return Float.compare(this.position, motionConstrainedPoint.position);
    }

    public void different(MotionConstrainedPoint motionConstrainedPoint, HashSet<String> hashSet) {
        if (diff(this.alpha, motionConstrainedPoint.alpha)) {
            hashSet.add("alpha");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("elevation");
        }
        int i2 = this.visibility;
        int i3 = motionConstrainedPoint.visibility;
        if (i2 != i3 && this.mVisibilityMode == 0 && (i2 == 0 || i3 == 0)) {
            hashSet.add("alpha");
        }
        if (diff(this.rotation, motionConstrainedPoint.rotation)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(motionConstrainedPoint.mPathRotate)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(motionConstrainedPoint.mProgress)) {
            hashSet.add(NotificationCompat.CATEGORY_PROGRESS);
        }
        if (diff(this.rotationX, motionConstrainedPoint.rotationX)) {
            hashSet.add("rotationX");
        }
        if (diff(this.rotationY, motionConstrainedPoint.rotationY)) {
            hashSet.add("rotationY");
        }
        if (diff(this.mPivotX, motionConstrainedPoint.mPivotX)) {
            hashSet.add("transformPivotX");
        }
        if (diff(this.mPivotY, motionConstrainedPoint.mPivotY)) {
            hashSet.add("transformPivotY");
        }
        if (diff(this.scaleX, motionConstrainedPoint.scaleX)) {
            hashSet.add("scaleX");
        }
        if (diff(this.scaleY, motionConstrainedPoint.scaleY)) {
            hashSet.add("scaleY");
        }
        if (diff(this.translationX, motionConstrainedPoint.translationX)) {
            hashSet.add("translationX");
        }
        if (diff(this.translationY, motionConstrainedPoint.translationY)) {
            hashSet.add("translationY");
        }
        if (diff(this.translationZ, motionConstrainedPoint.translationZ)) {
            hashSet.add("translationZ");
        }
    }

    public void fillStandard(double[] dArr, int[] iArr) {
        int i2 = 0;
        float[] fArr = {this.position, this.f17x, this.f18y, this.width, this.height, this.alpha, this.elevation, this.rotation, this.rotationX, this.rotationY, this.scaleX, this.scaleY, this.mPivotX, this.mPivotY, this.translationX, this.translationY, this.translationZ, this.mPathRotate};
        for (int i3 : iArr) {
            if (i3 < 18) {
                dArr[i2] = fArr[i3];
                i2++;
            }
        }
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

    public boolean hasCustomData(String str) {
        return this.attributes.containsKey(str);
    }

    public void setBounds(float f2, float f3, float f4, float f5) {
        this.f17x = f2;
        this.f18y = f3;
        this.width = f4;
        this.height = f5;
    }

    public void setState(View view) {
        setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        applyParameters(view);
    }

    public void applyParameters(ConstraintSet.Constraint constraint) {
        ConstraintSet.PropertySet propertySet = constraint.propertySet;
        int i2 = propertySet.mVisibilityMode;
        this.mVisibilityMode = i2;
        int i3 = propertySet.visibility;
        this.visibility = i3;
        this.alpha = (i3 == 0 || i2 != 0) ? propertySet.alpha : 0.0f;
        ConstraintSet.Transform transform = constraint.transform;
        this.applyElevation = transform.applyElevation;
        this.elevation = transform.elevation;
        this.rotation = transform.rotation;
        this.rotationX = transform.rotationX;
        this.rotationY = transform.rotationY;
        this.scaleX = transform.scaleX;
        this.scaleY = transform.scaleY;
        this.mPivotX = transform.transformPivotX;
        this.mPivotY = transform.transformPivotY;
        this.translationX = transform.translationX;
        this.translationY = transform.translationY;
        this.translationZ = transform.translationZ;
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
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

    public void different(MotionConstrainedPoint motionConstrainedPoint, boolean[] zArr, String[] strArr) {
        zArr[0] = zArr[0] | diff(this.position, motionConstrainedPoint.position);
        zArr[1] = zArr[1] | diff(this.f17x, motionConstrainedPoint.f17x);
        zArr[2] = zArr[2] | diff(this.f18y, motionConstrainedPoint.f18y);
        zArr[3] = zArr[3] | diff(this.width, motionConstrainedPoint.width);
        zArr[4] = diff(this.height, motionConstrainedPoint.height) | zArr[4];
    }

    public void setState(ConstraintWidget constraintWidget, ConstraintSet constraintSet, int i2) {
        setBounds(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        applyParameters(constraintSet.getParameters(i2));
    }
}
