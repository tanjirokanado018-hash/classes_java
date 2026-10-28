package com.google.android.material.carousel;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class CarouselStrategyHelper {
    private CarouselStrategyHelper() {
    }

    public static float addEnd(float f2, float f3, int i2) {
        return (Math.max(0, i2 - 1) * f3) + f2;
    }

    public static float addStart(float f2, float f3, int i2) {
        return i2 > 0 ? (f3 / 2.0f) + f2 : f2;
    }

    public static KeylineState createCenterAlignedKeylineState(@NonNull Context context, float f2, float f3, @NonNull Arrangement arrangement) {
        float f4;
        float fMin = Math.min(getExtraSmallSize(context) + f2, arrangement.largeSize);
        float f5 = fMin / 2.0f;
        float f6 = 0.0f - f5;
        float fAddStart = addStart(0.0f, arrangement.smallSize, arrangement.smallCount);
        float fUpdateCurPosition = updateCurPosition(0.0f, addEnd(fAddStart, arrangement.smallSize, (int) Math.floor(arrangement.smallCount / 2.0f)), arrangement.smallSize, arrangement.smallCount);
        float fAddStart2 = addStart(fUpdateCurPosition, arrangement.mediumSize, arrangement.mediumCount);
        float fUpdateCurPosition2 = updateCurPosition(fUpdateCurPosition, addEnd(fAddStart2, arrangement.mediumSize, (int) Math.floor(arrangement.mediumCount / 2.0f)), arrangement.mediumSize, arrangement.mediumCount);
        float fAddStart3 = addStart(fUpdateCurPosition2, arrangement.largeSize, arrangement.largeCount);
        float fUpdateCurPosition3 = updateCurPosition(fUpdateCurPosition2, addEnd(fAddStart3, arrangement.largeSize, arrangement.largeCount), arrangement.largeSize, arrangement.largeCount);
        float fAddStart4 = addStart(fUpdateCurPosition3, arrangement.mediumSize, arrangement.mediumCount);
        float fAddStart5 = addStart(updateCurPosition(fUpdateCurPosition3, addEnd(fAddStart4, arrangement.mediumSize, (int) Math.ceil(arrangement.mediumCount / 2.0f)), arrangement.mediumSize, arrangement.mediumCount), arrangement.smallSize, arrangement.smallCount);
        float f7 = f5 + f3;
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(fMin, arrangement.largeSize, f2);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(arrangement.smallSize, arrangement.largeSize, f2);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(arrangement.mediumSize, arrangement.largeSize, f2);
        KeylineState.Builder builderAddAnchorKeyline = new KeylineState.Builder(arrangement.largeSize, f3).addAnchorKeyline(f6, childMaskPercentage, fMin);
        int i2 = arrangement.smallCount;
        if (i2 > 0) {
            builderAddAnchorKeyline.addKeylineRange(fAddStart, childMaskPercentage2, arrangement.smallSize, (int) Math.floor(i2 / 2.0f));
        }
        int i3 = arrangement.mediumCount;
        if (i3 > 0) {
            builderAddAnchorKeyline.addKeylineRange(fAddStart2, childMaskPercentage3, arrangement.mediumSize, (int) Math.floor(i3 / 2.0f));
        }
        builderAddAnchorKeyline.addKeylineRange(fAddStart3, 0.0f, arrangement.largeSize, arrangement.largeCount, true);
        int i4 = arrangement.mediumCount;
        if (i4 > 0) {
            f4 = 2.0f;
            builderAddAnchorKeyline.addKeylineRange(fAddStart4, childMaskPercentage3, arrangement.mediumSize, (int) Math.ceil(i4 / 2.0f));
        } else {
            f4 = 2.0f;
        }
        int i5 = arrangement.smallCount;
        if (i5 > 0) {
            builderAddAnchorKeyline.addKeylineRange(fAddStart5, childMaskPercentage2, arrangement.smallSize, (int) Math.ceil(i5 / f4));
        }
        builderAddAnchorKeyline.addAnchorKeyline(f7, childMaskPercentage, fMin);
        return builderAddAnchorKeyline.build();
    }

    public static KeylineState createKeylineState(@NonNull Context context, float f2, float f3, @NonNull Arrangement arrangement, int i2) {
        return i2 == 1 ? createCenterAlignedKeylineState(context, f2, f3, arrangement) : createLeftAlignedKeylineState(context, f2, f3, arrangement);
    }

    public static KeylineState createLeftAlignedKeylineState(@NonNull Context context, float f2, float f3, @NonNull Arrangement arrangement) {
        float fMin = Math.min(getExtraSmallSize(context) + f2, arrangement.largeSize);
        float f4 = fMin / 2.0f;
        float f5 = 0.0f - f4;
        float fAddStart = addStart(0.0f, arrangement.largeSize, arrangement.largeCount);
        float fUpdateCurPosition = updateCurPosition(0.0f, addEnd(fAddStart, arrangement.largeSize, arrangement.largeCount), arrangement.largeSize, arrangement.largeCount);
        float fAddStart2 = addStart(fUpdateCurPosition, arrangement.mediumSize, arrangement.mediumCount);
        float fAddStart3 = addStart(updateCurPosition(fUpdateCurPosition, fAddStart2, arrangement.mediumSize, arrangement.mediumCount), arrangement.smallSize, arrangement.smallCount);
        float f6 = f4 + f3;
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(fMin, arrangement.largeSize, f2);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(arrangement.smallSize, arrangement.largeSize, f2);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(arrangement.mediumSize, arrangement.largeSize, f2);
        KeylineState.Builder builderAddKeylineRange = new KeylineState.Builder(arrangement.largeSize, f3).addAnchorKeyline(f5, childMaskPercentage, fMin).addKeylineRange(fAddStart, 0.0f, arrangement.largeSize, arrangement.largeCount, true);
        if (arrangement.mediumCount > 0) {
            builderAddKeylineRange.addKeyline(fAddStart2, childMaskPercentage3, arrangement.mediumSize);
        }
        int i2 = arrangement.smallCount;
        if (i2 > 0) {
            builderAddKeylineRange.addKeylineRange(fAddStart3, childMaskPercentage2, arrangement.smallSize, i2);
        }
        builderAddKeylineRange.addAnchorKeyline(f6, childMaskPercentage, fMin);
        return builderAddKeylineRange.build();
    }

    public static float getExtraSmallSize(@NonNull Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_gone_size);
    }

    public static float getSmallSizeMax(@NonNull Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
    }

    public static float getSmallSizeMin(@NonNull Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
    }

    public static int maxValue(int[] iArr) {
        int i2 = Integer.MIN_VALUE;
        for (int i3 : iArr) {
            if (i3 > i2) {
                i2 = i3;
            }
        }
        return i2;
    }

    public static float updateCurPosition(float f2, float f3, float f4, int i2) {
        return i2 > 0 ? (f4 / 2.0f) + f3 : f2;
    }
}
