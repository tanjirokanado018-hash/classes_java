package androidx.lifecycle;

import androidx.annotation.MainThread;
import kotlin.jvm.internal.k;
import w.ar;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@MainThread
public final class LifecycleController {
    private final DispatchQueue dispatchQueue;
    private final Lifecycle lifecycle;
    private final Lifecycle.State minState;
    private final LifecycleEventObserver observer;

    public LifecycleController(Lifecycle lifecycle, Lifecycle.State minState, DispatchQueue dispatchQueue, final ar parentJob) {
        k.g(lifecycle, "lifecycle");
        k.g(minState, "minState");
        k.g(dispatchQueue, "dispatchQueue");
        k.g(parentJob, "parentJob");
        this.lifecycle = lifecycle;
        this.minState = minState;
        this.dispatchQueue = dispatchQueue;
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.lifecycle.d
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                LifecycleController.observer$lambda$0(this.b, parentJob, lifecycleOwner, event);
            }
        };
        this.observer = lifecycleEventObserver;
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(lifecycleEventObserver);
        } else {
            k.c(parentJob);
            finish();
        }
    }

    private final void handleDestroy(ar arVar) {
        k.c(arVar);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observer$lambda$0(LifecycleController this$0, ar parentJob, LifecycleOwner source, Lifecycle.Event event) {
        k.g(this$0, "this$0");
        k.g(parentJob, "$parentJob");
        k.g(source, "source");
        k.g(event, "<anonymous parameter 1>");
        if (source.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            k.c(parentJob);
            this$0.finish();
            return;
        }
        int iCompareTo = source.getLifecycle().getCurrentState().compareTo(this$0.minState);
        DispatchQueue dispatchQueue = this$0.dispatchQueue;
        if (iCompareTo < 0) {
            dispatchQueue.pause();
        } else {
            dispatchQueue.resume();
        }
    }

    @MainThread
    public final void finish() {
        this.lifecycle.removeObserver(this.observer);
        this.dispatchQueue.finish();
    }
}
