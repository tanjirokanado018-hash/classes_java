package kotlinx.coroutines.internal;

import w.AbstractC0031a;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class p extends AbstractC0031a implements k.d {
    public final i.e d;

    public p(i.e eVar, i.j jVar) {
        super(jVar, true);
        this.d = eVar;
    }

    @Override // w.ay
    public void c(Object obj) {
        b.d(y.u(this.d), kotlin.jvm.internal.k.n(obj), null);
    }

    @Override // w.ay
    public void d(Object obj) {
        this.d.resumeWith(kotlin.jvm.internal.k.n(obj));
    }

    @Override // k.d
    public final k.d getCallerFrame() {
        i.e eVar = this.d;
        if (eVar instanceof k.d) {
            return (k.d) eVar;
        }
        return null;
    }

    @Override // w.ay
    public final boolean u() {
        return true;
    }
}
