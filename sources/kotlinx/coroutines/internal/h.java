package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f452a = AtomicReferenceFieldUpdater.newUpdater(h.class, Object.class, "_cur");
    private volatile /* synthetic */ Object _cur = new j(8, false);

    public final boolean a(Object obj) {
        while (true) {
            j jVar = (j) this._cur;
            int iA = jVar.a(obj);
            if (iA == 0) {
                return true;
            }
            if (iA == 1) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f452a;
                j jVarE = jVar.e();
                while (!atomicReferenceFieldUpdater.compareAndSet(this, jVar, jVarE) && atomicReferenceFieldUpdater.get(this) == jVar) {
                }
            } else if (iA == 2) {
                return false;
            }
        }
    }

    public final void b() {
        while (true) {
            j jVar = (j) this._cur;
            if (jVar.b()) {
                return;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f452a;
            j jVarE = jVar.e();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, jVar, jVarE) && atomicReferenceFieldUpdater.get(this) == jVar) {
            }
        }
    }

    public final int c() {
        return ((j) this._cur).c();
    }

    public final Object d() {
        while (true) {
            j jVar = (j) this._cur;
            Object objF = jVar.f();
            if (objF != j.f456g) {
                return objF;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f452a;
            j jVarE = jVar.e();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, jVar, jVarE) && atomicReferenceFieldUpdater.get(this) == jVar) {
            }
        }
    }
}
