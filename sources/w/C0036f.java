package w;

import androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$1;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: w.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class C0036f extends ae implements InterfaceC0035e, k.d {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f536h = AtomicIntegerFieldUpdater.newUpdater(C0036f.class, "_decision");

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f537i = AtomicReferenceFieldUpdater.newUpdater(C0036f.class, Object.class, "_state");
    private volatile /* synthetic */ int _decision;
    private volatile /* synthetic */ Object _state;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final i.e f538e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final i.j f539f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ag f540g;

    public C0036f(i.e eVar) {
        super(1);
        this.f538e = eVar;
        this.f539f = eVar.getContext();
        this._decision = 0;
        this._state = C0032b.b;
    }

    public static void p(Object obj, ContextAwareKt$withContextAvailable$2$1 contextAwareKt$withContextAvailable$2$1) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + contextAwareKt$withContextAvailable$2$1 + ", already has " + obj).toString());
    }

    @Override // w.ae
    public final void a(Object obj, CancellationException cancellationException) {
        while (true) {
            Object obj2 = this._state;
            if (obj2 instanceof C) {
                throw new IllegalStateException("Not completed".toString());
            }
            if (obj2 instanceof m) {
                return;
            }
            if (!(obj2 instanceof C0042l)) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f537i;
                C0042l c0042l = new C0042l(obj2, (AbstractC0034d) null, (p.l) null, cancellationException, 14);
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj2, c0042l)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj2) {
                    }
                }
                return;
            }
            C0042l c0042l2 = (C0042l) obj2;
            if (!(!(c0042l2.f546e != null))) {
                throw new IllegalStateException("Must be called at most once".toString());
            }
            C0042l c0042lA = C0042l.a(c0042l2, null, cancellationException, 15);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f537i;
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(this, obj2, c0042lA)) {
                    AbstractC0034d abstractC0034d = c0042l2.b;
                    if (abstractC0034d != null) {
                        h(abstractC0034d, cancellationException);
                    }
                    p.l lVar = c0042l2.f545c;
                    if (lVar != null) {
                        try {
                            lVar.invoke(cancellationException);
                            return;
                        } catch (Throwable th) {
                            kotlin.jvm.internal.k.j(this.f539f, new g.b("Exception in resume onCancellation handler for " + this, th));
                            return;
                        }
                    }
                    return;
                }
            } while (atomicReferenceFieldUpdater2.get(this) == obj2);
        }
    }

    @Override // w.ae
    public final i.e b() {
        return this.f538e;
    }

    @Override // w.ae
    public final Throwable c(Object obj) {
        Throwable thC = super.c(obj);
        if (thC != null) {
            return thC;
        }
        return null;
    }

    @Override // w.ae
    public final Object d(Object obj) {
        return obj instanceof C0042l ? ((C0042l) obj).f544a : obj;
    }

    @Override // w.ae
    public final Object f() {
        return this._state;
    }

    public final void g(ContextAwareKt$withContextAvailable$2$1 contextAwareKt$withContextAvailable$2$1, Throwable th) {
        try {
            contextAwareKt$withContextAvailable$2$1.invoke((Object) th);
        } catch (Throwable th2) {
            kotlin.jvm.internal.k.j(this.f539f, new g.b("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    @Override // k.d
    public final k.d getCallerFrame() {
        i.e eVar = this.f538e;
        if (eVar instanceof k.d) {
            return (k.d) eVar;
        }
        return null;
    }

    @Override // i.e
    public final i.j getContext() {
        return this.f539f;
    }

    public final void h(AbstractC0034d abstractC0034d, Throwable th) {
        try {
            ((ap) abstractC0034d).b.invoke(th);
        } catch (Throwable th2) {
            kotlin.jvm.internal.k.j(this.f539f, new g.b("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    public final void i(Throwable th) {
        ag agVar;
        while (true) {
            Object obj = this._state;
            if (!(obj instanceof C)) {
                return;
            }
            boolean z2 = obj instanceof AbstractC0034d;
            C0037g c0037g = new C0037g(this, th, z2);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f537i;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0037g)) {
                    AbstractC0034d abstractC0034d = z2 ? (AbstractC0034d) obj : null;
                    if (abstractC0034d != null) {
                        h(abstractC0034d, th);
                    }
                    if (!o() && (agVar = this.f540g) != null) {
                        agVar.b();
                        this.f540g = B.b;
                    }
                    j(this.d);
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(this) == obj);
        }
    }

    public final void j(int i2) {
        do {
            int i3 = this._decision;
            if (i3 != 0) {
                if (i3 != 1) {
                    throw new IllegalStateException("Already resumed".toString());
                }
                i.e eVar = this.f538e;
                boolean z2 = i2 == 4;
                if (z2 || !(eVar instanceof kotlinx.coroutines.internal.d) || kotlin.jvm.internal.k.l(i2) != kotlin.jvm.internal.k.l(this.d)) {
                    kotlin.jvm.internal.k.p(this, eVar, z2);
                    return;
                }
                s sVar = ((kotlinx.coroutines.internal.d) eVar).f445e;
                i.j context = eVar.getContext();
                if (sVar.isDispatchNeeded(context)) {
                    sVar.dispatch(context, this);
                    return;
                }
                ak akVarA = H.a();
                if (akVarA.b >= 4294967296L) {
                    akVarA.c(this);
                    return;
                }
                akVarA.e(true);
                try {
                    kotlin.jvm.internal.k.p(this, this.f538e, true);
                    do {
                    } while (akVarA.f());
                } catch (Throwable th) {
                    try {
                        e(th, null);
                    } finally {
                        akVarA.b();
                    }
                }
                return;
            }
        } while (!f536h.compareAndSet(this, 0, 2));
    }

    public final Object k() throws Throwable {
        B b;
        ar arVar;
        kotlinx.coroutines.internal.d dVar;
        Throwable thJ;
        Throwable thJ2;
        boolean zO = o();
        do {
            int i2 = this._decision;
            b = B.b;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new IllegalStateException("Already suspended".toString());
                }
                if (zO) {
                    i.e eVar = this.f538e;
                    dVar = eVar instanceof kotlinx.coroutines.internal.d ? (kotlinx.coroutines.internal.d) eVar : null;
                    if (dVar != null && (thJ = dVar.j(this)) != null) {
                        ag agVar = this.f540g;
                        if (agVar != null) {
                            agVar.b();
                            this.f540g = b;
                        }
                        i(thJ);
                    }
                }
                Object obj = this._state;
                if (obj instanceof m) {
                    throw ((m) obj).f547a;
                }
                if (!kotlin.jvm.internal.k.l(this.d) || (arVar = (ar) this.f539f.get(t.f551c)) == null || arVar.a()) {
                    return d(obj);
                }
                CancellationException cancellationExceptionL = ((ay) arVar).l();
                a(obj, cancellationExceptionL);
                throw cancellationExceptionL;
            }
        } while (!f536h.compareAndSet(this, 0, 1));
        if (this.f540g == null) {
            m();
        }
        if (zO) {
            i.e eVar2 = this.f538e;
            dVar = eVar2 instanceof kotlinx.coroutines.internal.d ? (kotlinx.coroutines.internal.d) eVar2 : null;
            if (dVar != null && (thJ2 = dVar.j(this)) != null) {
                ag agVar2 = this.f540g;
                if (agVar2 != null) {
                    agVar2.b();
                    this.f540g = b;
                }
                i(thJ2);
            }
        }
        return j.a.b;
    }

    public final void l() {
        ag agVarM = m();
        if (agVarM != null && (!(this._state instanceof C))) {
            agVarM.b();
            this.f540g = B.b;
        }
    }

    public final ag m() {
        ar arVar = (ar) this.f539f.get(t.f551c);
        if (arVar == null) {
            return null;
        }
        ag agVarK = kotlin.jvm.internal.k.k(arVar, true, new C0038h(this), 2);
        this.f540g = agVarK;
        return agVarK;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void n(ContextAwareKt$withContextAvailable$2$1 contextAwareKt$withContextAvailable$2$1) {
        AbstractC0034d apVar = contextAwareKt$withContextAvailable$2$1 instanceof AbstractC0034d ? (AbstractC0034d) contextAwareKt$withContextAvailable$2$1 : new ap(contextAwareKt$withContextAvailable$2$1);
        while (true) {
            Object obj = this._state;
            if (obj instanceof C0032b) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f537i;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, apVar)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj) {
                    }
                }
                return;
            }
            if (obj instanceof AbstractC0034d) {
                p(obj, contextAwareKt$withContextAvailable$2$1);
                throw null;
            }
            boolean z2 = obj instanceof m;
            if (z2) {
                m mVar = (m) obj;
                mVar.getClass();
                if (!m.b.compareAndSet(mVar, 0, 1)) {
                    p(obj, contextAwareKt$withContextAvailable$2$1);
                    throw null;
                }
                if (obj instanceof C0037g) {
                    if (!z2) {
                        mVar = null;
                    }
                    g(contextAwareKt$withContextAvailable$2$1, mVar != null ? mVar.f547a : null);
                    return;
                }
                return;
            }
            if (!(obj instanceof C0042l)) {
                C0042l c0042l = new C0042l(obj, apVar, (p.l) null, (CancellationException) null, 28);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f537i;
                while (!atomicReferenceFieldUpdater2.compareAndSet(this, obj, c0042l)) {
                    if (atomicReferenceFieldUpdater2.get(this) != obj) {
                    }
                }
                return;
            }
            C0042l c0042l2 = (C0042l) obj;
            if (c0042l2.b != null) {
                p(obj, contextAwareKt$withContextAvailable$2$1);
                throw null;
            }
            Throwable th = c0042l2.f546e;
            if (th != null) {
                g(contextAwareKt$withContextAvailable$2$1, th);
                return;
            }
            C0042l c0042lA = C0042l.a(c0042l2, apVar, null, 29);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = f537i;
            while (!atomicReferenceFieldUpdater3.compareAndSet(this, obj, c0042lA)) {
                if (atomicReferenceFieldUpdater3.get(this) != obj) {
                }
            }
            return;
        }
    }

    public final boolean o() {
        return this.d == 2 && ((kotlinx.coroutines.internal.d) this.f538e).g();
    }

    @Override // i.e
    public final void resumeWith(Object obj) {
        Object c0042l;
        ag agVar;
        boolean z2;
        Throwable thA = g.f.a(obj);
        if (thA != null) {
            obj = new m(false, thA);
        }
        int i2 = this.d;
        while (true) {
            Object obj2 = this._state;
            if (!(obj2 instanceof C)) {
                if (obj2 instanceof C0037g) {
                    C0037g c0037g = (C0037g) obj2;
                    c0037g.getClass();
                    if (C0037g.f541c.compareAndSet(c0037g, 0, 1)) {
                        return;
                    }
                }
                throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
            }
            C c2 = (C) obj2;
            if (!(obj instanceof m) && kotlin.jvm.internal.k.l(i2) && ((z2 = c2 instanceof AbstractC0034d))) {
                c0042l = new C0042l(obj, z2 ? (AbstractC0034d) c2 : null, (p.l) null, (CancellationException) null, 16);
            } else {
                c0042l = obj;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f537i;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj2, c0042l)) {
                    if (!o() && (agVar = this.f540g) != null) {
                        agVar.b();
                        this.f540g = B.b;
                    }
                    j(i2);
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(this) == obj2);
        }
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("CancellableContinuation(");
        sb.append(y.ak(this.f538e));
        sb.append("){");
        Object obj = this._state;
        if (obj instanceof C) {
            str = "Active";
        } else {
            str = obj instanceof C0037g ? "Cancelled" : "Completed";
        }
        sb.append(str);
        sb.append("}@");
        sb.append(y.m(this));
        return sb.toString();
    }
}
