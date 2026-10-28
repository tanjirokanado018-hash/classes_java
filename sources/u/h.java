package u;

import java.util.Iterator;
import java.util.NoSuchElementException;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class h extends i implements Iterator, i.e {
    public int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f511c;
    public Iterator d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public i.e f512e;

    public final RuntimeException a() {
        int i2 = this.b;
        if (i2 == 4) {
            return new NoSuchElementException();
        }
        if (i2 == 5) {
            return new IllegalStateException("Iterator has failed.");
        }
        return new IllegalStateException("Unexpected state of the iterator: " + this.b);
    }

    @Override // i.e
    public final i.j getContext() {
        return i.k.b;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        while (true) {
            int i2 = this.b;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 == 2 || i2 == 3) {
                        return true;
                    }
                    if (i2 == 4) {
                        return false;
                    }
                    throw a();
                }
                Iterator it = this.d;
                kotlin.jvm.internal.k.d(it);
                if (it.hasNext()) {
                    this.b = 2;
                    return true;
                }
                this.d = null;
            }
            this.b = 5;
            i.e eVar = this.f512e;
            kotlin.jvm.internal.k.d(eVar);
            this.f512e = null;
            eVar.resumeWith(g.i.f423a);
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i2 = this.b;
        if (i2 == 0 || i2 == 1) {
            if (hasNext()) {
                return next();
            }
            throw new NoSuchElementException();
        }
        if (i2 == 2) {
            this.b = 1;
            Iterator it = this.d;
            kotlin.jvm.internal.k.d(it);
            return it.next();
        }
        if (i2 != 3) {
            throw a();
        }
        this.b = 0;
        Object obj = this.f511c;
        this.f511c = null;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // i.e
    public final void resumeWith(Object obj) throws Throwable {
        y.aj(obj);
        this.b = 4;
    }
}
