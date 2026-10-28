package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DynamicScheme {
    public final double contrastLevel;
    public final TonalPalette errorPalette = TonalPalette.fromHueAndChroma(25.0d, 84.0d);
    public final boolean isDark;
    public final TonalPalette neutralPalette;
    public final TonalPalette neutralVariantPalette;
    public final TonalPalette primaryPalette;
    public final TonalPalette secondaryPalette;
    public final int sourceColorArgb;
    public final Hct sourceColorHct;
    public final TonalPalette tertiaryPalette;
    public final Variant variant;

    public DynamicScheme(Hct hct, Variant variant, boolean z2, double d, TonalPalette tonalPalette, TonalPalette tonalPalette2, TonalPalette tonalPalette3, TonalPalette tonalPalette4, TonalPalette tonalPalette5) {
        this.sourceColorArgb = hct.toInt();
        this.sourceColorHct = hct;
        this.variant = variant;
        this.isDark = z2;
        this.contrastLevel = d;
        this.primaryPalette = tonalPalette;
        this.secondaryPalette = tonalPalette2;
        this.tertiaryPalette = tonalPalette3;
        this.neutralPalette = tonalPalette4;
        this.neutralVariantPalette = tonalPalette5;
    }

    public static double getRotatedHue(Hct hct, double[] dArr, double[] dArr2) {
        double d;
        double hue = hct.getHue();
        int i2 = 0;
        if (dArr2.length != 1) {
            int length = dArr.length;
            while (i2 <= length - 2) {
                double d2 = dArr[i2];
                int i3 = i2 + 1;
                double d3 = dArr[i3];
                if (d2 >= hue || hue >= d3) {
                    i2 = i3;
                } else {
                    d = dArr2[i2];
                }
            }
            return hue;
        }
        d = dArr2[0];
        return MathUtils.sanitizeDegreesDouble(hue + d);
    }
}
