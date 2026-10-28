package kotlinx.coroutines.internal;

import w.aa;
import w.ab;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class e extends w.s implements Runnable, ab {
    public final w.s b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f449c;
    public final h d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final Object f450e;
    private volatile int runningWorkers;

    /* JADX WARN: Multi-variable type inference failed */
    public e(w.s sVar, int i2) {
        this.b = sVar;
        this.f449c = i2;
        if ((sVar instanceof ab ? (ab) sVar : null) == null) {
            int i3 = aa.f520a;
        }
        this.d = new h();
        this.f450e = new Object();
    }

    public final boolean b() {
        synchronized (this.f450e) {
            if (this.runningWorkers >= this.f449c) {
                return false;
            }
            this.runningWorkers++;
            return true;
        }
    }

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        this.d.a(runnable);
        if (this.runningWorkers < this.f449c && b()) {
            this.b.dispatch(this, this);
        }
    }

    @Override // w.s
    public final void dispatchYield(i.j jVar, Runnable runnable) {
        this.d.a(runnable);
        if (this.runningWorkers < this.f449c && b()) {
            this.b.dispatchYield(this, this);
        }
    }

    @Override // w.s
    public final w.s limitedParallelism(int i2) {
        kotlin.jvm.internal.k.h(i2);
        return i2 >= this.f449c ? this : super.limitedParallelism(i2);
    }

    @Override // java.lang.Runnable
    public final void run() {
        while (true) {
            int i2 = 0;
            while (true) {
                Runnable runnable = (Runnable) this.d.d();
                if (runnable != null) {
                    try {
                        runnable.run();
                    } catch (Throwable th) {
                        kotlin.jvm.internal.k.j(i.k.b, th);
                    }
                    i2++;
                    if (i2 >= 16 && this.b.isDispatchNeeded(this)) {
                        this.b.dispatch(this, this);
                        return;
                    }
                }
            }
            synchronized (this.f450e) {
                this.runningWorkers--;
                if (this.d.c() == 0) {
                    return;
                } else {
                    this.runningWorkers++;
                }
            }
        }
    }
}
