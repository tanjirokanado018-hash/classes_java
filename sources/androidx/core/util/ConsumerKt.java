package androidx.core.util;

import androidx.annotation.RequiresApi;
import i.e;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(24)
public final class ConsumerKt {
    @RequiresApi(24)
    public static final <T> java.util.function.Consumer<T> asConsumer(e eVar) {
        return androidx.core.os.a.t(new ContinuationConsumer(eVar));
    }
}
