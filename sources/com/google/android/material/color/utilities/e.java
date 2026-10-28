package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class e implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f102a;

    public /* synthetic */ e(int i2) {
        this.f102a = i2;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.f102a) {
            case 0:
                return dynamicScheme.secondaryPalette;
            case 1:
                return MaterialDynamicColors.lambda$secondaryFixedDim$121(dynamicScheme);
            case 2:
                return dynamicScheme.neutralVariantPalette;
            case 3:
                return MaterialDynamicColors.lambda$surfaceVariant$34(dynamicScheme);
            case 4:
                return dynamicScheme.neutralVariantPalette;
            case 5:
                return MaterialDynamicColors.lambda$neutralVariantPaletteKeyColor$9(dynamicScheme);
            case 6:
                return dynamicScheme.neutralPalette;
            case 7:
                return MaterialDynamicColors.lambda$textHintInverse$161(dynamicScheme);
            case 8:
                return dynamicScheme.neutralPalette;
            case 9:
                return MaterialDynamicColors.lambda$shadow$47(dynamicScheme);
            case 10:
                return dynamicScheme.neutralPalette;
            case 11:
                return MaterialDynamicColors.lambda$surfaceContainerHighest$30(dynamicScheme);
            case 12:
                return dynamicScheme.primaryPalette;
            case 13:
                return MaterialDynamicColors.lambda$primaryFixed$104(dynamicScheme);
            case 14:
                return dynamicScheme.primaryPalette;
            case 15:
                return MaterialDynamicColors.lambda$primaryFixedDim$107(dynamicScheme);
            case 16:
                return dynamicScheme.tertiaryPalette;
            case 17:
                return MaterialDynamicColors.lambda$tertiaryContainer$86(dynamicScheme);
            case 18:
                return dynamicScheme.secondaryPalette;
            case 19:
                return MaterialDynamicColors.lambda$onSecondary$71(dynamicScheme);
            case 20:
                return dynamicScheme.secondaryPalette;
            case 21:
                return MaterialDynamicColors.lambda$secondary$68(dynamicScheme);
            case 22:
                return dynamicScheme.neutralPalette;
            case 23:
                return MaterialDynamicColors.lambda$surfaceContainerLowest$22(dynamicScheme);
            case 24:
                return dynamicScheme.neutralVariantPalette;
            case 25:
                return MaterialDynamicColors.lambda$outline$43(dynamicScheme);
            case 26:
                return dynamicScheme.primaryPalette;
            case 27:
                return MaterialDynamicColors.lambda$surfaceTint$51(dynamicScheme);
            case 28:
                return dynamicScheme.secondaryPalette;
            default:
                return MaterialDynamicColors.lambda$onSecondaryFixed$124(dynamicScheme);
        }
    }
}
