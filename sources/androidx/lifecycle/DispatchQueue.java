package androidx.lifecycle;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import i.j;
import java.util.ArrayDeque;
import java.util.Queue;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import w.af;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class DispatchQueue {
    private boolean finished;
    private boolean isDraining;
    private boolean paused = true;
    private final Queue<Runnable> queue = new ArrayDeque();

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dispatchAndEnqueue$lambda$2$lambda$1(DispatchQueue this$0, Runnable runnable) {
        k.g(this$0, "this$0");
        k.g(runnable, "$runnable");
        this$0.enqueue(runnable);
    }

    @MainThread
    private final void enqueue(Runnable runnable) {
        if (!this.queue.offer(runnable)) {
            throw new IllegalStateException("cannot enqueue any more runnables".toString());
        }
        drainQueue();
    }

    @MainThread
    public final boolean canRun() {
        return this.finished || !this.paused;
    }

    @AnyThread
    public final void dispatchAndEnqueue(j context, Runnable runnable) {
        k.g(context, "context");
        k.g(runnable, "runnable");
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        x.c cVar = ((x.c) l.f459a).f562e;
        if (cVar.isDispatchNeeded(context) || canRun()) {
            cVar.dispatch(context, new androidx.core.content.res.a(3, this, runnable));
        } else {
            enqueue(runnable);
        }
    }

    @MainThread
    public final void drainQueue() {
        if (this.isDraining) {
            return;
        }
        try {
            this.isDraining = true;
            while ((!this.queue.isEmpty()) && canRun()) {
                Runnable runnablePoll = this.queue.poll();
                if (runnablePoll != null) {
                    runnablePoll.run();
                }
            }
            this.isDraining = false;
        } catch (Throwable th) {
            this.isDraining = false;
            throw th;
        }
    }

    @MainThread
    public final void finish() {
        this.finished = true;
        drainQueue();
    }

    @MainThread
    public final void pause() {
        this.paused = true;
    }

    @MainThread
    public final void resume() {
        if (this.paused) {
            if (!(!this.finished)) {
                throw new IllegalStateException("Cannot resume a finished dispatcher".toString());
            }
            this.paused = false;
            drainQueue();
        }
    }
}
