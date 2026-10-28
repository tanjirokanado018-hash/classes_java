package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PendingIntentCompat {

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static void send(@NonNull PendingIntent pendingIntent, @NonNull Context context, int i2, @NonNull Intent intent, @Nullable PendingIntent.OnFinished onFinished, @Nullable Handler handler, @Nullable String str, @Nullable Bundle bundle) throws PendingIntent.CanceledException {
            pendingIntent.send(context, i2, intent, onFinished, handler, str, bundle);
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static PendingIntent getForegroundService(Context context, int i2, Intent intent, int i3) {
            return PendingIntent.getForegroundService(context, i2, intent, i3);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Flags {
    }

    public static class GatedCallback implements Closeable {

        @Nullable
        private PendingIntent.OnFinished mCallback;
        private final CountDownLatch mComplete = new CountDownLatch(1);
        private boolean mSuccess = false;

        public GatedCallback(@Nullable PendingIntent.OnFinished onFinished) {
            this.mCallback = onFinished;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSendFinished(PendingIntent pendingIntent, Intent intent, int i2, String str, Bundle bundle) {
            boolean z2 = false;
            while (true) {
                try {
                    this.mComplete.await();
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                } catch (Throwable th) {
                    if (z2) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            }
            if (z2) {
                Thread.currentThread().interrupt();
            }
            PendingIntent.OnFinished onFinished = this.mCallback;
            if (onFinished != null) {
                onFinished.onSendFinished(pendingIntent, intent, i2, str, bundle);
                this.mCallback = null;
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (!this.mSuccess) {
                this.mCallback = null;
            }
            this.mComplete.countDown();
        }

        public void complete() {
            this.mSuccess = true;
        }

        @Nullable
        public PendingIntent.OnFinished getCallback() {
            if (this.mCallback == null) {
                return null;
            }
            return new PendingIntent.OnFinished() { // from class: androidx.core.app.e
                @Override // android.app.PendingIntent.OnFinished
                public final void onSendFinished(PendingIntent pendingIntent, Intent intent, int i2, String str, Bundle bundle) {
                    this.f25a.onSendFinished(pendingIntent, intent, i2, str, bundle);
                }
            };
        }
    }

    private PendingIntentCompat() {
    }

    private static int addMutabilityFlags(boolean z2, int i2) {
        int i3;
        if (z2) {
            if (Build.VERSION.SDK_INT < 31) {
                return i2;
            }
            i3 = 33554432;
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                return i2;
            }
            i3 = AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        }
        return i2 | i3;
    }

    @NonNull
    public static PendingIntent getActivities(@NonNull Context context, int i2, @NonNull @SuppressLint({"ArrayReturn"}) Intent[] intentArr, int i3, @Nullable Bundle bundle, boolean z2) {
        return PendingIntent.getActivities(context, i2, intentArr, addMutabilityFlags(z2, i3), bundle);
    }

    @Nullable
    public static PendingIntent getActivity(@NonNull Context context, int i2, @NonNull Intent intent, int i3, @Nullable Bundle bundle, boolean z2) {
        return PendingIntent.getActivity(context, i2, intent, addMutabilityFlags(z2, i3), bundle);
    }

    @Nullable
    public static PendingIntent getBroadcast(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getBroadcast(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @NonNull
    @RequiresApi(26)
    public static PendingIntent getForegroundService(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return Api26Impl.getForegroundService(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @Nullable
    public static PendingIntent getService(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getService(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @SuppressLint({"LambdaLast"})
    public static void send(@NonNull PendingIntent pendingIntent, int i2, @Nullable PendingIntent.OnFinished onFinished, @Nullable Handler handler) {
        GatedCallback gatedCallback = new GatedCallback(onFinished);
        try {
            pendingIntent.send(i2, gatedCallback.getCallback(), handler);
            gatedCallback.complete();
            gatedCallback.close();
        } catch (Throwable th) {
            try {
                gatedCallback.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @NonNull
    public static PendingIntent getActivities(@NonNull Context context, int i2, @NonNull @SuppressLint({"ArrayReturn"}) Intent[] intentArr, int i3, boolean z2) {
        return PendingIntent.getActivities(context, i2, intentArr, addMutabilityFlags(z2, i3));
    }

    @Nullable
    public static PendingIntent getActivity(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getActivity(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @SuppressLint({"LambdaLast"})
    public static void send(@NonNull PendingIntent pendingIntent, @NonNull @SuppressLint({"ContextFirst"}) Context context, int i2, @NonNull Intent intent, @Nullable PendingIntent.OnFinished onFinished, @Nullable Handler handler) {
        send(pendingIntent, context, i2, intent, onFinished, handler, null, null);
    }

    @SuppressLint({"LambdaLast"})
    public static void send(@NonNull PendingIntent pendingIntent, @NonNull @SuppressLint({"ContextFirst"}) Context context, int i2, @NonNull Intent intent, @Nullable PendingIntent.OnFinished onFinished, @Nullable Handler handler, @Nullable String str, @Nullable Bundle bundle) {
        GatedCallback gatedCallback = new GatedCallback(onFinished);
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                Api23Impl.send(pendingIntent, context, i2, intent, onFinished, handler, str, bundle);
            } else {
                pendingIntent.send(context, i2, intent, gatedCallback.getCallback(), handler, str);
            }
            gatedCallback.complete();
            gatedCallback.close();
        } catch (Throwable th) {
            try {
                gatedCallback.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
