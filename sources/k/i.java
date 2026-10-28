package k;

import kotlin.jvm.internal.k;
import kotlin.jvm.internal.p;
import kotlin.jvm.internal.q;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class i extends c implements kotlin.jvm.internal.h {
    private final int arity;

    public i(i.e eVar) {
        super(eVar);
        this.arity = 2;
    }

    @Override // kotlin.jvm.internal.h
    public int getArity() {
        return this.arity;
    }

    @Override // k.a
    public String toString() {
        if (getCompletion() != null) {
            return super.toString();
        }
        p.f437a.getClass();
        String strA = q.a(this);
        k.f(strA, "renderLambdaToString(this)");
        return strA;
    }
}
