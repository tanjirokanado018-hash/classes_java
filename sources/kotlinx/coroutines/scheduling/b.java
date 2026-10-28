package kotlinx.coroutines.scheduling;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlinx.coroutines.internal.o;
import kotlinx.coroutines.internal.q;
import w.x;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class b implements Executor, Closeable {
    private volatile /* synthetic */ int _isTerminated;
    public final int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f476c;
    volatile /* synthetic */ long controlState;
    public final long d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final String f477e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final e f478f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final e f479g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final o f480h;
    private volatile /* synthetic */ long parkedWorkersStack;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public static final q f475l = new q("NOT_IN_STACK");

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final /* synthetic */ AtomicLongFieldUpdater f472i = AtomicLongFieldUpdater.newUpdater(b.class, "parkedWorkersStack");

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static final /* synthetic */ AtomicLongFieldUpdater f473j = AtomicLongFieldUpdater.newUpdater(b.class, "controlState");

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f474k = AtomicIntegerFieldUpdater.newUpdater(b.class, "_isTerminated");

    public b(int i2, int i3, long j2, String str) {
        this.b = i2;
        this.f476c = i3;
        this.d = j2;
        this.f477e = str;
        if (i2 < 1) {
            throw new IllegalArgumentException(("Core pool size " + i2 + " should be at least 1").toString());
        }
        if (i3 < i2) {
            throw new IllegalArgumentException(("Max pool size " + i3 + " should be greater than or equals to core pool size " + i2).toString());
        }
        if (i3 > 2097150) {
            throw new IllegalArgumentException(("Max pool size " + i3 + " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (j2 <= 0) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j2 + " must be positive").toString());
        }
        this.f478f = new e();
        this.f479g = new e();
        this.parkedWorkersStack = 0L;
        this.f480h = new o(i2 + 1);
        this.controlState = ((long) i2) << 42;
        this._isTerminated = 0;
    }

    public static /* synthetic */ void c(b bVar, Runnable runnable, boolean z2, int i2) {
        j jVar = l.f489f;
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        bVar.b(runnable, jVar, z2);
    }

    public final int a() {
        synchronized (this.f480h) {
            if (this._isTerminated != 0) {
                return -1;
            }
            long j2 = this.controlState;
            int i2 = (int) (j2 & 2097151);
            int i3 = i2 - ((int) ((j2 & 4398044413952L) >> 21));
            if (i3 < 0) {
                i3 = 0;
            }
            if (i3 >= this.b) {
                return 0;
            }
            if (i2 >= this.f476c) {
                return 0;
            }
            int i4 = ((int) (this.controlState & 2097151)) + 1;
            if (i4 <= 0 || this.f480h.b(i4) != null) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            a aVar = new a(this, i4);
            this.f480h.c(i4, aVar);
            if (i4 != ((int) (2097151 & f473j.incrementAndGet(this)))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            aVar.start();
            return i3 + 1;
        }
    }

    public final void b(Runnable runnable, j jVar, boolean z2) {
        i kVar;
        int i2;
        l.f488e.getClass();
        long jNanoTime = System.nanoTime();
        if (runnable instanceof i) {
            kVar = (i) runnable;
            kVar.b = jNanoTime;
            kVar.f484c = jVar;
        } else {
            kVar = new k(runnable, jNanoTime, jVar);
        }
        Thread threadCurrentThread = Thread.currentThread();
        i iVarA = null;
        a aVar = threadCurrentThread instanceof a ? (a) threadCurrentThread : null;
        if (aVar == null || !kotlin.jvm.internal.k.a(aVar.f471h, this)) {
            aVar = null;
        }
        if (aVar == null || (i2 = aVar.f467c) == 5 || (kVar.f484c.f485a == 0 && i2 == 2)) {
            iVarA = kVar;
        } else {
            aVar.f470g = true;
            n nVar = aVar.b;
            if (z2) {
                iVarA = nVar.a(kVar);
            } else {
                nVar.getClass();
                i iVar = (i) n.b.getAndSet(nVar, kVar);
                if (iVar != null) {
                    iVarA = nVar.a(iVar);
                }
            }
        }
        if (iVarA != null) {
            if (!(iVarA.f484c.f485a == 1 ? this.f479g : this.f478f).a(iVarA)) {
                throw new RejectedExecutionException(androidx.appcompat.graphics.drawable.a.g(new StringBuilder(), this.f477e, " was terminated"));
            }
        }
        boolean z3 = z2 && aVar != null;
        if (kVar.f484c.f485a == 0) {
            if (z3 || h() || g(this.controlState)) {
                return;
            }
            h();
            return;
        }
        long jAddAndGet = f473j.addAndGet(this, 2097152L);
        if (z3 || h() || g(jAddAndGet)) {
            return;
        }
        h();
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0085  */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws InterruptedException {
        int i2;
        i iVarA;
        if (f474k.compareAndSet(this, 0, 1)) {
            Thread threadCurrentThread = Thread.currentThread();
            a aVar = threadCurrentThread instanceof a ? (a) threadCurrentThread : null;
            if (aVar == null || !kotlin.jvm.internal.k.a(aVar.f471h, this)) {
                aVar = null;
            }
            synchronized (this.f480h) {
                i2 = (int) (this.controlState & 2097151);
            }
            if (1 <= i2) {
                int i3 = 1;
                while (true) {
                    Object objB = this.f480h.b(i3);
                    kotlin.jvm.internal.k.d(objB);
                    a aVar2 = (a) objB;
                    if (aVar2 != aVar) {
                        while (aVar2.isAlive()) {
                            LockSupport.unpark(aVar2);
                            aVar2.join(10000L);
                        }
                        n nVar = aVar2.b;
                        e eVar = this.f479g;
                        nVar.getClass();
                        i iVar = (i) n.b.getAndSet(nVar, null);
                        if (iVar != null) {
                            eVar.a(iVar);
                        }
                        while (true) {
                            i iVarC = nVar.c();
                            if (iVarC == null) {
                                break;
                            } else {
                                eVar.a(iVarC);
                            }
                        }
                    }
                    if (i3 == i2) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            this.f479g.b();
            this.f478f.b();
            while (true) {
                if (aVar != null) {
                    iVarA = aVar.a(true);
                    if (iVarA == null) {
                        iVarA = (i) this.f478f.d();
                        if (iVarA == null) {
                            break;
                            break;
                        }
                    }
                } else {
                    iVarA = (i) this.f478f.d();
                    if (iVarA == null && (iVarA = (i) this.f479g.d()) == null) {
                        break;
                    }
                }
                try {
                    iVarA.run();
                } catch (Throwable th) {
                    Thread threadCurrentThread2 = Thread.currentThread();
                    threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
                }
            }
            if (aVar != null) {
                aVar.h(5);
            }
            this.parkedWorkersStack = 0L;
            this.controlState = 0L;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    public final boolean d() {
        return this._isTerminated;
    }

    public final void e(a aVar) {
        long j2;
        int iB;
        if (aVar.c() != f475l) {
            return;
        }
        do {
            j2 = this.parkedWorkersStack;
            iB = aVar.b();
            aVar.g(this.f480h.b((int) (2097151 & j2)));
        } while (!f472i.compareAndSet(this, j2, ((long) iB) | ((2097152 + j2) & (-2097152))));
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        c(this, runnable, false, 6);
    }

    public final void f(a aVar, int i2, int i3) {
        while (true) {
            long j2 = this.parkedWorkersStack;
            int iB = (int) (2097151 & j2);
            long j3 = (2097152 + j2) & (-2097152);
            if (iB == i2) {
                if (i3 == 0) {
                    Object objC = aVar.c();
                    while (true) {
                        if (objC == f475l) {
                            iB = -1;
                            break;
                        }
                        if (objC == null) {
                            iB = 0;
                            break;
                        }
                        a aVar2 = (a) objC;
                        iB = aVar2.b();
                        if (iB != 0) {
                            break;
                        } else {
                            objC = aVar2.c();
                        }
                    }
                } else {
                    iB = i3;
                }
            }
            if (iB >= 0 && f472i.compareAndSet(this, j2, j3 | ((long) iB))) {
                return;
            }
        }
    }

    public final boolean g(long j2) {
        int i2 = ((int) (2097151 & j2)) - ((int) ((j2 & 4398044413952L) >> 21));
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = this.b;
        if (i2 < i3) {
            int iA = a();
            if (iA == 1 && i3 > 1) {
                a();
            }
            if (iA > 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean h() {
        q qVar;
        int iB;
        while (true) {
            long j2 = this.parkedWorkersStack;
            a aVar = (a) this.f480h.b((int) (2097151 & j2));
            if (aVar == null) {
                aVar = null;
            } else {
                long j3 = (2097152 + j2) & (-2097152);
                Object objC = aVar.c();
                while (true) {
                    qVar = f475l;
                    if (objC == qVar) {
                        iB = -1;
                        break;
                    }
                    if (objC == null) {
                        iB = 0;
                        break;
                    }
                    a aVar2 = (a) objC;
                    iB = aVar2.b();
                    if (iB != 0) {
                        break;
                    }
                    objC = aVar2.c();
                }
                if (iB >= 0 && f472i.compareAndSet(this, j2, ((long) iB) | j3)) {
                    aVar.g(qVar);
                }
            }
            if (aVar == null) {
                return false;
            }
            if (a.f466i.compareAndSet(aVar, -1, 0)) {
                LockSupport.unpark(aVar);
                return true;
            }
        }
    }

    public final String toString() {
        StringBuilder sb;
        char c2;
        ArrayList arrayList = new ArrayList();
        int iA = this.f480h.a();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 1; i7 < iA; i7++) {
            a aVar = (a) this.f480h.b(i7);
            if (aVar != null) {
                int iB = aVar.b.b();
                int iC = x.c(aVar.f467c);
                if (iC == 0) {
                    i2++;
                    sb = new StringBuilder();
                    sb.append(iB);
                    c2 = 'c';
                } else if (iC == 1) {
                    i3++;
                    sb = new StringBuilder();
                    sb.append(iB);
                    c2 = 'b';
                } else if (iC == 2) {
                    i4++;
                } else if (iC == 3) {
                    i5++;
                    if (iB > 0) {
                        sb = new StringBuilder();
                        sb.append(iB);
                        c2 = 'd';
                    }
                } else if (iC == 4) {
                    i6++;
                }
                sb.append(c2);
                arrayList.add(sb.toString());
            }
        }
        long j2 = this.controlState;
        return this.f477e + '@' + y.m(this) + "[Pool Size {core = " + this.b + ", max = " + this.f476c + "}, Worker States {CPU = " + i2 + ", blocking = " + i3 + ", parked = " + i4 + ", dormant = " + i5 + ", terminated = " + i6 + "}, running workers queues = " + arrayList + ", global CPU queue size = " + this.f478f.c() + ", global blocking queue size = " + this.f479g.c() + ", Control State {created workers= " + ((int) (2097151 & j2)) + ", blocking tasks = " + ((int) ((4398044413952L & j2) >> 21)) + ", CPUs acquired = " + (this.b - ((int) ((9223367638808264704L & j2) >> 42))) + "}]";
    }
}
