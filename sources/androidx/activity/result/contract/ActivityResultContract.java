package androidx.activity.result.contract;

import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ActivityResultContract<I, O> {

    public static final class SynchronousResult<T> {
        private final T value;

        public SynchronousResult(T t2) {
            this.value = t2;
        }

        public final T getValue() {
            return this.value;
        }
    }

    public abstract Intent createIntent(Context context, I i2);

    public SynchronousResult<O> getSynchronousResult(Context context, I i2) {
        k.g(context, "context");
        return null;
    }

    public abstract O parseResult(int i2, Intent intent);
}
