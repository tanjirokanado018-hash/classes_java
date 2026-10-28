package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class KeyPosition extends KeyPositionBase {
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    private static final String PERCENT_X = "percentX";
    private static final String PERCENT_Y = "percentY";
    private static final String TAG = "KeyPosition";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    String mTransitionEasing = null;
    int mPathMotionArc = Key.UNSET;
    int mDrawPath = 0;
    float mPercentWidth = Float.NaN;
    float mPercentHeight = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    int mPositionType = 0;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;

    public static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:27:0x00a5  */
        public static void read(KeyPosition keyPosition, TypedArray typedArray) {
            float f2;
            int indexCount = typedArray.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArray.getIndex(i2);
                switch (mAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyPosition.mTargetId);
                            keyPosition.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyPosition.mTargetString = typedArray.getString(index);
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyPosition.mTargetString = typedArray.getString(index);
                        } else {
                            keyPosition.mTargetId = typedArray.getResourceId(index, keyPosition.mTargetId);
                            continue;
                        }
                        break;
                    case 2:
                        keyPosition.mFramePosition = typedArray.getInt(index, keyPosition.mFramePosition);
                        continue;
                        break;
                    case 3:
                        keyPosition.mTransitionEasing = typedArray.peekValue(index).type == 3 ? typedArray.getString(index) : Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                        continue;
                        break;
                    case 4:
                        keyPosition.mCurveFit = typedArray.getInteger(index, keyPosition.mCurveFit);
                        continue;
                        break;
                    case 5:
                        keyPosition.mDrawPath = typedArray.getInt(index, keyPosition.mDrawPath);
                        continue;
                        break;
                    case 6:
                        keyPosition.mPercentX = typedArray.getFloat(index, keyPosition.mPercentX);
                        continue;
                        break;
                    case 7:
                        keyPosition.mPercentY = typedArray.getFloat(index, keyPosition.mPercentY);
                        continue;
                        break;
                    case 8:
                        f2 = typedArray.getFloat(index, keyPosition.mPercentHeight);
                        keyPosition.mPercentWidth = f2;
                        break;
                    case 9:
                        keyPosition.mPositionType = typedArray.getInt(index, keyPosition.mPositionType);
                        continue;
                        break;
                    case 10:
                        keyPosition.mPathMotionArc = typedArray.getInt(index, keyPosition.mPathMotionArc);
                        continue;
                        break;
                    case 11:
                        keyPosition.mPercentWidth = typedArray.getFloat(index, keyPosition.mPercentWidth);
                        continue;
                        break;
                    case 12:
                        f2 = typedArray.getFloat(index, keyPosition.mPercentHeight);
                        break;
                    default:
                        Integer.toHexString(index);
                        mAttrMap.get(index);
                        continue;
                        break;
                }
                keyPosition.mPercentHeight = f2;
            }
            int i3 = keyPosition.mFramePosition;
        }
    }

    public KeyPosition() {
        this.mType = 2;
    }

    private void calcCartesianPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f9 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f10 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) (((Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX) * f7) + (f8 * f6) + f2);
        this.mCalculatedPositionY = (int) ((f7 * f10) + (f6 * f9) + f3);
    }

    private void calcPathPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = this.mPercentX;
        float f9 = (f6 * f8) + f2;
        float f10 = this.mPercentY;
        this.mCalculatedPositionX = ((-f7) * f10) + f9;
        this.mCalculatedPositionY = (f6 * f10) + (f7 * f8) + f3;
    }

    private void calcScreenPosition(int i2, int i3) {
        float f2 = this.mPercentX;
        float f3 = 0;
        this.mCalculatedPositionX = (i2 * f2) + f3;
        this.mCalculatedPositionY = (i3 * f2) + f3;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> map) {
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void calcPosition(int i2, int i3, float f2, float f3, float f4, float f5) {
        int i4 = this.mPositionType;
        if (i4 == 1) {
            calcPathPosition(f2, f3, f4, f5);
        } else if (i4 != 2) {
            calcCartesianPosition(f2, f3, f4, f5);
        } else {
            calcScreenPosition(i2, i3);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public float getPositionX() {
        return this.mCalculatedPositionX;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public float getPositionY() {
        return this.mCalculatedPositionY;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public boolean intersects(int i2, int i3, RectF rectF, RectF rectF2, float f2, float f3) {
        calcPosition(i2, i3, rectF.centerX(), rectF.centerY(), rectF2.centerX(), rectF2.centerY());
        return Math.abs(f2 - this.mCalculatedPositionX) < 20.0f && Math.abs(f3 - this.mCalculatedPositionY) < 20.0f;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void positionAttributes(View view, RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        int i2 = this.mPositionType;
        if (i2 == 1) {
            positionPathAttributes(rectF, rectF2, f2, f3, strArr, fArr);
        } else if (i2 != 2) {
            positionCartAttributes(rectF, rectF2, f2, f3, strArr, fArr);
        } else {
            positionScreenAttributes(view, rectF, rectF2, f2, f3, strArr, fArr);
        }
    }

    public void positionCartAttributes(RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = rectF.centerX();
        float fCenterY = rectF.centerY();
        float fCenterX2 = rectF2.centerX() - fCenterX;
        float fCenterY2 = rectF2.centerY() - fCenterY;
        String str = strArr[0];
        if (str == null) {
            strArr[0] = PERCENT_X;
            fArr[0] = (f2 - fCenterX) / fCenterX2;
            strArr[1] = PERCENT_Y;
            fArr[1] = (f3 - fCenterY) / fCenterY2;
            return;
        }
        float f4 = (f2 - fCenterX) / fCenterX2;
        if (PERCENT_X.equals(str)) {
            fArr[0] = f4;
            fArr[1] = (f3 - fCenterY) / fCenterY2;
        } else {
            fArr[1] = f4;
            fArr[0] = (f3 - fCenterY) / fCenterY2;
        }
    }

    public void positionPathAttributes(RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = rectF.centerX();
        float fCenterY = rectF.centerY();
        float fCenterX2 = rectF2.centerX() - fCenterX;
        float fCenterY2 = rectF2.centerY() - fCenterY;
        float fHypot = (float) Math.hypot(fCenterX2, fCenterY2);
        if (fHypot < 1.0E-4d) {
            System.out.println("distance ~ 0");
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            return;
        }
        float f4 = fCenterX2 / fHypot;
        float f5 = fCenterY2 / fHypot;
        float f6 = f3 - fCenterY;
        float f7 = f2 - fCenterX;
        float f8 = ((f4 * f6) - (f7 * f5)) / fHypot;
        float f9 = ((f5 * f6) + (f4 * f7)) / fHypot;
        String str = strArr[0];
        if (str != null) {
            if (PERCENT_X.equals(str)) {
                fArr[0] = f9;
                fArr[1] = f8;
                return;
            }
            return;
        }
        strArr[0] = PERCENT_X;
        strArr[1] = PERCENT_Y;
        fArr[0] = f9;
        fArr[1] = f8;
    }

    public void positionScreenAttributes(View view, RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        rectF.centerX();
        rectF.centerY();
        rectF2.centerX();
        rectF2.centerY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        String str = strArr[0];
        if (str == null) {
            strArr[0] = PERCENT_X;
            fArr[0] = f2 / width;
            strArr[1] = PERCENT_Y;
            fArr[1] = f3 / height;
            return;
        }
        float f4 = f2 / width;
        if (PERCENT_X.equals(str)) {
            fArr[0] = f4;
            fArr[1] = f3 / height;
        } else {
            fArr[1] = f4;
            fArr[0] = f3 / height;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        float f2;
        str.getClass();
        switch (str) {
            case "transitionEasing":
                this.mTransitionEasing = obj.toString();
                return;
            case "percentWidth":
                this.mPercentWidth = toFloat(obj);
                return;
            case "percentHeight":
                f2 = toFloat(obj);
                break;
            case "drawPath":
                this.mDrawPath = toInt(obj);
                return;
            case "sizePercent":
                f2 = toFloat(obj);
                this.mPercentWidth = f2;
                break;
            case "percentX":
                this.mPercentX = toFloat(obj);
                return;
            case "percentY":
                this.mPercentY = toFloat(obj);
                return;
            default:
                return;
        }
        this.mPercentHeight = f2;
    }
}
