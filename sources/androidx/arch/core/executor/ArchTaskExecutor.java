package androidx.arch.core.executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance;

    @NonNull
    private final TaskExecutor mDefaultTaskExecutor;

    @NonNull
    private TaskExecutor mDelegate;

    @NonNull
    private static final Executor sMainThreadExecutor = new a(0);

    @NonNull
    private static final Executor sIOThreadExecutor = new a(1);

    private ArchTaskExecutor() {
        DefaultTaskExecutor defaultTaskExecutor = new DefaultTaskExecutor();
        this.mDefaultTaskExecutor = defaultTaskExecutor;
        this.mDelegate = defaultTaskExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            try {
                if (sInstance == null) {
                    sInstance = new ArchTaskExecutor();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sInstance;
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(Runnable runnable) {
        getInstance().postToMainThread(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$1(Runnable runnable) {
        getInstance().executeOnDiskIO(runnable);
    }

    @Override // androidx.arch.core.executor.TaskExecutor
    public void executeOnDiskIO(@NonNull Runnable runnable) {
        this.mDelegate.executeOnDiskIO(runnable);
    }

    @Override // androidx.arch.core.executor.TaskExecutor
    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }

    @Override // androidx.arch.core.executor.TaskExecutor
    public void postToMainThread(@NonNull Runnable runnable) {
        this.mDelegate.postToMainThread(runnable);
    }

    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        if (taskExecutor == null) {
            taskExecutor = this.mDefaultTaskExecutor;
        }
        this.mDelegate = taskExecutor;
    }
}
