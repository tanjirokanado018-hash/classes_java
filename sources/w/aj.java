package w;

import androidx.core.location.LocationRequestCompat;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class aj extends ak implements ab {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f523e = AtomicReferenceFieldUpdater.newUpdater(aj.class, Object.class, "_queue");
    private volatile /* synthetic */ Object _queue = null;
    private volatile /* synthetic */ Object _delayed = null;
    private volatile /* synthetic */ int _isCompleted = 0;

    static {
        AtomicReferenceFieldUpdater.newUpdater(aj.class, Object.class, "_delayed");
    }

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        h(runnable);
    }

    @Override // w.ak
    public void g() {
        H.f517a.set(null);
        this._isCompleted = 1;
        loop0: while (true) {
            Object obj = this._queue;
            kotlinx.coroutines.internal.q qVar = y.f553a;
            if (obj == null) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f523e;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, null, qVar)) {
                        break loop0;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == null);
            } else {
                if (obj instanceof kotlinx.coroutines.internal.j) {
                    ((kotlinx.coroutines.internal.j) obj).b();
                    break;
                }
                if (obj == qVar) {
                    break;
                }
                kotlinx.coroutines.internal.j jVar = new kotlinx.coroutines.internal.j(8, true);
                jVar.a((Runnable) obj);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f523e;
                do {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, jVar)) {
                        break loop0;
                    }
                } while (atomicReferenceFieldUpdater2.get(this) == obj);
            }
        }
        while (k() <= 0) {
        }
        System.nanoTime();
    }

    public void h(Runnable runnable) {
        loop0: while (true) {
            Object obj = this._queue;
            if (this._isCompleted == 0) {
                if (obj == null) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f523e;
                    do {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, null, runnable)) {
                            break loop0;
                        }
                    } while (atomicReferenceFieldUpdater.get(this) == null);
                } else if (obj instanceof kotlinx.coroutines.internal.j) {
                    kotlinx.coroutines.internal.j jVar = (kotlinx.coroutines.internal.j) obj;
                    int iA = jVar.a(runnable);
                    if (iA == 0) {
                        break;
                    }
                    if (iA == 1) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f523e;
                        kotlinx.coroutines.internal.j jVarE = jVar.e();
                        while (!atomicReferenceFieldUpdater2.compareAndSet(this, obj, jVarE) && atomicReferenceFieldUpdater2.get(this) == obj) {
                        }
                    } else if (iA != 2) {
                    }
                } else if (obj != y.f553a) {
                    kotlinx.coroutines.internal.j jVar2 = new kotlinx.coroutines.internal.j(8, true);
                    jVar2.a((Runnable) obj);
                    jVar2.a(runnable);
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = f523e;
                    do {
                        if (atomicReferenceFieldUpdater3.compareAndSet(this, obj, jVar2)) {
                            break loop0;
                        }
                    } while (atomicReferenceFieldUpdater3.get(this) == obj);
                }
            }
            z.f559f.h(runnable);
            return;
        }
        Thread threadD = d();
        if (Thread.currentThread() != threadD) {
            LockSupport.unpark(threadD);
        }
    }

    public final long i() {
        kotlinx.coroutines.internal.a aVar = this.d;
        if (((aVar == null || aVar.b == aVar.f439c) ? Long.MAX_VALUE : 0L) == 0) {
            return 0L;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (!(obj instanceof kotlinx.coroutines.internal.j)) {
                if (obj == y.f553a) {
                    return LocationRequestCompat.PASSIVE_INTERVAL;
                }
                return 0L;
            }
            if (!((kotlinx.coroutines.internal.j) obj).d()) {
                return 0L;
            }
        }
        return LocationRequestCompat.PASSIVE_INTERVAL;
    }

    public final boolean j() {
        kotlinx.coroutines.internal.a aVar = this.d;
        if (aVar != null && aVar.b != aVar.f439c) {
            return false;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (obj instanceof kotlinx.coroutines.internal.j) {
                return ((kotlinx.coroutines.internal.j) obj).d();
            }
            if (obj != y.f553a) {
                return false;
            }
        }
        return true;
    }

    public final long k() {
        Runnable runnable;
        if (f()) {
            return 0L;
        }
        loop0: while (true) {
            Object obj = this._queue;
            runnable = null;
            if (obj == null) {
                break;
            }
            if (obj instanceof kotlinx.coroutines.internal.j) {
                kotlinx.coroutines.internal.j jVar = (kotlinx.coroutines.internal.j) obj;
                Object objF = jVar.f();
                if (objF != kotlinx.coroutines.internal.j.f456g) {
                    runnable = (Runnable) objF;
                    break;
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f523e;
                kotlinx.coroutines.internal.j jVarE = jVar.e();
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, jVarE) && atomicReferenceFieldUpdater.get(this) == obj) {
                }
            } else {
                if (obj == y.f553a) {
                    break;
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f523e;
                do {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, null)) {
                        runnable = (Runnable) obj;
                        break loop0;
                    }
                } while (atomicReferenceFieldUpdater2.get(this) == obj);
            }
        }
        if (runnable == null) {
            return i();
        }
        runnable.run();
        return 0L;
    }

    public final void l() {
        this._queue = null;
        this._delayed = null;
    }
}
