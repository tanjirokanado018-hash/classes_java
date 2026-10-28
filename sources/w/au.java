package w;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class au extends kotlinx.coroutines.internal.g implements ag, an, p.l {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ay f528e;

    @Override // w.an
    public final boolean a() {
        return true;
    }

    @Override // w.ag
    public final void b() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        ay ayVarL = l();
        while (true) {
            Object objQ = ayVarL.q();
            if (!(objQ instanceof au)) {
                if (!(objQ instanceof an) || ((an) objQ).d() == null) {
                    return;
                }
                k();
                return;
            }
            if (objQ != this) {
                return;
            }
            ah ahVar = y.f558h;
            do {
                atomicReferenceFieldUpdater = ay.b;
                if (atomicReferenceFieldUpdater.compareAndSet(ayVarL, objQ, ahVar)) {
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(ayVarL) == objQ);
        }
    }

    @Override // w.an
    public final A d() {
        return null;
    }

    public final ay l() {
        ay ayVar = this.f528e;
        if (ayVar != null) {
            return ayVar;
        }
        g.b bVar = new g.b("lateinit property job has not been initialized");
        kotlin.jvm.internal.k.q(kotlin.jvm.internal.k.class.getName(), bVar);
        throw bVar;
    }

    public abstract void m(Throwable th);

    @Override // kotlinx.coroutines.internal.g
    public final String toString() {
        return getClass().getSimpleName() + '@' + y.m(this) + "[job@" + y.m(l()) + ']';
    }
}
