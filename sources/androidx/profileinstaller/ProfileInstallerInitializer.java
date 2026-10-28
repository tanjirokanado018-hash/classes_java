package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ProfileInstallerInitializer implements Initializer<Result> {
    private static final int DELAY_MS = 5000;

    @RequiresApi(16)
    public static class Choreographer16Impl {
        private Choreographer16Impl() {
        }

        @DoNotInline
        public static void postFrameCallback(final Runnable runnable) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.profileinstaller.c
                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j2) {
                    runnable.run();
                }
            });
        }
    }

    @RequiresApi(28)
    public static class Handler28Impl {
        private Handler28Impl() {
        }

        @DoNotInline
        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }
    }

    public static class Result {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeInBackground(@NonNull Context context) {
        new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()).execute(new b(context, 1));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    @NonNull
    public Result create(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 24) {
            return new Result();
        }
        delayAfterFirstFrame(context.getApplicationContext());
        return new Result();
    }

    @RequiresApi(16)
    public void delayAfterFirstFrame(@NonNull Context context) {
        Choreographer16Impl.postFrameCallback(new androidx.core.content.res.a(4, this, context));
    }

    @Override // androidx.startup.Initializer
    @NonNull
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }

    /* JADX INFO: renamed from: installAfterDelay, reason: merged with bridge method [inline-methods] */
    public void lambda$delayAfterFirstFrame$0(@NonNull Context context) {
        (Build.VERSION.SDK_INT >= 28 ? Handler28Impl.createAsync(Looper.getMainLooper()) : new Handler(Looper.getMainLooper())).postDelayed(new b(context, 0), new Random().nextInt(Math.max(1000, 1)) + DELAY_MS);
    }
}
