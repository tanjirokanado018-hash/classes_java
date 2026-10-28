package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class n {
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(n.class, Object.class, "lastScheduledTask");

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f491c = AtomicIntegerFieldUpdater.newUpdater(n.class, "producerIndex");
    public static final /* synthetic */ AtomicIntegerFieldUpdater d = AtomicIntegerFieldUpdater.newUpdater(n.class, "consumerIndex");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f492e = AtomicIntegerFieldUpdater.newUpdater(n.class, "blockingTasksInBuffer");

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final AtomicReferenceArray f493a = new AtomicReferenceArray(128);
    private volatile /* synthetic */ Object lastScheduledTask = null;
    private volatile /* synthetic */ int producerIndex = 0;
    private volatile /* synthetic */ int consumerIndex = 0;
    private volatile /* synthetic */ int blockingTasksInBuffer = 0;

    public final i a(i iVar) {
        if (iVar.f484c.f485a == 1) {
            f492e.incrementAndGet(this);
        }
        if (this.producerIndex - this.consumerIndex == 127) {
            return iVar;
        }
        int i2 = this.producerIndex & 127;
        while (this.f493a.get(i2) != null) {
            Thread.yield();
        }
        this.f493a.lazySet(i2, iVar);
        f491c.incrementAndGet(this);
        return null;
    }

    public final int b() {
        return this.lastScheduledTask != null ? (this.producerIndex - this.consumerIndex) + 1 : this.producerIndex - this.consumerIndex;
    }

    public final i c() {
        i iVar;
        while (true) {
            int i2 = this.consumerIndex;
            if (i2 - this.producerIndex == 0) {
                return null;
            }
            int i3 = i2 & 127;
            if (d.compareAndSet(this, i2, i2 + 1) && (iVar = (i) this.f493a.getAndSet(i3, null)) != null) {
                if (iVar.f484c.f485a == 1) {
                    f492e.decrementAndGet(this);
                }
                return iVar;
            }
        }
    }

    public final long d(n nVar) {
        int i2 = nVar.producerIndex;
        AtomicReferenceArray atomicReferenceArray = nVar.f493a;
        for (int i3 = nVar.consumerIndex; i3 != i2; i3++) {
            int i4 = i3 & 127;
            if (nVar.blockingTasksInBuffer == 0) {
                break;
            }
            i iVar = (i) atomicReferenceArray.get(i4);
            if (iVar != null && iVar.f484c.f485a == 1) {
                do {
                    if (atomicReferenceArray.compareAndSet(i4, iVar, null)) {
                        f492e.decrementAndGet(nVar);
                        i iVar2 = (i) b.getAndSet(this, iVar);
                        if (iVar2 == null) {
                            return -1L;
                        }
                        a(iVar2);
                        return -1L;
                    }
                } while (atomicReferenceArray.get(i4) == iVar);
            }
        }
        return e(nVar, true);
    }

    public final long e(n nVar, boolean z2) {
        while (true) {
            i iVar = (i) nVar.lastScheduledTask;
            if (iVar == null) {
                return -2L;
            }
            if (z2 && iVar.f484c.f485a != 1) {
                return -2L;
            }
            l.f488e.getClass();
            long jNanoTime = System.nanoTime() - iVar.b;
            long j2 = l.f486a;
            if (jNanoTime < j2) {
                return j2 - jNanoTime;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(nVar, iVar, null)) {
                    i iVar2 = (i) b.getAndSet(this, iVar);
                    if (iVar2 == null) {
                        return -1L;
                    }
                    a(iVar2);
                    return -1L;
                }
            } while (atomicReferenceFieldUpdater.get(nVar) == iVar);
        }
    }
}
