package com.google.android.material.color;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ColorContrastOptions {

    @StyleRes
    private final int highContrastThemeOverlayResourceId;

    @StyleRes
    private final int mediumContrastThemeOverlayResourceId;

    public static class Builder {

        @StyleRes
        private int highContrastThemeOverlayResourceId;

        @StyleRes
        private int mediumContrastThemeOverlayResourceId;

        @NonNull
        public ColorContrastOptions build() {
            return new ColorContrastOptions(this);
        }

        @NonNull
        public Builder setHighContrastThemeOverlay(@StyleRes int i2) {
            this.highContrastThemeOverlayResourceId = i2;
            return this;
        }

        @NonNull
        public Builder setMediumContrastThemeOverlay(@StyleRes int i2) {
            this.mediumContrastThemeOverlayResourceId = i2;
            return this;
        }
    }

    private ColorContrastOptions(Builder builder) {
        this.mediumContrastThemeOverlayResourceId = builder.mediumContrastThemeOverlayResourceId;
        this.highContrastThemeOverlayResourceId = builder.highContrastThemeOverlayResourceId;
    }

    @StyleRes
    public int getHighContrastThemeOverlay() {
        return this.highContrastThemeOverlayResourceId;
    }

    @StyleRes
    public int getMediumContrastThemeOverlay() {
        return this.mediumContrastThemeOverlayResourceId;
    }
}
