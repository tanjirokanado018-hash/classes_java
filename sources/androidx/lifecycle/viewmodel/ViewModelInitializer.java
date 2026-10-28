package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewModelInitializer<T extends ViewModel> {
    private final Class<T> clazz;
    private final l initializer;

    public ViewModelInitializer(Class<T> clazz, l initializer) {
        k.g(clazz, "clazz");
        k.g(initializer, "initializer");
        this.clazz = clazz;
        this.initializer = initializer;
    }

    public final Class<T> getClazz$lifecycle_viewmodel_release() {
        return this.clazz;
    }

    public final l getInitializer$lifecycle_viewmodel_release() {
        return this.initializer;
    }
}
