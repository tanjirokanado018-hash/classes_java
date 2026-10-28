package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.PointF;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class BitmapKt {
    public static final Bitmap applyCanvas(Bitmap bitmap, l lVar) {
        lVar.invoke(new Canvas(bitmap));
        return bitmap;
    }

    public static final boolean contains(Bitmap bitmap, Point point) {
        int i2;
        int width = bitmap.getWidth();
        int i3 = point.x;
        return i3 >= 0 && i3 < width && (i2 = point.y) >= 0 && i2 < bitmap.getHeight();
    }

    public static final Bitmap createBitmap(int i2, int i3, Bitmap.Config config) {
        return Bitmap.createBitmap(i2, i3, config);
    }

    public static /* synthetic */ Bitmap createBitmap$default(int i2, int i3, Bitmap.Config config, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        return Bitmap.createBitmap(i2, i3, config);
    }

    public static final int get(Bitmap bitmap, int i2, int i3) {
        return bitmap.getPixel(i2, i3);
    }

    public static final Bitmap scale(Bitmap bitmap, int i2, int i3, boolean z2) {
        return Bitmap.createScaledBitmap(bitmap, i2, i3, z2);
    }

    public static /* synthetic */ Bitmap scale$default(Bitmap bitmap, int i2, int i3, boolean z2, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            z2 = true;
        }
        return Bitmap.createScaledBitmap(bitmap, i2, i3, z2);
    }

    public static final void set(Bitmap bitmap, int i2, int i3, @ColorInt int i4) {
        bitmap.setPixel(i2, i3, i4);
    }

    public static final boolean contains(Bitmap bitmap, PointF pointF) {
        float f2 = pointF.x;
        if (f2 >= 0.0f && f2 < bitmap.getWidth()) {
            float f3 = pointF.y;
            if (f3 >= 0.0f && f3 < bitmap.getHeight()) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(26)
    @SuppressLint({"ClassVerificationFailure"})
    public static final Bitmap createBitmap(int i2, int i3, Bitmap.Config config, boolean z2, ColorSpace colorSpace) {
        return Bitmap.createBitmap(i2, i3, config, z2, colorSpace);
    }

    public static /* synthetic */ Bitmap createBitmap$default(int i2, int i3, Bitmap.Config config, boolean z2, ColorSpace colorSpace, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        if ((i4 & 8) != 0) {
            z2 = true;
        }
        if ((i4 & 16) != 0) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        return Bitmap.createBitmap(i2, i3, config, z2, colorSpace);
    }
}
