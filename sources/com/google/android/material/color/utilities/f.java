package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class f implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f103a;
    public final /* synthetic */ MaterialDynamicColors b;

    public /* synthetic */ f(MaterialDynamicColors materialDynamicColors, int i2) {
        this.f103a = i2;
        this.b = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.f103a) {
            case 0:
                return this.b.lambda$tertiaryContainer$87((DynamicScheme) obj);
            case 1:
                return this.b.lambda$onSecondary$72((DynamicScheme) obj);
            case 2:
                return this.b.highestSurface((DynamicScheme) obj);
            case 3:
                return this.b.lambda$secondary$69((DynamicScheme) obj);
            case 4:
                return this.b.highestSurface((DynamicScheme) obj);
            case 5:
                return this.b.lambda$onSecondaryFixed$125((DynamicScheme) obj);
            case 6:
                return this.b.lambda$onSecondaryFixed$126((DynamicScheme) obj);
            case 7:
                return this.b.lambda$inverseOnSurface$41((DynamicScheme) obj);
            case 8:
                return this.b.lambda$onTertiaryContainer$89((DynamicScheme) obj);
            case 9:
                return this.b.lambda$onTertiaryContainer$90((DynamicScheme) obj);
            case 10:
                return this.b.lambda$onSecondaryContainer$77((DynamicScheme) obj);
            case 11:
                return this.b.lambda$onSecondaryContainer$78((DynamicScheme) obj);
            case 12:
                return this.b.lambda$onPrimaryFixed$111((DynamicScheme) obj);
            case 13:
                return this.b.lambda$onPrimaryFixed$112((DynamicScheme) obj);
            case 14:
                return this.b.highestSurface((DynamicScheme) obj);
            case 15:
                return this.b.lambda$error$93((DynamicScheme) obj);
            case 16:
                return this.b.highestSurface((DynamicScheme) obj);
            case 17:
                return this.b.lambda$onErrorContainer$102((DynamicScheme) obj);
            case 18:
                return this.b.highestSurface((DynamicScheme) obj);
            case 19:
                return this.b.lambda$errorContainer$99((DynamicScheme) obj);
            case 20:
                return this.b.highestSurface((DynamicScheme) obj);
            case 21:
                return this.b.lambda$tertiaryFixed$133((DynamicScheme) obj);
            case 22:
                return this.b.highestSurface((DynamicScheme) obj);
            case 23:
                return this.b.lambda$primary$54((DynamicScheme) obj);
            case 24:
                return this.b.lambda$onSecondaryFixedVariant$129((DynamicScheme) obj);
            case 25:
                return this.b.lambda$onSecondaryFixedVariant$130((DynamicScheme) obj);
            case 26:
                return this.b.highestSurface((DynamicScheme) obj);
            default:
                return this.b.lambda$tertiary$81((DynamicScheme) obj);
        }
    }
}
