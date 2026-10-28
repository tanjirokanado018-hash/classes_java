package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Scheme {
    private int background;
    private int error;
    private int errorContainer;
    private int inverseOnSurface;
    private int inversePrimary;
    private int inverseSurface;
    private int onBackground;
    private int onError;
    private int onErrorContainer;
    private int onPrimary;
    private int onPrimaryContainer;
    private int onSecondary;
    private int onSecondaryContainer;
    private int onSurface;
    private int onSurfaceVariant;
    private int onTertiary;
    private int onTertiaryContainer;
    private int outline;
    private int outlineVariant;
    private int primary;
    private int primaryContainer;
    private int scrim;
    private int secondary;
    private int secondaryContainer;
    private int shadow;
    private int surface;
    private int surfaceVariant;
    private int tertiary;
    private int tertiaryContainer;

    public Scheme() {
    }

    public Scheme(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21, int i22, int i23, int i24, int i25, int i26, int i27, int i28, int i29, int i30) {
        this.primary = i2;
        this.onPrimary = i3;
        this.primaryContainer = i4;
        this.onPrimaryContainer = i5;
        this.secondary = i6;
        this.onSecondary = i7;
        this.secondaryContainer = i8;
        this.onSecondaryContainer = i9;
        this.tertiary = i10;
        this.onTertiary = i11;
        this.tertiaryContainer = i12;
        this.onTertiaryContainer = i13;
        this.error = i14;
        this.onError = i15;
        this.errorContainer = i16;
        this.onErrorContainer = i17;
        this.background = i18;
        this.onBackground = i19;
        this.surface = i20;
        this.onSurface = i21;
        this.surfaceVariant = i22;
        this.onSurfaceVariant = i23;
        this.outline = i24;
        this.outlineVariant = i25;
        this.shadow = i26;
        this.scrim = i27;
        this.inverseSurface = i28;
        this.inverseOnSurface = i29;
        this.inversePrimary = i30;
    }

    public static Scheme dark(int i2) {
        return darkFromCorePalette(CorePalette.of(i2));
    }

    public static Scheme darkContent(int i2) {
        return darkFromCorePalette(CorePalette.contentOf(i2));
    }

    private static Scheme darkFromCorePalette(CorePalette corePalette) {
        return new Scheme().withPrimary(corePalette.a1.tone(80)).withOnPrimary(corePalette.a1.tone(20)).withPrimaryContainer(corePalette.a1.tone(30)).withOnPrimaryContainer(corePalette.a1.tone(90)).withSecondary(corePalette.a2.tone(80)).withOnSecondary(corePalette.a2.tone(20)).withSecondaryContainer(corePalette.a2.tone(30)).withOnSecondaryContainer(corePalette.a2.tone(90)).withTertiary(corePalette.a3.tone(80)).withOnTertiary(corePalette.a3.tone(20)).withTertiaryContainer(corePalette.a3.tone(30)).withOnTertiaryContainer(corePalette.a3.tone(90)).withError(corePalette.error.tone(80)).withOnError(corePalette.error.tone(20)).withErrorContainer(corePalette.error.tone(30)).withOnErrorContainer(corePalette.error.tone(80)).withBackground(corePalette.n1.tone(10)).withOnBackground(corePalette.n1.tone(90)).withSurface(corePalette.n1.tone(10)).withOnSurface(corePalette.n1.tone(90)).withSurfaceVariant(corePalette.n2.tone(30)).withOnSurfaceVariant(corePalette.n2.tone(80)).withOutline(corePalette.n2.tone(60)).withOutlineVariant(corePalette.n2.tone(30)).withShadow(corePalette.n1.tone(0)).withScrim(corePalette.n1.tone(0)).withInverseSurface(corePalette.n1.tone(90)).withInverseOnSurface(corePalette.n1.tone(20)).withInversePrimary(corePalette.a1.tone(40));
    }

    public static Scheme light(int i2) {
        return lightFromCorePalette(CorePalette.of(i2));
    }

    public static Scheme lightContent(int i2) {
        return lightFromCorePalette(CorePalette.contentOf(i2));
    }

    private static Scheme lightFromCorePalette(CorePalette corePalette) {
        return new Scheme().withPrimary(corePalette.a1.tone(40)).withOnPrimary(corePalette.a1.tone(100)).withPrimaryContainer(corePalette.a1.tone(90)).withOnPrimaryContainer(corePalette.a1.tone(10)).withSecondary(corePalette.a2.tone(40)).withOnSecondary(corePalette.a2.tone(100)).withSecondaryContainer(corePalette.a2.tone(90)).withOnSecondaryContainer(corePalette.a2.tone(10)).withTertiary(corePalette.a3.tone(40)).withOnTertiary(corePalette.a3.tone(100)).withTertiaryContainer(corePalette.a3.tone(90)).withOnTertiaryContainer(corePalette.a3.tone(10)).withError(corePalette.error.tone(40)).withOnError(corePalette.error.tone(100)).withErrorContainer(corePalette.error.tone(90)).withOnErrorContainer(corePalette.error.tone(10)).withBackground(corePalette.n1.tone(99)).withOnBackground(corePalette.n1.tone(10)).withSurface(corePalette.n1.tone(99)).withOnSurface(corePalette.n1.tone(10)).withSurfaceVariant(corePalette.n2.tone(90)).withOnSurfaceVariant(corePalette.n2.tone(30)).withOutline(corePalette.n2.tone(50)).withOutlineVariant(corePalette.n2.tone(80)).withShadow(corePalette.n1.tone(0)).withScrim(corePalette.n1.tone(0)).withInverseSurface(corePalette.n1.tone(20)).withInverseOnSurface(corePalette.n1.tone(95)).withInversePrimary(corePalette.a1.tone(80));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scheme) || !super.equals(obj)) {
            return false;
        }
        Scheme scheme = (Scheme) obj;
        return this.primary == scheme.primary && this.onPrimary == scheme.onPrimary && this.primaryContainer == scheme.primaryContainer && this.onPrimaryContainer == scheme.onPrimaryContainer && this.secondary == scheme.secondary && this.onSecondary == scheme.onSecondary && this.secondaryContainer == scheme.secondaryContainer && this.onSecondaryContainer == scheme.onSecondaryContainer && this.tertiary == scheme.tertiary && this.onTertiary == scheme.onTertiary && this.tertiaryContainer == scheme.tertiaryContainer && this.onTertiaryContainer == scheme.onTertiaryContainer && this.error == scheme.error && this.onError == scheme.onError && this.errorContainer == scheme.errorContainer && this.onErrorContainer == scheme.onErrorContainer && this.background == scheme.background && this.onBackground == scheme.onBackground && this.surface == scheme.surface && this.onSurface == scheme.onSurface && this.surfaceVariant == scheme.surfaceVariant && this.onSurfaceVariant == scheme.onSurfaceVariant && this.outline == scheme.outline && this.outlineVariant == scheme.outlineVariant && this.shadow == scheme.shadow && this.scrim == scheme.scrim && this.inverseSurface == scheme.inverseSurface && this.inverseOnSurface == scheme.inverseOnSurface && this.inversePrimary == scheme.inversePrimary;
    }

    public int getBackground() {
        return this.background;
    }

    public int getError() {
        return this.error;
    }

    public int getErrorContainer() {
        return this.errorContainer;
    }

    public int getInverseOnSurface() {
        return this.inverseOnSurface;
    }

    public int getInversePrimary() {
        return this.inversePrimary;
    }

    public int getInverseSurface() {
        return this.inverseSurface;
    }

    public int getOnBackground() {
        return this.onBackground;
    }

    public int getOnError() {
        return this.onError;
    }

    public int getOnErrorContainer() {
        return this.onErrorContainer;
    }

    public int getOnPrimary() {
        return this.onPrimary;
    }

    public int getOnPrimaryContainer() {
        return this.onPrimaryContainer;
    }

    public int getOnSecondary() {
        return this.onSecondary;
    }

    public int getOnSecondaryContainer() {
        return this.onSecondaryContainer;
    }

    public int getOnSurface() {
        return this.onSurface;
    }

    public int getOnSurfaceVariant() {
        return this.onSurfaceVariant;
    }

    public int getOnTertiary() {
        return this.onTertiary;
    }

    public int getOnTertiaryContainer() {
        return this.onTertiaryContainer;
    }

    public int getOutline() {
        return this.outline;
    }

    public int getOutlineVariant() {
        return this.outlineVariant;
    }

    public int getPrimary() {
        return this.primary;
    }

    public int getPrimaryContainer() {
        return this.primaryContainer;
    }

    public int getScrim() {
        return this.scrim;
    }

    public int getSecondary() {
        return this.secondary;
    }

    public int getSecondaryContainer() {
        return this.secondaryContainer;
    }

    public int getShadow() {
        return this.shadow;
    }

    public int getSurface() {
        return this.surface;
    }

    public int getSurfaceVariant() {
        return this.surfaceVariant;
    }

    public int getTertiary() {
        return this.tertiary;
    }

    public int getTertiaryContainer() {
        return this.tertiaryContainer;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((super.hashCode() * 31) + this.primary) * 31) + this.onPrimary) * 31) + this.primaryContainer) * 31) + this.onPrimaryContainer) * 31) + this.secondary) * 31) + this.onSecondary) * 31) + this.secondaryContainer) * 31) + this.onSecondaryContainer) * 31) + this.tertiary) * 31) + this.onTertiary) * 31) + this.tertiaryContainer) * 31) + this.onTertiaryContainer) * 31) + this.error) * 31) + this.onError) * 31) + this.errorContainer) * 31) + this.onErrorContainer) * 31) + this.background) * 31) + this.onBackground) * 31) + this.surface) * 31) + this.onSurface) * 31) + this.surfaceVariant) * 31) + this.onSurfaceVariant) * 31) + this.outline) * 31) + this.outlineVariant) * 31) + this.shadow) * 31) + this.scrim) * 31) + this.inverseSurface) * 31) + this.inverseOnSurface) * 31) + this.inversePrimary;
    }

    public void setBackground(int i2) {
        this.background = i2;
    }

    public void setError(int i2) {
        this.error = i2;
    }

    public void setErrorContainer(int i2) {
        this.errorContainer = i2;
    }

    public void setInverseOnSurface(int i2) {
        this.inverseOnSurface = i2;
    }

    public void setInversePrimary(int i2) {
        this.inversePrimary = i2;
    }

    public void setInverseSurface(int i2) {
        this.inverseSurface = i2;
    }

    public void setOnBackground(int i2) {
        this.onBackground = i2;
    }

    public void setOnError(int i2) {
        this.onError = i2;
    }

    public void setOnErrorContainer(int i2) {
        this.onErrorContainer = i2;
    }

    public void setOnPrimary(int i2) {
        this.onPrimary = i2;
    }

    public void setOnPrimaryContainer(int i2) {
        this.onPrimaryContainer = i2;
    }

    public void setOnSecondary(int i2) {
        this.onSecondary = i2;
    }

    public void setOnSecondaryContainer(int i2) {
        this.onSecondaryContainer = i2;
    }

    public void setOnSurface(int i2) {
        this.onSurface = i2;
    }

    public void setOnSurfaceVariant(int i2) {
        this.onSurfaceVariant = i2;
    }

    public void setOnTertiary(int i2) {
        this.onTertiary = i2;
    }

    public void setOnTertiaryContainer(int i2) {
        this.onTertiaryContainer = i2;
    }

    public void setOutline(int i2) {
        this.outline = i2;
    }

    public void setOutlineVariant(int i2) {
        this.outlineVariant = i2;
    }

    public void setPrimary(int i2) {
        this.primary = i2;
    }

    public void setPrimaryContainer(int i2) {
        this.primaryContainer = i2;
    }

    public void setScrim(int i2) {
        this.scrim = i2;
    }

    public void setSecondary(int i2) {
        this.secondary = i2;
    }

    public void setSecondaryContainer(int i2) {
        this.secondaryContainer = i2;
    }

    public void setShadow(int i2) {
        this.shadow = i2;
    }

    public void setSurface(int i2) {
        this.surface = i2;
    }

    public void setSurfaceVariant(int i2) {
        this.surfaceVariant = i2;
    }

    public void setTertiary(int i2) {
        this.tertiary = i2;
    }

    public void setTertiaryContainer(int i2) {
        this.tertiaryContainer = i2;
    }

    public String toString() {
        return "Scheme{primary=" + this.primary + ", onPrimary=" + this.onPrimary + ", primaryContainer=" + this.primaryContainer + ", onPrimaryContainer=" + this.onPrimaryContainer + ", secondary=" + this.secondary + ", onSecondary=" + this.onSecondary + ", secondaryContainer=" + this.secondaryContainer + ", onSecondaryContainer=" + this.onSecondaryContainer + ", tertiary=" + this.tertiary + ", onTertiary=" + this.onTertiary + ", tertiaryContainer=" + this.tertiaryContainer + ", onTertiaryContainer=" + this.onTertiaryContainer + ", error=" + this.error + ", onError=" + this.onError + ", errorContainer=" + this.errorContainer + ", onErrorContainer=" + this.onErrorContainer + ", background=" + this.background + ", onBackground=" + this.onBackground + ", surface=" + this.surface + ", onSurface=" + this.onSurface + ", surfaceVariant=" + this.surfaceVariant + ", onSurfaceVariant=" + this.onSurfaceVariant + ", outline=" + this.outline + ", outlineVariant=" + this.outlineVariant + ", shadow=" + this.shadow + ", scrim=" + this.scrim + ", inverseSurface=" + this.inverseSurface + ", inverseOnSurface=" + this.inverseOnSurface + ", inversePrimary=" + this.inversePrimary + '}';
    }

    public Scheme withBackground(int i2) {
        this.background = i2;
        return this;
    }

    public Scheme withError(int i2) {
        this.error = i2;
        return this;
    }

    public Scheme withErrorContainer(int i2) {
        this.errorContainer = i2;
        return this;
    }

    public Scheme withInverseOnSurface(int i2) {
        this.inverseOnSurface = i2;
        return this;
    }

    public Scheme withInversePrimary(int i2) {
        this.inversePrimary = i2;
        return this;
    }

    public Scheme withInverseSurface(int i2) {
        this.inverseSurface = i2;
        return this;
    }

    public Scheme withOnBackground(int i2) {
        this.onBackground = i2;
        return this;
    }

    public Scheme withOnError(int i2) {
        this.onError = i2;
        return this;
    }

    public Scheme withOnErrorContainer(int i2) {
        this.onErrorContainer = i2;
        return this;
    }

    public Scheme withOnPrimary(int i2) {
        this.onPrimary = i2;
        return this;
    }

    public Scheme withOnPrimaryContainer(int i2) {
        this.onPrimaryContainer = i2;
        return this;
    }

    public Scheme withOnSecondary(int i2) {
        this.onSecondary = i2;
        return this;
    }

    public Scheme withOnSecondaryContainer(int i2) {
        this.onSecondaryContainer = i2;
        return this;
    }

    public Scheme withOnSurface(int i2) {
        this.onSurface = i2;
        return this;
    }

    public Scheme withOnSurfaceVariant(int i2) {
        this.onSurfaceVariant = i2;
        return this;
    }

    public Scheme withOnTertiary(int i2) {
        this.onTertiary = i2;
        return this;
    }

    public Scheme withOnTertiaryContainer(int i2) {
        this.onTertiaryContainer = i2;
        return this;
    }

    public Scheme withOutline(int i2) {
        this.outline = i2;
        return this;
    }

    public Scheme withOutlineVariant(int i2) {
        this.outlineVariant = i2;
        return this;
    }

    public Scheme withPrimary(int i2) {
        this.primary = i2;
        return this;
    }

    public Scheme withPrimaryContainer(int i2) {
        this.primaryContainer = i2;
        return this;
    }

    public Scheme withScrim(int i2) {
        this.scrim = i2;
        return this;
    }

    public Scheme withSecondary(int i2) {
        this.secondary = i2;
        return this;
    }

    public Scheme withSecondaryContainer(int i2) {
        this.secondaryContainer = i2;
        return this;
    }

    public Scheme withShadow(int i2) {
        this.shadow = i2;
        return this;
    }

    public Scheme withSurface(int i2) {
        this.surface = i2;
        return this;
    }

    public Scheme withSurfaceVariant(int i2) {
        this.surfaceVariant = i2;
        return this;
    }

    public Scheme withTertiary(int i2) {
        this.tertiary = i2;
        return this;
    }

    public Scheme withTertiaryContainer(int i2) {
        this.tertiaryContainer = i2;
        return this;
    }
}
