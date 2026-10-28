package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ViewGroup;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ViewGroupUtils {
    private static Method sGetChildDrawingOrderMethod = null;
    private static boolean sGetChildDrawingOrderMethodFetched = false;
    private static boolean sTryHiddenSuppressLayout = true;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static int getChildDrawingOrder(ViewGroup viewGroup, int i2) {
            return viewGroup.getChildDrawingOrder(i2);
        }

        @DoNotInline
        public static void suppressLayout(ViewGroup viewGroup, boolean z2) {
            viewGroup.suppressLayout(z2);
        }
    }

    private ViewGroupUtils() {
    }

    public static int getChildDrawingOrder(@NonNull ViewGroup viewGroup, int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getChildDrawingOrder(viewGroup, i2);
        }
        if (!sGetChildDrawingOrderMethodFetched) {
            try {
                Class cls = Integer.TYPE;
                Method declaredMethod = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", cls, cls);
                sGetChildDrawingOrderMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            sGetChildDrawingOrderMethodFetched = true;
        }
        Method method = sGetChildDrawingOrderMethod;
        if (method != null) {
            try {
                return ((Integer) method.invoke(viewGroup, Integer.valueOf(viewGroup.getChildCount()), Integer.valueOf(i2))).intValue();
            } catch (IllegalAccessException | InvocationTargetException unused2) {
            }
        }
        return i2;
    }

    @SuppressLint({"NewApi"})
    private static void hiddenSuppressLayout(@NonNull ViewGroup viewGroup, boolean z2) {
        if (sTryHiddenSuppressLayout) {
            try {
                Api29Impl.suppressLayout(viewGroup, z2);
            } catch (NoSuchMethodError unused) {
                sTryHiddenSuppressLayout = false;
            }
        }
    }

    public static void suppressLayout(@NonNull ViewGroup viewGroup, boolean z2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.suppressLayout(viewGroup, z2);
        } else {
            hiddenSuppressLayout(viewGroup, z2);
        }
    }
}
