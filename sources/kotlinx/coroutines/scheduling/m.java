package kotlinx.coroutines.scheduling;

import w.s;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class m extends s {
    public static final m b = new m();

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        d dVar = d.f482c;
        dVar.b.b(runnable, l.f490g, false);
    }

    @Override // w.s
    public final void dispatchYield(i.j jVar, Runnable runnable) {
        d dVar = d.f482c;
        dVar.b.b(runnable, l.f490g, true);
    }
}
