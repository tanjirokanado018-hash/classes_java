package androidx.core.os;

import android.os.OutcomeReceiver;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.d;
import i.e;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
public final class OutcomeReceiverKt {
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static final <R, E extends Throwable> OutcomeReceiver<R, E> asOutcomeReceiver(e eVar) {
        return d.h(new ContinuationOutcomeReceiver(eVar));
    }
}
