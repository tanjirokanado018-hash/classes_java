package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class s extends i.a implements i.g {
    public static final r Key = new r(i.f.b, q.f550c);

    public s() {
        super(i.f.b);
    }

    public abstract void dispatch(i.j jVar, Runnable runnable);

    public void dispatchYield(i.j jVar, Runnable runnable) {
        dispatch(jVar, runnable);
    }

    @Override // i.a, i.j
    public <E extends i.h> E get(i.i key) {
        kotlin.jvm.internal.k.g(key, "key");
        if (!(key instanceof i.b)) {
            if (i.f.b == key) {
                return this;
            }
            return null;
        }
        i.b bVar = (i.b) key;
        i.i key2 = getKey();
        kotlin.jvm.internal.k.g(key2, "key");
        if (key2 != bVar && bVar.f426c != key2) {
            return null;
        }
        E e2 = (E) bVar.b.invoke(this);
        if (e2 instanceof i.h) {
            return e2;
        }
        return null;
    }

    @Override // i.g
    public final <T> i.e interceptContinuation(i.e eVar) {
        return new kotlinx.coroutines.internal.d(this, eVar);
    }

    public boolean isDispatchNeeded(i.j jVar) {
        return !(this instanceof I);
    }

    public s limitedParallelism(int i2) {
        kotlin.jvm.internal.k.h(i2);
        return new kotlinx.coroutines.internal.e(this, i2);
    }

    @Override // i.a, i.j
    public i.j minusKey(i.i key) {
        kotlin.jvm.internal.k.g(key, "key");
        boolean z2 = key instanceof i.b;
        i.k kVar = i.k.b;
        if (z2) {
            i.b bVar = (i.b) key;
            i.i key2 = getKey();
            kotlin.jvm.internal.k.g(key2, "key");
            if ((key2 == bVar || bVar.f426c == key2) && ((i.h) bVar.b.invoke(this)) != null) {
                return kVar;
            }
        } else if (i.f.b == key) {
            return kVar;
        }
        return this;
    }

    @Override // i.g
    public final void releaseInterceptedContinuation(i.e eVar) {
        ((kotlinx.coroutines.internal.d) eVar).i();
    }

    public String toString() {
        return getClass().getSimpleName() + '@' + y.m(this);
    }

    public final s plus(s sVar) {
        return sVar;
    }
}
