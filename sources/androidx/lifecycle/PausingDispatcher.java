package androidx.lifecycle;

import i.j;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import w.af;
import w.s;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PausingDispatcher extends s {
    public final DispatchQueue dispatchQueue = new DispatchQueue();

    @Override // w.s
    public void dispatch(j context, Runnable block) {
        k.g(context, "context");
        k.g(block, "block");
        this.dispatchQueue.dispatchAndEnqueue(context, block);
    }

    @Override // w.s
    public boolean isDispatchNeeded(j context) {
        k.g(context, "context");
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        if (((x.c) l.f459a).f562e.isDispatchNeeded(context)) {
            return true;
        }
        return !this.dispatchQueue.canRun();
    }
}
