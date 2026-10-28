package kotlinx.coroutines.scheduling;

import androidx.core.location.LocationRequestCompat;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a extends Thread {

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f466i = AtomicIntegerFieldUpdater.newUpdater(a.class, "workerCtl");
    public final n b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f467c;
    public long d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public long f468e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f469f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f470g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ b f471h;
    private volatile int indexInArray;
    private volatile Object nextParkedWorker;
    volatile /* synthetic */ int workerCtl;

    public a(b bVar, int i2) {
        this.f471h = bVar;
        setDaemon(true);
        this.b = new n();
        this.f467c = 4;
        this.workerCtl = 0;
        this.nextParkedWorker = b.f475l;
        q.e.b.getClass();
        this.f469f = q.e.f498c.a();
        f(i2);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002e  */
    public final i a(boolean z2) {
        i iVarE;
        i iVarE2;
        long j2;
        i iVarC;
        if (this.f467c != 1) {
            b bVar = this.f471h;
            do {
                j2 = bVar.controlState;
                if (((int) ((9223367638808264704L & j2) >> 42)) == 0) {
                    if (z2) {
                        n nVar = this.b;
                        nVar.getClass();
                        iVarC = (i) n.b.getAndSet(nVar, null);
                        if (iVarC == null) {
                            iVarC = nVar.c();
                        }
                        if (iVarC == null) {
                            iVarC = (i) this.f471h.f479g.d();
                        }
                    } else {
                        iVarC = (i) this.f471h.f479g.d();
                    }
                    return iVarC == null ? i(true) : iVarC;
                }
            } while (!b.f473j.compareAndSet(bVar, j2, j2 - 4398046511104L));
            this.f467c = 1;
        }
        if (z2) {
            boolean z3 = d(this.f471h.b * 2) == 0;
            if (z3 && (iVarE2 = e()) != null) {
                return iVarE2;
            }
            n nVar2 = this.b;
            nVar2.getClass();
            i iVar = (i) n.b.getAndSet(nVar2, null);
            i iVarC2 = iVar == null ? nVar2.c() : iVar;
            if (iVarC2 != null) {
                return iVarC2;
            }
            if (!z3 && (iVarE = e()) != null) {
                return iVarE;
            }
        } else {
            i iVarE3 = e();
            if (iVarE3 != null) {
                return iVarE3;
            }
        }
        return i(false);
    }

    public final int b() {
        return this.indexInArray;
    }

    public final Object c() {
        return this.nextParkedWorker;
    }

    public final int d(int i2) {
        int i3 = this.f469f;
        int i4 = i3 ^ (i3 << 13);
        int i5 = i4 ^ (i4 >> 17);
        int i6 = i5 ^ (i5 << 5);
        this.f469f = i6;
        int i7 = i2 - 1;
        return (i7 & i2) == 0 ? i6 & i7 : (i6 & Integer.MAX_VALUE) % i2;
    }

    public final i e() {
        e eVar;
        int iD = d(2);
        b bVar = this.f471h;
        if (iD == 0) {
            i iVar = (i) bVar.f478f.d();
            if (iVar != null) {
                return iVar;
            }
            eVar = bVar.f479g;
        } else {
            i iVar2 = (i) bVar.f479g.d();
            if (iVar2 != null) {
                return iVar2;
            }
            eVar = bVar.f478f;
        }
        return (i) eVar.d();
    }

    public final void f(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f471h.f477e);
        sb.append("-worker-");
        sb.append(i2 == 0 ? "TERMINATED" : String.valueOf(i2));
        setName(sb.toString());
        this.indexInArray = i2;
    }

    public final void g(Object obj) {
        this.nextParkedWorker = obj;
    }

    public final boolean h(int i2) {
        int i3 = this.f467c;
        boolean z2 = i3 == 1;
        if (z2) {
            b.f473j.addAndGet(this.f471h, 4398046511104L);
        }
        if (i3 != i2) {
            this.f467c = i2;
        }
        return z2;
    }

    public final i i(boolean z2) {
        long jE;
        int i2 = (int) (this.f471h.controlState & 2097151);
        if (i2 < 2) {
            return null;
        }
        int iD = d(i2);
        b bVar = this.f471h;
        long jMin = LocationRequestCompat.PASSIVE_INTERVAL;
        for (int i3 = 0; i3 < i2; i3++) {
            iD++;
            if (iD > i2) {
                iD = 1;
            }
            a aVar = (a) bVar.f480h.b(iD);
            if (aVar != null && aVar != this) {
                n nVar = this.b;
                n nVar2 = aVar.b;
                if (z2) {
                    jE = nVar.d(nVar2);
                } else {
                    nVar.getClass();
                    i iVarC = nVar2.c();
                    if (iVarC != null) {
                        i iVar = (i) n.b.getAndSet(nVar, iVarC);
                        if (iVar != null) {
                            nVar.a(iVar);
                        }
                        jE = -1;
                    } else {
                        jE = nVar.e(nVar2, false);
                    }
                }
                if (jE == -1) {
                    n nVar3 = this.b;
                    nVar3.getClass();
                    i iVar2 = (i) n.b.getAndSet(nVar3, null);
                    return iVar2 == null ? nVar3.c() : iVar2;
                }
                if (jE > 0) {
                    jMin = Math.min(jMin, jE);
                }
            }
        }
        if (jMin == LocationRequestCompat.PASSIVE_INTERVAL) {
            jMin = 0;
        }
        this.f468e = jMin;
        return null;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        loop0: while (true) {
            boolean z2 = false;
            while (true) {
                if (this.f471h.d() || this.f467c == 5) {
                    break loop0;
                }
                i iVarA = a(this.f470g);
                if (iVarA != null) {
                    this.f468e = 0L;
                    int i2 = iVarA.f484c.f485a;
                    this.d = 0L;
                    if (this.f467c == 3) {
                        this.f467c = 2;
                    }
                    b bVar = this.f471h;
                    if (i2 != 0 && h(2) && !bVar.h() && !bVar.g(bVar.controlState)) {
                        bVar.h();
                    }
                    bVar.getClass();
                    try {
                        iVarA.run();
                    } catch (Throwable th) {
                        Thread threadCurrentThread = Thread.currentThread();
                        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
                    }
                    if (i2 != 0) {
                        b.f473j.addAndGet(bVar, -2097152L);
                        if (this.f467c == 5) {
                            break;
                        }
                        this.f467c = 4;
                        break;
                    }
                    break;
                }
                this.f470g = false;
                if (this.f468e != 0) {
                    if (z2) {
                        h(3);
                        Thread.interrupted();
                        LockSupport.parkNanos(this.f468e);
                        this.f468e = 0L;
                        break;
                    }
                    z2 = true;
                } else if (this.nextParkedWorker != b.f475l) {
                    this.workerCtl = -1;
                    while (this.nextParkedWorker != b.f475l && this.workerCtl == -1 && !this.f471h.d() && this.f467c != 5) {
                        h(3);
                        Thread.interrupted();
                        if (this.d == 0) {
                            this.d = System.nanoTime() + this.f471h.d;
                        }
                        LockSupport.parkNanos(this.f471h.d);
                        if (System.nanoTime() - this.d >= 0) {
                            this.d = 0L;
                            b bVar2 = this.f471h;
                            synchronized (bVar2.f480h) {
                                try {
                                    if (!bVar2.d() && ((int) (bVar2.controlState & 2097151)) > bVar2.b && f466i.compareAndSet(this, -1, 1)) {
                                        int i3 = this.indexInArray;
                                        f(0);
                                        bVar2.f(this, i3, 0);
                                        int andDecrement = (int) (b.f473j.getAndDecrement(bVar2) & 2097151);
                                        if (andDecrement != i3) {
                                            Object objB = bVar2.f480h.b(andDecrement);
                                            kotlin.jvm.internal.k.d(objB);
                                            a aVar = (a) objB;
                                            bVar2.f480h.c(i3, aVar);
                                            aVar.f(i3);
                                            bVar2.f(aVar, andDecrement, i3);
                                        }
                                        bVar2.f480h.c(andDecrement, null);
                                        this.f467c = 5;
                                    }
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }
                } else {
                    this.f471h.e(this);
                }
            }
        }
        h(5);
    }
}
