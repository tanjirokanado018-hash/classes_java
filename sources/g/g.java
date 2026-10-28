package g;

import java.io.Serializable;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class g implements c, Serializable {
    public p.a b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public volatile Object f421c = h.f422a;
    public final Object d = this;

    public g(p.a aVar) {
        this.b = aVar;
    }

    @Override // g.c
    public final Object getValue() {
        Object objInvoke;
        Object obj = this.f421c;
        h hVar = h.f422a;
        if (obj != hVar) {
            return obj;
        }
        synchronized (this.d) {
            objInvoke = this.f421c;
            if (objInvoke == hVar) {
                p.a aVar = this.b;
                k.d(aVar);
                objInvoke = aVar.invoke();
                this.f421c = objInvoke;
                this.b = null;
            }
        }
        return objInvoke;
    }

    public final String toString() {
        return this.f421c != h.f422a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
