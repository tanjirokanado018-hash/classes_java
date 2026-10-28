package com.google.android.material.shape;

import android.graphics.RectF;
import androidx.annotation.NonNull;
import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AbsoluteCornerSize implements CornerSize {
    private final float size;

    public AbsoluteCornerSize(float f2) {
        this.size = f2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AbsoluteCornerSize) && this.size == ((AbsoluteCornerSize) obj).size;
    }

    public float getCornerSize() {
        return this.size;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.size)});
    }

    @Override // com.google.android.material.shape.CornerSize
    public float getCornerSize(@NonNull RectF rectF) {
        return this.size;
    }
}
