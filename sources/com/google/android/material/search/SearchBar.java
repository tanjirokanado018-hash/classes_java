package com.google.android.material.search;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class SearchBar extends Toolbar {
    private static final int DEFAULT_SCROLL_FLAGS = 53;
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_SearchBar;
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";

    @Nullable
    private final AccessibilityManager accessibilityManager;
    private MaterialShapeDrawable backgroundShape;

    @Nullable
    private View centerView;
    private final boolean defaultMarginsEnabled;
    private final Drawable defaultNavigationIcon;
    private boolean defaultScrollFlagsEnabled;
    private final boolean forceDefaultNavigationOnClickListener;
    private final boolean layoutInflated;
    private int menuResId;

    @Nullable
    private Integer navigationIconTint;

    @Nullable
    private Drawable originalNavigationIconBackground;
    private final SearchBarAnimationHelper searchBarAnimationHelper;
    private final TextView textView;
    private final boolean tintNavigationIcon;
    private final AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    public static abstract class OnLoadAnimationCallback {
        public void onAnimationEnd() {
        }

        public void onAnimationStart() {
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.search.SearchBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        String text;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeString(this.text);
        }

        public SavedState(Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.text = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public static class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
        private boolean initialized;

        public ScrollingViewBehavior() {
            this.initialized = false;
        }

        private void setAppBarLayoutTransparent(AppBarLayout appBarLayout) {
            appBarLayout.setBackgroundColor(0);
            if (Build.VERSION.SDK_INT == 21) {
                appBarLayout.setOutlineProvider(null);
            } else {
                appBarLayout.setTargetElevation(0.0f);
            }
        }

        @Override // com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
            boolean zOnDependentViewChanged = super.onDependentViewChanged(coordinatorLayout, view, view2);
            if (!this.initialized && (view2 instanceof AppBarLayout)) {
                this.initialized = true;
                setAppBarLayoutTransparent((AppBarLayout) view2);
            }
            return zOnDependentViewChanged;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public boolean shouldHeaderOverlapScrollingChild() {
            return true;
        }

        public ScrollingViewBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.initialized = false;
        }
    }

    public SearchBar(@NonNull Context context) {
        this(context, null);
    }

    private int defaultIfZero(int i2, int i3) {
        return i2 == 0 ? i3 : i2;
    }

    private ColorStateList getCompatBackgroundColorStateList(@ColorInt int i2, @ColorInt int i3) {
        int[][] iArr = {new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused}, new int[0]};
        int iLayer = MaterialColors.layer(i2, i3);
        return new ColorStateList(iArr, new int[]{iLayer, iLayer, i2});
    }

    private void initBackground(ShapeAppearanceModel shapeAppearanceModel, @ColorInt int i2, float f2, float f3, @ColorInt int i3) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        this.backgroundShape = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(getContext());
        this.backgroundShape.setElevation(f2);
        if (f3 >= 0.0f) {
            this.backgroundShape.setStroke(f3, i3);
        }
        int color = MaterialColors.getColor(this, R.attr.colorControlHighlight);
        this.backgroundShape.setFillColor(ColorStateList.valueOf(i2));
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(color);
        MaterialShapeDrawable materialShapeDrawable2 = this.backgroundShape;
        ViewCompat.setBackground(this, new RippleDrawable(colorStateListValueOf, materialShapeDrawable2, materialShapeDrawable2));
    }

    private void initNavigationIcon() {
        setNavigationIcon(getNavigationIcon() == null ? this.defaultNavigationIcon : getNavigationIcon());
        setNavigationIconDecorative(true);
    }

    private void initTextView(@StyleRes int i2, String str, String str2) {
        if (i2 != -1) {
            TextViewCompat.setTextAppearance(this.textView, i2);
        }
        setText(str);
        setHint(str2);
        if (getNavigationIcon() == null) {
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.textView.getLayoutParams(), getResources().getDimensionPixelSize(R.dimen.m3_searchbar_text_margin_start_no_navigation_icon));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z2) {
        setFocusableInTouchMode(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOnLoadAnimation$1() {
        this.searchBarAnimationHelper.startOnLoadAnimation(this);
    }

    private void layoutCenterView() {
        View view = this.centerView;
        if (view == null) {
            return;
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredWidth2 = (getMeasuredWidth() / 2) - (measuredWidth / 2);
        int i2 = measuredWidth2 + measuredWidth;
        int measuredHeight = this.centerView.getMeasuredHeight();
        int measuredHeight2 = (getMeasuredHeight() / 2) - (measuredHeight / 2);
        layoutChild(this.centerView, measuredWidth2, measuredHeight2, i2, measuredHeight2 + measuredHeight);
    }

    private void layoutChild(View view, int i2, int i3, int i4, int i5) {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            view.layout(getMeasuredWidth() - i4, i3, getMeasuredWidth() - i2, i5);
        } else {
            view.layout(i2, i3, i4, i5);
        }
    }

    @Nullable
    private Drawable maybeTintNavigationIcon(@Nullable Drawable drawable) {
        int color;
        if (!this.tintNavigationIcon || drawable == null) {
            return drawable;
        }
        Integer num = this.navigationIconTint;
        if (num != null) {
            color = num.intValue();
        } else {
            color = MaterialColors.getColor(this, drawable == this.defaultNavigationIcon ? R.attr.colorOnSurfaceVariant : R.attr.colorOnSurface);
        }
        Drawable drawableWrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(drawableWrap, color);
        return drawableWrap;
    }

    private void measureCenterView(int i2, int i3) {
        View view = this.centerView;
        if (view != null) {
            view.measure(i2, i3);
        }
    }

    private void setDefaultMargins() {
        if (this.defaultMarginsEnabled && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.m3_searchbar_margin_horizontal);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(getDefaultMarginVerticalResource());
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.leftMargin = defaultIfZero(marginLayoutParams.leftMargin, dimensionPixelSize);
            marginLayoutParams.topMargin = defaultIfZero(marginLayoutParams.topMargin, dimensionPixelSize2);
            marginLayoutParams.rightMargin = defaultIfZero(marginLayoutParams.rightMargin, dimensionPixelSize);
            marginLayoutParams.bottomMargin = defaultIfZero(marginLayoutParams.bottomMargin, dimensionPixelSize2);
        }
    }

    private void setHandwritingBoundsInsets() {
        int width;
        if (Build.VERSION.SDK_INT < 34) {
            return;
        }
        int right = 0;
        boolean z2 = getLayoutDirection() == 1;
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        if (navigationIconButton == null || !navigationIconButton.isClickable()) {
            width = 0;
        } else {
            width = z2 ? getWidth() - navigationIconButton.getLeft() : navigationIconButton.getRight();
        }
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(this);
        if (actionMenuView != null) {
            right = z2 ? actionMenuView.getRight() : getWidth() - actionMenuView.getLeft();
        }
        float f2 = -(z2 ? right : width);
        if (!z2) {
            width = right;
        }
        setHandwritingBoundsOffsets(f2, 0.0f, -width, 0.0f);
    }

    private void setNavigationIconDecorative(boolean z2) {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        if (navigationIconButton == null) {
            return;
        }
        navigationIconButton.setClickable(!z2);
        navigationIconButton.setFocusable(!z2);
        Drawable background = navigationIconButton.getBackground();
        if (background != null) {
            this.originalNavigationIconBackground = background;
        }
        navigationIconButton.setBackgroundDrawable(z2 ? null : this.originalNavigationIconBackground);
        setHandwritingBoundsInsets();
    }

    private void setOrClearDefaultScrollFlags() {
        if (getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) getLayoutParams();
            if (this.defaultScrollFlagsEnabled) {
                if (layoutParams.getScrollFlags() == 0) {
                    layoutParams.setScrollFlags(DEFAULT_SCROLL_FLAGS);
                }
            } else if (layoutParams.getScrollFlags() == DEFAULT_SCROLL_FLAGS) {
                layoutParams.setScrollFlags(0);
            }
        }
    }

    private void setupTouchExplorationStateChangeListener() {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            if (accessibilityManager.isEnabled() && this.accessibilityManager.isTouchExplorationEnabled()) {
                setFocusableInTouchMode(true);
            }
            addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.search.SearchBar.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    AccessibilityManagerCompat.addTouchExplorationStateChangeListener(SearchBar.this.accessibilityManager, SearchBar.this.touchExplorationStateChangeListener);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(SearchBar.this.accessibilityManager, SearchBar.this.touchExplorationStateChangeListener);
                }
            });
        }
    }

    private void validateAttributes(@Nullable AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        if (attributeSet.getAttributeValue(NAMESPACE_APP, "title") != null) {
            throw new UnsupportedOperationException("SearchBar does not support title. Use hint or text instead.");
        }
        if (attributeSet.getAttributeValue(NAMESPACE_APP, "subtitle") != null) {
            throw new UnsupportedOperationException("SearchBar does not support subtitle. Use hint or text instead.");
        }
    }

    public void addCollapseAnimationListener(@NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        this.searchBarAnimationHelper.addCollapseAnimationListener(animatorListenerAdapter);
    }

    public void addExpandAnimationListener(@NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        this.searchBarAnimationHelper.addExpandAnimationListener(animatorListenerAdapter);
    }

    public void addOnLoadAnimationCallback(@NonNull OnLoadAnimationCallback onLoadAnimationCallback) {
        this.searchBarAnimationHelper.addOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (this.layoutInflated && this.centerView == null && !(view instanceof ActionMenuView)) {
            this.centerView = view;
            view.setAlpha(0.0f);
        }
        super.addView(view, i2, layoutParams);
    }

    public void clearText() {
        this.textView.setText("");
    }

    public boolean collapse(@NonNull View view) {
        return collapse(view, null);
    }

    public boolean expand(@NonNull View view) {
        return expand(view, null);
    }

    @Nullable
    public View getCenterView() {
        return this.centerView;
    }

    public float getCompatElevation() {
        MaterialShapeDrawable materialShapeDrawable = this.backgroundShape;
        return materialShapeDrawable != null ? materialShapeDrawable.getElevation() : ViewCompat.getElevation(this);
    }

    public float getCornerSize() {
        return this.backgroundShape.getTopLeftCornerResolvedSize();
    }

    @DimenRes
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getDefaultMarginVerticalResource() {
        return R.dimen.m3_searchbar_margin_vertical;
    }

    @DrawableRes
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getDefaultNavigationIconResource() {
        return R.drawable.ic_search_black_24;
    }

    @Nullable
    public CharSequence getHint() {
        return this.textView.getHint();
    }

    public int getMenuResId() {
        return this.menuResId;
    }

    @ColorInt
    public int getStrokeColor() {
        return this.backgroundShape.getStrokeColor().getDefaultColor();
    }

    @Dimension
    public float getStrokeWidth() {
        return this.backgroundShape.getStrokeWidth();
    }

    @NonNull
    public CharSequence getText() {
        return this.textView.getText();
    }

    @NonNull
    public TextView getTextView() {
        return this.textView;
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void inflateMenu(@MenuRes int i2) {
        Menu menu = getMenu();
        boolean z2 = menu instanceof MenuBuilder;
        if (z2) {
            ((MenuBuilder) menu).stopDispatchingItemsChanged();
        }
        super.inflateMenu(i2);
        this.menuResId = i2;
        if (z2) {
            ((MenuBuilder) menu).startDispatchingItemsChanged();
        }
    }

    public boolean isCollapsing() {
        return this.searchBarAnimationHelper.isCollapsing();
    }

    public boolean isDefaultScrollFlagsEnabled() {
        return this.defaultScrollFlagsEnabled;
    }

    public boolean isExpanding() {
        return this.searchBarAnimationHelper.isExpanding();
    }

    public boolean isOnLoadAnimationFadeInEnabled() {
        return this.searchBarAnimationHelper.isOnLoadAnimationFadeInEnabled();
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.backgroundShape);
        setDefaultMargins();
        setOrClearDefaultScrollFlags();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getCanonicalName());
        int i2 = Build.VERSION.SDK_INT;
        accessibilityNodeInfo.setEditable(isEnabled());
        CharSequence text = getText();
        boolean zIsEmpty = TextUtils.isEmpty(text);
        if (i2 >= 26) {
            accessibilityNodeInfo.setHintText(getHint());
            accessibilityNodeInfo.setShowingHintText(zIsEmpty);
        }
        if (zIsEmpty) {
            text = getHint();
        }
        accessibilityNodeInfo.setText(text);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        layoutCenterView();
        setHandwritingBoundsInsets();
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        measureCenterView(i2, i3);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setText(savedState.text);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    @NonNull
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = getText();
        savedState.text = text == null ? null : text.toString();
        return savedState;
    }

    public boolean removeCollapseAnimationListener(@NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        return this.searchBarAnimationHelper.removeCollapseAnimationListener(animatorListenerAdapter);
    }

    public boolean removeExpandAnimationListener(@NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        return this.searchBarAnimationHelper.removeExpandAnimationListener(animatorListenerAdapter);
    }

    public boolean removeOnLoadAnimationCallback(@NonNull OnLoadAnimationCallback onLoadAnimationCallback) {
        return this.searchBarAnimationHelper.removeOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    public void setCenterView(@Nullable View view) {
        View view2 = this.centerView;
        if (view2 != null) {
            removeView(view2);
            this.centerView = null;
        }
        if (view != null) {
            addView(view);
        }
    }

    public void setDefaultScrollFlagsEnabled(boolean z2) {
        this.defaultScrollFlagsEnabled = z2;
        setOrClearDefaultScrollFlags();
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f2) {
        super.setElevation(f2);
        MaterialShapeDrawable materialShapeDrawable = this.backgroundShape;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(f2);
        }
    }

    public void setHint(@StringRes int i2) {
        this.textView.setHint(i2);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setNavigationIcon(@Nullable Drawable drawable) {
        super.setNavigationIcon(maybeTintNavigationIcon(drawable));
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        if (this.forceDefaultNavigationOnClickListener) {
            return;
        }
        super.setNavigationOnClickListener(onClickListener);
        setNavigationIconDecorative(onClickListener == null);
    }

    public void setOnLoadAnimationFadeInEnabled(boolean z2) {
        this.searchBarAnimationHelper.setOnLoadAnimationFadeInEnabled(z2);
    }

    public void setStrokeColor(@ColorInt int i2) {
        if (getStrokeColor() != i2) {
            this.backgroundShape.setStrokeColor(ColorStateList.valueOf(i2));
        }
    }

    public void setStrokeWidth(@Dimension float f2) {
        if (getStrokeWidth() != f2) {
            this.backgroundShape.setStrokeWidth(f2);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setSubtitle(CharSequence charSequence) {
    }

    public void setText(@StringRes int i2) {
        this.textView.setText(i2);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
    }

    public void startOnLoadAnimation() {
        post(new m(2, this));
    }

    public void stopOnLoadAnimation() {
        this.searchBarAnimationHelper.stopOnLoadAnimation(this);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialSearchBarStyle);
    }

    public boolean collapse(@NonNull View view, @Nullable AppBarLayout appBarLayout) {
        return collapse(view, appBarLayout, false);
    }

    public boolean expand(@NonNull View view, @Nullable AppBarLayout appBarLayout) {
        return expand(view, appBarLayout, false);
    }

    public void setHint(@Nullable CharSequence charSequence) {
        this.textView.setHint(charSequence);
    }

    public void setText(@Nullable CharSequence charSequence) {
        this.textView.setText(charSequence);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SearchBar(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        int i3 = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, i3), attributeSet, i2);
        this.menuResId = -1;
        this.touchExplorationStateChangeListener = new a(this);
        Context context2 = getContext();
        validateAttributes(attributeSet);
        this.defaultNavigationIcon = AppCompatResources.getDrawable(context2, getDefaultNavigationIconResource());
        this.searchBarAnimationHelper = new SearchBarAnimationHelper();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.SearchBar, i2, i3, new int[0]);
        ShapeAppearanceModel shapeAppearanceModelBuild = ShapeAppearanceModel.builder(context2, attributeSet, i2, i3).build();
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SearchBar_backgroundTint, 0);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.SearchBar_elevation, 0.0f);
        this.defaultMarginsEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchBar_defaultMarginsEnabled, true);
        this.defaultScrollFlagsEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchBar_defaultScrollFlagsEnabled, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchBar_hideNavigationIcon, false);
        this.forceDefaultNavigationOnClickListener = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchBar_forceDefaultNavigationOnClickListener, false);
        this.tintNavigationIcon = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchBar_tintNavigationIcon, true);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SearchBar_navigationIconTint)) {
            this.navigationIconTint = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(R.styleable.SearchBar_navigationIconTint, -1));
        }
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SearchBar_android_textAppearance, -1);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.SearchBar_android_text);
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.SearchBar_android_hint);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.SearchBar_strokeWidth, -1.0f);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.SearchBar_strokeColor, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (!z2) {
            initNavigationIcon();
        }
        setClickable(true);
        setFocusable(true);
        LayoutInflater.from(context2).inflate(R.layout.mtrl_search_bar, this);
        this.layoutInflated = true;
        this.textView = (TextView) findViewById(R.id.open_search_bar_text_view);
        ViewCompat.setElevation(this, dimension);
        initTextView(resourceId, string, string2);
        initBackground(shapeAppearanceModelBuild, color, dimension, dimension2, color2);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setupTouchExplorationStateChangeListener();
    }

    public boolean collapse(@NonNull View view, @Nullable AppBarLayout appBarLayout, boolean z2) {
        if ((view.getVisibility() != 0 || isCollapsing()) && !isExpanding()) {
            return false;
        }
        this.searchBarAnimationHelper.startCollapseAnimation(this, view, appBarLayout, z2);
        return true;
    }

    public boolean expand(@NonNull View view, @Nullable AppBarLayout appBarLayout, boolean z2) {
        if ((view.getVisibility() == 0 || isExpanding()) && !isCollapsing()) {
            return false;
        }
        this.searchBarAnimationHelper.startExpandAnimation(this, view, appBarLayout, z2);
        return true;
    }
}
