package k;

import i.j;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class c extends a {
    private final j _context;
    private transient i.e intercepted;

    public c(i.e eVar) {
        this(eVar, eVar != null ? eVar.getContext() : null);
    }

    @Override // i.e
    public j getContext() {
        j jVar = this._context;
        k.d(jVar);
        return jVar;
    }

    public final i.e intercepted() {
        i.e eVarInterceptContinuation = this.intercepted;
        if (eVarInterceptContinuation == null) {
            i.g gVar = (i.g) getContext().get(i.f.b);
            if (gVar == null || (eVarInterceptContinuation = gVar.interceptContinuation(this)) == null) {
                eVarInterceptContinuation = this;
            }
            this.intercepted = eVarInterceptContinuation;
        }
        return eVarInterceptContinuation;
    }

    @Override // k.a
    public void releaseIntercepted() {
        i.e eVar = this.intercepted;
        if (eVar != null && eVar != this) {
            i.h hVar = getContext().get(i.f.b);
            k.d(hVar);
            ((i.g) hVar).releaseInterceptedContinuation(eVar);
        }
        this.intercepted = b.b;
    }

    public c(i.e eVar, j jVar) {
        super(eVar);
        this._context = jVar;
    }
}
