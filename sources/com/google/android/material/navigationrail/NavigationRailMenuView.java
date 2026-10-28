package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationRailMenuView extends NavigationBarMenuView {

    @Px
    private int itemMinimumHeight;
    private final FrameLayout.LayoutParams layoutParams;

    public NavigationRailMenuView(@NonNull Context context) {
        super(context);
        this.itemMinimumHeight = -1;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        this.layoutParams = layoutParams;
        layoutParams.gravity = 49;
        setLayoutParams(layoutParams);
        setItemActiveIndicatorResizeable(true);
    }

    private int makeSharedHeightSpec(int i2, int i3, int i4) {
        int iMax = i3 / Math.max(1, i4);
        int size = this.itemMinimumHeight;
        if (size == -1) {
            size = View.MeasureSpec.getSize(i2);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(size, iMax), 0);
    }

    private int measureChildHeight(View view, int i2, int i3) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        view.measure(i2, i3);
        return view.getMeasuredHeight();
    }

    private int measureSharedChildHeights(int i2, int i3, int i4, View view) {
        int iMakeSharedHeightSpec = view == null ? makeSharedHeightSpec(i2, i3, i4) : View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 0);
        int childCount = getChildCount();
        int iMeasureChildHeight = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != view) {
                iMeasureChildHeight += measureChildHeight(childAt, i2, iMakeSharedHeightSpec);
            }
        }
        return iMeasureChildHeight;
    }

    private int measureShiftingChildHeights(int i2, int i3, int i4) {
        int iMeasureChildHeight;
        View childAt = getChildAt(getSelectedItemPosition());
        if (childAt != null) {
            iMeasureChildHeight = measureChildHeight(childAt, i2, makeSharedHeightSpec(i2, i3, i4));
            i3 -= iMeasureChildHeight;
            i4--;
        } else {
            iMeasureChildHeight = 0;
        }
        return iMeasureChildHeight + measureSharedChildHeights(i2, i3, i4, childAt);
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuView
    @NonNull
    public NavigationBarItemView createNavigationBarItemView(@NonNull Context context) {
        return new NavigationRailItemView(context);
    }

    @Px
    public int getItemMinimumHeight() {
        return this.itemMinimumHeight;
    }

    public int getMenuGravity() {
        return this.layoutParams.gravity;
    }

    public boolean isTopGravity() {
        return (this.layoutParams.gravity & 112) == 48;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight() + i7;
                childAt.layout(0, i7, i6, measuredHeight);
                i7 = measuredHeight;
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i3);
        int size2 = getMenu().getVisibleItems().size();
        setMeasuredDimension(View.MeasureSpec.getSize(i2), View.resolveSizeAndState((size2 <= 1 || !isShifting(getLabelVisibilityMode(), size2)) ? measureSharedChildHeights(i2, size, size2, null) : measureShiftingChildHeights(i2, size, size2), i3, 0));
    }

    public void setItemMinimumHeight(@Px int i2) {
        if (this.itemMinimumHeight != i2) {
            this.itemMinimumHeight = i2;
            requestLayout();
        }
    }

    public void setMenuGravity(int i2) {
        FrameLayout.LayoutParams layoutParams = this.layoutParams;
        if (layoutParams.gravity != i2) {
            layoutParams.gravity = i2;
            setLayoutParams(layoutParams);
        }
    }
}
