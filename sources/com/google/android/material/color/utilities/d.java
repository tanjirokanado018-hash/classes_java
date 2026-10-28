package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class d implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f101a;

    public /* synthetic */ d(int i2) {
        this.f101a = i2;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.f101a) {
            case 0:
                return dynamicScheme.neutralPalette;
            case 1:
                return MaterialDynamicColors.lambda$controlHighlight$150(dynamicScheme);
            case 2:
                return MaterialDynamicColors.lambda$controlHighlight$151(dynamicScheme);
            case 3:
                return dynamicScheme.primaryPalette;
            case 4:
                return MaterialDynamicColors.lambda$primaryContainer$59(dynamicScheme);
            case 5:
                return dynamicScheme.neutralPalette;
            case 6:
                return MaterialDynamicColors.lambda$background$11(dynamicScheme);
            case 7:
                return dynamicScheme.neutralPalette;
            case 8:
                return MaterialDynamicColors.lambda$surfaceContainerHigh$28(dynamicScheme);
            case 9:
                return dynamicScheme.neutralPalette;
            case 10:
                return MaterialDynamicColors.lambda$onBackground$13(dynamicScheme);
            case 11:
                return dynamicScheme.neutralVariantPalette;
            case 12:
                return MaterialDynamicColors.lambda$outlineVariant$45(dynamicScheme);
            case 13:
                return dynamicScheme.neutralPalette;
            case 14:
                return MaterialDynamicColors.lambda$surfaceBright$20(dynamicScheme);
            case 15:
                return dynamicScheme.primaryPalette;
            case 16:
                return MaterialDynamicColors.lambda$onPrimary$56(dynamicScheme);
            case 17:
                return dynamicScheme.primaryPalette;
            case 18:
                return MaterialDynamicColors.lambda$inversePrimary$65(dynamicScheme);
            case 19:
                return dynamicScheme.secondaryPalette;
            case 20:
                return MaterialDynamicColors.lambda$secondaryFixed$118(dynamicScheme);
            case 21:
                return dynamicScheme.primaryPalette;
            case 22:
                return MaterialDynamicColors.lambda$onPrimaryFixedVariant$114(dynamicScheme);
            case 23:
                return dynamicScheme.tertiaryPalette;
            case 24:
                return MaterialDynamicColors.lambda$tertiaryFixedDim$135(dynamicScheme);
            case 25:
                return dynamicScheme.errorPalette;
            case 26:
                return MaterialDynamicColors.lambda$onError$95(dynamicScheme);
            case 27:
                return dynamicScheme.neutralPalette;
            case 28:
                return MaterialDynamicColors.lambda$textPrimaryInverseDisableOnly$157(dynamicScheme);
            default:
                return dynamicScheme.primaryPalette;
        }
    }
}
