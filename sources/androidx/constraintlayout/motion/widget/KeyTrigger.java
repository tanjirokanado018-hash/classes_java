package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class KeyTrigger extends Key {
    public static final int KEY_TYPE = 5;
    static final String NAME = "KeyTrigger";
    private static final String TAG = "KeyTrigger";
    RectF mCollisionRect;
    private Method mFireCross;
    private boolean mFireCrossReset;
    private float mFireLastPos;
    private Method mFireNegativeCross;
    private boolean mFireNegativeReset;
    private Method mFirePositiveCross;
    private boolean mFirePositiveReset;
    private float mFireThreshold;
    private String mNegativeCross;
    private String mPositiveCross;
    private boolean mPostLayout;
    RectF mTargetRect;
    private int mTriggerCollisionId;
    private View mTriggerCollisionView;
    private int mTriggerID;
    private int mTriggerReceiver;
    float mTriggerSlack;
    private int mCurveFit = -1;
    private String mCross = null;

    public static class Loader {
        private static final int COLLISION = 9;
        private static final int CROSS = 4;
        private static final int FRAME_POS = 8;
        private static final int NEGATIVE_CROSS = 1;
        private static final int POSITIVE_CROSS = 2;
        private static final int POST_LAYOUT = 10;
        private static final int TARGET_ID = 7;
        private static final int TRIGGER_ID = 6;
        private static final int TRIGGER_RECEIVER = 11;
        private static final int TRIGGER_SLACK = 5;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
            mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
            mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
            mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
            mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
        }

        private Loader() {
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0065  */
        public static void read(KeyTrigger keyTrigger, TypedArray typedArray, Context context) {
            int indexCount = typedArray.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArray.getIndex(i2);
                switch (mAttrMap.get(index)) {
                    case 1:
                        keyTrigger.mNegativeCross = typedArray.getString(index);
                        continue;
                        break;
                    case 2:
                        keyTrigger.mPositiveCross = typedArray.getString(index);
                        continue;
                        break;
                    case 4:
                        keyTrigger.mCross = typedArray.getString(index);
                        continue;
                        break;
                    case 5:
                        keyTrigger.mTriggerSlack = typedArray.getFloat(index, keyTrigger.mTriggerSlack);
                        continue;
                        break;
                    case 6:
                        keyTrigger.mTriggerID = typedArray.getResourceId(index, keyTrigger.mTriggerID);
                        continue;
                        break;
                    case 7:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                            keyTrigger.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyTrigger.mTargetString = typedArray.getString(index);
                            } else {
                                continue;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyTrigger.mTargetString = typedArray.getString(index);
                        } else {
                            keyTrigger.mTargetId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                        }
                        break;
                    case 8:
                        int integer = typedArray.getInteger(index, keyTrigger.mFramePosition);
                        keyTrigger.mFramePosition = integer;
                        keyTrigger.mFireThreshold = (integer + 0.5f) / 100.0f;
                        continue;
                        break;
                    case 9:
                        keyTrigger.mTriggerCollisionId = typedArray.getResourceId(index, keyTrigger.mTriggerCollisionId);
                        continue;
                        break;
                    case 10:
                        keyTrigger.mPostLayout = typedArray.getBoolean(index, keyTrigger.mPostLayout);
                        continue;
                        break;
                    case 11:
                        keyTrigger.mTriggerReceiver = typedArray.getResourceId(index, keyTrigger.mTriggerReceiver);
                        break;
                }
                Integer.toHexString(index);
                mAttrMap.get(index);
            }
        }
    }

    public KeyTrigger() {
        int i2 = Key.UNSET;
        this.mTriggerReceiver = i2;
        this.mNegativeCross = null;
        this.mPositiveCross = null;
        this.mTriggerID = i2;
        this.mTriggerCollisionId = i2;
        this.mTriggerCollisionView = null;
        this.mTriggerSlack = 0.1f;
        this.mFireCrossReset = true;
        this.mFireNegativeReset = true;
        this.mFirePositiveReset = true;
        this.mFireThreshold = Float.NaN;
        this.mPostLayout = false;
        this.mCollisionRect = new RectF();
        this.mTargetRect = new RectF();
        this.mType = 5;
        this.mCustomConstraints = new HashMap<>();
    }

    private void setUpRect(RectF rectF, View view, boolean z2) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (z2) {
            view.getMatrix().mapRect(rectF);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> map) {
    }

    /* JADX WARN: Code duplicated, block: B:38:0x008d  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:45:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:51:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:54:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:56:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:58:0x00dc  */
    public void conditionallyFire(float f2, View view) {
        boolean z2;
        boolean z3;
        boolean z4;
        float f3;
        float f4;
        float f5;
        float f6;
        boolean z5;
        boolean z6 = true;
        if (this.mTriggerCollisionId != Key.UNSET) {
            if (this.mTriggerCollisionView == null) {
                this.mTriggerCollisionView = ((ViewGroup) view.getParent()).findViewById(this.mTriggerCollisionId);
            }
            setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            setUpRect(this.mTargetRect, view, this.mPostLayout);
            if (this.mCollisionRect.intersect(this.mTargetRect)) {
                if (this.mFireCrossReset) {
                    this.mFireCrossReset = false;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (this.mFirePositiveReset) {
                    this.mFirePositiveReset = false;
                    z5 = true;
                } else {
                    z5 = false;
                }
                this.mFireNegativeReset = true;
                z6 = z5;
                z4 = false;
            } else {
                if (this.mFireCrossReset) {
                    z2 = false;
                } else {
                    this.mFireCrossReset = true;
                    z2 = true;
                }
                if (this.mFireNegativeReset) {
                    this.mFireNegativeReset = false;
                    z4 = true;
                } else {
                    z4 = false;
                }
                this.mFirePositiveReset = true;
                z6 = false;
            }
        } else {
            if (this.mFireCrossReset) {
                float f7 = this.mFireThreshold;
                if ((this.mFireLastPos - f7) * (f2 - f7) < 0.0f) {
                    this.mFireCrossReset = false;
                    z2 = true;
                }
                if (this.mFireNegativeReset) {
                    f5 = this.mFireThreshold;
                    f6 = f2 - f5;
                    if ((this.mFireLastPos - f5) * f6 >= 0.0f && f6 < 0.0f) {
                        this.mFireNegativeReset = false;
                        z3 = true;
                    }
                    if (this.mFirePositiveReset) {
                        f3 = this.mFireThreshold;
                        f4 = f2 - f3;
                        if ((this.mFireLastPos - f3) * f4 < 0.0f || f4 <= 0.0f) {
                            z6 = false;
                        } else {
                            this.mFirePositiveReset = false;
                        }
                        z4 = z3;
                    } else {
                        if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                            this.mFirePositiveReset = true;
                        }
                        z4 = z3;
                        z6 = false;
                    }
                } else if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFireNegativeReset = true;
                }
                z3 = false;
                if (this.mFirePositiveReset) {
                    f3 = this.mFireThreshold;
                    f4 = f2 - f3;
                    if ((this.mFireLastPos - f3) * f4 < 0.0f) {
                        z6 = false;
                    } else {
                        z6 = false;
                    }
                    z4 = z3;
                } else {
                    if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z4 = z3;
                    z6 = false;
                }
            } else if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireCrossReset = true;
            }
            z2 = false;
            if (this.mFireNegativeReset) {
                f5 = this.mFireThreshold;
                f6 = f2 - f5;
                if ((this.mFireLastPos - f5) * f6 >= 0.0f) {
                }
                if (this.mFirePositiveReset) {
                    f3 = this.mFireThreshold;
                    f4 = f2 - f3;
                    if ((this.mFireLastPos - f3) * f4 < 0.0f) {
                        z6 = false;
                    } else {
                        z6 = false;
                    }
                    z4 = z3;
                } else {
                    if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z4 = z3;
                    z6 = false;
                }
            } else if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireNegativeReset = true;
            }
            z3 = false;
            if (this.mFirePositiveReset) {
                f3 = this.mFireThreshold;
                f4 = f2 - f3;
                if ((this.mFireLastPos - f3) * f4 < 0.0f) {
                    z6 = false;
                } else {
                    z6 = false;
                }
                z4 = z3;
            } else {
                if (Math.abs(f2 - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFirePositiveReset = true;
                }
                z4 = z3;
                z6 = false;
            }
        }
        this.mFireLastPos = f2;
        if (z4 || z2 || z6) {
            ((MotionLayout) view.getParent()).fireTrigger(this.mTriggerID, z6, f2);
        }
        if (this.mTriggerReceiver != Key.UNSET) {
            view = ((MotionLayout) view.getParent()).findViewById(this.mTriggerReceiver);
        }
        if (z4 && this.mNegativeCross != null) {
            if (this.mFireNegativeCross == null) {
                try {
                    this.mFireNegativeCross = view.getClass().getMethod(this.mNegativeCross, new Class[0]);
                } catch (NoSuchMethodException unused) {
                    view.getClass();
                    Debug.getName(view);
                }
            }
            try {
                this.mFireNegativeCross.invoke(view, new Object[0]);
            } catch (Exception unused2) {
                view.getClass();
                Debug.getName(view);
            }
        }
        if (z6 && this.mPositiveCross != null) {
            if (this.mFirePositiveCross == null) {
                try {
                    this.mFirePositiveCross = view.getClass().getMethod(this.mPositiveCross, new Class[0]);
                } catch (NoSuchMethodException unused3) {
                    view.getClass();
                    Debug.getName(view);
                }
            }
            try {
                this.mFirePositiveCross.invoke(view, new Object[0]);
            } catch (Exception unused4) {
                view.getClass();
                Debug.getName(view);
            }
        }
        if (!z2 || this.mCross == null) {
            return;
        }
        if (this.mFireCross == null) {
            try {
                this.mFireCross = view.getClass().getMethod(this.mCross, new Class[0]);
            } catch (NoSuchMethodException unused5) {
                view.getClass();
                Debug.getName(view);
            }
        }
        try {
            this.mFireCross.invoke(view, new Object[0]);
        } catch (Exception unused6) {
            view.getClass();
            Debug.getName(view);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    public int getCurveFit() {
        return this.mCurveFit;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTrigger), context);
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
    }
}
