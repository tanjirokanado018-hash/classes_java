package i;

import java.io.Serializable;
import p.p;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d implements j, Serializable {
    public final j b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final h f428c;

    public d(h element, j left) {
        kotlin.jvm.internal.k.g(left, "left");
        kotlin.jvm.internal.k.g(element, "element");
        this.b = left;
        this.f428c = element;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof d) {
                d dVar = (d) obj;
                dVar.getClass();
                int i2 = 2;
                d dVar2 = dVar;
                int i3 = 2;
                while (true) {
                    j jVar = dVar2.b;
                    dVar2 = jVar instanceof d ? (d) jVar : null;
                    if (dVar2 == null) {
                        break;
                    }
                    i3++;
                }
                d dVar3 = this;
                while (true) {
                    j jVar2 = dVar3.b;
                    dVar3 = jVar2 instanceof d ? (d) jVar2 : null;
                    if (dVar3 == null) {
                        break;
                    }
                    i2++;
                }
                if (i3 == i2) {
                    d dVar4 = this;
                    while (true) {
                        h hVar = dVar4.f428c;
                        if (!kotlin.jvm.internal.k.a(dVar.get(hVar.getKey()), hVar)) {
                            break;
                        }
                        j jVar3 = dVar4.b;
                        if (jVar3 instanceof d) {
                            dVar4 = (d) jVar3;
                        } else {
                            kotlin.jvm.internal.k.e(jVar3, "null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
                            h hVar2 = (h) jVar3;
                            if (kotlin.jvm.internal.k.a(dVar.get(hVar2.getKey()), hVar2)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // i.j
    public final Object fold(Object obj, p pVar) {
        return pVar.invoke(this.b.fold(obj, pVar), this.f428c);
    }

    @Override // i.j
    public final h get(i key) {
        kotlin.jvm.internal.k.g(key, "key");
        d dVar = this;
        while (true) {
            h hVar = dVar.f428c.get(key);
            if (hVar != null) {
                return hVar;
            }
            j jVar = dVar.b;
            if (!(jVar instanceof d)) {
                return jVar.get(key);
            }
            dVar = (d) jVar;
        }
    }

    public final int hashCode() {
        return this.f428c.hashCode() + this.b.hashCode();
    }

    @Override // i.j
    public final j minusKey(i key) {
        kotlin.jvm.internal.k.g(key, "key");
        h hVar = this.f428c;
        h hVar2 = hVar.get(key);
        j jVar = this.b;
        if (hVar2 != null) {
            return jVar;
        }
        j jVarMinusKey = jVar.minusKey(key);
        if (jVarMinusKey == jVar) {
            return this;
        }
        return jVarMinusKey == k.b ? hVar : new d(hVar, jVarMinusKey);
    }

    @Override // i.j
    public final j plus(j jVar) {
        return y.ac(this, jVar);
    }

    public final String toString() {
        return "[" + ((String) fold("", c.f427c)) + ']';
    }
}
