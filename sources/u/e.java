package u;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class e implements Iterator {
    public Object b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f508c = -2;
    public final /* synthetic */ f d;

    public e(f fVar) {
        this.d = fVar;
    }

    public final void a() {
        Object objInvoke;
        int i2 = this.f508c;
        f fVar = this.d;
        if (i2 == -2) {
            objInvoke = ((p.a) fVar.f510c).invoke();
        } else {
            p.l lVar = fVar.b;
            Object obj = this.b;
            kotlin.jvm.internal.k.d(obj);
            objInvoke = lVar.invoke(obj);
        }
        this.b = objInvoke;
        this.f508c = objInvoke == null ? 0 : 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f508c < 0) {
            a();
        }
        return this.f508c == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f508c < 0) {
            a();
        }
        if (this.f508c == 0) {
            throw new NoSuchElementException();
        }
        Object obj = this.b;
        kotlin.jvm.internal.k.e(obj, "null cannot be cast to non-null type T of kotlin.sequences.GeneratorSequence");
        this.f508c = -1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
