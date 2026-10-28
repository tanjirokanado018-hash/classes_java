package com.google.android.material.color;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.utilities.Blend;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.color.utilities.Hct;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.transformation.FabTransformationScrimBehavior;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MaterialColors {
    public static final float ALPHA_DISABLED = 0.38f;
    public static final float ALPHA_DISABLED_LOW = 0.12f;
    public static final float ALPHA_FULL = 1.0f;
    public static final float ALPHA_LOW = 0.32f;
    public static final float ALPHA_MEDIUM = 0.54f;
    private static final int CHROMA_NEUTRAL = 6;
    private static final int TONE_ACCENT_CONTAINER_DARK = 30;
    private static final int TONE_ACCENT_CONTAINER_LIGHT = 90;
    private static final int TONE_ACCENT_DARK = 80;
    private static final int TONE_ACCENT_LIGHT = 40;
    private static final int TONE_ON_ACCENT_CONTAINER_DARK = 90;
    private static final int TONE_ON_ACCENT_CONTAINER_LIGHT = 10;
    private static final int TONE_ON_ACCENT_DARK = 20;
    private static final int TONE_ON_ACCENT_LIGHT = 100;
    private static final int TONE_SURFACE_CONTAINER_DARK = 12;
    private static final int TONE_SURFACE_CONTAINER_HIGH_DARK = 17;
    private static final int TONE_SURFACE_CONTAINER_HIGH_LIGHT = 92;
    private static final int TONE_SURFACE_CONTAINER_LIGHT = 94;

    private MaterialColors() {
    }

    @ColorInt
    public static int compositeARGBWithAlpha(@ColorInt int i2, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 255) int i3) {
        return ColorUtils.setAlphaComponent(i2, (Color.alpha(i2) * i3) / 255);
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @AttrRes int i2, @ColorInt int i3) {
        Integer colorOrNull = getColorOrNull(context, i2);
        return colorOrNull != null ? colorOrNull.intValue() : i3;
    }

    @Nullable
    @ColorInt
    public static Integer getColorOrNull(@NonNull Context context, @AttrRes int i2) {
        TypedValue typedValueResolve = MaterialAttributes.resolve(context, i2);
        if (typedValueResolve != null) {
            return Integer.valueOf(resolveColor(context, typedValueResolve));
        }
        return null;
    }

    @ColorInt
    private static int getColorRole(@ColorInt int i2, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 100) int i3) {
        Hct hctFromInt = Hct.fromInt(i2);
        hctFromInt.setTone(i3);
        return hctFromInt.toInt();
    }

    @NonNull
    public static ColorRoles getColorRoles(@ColorInt int i2, boolean z2) {
        return z2 ? new ColorRoles(getColorRole(i2, 40), getColorRole(i2, 100), getColorRole(i2, 90), getColorRole(i2, 10)) : new ColorRoles(getColorRole(i2, TONE_ACCENT_DARK), getColorRole(i2, 20), getColorRole(i2, 30), getColorRole(i2, 90));
    }

    @NonNull
    public static ColorStateList getColorStateList(@NonNull Context context, @AttrRes int i2, @NonNull ColorStateList colorStateList) {
        TypedValue typedValueResolve = MaterialAttributes.resolve(context, i2);
        ColorStateList colorStateListResolveColorStateList = typedValueResolve != null ? resolveColorStateList(context, typedValueResolve) : null;
        return colorStateListResolveColorStateList == null ? colorStateList : colorStateListResolveColorStateList;
    }

    @Nullable
    public static ColorStateList getColorStateListOrNull(@NonNull Context context, @AttrRes int i2) {
        TypedValue typedValueResolve = MaterialAttributes.resolve(context, i2);
        if (typedValueResolve == null) {
            return null;
        }
        int i3 = typedValueResolve.resourceId;
        if (i3 != 0) {
            return ContextCompat.getColorStateList(context, i3);
        }
        int i4 = typedValueResolve.data;
        if (i4 != 0) {
            return ColorStateList.valueOf(i4);
        }
        return null;
    }

    @ColorInt
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static int getSurfaceContainerFromSeed(@NonNull Context context, @ColorInt int i2) {
        return getColorRole(i2, isLightTheme(context) ? TONE_SURFACE_CONTAINER_LIGHT : 12, 6);
    }

    @ColorInt
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static int getSurfaceContainerHighFromSeed(@NonNull Context context, @ColorInt int i2) {
        return getColorRole(i2, isLightTheme(context) ? TONE_SURFACE_CONTAINER_HIGH_LIGHT : 17, 6);
    }

    @ColorInt
    public static int harmonize(@ColorInt int i2, @ColorInt int i3) {
        return Blend.harmonize(i2, i3);
    }

    @ColorInt
    public static int harmonizeWithPrimary(@NonNull Context context, @ColorInt int i2) {
        return harmonize(i2, getColor(context, R.attr.colorPrimary, MaterialColors.class.getCanonicalName()));
    }

    public static boolean isColorLight(@ColorInt int i2) {
        return i2 != 0 && ColorUtils.calculateLuminance(i2) > 0.5d;
    }

    public static boolean isLightTheme(@NonNull Context context) {
        return MaterialAttributes.resolveBoolean(context, R.attr.isLightTheme, true);
    }

    @ColorInt
    public static int layer(@ColorInt int i2, @ColorInt int i3) {
        return ColorUtils.compositeColors(i3, i2);
    }

    private static int resolveColor(@NonNull Context context, @NonNull TypedValue typedValue) {
        int i2 = typedValue.resourceId;
        return i2 != 0 ? ContextCompat.getColor(context, i2) : typedValue.data;
    }

    private static ColorStateList resolveColorStateList(@NonNull Context context, @NonNull TypedValue typedValue) {
        int i2 = typedValue.resourceId;
        return i2 != 0 ? ContextCompat.getColorStateList(context, i2) : ColorStateList.valueOf(typedValue.data);
    }

    @ColorInt
    public static int getColor(Context context, @AttrRes int i2, String str) {
        return resolveColor(context, MaterialAttributes.resolveTypedValueOrThrow(context, i2, str));
    }

    @ColorInt
    private static int getColorRole(@ColorInt int i2, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 100) int i3, int i4) {
        Hct hctFromInt = Hct.fromInt(getColorRole(i2, i3));
        hctFromInt.setChroma(i4);
        return hctFromInt.toInt();
    }

    @NonNull
    public static ColorRoles getColorRoles(@NonNull Context context, @ColorInt int i2) {
        return getColorRoles(i2, isLightTheme(context));
    }

    @ColorInt
    public static int layer(@ColorInt int i2, @ColorInt int i3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2) {
        return layer(i2, ColorUtils.setAlphaComponent(i3, Math.round(Color.alpha(i3) * f2)));
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int i2) {
        return resolveColor(view.getContext(), MaterialAttributes.resolveTypedValueOrThrow(view, i2));
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int i2, @AttrRes int i3) {
        return layer(view, i2, i3, 1.0f);
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int i2, @ColorInt int i3) {
        return getColor(view.getContext(), i2, i3);
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int i2, @AttrRes int i3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2) {
        return layer(getColor(view, i2), getColor(view, i3), f2);
    }
}
