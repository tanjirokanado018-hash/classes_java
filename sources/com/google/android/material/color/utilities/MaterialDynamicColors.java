package com.google.android.material.color.utilities;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class MaterialDynamicColors {
    public static double findDesiredChromaByTone(double d, double d2, double d3, boolean z2) {
        Hct hctFrom = Hct.from(d, d2, d3);
        if (hctFrom.getChroma() >= d2) {
            return d3;
        }
        Hct hct = hctFrom;
        double chroma = hctFrom.getChroma();
        double d4 = d3;
        while (hct.getChroma() < d2) {
            double d5 = d4 + (z2 ? -1.0d : 1.0d);
            Hct hctFrom2 = Hct.from(d, d2, d5);
            if (chroma > hctFrom2.getChroma() || Math.abs(hctFrom2.getChroma() - d2) < 0.4d) {
                return d5;
            }
            if (Math.abs(hctFrom2.getChroma() - d2) < Math.abs(hct.getChroma() - d2)) {
                hct = hctFrom2;
            }
            chroma = Math.max(chroma, hctFrom2.getChroma());
            d4 = d5;
        }
        return d4;
    }

    private static boolean isFidelity(DynamicScheme dynamicScheme) {
        Variant variant = dynamicScheme.variant;
        return variant == Variant.FIDELITY || variant == Variant.CONTENT;
    }

    private static boolean isMonochrome(DynamicScheme dynamicScheme) {
        return dynamicScheme.variant == Variant.MONOCHROME;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$background$11(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 6.0d : 98.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$controlActivated$146(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$controlHighlight$150(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 100.0d : 0.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$controlHighlight$151(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 0.2d : 0.12d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$controlNormal$148(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 30.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$error$92(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$error$93(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(errorContainer(), error(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$errorContainer$98(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$errorContainer$99(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(errorContainer(), error(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$inverseOnSurface$40(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 20.0d : 95.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$inverseOnSurface$41(DynamicScheme dynamicScheme) {
        return inverseSurface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$inversePrimary$65(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 40.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$inversePrimary$66(DynamicScheme dynamicScheme) {
        return inverseSurface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$inverseSurface$38(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 20.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$neutralPaletteKeyColor$7(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.neutralPalette.getKeyColor().getTone());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$neutralVariantPaletteKeyColor$9(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.neutralVariantPalette.getKeyColor().getTone());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onBackground$13(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onBackground$14(DynamicScheme dynamicScheme) {
        return background();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onError$95(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 20.0d : 100.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onError$96(DynamicScheme dynamicScheme) {
        return error();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onErrorContainer$101(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onErrorContainer$102(DynamicScheme dynamicScheme) {
        return errorContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onPrimary$56(DynamicScheme dynamicScheme) {
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 10.0d : 90.0d);
        }
        return Double.valueOf(z2 ? 20.0d : 100.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimary$57(DynamicScheme dynamicScheme) {
        return primary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Double lambda$onPrimaryContainer$62(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.foregroundTone(((Double) primaryContainer().tone.apply(dynamicScheme)).doubleValue(), 4.5d));
        }
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 0.0d : 100.0d);
        }
        return Double.valueOf(z2 ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimaryContainer$63(DynamicScheme dynamicScheme) {
        return primaryContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onPrimaryFixed$110(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 100.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimaryFixed$111(DynamicScheme dynamicScheme) {
        return primaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimaryFixed$112(DynamicScheme dynamicScheme) {
        return primaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onPrimaryFixedVariant$114(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 90.0d : 30.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimaryFixedVariant$115(DynamicScheme dynamicScheme) {
        return primaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onPrimaryFixedVariant$116(DynamicScheme dynamicScheme) {
        return primaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onSecondary$71(DynamicScheme dynamicScheme) {
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 10.0d : 100.0d);
        }
        return Double.valueOf(z2 ? 20.0d : 100.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondary$72(DynamicScheme dynamicScheme) {
        return secondary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Double lambda$onSecondaryContainer$77(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.foregroundTone(((Double) secondaryContainer().tone.apply(dynamicScheme)).doubleValue(), 4.5d));
        }
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondaryContainer$78(DynamicScheme dynamicScheme) {
        return secondaryContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onSecondaryFixed$124(DynamicScheme dynamicScheme) {
        return Double.valueOf(10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondaryFixed$125(DynamicScheme dynamicScheme) {
        return secondaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondaryFixed$126(DynamicScheme dynamicScheme) {
        return secondaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onSecondaryFixedVariant$128(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 25.0d : 30.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondaryFixedVariant$129(DynamicScheme dynamicScheme) {
        return secondaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onSecondaryFixedVariant$130(DynamicScheme dynamicScheme) {
        return secondaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onSurface$32(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onSurfaceVariant$36(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 30.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onTertiary$83(DynamicScheme dynamicScheme) {
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 10.0d : 90.0d);
        }
        return Double.valueOf(z2 ? 20.0d : 100.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiary$84(DynamicScheme dynamicScheme) {
        return tertiary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Double lambda$onTertiaryContainer$89(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 0.0d : 100.0d);
        }
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.foregroundTone(((Double) tertiaryContainer().tone.apply(dynamicScheme)).doubleValue(), 4.5d));
        }
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiaryContainer$90(DynamicScheme dynamicScheme) {
        return tertiaryContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onTertiaryFixed$138(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 100.0d : 10.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiaryFixed$139(DynamicScheme dynamicScheme) {
        return tertiaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiaryFixed$140(DynamicScheme dynamicScheme) {
        return tertiaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$onTertiaryFixedVariant$142(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 90.0d : 30.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiaryFixedVariant$143(DynamicScheme dynamicScheme) {
        return tertiaryFixedDim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DynamicColor lambda$onTertiaryFixedVariant$144(DynamicScheme dynamicScheme) {
        return tertiaryFixed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$outline$43(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 60.0d : 50.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$outlineVariant$45(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$primary$53(DynamicScheme dynamicScheme) {
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 100.0d : 0.0d);
        }
        return Double.valueOf(z2 ? 80.0d : 40.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$primary$54(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(primaryContainer(), primary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$primaryContainer$59(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(performAlbers(dynamicScheme.sourceColorHct, dynamicScheme));
        }
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 85.0d : 25.0d);
        }
        return Double.valueOf(z2 ? 30.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$primaryContainer$60(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(primaryContainer(), primary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$primaryFixed$104(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 40.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$primaryFixed$105(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(primaryFixed(), primaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$primaryFixedDim$107(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 30.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$primaryFixedDim$108(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(primaryFixed(), primaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$primaryPaletteKeyColor$1(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.primaryPalette.getKeyColor().getTone());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$scrim$49(DynamicScheme dynamicScheme) {
        return Double.valueOf(0.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$secondary$68(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$secondary$69(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(secondaryContainer(), secondary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$secondaryContainer$74(DynamicScheme dynamicScheme) {
        double d = dynamicScheme.isDark ? 30.0d : 90.0d;
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 30.0d : 85.0d);
        }
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(performAlbers(dynamicScheme.secondaryPalette.getHct(findDesiredChromaByTone(dynamicScheme.secondaryPalette.getHue(), dynamicScheme.secondaryPalette.getChroma(), d, !dynamicScheme.isDark)), dynamicScheme));
        }
        return Double.valueOf(d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$secondaryContainer$75(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(secondaryContainer(), secondary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$secondaryFixed$118(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 80.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$secondaryFixed$119(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(secondaryFixed(), secondaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$secondaryFixedDim$121(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 70.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$secondaryFixedDim$122(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(secondaryFixed(), secondaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$secondaryPaletteKeyColor$3(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.secondaryPalette.getKeyColor().getTone());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$shadow$47(DynamicScheme dynamicScheme) {
        return Double.valueOf(0.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surface$16(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 6.0d : 98.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceBright$20(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 24.0d : 98.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceContainer$26(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 12.0d : 94.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceContainerHigh$28(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 17.0d : 92.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceContainerHighest$30(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 22.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceContainerLow$24(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 10.0d : 96.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceContainerLowest$22(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 4.0d : 100.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceDim$18(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 6.0d : 87.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceTint$51(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$surfaceVariant$34(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$tertiary$80(DynamicScheme dynamicScheme) {
        boolean zIsMonochrome = isMonochrome(dynamicScheme);
        boolean z2 = dynamicScheme.isDark;
        if (zIsMonochrome) {
            return Double.valueOf(z2 ? 90.0d : 25.0d);
        }
        return Double.valueOf(z2 ? 80.0d : 40.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$tertiary$81(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(tertiaryContainer(), tertiary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$tertiaryContainer$86(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 60.0d : 49.0d);
        }
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DislikeAnalyzer.fixIfDisliked(dynamicScheme.tertiaryPalette.getHct(performAlbers(dynamicScheme.tertiaryPalette.getHct(dynamicScheme.sourceColorHct.getTone()), dynamicScheme))).getTone());
        }
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$tertiaryContainer$87(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(tertiaryContainer(), tertiary(), 15.0d, TonePolarity.NEARER, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$tertiaryFixed$132(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 40.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$tertiaryFixed$133(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(tertiaryFixed(), tertiaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$tertiaryFixedDim$135(DynamicScheme dynamicScheme) {
        return Double.valueOf(isMonochrome(dynamicScheme) ? 30.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ToneDeltaPair lambda$tertiaryFixedDim$136(DynamicScheme dynamicScheme) {
        return new ToneDeltaPair(tertiaryFixed(), tertiaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$tertiaryPaletteKeyColor$5(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.tertiaryPalette.getKeyColor().getTone());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$textHintInverse$161(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$textPrimaryInverse$153(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$textPrimaryInverseDisableOnly$157(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$textSecondaryAndTertiaryInverse$155(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 80.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Double lambda$textSecondaryAndTertiaryInverseDisabled$159(DynamicScheme dynamicScheme) {
        return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
    }

    public static double performAlbers(Hct hct, DynamicScheme dynamicScheme) {
        Hct hctInViewingConditions = hct.inViewingConditions(viewingConditionsForAlbers(dynamicScheme));
        return DynamicColor.enableLightForeground((!DynamicColor.tonePrefersLightForeground(hct.getTone()) || DynamicColor.toneAllowsLightForeground(hctInViewingConditions.getTone())) ? hctInViewingConditions.getTone() : hct.getTone());
    }

    private static ViewingConditions viewingConditionsForAlbers(DynamicScheme dynamicScheme) {
        return ViewingConditions.defaultWithBackgroundLstar(dynamicScheme.isDark ? 30.0d : 80.0d);
    }

    @NonNull
    public DynamicColor background() {
        return new DynamicColor("background", new d(5), new d(6), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor controlActivated() {
        return DynamicColor.fromPalette("control_activated", new b(14), new b(15));
    }

    @NonNull
    public DynamicColor controlHighlight() {
        return new DynamicColor("control_highlight", new d(0), new d(1), false, null, null, null, null, new d(2));
    }

    @NonNull
    public DynamicColor controlNormal() {
        return DynamicColor.fromPalette("control_normal", new b(12), new b(13));
    }

    @NonNull
    public DynamicColor error() {
        return new DynamicColor("error", new g(6), new g(7), true, new f(this, 14), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), new f(this, 15));
    }

    @NonNull
    public DynamicColor errorContainer() {
        return new DynamicColor("error_container", new g(20), new g(21), true, new f(this, 18), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new f(this, 19));
    }

    @NonNull
    public DynamicColor highestSurface(@NonNull DynamicScheme dynamicScheme) {
        return dynamicScheme.isDark ? surfaceBright() : surfaceDim();
    }

    @NonNull
    public DynamicColor inverseOnSurface() {
        return new DynamicColor("inverse_on_surface", new g(0), new g(1), false, new f(this, 7), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor inversePrimary() {
        return new DynamicColor("inverse_primary", new d(17), new d(18), false, new c(this, 13), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
    }

    @NonNull
    public DynamicColor inverseSurface() {
        return new DynamicColor("inverse_surface", new g(14), new g(15), false, null, null, null, null);
    }

    @NonNull
    public DynamicColor neutralPaletteKeyColor() {
        return DynamicColor.fromPalette("neutral_palette_key_color", new b(8), new b(9));
    }

    @NonNull
    public DynamicColor neutralVariantPaletteKeyColor() {
        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new e(4), new e(5));
    }

    @NonNull
    public DynamicColor onBackground() {
        int i2 = 10;
        return new DynamicColor("on_background", new d(9), new d(i2), false, new c(this, i2), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
    }

    @NonNull
    public DynamicColor onError() {
        return new DynamicColor("on_error", new d(25), new d(26), false, new c(this, 20), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onErrorContainer() {
        int i2 = 17;
        return new DynamicColor("on_error_container", new g(16), new g(i2), false, new f(this, i2), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onPrimary() {
        return new DynamicColor("on_primary", new d(15), new d(16), false, new c(this, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onPrimaryContainer() {
        return new DynamicColor("on_primary_container", new d(29), new c(this, 21), false, new c(this, 22), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onPrimaryFixed() {
        return new DynamicColor("on_primary_fixed", new g(4), new g(5), false, new f(this, 12), new f(this, 13), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onPrimaryFixedVariant() {
        return new DynamicColor("on_primary_fixed_variant", new d(21), new d(22), false, new c(this, 16), new c(this, 17), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
    }

    @NonNull
    public DynamicColor onSecondary() {
        return new DynamicColor("on_secondary", new e(18), new e(19), false, new f(this, 1), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onSecondaryContainer() {
        return new DynamicColor("on_secondary_container", new g(3), new f(this, 10), false, new f(this, 11), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onSecondaryFixed() {
        return new DynamicColor("on_secondary_fixed", new e(28), new e(29), false, new f(this, 5), new f(this, 6), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onSecondaryFixedVariant() {
        return new DynamicColor("on_secondary_fixed_variant", new g(28), new g(29), false, new f(this, 24), new f(this, 25), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
    }

    @NonNull
    public DynamicColor onSurface() {
        return new DynamicColor("on_surface", new b(6), new b(7), false, new c(this, 1), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onSurfaceVariant() {
        return new DynamicColor("on_surface_variant", new g(12), new g(13), false, new f(this, 16), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
    }

    @NonNull
    public DynamicColor onTertiary() {
        return new DynamicColor("on_tertiary", new b(4), new b(5), false, new c(this, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onTertiaryContainer() {
        return new DynamicColor("on_tertiary_container", new g(2), new f(this, 8), false, new f(this, 9), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onTertiaryFixed() {
        return new DynamicColor("on_tertiary_fixed", new b(26), new b(27), false, new c(this, 6), new c(this, 7), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    @NonNull
    public DynamicColor onTertiaryFixedVariant() {
        return new DynamicColor("on_tertiary_fixed_variant", new b(18), new b(19), false, new c(this, 2), new c(this, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
    }

    @NonNull
    public DynamicColor outline() {
        return new DynamicColor("outline", new e(24), new e(25), false, new f(this, 4), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
    }

    @NonNull
    public DynamicColor outlineVariant() {
        int i2 = 11;
        return new DynamicColor("outline_variant", new d(i2), new d(12), false, new c(this, i2), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), null);
    }

    @NonNull
    public DynamicColor primary() {
        return new DynamicColor("primary", new g(24), new g(25), true, new f(this, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), new f(this, 23));
    }

    @NonNull
    public DynamicColor primaryContainer() {
        return new DynamicColor("primary_container", new d(3), new d(4), true, new c(this, 8), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 9));
    }

    @NonNull
    public DynamicColor primaryFixed() {
        return new DynamicColor("primary_fixed", new e(12), new e(13), true, new c(this, 25), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 26));
    }

    @NonNull
    public DynamicColor primaryFixedDim() {
        return new DynamicColor("primary_fixed_dim", new e(14), new e(15), true, new c(this, 27), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 28));
    }

    @NonNull
    public DynamicColor primaryPaletteKeyColor() {
        return DynamicColor.fromPalette("primary_palette_key_color", new b(16), new b(17));
    }

    @NonNull
    public DynamicColor scrim() {
        return new DynamicColor("scrim", new g(26), new g(27), false, null, null, null, null);
    }

    @NonNull
    public DynamicColor secondary() {
        return new DynamicColor("secondary", new e(20), new e(21), true, new f(this, 2), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), new f(this, 3));
    }

    @NonNull
    public DynamicColor secondaryContainer() {
        return new DynamicColor("secondary_container", new b(24), new b(25), true, new c(this, 4), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 5));
    }

    @NonNull
    public DynamicColor secondaryFixed() {
        return new DynamicColor("secondary_fixed", new d(19), new d(20), true, new c(this, 14), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 15));
    }

    @NonNull
    public DynamicColor secondaryFixedDim() {
        return new DynamicColor("secondary_fixed_dim", new e(0), new e(1), true, new c(this, 23), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 24));
    }

    @NonNull
    public DynamicColor secondaryPaletteKeyColor() {
        return DynamicColor.fromPalette("secondary_palette_key_color", new b(20), new b(21));
    }

    @NonNull
    public DynamicColor shadow() {
        return new DynamicColor("shadow", new e(8), new e(9), false, null, null, null, null);
    }

    @NonNull
    public DynamicColor surface() {
        return new DynamicColor("surface", new b(0), new b(1), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceBright() {
        return new DynamicColor("surface_bright", new d(13), new d(14), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceContainer() {
        return new DynamicColor("surface_container", new g(10), new g(11), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceContainerHigh() {
        return new DynamicColor("surface_container_high", new d(7), new d(8), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceContainerHighest() {
        return new DynamicColor("surface_container_highest", new e(10), new e(11), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceContainerLow() {
        return new DynamicColor("surface_container_low", new b(22), new b(23), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceContainerLowest() {
        return new DynamicColor("surface_container_lowest", new e(22), new e(23), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceDim() {
        return new DynamicColor("surface_dim", new b(10), new b(11), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceTint() {
        return new DynamicColor("surface_tint", new e(26), new e(27), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor surfaceVariant() {
        return new DynamicColor("surface_variant", new e(2), new e(3), true, null, null, null, null);
    }

    @NonNull
    public DynamicColor tertiary() {
        final int i2 = 0;
        Function function = new Function() { // from class: com.google.android.material.color.utilities.h
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                switch (i2) {
                    case 0:
                        return dynamicScheme.tertiaryPalette;
                    default:
                        return MaterialDynamicColors.lambda$tertiary$80(dynamicScheme);
                }
            }
        };
        final int i3 = 1;
        return new DynamicColor("tertiary", function, new Function() { // from class: com.google.android.material.color.utilities.h
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                switch (i3) {
                    case 0:
                        return dynamicScheme.tertiaryPalette;
                    default:
                        return MaterialDynamicColors.lambda$tertiary$80(dynamicScheme);
                }
            }
        }, true, new f(this, 26), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), new f(this, 27));
    }

    @NonNull
    public DynamicColor tertiaryContainer() {
        return new DynamicColor("tertiary_container", new e(16), new e(17), true, new c(this, 29), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new f(this, 0));
    }

    @NonNull
    public DynamicColor tertiaryFixed() {
        return new DynamicColor("tertiary_fixed", new g(22), new g(23), true, new f(this, 20), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new f(this, 21));
    }

    @NonNull
    public DynamicColor tertiaryFixedDim() {
        return new DynamicColor("tertiary_fixed_dim", new d(23), new d(24), true, new c(this, 18), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 7.0d), new c(this, 19));
    }

    @NonNull
    public DynamicColor tertiaryPaletteKeyColor() {
        return DynamicColor.fromPalette("tertiary_palette_key_color", new b(28), new b(29));
    }

    @NonNull
    public DynamicColor textHintInverse() {
        return DynamicColor.fromPalette("text_hint_inverse", new e(6), new e(7));
    }

    @NonNull
    public DynamicColor textPrimaryInverse() {
        return DynamicColor.fromPalette("text_primary_inverse", new g(8), new g(9));
    }

    @NonNull
    public DynamicColor textPrimaryInverseDisableOnly() {
        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new d(27), new d(28));
    }

    @NonNull
    public DynamicColor textSecondaryAndTertiaryInverse() {
        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new g(18), new g(19));
    }

    @NonNull
    public DynamicColor textSecondaryAndTertiaryInverseDisabled() {
        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new b(2), new b(3));
    }
}
