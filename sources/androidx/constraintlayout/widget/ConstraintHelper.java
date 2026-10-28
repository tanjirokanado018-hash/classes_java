package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ConstraintHelper extends View {
    protected int mCount;
    protected Helper mHelperWidget;
    protected int[] mIds;
    private HashMap<Integer, String> mMap;
    protected String mReferenceIds;
    protected boolean mUseViewMeasure;
    private View[] mViews;
    protected Context myContext;

    public ConstraintHelper(Context context) {
        super(context);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(null);
    }

    private void addID(String str) {
        if (str == null || str.length() == 0 || this.myContext == null) {
            return;
        }
        String strTrim = str.trim();
        if (getParent() instanceof ConstraintLayout) {
        }
        int iFindId = findId(strTrim);
        if (iFindId != 0) {
            this.mMap.put(Integer.valueOf(iFindId), strTrim);
            addRscID(iFindId);
        }
    }

    private void addRscID(int i2) {
        if (i2 == getId()) {
            return;
        }
        int i3 = this.mCount + 1;
        int[] iArr = this.mIds;
        if (i3 > iArr.length) {
            this.mIds = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.mIds;
        int i4 = this.mCount;
        iArr2[i4] = i2;
        this.mCount = i4 + 1;
    }

    private int[] convertReferenceString(View view, String str) {
        String[] strArrSplit = str.split(",");
        view.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i2 = 0;
        for (String str2 : strArrSplit) {
            int iFindId = findId(str2.trim());
            if (iFindId != 0) {
                iArr[i2] = iFindId;
                i2++;
            }
        }
        return i2 != strArrSplit.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    private int findId(ConstraintLayout constraintLayout, String str) {
        Resources resources;
        String resourceEntryName;
        if (str == null || constraintLayout == null || (resources = this.myContext.getResources()) == null) {
            return 0;
        }
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            if (childAt.getId() != -1) {
                try {
                    resourceEntryName = resources.getResourceEntryName(childAt.getId());
                } catch (Resources.NotFoundException unused) {
                    resourceEntryName = null;
                }
                if (str.equals(resourceEntryName)) {
                    return childAt.getId();
                }
            }
        }
        return 0;
    }

    public void addView(View view) {
        if (view == this || view.getId() == -1 || view.getParent() == null) {
            return;
        }
        this.mReferenceIds = null;
        addRscID(view.getId());
        requestLayout();
    }

    public void applyLayoutFeatures() {
        ViewParent parent = getParent();
        if (parent == null || !(parent instanceof ConstraintLayout)) {
            return;
        }
        applyLayoutFeatures((ConstraintLayout) parent);
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.mIds, this.mCount);
    }

    public View[] getViews(ConstraintLayout constraintLayout) {
        View[] viewArr = this.mViews;
        if (viewArr == null || viewArr.length != this.mCount) {
            this.mViews = new View[this.mCount];
        }
        for (int i2 = 0; i2 < this.mCount; i2++) {
            this.mViews[i2] = constraintLayout.getViewById(this.mIds[i2]);
        }
        return this.mViews;
    }

    public void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mReferenceIds = string;
                    setIds(string);
                }
            }
        }
    }

    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        ConstraintSet.Layout layout = constraint.layout;
        int[] iArr = layout.mReferenceIds;
        if (iArr != null) {
            setReferencedIds(iArr);
        } else {
            String str = layout.mReferenceIdString;
            if (str != null && str.length() > 0) {
                ConstraintSet.Layout layout2 = constraint.layout;
                layout2.mReferenceIds = convertReferenceString(this, layout2.mReferenceIdString);
            }
        }
        helperWidget.removeAllIds();
        if (constraint.layout.mReferenceIds == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            int[] iArr2 = constraint.layout.mReferenceIds;
            if (i2 >= iArr2.length) {
                return;
            }
            ConstraintWidget constraintWidget = sparseArray.get(iArr2[i2]);
            if (constraintWidget != null) {
                helperWidget.add(constraintWidget);
            }
            i2++;
        }
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String str = this.mReferenceIds;
        if (str != null) {
            setIds(str);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        if (this.mUseViewMeasure) {
            super.onMeasure(i2, i3);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public void removeView(View view) {
        int i2;
        int id = view.getId();
        if (id == -1) {
            return;
        }
        this.mReferenceIds = null;
        int i3 = 0;
        while (i3 < this.mCount) {
            if (this.mIds[i3] == id) {
                while (true) {
                    i2 = this.mCount;
                    if (i3 >= i2 - 1) {
                        break;
                    }
                    int[] iArr = this.mIds;
                    int i4 = i3 + 1;
                    iArr[i3] = iArr[i4];
                    i3 = i4;
                }
                this.mIds[i2 - 1] = 0;
                this.mCount = i2 - 1;
                break;
            }
            i3++;
        }
        requestLayout();
    }

    public void resolveRtl(ConstraintWidget constraintWidget, boolean z2) {
    }

    public void setIds(String str) {
        this.mReferenceIds = str;
        if (str == null) {
            return;
        }
        int i2 = 0;
        this.mCount = 0;
        while (true) {
            int iIndexOf = str.indexOf(44, i2);
            if (iIndexOf == -1) {
                addID(str.substring(i2));
                return;
            } else {
                addID(str.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    public void setReferencedIds(int[] iArr) {
        this.mReferenceIds = null;
        this.mCount = 0;
        for (int i2 : iArr) {
            addRscID(i2);
        }
    }

    public void updatePostConstraints(ConstraintLayout constraintLayout) {
    }

    public void updatePostLayout(ConstraintLayout constraintLayout) {
    }

    public void updatePostMeasure(ConstraintLayout constraintLayout) {
    }

    public void updatePreDraw(ConstraintLayout constraintLayout) {
    }

    public void updatePreLayout(ConstraintWidgetContainer constraintWidgetContainer, Helper helper, SparseArray<ConstraintWidget> sparseArray) {
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            helper.add(sparseArray.get(this.mIds[i2]));
        }
    }

    public void validateParams() {
        if (this.mHelperWidget == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).widget = (ConstraintWidget) this.mHelperWidget;
        }
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(attributeSet);
    }

    private int findId(String str) {
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        int iFindId = 0;
        if (isInEditMode() && constraintLayout != null) {
            Object designInformation = constraintLayout.getDesignInformation(0, str);
            if (designInformation instanceof Integer) {
                iFindId = ((Integer) designInformation).intValue();
            }
        }
        if (iFindId == 0 && constraintLayout != null) {
            iFindId = findId(constraintLayout, str);
        }
        if (iFindId == 0) {
            try {
                iFindId = R.id.class.getField(str).getInt(null);
            } catch (Exception unused) {
            }
        }
        return iFindId == 0 ? this.myContext.getResources().getIdentifier(str, "id", this.myContext.getPackageName()) : iFindId;
    }

    public void applyLayoutFeatures(ConstraintLayout constraintLayout) {
        int visibility = getVisibility();
        float elevation = getElevation();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            View viewById = constraintLayout.getViewById(this.mIds[i2]);
            if (viewById != null) {
                viewById.setVisibility(visibility);
                if (elevation > 0.0f) {
                    viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                }
            }
        }
    }

    public void updatePreLayout(ConstraintLayout constraintLayout) {
        String str;
        int iFindId;
        if (isInEditMode()) {
            setIds(this.mReferenceIds);
        }
        Helper helper = this.mHelperWidget;
        if (helper == null) {
            return;
        }
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            int i3 = this.mIds[i2];
            View viewById = constraintLayout.getViewById(i3);
            if (viewById == null && (iFindId = findId(constraintLayout, (str = this.mMap.get(Integer.valueOf(i3))))) != 0) {
                this.mIds[i2] = iFindId;
                this.mMap.put(Integer.valueOf(iFindId), str);
                viewById = constraintLayout.getViewById(iFindId);
            }
            if (viewById != null) {
                this.mHelperWidget.add(constraintLayout.getViewWidget(viewById));
            }
        }
        this.mHelperWidget.updateConstraints(constraintLayout.mLayoutWidget);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(attributeSet);
    }
}
