package com.google.android.material.color;

import androidx.annotation.ColorInt;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ColorRoles {
    private final int accent;
    private final int accentContainer;
    private final int onAccent;
    private final int onAccentContainer;

    public ColorRoles(@ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5) {
        this.accent = i2;
        this.onAccent = i3;
        this.accentContainer = i4;
        this.onAccentContainer = i5;
    }

    @ColorInt
    public int getAccent() {
        return this.accent;
    }

    @ColorInt
    public int getAccentContainer() {
        return this.accentContainer;
    }

    @ColorInt
    public int getOnAccent() {
        return this.onAccent;
    }

    @ColorInt
    public int getOnAccentContainer() {
        return this.onAccentContainer;
    }
}
