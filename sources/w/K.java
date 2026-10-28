package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class K implements i.h, i.i {
    public static final K b = new K();

    @Override // i.j
    public final Object fold(Object obj, p.p pVar) {
        return pVar.invoke(obj, this);
    }

    @Override // i.j
    public final i.h get(i.i iVar) {
        return y.j(this, iVar);
    }

    @Override // i.j
    public final i.j minusKey(i.i iVar) {
        return y.y(this, iVar);
    }

    @Override // i.j
    public final i.j plus(i.j context) {
        kotlin.jvm.internal.k.g(context, "context");
        return y.ac(this, context);
    }

    @Override // i.h
    public final i.i getKey() {
        return this;
    }
}
