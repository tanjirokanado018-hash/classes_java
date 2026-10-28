package u;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a implements g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final AtomicReference f503a;

    public a(k kVar) {
        this.f503a = new AtomicReference(kVar);
    }

    @Override // u.g
    public final Iterator iterator() {
        g gVar = (g) this.f503a.getAndSet(null);
        if (gVar != null) {
            return gVar.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}
