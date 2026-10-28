package androidx.core.view;

import android.view.ScaleGestureDetector;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ScaleGestureDetectorCompat {
    private ScaleGestureDetectorCompat() {
    }

    public static boolean isQuickScaleEnabled(@NonNull ScaleGestureDetector scaleGestureDetector) {
        return scaleGestureDetector.isQuickScaleEnabled();
    }

    public static void setQuickScaleEnabled(@NonNull ScaleGestureDetector scaleGestureDetector, boolean z2) {
        scaleGestureDetector.setQuickScaleEnabled(z2);
    }

    @Deprecated
    public static boolean isQuickScaleEnabled(Object obj) {
        return isQuickScaleEnabled((ScaleGestureDetector) obj);
    }

    @Deprecated
    public static void setQuickScaleEnabled(Object obj, boolean z2) {
        setQuickScaleEnabled((ScaleGestureDetector) obj, z2);
    }
}
