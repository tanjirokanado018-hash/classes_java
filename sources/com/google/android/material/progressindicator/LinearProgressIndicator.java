package com.google.android.material.progressindicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class LinearProgressIndicator extends BaseProgressIndicator<LinearProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_LinearProgressIndicator;
    public static final int INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS = 0;
    public static final int INDETERMINATE_ANIMATION_TYPE_DISJOINT = 1;
    public static final int INDICATOR_DIRECTION_END_TO_START = 3;
    public static final int INDICATOR_DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int INDICATOR_DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int INDICATOR_DIRECTION_START_TO_END = 2;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface IndeterminateAnimationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface IndicatorDirection {
    }

    public LinearProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    private void initializeDrawables() {
        LinearDrawingDelegate linearDrawingDelegate = new LinearDrawingDelegate((LinearProgressIndicatorSpec) this.spec);
        setIndeterminateDrawable(IndeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec, linearDrawingDelegate));
        setProgressDrawable(DeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec, linearDrawingDelegate));
    }

    public int getIndeterminateAnimationType() {
        return ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType;
    }

    public int getIndicatorDirection() {
        return ((LinearProgressIndicatorSpec) this.spec).indicatorDirection;
    }

    @Px
    public int getTrackStopIndicatorSize() {
        return ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize;
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        S s2 = this.spec;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) s2;
        boolean z3 = true;
        if (((LinearProgressIndicatorSpec) s2).indicatorDirection != 1 && ((ViewCompat.getLayoutDirection(this) != 1 || ((LinearProgressIndicatorSpec) this.spec).indicatorDirection != 2) && (ViewCompat.getLayoutDirection(this) != 0 || ((LinearProgressIndicatorSpec) this.spec).indicatorDirection != 3))) {
            z3 = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z3;
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        int paddingRight = i2 - (getPaddingRight() + getPaddingLeft());
        int paddingBottom = i3 - (getPaddingBottom() + getPaddingTop());
        IndeterminateDrawable<LinearProgressIndicatorSpec> indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != null) {
            indeterminateDrawable.setBounds(0, 0, paddingRight, paddingBottom);
        }
        DeterminateDrawable<LinearProgressIndicatorSpec> progressDrawable = getProgressDrawable();
        if (progressDrawable != null) {
            progressDrawable.setBounds(0, 0, paddingRight, paddingBottom);
        }
    }

    public void setIndeterminateAnimationType(int i2) {
        IndeterminateDrawable<LinearProgressIndicatorSpec> indeterminateDrawable;
        IndeterminateAnimatorDelegate<ObjectAnimator> linearIndeterminateDisjointAnimatorDelegate;
        if (((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType == i2) {
            return;
        }
        if (visibleToUser() && isIndeterminate()) {
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
        S s2 = this.spec;
        ((LinearProgressIndicatorSpec) s2).indeterminateAnimationType = i2;
        ((LinearProgressIndicatorSpec) s2).validateSpec();
        if (i2 == 0) {
            indeterminateDrawable = getIndeterminateDrawable();
            linearIndeterminateDisjointAnimatorDelegate = new LinearIndeterminateContiguousAnimatorDelegate((LinearProgressIndicatorSpec) this.spec);
        } else {
            indeterminateDrawable = getIndeterminateDrawable();
            linearIndeterminateDisjointAnimatorDelegate = new LinearIndeterminateDisjointAnimatorDelegate(getContext(), (LinearProgressIndicatorSpec) this.spec);
        }
        indeterminateDrawable.setAnimatorDelegate(linearIndeterminateDisjointAnimatorDelegate);
        invalidate();
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setIndicatorColor(@NonNull int... iArr) {
        super.setIndicatorColor(iArr);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
    }

    public void setIndicatorDirection(int i2) {
        S s2 = this.spec;
        ((LinearProgressIndicatorSpec) s2).indicatorDirection = i2;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) s2;
        boolean z2 = true;
        if (i2 != 1 && ((ViewCompat.getLayoutDirection(this) != 1 || ((LinearProgressIndicatorSpec) this.spec).indicatorDirection != 2) && (ViewCompat.getLayoutDirection(this) != 0 || i2 != 3))) {
            z2 = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z2;
        invalidate();
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setProgressCompat(int i2, boolean z2) {
        S s2 = this.spec;
        if (s2 != 0 && ((LinearProgressIndicatorSpec) s2).indeterminateAnimationType == 0 && isIndeterminate()) {
            return;
        }
        super.setProgressCompat(i2, z2);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setTrackCornerRadius(int i2) {
        super.setTrackCornerRadius(i2);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
        invalidate();
    }

    public void setTrackStopIndicatorSize(@Px int i2) {
        S s2 = this.spec;
        if (((LinearProgressIndicatorSpec) s2).trackStopIndicatorSize != i2) {
            ((LinearProgressIndicatorSpec) s2).trackStopIndicatorSize = Math.min(i2, ((LinearProgressIndicatorSpec) s2).trackThickness);
            ((LinearProgressIndicatorSpec) this.spec).validateSpec();
            invalidate();
        }
    }

    public LinearProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public LinearProgressIndicatorSpec createSpec(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        return new LinearProgressIndicatorSpec(context, attributeSet);
    }

    public LinearProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        super(context, attributeSet, i2, DEF_STYLE_RES);
        initializeDrawables();
    }
}
