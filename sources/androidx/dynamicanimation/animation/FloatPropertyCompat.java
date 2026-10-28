package androidx.dynamicanimation.animation;

import android.util.FloatProperty;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class FloatPropertyCompat<T> {
    final String mPropertyName;

    public FloatPropertyCompat(String str) {
        this.mPropertyName = str;
    }

    @RequiresApi(24)
    public static <T> FloatPropertyCompat<T> createFloatPropertyCompat(final FloatProperty<T> floatProperty) {
        return new FloatPropertyCompat<T>(floatProperty.getName()) { // from class: androidx.dynamicanimation.animation.FloatPropertyCompat.1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public float getValue(T t2) {
                return ((Float) floatProperty.get(t2)).floatValue();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public void setValue(T t2, float f2) {
                floatProperty.setValue(t2, f2);
            }
        };
    }

    public abstract float getValue(T t2);

    public abstract void setValue(T t2, float f2);
}
