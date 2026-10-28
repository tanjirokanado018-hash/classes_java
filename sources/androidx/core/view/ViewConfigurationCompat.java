package androidx.core.view;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.InputDevice;
import android.view.ViewConfiguration;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Supplier;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewConfigurationCompat {
    private static final int NO_FLING_MAX_VELOCITY = Integer.MIN_VALUE;
    private static final int NO_FLING_MIN_VELOCITY = Integer.MAX_VALUE;
    private static final int RESOURCE_ID_NOT_SUPPORTED = -1;
    private static final int RESOURCE_ID_SUPPORTED_BUT_NOT_FOUND = 0;
    private static final String TAG = "ViewConfigCompat";
    private static Method sGetScaledScrollFactorMethod;

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static float getScaledHorizontalScrollFactor(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledHorizontalScrollFactor();
        }

        @DoNotInline
        public static float getScaledVerticalScrollFactor(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledVerticalScrollFactor();
        }
    }

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static int getScaledHoverSlop(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledHoverSlop();
        }

        @DoNotInline
        public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration viewConfiguration) {
            return viewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent();
        }
    }

    @RequiresApi(34)
    public static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        public static int getScaledMaximumFlingVelocity(@NonNull ViewConfiguration viewConfiguration, int i2, int i3, int i4) {
            return viewConfiguration.getScaledMaximumFlingVelocity(i2, i3, i4);
        }

        @DoNotInline
        public static int getScaledMinimumFlingVelocity(@NonNull ViewConfiguration viewConfiguration, int i2, int i3, int i4) {
            return viewConfiguration.getScaledMinimumFlingVelocity(i2, i3, i4);
        }
    }

    static {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
            } catch (Exception unused) {
            }
        }
    }

    private ViewConfigurationCompat() {
    }

    private static int getCompatFlingVelocityThreshold(Resources resources, int i2, Supplier<Integer> supplier, int i3) {
        int dimensionPixelSize;
        if (i2 != -1) {
            return (i2 == 0 || (dimensionPixelSize = resources.getDimensionPixelSize(i2)) < 0) ? i3 : dimensionPixelSize;
        }
        return supplier.get().intValue();
    }

    private static float getLegacyScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        Method method;
        if (Build.VERSION.SDK_INT >= 25 && (method = sGetScaledScrollFactorMethod) != null) {
            try {
                return ((Integer) method.invoke(viewConfiguration, new Object[0])).intValue();
            } catch (Exception unused) {
            }
        }
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
            return typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return 0.0f;
    }

    private static int getPlatformResId(Resources resources, String str, String str2) {
        return resources.getIdentifier(str, str2, "android");
    }

    private static int getPreApi34MaximumFlingVelocityResId(Resources resources, int i2, int i3) {
        if (i2 == 4194304 && i3 == 26) {
            return getPlatformResId(resources, "config_viewMaxRotaryEncoderFlingVelocity", "dimen");
        }
        return -1;
    }

    private static int getPreApi34MinimumFlingVelocityResId(Resources resources, int i2, int i3) {
        if (i2 == 4194304 && i3 == 26) {
            return getPlatformResId(resources, "config_viewMinRotaryEncoderFlingVelocity", "dimen");
        }
        return -1;
    }

    public static float getScaledHorizontalScrollFactor(@NonNull ViewConfiguration viewConfiguration, @NonNull Context context) {
        return Build.VERSION.SDK_INT >= 26 ? Api26Impl.getScaledHorizontalScrollFactor(viewConfiguration) : getLegacyScrollFactor(viewConfiguration, context);
    }

    public static int getScaledHoverSlop(@NonNull ViewConfiguration viewConfiguration) {
        return Build.VERSION.SDK_INT >= 28 ? Api28Impl.getScaledHoverSlop(viewConfiguration) : viewConfiguration.getScaledTouchSlop() / 2;
    }

    public static int getScaledMaximumFlingVelocity(@NonNull Context context, @NonNull ViewConfiguration viewConfiguration, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api34Impl.getScaledMaximumFlingVelocity(viewConfiguration, i2, i3, i4);
        }
        if (!isInputDeviceInfoValid(i2, i3, i4)) {
            return Integer.MIN_VALUE;
        }
        Resources resources = context.getResources();
        int preApi34MaximumFlingVelocityResId = getPreApi34MaximumFlingVelocityResId(resources, i4, i3);
        Objects.requireNonNull(viewConfiguration);
        return getCompatFlingVelocityThreshold(resources, preApi34MaximumFlingVelocityResId, new l(viewConfiguration, 0), Integer.MIN_VALUE);
    }

    public static int getScaledMinimumFlingVelocity(@NonNull Context context, @NonNull ViewConfiguration viewConfiguration, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api34Impl.getScaledMinimumFlingVelocity(viewConfiguration, i2, i3, i4);
        }
        if (!isInputDeviceInfoValid(i2, i3, i4)) {
            return Integer.MAX_VALUE;
        }
        Resources resources = context.getResources();
        int preApi34MinimumFlingVelocityResId = getPreApi34MinimumFlingVelocityResId(resources, i4, i3);
        Objects.requireNonNull(viewConfiguration);
        return getCompatFlingVelocityThreshold(resources, preApi34MinimumFlingVelocityResId, new l(viewConfiguration, 1), Integer.MAX_VALUE);
    }

    @Deprecated
    public static int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledPagingTouchSlop();
    }

    public static float getScaledVerticalScrollFactor(@NonNull ViewConfiguration viewConfiguration, @NonNull Context context) {
        return Build.VERSION.SDK_INT >= 26 ? Api26Impl.getScaledVerticalScrollFactor(viewConfiguration) : getLegacyScrollFactor(viewConfiguration, context);
    }

    @Deprecated
    public static boolean hasPermanentMenuKey(ViewConfiguration viewConfiguration) {
        return viewConfiguration.hasPermanentMenuKey();
    }

    private static boolean isInputDeviceInfoValid(int i2, int i3, int i4) {
        InputDevice device = InputDevice.getDevice(i2);
        return (device == null || device.getMotionRange(i3, i4) == null) ? false : true;
    }

    public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(@NonNull ViewConfiguration viewConfiguration, @NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.shouldShowMenuShortcutsWhenKeyboardPresent(viewConfiguration);
        }
        Resources resources = context.getResources();
        int platformResId = getPlatformResId(resources, "config_showMenuShortcutsWhenKeyboardPresent", "bool");
        return platformResId != 0 && resources.getBoolean(platformResId);
    }
}
