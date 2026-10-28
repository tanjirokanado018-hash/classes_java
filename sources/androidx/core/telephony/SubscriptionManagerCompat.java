package androidx.core.telephony;

import android.os.Build;
import android.telephony.SubscriptionManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.a;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(22)
public class SubscriptionManagerCompat {
    private static Method sGetSlotIndexMethod;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static int getSlotIndex(int i2) {
            return SubscriptionManager.getSlotIndex(i2);
        }
    }

    private SubscriptionManagerCompat() {
    }

    public static int getSlotIndex(int i2) {
        if (i2 == -1) {
            return -1;
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 29) {
            return Api29Impl.getSlotIndex(i2);
        }
        try {
            if (sGetSlotIndexMethod == null) {
                sGetSlotIndexMethod = i3 >= 26 ? a.c().getDeclaredMethod("getSlotIndex", Integer.TYPE) : a.c().getDeclaredMethod("getSlotId", Integer.TYPE);
                sGetSlotIndexMethod.setAccessible(true);
            }
            Integer num = (Integer) sGetSlotIndexMethod.invoke(null, Integer.valueOf(i2));
            if (num != null) {
                return num.intValue();
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        return -1;
    }
}
