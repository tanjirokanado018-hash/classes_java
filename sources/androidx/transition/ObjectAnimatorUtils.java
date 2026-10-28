package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ObjectAnimatorUtils {

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static <T, V> ObjectAnimator ofObject(T t2, Property<T, V> property, Path path) {
            return ObjectAnimator.ofObject(t2, property, (TypeConverter) null, path);
        }
    }

    private ObjectAnimatorUtils() {
    }

    public static <T> ObjectAnimator ofPointF(T t2, Property<T, PointF> property, Path path) {
        return Api21Impl.ofObject(t2, property, path);
    }
}
