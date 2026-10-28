package androidx.core.util;

import g.i;
import i.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class ContinuationRunnable extends AtomicBoolean implements Runnable {
    private final e continuation;

    public ContinuationRunnable(e eVar) {
        super(false);
        this.continuation = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (compareAndSet(false, true)) {
            this.continuation.resumeWith(i.f423a);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicBoolean
    public String toString() {
        return "ContinuationRunnable(ran = " + get() + ')';
    }
}
