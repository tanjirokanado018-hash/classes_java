package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MotionScene {
    static final int ANTICIPATE = 4;
    static final int BOUNCE = 5;
    private static final boolean DEBUG = false;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final int INTERPOLATOR_REFRENCE_ID = -2;
    public static final int LAYOUT_HONOR_REQUEST = 1;
    public static final int LAYOUT_IGNORE_REQUEST = 0;
    static final int LINEAR = 3;
    private static final int SPLINE_STRING = -1;
    public static final String TAG = "MotionScene";
    static final int TRANSITION_BACKWARD = 0;
    static final int TRANSITION_FORWARD = 1;
    public static final int UNSET = -1;
    private MotionEvent mLastTouchDown;
    float mLastTouchX;
    float mLastTouchY;
    private final MotionLayout mMotionLayout;
    private boolean mRtl;
    private MotionLayout.MotionTracker mVelocityTracker;
    StateSet mStateSet = null;
    Transition mCurrentTransition = null;
    private boolean mDisableAutoTransition = false;
    private ArrayList<Transition> mTransitionList = new ArrayList<>();
    private Transition mDefaultTransition = null;
    private ArrayList<Transition> mAbstractTransitionList = new ArrayList<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private HashMap<String, Integer> mConstraintSetIdMap = new HashMap<>();
    private SparseIntArray mDeriveMap = new SparseIntArray();
    private boolean DEBUG_DESKTOP = false;
    private int mDefaultDuration = 400;
    private int mLayoutDuringTransition = 0;
    private boolean mMotionOutsideRegion = false;

    public static class Transition {
        public static final int AUTO_ANIMATE_TO_END = 4;
        public static final int AUTO_ANIMATE_TO_START = 3;
        public static final int AUTO_JUMP_TO_END = 2;
        public static final int AUTO_JUMP_TO_START = 1;
        public static final int AUTO_NONE = 0;
        static final int TRANSITION_FLAG_FIRST_DRAW = 1;
        private int mAutoTransition;
        private int mConstraintSetEnd;
        private int mConstraintSetStart;
        private int mDefaultInterpolator;
        private int mDefaultInterpolatorID;
        private String mDefaultInterpolatorString;
        private boolean mDisable;
        private int mDuration;
        private int mId;
        private boolean mIsAbstract;
        private ArrayList<KeyFrames> mKeyFramesList;
        private int mLayoutDuringTransition;
        private final MotionScene mMotionScene;
        private ArrayList<TransitionOnClick> mOnClicks;
        private int mPathMotionArc;
        private float mStagger;
        private TouchResponse mTouchResponse;
        private int mTransitionFlags;

        public static class TransitionOnClick implements View.OnClickListener {
            public static final int ANIM_TOGGLE = 17;
            public static final int ANIM_TO_END = 1;
            public static final int ANIM_TO_START = 16;
            public static final int JUMP_TO_END = 256;
            public static final int JUMP_TO_START = 4096;
            int mMode;
            int mTargetId;
            private final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.OnClick);
                int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
                for (int i2 = 0; i2 < indexCount; i2++) {
                    int index = typedArrayObtainStyledAttributes.getIndex(i2);
                    if (index == R.styleable.OnClick_targetId) {
                        this.mTargetId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                    } else if (index == R.styleable.OnClick_clickAction) {
                        this.mMode = typedArrayObtainStyledAttributes.getInt(index, this.mMode);
                    }
                }
                typedArrayObtainStyledAttributes.recycle();
            }

            public void addOnClickListeners(MotionLayout motionLayout, int i2, Transition transition) {
                boolean z2;
                View viewFindViewById;
                int i3 = this.mTargetId;
                View view = motionLayout;
                if (i3 != -1) {
                    viewFindViewById = motionLayout.findViewById(i3);
                }
                if (view == null) {
                    view = viewFindViewById;
                    return;
                }
                int i4 = transition.mConstraintSetStart;
                int i5 = transition.mConstraintSetEnd;
                if (i4 == -1) {
                    view = viewFindViewById;
                    view.setOnClickListener(this);
                    return;
                }
                int i6 = this.mMode;
                boolean z3 = false;
                if ((i6 & 1) == 0 || i2 != i4) {
                    view = viewFindViewById;
                    z2 = false;
                } else {
                    z2 = true;
                }
                boolean z4 = ((i6 & 1) != 0 && i2 == i4) | z2 | ((i6 & 256) != 0 && i2 == i4) | ((i6 & 16) != 0 && i2 == i5);
                if ((i6 & 4096) != 0 && i2 == i5) {
                    z3 = true;
                }
                if (z4 || z3) {
                    view.setOnClickListener(this);
                }
            }

            public boolean isTransitionViable(Transition transition, MotionLayout motionLayout) {
                Transition transition2 = this.mTransition;
                if (transition2 == transition) {
                    return true;
                }
                int i2 = transition2.mConstraintSetEnd;
                int i3 = this.mTransition.mConstraintSetStart;
                int i4 = motionLayout.mCurrentState;
                if (i3 == -1) {
                    return i4 != i2;
                }
                return i4 == i3 || i4 == i2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                float f2;
                MotionLayout motionLayout = this.mTransition.mMotionScene.mMotionLayout;
                if (motionLayout.isInteractionEnabled()) {
                    if (this.mTransition.mConstraintSetStart == -1) {
                        int currentState = motionLayout.getCurrentState();
                        if (currentState == -1) {
                            motionLayout.transitionToState(this.mTransition.mConstraintSetEnd);
                            return;
                        }
                        Transition transition = new Transition(this.mTransition.mMotionScene, this.mTransition);
                        transition.mConstraintSetStart = currentState;
                        transition.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                        motionLayout.setTransition(transition);
                        motionLayout.transitionToEnd();
                        return;
                    }
                    Transition transition2 = this.mTransition.mMotionScene.mCurrentTransition;
                    int i2 = this.mMode;
                    boolean z2 = false;
                    boolean z3 = ((i2 & 1) == 0 && (i2 & 256) == 0) ? false : true;
                    boolean z4 = ((i2 & 16) == 0 && (i2 & 4096) == 0) ? false : true;
                    if (z3 && z4) {
                        Transition transition3 = this.mTransition.mMotionScene.mCurrentTransition;
                        Transition transition4 = this.mTransition;
                        if (transition3 != transition4) {
                            motionLayout.setTransition(transition4);
                        }
                        if (motionLayout.getCurrentState() != motionLayout.getEndState() && motionLayout.getProgress() <= 0.5f) {
                            z2 = z3;
                            z4 = false;
                        }
                    } else {
                        z2 = z3;
                    }
                    if (isTransitionViable(transition2, motionLayout)) {
                        if (z2 && (this.mMode & 1) != 0) {
                            motionLayout.setTransition(this.mTransition);
                            motionLayout.transitionToEnd();
                            return;
                        }
                        if (z4 && (this.mMode & 16) != 0) {
                            motionLayout.setTransition(this.mTransition);
                            motionLayout.transitionToStart();
                            return;
                        }
                        if (z2 && (this.mMode & 256) != 0) {
                            motionLayout.setTransition(this.mTransition);
                            f2 = 1.0f;
                        } else {
                            if (!z4 || (this.mMode & 4096) == 0) {
                                return;
                            }
                            motionLayout.setTransition(this.mTransition);
                            f2 = 0.0f;
                        }
                        motionLayout.setProgress(f2);
                    }
                }
            }

            public void removeOnClickListeners(MotionLayout motionLayout) {
                View viewFindViewById;
                int i2 = this.mTargetId;
                if (i2 == -1 || (viewFindViewById = motionLayout.findViewById(i2)) == null) {
                    return;
                }
                viewFindViewById.setOnClickListener(null);
            }
        }

        public Transition(int i2, MotionScene motionScene, int i3, int i4) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mId = i2;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = i3;
            this.mConstraintSetEnd = i4;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        private void fill(MotionScene motionScene, Context context, TypedArray typedArray) {
            ConstraintSet constraintSet;
            SparseArray sparseArray;
            int i2;
            int indexCount = typedArray.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = typedArray.getIndex(i3);
                if (index == R.styleable.Transition_constraintSetEnd) {
                    this.mConstraintSetEnd = typedArray.getResourceId(index, this.mConstraintSetEnd);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetEnd))) {
                        constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.mConstraintSetEnd);
                        sparseArray = motionScene.mConstraintSetMap;
                        i2 = this.mConstraintSetEnd;
                        sparseArray.append(i2, constraintSet);
                    }
                } else if (index == R.styleable.Transition_constraintSetStart) {
                    this.mConstraintSetStart = typedArray.getResourceId(index, this.mConstraintSetStart);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetStart))) {
                        constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.mConstraintSetStart);
                        sparseArray = motionScene.mConstraintSetMap;
                        i2 = this.mConstraintSetStart;
                        sparseArray.append(i2, constraintSet);
                    }
                } else if (index == R.styleable.Transition_motionInterpolator) {
                    int i4 = typedArray.peekValue(index).type;
                    if (i4 == 1) {
                        int resourceId = typedArray.getResourceId(index, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (i4 == 3) {
                        String string = typedArray.getString(index);
                        this.mDefaultInterpolatorString = string;
                        if (string.indexOf("/") > 0) {
                            this.mDefaultInterpolatorID = typedArray.getResourceId(index, -1);
                            this.mDefaultInterpolator = -2;
                        } else {
                            this.mDefaultInterpolator = -1;
                        }
                    } else {
                        this.mDefaultInterpolator = typedArray.getInteger(index, this.mDefaultInterpolator);
                    }
                } else if (index == R.styleable.Transition_duration) {
                    this.mDuration = typedArray.getInt(index, this.mDuration);
                } else if (index == R.styleable.Transition_staggered) {
                    this.mStagger = typedArray.getFloat(index, this.mStagger);
                } else if (index == R.styleable.Transition_autoTransition) {
                    this.mAutoTransition = typedArray.getInteger(index, this.mAutoTransition);
                } else if (index == R.styleable.Transition_android_id) {
                    this.mId = typedArray.getResourceId(index, this.mId);
                } else if (index == R.styleable.Transition_transitionDisable) {
                    this.mDisable = typedArray.getBoolean(index, this.mDisable);
                } else if (index == R.styleable.Transition_pathMotionArc) {
                    this.mPathMotionArc = typedArray.getInteger(index, -1);
                } else if (index == R.styleable.Transition_layoutDuringTransition) {
                    this.mLayoutDuringTransition = typedArray.getInteger(index, 0);
                } else if (index == R.styleable.Transition_transitionFlags) {
                    this.mTransitionFlags = typedArray.getInteger(index, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
        }

        private void fillFromAttributeList(MotionScene motionScene, Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Transition);
            fill(motionScene, context, typedArrayObtainStyledAttributes);
            typedArrayObtainStyledAttributes.recycle();
        }

        public void addOnClick(Context context, XmlPullParser xmlPullParser) {
            this.mOnClicks.add(new TransitionOnClick(context, this, xmlPullParser));
        }

        public String debugString(Context context) {
            String resourceEntryName = this.mConstraintSetStart == -1 ? "null" : context.getResources().getResourceEntryName(this.mConstraintSetStart);
            if (this.mConstraintSetEnd == -1) {
                return a.e(resourceEntryName, " -> null");
            }
            return resourceEntryName + " -> " + context.getResources().getResourceEntryName(this.mConstraintSetEnd);
        }

        public int getDuration() {
            return this.mDuration;
        }

        public int getEndConstraintSetId() {
            return this.mConstraintSetEnd;
        }

        public int getId() {
            return this.mId;
        }

        public List<KeyFrames> getKeyFrameList() {
            return this.mKeyFramesList;
        }

        public int getLayoutDuringTransition() {
            return this.mLayoutDuringTransition;
        }

        public List<TransitionOnClick> getOnClickList() {
            return this.mOnClicks;
        }

        public int getPathMotionArc() {
            return this.mPathMotionArc;
        }

        public float getStagger() {
            return this.mStagger;
        }

        public int getStartConstraintSetId() {
            return this.mConstraintSetStart;
        }

        public TouchResponse getTouchResponse() {
            return this.mTouchResponse;
        }

        public boolean isEnabled() {
            return !this.mDisable;
        }

        public boolean isTransitionFlag(int i2) {
            return (i2 & this.mTransitionFlags) != 0;
        }

        public void setDuration(int i2) {
            this.mDuration = i2;
        }

        public void setEnable(boolean z2) {
            this.mDisable = !z2;
        }

        public void setPathMotionArc(int i2) {
            this.mPathMotionArc = i2;
        }

        public void setStagger(float f2) {
            this.mStagger = f2;
        }

        public Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            fillFromAttributeList(motionScene, context, Xml.asAttributeSet(xmlPullParser));
        }

        public Transition(MotionScene motionScene, Transition transition) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mMotionScene = motionScene;
            if (transition != null) {
                this.mPathMotionArc = transition.mPathMotionArc;
                this.mDefaultInterpolator = transition.mDefaultInterpolator;
                this.mDefaultInterpolatorString = transition.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = transition.mDefaultInterpolatorID;
                this.mDuration = transition.mDuration;
                this.mKeyFramesList = transition.mKeyFramesList;
                this.mStagger = transition.mStagger;
                this.mLayoutDuringTransition = transition.mLayoutDuringTransition;
            }
        }
    }

    public MotionScene(Context context, MotionLayout motionLayout, int i2) {
        this.mMotionLayout = motionLayout;
        load(context, i2);
        this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
        this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(R.id.motion_base));
    }

    private int getId(Context context, String str) {
        int identifier;
        if (str.contains("/")) {
            identifier = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
            if (this.DEBUG_DESKTOP) {
                System.out.println("id getMap res = " + identifier);
            }
        } else {
            identifier = -1;
        }
        return (identifier != -1 || str.length() <= 1) ? identifier : Integer.parseInt(str.substring(1));
    }

    private int getIndex(Transition transition) {
        int i2 = transition.mId;
        if (i2 == -1) {
            throw new IllegalArgumentException("The transition must have an id");
        }
        for (int i3 = 0; i3 < this.mTransitionList.size(); i3++) {
            if (this.mTransitionList.get(i3).mId == i2) {
                return i3;
            }
        }
        return -1;
    }

    private int getRealID(int i2) {
        int iStateGetConstraintID;
        StateSet stateSet = this.mStateSet;
        return (stateSet == null || (iStateGetConstraintID = stateSet.stateGetConstraintID(i2, -1, -1)) == -1) ? i2 : iStateGetConstraintID;
    }

    private boolean hasCycleDependency(int i2) {
        int i3 = this.mDeriveMap.get(i2);
        int size = this.mDeriveMap.size();
        while (i3 > 0) {
            if (i3 == i2) {
                return true;
            }
            int i4 = size - 1;
            if (size < 0) {
                return true;
            }
            i3 = this.mDeriveMap.get(i3);
            size = i4;
        }
        return false;
    }

    private boolean isProcessingTouch() {
        return this.mVelocityTracker != null;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x008a  */
    private void load(Context context, int i2) {
        XmlResourceParser xml = context.getResources().getXml(i2);
        try {
            int eventType = xml.getEventType();
            Transition transition = null;
            while (true) {
                if (eventType == 1) {
                    return;
                }
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    if (this.DEBUG_DESKTOP) {
                        System.out.println("parsing = " + name);
                    }
                    switch (name) {
                        case "MotionScene":
                            parseMotionSceneTags(context, xml);
                            break;
                        case "Transition":
                            ArrayList<Transition> arrayList = this.mTransitionList;
                            transition = new Transition(this, context, xml);
                            arrayList.add(transition);
                            if (this.mCurrentTransition == null && !transition.mIsAbstract) {
                                this.mCurrentTransition = transition;
                                if (transition.mTouchResponse != null) {
                                    this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
                                }
                            }
                            if (!transition.mIsAbstract) {
                                break;
                            } else {
                                if (transition.mConstraintSetEnd == -1) {
                                    this.mDefaultTransition = transition;
                                } else {
                                    this.mAbstractTransitionList.add(transition);
                                }
                                this.mTransitionList.remove(transition);
                                break;
                            }
                            break;
                        case "OnSwipe":
                            if (transition == null) {
                                context.getResources().getResourceEntryName(i2);
                                xml.getLineNumber();
                            }
                            transition.mTouchResponse = new TouchResponse(context, this.mMotionLayout, xml);
                            break;
                        case "OnClick":
                            transition.addOnClick(context, xml);
                            break;
                        case "StateSet":
                            this.mStateSet = new StateSet(context, xml);
                            break;
                        case "ConstraintSet":
                            parseConstraintSet(context, xml);
                            break;
                        case "KeyFrameSet":
                            transition.mKeyFramesList.add(new KeyFrames(context, xml));
                            break;
                    }
                }
                eventType = xml.next();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }

    private void parseConstraintSet(Context context, XmlPullParser xmlPullParser) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.setForceId(false);
        int attributeCount = xmlPullParser.getAttributeCount();
        int id = -1;
        int id2 = -1;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String attributeValue = xmlPullParser.getAttributeValue(i2);
            if (this.DEBUG_DESKTOP) {
                System.out.println("id string = " + attributeValue);
            }
            attributeName.getClass();
            if (attributeName.equals("deriveConstraintsFrom")) {
                id2 = getId(context, attributeValue);
            } else if (attributeName.equals("id")) {
                id = getId(context, attributeValue);
                this.mConstraintSetIdMap.put(stripID(attributeValue), Integer.valueOf(id));
            }
        }
        if (id != -1) {
            if (this.mMotionLayout.mDebugPath != 0) {
                constraintSet.setValidateOnParse(true);
            }
            constraintSet.load(context, xmlPullParser);
            if (id2 != -1) {
                this.mDeriveMap.put(id, id2);
            }
            this.mConstraintSetMap.put(id, constraintSet);
        }
    }

    private void parseMotionSceneTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.MotionScene);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.MotionScene_defaultDuration) {
                this.mDefaultDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDefaultDuration);
            } else if (index == R.styleable.MotionScene_layoutDuringTransition) {
                this.mLayoutDuringTransition = typedArrayObtainStyledAttributes.getInteger(index, 0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void readConstraintChain(int i2) {
        int i3 = this.mDeriveMap.get(i2);
        if (i3 > 0) {
            readConstraintChain(this.mDeriveMap.get(i2));
            ConstraintSet constraintSet = this.mConstraintSetMap.get(i2);
            ConstraintSet constraintSet2 = this.mConstraintSetMap.get(i3);
            if (constraintSet2 == null) {
                Debug.getName(this.mMotionLayout.getContext(), i3);
            } else {
                constraintSet.readFallback(constraintSet2);
                this.mDeriveMap.put(i2, -1);
            }
        }
    }

    public static String stripID(String str) {
        if (str == null) {
            return "";
        }
        int iIndexOf = str.indexOf(47);
        return iIndexOf < 0 ? str : str.substring(iIndexOf + 1);
    }

    public void addOnClickListeners(MotionLayout motionLayout, int i2) {
        for (Transition transition : this.mTransitionList) {
            if (transition.mOnClicks.size() > 0) {
                Iterator it = transition.mOnClicks.iterator();
                while (it.hasNext()) {
                    ((Transition.TransitionOnClick) it.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        for (Transition transition2 : this.mAbstractTransitionList) {
            if (transition2.mOnClicks.size() > 0) {
                Iterator it2 = transition2.mOnClicks.iterator();
                while (it2.hasNext()) {
                    ((Transition.TransitionOnClick) it2.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        for (Transition transition3 : this.mTransitionList) {
            if (transition3.mOnClicks.size() > 0) {
                Iterator it3 = transition3.mOnClicks.iterator();
                while (it3.hasNext()) {
                    ((Transition.TransitionOnClick) it3.next()).addOnClickListeners(motionLayout, i2, transition3);
                }
            }
        }
        for (Transition transition4 : this.mAbstractTransitionList) {
            if (transition4.mOnClicks.size() > 0) {
                Iterator it4 = transition4.mOnClicks.iterator();
                while (it4.hasNext()) {
                    ((Transition.TransitionOnClick) it4.next()).addOnClickListeners(motionLayout, i2, transition4);
                }
            }
        }
    }

    public void addTransition(Transition transition) {
        int index = getIndex(transition);
        if (index == -1) {
            this.mTransitionList.add(transition);
        } else {
            this.mTransitionList.set(index, transition);
        }
    }

    public boolean autoTransition(MotionLayout motionLayout, int i2) {
        if (isProcessingTouch() || this.mDisableAutoTransition) {
            return false;
        }
        for (Transition transition : this.mTransitionList) {
            if (transition.mAutoTransition != 0) {
                if (i2 == transition.mConstraintSetStart && (transition.mAutoTransition == 4 || transition.mAutoTransition == 2)) {
                    MotionLayout.TransitionState transitionState = MotionLayout.TransitionState.FINISHED;
                    motionLayout.setState(transitionState);
                    motionLayout.setTransition(transition);
                    if (transition.mAutoTransition == 4) {
                        motionLayout.transitionToEnd();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        transitionState = MotionLayout.TransitionState.MOVING;
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    }
                    motionLayout.setState(transitionState);
                    return true;
                }
                if (i2 == transition.mConstraintSetEnd && (transition.mAutoTransition == 3 || transition.mAutoTransition == 1)) {
                    MotionLayout.TransitionState transitionState2 = MotionLayout.TransitionState.FINISHED;
                    motionLayout.setState(transitionState2);
                    motionLayout.setTransition(transition);
                    if (transition.mAutoTransition == 3) {
                        motionLayout.transitionToStart();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        transitionState2 = MotionLayout.TransitionState.MOVING;
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    }
                    motionLayout.setState(transitionState2);
                    return true;
                }
            }
        }
        return false;
    }

    public Transition bestTransitionFor(int i2, float f2, float f3, MotionEvent motionEvent) {
        if (i2 == -1) {
            return this.mCurrentTransition;
        }
        List<Transition> transitionsWithState = getTransitionsWithState(i2);
        RectF rectF = new RectF();
        float f4 = 0.0f;
        Transition transition = null;
        for (Transition transition2 : transitionsWithState) {
            if (!transition2.mDisable && transition2.mTouchResponse != null) {
                transition2.mTouchResponse.setRTL(this.mRtl);
                RectF touchRegion = transition2.mTouchResponse.getTouchRegion(this.mMotionLayout, rectF);
                if (touchRegion == null || motionEvent == null || touchRegion.contains(motionEvent.getX(), motionEvent.getY())) {
                    RectF touchRegion2 = transition2.mTouchResponse.getTouchRegion(this.mMotionLayout, rectF);
                    if (touchRegion2 == null || motionEvent == null || touchRegion2.contains(motionEvent.getX(), motionEvent.getY())) {
                        float fDot = transition2.mTouchResponse.dot(f2, f3) * (transition2.mConstraintSetEnd == i2 ? -1.0f : 1.1f);
                        if (fDot > f4) {
                            transition = transition2;
                            f4 = fDot;
                        }
                    }
                }
            }
        }
        return transition;
    }

    public void disableAutoTransition(boolean z2) {
        this.mDisableAutoTransition = z2;
    }

    public int gatPathMotionArc() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return transition.mPathMotionArc;
        }
        return -1;
    }

    public ConstraintSet getConstraintSet(int i2) {
        return getConstraintSet(i2, -1, -1);
    }

    public int[] getConstraintSetIds() {
        int size = this.mConstraintSetMap.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = this.mConstraintSetMap.keyAt(i2);
        }
        return iArr;
    }

    public ArrayList<Transition> getDefinedTransitions() {
        return this.mTransitionList;
    }

    public int getDuration() {
        Transition transition = this.mCurrentTransition;
        return transition != null ? transition.mDuration : this.mDefaultDuration;
    }

    public int getEndId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetEnd;
    }

    public Interpolator getInterpolator() {
        int i2 = this.mCurrentTransition.mDefaultInterpolator;
        if (i2 == -2) {
            return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
        }
        if (i2 == -1) {
            final Easing interpolator = Easing.getInterpolator(this.mCurrentTransition.mDefaultInterpolatorString);
            return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionScene.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float f2) {
                    return (float) interpolator.get(f2);
                }
            };
        }
        if (i2 == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (i2 == 1) {
            return new AccelerateInterpolator();
        }
        if (i2 == 2) {
            return new DecelerateInterpolator();
        }
        if (i2 == 4) {
            return new AnticipateInterpolator();
        }
        if (i2 != 5) {
            return null;
        }
        return new BounceInterpolator();
    }

    public Key getKeyFrame(Context context, int i2, int i3, int i4) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return null;
        }
        for (KeyFrames keyFrames : transition.mKeyFramesList) {
            for (Integer num : keyFrames.getKeys()) {
                if (i3 == num.intValue()) {
                    for (Key key : keyFrames.getKeyFramesForView(num.intValue())) {
                        if (key.mFramePosition == i4 && key.mType == i2) {
                            return key;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            Iterator it = transition.mKeyFramesList.iterator();
            while (it.hasNext()) {
                ((KeyFrames) it.next()).addFrames(motionController);
            }
        } else {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator it2 = transition2.mKeyFramesList.iterator();
                while (it2.hasNext()) {
                    ((KeyFrames) it2.next()).addFrames(motionController);
                }
            }
        }
    }

    public float getMaxAcceleration() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getMaxAcceleration();
    }

    public float getMaxVelocity() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getMaxVelocity();
    }

    public boolean getMoveWhenScrollAtTop() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return false;
        }
        return this.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop();
    }

    public float getPathPercent(View view, int i2) {
        return 0.0f;
    }

    public float getProgressDirection(float f2, float f3) {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getProgressDirection(f2, f3);
    }

    public float getStaggered() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return transition.mStagger;
        }
        return 0.0f;
    }

    public int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    public Transition getTransitionById(int i2) {
        for (Transition transition : this.mTransitionList) {
            if (transition.mId == i2) {
                return transition;
            }
        }
        return null;
    }

    public int getTransitionDirection(int i2) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mConstraintSetStart == i2) {
                return 0;
            }
        }
        return 1;
    }

    public List<Transition> getTransitionsWithState(int i2) {
        int realID = getRealID(i2);
        ArrayList arrayList = new ArrayList();
        for (Transition transition : this.mTransitionList) {
            if (transition.mConstraintSetStart == realID || transition.mConstraintSetEnd == realID) {
                arrayList.add(transition);
            }
        }
        return arrayList;
    }

    public boolean hasKeyFramePosition(View view, int i2) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return false;
        }
        Iterator it = transition.mKeyFramesList.iterator();
        while (it.hasNext()) {
            Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
            while (it2.hasNext()) {
                if (it2.next().mFramePosition == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public int lookUpConstraintId(String str) {
        return this.mConstraintSetIdMap.get(str).intValue();
    }

    public String lookUpConstraintName(int i2) {
        for (Map.Entry<String, Integer> entry : this.mConstraintSetIdMap.entrySet()) {
            if (entry.getValue().intValue() == i2) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
    }

    public void processScrollMove(float f2, float f3) {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.scrollMove(f2, f3);
    }

    public void processScrollUp(float f2, float f3) {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.scrollUp(f2, f3);
    }

    public void processTouchEvent(MotionEvent motionEvent, int i2, MotionLayout motionLayout) {
        MotionLayout.MotionTracker motionTracker;
        MotionEvent motionEvent2;
        RectF rectF = new RectF();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (i2 != -1) {
            int action = motionEvent.getAction();
            boolean z2 = false;
            if (action == 0) {
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                this.mLastTouchDown = motionEvent;
                if (this.mCurrentTransition.mTouchResponse != null) {
                    RectF limitBoundsTo = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, rectF);
                    if (limitBoundsTo != null && !limitBoundsTo.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                        this.mLastTouchDown = null;
                        return;
                    }
                    RectF touchRegion = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, rectF);
                    if (touchRegion == null || touchRegion.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                        this.mMotionOutsideRegion = false;
                    } else {
                        this.mMotionOutsideRegion = true;
                    }
                    this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
                    return;
                }
                return;
            }
            if (action == 2) {
                float rawY = motionEvent.getRawY() - this.mLastTouchY;
                float rawX = motionEvent.getRawX() - this.mLastTouchX;
                if ((rawX == 0.0d && rawY == 0.0d) || (motionEvent2 = this.mLastTouchDown) == null) {
                    return;
                }
                Transition transitionBestTransitionFor = bestTransitionFor(i2, rawX, rawY, motionEvent2);
                if (transitionBestTransitionFor != null) {
                    motionLayout.setTransition(transitionBestTransitionFor);
                    RectF touchRegion2 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, rectF);
                    if (touchRegion2 != null && !touchRegion2.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                        z2 = true;
                    }
                    this.mMotionOutsideRegion = z2;
                    this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
                }
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null && !this.mMotionOutsideRegion) {
            this.mCurrentTransition.mTouchResponse.processTouchEvent(motionEvent, this.mVelocityTracker, i2, this);
        }
        this.mLastTouchX = motionEvent.getRawX();
        this.mLastTouchY = motionEvent.getRawY();
        if (motionEvent.getAction() != 1 || (motionTracker = this.mVelocityTracker) == null) {
            return;
        }
        motionTracker.recycle();
        this.mVelocityTracker = null;
        int i3 = motionLayout.mCurrentState;
        if (i3 != -1) {
            autoTransition(motionLayout, i3);
        }
    }

    public void readFallback(MotionLayout motionLayout) {
        for (int i2 = 0; i2 < this.mConstraintSetMap.size(); i2++) {
            int iKeyAt = this.mConstraintSetMap.keyAt(i2);
            if (hasCycleDependency(iKeyAt)) {
                return;
            }
            readConstraintChain(iKeyAt);
        }
        for (int i3 = 0; i3 < this.mConstraintSetMap.size(); i3++) {
            this.mConstraintSetMap.valueAt(i3).readFallback(motionLayout);
        }
    }

    public void removeTransition(Transition transition) {
        int index = getIndex(transition);
        if (index != -1) {
            this.mTransitionList.remove(index);
        }
    }

    public void setConstraintSet(int i2, ConstraintSet constraintSet) {
        this.mConstraintSetMap.put(i2, constraintSet);
    }

    public void setDuration(int i2) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            transition.setDuration(i2);
        } else {
            this.mDefaultDuration = i2;
        }
    }

    public void setKeyframe(View view, int i2, String str, Object obj) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return;
        }
        Iterator it = transition.mKeyFramesList.iterator();
        while (it.hasNext()) {
            Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
            while (it2.hasNext()) {
                if (it2.next().mFramePosition == i2) {
                    if (obj != null) {
                    }
                    str.equalsIgnoreCase("app:PerpendicularPath_percent");
                }
            }
        }
    }

    public void setRtl(boolean z2) {
        this.mRtl = z2;
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0026  */
    /* JADX WARN: Code duplicated, block: B:33:0x0068  */
    /* JADX WARN: Code duplicated, block: B:38:0x0083  */
    /* JADX WARN: Code duplicated, block: B:43:0x005a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x0074 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:0x0062 A[SYNTHETIC] */
    public void setTransition(int i2, int i3) {
        int iStateGetConstraintID;
        int iStateGetConstraintID2;
        Iterator<Transition> it;
        Transition transition;
        Transition transition2;
        Transition next;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null) {
            iStateGetConstraintID = stateSet.stateGetConstraintID(i2, -1, -1);
            if (iStateGetConstraintID == -1) {
                iStateGetConstraintID = i2;
            }
            iStateGetConstraintID2 = this.mStateSet.stateGetConstraintID(i3, -1, -1);
            if (iStateGetConstraintID2 == -1) {
            }
            it = this.mTransitionList.iterator();
            while (true) {
                if (it.hasNext()) {
                    transition = this.mDefaultTransition;
                    for (Transition transition3 : this.mAbstractTransitionList) {
                        if (transition3.mConstraintSetEnd == i3) {
                            transition = transition3;
                        }
                    }
                    transition2 = new Transition(this, transition);
                    transition2.mConstraintSetStart = iStateGetConstraintID;
                    transition2.mConstraintSetEnd = iStateGetConstraintID2;
                    if (iStateGetConstraintID != -1) {
                        this.mTransitionList.add(transition2);
                    }
                    this.mCurrentTransition = transition2;
                    return;
                }
                next = it.next();
                if ((next.mConstraintSetEnd != iStateGetConstraintID2 && next.mConstraintSetStart == iStateGetConstraintID) || (next.mConstraintSetEnd == i3 && next.mConstraintSetStart == i2)) {
                    break;
                }
            }
            this.mCurrentTransition = next;
            if (next != null || next.mTouchResponse == null) {
            }
            this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
            return;
        }
        iStateGetConstraintID = i2;
        iStateGetConstraintID2 = i3;
        it = this.mTransitionList.iterator();
        while (true) {
            if (it.hasNext()) {
                transition = this.mDefaultTransition;
                while (r3.hasNext()) {
                    if (transition3.mConstraintSetEnd == i3) {
                        transition = transition3;
                    }
                }
                transition2 = new Transition(this, transition);
                transition2.mConstraintSetStart = iStateGetConstraintID;
                transition2.mConstraintSetEnd = iStateGetConstraintID2;
                if (iStateGetConstraintID != -1) {
                    this.mTransitionList.add(transition2);
                }
                this.mCurrentTransition = transition2;
                return;
            }
            next = it.next();
            if (next.mConstraintSetEnd != iStateGetConstraintID2) {
            }
        }
        this.mCurrentTransition = next;
        if (next != null) {
        }
    }

    public void setupTouch() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.setupTouch();
    }

    public boolean supportTouch() {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        return (transition == null || transition.mTouchResponse == null) ? false : true;
    }

    public boolean validateLayout(MotionLayout motionLayout) {
        return motionLayout == this.mMotionLayout && motionLayout.mScene == this;
    }

    public MotionScene(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    public ConstraintSet getConstraintSet(int i2, int i3, int i4) {
        ConstraintSet constraintSet;
        int iStateGetConstraintID;
        if (this.DEBUG_DESKTOP) {
            PrintStream printStream = System.out;
            printStream.println("id " + i2);
            printStream.println("size " + this.mConstraintSetMap.size());
        }
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (iStateGetConstraintID = stateSet.stateGetConstraintID(i2, i3, i4)) != -1) {
            i2 = iStateGetConstraintID;
        }
        if (this.mConstraintSetMap.get(i2) == null) {
            Debug.getName(this.mMotionLayout.getContext(), i2);
            SparseArray<ConstraintSet> sparseArray = this.mConstraintSetMap;
            constraintSet = sparseArray.get(sparseArray.keyAt(0));
        } else {
            constraintSet = this.mConstraintSetMap.get(i2);
        }
        return constraintSet;
    }

    public void setTransition(Transition transition) {
        this.mCurrentTransition = transition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    public ConstraintSet getConstraintSet(Context context, String str) {
        if (this.DEBUG_DESKTOP) {
            PrintStream printStream = System.out;
            printStream.println("id " + str);
            printStream.println("size " + this.mConstraintSetMap.size());
        }
        for (int i2 = 0; i2 < this.mConstraintSetMap.size(); i2++) {
            int iKeyAt = this.mConstraintSetMap.keyAt(i2);
            String resourceName = context.getResources().getResourceName(iKeyAt);
            if (this.DEBUG_DESKTOP) {
                System.out.println("Id for <" + i2 + "> is <" + resourceName + "> looking for <" + str + ">");
            }
            if (str.equals(resourceName)) {
                return this.mConstraintSetMap.get(iKeyAt);
            }
        }
        return null;
    }
}
