package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ImageViewUtils {
    private static Field sDrawMatrixField = null;
    private static boolean sDrawMatrixFieldFetched = false;
    private static boolean sTryHiddenAnimateTransform = true;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static void animateTransform(ImageView imageView, Matrix matrix) {
            imageView.animateTransform(matrix);
        }
    }

    private ImageViewUtils() {
    }

    public static void animateTransform(@NonNull ImageView imageView, @Nullable Matrix matrix) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.animateTransform(imageView, matrix);
            return;
        }
        if (matrix != null) {
            hiddenAnimateTransform(imageView, matrix);
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setBounds(0, 0, (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight(), (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom());
            imageView.invalidate();
        }
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    private static void fetchDrawMatrixField() {
        if (sDrawMatrixFieldFetched) {
            return;
        }
        try {
            Field declaredField = ImageView.class.getDeclaredField("mDrawMatrix");
            sDrawMatrixField = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException unused) {
        }
        sDrawMatrixFieldFetched = true;
    }

    @RequiresApi(21)
    @SuppressLint({"NewApi"})
    private static void hiddenAnimateTransform(@NonNull ImageView imageView, @Nullable Matrix matrix) {
        if (sTryHiddenAnimateTransform) {
            try {
                Api29Impl.animateTransform(imageView, matrix);
            } catch (NoSuchMethodError unused) {
                sTryHiddenAnimateTransform = false;
            }
        }
    }
}
