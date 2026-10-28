package androidx.core.os;

import android.os.Build;
import android.os.Trace;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public final class TraceCompat {
    private static final String TAG = "TraceCompat";
    private static Method sAsyncTraceBeginMethod;
    private static Method sAsyncTraceEndMethod;
    private static Method sIsTagEnabledMethod;
    private static Method sTraceCounterMethod;
    private static long sTraceTagApp;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static void beginAsyncSection(String str, int i2) {
            Trace.beginAsyncSection(str, i2);
        }

        @DoNotInline
        public static void endAsyncSection(String str, int i2) {
            Trace.endAsyncSection(str, i2);
        }

        @DoNotInline
        public static boolean isEnabled() {
            return Trace.isEnabled();
        }

        @DoNotInline
        public static void setCounter(String str, long j2) {
            Trace.setCounter(str, j2);
        }
    }

    static {
        if (Build.VERSION.SDK_INT < 29) {
            try {
                sTraceTagApp = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                Class cls = Long.TYPE;
                sIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", cls);
                Class cls2 = Integer.TYPE;
                sAsyncTraceBeginMethod = Trace.class.getMethod("asyncTraceBegin", cls, String.class, cls2);
                sAsyncTraceEndMethod = Trace.class.getMethod("asyncTraceEnd", cls, String.class, cls2);
                sTraceCounterMethod = Trace.class.getMethod("traceCounter", cls, String.class, cls2);
            } catch (Exception unused) {
            }
        }
    }

    private TraceCompat() {
    }

    public static void beginAsyncSection(@NonNull String str, int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.beginAsyncSection(str, i2);
        } else {
            try {
                sAsyncTraceBeginMethod.invoke(null, Long.valueOf(sTraceTagApp), str, Integer.valueOf(i2));
            } catch (Exception unused) {
            }
        }
    }

    public static void beginSection(@NonNull String str) {
        Trace.beginSection(str);
    }

    public static void endAsyncSection(@NonNull String str, int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.endAsyncSection(str, i2);
        } else {
            try {
                sAsyncTraceEndMethod.invoke(null, Long.valueOf(sTraceTagApp), str, Integer.valueOf(i2));
            } catch (Exception unused) {
            }
        }
    }

    public static void endSection() {
        Trace.endSection();
    }

    public static boolean isEnabled() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.isEnabled();
        }
        try {
            return ((Boolean) sIsTagEnabledMethod.invoke(null, Long.valueOf(sTraceTagApp))).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public static void setCounter(@NonNull String str, int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.setCounter(str, i2);
        } else {
            try {
                sTraceCounterMethod.invoke(null, Long.valueOf(sTraceTagApp), str, Integer.valueOf(i2));
            } catch (Exception unused) {
            }
        }
    }
}
