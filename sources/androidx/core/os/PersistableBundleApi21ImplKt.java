package androidx.core.os;

import android.os.Build;
import android.os.PersistableBundle;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
final class PersistableBundleApi21ImplKt {
    public static final PersistableBundleApi21ImplKt INSTANCE = new PersistableBundleApi21ImplKt();

    private PersistableBundleApi21ImplKt() {
    }

    @DoNotInline
    public static final PersistableBundle createPersistableBundle(int i2) {
        return new PersistableBundle(i2);
    }

    @DoNotInline
    public static final void putValue(PersistableBundle persistableBundle, String str, Object obj) {
        String str2;
        if (obj == null) {
            str2 = null;
        } else {
            if (obj instanceof Boolean) {
                if (Build.VERSION.SDK_INT >= 22) {
                    PersistableBundleApi22ImplKt.putBoolean(persistableBundle, str, ((Boolean) obj).booleanValue());
                    return;
                }
                throw new IllegalArgumentException("Illegal value type boolean for key \"" + str + '\"');
            }
            if (obj instanceof Double) {
                persistableBundle.putDouble(str, ((Number) obj).doubleValue());
                return;
            }
            if (obj instanceof Integer) {
                persistableBundle.putInt(str, ((Number) obj).intValue());
                return;
            }
            if (obj instanceof Long) {
                persistableBundle.putLong(str, ((Number) obj).longValue());
                return;
            }
            if (!(obj instanceof String)) {
                if (obj instanceof boolean[]) {
                    if (Build.VERSION.SDK_INT >= 22) {
                        PersistableBundleApi22ImplKt.putBooleanArray(persistableBundle, str, (boolean[]) obj);
                        return;
                    }
                    throw new IllegalArgumentException("Illegal value type boolean[] for key \"" + str + '\"');
                }
                if (obj instanceof double[]) {
                    persistableBundle.putDoubleArray(str, (double[]) obj);
                    return;
                }
                if (obj instanceof int[]) {
                    persistableBundle.putIntArray(str, (int[]) obj);
                    return;
                }
                if (obj instanceof long[]) {
                    persistableBundle.putLongArray(str, (long[]) obj);
                    return;
                }
                if (!(obj instanceof Object[])) {
                    throw new IllegalArgumentException("Illegal value type " + obj.getClass().getCanonicalName() + " for key \"" + str + '\"');
                }
                Class<?> componentType = obj.getClass().getComponentType();
                k.d(componentType);
                if (String.class.isAssignableFrom(componentType)) {
                    persistableBundle.putStringArray(str, (String[]) obj);
                    return;
                }
                throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + str + '\"');
            }
            str2 = (String) obj;
        }
        persistableBundle.putString(str, str2);
    }
}
