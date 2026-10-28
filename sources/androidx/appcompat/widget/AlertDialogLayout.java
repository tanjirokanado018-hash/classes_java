package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        super(context);
    }

    private void forceUniformWidth(int i2, int i3) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), BasicMeasure.EXACTLY);
        for (int i4 = 0; i4 < i2; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).width == -1) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).height;
                    ((LinearLayout.LayoutParams) layoutParams).height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i3, 0);
                    ((LinearLayout.LayoutParams) layoutParams).height = i5;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View view) {
        int minimumHeight = ViewCompat.getMinimumHeight(view);
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    private void setChildFrame(View view, int i2, int i3, int i4, int i5) {
        view.layout(i2, i3, i4 + i2, i5 + i3);
    }

    private boolean tryOnMeasure(int i2, int i3) {
        int iCombineMeasuredStates;
        int iResolveMinimumHeight;
        int measuredHeight;
        int measuredHeight2;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                int id = childAt.getId();
                if (id == R.id.topPanel) {
                    view = childAt;
                } else if (id == R.id.buttonPanel) {
                    view2 = childAt;
                } else {
                    if ((id != R.id.contentPanel && id != R.id.customPanel) || view3 != null) {
                        return false;
                    }
                    view3 = childAt;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        int mode2 = View.MeasureSpec.getMode(i2);
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (view != null) {
            view.measure(i2, 0);
            paddingBottom += view.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(0, view.getMeasuredState());
        } else {
            iCombineMeasuredStates = 0;
        }
        if (view2 != null) {
            view2.measure(i2, 0);
            iResolveMinimumHeight = resolveMinimumHeight(view2);
            measuredHeight = view2.getMeasuredHeight() - iResolveMinimumHeight;
            paddingBottom += iResolveMinimumHeight;
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        } else {
            iResolveMinimumHeight = 0;
            measuredHeight = 0;
        }
        if (view3 != null) {
            view3.measure(i2, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingBottom), mode));
            measuredHeight2 = view3.getMeasuredHeight();
            paddingBottom += measuredHeight2;
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        } else {
            measuredHeight2 = 0;
        }
        int i5 = size - paddingBottom;
        if (view2 != null) {
            int i6 = paddingBottom - iResolveMinimumHeight;
            int iMin = Math.min(i5, measuredHeight);
            if (iMin > 0) {
                i5 -= iMin;
                iResolveMinimumHeight += iMin;
            }
            view2.measure(i2, View.MeasureSpec.makeMeasureSpec(iResolveMinimumHeight, BasicMeasure.EXACTLY));
            paddingBottom = i6 + view2.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        }
        if (view3 != null && i5 > 0) {
            view3.measure(i2, View.MeasureSpec.makeMeasureSpec(measuredHeight2 + i5, mode));
            paddingBottom = (paddingBottom - measuredHeight2) + view3.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        }
        int iMax = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt2 = getChildAt(i7);
            if (childAt2.getVisibility() != 8) {
                iMax = Math.max(iMax, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + iMax, i2, iCombineMeasuredStates), View.resolveSizeAndState(paddingBottom, i3, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, i3);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00a7  */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i4 - i2;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i9 = gravity & 112;
        int i10 = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int paddingTop = i9 != 16 ? i9 != 80 ? getPaddingTop() : ((getPaddingTop() + i5) - i3) - measuredHeight : (((i5 - i3) - measuredHeight) / 2) + getPaddingTop();
        Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt(i11);
            if (childAt != null && childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                int i12 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                if (i12 < 0) {
                    i12 = i10;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i12, getLayoutDirection()) & 7;
                if (absoluteGravity != 1) {
                    if (absoluteGravity != 5) {
                        i7 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                    } else {
                        i6 = paddingRight - measuredWidth;
                    }
                    if (hasDividerBeforeChildAt(i11)) {
                        paddingTop += intrinsicHeight;
                    }
                    int i13 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    setChildFrame(childAt, i7, i13, measuredWidth, measuredHeight2);
                    paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i13;
                } else {
                    i6 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                }
                i7 = i6 - ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                if (hasDividerBeforeChildAt(i11)) {
                    paddingTop += intrinsicHeight;
                }
                int i14 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                setChildFrame(childAt, i7, i14, measuredWidth, measuredHeight2);
                paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i14;
            }
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public void onMeasure(int i2, int i3) {
        if (tryOnMeasure(i2, i3)) {
            return;
        }
        super.onMeasure(i2, i3);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
