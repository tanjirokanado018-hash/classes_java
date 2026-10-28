package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class g {
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(g.class, Object.class, "_next");

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f451c = AtomicReferenceFieldUpdater.newUpdater(g.class, Object.class, "_prev");
    public static final /* synthetic */ AtomicReferenceFieldUpdater d = AtomicReferenceFieldUpdater.newUpdater(g.class, Object.class, "_removedRef");
    volatile /* synthetic */ Object _next = this;
    volatile /* synthetic */ Object _prev = this;
    private volatile /* synthetic */ Object _removedRef = null;

    public final g e() {
        Object obj;
        while (true) {
            g gVar = (g) this._prev;
            g gVar2 = gVar;
            while (true) {
                g gVar3 = null;
                while (true) {
                    obj = gVar2._next;
                    if (obj == this) {
                        if (gVar == gVar2) {
                            return gVar2;
                        }
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f451c;
                        while (!atomicReferenceFieldUpdater.compareAndSet(this, gVar, gVar2)) {
                            if (atomicReferenceFieldUpdater.get(this) != gVar) {
                                break;
                            }
                        }
                        return gVar2;
                    }
                    if (j()) {
                        return null;
                    }
                    if (obj == null) {
                        return gVar2;
                    }
                    if (obj instanceof m) {
                        ((m) obj).a(gVar2);
                        break;
                    }
                    if (!(obj instanceof n)) {
                        gVar3 = gVar2;
                        gVar2 = (g) obj;
                    } else {
                        if (gVar3 != null) {
                            break;
                        }
                        gVar2 = (g) gVar2._prev;
                    }
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = b;
                g gVar4 = ((n) obj).f460a;
                while (!atomicReferenceFieldUpdater2.compareAndSet(gVar3, gVar2, gVar4)) {
                    if (atomicReferenceFieldUpdater2.get(gVar3) != gVar2) {
                        break;
                    }
                }
                gVar2 = gVar3;
            }
        }
    }

    public final void f(g gVar) {
        while (true) {
            g gVar2 = (g) gVar._prev;
            if (g() != gVar) {
                return;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f451c;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(gVar, gVar2, this)) {
                    if (j()) {
                        gVar.e();
                        return;
                    }
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(gVar) == gVar2);
        }
    }

    public final Object g() {
        while (true) {
            Object obj = this._next;
            if (!(obj instanceof m)) {
                return obj;
            }
            ((m) obj).a(this);
        }
    }

    public final g h() {
        g gVar;
        Object objG = g();
        n nVar = objG instanceof n ? (n) objG : null;
        return (nVar == null || (gVar = nVar.f460a) == null) ? (g) objG : gVar;
    }

    public final g i() {
        g gVarE = e();
        if (gVarE == null) {
            Object obj = this._prev;
            while (true) {
                gVarE = (g) obj;
                if (!gVarE.j()) {
                    break;
                }
                obj = gVarE._prev;
            }
        }
        return gVarE;
    }

    public boolean j() {
        return g() instanceof n;
    }

    public final void k() {
        while (true) {
            Object objG = g();
            if (objG instanceof n) {
                g gVar = ((n) objG).f460a;
                return;
            }
            if (objG == this) {
                return;
            }
            g gVar2 = (g) objG;
            n nVar = (n) gVar2._removedRef;
            if (nVar == null) {
                nVar = new n(gVar2);
                d.lazySet(gVar2, nVar);
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(this, objG, nVar)) {
                    gVar2.e();
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(this) == objG);
        }
    }

    public String toString() {
        return new f(this) + '@' + y.m(this);
    }
}
