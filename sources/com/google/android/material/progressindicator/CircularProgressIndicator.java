package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class CircularProgressIndicator extends BaseProgressIndicator<CircularProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CircularProgressIndicator;
    public static final int INDICATOR_DIRECTION_CLOCKWISE = 0;
    public static final int INDICATOR_DIRECTION_COUNTERCLOCKWISE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface IndicatorDirection {
    }

    public CircularProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    private void initializeDrawables() {
        CircularDrawingDelegate circularDrawingDelegate = new CircularDrawingDelegate((CircularProgressIndicatorSpec) this.spec);
        setIndeterminateDrawable(IndeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.spec, circularDrawingDelegate));
        setProgressDrawable(DeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.spec, circularDrawingDelegate));
    }

    public int getIndicatorDirection() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorDirection;
    }

    @Px
    public int getIndicatorInset() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorInset;
    }

    @Px
    public int getIndicatorSize() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorSize;
    }

    public void setIndicatorDirection(int i2) {
        ((CircularProgressIndicatorSpec) this.spec).indicatorDirection = i2;
        invalidate();
    }

    public void setIndicatorInset(@Px int i2) {
        S s2 = this.spec;
        if (((CircularProgressIndicatorSpec) s2).indicatorInset != i2) {
            ((CircularProgressIndicatorSpec) s2).indicatorInset = i2;
            invalidate();
        }
    }

    public void setIndicatorSize(@Px int i2) {
        int iMax = Math.max(i2, getTrackThickness() * 2);
        S s2 = this.spec;
        if (((CircularProgressIndicatorSpec) s2).indicatorSize != iMax) {
            ((CircularProgressIndicatorSpec) s2).indicatorSize = iMax;
            ((CircularProgressIndicatorSpec) s2).validateSpec();
            requestLayout();
            invalidate();
        }
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setTrackThickness(int i2) {
        super.setTrackThickness(i2);
        ((CircularProgressIndicatorSpec) this.spec).validateSpec();
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.circularProgressIndicatorStyle);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public CircularProgressIndicatorSpec createSpec(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        return new CircularProgressIndicatorSpec(context, attributeSet);
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        super(context, attributeSet, i2, DEF_STYLE_RES);
        initializeDrawables();
    }
}
