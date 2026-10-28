package com.google.android.material.sidesheet;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class SheetUtils {
    private SheetUtils() {
    }

    public static boolean isSwipeMostlyHorizontal(float f2, float f3) {
        return Math.abs(f2) > Math.abs(f3);
    }
}
