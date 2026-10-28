package androidx.core.util;

import i.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class AndroidXContinuationConsumer<T> extends AtomicBoolean implements Consumer<T> {
    private final e continuation;

    public AndroidXContinuationConsumer(e eVar) {
        super(false);
        this.continuation = eVar;
    }

    @Override // androidx.core.util.Consumer
    public void accept(T t2) {
        if (compareAndSet(false, true)) {
            this.continuation.resumeWith(t2);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicBoolean
    public String toString() {
        return "ContinuationConsumer(resultAccepted = " + get() + ')';
    }
}
