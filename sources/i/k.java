package i;

import java.io.Serializable;
import p.p;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class k implements j, Serializable {
    public static final k b = new k();
    private static final long serialVersionUID = 0;

    @Override // i.j
    public final h get(i key) {
        kotlin.jvm.internal.k.g(key, "key");
        return null;
    }

    public final int hashCode() {
        return 0;
    }

    @Override // i.j
    public final j minusKey(i key) {
        kotlin.jvm.internal.k.g(key, "key");
        return this;
    }

    @Override // i.j
    public final j plus(j context) {
        kotlin.jvm.internal.k.g(context, "context");
        return context;
    }

    public final String toString() {
        return "EmptyCoroutineContext";
    }

    @Override // i.j
    public final Object fold(Object obj, p pVar) {
        return obj;
    }
}
