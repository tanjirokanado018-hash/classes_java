package androidx.lifecycle.viewmodel;

import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MutableCreationExtras extends CreationExtras {
    /* JADX WARN: Multi-variable type inference failed */
    public MutableCreationExtras() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.lifecycle.viewmodel.CreationExtras
    public <T> T get(CreationExtras.Key<T> key) {
        k.g(key, "key");
        return (T) getMap$lifecycle_viewmodel_release().get(key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void set(CreationExtras.Key<T> key, T t2) {
        k.g(key, "key");
        getMap$lifecycle_viewmodel_release().put(key, t2);
    }

    public MutableCreationExtras(CreationExtras initialExtras) {
        k.g(initialExtras, "initialExtras");
        getMap$lifecycle_viewmodel_release().putAll(initialExtras.getMap$lifecycle_viewmodel_release());
    }

    public /* synthetic */ MutableCreationExtras(CreationExtras creationExtras, int i2, f fVar) {
        this((i2 & 1) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }
}
