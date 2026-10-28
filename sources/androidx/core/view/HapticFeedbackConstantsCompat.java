package androidx.core.view;

import android.os.Build;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class HapticFeedbackConstantsCompat {
    public static final int CLOCK_TICK = 4;
    public static final int CONFIRM = 16;
    public static final int CONTEXT_CLICK = 6;
    public static final int DRAG_START = 25;

    @VisibleForTesting
    static final int FIRST_CONSTANT_INT = 0;
    public static final int FLAG_IGNORE_VIEW_SETTING = 1;
    public static final int GESTURE_END = 13;
    public static final int GESTURE_START = 12;
    public static final int GESTURE_THRESHOLD_ACTIVATE = 23;
    public static final int GESTURE_THRESHOLD_DEACTIVATE = 24;
    public static final int KEYBOARD_PRESS = 3;
    public static final int KEYBOARD_RELEASE = 7;
    public static final int KEYBOARD_TAP = 3;

    @VisibleForTesting
    static final int LAST_CONSTANT_INT = 27;
    public static final int LONG_PRESS = 0;
    public static final int NO_HAPTICS = -1;
    public static final int REJECT = 17;
    public static final int SEGMENT_FREQUENT_TICK = 27;
    public static final int SEGMENT_TICK = 26;
    public static final int TEXT_HANDLE_MOVE = 9;
    public static final int TOGGLE_OFF = 22;
    public static final int TOGGLE_ON = 21;
    public static final int VIRTUAL_KEY = 1;
    public static final int VIRTUAL_KEY_RELEASE = 8;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface HapticFeedbackFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface HapticFeedbackType {
    }

    private HapticFeedbackConstantsCompat() {
    }

    /* JADX WARN: Code duplicated, block: B:24:0x002d  */
    /* JADX WARN: Code duplicated, block: B:25:0x002f  */
    public static int getFeedbackConstantOrFallback(int i2) {
        int i3 = -1;
        if (i2 == -1) {
            return -1;
        }
        int i4 = Build.VERSION.SDK_INT;
        int i5 = 0;
        if (i4 < 34) {
            switch (i2) {
                case 21:
                case 23:
                case 26:
                    i2 = 6;
                    break;
                case 22:
                case 24:
                case 27:
                    i2 = 4;
                    break;
                case 25:
                    i2 = 0;
                    break;
            }
        }
        if (i4 >= 30) {
            i5 = i2;
        } else if (i2 == 12) {
            i5 = 1;
        } else if (i2 == 13) {
            i5 = 6;
        } else if (i2 == 16) {
            i5 = 1;
        } else if (i2 != 17) {
            i5 = i2;
        }
        if (i4 >= 27 || (i5 != 7 && i5 != 8 && i5 != 9)) {
            i3 = i5;
        }
        if (i4 >= 23 || i3 != 6) {
            return i3;
        }
        return 4;
    }
}
