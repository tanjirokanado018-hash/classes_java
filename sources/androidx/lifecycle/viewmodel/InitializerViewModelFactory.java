package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.h;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class InitializerViewModelFactory implements ViewModelProvider.Factory {
    private final ViewModelInitializer<?>[] initializers;

    public InitializerViewModelFactory(ViewModelInitializer<?>... initializers) {
        k.g(initializers, "initializers");
        this.initializers = initializers;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final /* synthetic */ ViewModel create(Class cls) {
        return h.a(this, cls);
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        k.g(modelClass, "modelClass");
        k.g(extras, "extras");
        T t2 = null;
        for (ViewModelInitializer<?> viewModelInitializer : this.initializers) {
            if (k.a(viewModelInitializer.getClazz$lifecycle_viewmodel_release(), modelClass)) {
                Object objInvoke = viewModelInitializer.getInitializer$lifecycle_viewmodel_release().invoke(extras);
                t2 = objInvoke instanceof ViewModel ? (T) objInvoke : null;
            }
        }
        if (t2 != null) {
            return t2;
        }
        throw new IllegalArgumentException("No initializer set for given class ".concat(modelClass.getName()));
    }
}
