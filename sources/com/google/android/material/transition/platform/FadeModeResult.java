package com.google.android.material.transition.platform;

import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
class FadeModeResult {
    final int endAlpha;
    final boolean endOnTop;
    final int startAlpha;

    private FadeModeResult(int i2, int i3, boolean z2) {
        this.startAlpha = i2;
        this.endAlpha = i3;
        this.endOnTop = z2;
    }

    public static FadeModeResult endOnTop(int i2, int i3) {
        return new FadeModeResult(i2, i3, true);
    }

    public static FadeModeResult startOnTop(int i2, int i3) {
        return new FadeModeResult(i2, i3, false);
    }
}
