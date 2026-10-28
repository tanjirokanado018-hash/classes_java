package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.LinearLayout;
import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface DividerMode {
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LinearLayoutCompat> {
        private int mBaselineAlignedChildIndexId;
        private int mBaselineAlignedId;
        private int mDividerId;
        private int mDividerPaddingId;
        private int mGravityId;
        private int mMeasureWithLargestChildId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mShowDividersId;
        private int mWeightSumId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(@NonNull PropertyMapper propertyMapper) {
            this.mBaselineAlignedId = propertyMapper.mapBoolean("baselineAligned", R.attr.baselineAligned);
            this.mBaselineAlignedChildIndexId = propertyMapper.mapInt("baselineAlignedChildIndex", R.attr.baselineAlignedChildIndex);
            this.mGravityId = propertyMapper.mapGravity("gravity", R.attr.gravity);
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", R.attr.orientation, new IntFunction<String>() { // from class: androidx.appcompat.widget.LinearLayoutCompat.InspectionCompanion.1
                @Override // java.util.function.IntFunction
                public String apply(int i2) {
                    if (i2 != 0) {
                        return i2 != 1 ? String.valueOf(i2) : "vertical";
                    }
                    return "horizontal";
                }
            });
            this.mWeightSumId = propertyMapper.mapFloat("weightSum", R.attr.weightSum);
            this.mDividerId = propertyMapper.mapObject("divider", androidx.appcompat.R.attr.divider);
            this.mDividerPaddingId = propertyMapper.mapInt("dividerPadding", androidx.appcompat.R.attr.dividerPadding);
            this.mMeasureWithLargestChildId = propertyMapper.mapBoolean("measureWithLargestChild", androidx.appcompat.R.attr.measureWithLargestChild);
            this.mShowDividersId = propertyMapper.mapIntFlag("showDividers", androidx.appcompat.R.attr.showDividers, new IntFunction<Set<String>>() { // from class: androidx.appcompat.widget.LinearLayoutCompat.InspectionCompanion.2
                @Override // java.util.function.IntFunction
                public Set<String> apply(int i2) {
                    HashSet hashSet = new HashSet();
                    if (i2 == 0) {
                        hashSet.add("none");
                    }
                    if (i2 == 1) {
                        hashSet.add("beginning");
                    }
                    if (i2 == 2) {
                        hashSet.add("middle");
                    }
                    if (i2 == 4) {
                        hashSet.add("end");
                    }
                    return hashSet;
                }
            });
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(@NonNull LinearLayoutCompat linearLayoutCompat, @NonNull PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw androidx.activity.f.f();
            }
            propertyReader.readBoolean(this.mBaselineAlignedId, linearLayoutCompat.isBaselineAligned());
            propertyReader.readInt(this.mBaselineAlignedChildIndexId, linearLayoutCompat.getBaselineAlignedChildIndex());
            propertyReader.readGravity(this.mGravityId, linearLayoutCompat.getGravity());
            propertyReader.readIntEnum(this.mOrientationId, linearLayoutCompat.getOrientation());
            propertyReader.readFloat(this.mWeightSumId, linearLayoutCompat.getWeightSum());
            propertyReader.readObject(this.mDividerId, linearLayoutCompat.getDividerDrawable());
            propertyReader.readInt(this.mDividerPaddingId, linearLayoutCompat.getDividerPadding());
            propertyReader.readBoolean(this.mMeasureWithLargestChildId, linearLayoutCompat.isMeasureWithLargestChildEnabled());
            propertyReader.readIntFlag(this.mShowDividersId, linearLayoutCompat.getShowDividers());
        }
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(int i2, int i3) {
            super(i2, i3);
        }

        public LayoutParams(int i2, int i3, float f2) {
            super(i2, i3, f2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    private void forceUniformHeight(int i2, int i3) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), BasicMeasure.EXACTLY);
        for (int i4 = 0; i4 < i2; i4++) {
            View virtualChildAt = getVirtualChildAt(i4);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).height == -1) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).width;
                    ((LinearLayout.LayoutParams) layoutParams).width = virtualChildAt.getMeasuredWidth();
                    measureChildWithMargins(virtualChildAt, i3, 0, iMakeMeasureSpec, 0);
                    ((LinearLayout.LayoutParams) layoutParams).width = i5;
                }
            }
        }
    }

    private void forceUniformWidth(int i2, int i3) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), BasicMeasure.EXACTLY);
        for (int i4 = 0; i4 < i2; i4++) {
            View virtualChildAt = getVirtualChildAt(i4);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).width == -1) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).height;
                    ((LinearLayout.LayoutParams) layoutParams).height = virtualChildAt.getMeasuredHeight();
                    measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i3, 0);
                    ((LinearLayout.LayoutParams) layoutParams).height = i5;
                }
            }
        }
    }

    private void setChildFrame(View view, int i2, int i3, int i4, int i5) {
        view.layout(i2, i3, i4 + i2, i5 + i3);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void drawDividersHorizontal(Canvas canvas) {
        int right;
        int left;
        int paddingRight;
        int virtualChildCount = getVirtualChildCount();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                drawVerticalDivider(canvas, zIsLayoutRtl ? virtualChildAt.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (virtualChildAt.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 != null) {
                LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                if (zIsLayoutRtl) {
                    left = virtualChildAt2.getLeft();
                    paddingRight = ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    right = (left - paddingRight) - this.mDividerWidth;
                } else {
                    right = virtualChildAt2.getRight() + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                }
            } else if (zIsLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth();
                paddingRight = getPaddingRight();
                right = (left - paddingRight) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    public void drawDividersVertical(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt.getLayoutParams())).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            drawHorizontalDivider(canvas, virtualChildAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : virtualChildAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt2.getLayoutParams())).bottomMargin);
        }
    }

    public void drawHorizontalDivider(Canvas canvas, int i2) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i2, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i2);
        this.mDivider.draw(canvas);
    }

    public void drawVerticalDivider(Canvas canvas, int i2) {
        this.mDivider.setBounds(i2, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i2, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i2;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i3 = this.mBaselineAlignedChildIndex;
        if (childCount <= i3) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i3);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i2 = this.mGravity & 112) != 48) {
            if (i2 == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i2 == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return bottom + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public int getChildrenSkipCount(View view, int i2) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @GravityInt
    public int getGravity() {
        return this.mGravity;
    }

    public int getLocationOffset(View view) {
        return 0;
    }

    public int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public View getVirtualChildAt(int i2) {
        return getChildAt(i2);
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean hasDividerBeforeChildAt(int i2) {
        if (i2 == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i2 == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (getChildAt(i3).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:33:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:35:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:39:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:41:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:42:0x00de  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:45:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:48:0x0103  */
    public void layoutHorizontal(int i2, int i3, int i4, int i5) {
        int paddingLeft;
        int i6;
        int i7;
        int i8;
        int baseline;
        int i9;
        int i10;
        int measuredHeight;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i11 = i5 - i3;
        int paddingBottom = i11 - getPaddingBottom();
        int paddingBottom2 = (i11 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i12 = this.mGravity;
        int i13 = i12 & 112;
        boolean z2 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(8388615 & i12, getLayoutDirection());
        if (absoluteGravity != 1) {
            paddingLeft = absoluteGravity != 5 ? getPaddingLeft() : ((getPaddingLeft() + i4) - i2) - this.mTotalLength;
        } else {
            paddingLeft = getPaddingLeft() + (((i4 - i2) - this.mTotalLength) / 2);
        }
        if (zIsLayoutRtl) {
            i6 = virtualChildCount - 1;
            i7 = -1;
        } else {
            i6 = 0;
            i7 = 1;
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            int i14 = (i7 * childrenSkipCount) + i6;
            View virtualChildAt = getVirtualChildAt(i14);
            if (virtualChildAt == null) {
                paddingLeft = measureNullChild(i14) + paddingLeft;
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight2 = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    int i15 = childrenSkipCount;
                    if (z2) {
                        i8 = virtualChildCount;
                        baseline = ((LinearLayout.LayoutParams) layoutParams).height != -1 ? virtualChildAt.getBaseline() : -1;
                        i9 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                        if (i9 < 0) {
                            i9 = i13;
                        }
                        i10 = i9 & 112;
                        if (i10 != 16) {
                            measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + ((LinearLayout.LayoutParams) layoutParams).topMargin) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        } else if (i10 != 48) {
                            measuredHeight = ((LinearLayout.LayoutParams) layoutParams).topMargin + paddingTop;
                            if (baseline != -1) {
                                measuredHeight = (iArr[1] - baseline) + measuredHeight;
                            }
                        } else if (i10 != 80) {
                            measuredHeight = paddingTop;
                        } else {
                            measuredHeight = (paddingBottom - measuredHeight2) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline);
                            }
                        }
                        if (hasDividerBeforeChildAt(i14)) {
                            paddingLeft += this.mDividerWidth;
                        }
                        int i16 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                        setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i16, measuredHeight, measuredWidth, measuredHeight2);
                        paddingLeft = getNextLocationOffset(virtualChildAt) + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).rightMargin + i16;
                        childrenSkipCount = getChildrenSkipCount(virtualChildAt, i14) + i15;
                    } else {
                        i8 = virtualChildCount;
                    }
                    i9 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i9 < 0) {
                        i9 = i13;
                    }
                    i10 = i9 & 112;
                    if (i10 != 16) {
                        measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + ((LinearLayout.LayoutParams) layoutParams).topMargin) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    } else if (i10 != 48) {
                        measuredHeight = ((LinearLayout.LayoutParams) layoutParams).topMargin + paddingTop;
                        if (baseline != -1) {
                            measuredHeight = (iArr[1] - baseline) + measuredHeight;
                        }
                    } else if (i10 != 80) {
                        measuredHeight = paddingTop;
                    } else {
                        measuredHeight = (paddingBottom - measuredHeight2) - ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline);
                        }
                    }
                    if (hasDividerBeforeChildAt(i14)) {
                        paddingLeft += this.mDividerWidth;
                    }
                    int i17 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                    setChildFrame(virtualChildAt, getLocationOffset(virtualChildAt) + i17, measuredHeight, measuredWidth, measuredHeight2);
                    paddingLeft = getNextLocationOffset(virtualChildAt) + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).rightMargin + i17;
                    childrenSkipCount = getChildrenSkipCount(virtualChildAt, i14) + i15;
                }
                childrenSkipCount++;
                virtualChildCount = i8;
                i13 = i13;
                paddingTop = paddingTop;
            }
            i8 = virtualChildCount;
            childrenSkipCount++;
            virtualChildCount = i8;
            i13 = i13;
            paddingTop = paddingTop;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00a0  */
    public void layoutVertical(int i2, int i3, int i4, int i5) {
        int paddingTop;
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i4 - i2;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.mGravity;
        int i10 = i9 & 112;
        int i11 = i9 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i10 != 16) {
            paddingTop = i10 != 80 ? getPaddingTop() : ((getPaddingTop() + i5) - i3) - this.mTotalLength;
        } else {
            paddingTop = getPaddingTop() + (((i5 - i3) - this.mTotalLength) / 2);
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                paddingTop = measureNullChild(childrenSkipCount) + paddingTop;
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                int i12 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                if (i12 < 0) {
                    i12 = i11;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i12, getLayoutDirection()) & 7;
                if (absoluteGravity != 1) {
                    if (absoluteGravity != 5) {
                        i7 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                    } else {
                        i6 = paddingRight - measuredWidth;
                    }
                    int i13 = i7;
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        paddingTop += this.mDividerHeight;
                    }
                    int i14 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    setChildFrame(virtualChildAt, i13, getLocationOffset(virtualChildAt) + i14, measuredWidth, measuredHeight);
                    paddingTop = getNextLocationOffset(virtualChildAt) + measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i14;
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                } else {
                    i6 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                }
                i7 = i6 - ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                int i15 = i7;
                if (hasDividerBeforeChildAt(childrenSkipCount)) {
                    paddingTop += this.mDividerHeight;
                }
                int i16 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                setChildFrame(virtualChildAt, i15, getLocationOffset(virtualChildAt) + i16, measuredWidth, measuredHeight);
                paddingTop = getNextLocationOffset(virtualChildAt) + measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i16;
                childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
            }
            childrenSkipCount++;
        }
    }

    public void measureChildBeforeLayout(View view, int i2, int i3, int i4, int i5, int i6) {
        measureChildWithMargins(view, i3, i4, i5, i6);
    }

    /* JADX WARN: Code duplicated, block: B:167:0x03b1 A[PHI: r3
      0x03b1: PHI (r3v34 int) = (r3v30 int), (r3v35 int) binds: [B:171:0x03c2, B:165:0x03ae] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:201:0x0449  */
    /* JADX WARN: Code duplicated, block: B:61:0x016f  */
    /* JADX WARN: Code duplicated, block: B:68:0x0191  */
    /* JADX WARN: Code duplicated, block: B:75:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:78:0x01c5 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:80:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:83:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:86:0x01da  */
    public void measureHorizontal(int i2, int i3) {
        int[] iArr;
        int iCombineMeasuredStates;
        int i4;
        int iMax;
        int i5;
        int i6;
        int baseline;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z2;
        boolean z3;
        View view;
        int i11;
        boolean z4;
        int i12;
        int measuredHeight;
        int childrenSkipCount;
        int baseline2;
        int i13;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr2 = this.mMaxAscent;
        int[] iArr3 = this.mMaxDescent;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z5 = this.mBaselineAligned;
        boolean z6 = this.mUseLargestChild;
        int i14 = BasicMeasure.EXACTLY;
        boolean z7 = mode == 1073741824;
        float f2 = 0.0f;
        int childrenSkipCount2 = 0;
        int iMax2 = 0;
        int iMax3 = 0;
        int iMax4 = 0;
        int iMax5 = 0;
        boolean z8 = false;
        int iCombineMeasuredStates2 = 0;
        boolean z9 = true;
        boolean z10 = false;
        while (true) {
            iArr = iArr3;
            if (childrenSkipCount2 >= virtualChildCount) {
                break;
            }
            View virtualChildAt = getVirtualChildAt(childrenSkipCount2);
            if (virtualChildAt == null) {
                this.mTotalLength = measureNullChild(childrenSkipCount2) + this.mTotalLength;
            } else {
                if (virtualChildAt.getVisibility() == 8) {
                    childrenSkipCount2 += getChildrenSkipCount(virtualChildAt, childrenSkipCount2);
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount2)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    float f3 = ((LinearLayout.LayoutParams) layoutParams).weight;
                    float f4 = f2 + f3;
                    if (mode == i14 && ((LinearLayout.LayoutParams) layoutParams).width == 0 && f3 > 0.0f) {
                        int i15 = this.mTotalLength;
                        if (z7) {
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + i15;
                        } else {
                            this.mTotalLength = Math.max(i15, ((LinearLayout.LayoutParams) layoutParams).leftMargin + i15 + ((LinearLayout.LayoutParams) layoutParams).rightMargin);
                        }
                        if (z5) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            virtualChildAt.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                            i10 = childrenSkipCount2;
                            z2 = z6;
                            z3 = z5;
                            view = virtualChildAt;
                        } else {
                            i10 = childrenSkipCount2;
                            z2 = z6;
                            z3 = z5;
                            view = virtualChildAt;
                            i11 = BasicMeasure.EXACTLY;
                            z8 = true;
                        }
                        if (mode2 == i11 && ((LinearLayout.LayoutParams) layoutParams).height == -1) {
                            z4 = true;
                            z10 = true;
                        } else {
                            z4 = false;
                        }
                        i12 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i12;
                        iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, view.getMeasuredState());
                        if (z3 && (baseline2 = view.getBaseline()) != -1) {
                            i13 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                            if (i13 < 0) {
                                i13 = this.mGravity;
                            }
                            int i16 = (((i13 & 112) >> 4) & (-2)) >> 1;
                            iArr2[i16] = Math.max(iArr2[i16], baseline2);
                            iArr[i16] = Math.max(iArr[i16], measuredHeight - baseline2);
                        }
                        iMax3 = Math.max(iMax3, measuredHeight);
                        if (z9 || ((LinearLayout.LayoutParams) layoutParams).height != -1) {
                            z9 = false;
                        } else {
                            z9 = true;
                        }
                        if (((LinearLayout.LayoutParams) layoutParams).weight > 0.0f) {
                            if (!z4) {
                                i12 = measuredHeight;
                            }
                            iMax5 = Math.max(iMax5, i12);
                        } else {
                            int i17 = iMax5;
                            if (!z4) {
                                i12 = measuredHeight;
                            }
                            iMax4 = Math.max(iMax4, i12);
                            iMax5 = i17;
                        }
                        int i18 = i10;
                        childrenSkipCount = getChildrenSkipCount(view, i18) + i18;
                        f2 = f4;
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams).width != 0 || f3 <= 0.0f) {
                            i9 = Integer.MIN_VALUE;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams).width = -2;
                            i9 = 0;
                        }
                        i10 = childrenSkipCount2;
                        int i19 = i9;
                        z2 = z6;
                        z3 = z5;
                        measureChildBeforeLayout(virtualChildAt, i10, i2, f4 == 0.0f ? this.mTotalLength : 0, i3, 0);
                        if (i19 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) layoutParams).width = i19;
                        }
                        int measuredWidth = virtualChildAt.getMeasuredWidth();
                        if (z7) {
                            view = virtualChildAt;
                            this.mTotalLength = getNextLocationOffset(view) + ((LinearLayout.LayoutParams) layoutParams).leftMargin + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).rightMargin + this.mTotalLength;
                        } else {
                            view = virtualChildAt;
                            int i20 = this.mTotalLength;
                            this.mTotalLength = Math.max(i20, getNextLocationOffset(view) + i20 + measuredWidth + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin);
                        }
                        if (z2) {
                            iMax2 = Math.max(measuredWidth, iMax2);
                        }
                    }
                    i11 = BasicMeasure.EXACTLY;
                    if (mode2 == i11) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    i12 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    measuredHeight = view.getMeasuredHeight() + i12;
                    iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, view.getMeasuredState());
                    if (z3) {
                        i13 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                        if (i13 < 0) {
                            i13 = this.mGravity;
                        }
                        int i110 = (((i13 & 112) >> 4) & (-2)) >> 1;
                        iArr2[i110] = Math.max(iArr2[i110], baseline2);
                        iArr[i110] = Math.max(iArr[i110], measuredHeight - baseline2);
                    }
                    iMax3 = Math.max(iMax3, measuredHeight);
                    if (z9) {
                        z9 = false;
                    } else {
                        z9 = false;
                    }
                    if (((LinearLayout.LayoutParams) layoutParams).weight > 0.0f) {
                        if (!z4) {
                            i12 = measuredHeight;
                        }
                        iMax5 = Math.max(iMax5, i12);
                    } else {
                        int i111 = iMax5;
                        if (!z4) {
                            i12 = measuredHeight;
                        }
                        iMax4 = Math.max(iMax4, i12);
                        iMax5 = i111;
                    }
                    int i112 = i10;
                    childrenSkipCount = getChildrenSkipCount(view, i112) + i112;
                    f2 = f4;
                }
                childrenSkipCount2 = childrenSkipCount + 1;
                iArr3 = iArr;
                z6 = z2;
                z5 = z3;
                i14 = BasicMeasure.EXACTLY;
            }
            childrenSkipCount = childrenSkipCount2;
            z2 = z6;
            z3 = z5;
            childrenSkipCount2 = childrenSkipCount + 1;
            iArr3 = iArr;
            z6 = z2;
            z5 = z3;
            i14 = BasicMeasure.EXACTLY;
        }
        boolean z11 = z6;
        boolean z12 = z5;
        int i21 = iMax3;
        int i22 = iMax4;
        int i23 = iMax5;
        int i24 = iCombineMeasuredStates2;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        int i25 = iArr2[1];
        int iMax6 = (i25 == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? i21 : Math.max(i21, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(i25, iArr2[2]))));
        if (z11 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            int childrenSkipCount3 = 0;
            while (childrenSkipCount3 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(childrenSkipCount3);
                if (virtualChildAt2 == null) {
                    this.mTotalLength = measureNullChild(childrenSkipCount3) + this.mTotalLength;
                } else if (virtualChildAt2.getVisibility() == 8) {
                    childrenSkipCount3 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount3);
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                    int i26 = this.mTotalLength;
                    if (z7) {
                        this.mTotalLength = getNextLocationOffset(virtualChildAt2) + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + iMax2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + i26;
                    } else {
                        this.mTotalLength = Math.max(i26, getNextLocationOffset(virtualChildAt2) + i26 + iMax2 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin);
                    }
                    childrenSkipCount3++;
                    iMax6 = iMax6;
                }
                childrenSkipCount3++;
                iMax6 = iMax6;
            }
        }
        int iMax7 = iMax6;
        int paddingRight = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        this.mTotalLength = paddingRight;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i2, 0);
        int i27 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (z8 || (i27 != 0 && f2 > 0.0f)) {
            float f5 = this.mWeightSum;
            if (f5 > 0.0f) {
                f2 = f5;
            }
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            this.mTotalLength = 0;
            int i28 = i22;
            iCombineMeasuredStates = i24;
            int iMax8 = -1;
            int i29 = 0;
            while (i29 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i29);
                if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                    i5 = i27;
                    virtualChildCount = virtualChildCount;
                } else {
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt3.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    if (f6 > 0.0f) {
                        int measuredWidth2 = (int) ((i27 * f6) / f2);
                        float f7 = f2 - f6;
                        int i30 = i27 - measuredWidth2;
                        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin, ((LinearLayout.LayoutParams) layoutParams3).height);
                        if (((LinearLayout.LayoutParams) layoutParams3).width == 0) {
                            i8 = BasicMeasure.EXACTLY;
                            if (mode == 1073741824) {
                                if (measuredWidth2 <= 0) {
                                    measuredWidth2 = 0;
                                }
                            }
                            virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i8), childMeasureSpec);
                            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                            f2 = f7;
                            i5 = i30;
                        } else {
                            i8 = BasicMeasure.EXACTLY;
                        }
                        measuredWidth2 = virtualChildAt3.getMeasuredWidth() + measuredWidth2;
                        if (measuredWidth2 < 0) {
                            measuredWidth2 = 0;
                        }
                        virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i8), childMeasureSpec);
                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                        f2 = f7;
                        i5 = i30;
                    } else {
                        i5 = i27;
                    }
                    int i31 = this.mTotalLength;
                    if (z7) {
                        this.mTotalLength = getNextLocationOffset(virtualChildAt3) + virtualChildAt3.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin + i31;
                    } else {
                        this.mTotalLength = Math.max(i31, getNextLocationOffset(virtualChildAt3) + virtualChildAt3.getMeasuredWidth() + i31 + ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin);
                    }
                    boolean z13 = mode2 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams3).height == -1;
                    int i32 = ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin;
                    int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i32;
                    iMax8 = Math.max(iMax8, measuredHeight2);
                    if (!z13) {
                        i32 = measuredHeight2;
                    }
                    int iMax9 = Math.max(i28, i32);
                    if (z9) {
                        i6 = -1;
                        boolean z14 = ((LinearLayout.LayoutParams) layoutParams3).height == -1;
                        if (z12 && (baseline = virtualChildAt3.getBaseline()) != i6) {
                            i7 = ((LinearLayout.LayoutParams) layoutParams3).gravity;
                            if (i7 < 0) {
                                i7 = this.mGravity;
                            }
                            int i33 = (((i7 & 112) >> 4) & (-2)) >> 1;
                            iArr2[i33] = Math.max(iArr2[i33], baseline);
                            iArr[i33] = Math.max(iArr[i33], measuredHeight2 - baseline);
                        }
                        z9 = z14;
                        i28 = iMax9;
                        f2 = f2;
                    } else {
                        i6 = -1;
                    }
                    if (z12) {
                        i7 = ((LinearLayout.LayoutParams) layoutParams3).gravity;
                        if (i7 < 0) {
                            i7 = this.mGravity;
                        }
                        int i34 = (((i7 & 112) >> 4) & (-2)) >> 1;
                        iArr2[i34] = Math.max(iArr2[i34], baseline);
                        iArr[i34] = Math.max(iArr[i34], measuredHeight2 - baseline);
                    }
                    z9 = z14;
                    i28 = iMax9;
                    f2 = f2;
                }
                i29++;
                i27 = i5;
                virtualChildCount = virtualChildCount;
            }
            i4 = virtualChildCount;
            this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
            int i35 = iArr2[1];
            iMax7 = (i35 == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? iMax8 : Math.max(iMax8, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(i35, iArr2[2]))));
            iMax = i28;
        } else {
            iMax = Math.max(i22, i23);
            if (z11 && mode != 1073741824) {
                for (int i36 = 0; i36 < virtualChildCount; i36++) {
                    View virtualChildAt4 = getVirtualChildAt(i36);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt4.getLayoutParams())).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax2, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), BasicMeasure.EXACTLY));
                    }
                }
            }
            i4 = virtualChildCount;
            iCombineMeasuredStates = i24;
        }
        if (z9 || mode2 == 1073741824) {
            iMax = iMax7;
        }
        setMeasuredDimension(iResolveSizeAndState | ((-16777216) & iCombineMeasuredStates), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax, getSuggestedMinimumHeight()), i3, iCombineMeasuredStates << 16));
        if (z10) {
            forceUniformHeight(i4, i2);
        }
    }

    public int measureNullChild(int i2) {
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:131:0x02d6 A[PHI: r10
      0x02d6: PHI (r10v23 int) = (r10v21 int), (r10v24 int) binds: [B:135:0x02e7, B:129:0x02d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:151:0x0328  */
    public void measureVertical(int i2, int i3) {
        int i4;
        int iCombineMeasuredStates;
        int iMax;
        int i5;
        int i6;
        int i7;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int iMax2;
        int i15;
        View view;
        int iMax3;
        boolean z3;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int i16 = this.mBaselineAlignedChildIndex;
        boolean z4 = this.mUseLargestChild;
        float f2 = 0.0f;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int iMax4 = 0;
        int i20 = 0;
        int childrenSkipCount = 0;
        boolean z5 = false;
        boolean z6 = true;
        boolean z7 = false;
        while (true) {
            int i21 = 8;
            int i22 = iMax4;
            if (childrenSkipCount >= virtualChildCount) {
                int i23 = i17;
                int i24 = i19;
                int i25 = i20;
                int i26 = mode2;
                int iMax5 = i18;
                int i27 = virtualChildCount;
                if (this.mTotalLength > 0) {
                    i4 = i27;
                    if (hasDividerBeforeChildAt(i4)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    i4 = i27;
                }
                if (z4 && (i26 == Integer.MIN_VALUE || i26 == 0)) {
                    this.mTotalLength = 0;
                    int childrenSkipCount2 = 0;
                    while (childrenSkipCount2 < i4) {
                        View virtualChildAt = getVirtualChildAt(childrenSkipCount2);
                        if (virtualChildAt == null) {
                            this.mTotalLength = measureNullChild(childrenSkipCount2) + this.mTotalLength;
                        } else if (virtualChildAt.getVisibility() == i21) {
                            childrenSkipCount2 += getChildrenSkipCount(virtualChildAt, childrenSkipCount2);
                        } else {
                            LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                            int i28 = this.mTotalLength;
                            this.mTotalLength = Math.max(i28, getNextLocationOffset(virtualChildAt) + i28 + i24 + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin);
                        }
                        childrenSkipCount2++;
                        i21 = 8;
                    }
                }
                int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
                this.mTotalLength = paddingBottom;
                int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i3, 0);
                int i29 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
                if (z5 || (i29 != 0 && f2 > 0.0f)) {
                    float f3 = this.mWeightSum;
                    if (f3 > 0.0f) {
                        f2 = f3;
                    }
                    this.mTotalLength = 0;
                    int i30 = i29;
                    int i31 = i25;
                    iCombineMeasuredStates = i23;
                    int i32 = 0;
                    while (i32 < i4) {
                        View virtualChildAt2 = getVirtualChildAt(i32);
                        if (virtualChildAt2.getVisibility() == 8) {
                            i5 = i30;
                        } else {
                            LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                            float f4 = ((LinearLayout.LayoutParams) layoutParams2).weight;
                            if (f4 > 0.0f) {
                                int measuredHeight = (int) ((i30 * f4) / f2);
                                float f5 = f2 - f4;
                                i5 = i30 - measuredHeight;
                                int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin, ((LinearLayout.LayoutParams) layoutParams2).width);
                                if (((LinearLayout.LayoutParams) layoutParams2).height == 0) {
                                    i8 = BasicMeasure.EXACTLY;
                                    if (i26 == 1073741824) {
                                        if (measuredHeight <= 0) {
                                            measuredHeight = 0;
                                        }
                                    }
                                    virtualChildAt2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt2.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                                    f2 = f5;
                                } else {
                                    i8 = BasicMeasure.EXACTLY;
                                }
                                measuredHeight = virtualChildAt2.getMeasuredHeight() + measuredHeight;
                                if (measuredHeight < 0) {
                                    measuredHeight = 0;
                                }
                                virtualChildAt2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt2.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                                f2 = f5;
                            } else {
                                i5 = i30;
                            }
                            int i33 = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                            int measuredWidth = virtualChildAt2.getMeasuredWidth() + i33;
                            iMax5 = Math.max(iMax5, measuredWidth);
                            float f6 = f2;
                            if (mode != 1073741824) {
                                i6 = iCombineMeasuredStates;
                                i7 = -1;
                                if (((LinearLayout.LayoutParams) layoutParams2).width != -1) {
                                }
                                int iMax6 = Math.max(i31, i33);
                                if (z6 || ((LinearLayout.LayoutParams) layoutParams2).width != i7) {
                                    z2 = false;
                                } else {
                                    z2 = true;
                                }
                                int i34 = this.mTotalLength;
                                this.mTotalLength = Math.max(i34, getNextLocationOffset(virtualChildAt2) + virtualChildAt2.getMeasuredHeight() + i34 + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin);
                                z6 = z2;
                                iCombineMeasuredStates = i6;
                                i31 = iMax6;
                                f2 = f6;
                            } else {
                                i6 = iCombineMeasuredStates;
                                i7 = -1;
                            }
                            i33 = measuredWidth;
                            int iMax7 = Math.max(i31, i33);
                            if (z6) {
                                z2 = false;
                            } else {
                                z2 = false;
                            }
                            int i35 = this.mTotalLength;
                            this.mTotalLength = Math.max(i35, getNextLocationOffset(virtualChildAt2) + virtualChildAt2.getMeasuredHeight() + i35 + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin);
                            z6 = z2;
                            iCombineMeasuredStates = i6;
                            i31 = iMax7;
                            f2 = f6;
                        }
                        i32++;
                        i30 = i5;
                    }
                    this.mTotalLength = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
                    iMax = i31;
                } else {
                    iMax = Math.max(i25, i22);
                    if (z4 && i26 != 1073741824) {
                        for (int i36 = 0; i36 < i4; i36++) {
                            View virtualChildAt3 = getVirtualChildAt(i36);
                            if (virtualChildAt3 != null && virtualChildAt3.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) virtualChildAt3.getLayoutParams())).weight > 0.0f) {
                                virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt3.getMeasuredWidth(), BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i24, BasicMeasure.EXACTLY));
                            }
                        }
                    }
                    iCombineMeasuredStates = i23;
                }
                if (z6 || mode == 1073741824) {
                    iMax = iMax5;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax, getSuggestedMinimumWidth()), i2, iCombineMeasuredStates), iResolveSizeAndState);
                if (z7) {
                    forceUniformWidth(i4, i3);
                    return;
                }
                return;
            }
            View virtualChildAt4 = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt4 == null) {
                this.mTotalLength = measureNullChild(childrenSkipCount) + this.mTotalLength;
                i12 = mode2;
                iMax4 = i22;
                i14 = virtualChildCount;
            } else {
                int i37 = i17;
                if (virtualChildAt4.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt4, childrenSkipCount);
                    iMax4 = i22;
                    i17 = i37;
                    i14 = virtualChildCount;
                    i12 = mode2;
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt4.getLayoutParams();
                    float f7 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    float f8 = f2 + f7;
                    if (mode2 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams3).height == 0 && f7 > 0.0f) {
                        int i38 = this.mTotalLength;
                        this.mTotalLength = Math.max(i38, ((LinearLayout.LayoutParams) layoutParams3).topMargin + i38 + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                        iMax3 = i19;
                        view = virtualChildAt4;
                        iMax2 = i20;
                        i10 = i37;
                        i11 = i18;
                        z5 = true;
                        i12 = mode2;
                        i13 = i22;
                        i14 = virtualChildCount;
                        i15 = childrenSkipCount;
                    } else {
                        int i39 = i18;
                        if (((LinearLayout.LayoutParams) layoutParams3).height != 0 || f7 <= 0.0f) {
                            i9 = Integer.MIN_VALUE;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams3).height = -2;
                            i9 = 0;
                        }
                        i10 = i37;
                        int i40 = i9;
                        i11 = i39;
                        int i41 = i19;
                        i12 = mode2;
                        i13 = i22;
                        i14 = virtualChildCount;
                        iMax2 = i20;
                        i15 = childrenSkipCount;
                        measureChildBeforeLayout(virtualChildAt4, childrenSkipCount, i2, 0, i3, f8 == 0.0f ? this.mTotalLength : 0);
                        if (i40 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) layoutParams3).height = i40;
                        }
                        int measuredHeight2 = virtualChildAt4.getMeasuredHeight();
                        int i42 = this.mTotalLength;
                        view = virtualChildAt4;
                        this.mTotalLength = Math.max(i42, getNextLocationOffset(view) + i42 + measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                        iMax3 = z4 ? Math.max(measuredHeight2, i41) : i41;
                    }
                    if (i16 >= 0 && i16 == i15 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i15 < i16 && ((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode == 1073741824 || ((LinearLayout.LayoutParams) layoutParams3).width != -1) {
                        z3 = false;
                    } else {
                        z3 = true;
                        z7 = true;
                    }
                    int i43 = ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin;
                    int measuredWidth2 = view.getMeasuredWidth() + i43;
                    int iMax8 = Math.max(i11, measuredWidth2);
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i10, view.getMeasuredState());
                    z6 = z6 && ((LinearLayout.LayoutParams) layoutParams3).width == -1;
                    if (((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                        if (!z3) {
                            i43 = measuredWidth2;
                        }
                        iMax4 = Math.max(i13, i43);
                    } else {
                        if (!z3) {
                            i43 = measuredWidth2;
                        }
                        iMax2 = Math.max(iMax2, i43);
                        iMax4 = i13;
                    }
                    int childrenSkipCount3 = getChildrenSkipCount(view, i15) + i15;
                    i19 = iMax3;
                    i18 = iMax8;
                    f2 = f8;
                    i20 = iMax2;
                    childrenSkipCount = childrenSkipCount3;
                    i17 = iCombineMeasuredStates2;
                }
            }
            childrenSkipCount++;
            virtualChildCount = i14;
            mode2 = i12;
        }
    }

    @Override // android.view.View
    public void onDraw(@NonNull Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (this.mOrientation == 1) {
            layoutVertical(i2, i3, i4, i5);
        } else {
            layoutHorizontal(i2, i3, i4, i5);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        if (this.mOrientation == 1) {
            measureVertical(i2, i3);
        } else {
            measureHorizontal(i2, i3);
        }
    }

    public void setBaselineAligned(boolean z2) {
        this.mBaselineAligned = z2;
    }

    public void setBaselineAlignedChildIndex(int i2) {
        if (i2 >= 0 && i2 < getChildCount()) {
            this.mBaselineAlignedChildIndex = i2;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i2) {
        this.mDividerPadding = i2;
    }

    public void setGravity(@GravityInt int i2) {
        if (this.mGravity != i2) {
            if ((8388615 & i2) == 0) {
                i2 |= GravityCompat.START;
            }
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.mGravity = i2;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i2) {
        int i3 = i2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i4 = this.mGravity;
        if ((8388615 & i4) != i3) {
            this.mGravity = i3 | ((-8388616) & i4);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z2) {
        this.mUseLargestChild = z2;
    }

    public void setOrientation(int i2) {
        if (this.mOrientation != i2) {
            this.mOrientation = i2;
            requestLayout();
        }
    }

    public void setShowDividers(int i2) {
        if (i2 != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i2;
    }

    public void setVerticalGravity(int i2) {
        int i3 = i2 & 112;
        int i4 = this.mGravity;
        if ((i4 & 112) != i3) {
            this.mGravity = i3 | (i4 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f2) {
        this.mWeightSum = Math.max(0.0f, f2);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i2 = this.mOrientation;
        if (i2 == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i2 == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, androidx.appcompat.R.styleable.LinearLayoutCompat, i2, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, androidx.appcompat.R.styleable.LinearLayoutCompat, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i2, 0);
        int i3 = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z2 = tintTypedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z2) {
            setBaselineAligned(z2);
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(androidx.appcompat.R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }
}
