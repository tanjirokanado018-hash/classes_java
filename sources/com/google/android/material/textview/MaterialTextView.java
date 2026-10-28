package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MaterialTextView extends AppCompatTextView {
    public MaterialTextView(@NonNull Context context) {
        this(context, null);
    }

    private void applyLineHeightFromViewAppearance(@NonNull Resources.Theme theme, int i2) {
        TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(i2, R.styleable.MaterialTextAppearance);
        int firstAvailableDimension = readFirstAvailableDimension(getContext(), typedArrayObtainStyledAttributes, R.styleable.MaterialTextAppearance_android_lineHeight, R.styleable.MaterialTextAppearance_lineHeight);
        typedArrayObtainStyledAttributes.recycle();
        if (firstAvailableDimension >= 0) {
            setLineHeight(firstAvailableDimension);
        }
    }

    private static boolean canApplyTextAppearanceLineHeight(Context context) {
        return MaterialAttributes.resolveBoolean(context, R.attr.textAppearanceLineHeightEnabled, true);
    }

    private static int findViewAppearanceResourceId(@NonNull Resources.Theme theme, @Nullable AttributeSet attributeSet, int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.MaterialTextView, i2, i3);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MaterialTextView_android_textAppearance, -1);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    private void initialize(@Nullable AttributeSet attributeSet, int i2, int i3) {
        int iFindViewAppearanceResourceId;
        Context context = getContext();
        if (canApplyTextAppearanceLineHeight(context)) {
            Resources.Theme theme = context.getTheme();
            if (viewAttrsHasLineHeight(context, theme, attributeSet, i2, i3) || (iFindViewAppearanceResourceId = findViewAppearanceResourceId(theme, attributeSet, i2, i3)) == -1) {
                return;
            }
            applyLineHeightFromViewAppearance(theme, iFindViewAppearanceResourceId);
        }
    }

    private static int readFirstAvailableDimension(@NonNull Context context, @NonNull TypedArray typedArray, @NonNull @StyleableRes int... iArr) {
        int dimensionPixelSize = -1;
        for (int i2 = 0; i2 < iArr.length && dimensionPixelSize < 0; i2++) {
            dimensionPixelSize = MaterialResources.getDimensionPixelSize(context, typedArray, iArr[i2], -1);
        }
        return dimensionPixelSize;
    }

    private static boolean viewAttrsHasLineHeight(@NonNull Context context, @NonNull Resources.Theme theme, @Nullable AttributeSet attributeSet, int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.MaterialTextView, i2, i3);
        int firstAvailableDimension = readFirstAvailableDimension(context, typedArrayObtainStyledAttributes, R.styleable.MaterialTextView_android_lineHeight, R.styleable.MaterialTextView_lineHeight);
        typedArrayObtainStyledAttributes.recycle();
        return firstAvailableDimension != -1;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public void setTextAppearance(@NonNull Context context, int i2) {
        super.setTextAppearance(context, i2);
        if (canApplyTextAppearanceLineHeight(context)) {
            applyLineHeightFromViewAppearance(context.getTheme(), i2);
        }
    }

    public MaterialTextView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.textViewStyle);
    }

    public MaterialTextView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, 0), attributeSet, i2);
        initialize(attributeSet, i2, 0);
    }

    @Deprecated
    public MaterialTextView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, i3), attributeSet, i2);
        initialize(attributeSet, i2, i3);
    }
}
