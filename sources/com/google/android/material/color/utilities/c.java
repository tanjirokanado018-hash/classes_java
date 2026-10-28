package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class c implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f100a;
    public final /* synthetic */ MaterialDynamicColors b;

    public /* synthetic */ c(MaterialDynamicColors materialDynamicColors, int i2) {
        this.f100a = i2;
        this.b = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.f100a) {
            case 0:
                return this.b.lambda$onTertiary$84((DynamicScheme) obj);
            case 1:
                return this.b.highestSurface((DynamicScheme) obj);
            case 2:
                return this.b.lambda$onTertiaryFixedVariant$143((DynamicScheme) obj);
            case 3:
                return this.b.lambda$onTertiaryFixedVariant$144((DynamicScheme) obj);
            case 4:
                return this.b.highestSurface((DynamicScheme) obj);
            case 5:
                return this.b.lambda$secondaryContainer$75((DynamicScheme) obj);
            case 6:
                return this.b.lambda$onTertiaryFixed$139((DynamicScheme) obj);
            case 7:
                return this.b.lambda$onTertiaryFixed$140((DynamicScheme) obj);
            case 8:
                return this.b.highestSurface((DynamicScheme) obj);
            case 9:
                return this.b.lambda$primaryContainer$60((DynamicScheme) obj);
            case 10:
                return this.b.lambda$onBackground$14((DynamicScheme) obj);
            case 11:
                return this.b.highestSurface((DynamicScheme) obj);
            case 12:
                return this.b.lambda$onPrimary$57((DynamicScheme) obj);
            case 13:
                return this.b.lambda$inversePrimary$66((DynamicScheme) obj);
            case 14:
                return this.b.highestSurface((DynamicScheme) obj);
            case 15:
                return this.b.lambda$secondaryFixed$119((DynamicScheme) obj);
            case 16:
                return this.b.lambda$onPrimaryFixedVariant$115((DynamicScheme) obj);
            case 17:
                return this.b.lambda$onPrimaryFixedVariant$116((DynamicScheme) obj);
            case 18:
                return this.b.highestSurface((DynamicScheme) obj);
            case 19:
                return this.b.lambda$tertiaryFixedDim$136((DynamicScheme) obj);
            case 20:
                return this.b.lambda$onError$96((DynamicScheme) obj);
            case 21:
                return this.b.lambda$onPrimaryContainer$62((DynamicScheme) obj);
            case 22:
                return this.b.lambda$onPrimaryContainer$63((DynamicScheme) obj);
            case 23:
                return this.b.highestSurface((DynamicScheme) obj);
            case 24:
                return this.b.lambda$secondaryFixedDim$122((DynamicScheme) obj);
            case 25:
                return this.b.highestSurface((DynamicScheme) obj);
            case 26:
                return this.b.lambda$primaryFixed$105((DynamicScheme) obj);
            case 27:
                return this.b.highestSurface((DynamicScheme) obj);
            case 28:
                return this.b.lambda$primaryFixedDim$108((DynamicScheme) obj);
            default:
                return this.b.highestSurface((DynamicScheme) obj);
        }
    }
}
