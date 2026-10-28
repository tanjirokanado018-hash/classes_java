package h;

import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class e implements Iterator {
    @Override // java.util.Iterator
    public final Boolean next() {
        return Boolean.valueOf(nextBoolean());
    }

    public abstract boolean nextBoolean();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public /* bridge */ /* synthetic */ Object next() {
        return Boolean.valueOf(nextBoolean());
    }
}
