package com.google.android.material.navigation;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM;
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int INVALID_ITEM_POSITION = -1;
    private ValueAnimator activeIndicatorAnimator;
    private int activeIndicatorDesiredHeight;
    private int activeIndicatorDesiredWidth;
    private boolean activeIndicatorEnabled;
    private int activeIndicatorLabelPadding;
    private int activeIndicatorMarginHorizontal;
    private float activeIndicatorProgress;
    private boolean activeIndicatorResizeable;
    private ActiveIndicatorTransform activeIndicatorTransform;

    @Nullable
    private final View activeIndicatorView;

    @StyleRes
    private int activeTextAppearance;

    @Nullable
    private BadgeDrawable badgeDrawable;
    private final ImageView icon;

    @Nullable
    private final FrameLayout iconContainer;

    @Nullable
    private ColorStateList iconTint;
    private boolean initialized;
    private boolean isShifting;

    @Nullable
    Drawable itemBackground;

    @Nullable
    private MenuItemImpl itemData;
    private int itemPaddingBottom;
    private int itemPaddingTop;
    private int itemPosition;
    private ColorStateList itemRippleColor;
    private final ViewGroup labelGroup;
    private int labelVisibilityMode;
    private final TextView largeLabel;

    @Nullable
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private float scaleUpFactor;
    private float shiftAmount;
    private final TextView smallLabel;

    @Nullable
    private Drawable wrappedIconDrawable;

    public static class ActiveIndicatorTransform {
        private static final float ALPHA_FRACTION = 0.2f;
        private static final float SCALE_X_HIDDEN = 0.4f;
        private static final float SCALE_X_SHOWN = 1.0f;

        private ActiveIndicatorTransform() {
        }

        public float calculateAlpha(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f3) {
            return AnimationUtils.lerp(0.0f, 1.0f, f3 == 0.0f ? 0.8f : 0.0f, f3 == 0.0f ? 1.0f : 0.2f, f2);
        }

        public float calculateScaleX(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f3) {
            return AnimationUtils.lerp(SCALE_X_HIDDEN, 1.0f, f2);
        }

        public float calculateScaleY(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f3) {
            return 1.0f;
        }

        public void updateForProgress(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f3, @NonNull View view) {
            view.setScaleX(calculateScaleX(f2, f3));
            view.setScaleY(calculateScaleY(f2, f3));
            view.setAlpha(calculateAlpha(f2, f3));
        }
    }

    public static class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {
        private ActiveIndicatorUnlabeledTransform() {
            super();
        }

        @Override // com.google.android.material.navigation.NavigationBarItemView.ActiveIndicatorTransform
        public float calculateScaleY(float f2, float f3) {
            return calculateScaleX(f2, f3);
        }
    }

    static {
        ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform();
        ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform();
    }

    public NavigationBarItemView(@NonNull Context context) {
        super(context);
        this.initialized = false;
        this.itemPosition = -1;
        this.activeTextAppearance = 0;
        this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        this.activeIndicatorProgress = 0.0f;
        this.activeIndicatorEnabled = false;
        this.activeIndicatorDesiredWidth = 0;
        this.activeIndicatorDesiredHeight = 0;
        this.activeIndicatorResizeable = false;
        this.activeIndicatorMarginHorizontal = 0;
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        this.iconContainer = (FrameLayout) findViewById(com.google.android.material.R.id.navigation_bar_item_icon_container);
        this.activeIndicatorView = findViewById(com.google.android.material.R.id.navigation_bar_item_active_indicator_view);
        ImageView imageView = (ImageView) findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view);
        this.icon = imageView;
        ViewGroup viewGroup = (ViewGroup) findViewById(com.google.android.material.R.id.navigation_bar_item_labels_group);
        this.labelGroup = viewGroup;
        TextView textView = (TextView) findViewById(com.google.android.material.R.id.navigation_bar_item_small_label_view);
        this.smallLabel = textView;
        TextView textView2 = (TextView) findViewById(com.google.android.material.R.id.navigation_bar_item_large_label_view);
        this.largeLabel = textView2;
        setBackgroundResource(getItemBackgroundResId());
        this.itemPaddingTop = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.itemPaddingBottom = viewGroup.getPaddingBottom();
        this.activeIndicatorLabelPadding = getResources().getDimensionPixelSize(com.google.android.material.R.dimen.m3_navigation_item_active_indicator_label_padding);
        ViewCompat.setImportantForAccessibility(textView, 2);
        ViewCompat.setImportantForAccessibility(textView2, 2);
        setFocusable(true);
        calculateTextScaleFactors(textView.getTextSize(), textView2.getTextSize());
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    if (NavigationBarItemView.this.icon.getVisibility() == 0) {
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        navigationBarItemView.tryUpdateBadgeBounds(navigationBarItemView.icon);
                    }
                }
            });
        }
    }

    private void calculateTextScaleFactors(float f2, float f3) {
        this.shiftAmount = f2 - f3;
        this.scaleUpFactor = (f3 * 1.0f) / f2;
        this.scaleDownFactor = (f2 * 1.0f) / f3;
    }

    private static Drawable createItemBackgroundCompat(@NonNull ColorStateList colorStateList) {
        return new RippleDrawable(RippleUtils.convertToRippleDrawableColor(colorStateList), null, null);
    }

    @Nullable
    private FrameLayout getCustomParentForBadge(View view) {
        ImageView imageView = this.icon;
        if (view == imageView && BadgeUtils.USE_COMPAT_PARENT) {
            return (FrameLayout) imageView.getParent();
        }
        return null;
    }

    private View getIconOrContainer() {
        FrameLayout frameLayout = this.iconContainer;
        return frameLayout != null ? frameLayout : this.icon;
    }

    private int getItemVisiblePosition() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        int iIndexOfChild = viewGroup.indexOfChild(this);
        int i2 = 0;
        for (int i3 = 0; i3 < iIndexOfChild; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if ((childAt instanceof NavigationBarItemView) && childAt.getVisibility() == 0) {
                i2++;
            }
        }
        return i2;
    }

    private int getSuggestedIconHeight() {
        return getIconOrContainer().getMeasuredHeight() + ((FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams()).topMargin;
    }

    private int getSuggestedIconWidth() {
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        int minimumWidth = badgeDrawable == null ? 0 : badgeDrawable.getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams();
        return Math.max(minimumWidth, layoutParams.rightMargin) + this.icon.getMeasuredWidth() + Math.max(minimumWidth, layoutParams.leftMargin);
    }

    private boolean hasBadge() {
        return this.badgeDrawable != null;
    }

    private boolean isActiveIndicatorResizeableAndUnlabeled() {
        return this.activeIndicatorResizeable && this.labelVisibilityMode == 2;
    }

    private void maybeAnimateActiveIndicatorToProgress(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f2) {
        if (!this.activeIndicatorEnabled || !this.initialized || !ViewCompat.isAttachedToWindow(this)) {
            setActiveIndicatorProgress(f2, f2);
            return;
        }
        ValueAnimator valueAnimator = this.activeIndicatorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.activeIndicatorAnimator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.activeIndicatorProgress, f2);
        this.activeIndicatorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NavigationBarItemView.this.setActiveIndicatorProgress(((Float) valueAnimator2.getAnimatedValue()).floatValue(), f2);
            }
        });
        this.activeIndicatorAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.google.android.material.R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        this.activeIndicatorAnimator.setDuration(MotionUtils.resolveThemeDuration(getContext(), com.google.android.material.R.attr.motionDurationLong2, getResources().getInteger(com.google.android.material.R.integer.material_motion_duration_long_1)));
        this.activeIndicatorAnimator.start();
    }

    private void refreshChecked() {
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null) {
            setChecked(menuItemImpl.isChecked());
        }
    }

    private void refreshItemBackground() {
        Drawable drawableCreateItemBackgroundCompat = this.itemBackground;
        RippleDrawable rippleDrawable = null;
        boolean z2 = true;
        if (this.itemRippleColor != null) {
            Drawable activeIndicatorDrawable = getActiveIndicatorDrawable();
            if (this.activeIndicatorEnabled && getActiveIndicatorDrawable() != null && this.iconContainer != null && activeIndicatorDrawable != null) {
                rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.itemRippleColor), null, activeIndicatorDrawable);
                z2 = false;
            } else if (drawableCreateItemBackgroundCompat == null) {
                drawableCreateItemBackgroundCompat = createItemBackgroundCompat(this.itemRippleColor);
            }
        }
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null) {
            frameLayout.setPadding(0, 0, 0, 0);
            this.iconContainer.setForeground(rippleDrawable);
        }
        ViewCompat.setBackground(this, drawableCreateItemBackgroundCompat);
        if (Build.VERSION.SDK_INT >= 26) {
            setDefaultFocusHighlightEnabled(z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActiveIndicatorProgress(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, float f3) {
        View view = this.activeIndicatorView;
        if (view != null) {
            this.activeIndicatorTransform.updateForProgress(f2, f3, view);
        }
        this.activeIndicatorProgress = f2;
    }

    private static void setTextAppearanceWithoutFontScaling(TextView textView, @StyleRes int i2) {
        TextViewCompat.setTextAppearance(textView, i2);
        int unscaledTextSize = MaterialResources.getUnscaledTextSize(textView.getContext(), i2, 0);
        if (unscaledTextSize != 0) {
            textView.setTextSize(0, unscaledTextSize);
        }
    }

    private static void setViewScaleValues(@NonNull View view, float f2, float f3, int i2) {
        view.setScaleX(f2);
        view.setScaleY(f3);
        view.setVisibility(i2);
    }

    private static void setViewTopMarginAndGravity(@NonNull View view, int i2, int i3) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i2;
        layoutParams.bottomMargin = i2;
        layoutParams.gravity = i3;
        view.setLayoutParams(layoutParams);
    }

    private void tryAttachBadgeToAnchor(@Nullable View view) {
        if (hasBadge() && view != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, view, getCustomParentForBadge(view));
        }
    }

    private void tryRemoveBadgeFromAnchor(@Nullable View view) {
        if (hasBadge()) {
            if (view != null) {
                setClipChildren(true);
                setClipToPadding(true);
                BadgeUtils.detachBadgeDrawable(this.badgeDrawable, view);
            }
            this.badgeDrawable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryUpdateBadgeBounds(View view) {
        if (hasBadge()) {
            BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, view, getCustomParentForBadge(view));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveIndicatorLayoutParams(int i2) {
        if (this.activeIndicatorView == null || i2 <= 0) {
            return;
        }
        int iMin = Math.min(this.activeIndicatorDesiredWidth, i2 - (this.activeIndicatorMarginHorizontal * 2));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
        layoutParams.height = isActiveIndicatorResizeableAndUnlabeled() ? iMin : this.activeIndicatorDesiredHeight;
        layoutParams.width = iMin;
        this.activeIndicatorView.setLayoutParams(layoutParams);
    }

    private void updateActiveIndicatorTransform() {
        this.activeIndicatorTransform = isActiveIndicatorResizeableAndUnlabeled() ? ACTIVE_INDICATOR_UNLABELED_TRANSFORM : ACTIVE_INDICATOR_LABELED_TRANSFORM;
    }

    private static void updateViewPaddingBottom(@NonNull View view, int i2) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i2);
    }

    public void clear() {
        removeBadge();
        this.itemData = null;
        this.activeIndicatorProgress = 0.0f;
        this.initialized = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null && this.activeIndicatorEnabled) {
            frameLayout.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Nullable
    public Drawable getActiveIndicatorDrawable() {
        View view = this.activeIndicatorView;
        if (view == null) {
            return null;
        }
        return view.getBackground();
    }

    @Nullable
    public BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    @DrawableRes
    public int getItemBackgroundResId() {
        return com.google.android.material.R.drawable.mtrl_navigation_bar_item_background;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    @Nullable
    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    @DimenRes
    public int getItemDefaultMarginResId() {
        return com.google.android.material.R.dimen.mtrl_navigation_bar_item_default_margin;
    }

    @LayoutRes
    public abstract int getItemLayoutResId();

    public int getItemPosition() {
        return this.itemPosition;
    }

    @Override // android.view.View
    public int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return this.labelGroup.getMeasuredHeight() + getSuggestedIconHeight() + (this.labelGroup.getVisibility() == 0 ? this.activeIndicatorLabelPadding : 0) + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.view.View
    public int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return Math.max(getSuggestedIconWidth(), this.labelGroup.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(@NonNull MenuItemImpl menuItemImpl, int i2) {
        this.itemData = menuItemImpl;
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setIcon(menuItemImpl.getIcon());
        setTitle(menuItemImpl.getTitle());
        setId(menuItemImpl.getItemId());
        if (!TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(menuItemImpl.getContentDescription());
        }
        CharSequence tooltipText = !TextUtils.isEmpty(menuItemImpl.getTooltipText()) ? menuItemImpl.getTooltipText() : menuItemImpl.getTitle();
        if (Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        this.initialized = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    @NonNull
    public int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, CHECKED_STATE_SET);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        if (badgeDrawable != null && badgeDrawable.isVisible()) {
            CharSequence title = this.itemData.getTitle();
            if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
                title = this.itemData.getContentDescription();
            }
            accessibilityNodeInfo.setContentDescription(((Object) title) + ", " + ((Object) this.badgeDrawable.getContentDescription()));
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatWrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        accessibilityNodeInfoCompatWrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            accessibilityNodeInfoCompatWrap.setClickable(false);
            accessibilityNodeInfoCompatWrap.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        accessibilityNodeInfoCompatWrap.setRoleDescription(getResources().getString(com.google.android.material.R.string.item_view_role_description));
    }

    @Override // android.view.View
    public void onSizeChanged(final int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        post(new Runnable() { // from class: com.google.android.material.navigation.NavigationBarItemView.2
            @Override // java.lang.Runnable
            public void run() {
                NavigationBarItemView.this.updateActiveIndicatorLayoutParams(i2);
            }
        });
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    public void removeBadge() {
        tryRemoveBadgeFromAnchor(this.icon);
    }

    public void setActiveIndicatorDrawable(@Nullable Drawable drawable) {
        View view = this.activeIndicatorView;
        if (view == null) {
            return;
        }
        view.setBackgroundDrawable(drawable);
        refreshItemBackground();
    }

    public void setActiveIndicatorEnabled(boolean z2) {
        this.activeIndicatorEnabled = z2;
        refreshItemBackground();
        View view = this.activeIndicatorView;
        if (view != null) {
            view.setVisibility(z2 ? 0 : 8);
            requestLayout();
        }
    }

    public void setActiveIndicatorHeight(int i2) {
        this.activeIndicatorDesiredHeight = i2;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorLabelPadding(int i2) {
        if (this.activeIndicatorLabelPadding != i2) {
            this.activeIndicatorLabelPadding = i2;
            refreshChecked();
        }
    }

    public void setActiveIndicatorMarginHorizontal(@Px int i2) {
        this.activeIndicatorMarginHorizontal = i2;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorResizeable(boolean z2) {
        this.activeIndicatorResizeable = z2;
    }

    public void setActiveIndicatorWidth(int i2) {
        this.activeIndicatorDesiredWidth = i2;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setBadge(@NonNull BadgeDrawable badgeDrawable) {
        ImageView imageView;
        if (this.badgeDrawable == badgeDrawable) {
            return;
        }
        if (hasBadge() && (imageView = this.icon) != null) {
            tryRemoveBadgeFromAnchor(imageView);
        }
        this.badgeDrawable = badgeDrawable;
        ImageView imageView2 = this.icon;
        if (imageView2 != null) {
            tryAttachBadgeToAnchor(imageView2);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean z2) {
        refreshDrawableState();
    }

    /* JADX WARN: Code duplicated, block: B:16:0x006a  */
    /* JADX WARN: Code duplicated, block: B:17:0x0085  */
    /* JADX WARN: Code duplicated, block: B:20:0x00a3 A[PHI: r0 r1
      0x00a3: PHI (r0v10 android.view.View) = (r0v5 android.view.View), (r0v14 android.view.View) binds: [B:26:0x00d0, B:19:0x00a1] A[DONT_GENERATE, DONT_INLINE]
      0x00a3: PHI (r1v17 int) = (r1v15 int), (r1v25 int) binds: [B:26:0x00d0, B:19:0x00a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:21:0x00b3 A[PHI: r0 r1
      0x00b3: PHI (r0v6 android.view.View) = (r0v5 android.view.View), (r0v14 android.view.View) binds: [B:26:0x00d0, B:19:0x00a1] A[DONT_GENERATE, DONT_INLINE]
      0x00b3: PHI (r1v16 int) = (r1v15 int), (r1v25 int) binds: [B:26:0x00d0, B:19:0x00a1] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean z2) {
        View iconOrContainer;
        int i2;
        TextView textView = this.largeLabel;
        textView.setPivotX(textView.getWidth() / 2);
        TextView textView2 = this.largeLabel;
        textView2.setPivotY(textView2.getBaseline());
        TextView textView3 = this.smallLabel;
        textView3.setPivotX(textView3.getWidth() / 2);
        TextView textView4 = this.smallLabel;
        textView4.setPivotY(textView4.getBaseline());
        maybeAnimateActiveIndicatorToProgress(z2 ? 1.0f : 0.0f);
        int i3 = this.labelVisibilityMode;
        if (i3 != -1) {
            if (i3 == 0) {
                iconOrContainer = getIconOrContainer();
                i2 = this.itemPaddingTop;
                if (z2) {
                    setViewTopMarginAndGravity(iconOrContainer, i2, 49);
                    updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                    this.largeLabel.setVisibility(0);
                } else {
                    setViewTopMarginAndGravity(iconOrContainer, i2, 17);
                    updateViewPaddingBottom(this.labelGroup, 0);
                    this.largeLabel.setVisibility(4);
                }
                this.smallLabel.setVisibility(4);
            } else if (i3 == 1) {
                updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                if (z2) {
                    setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                    setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                    TextView textView5 = this.smallLabel;
                    float f2 = this.scaleUpFactor;
                    setViewScaleValues(textView5, f2, f2, 4);
                } else {
                    setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                    TextView textView6 = this.largeLabel;
                    float f3 = this.scaleDownFactor;
                    setViewScaleValues(textView6, f3, f3, 4);
                    setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                }
            } else if (i3 == 2) {
                setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                this.largeLabel.setVisibility(8);
                this.smallLabel.setVisibility(8);
            }
        } else if (this.isShifting) {
            iconOrContainer = getIconOrContainer();
            i2 = this.itemPaddingTop;
            if (z2) {
                setViewTopMarginAndGravity(iconOrContainer, i2, 49);
                updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                this.largeLabel.setVisibility(0);
            } else {
                setViewTopMarginAndGravity(iconOrContainer, i2, 17);
                updateViewPaddingBottom(this.labelGroup, 0);
                this.largeLabel.setVisibility(4);
            }
            this.smallLabel.setVisibility(4);
        } else {
            updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
            if (z2) {
                setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                TextView textView7 = this.smallLabel;
                float f4 = this.scaleUpFactor;
                setViewScaleValues(textView7, f4, f4, 4);
            } else {
                setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                TextView textView8 = this.largeLabel;
                float f5 = this.scaleDownFactor;
                setViewScaleValues(textView8, f5, f5, 4);
                setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
            }
        }
        refreshDrawableState();
        setSelected(z2);
    }

    @Override // android.view.View, androidx.appcompat.view.menu.MenuView.ItemView
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        this.smallLabel.setEnabled(z2);
        this.largeLabel.setEnabled(z2);
        this.icon.setEnabled(z2);
        ViewCompat.setPointerIcon(this, z2 ? PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND) : null);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(@Nullable Drawable drawable) {
        if (drawable == this.originalIconDrawable) {
            return;
        }
        this.originalIconDrawable = drawable;
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable = DrawableCompat.wrap(drawable).mutate();
            this.wrappedIconDrawable = drawable;
            ColorStateList colorStateList = this.iconTint;
            if (colorStateList != null) {
                DrawableCompat.setTintList(drawable, colorStateList);
            }
        }
        this.icon.setImageDrawable(drawable);
    }

    public void setIconSize(int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i2;
        this.icon.setLayoutParams(layoutParams);
    }

    public void setIconTintList(@Nullable ColorStateList colorStateList) {
        Drawable drawable;
        this.iconTint = colorStateList;
        if (this.itemData == null || (drawable = this.wrappedIconDrawable) == null) {
            return;
        }
        DrawableCompat.setTintList(drawable, colorStateList);
        this.wrappedIconDrawable.invalidateSelf();
    }

    public void setItemBackground(int i2) {
        setItemBackground(i2 == 0 ? null : ContextCompat.getDrawable(getContext(), i2));
    }

    public void setItemPaddingBottom(int i2) {
        if (this.itemPaddingBottom != i2) {
            this.itemPaddingBottom = i2;
            refreshChecked();
        }
    }

    public void setItemPaddingTop(int i2) {
        if (this.itemPaddingTop != i2) {
            this.itemPaddingTop = i2;
            refreshChecked();
        }
    }

    public void setItemPosition(int i2) {
        this.itemPosition = i2;
    }

    public void setItemRippleColor(@Nullable ColorStateList colorStateList) {
        this.itemRippleColor = colorStateList;
        refreshItemBackground();
    }

    public void setLabelVisibilityMode(int i2) {
        if (this.labelVisibilityMode != i2) {
            this.labelVisibilityMode = i2;
            updateActiveIndicatorTransform();
            updateActiveIndicatorLayoutParams(getWidth());
            refreshChecked();
        }
    }

    public void setShifting(boolean z2) {
        if (this.isShifting != z2) {
            this.isShifting = z2;
            refreshChecked();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean z2, char c2) {
    }

    public void setTextAppearanceActive(@StyleRes int i2) {
        this.activeTextAppearance = i2;
        setTextAppearanceWithoutFontScaling(this.largeLabel, i2);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextAppearanceActiveBoldEnabled(boolean z2) {
        setTextAppearanceActive(this.activeTextAppearance);
        TextView textView = this.largeLabel;
        textView.setTypeface(textView.getTypeface(), z2 ? 1 : 0);
    }

    public void setTextAppearanceInactive(@StyleRes int i2) {
        setTextAppearanceWithoutFontScaling(this.smallLabel, i2);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextColor(@Nullable ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.smallLabel.setTextColor(colorStateList);
            this.largeLabel.setTextColor(colorStateList);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(@Nullable CharSequence charSequence) {
        this.smallLabel.setText(charSequence);
        this.largeLabel.setText(charSequence);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl == null || TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(charSequence);
        }
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2 != null && !TextUtils.isEmpty(menuItemImpl2.getTooltipText())) {
            charSequence = this.itemData.getTooltipText();
        }
        if (Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, charSequence);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        if (drawable != null && drawable.getConstantState() != null) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        this.itemBackground = drawable;
        refreshItemBackground();
    }
}
