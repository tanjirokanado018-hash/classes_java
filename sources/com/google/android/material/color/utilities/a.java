package com.google.android.material.color.utilities;

import java.util.function.Function;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Function {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f98a;
    public final /* synthetic */ Object b;

    public /* synthetic */ a(int i2, Object obj) {
        this.f98a = i2;
        this.b = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.f98a) {
            case 0:
                return DynamicColor.lambda$fromArgb$0((TonalPalette) this.b, (DynamicScheme) obj);
            case 1:
                return DynamicColor.lambda$fromArgb$1((Hct) this.b, (DynamicScheme) obj);
            default:
                return ((TemperatureCache) this.b).lambda$getHctsByTemp$0((Hct) obj);
        }
    }
}
