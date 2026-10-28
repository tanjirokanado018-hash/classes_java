package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.TintTypedArray;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class BottomNavigationView extends NavigationBarView {
    private static final int MAX_ITEM_COUNT = 5;

    @Deprecated
    public interface OnNavigationItemReselectedListener extends NavigationBarView.OnItemReselectedListener {
    }

    @Deprecated
    public interface OnNavigationItemSelectedListener extends NavigationBarView.OnItemSelectedListener {
    }

    public BottomNavigationView(@NonNull Context context) {
        this(context, null);
    }

    private void addCompatibilityTopDivider(@NonNull Context context) {
        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.design_bottom_navigation_shadow_color));
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
        addView(view);
    }

    private void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomnavigation.BottomNavigationView.1
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            @NonNull
            public WindowInsetsCompat onApplyWindowInsets(View view, @NonNull WindowInsetsCompat windowInsetsCompat, @NonNull ViewUtils.RelativePadding relativePadding) {
                relativePadding.bottom = windowInsetsCompat.getSystemWindowInsetBottom() + relativePadding.bottom;
                boolean z2 = ViewCompat.getLayoutDirection(view) == 1;
                int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                relativePadding.start += z2 ? systemWindowInsetRight : systemWindowInsetLeft;
                int i2 = relativePadding.end;
                if (!z2) {
                    systemWindowInsetLeft = systemWindowInsetRight;
                }
                relativePadding.end = i2 + systemWindowInsetLeft;
                relativePadding.applyToView(view);
                return windowInsetsCompat;
            }
        });
    }

    private int makeMinHeightSpec(int i2) {
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        if (View.MeasureSpec.getMode(i2) == 1073741824 || suggestedMinimumHeight <= 0) {
            return i2;
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i2), getPaddingBottom() + getPaddingTop() + suggestedMinimumHeight), BasicMeasure.EXACTLY);
    }

    private boolean shouldDrawCompatibilityTopDivider() {
        return false;
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public NavigationBarMenuView createNavigationBarMenuView(@NonNull Context context) {
        return new BottomNavigationMenuView(context);
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public int getMaxItemCount() {
        return 5;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return ((BottomNavigationMenuView) getMenuView()).isItemHorizontalTranslationEnabled();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, makeMinHeightSpec(i3));
    }

    public void setItemHorizontalTranslationEnabled(boolean z2) {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) getMenuView();
        if (bottomNavigationMenuView.isItemHorizontalTranslationEnabled() != z2) {
            bottomNavigationMenuView.setItemHorizontalTranslationEnabled(z2);
            getPresenter().updateMenuView(false);
        }
    }

    @Deprecated
    public void setOnNavigationItemReselectedListener(@Nullable OnNavigationItemReselectedListener onNavigationItemReselectedListener) {
        setOnItemReselectedListener(onNavigationItemReselectedListener);
    }

    @Deprecated
    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        setOnItemSelectedListener(onNavigationItemSelectedListener);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomNavigationStyle);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, R.style.Widget_Design_BottomNavigationView);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        Context context2 = getContext();
        TintTypedArray tintTypedArrayObtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.BottomNavigationView, i2, i3, new int[0]);
        setItemHorizontalTranslationEnabled(tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
        if (tintTypedArrayObtainTintedStyledAttributes.hasValue(R.styleable.BottomNavigationView_android_minHeight)) {
            setMinimumHeight(tintTypedArrayObtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.BottomNavigationView_android_minHeight, 0));
        }
        if (tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.BottomNavigationView_compatShadowEnabled, true) && shouldDrawCompatibilityTopDivider()) {
            addCompatibilityTopDivider(context2);
        }
        tintTypedArrayObtainTintedStyledAttributes.recycle();
        applyWindowInsets();
    }
}
