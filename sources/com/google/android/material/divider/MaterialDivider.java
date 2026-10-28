package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MaterialDivider extends View {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;

    @ColorInt
    private int color;

    @NonNull
    private final MaterialShapeDrawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private int thickness;

    public MaterialDivider(@NonNull Context context) {
        this(context, null);
    }

    public int getDividerColor() {
        return this.color;
    }

    @Px
    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    @Px
    public int getDividerInsetStart() {
        return this.insetStart;
    }

    public int getDividerThickness() {
        return this.thickness;
    }

    @Override // android.view.View
    public void onDraw(@NonNull Canvas canvas) {
        int width;
        int i2;
        super.onDraw(canvas);
        boolean z2 = ViewCompat.getLayoutDirection(this) == 1;
        int i3 = z2 ? this.insetEnd : this.insetStart;
        if (z2) {
            width = getWidth();
            i2 = this.insetStart;
        } else {
            width = getWidth();
            i2 = this.insetEnd;
        }
        this.dividerDrawable.setBounds(i3, 0, width - i2, getBottom() - getTop());
        this.dividerDrawable.draw(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i3);
        int measuredHeight = getMeasuredHeight();
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i4 = this.thickness;
            if (i4 > 0 && measuredHeight != i4) {
                measuredHeight = i4;
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    public void setDividerColor(@ColorInt int i2) {
        if (this.color != i2) {
            this.color = i2;
            this.dividerDrawable.setFillColor(ColorStateList.valueOf(i2));
            invalidate();
        }
    }

    public void setDividerColorResource(@ColorRes int i2) {
        setDividerColor(ContextCompat.getColor(getContext(), i2));
    }

    public void setDividerInsetEnd(@Px int i2) {
        this.insetEnd = i2;
    }

    public void setDividerInsetEndResource(@DimenRes int i2) {
        setDividerInsetEnd(getContext().getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerInsetStart(@Px int i2) {
        this.insetStart = i2;
    }

    public void setDividerInsetStartResource(@DimenRes int i2) {
        setDividerInsetStart(getContext().getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerThickness(@Px int i2) {
        if (this.thickness != i2) {
            this.thickness = i2;
            requestLayout();
        }
    }

    public void setDividerThicknessResource(@DimenRes int i2) {
        setDividerThickness(getContext().getResources().getDimensionPixelSize(i2));
    }

    public MaterialDivider(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialDividerStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MaterialDivider(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        int i3 = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, i3), attributeSet, i2);
        Context context2 = getContext();
        this.dividerDrawable = new MaterialShapeDrawable();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.MaterialDivider, i2, i3, new int[0]);
        this.thickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
        this.insetStart = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
        this.insetEnd = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
        setDividerColor(MaterialResources.getColorStateList(context2, typedArrayObtainStyledAttributes, R.styleable.MaterialDivider_dividerColor).getDefaultColor());
        typedArrayObtainStyledAttributes.recycle();
    }
}
