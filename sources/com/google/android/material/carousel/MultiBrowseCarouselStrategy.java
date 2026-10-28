package com.google.android.material.carousel;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private int keylineCount = 0;
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {1, 0};

    public boolean ensureArrangementFitsItemCount(Arrangement arrangement, int i2) {
        int itemCount = arrangement.getItemCount() - i2;
        boolean z2 = itemCount > 0 && (arrangement.smallCount > 0 || arrangement.mediumCount > 1);
        while (itemCount > 0) {
            int i3 = arrangement.smallCount;
            if (i3 > 0) {
                arrangement.smallCount = i3 - 1;
            } else {
                int i4 = arrangement.mediumCount;
                if (i4 > 1) {
                    arrangement.mediumCount = i4 - 1;
                }
            }
            itemCount--;
        }
        return z2;
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    @NonNull
    public KeylineState onFirstChildMeasuredWithMargins(@NonNull Carousel carousel, @NonNull View view) {
        float containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        float measuredHeight = view.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            f2 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            measuredHeight = view.getMeasuredWidth();
        }
        float f3 = f2;
        float smallItemSizeMin = getSmallItemSizeMin() + f3;
        float fMax = Math.max(getSmallItemSizeMax() + f3, smallItemSizeMin);
        float fMin = Math.min(measuredHeight + f3, containerHeight);
        float fClamp = MathUtils.clamp((measuredHeight / 3.0f) + f3, smallItemSizeMin + f3, fMax + f3);
        float f4 = (fMin + fClamp) / 2.0f;
        int[] iArrDoubleCounts = SMALL_COUNTS;
        if (containerHeight < 2.0f * smallItemSizeMin) {
            iArrDoubleCounts = new int[]{0};
        }
        int[] iArrDoubleCounts2 = MEDIUM_COUNTS;
        if (carousel.getCarouselAlignment() == 1) {
            iArrDoubleCounts = CarouselStrategy.doubleCounts(iArrDoubleCounts);
            iArrDoubleCounts2 = CarouselStrategy.doubleCounts(iArrDoubleCounts2);
        }
        int[] iArr = iArrDoubleCounts;
        int[] iArr2 = iArrDoubleCounts2;
        int iMax = (int) Math.max(1.0d, Math.floor(((containerHeight - (CarouselStrategyHelper.maxValue(iArr2) * f4)) - (CarouselStrategyHelper.maxValue(iArr) * fMax)) / fMin));
        int iCeil = (int) Math.ceil(containerHeight / fMin);
        int i2 = (iCeil - iMax) + 1;
        int[] iArr3 = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr3[i3] = iCeil - i3;
        }
        Arrangement arrangementFindLowestCostArrangement = Arrangement.findLowestCostArrangement(containerHeight, fClamp, smallItemSizeMin, fMax, iArr, f4, iArr2, fMin, iArr3);
        this.keylineCount = arrangementFindLowestCostArrangement.getItemCount();
        if (ensureArrangementFitsItemCount(arrangementFindLowestCostArrangement, carousel.getItemCount())) {
            arrangementFindLowestCostArrangement = Arrangement.findLowestCostArrangement(containerHeight, fClamp, smallItemSizeMin, fMax, new int[]{arrangementFindLowestCostArrangement.smallCount}, f4, new int[]{arrangementFindLowestCostArrangement.mediumCount}, fMin, new int[]{arrangementFindLowestCostArrangement.largeCount});
        }
        return CarouselStrategyHelper.createKeylineState(view.getContext(), f3, containerHeight, arrangementFindLowestCostArrangement, carousel.getCarouselAlignment());
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    public boolean shouldRefreshKeylineState(Carousel carousel, int i2) {
        return (i2 < this.keylineCount && carousel.getItemCount() >= this.keylineCount) || (i2 >= this.keylineCount && carousel.getItemCount() < this.keylineCount);
    }
}
