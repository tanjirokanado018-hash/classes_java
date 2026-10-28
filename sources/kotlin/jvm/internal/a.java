package kotlin.jvm.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a implements Iterator {
    public final Object[] b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f434c;

    public a(Object[] objArr) {
        this.b = objArr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f434c < this.b.length;
    }

    @Override // java.util.Iterator
    public final Object next() {
        try {
            Object[] objArr = this.b;
            int i2 = this.f434c;
            this.f434c = i2 + 1;
            return objArr[i2];
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.f434c--;
            throw new NoSuchElementException(e2.getMessage());
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
