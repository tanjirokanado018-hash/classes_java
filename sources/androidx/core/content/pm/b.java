package androidx.core.content.pm;

import android.graphics.Insets;
import android.view.WindowInsetsAnimation;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class b {
    public static /* synthetic */ WindowInsetsAnimation.Bounds i(Insets insets, Insets insets2) {
        return new WindowInsetsAnimation.Bounds(insets, insets2);
    }

    public static /* synthetic */ WindowInsetsAnimation j(int i2, Interpolator interpolator, long j2) {
        return new WindowInsetsAnimation(i2, interpolator, j2);
    }

    public static /* bridge */ /* synthetic */ WindowInsetsAnimation k(Object obj) {
        return (WindowInsetsAnimation) obj;
    }

    public static /* synthetic */ void o() {
    }
}
