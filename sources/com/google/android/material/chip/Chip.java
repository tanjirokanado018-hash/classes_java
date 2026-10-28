package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.AnimatorRes;
import androidx.annotation.BoolRes;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.MaterialCheckable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable, MaterialCheckable<Chip> {
    private static final String BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.Button";
    private static final int CHIP_BODY_VIRTUAL_ID = 0;
    private static final int CLOSE_ICON_VIRTUAL_ID = 1;
    private static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private static final int MIN_TOUCH_TARGET_DP = 48;
    private static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private static final String RADIO_BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.RadioButton";
    private static final String TAG = "Chip";

    @Nullable
    private CharSequence accessibilityClassName;

    @Nullable
    private ChipDrawable chipDrawable;
    private boolean closeIconFocused;
    private boolean closeIconHovered;
    private boolean closeIconPressed;
    private boolean deferredCheckedValue;
    private boolean ensureMinTouchTargetSize;
    private final TextAppearanceFontCallback fontCallback;

    @Nullable
    private InsetDrawable insetBackgroundDrawable;
    private int lastLayoutDirection;

    @Dimension(unit = 1)
    private int minTouchTargetSize;

    @Nullable
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    @Nullable
    private MaterialCheckable.OnCheckedChangeListener<Chip> onCheckedChangeListenerInternal;

    @Nullable
    private View.OnClickListener onCloseIconClickListener;
    private final Rect rect;
    private final RectF rectF;

    @Nullable
    private RippleDrawable ripple;

    @NonNull
    private final ChipTouchHelper touchHelper;
    private boolean touchHelperEnabled;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Chip_Action;
    private static final Rect EMPTY_BOUNDS = new Rect();
    private static final int[] SELECTED_STATE = {android.R.attr.state_selected};
    private static final int[] CHECKABLE_STATE_SET = {android.R.attr.state_checkable};

    public class ChipTouchHelper extends ExploreByTouchHelper {
        public ChipTouchHelper(Chip chip) {
            super(chip);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public int getVirtualViewAt(float f2, float f3) {
            return (Chip.this.hasCloseIcon() && Chip.this.getCloseIconTouchBounds().contains(f2, f3)) ? 1 : 0;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void getVisibleVirtualViews(@NonNull List<Integer> list) {
            list.add(0);
            if (Chip.this.hasCloseIcon() && Chip.this.isCloseIconVisible() && Chip.this.onCloseIconClickListener != null) {
                list.add(1);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public boolean onPerformActionForVirtualView(int i2, int i3, Bundle bundle) {
            if (i3 != 16) {
                return false;
            }
            if (i2 == 0) {
                return Chip.this.performClick();
            }
            if (i2 == 1) {
                return Chip.this.performCloseIconClick();
            }
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setCheckable(Chip.this.isCheckable());
            accessibilityNodeInfoCompat.setClickable(Chip.this.isClickable());
            accessibilityNodeInfoCompat.setClassName(Chip.this.getAccessibilityClassName());
            CharSequence text = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityNodeInfoCompat.setText(text);
            } else {
                accessibilityNodeInfoCompat.setContentDescription(text);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateNodeForVirtualView(int i2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (i2 != 1) {
                accessibilityNodeInfoCompat.setContentDescription("");
                accessibilityNodeInfoCompat.setBoundsInParent(Chip.EMPTY_BOUNDS);
                return;
            }
            CharSequence closeIconContentDescription = Chip.this.getCloseIconContentDescription();
            if (closeIconContentDescription == null) {
                CharSequence text = Chip.this.getText();
                Context context = Chip.this.getContext();
                int i3 = R.string.mtrl_chip_close_icon_content_description;
                Object[] objArr = new Object[1];
                objArr[0] = TextUtils.isEmpty(text) ? "" : text;
                closeIconContentDescription = context.getString(i3, objArr).trim();
            }
            accessibilityNodeInfoCompat.setContentDescription(closeIconContentDescription);
            accessibilityNodeInfoCompat.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            accessibilityNodeInfoCompat.setEnabled(Chip.this.isEnabled());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onVirtualViewKeyboardFocusChanged(int i2, boolean z2) {
            if (i2 == 1) {
                Chip.this.closeIconFocused = z2;
                Chip.this.refreshDrawableState();
            }
        }
    }

    public Chip(Context context) {
        this(context, null);
    }

    private void applyChipDrawable(@NonNull ChipDrawable chipDrawable) {
        chipDrawable.setDelegate(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    @NonNull
    private int[] createCloseIconDrawableState() {
        ?? IsEnabled = isEnabled();
        int i2 = IsEnabled;
        if (this.closeIconFocused) {
            i2 = IsEnabled + 1;
        }
        int i3 = i2;
        if (this.closeIconHovered) {
            i3 = i2 + 1;
        }
        int i4 = i3;
        if (this.closeIconPressed) {
            i4 = i3 + 1;
        }
        int i5 = i4;
        if (isChecked()) {
            i5 = i4 + 1;
        }
        int[] iArr = new int[i5];
        int i6 = 0;
        if (isEnabled()) {
            iArr[0] = 16842910;
            i6 = 1;
        }
        if (this.closeIconFocused) {
            iArr[i6] = 16842908;
            i6++;
        }
        if (this.closeIconHovered) {
            iArr[i6] = 16843623;
            i6++;
        }
        if (this.closeIconPressed) {
            iArr[i6] = 16842919;
            i6++;
        }
        if (isChecked()) {
            iArr[i6] = 16842913;
        }
        return iArr;
    }

    private void ensureChipDrawableHasCallback() {
        if (getBackgroundDrawable() == this.insetBackgroundDrawable && this.chipDrawable.getCallback() == null) {
            this.chipDrawable.setCallback(this.insetBackgroundDrawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        if (hasCloseIcon() && this.onCloseIconClickListener != null) {
            this.chipDrawable.getCloseIconTouchBounds(this.rectF);
        }
        return this.rectF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public Rect getCloseIconTouchBoundsInt() {
        RectF closeIconTouchBounds = getCloseIconTouchBounds();
        this.rect.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
        return this.rect;
    }

    @Nullable
    private TextAppearance getTextAppearance() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getTextAppearance();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return (chipDrawable == null || chipDrawable.getCloseIcon() == null) ? false : true;
    }

    private void initMinTouchTarget(Context context, @Nullable AttributeSet attributeSet, int i2) {
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Chip, i2, DEF_STYLE_RES, new int[0]);
        this.ensureMinTouchTargetSize = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Chip_ensureMinTouchTargetSize, false);
        this.minTouchTargetSize = (int) Math.ceil(typedArrayObtainStyledAttributes.getDimension(R.styleable.Chip_chipMinTouchTargetSize, (float) Math.ceil(ViewUtils.dpToPx(getContext(), 48))));
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initOutlineProvider() {
        setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.chip.Chip.2
            @Override // android.view.ViewOutlineProvider
            @TargetApi(21)
            public void getOutline(View view, @NonNull Outline outline) {
                if (Chip.this.chipDrawable != null) {
                    Chip.this.chipDrawable.getOutline(outline);
                } else {
                    outline.setAlpha(0.0f);
                }
            }
        });
    }

    private void insetChipBackgroundDrawable(int i2, int i3, int i4, int i5) {
        this.insetBackgroundDrawable = new InsetDrawable((Drawable) this.chipDrawable, i2, i3, i4, i5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(CompoundButton compoundButton, boolean z2) {
        MaterialCheckable.OnCheckedChangeListener<Chip> onCheckedChangeListener = this.onCheckedChangeListenerInternal;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, z2);
        }
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener2 = this.onCheckedChangeListener;
        if (onCheckedChangeListener2 != null) {
            onCheckedChangeListener2.onCheckedChanged(compoundButton, z2);
        }
    }

    private void removeBackgroundInset() {
        if (this.insetBackgroundDrawable != null) {
            this.insetBackgroundDrawable = null;
            setMinWidth(0);
            setMinHeight((int) getChipMinHeight());
            updateBackgroundDrawable();
        }
    }

    private void setCloseIconHovered(boolean z2) {
        if (this.closeIconHovered != z2) {
            this.closeIconHovered = z2;
            refreshDrawableState();
        }
    }

    private void setCloseIconPressed(boolean z2) {
        if (this.closeIconPressed != z2) {
            this.closeIconPressed = z2;
            refreshDrawableState();
        }
    }

    private void unapplyChipDrawable(@Nullable ChipDrawable chipDrawable) {
        if (chipDrawable != null) {
            chipDrawable.setDelegate(null);
        }
    }

    private void updateAccessibilityDelegate() {
        boolean z2;
        if (hasCloseIcon() && isCloseIconVisible() && this.onCloseIconClickListener != null) {
            ViewCompat.setAccessibilityDelegate(this, this.touchHelper);
            z2 = true;
        } else {
            ViewCompat.setAccessibilityDelegate(this, null);
            z2 = false;
        }
        this.touchHelperEnabled = z2;
    }

    private void updateBackgroundDrawable() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            updateFrameworkRippleBackground();
            return;
        }
        this.chipDrawable.setUseCompatRipple(true);
        ViewCompat.setBackground(this, getBackgroundDrawable());
        updatePaddingInternal();
        ensureChipDrawableHasCallback();
    }

    private void updateFrameworkRippleBackground() {
        this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.getRippleColor()), getBackgroundDrawable(), null);
        this.chipDrawable.setUseCompatRipple(false);
        ViewCompat.setBackground(this, this.ripple);
        updatePaddingInternal();
    }

    private void updatePaddingInternal() {
        ChipDrawable chipDrawable;
        if (TextUtils.isEmpty(getText()) || (chipDrawable = this.chipDrawable) == null) {
            return;
        }
        int iCalculateCloseIconWidth = (int) (this.chipDrawable.calculateCloseIconWidth() + this.chipDrawable.getTextEndPadding() + chipDrawable.getChipEndPadding());
        int iCalculateChipIconWidth = (int) (this.chipDrawable.calculateChipIconWidth() + this.chipDrawable.getTextStartPadding() + this.chipDrawable.getChipStartPadding());
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            iCalculateChipIconWidth += rect.left;
            iCalculateCloseIconWidth += rect.right;
        }
        ViewCompat.setPaddingRelative(this, iCalculateChipIconWidth, getPaddingTop(), iCalculateCloseIconWidth, getPaddingBottom());
    }

    private void updateTextPaintDrawState() {
        TextPaint paint = getPaint();
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            paint.drawableState = chipDrawable.getState();
        }
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), paint, this.fontCallback);
        }
    }

    private void validateAttributes(@Nullable AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        attributeSet.getAttributeValue(NAMESPACE_ANDROID, "background");
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableLeft") != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableStart") != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableEnd") != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableRight") != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        if (!attributeSet.getAttributeBooleanValue(NAMESPACE_ANDROID, "singleLine", true) || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "lines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "minLines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "maxLines", 1) != 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "gravity", 8388627);
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        if (this.touchHelperEnabled) {
            return this.touchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.touchHelperEnabled) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (!this.touchHelper.dispatchKeyEvent(keyEvent) || this.touchHelper.getKeyboardFocusedVirtualViewId() == Integer.MIN_VALUE) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null && chipDrawable.isCloseIconStateful() && this.chipDrawable.setCloseIconState(createCloseIconDrawableState())) {
            invalidate();
        }
    }

    public boolean ensureAccessibleTouchTarget(@Dimension int i2) {
        this.minTouchTargetSize = i2;
        if (!shouldEnsureMinTouchTargetSize()) {
            if (this.insetBackgroundDrawable != null) {
                removeBackgroundInset();
            } else {
                updateBackgroundDrawable();
            }
            return false;
        }
        int iMax = Math.max(0, i2 - this.chipDrawable.getIntrinsicHeight());
        int iMax2 = Math.max(0, i2 - this.chipDrawable.getIntrinsicWidth());
        if (iMax2 <= 0 && iMax <= 0) {
            if (this.insetBackgroundDrawable != null) {
                removeBackgroundInset();
            } else {
                updateBackgroundDrawable();
            }
            return false;
        }
        int i3 = iMax2 > 0 ? iMax2 / 2 : 0;
        int i4 = iMax > 0 ? iMax / 2 : 0;
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            if (rect.top == i4 && rect.bottom == i4 && rect.left == i3 && rect.right == i3) {
                updateBackgroundDrawable();
                return true;
            }
        }
        if (getMinHeight() != i2) {
            setMinHeight(i2);
        }
        if (getMinWidth() != i2) {
            setMinWidth(i2);
        }
        insetChipBackgroundDrawable(i3, i4, i3, i4);
        updateBackgroundDrawable();
        return true;
    }

    @Override // android.widget.CheckBox, android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    @NonNull
    public CharSequence getAccessibilityClassName() {
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        if (!isCheckable()) {
            return isClickable() ? BUTTON_ACCESSIBILITY_CLASS_NAME : GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME;
        }
        ViewParent parent = getParent();
        return ((parent instanceof ChipGroup) && ((ChipGroup) parent).isSingleSelection()) ? RADIO_BUTTON_ACCESSIBILITY_CLASS_NAME : BUTTON_ACCESSIBILITY_CLASS_NAME;
    }

    @Nullable
    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable = this.insetBackgroundDrawable;
        return insetDrawable == null ? this.chipDrawable : insetDrawable;
    }

    @Nullable
    public Drawable getCheckedIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCheckedIcon();
        }
        return null;
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCheckedIconTint();
        }
        return null;
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipBackgroundColor();
        }
        return null;
    }

    public float getChipCornerRadius() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return Math.max(0.0f, chipDrawable.getChipCornerRadius());
        }
        return 0.0f;
    }

    public Drawable getChipDrawable() {
        return this.chipDrawable;
    }

    public float getChipEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipEndPadding();
        }
        return 0.0f;
    }

    @Nullable
    public Drawable getChipIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipIcon();
        }
        return null;
    }

    public float getChipIconSize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipIconSize();
        }
        return 0.0f;
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipIconTint();
        }
        return null;
    }

    public float getChipMinHeight() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipMinHeight();
        }
        return 0.0f;
    }

    public float getChipStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipStartPadding();
        }
        return 0.0f;
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipStrokeColor();
        }
        return null;
    }

    public float getChipStrokeWidth() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getChipStrokeWidth();
        }
        return 0.0f;
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    @Nullable
    public Drawable getCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIcon();
        }
        return null;
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconContentDescription();
        }
        return null;
    }

    public float getCloseIconEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconEndPadding();
        }
        return 0.0f;
    }

    public float getCloseIconSize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconSize();
        }
        return 0.0f;
    }

    public float getCloseIconStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconStartPadding();
        }
        return 0.0f;
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconTint();
        }
        return null;
    }

    @Override // android.widget.TextView
    @Nullable
    public TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getEllipsize();
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public void getFocusedRect(@NonNull Rect rect) {
        if (this.touchHelperEnabled && (this.touchHelper.getKeyboardFocusedVirtualViewId() == 1 || this.touchHelper.getAccessibilityFocusedVirtualViewId() == 1)) {
            rect.set(getCloseIconTouchBoundsInt());
        } else {
            super.getFocusedRect(rect);
        }
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getHideMotionSpec();
        }
        return null;
    }

    public float getIconEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getIconEndPadding();
        }
        return 0.0f;
    }

    public float getIconStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getIconStartPadding();
        }
        return 0.0f;
    }

    @Nullable
    public ColorStateList getRippleColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getRippleColor();
        }
        return null;
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.chipDrawable.getShapeAppearanceModel();
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getShowMotionSpec();
        }
        return null;
    }

    public float getTextEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getTextEndPadding();
        }
        return 0.0f;
    }

    public float getTextStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.getTextStartPadding();
        }
        return 0.0f;
    }

    public boolean isCheckable() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return chipDrawable != null && chipDrawable.isCheckable();
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public boolean isCheckedIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return chipDrawable != null && chipDrawable.isCheckedIconVisible();
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public boolean isChipIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return chipDrawable != null && chipDrawable.isChipIconVisible();
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public boolean isCloseIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return chipDrawable != null && chipDrawable.isCloseIconVisible();
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    @Override // com.google.android.material.chip.ChipDrawable.Delegate
    public void onChipDrawableSizeChange() {
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
        requestLayout();
        invalidateOutline();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 2);
        if (isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, SELECTED_STATE);
        }
        if (isCheckable()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, CHECKABLE_STATE_SET);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    public void onFocusChanged(boolean z2, int i2, Rect rect) {
        super.onFocusChanged(z2, i2, rect);
        if (this.touchHelperEnabled) {
            this.touchHelper.onFocusChanged(z2, i2, rect);
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        boolean zContains;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 7) {
            if (actionMasked == 10) {
                zContains = false;
            }
            return super.onHoverEvent(motionEvent);
        }
        zContains = getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY());
        setCloseIconHovered(zContains);
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(chipGroup.getRowIndex(this), 1, chipGroup.isSingleLine() ? chipGroup.getIndexOfChip(this) : -1, 1, false, isChecked()));
        }
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    @Nullable
    @TargetApi(24)
    public PointerIcon onResolvePointerIcon(@NonNull MotionEvent motionEvent, int i2) {
        return (getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) && isEnabled()) ? PointerIcon.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND) : super.onResolvePointerIcon(motionEvent, i2);
    }

    @Override // android.widget.TextView, android.view.View
    @TargetApi(17)
    public void onRtlPropertiesChanged(int i2) {
        super.onRtlPropertiesChanged(i2);
        if (this.lastLayoutDirection != i2) {
            this.lastLayoutDirection = i2;
            updatePaddingInternal();
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001e, code lost:
    
        if (r0 != 3) goto L23;
     */
    @Override // android.widget.TextView, android.view.View
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(@androidx.annotation.NonNull android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L3b
            if (r0 == r2) goto L2b
            r4 = 2
            if (r0 == r4) goto L21
            r1 = 3
            if (r0 == r1) goto L34
            goto L41
        L21:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L41
            if (r1 != 0) goto L49
            r5.setCloseIconPressed(r3)
            goto L49
        L2b:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L34
            r5.performCloseIconClick()
            r0 = 1
            goto L35
        L34:
            r0 = 0
        L35:
            r5.setCloseIconPressed(r3)
            if (r0 != 0) goto L49
            goto L41
        L3b:
            if (r1 == 0) goto L41
            r5.setCloseIconPressed(r2)
            goto L49
        L41:
            boolean r6 = super.onTouchEvent(r6)
            if (r6 == 0) goto L48
            goto L49
        L48:
            r2 = 0
        L49:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @CallSuper
    public boolean performCloseIconClick() {
        boolean z2 = false;
        playSoundEffect(0);
        View.OnClickListener onClickListener = this.onCloseIconClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
            z2 = true;
        }
        if (this.touchHelperEnabled) {
            this.touchHelper.sendEventForVirtualView(1, 1);
        }
        return z2;
    }

    public void setAccessibilityClassName(@Nullable CharSequence charSequence) {
        this.accessibilityClassName = charSequence;
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (drawable == getBackgroundDrawable() || drawable == this.ripple) {
            super.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable == getBackgroundDrawable() || drawable == this.ripple) {
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundResource(int i2) {
    }

    @Override // android.view.View
    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
    }

    @Override // android.view.View
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
    }

    public void setCheckable(boolean z2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckable(z2);
        }
    }

    public void setCheckableResource(@BoolRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckableResource(i2);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            this.deferredCheckedValue = z2;
        } else if (chipDrawable.isCheckable()) {
            super.setChecked(z2);
        }
    }

    public void setCheckedIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIcon(drawable);
        }
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z2) {
        setCheckedIconVisible(z2);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int i2) {
        setCheckedIconVisible(i2);
    }

    public void setCheckedIconResource(@DrawableRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconResource(i2);
        }
    }

    public void setCheckedIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconTint(colorStateList);
        }
    }

    public void setCheckedIconTintResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconTintResource(i2);
        }
    }

    public void setCheckedIconVisible(@BoolRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconVisible(i2);
        }
    }

    public void setChipBackgroundColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipBackgroundColor(colorStateList);
        }
    }

    public void setChipBackgroundColorResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipBackgroundColorResource(i2);
        }
    }

    @Deprecated
    public void setChipCornerRadius(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipCornerRadius(f2);
        }
    }

    @Deprecated
    public void setChipCornerRadiusResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipCornerRadiusResource(i2);
        }
    }

    public void setChipDrawable(@NonNull ChipDrawable chipDrawable) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != chipDrawable) {
            unapplyChipDrawable(chipDrawable2);
            this.chipDrawable = chipDrawable;
            chipDrawable.setShouldDrawText(false);
            applyChipDrawable(this.chipDrawable);
            ensureAccessibleTouchTarget(this.minTouchTargetSize);
        }
    }

    public void setChipEndPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipEndPadding(f2);
        }
    }

    public void setChipEndPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipEndPaddingResource(i2);
        }
    }

    public void setChipIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIcon(drawable);
        }
    }

    @Deprecated
    public void setChipIconEnabled(boolean z2) {
        setChipIconVisible(z2);
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int i2) {
        setChipIconVisible(i2);
    }

    public void setChipIconResource(@DrawableRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconResource(i2);
        }
    }

    public void setChipIconSize(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconSize(f2);
        }
    }

    public void setChipIconSizeResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconSizeResource(i2);
        }
    }

    public void setChipIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconTint(colorStateList);
        }
    }

    public void setChipIconTintResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconTintResource(i2);
        }
    }

    public void setChipIconVisible(@BoolRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconVisible(i2);
        }
    }

    public void setChipMinHeight(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipMinHeight(f2);
        }
    }

    public void setChipMinHeightResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipMinHeightResource(i2);
        }
    }

    public void setChipStartPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStartPadding(f2);
        }
    }

    public void setChipStartPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStartPaddingResource(i2);
        }
    }

    public void setChipStrokeColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeColor(colorStateList);
        }
    }

    public void setChipStrokeColorResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeColorResource(i2);
        }
    }

    public void setChipStrokeWidth(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeWidth(f2);
        }
    }

    public void setChipStrokeWidthResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeWidthResource(i2);
        }
    }

    @Deprecated
    public void setChipText(@Nullable CharSequence charSequence) {
        setText(charSequence);
    }

    @Deprecated
    public void setChipTextResource(@StringRes int i2) {
        setText(getResources().getString(i2));
    }

    public void setCloseIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIcon(drawable);
        }
        updateAccessibilityDelegate();
    }

    public void setCloseIconContentDescription(@Nullable CharSequence charSequence) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconContentDescription(charSequence);
        }
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z2) {
        setCloseIconVisible(z2);
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int i2) {
        setCloseIconVisible(i2);
    }

    public void setCloseIconEndPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconEndPadding(f2);
        }
    }

    public void setCloseIconEndPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconEndPaddingResource(i2);
        }
    }

    public void setCloseIconResource(@DrawableRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconResource(i2);
        }
        updateAccessibilityDelegate();
    }

    public void setCloseIconSize(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconSize(f2);
        }
    }

    public void setCloseIconSizeResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconSizeResource(i2);
        }
    }

    public void setCloseIconStartPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconStartPadding(f2);
        }
    }

    public void setCloseIconStartPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconStartPaddingResource(i2);
        }
    }

    public void setCloseIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconTint(colorStateList);
        }
    }

    public void setCloseIconTintResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconTintResource(i2);
        }
    }

    public void setCloseIconVisible(@BoolRes int i2) {
        setCloseIconVisible(getResources().getBoolean(i2));
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    public void setCompoundDrawables(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    @RequiresApi(17)
    public void setCompoundDrawablesRelative(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i4 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(i2, i3, i4, i5);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i4 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(i2, i3, i4, i5);
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f2) {
        super.setElevation(f2);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setElevation(f2);
        }
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.chipDrawable == null) {
            return;
        }
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) {
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
        super.setEllipsize(truncateAt);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setEllipsize(truncateAt);
        }
    }

    public void setEnsureMinTouchTargetSize(boolean z2) {
        this.ensureMinTouchTargetSize = z2;
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
    }

    @Override // android.widget.TextView
    public void setGravity(int i2) {
        if (i2 != 8388627) {
            return;
        }
        super.setGravity(i2);
    }

    public void setHideMotionSpec(@Nullable MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setHideMotionSpec(motionSpec);
        }
    }

    public void setHideMotionSpecResource(@AnimatorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setHideMotionSpecResource(i2);
        }
    }

    public void setIconEndPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setIconEndPadding(f2);
        }
    }

    public void setIconEndPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setIconEndPaddingResource(i2);
        }
    }

    public void setIconStartPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setIconStartPadding(f2);
        }
    }

    public void setIconStartPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setIconStartPaddingResource(i2);
        }
    }

    @Override // com.google.android.material.internal.MaterialCheckable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setInternalOnCheckedChangeListener(@Nullable MaterialCheckable.OnCheckedChangeListener<Chip> onCheckedChangeListener) {
        this.onCheckedChangeListenerInternal = onCheckedChangeListener;
    }

    @Override // android.view.View
    public void setLayoutDirection(int i2) {
        if (this.chipDrawable == null) {
            return;
        }
        super.setLayoutDirection(i2);
    }

    @Override // android.widget.TextView
    public void setLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setLines(i2);
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMaxLines(i2);
    }

    @Override // android.widget.TextView
    public void setMaxWidth(@Px int i2) {
        super.setMaxWidth(i2);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setMaxWidth(i2);
        }
    }

    @Override // android.widget.TextView
    public void setMinLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMinLines(i2);
    }

    @Override // android.widget.CompoundButton
    public void setOnCheckedChangeListener(@Nullable CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.onCloseIconClickListener = onClickListener;
        updateAccessibilityDelegate();
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setRippleColor(colorStateList);
        }
        if (this.chipDrawable.getUseCompatRipple()) {
            return;
        }
        updateFrameworkRippleBackground();
    }

    public void setRippleColorResource(@ColorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setRippleColorResource(i2);
            if (this.chipDrawable.getUseCompatRipple()) {
                return;
            }
            updateFrameworkRippleBackground();
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public void setShowMotionSpec(@Nullable MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setShowMotionSpec(motionSpec);
        }
    }

    public void setShowMotionSpecResource(@AnimatorRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setShowMotionSpecResource(i2);
        }
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean z2) {
        if (!z2) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setSingleLine(z2);
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            return;
        }
        if (charSequence == null) {
            charSequence = "";
        }
        super.setText(chipDrawable.shouldDrawText() ? null : charSequence, bufferType);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setText(charSequence);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int i2) {
        super.setTextAppearance(i2);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearanceResource(i2);
        }
        updateTextPaintDrawState();
    }

    public void setTextAppearanceResource(@StyleRes int i2) {
        setTextAppearance(getContext(), i2);
    }

    public void setTextEndPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextEndPadding(f2);
        }
    }

    public void setTextEndPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextEndPaddingResource(i2);
        }
    }

    @Override // android.widget.TextView
    public void setTextSize(int i2, float f2) {
        super.setTextSize(i2, f2);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextSize(TypedValue.applyDimension(i2, f2, getResources().getDisplayMetrics()));
        }
        updateTextPaintDrawState();
    }

    public void setTextStartPadding(float f2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextStartPadding(f2);
        }
    }

    public void setTextStartPaddingResource(@DimenRes int i2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextStartPaddingResource(i2);
        }
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipStyle);
    }

    public void setCheckedIconVisible(boolean z2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconVisible(z2);
        }
    }

    public void setChipIconVisible(boolean z2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setChipIconVisible(z2);
        }
    }

    public void setCloseIconVisible(boolean z2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconVisible(z2);
        }
        updateAccessibilityDelegate();
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearanceResource(i2);
        }
        updateTextPaintDrawState();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Chip(Context context, AttributeSet attributeSet, int i2) {
        int i3 = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, i3), attributeSet, i2);
        this.rect = new Rect();
        this.rectF = new RectF();
        this.fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.chip.Chip.1
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrievalFailed(int i4) {
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrieved(@NonNull Typeface typeface, boolean z2) {
                Chip chip = Chip.this;
                chip.setText(chip.chipDrawable.shouldDrawText() ? Chip.this.chipDrawable.getText() : Chip.this.getText());
                Chip.this.requestLayout();
                Chip.this.invalidate();
            }
        };
        Context context2 = getContext();
        validateAttributes(attributeSet);
        ChipDrawable chipDrawableCreateFromAttributes = ChipDrawable.createFromAttributes(context2, attributeSet, i2, i3);
        initMinTouchTarget(context2, attributeSet, i2);
        setChipDrawable(chipDrawableCreateFromAttributes);
        chipDrawableCreateFromAttributes.setElevation(ViewCompat.getElevation(this));
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.Chip, i2, i3, new int[0]);
        if (Build.VERSION.SDK_INT < 23) {
            setTextColor(MaterialResources.getColorStateList(context2, typedArrayObtainStyledAttributes, R.styleable.Chip_android_textColor));
        }
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R.styleable.Chip_shapeAppearance);
        typedArrayObtainStyledAttributes.recycle();
        this.touchHelper = new ChipTouchHelper(this);
        updateAccessibilityDelegate();
        if (!zHasValue) {
            initOutlineProvider();
        }
        setChecked(this.deferredCheckedValue);
        setText(chipDrawableCreateFromAttributes.getText());
        setEllipsize(chipDrawableCreateFromAttributes.getEllipsize());
        updateTextPaintDrawState();
        if (!this.chipDrawable.shouldDrawText()) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        setGravity(8388627);
        updatePaddingInternal();
        if (shouldEnsureMinTouchTargetSize()) {
            setMinHeight(this.minTouchTargetSize);
        }
        this.lastLayoutDirection = ViewCompat.getLayoutDirection(this);
        super.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.google.android.material.chip.a
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f90a.lambda$new$0(compoundButton, z2);
            }
        });
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearance(textAppearance);
        }
        updateTextPaintDrawState();
    }
}
