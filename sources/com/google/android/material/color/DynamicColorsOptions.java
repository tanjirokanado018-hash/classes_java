package com.google.android.material.color;

import android.app.Activity;
import android.graphics.Bitmap;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.material.color.utilities.QuantizerCelebi;
import com.google.android.material.color.utilities.Score;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DynamicColorsOptions {
    private static final DynamicColors.Precondition ALWAYS_ALLOW = new DynamicColors.Precondition() { // from class: com.google.android.material.color.DynamicColorsOptions.1
        @Override // com.google.android.material.color.DynamicColors.Precondition
        public boolean shouldApplyDynamicColors(@NonNull Activity activity, int i2) {
            return true;
        }
    };
    private static final DynamicColors.OnAppliedCallback NO_OP_CALLBACK = new DynamicColors.OnAppliedCallback() { // from class: com.google.android.material.color.DynamicColorsOptions.2
        @Override // com.google.android.material.color.DynamicColors.OnAppliedCallback
        public void onApplied(@NonNull Activity activity) {
        }
    };

    @Nullable
    private Integer contentBasedSeedColor;

    @NonNull
    private final DynamicColors.OnAppliedCallback onAppliedCallback;

    @NonNull
    private final DynamicColors.Precondition precondition;

    @StyleRes
    private final int themeOverlay;

    public static class Builder {

        @Nullable
        private Bitmap contentBasedSourceBitmap;

        @Nullable
        private Integer contentBasedSourceColor;

        @StyleRes
        private int themeOverlay;

        @NonNull
        private DynamicColors.Precondition precondition = DynamicColorsOptions.ALWAYS_ALLOW;

        @NonNull
        private DynamicColors.OnAppliedCallback onAppliedCallback = DynamicColorsOptions.NO_OP_CALLBACK;

        @NonNull
        public DynamicColorsOptions build() {
            return new DynamicColorsOptions(this);
        }

        @NonNull
        public Builder setContentBasedSource(@ColorInt int i2) {
            this.contentBasedSourceBitmap = null;
            this.contentBasedSourceColor = Integer.valueOf(i2);
            return this;
        }

        @NonNull
        public Builder setOnAppliedCallback(@NonNull DynamicColors.OnAppliedCallback onAppliedCallback) {
            this.onAppliedCallback = onAppliedCallback;
            return this;
        }

        @NonNull
        public Builder setPrecondition(@NonNull DynamicColors.Precondition precondition) {
            this.precondition = precondition;
            return this;
        }

        @NonNull
        public Builder setThemeOverlay(@StyleRes int i2) {
            this.themeOverlay = i2;
            return this;
        }

        @NonNull
        public Builder setContentBasedSource(@NonNull Bitmap bitmap) {
            this.contentBasedSourceBitmap = bitmap;
            this.contentBasedSourceColor = null;
            return this;
        }
    }

    private DynamicColorsOptions(Builder builder) {
        Integer numValueOf;
        this.themeOverlay = builder.themeOverlay;
        this.precondition = builder.precondition;
        this.onAppliedCallback = builder.onAppliedCallback;
        if (builder.contentBasedSourceColor != null) {
            numValueOf = builder.contentBasedSourceColor;
        } else if (builder.contentBasedSourceBitmap == null) {
            return;
        } else {
            numValueOf = Integer.valueOf(extractSeedColorFromImage(builder.contentBasedSourceBitmap));
        }
        this.contentBasedSeedColor = numValueOf;
    }

    private static int extractSeedColorFromImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Score.score(QuantizerCelebi.quantize(iArr, 128)).get(0).intValue();
    }

    @Nullable
    public Integer getContentBasedSeedColor() {
        return this.contentBasedSeedColor;
    }

    @NonNull
    public DynamicColors.OnAppliedCallback getOnAppliedCallback() {
        return this.onAppliedCallback;
    }

    @NonNull
    public DynamicColors.Precondition getPrecondition() {
        return this.precondition;
    }

    @StyleRes
    public int getThemeOverlay() {
        return this.themeOverlay;
    }
}
