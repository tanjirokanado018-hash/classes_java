package h;

import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class k implements Iterator {
    @Override // java.util.Iterator
    public final Long next() {
        return Long.valueOf(nextLong());
    }

    public abstract long nextLong();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public /* bridge */ /* synthetic */ Object next() {
        return Long.valueOf(nextLong());
    }
}
