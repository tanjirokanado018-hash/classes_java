package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f99a;

    public /* synthetic */ b(int i2) {
        this.f99a = i2;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.f99a) {
            case 0:
                return dynamicScheme.neutralPalette;
            case 1:
                return MaterialDynamicColors.lambda$surface$16(dynamicScheme);
            case 2:
                return dynamicScheme.neutralPalette;
            case 3:
                return MaterialDynamicColors.lambda$textSecondaryAndTertiaryInverseDisabled$159(dynamicScheme);
            case 4:
                return dynamicScheme.tertiaryPalette;
            case 5:
                return MaterialDynamicColors.lambda$onTertiary$83(dynamicScheme);
            case 6:
                return dynamicScheme.neutralPalette;
            case 7:
                return MaterialDynamicColors.lambda$onSurface$32(dynamicScheme);
            case 8:
                return dynamicScheme.neutralPalette;
            case 9:
                return MaterialDynamicColors.lambda$neutralPaletteKeyColor$7(dynamicScheme);
            case 10:
                return dynamicScheme.neutralPalette;
            case 11:
                return MaterialDynamicColors.lambda$surfaceDim$18(dynamicScheme);
            case 12:
                return dynamicScheme.neutralVariantPalette;
            case 13:
                return MaterialDynamicColors.lambda$controlNormal$148(dynamicScheme);
            case 14:
                return dynamicScheme.primaryPalette;
            case 15:
                return MaterialDynamicColors.lambda$controlActivated$146(dynamicScheme);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return MaterialDynamicColors.lambda$primaryPaletteKeyColor$1(dynamicScheme);
            case 18:
                return dynamicScheme.tertiaryPalette;
            case 19:
                return MaterialDynamicColors.lambda$onTertiaryFixedVariant$142(dynamicScheme);
            case 20:
                return dynamicScheme.secondaryPalette;
            case 21:
                return MaterialDynamicColors.lambda$secondaryPaletteKeyColor$3(dynamicScheme);
            case 22:
                return dynamicScheme.neutralPalette;
            case 23:
                return MaterialDynamicColors.lambda$surfaceContainerLow$24(dynamicScheme);
            case 24:
                return dynamicScheme.secondaryPalette;
            case 25:
                return MaterialDynamicColors.lambda$secondaryContainer$74(dynamicScheme);
            case 26:
                return dynamicScheme.tertiaryPalette;
            case 27:
                return MaterialDynamicColors.lambda$onTertiaryFixed$138(dynamicScheme);
            case 28:
                return dynamicScheme.tertiaryPalette;
            default:
                return MaterialDynamicColors.lambda$tertiaryPaletteKeyColor$5(dynamicScheme);
        }
    }
}
