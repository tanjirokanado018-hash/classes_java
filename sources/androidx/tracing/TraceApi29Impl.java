package androidx.tracing;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
final class TraceApi29Impl {
    private TraceApi29Impl() {
    }

    public static void beginAsyncSection(@NonNull String str, int i2) {
        android.os.Trace.beginAsyncSection(str, i2);
    }

    public static void endAsyncSection(@NonNull String str, int i2) {
        android.os.Trace.endAsyncSection(str, i2);
    }

    public static void setCounter(@NonNull String str, int i2) {
        android.os.Trace.setCounter(str, i2);
    }
}
