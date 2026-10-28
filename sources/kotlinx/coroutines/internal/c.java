package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class c extends m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f443a = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "_consensus");
    private volatile /* synthetic */ Object _consensus = b.f440a;

    @Override // kotlinx.coroutines.internal.m
    public final Object a(Object obj) {
        Object obj2 = this._consensus;
        q qVar = b.f440a;
        if (obj2 == qVar) {
            q qVarC = c(obj);
            obj2 = this._consensus;
            if (obj2 == qVar) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f443a;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, qVar, qVarC)) {
                    if (atomicReferenceFieldUpdater.get(this) != qVar) {
                        obj2 = this._consensus;
                    }
                }
                obj2 = qVarC;
            }
        }
        b(obj, obj2);
        return obj2;
    }

    public abstract void b(Object obj, Object obj2);

    public abstract q c(Object obj);
}
