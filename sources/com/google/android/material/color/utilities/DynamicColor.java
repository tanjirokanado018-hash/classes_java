package com.google.android.material.color.utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class DynamicColor {
    public final Function<DynamicScheme, DynamicColor> background;
    public final ContrastCurve contrastCurve;
    private final HashMap<DynamicScheme, Hct> hctCache;
    public final boolean isBackground;
    public final String name;
    public final Function<DynamicScheme, Double> opacity;
    public final Function<DynamicScheme, TonalPalette> palette;
    public final Function<DynamicScheme, DynamicColor> secondBackground;
    public final Function<DynamicScheme, Double> tone;
    public final Function<DynamicScheme, ToneDeltaPair> toneDeltaPair;

    public DynamicColor(@NonNull String str, @NonNull Function<DynamicScheme, TonalPalette> function, @NonNull Function<DynamicScheme, Double> function2, boolean z2, @Nullable Function<DynamicScheme, DynamicColor> function3, @Nullable Function<DynamicScheme, DynamicColor> function4, @Nullable ContrastCurve contrastCurve, @Nullable Function<DynamicScheme, ToneDeltaPair> function5) {
        this.hctCache = new HashMap<>();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z2;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = null;
    }

    public static double enableLightForeground(double d) {
        if (!tonePrefersLightForeground(d) || toneAllowsLightForeground(d)) {
            return d;
        }
        return 49.0d;
    }

    public static double foregroundTone(double d, double d2) {
        double dLighterUnsafe = Contrast.lighterUnsafe(d, d2);
        double dDarkerUnsafe = Contrast.darkerUnsafe(d, d2);
        double dRatioOfTones = Contrast.ratioOfTones(dLighterUnsafe, d);
        double dRatioOfTones2 = Contrast.ratioOfTones(dDarkerUnsafe, d);
        if (tonePrefersLightForeground(d)) {
            return (dRatioOfTones >= d2 || dRatioOfTones >= dRatioOfTones2 || ((Math.abs(dRatioOfTones - dRatioOfTones2) > 0.1d ? 1 : (Math.abs(dRatioOfTones - dRatioOfTones2) == 0.1d ? 0 : -1)) < 0 && (dRatioOfTones > d2 ? 1 : (dRatioOfTones == d2 ? 0 : -1)) < 0 && (dRatioOfTones2 > d2 ? 1 : (dRatioOfTones2 == d2 ? 0 : -1)) < 0)) ? dLighterUnsafe : dDarkerUnsafe;
        }
        return (dRatioOfTones2 >= d2 || dRatioOfTones2 >= dRatioOfTones) ? dDarkerUnsafe : dLighterUnsafe;
    }

    @NonNull
    public static DynamicColor fromArgb(@NonNull String str, int i2) {
        return fromPalette(str, new a(0, TonalPalette.fromInt(i2)), new a(1, Hct.fromInt(i2)));
    }

    @NonNull
    public static DynamicColor fromPalette(@NonNull String str, @NonNull Function<DynamicScheme, TonalPalette> function, @NonNull Function<DynamicScheme, Double> function2) {
        return new DynamicColor(str, function, function2, false, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TonalPalette lambda$fromArgb$0(TonalPalette tonalPalette, DynamicScheme dynamicScheme) {
        return tonalPalette;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$fromArgb$1(Hct hct, DynamicScheme dynamicScheme) {
        return Double.valueOf(hct.getTone());
    }

    public static boolean toneAllowsLightForeground(double d) {
        return Math.round(d) <= 49;
    }

    public static boolean tonePrefersLightForeground(double d) {
        return Math.round(d) < 60;
    }

    public int getArgb(@NonNull DynamicScheme dynamicScheme) {
        int i2 = getHct(dynamicScheme).toInt();
        Function<DynamicScheme, Double> function = this.opacity;
        if (function == null) {
            return i2;
        }
        return (MathUtils.clampInt(0, 255, (int) Math.round(((Double) function.apply(dynamicScheme)).doubleValue() * 255.0d)) << 24) | (i2 & ViewCompat.MEASURED_SIZE_MASK);
    }

    @NonNull
    public Hct getHct(@NonNull DynamicScheme dynamicScheme) {
        Hct hct = this.hctCache.get(dynamicScheme);
        if (hct != null) {
            return hct;
        }
        Hct hct2 = ((TonalPalette) this.palette.apply(dynamicScheme)).getHct(getTone(dynamicScheme));
        if (this.hctCache.size() > 4) {
            this.hctCache.clear();
        }
        this.hctCache.put(dynamicScheme, hct2);
        return hct2;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:89:0x0196  */
    public double getTone(@NonNull DynamicScheme dynamicScheme) {
        double d;
        double dMax;
        boolean z2 = dynamicScheme.contrastLevel < 0.0d;
        Function<DynamicScheme, ToneDeltaPair> function = this.toneDeltaPair;
        if (function == null) {
            double dDoubleValue = ((Double) this.tone.apply(dynamicScheme)).doubleValue();
            Function<DynamicScheme, DynamicColor> function2 = this.background;
            if (function2 == null) {
                return dDoubleValue;
            }
            double tone = ((DynamicColor) function2.apply(dynamicScheme)).getTone(dynamicScheme);
            double contrast = this.contrastCurve.getContrast(dynamicScheme.contrastLevel);
            if (Contrast.ratioOfTones(tone, dDoubleValue) < contrast) {
                dDoubleValue = foregroundTone(tone, contrast);
            }
            if (z2) {
                dDoubleValue = foregroundTone(tone, contrast);
            }
            if (!this.isBackground || 50.0d > dDoubleValue) {
                d = dDoubleValue;
            } else {
                d = 60.0d;
                if (dDoubleValue >= 60.0d) {
                    d = dDoubleValue;
                } else if (Contrast.ratioOfTones(49.0d, tone) >= contrast) {
                    d = 49.0d;
                }
            }
            if (this.secondBackground == null) {
                return d;
            }
            double tone2 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
            double tone3 = ((DynamicColor) this.secondBackground.apply(dynamicScheme)).getTone(dynamicScheme);
            double dMax2 = Math.max(tone2, tone3);
            double dMin = Math.min(tone2, tone3);
            if (Contrast.ratioOfTones(dMax2, d) >= contrast && Contrast.ratioOfTones(dMin, d) >= contrast) {
                return d;
            }
            double dLighter = Contrast.lighter(dMax2, contrast);
            double dDarker = Contrast.darker(dMin, contrast);
            ArrayList arrayList = new ArrayList();
            if (dLighter != -1.0d) {
                arrayList.add(Double.valueOf(dLighter));
            }
            if (dDarker != -1.0d) {
                arrayList.add(Double.valueOf(dDarker));
            }
            if (tonePrefersLightForeground(tone2) || tonePrefersLightForeground(tone3)) {
                if (dLighter == -1.0d) {
                    return 100.0d;
                }
                return dLighter;
            }
            if (arrayList.size() == 1) {
                return ((Double) arrayList.get(0)).doubleValue();
            }
            if (dDarker == -1.0d) {
                return 0.0d;
            }
            return dDarker;
        }
        ToneDeltaPair toneDeltaPair = (ToneDeltaPair) function.apply(dynamicScheme);
        DynamicColor roleA = toneDeltaPair.getRoleA();
        DynamicColor roleB = toneDeltaPair.getRoleB();
        double delta = toneDeltaPair.getDelta();
        TonePolarity polarity = toneDeltaPair.getPolarity();
        boolean stayTogether = toneDeltaPair.getStayTogether();
        double tone4 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
        boolean z3 = polarity == TonePolarity.NEARER || (polarity == TonePolarity.LIGHTER && !dynamicScheme.isDark) || (polarity == TonePolarity.DARKER && dynamicScheme.isDark);
        DynamicColor dynamicColor = z3 ? roleA : roleB;
        DynamicColor dynamicColor2 = z3 ? roleB : roleA;
        boolean zEquals = this.name.equals(dynamicColor.name);
        double d2 = dynamicScheme.isDark ? 1.0d : -1.0d;
        double contrast2 = dynamicColor.contrastCurve.getContrast(dynamicScheme.contrastLevel);
        double contrast3 = dynamicColor2.contrastCurve.getContrast(dynamicScheme.contrastLevel);
        double dDoubleValue2 = ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue2) < contrast2) {
            dDoubleValue2 = foregroundTone(tone4, contrast2);
        }
        double dDoubleValue3 = ((Double) dynamicColor2.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue3) < contrast3) {
            dDoubleValue3 = foregroundTone(tone4, contrast3);
        }
        if (z2) {
            dDoubleValue2 = foregroundTone(tone4, contrast2);
            dDoubleValue3 = foregroundTone(tone4, contrast3);
        }
        if ((dDoubleValue3 - dDoubleValue2) * d2 < delta) {
            double d3 = delta * d2;
            dDoubleValue3 = MathUtils.clampDouble(0.0d, 100.0d, dDoubleValue2 + d3);
            if ((dDoubleValue3 - dDoubleValue2) * d2 < delta) {
                dDoubleValue2 = MathUtils.clampDouble(0.0d, 100.0d, dDoubleValue3 - d3);
            }
        }
        if (50.0d > dDoubleValue2 || dDoubleValue2 >= 60.0d) {
            if (50.0d > dDoubleValue3 || dDoubleValue3 >= 60.0d) {
                dMax = dDoubleValue3;
            } else if (!stayTogether) {
                dMax = d2 > 0.0d ? 60.0d : 49.0d;
            } else if (d2 > 0.0d) {
                dMax = Math.max(dDoubleValue3, (delta * d2) + 60.0d);
                dDoubleValue2 = 60.0d;
            } else {
                dMax = Math.min(dDoubleValue3, (delta * d2) + 49.0d);
                dDoubleValue2 = 49.0d;
            }
        } else if (d2 > 0.0d) {
            dDoubleValue2 = 60.0d;
            dMax = Math.max(dDoubleValue3, (delta * d2) + 60.0d);
        } else {
            dMax = Math.min(dDoubleValue3, (delta * d2) + 49.0d);
            dDoubleValue2 = 49.0d;
        }
        return zEquals ? dDoubleValue2 : dMax;
    }

    public DynamicColor(@NonNull String str, @NonNull Function<DynamicScheme, TonalPalette> function, @NonNull Function<DynamicScheme, Double> function2, boolean z2, @Nullable Function<DynamicScheme, DynamicColor> function3, @Nullable Function<DynamicScheme, DynamicColor> function4, @Nullable ContrastCurve contrastCurve, @Nullable Function<DynamicScheme, ToneDeltaPair> function5, @Nullable Function<DynamicScheme, Double> function6) {
        this.hctCache = new HashMap<>();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z2;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = function6;
    }

    @NonNull
    public static DynamicColor fromPalette(@NonNull String str, @NonNull Function<DynamicScheme, TonalPalette> function, @NonNull Function<DynamicScheme, Double> function2, boolean z2) {
        return new DynamicColor(str, function, function2, z2, null, null, null, null);
    }
}
