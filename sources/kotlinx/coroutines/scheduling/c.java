package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlinx.coroutines.internal.r;
import w.al;
import w.s;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends al implements Executor {
    public static final c b = new c();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final s f481c;

    static {
        m mVar = m.b;
        int i2 = r.f462a;
        if (64 >= i2) {
            i2 = 64;
        }
        f481c = mVar.limitedParallelism(kotlin.jvm.internal.k.u("kotlinx.coroutines.io.parallelism", i2, 0, 0, 12));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO".toString());
    }

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        f481c.dispatch(jVar, runnable);
    }

    @Override // w.s
    public final void dispatchYield(i.j jVar, Runnable runnable) {
        f481c.dispatchYield(jVar, runnable);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        dispatch(i.k.b, runnable);
    }

    @Override // w.s
    public final s limitedParallelism(int i2) {
        return m.b.limitedParallelism(i2);
    }

    @Override // w.s
    public final String toString() {
        return "Dispatchers.IO";
    }
}
