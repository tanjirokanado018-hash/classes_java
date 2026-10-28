package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateHandleSupport$savedStateHandlesVM$1$1 extends l implements p.l {
    public static final SavedStateHandleSupport$savedStateHandlesVM$1$1 INSTANCE = new SavedStateHandleSupport$savedStateHandlesVM$1$1();

    public SavedStateHandleSupport$savedStateHandlesVM$1$1() {
        super(1);
    }

    @Override // p.l
    public final SavedStateHandlesVM invoke(CreationExtras initializer) {
        k.g(initializer, "$this$initializer");
        return new SavedStateHandlesVM();
    }
}
