package com.google.android.material.shape;

import android.graphics.RectF;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.android.material.color.utilities.Contrast;
import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class RelativeCornerSize implements CornerSize {
    private final float percent;

    public RelativeCornerSize(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2) {
        this.percent = f2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static RelativeCornerSize createFromCornerSize(@NonNull RectF rectF, @NonNull CornerSize cornerSize) {
        return cornerSize instanceof RelativeCornerSize ? (RelativeCornerSize) cornerSize : new RelativeCornerSize(cornerSize.getCornerSize(rectF) / getMaxCornerSize(rectF));
    }

    private static float getMaxCornerSize(@NonNull RectF rectF) {
        return Math.min(rectF.width(), rectF.height());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RelativeCornerSize) && this.percent == ((RelativeCornerSize) obj).percent;
    }

    @Override // com.google.android.material.shape.CornerSize
    public float getCornerSize(@NonNull RectF rectF) {
        return this.percent * getMaxCornerSize(rectF);
    }

    @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN)
    public float getRelativePercent() {
        return this.percent;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.percent)});
    }
}
