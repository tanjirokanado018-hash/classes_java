package w;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ad extends kotlinx.coroutines.internal.p {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f521e = AtomicIntegerFieldUpdater.newUpdater(ad.class, "_decision");
    private volatile /* synthetic */ int _decision;

    public ad(i.e eVar, i.j jVar) {
        super(eVar, jVar);
        this._decision = 0;
    }

    public final Object ae() throws Throwable {
        an anVar;
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new IllegalStateException("Already suspended".toString());
                }
                Object objQ = q();
                ao aoVar = objQ instanceof ao ? (ao) objQ : null;
                if (aoVar != null && (anVar = aoVar.f525a) != null) {
                    objQ = anVar;
                }
                if (objQ instanceof m) {
                    throw ((m) objQ).f547a;
                }
                return objQ;
            }
        } while (!f521e.compareAndSet(this, 0, 1));
        return j.a.b;
    }

    @Override // kotlinx.coroutines.internal.p, w.ay
    public final void c(Object obj) {
        d(obj);
    }

    @Override // kotlinx.coroutines.internal.p, w.ay
    public final void d(Object obj) {
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("Already resumed".toString());
                }
                kotlinx.coroutines.internal.b.d(y.u(this.d), kotlin.jvm.internal.k.n(obj), null);
                return;
            }
        } while (!f521e.compareAndSet(this, 0, 2));
    }
}
