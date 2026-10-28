package com.google.android.material.checkbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.TintTypedArray;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.a;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MaterialCheckBox extends AppCompatCheckBox {
    private static final int[][] CHECKBOX_STATES;
    private static final int[] ERROR_STATE_SET;

    @SuppressLint({"DiscouragedApi"})
    private static final int FRAMEWORK_BUTTON_DRAWABLE_RES_ID;
    public static final int STATE_CHECKED = 1;
    public static final int STATE_INDETERMINATE = 2;
    public static final int STATE_UNCHECKED = 0;
    private boolean broadcasting;

    @Nullable
    private Drawable buttonDrawable;

    @Nullable
    private Drawable buttonIconDrawable;

    @Nullable
    ColorStateList buttonIconTintList;

    @NonNull
    private PorterDuff.Mode buttonIconTintMode;

    @Nullable
    ColorStateList buttonTintList;
    private boolean centerIfNoTextEnabled;
    private int checkedState;
    private int[] currentStateChecked;

    @Nullable
    private CharSequence customStateDescription;

    @Nullable
    private CharSequence errorAccessibilityLabel;
    private boolean errorShown;

    @Nullable
    private ColorStateList materialThemeColorsTintList;

    @Nullable
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    @NonNull
    private final LinkedHashSet<OnCheckedStateChangedListener> onCheckedStateChangedListeners;

    @NonNull
    private final LinkedHashSet<OnErrorChangedListener> onErrorChangedListeners;

    @Nullable
    private final AnimatedVectorDrawableCompat transitionToUnchecked;
    private final Animatable2Compat.AnimationCallback transitionToUncheckedCallback;
    private boolean useMaterialThemeColors;
    private boolean usingMaterialButtonDrawable;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_CheckBox;
    private static final int[] INDETERMINATE_STATE_SET = {R.attr.state_indeterminate};

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface CheckedState {
    }

    public interface OnCheckedStateChangedListener {
        void onCheckedStateChangedListener(@NonNull MaterialCheckBox materialCheckBox, int i2);
    }

    public interface OnErrorChangedListener {
        void onErrorChanged(@NonNull MaterialCheckBox materialCheckBox, boolean z2);
    }

    static {
        int i2 = R.attr.state_error;
        ERROR_STATE_SET = new int[]{i2};
        CHECKBOX_STATES = new int[][]{new int[]{android.R.attr.state_enabled, i2}, new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, new int[]{android.R.attr.state_enabled, -16842912}, new int[]{-16842910, android.R.attr.state_checked}, new int[]{-16842910, -16842912}};
        FRAMEWORK_BUTTON_DRAWABLE_RES_ID = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");
    }

    public MaterialCheckBox(Context context) {
        this(context, null);
    }

    @NonNull
    private String getButtonStateDescription() {
        Resources resources;
        int i2;
        int i3 = this.checkedState;
        if (i3 == 1) {
            resources = getResources();
            i2 = R.string.mtrl_checkbox_state_description_checked;
        } else if (i3 == 0) {
            resources = getResources();
            i2 = R.string.mtrl_checkbox_state_description_unchecked;
        } else {
            resources = getResources();
            i2 = R.string.mtrl_checkbox_state_description_indeterminate;
        }
        return resources.getString(i2);
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList == null) {
            int[][] iArr = CHECKBOX_STATES;
            int[] iArr2 = new int[iArr.length];
            int color = MaterialColors.getColor(this, R.attr.colorControlActivated);
            int color2 = MaterialColors.getColor(this, R.attr.colorError);
            int color3 = MaterialColors.getColor(this, R.attr.colorSurface);
            int color4 = MaterialColors.getColor(this, R.attr.colorOnSurface);
            iArr2[0] = MaterialColors.layer(color3, color2, 1.0f);
            iArr2[1] = MaterialColors.layer(color3, color, 1.0f);
            iArr2[2] = MaterialColors.layer(color3, color4, 0.54f);
            iArr2[3] = MaterialColors.layer(color3, color4, 0.38f);
            iArr2[4] = MaterialColors.layer(color3, color4, 0.38f);
            this.materialThemeColorsTintList = new ColorStateList(iArr, iArr2);
        }
        return this.materialThemeColorsTintList;
    }

    @Nullable
    private ColorStateList getSuperButtonTintList() {
        ColorStateList colorStateList = this.buttonTintList;
        if (colorStateList != null) {
            return colorStateList;
        }
        return super.getButtonTintList() != null ? super.getButtonTintList() : getSupportButtonTintList();
    }

    private boolean isButtonDrawableLegacy(TintTypedArray tintTypedArray) {
        return tintTypedArray.getResourceId(R.styleable.MaterialCheckBox_android_button, 0) == FRAMEWORK_BUTTON_DRAWABLE_RES_ID && tintTypedArray.getResourceId(R.styleable.MaterialCheckBox_buttonCompat, 0) == 0;
    }

    private /* synthetic */ void lambda$new$0() {
        this.buttonIconDrawable.jumpToCurrentState();
    }

    private void refreshButtonDrawable() {
        this.buttonDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonDrawable, this.buttonTintList, CompoundButtonCompat.getButtonTintMode(this));
        this.buttonIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonIconDrawable, this.buttonIconTintList, this.buttonIconTintMode);
        setUpDefaultButtonDrawableAnimationIfNeeded();
        updateButtonTints();
        super.setButtonDrawable(DrawableUtils.compositeTwoLayeredDrawable(this.buttonDrawable, this.buttonIconDrawable));
        refreshDrawableState();
    }

    private void setDefaultStateDescription() {
        if (Build.VERSION.SDK_INT < 30 || this.customStateDescription != null) {
            return;
        }
        super.setStateDescription(getButtonStateDescription());
    }

    private void setUpDefaultButtonDrawableAnimationIfNeeded() {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat;
        if (this.usingMaterialButtonDrawable) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat2 = this.transitionToUnchecked;
            if (animatedVectorDrawableCompat2 != null) {
                animatedVectorDrawableCompat2.unregisterAnimationCallback(this.transitionToUncheckedCallback);
                this.transitionToUnchecked.registerAnimationCallback(this.transitionToUncheckedCallback);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                Drawable drawable = this.buttonDrawable;
                if (!(drawable instanceof AnimatedStateListDrawable) || (animatedVectorDrawableCompat = this.transitionToUnchecked) == null) {
                    return;
                }
                ((AnimatedStateListDrawable) drawable).addTransition(R.id.checked, R.id.unchecked, animatedVectorDrawableCompat, false);
                ((AnimatedStateListDrawable) this.buttonDrawable).addTransition(R.id.indeterminate, R.id.unchecked, this.transitionToUnchecked, false);
            }
        }
    }

    private void updateButtonTints() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        Drawable drawable = this.buttonDrawable;
        if (drawable != null && (colorStateList2 = this.buttonTintList) != null) {
            DrawableCompat.setTintList(drawable, colorStateList2);
        }
        Drawable drawable2 = this.buttonIconDrawable;
        if (drawable2 == null || (colorStateList = this.buttonIconTintList) == null) {
            return;
        }
        DrawableCompat.setTintList(drawable2, colorStateList);
    }

    private void updateIconTintIfNeeded() {
    }

    public void addOnCheckedStateChangedListener(@NonNull OnCheckedStateChangedListener onCheckedStateChangedListener) {
        this.onCheckedStateChangedListeners.add(onCheckedStateChangedListener);
    }

    public void addOnErrorChangedListener(@NonNull OnErrorChangedListener onErrorChangedListener) {
        this.onErrorChangedListeners.add(onErrorChangedListener);
    }

    public void clearOnCheckedStateChangedListeners() {
        this.onCheckedStateChangedListeners.clear();
    }

    public void clearOnErrorChangedListeners() {
        this.onErrorChangedListeners.clear();
    }

    @Override // android.widget.CompoundButton
    @Nullable
    public Drawable getButtonDrawable() {
        return this.buttonDrawable;
    }

    @Nullable
    public Drawable getButtonIconDrawable() {
        return this.buttonIconDrawable;
    }

    @Nullable
    public ColorStateList getButtonIconTintList() {
        return this.buttonIconTintList;
    }

    @NonNull
    public PorterDuff.Mode getButtonIconTintMode() {
        return this.buttonIconTintMode;
    }

    @Override // android.widget.CompoundButton
    @Nullable
    public ColorStateList getButtonTintList() {
        return this.buttonTintList;
    }

    public int getCheckedState() {
        return this.checkedState;
    }

    @Nullable
    public CharSequence getErrorAccessibilityLabel() {
        return this.errorAccessibilityLabel;
    }

    public boolean isCenterIfNoTextEnabled() {
        return this.centerIfNoTextEnabled;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public boolean isChecked() {
        return this.checkedState == 1;
    }

    public boolean isErrorShown() {
        return this.errorShown;
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.buttonTintList == null && this.buttonIconTintList == null) {
            setUseMaterialThemeColors(true);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 2);
        if (getCheckedState() == 2) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, INDETERMINATE_STATE_SET);
        }
        if (isErrorShown()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, ERROR_STATE_SET);
        }
        this.currentStateChecked = DrawableUtils.getCheckedState(iArrOnCreateDrawableState);
        updateIconTintIfNeeded();
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        Drawable buttonDrawable;
        if (!this.centerIfNoTextEnabled || !TextUtils.isEmpty(getText()) || (buttonDrawable = CompoundButtonCompat.getButtonDrawable(this)) == null) {
            super.onDraw(canvas);
            return;
        }
        int width = ((getWidth() - buttonDrawable.getIntrinsicWidth()) / 2) * (ViewUtils.isLayoutRtl(this) ? -1 : 1);
        int iSave = canvas.save();
        canvas.translate(width, 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(iSave);
        if (getBackground() != null) {
            Rect bounds = buttonDrawable.getBounds();
            DrawableCompat.setHotspotBounds(getBackground(), bounds.left + width, bounds.top, bounds.right + width, bounds.bottom);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@Nullable AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (accessibilityNodeInfo != null && isErrorShown()) {
            accessibilityNodeInfo.setText(((Object) accessibilityNodeInfo.getText()) + ", " + ((Object) this.errorAccessibilityLabel));
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCheckedState(savedState.checkedState);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checkedState = getCheckedState();
        return savedState;
    }

    public void removeOnCheckedStateChangedListener(@NonNull OnCheckedStateChangedListener onCheckedStateChangedListener) {
        this.onCheckedStateChangedListeners.remove(onCheckedStateChangedListener);
    }

    public void removeOnErrorChangedListener(@NonNull OnErrorChangedListener onErrorChangedListener) {
        this.onErrorChangedListeners.remove(onErrorChangedListener);
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton
    public void setButtonDrawable(@DrawableRes int i2) {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    public void setButtonIconDrawable(@Nullable Drawable drawable) {
        this.buttonIconDrawable = drawable;
        refreshButtonDrawable();
    }

    public void setButtonIconDrawableResource(@DrawableRes int i2) {
        setButtonIconDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    public void setButtonIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.buttonIconTintList == colorStateList) {
            return;
        }
        this.buttonIconTintList = colorStateList;
        refreshButtonDrawable();
    }

    public void setButtonIconTintMode(@NonNull PorterDuff.Mode mode) {
        if (this.buttonIconTintMode == mode) {
            return;
        }
        this.buttonIconTintMode = mode;
        refreshButtonDrawable();
    }

    @Override // android.widget.CompoundButton
    public void setButtonTintList(@Nullable ColorStateList colorStateList) {
        if (this.buttonTintList == colorStateList) {
            return;
        }
        this.buttonTintList = colorStateList;
        refreshButtonDrawable();
    }

    @Override // android.widget.CompoundButton
    public void setButtonTintMode(@Nullable PorterDuff.Mode mode) {
        setSupportButtonTintMode(mode);
        refreshButtonDrawable();
    }

    public void setCenterIfNoTextEnabled(boolean z2) {
        this.centerIfNoTextEnabled = z2;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
        setCheckedState(z2 ? 1 : 0);
    }

    public void setCheckedState(int i2) {
        AutofillManager autofillManagerM;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        if (this.checkedState != i2) {
            this.checkedState = i2;
            super.setChecked(i2 == 1);
            refreshDrawableState();
            setDefaultStateDescription();
            if (this.broadcasting) {
                return;
            }
            this.broadcasting = true;
            LinkedHashSet<OnCheckedStateChangedListener> linkedHashSet = this.onCheckedStateChangedListeners;
            if (linkedHashSet != null) {
                Iterator<OnCheckedStateChangedListener> it = linkedHashSet.iterator();
                while (it.hasNext()) {
                    it.next().onCheckedStateChangedListener(this, this.checkedState);
                }
            }
            if (this.checkedState != 2 && (onCheckedChangeListener = this.onCheckedChangeListener) != null) {
                onCheckedChangeListener.onCheckedChanged(this, isChecked());
            }
            if (Build.VERSION.SDK_INT >= 26 && (autofillManagerM = a.m(getContext().getSystemService(a.o()))) != null) {
                autofillManagerM.notifyValueChanged(this);
            }
            this.broadcasting = false;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        updateIconTintIfNeeded();
    }

    public void setErrorAccessibilityLabel(@Nullable CharSequence charSequence) {
        this.errorAccessibilityLabel = charSequence;
    }

    public void setErrorAccessibilityLabelResource(@StringRes int i2) {
        setErrorAccessibilityLabel(i2 != 0 ? getResources().getText(i2) : null);
    }

    public void setErrorShown(boolean z2) {
        if (this.errorShown == z2) {
            return;
        }
        this.errorShown = z2;
        refreshDrawableState();
        Iterator<OnErrorChangedListener> it = this.onErrorChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onErrorChanged(this, this.errorShown);
        }
    }

    @Override // android.widget.CompoundButton
    public void setOnCheckedChangeListener(@Nullable CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.widget.CompoundButton, android.view.View
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public void setStateDescription(@Nullable CharSequence charSequence) {
        this.customStateDescription = charSequence;
        if (charSequence == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    public void setUseMaterialThemeColors(boolean z2) {
        this.useMaterialThemeColors = z2;
        CompoundButtonCompat.setButtonTintList(this, z2 ? getMaterialThemeColorsTintList() : null);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        setChecked(!isChecked());
    }

    public static class SavedState extends View.BaseSavedState {

        @NonNull
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.google.android.material.checkbox.MaterialCheckBox.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };
        int checkedState;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checkedState = ((Integer) parcel.readValue(getClass().getClassLoader())).intValue();
        }

        @NonNull
        private String getCheckedStateString() {
            int i2 = this.checkedState;
            if (i2 != 1) {
                return i2 != 2 ? "unchecked" : "indeterminate";
            }
            return "checked";
        }

        @NonNull
        public String toString() {
            StringBuilder sb = new StringBuilder("MaterialCheckBox.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" CheckedState=");
            return androidx.appcompat.graphics.drawable.a.g(sb, getCheckedStateString(), "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeValue(Integer.valueOf(this.checkedState));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public MaterialCheckBox(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkboxStyle);
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton
    public void setButtonDrawable(@Nullable Drawable drawable) {
        this.buttonDrawable = drawable;
        this.usingMaterialButtonDrawable = false;
        refreshButtonDrawable();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MaterialCheckBox(Context context, @Nullable AttributeSet attributeSet, int i2) {
        int i3 = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, i3), attributeSet, i2);
        this.onErrorChangedListeners = new LinkedHashSet<>();
        this.onCheckedStateChangedListeners = new LinkedHashSet<>();
        this.transitionToUnchecked = AnimatedVectorDrawableCompat.create(getContext(), R.drawable.mtrl_checkbox_button_checked_unchecked);
        this.transitionToUncheckedCallback = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.checkbox.MaterialCheckBox.1
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                ColorStateList colorStateList = MaterialCheckBox.this.buttonTintList;
                if (colorStateList != null) {
                    DrawableCompat.setTintList(drawable, colorStateList);
                }
            }

            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationStart(Drawable drawable) {
                super.onAnimationStart(drawable);
                MaterialCheckBox materialCheckBox = MaterialCheckBox.this;
                ColorStateList colorStateList = materialCheckBox.buttonTintList;
                if (colorStateList != null) {
                    DrawableCompat.setTint(drawable, colorStateList.getColorForState(materialCheckBox.currentStateChecked, MaterialCheckBox.this.buttonTintList.getDefaultColor()));
                }
            }
        };
        Context context2 = getContext();
        this.buttonDrawable = CompoundButtonCompat.getButtonDrawable(this);
        this.buttonTintList = getSuperButtonTintList();
        setSupportButtonTintList(null);
        TintTypedArray tintTypedArrayObtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.MaterialCheckBox, i2, i3, new int[0]);
        this.buttonIconDrawable = tintTypedArrayObtainTintedStyledAttributes.getDrawable(R.styleable.MaterialCheckBox_buttonIcon);
        if (this.buttonDrawable != null && ThemeEnforcement.isMaterial3Theme(context2) && isButtonDrawableLegacy(tintTypedArrayObtainTintedStyledAttributes)) {
            super.setButtonDrawable((Drawable) null);
            this.buttonDrawable = AppCompatResources.getDrawable(context2, R.drawable.mtrl_checkbox_button);
            this.usingMaterialButtonDrawable = true;
            if (this.buttonIconDrawable == null) {
                this.buttonIconDrawable = AppCompatResources.getDrawable(context2, R.drawable.mtrl_checkbox_button_icon);
            }
        }
        this.buttonIconTintList = MaterialResources.getColorStateList(context2, tintTypedArrayObtainTintedStyledAttributes, R.styleable.MaterialCheckBox_buttonIconTint);
        this.buttonIconTintMode = ViewUtils.parseTintMode(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.MaterialCheckBox_buttonIconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.useMaterialThemeColors = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.MaterialCheckBox_useMaterialThemeColors, false);
        this.centerIfNoTextEnabled = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.MaterialCheckBox_centerIfNoTextEnabled, true);
        this.errorShown = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.MaterialCheckBox_errorShown, false);
        this.errorAccessibilityLabel = tintTypedArrayObtainTintedStyledAttributes.getText(R.styleable.MaterialCheckBox_errorAccessibilityLabel);
        if (tintTypedArrayObtainTintedStyledAttributes.hasValue(R.styleable.MaterialCheckBox_checkedState)) {
            setCheckedState(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.MaterialCheckBox_checkedState, 0));
        }
        tintTypedArrayObtainTintedStyledAttributes.recycle();
        refreshButtonDrawable();
    }
}
