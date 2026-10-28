package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class UncontainedCarouselStrategy extends CarouselStrategy {
    private static final float MEDIUM_LARGE_ITEM_PERCENTAGE_THRESHOLD = 0.85f;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UncontainedCarouselStrategy() {
    }

    private float calculateMediumChildSize(float f2, float f3, float f4) {
        float fMax = Math.max(1.5f * f4, f2);
        float f5 = MEDIUM_LARGE_ITEM_PERCENTAGE_THRESHOLD * f3;
        if (fMax > f5) {
            fMax = Math.max(f5, f4 * 1.2f);
        }
        return Math.min(f3, fMax);
    }

    private KeylineState createCenterAlignedKeylineState(float f2, float f3, float f4, int i2, float f5, float f6, float f7) {
        float fMin = Math.min(f6, f4);
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(fMin, f4, f3);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(f5, f4, f3);
        float f8 = f5 / 2.0f;
        float f9 = (f7 + 0.0f) - f8;
        float f10 = f9 + f8;
        float f11 = fMin / 2.0f;
        float f12 = (i2 * f4) + f10;
        KeylineState.Builder builderAddKeylineRange = new KeylineState.Builder(f4, f2).addAnchorKeyline((f9 - f8) - f11, childMaskPercentage, fMin).addKeyline(f9, childMaskPercentage2, f5, false).addKeylineRange((f4 / 2.0f) + f10, 0.0f, f4, i2, true);
        builderAddKeylineRange.addKeyline(f8 + f12, childMaskPercentage2, f5, false);
        builderAddKeylineRange.addAnchorKeyline(f12 + f5 + f11, childMaskPercentage, fMin);
        return builderAddKeylineRange.build();
    }

    private KeylineState createLeftAlignedKeylineState(Context context, float f2, float f3, float f4, int i2, float f5, int i3, float f6) {
        float fMin = Math.min(f6, f4);
        float fMax = Math.max(fMin, 0.5f * f5);
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(fMax, f4, f2);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(fMin, f4, f2);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(f5, f4, f2);
        float f7 = (i2 * f4) + 0.0f;
        KeylineState.Builder builderAddKeylineRange = new KeylineState.Builder(f4, f3).addAnchorKeyline(0.0f - (fMax / 2.0f), childMaskPercentage, fMax).addKeylineRange(f4 / 2.0f, 0.0f, f4, i2, true);
        if (i3 > 0) {
            float f8 = (f5 / 2.0f) + f7;
            f7 += f5;
            builderAddKeylineRange.addKeyline(f8, childMaskPercentage3, f5, false);
        }
        builderAddKeylineRange.addAnchorKeyline((CarouselStrategyHelper.getExtraSmallSize(context) / 2.0f) + f7, childMaskPercentage2, fMin);
        return builderAddKeylineRange.build();
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    public boolean isContained() {
        return false;
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    @NonNull
    public KeylineState onFirstChildMeasuredWithMargins(@NonNull Carousel carousel, @NonNull View view) {
        float f2;
        float containerWidth = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f3 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        float measuredHeight = view.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            float f4 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            measuredHeight = view.getMeasuredWidth();
            f2 = f4;
        } else {
            f2 = f3;
        }
        float f5 = measuredHeight + f2;
        float extraSmallSize = CarouselStrategyHelper.getExtraSmallSize(view.getContext()) + f2;
        float extraSmallSize2 = CarouselStrategyHelper.getExtraSmallSize(view.getContext()) + f2;
        int iMax = Math.max(1, (int) Math.floor(containerWidth / f5));
        float f6 = containerWidth - (iMax * f5);
        if (carousel.getCarouselAlignment() == 1) {
            float f7 = f6 / 2.0f;
            return createCenterAlignedKeylineState(containerWidth, f2, f5, iMax, Math.max(Math.min(3.0f * f7, f5), getSmallItemSizeMin() + f2), extraSmallSize2, f7);
        }
        return createLeftAlignedKeylineState(view.getContext(), f2, containerWidth, f5, iMax, calculateMediumChildSize(extraSmallSize, f5, f6), f6 > 0.0f ? 1 : 0, extraSmallSize2);
    }
}
