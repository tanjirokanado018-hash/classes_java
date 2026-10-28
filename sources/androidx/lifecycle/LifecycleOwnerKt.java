package androidx.lifecycle;

import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LifecycleOwnerKt {
    public static final LifecycleCoroutineScope getLifecycleScope(LifecycleOwner lifecycleOwner) {
        k.g(lifecycleOwner, "<this>");
        return LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle());
    }
}
