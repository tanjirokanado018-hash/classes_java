package androidx.core.util;

import androidx.annotation.RequiresApi;
import i.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(24)
final class ContinuationConsumer<T> extends AtomicBoolean implements java.util.function.Consumer<T> {
    private final e continuation;

    public ContinuationConsumer(e eVar) {
        super(false);
        this.continuation = eVar;
    }

    @Override // java.util.function.Consumer
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
