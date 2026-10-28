package w;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ay implements ar, InterfaceC0041k, D {
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(ay.class, Object.class, "_state");
    private volatile /* synthetic */ Object _parentHandle;
    private volatile /* synthetic */ Object _state;

    public ay(boolean z2) {
        this._state = z2 ? y.f558h : y.f557g;
        this._parentHandle = null;
    }

    public static String ac(Object obj) {
        if (!(obj instanceof aw)) {
            if (obj instanceof an) {
                return ((an) obj).a() ? "Active" : "New";
            }
            return obj instanceof m ? "Cancelled" : "Completed";
        }
        aw awVar = (aw) obj;
        if (awVar.e()) {
            return "Cancelling";
        }
        return awVar.f() ? "Completing" : "Active";
    }

    public static C0040j x(kotlinx.coroutines.internal.g gVar) {
        while (gVar.j()) {
            gVar = gVar.i();
        }
        while (true) {
            gVar = gVar.h();
            if (!gVar.j()) {
                if (gVar instanceof C0040j) {
                    return (C0040j) gVar;
                }
                if (gVar instanceof A) {
                    return null;
                }
            }
        }
    }

    @Override // w.ar
    public boolean a() {
        Object objQ = q();
        return (objQ instanceof an) && ((an) objQ).a();
    }

    public final void ab(au auVar) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        A a2 = new A();
        auVar.getClass();
        kotlinx.coroutines.internal.g.f451c.lazySet(a2, auVar);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = kotlinx.coroutines.internal.g.b;
        atomicReferenceFieldUpdater2.lazySet(a2, auVar);
        loop0: while (auVar.g() == auVar) {
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(auVar, auVar, a2)) {
                    a2.f(auVar);
                    break loop0;
                }
            } while (atomicReferenceFieldUpdater2.get(auVar) == auVar);
        }
        kotlinx.coroutines.internal.g gVarH = auVar.h();
        do {
            atomicReferenceFieldUpdater = b;
            if (atomicReferenceFieldUpdater.compareAndSet(this, auVar, gVarH)) {
                return;
            }
        } while (atomicReferenceFieldUpdater.get(this) == auVar);
    }

    public final Object ad(Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        kotlinx.coroutines.internal.q qVar;
        if (!(obj instanceof an)) {
            return y.b;
        }
        if (((obj instanceof ah) || (obj instanceof au)) && !(obj instanceof C0040j) && !(obj2 instanceof m)) {
            an anVar = (an) obj;
            Object aoVar = obj2 instanceof an ? new ao((an) obj2) : obj2;
            do {
                atomicReferenceFieldUpdater = b;
                if (atomicReferenceFieldUpdater.compareAndSet(this, anVar, aoVar)) {
                    z(obj2);
                    i(anVar, obj2);
                    return obj2;
                }
            } while (atomicReferenceFieldUpdater.get(this) == anVar);
            return y.d;
        }
        an anVar2 = (an) obj;
        A aO = o(anVar2);
        if (aO == null) {
            return y.d;
        }
        C0040j c0040jX = null;
        aw awVar = anVar2 instanceof aw ? (aw) anVar2 : null;
        if (awVar == null) {
            awVar = new aw(aO, null);
        }
        synchronized (awVar) {
            try {
                if (!awVar.f()) {
                    awVar.i();
                    if (awVar != anVar2) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = b;
                        while (!atomicReferenceFieldUpdater2.compareAndSet(this, anVar2, awVar)) {
                            if (atomicReferenceFieldUpdater2.get(this) != anVar2) {
                                qVar = y.d;
                            }
                        }
                    }
                    boolean zE = awVar.e();
                    m mVar = obj2 instanceof m ? (m) obj2 : null;
                    if (mVar != null) {
                        awVar.b(mVar.f547a);
                    }
                    Throwable thC = awVar.c();
                    if (!(!zE)) {
                        thC = null;
                    }
                    if (thC != null) {
                        y(aO, thC);
                    }
                    C0040j c0040j = anVar2 instanceof C0040j ? (C0040j) anVar2 : null;
                    if (c0040j == null) {
                        A aD = anVar2.d();
                        if (aD != null) {
                            c0040jX = x(aD);
                        }
                    } else {
                        c0040jX = c0040j;
                    }
                    if (c0040jX != null) {
                        while (kotlin.jvm.internal.k.k(c0040jX.f543f, false, new av(this, awVar, c0040jX, obj2), 1) == B.b) {
                            c0040jX = x(c0040jX);
                            if (c0040jX == null) {
                            }
                        }
                        return y.f554c;
                    }
                    return k(awVar, obj2);
                }
                qVar = y.b;
                return qVar;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean b(Object obj, A a2, au auVar) {
        char c2;
        ax axVar = new ax(auVar, this, obj);
        do {
            kotlinx.coroutines.internal.g gVarI = a2.i();
            kotlinx.coroutines.internal.g.f451c.lazySet(auVar, gVarI);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = kotlinx.coroutines.internal.g.b;
            atomicReferenceFieldUpdater.lazySet(auVar, a2);
            axVar.f533c = a2;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(gVarI, a2, axVar)) {
                    if (axVar.a(gVarI) != null) {
                        c2 = 2;
                        break;
                    }
                    c2 = 1;
                    break;
                }
                if (atomicReferenceFieldUpdater.get(gVarI) != a2) {
                    c2 = 0;
                    break;
                }
            }
            if (c2 == 1) {
                return true;
            }
        } while (c2 != 2);
        return false;
    }

    public void d(Object obj) {
        c(obj);
    }

    public final boolean e(Object obj) {
        kotlinx.coroutines.internal.q qVar;
        Object objAd = y.b;
        if (n()) {
            do {
                Object objQ = q();
                if (!(objQ instanceof an) || ((objQ instanceof aw) && ((aw) objQ).f())) {
                    objAd = y.b;
                    break;
                }
                objAd = ad(objQ, new m(false, j(obj)));
            } while (objAd == y.d);
            if (objAd == y.f554c) {
                return true;
            }
        }
        if (objAd == y.b) {
            Throwable thJ = null;
            while (true) {
                Object objQ2 = q();
                if (objQ2 instanceof aw) {
                    synchronized (objQ2) {
                        try {
                            if (((aw) objQ2).g()) {
                                qVar = y.f555e;
                            } else {
                                boolean zE = ((aw) objQ2).e();
                                if (thJ == null) {
                                    thJ = j(obj);
                                }
                                ((aw) objQ2).b(thJ);
                                Throwable thC = zE ^ true ? ((aw) objQ2).c() : null;
                                if (thC != null) {
                                    y(((aw) objQ2).b, thC);
                                }
                                qVar = y.b;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    objAd = qVar;
                } else if (objQ2 instanceof an) {
                    if (thJ == null) {
                        thJ = j(obj);
                    }
                    an anVar = (an) objQ2;
                    if (anVar.a()) {
                        A aO = o(anVar);
                        if (aO == null) {
                            continue;
                        } else {
                            aw awVar = new aw(aO, thJ);
                            while (true) {
                                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
                                if (atomicReferenceFieldUpdater.compareAndSet(this, anVar, awVar)) {
                                    y(aO, thJ);
                                    qVar = y.b;
                                    objAd = qVar;
                                } else if (atomicReferenceFieldUpdater.get(this) != anVar) {
                                }
                            }
                        }
                    } else {
                        Object objAd2 = ad(objQ2, new m(false, thJ));
                        if (objAd2 == y.b) {
                            throw new IllegalStateException(("Cannot happen in " + objQ2).toString());
                        }
                        if (objAd2 != y.d) {
                            objAd = objAd2;
                        }
                    }
                } else {
                    qVar = y.f555e;
                    objAd = qVar;
                }
            }
        }
        if (objAd != y.b && objAd != y.f554c) {
            if (objAd == y.f555e) {
                return false;
            }
            c(objAd);
        }
        return true;
    }

    public final boolean f(Throwable th) {
        if (u()) {
            return true;
        }
        boolean z2 = th instanceof CancellationException;
        InterfaceC0039i interfaceC0039i = (InterfaceC0039i) this._parentHandle;
        if (interfaceC0039i == null || interfaceC0039i == B.b) {
            return z2;
        }
        return interfaceC0039i.c(th) || z2;
    }

    @Override // i.j
    public final Object fold(Object obj, p.p pVar) {
        return pVar.invoke(obj, this);
    }

    public String g() {
        return "Job was cancelled";
    }

    @Override // i.j
    public final i.h get(i.i iVar) {
        return y.j(this, iVar);
    }

    @Override // i.h
    public final i.i getKey() {
        return t.f551c;
    }

    public boolean h(Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        return e(th) && m();
    }

    public final void i(an anVar, Object obj) {
        InterfaceC0039i interfaceC0039i = (InterfaceC0039i) this._parentHandle;
        if (interfaceC0039i != null) {
            interfaceC0039i.b();
            this._parentHandle = B.b;
        }
        g.b bVar = null;
        m mVar = obj instanceof m ? (m) obj : null;
        Throwable th = mVar != null ? mVar.f547a : null;
        if (anVar instanceof au) {
            try {
                ((au) anVar).m(th);
                return;
            } catch (Throwable th2) {
                s(new g.b("Exception in completion handler " + anVar + " for " + this, th2));
                return;
            }
        }
        A aD = anVar.d();
        if (aD != null) {
            for (kotlinx.coroutines.internal.g gVarH = (kotlinx.coroutines.internal.g) aD.g(); !kotlin.jvm.internal.k.a(gVarH, aD); gVarH = gVarH.h()) {
                if (gVarH instanceof au) {
                    au auVar = (au) gVarH;
                    try {
                        auVar.m(th);
                    } catch (Throwable th3) {
                        if (bVar != null) {
                            y.a(bVar, th3);
                        } else {
                            bVar = new g.b("Exception in completion handler " + auVar + " for " + this, th3);
                        }
                    }
                }
            }
            if (bVar != null) {
                s(bVar);
            }
        }
    }

    public final Throwable j(Object obj) {
        Throwable thC;
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        ay ayVar = (ay) ((D) obj);
        Object objQ = ayVar.q();
        if (objQ instanceof aw) {
            thC = ((aw) objQ).c();
        } else if (objQ instanceof m) {
            thC = ((m) objQ).f547a;
        } else {
            if (objQ instanceof an) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + objQ).toString());
            }
            thC = null;
        }
        CancellationException asVar = thC instanceof CancellationException ? (CancellationException) thC : null;
        if (asVar == null) {
            asVar = new as("Parent job is ".concat(ac(objQ)), thC, ayVar);
        }
        return asVar;
    }

    public final Object k(aw awVar, Object obj) {
        Object obj2 = null;
        Throwable asVar = null;
        m mVar = obj instanceof m ? (m) obj : null;
        Throwable th = mVar != null ? mVar.f547a : null;
        synchronized (awVar) {
            awVar.e();
            ArrayList<Throwable> arrayListH = awVar.h(th);
            if (!arrayListH.isEmpty()) {
                for (Object obj3 : arrayListH) {
                    if (!(((Throwable) obj3) instanceof CancellationException)) {
                        obj2 = obj3;
                        break;
                    }
                }
                asVar = (Throwable) obj2;
                if (asVar == null) {
                    asVar = (Throwable) arrayListH.get(0);
                }
            } else if (awVar.e()) {
                asVar = new as(g(), null, this);
            }
            if (asVar != null && arrayListH.size() > 1) {
                Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(arrayListH.size()));
                for (Throwable th2 : arrayListH) {
                    if (th2 != asVar && th2 != asVar && !(th2 instanceof CancellationException) && setNewSetFromMap.add(th2)) {
                        y.a(asVar, th2);
                    }
                }
            }
        }
        if (asVar != null && asVar != th) {
            obj = new m(false, asVar);
        }
        if (asVar != null && (f(asVar) || r(asVar))) {
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
            }
            m.b.compareAndSet((m) obj, 0, 1);
        }
        z(obj);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
        Object aoVar = obj instanceof an ? new ao((an) obj) : obj;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, awVar, aoVar) && atomicReferenceFieldUpdater.get(this) == awVar) {
        }
        i(awVar, obj);
        return obj;
    }

    public final CancellationException l() {
        CancellationException cancellationException;
        Object objQ = q();
        if (!(objQ instanceof aw)) {
            if (objQ instanceof an) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (!(objQ instanceof m)) {
                return new as(getClass().getSimpleName().concat(" has completed normally"), null, this);
            }
            Throwable th = ((m) objQ).f547a;
            cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            return cancellationException == null ? new as(g(), th, this) : cancellationException;
        }
        Throwable thC = ((aw) objQ).c();
        if (thC == null) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        }
        String strConcat = getClass().getSimpleName().concat(" is cancelling");
        cancellationException = thC instanceof CancellationException ? (CancellationException) thC : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        if (strConcat == null) {
            strConcat = g();
        }
        return new as(strConcat, thC, this);
    }

    public boolean m() {
        return true;
    }

    @Override // i.j
    public final i.j minusKey(i.i iVar) {
        return y.y(this, iVar);
    }

    public boolean n() {
        return false;
    }

    public final A o(an anVar) {
        A aD = anVar.d();
        if (aD != null) {
            return aD;
        }
        if (anVar instanceof ah) {
            return new A();
        }
        if (anVar instanceof au) {
            ab((au) anVar);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + anVar).toString());
    }

    public final InterfaceC0039i p() {
        return (InterfaceC0039i) this._parentHandle;
    }

    @Override // i.j
    public final i.j plus(i.j context) {
        kotlin.jvm.internal.k.g(context, "context");
        return y.ac(this, context);
    }

    public final Object q() {
        while (true) {
            Object obj = this._state;
            if (!(obj instanceof kotlinx.coroutines.internal.m)) {
                return obj;
            }
            ((kotlinx.coroutines.internal.m) obj).a(this);
        }
    }

    public boolean r(Throwable th) {
        return false;
    }

    public final void t(ar arVar) {
        byte b2;
        B b3 = B.b;
        if (arVar == null) {
            this._parentHandle = b3;
            return;
        }
        ay ayVar = (ay) arVar;
        do {
            Object objQ = ayVar.q();
            boolean z2 = objQ instanceof ah;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            b2 = 0;
            if (z2) {
                if (!((ah) objQ).b) {
                    ah ahVar = y.f558h;
                    while (true) {
                        if (atomicReferenceFieldUpdater.compareAndSet(ayVar, objQ, ahVar)) {
                            ayVar.aa();
                            b2 = 1;
                        } else if (atomicReferenceFieldUpdater.get(ayVar) != objQ) {
                            b2 = -1;
                        }
                    }
                }
            } else if (objQ instanceof am) {
                A a2 = ((am) objQ).b;
                while (true) {
                    if (atomicReferenceFieldUpdater.compareAndSet(ayVar, objQ, a2)) {
                        ayVar.aa();
                        b2 = 1;
                    } else if (atomicReferenceFieldUpdater.get(ayVar) != objQ) {
                        b2 = -1;
                    }
                }
            }
            if (b2 == 0) {
                break;
            }
        } while (b2 != 1);
        InterfaceC0039i interfaceC0039i = (InterfaceC0039i) kotlin.jvm.internal.k.k(ayVar, true, new C0040j(this), 2);
        this._parentHandle = interfaceC0039i;
        if (!(q() instanceof an)) {
            interfaceC0039i.b();
            this._parentHandle = b3;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(w() + '{' + ac(q()) + '}');
        sb.append('@');
        sb.append(y.m(this));
        return sb.toString();
    }

    public boolean u() {
        return false;
    }

    public final Object v(Object obj) {
        Object objAd;
        do {
            objAd = ad(q(), obj);
            if (objAd == y.b) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                m mVar = obj instanceof m ? (m) obj : null;
                throw new IllegalStateException(str, mVar != null ? mVar.f547a : null);
            }
        } while (objAd == y.d);
        return objAd;
    }

    public String w() {
        return getClass().getSimpleName();
    }

    public final void y(A a2, Throwable th) {
        g.b bVar = null;
        for (kotlinx.coroutines.internal.g gVarH = (kotlinx.coroutines.internal.g) a2.g(); !kotlin.jvm.internal.k.a(gVarH, a2); gVarH = gVarH.h()) {
            if (gVarH instanceof at) {
                au auVar = (au) gVarH;
                try {
                    auVar.m(th);
                } catch (Throwable th2) {
                    if (bVar != null) {
                        y.a(bVar, th2);
                    } else {
                        bVar = new g.b("Exception in completion handler " + auVar + " for " + this, th2);
                    }
                }
            }
        }
        if (bVar != null) {
            s(bVar);
        }
        f(th);
    }

    public void c(Object obj) {
    }

    public void s(g.b bVar) {
        throw bVar;
    }

    public void z(Object obj) {
    }

    public void aa() {
    }
}
