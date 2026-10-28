package androidx.lifecycle;

import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class ComputableLiveData<T> {
    private final LiveData<T> _liveData;
    private final AtomicBoolean computing;
    private final Executor executor;
    private final AtomicBoolean invalid;
    public final Runnable invalidationRunnable;
    private final LiveData<T> liveData;
    public final Runnable refreshRunnable;

    /* JADX WARN: Multi-variable type inference failed */
    public ComputableLiveData() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @VisibleForTesting
    public static /* synthetic */ void getInvalidationRunnable$lifecycle_livedata_release$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getRefreshRunnable$lifecycle_livedata_release$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invalidationRunnable$lambda$1(ComputableLiveData this$0) {
        k.g(this$0, "this$0");
        boolean zHasActiveObservers = this$0.getLiveData().hasActiveObservers();
        if (this$0.invalid.compareAndSet(false, true) && zHasActiveObservers) {
            this$0.executor.execute(this$0.refreshRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void refreshRunnable$lambda$0(ComputableLiveData this$0) {
        k.g(this$0, "this$0");
        while (this$0.computing.compareAndSet(false, true)) {
            Object objCompute = null;
            boolean z2 = false;
            while (this$0.invalid.compareAndSet(true, false)) {
                try {
                    objCompute = this$0.compute();
                    z2 = true;
                } catch (Throwable th) {
                    this$0.computing.set(false);
                    throw th;
                }
            }
            if (z2) {
                this$0.getLiveData().postValue(objCompute);
            }
            this$0.computing.set(false);
            if (!z2 || !this$0.invalid.get()) {
                return;
            }
        }
    }

    @WorkerThread
    public abstract T compute();

    public final AtomicBoolean getComputing$lifecycle_livedata_release() {
        return this.computing;
    }

    public final Executor getExecutor$lifecycle_livedata_release() {
        return this.executor;
    }

    public final AtomicBoolean getInvalid$lifecycle_livedata_release() {
        return this.invalid;
    }

    public LiveData<T> getLiveData() {
        return this.liveData;
    }

    public void invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.invalidationRunnable);
    }

    public ComputableLiveData(Executor executor) {
        k.g(executor, "executor");
        this.executor = executor;
        LiveData<T> liveData = new LiveData<T>(this) { // from class: androidx.lifecycle.ComputableLiveData$_liveData$1
            final /* synthetic */ ComputableLiveData<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // androidx.lifecycle.LiveData
            public void onActive() {
                this.this$0.getExecutor$lifecycle_livedata_release().execute(this.this$0.refreshRunnable);
            }
        };
        this._liveData = liveData;
        this.liveData = liveData;
        final int i2 = 1;
        this.invalid = new AtomicBoolean(true);
        final int i3 = 0;
        this.computing = new AtomicBoolean(false);
        this.refreshRunnable = new Runnable(this) { // from class: androidx.lifecycle.a

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            public final /* synthetic */ ComputableLiveData f68c;

            {
                this.f68c = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i3;
                ComputableLiveData computableLiveData = this.f68c;
                switch (i4) {
                    case 0:
                        ComputableLiveData.refreshRunnable$lambda$0(computableLiveData);
                        break;
                    default:
                        ComputableLiveData.invalidationRunnable$lambda$1(computableLiveData);
                        break;
                }
            }
        };
        this.invalidationRunnable = new Runnable(this) { // from class: androidx.lifecycle.a

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            public final /* synthetic */ ComputableLiveData f68c;

            {
                this.f68c = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i2;
                ComputableLiveData computableLiveData = this.f68c;
                switch (i4) {
                    case 0:
                        ComputableLiveData.refreshRunnable$lambda$0(computableLiveData);
                        break;
                    default:
                        ComputableLiveData.invalidationRunnable$lambda$1(computableLiveData);
                        break;
                }
            }
        };
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ComputableLiveData(Executor executor, int i2, kotlin.jvm.internal.f fVar) {
        if ((i2 & 1) != 0) {
            executor = ArchTaskExecutor.getIOThreadExecutor();
            k.f(executor, "getIOThreadExecutor()");
        }
        this(executor);
    }
}
