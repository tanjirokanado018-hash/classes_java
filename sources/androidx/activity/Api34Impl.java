package androidx.activity;

import android.window.BackEvent;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(34)
public final class Api34Impl {
    public static final Api34Impl INSTANCE = new Api34Impl();

    private Api34Impl() {
    }

    @DoNotInline
    public final BackEvent createOnBackEvent(float f2, float f3, float f4, int i2) {
        return new BackEvent(f2, f3, f4, i2);
    }

    @DoNotInline
    public final float progress(BackEvent backEvent) {
        k.g(backEvent, "backEvent");
        return backEvent.getProgress();
    }

    @DoNotInline
    public final int swipeEdge(BackEvent backEvent) {
        k.g(backEvent, "backEvent");
        return backEvent.getSwipeEdge();
    }

    @DoNotInline
    public final float touchX(BackEvent backEvent) {
        k.g(backEvent, "backEvent");
        return backEvent.getTouchX();
    }

    @DoNotInline
    public final float touchY(BackEvent backEvent) {
        k.g(backEvent, "backEvent");
        return backEvent.getTouchY();
    }
}
