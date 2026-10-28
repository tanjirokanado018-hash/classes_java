package com.google.android.material.transition;

import android.graphics.RectF;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class FitModeEvaluators {
    private static final FitModeEvaluator WIDTH = new FitModeEvaluator() { // from class: com.google.android.material.transition.FitModeEvaluators.1
        @Override // com.google.android.material.transition.FitModeEvaluator
        public void applyMask(RectF rectF, float f2, FitModeResult fitModeResult) {
            rectF.bottom -= Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight) * f2;
        }

        @Override // com.google.android.material.transition.FitModeEvaluator
        public FitModeResult evaluate(float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            float fLerp = TransitionUtils.lerp(f5, f7, f3, f4, f2, true);
            float f9 = fLerp / f5;
            float f10 = fLerp / f7;
            return new FitModeResult(f9, f10, fLerp, f6 * f9, fLerp, f8 * f10);
        }

        @Override // com.google.android.material.transition.FitModeEvaluator
        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartHeight > fitModeResult.currentEndHeight;
        }
    };
    private static final FitModeEvaluator HEIGHT = new FitModeEvaluator() { // from class: com.google.android.material.transition.FitModeEvaluators.2
        @Override // com.google.android.material.transition.FitModeEvaluator
        public void applyMask(RectF rectF, float f2, FitModeResult fitModeResult) {
            float fAbs = (Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth) / 2.0f) * f2;
            rectF.left += fAbs;
            rectF.right -= fAbs;
        }

        @Override // com.google.android.material.transition.FitModeEvaluator
        public FitModeResult evaluate(float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            float fLerp = TransitionUtils.lerp(f6, f8, f3, f4, f2, true);
            float f9 = fLerp / f6;
            float f10 = fLerp / f8;
            return new FitModeResult(f9, f10, f5 * f9, fLerp, f7 * f10, fLerp);
        }

        @Override // com.google.android.material.transition.FitModeEvaluator
        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartWidth > fitModeResult.currentEndWidth;
        }
    };

    private FitModeEvaluators() {
    }

    public static FitModeEvaluator get(int i2, boolean z2, RectF rectF, RectF rectF2) {
        if (i2 == 0) {
            return shouldAutoFitToWidth(z2, rectF, rectF2) ? WIDTH : HEIGHT;
        }
        if (i2 == 1) {
            return WIDTH;
        }
        if (i2 == 2) {
            return HEIGHT;
        }
        throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("Invalid fit mode: ", i2));
    }

    private static boolean shouldAutoFitToWidth(boolean z2, RectF rectF, RectF rectF2) {
        float fWidth = rectF.width();
        float fHeight = rectF.height();
        float fWidth2 = rectF2.width();
        float fHeight2 = rectF2.height();
        float f2 = (fHeight2 * fWidth) / fWidth2;
        float f3 = (fWidth2 * fHeight) / fWidth;
        if (z2) {
            if (f2 < fHeight) {
                return false;
            }
        } else if (f3 < fHeight2) {
            return false;
        }
        return true;
    }
}
