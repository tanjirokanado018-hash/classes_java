package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WindowUtils {
    private static final String TAG = "WindowUtils";

    public static class Api14Impl {
        private Api14Impl() {
        }

        @NonNull
        public static Rect getCurrentWindowBounds(@NonNull WindowManager windowManager) {
            int i2;
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point realSizeForDisplay = getRealSizeForDisplay(defaultDisplay);
            Rect rect = new Rect();
            int i3 = realSizeForDisplay.x;
            if (i3 == 0 || (i2 = realSizeForDisplay.y) == 0) {
                defaultDisplay.getRectSize(rect);
            } else {
                rect.right = i3;
                rect.bottom = i2;
            }
            return rect;
        }

        private static Point getRealSizeForDisplay(Display display) {
            Point point = new Point();
            try {
                Method declaredMethod = Display.class.getDeclaredMethod("getRealSize", Point.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(display, point);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                String unused2 = WindowUtils.TAG;
            }
            return point;
        }
    }

    @RequiresApi(api = 17)
    public static class Api17Impl {
        private Api17Impl() {
        }

        @NonNull
        public static Rect getCurrentWindowBounds(@NonNull WindowManager windowManager) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            Rect rect = new Rect();
            rect.right = point.x;
            rect.bottom = point.y;
            return rect;
        }
    }

    @RequiresApi(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static class Api30Impl {
        private Api30Impl() {
        }

        @NonNull
        public static Rect getCurrentWindowBounds(@NonNull WindowManager windowManager) {
            return windowManager.getCurrentWindowMetrics().getBounds();
        }
    }

    private WindowUtils() {
    }

    @NonNull
    public static Rect getCurrentWindowBounds(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        return Build.VERSION.SDK_INT >= 30 ? Api30Impl.getCurrentWindowBounds(windowManager) : Api17Impl.getCurrentWindowBounds(windowManager);
    }
}
