package w;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ax extends kotlinx.coroutines.internal.c {
    public final kotlinx.coroutines.internal.g b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public kotlinx.coroutines.internal.g f533c;
    public final /* synthetic */ ay d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f534e;

    public ax(kotlinx.coroutines.internal.g gVar, ay ayVar, Object obj) {
        this.d = ayVar;
        this.f534e = obj;
        this.b = gVar;
    }

    @Override // kotlinx.coroutines.internal.c
    public final void b(Object obj, Object obj2) {
        kotlinx.coroutines.internal.g gVar = (kotlinx.coroutines.internal.g) obj;
        boolean z2 = obj2 == null;
        kotlinx.coroutines.internal.g gVar2 = this.b;
        kotlinx.coroutines.internal.g gVar3 = z2 ? gVar2 : this.f533c;
        if (gVar3 != null) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = kotlinx.coroutines.internal.g.b;
            while (!atomicReferenceFieldUpdater.compareAndSet(gVar, this, gVar3)) {
                if (atomicReferenceFieldUpdater.get(gVar) != this) {
                    return;
                }
            }
            if (z2) {
                kotlinx.coroutines.internal.g gVar4 = this.f533c;
                kotlin.jvm.internal.k.d(gVar4);
                gVar2.f(gVar4);
            }
        }
    }

    @Override // kotlinx.coroutines.internal.c
    public final kotlinx.coroutines.internal.q c(Object obj) {
        if (this.d.q() == this.f534e) {
            return null;
        }
        return kotlinx.coroutines.internal.b.d;
    }
}
