package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.ColorSpace;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ColorKt {
    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component1(long j2) {
        return Color.red(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component2(long j2) {
        return Color.green(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component3(long j2) {
        return Color.blue(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component4(long j2) {
        return Color.alpha(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final long convertTo(@ColorInt int i2, ColorSpace.Named named) {
        return Color.convert(i2, ColorSpace.get(named));
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getAlpha(long j2) {
        return Color.alpha(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getBlue(long j2) {
        return Color.blue(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final ColorSpace getColorSpace(long j2) {
        return Color.colorSpace(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getGreen(long j2) {
        return Color.green(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getLuminance(@ColorInt int i2) {
        return Color.luminance(i2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getRed(long j2) {
        return Color.red(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final boolean isSrgb(long j2) {
        return Color.isSrgb(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final boolean isWideGamut(long j2) {
        return Color.isWideGamut(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Color plus(Color color, Color color2) {
        return ColorUtils.compositeColors(color2, color);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Color toColor(@ColorInt int i2) {
        return Color.valueOf(i2);
    }

    @ColorInt
    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final int toColorInt(long j2) {
        return Color.toArgb(j2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final long toColorLong(@ColorInt int i2) {
        return Color.pack(i2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component1(Color color) {
        return color.getComponent(0);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component2(Color color) {
        return color.getComponent(1);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component3(Color color) {
        return color.getComponent(2);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float component4(Color color) {
        return color.getComponent(3);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final long convertTo(@ColorInt int i2, ColorSpace colorSpace) {
        return Color.convert(i2, colorSpace);
    }

    public static final int getAlpha(@ColorInt int i2) {
        return (i2 >> 24) & 255;
    }

    public static final int getBlue(@ColorInt int i2) {
        return i2 & 255;
    }

    public static final int getGreen(@ColorInt int i2) {
        return (i2 >> 8) & 255;
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final float getLuminance(long j2) {
        return Color.luminance(j2);
    }

    public static final int getRed(@ColorInt int i2) {
        return (i2 >> 16) & 255;
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Color toColor(long j2) {
        return Color.valueOf(j2);
    }

    @ColorInt
    public static final int toColorInt(String str) {
        return Color.parseColor(str);
    }

    public static final int component1(@ColorInt int i2) {
        return (i2 >> 24) & 255;
    }

    public static final int component2(@ColorInt int i2) {
        return (i2 >> 16) & 255;
    }

    public static final int component3(@ColorInt int i2) {
        return (i2 >> 8) & 255;
    }

    public static final int component4(@ColorInt int i2) {
        return i2 & 255;
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final long convertTo(long j2, ColorSpace.Named named) {
        return Color.convert(j2, ColorSpace.get(named));
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final long convertTo(long j2, ColorSpace colorSpace) {
        return Color.convert(j2, colorSpace);
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Color convertTo(Color color, ColorSpace.Named named) {
        return color.convert(ColorSpace.get(named));
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Color convertTo(Color color, ColorSpace colorSpace) {
        return color.convert(colorSpace);
    }
}
