package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Blend {
    private Blend() {
    }

    public static int cam16Ucs(int i2, int i3, double d) {
        Cam16 cam16FromInt = Cam16.fromInt(i2);
        Cam16 cam16FromInt2 = Cam16.fromInt(i3);
        double jstar = cam16FromInt.getJstar();
        double astar = cam16FromInt.getAstar();
        double bstar = cam16FromInt.getBstar();
        return Cam16.fromUcs(((cam16FromInt2.getJstar() - jstar) * d) + jstar, ((cam16FromInt2.getAstar() - astar) * d) + astar, ((cam16FromInt2.getBstar() - bstar) * d) + bstar).toInt();
    }

    public static int harmonize(int i2, int i3) {
        Hct hctFromInt = Hct.fromInt(i2);
        Hct hctFromInt2 = Hct.fromInt(i3);
        double dMin = Math.min(MathUtils.differenceDegrees(hctFromInt.getHue(), hctFromInt2.getHue()) * 0.5d, 15.0d);
        return Hct.from(MathUtils.sanitizeDegreesDouble((MathUtils.rotationDirection(hctFromInt.getHue(), hctFromInt2.getHue()) * dMin) + hctFromInt.getHue()), hctFromInt.getChroma(), hctFromInt.getTone()).toInt();
    }

    public static int hctHue(int i2, int i3, double d) {
        return Hct.from(Cam16.fromInt(cam16Ucs(i2, i3, d)).getHue(), Cam16.fromInt(i2).getChroma(), ColorUtils.lstarFromArgb(i2)).toInt();
    }
}
