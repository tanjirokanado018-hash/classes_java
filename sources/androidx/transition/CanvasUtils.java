package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class CanvasUtils {
    private static Method sInorderBarrierMethod;
    private static boolean sOrderMethodsFetched;
    private static Method sReorderBarrierMethod;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static void disableZ(Canvas canvas) {
            canvas.disableZ();
        }

        @DoNotInline
        public static void enableZ(Canvas canvas) {
            canvas.enableZ();
        }
    }

    private CanvasUtils() {
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    public static void enableZ(@NonNull Canvas canvas, boolean z2) {
        Method method;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 29) {
            if (z2) {
                Api29Impl.enableZ(canvas);
                return;
            } else {
                Api29Impl.disableZ(canvas);
                return;
            }
        }
        if (i2 == 28) {
            throw new IllegalStateException("This method doesn't work on Pie!");
        }
        if (!sOrderMethodsFetched) {
            try {
                Method declaredMethod = Canvas.class.getDeclaredMethod("insertReorderBarrier", new Class[0]);
                sReorderBarrierMethod = declaredMethod;
                declaredMethod.setAccessible(true);
                Method declaredMethod2 = Canvas.class.getDeclaredMethod("insertInorderBarrier", new Class[0]);
                sInorderBarrierMethod = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            sOrderMethodsFetched = true;
        }
        if (z2) {
            try {
                Method method2 = sReorderBarrierMethod;
                if (method2 != null) {
                    method2.invoke(canvas, new Object[0]);
                }
            } catch (IllegalAccessException unused2) {
                return;
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
        if (z2 || (method = sInorderBarrierMethod) == null) {
            return;
        }
        method.invoke(canvas, new Object[0]);
    }
}
