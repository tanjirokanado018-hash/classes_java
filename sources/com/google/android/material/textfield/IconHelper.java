package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.ripple.RippleUtils;
import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class IconHelper {
    private IconHelper() {
    }

    public static void applyIconTint(@NonNull TextInputLayout textInputLayout, @NonNull CheckableImageButton checkableImageButton, ColorStateList colorStateList, PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (colorStateList == null || !colorStateList.isStateful()) {
                DrawableCompat.setTintList(drawable, colorStateList);
            } else {
                DrawableCompat.setTintList(drawable, ColorStateList.valueOf(colorStateList.getColorForState(mergeIconState(textInputLayout, checkableImageButton), colorStateList.getDefaultColor())));
            }
            if (mode != null) {
                DrawableCompat.setTintMode(drawable, mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public static ImageView.ScaleType convertScaleType(int i2) {
        if (i2 == 0) {
            return ImageView.ScaleType.FIT_XY;
        }
        if (i2 == 1) {
            return ImageView.ScaleType.FIT_START;
        }
        if (i2 == 2) {
            return ImageView.ScaleType.FIT_CENTER;
        }
        if (i2 == 3) {
            return ImageView.ScaleType.FIT_END;
        }
        if (i2 != 5) {
            return i2 != 6 ? ImageView.ScaleType.CENTER : ImageView.ScaleType.CENTER_INSIDE;
        }
        return ImageView.ScaleType.CENTER_CROP;
    }

    private static int[] mergeIconState(@NonNull TextInputLayout textInputLayout, @NonNull CheckableImageButton checkableImageButton) {
        int[] drawableState = textInputLayout.getDrawableState();
        int[] drawableState2 = checkableImageButton.getDrawableState();
        int length = drawableState.length;
        int[] iArrCopyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
        System.arraycopy(drawableState2, 0, iArrCopyOf, length, drawableState2.length);
        return iArrCopyOf;
    }

    public static void refreshIconDrawableState(@NonNull TextInputLayout textInputLayout, @NonNull CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() == null || colorStateList == null || !colorStateList.isStateful()) {
            return;
        }
        int colorForState = colorStateList.getColorForState(mergeIconState(textInputLayout, checkableImageButton), colorStateList.getDefaultColor());
        Drawable drawableMutate = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTintList(drawableMutate, ColorStateList.valueOf(colorForState));
        checkableImageButton.setImageDrawable(drawableMutate);
    }

    public static void setCompatRippleBackgroundIfNeeded(@NonNull CheckableImageButton checkableImageButton) {
        if (Build.VERSION.SDK_INT <= 22) {
            checkableImageButton.setBackground(RippleUtils.createOvalRippleLollipop(checkableImageButton.getContext(), (int) ViewUtils.dpToPx(checkableImageButton.getContext(), 4)));
        }
    }

    private static void setIconClickable(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnLongClickListener onLongClickListener) {
        boolean zHasOnClickListeners = ViewCompat.hasOnClickListeners(checkableImageButton);
        boolean z2 = onLongClickListener != null;
        boolean z3 = zHasOnClickListeners || z2;
        checkableImageButton.setFocusable(z3);
        checkableImageButton.setClickable(zHasOnClickListeners);
        checkableImageButton.setPressable(zHasOnClickListeners);
        checkableImageButton.setLongClickable(z2);
        ViewCompat.setImportantForAccessibility(checkableImageButton, z3 ? 1 : 2);
    }

    public static void setIconMinSize(@NonNull CheckableImageButton checkableImageButton, @Px int i2) {
        checkableImageButton.setMinimumWidth(i2);
        checkableImageButton.setMinimumHeight(i2);
    }

    public static void setIconOnClickListener(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnClickListener onClickListener, @Nullable View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnClickListener(onClickListener);
        setIconClickable(checkableImageButton, onLongClickListener);
    }

    public static void setIconOnLongClickListener(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnLongClickListener(onLongClickListener);
        setIconClickable(checkableImageButton, onLongClickListener);
    }

    public static void setIconScaleType(@NonNull CheckableImageButton checkableImageButton, @NonNull ImageView.ScaleType scaleType) {
        checkableImageButton.setScaleType(scaleType);
    }
}
