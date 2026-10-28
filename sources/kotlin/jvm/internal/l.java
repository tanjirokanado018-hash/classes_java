package kotlin.jvm.internal;

import java.io.Serializable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class l implements h, Serializable {
    private final int arity;

    public l(int i2) {
        this.arity = i2;
    }

    @Override // kotlin.jvm.internal.h
    public int getArity() {
        return this.arity;
    }

    public String toString() {
        p.f437a.getClass();
        String strA = q.a(this);
        k.f(strA, "renderLambdaToString(this)");
        return strA;
    }
}
