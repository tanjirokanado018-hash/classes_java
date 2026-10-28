package androidx.lifecycle;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public interface LifecycleRegistryOwner extends LifecycleOwner {
    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    /* bridge */ /* synthetic */ Lifecycle getLifecycle();

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    LifecycleRegistry getLifecycle();
}
