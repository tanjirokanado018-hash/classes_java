package kotlinx.coroutines.scheduling;

import w.al;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class g extends al {
    public final b b;

    public g(int i2, int i3, long j2) {
        this.b = new b(i2, i3, j2, "DefaultDispatcher");
    }

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        b.c(this.b, runnable, false, 6);
    }

    @Override // w.s
    public final void dispatchYield(i.j jVar, Runnable runnable) {
        b.c(this.b, runnable, true, 2);
    }
}
