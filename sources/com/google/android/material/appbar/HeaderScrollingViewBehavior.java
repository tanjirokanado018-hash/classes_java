package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    private int overlayTop;
    final Rect tempRect1;
    final Rect tempRect2;
    private int verticalLayoutGap;

    public HeaderScrollingViewBehavior() {
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }

    private static int resolveGravity(int i2) {
        if (i2 == 0) {
            return 8388659;
        }
        return i2;
    }

    @Nullable
    public abstract View findFirstDependency(List<View> list);

    public final int getOverlapPixelsForOffset(View view) {
        if (this.overlayTop == 0) {
            return 0;
        }
        float overlapRatioForOffset = getOverlapRatioForOffset(view);
        int i2 = this.overlayTop;
        return MathUtils.clamp((int) (overlapRatioForOffset * i2), 0, i2);
    }

    public float getOverlapRatioForOffset(View view) {
        return 1.0f;
    }

    public final int getOverlayTop() {
        return this.overlayTop;
    }

    public int getScrollRange(@NonNull View view) {
        return view.getMeasuredHeight();
    }

    public final int getVerticalLayoutGap() {
        return this.verticalLayoutGap;
    }

    @Override // com.google.android.material.appbar.ViewOffsetBehavior
    public void layoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i2) {
        int bottom;
        View viewFindFirstDependency = findFirstDependency(coordinatorLayout.getDependencies(view));
        if (viewFindFirstDependency != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            Rect rect = this.tempRect1;
            rect.set(coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, viewFindFirstDependency.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((viewFindFirstDependency.getBottom() + coordinatorLayout.getHeight()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
            if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
                rect.left = lastWindowInsets.getSystemWindowInsetLeft() + rect.left;
                rect.right -= lastWindowInsets.getSystemWindowInsetRight();
            }
            Rect rect2 = this.tempRect2;
            GravityCompat.apply(resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i2);
            int overlapPixelsForOffset = getOverlapPixelsForOffset(viewFindFirstDependency);
            view.layout(rect2.left, rect2.top - overlapPixelsForOffset, rect2.right, rect2.bottom - overlapPixelsForOffset);
            bottom = rect2.top - viewFindFirstDependency.getBottom();
        } else {
            super.layoutChild(coordinatorLayout, view, i2);
            bottom = 0;
        }
        this.verticalLayoutGap = bottom;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i2, int i3, int i4, int i5) {
        View viewFindFirstDependency;
        WindowInsetsCompat lastWindowInsets;
        int i6 = view.getLayoutParams().height;
        if ((i6 != -1 && i6 != -2) || (viewFindFirstDependency = findFirstDependency(coordinatorLayout.getDependencies(view))) == null) {
            return false;
        }
        int size = View.MeasureSpec.getSize(i4);
        if (size <= 0) {
            size = coordinatorLayout.getHeight();
        } else if (ViewCompat.getFitsSystemWindows(viewFindFirstDependency) && (lastWindowInsets = coordinatorLayout.getLastWindowInsets()) != null) {
            size += lastWindowInsets.getSystemWindowInsetBottom() + lastWindowInsets.getSystemWindowInsetTop();
        }
        int scrollRange = size + getScrollRange(viewFindFirstDependency);
        int measuredHeight = viewFindFirstDependency.getMeasuredHeight();
        if (shouldHeaderOverlapScrollingChild()) {
            view.setTranslationY(-measuredHeight);
        } else {
            view.setTranslationY(0.0f);
            scrollRange -= measuredHeight;
        }
        coordinatorLayout.onMeasureChild(view, i2, i3, View.MeasureSpec.makeMeasureSpec(scrollRange, i6 == -1 ? BasicMeasure.EXACTLY : Integer.MIN_VALUE), i5);
        return true;
    }

    public final void setOverlayTop(int i2) {
        this.overlayTop = i2;
    }

    public boolean shouldHeaderOverlapScrollingChild() {
        return false;
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }
}
