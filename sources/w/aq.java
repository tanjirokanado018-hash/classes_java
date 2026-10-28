package w;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class aq extends at {

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f526g = AtomicIntegerFieldUpdater.newUpdater(aq.class, "_invoked");
    private volatile /* synthetic */ int _invoked = 0;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final p.l f527f;

    public aq(au auVar) {
        this.f527f = auVar;
    }

    @Override // p.l
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m((Throwable) obj);
        return g.i.f423a;
    }

    @Override // w.au
    public final void m(Throwable th) {
        if (f526g.compareAndSet(this, 0, 1)) {
            this.f527f.invoke(th);
        }
    }
}
