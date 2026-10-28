package androidx.core.util;

import android.annotation.SuppressLint;
import android.util.Half;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@SuppressLint({"ClassVerificationFailure"})
public final class HalfKt {
    @RequiresApi(26)
    public static final Half toHalf(double d) {
        return Half.valueOf((float) d);
    }

    @RequiresApi(26)
    public static final Half toHalf(float f2) {
        return Half.valueOf(f2);
    }

    @RequiresApi(26)
    public static final Half toHalf(String str) {
        return Half.valueOf(str);
    }

    @RequiresApi(26)
    public static final Half toHalf(short s2) {
        return Half.valueOf(s2);
    }
}
