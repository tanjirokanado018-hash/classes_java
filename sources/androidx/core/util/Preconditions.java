package androidx.core.util;

import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    public static float checkArgumentFinite(float f2, @NonNull String str) {
        if (Float.isNaN(f2)) {
            throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.e(str, " must not be NaN"));
        }
        if (Float.isInfinite(f2)) {
            throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.e(str, " must not be infinite"));
        }
        return f2;
    }

    public static double checkArgumentInRange(double d, double d2, double d3, @NonNull String str) {
        if (d < d2) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", str, Double.valueOf(d2), Double.valueOf(d3)));
        }
        if (d <= d3) {
            return d;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", str, Double.valueOf(d2), Double.valueOf(d3)));
    }

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    public static int checkArgumentNonnegative(int i2) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException();
    }

    public static int checkFlagsArgument(int i2, int i3) {
        if ((i2 & i3) == i2) {
            return i2;
        }
        throw new IllegalArgumentException("Requested flags 0x" + Integer.toHexString(i2) + ", but only 0x" + Integer.toHexString(i3) + " are allowed");
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T t2) {
        t2.getClass();
        return t2;
    }

    public static void checkState(boolean z2) {
        checkState(z2, null);
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(@Nullable T t2) {
        if (TextUtils.isEmpty(t2)) {
            throw new IllegalArgumentException();
        }
        return t2;
    }

    public static void checkArgument(boolean z2, @NonNull Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static float checkArgumentInRange(float f2, float f3, float f4, @NonNull String str) {
        if (f2 < f3) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", str, Float.valueOf(f3), Float.valueOf(f4)));
        }
        if (f2 <= f4) {
            return f2;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", str, Float.valueOf(f3), Float.valueOf(f4)));
    }

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    public static int checkArgumentNonnegative(int i2, @Nullable String str) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str);
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T t2, @NonNull Object obj) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static void checkState(boolean z2, @Nullable String str) {
        if (!z2) {
            throw new IllegalStateException(str);
        }
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(@Nullable T t2, @NonNull Object obj) {
        if (TextUtils.isEmpty(t2)) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
        return t2;
    }

    public static void checkArgument(boolean z2, @NonNull String str, @NonNull Object... objArr) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static int checkArgumentInRange(int i2, int i3, int i4, @NonNull String str) {
        if (i2 < i3) {
            Locale locale = Locale.US;
            throw new IllegalArgumentException(str + " is out of range of [" + i3 + ", " + i4 + "] (too low)");
        }
        if (i2 <= i4) {
            return i2;
        }
        Locale locale2 = Locale.US;
        throw new IllegalArgumentException(str + " is out of range of [" + i3 + ", " + i4 + "] (too high)");
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(@Nullable T t2, @NonNull String str, @NonNull Object... objArr) {
        if (TextUtils.isEmpty(t2)) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
        return t2;
    }

    public static long checkArgumentInRange(long j2, long j3, long j4, @NonNull String str) {
        if (j2 < j3) {
            Locale locale = Locale.US;
            throw new IllegalArgumentException(str + " is out of range of [" + j3 + ", " + j4 + "] (too low)");
        }
        if (j2 <= j4) {
            return j2;
        }
        Locale locale2 = Locale.US;
        throw new IllegalArgumentException(str + " is out of range of [" + j3 + ", " + j4 + "] (too high)");
    }
}
