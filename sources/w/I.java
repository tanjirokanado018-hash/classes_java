package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class I extends s {
    public static final /* synthetic */ int b = 0;

    static {
        new I();
    }

    @Override // w.s
    public final void dispatch(i.j jVar, Runnable runnable) {
        x.b(jVar.get(L.b));
        throw new UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. If you wrap Unconfined dispatcher in your code, make sure you properly delegate isDispatchNeeded and dispatch calls.");
    }

    @Override // w.s
    public final s limitedParallelism(int i2) {
        throw new UnsupportedOperationException("limitedParallelism is not supported for Dispatchers.Unconfined");
    }

    @Override // w.s
    public final String toString() {
        return "Dispatchers.Unconfined";
    }
}
