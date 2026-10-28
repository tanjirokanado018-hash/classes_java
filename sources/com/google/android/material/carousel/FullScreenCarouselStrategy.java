package com.google.android.material.carousel;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class FullScreenCarouselStrategy extends CarouselStrategy {
    @Override // com.google.android.material.carousel.CarouselStrategy
    @NonNull
    public KeylineState onFirstChildMeasuredWithMargins(@NonNull Carousel carousel, @NonNull View view) {
        float containerHeight;
        int i2;
        int i3;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
            i2 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            i3 = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        } else {
            containerHeight = carousel.getContainerHeight();
            i2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            i3 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        float f2 = i2 + i3;
        return CarouselStrategyHelper.createLeftAlignedKeylineState(view.getContext(), f2, containerHeight, new Arrangement(0, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, Math.min(containerHeight + f2, containerHeight), 1, containerHeight));
    }
}
