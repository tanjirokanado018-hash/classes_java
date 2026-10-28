package androidx.core.os;

import android.os.OutcomeReceiver;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import i.e;
import java.lang.Throwable;
import java.util.concurrent.atomic.AtomicBoolean;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
final class ContinuationOutcomeReceiver<R, E extends Throwable> extends AtomicBoolean implements OutcomeReceiver<R, E> {
    private final e continuation;

    public ContinuationOutcomeReceiver(e eVar) {
        super(false);
        this.continuation = eVar;
    }

    @Override // android.os.OutcomeReceiver
    public void onError(E e2) {
        if (compareAndSet(false, true)) {
            this.continuation.resumeWith(y.f(e2));
        }
    }

    @Override // android.os.OutcomeReceiver
    public void onResult(R r2) {
        if (compareAndSet(false, true)) {
            this.continuation.resumeWith(r2);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicBoolean
    public String toString() {
        return "ContinuationOutcomeReceiver(outcomeReceived = " + get() + ')';
    }
}
