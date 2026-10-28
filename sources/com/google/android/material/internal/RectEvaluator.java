package com.google.android.material.internal;

import android.animation.TypeEvaluator;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class RectEvaluator implements TypeEvaluator<Rect> {
    private final Rect rect;

    public RectEvaluator(@NonNull Rect rect) {
        this.rect = rect;
    }

    @Override // android.animation.TypeEvaluator
    public Rect evaluate(float f2, @NonNull Rect rect, @NonNull Rect rect2) {
        int i2 = rect.left;
        int i3 = i2 + ((int) ((rect2.left - i2) * f2));
        int i4 = rect.top;
        int i5 = i4 + ((int) ((rect2.top - i4) * f2));
        int i6 = rect.right;
        int i7 = i6 + ((int) ((rect2.right - i6) * f2));
        int i8 = rect.bottom;
        this.rect.set(i3, i5, i7, i8 + ((int) ((rect2.bottom - i8) * f2)));
        return this.rect;
    }
}
