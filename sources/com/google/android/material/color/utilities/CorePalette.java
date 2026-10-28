package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class CorePalette {
    public TonalPalette a1;
    public TonalPalette a2;
    public TonalPalette a3;
    public TonalPalette error;
    public TonalPalette n1;
    public TonalPalette n2;

    private CorePalette(int i2, boolean z2) {
        TonalPalette tonalPaletteFromHueAndChroma;
        Hct hctFromInt = Hct.fromInt(i2);
        double hue = hctFromInt.getHue();
        double chroma = hctFromInt.getChroma();
        if (z2) {
            this.a1 = TonalPalette.fromHueAndChroma(hue, chroma);
            this.a2 = TonalPalette.fromHueAndChroma(hue, chroma / 3.0d);
            this.a3 = TonalPalette.fromHueAndChroma(60.0d + hue, chroma / 2.0d);
            this.n1 = TonalPalette.fromHueAndChroma(hue, Math.min(chroma / 12.0d, 4.0d));
            tonalPaletteFromHueAndChroma = TonalPalette.fromHueAndChroma(hue, Math.min(chroma / 6.0d, 8.0d));
        } else {
            this.a1 = TonalPalette.fromHueAndChroma(hue, Math.max(48.0d, chroma));
            this.a2 = TonalPalette.fromHueAndChroma(hue, 16.0d);
            this.a3 = TonalPalette.fromHueAndChroma(60.0d + hue, 24.0d);
            this.n1 = TonalPalette.fromHueAndChroma(hue, 4.0d);
            tonalPaletteFromHueAndChroma = TonalPalette.fromHueAndChroma(hue, 8.0d);
        }
        this.n2 = tonalPaletteFromHueAndChroma;
        this.error = TonalPalette.fromHueAndChroma(25.0d, 84.0d);
    }

    public static CorePalette contentOf(int i2) {
        return new CorePalette(i2, true);
    }

    public static CorePalette of(int i2) {
        return new CorePalette(i2, false);
    }
}
