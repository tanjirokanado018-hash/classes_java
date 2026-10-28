package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class StateSet {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    ConstraintSet mDefaultConstraintSet;
    int mDefaultState = -1;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    public static class State {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser xmlPullParser) {
            this.mConstraintID = -1;
            this.mIsLayout = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.State_android_id) {
                    this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == R.styleable.State_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        this.mIsLayout = true;
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void add(Variant variant) {
            this.mVariants.add(variant);
        }

        public int findMatch(float f2, float f3) {
            for (int i2 = 0; i2 < this.mVariants.size(); i2++) {
                if (this.mVariants.get(i2).match(f2, f3)) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public static class Variant {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        public Variant(Context context, XmlPullParser xmlPullParser) {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            this.mIsLayout = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.Variant_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        this.mIsLayout = true;
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxHeight);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMinHeight);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxWidth);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMinWidth);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public boolean match(float f2, float f3) {
            if (!Float.isNaN(this.mMinWidth) && f2 < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && f3 < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || f2 <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || f3 <= this.mMaxHeight;
            }
            return false;
        }
    }

    public StateSet(Context context, XmlPullParser xmlPullParser) {
        load(context, xmlPullParser);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x007b  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void load(Context context, XmlPullParser xmlPullParser) {
        byte b;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.StateSet);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.StateSet_defaultState) {
                this.mDefaultState = typedArrayObtainStyledAttributes.getResourceId(index, this.mDefaultState);
            }
        }
        try {
            int eventType = xmlPullParser.getEventType();
            State state = null;
            while (eventType != 1) {
                if (eventType == 0) {
                    xmlPullParser.getName();
                } else if (eventType == 2) {
                    String name = xmlPullParser.getName();
                    switch (name.hashCode()) {
                        case 80204913:
                            if (name.equals("State")) {
                                b = 2;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1301459538:
                            if (name.equals("LayoutDescription")) {
                                b = 0;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1382829617:
                            if (name.equals("StateSet")) {
                                b = 1;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1901439077:
                            if (name.equals("Variant")) {
                                b = 3;
                            } else {
                                b = -1;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    if (b != 0 && b != 1) {
                        if (b == 2) {
                            state = new State(context, xmlPullParser);
                            this.mStateList.put(state.mId, state);
                        } else if (b == 3) {
                            Variant variant = new Variant(context, xmlPullParser);
                            if (state != null) {
                                state.add(variant);
                            }
                        }
                    }
                } else if (eventType == 3 && "StateSet".equals(xmlPullParser.getName())) {
                    return;
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }

    public int convertToConstraintSet(int i2, int i3, float f2, float f3) {
        State state = this.mStateList.get(i3);
        if (state == null) {
            return i3;
        }
        if (f2 == -1.0f || f3 == -1.0f) {
            if (state.mConstraintID == i2) {
                return i2;
            }
            Iterator<Variant> it = state.mVariants.iterator();
            while (it.hasNext()) {
                if (i2 == it.next().mConstraintID) {
                    return i2;
                }
            }
            return state.mConstraintID;
        }
        Variant variant = null;
        for (Variant variant2 : state.mVariants) {
            if (variant2.match(f2, f3)) {
                if (i2 == variant2.mConstraintID) {
                    return i2;
                }
                variant = variant2;
            }
        }
        return variant != null ? variant.mConstraintID : state.mConstraintID;
    }

    public boolean needsToChange(int i2, float f2, float f3) {
        int i3 = this.mCurrentStateId;
        if (i3 != i2) {
            return true;
        }
        State stateValueAt = i2 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i3);
        int i4 = this.mCurrentConstraintNumber;
        return (i4 == -1 || !stateValueAt.mVariants.get(i4).match(f2, f3)) && this.mCurrentConstraintNumber != stateValueAt.findMatch(f2, f3);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public int stateGetConstraintID(int i2, int i3, int i4) {
        return updateConstraints(-1, i2, i3, i4);
    }

    public int updateConstraints(int i2, int i3, float f2, float f3) {
        int iFindMatch;
        if (i2 != i3) {
            State state = this.mStateList.get(i3);
            if (state == null) {
                return -1;
            }
            int iFindMatch2 = state.findMatch(f2, f3);
            return iFindMatch2 == -1 ? state.mConstraintID : state.mVariants.get(iFindMatch2).mConstraintID;
        }
        State stateValueAt = i3 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(this.mCurrentStateId);
        if (stateValueAt == null) {
            return -1;
        }
        if ((this.mCurrentConstraintNumber == -1 || !stateValueAt.mVariants.get(i2).match(f2, f3)) && i2 != (iFindMatch = stateValueAt.findMatch(f2, f3))) {
            return iFindMatch == -1 ? stateValueAt.mConstraintID : stateValueAt.mVariants.get(iFindMatch).mConstraintID;
        }
        return i2;
    }
}
