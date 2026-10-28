package kotlinx.coroutines.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import w.B;
import w.C0036f;
import w.H;
import w.InterfaceC0035e;
import w.ae;
import w.ag;
import w.ak;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d extends ae implements k.d, i.e {

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f444i = AtomicReferenceFieldUpdater.newUpdater(d.class, Object.class, "_reusableCancellableContinuation");
    private volatile /* synthetic */ Object _reusableCancellableContinuation;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final w.s f445e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final i.e f446f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public Object f447g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final Object f448h;

    public d(w.s sVar, i.e eVar) {
        super(-1);
        this.f445e = sVar;
        this.f446f = eVar;
        this.f447g = b.b;
        Object objFold = eVar.getContext().fold(0, s.f463c);
        kotlin.jvm.internal.k.d(objFold);
        this.f448h = objFold;
        this._reusableCancellableContinuation = null;
    }

    @Override // w.ae
    public final void a(Object obj, CancellationException cancellationException) {
        if (obj instanceof w.n) {
            ((w.n) obj).b.invoke(cancellationException);
        }
    }

    @Override // w.ae
    public final Object f() {
        Object obj = this.f447g;
        this.f447g = b.b;
        return obj;
    }

    public final boolean g() {
        return this._reusableCancellableContinuation != null;
    }

    @Override // k.d
    public final k.d getCallerFrame() {
        i.e eVar = this.f446f;
        if (eVar instanceof k.d) {
            return (k.d) eVar;
        }
        return null;
    }

    @Override // i.e
    public final i.j getContext() {
        return this.f446f.getContext();
    }

    public final boolean h(CancellationException cancellationException) {
        while (true) {
            Object obj = this._reusableCancellableContinuation;
            q qVar = b.f441c;
            if (kotlin.jvm.internal.k.a(obj, qVar)) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f444i;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, qVar, cancellationException)) {
                    if (atomicReferenceFieldUpdater.get(this) != qVar) {
                    }
                }
                return true;
            }
            if (obj instanceof Throwable) {
                return true;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f444i;
            while (!atomicReferenceFieldUpdater2.compareAndSet(this, obj, null)) {
                if (atomicReferenceFieldUpdater2.get(this) != obj) {
                }
            }
            return false;
        }
    }

    public final void i() {
        ag agVar;
        Object obj = this._reusableCancellableContinuation;
        C0036f c0036f = obj instanceof C0036f ? (C0036f) obj : null;
        if (c0036f == null || (agVar = c0036f.f540g) == null) {
            return;
        }
        agVar.b();
        c0036f.f540g = B.b;
    }

    public final Throwable j(InterfaceC0035e interfaceC0035e) {
        while (true) {
            Object obj = this._reusableCancellableContinuation;
            q qVar = b.f441c;
            if (obj == qVar) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f444i;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, qVar, interfaceC0035e)) {
                    if (atomicReferenceFieldUpdater.get(this) != qVar) {
                    }
                }
                return null;
            }
            if (!(obj instanceof Throwable)) {
                throw new IllegalStateException(("Inconsistent state " + obj).toString());
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f444i;
            while (!atomicReferenceFieldUpdater2.compareAndSet(this, obj, null)) {
                if (atomicReferenceFieldUpdater2.get(this) != obj) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            return (Throwable) obj;
        }
    }

    @Override // i.e
    public final void resumeWith(Object obj) {
        i.e eVar = this.f446f;
        i.j context = eVar.getContext();
        Throwable thA = g.f.a(obj);
        Object mVar = thA == null ? obj : new w.m(false, thA);
        w.s sVar = this.f445e;
        if (sVar.isDispatchNeeded(context)) {
            this.f447g = mVar;
            this.d = 0;
            sVar.dispatch(context, this);
            return;
        }
        ak akVarA = H.a();
        if (akVarA.b >= 4294967296L) {
            this.f447g = mVar;
            this.d = 0;
            akVarA.c(this);
            return;
        }
        akVarA.e(true);
        try {
            i.j context2 = eVar.getContext();
            Object objE = b.e(context2, this.f448h);
            try {
                eVar.resumeWith(obj);
                b.c(context2, objE);
                while (akVarA.f()) {
                }
            } catch (Throwable th) {
                b.c(context2, objE);
                throw th;
            }
        } catch (Throwable th2) {
            try {
                e(th2, null);
            } finally {
                akVarA.b();
            }
        }
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.f445e + ", " + y.ak(this.f446f) + ']';
    }

    @Override // w.ae
    public final i.e b() {
        return this;
    }
}
