package androidx.core.os;

import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    public OperationCanceledException(@Nullable String str) {
        super(ObjectsCompat.toString(str, "The operation has been canceled."));
    }
}
