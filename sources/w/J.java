package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class J extends kotlinx.coroutines.internal.p {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ThreadLocal f518e;

    public final boolean ae() {
        ThreadLocal threadLocal = this.f518e;
        if (threadLocal.get() == null) {
            return false;
        }
        threadLocal.set(null);
        return true;
    }

    @Override // kotlinx.coroutines.internal.p, w.ay
    public final void d(Object obj) {
        ThreadLocal threadLocal = this.f518e;
        g.d dVar = (g.d) threadLocal.get();
        if (dVar != null) {
            kotlinx.coroutines.internal.b.c((i.j) dVar.b, dVar.f420c);
            threadLocal.set(null);
        }
        Object objN = kotlin.jvm.internal.k.n(obj);
        i.e eVar = this.d;
        i.j context = eVar.getContext();
        Object objE = kotlinx.coroutines.internal.b.e(context, null);
        J jX = objE != kotlinx.coroutines.internal.b.f442e ? kotlin.jvm.internal.k.x(eVar, context, objE) : null;
        try {
            eVar.resumeWith(objN);
        } finally {
            if (jX == null || jX.ae()) {
                kotlinx.coroutines.internal.b.c(context, objE);
            }
        }
    }
}
