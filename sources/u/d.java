package u;

import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d implements g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final g f507a;
    public final p.l b;

    public d(f fVar) {
        n nVar = n.b;
        this.f507a = fVar;
        this.b = nVar;
    }

    @Override // u.g
    public final Iterator iterator() {
        return new c(this);
    }
}
