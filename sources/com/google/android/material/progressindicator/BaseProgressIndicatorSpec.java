package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class BaseProgressIndicatorSpec {
    public int hideAnimationBehavior;

    @NonNull
    public int[] indicatorColors = new int[0];

    @Px
    public int indicatorTrackGapSize;
    public int showAnimationBehavior;

    @ColorInt
    public int trackColor;

    @Px
    public int trackCornerRadius;

    @Px
    public int trackThickness;

    public BaseProgressIndicatorSpec(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2, @StyleRes int i3) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_track_thickness);
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.BaseProgressIndicator, i2, i3, new int[0]);
        this.trackThickness = MaterialResources.getDimensionPixelSize(context, typedArrayObtainStyledAttributes, R.styleable.BaseProgressIndicator_trackThickness, dimensionPixelSize);
        this.trackCornerRadius = Math.min(MaterialResources.getDimensionPixelSize(context, typedArrayObtainStyledAttributes, R.styleable.BaseProgressIndicator_trackCornerRadius, 0), this.trackThickness / 2);
        this.showAnimationBehavior = typedArrayObtainStyledAttributes.getInt(R.styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.hideAnimationBehavior = typedArrayObtainStyledAttributes.getInt(R.styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        this.indicatorTrackGapSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BaseProgressIndicator_indicatorTrackGapSize, 0);
        loadIndicatorColors(context, typedArrayObtainStyledAttributes);
        loadTrackColor(context, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void loadIndicatorColors(@NonNull Context context, @NonNull TypedArray typedArray) {
        if (!typedArray.hasValue(R.styleable.BaseProgressIndicator_indicatorColor)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, R.attr.colorPrimary, -1)};
            return;
        }
        if (typedArray.peekValue(R.styleable.BaseProgressIndicator_indicatorColor).type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(R.styleable.BaseProgressIndicator_indicatorColor, -1)};
            return;
        }
        int[] intArray = context.getResources().getIntArray(typedArray.getResourceId(R.styleable.BaseProgressIndicator_indicatorColor, -1));
        this.indicatorColors = intArray;
        if (intArray.length == 0) {
            throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
        }
    }

    private void loadTrackColor(@NonNull Context context, @NonNull TypedArray typedArray) {
        int iCompositeARGBWithAlpha;
        if (typedArray.hasValue(R.styleable.BaseProgressIndicator_trackColor)) {
            iCompositeARGBWithAlpha = typedArray.getColor(R.styleable.BaseProgressIndicator_trackColor, -1);
        } else {
            this.trackColor = this.indicatorColors[0];
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
            float f2 = typedArrayObtainStyledAttributes.getFloat(0, 0.2f);
            typedArrayObtainStyledAttributes.recycle();
            iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (f2 * 255.0f));
        }
        this.trackColor = iCompositeARGBWithAlpha;
    }

    public boolean isHideAnimationEnabled() {
        return this.hideAnimationBehavior != 0;
    }

    public boolean isShowAnimationEnabled() {
        return this.showAnimationBehavior != 0;
    }

    @CallSuper
    public void validateSpec() {
        if (this.indicatorTrackGapSize < 0) {
            throw new IllegalArgumentException("indicatorTrackGapSize must be >= 0.");
        }
    }
}
