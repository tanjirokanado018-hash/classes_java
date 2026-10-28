package androidx.core.graphics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PorterDuffKt {
    public static final PorterDuffColorFilter toColorFilter(PorterDuff.Mode mode, int i2) {
        return new PorterDuffColorFilter(i2, mode);
    }

    public static final PorterDuffXfermode toXfermode(PorterDuff.Mode mode) {
        return new PorterDuffXfermode(mode);
    }
}
