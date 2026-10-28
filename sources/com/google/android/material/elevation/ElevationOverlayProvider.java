package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ElevationOverlayProvider {
    private static final float FORMULA_MULTIPLIER = 4.5f;
    private static final float FORMULA_OFFSET = 2.0f;
    private static final int OVERLAY_ACCENT_COLOR_ALPHA = (int) Math.round(5.1000000000000005d);
    private final int colorSurface;
    private final float displayDensity;
    private final int elevationOverlayAccentColor;
    private final int elevationOverlayColor;
    private final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(@NonNull Context context) {
        this(MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false), MaterialColors.getColor(context, R.attr.elevationOverlayColor, 0), MaterialColors.getColor(context, R.attr.elevationOverlayAccentColor, 0), MaterialColors.getColor(context, R.attr.colorSurface, 0), context.getResources().getDisplayMetrics().density);
    }

    private boolean isThemeSurfaceColor(@ColorInt int i2) {
        return ColorUtils.setAlphaComponent(i2, 255) == this.colorSurface;
    }

    public int calculateOverlayAlpha(float f2) {
        return Math.round(calculateOverlayAlphaFraction(f2) * 255.0f);
    }

    public float calculateOverlayAlphaFraction(float f2) {
        float f3 = this.displayDensity;
        if (f3 <= 0.0f || f2 <= 0.0f) {
            return 0.0f;
        }
        return Math.min(((((float) Math.log1p(f2 / f3)) * FORMULA_MULTIPLIER) + FORMULA_OFFSET) / 100.0f, 1.0f);
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int i2, float f2) {
        int i3;
        float fCalculateOverlayAlphaFraction = calculateOverlayAlphaFraction(f2);
        int iAlpha = Color.alpha(i2);
        int iLayer = MaterialColors.layer(ColorUtils.setAlphaComponent(i2, 255), this.elevationOverlayColor, fCalculateOverlayAlphaFraction);
        if (fCalculateOverlayAlphaFraction > 0.0f && (i3 = this.elevationOverlayAccentColor) != 0) {
            iLayer = MaterialColors.layer(iLayer, ColorUtils.setAlphaComponent(i3, OVERLAY_ACCENT_COLOR_ALPHA));
        }
        return ColorUtils.setAlphaComponent(iLayer, iAlpha);
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int i2, float f2) {
        return (this.elevationOverlayEnabled && isThemeSurfaceColor(i2)) ? compositeOverlay(i2, f2) : i2;
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float f2) {
        return compositeOverlayIfNeeded(this.colorSurface, f2);
    }

    public float getParentAbsoluteElevation(@NonNull View view) {
        return ViewUtils.getParentAbsoluteElevation(view);
    }

    @ColorInt
    public int getThemeElevationOverlayColor() {
        return this.elevationOverlayColor;
    }

    @ColorInt
    public int getThemeSurfaceColor() {
        return this.colorSurface;
    }

    public boolean isThemeElevationOverlayEnabled() {
        return this.elevationOverlayEnabled;
    }

    public ElevationOverlayProvider(boolean z2, @ColorInt int i2, @ColorInt int i3, @ColorInt int i4, float f2) {
        this.elevationOverlayEnabled = z2;
        this.elevationOverlayColor = i2;
        this.elevationOverlayAccentColor = i3;
        this.colorSurface = i4;
        this.displayDensity = f2;
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int i2, float f2, @NonNull View view) {
        return compositeOverlay(i2, getParentAbsoluteElevation(view) + f2);
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int i2, float f2, @NonNull View view) {
        return compositeOverlayIfNeeded(i2, getParentAbsoluteElevation(view) + f2);
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float f2, @NonNull View view) {
        return compositeOverlayWithThemeSurfaceColorIfNeeded(getParentAbsoluteElevation(view) + f2);
    }
}
