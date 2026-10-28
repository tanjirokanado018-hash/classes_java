package i;

import p.p;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a implements h {
    private final i key;

    public a(i iVar) {
        this.key = iVar;
    }

    @Override // i.j
    public <R> R fold(R r2, p operation) {
        kotlin.jvm.internal.k.g(operation, "operation");
        return (R) operation.invoke(r2, this);
    }

    @Override // i.j
    public h get(i iVar) {
        return y.j(this, iVar);
    }

    @Override // i.h
    public i getKey() {
        return this.key;
    }

    @Override // i.j
    public j minusKey(i iVar) {
        return y.y(this, iVar);
    }

    @Override // i.j
    public j plus(j context) {
        kotlin.jvm.internal.k.g(context, "context");
        return y.ac(this, context);
    }
}
