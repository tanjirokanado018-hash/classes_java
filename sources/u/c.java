package u;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c implements Iterator {
    public final Iterator b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f505c = -1;
    public Object d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ d f506e;

    public c(d dVar) {
        this.f506e = dVar;
        this.b = dVar.f507a.iterator();
    }

    public final void a() {
        int i2;
        Object next;
        boolean zBooleanValue;
        do {
            Iterator it = this.b;
            if (it.hasNext()) {
                next = it.next();
                d dVar = this.f506e;
                zBooleanValue = ((Boolean) dVar.b.invoke(next)).booleanValue();
                dVar.getClass();
            } else {
                i2 = 0;
            }
            this.f505c = i2;
        } while (zBooleanValue);
        this.d = next;
        i2 = 1;
        this.f505c = i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f505c == -1) {
            a();
        }
        return this.f505c == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f505c == -1) {
            a();
        }
        if (this.f505c == 0) {
            throw new NoSuchElementException();
        }
        Object obj = this.d;
        this.d = null;
        this.f505c = -1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
