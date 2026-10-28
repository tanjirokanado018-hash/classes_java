package k;

import i.j;
import i.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class g extends a {
    public g(i.e eVar) {
        super(eVar);
        if (eVar != null && eVar.getContext() != k.b) {
            throw new IllegalArgumentException("Coroutines with restricted suspension must have EmptyCoroutineContext".toString());
        }
    }

    @Override // i.e
    public j getContext() {
        return k.b;
    }
}
