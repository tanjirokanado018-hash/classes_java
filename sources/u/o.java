package u;

import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class o implements Iterator {
    public final Iterator b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ f f514c;

    public o(f fVar) {
        this.f514c = fVar;
        this.b = ((g) fVar.f510c).iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.f514c.b.invoke(this.b.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
