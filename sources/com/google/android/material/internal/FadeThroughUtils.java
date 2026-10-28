package com.google.android.material.internal;

import androidx.annotation.FloatRange;
import com.google.android.material.color.utilities.Contrast;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class FadeThroughUtils {
    static final float THRESHOLD_ALPHA = 0.5f;

    private FadeThroughUtils() {
    }

    public static void calculateFadeOutAndInAlphas(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, float[] fArr) {
        if (f2 <= 0.5f) {
            fArr[0] = 1.0f - (f2 * 2.0f);
            fArr[1] = 0.0f;
        } else {
            fArr[0] = 0.0f;
            fArr[1] = (f2 * 2.0f) - 1.0f;
        }
    }
}
